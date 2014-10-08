package net.canarymod.api.entity.living.animal;

import net.canarymod.api.DyeColor;
import net.canarymod.api.entity.EntityType;
import static net.canarymod.api.entity.EntityType.SHEEP;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.EnumDyeColor;

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
        getHandle().v();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DyeColor getColor() {
        return DyeColor.values()[getHandle().cj().a()];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setColor(DyeColor color) {
        getHandle().b(EnumDyeColor.b(color.getDyeColorCode()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSheared() {
        return getHandle().ck();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSheared(boolean sheared) {
        getHandle().l(sheared);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntitySheep getHandle() {
        return (EntitySheep) entity;
    }

}
