package net.canarymod.api.ai;

import net.minecraft.entity.ai.EntityAITasks;

import java.util.Iterator;
import net.canarymod.Canary;

/**
 * @author Somners
 */
public class CanaryAIManager implements AIManager {

    private EntityAITasks tasks;

    public CanaryAIManager(EntityAITasks tasks) {
        this.tasks = tasks;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeTask(Class<? extends AIBase> ai) {
        if (this.hasTask(ai)) {
            Iterator<?> it = tasks.b.iterator();
            while (it.hasNext()) {
                EntityAITasks.EntityAITaskEntry entry = (EntityAITasks.EntityAITaskEntry) it.next();
                if (entry.a instanceof EntityAICanary) {
                    if (ai.isAssignableFrom(((EntityAICanary) entry.a).getAIBase().getClass())) {
                        tasks.a(entry.a);
                        return true;
                    }
                }
                // Make sure its not null because we don't wrap ALL ai
                else if (entry.a.getCanaryAIBase() != null) {
                    if (ai.isAssignableFrom(entry.a.getCanaryAIBase().getClass())) {
                        tasks.a(entry.a);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasTask(Class<? extends AIBase> ai) {
        Iterator<?> it = tasks.b.iterator();
        while (it.hasNext()) {
            EntityAITasks.EntityAITaskEntry entry = (EntityAITasks.EntityAITaskEntry) it.next();
            if (entry.a instanceof EntityAICanary) {
                if (ai.isAssignableFrom(((EntityAICanary) entry.a).getAIBase().getClass())) {
                    return true;
                }
            }
            // Make sure its not null because we don't wrap ALL ai
            else if (entry.a.getCanaryAIBase() != null) {
                if (ai.isAssignableFrom(entry.a.getCanaryAIBase().getClass())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AIBase getTask(Class<? extends AIBase> ai) {
        Iterator<?> it = tasks.b.iterator();
        while (it.hasNext()) {
            EntityAITasks.EntityAITaskEntry entry = (EntityAITasks.EntityAITaskEntry) it.next();
            if (entry.a instanceof EntityAICanary) {
                if (((EntityAICanary) entry.a).getAIBase().getClass().isAssignableFrom(ai)) {
                    return ((EntityAICanary) entry.a).getAIBase();
                }
            }
            // Make sure its not null because we don't wrap ALL ai
            else if (entry.a.getCanaryAIBase() != null) {
                if (entry.a.getCanaryAIBase().getClass().isAssignableFrom(ai)) {
                    return entry.a.getCanaryAIBase();
                }
            }
        }
        return null;
    }

    @Override
    public boolean addTask(int priority, AIBase ai) {
        if (!this.hasTask(ai.getClass())) {
            if (ai instanceof CanaryAIBase) {
                tasks.a(priority, ((CanaryAIBase)ai).getHandle());
                return true;
            }
            else {
                        
                tasks.a(priority, new EntityAICanary(ai));
                return true;
            }
        }
        return false;
    }
}
