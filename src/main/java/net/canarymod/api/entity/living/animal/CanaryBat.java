package net.canarymod.api.entity.living.animal;

import net.canarymod.api.entity.EntityType;
import net.minecraft.entity.passive.EntityBat;

import static net.canarymod.api.entity.EntityType.BAT;

/**
 * Bat wrapper implementation
 *
 * @author Jason (darkdiplomat)
 */
public class CanaryBat extends CanaryAnimal implements Bat {

    /**
     * Constructs a new wrapper for EntityBat
     *
     * @param entity
     *         the EntityBat to be wrapped
     */
    public CanaryBat(EntityBat entity) {
        super(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityType getEntityType() {
        return BAT;
    }

    @Override
    public String getFqName() {
        return "Bat";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isHanging() {
        return getHandle().bN();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setHanging(boolean hanging) {
        getHandle().a(hanging);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityBat getHandle() {
        return (EntityBat) entity;
    }
}
