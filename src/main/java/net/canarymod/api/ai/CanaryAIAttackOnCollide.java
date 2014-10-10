package net.canarymod.api.ai;

import net.minecraft.entity.ai.EntityAIAttackOnCollide;

/**
 * @author Aaron
 */
public class CanaryAIAttackOnCollide extends CanaryAIBase implements AIAttackOnCollide {

    public CanaryAIAttackOnCollide(EntityAIAttackOnCollide ai) {
        super(ai);

    }

    public EntityAIAttackOnCollide getHandle() {
        return (EntityAIAttackOnCollide) handle;
    }
}
