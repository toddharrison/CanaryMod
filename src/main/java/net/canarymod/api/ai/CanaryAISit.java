package net.canarymod.api.ai;

import net.minecraft.entity.ai.EntityAISit;

/**
 * @author Aaron
 */
public class CanaryAISit extends CanaryAIBase implements AISit {

    public CanaryAISit(EntityAISit ai) {
        super(ai);

    }

    public EntityAISit getHandle() {
        return (EntityAISit) handle;
    }
}
