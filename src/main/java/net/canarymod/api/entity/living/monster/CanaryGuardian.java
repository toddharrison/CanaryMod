package net.canarymod.api.entity.living.monster;

import net.canarymod.api.entity.EntityType;
import net.minecraft.entity.monster.EntityGuardian;

/**
 * EntityGuardian wrapper implementation
 *
 * @author Jason Jones (darkdiplomat)
 */
public class CanaryGuardian extends CanaryEntityMob implements Guardian {

    public CanaryGuardian(EntityGuardian guardian) {
        super(guardian);
    }

    @Override
    public String getFqName() {
        return "Guardian";
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.GUARDIAN;
    }

    @Override
    public boolean isElder() {
        return getHandle().cl();
    }

    public EntityGuardian getHandle() {
        return (EntityGuardian)super.getHandle();
    }
}
