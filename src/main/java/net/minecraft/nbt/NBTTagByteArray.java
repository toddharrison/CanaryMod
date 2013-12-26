package net.minecraft.nbt;


import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;


public class NBTTagByteArray extends NBTBase {

    public byte[] b; // CanaryMod: private => public

    NBTTagByteArray() {
    }

    public NBTTagByteArray(byte[] abyte) {
        this.b = abyte;
    }

    void a(DataOutput dataoutput) throws IOException {
        dataoutput.writeInt(this.b.length);
        dataoutput.write(this.b);
    }

    void a(DataInput datainput, int i0) throws IOException {
        int i1 = datainput.readInt();

        this.b = new byte[i1];
        datainput.readFully(this.b);
    }

    public byte a() {
        return (byte) 7;
    }

    public String toString() {
        return "[" + this.b.length + " bytes]";
    }

    public NBTBase b() {
        byte[] abyte = new byte[this.b.length];

        System.arraycopy(this.b, 0, abyte, 0, this.b.length);
        return new NBTTagByteArray(abyte);
    }

    public boolean equals(Object object) {
        return super.equals(object) ? Arrays.equals(this.b, ((NBTTagByteArray) object).b) : false;
    }

    public int hashCode() {
        return super.hashCode() ^ Arrays.hashCode(this.b);
    }

    public byte[] c() {
        return this.b;
    }
}
