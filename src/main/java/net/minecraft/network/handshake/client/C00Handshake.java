package net.minecraft.network.handshake.client;

import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.handshake.INetHandlerHandshakeServer;

import java.io.IOException;

public class C00Handshake implements Packet {
    private int a;
    public String b; //CanaryMod - Bungeecord support
    private int c;
    private EnumConnectionState d;

    public void a(PacketBuffer packetbuffer)
            throws IOException {
        this.a = packetbuffer.e();
        this.b = packetbuffer.c(Short.MAX_VALUE); //CanaryMod - Bungeecord support
        this.c = packetbuffer.readUnsignedShort();
        this.d = EnumConnectionState.a(packetbuffer.e());
    }

    public void b(PacketBuffer packetbuffer) throws IOException {
        packetbuffer.b(this.a);
        packetbuffer.a(this.b);
        packetbuffer.writeShort(this.c);
        packetbuffer.b(this.d.a());
    }

    public void a(INetHandlerHandshakeServer inethandlerhandshakeserver) {
        inethandlerhandshakeserver.a(this);
    }

    public EnumConnectionState a() {
        return this.d;
    }

    public int b() {
        return this.a;
    }

    public void a(INetHandler inethandler) {
        this.a((INetHandlerHandshakeServer)inethandler);
    }
}