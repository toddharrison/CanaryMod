package net.canarymod.api.entity.living.animal;

import net.canarymod.api.entity.EntityType;
import net.minecraft.entity.passive.EntityChicken;

import static net.canarymod.api.entity.EntityType.CHICKEN;

/**
 * Chicken wrapper implementation
 *
 * @author Chris (damagefilter)
 * @author Jason (darkdiplomat)
 */
public class CanaryChicken extends CanaryAgeableAnimal implements Chicken {

    /**
     * Constructs a new wrapper for EntityChicken
     *
     * @param entity
     *         the EntityChicken to be wrapped
     */
    public CanaryChicken(EntityChicken entity) {
        super(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityType getEntityType() {
        return CHICKEN;
    }

    @Override
    public String getFqName() {
        return "Chicken";
    }

    public int getTimeUntilNextEgg() {
        return getHandle().bu;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTimeUntilNextEgg(int timeTicks) {
        getHandle().bu = timeTicks;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityChicken getHandle() {
        return (EntityChicken) entity;
    }

}
