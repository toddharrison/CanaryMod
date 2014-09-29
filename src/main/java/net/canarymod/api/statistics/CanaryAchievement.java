package net.canarymod.api.statistics;

/**
 * Achievement wrapper
 *
 * @author Jason (darkdiplomat)
 */
public class CanaryAchievement extends CanaryStat implements Achievement {
    /**
     * Constructs a new StatBase wrapper
     *
     * @param statbase
     *         the StatBase to wrap
     */
    public CanaryAchievement(net.minecraft.stats.Achievement achievement) {
        super(achievement);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return getHandle().k;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Achievement getParent() {
        return getHandle().c.getCanaryAchievement();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSpecial() {
        return false;
    }

    public final net.minecraft.stats.Achievement getHandle() {
        return (net.minecraft.stats.Achievement) super.getHandle();
    }
}
