package net.canarymod.api.ai;

import net.minecraft.entity.ai.EntityAIDoorInteract;

/**
 * @author Aaron
 */
public class CanaryAIDoorInteract extends CanaryAIBase implements AIDoorInteract {

    public CanaryAIDoorInteract(EntityAIDoorInteract ai) {
        super(ai);

    }

    public EntityAIDoorInteract getHandle() {
        return (EntityAIDoorInteract) handle;
    }
}
