package net.canarymod.api.scoreboard;

/**
 * @author Somners
 */
public class CanaryScoreObjective implements ScoreObjective {

    private final net.minecraft.scoreboard.ScoreObjective handle;

    public CanaryScoreObjective(net.minecraft.scoreboard.ScoreObjective handle) {
        this.handle = handle;
    }

    @Override
    public String getProtocolName() {
        return handle.b();
    }

    @Override
    public ScoreObjectiveCriteria getScoreObjectiveCriteria() {
        return ((net.minecraft.scoreboard.ScoreDummyCriteria) handle.c()).getCanaryScoreObjectiveCriteria();
    }

    @Override
    public String getDisplayName() {
        return handle.d();
    }

    @Override
    public void setDisplayName(String name) {
        handle.a(name);
    }

    public net.minecraft.scoreboard.ScoreObjective getHandle() {
        return handle;
    }
}
