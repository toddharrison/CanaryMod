package net.canarymod.api.entity.living.animal;

import net.canarymod.api.entity.EntityType;
import net.canarymod.api.entity.vehicle.CanaryAnimalVehicle;
import net.minecraft.entity.passive.EntityPig;

import static net.canarymod.api.entity.EntityType.PIG;

/**
 * Pig wrapper implementation
 *
 * @author Jason (darkdiplomat)
 */
public class CanaryPig extends CanaryAnimalVehicle implements Pig {

    /**
     * Constructs a new wrapper for EntityPig
     *
     * @param entity
     *         the EntityPig to wrap
     */
    public CanaryPig(EntityPig entity) {
        super(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityType getEntityType() {
        return PIG;
    }

    @Override
    public String getFqName() {
        return "Pig";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSaddled() {
        return getHandle().bX();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSaddled(boolean saddled) {
        getHandle().i(saddled);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getGrowingAge() {
        return getHandle().d();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setGrowingAge(int age) {
        getHandle().c(age);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityPig getHandle() {
        return (EntityPig) entity;
    }
}
