package net.minecraft.scoreboard;

import com.google.common.collect.Sets;
import net.canarymod.api.scoreboard.CanaryTeam;
import net.minecraft.util.EnumChatFormatting;

import java.util.Collection;
import java.util.Set;

public class ScorePlayerTeam extends Team {

    public final Scoreboard a; // CanaryMod: private to public
    private final String b;
    private final Set c = Sets.newHashSet();
    private String d;
    private String e = "";
    private String f = "";
    private boolean g = true;
    private boolean h = true;
    private Team.EnumVisible i;
    private Team.EnumVisible j;
    private EnumChatFormatting k;

    private CanaryTeam team = new CanaryTeam(this); // CanaryMod: initialize our variable

    public ScorePlayerTeam(Scoreboard scoreboard, String s0) {
        this.i = Team.EnumVisible.ALWAYS;
        this.j = Team.EnumVisible.ALWAYS;
        this.k = EnumChatFormatting.RESET;
        this.a = scoreboard;
        this.b = s0;
        this.d = s0;
    }

    public String b() {
        return this.b;
    }

    public String c() {
        return this.d;
    }

    public void a(String s0) {
        if (s0 == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        else {
            this.d = s0;
            this.a.b(this);
        }
    }

    public Collection d() {
        return this.c;
    }

    public String e() {
        return this.e;
    }

    public void b(String s0) {
        if (s0 == null) {
            throw new IllegalArgumentException("Prefix cannot be null");
        }
        else {
            this.e = s0;
            this.a.b(this);
        }
    }

    public String f() {
        return this.f;
    }

    public void c(String s0) {
        this.f = s0;
        this.a.b(this);
    }

    public String d(String s0) {
        return this.e() + s0 + this.f();
    }

    public static String a(Team team, String s0) {
        return team == null ? s0 : team.d(s0);
    }

    public boolean g() {
        return this.g;
    }

    public void a(boolean flag0) {
        this.g = flag0;
        this.a.b(this);
    }

    public boolean h() {
        return this.h;
    }

    public void b(boolean flag0) {
        this.h = flag0;
        this.a.b(this);
    }

    public Team.EnumVisible i() {
        return this.i;
    }

    public Team.EnumVisible j() {
        return this.j;
    }

    public void a(Team.EnumVisible team_enumvisible) {
        this.i = team_enumvisible;
        this.a.b(this);
    }

    public void b(Team.EnumVisible team_enumvisible) {
        this.j = team_enumvisible;
        this.a.b(this);
    }

    public int k() {
        int i0 = 0;

        if (this.g()) {
            i0 |= 1;
        }

        if (this.h()) {
            i0 |= 2;
        }

        return i0;
    }

    public void a(EnumChatFormatting enumchatformatting) {
        this.k = enumchatformatting;
    }

    public EnumChatFormatting l() {
        return this.k;
    }

    // CanaryMod: getter
    public CanaryTeam getCanaryTeam() {
        return this.team;
    }
}
