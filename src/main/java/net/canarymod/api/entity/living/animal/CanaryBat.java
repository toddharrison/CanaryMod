package net.canarymod.api.entity.living.animal;

import net.canarymod.api.entity.EntityType;
import net.canarymod.api.entity.living.AmbientCreature;
import net.minecraft.entity.passive.EntityBat;

import static net.canarymod.api.entity.EntityType.BAT;

/**
 * Bat wrapper implementation
 *
 * @author Jason (darkdiplomat)
 */
public class CanaryBat extends CanaryAnimal implements Bat, AmbientCreature {

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
        return getHandle().cb();
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

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAmbient() {
        return true;
    }

}
