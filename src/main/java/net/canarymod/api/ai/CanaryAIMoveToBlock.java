package net.canarymod.api.ai;

import net.minecraft.entity.ai.EntityAIMoveToBlock;

/**
 * @author Aaron
 */
public class CanaryAIMoveToBlock extends CanaryAIBase implements AIMoveToBlock {

    public CanaryAIMoveToBlock(EntityAIMoveToBlock ai) {
        super(ai);

    }

    public EntityAIMoveToBlock getHandle() {
        return (EntityAIMoveToBlock) handle;
    }
}
