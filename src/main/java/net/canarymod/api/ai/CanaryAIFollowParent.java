package net.canarymod.api.ai;

import net.minecraft.entity.ai.EntityAIFollowParent;

/**
 * @author Aaron
 */
public class CanaryAIFollowParent extends CanaryAIBase implements AIFollowParent {

    public CanaryAIFollowParent(EntityAIFollowParent ai) {
        super(ai);

    }

    public EntityAIFollowParent getHandle() {
        return (EntityAIFollowParent) handle;
    }
}
