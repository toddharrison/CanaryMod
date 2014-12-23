package net.canarymod.api.ai;

import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;

/**
 * @author Aaron
 */
public class CanaryAIOwnerHurtByTarget extends CanaryAIBase implements AIOwnerHurtByTarget {

    public CanaryAIOwnerHurtByTarget(EntityAIOwnerHurtByTarget ai) {
        super(ai);

    }

    public EntityAIOwnerHurtByTarget getHandle() {
        return (EntityAIOwnerHurtByTarget) handle;
    }
}
