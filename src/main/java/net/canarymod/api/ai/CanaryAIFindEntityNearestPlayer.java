package net.canarymod.api.ai;

import net.minecraft.entity.ai.EntityAIFindEntityNearestPlayer;

/**
 * @author Aaron
 */
public class CanaryAIFindEntityNearestPlayer extends CanaryAIBase implements AIFindEntityNearestPlayer {

    public CanaryAIFindEntityNearestPlayer(EntityAIFindEntityNearestPlayer ai) {
        super(ai);

    }

    public EntityAIFindEntityNearestPlayer getHandle() {
        return (EntityAIFindEntityNearestPlayer) handle;
    }
}
