package net.canarymod.api.entity.vehicle;

import net.minecraft.entity.item.EntityMinecart;

/**
 * Minecart wrapper implementation
 *
 * @author Jason (darkdiplomat)
 */
public abstract class CanaryMinecart extends CanaryVehicle implements Minecart {

    /**
     * Constructs a new wrapper for EntityMinecart
     *
     * @param entity
     *         the EntityMinecart to be wrapped
     */
    public CanaryMinecart(EntityMinecart entity) {
        super(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isInReverse() {
        return getHandle().a;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setRollingAmplitude(int amp) {
        getHandle().j(amp);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getRollingAmplitude() {
        return getHandle().q();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setRollingDirection(int direction) {
        getHandle().k(direction);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getRollingDirection() {
        return getHandle().r();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityMinecart getHandle() {
        return (EntityMinecart) entity;
    }
}
