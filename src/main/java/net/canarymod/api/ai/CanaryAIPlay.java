package net.canarymod.api.ai;

import net.minecraft.entity.ai.EntityAIPlay;

/**
 * @author Aaron
 */
public class CanaryAIPlay extends CanaryAIBase implements AIPlay {

    public CanaryAIPlay(EntityAIPlay ai) {
        super(ai);

    }

    public EntityAIPlay getHandle() {
        return (EntityAIPlay) handle;
    }
}
