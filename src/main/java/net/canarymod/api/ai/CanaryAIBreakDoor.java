package net.canarymod.api.ai;

import net.minecraft.entity.ai.EntityAIBreakDoor;

/**
 * @author Aaron
 */
public class CanaryAIBreakDoor extends CanaryAIBase implements AIBreakDoor {

    public CanaryAIBreakDoor(EntityAIBreakDoor ai) {
        super(ai);

    }

    public EntityAIBreakDoor getHandle() {
        return (EntityAIBreakDoor) handle;
    }
}
