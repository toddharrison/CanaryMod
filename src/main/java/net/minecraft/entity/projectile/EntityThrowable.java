package net.minecraft.entity.projectile;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.List;

public abstract class EntityThrowable extends Entity implements IProjectile {

    private int c = -1;
    private int d = -1;
    private int e = -1;
    private Block f;
    protected boolean a;
    public int b;
    private EntityLivingBase g;
    private String h;
    private int i;
    private int ap;
    public float gravity = 0.03F; // CanaryMod

    public EntityThrowable(World world) {
        super(world);
        this.a(0.25F, 0.25F);
    }

    protected void h() {}

    public EntityThrowable(World world, EntityLivingBase entitylivingbase) {
        super(world);
        this.g = entitylivingbase;
        this.a(0.25F, 0.25F);
        this.b(entitylivingbase.s, entitylivingbase.t + (double) entitylivingbase.aR(), entitylivingbase.u, entitylivingbase.y, entitylivingbase.z);
        this.s -= (double) (MathHelper.b(this.y / 180.0F * 3.1415927F) * 0.16F);
        this.t -= 0.10000000149011612D;
        this.u -= (double) (MathHelper.a(this.y / 180.0F * 3.1415927F) * 0.16F);
        this.b(this.s, this.t, this.u);
        float f0 = 0.4F;

        this.v = (double) (-MathHelper.a(this.y / 180.0F * 3.1415927F) * MathHelper.b(this.z / 180.0F * 3.1415927F) * f0);
        this.x = (double) (MathHelper.b(this.y / 180.0F * 3.1415927F) * MathHelper.b(this.z / 180.0F * 3.1415927F) * f0);
        this.w = (double) (-MathHelper.a((this.z + this.l()) / 180.0F * 3.1415927F) * f0);
        this.c(this.v, this.w, this.x, this.j(), 1.0F);
    }

    public EntityThrowable(World world, double d0, double d1, double d2) {
        super(world);
        this.i = 0;
        this.a(0.25F, 0.25F);
        this.b(d0, d1, d2);
    }

    protected float j() {
        return 1.5F;
    }

    protected float l() {
        return 0.0F;
    }

    public void c(double d0, double d1, double d2, float f0, float f1) {
        float f2 = MathHelper.a(d0 * d0 + d1 * d1 + d2 * d2);

        d0 /= (double) f2;
        d1 /= (double) f2;
        d2 /= (double) f2;
        d0 += this.V.nextGaussian() * 0.007499999832361937D * (double) f1;
        d1 += this.V.nextGaussian() * 0.007499999832361937D * (double) f1;
        d2 += this.V.nextGaussian() * 0.007499999832361937D * (double) f1;
        d0 *= (double) f0;
        d1 *= (double) f0;
        d2 *= (double) f0;
        this.v = d0;
        this.w = d1;
        this.x = d2;
        float f3 = MathHelper.a(d0 * d0 + d2 * d2);

        this.A = this.y = (float) (Math.atan2(d0, d2) * 180.0D / 3.1415927410125732D);
        this.B = this.z = (float) (Math.atan2(d1, (double) f3) * 180.0D / 3.1415927410125732D);
        this.i = 0;
    }

