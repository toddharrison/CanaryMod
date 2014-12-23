package net.canarymod.api.entity.living.monster;

import net.canarymod.api.entity.EnderCrystal;
import net.canarymod.api.entity.EntityType;
import net.canarymod.api.entity.living.CanaryEntityLiving;
import net.minecraft.entity.boss.EntityDragon;

/**
 * Dragon wrapper implementation
 *
 * @author Jason (darkdiplomat)
 */
public class CanaryEnderDragon extends CanaryEntityLiving implements EnderDragon {

    /**
     * Constructs a new wrapper for EntityDragon
     *
     * @param entity
     *         the EntityDragon to wrap
     */
    public CanaryEnderDragon(EntityDragon entity) {
        super(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityType getEntityType() {
        return EntityType.ENDERDRAGON;
    }

    @Override
    public String getFqName() {
        return "EnderDragon";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSlowed() {
        return getHandle().bv;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EnderCrystal getHealingCrystal() {
        return getHandle().bx != null ? (EnderCrystal) getHandle().bx.getCanaryEntity() : null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityDragon getHandle() {
        return (EntityDragon) entity;
    }
}
