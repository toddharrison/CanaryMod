package net.canarymod.api.ai;

import net.minecraft.entity.ai.EntityAIDefendVillage;

/**
 * @author Aaron
 */
public class CanaryAIDefendVillage extends CanaryAIBase implements AIDefendVillage {

    public CanaryAIDefendVillage(EntityAIDefendVillage ai) {
        super(ai);

    }

    public EntityAIDefendVillage getHandle() {
        return (EntityAIDefendVillage) handle;
    }
}
