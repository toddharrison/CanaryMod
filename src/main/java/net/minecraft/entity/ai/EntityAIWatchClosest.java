package net.minecraft.entity.ai;


import net.canarymod.api.ai.CanaryAIWatchClosest;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;


public class EntityAIWatchClosest extends EntityAIBase {

    protected EntityLiving a;
    protected Entity b;
    protected float c;
    private int e;
    private float f;
    protected Class d;
   
    public EntityAIWatchClosest(EntityLiving entityliving, Class oclass0, float f0) {
        this.a = entityliving;
        this.d = oclass0;
        this.c = f0;
        this.f = 0.02F;
        this.a(2);
        this.canaryAI = new CanaryAIWatchClosest(this); //CanaryMod: set our variable
    }

    public EntityAIWatchClosest(EntityLiving entityliving, Class oclass0, float f0, float f1) {
        this.a = entityliving;
        this.d = oclass0;
        this.c = f0;
        this.f = f1;
        this.a(2);
        this.canaryAI = new CanaryAIWatchClosest(this); //CanaryMod: set our variable
    }

    public boolean a() {
        if (this.a.bb().nextFloat() >= this.f) {
            return false;
        } else {
            if (this.a.u() != null) {
                this.b = this.a.u();
            }

            if (this.d == EntityPlayer.class) {
                this.b = this.a.o.a(this.a, (double) this.c);
            } else {
                this.b = this.a.o.a(this.d, this.a.aQ().b((double) this.c, 3.0D, (double) this.c), (Entity) this.a);
            }

            return this.b != null;
        }
    }

    public boolean b() {
        return !this.b.ai() ? false : (this.a.h(this.b) > (double) (this.c * this.c) ? false : this.e > 0);
    }

    public void c() {
        this.e = 40 + this.a.bb().nextInt(40);
    }

    public void d() {
        this.b = null;
    }

    public void e() {
        this.a.p().a(this.b.s, this.b.t + (double) this.b.aR(), this.b.u, 10.0F, (float) this.a.bP());
        --this.e;
    }
}
