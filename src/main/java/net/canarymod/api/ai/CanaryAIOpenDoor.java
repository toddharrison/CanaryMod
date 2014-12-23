package net.canarymod.api.ai;

import net.minecraft.entity.ai.EntityAIOpenDoor;

/**
 * @author Aaron
 */
public class CanaryAIOpenDoor extends CanaryAIBase implements AIOpenDoor {

    public CanaryAIOpenDoor(EntityAIOpenDoor ai) {
        super(ai);

    }

    public EntityAIOpenDoor getHandle() {
        return (EntityAIOpenDoor) handle;
    }
}
