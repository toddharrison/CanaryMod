package net.minecraft.nbt;


import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;


public class NBTTagLong extends NBTBase.NBTPrimitive {

    public long b; // CanaryMod: private => public

    NBTTagLong() {
    }

    public NBTTagLong(long i0) {
        this.b = i0;
    }

    void a(DataOutput dataoutput) throws IOException {
        dataoutput.writeLong(this.b);
    }

    void a(DataInput datainput, int i0) throws IOException {
        this.b = datainput.readLong();
    }

    public byte a() {
        return (byte) 4;
    }

    public String toString() {
        return "" + this.b + "L";
    }

    public NBTBase b() {
        return new NBTTagLong(this.b);
    }

    public boolean equals(Object object) {
        if (super.equals(object)) {
            NBTTagLong nbttaglong = (NBTTagLong) object;

            return this.b == nbttaglong.b;
        }
        else {
            return false;
        }
    }

    public int hashCode() {
        return super.hashCode() ^ (int) (this.b ^ this.b >>> 32);
    }

    public long c() {
        return this.b;
    }

    public int d() {
        return (int) (this.b & -1L);
    }

    public short e() {
        return (short) ((int) (this.b & 65535L));
    }

    public byte f() {
        return (byte) ((int) (this.b & 255L));
    }

    public double g() {
        return (double) this.b;
    }

    public float h() {
        return (float) this.b;
    }
}
