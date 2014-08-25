package net.canarymod.api.scoreboard;

import net.canarymod.Canary;
import net.canarymod.api.world.CanaryWorld;
import net.canarymod.api.world.DimensionType;
import net.canarymod.api.world.World;
import net.minecraft.scoreboard.ServerScoreboard;

import static net.canarymod.Canary.log;


/**
 * @author Somners
 */
public class CanaryScoreboardManager implements ScoreboardManager {

    @Override
    public Scoreboard getScoreboard(String worldName) {
        return this.getScoreboard(Canary.getServer().getWorldManager().getWorld(worldName, DimensionType.NORMAL, true));
    }

    @Override
    public Scoreboard getScoreboard(World world) {
        if (world == null) {
            return null;
        }
        if (world.getType() != DimensionType.fromId(0)) {
            world = Canary.getServer().getWorldManager().getWorld(world.getName(), DimensionType.NORMAL, true);
        }
        return ((ServerScoreboard) ((CanaryWorld) world).getHandle().D).getCanaryScoreboard();
    }

    @Override
    public ScoreObjectiveCriteria getScoreCriteria(String name) {
        return ((net.minecraft.scoreboard.ScoreDummyCriteria) net.minecraft.scoreboard.IScoreObjectiveCriteria.a.get(name)).getCanaryScoreObjectiveCriteria();
    }

    @Override
    public void registerScoreCriteria(String name, Class<? extends ScoreObjectiveCriteria> criteria) {
        if (net.minecraft.scoreboard.IScoreObjectiveCriteria.a.containsKey(name)) {
            return;
        }
        try {
            new ScoreCanaryCriteria(criteria.newInstance());
        }
        catch (InstantiationException ex) {
            log.error("Exception Registering ScoreObjectiveCritera: " + criteria.getName());
        }
        catch (IllegalAccessException ex) {
            log.error("Exception Registering ScoreObjectiveCritera: " + criteria.getName());
        }
    }

}
