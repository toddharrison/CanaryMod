package net.minecraft.entity.ai;

import net.canarymod.api.ai.CanaryAIManager;
import net.minecraft.profiler.Profiler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EntityAITasks {

    private static final Logger a = LogManager.getLogger();
    public List b = new ArrayList(); // CanaryMod: private -> public
    public List c = new ArrayList(); // CanaryMod: private -> public
    private final Profiler d;
    private int e;
    private int f = 3;

    private CanaryAIManager manager = new CanaryAIManager(this);

    public EntityAITasks(Profiler profiler) {
        this.d = profiler;
    }

    public void a(int i0, EntityAIBase entityaibase) {
        this.b.add(new EntityAITaskEntry(i0, entityaibase));
    }

    public void a(EntityAIBase entityaibase) {
        Iterator iterator = this.b.iterator();

        while (iterator.hasNext()) {
            EntityAITaskEntry entityaitasks_entityaitaskentry = (EntityAITaskEntry) iterator.next();
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
        ArrayList arraylist = new ArrayList();
        Iterator iterator;
        EntityAITaskEntry entityaitasks_entityaitaskentry;

        if (this.e++ % this.f == 0) {
            iterator = this.b.iterator();

            while (iterator.hasNext()) {
                entityaitasks_entityaitaskentry = (EntityAITaskEntry) iterator.next();
                boolean flag0 = this.c.contains(entityaitasks_entityaitaskentry);

                if (flag0) {
                    if (this.b(entityaitasks_entityaitaskentry) && this.a(entityaitasks_entityaitaskentry)) {
                        continue;
                    }

                    entityaitasks_entityaitaskentry.a.d();
                    this.c.remove(entityaitasks_entityaitaskentry);
                }

                if (this.b(entityaitasks_entityaitaskentry) && entityaitasks_entityaitaskentry.a.a()) {
                    arraylist.add(entityaitasks_entityaitaskentry);
                    this.c.add(entityaitasks_entityaitaskentry);
                }
            }
        }
        else {
            iterator = this.c.iterator();

            while (iterator.hasNext()) {
                entityaitasks_entityaitaskentry = (EntityAITaskEntry) iterator.next();
                if (!entityaitasks_entityaitaskentry.a.b()) {
                    entityaitasks_entityaitaskentry.a.d();
                    iterator.remove();
                }
            }
        }

        this.d.a("goalStart");
        iterator = arraylist.iterator();

        while (iterator.hasNext()) {
            entityaitasks_entityaitaskentry = (EntityAITaskEntry) iterator.next();
            this.d.a(entityaitasks_entityaitaskentry.a.getClass().getSimpleName());
            entityaitasks_entityaitaskentry.a.c();
            this.d.b();
        }

        this.d.b();
        this.d.a("goalTick");
        iterator = this.c.iterator();

        while (iterator.hasNext()) {
            entityaitasks_entityaitaskentry = (EntityAITaskEntry) iterator.next();
            entityaitasks_entityaitaskentry.a.e();
        }

        this.d.b();
    }

    private boolean a(EntityAITaskEntry entityaitasks_entityaitaskentry) {
        this.d.a("canContinue");
        boolean flag0 = entityaitasks_entityaitaskentry.a.b();

        this.d.b();
        return flag0;
    }

    private boolean b(EntityAITaskEntry entityaitasks_entityaitaskentry) {
        this.d.a("canUse");
        Iterator iterator = this.b.iterator();

        while (iterator.hasNext()) {
            EntityAITaskEntry entityaitasks_entityaitaskentry1 = (EntityAITaskEntry) iterator.next();

            if (entityaitasks_entityaitaskentry1 != entityaitasks_entityaitaskentry) {
                if (entityaitasks_entityaitaskentry.b >= entityaitasks_entityaitaskentry1.b) {
                    if (this.c.contains(entityaitasks_entityaitaskentry1) && !this.a(entityaitasks_entityaitaskentry, entityaitasks_entityaitaskentry1)) {
                        this.d.b();
                        return false;
                    }
                }
                else if (this.c.contains(entityaitasks_entityaitaskentry1) && !entityaitasks_entityaitaskentry1.a.i()) {
                    this.d.b();
                    return false;
                }
            }
        }

        this.d.b();
        return true;
    }

    private boolean a(EntityAITaskEntry entityaitasks_entityaitaskentry, EntityAITaskEntry entityaitasks_entityaitaskentry1) {
        return (entityaitasks_entityaitaskentry.a.j() & entityaitasks_entityaitaskentry1.a.j()) == 0;
    }

    public class EntityAITaskEntry { // CanaryMod: package => public

        public EntityAIBase a;
        public int b;

        public EntityAITaskEntry(int i0, EntityAIBase entityaibase) {
            this.b = i0;
            this.a = entityaibase;
        }
    }

    public CanaryAIManager getAIManager() {
        return this.manager;
    }
}
