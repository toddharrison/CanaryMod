package net.canarymod.api.ai;

import net.minecraft.entity.ai.EntityAIRestrictOpenDoor;

/**
 * @author Aaron
 */
public class CanaryAIRestrictOpenDoor extends CanaryAIBase implements AIRestrictOpenDoor {

    public CanaryAIRestrictOpenDoor(EntityAIRestrictOpenDoor ai) {
        super(ai);

    }

    public EntityAIRestrictOpenDoor getHandle() {
        return (EntityAIRestrictOpenDoor) handle;
    }
}
