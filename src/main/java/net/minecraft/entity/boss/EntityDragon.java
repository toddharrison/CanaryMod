package net.minecraft.entity.boss;

import net.canarymod.api.entity.living.monster.CanaryEnderDragon;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EntityDragon extends EntityLiving implements IEntityMultiPart, IMob {

    public double a;
    public double b;
    public double c;
    public double[][] bi = new double[64][3];
    public int bj = -1;
    public EntityDragonPart[] bk;
    public EntityDragonPart bl;
    public EntityDragonPart bm;
    public EntityDragonPart bn;
    public EntityDragonPart bo;
    public EntityDragonPart bp;
    public EntityDragonPart bq;
    public EntityDragonPart br;
    public float bs;
    public float bt;
    public boolean bu;
    public boolean bv;
    private Entity by;
    public int bw;
    public EntityEnderCrystal bx;
   
    public EntityDragon(World world) {
        super(world);
        this.bk = new EntityDragonPart[] { this.bl = new EntityDragonPart(this, "head", 6.0F, 6.0F), this.bm = new EntityDragonPart(this, "body", 8.0F, 8.0F), this.bn = new EntityDragonPart(this, "tail", 4.0F, 4.0F), this.bo = new EntityDragonPart(this, "tail", 4.0F, 4.0F), this.bp = new EntityDragonPart(this, "tail", 4.0F, 4.0F), this.bq = new EntityDragonPart(this, "wing", 4.0F, 4.0F), this.br = new EntityDragonPart(this, "wing", 4.0F, 4.0F)};
        this.h(this.bt());
        this.a(16.0F, 8.0F);
        this.T = true;
        this.ab = true;
        this.b = 100.0D;
        this.ah = true;
        this.entity = new CanaryEnderDragon(this); // CanaryMod: Wrap Entity
    }

    protected void aW() {
        super.aW();
        this.a(SharedMonsterAttributes.a).a(200.0D);
    }

    protected void h() {
        super.h();
    }

    public double[] b(int i0, float f0) {
        if (this.bm() <= 0.0F) {
            f0 = 0.0F;
        }

        f0 = 1.0F - f0;
        int i1 = this.bj - i0 * 1 & 63;
        int i2 = this.bj - i0 * 1 - 1 & 63;
        double[] adouble = new double[3];
        double d0 = this.bi[i1][0];
        double d1 = MathHelper.g(this.bi[i2][0] - d0);

        adouble[0] = d0 + d1 * (double) f0;
        d0 = this.bi[i1][1];
        d1 = this.bi[i2][1] - d0;
        adouble[1] = d0 + d1 * (double) f0;
        adouble[2] = this.bi[i1][2] + (this.bi[i2][2] - this.bi[i1][2]) * (double) f0;
        return adouble;
    }

    public void m() {
        float f0;
        float f1;

        if (this.o.D) {
            f0 = MathHelper.b(this.bt * 3.1415927F * 2.0F);
            f1 = MathHelper.b(this.bs * 3.1415927F * 2.0F);
            if (f1 <= -0.3F && f0 >= -0.3F && !this.R()) {
                this.o.a(this.s, this.t, this.u, "mob.enderdragon.wings", 5.0F, 0.8F + this.V.nextFloat() * 0.3F, false);
            }
        }

        this.bs = this.bt;
        float f2;

        if (this.bm() <= 0.0F) {
            f0 = (this.V.nextFloat() - 0.5F) * 8.0F;
            f1 = (this.V.nextFloat() - 0.5F) * 4.0F;
            f2 = (this.V.nextFloat() - 0.5F) * 8.0F;
            this.o.a(EnumParticleTypes.EXPLOSION_LARGE, this.s + (double) f0, this.t + 2.0D + (double) f1, this.u + (double) f2, 0.0D, 0.0D, 0.0D, new int[0]);
        } else {
            this.n();
            f0 = 0.2F / (MathHelper.a(this.v * this.v + this.x * this.x) * 10.0F + 1.0F);
            f0 *= (float) Math.pow(2.0D, this.w);
            if (this.bv) {
                this.bt += f0 * 0.5F;
            } else {
                this.bt += f0;
            }

            this.y = MathHelper.g(this.y);
            if (this.bj < 0) {
                for (int i0 = 0; i0 < this.bi.length; ++i0) {
                    this.bi[i0][0] = (double) this.y;
                    this.bi[i0][1] = this.t;
                }
            }

            if (++this.bj == this.bi.length) {
                this.bj = 0;
            }

            this.bi[this.bj][0] = (double) this.y;
            this.bi[this.bj][1] = this.t;
            double d0;
            double d1;
            double d2;
            double d3;
            float f3;

            if (this.o.D) {
                if (this.ba > 0) {
                    d3 = this.s + (this.bb - this.s) / (double) this.ba;
                    d0 = this.t + (this.bc - this.t) / (double) this.ba;
                    d1 = this.u + (this.bd - this.u) / (double) this.ba;
                    d2 = MathHelper.g(this.be - (double) this.y);
                    this.y = (float) ((double) this.y + d2 / (double) this.ba);
                    this.z = (float) ((double) this.z + (this.bf - (double) this.z) / (double) this.ba);
                    --this.ba;
                    this.b(d3, d0, d1);
                    this.b(this.y, this.z);
                }
            } else {
                d3 = this.a - this.s;
                d0 = this.b - this.t;
                d1 = this.c - this.u;
                d2 = d3 * d3 + d0 * d0 + d1 * d1;
                double d4;

                if (this.by != null) {
                    this.a = this.by.s;
                    this.c = this.by.u;
                    double d5 = this.a - this.s;
                    double d6 = this.c - this.u;
                    double d7 = Math.sqrt(d5 * d5 + d6 * d6);

                    d4 = 0.4000000059604645D + d7 / 80.0D - 1.0D;
                    if (d4 > 10.0D) {
                        d4 = 10.0D;
                    }

                    this.b = this.by.aQ().b + d4;
                } else {
                    this.a += this.V.nextGaussian() * 2.0D;
                    this.c += this.V.nextGaussian() * 2.0D;
                }

                if (this.bu || d2 < 100.0D || d2 > 22500.0D || this.D || this.E) {
                    this.cd();
                }

                d0 /= (double) MathHelper.a(d3 * d3 + d1 * d1);
                f3 = 0.6F;
                d0 = MathHelper.a(d0, (double) (-f3), (double) f3);
                this.w += d0 * 0.10000000149011612D;
                this.y = MathHelper.g(this.y);
                double d8 = 180.0D - Math.atan2(d3, d1) * 180.0D / 3.1415927410125732D;
                double d9 = MathHelper.g(d8 - (double) this.y);

                if (d9 > 50.0D) {
                    d9 = 50.0D;
                }

                if (d9 < -50.0D) {
                    d9 = -50.0D;
                }

                Vec3 vec3 = (new Vec3(this.a - this.s, this.b - this.t, this.c - this.u)).a();

                d4 = (double) (-MathHelper.b(this.y * 3.1415927F / 180.0F));
                Vec3 vec31 = (new Vec3((double) MathHelper.a(this.y * 3.1415927F / 180.0F), this.w, d4)).a();
                float f4 = ((float) vec31.b(vec3) + 0.5F) / 1.5F;

                if (f4 < 0.0F) {
                    f4 = 0.0F;
                }

                this.aZ *= 0.8F;
                float f5 = MathHelper.a(this.v * this.v + this.x * this.x) * 1.0F + 1.0F;
                double d10 = Math.sqrt(this.v * this.v + this.x * this.x) * 1.0D + 1.0D;

                if (d10 > 40.0D) {
                    d10 = 40.0D;
                }

                this.aZ = (float) ((double) this.aZ + d9 * (0.699999988079071D / d10 / (double) f5));
                this.y += this.aZ * 0.1F;
                float f6 = (float) (2.0D / (d10 + 1.0D));
                float f7 = 0.06F;

                this.a(0.0F, -1.0F, f7 * (f4 * f6 + (1.0F - f6)));
                if (this.bv) {
                    this.d(this.v * 0.800000011920929D, this.w * 0.800000011920929D, this.x * 0.800000011920929D);
                } else {
                    this.d(this.v, this.w, this.x);
                }

                Vec3 vec32 = (new Vec3(this.v, this.w, this.x)).a();
                float f8 = ((float) vec32.b(vec31) + 1.0F) / 2.0F;

                f8 = 0.8F + 0.15F * f8;
                this.v *= (double) f8;
                this.x *= (double) f8;
                this.w *= 0.9100000262260437D;
            }

            this.aG = this.y;
            this.bl.J = this.bl.K = 3.0F;
            this.bn.J = this.bn.K = 2.0F;
            this.bo.J = this.bo.K = 2.0F;
            this.bp.J = this.bp.K = 2.0F;
            this.bm.K = 3.0F;
            this.bm.J = 5.0F;
            this.bq.K = 2.0F;
            this.bq.J = 4.0F;
            this.br.K = 3.0F;
            this.br.J = 4.0F;
            f1 = (float) (this.b(5, 1.0F)[1] - this.b(10, 1.0F)[1]) * 10.0F / 180.0F * 3.1415927F;
            f2 = MathHelper.b(f1);
            float f9 = -MathHelper.a(f1);
            float f10 = this.y * 3.1415927F / 180.0F;
            float f11 = MathHelper.a(f10);
            float f12 = MathHelper.b(f10);

            this.bm.s_();
            this.bm.b(this.s + (double) (f11 * 0.5F), this.t, this.u - (double) (f12 * 0.5F), 0.0F, 0.0F);
            this.bq.s_();
            this.bq.b(this.s + (double) (f12 * 4.5F), this.t + 2.0D, this.u + (double) (f11 * 4.5F), 0.0F, 0.0F);
            this.br.s_();
            this.br.b(this.s - (double) (f12 * 4.5F), this.t + 2.0D, this.u - (double) (f11 * 4.5F), 0.0F, 0.0F);
            if (!this.o.D && this.as == 0) {
                this.a(this.o.b((Entity) this, this.bq.aQ().b(4.0D, 2.0D, 4.0D).c(0.0D, -2.0D, 0.0D)));
                this.a(this.o.b((Entity) this, this.br.aQ().b(4.0D, 2.0D, 4.0D).c(0.0D, -2.0D, 0.0D)));
                this.b(this.o.b((Entity) this, this.bl.aQ().b(1.0D, 1.0D, 1.0D)));
            }

            double[] adouble = this.b(5, 1.0F);
            double[] adouble1 = this.b(0, 1.0F);

            f3 = MathHelper.a(this.y * 3.1415927F / 180.0F - this.aZ * 0.01F);
            float f13 = MathHelper.b(this.y * 3.1415927F / 180.0F - this.aZ * 0.01F);

            this.bl.s_();
            this.bl.b(this.s + (double) (f3 * 5.5F * f2), this.t + (adouble1[1] - adouble[1]) * 1.0D + (double) (f9 * 5.5F), this.u - (double) (f13 * 5.5F * f2), 0.0F, 0.0F);

            for (int i1 = 0; i1 < 3; ++i1) {
                EntityDragonPart entitydragonpart = null;

                if (i1 == 0) {
                    entitydragonpart = this.bn;
                }

                if (i1 == 1) {
                    entitydragonpart = this.bo;
                }

                if (i1 == 2) {
                    entitydragonpart = this.bp;
                }

                double[] adouble2 = this.b(12 + i1 * 2, 1.0F);
                float f14 = this.y * 3.1415927F / 180.0F + this.b(adouble2[0] - adouble[0]) * 3.1415927F / 180.0F * 1.0F;
                float f15 = MathHelper.a(f14);
                float f16 = MathHelper.b(f14);
                float f17 = 1.5F;
                float f18 = (float) (i1 + 1) * 2.0F;

                entitydragonpart.s_();
                entitydragonpart.b(this.s - (double) ((f11 * f17 + f15 * f18) * f2), this.t + (adouble2[1] - adouble[1]) * 1.0D - (double) ((f18 + f17) * f9) + 1.5D, this.u + (double) ((f12 * f17 + f16 * f18) * f2), 0.0F, 0.0F);
            }

            if (!this.o.D) {
                this.bv = this.b(this.bl.aQ()) | this.b(this.bm.aQ());
            }

        }
    }

    private void n() {
        if (this.bx != null) {
            if (this.bx.I) {
                if (!this.o.D) {
                    this.a(this.bl, DamageSource.a((Explosion) null), 10.0F);
                }

                this.bx = null;
            } else if (this.W % 10 == 0 && this.bm() < this.bt()) {
                this.h(this.bm() + 1.0F);
            }
        }

        if (this.V.nextInt(10) == 0) {
            float f0 = 32.0F;
            List list = this.o.a(EntityEnderCrystal.class, this.aQ().b((double) f0, (double) f0, (double) f0));
            EntityEnderCrystal entityendercrystal = null;
            double d0 = Double.MAX_VALUE;
            Iterator iterator = list.iterator();

            while (iterator.hasNext()) {
                EntityEnderCrystal entityendercrystal1 = (EntityEnderCrystal) iterator.next();
                double d1 = entityendercrystal1.h(this);

                if (d1 < d0) {
                    d0 = d1;
                    entityendercrystal = entityendercrystal1;
                }
            }

            this.bx = entityendercrystal;
        }

    }

    private void a(List list) {
        double d0 = (this.bm.aQ().a + this.bm.aQ().d) / 2.0D;
        double d1 = (this.bm.aQ().c + this.bm.aQ().f) / 2.0D;
        Iterator iterator = list.iterator();

        while (iterator.hasNext()) {
            Entity entity = (Entity) iterator.next();

            if (entity instanceof EntityLivingBase) {
                double d2 = entity.s - d0;
                double d3 = entity.u - d1;
                double d4 = d2 * d2 + d3 * d3;

                entity.g(d2 / d4 * 4.0D, 0.20000000298023224D, d3 / d4 * 4.0D);
            }
        }

    }

    private void b(List list) {
        for (int i0 = 0; i0 < list.size(); ++i0) {
            Entity entity = (Entity) list.get(i0);

            if (entity instanceof EntityLivingBase) {
                entity.a(DamageSource.a((EntityLivingBase) this), 10.0F);
                this.a(this, entity);
            }
        }

    }

    private void cd() {
        this.bu = false;
        ArrayList arraylist = Lists.newArrayList(this.o.j);
        Iterator iterator = arraylist.iterator();

        while (iterator.hasNext()) {
            if (((EntityPlayer) iterator.next()).v()) {
                iterator.remove();
            }
        }

        if (this.V.nextInt(2) == 0 && !arraylist.isEmpty()) {
            this.by = (Entity) arraylist.get(this.V.nextInt(arraylist.size()));
        } else {
            boolean flag0;

            do {
                this.a = 0.0D;
                this.b = (double) (70.0F + this.V.nextFloat() * 50.0F);
                this.c = 0.0D;
                this.a += (double) (this.V.nextFloat() * 120.0F - 60.0F);
                this.c += (double) (this.V.nextFloat() * 120.0F - 60.0F);
                double d0 = this.s - this.a;
                double d1 = this.t - this.b;
                double d2 = this.u - this.c;

                flag0 = d0 * d0 + d1 * d1 + d2 * d2 > 100.0D;
            } while (!flag0);

            this.by = null;
        }

    }

    private float b(double d0) {
        return (float) MathHelper.g(d0);
    }

    private boolean b(AxisAlignedBB axisalignedbb) {
        int i0 = MathHelper.c(axisalignedbb.a);
        int i1 = MathHelper.c(axisalignedbb.b);
        int i2 = MathHelper.c(axisalignedbb.c);
        int i3 = MathHelper.c(axisalignedbb.d);
        int i4 = MathHelper.c(axisalignedbb.e);
        int i5 = MathHelper.c(axisalignedbb.f);
        boolean flag0 = false;
        boolean flag1 = false;

        for (int i6 = i0; i6 <= i3; ++i6) {
            for (int i7 = i1; i7 <= i4; ++i7) {
                for (int i8 = i2; i8 <= i5; ++i8) {
                    Block block = this.o.p(new BlockPos(i6, i7, i8)).c();

                    if (block.r() != Material.a) {
                        if (block != Blocks.cv && block != Blocks.Z && block != Blocks.bH && block != Blocks.h && block != Blocks.bX && this.o.Q().b("mobGriefing")) {
                            flag1 = this.o.g(new BlockPos(i6, i7, i8)) || flag1;
                        } else {
                            flag0 = true;
                        }
                    }
                }
            }
        }

        if (flag1) {
            double d0 = axisalignedbb.a + (axisalignedbb.d - axisalignedbb.a) * (double) this.V.nextFloat();
            double d1 = axisalignedbb.b + (axisalignedbb.e - axisalignedbb.b) * (double) this.V.nextFloat();
            double d2 = axisalignedbb.c + (axisalignedbb.f - axisalignedbb.c) * (double) this.V.nextFloat();

            this.o.a(EnumParticleTypes.EXPLOSION_LARGE, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
        }

        return flag0;
    }

    public boolean a(EntityDragonPart entitydragonpart, DamageSource damagesource, float f0) {
        if (entitydragonpart != this.bl) {
            f0 = f0 / 4.0F + 1.0F;
        }

        float f1 = this.y * 3.1415927F / 180.0F;
        float f2 = MathHelper.a(f1);
        float f3 = MathHelper.b(f1);

        this.a = this.s + (double) (f2 * 5.0F) + (double) ((this.V.nextFloat() - 0.5F) * 2.0F);
        this.b = this.t + (double) (this.V.nextFloat() * 3.0F) + 1.0D;
        this.c = this.u - (double) (f3 * 5.0F) + (double) ((this.V.nextFloat() - 0.5F) * 2.0F);
        this.by = null;
        if (damagesource.j() instanceof EntityPlayer || damagesource.c()) {
            this.e(damagesource, f0);
        }

        return true;
    }

    public boolean a(DamageSource damagesource, float f0) {
        if (damagesource instanceof EntityDamageSource && ((EntityDamageSource) damagesource).w()) {
            this.e(damagesource, f0);
        }

        return false;
    }

    protected boolean e(DamageSource damagesource, float f0) {
        return super.a(damagesource, f0);
    }

    public void G() {
        this.J();
    }

    protected void aY() {
        ++this.bw;
        if (this.bw >= 180 && this.bw <= 200) {
            float f0 = (this.V.nextFloat() - 0.5F) * 8.0F;
            float f1 = (this.V.nextFloat() - 0.5F) * 4.0F;
            float f2 = (this.V.nextFloat() - 0.5F) * 8.0F;

            this.o.a(EnumParticleTypes.EXPLOSION_HUGE, this.s + (double) f0, this.t + 2.0D + (double) f1, this.u + (double) f2, 0.0D, 0.0D, 0.0D, new int[0]);
        }

        int i0;
        int i1;

        if (!this.o.D) {
            if (this.bw > 150 && this.bw % 5 == 0 && this.o.Q().b("doMobLoot")) {
                i0 = 1000;

                while (i0 > 0) {
                    i1 = EntityXPOrb.a(i0);
                    i0 -= i1;
                    this.o.d((Entity) (new EntityXPOrb(this.o, this.s, this.t, this.u, i1)));
                }
            }

            if (this.bw == 1) {
                this.o.a(1018, new BlockPos(this), 0);
            }
        }

        this.d(0.0D, 0.10000000149011612D, 0.0D);
        this.aG = this.y += 20.0F;
        if (this.bw == 200 && !this.o.D) {
            i0 = 2000;

            while (i0 > 0) {
                i1 = EntityXPOrb.a(i0);
                i0 -= i1;
                this.o.d((Entity) (new EntityXPOrb(this.o, this.s, this.t, this.u, i1)));
            }

            this.a(new BlockPos(this.s, 64.0D, this.u));
            this.J();
        }

    }

    private void a(BlockPos blockpos) {
        boolean flag0 = true;
        double d0 = 12.25D;
        double d1 = 6.25D;

        for (int i0 = -1; i0 <= 32; ++i0) {
            for (int i1 = -4; i1 <= 4; ++i1) {
                for (int i2 = -4; i2 <= 4; ++i2) {
                    double d2 = (double) (i1 * i1 + i2 * i2);

                    if (d2 <= 12.25D) {
                        BlockPos blockpos1 = blockpos.a(i1, i0, i2);

                        if (i0 < 0) {
                            if (d2 <= 6.25D) {
                                this.o.a(blockpos1, Blocks.h.P());
                            }
                        } else if (i0 > 0) {
                            this.o.a(blockpos1, Blocks.a.P());
                        } else if (d2 > 6.25D) {
                            this.o.a(blockpos1, Blocks.h.P());
                        } else {
                            this.o.a(blockpos1, Blocks.bF.P());
                        }
                    }
                }
            }
        }

        this.o.a(blockpos, Blocks.h.P());
        this.o.a(blockpos.a(), Blocks.h.P());
        BlockPos blockpos2 = blockpos.b(2);

        this.o.a(blockpos2, Blocks.h.P());
        this.o.a(blockpos2.e(), Blocks.aa.P().a(BlockTorch.a, EnumFacing.EAST));
        this.o.a(blockpos2.f(), Blocks.aa.P().a(BlockTorch.a, EnumFacing.WEST));
        this.o.a(blockpos2.c(), Blocks.aa.P().a(BlockTorch.a, EnumFacing.SOUTH));
        this.o.a(blockpos2.d(), Blocks.aa.P().a(BlockTorch.a, EnumFacing.NORTH));
        this.o.a(blockpos.b(3), Blocks.h.P());
        this.o.a(blockpos.b(4), Blocks.bI.P());
    }

    protected void D() {}

    public Entity[] aC() {
        return this.bk;
    }

    public boolean ad() {
        return false;
    }

    public World a() {
        return this.o;
    }

    protected String z() {
        return "mob.enderdragon.growl";
    }

    protected String bn() {
        return "mob.enderdragon.hit";
    }

    protected float bA() {
        return 5.0F;
    }
}
