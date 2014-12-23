package net.minecraft.network.play.server;


import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.ScoreObjective;


public class S3BPacketScoreboardObjective implements Packet {

    private String a;
    private String b;
    private IScoreObjectiveCriteria.EnumRenderType c;
    private int d;
   
    public S3BPacketScoreboardObjective() {}

    public S3BPacketScoreboardObjective(ScoreObjective scoreobjective, int i0) {
        this.a = scoreobjective.b();
        this.a = a.substring(0, Math.min(16, a.length())); // CanaryMod: make sure its not too long
        this.b = scoreobjective.d();
        this.c = scoreobjective.c().c();
        this.d = i0;
    }

    public void a(PacketBuffer packetbuffer) throws IOException {
        this.a = packetbuffer.c(16);
        this.d = packetbuffer.readByte();
        if (this.d == 0 || this.d == 2) {
            this.b = packetbuffer.c(32);
            this.c = IScoreObjectiveCriteria.EnumRenderType.a(packetbuffer.c(16));
        }

    }

    public void b(PacketBuffer packetbuffer) throws IOException {
        packetbuffer.a(this.a);
        packetbuffer.writeByte(this.d);
        if (this.d == 0 || this.d == 2) {
            packetbuffer.a(this.b);
            packetbuffer.a(this.c.a());
        }

    }

    public void a(INetHandlerPlayClient inethandlerplayclient) {
        inethandlerplayclient.a(this);
    }

    public void a(INetHandler inethandler) {
        this.a((INetHandlerPlayClient) inethandler);
    }
}
