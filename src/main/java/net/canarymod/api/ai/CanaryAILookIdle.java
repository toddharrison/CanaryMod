package net.canarymod.api.ai;

import net.minecraft.entity.ai.EntityAILookIdle;

/**
 * @author Aaron
 */
public class CanaryAILookIdle extends CanaryAIBase implements AILookIdle {

    public CanaryAILookIdle(EntityAILookIdle ai) {
        super(ai);

    }

    public EntityAILookIdle getHandle() {
        return (EntityAILookIdle) handle;
    }
}
