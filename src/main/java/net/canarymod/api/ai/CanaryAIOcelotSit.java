package net.canarymod.api.ai;

import net.minecraft.entity.ai.EntityAIOcelotSit;

/**
 * @author Aaron
 */
public class CanaryAIOcelotSit extends CanaryAIBase implements AIOcelotSit {

    public CanaryAIOcelotSit(EntityAIOcelotSit ai) {
        super(ai);

    }

    public EntityAIOcelotSit getHandle() {
        return (EntityAIOcelotSit) handle;
    }
}
