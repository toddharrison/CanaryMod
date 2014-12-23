package net.minecraft.network.play.server;


import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.scoreboard.ScoreObjective;


public class S3DPacketDisplayScoreboard implements Packet {

    private int a;
    private String b;
   
    public S3DPacketDisplayScoreboard() {}

    public S3DPacketDisplayScoreboard(int i0, ScoreObjective scoreobjective) {
        this.a = i0;
        if (scoreobjective == null) {
            this.b = "";
        } else {
            this.b = scoreobjective.b();
            this.b = b.substring(0, Math.min(16, b.length())); // CanaryMod: make sure its not too long
        }

    }

    public void a(PacketBuffer packetbuffer) throws IOException {
        this.a = packetbuffer.readByte();
        this.b = packetbuffer.c(16);
    }

    public void b(PacketBuffer packetbuffer) throws IOException {
        packetbuffer.writeByte(this.a);
        packetbuffer.a(this.b);
    }

    public void a(INetHandlerPlayClient inethandlerplayclient) {
        inethandlerplayclient.a(this);
    }

    public void a(INetHandler inethandler) {
        this.a((INetHandlerPlayClient) inethandler);
    }
}
