package net.canarymod.api.ai;

import net.minecraft.entity.ai.EntityAIBeg;

/**
 * @author Aaron
 */
public class CanaryAIBeg extends CanaryAIBase implements AIBeg {

    public CanaryAIBeg(EntityAIBeg ai) {
        super(ai);

    }

    public EntityAIBeg getHandle() {
        return (EntityAIBeg) handle;
    }
}
