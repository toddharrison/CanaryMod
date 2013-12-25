package net.canarymod.api.entity.living.monster;

import net.canarymod.api.entity.living.CanaryEntityLiving;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.monster.EntitySlime;

/**
 * EntityMob wrapper implementation
 *
 * @author Jason (darkdiplomat)
 */
public abstract class CanaryEntityMob extends CanaryEntityLiving implements EntityMob {

    public CanaryEntityMob(net.minecraft.entity.monster.EntityMob entity) {
        super(entity);
    }

    public CanaryEntityMob(EntitySlime entity) {
        super(entity);
    }

    public CanaryEntityMob(EntityFlying entity) {
        super(entity);
    }
}
