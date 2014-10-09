package net.canarymod.api.scoreboard;

import net.canarymod.Canary;
import net.canarymod.api.entity.living.humanoid.CanaryPlayer;
import net.canarymod.api.entity.living.humanoid.Player;
import net.minecraft.network.play.server.S3DPacketDisplayScoreboard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Somners
 */
public class CanaryScoreboard implements Scoreboard {

    private net.minecraft.scoreboard.Scoreboard handle;
    protected String saveName;

    public CanaryScoreboard(net.minecraft.scoreboard.Scoreboard handle, String saveName) {
        this.handle = handle;
        this.saveName = saveName;
    }

    @Override
    public List<ScoreObjective> getScoreObjectives() {
        List<ScoreObjective> toRet = new ArrayList<ScoreObjective>();
        for (Object o : handle.getAllScoreObjective()) {
            net.minecraft.scoreboard.ScoreObjective objective = (net.minecraft.scoreboard.ScoreObjective) o;
            toRet.add(objective.getCanaryScoreObjective());
        }
        return toRet;
    }

    @Override
    public ScoreObjective addScoreObjective(String name) {
        ScoreObjective so = getScoreObjective(name);
        if (so == null) {
            so = this.handle.a(name, net.minecraft.scoreboard.IScoreObjectiveCriteria.b).getCanaryScoreObjective();
        }
        return so;
    }

    @Override
    public ScoreObjective addScoreObjective(String name, ScoreObjectiveCriteria criteria) {
        ScoreObjective so = getScoreObjective(name);
        if (so == null) {
            so = this.handle.a(name, ((CanaryScoreDummyCriteria) criteria).getHandle()).getCanaryScoreObjective();
        }
        return so;
    }

    @Override
    public void removeScoreObjective(ScoreObjective objective) {
        this.handle.c(((CanaryScoreObjective) objective).getHandle());
    }

    @Override
    public ScoreObjective getScoreObjective(String name) {
        net.minecraft.scoreboard.ScoreObjective so = handle.getScoreObjective(name);
        if (so!= null) {
            return so.getCanaryScoreObjective();
        }
        return null;
    }

    @Override
    public void removeScoreObjective(String name) {
        net.minecraft.scoreboard.ScoreObjective obj = handle.getScoreObjective(name);
        if (obj != null) {
            handle.k(obj);
        }

    }

    @Override
    public List<Team> getTeams() {
        List<Team> toRet = new ArrayList<Team>();
        for (Object o : handle.g()) {
            net.minecraft.scoreboard.ScorePlayerTeam team = (net.minecraft.scoreboard.ScorePlayerTeam) o;
            toRet.add(team.getCanaryTeam());
        }
        return toRet;
    }

    @Override
    public void addTeam(Team team) {
        handle.a(((CanaryTeam) team).getHandle());
    }

    @Override
    public void removeTeam(Team team) {
        handle.c(((CanaryTeam) team).getHandle());
    }

    @Override
    public void removeTeam(String name) {
        for (Object o : handle.g()) {
            net.minecraft.scoreboard.ScorePlayerTeam team = (net.minecraft.scoreboard.ScorePlayerTeam) o;
            if (team.b().equalsIgnoreCase(name)) {
                handle.c(team);
                return;
            }
        }
    }

    @Override
    public Score getScore(Player player, ScoreObjective scoreObjective) {
        return this.getScore(player.getName(), scoreObjective);
    }

    @Override
    public Score getScore(String name, ScoreObjective scoreObjective) {
        return handle.c(name, ((CanaryScoreObjective) scoreObjective).getHandle()).getCanaryScore();
    }

    @Override
    public List<Score> getScores(ScoreObjective scoreObjective) {
        Collection i = handle.i(((CanaryScoreObjective) scoreObjective).getHandle());
        List<Score> scores = new ArrayList<Score>();
        for (Object o : i) {
            scores.add((Score) o);
        }
        return scores;
    }

    @Override
    public List<Score> getAllScores() {
        Collection i = handle.e();
        List<Score> scores = new ArrayList<Score>();
        for (Object o : i) {
            scores.add(((net.minecraft.scoreboard.Score) o).getCanaryScore());
        }
        return scores;
    }

    public net.minecraft.scoreboard.Scoreboard getHandle() {
        return handle;
    }

    @Override
    public void setScoreboardPosition(ScorePosition type, ScoreObjective objective) {
        handle.a(type.getId(), ((CanaryScoreObjective) objective).getHandle());
    }

    @Override
    public void setScoreboardPosition(ScorePosition type, ScoreObjective objective, Player player) {
        ((CanaryPlayer) player).getHandle().a.a(new S3DPacketDisplayScoreboard(type.getId(), ((CanaryScoreObjective) objective).getHandle()));
    }

    @Override
    public void clearScoreboardPosition(ScorePosition type) {
        for (Player p : Canary.getServer().getConfigurationManager().getAllPlayers()) {
            clearScoreboardPosition(type, p);
        }
    }

    @Override
    public void clearScoreboardPosition(ScorePosition type, Player player) {
        ((CanaryPlayer) player).getHandle().a.a(new S3DPacketDisplayScoreboard(type.getId(), null));
    }
    
    @Override
    public String getSaveName() {
        return saveName;
    }
}
