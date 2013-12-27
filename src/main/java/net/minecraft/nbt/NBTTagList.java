package net.minecraft.nbt;


import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class NBTTagList extends NBTBase {

    public List b = new ArrayList(); // CanaryMod: private => public
    private byte c = 0;

    void a(DataOutput dataoutput) throws IOException {
        if (!this.b.isEmpty()) {
            this.c = ((NBTBase) this.b.get(0)).a();
        }
        else {
            this.c = 0;
        }

        dataoutput.writeByte(this.c);
        dataoutput.writeInt(this.b.size());

        for (int i0 = 0; i0 < this.b.size(); ++i0) {
            ((NBTBase) this.b.get(i0)).a(dataoutput);
        }

    }

    void a(DataInput datainput, int i0) throws IOException {
        if (i0 > 512) {
            throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
        }
        else {
            this.c = datainput.readByte();
            int i1 = datainput.readInt();

            this.b = new ArrayList();

            for (int i2 = 0; i2 < i1; ++i2) {
                NBTBase nbtbase = NBTBase.a(this.c);

                nbtbase.a(datainput, i0 + 1);
                this.b.add(nbtbase);
            }

        }
    }

    public byte a() {
        return (byte) 9;
    }

    public String toString() {
        String s0 = "[";
        int i0 = 0;

        for (Iterator iterator = this.b.iterator(); iterator.hasNext(); ++i0) {
            NBTBase nbtbase = (NBTBase) iterator.next();

            s0 = s0 + "" + i0 + ':' + nbtbase + ',';
        }

        return s0 + "]";
    }

    public void a(NBTBase nbtbase) {
        if (this.c == 0) {
            this.c = nbtbase.a();
        }
        else if (this.c != nbtbase.a()) {
            System.err.println("WARNING: Adding mismatching tag types to tag list");
            return;
        }

        this.b.add(nbtbase);
    }

    public NBTTagCompound b(int i0) {
        if (i0 >= 0 && i0 < this.b.size()) {
            NBTBase nbtbase = (NBTBase) this.b.get(i0);

            return nbtbase.a() == 10 ? (NBTTagCompound) nbtbase : new NBTTagCompound();
        }
        else {
            return new NBTTagCompound();
        }
    }

    public int[] c(int i0) {
        if (i0 >= 0 && i0 < this.b.size()) {
            NBTBase nbtbase = (NBTBase) this.b.get(i0);

            return nbtbase.a() == 11 ? ((NBTTagIntArray) nbtbase).c() : new int[0];
        }
        else {
            return new int[0];
        }
    }

    public double d(int i0) {
        if (i0 >= 0 && i0 < this.b.size()) {
            NBTBase nbtbase = (NBTBase) this.b.get(i0);

            return nbtbase.a() == 6 ? ((NBTTagDouble) nbtbase).g() : 0.0D;
        }
        else {
            return 0.0D;
        }
    }

    public float e(int i0) {
        if (i0 >= 0 && i0 < this.b.size()) {
            NBTBase nbtbase = (NBTBase) this.b.get(i0);

            return nbtbase.a() == 5 ? ((NBTTagFloat) nbtbase).h() : 0.0F;
        }
        else {
            return 0.0F;
        }
    }

    public String f(int i0) {
        if (i0 >= 0 && i0 < this.b.size()) {
            NBTBase nbtbase = (NBTBase) this.b.get(i0);

            return nbtbase.a() == 8 ? nbtbase.a_() : nbtbase.toString();
        }
        else {
            return "";
        }
    }

    public int c() {
        return this.b.size();
    }

    public NBTBase b() {
        NBTTagList nbttaglist = new NBTTagList();

        nbttaglist.c = this.c;
        Iterator iterator = this.b.iterator();

        while (iterator.hasNext()) {
            NBTBase nbtbase = (NBTBase) iterator.next();
            NBTBase nbtbase1 = nbtbase.b();

            nbttaglist.b.add(nbtbase1);
        }

        return nbttaglist;
    }

    public boolean equals(Object object) {
        if (super.equals(object)) {
            NBTTagList nbttaglist = (NBTTagList) object;

            if (this.c == nbttaglist.c) {
                return this.b.equals(nbttaglist.b);
            }
        }

        return false;
    }

    public int hashCode() {
        return super.hashCode() ^ this.b.hashCode();
    }

    public int d() {
        return this.c;
    }
}
