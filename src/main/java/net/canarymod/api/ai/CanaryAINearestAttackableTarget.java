package net.canarymod.api.ai;

import net.minecraft.entity.ai.EntityAINearestAttackableTarget;

/**
 * @author Aaron
 */
public class CanaryAINearestAttackableTarget extends CanaryAIBase implements AINearestAttackableTarget {

    public CanaryAINearestAttackableTarget(EntityAINearestAttackableTarget ai) {
        super(ai);

    }

    public EntityAINearestAttackableTarget getHandle() {
        return (EntityAINearestAttackableTarget) handle;
    }
}
