package net.minecraft.network.play.server;


import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;


public class S3CPacketUpdateScore implements Packet {

    private String a = "";
    private String b = "";
    private int c;
    private S3CPacketUpdateScore.Action d;
   
    public S3CPacketUpdateScore() {}

    public S3CPacketUpdateScore(Score score) {
        this.a = score.e();
        this.b = score.d().b();
        this.b = b.substring(0, Math.min(16, b.length())); // CanaryMod: make sure its not too long
        this.c = score.c();
        this.d = S3CPacketUpdateScore.Action.CHANGE;
    }

    public S3CPacketUpdateScore(String s0) {
        this.a = s0;
        this.b = "";
        this.c = 0;
        this.d = S3CPacketUpdateScore.Action.REMOVE;
    }

    public S3CPacketUpdateScore(String s0, ScoreObjective scoreobjective) {
        this.a = s0;
        this.b = scoreobjective.b();
        this.b = b.substring(0, Math.min(16, b.length())); // CanaryMod: make sure its not too long
        this.c = 0;
        this.d = S3CPacketUpdateScore.Action.REMOVE;
    }

    public void a(PacketBuffer packetbuffer) throws IOException {
        this.a = packetbuffer.c(40);
        this.d = (S3CPacketUpdateScore.Action) packetbuffer.a(S3CPacketUpdateScore.Action.class);
        this.b = packetbuffer.c(16);
        if (this.d != S3CPacketUpdateScore.Action.REMOVE) {
            this.c = packetbuffer.e();
        }

    }

    public void b(PacketBuffer packetbuffer) throws IOException {
        packetbuffer.a(this.a);
        packetbuffer.a((Enum) this.d);
        packetbuffer.a(this.b);
        if (this.d != S3CPacketUpdateScore.Action.REMOVE) {
            packetbuffer.b(this.c);
        }

    }

    public void a(INetHandlerPlayClient inethandlerplayclient) {
        inethandlerplayclient.a(this);
    }

    public void a(INetHandler inethandler) {
        this.a((INetHandlerPlayClient) inethandler);
    }

    public static enum Action {

        CHANGE("CHANGE", 0), REMOVE("REMOVE", 1);

        private static final S3CPacketUpdateScore.Action[] $VALUES = new S3CPacketUpdateScore.Action[] { CHANGE, REMOVE};
      
        private Action(String p_i45957_1_, int p_i45957_2_) {}

    }
}
