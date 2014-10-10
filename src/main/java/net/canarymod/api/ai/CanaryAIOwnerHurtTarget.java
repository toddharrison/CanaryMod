package net.canarymod.api.ai;

import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;

/**
 * @author Aaron
 */
public class CanaryAIOwnerHurtTarget extends CanaryAIBase implements AIOwnerHurtTarget {

    public CanaryAIOwnerHurtTarget(EntityAIOwnerHurtTarget ai) {
        super(ai);

    }

    public EntityAIOwnerHurtTarget getHandle() {
        return (EntityAIOwnerHurtTarget) handle;
    }
}
