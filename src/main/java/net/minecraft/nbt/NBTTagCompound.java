package net.minecraft.nbt;

import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ReportedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;


public class NBTTagCompound extends NBTBase {

    private static final Logger b = LogManager.getLogger();
    private Map c = new HashMap();

    void a(DataOutput dataoutput) throws IOException {
        Iterator iterator = this.c.keySet().iterator();

        while (iterator.hasNext()) {
            String s0 = (String) iterator.next();
            NBTBase nbtbase = (NBTBase) this.c.get(s0);

            a(s0, nbtbase, dataoutput);
        }

        dataoutput.writeByte(0);
    }

    void a(DataInput datainput, int i0, NBTSizeTracker nbtsizetracker) throws IOException {
        if (i0 > 512) {
            throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
        } else {
            this.c.clear();

            byte b0;

            while ((b0 = a(datainput, nbtsizetracker)) != 0) {
                String s0 = b(datainput, nbtsizetracker);

                nbtsizetracker.a((long) (16 * s0.length()));
                NBTBase nbtbase = a(b0, s0, datainput, i0 + 1, nbtsizetracker);

                this.c.put(s0, nbtbase);
            }

        }
    }

    public Set c() {
        return this.c.keySet();
    }

    public byte a() {
        return (byte) 10;
    }

    public void a(String s0, NBTBase nbtbase) {
        this.c.put(s0, nbtbase);
    }

    public void a(String s0, byte b0) {
        this.c.put(s0, new NBTTagByte(b0));
    }

    public void a(String s0, short short1) {
        this.c.put(s0, new NBTTagShort(short1));
    }

    public void a(String s0, int i0) {
        this.c.put(s0, new NBTTagInt(i0));
    }

    public void a(String s0, long i0) {
        this.c.put(s0, new NBTTagLong(i0));
    }

    public void a(String s0, float f0) {
        this.c.put(s0, new NBTTagFloat(f0));
    }

    public void a(String s0, double d0) {
        this.c.put(s0, new NBTTagDouble(d0));
    }

    public void a(String s0, String s1) {
        this.c.put(s0, new NBTTagString(s1));
    }

    public void a(String s0, byte[] abyte) {
        this.c.put(s0, new NBTTagByteArray(abyte));
    }

    public void a(String s0, int[] aint) {
        this.c.put(s0, new NBTTagIntArray(aint));
    }

    public void a(String s0, boolean flag0) {
        this.a(s0, (byte) (flag0 ? 1 : 0));
    }

    public NBTBase a(String s0) {
        return (NBTBase) this.c.get(s0);
    }

    public byte b(String s0) {
        NBTBase nbtbase = (NBTBase) this.c.get(s0);

        return nbtbase != null ? nbtbase.a() : 0;
    }

    public boolean c(String s0) {
        return this.c.containsKey(s0);
    }

    public boolean b(String s0, int i0) {
        byte b0 = this.b(s0);

        return b0 == i0 ? true : (i0 != 99 ? false : b0 == 1 || b0 == 2 || b0 == 3 || b0 == 4 || b0 == 5 || b0 == 6);
    }

    public byte d(String s0) {
        try {
            return !this.c.containsKey(s0) ? 0 : ((NBTBase.NBTPrimitive) this.c.get(s0)).f();
        } catch (ClassCastException classcastexception) {
            return (byte) 0;
        }
    }

    public short e(String s0) {
        try {
            return !this.c.containsKey(s0) ? 0 : ((NBTBase.NBTPrimitive) this.c.get(s0)).e();
        } catch (ClassCastException classcastexception) {
            return (short) 0;
        }
    }

    public int f(String s0) {
        try {
            return !this.c.containsKey(s0) ? 0 : ((NBTBase.NBTPrimitive) this.c.get(s0)).d();
        } catch (ClassCastException classcastexception) {
            return 0;
        }
    }

    public long g(String s0) {
        try {
            return !this.c.containsKey(s0) ? 0L : ((NBTBase.NBTPrimitive) this.c.get(s0)).c();
        } catch (ClassCastException classcastexception) {
            return 0L;
        }
    }

    public float h(String s0) {
        try {
            return !this.c.containsKey(s0) ? 0.0F : ((NBTPrimitive) this.c.get(s0)).h();
        } catch (ClassCastException classcastexception) {
            return 0.0F;
        }
    }

    public double i(String s0) {
        try {
            return !this.c.containsKey(s0) ? 0.0D : ((NBTPrimitive) this.c.get(s0)).g();
        } catch (ClassCastException classcastexception) {
            return 0.0D;
        }
    }

    public String j(String s0) {
        try {
            return !this.c.containsKey(s0) ? "" : ((NBTBase) this.c.get(s0)).a_();
        } catch (ClassCastException classcastexception) {
            return "";
        }
    }

