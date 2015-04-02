package net.canarymod.api.scoreboard;

import net.canarymod.Canary;
import net.canarymod.api.entity.living.humanoid.CanaryPlayer;
import net.canarymod.api.entity.living.humanoid.Player;
import net.minecraft.network.play.server.S3DPacketDisplayScoreboard;

import net.minecraft.scoreboard.ScorePlayerTeam;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.canarymod.api.world.World;

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
            so = this.handle.a(String.format("%s_%s", getSaveName(), name), net.minecraft.scoreboard.IScoreObjectiveCriteria.b).getCanaryScoreObjective();
        }
        return so;
    }

    @Override
    public ScoreObjective addScoreObjective(String name, ScoreObjectiveCriteria criteria) {
        ScoreObjective so = getScoreObjective(name);
        if (so == null) {
            so = this.handle.a(String.format("%s_%s", getSaveName(), name), ((CanaryScoreDummyCriteria) criteria).getHandle()).getCanaryScoreObjective();
        }
        return so;
    }

    @Override
    public void removeScoreObjective(ScoreObjective objective) {
        this.handle.c(((CanaryScoreObjective) objective).getHandle());
    }

    @Override
    public ScoreObjective getScoreObjective(String name) {
        net.minecraft.scoreboard.ScoreObjective so = handle.getScoreObjective(String.format("%s_%s", getSaveName(), name));
        if (so!= null) {
            return so.getCanaryScoreObjective();
        }
        return null;
    }

    @Override
    public void removeScoreObjective(String name) {
        net.minecraft.scoreboard.ScoreObjective obj = handle.getScoreObjective(String.format("%s_%s", getSaveName(), name));
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
        handle.addTeam(((CanaryTeam) team).getHandle());
    }

    @Override
    public void removeTeam(Team team) {
        handle.d(((CanaryTeam) team).getHandle());
    }

    @Override
    public void removeTeam(String name) {
        for (Object o : handle.g()) {
            net.minecraft.scoreboard.ScorePlayerTeam team = (net.minecraft.scoreboard.ScorePlayerTeam) o;
            if (team.b().equalsIgnoreCase(name)) {
                handle.d(team);
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
            scores.add(((net.minecraft.scoreboard.Score) o).getCanaryScore());
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

    @Override
    public void setScoreboardPosition(ScorePosition type, ScoreObjective objective, World world) {
        for (Player player : world.getPlayerList()) {
            this.setScoreboardPosition(type, objective, player);
        }
    }
    
    @Override
    public void removeScore(String name) {
        handle.d(name, null);
    }

    @Override
    public void removeScore(String name, ScoreObjective objective) {
        handle.d(name, ((CanaryScoreObjective) objective).getHandle());
    }
    
    @Override
    public Team getTeam(String name) {
        ScorePlayerTeam team = handle.d(name);
        return team != null ? team.getCanaryTeam() : null;
    }
    
    @Override
    public Team addTeam(String name) throws IllegalArgumentException {
        return handle.e(name).getCanaryTeam();
    }
}
