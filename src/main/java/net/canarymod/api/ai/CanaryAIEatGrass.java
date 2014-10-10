package net.canarymod.api.ai;

import net.minecraft.entity.ai.EntityAIEatGrass;

/**
 * @author Aaron
 */
public class CanaryAIEatGrass extends CanaryAIBase implements AIEatGrass {

    public CanaryAIEatGrass(EntityAIEatGrass ai) {
        super(ai);

    }

    public EntityAIEatGrass getHandle() {
        return (EntityAIEatGrass) handle;
    }
}
