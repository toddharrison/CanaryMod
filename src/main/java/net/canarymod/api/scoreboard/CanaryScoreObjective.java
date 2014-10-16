package net.canarymod.api.scoreboard;

import net.canarymod.api.entity.living.humanoid.CanaryPlayer;
import net.canarymod.api.entity.living.humanoid.Player;
import net.canarymod.api.world.World;
import net.minecraft.network.play.server.S3DPacketDisplayScoreboard;

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
        return handle.b().replaceFirst(this.getScoreboard().getSaveName() + "_", "");
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

    @Override
    public void setScoreboardPosition(ScorePosition type) {
        this.getScoreboard().setScoreboardPosition(type, this);
    }

    @Override
    public void setScoreboardPosition(ScorePosition type, Player player) {
        this.getScoreboard().setScoreboardPosition(type, this, player);
    }

    @Override
    public void setScoreboardPosition(ScorePosition type, World world) {
        this.getScoreboard().setScoreboardPosition(type, this, world);
    }

    @Override
    public Scoreboard getScoreboard() {
        return this.getHandle().getScoreboard().getCanaryScoreboard();
    }

    public net.minecraft.scoreboard.ScoreObjective getHandle() {
        return handle;
    }
}