    public void s_() {
        this.P = this.s;
        this.Q = this.t;
        this.R = this.u;
        super.s_();
        if (this.b > 0) {
            --this.b;
        }

        if (this.a) {
            if (this.o.p(new BlockPos(this.c, this.d, this.e)).c() == this.f) {
                ++this.i;
                if (this.i == 1200) {
                    this.J();
                }

                return;
            }

            this.a = false;
            this.v *= (double) (this.V.nextFloat() * 0.2F);
            this.w *= (double) (this.V.nextFloat() * 0.2F);
            this.x *= (double) (this.V.nextFloat() * 0.2F);
            this.i = 0;
            this.ap = 0;
        } else {
            ++this.ap;
        }

        Vec3 vec3 = new Vec3(this.s, this.t, this.u);
        Vec3 vec31 = new Vec3(this.s + this.v, this.t + this.w, this.u + this.x);
        MovingObjectPosition movingobjectposition = this.o.a(vec3, vec31);

        vec3 = new Vec3(this.s, this.t, this.u);
        vec31 = new Vec3(this.s + this.v, this.t + this.w, this.u + this.x);
        if (movingobjectposition != null) {
            vec31 = new Vec3(movingobjectposition.c.a, movingobjectposition.c.b, movingobjectposition.c.c);
        }

        if (!this.o.D) {
            Entity entity = null;
            List list = this.o.b((Entity) this, this.aQ().a(this.v, this.w, this.x).b(1.0D, 1.0D, 1.0D));
            double d0 = 0.0D;
            EntityLivingBase entitylivingbase = this.n();

            for (int i0 = 0; i0 < list.size(); ++i0) {
                Entity entity1 = (Entity) list.get(i0);

                if (entity1.ad() && (entity1 != entitylivingbase || this.ap >= 5)) {
                    float f0 = 0.3F;
                    AxisAlignedBB axisalignedbb = entity1.aQ().b((double) f0, (double) f0, (double) f0);
                    MovingObjectPosition movingobjectposition1 = axisalignedbb.a(vec3, vec31);

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
        }

        if (movingobjectposition != null) {
            if (movingobjectposition.a == MovingObjectPosition.MovingObjectType.BLOCK && this.o.p(movingobjectposition.a()).c() == Blocks.aY) {
                this.aq();
            } else {
                this.a(movingobjectposition);
            }
        }

        this.s += this.v;
        this.t += this.w;
        this.u += this.x;
        float f1 = MathHelper.a(this.v * this.v + this.x * this.x);

        this.y = (float) (Math.atan2(this.v, this.x) * 180.0D / 3.1415927410125732D);

        for (this.z = (float) (Math.atan2(this.w, (double) f1) * 180.0D / 3.1415927410125732D); this.z - this.B < -180.0F; this.B -= 360.0F) {
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
        float f2 = 0.99F;
        float f3 = this.m();

        if (this.V()) {
            for (int i1 = 0; i1 < 4; ++i1) {
                float f4 = 0.25F;

                this.o.a(EnumParticleTypes.WATER_BUBBLE, this.s - this.v * (double) f4, this.t - this.w * (double) f4, this.u - this.x * (double) f4, this.v, this.w, this.x, new int[0]);
            }

            f2 = 0.8F;
        }

        this.v *= (double) f2;
        this.w *= (double) f2;
        this.x *= (double) f2;
        this.w -= (double) f3;
        this.b(this.s, this.t, this.u);
    }

    protected float m() {
        return 0.03F;
    }

    protected abstract void a(MovingObjectPosition movingobjectposition);

    public void b(NBTTagCompound nbttagcompound) {
        nbttagcompound.a("xTile", (short) this.c);
        nbttagcompound.a("yTile", (short) this.d);
        nbttagcompound.a("zTile", (short) this.e);
        ResourceLocation resourcelocation = (ResourceLocation) Block.c.c(this.f);

        nbttagcompound.a("inTile", resourcelocation == null ? "" : resourcelocation.toString());
        nbttagcompound.a("shake", (byte) this.b);
        nbttagcompound.a("inGround", (byte) (this.a ? 1 : 0));
        if ((this.h == null || this.h.length() == 0) && this.g instanceof EntityPlayer) {
            this.h = this.g.d_();
        }

        nbttagcompound.a("ownerName", this.h == null ? "" : this.h);
    }

    public void a(NBTTagCompound nbttagcompound) {
        this.c = nbttagcompound.e("xTile");
        this.d = nbttagcompound.e("yTile");
        this.e = nbttagcompound.e("zTile");
        if (nbttagcompound.b("inTile", 8)) {
            this.f = Block.b(nbttagcompound.j("inTile"));
        } else {
            this.f = Block.c(nbttagcompound.d("inTile") & 255);
        }

        this.b = nbttagcompound.d("shake") & 255;
        this.a = nbttagcompound.d("inGround") == 1;
        this.h = nbttagcompound.j("ownerName");
        if (this.h != null && this.h.length() == 0) {
            this.h = null;
        }

    }

    public EntityLivingBase n() {
        if (this.g == null && this.h != null && this.h.length() > 0) {
            this.g = this.o.a(this.h);
        }

        return this.g;
    }
}
