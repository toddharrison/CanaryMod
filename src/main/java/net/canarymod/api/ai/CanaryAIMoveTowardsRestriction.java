package net.canarymod.api.ai;

import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;

/**
 * @author Aaron
 */
public class CanaryAIMoveTowardsRestriction extends CanaryAIBase implements AIMoveTowardsRestriction {

    public CanaryAIMoveTowardsRestriction(EntityAIMoveTowardsRestriction ai) {
        super(ai);

    }

    public EntityAIMoveTowardsRestriction getHandle() {
        return (EntityAIMoveTowardsRestriction) handle;
    }
}
