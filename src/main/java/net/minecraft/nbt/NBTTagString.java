package net.minecraft.nbt;


import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;


public class NBTTagString extends NBTBase {

    public String b; // CanaryMod: private => public

    public NBTTagString() {
        this.b = "";
    }

    public NBTTagString(String s0) {
        this.b = s0;
        if (s0 == null) {
            throw new IllegalArgumentException("Empty string not allowed");
        }
    }

    void a(DataOutput dataoutput) throws IOException {
        dataoutput.writeUTF(this.b);
    }

    void a(DataInput datainput, int i0, NBTSizeTracker nbtsizetracker) throws IOException {
        this.b = datainput.readUTF();
        nbtsizetracker.a((long) (16 * this.b.length()));
    }

    public byte a() {
        return (byte) 8;
    }

    public String toString() {
        return "\"" + this.b + "\"";
    }

    public NBTBase b() {
        return new NBTTagString(this.b);
    }

    public boolean equals(Object object) {
        if (!super.equals(object)) {
            return false;
        }
        else {
            NBTTagString nbttagstring = (NBTTagString) object;

            return this.b == null && nbttagstring.b == null || this.b != null && this.b.equals(nbttagstring.b);
        }
    }

    public int hashCode() {
        return super.hashCode() ^ this.b.hashCode();
    }

    public String a_() {
        return this.b;
    }
}
