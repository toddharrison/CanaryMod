package net.canarymod.api.ai;

import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;

/**
 * @author Aaron
 */
public class CanaryAIMoveTowardsTarget extends CanaryAIBase implements AIMoveTowardsTarget {

    public CanaryAIMoveTowardsTarget(EntityAIMoveTowardsTarget ai) {
        super(ai);

    }

    public EntityAIMoveTowardsTarget getHandle() {
        return (EntityAIMoveTowardsTarget) handle;
    }
}
