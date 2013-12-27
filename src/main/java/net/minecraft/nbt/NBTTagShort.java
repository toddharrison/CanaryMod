package net.minecraft.nbt;


import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;


public class NBTTagShort extends NBTBase.NBTPrimitive {

    public short b; // CanaryMod: private => public

    public NBTTagShort() {
    }

    public NBTTagShort(short short1) {
        this.b = short1;
    }

    void a(DataOutput dataoutput) throws IOException {
        dataoutput.writeShort(this.b);
    }

    void a(DataInput datainput, int i0) throws IOException {
        this.b = datainput.readShort();
    }

    public byte a() {
        return (byte) 2;
    }

    public String toString() {
        return "" + this.b + "s";
    }

    public NBTBase b() {
        return new NBTTagShort(this.b);
    }

    public boolean equals(Object object) {
        if (super.equals(object)) {
            NBTTagShort nbttagshort = (NBTTagShort) object;

            return this.b == nbttagshort.b;
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
        return this.b;
    }

    public byte f() {
        return (byte) (this.b & 255);
    }

    public double g() {
        return (double) this.b;
    }

    public float h() {
        return (float) this.b;
    }
}
