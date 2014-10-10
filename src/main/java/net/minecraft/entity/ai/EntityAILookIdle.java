package net.minecraft.entity.ai;


import net.canarymod.api.ai.CanaryAILookIdle;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;


public class EntityAILookIdle extends EntityAIBase {

    private EntityLiving a;
    private double b;
    private double c;
    private int d;
   
    public EntityAILookIdle(EntityLiving entityliving) {
        this.a = entityliving;
        this.a(3);
        this.canaryAI = new CanaryAILookIdle(this); //CanaryMod: set our variable
    }

    public boolean a() {
        return this.a.bb().nextFloat() < 0.02F;
    }

    public boolean b() {
        return this.d >= 0;
    }

    public void c() {
        double d0 = 6.283185307179586D * this.a.bb().nextDouble();

        this.b = Math.cos(d0);
        this.c = Math.sin(d0);
        this.d = 20 + this.a.bb().nextInt(20);
    }

    public void e() {
        --this.d;
        this.a.p().a(this.a.s + this.b, this.a.t + (double) this.a.aR(), this.a.u + this.c, 10.0F, (float) this.a.bP());
    }
}
