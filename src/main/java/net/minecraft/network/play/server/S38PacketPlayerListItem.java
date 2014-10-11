package net.minecraft.network.play.server;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.canarymod.api.PlayerListAction;
import net.canarymod.api.PlayerListData;
import net.canarymod.api.chat.CanaryChatComponent;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.WorldSettings;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class S38PacketPlayerListItem implements Packet {

    private S38PacketPlayerListItem.Action a;
    private final List b = Lists.newArrayList();

    public S38PacketPlayerListItem() {
    }

    public S38PacketPlayerListItem(S38PacketPlayerListItem.Action s38packetplayerlistitem_action, EntityPlayerMP... aentityplayermp) {
        this.a = s38packetplayerlistitem_action;
        EntityPlayerMP[] aentityplayermp1 = aentityplayermp;
        int i0 = aentityplayermp.length;

        for (int i1 = 0; i1 < i0; ++i1) {
            EntityPlayerMP entityplayermp = aentityplayermp1[i1];

            this.b.add(new S38PacketPlayerListItem.AddPlayerData(entityplayermp.cc(), entityplayermp.h, entityplayermp.c.b(), entityplayermp.E()));
        }
    }

    public S38PacketPlayerListItem(S38PacketPlayerListItem.Action s38packetplayerlistitem_action, Iterable iterable) {
        this.a = s38packetplayerlistitem_action;
        Iterator iterator = iterable.iterator();

        while (iterator.hasNext()) {
            EntityPlayerMP entityplayermp = (EntityPlayerMP)iterator.next();

            this.b.add(new S38PacketPlayerListItem.AddPlayerData(entityplayermp.cc(), entityplayermp.h, entityplayermp.c.b(), entityplayermp.E()));
        }
    }

    // CanaryMod: New construct to just pass an AddPlayerData
    public S38PacketPlayerListItem(PlayerListAction action, PlayerListData data){
        this.a = S38PacketPlayerListItem.Action.valueOf(action.name());
        WorldSettings.GameType gameType = WorldSettings.GameType.a(data.getMode().getId());
        IChatComponent iChatComponent = data.displayNameSet() ? ((CanaryChatComponent)data.getDisplayName()).getNative() : null;
        this.b.add(new AddPlayerData(data.getProfile(), data.getPing(), gameType, iChatComponent));
    }

    public void a(PacketBuffer packetbuffer) throws IOException {
        this.a = (S38PacketPlayerListItem.Action)packetbuffer.a(S38PacketPlayerListItem.Action.class);
        int i0 = packetbuffer.e();

        for (int i1 = 0; i1 < i0; ++i1) {
            GameProfile gameprofile = null;
            int i2 = 0;
            WorldSettings.GameType worldsettings_gametype = null;
            IChatComponent ichatcomponent = null;

            switch (S38PacketPlayerListItem.SwitchAction.a[this.a.ordinal()]) {
                case 1:
                    gameprofile = new GameProfile(packetbuffer.g(), packetbuffer.c(16));
                    int i3 = packetbuffer.e();

                    for (int i4 = 0; i4 < i3; ++i4) {
                        String s0 = packetbuffer.c(32767);
                        String s1 = packetbuffer.c(32767);

                        if (packetbuffer.readBoolean()) {
                            gameprofile.getProperties().put(s0, new Property(s0, s1, packetbuffer.c(32767)));
                        }
                        else {
                            gameprofile.getProperties().put(s0, new Property(s0, s1));
                        }
                    }

                    worldsettings_gametype = WorldSettings.GameType.a(packetbuffer.e());
                    i2 = packetbuffer.e();
                    if (packetbuffer.readBoolean()) {
                        ichatcomponent = packetbuffer.d();
                    }
                    break;

                case 2:
                    gameprofile = new GameProfile(packetbuffer.g(), (String)null);
                    worldsettings_gametype = WorldSettings.GameType.a(packetbuffer.e());
                    break;

                case 3:
                    gameprofile = new GameProfile(packetbuffer.g(), (String)null);
                    i2 = packetbuffer.e();
                    break;

                case 4:
                    gameprofile = new GameProfile(packetbuffer.g(), (String)null);
                    if (packetbuffer.readBoolean()) {
                        ichatcomponent = packetbuffer.d();
                    }
                    break;

                case 5:
                    gameprofile = new GameProfile(packetbuffer.g(), (String)null);
            }

            this.b.add(new S38PacketPlayerListItem.AddPlayerData(gameprofile, i2, worldsettings_gametype, ichatcomponent));
        }
    }

    public void b(PacketBuffer packetbuffer) throws IOException {
        packetbuffer.a((Enum)this.a);
        packetbuffer.b(this.b.size());
        Iterator iterator = this.b.iterator();

        while (iterator.hasNext()) {
            S38PacketPlayerListItem.AddPlayerData s38packetplayerlistitem_addplayerdata = (S38PacketPlayerListItem.AddPlayerData)iterator.next();

            switch (S38PacketPlayerListItem.SwitchAction.a[this.a.ordinal()]) {
                case 1:
                    packetbuffer.a(s38packetplayerlistitem_addplayerdata.a().getId());
                    packetbuffer.a(s38packetplayerlistitem_addplayerdata.a().getName());
                    packetbuffer.b(s38packetplayerlistitem_addplayerdata.a().getProperties().size());
                    Iterator iterator1 = s38packetplayerlistitem_addplayerdata.a().getProperties().values().iterator();

                    while (iterator1.hasNext()) {
                        Property property = (Property)iterator1.next();

                        packetbuffer.a(property.getName());
                        packetbuffer.a(property.getValue());
                        if (property.hasSignature()) {
                            packetbuffer.writeBoolean(true);
                            packetbuffer.a(property.getSignature());
                        }
                        else {
                            packetbuffer.writeBoolean(false);
                        }
                    }

                    packetbuffer.b(s38packetplayerlistitem_addplayerdata.c().a());
                    packetbuffer.b(s38packetplayerlistitem_addplayerdata.b());
                    if (s38packetplayerlistitem_addplayerdata.d() == null) {
                        packetbuffer.writeBoolean(false);
                    }
                    else {
                        packetbuffer.writeBoolean(true);
                        packetbuffer.a(s38packetplayerlistitem_addplayerdata.d());
                    }
                    break;

                case 2:
                    packetbuffer.a(s38packetplayerlistitem_addplayerdata.a().getId());
                    packetbuffer.b(s38packetplayerlistitem_addplayerdata.c().a());
                    break;

                case 3:
                    packetbuffer.a(s38packetplayerlistitem_addplayerdata.a().getId());
                    packetbuffer.b(s38packetplayerlistitem_addplayerdata.b());
                    break;

                case 4:
                    packetbuffer.a(s38packetplayerlistitem_addplayerdata.a().getId());
                    if (s38packetplayerlistitem_addplayerdata.d() == null) {
                        packetbuffer.writeBoolean(false);
                    }
                    else {
                        packetbuffer.writeBoolean(true);
                        packetbuffer.a(s38packetplayerlistitem_addplayerdata.d());
                    }
                    break;

                case 5:
                    packetbuffer.a(s38packetplayerlistitem_addplayerdata.a().getId());
            }
        }
    }

    public void a(INetHandlerPlayClient inethandlerplayclient) {
        inethandlerplayclient.a(this);
    }

    public void a(INetHandler inethandler) {
        this.a((INetHandlerPlayClient)inethandler);
    }

    public static enum Action {

        ADD_PLAYER("ADD_PLAYER", 0),
        UPDATE_GAME_MODE("UPDATE_GAME_MODE", 1),
        UPDATE_LATENCY("UPDATE_LATENCY", 2),
        UPDATE_DISPLAY_NAME("UPDATE_DISPLAY_NAME", 3),
        REMOVE_PLAYER("REMOVE_PLAYER", 4);

        private static final S38PacketPlayerListItem.Action[] $VALUES = new S38PacketPlayerListItem.Action[]{ ADD_PLAYER, UPDATE_GAME_MODE, UPDATE_LATENCY, UPDATE_DISPLAY_NAME, REMOVE_PLAYER };

        private Action(String p_i45966_1_, int p_i45966_2_) {
        }

    }

    public class AddPlayerData {

        private final int b;
        private final WorldSettings.GameType c;
        private final GameProfile d;
        private final IChatComponent e;

        public AddPlayerData(GameProfile p_i45965_2_, int p_i45965_3_, WorldSettings.GameType p_i45965_4_, IChatComponent p_i45965_5_) {
            this.d = p_i45965_2_;
            this.b = p_i45965_3_;
            this.c = p_i45965_4_;
            this.e = p_i45965_5_;
        }

        public GameProfile a() {
            return this.d;
        }

        public int b() {
            return this.b;
        }

        public WorldSettings.GameType c() {
            return this.c;
        }

        public IChatComponent d() {
            return this.e;
        }
    }

    static final class SwitchAction {

        static final int[] a = new int[S38PacketPlayerListItem.Action.values().length];

        static {
            try {
                a[S38PacketPlayerListItem.Action.ADD_PLAYER.ordinal()] = 1;
            }
            catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                a[S38PacketPlayerListItem.Action.UPDATE_GAME_MODE.ordinal()] = 2;
            }
            catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                a[S38PacketPlayerListItem.Action.UPDATE_LATENCY.ordinal()] = 3;
            }
            catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                a[S38PacketPlayerListItem.Action.UPDATE_DISPLAY_NAME.ordinal()] = 4;
            }
            catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

            try {
                a[S38PacketPlayerListItem.Action.REMOVE_PLAYER.ordinal()] = 5;
            }
            catch (NoSuchFieldError nosuchfielderror4) {
                ;
            }
        }
    }
}
