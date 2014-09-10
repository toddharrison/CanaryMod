package net.canarymod.api.entity.living.animal;

import net.canarymod.api.DyeColor;
import net.canarymod.api.entity.EntityType;
import net.minecraft.entity.passive.EntitySheep;

import static net.canarymod.api.entity.EntityType.SHEEP;

/**
 * Sheep wrapper implementation
 *
 * @author Jason (darkdiplomat)
 */
public class CanarySheep extends CanaryAgeableAnimal implements Sheep {

    /**
     * Constructs a new wrapper for EntitySheep
     *
     * @param entity the EntitySheep to wrap
     */
    public CanarySheep(EntitySheep entity) {
        super(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityType getEntityType() {
        return SHEEP;
    }

    @Override
    public String getFqName() {
        return "Sheep";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void eatGrass() {
        getHandle().n();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DyeColor getColor() {
        return DyeColor.values()[getHandle().bZ()];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setColor(DyeColor color) {
        getHandle().s(color.getDyeColorCode());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSheared() {
        return getHandle().bY();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSheared(boolean sheared) {
        getHandle().i(sheared);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntitySheep getHandle() {
        return (EntitySheep) entity;
    }

}
