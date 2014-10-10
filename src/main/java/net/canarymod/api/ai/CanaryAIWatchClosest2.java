package net.canarymod.api.ai;

import net.minecraft.entity.ai.EntityAIWatchClosest2;

/**
 * @author Aaron
 */
public class CanaryAIWatchClosest2 extends CanaryAIBase implements AIWatchClosest2 {

    public CanaryAIWatchClosest2(EntityAIWatchClosest2 ai) {
        super(ai);

    }

    public EntityAIWatchClosest2 getHandle() {
        return (EntityAIWatchClosest2) handle;
    }
}
