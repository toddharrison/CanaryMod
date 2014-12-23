package net.canarymod.api.ai;

import net.minecraft.entity.ai.EntityAIAvoidEntity;

/**
 * @author Aaron
 */
public class CanaryAIAvoidEntity extends CanaryAIBase implements AIAvoidEntity {

    public CanaryAIAvoidEntity(EntityAIAvoidEntity ai) {
        super(ai);

    }

    public EntityAIAvoidEntity getHandle() {
        return (EntityAIAvoidEntity) handle;
    }
}
