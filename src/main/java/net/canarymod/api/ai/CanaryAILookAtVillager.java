package net.canarymod.api.ai;

import net.minecraft.entity.ai.EntityAILookAtVillager;

/**
 * @author Aaron
 */
public class CanaryAILookAtVillager extends CanaryAIBase implements AILookAtVillager {

    public CanaryAILookAtVillager(EntityAILookAtVillager ai) {
        super(ai);

    }

    public EntityAILookAtVillager getHandle() {
        return (EntityAILookAtVillager) handle;
    }
}
