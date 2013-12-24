package net.minecraft.network;

import com.google.common.collect.BiMap;
import io.netty.buffer.ByteBuf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public abstract class Packet {

    private static final Logger a = LogManager.getLogger();

    public static Packet a(BiMap bimap, int i0) {
        try {
            Class oclass0 = (Class) bimap.get(Integer.valueOf(i0));

            return oclass0 == null ? null : (Packet) oclass0.newInstance();
        }
        catch (Exception exception) {
            a.error("Couldn\'t create packet " + i0, exception);
            return null;
        }
    }

    public static void a(ByteBuf bytebuf, byte[] abyte) {
        bytebuf.writeShort(abyte.length);
        bytebuf.writeBytes(abyte);
    }

    public static byte[] a(ByteBuf bytebuf) throws IOException {
        short short1 = bytebuf.readShort();

        if (short1 < 0) {
            throw new IOException("Key was smaller than nothing!  Weird key!");
        }
        else {
            byte[] abyte = new byte[short1];

            bytebuf.readBytes(abyte);
            return abyte;
        }
    }

    public abstract void a(PacketBuffer var1) throws IOException;

    public abstract void b(PacketBuffer var1) throws IOException;

    public abstract void a(INetHandler inethandler);

    public boolean a() {
        return false;
    }

    public String toString() {
        return this.getClass().getSimpleName();
    }

    public String b() {
        return "";
    }
}
