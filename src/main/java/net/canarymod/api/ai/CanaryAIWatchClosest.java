package net.canarymod.api.ai;

import net.minecraft.entity.ai.EntityAIWatchClosest;

/**
 * @author Aaron
 */
public class CanaryAIWatchClosest extends CanaryAIBase implements AIWatchClosest {

    public CanaryAIWatchClosest(EntityAIWatchClosest ai) {
        super(ai);

    }

    public EntityAIWatchClosest getHandle() {
        return (EntityAIWatchClosest) handle;
    }
}
