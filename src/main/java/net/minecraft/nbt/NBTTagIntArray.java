package net.minecraft.nbt;


import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;


public class NBTTagIntArray extends NBTBase {

    public int[] b; // CanaryMod: private => public

    NBTTagIntArray() {
    }

    public NBTTagIntArray(int[] aint) {
        this.b = aint;
    }

    void a(DataOutput dataoutput) throws IOException {
        dataoutput.writeInt(this.b.length);

        for (int i0 = 0; i0 < this.b.length; ++i0) {
            dataoutput.writeInt(this.b[i0]);
        }

    }

    void a(DataInput datainput, int i0) throws IOException {
        int i1 = datainput.readInt();

        this.b = new int[i1];

        for (int i2 = 0; i2 < i1; ++i2) {
            this.b[i2] = datainput.readInt();
        }

    }

    public byte a() {
        return (byte) 11;
    }

    public String toString() {
        String s0 = "[";
        int[] aint = this.b;
        int i0 = aint.length;

        for (int i1 = 0; i1 < i0; ++i1) {
            int i2 = aint[i1];

            s0 = s0 + i2 + ",";
        }

        return s0 + "]";
    }

    public NBTBase b() {
        int[] aint = new int[this.b.length];

        System.arraycopy(this.b, 0, aint, 0, this.b.length);
        return new NBTTagIntArray(aint);
    }

    public boolean equals(Object object) {
        return super.equals(object) ? Arrays.equals(this.b, ((NBTTagIntArray) object).b) : false;
    }

    public int hashCode() {
        return super.hashCode() ^ Arrays.hashCode(this.b);
    }

    public int[] c() {
        return this.b;
    }
}
