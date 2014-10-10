package net.canarymod.api.ai;

import net.minecraft.entity.ai.EntityAITradePlayer;

/**
 * @author Aaron
 */
public class CanaryAITradePlayer extends CanaryAIBase implements AITradePlayer {

    public CanaryAITradePlayer(EntityAITradePlayer ai) {
        super(ai);

    }

    public EntityAITradePlayer getHandle() {
        return (EntityAITradePlayer) handle;
    }
}
