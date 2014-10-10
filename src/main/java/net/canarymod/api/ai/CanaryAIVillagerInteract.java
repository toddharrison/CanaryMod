package net.canarymod.api.ai;

import net.minecraft.entity.ai.EntityAIVillagerInteract;

/**
 * @author Aaron
 */
public class CanaryAIVillagerInteract extends CanaryAIBase implements AIVillagerInteract {

    public CanaryAIVillagerInteract(EntityAIVillagerInteract ai) {
        super(ai);

    }

    public EntityAIVillagerInteract getHandle() {
        return (EntityAIVillagerInteract) handle;
    }
}
