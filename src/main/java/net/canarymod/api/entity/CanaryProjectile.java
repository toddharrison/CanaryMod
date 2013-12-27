package net.canarymod.api.entity;

import net.minecraft.entity.IProjectile;

/**
 * Projectile wrapper implementation
 *
 * @author Jason (darkdiplomat)
 */
public abstract class CanaryProjectile extends CanaryEntity implements Projectile {

    public CanaryProjectile(IProjectile projectile) {
        super((net.minecraft.entity.Entity) projectile);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setProjectileHeading(double motionX, double motionY, double motionZ, float rotationYaw, float rotationPitch) {
        ((IProjectile) getHandle()).c(motionX, motionY, motionZ, rotationYaw, rotationPitch);
    }

}
