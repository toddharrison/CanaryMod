package net.minecraft.scoreboard;

import net.canarymod.api.scoreboard.CanaryScoreDummyCriteria;

import java.util.List;

public class ScoreDummyCriteria implements IScoreObjectiveCriteria {

    private final String j;
    protected net.canarymod.api.scoreboard.ScoreObjectiveCriteria criteria;

    public ScoreDummyCriteria(String s0) {
        this.j = s0;
        // CanaryMod: lets not add doubles
        if (!IScoreObjectiveCriteria.a.containsKey(s0)) {
            IScoreObjectiveCriteria.a.put(s0, this);
        }//
        // CanaryMod: Set Variable
        if (!(this instanceof ScoreHealthCriteria)) {
            criteria = new CanaryScoreDummyCriteria(this);
        }//
    }

    public String a() {
        return this.j;
    }

    public int a(List list) {
        return 0;
    }

    public boolean b() {
        return false;
    }

    public IScoreObjectiveCriteria.EnumRenderType c() {
        return IScoreObjectiveCriteria.EnumRenderType.INTEGER;
    }

    public net.canarymod.api.scoreboard.ScoreObjectiveCriteria getCanaryScoreObjectiveCriteria() {
        return this.criteria;
    }
}
