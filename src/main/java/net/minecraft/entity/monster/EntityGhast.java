package net.minecraft.entity.monster;

import net.canarymod.api.entity.living.monster.CanaryGhast;
import net.canarymod.hook.entity.MobTargetHook;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityGhast extends EntityFlying implements IMob {

    public int h;
    public double i;
    public double bm;
    public double bn;
    public Entity bq; // CanaryMod: private => public; Target
    public int br; // CanaryMod: private => public; argoCoolDown
    public int bo;
    public int bp;
    private int bs = 1;

    public EntityGhast(World world) {
        super(world);
        this.a(4.0F, 4.0F);
        this.ae = true;
        this.b = 5;
        this.entity = new CanaryGhast(this); // CanaryMod: Wrap Entity
    }

    public boolean a(DamageSource damagesource, float f0) {
        if (this.aw()) {
            return false;
        }
        else if ("fireball".equals(damagesource.p()) && damagesource.j() instanceof EntityPlayer) {
            super.a(damagesource, 1000.0F);
            ((EntityPlayer) damagesource.j()).a((StatBase) AchievementList.z);
            return true;
        }
        else {
            return super.a(damagesource, f0);
        }
    }

    protected void c() {
        super.c();
        this.af.a(16, Byte.valueOf((byte) 0));
    }

    protected void aD() {
        super.aD();
        this.a(SharedMonsterAttributes.a).a(10.0D);
    }

    protected void bq() {
        if (!this.o.E && this.o.r == EnumDifficulty.PEACEFUL) {
            this.B();
        }

        this.w();
        this.bo = this.bp;
        double d0 = this.i - this.s;
        double d1 = this.bm - this.t;
        double d2 = this.bn - this.u;
        double d3 = d0 * d0 + d1 * d1 + d2 * d2;

        if (d3 < 1.0D || d3 > 3600.0D) {
            this.i = this.s + (double) ((this.Z.nextFloat() * 2.0F - 1.0F) * 16.0F);
            this.bm = this.t + (double) ((this.Z.nextFloat() * 2.0F - 1.0F) * 16.0F);
            this.bn = this.u + (double) ((this.Z.nextFloat() * 2.0F - 1.0F) * 16.0F);
        }

        if (this.h-- <= 0) {
            this.h += this.Z.nextInt(5) + 2;
            d3 = (double) MathHelper.a(d3);
            if (this.a(this.i, this.bm, this.bn, d3)) {
                this.v += d0 / d3 * 0.1D;
                this.w += d1 / d3 * 0.1D;
                this.x += d2 / d3 * 0.1D;
            }
            else {
                this.i = this.s;
                this.bm = this.t;
                this.bn = this.u;
            }
        }

        if (this.bq != null && this.bq.K) {
            this.bq = null;
        }

        if (this.bq == null || this.br-- <= 0) {
            // CanaryMod: MobTarget
            EntityPlayer entity = this.o.b(this, 100.0D);

            if (entity != null) {
                MobTargetHook hook = (MobTargetHook) new MobTargetHook((net.canarymod.api.entity.living.LivingBase) this.getCanaryEntity(), (net.canarymod.api.entity.living.LivingBase) entity.getCanaryEntity()).call();
                if (!hook.isCanceled()) {
                    this.bq = entity;
                }
            }
            //
            if (this.bq != null) {
                this.br = 20;
            }
        }

        double d4 = 64.0D;

        if (this.bq != null && this.bq.f((Entity) this) < d4 * d4) {
            double d5 = this.bq.s - this.s;
            double d6 = this.bq.C.b + (double) (this.bq.N / 2.0F) - (this.t + (double) (this.N / 2.0F));
            double d7 = this.bq.u - this.u;

            this.aM = this.y = -((float) Math.atan2(d5, d7)) * 180.0F / 3.1415927F;
            if (this.p(this.bq)) {
                if (this.bp == 10) {
                    this.o.a((EntityPlayer) null, 1007, (int) this.s, (int) this.t, (int) this.u, 0);
                }

                ++this.bp;
                if (this.bp == 20) {
                    this.o.a((EntityPlayer) null, 1008, (int) this.s, (int) this.t, (int) this.u, 0);
                    EntityLargeFireball entitylargefireball = new EntityLargeFireball(this.o, this, d5, d6, d7);

                    entitylargefireball.e = this.bs;
                    double d8 = 4.0D;
                    Vec3 vec3 = this.j(1.0F);

                    entitylargefireball.s = this.s + vec3.a * d8;
                    entitylargefireball.t = this.t + (double) (this.N / 2.0F) + 0.5D;
                    entitylargefireball.u = this.u + vec3.c * d8;
                    this.o.d((Entity) entitylargefireball);
                    this.bp = -40;
                }
            }
            else if (this.bp > 0) {
                --this.bp;
            }
        }
        else {
            this.aM = this.y = -((float) Math.atan2(this.v, this.x)) * 180.0F / 3.1415927F;
            if (this.bp > 0) {
                --this.bp;
            }
        }

        if (!this.o.E) {
            byte b0 = this.af.a(16);
            byte b1 = (byte) (this.bp > 10 ? 1 : 0);

            if (b0 != b1) {
                this.af.b(16, Byte.valueOf(b1));
            }
        }
    }

    private boolean a(double d0, double d1, double d2, double d3) {
        double d4 = (this.i - this.s) / d3;
        double d5 = (this.bm - this.t) / d3;
        double d6 = (this.bn - this.u) / d3;
        AxisAlignedBB axisalignedbb = this.C.b();

        for (int i0 = 1; (double) i0 < d3; ++i0) {
            axisalignedbb.d(d4, d5, d6);
            if (!this.o.a((Entity) this, axisalignedbb).isEmpty()) {
                return false;
            }
        }

        return true;
    }

    protected String t() {
        return "mob.ghast.moan";
    }

    protected String aT() {
        return "mob.ghast.scream";
    }

    protected String aU() {
        return "mob.ghast.death";
    }

    protected Item u() {
        return Items.H;
    }

    protected void b(boolean flag0, int i0) {
        int i1 = this.Z.nextInt(2) + this.Z.nextInt(1 + i0);

        int i2;

        for (i2 = 0; i2 < i1; ++i2) {
            this.a(Items.bk, 1);
        }

        i1 = this.Z.nextInt(3) + this.Z.nextInt(1 + i0);

        for (i2 = 0; i2 < i1; ++i2) {
            this.a(Items.H, 1);
        }
    }

    protected float bf() {
        return 10.0F;
    }

    public boolean by() {
        return this.Z.nextInt(20) == 0 && super.by() && this.o.r != EnumDifficulty.PEACEFUL;
    }

    public int bB() {
        return 1;
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("ExplosionPower", this.bs);
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        if (nbttagcompound.b("ExplosionPower", 99)) {
            this.bs = nbttagcompound.f("ExplosionPower");
        }
    }
}
