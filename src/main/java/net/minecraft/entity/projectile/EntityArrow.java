package net.minecraft.entity.projectile;

import net.canarymod.api.entity.CanaryArrow;
import net.canarymod.hook.entity.ProjectileHitHook;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.util.*;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public class EntityArrow extends Entity implements IProjectile {

    private int d = -1;
    private int e = -1;
    private int f = -1;
    private Block g;
    private int h;
    public boolean i; // CanaryMod: private => public; inGround
    public int a;
    public int b;
    public Entity c;
    public int ap; // CanaryMod: private => public; ticksInGround
    public int aq; // CanaryMod: private => public; ticksInAir
    private double ar = 2.0D;
    public int as; // CanaryMod: private => public; ticksInAir

    public EntityArrow(World world) {
        super(world);
        this.j = 10.0D;
        this.a(0.5F, 0.5F);
        this.entity = new CanaryArrow(this); // CanaryMod: Wrap Entity
    }

    public EntityArrow(World world, double d0, double d1, double d2) {
        super(world);
        this.j = 10.0D;
        this.a(0.5F, 0.5F);
        this.b(d0, d1, d2);
        this.entity = new CanaryArrow(this); // CanaryMod: Wrap Entity
    }

    public EntityArrow(World world, EntityLivingBase entitylivingbase, EntityLivingBase entitylivingbase1, float f0, float f1) {
        super(world);
        this.j = 10.0D;
        this.c = entitylivingbase;
        if (entitylivingbase instanceof EntityPlayer) {
            this.a = 1;
        }
        this.entity = new CanaryArrow(this); // CanaryMod: Wrap Entity

        this.t = entitylivingbase.t + (double) entitylivingbase.aR() - 0.10000000149011612D;
        double d0 = entitylivingbase1.s - entitylivingbase.s;
        double d1 = entitylivingbase1.aQ().b + (double) (entitylivingbase1.K / 3.0F) - this.t;
        double d2 = entitylivingbase1.u - entitylivingbase.u;
        double d3 = (double) MathHelper.a(d0 * d0 + d2 * d2);

        if (d3 >= 1.0E-7D) {
            float f2 = (float) (Math.atan2(d2, d0) * 180.0D / 3.1415927410125732D) - 90.0F;
            float f3 = (float) (-(Math.atan2(d1, d3) * 180.0D / 3.1415927410125732D));
            double d4 = d0 / d3;
            double d5 = d2 / d3;

            this.b(entitylivingbase.s + d4, this.t, entitylivingbase.u + d5, f2, f3);
            float f4 = (float) (d3 * 0.20000000298023224D);

            this.c(d0, d1 + (double) f4, d2, f0, f1);
        }
    }

    public EntityArrow(World world, EntityLivingBase entitylivingbase, float f0) {
        super(world);
        this.j = 10.0D;
        this.c = entitylivingbase;
        if (entitylivingbase instanceof EntityPlayer) {
            this.a = 1;
        }
        this.entity = new CanaryArrow(this); // CanaryMod: Wrap Entity

        this.a(0.5F, 0.5F);
        this.b(entitylivingbase.s, entitylivingbase.t + (double) entitylivingbase.aR(), entitylivingbase.u, entitylivingbase.y, entitylivingbase.z);
        this.s -= (double) (MathHelper.b(this.y / 180.0F * 3.1415927F) * 0.16F);
        this.t -= 0.10000000149011612D;
        this.u -= (double) (MathHelper.a(this.y / 180.0F * 3.1415927F) * 0.16F);
        this.b(this.s, this.t, this.u);
        this.v = (double) (-MathHelper.a(this.y / 180.0F * 3.1415927F) * MathHelper.b(this.z / 180.0F * 3.1415927F));
        this.x = (double) (MathHelper.b(this.y / 180.0F * 3.1415927F) * MathHelper.b(this.z / 180.0F * 3.1415927F));
        this.w = (double) (-MathHelper.a(this.z / 180.0F * 3.1415927F));
        this.c(this.v, this.w, this.x, f0 * 1.5F, 1.0F);
    }

    protected void h() {
        this.ac.a(16, Byte.valueOf((byte) 0));
    }

    public void c(double d0, double d1, double d2, float f0, float f1) {
        float f2 = MathHelper.a(d0 * d0 + d1 * d1 + d2 * d2);

        d0 /= (double) f2;
        d1 /= (double) f2;
        d2 /= (double) f2;
        d0 += this.V.nextGaussian() * (double) (this.V.nextBoolean() ? -1 : 1) * 0.007499999832361937D * (double) f1;
        d1 += this.V.nextGaussian() * (double) (this.V.nextBoolean() ? -1 : 1) * 0.007499999832361937D * (double) f1;
        d2 += this.V.nextGaussian() * (double) (this.V.nextBoolean() ? -1 : 1) * 0.007499999832361937D * (double) f1;
        d0 *= (double) f0;
        d1 *= (double) f0;
        d2 *= (double) f0;
        this.v = d0;
        this.w = d1;
        this.x = d2;
        float f3 = MathHelper.a(d0 * d0 + d2 * d2);

        this.A = this.y = (float) (Math.atan2(d0, d2) * 180.0D / 3.1415927410125732D);
        this.B = this.z = (float) (Math.atan2(d1, (double) f3) * 180.0D / 3.1415927410125732D);
        this.ap = 0;
    }

    public void s_() {
        super.s_();
        if (this.B == 0.0F && this.A == 0.0F) {
            float f0 = MathHelper.a(this.v * this.v + this.x * this.x);

            this.A = this.y = (float) (Math.atan2(this.v, this.x) * 180.0D / 3.1415927410125732D);
            this.B = this.z = (float) (Math.atan2(this.w, (double) f0) * 180.0D / 3.1415927410125732D);
        }

        BlockPos blockpos = new BlockPos(this.d, this.e, this.f);
        IBlockState iblockstate = this.o.p(blockpos);
        Block block = iblockstate.c();

        if (block.r() != Material.a) {
            block.a((IBlockAccess) this.o, blockpos);
            AxisAlignedBB axisalignedbb = block.a(this.o, blockpos, iblockstate);

            if (axisalignedbb != null && axisalignedbb.a(new Vec3(this.s, this.t, this.u))) {
                this.i = true;
            }
        }

        if (this.b > 0) {
            --this.b;
        }

        if (this.i) {
            int i0 = block.c(iblockstate);

            if (block == this.g && i0 == this.h) {
                ++this.ap;
                if (this.ap >= 1200) {
                    this.J();
                }

            } else {
                this.i = false;
                this.v *= (double) (this.V.nextFloat() * 0.2F);
                this.w *= (double) (this.V.nextFloat() * 0.2F);
                this.x *= (double) (this.V.nextFloat() * 0.2F);
                this.ap = 0;
                this.aq = 0;
            }
        } else {
            ++this.aq;
            Vec3 vec3 = new Vec3(this.s, this.t, this.u);
            Vec3 vec31 = new Vec3(this.s + this.v, this.t + this.w, this.u + this.x);
            MovingObjectPosition movingobjectposition = this.o.a(vec3, vec31, false, true, false);

            vec3 = new Vec3(this.s, this.t, this.u);
            vec31 = new Vec3(this.s + this.v, this.t + this.w, this.u + this.x);
            if (movingobjectposition != null) {
                vec31 = new Vec3(movingobjectposition.c.a, movingobjectposition.c.b, movingobjectposition.c.c);
            }

            Entity entity = null;
            List list = this.o.b((Entity) this, this.aQ().a(this.v, this.w, this.x).b(1.0D, 1.0D, 1.0D));
            double d0 = 0.0D;

            int i1;
            float f1;

            for (i1 = 0; i1 < list.size(); ++i1) {
                Entity entity1 = (Entity) list.get(i1);

                if (entity1.ad() && (entity1 != this.c || this.aq >= 5)) {
                    f1 = 0.3F;
                    AxisAlignedBB axisalignedbb1 = entity1.aQ().b((double) f1, (double) f1, (double) f1);
                    MovingObjectPosition movingobjectposition1 = axisalignedbb1.a(vec3, vec31);

                    if (movingobjectposition1 != null) {
                        double d1 = vec3.f(movingobjectposition1.c);

                        if (d1 < d0 || d0 == 0.0D) {
                            entity = entity1;
                            d0 = d1;
                        }
                    }
                }
            }

            if (entity != null) {
                movingobjectposition = new MovingObjectPosition(entity);
            }

            if (movingobjectposition != null && movingobjectposition.d != null && movingobjectposition.d instanceof EntityPlayer) {
                EntityPlayer entityplayer = (EntityPlayer) movingobjectposition.d;

                if (entityplayer.by.a || this.c instanceof EntityPlayer && !((EntityPlayer) this.c).a(entityplayer)) {
                    movingobjectposition = null;
                }
            }

            float f2;
            float f3;
            float f4;

            if (movingobjectposition != null) {
                // CanaryMod: ProjectileHit
                ProjectileHitHook hook = (ProjectileHitHook) new ProjectileHitHook(this.getCanaryEntity(), movingobjectposition.d == null ? null : movingobjectposition.d.getCanaryEntity()).call();
                if (movingobjectposition.d != null) { //
                    f2 = MathHelper.a(this.v * this.v + this.w * this.w + this.x * this.x);
                    int i2 = MathHelper.f((double) f2 * this.ar);

                    if (this.l()) {
                        i2 += this.V.nextInt(i2 / 2 + 2);
                    }

                    DamageSource damagesource;

                    if (this.c == null) {
                        damagesource = DamageSource.a(this, this);
                    } else {
                        damagesource = DamageSource.a(this, this.c);
                    }

                    if (this.au() && !(movingobjectposition.d instanceof EntityEnderman)) {
                        movingobjectposition.d.e(5);
                    }

                    if (!hook.isCanceled() && movingobjectposition.d.a(damagesource, (float) i2)) { //
                        if (movingobjectposition.d instanceof EntityLivingBase) {
                            EntityLivingBase entitylivingbase = (EntityLivingBase) movingobjectposition.d;

                            if (!this.o.D) {
                                entitylivingbase.o(entitylivingbase.bu() + 1);
                            }

                            if (this.as > 0) {
                                f4 = MathHelper.a(this.v * this.v + this.x * this.x);
                                if (f4 > 0.0F) {
                                    movingobjectposition.d.g(this.v * (double) this.as * 0.6000000238418579D / (double) f4, 0.1D, this.x * (double) this.as * 0.6000000238418579D / (double) f4);
                                }
                            }

                            if (this.c instanceof EntityLivingBase) {
                                EnchantmentHelper.a(entitylivingbase, this.c);
                                EnchantmentHelper.b((EntityLivingBase) this.c, entitylivingbase);
                            }

                            if (this.c != null && movingobjectposition.d != this.c && movingobjectposition.d instanceof EntityPlayer && this.c instanceof EntityPlayerMP) {
                                ((EntityPlayerMP) this.c).a.a((Packet) (new S2BPacketChangeGameState(6, 0.0F)));
                            }
                        }

                        this.a("random.bowhit", 1.0F, 1.2F / (this.V.nextFloat() * 0.2F + 0.9F));
                        if (!(movingobjectposition.d instanceof EntityEnderman)) {
                            this.J();
                        }
                    } else {
                        this.v *= -0.10000000149011612D;
                        this.w *= -0.10000000149011612D;
                        this.x *= -0.10000000149011612D;
                        this.y += 180.0F;
                        this.A += 180.0F;
                        this.aq = 0;
                    }
                } else {
                    BlockPos blockpos1 = movingobjectposition.a();

                    this.d = blockpos1.n();
                    this.e = blockpos1.o();
                    this.f = blockpos1.p();
                    iblockstate = this.o.p(blockpos1);
                    this.g = iblockstate.c();
                    this.h = this.g.c(iblockstate);
                    this.v = (double) ((float) (movingobjectposition.c.a - this.s));
                    this.w = (double) ((float) (movingobjectposition.c.b - this.t));
                    this.x = (double) ((float) (movingobjectposition.c.c - this.u));
                    f3 = MathHelper.a(this.v * this.v + this.w * this.w + this.x * this.x);
                    this.s -= this.v / (double) f3 * 0.05000000074505806D;
                    this.t -= this.w / (double) f3 * 0.05000000074505806D;
                    this.u -= this.x / (double) f3 * 0.05000000074505806D;
                    this.a("random.bowhit", 1.0F, 1.2F / (this.V.nextFloat() * 0.2F + 0.9F));
                    this.i = true;
                    this.b = 7;
                    this.a(false);
                    if (this.g.r() != Material.a) {
                        this.g.a(this.o, blockpos1, iblockstate, (Entity) this);
                    }
                }
            }

            if (this.l()) {
                for (i1 = 0; i1 < 4; ++i1) {
                    this.o.a(EnumParticleTypes.CRIT, this.s + this.v * (double) i1 / 4.0D, this.t + this.w * (double) i1 / 4.0D, this.u + this.x * (double) i1 / 4.0D, -this.v, -this.w + 0.2D, -this.x, new int[0]);
                }
            }

            this.s += this.v;
            this.t += this.w;
            this.u += this.x;
            f2 = MathHelper.a(this.v * this.v + this.x * this.x);
            this.y = (float) (Math.atan2(this.v, this.x) * 180.0D / 3.1415927410125732D);

            for (this.z = (float) (Math.atan2(this.w, (double) f2) * 180.0D / 3.1415927410125732D); this.z - this.B < -180.0F; this.B -= 360.0F) {
                ;
            }

            while (this.z - this.B >= 180.0F) {
                this.B += 360.0F;
            }

            while (this.y - this.A < -180.0F) {
                this.A -= 360.0F;
            }

            while (this.y - this.A >= 180.0F) {
                this.A += 360.0F;
            }

            this.z = this.B + (this.z - this.B) * 0.2F;
            this.y = this.A + (this.y - this.A) * 0.2F;
            f3 = 0.99F;
            f1 = 0.05F;
            if (this.V()) {
                for (int i3 = 0; i3 < 4; ++i3) {
                    f4 = 0.25F;
                    this.o.a(EnumParticleTypes.WATER_BUBBLE, this.s - this.v * (double) f4, this.t - this.w * (double) f4, this.u - this.x * (double) f4, this.v, this.w, this.x, new int[0]);
                }

                f3 = 0.6F;
            }

            if (this.U()) {
                this.N();
            }

            this.v *= (double) f3;
            this.w *= (double) f3;
            this.x *= (double) f3;
            this.w -= (double) f1;
            this.b(this.s, this.t, this.u);
            this.Q();
        }
    }

    public void b(NBTTagCompound nbttagcompound) {
        nbttagcompound.a("xTile", (short) this.d);
        nbttagcompound.a("yTile", (short) this.e);
        nbttagcompound.a("zTile", (short) this.f);
        nbttagcompound.a("life", (short) this.ap);
        ResourceLocation resourcelocation = (ResourceLocation) Block.c.c(this.g);

        nbttagcompound.a("inTile", resourcelocation == null ? "" : resourcelocation.toString());
        nbttagcompound.a("inData", (byte) this.h);
        nbttagcompound.a("shake", (byte) this.b);
        nbttagcompound.a("inGround", (byte) (this.i ? 1 : 0));
        nbttagcompound.a("pickup", (byte) this.a);
        nbttagcompound.a("damage", this.ar);
    }

    public void a(NBTTagCompound nbttagcompound) {
        this.d = nbttagcompound.e("xTile");
        this.e = nbttagcompound.e("yTile");
        this.f = nbttagcompound.e("zTile");
        this.ap = nbttagcompound.e("life");
        if (nbttagcompound.b("inTile", 8)) {
            this.g = Block.b(nbttagcompound.j("inTile"));
        } else {
            this.g = Block.c(nbttagcompound.d("inTile") & 255);
        }

        this.h = nbttagcompound.d("inData") & 255;
        this.b = nbttagcompound.d("shake") & 255;
        this.i = nbttagcompound.d("inGround") == 1;
        if (nbttagcompound.b("damage", 99)) {
            this.ar = nbttagcompound.i("damage");
        }

        if (nbttagcompound.b("pickup", 99)) {
            this.a = nbttagcompound.d("pickup");
        } else if (nbttagcompound.b("player", 99)) {
            this.a = nbttagcompound.n("player") ? 1 : 0;
        }

    }

    public void d(EntityPlayer entityplayer) {
        if (!this.o.D && this.i && this.b <= 0) {
            boolean flag0 = this.a == 1 || this.a == 2 && entityplayer.by.d;

            if (this.a == 1 && !entityplayer.bg.a(new ItemStack(Items.g, 1))) {
                flag0 = false;
            }

            if (flag0) {
                this.a("random.pop", 0.2F, ((this.V.nextFloat() - this.V.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                entityplayer.a((Entity) this, 1);
                this.J();
            }

        }
    }

    protected boolean r_() {
        return false;
    }

    public void b(double d0) {
        this.ar = d0;
    }

    public double j() {
        return this.ar;
    }

    public void a(int i0) {
        this.as = i0;
    }

    public boolean aE() {
        return false;
    }

    public void a(boolean flag0) {
        byte b0 = this.ac.a(16);

        if (flag0) {
            this.ac.b(16, Byte.valueOf((byte) (b0 | 1)));
        } else {
            this.ac.b(16, Byte.valueOf((byte) (b0 & -2)));
        }

    }

    public boolean l() {
        byte b0 = this.ac.a(16);

        return (b0 & 1) != 0;
    }
}
