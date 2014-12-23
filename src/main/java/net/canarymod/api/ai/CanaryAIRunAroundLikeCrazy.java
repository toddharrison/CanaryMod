package net.canarymod.api.ai;

import net.minecraft.entity.ai.EntityAIRunAroundLikeCrazy;

/**
 * @author Aaron
 */
public class CanaryAIRunAroundLikeCrazy extends CanaryAIBase implements AIRunAroundLikeCrazy {

    public CanaryAIRunAroundLikeCrazy(EntityAIRunAroundLikeCrazy ai) {
        super(ai);

    }

    public EntityAIRunAroundLikeCrazy getHandle() {
        return (EntityAIRunAroundLikeCrazy) handle;
    }
}
