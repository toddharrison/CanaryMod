package net.canarymod.api.ai;

import net.minecraft.entity.ai.EntityAILeapAtTarget;

/**
 * @author Aaron
 */
public class CanaryAILeapAtTarget extends CanaryAIBase implements AILeapAtTarget {

    public CanaryAILeapAtTarget(EntityAILeapAtTarget ai) {
        super(ai);

    }

    public EntityAILeapAtTarget getHandle() {
        return (EntityAILeapAtTarget) handle;
    }
}
