package net.canarymod.api.entity.living.monster;

import net.canarymod.api.entity.EntityType;
import net.minecraft.entity.monster.EntityEndermite;

/**
 * EntityEndermite wrapper implementation
 *
 * @author Jason Jones (darkdiplomat)
 */
public class CanaryEndermite extends CanaryEntityMob implements Endermite {

    public CanaryEndermite(EntityEndermite entity) {
        super(entity);
    }

    @Override
    public boolean isPlayerSpanwed() {
        return getHandle().n();
    }

    @Override
    public String getFqName() {
        return "Endermite";
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.ENDERMITE;
    }

    public EntityEndermite getHandle() {
        return (EntityEndermite)super.getHandle();
    }
}
