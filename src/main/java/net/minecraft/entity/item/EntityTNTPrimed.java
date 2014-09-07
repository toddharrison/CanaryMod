package net.minecraft.entity.item;

import net.canarymod.api.entity.CanaryTNTPrimed;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityTNTPrimed extends Entity {

    public int a;
    private EntityLivingBase b;

    public EntityTNTPrimed(World world) {
        super(world);
        this.k = true;
        this.a(0.98F, 0.98F);
        this.L = this.N / 2.0F;
        this.entity = new CanaryTNTPrimed(this); // CanaryMod: Wrap Entity
    }

    public EntityTNTPrimed(World world, double d0, double d1, double d2, EntityLivingBase entitylivingbase) {
        this(world);
        this.b(d0, d1, d2);
        float f0 = (float) (Math.random() * 3.1415927410125732D * 2.0D);

        this.v = (double) (-((float) Math.sin((double) f0)) * 0.02F);
        this.w = 0.20000000298023224D;
        this.x = (double) (-((float) Math.cos((double) f0)) * 0.02F);
        this.a = 80;
        this.p = d0;
        this.q = d1;
        this.r = d2;
        this.b = entitylivingbase;
    }

    protected void c() {
    }

    protected boolean g_() {
        return false;
    }

    public boolean R() {
        return !this.K;
    }

    public void h() {
        this.p = this.s;
        this.q = this.t;
        this.r = this.u;
        this.w -= 0.03999999910593033D;
        this.d(this.v, this.w, this.x);
        this.v *= 0.9800000190734863D;
        this.w *= 0.9800000190734863D;
        this.x *= 0.9800000190734863D;
        if (this.D) {
            this.v *= 0.699999988079071D;
            this.x *= 0.699999988079071D;
            this.w *= -0.5D;
        }

        if (this.a-- <= 0) {
            this.B();
            if (!this.o.E) {
                this.f();
            }
        }
        else {
            this.o.a("smoke", this.s, this.t + 0.5D, this.u, 0.0D, 0.0D, 0.0D);
        }
    }

    private void f() {
        // float f0 = 4.0F;

        this.o.a(this, this.s, this.t, this.u, ((CanaryTNTPrimed) entity).getPower(), true); // CanaryMod: get power level
    }

    protected void b(NBTTagCompound nbttagcompound) {
        nbttagcompound.a("Fuse", (byte) this.a);
    }

    protected void a(NBTTagCompound nbttagcompound) {
        this.a = nbttagcompound.d("Fuse");
    }

    public EntityLivingBase e() {
        return this.b;
    }
}
