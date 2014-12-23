package net.canarymod.api.ai;

import net.minecraft.entity.ai.EntityAIFollowGolem;

/**
 * @author Aaron
 */
public class CanaryAIFollowGolem extends CanaryAIBase implements AIFollowGolem {

    public CanaryAIFollowGolem(EntityAIFollowGolem ai) {
        super(ai);

    }

    public EntityAIFollowGolem getHandle() {
        return (EntityAIFollowGolem) handle;
    }
}
