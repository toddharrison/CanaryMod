package net.canarymod.api.ai;

import net.minecraft.entity.ai.EntityAIMoveIndoors;

/**
 * @author Aaron
 */
public class CanaryAIMoveIndoors extends CanaryAIBase implements AIMoveIndoors {

    public CanaryAIMoveIndoors(EntityAIMoveIndoors ai) {
        super(ai);

    }

    public EntityAIMoveIndoors getHandle() {
        return (EntityAIMoveIndoors) handle;
    }
}
