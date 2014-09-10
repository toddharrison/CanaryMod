package net.minecraft.entity.item;

import net.canarymod.api.entity.CanaryXPOrb;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityXPOrb extends Entity {

    public int a;
    public int b;
    public int c;
    public int d = 5; // CanaryMod: private => public; health
    private int e;
    private EntityPlayer f;
    private int g;

    public EntityXPOrb(World world, double d0, double d1, double d2, int i0) {
        super(world);
        this.a(0.5F, 0.5F);
        this.L = this.N / 2.0F;
        this.b(d0, d1, d2);
        this.y = (float) (Math.random() * 360.0D);
        this.v = (double) ((float) (Math.random() * 0.20000000298023224D - 0.10000000149011612D) * 2.0F);
        this.w = (double) ((float) (Math.random() * 0.2D) * 2.0F);
        this.x = (double) ((float) (Math.random() * 0.20000000298023224D - 0.10000000149011612D) * 2.0F);
        this.e = i0;
        this.entity = new CanaryXPOrb(this); // CanaryMod: Wrap Entity
    }

    protected boolean g_() {
        return false;
    }

    public EntityXPOrb(World world) {
        super(world);
        this.a(0.25F, 0.25F);
        this.L = this.N / 2.0F;
        this.entity = new CanaryXPOrb(this); // CanaryMod: Wrap Entity
    }

    protected void c() {
    }

    public void h() {
        super.h();
        if (this.c > 0) {
            --this.c;
        }

        this.p = this.s;
        this.q = this.t;
        this.r = this.u;
        this.w -= 0.029999999329447746D;
        if (this.o.a(MathHelper.c(this.s), MathHelper.c(this.t), MathHelper.c(this.u)).o() == Material.i) {
            this.w = 0.20000000298023224D;
            this.v = (double) ((this.Z.nextFloat() - this.Z.nextFloat()) * 0.2F);
            this.x = (double) ((this.Z.nextFloat() - this.Z.nextFloat()) * 0.2F);
            this.a("random.fizz", 0.4F, 2.0F + this.Z.nextFloat() * 0.4F);
        }

        this.j(this.s, (this.C.b + this.C.e) / 2.0D, this.u);
        double d0 = 8.0D;

        if (this.g < this.a - 20 + this.y() % 100) {
            if (this.f == null || this.f.f(this) > d0 * d0) {
                this.f = this.o.a(this, d0);
            }

            this.g = this.a;
        }

        if (this.f != null) {
            double d1 = (this.f.s - this.s) / d0;
            double d2 = (this.f.t + (double) this.f.g() - this.t) / d0;
            double d3 = (this.f.u - this.u) / d0;
            double d4 = Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
            double d5 = 1.0D - d4;

            if (d5 > 0.0D) {
                d5 *= d5;
                this.v += d1 / d4 * d5 * 0.1D;
                this.w += d2 / d4 * d5 * 0.1D;
                this.x += d3 / d4 * d5 * 0.1D;
            }
        }

        this.d(this.v, this.w, this.x);
        float f0 = 0.98F;

        if (this.D) {
            f0 = this.o.a(MathHelper.c(this.s), MathHelper.c(this.C.b) - 1, MathHelper.c(this.u)).K * 0.98F;
        }

        this.v *= (double) f0;
        this.w *= 0.9800000190734863D;
        this.x *= (double) f0;
        if (this.D) {
            this.w *= -0.8999999761581421D;
        }

        ++this.a;
        ++this.b;
        if (this.b >= 6000) {
            this.B();
        }
    }

    public boolean N() {
        return this.o.a(this.C, Material.h, (Entity) this);
    }

    protected void f(int i0) {
        this.a(DamageSource.a, (float) i0);
    }

    public boolean a(DamageSource damagesource, float f0) {
        if (this.aw()) {
            return false;
        } else {
            this.Q();
            this.d = (int) ((float) this.d - f0);
            if (this.d <= 0) {
                this.B();
            }

            return false;
        }
    }

    public void b(NBTTagCompound nbttagcompound) {
        nbttagcompound.a("Health", (short) ((byte) this.d));
        nbttagcompound.a("Age", (short) this.b);
        nbttagcompound.a("Value", (short) this.e);
    }

    public void a(NBTTagCompound nbttagcompound) {
        this.d = nbttagcompound.e("Health") & 255;
        this.b = nbttagcompound.e("Age");
        this.e = nbttagcompound.e("Value");
    }

    public void b_(EntityPlayer entityplayer) {
        if (!this.o.E) {
            if (this.c == 0 && entityplayer.bt == 0) {
                entityplayer.bt = 2;
                this.o.a((Entity) entityplayer, "random.orb", 0.1F, 0.5F * ((this.Z.nextFloat() - this.Z.nextFloat()) * 0.7F + 1.8F));
                entityplayer.a((Entity) this, 1);
                entityplayer.v(this.e);
                this.B();
            }
        }
    }

    public int e() {
        return this.e;
    }

    public static int a(int i0) {
        return i0 >= 2477 ? 2477 : (i0 >= 1237 ? 1237 : (i0 >= 617 ? 617 : (i0 >= 307 ? 307 : (i0 >= 149 ? 149 : (i0 >= 73 ? 73 : (i0 >= 37 ? 37 : (i0 >= 17 ? 17 : (i0 >= 7 ? 7 : (i0 >= 3 ? 3 : 1)))))))));
    }

    public boolean av() {
        return false;
    }

    // CanaryMod
    public void setXPValue(int value) {
        this.e = value;
    }

    public EntityPlayer getClosestPlayer() {
        return this.f;
    }
    //
}
