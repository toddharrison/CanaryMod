package net.canarymod.api.ai;

import net.minecraft.entity.ai.EntityAIPanic;

/**
 * @author Aaron
 */
public class CanaryAIPanic extends CanaryAIBase implements AIPanic {

    public CanaryAIPanic(EntityAIPanic ai) {
        super(ai);

    }

    public EntityAIPanic getHandle() {
        return (EntityAIPanic) handle;
    }
}
