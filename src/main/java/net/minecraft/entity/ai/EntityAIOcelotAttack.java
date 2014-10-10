package net.minecraft.entity.ai;


import net.canarymod.api.ai.CanaryAIOcelotAttack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.World;


public class EntityAIOcelotAttack extends EntityAIBase {

    World a;
    EntityLiving b;
    EntityLivingBase c;
    int d;
   
    public EntityAIOcelotAttack(EntityLiving entityliving) {
        this.b = entityliving;
        this.a = entityliving.o;
        this.a(3);
        this.canaryAI = new CanaryAIOcelotAttack(this); //CanaryMod: set our variable
    }

    public boolean a() {
        EntityLivingBase entitylivingbase = this.b.u();

        if (entitylivingbase == null) {
            return false;
        } else {
            this.c = entitylivingbase;
            return true;
        }
    }

    public boolean b() {
        return !this.c.ai() ? false : (this.b.h(this.c) > 225.0D ? false : !this.b.s().m() || this.a());
    }

    public void d() {
        this.c = null;
        this.b.s().n();
    }

    public void e() {
        this.b.p().a(this.c, 30.0F, 30.0F);
        double d0 = (double) (this.b.J * 2.0F * this.b.J * 2.0F);
        double d1 = this.b.e(this.c.s, this.c.aQ().b, this.c.u);
        double d2 = 0.8D;

        if (d1 > d0 && d1 < 16.0D) {
            d2 = 1.33D;
        } else if (d1 < 225.0D) {
            d2 = 0.6D;
        }

        this.b.s().a((Entity) this.c, d2);
        this.d = Math.max(this.d - 1, 0);
        if (d1 <= d0) {
            if (this.d <= 0) {
                this.d = 20;
                this.b.r(this.c);
            }
        }
    }
}
