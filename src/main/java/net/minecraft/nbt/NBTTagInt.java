package net.minecraft.nbt;


import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;


public class NBTTagInt extends NBTBase.NBTPrimitive {

    public int b; // CanaryMod: private => public

    NBTTagInt() {
    }

    public NBTTagInt(int i0) {
        this.b = i0;
    }

    void a(DataOutput dataoutput) throws IOException {
        dataoutput.writeInt(this.b);
    }

    void a(DataInput datainput, int i0) throws IOException {
        this.b = datainput.readInt();
    }

    public byte a() {
        return (byte) 3;
    }

    public String toString() {
        return "" + this.b;
    }

    public NBTBase b() {
        return new NBTTagInt(this.b);
    }

    public boolean equals(Object object) {
        if (super.equals(object)) {
            NBTTagInt nbttagint = (NBTTagInt) object;

            return this.b == nbttagint.b;
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
        return (short) (this.b & '\uffff');
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
