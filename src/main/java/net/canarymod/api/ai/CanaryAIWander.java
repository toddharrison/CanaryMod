package net.canarymod.api.ai;

import net.minecraft.entity.ai.EntityAIWander;

/**
 * @author Aaron
 */
public class CanaryAIWander extends CanaryAIBase implements AIWander {

    public CanaryAIWander(EntityAIWander ai) {
        super(ai);

    }

    public EntityAIWander getHandle() {
        return (EntityAIWander) handle;
    }
}
