package net.minecraft.network.play.server;


import net.canarymod.api.world.effects.Particle;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

import java.io.IOException;


public class S2APacketParticles extends Packet {

    private String a;
    private float b;
    private float c;
    private float d;
    private float e;
    private float f;
    private float g;
    private float h;
    private int i;

    public S2APacketParticles() {
    }

    // CanaryMod: our special constructor
    public S2APacketParticles(Particle particle) {
        this.a = particle.type.getMcName();
        this.b = (float) particle.x;
        this.c = (float) particle.y;
        this.d = (float) particle.z;
        this.e = (float) particle.velocityX;
        this.f = (float) particle.velocityY;
        this.g = (float) particle.velocityZ;
        this.h = particle.speed;
        this.i = particle.quantity;
    }//

    public S2APacketParticles(String s0, float f0, float f1, float f2, float f3, float f4, float f5, float f6, int i0) {
        this.a = s0;
        this.b = f0;
        this.c = f1;
        this.d = f2;
        this.e = f3;
        this.f = f4;
        this.g = f5;
        this.h = f6;
        this.i = i0;
    }

    public void a(PacketBuffer packetbuffer) throws IOException {
        this.a = packetbuffer.c(64);
        this.b = packetbuffer.readFloat();
        this.c = packetbuffer.readFloat();
        this.d = packetbuffer.readFloat();
        this.e = packetbuffer.readFloat();
        this.f = packetbuffer.readFloat();
        this.g = packetbuffer.readFloat();
        this.h = packetbuffer.readFloat();
        this.i = packetbuffer.readInt();
    }

    public void b(PacketBuffer packetbuffer) throws IOException {
        packetbuffer.a(this.a);
        packetbuffer.writeFloat(this.b);
        packetbuffer.writeFloat(this.c);
        packetbuffer.writeFloat(this.d);
        packetbuffer.writeFloat(this.e);
        packetbuffer.writeFloat(this.f);
        packetbuffer.writeFloat(this.g);
        packetbuffer.writeFloat(this.h);
        packetbuffer.writeInt(this.i);
    }

    public void a(INetHandlerPlayClient inethandlerplayclient) {
        inethandlerplayclient.a(this);
    }

    public void a(INetHandler inethandler) {
        this.a((INetHandlerPlayClient) inethandler);
    }
}
