package net.minecraft.scoreboard;


import java.util.*;

import net.canarymod.api.scoreboard.CanaryScoreboard;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S3BPacketScoreboardObjective;
import net.minecraft.network.play.server.S3CPacketUpdateScore;
import net.minecraft.network.play.server.S3DPacketDisplayScoreboard;
import net.minecraft.network.play.server.S3EPacketTeams;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardSaveData;
import net.minecraft.server.MinecraftServer;


public class ServerScoreboard extends Scoreboard {

    private final MinecraftServer a;
    private final Set b = new HashSet();
    private ScoreboardSaveData c;

    public ServerScoreboard(MinecraftServer minecraftserver) {
        this.a = minecraftserver;
        scoreboard = new CanaryScoreboard(this, "scoreboard");
    }

    public void a(Score score) {
        super.a(score);
        // CanaryMod: Don't Check This
        //if (this.b.contains(score.d())) {
            this.a.ah().a((Packet) (new S3CPacketUpdateScore(score, 0)));
        //}
        // CanaryMod: End
        this.b();
    }

    public void a(String s0) {
        super.a(s0);
        this.a.ah().a((Packet) (new S3CPacketUpdateScore(s0)));
        this.b();
    }

    public void a(int i0, ScoreObjective scoreobjective) {
        ScoreObjective scoreobjective1 = this.a(i0);

        super.a(i0, scoreobjective);
        if (scoreobjective1 != scoreobjective && scoreobjective1 != null) {
            if (this.h(scoreobjective1) > 0) {
                this.a.ah().a((Packet) (new S3DPacketDisplayScoreboard(i0, scoreobjective)));
            } else {
                this.g(scoreobjective1);
            }
        }

        if (scoreobjective != null) {
            if (this.b.contains(scoreobjective)) {
                this.a.ah().a((Packet) (new S3DPacketDisplayScoreboard(i0, scoreobjective)));
            } else {
                this.e(scoreobjective);
            }
        }

        this.b();
    }

    public boolean a(String s0, String s1) {
        if (super.a(s0, s1)) {
            ScorePlayerTeam scoreplayerteam = this.e(s1);

            this.a.ah().a((Packet) (new S3EPacketTeams(scoreplayerteam, Arrays.asList(new String[] { s0}), 3)));
            this.b();
            return true;
        } else {
            return false;
        }
    }

    public void a(String s0, ScorePlayerTeam scoreplayerteam) {
        super.a(s0, scoreplayerteam);
        this.a.ah().a((Packet) (new S3EPacketTeams(scoreplayerteam, Arrays.asList(new String[] { s0}), 4)));
        this.b();
    }

    public void a(ScoreObjective scoreobjective) {
        super.a(scoreobjective);
        this.b();
    }

    public void b(ScoreObjective scoreobjective) {
        super.b(scoreobjective);
        if (this.b.contains(scoreobjective)) {
            this.a.ah().a((Packet) (new S3BPacketScoreboardObjective(scoreobjective, 2)));
        }

        this.b();
    }

    public void c(ScoreObjective scoreobjective) {
        super.c(scoreobjective);
        if (this.b.contains(scoreobjective)) {
            this.g(scoreobjective);
        }

        this.b();
    }

    public void a(ScorePlayerTeam scoreplayerteam) {
        super.a(scoreplayerteam);
        this.a.ah().a((Packet) (new S3EPacketTeams(scoreplayerteam, 0)));
        this.b();
    }

    public void b(ScorePlayerTeam scoreplayerteam) {
        super.b(scoreplayerteam);
        this.a.ah().a((Packet) (new S3EPacketTeams(scoreplayerteam, 2)));
        this.b();
    }

    public void c(ScorePlayerTeam scoreplayerteam) {
        super.c(scoreplayerteam);
        this.a.ah().a((Packet) (new S3EPacketTeams(scoreplayerteam, 1)));
        this.b();
    }

    public void a(ScoreboardSaveData scoreboardsavedata) {
        this.c = scoreboardsavedata;
    }

    protected void b() {
        if (this.c != null) {
            this.c.c();
        }

    }

    public List d(ScoreObjective scoreobjective) {
        ArrayList arraylist = new ArrayList();

        arraylist.add(new S3BPacketScoreboardObjective(scoreobjective, 0));

        for (int i0 = 0; i0 < 3; ++i0) {
            if (this.a(i0) == scoreobjective) {
                arraylist.add(new S3DPacketDisplayScoreboard(i0, scoreobjective));
            }
        }

        Iterator iterator = this.i(scoreobjective).iterator();

        while (iterator.hasNext()) {
            Score score = (Score) iterator.next();

            arraylist.add(new S3CPacketUpdateScore(score, 0));
        }

        return arraylist;
    }

    public void e(ScoreObjective scoreobjective) {
        List list = this.d(scoreobjective);
        Iterator iterator = this.a.ah().e.iterator();

        while (iterator.hasNext()) {
            EntityPlayerMP entityplayermp = (EntityPlayerMP) iterator.next();
            Iterator iterator1 = list.iterator();

            while (iterator1.hasNext()) {
                Packet packet = (Packet) iterator1.next();

                entityplayermp.a.a(packet);
            }
        }

        this.b.add(scoreobjective);
    }

    public List f(ScoreObjective scoreobjective) {
        ArrayList arraylist = new ArrayList();

        arraylist.add(new S3BPacketScoreboardObjective(scoreobjective, 1));

        for (int i0 = 0; i0 < 3; ++i0) {
            if (this.a(i0) == scoreobjective) {
                arraylist.add(new S3DPacketDisplayScoreboard(i0, scoreobjective));
            }
        }

        return arraylist;
    }

    public void g(ScoreObjective scoreobjective) {
        List list = this.f(scoreobjective);
        Iterator iterator = this.a.ah().e.iterator();

        while (iterator.hasNext()) {
            EntityPlayerMP entityplayermp = (EntityPlayerMP) iterator.next();
            Iterator iterator1 = list.iterator();

            while (iterator1.hasNext()) {
                Packet packet = (Packet) iterator1.next();

                entityplayermp.a.a(packet);
            }
        }

        this.b.remove(scoreobjective);
    }

    public int h(ScoreObjective scoreobjective) {
        int i0 = 0;

        for (int i1 = 0; i1 < 3; ++i1) {
            if (this.a(i1) == scoreobjective) {
                ++i0;
            }
        }

        return i0;
    }
}
