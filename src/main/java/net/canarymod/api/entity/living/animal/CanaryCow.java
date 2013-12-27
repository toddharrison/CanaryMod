package net.canarymod.api.entity.living.animal;

import net.canarymod.api.entity.EntityType;
import net.minecraft.entity.passive.EntityCow;

import static net.canarymod.api.entity.EntityType.COW;

/**
 * Cow wrapper implementation
 *
 * @author Chris (damagefilter)
 * @author Jason (darkdiplomat)
 */
public class CanaryCow extends CanaryAgeableAnimal implements Cow {

    /**
     * Constructs a new wrapper for EntityCow
     *
     * @param entity
     *         the EntityCow to wrap
     */
    public CanaryCow(EntityCow entity) {
        super(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityType getEntityType() {
        return COW;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getFqName() {
        return "Cow";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityCow getHandle() {
        return (EntityCow) entity;
    }
}
