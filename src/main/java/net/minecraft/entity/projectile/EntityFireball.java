package net.minecraft.entity.projectile;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.List;

public abstract class EntityFireball extends Entity {

    private int e = -1;
    private int f = -1;
    private int g = -1;
    private Block h;
    public boolean i; // CanaryMod: private => public; inGround
    public EntityLivingBase a;
    public int ap; // CanaryMod: private => public; ticksAlive
    public int aq; // CanaryMod: private => public; ticksInAir
    public double b;
    public double c;
    public double d;
    private float motionFactor = 0.95F; // CanaryMod: changeable motion factor

    public EntityFireball(World world) {
        super(world);
        this.a(1.0F, 1.0F);
    }

    protected void h() {}

    public EntityFireball(World world, double d0, double d1, double d2, double d3, double d4, double d5) {
        super(world);
        this.a(1.0F, 1.0F);
        this.b(d0, d1, d2, this.y, this.z);
        this.b(d0, d1, d2);
        double d6 = (double) MathHelper.a(d3 * d3 + d4 * d4 + d5 * d5);

        this.b = d3 / d6 * 0.1D;
        this.c = d4 / d6 * 0.1D;
        this.d = d5 / d6 * 0.1D;
    }

    public EntityFireball(World world, EntityLivingBase entitylivingbase, double d0, double d1, double d2) {
        super(world);
        this.a = entitylivingbase;
        this.a(1.0F, 1.0F);
        this.b(entitylivingbase.s, entitylivingbase.t, entitylivingbase.u, entitylivingbase.y, entitylivingbase.z);
        this.b(this.s, this.t, this.u);
        this.v = this.w = this.x = 0.0D;
        d0 += this.V.nextGaussian() * 0.4D;
        d1 += this.V.nextGaussian() * 0.4D;
        d2 += this.V.nextGaussian() * 0.4D;
        double d3 = (double) MathHelper.a(d0 * d0 + d1 * d1 + d2 * d2);

        this.b = d0 / d3 * 0.1D;
        this.c = d1 / d3 * 0.1D;
        this.d = d2 / d3 * 0.1D;
    }

