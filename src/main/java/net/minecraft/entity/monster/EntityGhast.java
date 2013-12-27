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
    public double j;
    public double bn;
    public Entity bq; // CanaryMod: private => public; Target
    public int br; // CanaryMod: private => public; argoCoolDown
    public int bo;
    public int bp;
    private int bs = 1;

    public EntityGhast(World world) {
        super(world);
        this.a(4.0F, 4.0F);
        this.af = true;
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
        this.ag.a(16, Byte.valueOf((byte) 0));
    }

    protected void aD() {
        super.aD();
        this.a(SharedMonsterAttributes.a).a(10.0D);
    }

    protected void bq() {
        if (!this.p.E && this.p.r == EnumDifficulty.PEACEFUL) {
            this.B();
        }

        this.w();
        this.bo = this.bp;
        double d0 = this.i - this.t;
        double d1 = this.j - this.u;
        double d2 = this.bn - this.v;
        double d3 = d0 * d0 + d1 * d1 + d2 * d2;

        if (d3 < 1.0D || d3 > 3600.0D) {
            this.i = this.t + (double) ((this.aa.nextFloat() * 2.0F - 1.0F) * 16.0F);
            this.j = this.u + (double) ((this.aa.nextFloat() * 2.0F - 1.0F) * 16.0F);
            this.bn = this.v + (double) ((this.aa.nextFloat() * 2.0F - 1.0F) * 16.0F);
        }

        if (this.h-- <= 0) {
            this.h += this.aa.nextInt(5) + 2;
            d3 = (double) MathHelper.a(d3);
            if (this.a(this.i, this.j, this.bn, d3)) {
                this.w += d0 / d3 * 0.1D;
                this.x += d1 / d3 * 0.1D;
                this.y += d2 / d3 * 0.1D;
            }
            else {
                this.i = this.t;
                this.j = this.u;
                this.bn = this.v;
            }
        }

        if (this.bq != null && this.bq.L) {
            this.bq = null;
        }

        if (this.bq == null || this.br-- <= 0) {
            // CanaryMod: MobTarget
            EntityPlayer entity = this.p.b(this, 100.0D);

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

        if (this.bq != null && this.bq.e((Entity) this) < d4 * d4) {
            double d5 = this.bq.t - this.t;
            double d6 = this.bq.D.b + (double) (this.bq.O / 2.0F) - (this.u + (double) (this.O / 2.0F));
            double d7 = this.bq.v - this.v;

            this.aN = this.z = -((float) Math.atan2(d5, d7)) * 180.0F / 3.1415927F;
            if (this.o(this.bq)) {
                if (this.bp == 10) {
                    this.p.a((EntityPlayer) null, 1007, (int) this.t, (int) this.u, (int) this.v, 0);
                }

                ++this.bp;
                if (this.bp == 20) {
                    this.p.a((EntityPlayer) null, 1008, (int) this.t, (int) this.u, (int) this.v, 0);
                    EntityLargeFireball entitylargefireball = new EntityLargeFireball(this.p, this, d5, d6, d7);

                    entitylargefireball.e = this.bs;
                    double d8 = 4.0D;
                    Vec3 vec3 = this.j(1.0F);

                    entitylargefireball.t = this.t + vec3.c * d8;
                    entitylargefireball.u = this.u + (double) (this.O / 2.0F) + 0.5D;
                    entitylargefireball.v = this.v + vec3.e * d8;
                    this.p.d((Entity) entitylargefireball);
                    this.bp = -40;
                }
            }
            else if (this.bp > 0) {
                --this.bp;
            }
        }
        else {
            this.aN = this.z = -((float) Math.atan2(this.w, this.y)) * 180.0F / 3.1415927F;
            if (this.bp > 0) {
                --this.bp;
            }
        }

        if (!this.p.E) {
            byte b0 = this.ag.a(16);
            byte b1 = (byte) (this.bp > 10 ? 1 : 0);

            if (b0 != b1) {
                this.ag.b(16, Byte.valueOf(b1));
            }
        }
    }

    private boolean a(double d0, double d1, double d2, double d3) {
        double d4 = (this.i - this.t) / d3;
        double d5 = (this.j - this.u) / d3;
        double d6 = (this.bn - this.v) / d3;
        AxisAlignedBB axisalignedbb = this.D.c();

        for (int i0 = 1; (double) i0 < d3; ++i0) {
            axisalignedbb.d(d4, d5, d6);
            if (!this.p.a((Entity) this, axisalignedbb).isEmpty()) {
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
        int i1 = this.aa.nextInt(2) + this.aa.nextInt(1 + i0);

        int i2;

        for (i2 = 0; i2 < i1; ++i2) {
            this.a(Items.bk, 1);
        }

        i1 = this.aa.nextInt(3) + this.aa.nextInt(1 + i0);

        for (i2 = 0; i2 < i1; ++i2) {
            this.a(Items.H, 1);
        }
    }

    protected float bf() {
        return 10.0F;
    }

    public boolean bw() {
        return this.aa.nextInt(20) == 0 && super.bw() && this.p.r != EnumDifficulty.PEACEFUL;
    }

    public int bz() {
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
