package net.minecraft.entity.ai;


import net.canarymod.api.ai.CanaryAILeapAtTarget;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.MathHelper;


public class EntityAILeapAtTarget extends EntityAIBase {

    EntityLiving a;
    EntityLivingBase b;
    float c;
   
    public EntityAILeapAtTarget(EntityLiving entityliving, float f0) {
        this.a = entityliving;
        this.c = f0;
        this.a(5);
        this.canaryAI = new CanaryAILeapAtTarget(this); //CanaryMod: set our variable
    }

    public boolean a() {
        this.b = this.a.u();
        if (this.b == null) {
            return false;
        } else {
            double d0 = this.a.h(this.b);

            return d0 >= 4.0D && d0 <= 16.0D ? (!this.a.C ? false : this.a.bb().nextInt(5) == 0) : false;
        }
    }

    public boolean b() {
        return !this.a.C;
    }

    public void c() {
        double d0 = this.b.s - this.a.s;
        double d1 = this.b.u - this.a.u;
        float f0 = MathHelper.a(d0 * d0 + d1 * d1);

        this.a.v += d0 / (double) f0 * 0.5D * 0.800000011920929D + this.a.v * 0.20000000298023224D;
        this.a.x += d1 / (double) f0 * 0.5D * 0.800000011920929D + this.a.x * 0.20000000298023224D;
        this.a.w = (double) this.c;
    }
}
