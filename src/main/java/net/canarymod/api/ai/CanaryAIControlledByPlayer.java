package net.canarymod.api.ai;

import net.minecraft.entity.ai.EntityAIControlledByPlayer;

/**
 * @author Aaron
 */
public class CanaryAIControlledByPlayer extends CanaryAIBase implements AIControlledByPlayer {

    public CanaryAIControlledByPlayer(EntityAIControlledByPlayer ai) {
        super(ai);

    }

    public EntityAIControlledByPlayer getHandle() {
        return (EntityAIControlledByPlayer) handle;
    }
}
