package net.minecraft.world.chunk.storage;


import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.SaveFormatOld;
import net.minecraft.world.storage.WorldInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;


public class AnvilSaveConverter extends SaveFormatOld {

    private static final Logger b = LogManager.getLogger();

    public AnvilSaveConverter(File file1, net.canarymod.api.world.DimensionType type) {
        super(file1, type);
    }

    protected int c() {
        return 19133;
    }

    public void d() {
        RegionFileCache.a();
    }

    public ISaveHandler a(String s0, boolean flag0) {
        return new AnvilSaveHandler(this.a, s0, flag0, type);
    }

    public boolean b(String s0) {
        WorldInfo worldinfo = this.c(s0);

        return worldinfo != null && worldinfo.l() != this.c();
    }

    public boolean a(String s0, IProgressUpdate iprogressupdate) {
        iprogressupdate.a(0);
        ArrayList arraylist = new ArrayList();
        ArrayList arraylist1 = new ArrayList();
        ArrayList arraylist2 = new ArrayList();
        File file1 = new File(this.a, s0);
        File file2 = new File(file1, "DIM-1");
        File file3 = new File(file1, "DIM1");

        b.info("Scanning folders...");
        this.a(file1, (Collection) arraylist);
        if (file2.exists()) {
            this.a(file2, (Collection) arraylist1);
        }

        if (file3.exists()) {
            this.a(file3, (Collection) arraylist2);
        }

        int i0 = arraylist.size() + arraylist1.size() + arraylist2.size();

        b.info("Total conversion count is " + i0);
        WorldInfo worldinfo = this.c(s0);
        Object object = null;

        if (worldinfo.u() == WorldType.c) {
            object = new WorldChunkManagerHell(BiomeGenBase.p, 0.5F);
        }
        else {
            object = new WorldChunkManager(worldinfo.b(), worldinfo.u());
        }

        this.a(new File(file1, "region"), (Iterable) arraylist, (WorldChunkManager) object, 0, i0, iprogressupdate);
        this.a(new File(file2, "region"), (Iterable) arraylist1, new WorldChunkManagerHell(BiomeGenBase.w, 0.0F), arraylist.size(), i0, iprogressupdate);
        this.a(new File(file3, "region"), (Iterable) arraylist2, new WorldChunkManagerHell(BiomeGenBase.x, 0.0F), arraylist.size() + arraylist1.size(), i0, iprogressupdate);
        worldinfo.e(19133);
        if (worldinfo.u() == WorldType.f) {
            worldinfo.a(WorldType.b);
        }

        this.g(s0);
        ISaveHandler isavehandler = this.a(s0, false);

        isavehandler.a(worldinfo);
        return true;
    }

    private void g(String s0) {
        File file1 = new File(this.a, s0);

        if (!file1.exists()) {
            b.warn("Unable to create level.dat_mcr backup");
        }
        else {
            File file2 = new File(file1, "level.dat");

            if (!file2.exists()) {
                b.warn("Unable to create level.dat_mcr backup");
            }
            else {
                File file3 = new File(file1, "level.dat_mcr");

                if (!file2.renameTo(file3)) {
                    b.warn("Unable to create level.dat_mcr backup");
                }

            }
        }
    }

    private void a(File file1, Iterable iterable, WorldChunkManager worldchunkmanager, int i0, int i1, IProgressUpdate iprogressupdate) {
        Iterator iterator = iterable.iterator();

        while (iterator.hasNext()) {
            File file2 = (File) iterator.next();

            this.a(file1, file2, worldchunkmanager, i0, i1, iprogressupdate);
            ++i0;
            int i2 = (int) Math.round(100.0D * (double) i0 / (double) i1);

            iprogressupdate.a(i2);
        }

    }

    private void a(File file1, File file2, WorldChunkManager worldchunkmanager, int i0, int i1, IProgressUpdate iprogressupdate) {
        try {
            String s0 = file2.getName();
            RegionFile regionfile = new RegionFile(file2);
            RegionFile regionfile1 = new RegionFile(new File(file1, s0.substring(0, s0.length() - ".mcr".length()) + ".mca"));

            for (int i2 = 0; i2 < 32; ++i2) {
                int i3;

                for (i3 = 0; i3 < 32; ++i3) {
                    if (regionfile.c(i2, i3) && !regionfile1.c(i2, i3)) {
                        DataInputStream datainputstream = regionfile.a(i2, i3);

                        if (datainputstream == null) {
                            b.warn("Failed to fetch input stream");
                        }
                        else {
                            NBTTagCompound nbttagcompound = CompressedStreamTools.a((DataInput) datainputstream);

                            datainputstream.close();
                            NBTTagCompound nbttagcompound1 = nbttagcompound.m("Level");
                            ChunkLoader.AnvilConverterData chunkloader_anvilconverterdata = ChunkLoader.a(nbttagcompound1);
                            NBTTagCompound nbttagcompound2 = new NBTTagCompound();
                            NBTTagCompound nbttagcompound3 = new NBTTagCompound();

                            nbttagcompound2.a("Level", (NBTBase) nbttagcompound3);
                            ChunkLoader.a(chunkloader_anvilconverterdata, nbttagcompound3, worldchunkmanager);
                            DataOutputStream dataoutputstream = regionfile1.b(i2, i3);

                            CompressedStreamTools.a(nbttagcompound2, (DataOutput) dataoutputstream);
                            dataoutputstream.close();
                        }
                    }
                }

                i3 = (int) Math.round(100.0D * (double) (i0 * 1024) / (double) (i1 * 1024));
                int i4 = (int) Math.round(100.0D * (double) ((i2 + 1) * 32 + i0 * 1024) / (double) (i1 * 1024));

                if (i4 > i3) {
                    iprogressupdate.a(i4);
                }
            }

            regionfile.c();
            regionfile1.c();
        }
        catch (IOException ioexception) {
            ioexception.printStackTrace();
        }

    }

    private void a(File file3, Collection s0) {
        File file2 = new File(file3, "region");
        File[] afile = file2.listFiles(new FilenameFilter() {

            public boolean accept(File file3, String s0) {
                return s0.endsWith(".mcr");
            }
        });

        if (afile != null) {
            Collections.addAll(s0, afile);
        }

    }

}
