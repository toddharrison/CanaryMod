package net.canarymod.api.ai;

import net.minecraft.entity.ai.EntityAIFindEntityNearest;

/**
 * @author Aaron
 */
public class CanaryAIFindEntityNearest extends CanaryAIBase implements AIFindEntityNearest {

    public CanaryAIFindEntityNearest(EntityAIFindEntityNearest ai) {
        super(ai);

    }

    public EntityAIFindEntityNearest getHandle() {
        return (EntityAIFindEntityNearest) handle;
    }
}
