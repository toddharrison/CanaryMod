package net.canarymod.api.ai;

import net.minecraft.entity.ai.EntityAISwimming;

/**
 * @author Aaron
 */
public class CanaryAISwimming extends CanaryAIBase implements AISwimming {

    public CanaryAISwimming(EntityAISwimming ai) {
        super(ai);

    }

    public EntityAISwimming getHandle() {
        return (EntityAISwimming) handle;
    }
}
