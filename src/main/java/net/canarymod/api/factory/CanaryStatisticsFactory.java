package net.canarymod.api.factory;

import net.canarymod.api.statistics.Achievement;
import net.canarymod.api.statistics.Stat;
import net.minecraft.stats.StatList;

/**
 * Statistics Factory implementation
 *
 * @author Jason (darkdiplomat)
 */
public final class CanaryStatisticsFactory implements StatisticsFactory {

    @Override
    public Stat getStat(String nmsName) {
        return StatList.a(nmsName).getCanaryStat();
    }

    @Override
    public Achievement getAchievement(String nmsName) {
        Stat stat = getStat(nmsName);
        return stat != null ? (Achievement) stat : null;
    }
}
