package net.minecraft.network.handshake.client;

import java.io.IOException;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.handshake.INetHandlerHandshakeServer;

public class C00Handshake extends Packet
{
    private int a;
    public String b; //CanaryMod - Bungeecord support
    private int c;
    private EnumConnectionState d;

    public void a(PacketBuffer packetbuffer)
            throws IOException
    {
        this.a = packetbuffer.a();
        this.b = packetbuffer.c(Short.MAX_VALUE); //CanaryMod - Bungeecord support
        this.c = packetbuffer.readUnsignedShort();
        this.d = EnumConnectionState.a(packetbuffer.a());
    }

    public void b(PacketBuffer packetbuffer) throws IOException {
        packetbuffer.b(this.a);
        packetbuffer.a(this.b);
        packetbuffer.writeShort(this.c);
        packetbuffer.b(this.d.c());
    }

    public void a(INetHandlerHandshakeServer inethandlerhandshakeserver) {
        inethandlerhandshakeserver.a(this);
    }

    public boolean a() {
        return true;
    }

    public EnumConnectionState c() {
        return this.d;
    }

    public int d() {
        return this.a;
    }

    public void a(INetHandler inethandler) {
        a((INetHandlerHandshakeServer)inethandler);
    }
}