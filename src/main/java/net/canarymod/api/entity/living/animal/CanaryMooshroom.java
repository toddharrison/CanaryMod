package net.canarymod.api.entity.living.animal;

import net.canarymod.api.entity.EntityType;
import net.minecraft.entity.passive.EntityMooshroom;

import static net.canarymod.api.entity.EntityType.MOOSHROOM;

/**
 * MooshroomCow wrapper implementation
 *
 * @author Jason (darkdiplomat)
 */
public class CanaryMooshroom extends CanaryCow implements Mooshroom {

    /**
     * Constructs a new wrapper for EntityMooshroom
     *
     * @param entity
     *         the EntityMooshroom to wrap
     */
    public CanaryMooshroom(EntityMooshroom entity) {
        super(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityType getEntityType() {
        return MOOSHROOM;
    }

    @Override
    public String getFqName() {
        return "Mooshroom";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityMooshroom getHandle() {
        return (EntityMooshroom) entity;
    }
}
