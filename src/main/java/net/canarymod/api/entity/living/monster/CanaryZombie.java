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
        return getHandle().cm();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setVillager(boolean villager) {
        getHandle().m(villager);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isChild() {
        return getHandle().i_();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setChild(boolean child) {
        getHandle().l(child);
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
        getHandle().setConversionTime(ticks);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isConverting() {
        return getHandle().cn();
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
