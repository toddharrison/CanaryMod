package net.minecraft.nbt;


import net.minecraft.util.MathHelper;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;


public class NBTTagFloat extends NBTBase.NBTPrimitive {

    public float b; // CanaryMod: private => public

    NBTTagFloat() {
    }

    public NBTTagFloat(float f0) {
        this.b = f0;
    }

    void a(DataOutput dataoutput) throws IOException {
        dataoutput.writeFloat(this.b);
    }

    void a(DataInput datainput, int i0, NBTSizeTracker nbtsizetracker) throws IOException {
        nbtsizetracker.a(32L);
        this.b = datainput.readFloat();
    }

    public byte a() {
        return (byte) 5;
    }

    public String toString() {
        return "" + this.b + "f";
    }

    public NBTBase b() {
        return new NBTTagFloat(this.b);
    }

    public boolean equals(Object object) {
        if (super.equals(object)) {
            NBTTagFloat nbttagfloat = (NBTTagFloat) object;

            return this.b == nbttagfloat.b;
        }
        else {
            return false;
        }
    }

    public int hashCode() {
        return super.hashCode() ^ Float.floatToIntBits(this.b);
    }

    public long c() {
        return (long) this.b;
    }

    public int d() {
        return MathHelper.d(this.b);
    }

    public short e() {
        return (short) (MathHelper.d(this.b) & '\uffff');
    }

    public byte f() {
        return (byte) (MathHelper.d(this.b) & 255);
    }

    public double g() {
        return (double) this.b;
    }

    public float h() {
        return this.b;
    }
}
