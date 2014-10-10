package net.canarymod.api.ai;

import net.minecraft.entity.ai.EntityAIFollowOwner;

/**
 * @author Aaron
 */
public class CanaryAIFollowOwner extends CanaryAIBase implements AIFollowOwner {

    public CanaryAIFollowOwner(EntityAIFollowOwner ai) {
        super(ai);

    }

    public EntityAIFollowOwner getHandle() {
        return (EntityAIFollowOwner) handle;
    }
}