    public byte[] k(String s0) {
        try {
            return !this.c.containsKey(s0) ? new byte[0] : ((NBTTagByteArray) this.c.get(s0)).c();
        } catch (ClassCastException classcastexception) {
            throw new ReportedException(this.a(s0, 7, classcastexception));
        }
    }

    public int[] l(String s0) {
        try {
            return !this.c.containsKey(s0) ? new int[0] : ((NBTTagIntArray) this.c.get(s0)).c();
        } catch (ClassCastException classcastexception) {
            throw new ReportedException(this.a(s0, 11, classcastexception));
        }
    }

    public NBTTagCompound m(String s0) {
        try {
            return !this.c.containsKey(s0) ? new NBTTagCompound() : (NBTTagCompound) this.c.get(s0);
        } catch (ClassCastException classcastexception) {
            throw new ReportedException(this.a(s0, 10, classcastexception));
        }
    }

    public NBTTagList c(String s0, int i0) {
        try {
            if (this.b(s0) != 9) {
                return new NBTTagList();
            } else {
                NBTTagList nbttaglist = (NBTTagList) this.c.get(s0);

                return nbttaglist.c() > 0 && nbttaglist.d() != i0 ? new NBTTagList() : nbttaglist;
            }
        } catch (ClassCastException classcastexception) {
            throw new ReportedException(this.a(s0, 9, classcastexception));
        }
    }

    // CanaryMod: Unchecked List grabbery
    public NBTTagList getListUnsafe(String s0) {
        try {
            NBTTagList nbttaglist = (NBTTagList) this.c.get(s0);

            return nbttaglist;
        } catch (ClassCastException classcastexception) {
            throw new ReportedException(this.a(s0, 9, classcastexception));
        }
    }

    public boolean n(String s0) {
        return this.d(s0) != 0;
    }

    public void o(String s0) {
        this.c.remove(s0);
    }

    public String toString() {
        String s0 = "{";

        String s1;

        for (Iterator iterator = this.c.keySet().iterator(); iterator.hasNext(); s0 = s0 + s1 + ':' + this.c.get(s1) + ',') {
            s1 = (String) iterator.next();
        }

        return s0 + "}";
    }

    public boolean d() {
        return this.c.isEmpty();
    }

    private CrashReport a(final String s0, final int i0, ClassCastException classcastexception) {
        CrashReport crashreport = CrashReport.a(classcastexception, "Reading NBT data");
        CrashReportCategory crashreportcategory = crashreport.a("Corrupt NBT tag", 1);

        crashreportcategory.a("Tag type found", new Callable() {

            public String call() {
                return NBTBase.a[((NBTBase) NBTTagCompound.this.c.get(s0)).a()];
            }
        });
        crashreportcategory.a("Tag type expected", new Callable() {

            public String call() {
                return NBTBase.a[i0];
            }
        });
        crashreportcategory.a("Tag name", (Object) s0);
        return crashreport;
    }

    public NBTBase b() {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        Iterator iterator = this.c.keySet().iterator();

        while (iterator.hasNext()) {
            String s0 = (String) iterator.next();

            nbttagcompound.a(s0, ((NBTBase) this.c.get(s0)).b());
        }

        return nbttagcompound;
    }

    public boolean equals(Object object) {
        if (super.equals(object)) {
            NBTTagCompound nbttagcompound = (NBTTagCompound) object;

            return this.c.entrySet().equals(nbttagcompound.c.entrySet());
        } else {
            return false;
        }
    }

    public int hashCode() {
        return super.hashCode() ^ this.c.hashCode();
    }

    private static void a(String s0, NBTBase nbtbase, DataOutput dataoutput) throws IOException {
        dataoutput.writeByte(nbtbase.a());
        if (nbtbase.a() != 0) {
            dataoutput.writeUTF(s0);
            nbtbase.a(dataoutput);
        }
    }

    private static byte a(DataInput datainput, NBTSizeTracker nbtsizetracker) throws IOException {
        return datainput.readByte();
    }

    private static String b(DataInput datainput, NBTSizeTracker nbtsizetracker) throws IOException {
        return datainput.readUTF();
    }

    static NBTBase a(byte b0, String s0, DataInput datainput, int i0, NBTSizeTracker nbtsizetracker) {
        NBTBase nbtbase = NBTBase.a(b0);

        try {
            nbtbase.a(datainput, i0, nbtsizetracker);
            return nbtbase;
        } catch (IOException ioexception) {
            CrashReport crashreport = CrashReport.a(ioexception, "Loading NBT data");
            CrashReportCategory crashreportcategory = crashreport.a("NBT Tag");

            crashreportcategory.a("Tag name", (Object) s0);
            crashreportcategory.a("Tag type", (Object) Byte.valueOf(b0));
            throw new ReportedException(crashreport);
        }
    }

}
