package net.canarymod.api.entity.living.animal;

import net.canarymod.api.DyeColor;
import net.canarymod.api.entity.EntityType;
import net.minecraft.entity.passive.EntityWolf;

import static net.canarymod.api.entity.EntityType.WOLF;
import net.minecraft.item.EnumDyeColor;

/**
 * Wolf wrapper implementation
 *
 * @author Jason (darkdiplomat)
 */
public class CanaryWolf extends CanaryTameable implements Wolf {

    /**
     * Constructs a new wrapper for EntityWolf
     *
     * @param entity the EntityWolf to wrap
     */
    public CanaryWolf(EntityWolf entity) {
        super(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityType getEntityType() {
        return WOLF;
    }

    @Override
    public String getFqName() {
        return "Wolf";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCollarColor(DyeColor color) {
        getHandle().a(EnumDyeColor.b(color.getDyeColorCode()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DyeColor getCollarColor() {
        return DyeColor.values()[getHandle().cu().a()];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAngry() {
        return getHandle().ct();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAngry(boolean angry) {
        getHandle().o(angry);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityWolf getHandle() {
        return (EntityWolf) entity;
    }
}
