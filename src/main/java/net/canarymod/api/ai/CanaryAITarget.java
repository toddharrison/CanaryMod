package net.canarymod.api.ai;

import net.minecraft.entity.ai.EntityAITarget;

/**
 * @author Aaron
 */
public class CanaryAITarget extends CanaryAIBase implements AITarget {

    public CanaryAITarget(EntityAITarget ai) {
        super(ai);

    }

    public EntityAITarget getHandle() {
        return (EntityAITarget) handle;
    }
}
