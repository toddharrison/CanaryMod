package net.canarymod.api.ai;

import net.minecraft.entity.ai.EntityAIMoveThroughVillage;

/**
 * @author Aaron
 */
public class CanaryAIMoveThroughVillage extends CanaryAIBase implements AIMoveThroughVillage {

    public CanaryAIMoveThroughVillage(EntityAIMoveThroughVillage ai) {
        super(ai);

    }

    public EntityAIMoveThroughVillage getHandle() {
        return (EntityAIMoveThroughVillage) handle;
    }
}
