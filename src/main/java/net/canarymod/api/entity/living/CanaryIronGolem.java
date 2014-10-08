package net.canarymod.api.entity.living;

import net.canarymod.api.entity.EntityType;
import net.canarymod.api.world.CanaryVillage;
import net.canarymod.api.world.Village;
import net.minecraft.entity.monster.EntityIronGolem;

/**
 * IronGolem wrapper implementation
 *
 * @author Jason (darkdiplomat)
 */
public class CanaryIronGolem extends CanaryEntityLiving implements IronGolem {

    /**
     * Constructs a new wrapper for EntityIronGolem
     *
     * @param entity
     *         the EntityIronGolem to be wrapped
     */
    public CanaryIronGolem(EntityIronGolem entity) {
        super(entity);
    }

    @Override
    public boolean isGolem() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityType getEntityType() {
        return EntityType.IRONGOLEM;
    }

    @Override
    public String getFqName() {
        return "IronGolem";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Village getVillage() {
        return getHandle().n().getCanaryVillage();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setVillage(Village village) {
        getHandle().a = ((CanaryVillage) village).getHandle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPlayerCreated() {
        return getHandle().cl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPlayerCreated(boolean created) {
        getHandle().l(created);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isHoldingRose() {
        return getHandle().ck() > 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setHoldingRose(boolean holding) {
        getHandle().a(holding);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getHoldRoseTicks() {
        return getHandle().ck();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setHoldRoseTicks(int ticks) {
        getHandle().setRoseTicks(ticks);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityIronGolem getHandle() {
        return (EntityIronGolem) entity;
    }
}
