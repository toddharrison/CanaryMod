package net.minecraft.nbt;


import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;


public class NBTTagList extends NBTBase {

    private static final Logger b = LogManager.getLogger();
    public List c = Lists.newArrayList(); // CanaryMod: private => public
    private byte d = 0;

    void a(DataOutput dataoutput) throws IOException {
        if (!this.c.isEmpty()) {
            this.d = ((NBTBase) this.c.get(0)).a();
        }
        else {
            this.d = 0;
        }

        dataoutput.writeByte(this.d);
        dataoutput.writeInt(this.c.size());

        for (int i0 = 0; i0 < this.c.size(); ++i0) {
            ((NBTBase) this.c.get(i0)).a(dataoutput);
        }

    }

    void a(DataInput datainput, int i0, NBTSizeTracker nbtsizetracker) throws IOException {
        if (i0 > 512) {
            throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
        }
        else {
            nbtsizetracker.a(8L);
            this.d = datainput.readByte();
            int i1 = datainput.readInt();

            this.c = Lists.newArrayList();

            for (int i2 = 0; i2 < i1; ++i2) {
                NBTBase nbtbase = NBTBase.a(this.d);

                nbtbase.a(datainput, i0 + 1, nbtsizetracker);
                this.c.add(nbtbase);
            }

        }
    }

    public byte a() {
        return (byte) 9;
    }

    public String toString() {
        String s0 = "[";
        int i0 = 0;

        for (Iterator iterator = this.c.iterator(); iterator.hasNext(); ++i0) {
            NBTBase nbtbase = (NBTBase) iterator.next();

            s0 = s0 + "" + i0 + ':' + nbtbase + ',';
        }

        return s0 + "]";
    }

    public void a(NBTBase nbtbase) {
        if (this.d == 0) {
            this.d = nbtbase.a();
        }
        else if (this.d != nbtbase.a()) {
            b.warn("Adding mismatching tag types to tag list");
            return;
        }

        this.c.add(nbtbase);
    }

    public void a(int i0, NBTBase nbtbase) {
        if (i0 >= 0 && i0 < this.c.size()) {
            if (this.d == 0) {
                this.d = nbtbase.a();
            }
            else if (this.d != nbtbase.a()) {
                b.warn("Adding mismatching tag types to tag list");
                return;
            }

            this.c.set(i0, nbtbase);
        }
        else {
            b.warn("index out of bounds to set tag in tag list");
        }
    }

    public NBTBase a(int i0) {
        return (NBTBase) this.c.remove(i0);
    }

    public boolean c_() {
        return this.c.isEmpty();
    }

    public NBTTagCompound b(int i0) {
        if (i0 >= 0 && i0 < this.c.size()) {
            NBTBase nbtbase = (NBTBase) this.c.get(i0);

            return nbtbase.a() == 10 ? (NBTTagCompound) nbtbase : new NBTTagCompound();
        }
        else {
            return new NBTTagCompound();
        }
    }

    public int[] c(int i0) {
        if (i0 >= 0 && i0 < this.c.size()) {
            NBTBase nbtbase = (NBTBase) this.c.get(i0);

            return nbtbase.a() == 11 ? ((NBTTagIntArray) nbtbase).c() : new int[0];
        }
        else {
            return new int[0];
        }
    }

    public double d(int i0) {
        if (i0 >= 0 && i0 < this.c.size()) {
            NBTBase nbtbase = (NBTBase) this.c.get(i0);

            return nbtbase.a() == 6 ? ((NBTTagDouble) nbtbase).g() : 0.0D;
        }
        else {
            return 0.0D;
        }
    }

    public float e(int i0) {
        if (i0 >= 0 && i0 < this.c.size()) {
            NBTBase nbtbase = (NBTBase) this.c.get(i0);

            return nbtbase.a() == 5 ? ((NBTTagFloat) nbtbase).h() : 0.0F;
        }
        else {
            return 0.0F;
        }
    }

    public String f(int i0) {
        if (i0 >= 0 && i0 < this.c.size()) {
            NBTBase nbtbase = (NBTBase) this.c.get(i0);

            return nbtbase.a() == 8 ? nbtbase.a_() : nbtbase.toString();
        }
        else {
            return "";
        }
    }

    public NBTBase g(int i0) {
        return (NBTBase) (i0 >= 0 && i0 < this.c.size() ? (NBTBase) this.c.get(i0) : new NBTTagEnd());
    }

    public int c() {
        return this.c.size();
    }

    public NBTBase b() {
        NBTTagList nbttaglist = new NBTTagList();

        nbttaglist.d = this.d;
        Iterator iterator = this.c.iterator();

        while (iterator.hasNext()) {
            NBTBase nbtbase = (NBTBase) iterator.next();
            NBTBase nbtbase1 = nbtbase.b();

            nbttaglist.c.add(nbtbase1);
        }

        return nbttaglist;
    }

    public boolean equals(Object object) {
        if (super.equals(object)) {
            NBTTagList nbttaglist = (NBTTagList) object;

            if (this.d == nbttaglist.d) {
                return this.c.equals(nbttaglist.c);
            }
        }

        return false;
    }

    public int hashCode() {
        return super.hashCode() ^ this.c.hashCode();
    }

    public int f() {
        return this.d;
    }
}
