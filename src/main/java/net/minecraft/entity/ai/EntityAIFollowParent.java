package net.minecraft.entity.ai;


import java.util.Iterator;
import java.util.List;
import net.canarymod.api.ai.CanaryAIFollowParent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.EntityAnimal;


public class EntityAIFollowParent extends EntityAIBase {

    EntityAnimal a;
    EntityAnimal b;
    double c;
    private int d;
   
    public EntityAIFollowParent(EntityAnimal entityanimal, double d0) {
        this.a = entityanimal;
        this.c = d0;
        this.canaryAI = new CanaryAIFollowParent(this); //CanaryMod: set our variable
    }

    public boolean a() {
        if (this.a.l() >= 0) {
            return false;
        } else {
            List list = this.a.o.a(this.a.getClass(), this.a.aQ().b(8.0D, 4.0D, 8.0D));
            EntityAnimal entityanimal = null;
            double d0 = Double.MAX_VALUE;
            Iterator iterator = list.iterator();

            while (iterator.hasNext()) {
                EntityAnimal entityanimal1 = (EntityAnimal) iterator.next();

                if (entityanimal1.l() >= 0) {
                    double d1 = this.a.h(entityanimal1);

                    if (d1 <= d0) {
                        d0 = d1;
                        entityanimal = entityanimal1;
                    }
                }
            }

            if (entityanimal == null) {
                return false;
            } else if (d0 < 9.0D) {
                return false;
            } else {
                this.b = entityanimal;
                return true;
            }
        }
    }

    public boolean b() {
        if (this.a.l() >= 0) {
            return false;
        } else if (!this.b.ai()) {
            return false;
        } else {
            double d0 = this.a.h(this.b);

            return d0 >= 9.0D && d0 <= 256.0D;
        }
    }

    public void c() {
        this.d = 0;
    }

    public void d() {
        this.b = null;
    }

    public void e() {
        if (--this.d <= 0) {
            this.d = 10;
            this.a.s().a((Entity) this.b, this.c);
        }
    }
}
