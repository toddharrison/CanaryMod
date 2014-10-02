package net.minecraft.stats;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S37PacketStatistics;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.IJsonSerializable;
import net.minecraft.util.TupleIntJsonSerializable;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.*;
import java.util.Map.Entry;

public class StatisticsFile extends StatFileWriter {

    private static final Logger b = LogManager.getLogger();
    private final MinecraftServer c;
    private final File d;
    private final Set e = Sets.newHashSet();
    private int f = -300;
    private boolean g = false;

    public StatisticsFile(MinecraftServer minecraftserver, File file1) {
        this.c = minecraftserver;
        this.d = file1;
    }

    public void a() {
        if (this.d.isFile()) {
            try {
                this.a.clear();
                this.a.putAll(this.a(FileUtils.readFileToString(this.d)));
            }
            catch (IOException ioexception) {
                b.error("Couldn\'t read statistics file " + this.d, ioexception);
            }
            catch (JsonParseException jsonparseexception) {
                b.error("Couldn\'t parse statistics file " + this.d, jsonparseexception);
            }
        }
    }

    public void b() {
        try {
            FileUtils.writeStringToFile(this.d, a(this.a));
        }
        catch (IOException ioexception) {
            b.error("Couldn\'t save stats", ioexception);
        }
    }

    public void a(EntityPlayer entityplayer, StatBase statbase, int i0) {
        int i1 = statbase.d() ? this.a(statbase) : 0;

        super.a(entityplayer, statbase, i0);
        this.e.add(statbase);
        if (statbase.d() && i1 == 0 && i0 > 0) {
            this.g = true;
            if (this.c.az() && entityplayer != null) { // CanaryMod: null check
                this.c.an().a((IChatComponent)(new ChatComponentTranslation("chat.type.achievement", new Object[]{ entityplayer.e_(), statbase.j() })));
            }
        }

        if (statbase.d() && i1 > 0 && i0 == 0) {
            this.g = true;
            if (this.c.az()) {
                this.c.an().a((IChatComponent)(new ChatComponentTranslation("chat.type.achievement.taken", new Object[]{ entityplayer.e_(), statbase.j() })));
            }
        }
    }

    public Set c() {
        HashSet hashset = Sets.newHashSet(this.e);

        this.e.clear();
        this.g = false;
        return hashset;
    }

    public Map a(String s0) {
        JsonElement jsonelement = (new JsonParser()).parse(s0);

        if (!jsonelement.isJsonObject()) {
            return Maps.newHashMap();
        }
        else {
            JsonObject jsonobject = jsonelement.getAsJsonObject();
            HashMap hashmap = Maps.newHashMap();
            Iterator iterator = jsonobject.entrySet().iterator();

            while (iterator.hasNext()) {
                Entry entry = (Entry)iterator.next();
                StatBase statbase = StatList.a((String)entry.getKey());

                if (statbase != null) {
                    TupleIntJsonSerializable tupleintjsonserializable = new TupleIntJsonSerializable();

                    if (((JsonElement)entry.getValue()).isJsonPrimitive() && ((JsonElement)entry.getValue()).getAsJsonPrimitive().isNumber()) {
                        tupleintjsonserializable.a(((JsonElement)entry.getValue()).getAsInt());
                    }
                    else if (((JsonElement)entry.getValue()).isJsonObject()) {
                        JsonObject jsonobject1 = ((JsonElement)entry.getValue()).getAsJsonObject();

                        if (jsonobject1.has("value") && jsonobject1.get("value").isJsonPrimitive() && jsonobject1.get("value").getAsJsonPrimitive().isNumber()) {
                            tupleintjsonserializable.a(jsonobject1.getAsJsonPrimitive("value").getAsInt());
                        }

                        if (jsonobject1.has("progress") && statbase.l() != null) {
                            try {
                                Constructor constructor = statbase.l().getConstructor(new Class[0]);
                                IJsonSerializable ijsonserializable = (IJsonSerializable)constructor.newInstance(new Object[0]);

                                ijsonserializable.a(jsonobject1.get("progress"));
                                tupleintjsonserializable.a(ijsonserializable);
                            }
                            catch (Throwable throwable) {
                                b.warn("Invalid statistic progress in " + this.d, throwable);
                            }
                        }
                    }

                    hashmap.put(statbase, tupleintjsonserializable);
                }
                else {
                    b.warn("Invalid statistic in " + this.d + ": Don\'t know what " + (String)entry.getKey() + " is");
                }
            }

            return hashmap;
        }
    }

    public static String a(Map map) {
        JsonObject jsonobject = new JsonObject();
        Iterator iterator = map.entrySet().iterator();

        while (iterator.hasNext()) {
            Entry entry = (Entry)iterator.next();

            if (((TupleIntJsonSerializable)entry.getValue()).b() != null) {
                JsonObject jsonobject1 = new JsonObject();

                jsonobject1.addProperty("value", Integer.valueOf(((TupleIntJsonSerializable)entry.getValue()).a()));

                try {
                    jsonobject1.add("progress", ((TupleIntJsonSerializable)entry.getValue()).b().a());
                }
                catch (Throwable throwable) {
                    b.warn("Couldn\'t save statistic " + ((StatBase)entry.getKey()).e() + ": error serializing progress", throwable);
                }

                jsonobject.add(((StatBase)entry.getKey()).e, jsonobject1);
            }
            else {
                jsonobject.addProperty(((StatBase)entry.getKey()).e, Integer.valueOf(((TupleIntJsonSerializable)entry.getValue()).a()));
            }
        }

        return jsonobject.toString();
    }

    public void d() {
        Iterator iterator = this.a.keySet().iterator();

        while (iterator.hasNext()) {
            StatBase statbase = (StatBase)iterator.next();

            this.e.add(statbase);
        }
    }

    public void a(EntityPlayerMP entityplayermp) {
        int i0 = this.c.ar();
        HashMap hashmap = Maps.newHashMap();

        if (this.g || i0 - this.f > 300) {
            this.f = i0;
            Iterator iterator = this.c().iterator();

            while (iterator.hasNext()) {
                StatBase statbase = (StatBase)iterator.next();

                hashmap.put(statbase, Integer.valueOf(this.a(statbase)));
            }
        }

        // CanaryMod: null check
        if (entityplayermp != null) {
            entityplayermp.a.a((Packet)(new S37PacketStatistics(hashmap)));
        }
    }

    public void b(EntityPlayerMP entityplayermp) {
        HashMap hashmap = Maps.newHashMap();
        Iterator iterator = AchievementList.e.iterator();

        while (iterator.hasNext()) {
            Achievement achievement = (Achievement)iterator.next();

            if (this.a(achievement)) {
                hashmap.put(achievement, Integer.valueOf(this.a((StatBase)achievement)));
                this.e.remove(achievement);
            }
        }

        // CanaryMod: null check
        if (entityplayermp != null) {
            entityplayermp.a.a((Packet)(new S37PacketStatistics(hashmap)));
        }
    }

    public boolean e() {
        return this.g;
    }
}
