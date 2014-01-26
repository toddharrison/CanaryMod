package net.canarymod.api.ai;

import net.minecraft.entity.ai.EntityAIBase;

import static net.canarymod.Canary.log;

/**
 * @author Somners
 */
public class EntityAICanary extends EntityAIBase {

    private final AIBase ai;

    public EntityAICanary(AIBase ai) {
        this.ai = ai;
    }

    @Override
    public boolean a() {
        try {
            return ai.shouldExecute();
        }
        catch (Exception ex) {
            log.error(String.format("Excpetion in AI class '%s' while executing method '%s'",
                    ai.getClass().getName(), ai.getClass().getName() + ".shouldExecute()"), ex);
        }
        return false;
    }

    @Override
    public boolean b() {
        try {
            return ai.continueExecuting();
        }
        catch (Exception ex) {
            log.error(String.format("Excpetion in AI class '%s' while executing method '%s'",
                    ai.getClass().getName(), ai.getClass().getName() + ".continueExecuting()"), ex);
        }
        return false;
    }

    @Override
    public boolean i() {
        try {
            return ai.isContinuous();
        }
        catch (Exception ex) {
            log.error(String.format("Excpetion in AI class '%s' while executing method '%s'",
                    ai.getClass().getName(), ai.getClass().getName() + ".isContinuous()"), ex);
        }
        return false;
    }

    @Override
    public void c() {
        try {
            ai.startExecuting();
        }
        catch (Exception ex) {
            log.error(String.format("Excpetion in AI class '%s' while executing method '%s'",
                    ai.getClass().getName(), ai.getClass().getName() + ".startExecuting()"), ex);
        }
    }

    @Override
    public void d() {
        try {
            ai.resetTask();
        }
        catch (Exception ex) {
            log.error(String.format("Excpetion in AI class '%s' while executing method '%s'",
                    ai.getClass().getName(), ai.getClass().getName() + ".resetTask()"), ex);
        }
    }

    @Override
    public void e() {
        try {
            ai.updateTask();
        }
        catch (Exception ex) {
            log.error(String.format("Excpetion in AI class '%s' while executing method '%s'",
                    ai.getClass().getName(), ai.getClass().getName() + ".updateTask()"), ex);
        }
    }

    public AIBase getAIBase() {
        return this.ai;
    }

}
