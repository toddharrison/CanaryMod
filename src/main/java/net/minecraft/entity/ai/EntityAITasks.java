package net.minecraft.entity.ai;

import net.canarymod.api.ai.CanaryAIManager;
import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.profiler.Profiler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EntityAITasks {

    private static final Logger a = LogManager.getLogger();
    public List b = Lists.newArrayList(); // CanaryMod: private -> public
    public List c = Lists.newArrayList(); // CanaryMod: private -> public
    private final Profiler d;
    private int e;
    private int f = 3;
    // CanaryMod: our variables
    private CanaryAIManager manager = new CanaryAIManager(this);

    public EntityAITasks(Profiler profiler) {
        this.d = profiler;
    }

    public void a(int i0, EntityAIBase entityaibase) {
        this.b.add(new EntityAITasks.EntityAITaskEntry(i0, entityaibase));
    }

    public void a(EntityAIBase entityaibase) {
        Iterator iterator = this.b.iterator();

        while (iterator.hasNext()) {
            EntityAITasks.EntityAITaskEntry entityaitasks_entityaitaskentry = (EntityAITasks.EntityAITaskEntry) iterator.next();
            EntityAIBase entityaibase1 = entityaitasks_entityaitaskentry.a;

            if (entityaibase1 == entityaibase) {
                if (this.c.contains(entityaitasks_entityaitaskentry)) {
                    entityaibase1.d();
                    this.c.remove(entityaitasks_entityaitaskentry);
                }

                iterator.remove();
            }
        }

    }

    public void a() {
        this.d.a("goalSetup");
        Iterator iterator;
        EntityAITasks.EntityAITaskEntry entityaitasks_entityaitaskentry;

        if (this.e++ % this.f == 0) {
            iterator = this.b.iterator();

            while (iterator.hasNext()) {
                entityaitasks_entityaitaskentry = (EntityAITasks.EntityAITaskEntry) iterator.next();
                boolean flag0 = this.c.contains(entityaitasks_entityaitaskentry);

                if (flag0) {
                    if (this.b(entityaitasks_entityaitaskentry) && this.a(entityaitasks_entityaitaskentry)) {
                        continue;
                    }

                    entityaitasks_entityaitaskentry.a.d();
                    this.c.remove(entityaitasks_entityaitaskentry);
                }

                if (this.b(entityaitasks_entityaitaskentry) && entityaitasks_entityaitaskentry.a.a()) {
                    entityaitasks_entityaitaskentry.a.c();
                    this.c.add(entityaitasks_entityaitaskentry);
                }
            }
        } else {
            iterator = this.c.iterator();

            while (iterator.hasNext()) {
                entityaitasks_entityaitaskentry = (EntityAITasks.EntityAITaskEntry) iterator.next();
                if (!this.a(entityaitasks_entityaitaskentry)) {
                    entityaitasks_entityaitaskentry.a.d();
                    iterator.remove();
                }
            }
        }

        this.d.b();
        this.d.a("goalTick");
        iterator = this.c.iterator();

        while (iterator.hasNext()) {
            entityaitasks_entityaitaskentry = (EntityAITasks.EntityAITaskEntry) iterator.next();
            entityaitasks_entityaitaskentry.a.e();
        }

        this.d.b();
    }

    private boolean a(EntityAITasks.EntityAITaskEntry entityaitasks_entityaitaskentry) {
        boolean flag0 = entityaitasks_entityaitaskentry.a.b();

        return flag0;
    }

    private boolean b(EntityAITasks.EntityAITaskEntry entityaitasks_entityaitaskentry) {
        Iterator iterator = this.b.iterator();

        while (iterator.hasNext()) {
            EntityAITasks.EntityAITaskEntry entityaitasks_entityaitaskentry1 = (EntityAITasks.EntityAITaskEntry) iterator.next();

            if (entityaitasks_entityaitaskentry1 != entityaitasks_entityaitaskentry) {
                if (entityaitasks_entityaitaskentry.b >= entityaitasks_entityaitaskentry1.b) {
                    if (!this.a(entityaitasks_entityaitaskentry, entityaitasks_entityaitaskentry1) && this.c.contains(entityaitasks_entityaitaskentry1)) {
                        return false;
                    }
                } else if (!entityaitasks_entityaitaskentry1.a.i() && this.c.contains(entityaitasks_entityaitaskentry1)) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean a(EntityAITasks.EntityAITaskEntry entityaitasks_entityaitaskentry, EntityAITasks.EntityAITaskEntry entityaitasks_entityaitaskentry1) {
        return (entityaitasks_entityaitaskentry.a.j() & entityaitasks_entityaitaskentry1.a.j()) == 0;
    }

    class EntityAITaskEntry {

        public EntityAIBase a;
        public int b;
      
        public EntityAITaskEntry(int p_i1627_2_, EntityAIBase p_i1627_3_) {
            this.b = p_i1627_2_;
            this.a = p_i1627_3_;
        }
    }

    public CanaryAIManager getAIManager() {
        return this.manager;
    }
}
