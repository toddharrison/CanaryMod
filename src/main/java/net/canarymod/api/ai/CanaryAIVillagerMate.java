package net.canarymod.api.ai;

import net.minecraft.entity.ai.EntityAIVillagerMate;

/**
 * @author Aaron
 */
public class CanaryAIVillagerMate extends CanaryAIBase implements AIVillagerMate {

    public CanaryAIVillagerMate(EntityAIVillagerMate ai) {
        super(ai);

    }

    public EntityAIVillagerMate getHandle() {
        return (EntityAIVillagerMate) handle;
    }
}
