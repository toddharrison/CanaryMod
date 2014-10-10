package net.canarymod.api.ai;

import net.minecraft.entity.ai.EntityAIOcelotAttack;

/**
 * @author Aaron
 */
public class CanaryAIOcelotAttack extends CanaryAIBase implements AIOcelotAttack {

    public CanaryAIOcelotAttack(EntityAIOcelotAttack ai) {
        super(ai);

    }

    public EntityAIOcelotAttack getHandle() {
        return (EntityAIOcelotAttack) handle;
    }
}
