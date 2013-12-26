package net.minecraft.nbt;


import net.minecraft.util.MathHelper;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;


public class NBTTagDouble extends NBTBase.NBTPrimitive {

    public double b; // CanaryMod: private => public

    NBTTagDouble() {
    }

    public NBTTagDouble(double d0) {
        this.b = d0;
    }

    void a(DataOutput dataoutput) throws IOException {
        dataoutput.writeDouble(this.b);
    }

    void a(DataInput datainput, int i0) throws IOException {
        this.b = datainput.readDouble();
    }

    public byte a() {
        return (byte) 6;
    }

    public String toString() {
        return "" + this.b + "d";
    }

    public NBTBase b() {
        return new NBTTagDouble(this.b);
    }

    public boolean equals(Object object) {
        if (super.equals(object)) {
            NBTTagDouble nbttagdouble = (NBTTagDouble) object;

            return this.b == nbttagdouble.b;
        }
        else {
            return false;
        }
    }

    public int hashCode() {
        long i0 = Double.doubleToLongBits(this.b);

        return super.hashCode() ^ (int) (i0 ^ i0 >>> 32);
    }

    public long c() {
        return (long) Math.floor(this.b);
    }

    public int d() {
        return MathHelper.c(this.b);
    }

    public short e() {
        return (short) (MathHelper.c(this.b) & '\uffff');
    }

    public byte f() {
        return (byte) (MathHelper.c(this.b) & 255);
    }

    public double g() {
        return this.b;
    }

    public float h() {
        return (float) this.b;
    }
}
