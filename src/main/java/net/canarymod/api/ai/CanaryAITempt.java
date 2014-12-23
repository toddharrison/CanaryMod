package net.canarymod.api.ai;

import net.minecraft.entity.ai.EntityAITempt;

/**
 * @author Aaron
 */
public class CanaryAITempt extends CanaryAIBase implements AITempt {

    public CanaryAITempt(EntityAITempt ai) {
        super(ai);

    }

    public EntityAITempt getHandle() {
        return (EntityAITempt) handle;
    }
}
