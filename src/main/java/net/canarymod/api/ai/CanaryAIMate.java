package net.canarymod.api.ai;

import net.minecraft.entity.ai.EntityAIMate;

/**
 * @author Aaron
 */
public class CanaryAIMate extends CanaryAIBase implements AIMate {

    public CanaryAIMate(EntityAIMate ai) {
        super(ai);

    }

    public EntityAIMate getHandle() {
        return (EntityAIMate) handle;
    }
}
