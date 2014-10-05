package net.minecraft.scoreboard;

import net.canarymod.api.scoreboard.CanaryScoreObjective;

public class ScoreObjective {

    private final Scoreboard a;
    private final String b;
    private final IScoreObjectiveCriteria c;
    private IScoreObjectiveCriteria.EnumRenderType d;
    private String e;

    // CanaryMod: our variables
    private final CanaryScoreObjective scoreObjective = new CanaryScoreObjective(this);

    public ScoreObjective(Scoreboard scoreboard, String s0, IScoreObjectiveCriteria iscoreobjectivecriteria) {
        this.a = scoreboard;
        this.b = s0;
        this.c = iscoreobjectivecriteria;
        this.e = s0;
        this.d = iscoreobjectivecriteria.c();
    }

    public String b() {
        return this.b;
    }

    public IScoreObjectiveCriteria c() {
        return this.c;
    }

    public String d() {
        return this.e;
    }

    public void a(String s0) {
        this.e = s0;
        this.a.b(this);
    }

    public IScoreObjectiveCriteria.EnumRenderType e() {
        return this.d;
    }

    public void a(IScoreObjectiveCriteria.EnumRenderType iscoreobjectivecriteria_enumrendertype) {
        this.d = iscoreobjectivecriteria_enumrendertype;
        this.a.b(this);
    }

    // CanaryMod: getter
    public CanaryScoreObjective getCanaryScoreObjective() {
        return this.scoreObjective;
    }
}
