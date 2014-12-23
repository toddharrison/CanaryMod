package net.canarymod.api.ai;

import net.minecraft.entity.ai.EntityAIFleeSun;

/**
 * @author Aaron
 */
public class CanaryAIFleeSun extends CanaryAIBase implements AIFleeSun {

    public CanaryAIFleeSun(EntityAIFleeSun ai) {
        super(ai);

    }

    public EntityAIFleeSun getHandle() {
        return (EntityAIFleeSun) handle;
    }
}
