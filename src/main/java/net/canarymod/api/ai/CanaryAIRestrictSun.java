package net.canarymod.api.ai;

import net.minecraft.entity.ai.EntityAIRestrictSun;

/**
 * @author Aaron
 */
public class CanaryAIRestrictSun extends CanaryAIBase implements AIRestrictSun {

    public CanaryAIRestrictSun(EntityAIRestrictSun ai) {
        super(ai);

    }

    public EntityAIRestrictSun getHandle() {
        return (EntityAIRestrictSun) handle;
    }
}
