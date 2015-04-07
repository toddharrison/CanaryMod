package net.canarymod.api.ai;

import net.minecraft.entity.ai.EntityAIBase;

/**
 * @author Aaron
 */
public abstract class CanaryAIBase implements AIBase {

    protected EntityAIBase handle;

    public CanaryAIBase(EntityAIBase ai) {
        this.handle = ai;
    }

    @Override
    public boolean shouldExecute() {
        return this.handle.a();
    }

    @Override
    public boolean continueExecuting() {
        return this.handle.b();
    }

    @Override
    public boolean isContinuous() {
        return this.handle.i();
    }

    @Override
    public void startExecuting() {
        this.handle.c();
    }

    @Override
    public void resetTask() {
        this.handle.d();
    }

    @Override
    public void updateTask() {
        this.handle.e();
    }

    public abstract EntityAIBase getHandle();
}
