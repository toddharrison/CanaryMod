package net.minecraft.entity.ai;


import java.util.Iterator;
import java.util.List;
import net.canarymod.api.ai.CanaryAIPlay;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.util.Vec3;


public class EntityAIPlay extends EntityAIBase {

    private EntityVillager a;
    private EntityLivingBase b;
    private double c;
    private int d;
   
    public EntityAIPlay(EntityVillager entityvillager, double d0) {
        this.a = entityvillager;
        this.c = d0;
        this.a(1);
        this.canaryAI = new CanaryAIPlay(this); //CanaryMod: set our variable
    }

    public boolean a() {
        if (this.a.l() >= 0) {
            return false;
        } else if (this.a.bb().nextInt(400) != 0) {
            return false;
        } else {
            List list = this.a.o.a(EntityVillager.class, this.a.aQ().b(6.0D, 3.0D, 6.0D));
            double d0 = Double.MAX_VALUE;
            Iterator iterator = list.iterator();

            while (iterator.hasNext()) {
                EntityVillager entityvillager = (EntityVillager) iterator.next();

                if (entityvillager != this.a && !entityvillager.cl() && entityvillager.l() < 0) {
                    double d1 = entityvillager.h(this.a);

                    if (d1 <= d0) {
                        d0 = d1;
                        this.b = entityvillager;
                    }
                }
            }

            if (this.b == null) {
                Vec3 vec3 = RandomPositionGenerator.a(this.a, 16, 3);

                if (vec3 == null) {
                    return false;
                }
            }

            return true;
        }
    }

    public boolean b() {
        return this.d > 0;
    }

    public void c() {
        if (this.b != null) {
            this.a.m(true);
        }

        this.d = 1000;
    }

    public void d() {
        this.a.m(false);
        this.b = null;
    }

    public void e() {
        --this.d;
        if (this.b != null) {
            if (this.a.h(this.b) > 4.0D) {
                this.a.s().a((Entity) this.b, this.c);
            }
        } else if (this.a.s().m()) {
            Vec3 vec3 = RandomPositionGenerator.a(this.a, 16, 3);

            if (vec3 == null) {
                return;
            }

            this.a.s().a(vec3.a, vec3.b, vec3.c, this.c);
        }

    }
}
