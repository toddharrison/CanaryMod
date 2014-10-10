package net.canarymod.api.ai;

import net.minecraft.entity.ai.EntityAIArrowAttack;

/**
 * @author Aaron
 */
public class CanaryAIArrowAttack extends CanaryAIBase implements AIArrowAttack {

    public CanaryAIArrowAttack(EntityAIArrowAttack ai) {
        super(ai);

    }

    public EntityAIArrowAttack getHandle() {
        return (EntityAIArrowAttack) handle;
    }
}
