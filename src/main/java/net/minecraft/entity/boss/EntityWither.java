package net.minecraft.entity.boss;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import net.canarymod.api.entity.living.monster.CanaryWither;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

public class EntityWither extends EntityMob implements IRangedAttackMob {

    private static final Predicate bp = new Predicate() {

        public boolean a(Entity p_a_1_) {
            return p_a_1_ instanceof EntityLivingBase && ((EntityLivingBase) p_a_1_).by() != EnumCreatureAttribute.UNDEAD;
        }

        public boolean apply(Object p_apply_1_) {
            return this.a((Entity) p_apply_1_);
        }
    };
    private float[] b = new float[2];
    private float[] c = new float[2];
    private float[] bk = new float[2];
    private float[] bl = new float[2];
    private int[] bm = new int[2];
    private int[] bn = new int[2];
    private int bo;

    public EntityWither(World world) {
        super(world);
        this.h(this.bt());
        this.a(0.9F, 3.5F);
        this.ab = true;
        ((PathNavigateGround) this.s()).d(true);
        this.i.a(0, new EntityAISwimming(this));
        this.i.a(2, new EntityAIArrowAttack(this, 1.0D, 40, 20.0F));
        this.i.a(5, new EntityAIWander(this, 1.0D));
        this.i.a(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.i.a(7, new EntityAILookIdle(this));
        this.bg.a(1, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.bg.a(2, new EntityAINearestAttackableTarget(this, EntityLiving.class, 0, false, false, bp));
        this.b_ = 50;
        this.entity = new CanaryWither(this); // CanaryMod: Wrap Entity
    }

    protected void h() {
        super.h();
        this.ac.a(17, new Integer(0));
        this.ac.a(18, new Integer(0));
        this.ac.a(19, new Integer(0));
        this.ac.a(20, new Integer(0));
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("Invul", this.cj());
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.r(nbttagcompound.f("Invul"));
    }

    protected String z() {
        return "mob.wither.idle";
    }

    protected String bn() {
        return "mob.wither.hurt";
    }

    protected String bo() {
        return "mob.wither.death";
    }

    public void m() {
        this.w *= 0.6000000238418579D;
        double d0;
        double d1;
        double d2;

        if (!this.o.D && this.s(0) > 0) {
            Entity entity = this.o.a(this.s(0));

            if (entity != null) {
                if (this.t < entity.t || !this.ck() && this.t < entity.t + 5.0D) {
                    if (this.w < 0.0D) {
                        this.w = 0.0D;
                    }

                    this.w += (0.5D - this.w) * 0.6000000238418579D;
                }

                double d3 = entity.s - this.s;

                d0 = entity.u - this.u;
                d1 = d3 * d3 + d0 * d0;
                if (d1 > 9.0D) {
                    d2 = (double) MathHelper.a(d1);
                    this.v += (d3 / d2 * 0.5D - this.v) * 0.6000000238418579D;
                    this.x += (d0 / d2 * 0.5D - this.x) * 0.6000000238418579D;
                }
            }
        }

        if (this.v * this.v + this.x * this.x > 0.05000000074505806D) {
            this.y = (float) Math.atan2(this.x, this.v) * 57.295776F - 90.0F;
        }

        super.m();

        int i0;

        for (i0 = 0; i0 < 2; ++i0) {
            this.bl[i0] = this.c[i0];
            this.bk[i0] = this.b[i0];
        }

        int i1;

        for (i0 = 0; i0 < 2; ++i0) {
            i1 = this.s(i0 + 1);
            Entity entity1 = null;

            if (i1 > 0) {
                entity1 = this.o.a(i1);
            }

            if (entity1 != null) {
                d0 = this.t(i0 + 1);
                d1 = this.u(i0 + 1);
                d2 = this.v(i0 + 1);
                double d4 = entity1.s - d0;
                double d5 = entity1.t + (double) entity1.aR() - d1;
                double d6 = entity1.u - d2;
                double d7 = (double) MathHelper.a(d4 * d4 + d6 * d6);
                float f0 = (float) (Math.atan2(d6, d4) * 180.0D / 3.1415927410125732D) - 90.0F;
                float f1 = (float) (-(Math.atan2(d5, d7) * 180.0D / 3.1415927410125732D));

                this.b[i0] = this.b(this.b[i0], f1, 40.0F);
                this.c[i0] = this.b(this.c[i0], f0, 10.0F);
            }
            else {
                this.c[i0] = this.b(this.c[i0], this.aG, 10.0F);
            }
        }

        boolean flag0 = this.ck();

        for (i1 = 0; i1 < 3; ++i1) {
            double d8 = this.t(i1);
            double d9 = this.u(i1);
            double d10 = this.v(i1);

            this.o.a(EnumParticleTypes.SMOKE_NORMAL, d8 + this.V.nextGaussian() * 0.30000001192092896D, d9 + this.V.nextGaussian() * 0.30000001192092896D, d10 + this.V.nextGaussian() * 0.30000001192092896D, 0.0D, 0.0D, 0.0D, new int[0]);
            if (flag0 && this.o.s.nextInt(4) == 0) {
                this.o.a(EnumParticleTypes.SPELL_MOB, d8 + this.V.nextGaussian() * 0.30000001192092896D, d9 + this.V.nextGaussian() * 0.30000001192092896D, d10 + this.V.nextGaussian() * 0.30000001192092896D, 0.699999988079071D, 0.699999988079071D, 0.5D, new int[0]);
            }
        }

        if (this.cj() > 0) {
            for (i1 = 0; i1 < 3; ++i1) {
                this.o.a(EnumParticleTypes.SPELL_MOB, this.s + this.V.nextGaussian() * 1.0D, this.t + (double) (this.V.nextFloat() * 3.3F), this.u + this.V.nextGaussian() * 1.0D, 0.699999988079071D, 0.699999988079071D, 0.8999999761581421D, new int[0]);
            }
        }

    }

    protected void E() {
        int i0;

        if (this.cj() > 0) {
            i0 = this.cj() - 1;
            if (i0 <= 0) {
                this.o.a(this, this.s, this.t + (double) this.aR(), this.u, 7.0F, false, this.o.Q().b("mobGriefing"));
                this.o.a(1013, new BlockPos(this), 0);
            }

            this.r(i0);
            if (this.W % 10 == 0) {
                this.g(10.0F);
            }

        }
        else {
            super.E();

            int i1;

            for (i0 = 1; i0 < 3; ++i0) {
                if (this.W >= this.bm[i0 - 1]) {
                    this.bm[i0 - 1] = this.W + 10 + this.V.nextInt(10);
                    if (this.o.aa() == EnumDifficulty.NORMAL || this.o.aa() == EnumDifficulty.HARD) {
                        int i11001 = i0 - 1;
                        int i11003 = this.bn[i0 - 1];

                        this.bn[i11001] = this.bn[i0 - 1] + 1;
                        if (i11003 > 15) {
                            float f0 = 10.0F;
                            float f1 = 5.0F;
                            double d0 = MathHelper.a(this.V, this.s - (double) f0, this.s + (double) f0);
                            double d1 = MathHelper.a(this.V, this.t - (double) f1, this.t + (double) f1);
                            double d2 = MathHelper.a(this.V, this.u - (double) f0, this.u + (double) f0);

                            this.a(i0 + 1, d0, d1, d2, true);
                            this.bn[i0 - 1] = 0;
                        }
                    }

                    i1 = this.s(i0);
                    if (i1 > 0) {
                        Entity entity = this.o.a(i1);

                        if (entity != null && entity.ai() && this.h(entity) <= 900.0D && this.t(entity)) {
                            this.a(i0 + 1, (EntityLivingBase) entity);
                            this.bm[i0 - 1] = this.W + 40 + this.V.nextInt(20);
                            this.bn[i0 - 1] = 0;
                        }
                        else {
                            this.b(i0, 0);
                        }
                    }
                    else {
                        List list = this.o.a(EntityLivingBase.class, this.aQ().b(20.0D, 8.0D, 20.0D), Predicates.and(bp, IEntitySelector.d));

                        for (int i4 = 0; i4 < 10 && !list.isEmpty(); ++i4) {
                            EntityLivingBase entitylivingbase = (EntityLivingBase) list.get(this.V.nextInt(list.size()));

                            if (entitylivingbase != this && entitylivingbase.ai() && this.t(entitylivingbase)) {
                                if (entitylivingbase instanceof EntityPlayer) {
                                    if (!((EntityPlayer) entitylivingbase).by.a) {
                                        this.b(i0, entitylivingbase.F());
                                    }
                                }
                                else {
                                    this.b(i0, entitylivingbase.F());
                                }
                                break;
                            }

                            list.remove(entitylivingbase);
                        }
                    }
                }
            }

            if (this.u() != null) {
                this.b(0, this.u().F());
            }
            else {
                this.b(0, 0);
            }

            if (this.bo > 0) {
                --this.bo;
                if (this.bo == 0 && this.o.Q().b("mobGriefing")) {
                    i0 = MathHelper.c(this.t);
                    i1 = MathHelper.c(this.s);
                    int i5 = MathHelper.c(this.u);
                    boolean flag0 = false;

                    for (int i6 = -1; i6 <= 1; ++i6) {
                        for (int i7 = -1; i7 <= 1; ++i7) {
                            for (int i8 = 0; i8 <= 3; ++i8) {
                                int i9 = i1 + i6;
                                int i10 = i0 + i8;
                                int i11 = i5 + i7;
                                Block block = this.o.p(new BlockPos(i9, i10, i11)).c();

                                if (block.r() != Material.a && block != Blocks.h && block != Blocks.bF && block != Blocks.bG && block != Blocks.bX && block != Blocks.cv) {
                                    flag0 = this.o.b(new BlockPos(i9, i10, i11), true) || flag0;
                                }
                            }
                        }
                    }

                    if (flag0) {
                        this.o.a((EntityPlayer) null, 1012, new BlockPos(this), 0);
                    }
                }
            }

            if (this.W % 20 == 0) {
                this.g(1.0F);
            }

        }
    }

    public void n() {
        this.r(220);
        this.h(this.bt() / 3.0F);
    }

    public void aB() {
    }

    public int bq() {
        return 4;
    }

    private double t(int i0) {
        if (i0 <= 0) {
            return this.s;
        }
        else {
            float f0 = (this.aG + (float) (180 * (i0 - 1))) / 180.0F * 3.1415927F;
            float f1 = MathHelper.b(f0);

            return this.s + (double) f1 * 1.3D;
        }
    }

    private double u(int i0) {
        return i0 <= 0 ? this.t + 3.0D : this.t + 2.2D;
    }

    private double v(int i0) {
        if (i0 <= 0) {
            return this.u;
        }
        else {
            float f0 = (this.aG + (float) (180 * (i0 - 1))) / 180.0F * 3.1415927F;
            float f1 = MathHelper.a(f0);

            return this.u + (double) f1 * 1.3D;
        }
    }

    private float b(float f0, float f1, float f2) {
        float f3 = MathHelper.g(f1 - f0);

        if (f3 > f2) {
            f3 = f2;
        }

        if (f3 < -f2) {
            f3 = -f2;
        }

        return f0 + f3;
    }

    private void a(int i0, EntityLivingBase entitylivingbase) {
        this.a(i0, entitylivingbase.s, entitylivingbase.t + (double) entitylivingbase.aR() * 0.5D, entitylivingbase.u, i0 == 0 && this.V.nextFloat() < 0.001F);
    }

    private void a(int i0, double d0, double d1, double d2, boolean flag0) {
        this.o.a((EntityPlayer) null, 1014, new BlockPos(this), 0);
        double d3 = this.t(i0);
        double d4 = this.u(i0);
        double d5 = this.v(i0);
        double d6 = d0 - d3;
        double d7 = d1 - d4;
        double d8 = d2 - d5;
        EntityWitherSkull entitywitherskull = new EntityWitherSkull(this.o, this, d6, d7, d8);

        if (flag0) {
            entitywitherskull.a(true);
        }

        entitywitherskull.t = d4;
        entitywitherskull.s = d3;
        entitywitherskull.u = d5;
        this.o.d((Entity) entitywitherskull);
    }

    public void a(EntityLivingBase entitylivingbase, float f0) {
        this.a(0, entitylivingbase);
    }

    public boolean a(DamageSource damagesource, float f0) {
        if (this.b(damagesource)) {
            return false;
        }
        else if (damagesource != DamageSource.f && !(damagesource.j() instanceof EntityWither)) {
            if (this.cj() > 0 && damagesource != DamageSource.j) {
                return false;
            }
            else {
                Entity entity;

                if (this.ck()) {
                    entity = damagesource.i();
                    if (entity instanceof EntityArrow) {
                        return false;
                    }
                }

                entity = damagesource.j();
                if (entity != null && !(entity instanceof EntityPlayer) && entity instanceof EntityLivingBase && ((EntityLivingBase) entity).by() == this.by()) {
                    return false;
                }
                else {
                    if (this.bo <= 0) {
                        this.bo = 20;
                    }

                    for (int i0 = 0; i0 < this.bn.length; ++i0) {
                        this.bn[i0] += 3;
                    }

                    return super.a(damagesource, f0);
                }
            }
        }
        else {
            return false;
        }
    }

    protected void b(boolean flag0, int i0) {
        EntityItem entityitem = this.a(Items.bZ, 1);

        if (entityitem != null) {
            entityitem.u();
        }

        if (!this.o.D) {
            Iterator iterator = this.o.a(EntityPlayer.class, this.aQ().b(50.0D, 100.0D, 50.0D)).iterator();

            while (iterator.hasNext()) {
                EntityPlayer entityplayer = (EntityPlayer) iterator.next();

                entityplayer.b((StatBase) AchievementList.J);
            }
        }
    }

    protected void D() {
        this.aO = 0;
    }

    public void e(float f0, float f1) {
    }

    public void c(PotionEffect potioneffect) {
    }

    protected void aW() {
        super.aW();
        this.a(SharedMonsterAttributes.a).a(300.0D);
        this.a(SharedMonsterAttributes.d).a(0.6000000238418579D);
        this.a(SharedMonsterAttributes.b).a(40.0D);
    }

    public int cj() {
        return this.ac.c(20);
    }

    public void r(int i0) {
        this.ac.b(20, Integer.valueOf(i0));
    }

    public int s(int i0) {
        return this.ac.c(17 + i0);
    }

    public void b(int i0, int i1) {
        this.ac.b(17 + i0, Integer.valueOf(i1));
    }

    public boolean ck() {
        return this.bm() <= this.bt() / 2.0F;
    }

    public EnumCreatureAttribute by() {
        return EnumCreatureAttribute.UNDEAD;
    }

    public void a(Entity entity) {
        this.m = null;
    }

}
