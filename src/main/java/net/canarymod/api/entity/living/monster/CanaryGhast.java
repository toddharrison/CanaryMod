package net.canarymod.api.entity.living.monster;

import net.canarymod.api.entity.EntityType;
import net.canarymod.api.entity.living.CanaryLivingBase;
import net.canarymod.api.entity.living.LivingBase;
import net.minecraft.entity.monster.EntityGhast;

/**
 * Ghast wrapper implementation
 *
 * @author Jason (darkdiplomat)
 */
public class CanaryGhast extends CanaryEntityMob implements Ghast {

    /**
     * Constructs a new wrapper for EntityGhast
     *
     * @param entity
     *         the EntityGhast to wrap
     */
    public CanaryGhast(EntityGhast entity) {
        super(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityType getEntityType() {
        return EntityType.GHAST;
    }

    @Override
    public String getFqName() {
        return "Ghast";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCourseChangeCooldown() {
        throw new UnsupportedOperationException("Minecraft update broke this shit");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCourseChangeCooldown(int cooldown) {
        throw new UnsupportedOperationException("Minecraft update broke this shit");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getWaypointX() {
        throw new UnsupportedOperationException("Minecraft update broke this shit");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setWaypointX(double waypointX) {
        throw new UnsupportedOperationException("Minecraft update broke this shit");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getWaypointY() {
        throw new UnsupportedOperationException("Minecraft update broke this shit");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setWaypointY(double waypointY) {
        throw new UnsupportedOperationException("Minecraft update broke this shit");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getWaypointZ() {
        throw new UnsupportedOperationException("Minecraft update broke this shit");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setWaypointZ(double waypointZ) {
        throw new UnsupportedOperationException("Minecraft update broke this shit");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getArgoCooldown() {
        throw new UnsupportedOperationException("Minecraft update broke this shit");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setArgoCooldown(int ticks) {
        throw new UnsupportedOperationException("Minecraft update broke this shit");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LivingBase getAttackTarget() {
        throw new UnsupportedOperationException("Minecraft update broke this shit");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAttackTarget(LivingBase livingbase) {
        throw new UnsupportedOperationException("Minecraft update broke this shit");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityGhast getHandle() {
        return (EntityGhast) entity;
    }
}
