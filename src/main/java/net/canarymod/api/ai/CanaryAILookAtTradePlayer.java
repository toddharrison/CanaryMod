package net.canarymod.api.ai;

import net.minecraft.entity.ai.EntityAILookAtTradePlayer;

/**
 * @author Aaron
 */
public class CanaryAILookAtTradePlayer extends CanaryAIBase implements AILookAtTradePlayer {

    public CanaryAILookAtTradePlayer(EntityAILookAtTradePlayer ai) {
        super(ai);

    }

    public EntityAILookAtTradePlayer getHandle() {
        return (EntityAILookAtTradePlayer) handle;
    }
}
