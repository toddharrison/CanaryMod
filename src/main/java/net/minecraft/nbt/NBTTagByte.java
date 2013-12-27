package net.minecraft.nbt;


import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;


public class NBTTagByte extends NBTBase.NBTPrimitive {

    public byte b; // CanaryMod: private => public

    NBTTagByte() {
    }

    public NBTTagByte(byte b0) {
        this.b = b0;
    }

    void a(DataOutput dataoutput) throws IOException {
        dataoutput.writeByte(this.b);
    }

    void a(DataInput datainput, int i0) throws IOException {
        this.b = datainput.readByte();
    }

    public byte a() {
        return (byte) 1;
    }

    public String toString() {
        return "" + this.b + "b";
    }

    public NBTBase b() {
        return new NBTTagByte(this.b);
    }

    public boolean equals(Object object) {
        if (super.equals(object)) {
            NBTTagByte nbttagbyte = (NBTTagByte) object;

            return this.b == nbttagbyte.b;
        }
        else {
            return false;
        }
    }

    public int hashCode() {
        return super.hashCode() ^ this.b;
    }

    public long c() {
        return (long) this.b;
    }

    public int d() {
        return this.b;
    }

    public short e() {
        return (short) this.b;
    }

    public byte f() {
        return this.b;
    }

    public double g() {
        return (double) this.b;
    }

    public float h() {
        return (float) this.b;
    }
}
