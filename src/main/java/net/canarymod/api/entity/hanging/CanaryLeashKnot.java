package net.canarymod.api.entity.hanging;

import net.canarymod.api.entity.EntityType;
import net.minecraft.entity.EntityLeashKnot;

import static net.canarymod.api.entity.EntityType.LEASHKNOT;

/**
 * LeashKnot wrapper implementation
 *
 * @author Jason (darkdiplomat)
 */
public class CanaryLeashKnot extends CanaryHangingEntity implements LeashKnot {

    public CanaryLeashKnot(EntityLeashKnot entity) {
        super(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityType getEntityType() {
        return LEASHKNOT;
    }

    @Override
    public String getFqName() {
        return "LeashKnot";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityLeashKnot getHandle() {
        return (EntityLeashKnot) entity;
    }

}
