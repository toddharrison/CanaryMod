package net.canarymod.api.ai;

import net.minecraft.entity.ai.EntityAIHarvestFarmland;

/**
 * @author Aaron
 */
public class CanaryAIHarvestFarmland extends CanaryAIBase implements AIHarvestFarmland {

    public CanaryAIHarvestFarmland(EntityAIHarvestFarmland ai) {
        super(ai);

    }

    public EntityAIHarvestFarmland getHandle() {
        return (EntityAIHarvestFarmland) handle;
    }
}
