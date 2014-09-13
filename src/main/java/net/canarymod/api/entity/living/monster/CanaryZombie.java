package net.canarymod.api.entity.living.monster;

import net.canarymod.api.entity.EntityType;
import net.minecraft.entity.monster.EntityZombie;

/**
 * Zombie wrapper implementation
 *
 * @author Jason (darkdiplomat)
 */
public class CanaryZombie extends CanaryEntityMob implements Zombie {

    /**
     * Constructs a new wrapper for EntityZombie
     *
     * @param entity
     *         the EntityZombie to wrap
     */
    public CanaryZombie(EntityZombie entity) {
        super(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityType getEntityType() {
        return EntityType.ZOMBIE;
    }

    @Override
    public String getFqName() {
        return "Zombie";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isVillager() {
        return getHandle().cb();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setVillager(boolean villager) {
        getHandle().i(villager);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isChild() {
        return getHandle().f();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setChild(boolean child) {
        getHandle().j(child);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getConversionTime() {
        return getHandle().getConvertTicks();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setConversionTime(int ticks) {
        getHandle().b(ticks);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isConverting() {
        return getHandle().cc();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stopConverting() {
        getHandle().stopConversion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void convertToVillager() {
        getHandle().cd();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityZombie getHandle() {
        return (EntityZombie) entity;
    }
}
