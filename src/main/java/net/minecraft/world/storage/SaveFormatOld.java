package net.minecraft.world.storage;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IProgressUpdate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class SaveFormatOld implements ISaveFormat {

    private static final Logger b = LogManager.getLogger();
    protected final File a;
    protected net.canarymod.api.world.DimensionType type;

    // CanaryMod changed constructor

    public SaveFormatOld(File file1, net.canarymod.api.world.DimensionType type) {
        if (!file1.exists()) {
            file1.mkdirs();
        }
        this.type = type;
        this.a = file1;
    }

    @Override
    public void d() {
    }

    public WorldInfo c(String s0) {
        File file1 = new File(this.a, s0);

        if (!file1.exists()) {
            return null;
        }
        else {
            File file2 = new File(file1, "level.dat");
            NBTTagCompound nbttagcompound;
            NBTTagCompound nbttagcompound1;

            if (file2.exists()) {
                try {
                    nbttagcompound = CompressedStreamTools.a((InputStream) (new FileInputStream(file2)));
                    nbttagcompound1 = nbttagcompound.m("Data");
                    return new WorldInfo(nbttagcompound1);
                }
                catch (Exception exception) {
                    b.error("Exception reading " + file2, exception);
                }
            }

            file2 = new File(file1, "level.dat_old");
            if (file2.exists()) {
                try {
                    nbttagcompound = CompressedStreamTools.a((InputStream) (new FileInputStream(file2)));
                    nbttagcompound1 = nbttagcompound.m("Data");
                    return new WorldInfo(nbttagcompound1);
                }
                catch (Exception exception1) {
                    b.error("Exception reading " + file2, exception1);
                }
            }

            return null;
        }
    }

    @Override
    public boolean e(String s0) {
        File file1 = new File(this.a, s0);

        if (!file1.exists()) {
            return true;
        }
        else {
            b.info("Deleting level " + s0);

            for (int i0 = 1; i0 <= 5; ++i0) {
                b.info("Attempt " + i0 + "...");
                if (a(file1.listFiles())) {
                    break;
                }

                b.warn("Unsuccessful in deleting contents.");
                if (i0 < 5) {
                    try {
                        Thread.sleep(500L);
                    }
                    catch (InterruptedException interruptedexception) {
                        ;
                    }
                }
            }

            return file1.delete();
        }
    }

    protected static boolean a(File[] afile) {
        for (int i0 = 0; i0 < afile.length; ++i0) {
            File file1 = afile[i0];

            b.debug("Deleting " + file1);
            if (file1.isDirectory() && !a(file1.listFiles())) {
                b.warn("Couldn\'t delete directory " + file1);
                return false;
            }

            if (!file1.delete()) {
                b.warn("Couldn\'t delete file " + file1);
                return false;
            }
        }

        return true;
    }

    @Override
    public ISaveHandler a(String s0, boolean flag0) {
        return new SaveHandler(this.a, s0, flag0, type);
    }

    @Override
    public boolean b(String s0) {
        return false;
    }

    @Override
    public boolean a(String s0, IProgressUpdate iprogressupdate) {
        return false;
    }
}
