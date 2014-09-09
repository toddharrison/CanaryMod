package net.minecraft.network.play.server;


import net.minecraft.block.Block;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.World;

import java.io.IOException;


public class S23PacketBlockChange extends Packet {

    public int a; // CanaryMod: private => public; posX
    public int b; // CanaryMod: private => public; posY
    public int c; // CanaryMod: private => public; posZ
    public Block d; // CanaryMod: private => public; Block
    public int e; // CanaryMod: private => public; meta

    public S23PacketBlockChange() {
    }

    public S23PacketBlockChange(int i0, int i1, int i2, World world) {
        this.a = i0;
        this.b = i1;
        this.c = i2;
        this.d = world.a(i0, i1, i2);
        this.e = world.e(i0, i1, i2);
    }

    public void a(PacketBuffer packetbuffer) throws IOException {
        this.a = packetbuffer.readInt();
        this.b = packetbuffer.readUnsignedByte();
        this.c = packetbuffer.readInt();
        this.d = Block.e(packetbuffer.a());
        this.e = packetbuffer.readUnsignedByte();
    }

    public void b(PacketBuffer packetbuffer) throws IOException {
        packetbuffer.writeInt(this.a);
        packetbuffer.writeByte(this.b);
        packetbuffer.writeInt(this.c);
        packetbuffer.b(Block.b(this.d));
        packetbuffer.writeByte(this.e);
    }

    public void a(INetHandlerPlayClient inethandlerplayclient) {
        inethandlerplayclient.a(this);
    }

    public String b() {
        return String.format("type=%d, data=%d, x=%d, y=%d, z=%d", new Object[]{Integer.valueOf(Block.b(this.d)), Integer.valueOf(this.e), Integer.valueOf(this.a), Integer.valueOf(this.b), Integer.valueOf(this.c)});
    }

    public void a(INetHandler inethandler) {
        this.a((INetHandlerPlayClient) inethandler);
    }
}
