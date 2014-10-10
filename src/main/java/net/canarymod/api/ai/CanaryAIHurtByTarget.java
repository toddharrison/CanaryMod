package net.canarymod.api.ai;

import net.minecraft.entity.ai.EntityAIHurtByTarget;

/**
 * @author Aaron
 */
public class CanaryAIHurtByTarget extends CanaryAIBase implements AIHurtByTarget {

    public CanaryAIHurtByTarget(EntityAIHurtByTarget ai) {
        super(ai);

    }

    public EntityAIHurtByTarget getHandle() {
        return (EntityAIHurtByTarget) handle;
    }
}