    public void s_() {
        if (!this.o.D && (this.a != null && this.a.I || !this.o.e(new BlockPos(this)))) {
            this.J();
        } else {
            super.s_();
            this.e(1);
            if (this.i) {
                if (this.o.p(new BlockPos(this.e, this.f, this.g)).c() == this.h) {
                    ++this.ap;
                    if (this.ap == 600) {
                        this.J();
                    }

                    return;
                }

                this.i = false;
                this.v *= (double) (this.V.nextFloat() * 0.2F);
                this.w *= (double) (this.V.nextFloat() * 0.2F);
                this.x *= (double) (this.V.nextFloat() * 0.2F);
                this.ap = 0;
                this.aq = 0;
            } else {
                ++this.aq;
            }

            Vec3 vec3 = new Vec3(this.s, this.t, this.u);
            Vec3 vec31 = new Vec3(this.s + this.v, this.t + this.w, this.u + this.x);
            MovingObjectPosition movingobjectposition = this.o.a(vec3, vec31);

            vec3 = new Vec3(this.s, this.t, this.u);
            vec31 = new Vec3(this.s + this.v, this.t + this.w, this.u + this.x);
            if (movingobjectposition != null) {
                vec31 = new Vec3(movingobjectposition.c.a, movingobjectposition.c.b, movingobjectposition.c.c);
            }

            Entity entity = null;
            List list = this.o.b((Entity) this, this.aQ().a(this.v, this.w, this.x).b(1.0D, 1.0D, 1.0D));
            double d0 = 0.0D;

            for (int i0 = 0; i0 < list.size(); ++i0) {
                Entity entity1 = (Entity) list.get(i0);

                if (entity1.ad() && (!entity1.k(this.a) || this.aq >= 25)) {
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

            if (movingobjectposition != null) {
                this.a(movingobjectposition);
            }

            this.s += this.v;
            this.t += this.w;
            this.u += this.x;
            float f1 = MathHelper.a(this.v * this.v + this.x * this.x);

            this.y = (float) (Math.atan2(this.x, this.v) * 180.0D / 3.1415927410125732D) + 90.0F;

            for (this.z = (float) (Math.atan2((double) f1, this.w) * 180.0D / 3.1415927410125732D) - 90.0F; this.z - this.B < -180.0F; this.B -= 360.0F) {
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
            float f2 = this.j();

            if (this.V()) {
                for (int i1 = 0; i1 < 4; ++i1) {
                    float f3 = 0.25F;

                    this.o.a(EnumParticleTypes.WATER_BUBBLE, this.s - this.v * (double) f3, this.t - this.w * (double) f3, this.u - this.x * (double) f3, this.v, this.w, this.x, new int[0]);
                }

                // f2 = 0.8F;
                f2 -= 0.15F; // CanaryMod: Change to reduce water speed rather than set it
            }

            this.v += this.b;
            this.w += this.c;
            this.x += this.d;
            this.v *= (double) f2;
            this.w *= (double) f2;
            this.x *= (double) f2;
            this.o.a(EnumParticleTypes.SMOKE_NORMAL, this.s, this.t + 0.5D, this.u, 0.0D, 0.0D, 0.0D, new int[0]);
            this.b(this.s, this.t, this.u);
        }
    }

    public float j() { // CanaryMod: protected => public
        return motionFactor; // CanaryMod: return custom factor
    }

    protected abstract void a(MovingObjectPosition movingobjectposition);

    public void b(NBTTagCompound nbttagcompound) {
        nbttagcompound.a("xTile", (short) this.e);
        nbttagcompound.a("yTile", (short) this.f);
        nbttagcompound.a("zTile", (short) this.g);
        ResourceLocation resourcelocation = (ResourceLocation) Block.c.c(this.h);

        nbttagcompound.a("inTile", resourcelocation == null ? "" : resourcelocation.toString());
        nbttagcompound.a("inGround", (byte) (this.i ? 1 : 0));
        nbttagcompound.a("direction", (NBTBase) this.a(new double[] { this.v, this.w, this.x}));
    }

    public void a(NBTTagCompound nbttagcompound) {
        this.e = nbttagcompound.e("xTile");
        this.f = nbttagcompound.e("yTile");
        this.g = nbttagcompound.e("zTile");        
        if (nbttagcompound.b("inTile", 8)) {
            this.h = Block.b(nbttagcompound.j("inTile"));
        } else {
            this.h = Block.c(nbttagcompound.d("inTile") & 255);
        }
        
        this.i = nbttagcompound.d("inGround") == 1;
        if (nbttagcompound.c("motionFactor")) { // CanaryMod: If motionFactor is stored, retrive it
            this.motionFactor = nbttagcompound.h("motionFactor");
        }

        if (nbttagcompound.b("direction", 9)) {
            NBTTagList nbttaglist = nbttagcompound.c("direction", 6);

            this.v = nbttaglist.d(0);
            this.w = nbttaglist.d(1);
            this.x = nbttaglist.d(2);
        } else {
            this.J();
        }

    }

    public boolean ad() {
        return true;
    }

    public float ao() {
        return 1.0F;
    }

    public boolean a(DamageSource damagesource, float f0) {
        if (this.b(damagesource)) {
            return false;
        } else {
            this.ac();
            if (damagesource.j() != null) {
                Vec3 vec3 = damagesource.j().ap();

                if (vec3 != null) {
                    this.v = vec3.a;
                    this.w = vec3.b;
                    this.x = vec3.c;
                    this.b = this.v * 0.1D;
                    this.c = this.w * 0.1D;
                    this.d = this.x * 0.1D;
                }

                if (damagesource.j() instanceof EntityLivingBase) {
                    this.a = (EntityLivingBase) damagesource.j();
                }

                return true;
            } else {
                return false;
            }
        }
    }

    public float c(float f0) {
        return 1.0F;
    }

    // CanaryMod
    public void setMotionFactor(float factor) {
        this.motionFactor = factor;
    }
}
