package net.canarymod.api.entity.living.animal;

import net.canarymod.api.entity.living.Ageable;

/**
 * Covers the age methods
 *
 * @author Jason (darkdiplomat)
 */
public abstract class CanaryAgeableAnimal extends CanaryAnimal implements Ageable {

    public CanaryAgeableAnimal(net.minecraft.entity.passive.EntityAnimal entity) {
        super(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getGrowingAge() {
        return getHandle().l();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setGrowingAge(int age) {
        getHandle().b(age);
    }

    @Override
    public net.minecraft.entity.passive.EntityAnimal getHandle() {
        return (net.minecraft.entity.passive.EntityAnimal) entity;
    }
}
