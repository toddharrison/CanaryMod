package net.minecraft.world.storage;

import net.canarymod.api.nbt.CanaryCompoundTag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.storage.IChunkLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SaveHandler implements ISaveHandler, IPlayerFileData {

    private static final Logger a = LogManager.getLogger();
    private final File worldDir; // CanaryMod renamed from b
    private final File globalPlayerFilesDir; // CanaryMod renamed from c
    private final File worldbaseDir; // CanaryMod
    private final File worldDataDir; // CanaryMod renamed from d
    private final long e = MinecraftServer.ap();
    private final String worldName; // CanaryMod renamed from f
    protected net.canarymod.api.world.DimensionType type;

    public SaveHandler(File file1, String s0, boolean flag0, net.canarymod.api.world.DimensionType type) {
        // CanaryMod refactored for more flexible folder structure
        this.worldDir = new File(file1, s0 + "/" + s0 + "_" + type.getName());
        this.worldDir.mkdirs();

        // CanaryMod put the players files in a global place valid for all worlds
        this.globalPlayerFilesDir = new File(file1, "players");
        // CanaryMod move the data dir into a world-specific folder
        this.worldDataDir = new File(this.worldDir, "data");
        this.worldDataDir.mkdirs();
        this.worldName = s0;
        this.worldbaseDir = file1;
        if (flag0) {
            this.globalPlayerFilesDir.mkdirs();
        }
        this.type = type;
        this.h();
    }

    // CanaryMod added getname

    /**
     * get the base name of this world saver (only world name, without dimension appendix)
     *
     * @return
     */
    public String getBaseName() {
        return this.worldName;
    }

    // CanaryMod

    /**
     * get the dir folder (worlds/)
     *
     * @return
     */
    public File getWorldBaseDir() {
        return worldbaseDir;
    }

    private void h() {
        try {
            File file1 = new File(this.worldDir, "session.lock");
            DataOutputStream dataoutputstream = new DataOutputStream(new FileOutputStream(file1));

            try {
                dataoutputstream.writeLong(this.e);
            }
            finally {
                dataoutputstream.close();
            }
        }
        catch (IOException ioexception) {
            ioexception.printStackTrace();
            throw new RuntimeException("Failed to check session lock, aborting");
        }
    }

    public File b() {
        return this.worldDir;
    }

    @Override
    public void c() throws MinecraftException {
        try {
            File file1 = new File(this.worldDir, "session.lock");
            DataInputStream datainputstream = new DataInputStream(new FileInputStream(file1));

            try {
                if (datainputstream.readLong() != this.e) {
                    throw new MinecraftException("The save is being accessed from another location, aborting");
                }
            }
            finally {
                datainputstream.close();
            }
        }
        catch (IOException ioexception) {
            throw new MinecraftException("Failed to check session lock, aborting");
        }
    }

    @Override
    public IChunkLoader a(WorldProvider worldprovider) {
        throw new RuntimeException("Old Chunk Storage is no longer supported.");
    }

    @Override
    public WorldInfo d() {
        File file1 = new File(this.worldDir, "level.dat");
        NBTTagCompound nbttagcompound;
        NBTTagCompound nbttagcompound1;

        if (file1.exists()) {
            try {
                nbttagcompound = CompressedStreamTools.a((InputStream) (new FileInputStream(file1)));
                nbttagcompound1 = nbttagcompound.m("Data");
                return new WorldInfo(nbttagcompound1);
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        file1 = new File(this.worldDir, "level.dat_old");
        if (file1.exists()) {
            try {
                nbttagcompound = CompressedStreamTools.a((InputStream) (new FileInputStream(file1)));
                nbttagcompound1 = nbttagcompound.m("Data");
                return new WorldInfo(nbttagcompound1);
            }
            catch (Exception exception1) {
                exception1.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public void a(WorldInfo worldinfo, NBTTagCompound nbttagcompound) {
        NBTTagCompound nbttagcompound1 = worldinfo.a(nbttagcompound);
        NBTTagCompound nbttagcompound2 = new NBTTagCompound();

        nbttagcompound2.a("Data", (NBTBase) nbttagcompound1);

        try {
            File file1 = new File(this.worldDir, "level.dat_new");
            File file2 = new File(this.worldDir, "level.dat_old");
            File file3 = new File(this.worldDir, "level.dat");

            CompressedStreamTools.a(nbttagcompound2, (OutputStream) (new FileOutputStream(file1)));
            if (file2.exists()) {
                file2.delete();
            }

            file3.renameTo(file2);
            if (file3.exists()) {
                file3.delete();
            }

            file1.renameTo(file3);
            if (file1.exists()) {
                file1.delete();
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    @Override
    public void a(WorldInfo worldinfo) {
        NBTTagCompound nbttagcompound = worldinfo.a();
        NBTTagCompound nbttagcompound1 = new NBTTagCompound();

        nbttagcompound1.a("Data", (NBTBase) nbttagcompound);

        try {
            File file1 = new File(this.worldDir, "level.dat_new");
            File file2 = new File(this.worldDir, "level.dat_old");
            File file3 = new File(this.worldDir, "level.dat");

            CompressedStreamTools.a(nbttagcompound1, (OutputStream) (new FileOutputStream(file1)));
            if (file2.exists()) {
                file2.delete();
            }

            file3.renameTo(file2);
            if (file3.exists()) {
                file3.delete();
            }

            file1.renameTo(file3);
            if (file1.exists()) {
                file1.delete();
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    @Override
    public void a(EntityPlayer entityplayer) {
        try {
            NBTTagCompound nbttagcompound = new NBTTagCompound();

            entityplayer.e(nbttagcompound);
            File file1 = new File(this.globalPlayerFilesDir, entityplayer.b_() + ".dat.tmp");
            File file2 = new File(this.globalPlayerFilesDir, entityplayer.b_() + ".dat");

            CompressedStreamTools.a(nbttagcompound, (OutputStream) (new FileOutputStream(file1)));
            if (file2.exists()) {
                file2.delete();
            }

            file1.renameTo(file2);
        }
        catch (Exception exception) {
            a.warn("Failed to save player data for " + entityplayer.b_());
        }
    }

    @Override
    public NBTTagCompound b(EntityPlayer entityplayer) {
        NBTTagCompound nbttagcompound = this.a(entityplayer.b_());

        if (nbttagcompound != null) {
            entityplayer.f(nbttagcompound);
        }

        return nbttagcompound;
    }

    public NBTTagCompound a(String s0) {
        try {
            File file1 = new File(this.globalPlayerFilesDir, s0 + ".dat");

            if (file1.exists()) {
                return CompressedStreamTools.a((InputStream) (new FileInputStream(file1)));
            }
        }
        catch (Exception exception) {
            a.warn("Failed to load player data for " + s0);
        }

        return null;
    }

    @Override
    public IPlayerFileData e() {
        return this;
    }

    @Override
    public String[] f() {
        String[] astring = this.globalPlayerFilesDir.list();

        for (int i0 = 0; i0 < astring.length; ++i0) {
            if (astring[i0].endsWith(".dat")) {
                astring[i0] = astring[i0].substring(0, astring[i0].length() - 4);
            }
        }

        return astring;
    }

    public void a() {
    }

    @Override
    public File b(String s0) {
        return new File(this.worldDataDir, s0 + ".dat");
    }

    @Override
    public String g() {
        return this.worldName;
    }

    // CanaryMod enable writing dat files from player name and a given base tag
    // This is a copy of this.a(EntityPlayer and might need adjustments accordingly!)
    public void writePlayerNbt(String player, CanaryCompoundTag tag) {
        try {
            NBTTagCompound nbttagcompound = tag.getHandle();

            File file1 = new File(this.worldDir, player + ".dat.tmp");
            File file2 = new File(this.worldDir, player + ".dat");

            CompressedStreamTools.a(nbttagcompound, (OutputStream) (new FileOutputStream(file1)));
            if (file2.exists()) {
                file2.delete();
            }
            file1.renameTo(file2);
        }
        catch (Exception exception) {
            a.warn("Failed to save player data for " + player);
        }
    }
    // CanaryMod end
}
