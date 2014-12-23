package net.canarymod.api.ai;

import net.minecraft.entity.ai.EntityAICreeperSwell;

/**
 * @author Aaron
 */
public class CanaryAICreeperSwell extends CanaryAIBase implements AICreeperSwell {

    public CanaryAICreeperSwell(EntityAICreeperSwell ai) {
        super(ai);

    }

    public EntityAICreeperSwell getHandle() {
        return (EntityAICreeperSwell) handle;
    }
}
