package net.canarymod.api.ai;

import net.minecraft.entity.ai.EntityAITargetNonTamed;

/**
 * @author Aaron
 */
public class CanaryAITargetNonTamed extends CanaryAIBase implements AITargetNonTamed {

    public CanaryAITargetNonTamed(EntityAITargetNonTamed ai) {
        super(ai);

    }

    public EntityAITargetNonTamed getHandle() {
        return (EntityAITargetNonTamed) handle;
    }
}
