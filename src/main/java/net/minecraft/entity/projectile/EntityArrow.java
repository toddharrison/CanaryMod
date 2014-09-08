package net.minecraft.entity.projectile;

import net.canarymod.api.entity.CanaryArrow;
import net.canarymod.hook.entity.ProjectileHitHook;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
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
    public int at; // CanaryMod: private => public; ticksInGround
    public int au; // CanaryMod: private => public; ticksInAir
    private double av = 2.0D;
    private int aw;

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
        this.L = 0.0F;
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

        this.t = entitylivingbase.t + (double) entitylivingbase.g() - 0.10000000149011612D;
        double d0 = entitylivingbase1.s - entitylivingbase.s;
        double d1 = entitylivingbase1.C.b + (double) (entitylivingbase1.N / 3.0F) - this.t;
        double d2 = entitylivingbase1.u - entitylivingbase.u;
        double d3 = (double) MathHelper.a(d0 * d0 + d2 * d2);

        if (d3 >= 1.0E-7D) {
            float f2 = (float) (Math.atan2(d2, d0) * 180.0D / 3.1415927410125732D) - 90.0F;
            float f3 = (float) (-(Math.atan2(d1, d3) * 180.0D / 3.1415927410125732D));
            double d4 = d0 / d3;
            double d5 = d2 / d3;

            this.b(entitylivingbase.s + d4, this.t, entitylivingbase.u + d5, f2, f3);
            this.L = 0.0F;
            float f4 = (float) d3 * 0.2F;

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
        this.b(entitylivingbase.s, entitylivingbase.t + (double) entitylivingbase.g(), entitylivingbase.u, entitylivingbase.y, entitylivingbase.z);
        this.s -= (double) (MathHelper.b(this.y / 180.0F * 3.1415927F) * 0.16F);
        this.t -= 0.10000000149011612D;
        this.u -= (double) (MathHelper.a(this.y / 180.0F * 3.1415927F) * 0.16F);
        this.b(this.s, this.t, this.u);
        this.L = 0.0F;
        this.v = (double) (-MathHelper.a(this.y / 180.0F * 3.1415927F) * MathHelper.b(this.z / 180.0F * 3.1415927F));
        this.x = (double) (MathHelper.b(this.y / 180.0F * 3.1415927F) * MathHelper.b(this.z / 180.0F * 3.1415927F));
        this.w = (double) (-MathHelper.a(this.z / 180.0F * 3.1415927F));
        this.c(this.v, this.w, this.x, f0 * 1.5F, 1.0F);
    }

    protected void c() {
        this.af.a(16, Byte.valueOf((byte) 0));
    }

    public void c(double d0, double d1, double d2, float f0, float f1) {
        float f2 = MathHelper.a(d0 * d0 + d1 * d1 + d2 * d2);

        d0 /= (double) f2;
        d1 /= (double) f2;
        d2 /= (double) f2;
        d0 += this.Z.nextGaussian() * (double) (this.Z.nextBoolean() ? -1 : 1) * 0.007499999832361937D * (double) f1;
        d1 += this.Z.nextGaussian() * (double) (this.Z.nextBoolean() ? -1 : 1) * 0.007499999832361937D * (double) f1;
        d2 += this.Z.nextGaussian() * (double) (this.Z.nextBoolean() ? -1 : 1) * 0.007499999832361937D * (double) f1;
        d0 *= (double) f0;
        d1 *= (double) f0;
        d2 *= (double) f0;
        this.v = d0;
        this.w = d1;
        this.x = d2;
        float f3 = MathHelper.a(d0 * d0 + d2 * d2);

        this.A = this.y = (float) (Math.atan2(d0, d2) * 180.0D / 3.1415927410125732D);
        this.B = this.z = (float) (Math.atan2(d1, (double) f3) * 180.0D / 3.1415927410125732D);
        this.at = 0;
    }

    public void h() {
        super.h();
        if (this.B == 0.0F && this.A == 0.0F) {
            float f0 = MathHelper.a(this.v * this.v + this.x * this.x);

            this.A = this.y = (float) (Math.atan2(this.v, this.x) * 180.0D / 3.1415927410125732D);
            this.B = this.z = (float) (Math.atan2(this.w, (double) f0) * 180.0D / 3.1415927410125732D);
        }

        Block block = this.o.a(this.d, this.e, this.f);

        if (block.o() != Material.a) {
            block.a((IBlockAccess) this.o, this.d, this.e, this.f);
            AxisAlignedBB axisalignedbb = block.a(this.o, this.d, this.e, this.f);

            if (axisalignedbb != null && axisalignedbb.a(Vec3.a(this.s, this.t, this.u))) {
                this.i = true;
            }
        }

        if (this.b > 0) {
            --this.b;
        }

        if (this.i) {
            int i0 = this.o.e(this.d, this.e, this.f);

            if (block == this.g && i0 == this.h) {
                ++this.at;
                if (this.at == 1200) {
                    this.B();
                }
            }
            else {
                this.i = false;
                this.v *= (double) (this.Z.nextFloat() * 0.2F);
                this.w *= (double) (this.Z.nextFloat() * 0.2F);
                this.x *= (double) (this.Z.nextFloat() * 0.2F);
                this.at = 0;
                this.au = 0;
            }
        }
        else {
            ++this.au;
            Vec3 vec3 = Vec3.a(this.s, this.t, this.u);
            Vec3 vec31 = Vec3.a(this.s + this.v, this.t + this.w, this.u + this.x);
            MovingObjectPosition movingobjectposition = this.o.a(vec3, vec31, false, true, false);

            vec3 = Vec3.a(this.s, this.t, this.u);
            vec31 = Vec3.a(this.s + this.v, this.t + this.w, this.u + this.x);
            if (movingobjectposition != null) {
                vec31 = Vec3.a(movingobjectposition.f.a, movingobjectposition.f.b, movingobjectposition.f.c);
            }

            Entity entity = null;
            List list = this.o.b((Entity) this, this.C.a(this.v, this.w, this.x).b(1.0D, 1.0D, 1.0D));
            double d0 = 0.0D;

            int i1;
            float f1;

            for (i1 = 0; i1 < list.size(); ++i1) {
                Entity entity1 = (Entity) list.get(i1);

                if (entity1.R() && (entity1 != this.c || this.au >= 5)) {
                    f1 = 0.3F;
                    AxisAlignedBB axisalignedbb1 = entity1.C.b((double) f1, (double) f1, (double) f1);
                    MovingObjectPosition movingobjectposition1 = axisalignedbb1.a(vec3, vec31);

                    if (movingobjectposition1 != null) {
                        double d1 = vec3.d(movingobjectposition1.f);

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

            if (movingobjectposition != null && movingobjectposition.g != null && movingobjectposition.g instanceof EntityPlayer) {
                EntityPlayer entityplayer = (EntityPlayer) movingobjectposition.g;

                if (entityplayer.bE.a || this.c instanceof EntityPlayer && !((EntityPlayer) this.c).a(entityplayer)) {
                    movingobjectposition = null;
                }
            }

            float f2;
            float f3;

            if (movingobjectposition != null) {
                // CanaryMod: ProjectileHit
                ProjectileHitHook hook = (ProjectileHitHook) new ProjectileHitHook(this.getCanaryEntity(), movingobjectposition.g == null ? null : movingobjectposition.g.getCanaryEntity()).call();
                if (!hook.isCanceled() && movingobjectposition.g != null) { //
                    f2 = MathHelper.a(this.v * this.v + this.w * this.w + this.x * this.x);
                    int i2 = MathHelper.f((double) f2 * this.av);

                    if (this.f()) {
                        i2 += this.Z.nextInt(i2 / 2 + 2);
                    }

                    DamageSource damagesource = null;

                    if (this.c == null) {
                        damagesource = DamageSource.a(this, this);
                    }
                    else {
                        damagesource = DamageSource.a(this, this.c);
                    }

                    if (this.al() && !(movingobjectposition.g instanceof EntityEnderman)) {
                        movingobjectposition.g.e(5);
                    }

                    if (movingobjectposition.g.a(damagesource, (float) i2)) {
                        if (movingobjectposition.g instanceof EntityLivingBase) {
                            EntityLivingBase entitylivingbase = (EntityLivingBase) movingobjectposition.g;

                            if (!this.o.E) {
                                entitylivingbase.p(entitylivingbase.aZ() + 1);
                            }

                            if (this.aw > 0) {
                                f3 = MathHelper.a(this.v * this.v + this.x * this.x);
                                if (f3 > 0.0F) {
                                    movingobjectposition.g.g(this.v * (double) this.aw * 0.6000000238418579D / (double) f3, 0.1D, this.x * (double) this.aw * 0.6000000238418579D / (double) f3);
                                }
                            }

                            if (this.c != null && this.c instanceof EntityLivingBase) {
                                EnchantmentHelper.a(entitylivingbase, this.c);
                                EnchantmentHelper.b((EntityLivingBase) this.c, (Entity) entitylivingbase);
                            }

                            if (this.c != null && movingobjectposition.g != this.c && movingobjectposition.g instanceof EntityPlayer && this.c instanceof EntityPlayerMP) {
                                ((EntityPlayerMP) this.c).a.a((Packet) (new S2BPacketChangeGameState(6, 0.0F)));
                            }
                        }

                        this.a("random.bowhit", 1.0F, 1.2F / (this.Z.nextFloat() * 0.2F + 0.9F));
                        if (!(movingobjectposition.g instanceof EntityEnderman)) {
                            this.B();
                        }
                    }
                    else {
                        this.v *= -0.10000000149011612D;
                        this.w *= -0.10000000149011612D;
                        this.x *= -0.10000000149011612D;
                        this.y += 180.0F;
                        this.A += 180.0F;
                        this.au = 0;
                    }
                }
                else {
                    this.d = movingobjectposition.b;
                    this.e = movingobjectposition.c;
                    this.f = movingobjectposition.d;
                    this.g = this.o.a(this.d, this.e, this.f);
                    this.h = this.o.e(this.d, this.e, this.f);
                    this.v = (double) ((float) (movingobjectposition.f.a - this.s));
                    this.w = (double) ((float) (movingobjectposition.f.b - this.t));
                    this.x = (double) ((float) (movingobjectposition.f.c - this.u));
                    f2 = MathHelper.a(this.v * this.v + this.w * this.w + this.x * this.x);
                    this.s -= this.v / (double) f2 * 0.05000000074505806D;
                    this.t -= this.w / (double) f2 * 0.05000000074505806D;
                    this.u -= this.x / (double) f2 * 0.05000000074505806D;
                    this.a("random.bowhit", 1.0F, 1.2F / (this.Z.nextFloat() * 0.2F + 0.9F));
                    this.i = true;
                    this.b = 7;
                    this.a(false);
                    if (this.g.o() != Material.a) {
                        this.g.a(this.o, this.d, this.e, this.f, (Entity) this);
                    }
                }
            }

            if (this.f()) {
                for (i1 = 0; i1 < 4; ++i1) {
                    this.o.a("crit", this.s + this.v * (double) i1 / 4.0D, this.t + this.w * (double) i1 / 4.0D, this.u + this.x * (double) i1 / 4.0D, -this.v, -this.w + 0.2D, -this.x);
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
            float f4 = 0.99F;

            f1 = 0.05F;
            if (this.M()) {
                for (int i3 = 0; i3 < 4; ++i3) {
                    f3 = 0.25F;
                    this.o.a("bubble", this.s - this.v * (double) f3, this.t - this.w * (double) f3, this.u - this.x * (double) f3, this.v, this.w, this.x);
                }

                f4 = 0.8F;
            }

            if (this.L()) {
                this.F();
            }

            this.v *= (double) f4;
            this.w *= (double) f4;
            this.x *= (double) f4;
            this.w -= (double) f1;
            this.b(this.s, this.t, this.u);
            this.I();
        }
    }

    public void b(NBTTagCompound nbttagcompound) {
        nbttagcompound.a("xTile", (short) this.d);
        nbttagcompound.a("yTile", (short) this.e);
        nbttagcompound.a("zTile", (short) this.f);
        nbttagcompound.a("life", (short) this.at);
        nbttagcompound.a("inTile", (byte) Block.b(this.g));
        nbttagcompound.a("inData", (byte) this.h);
        nbttagcompound.a("shake", (byte) this.b);
        nbttagcompound.a("inGround", (byte) (this.i ? 1 : 0));
        nbttagcompound.a("pickup", (byte) this.a);
        nbttagcompound.a("damage", this.av);
    }

    public void a(NBTTagCompound nbttagcompound) {
        this.d = nbttagcompound.e("xTile");
        this.e = nbttagcompound.e("yTile");
        this.f = nbttagcompound.e("zTile");
        this.at = nbttagcompound.e("life");
        this.g = Block.e(nbttagcompound.d("inTile") & 255);
        this.h = nbttagcompound.d("inData") & 255;
        this.b = nbttagcompound.d("shake") & 255;
        this.i = nbttagcompound.d("inGround") == 1;
        if (nbttagcompound.b("damage", 99)) {
            this.av = nbttagcompound.i("damage");
        }

        if (nbttagcompound.b("pickup", 99)) {
            this.a = nbttagcompound.d("pickup");
        }
        else if (nbttagcompound.b("player", 99)) {
            this.a = nbttagcompound.n("player") ? 1 : 0;
        }
    }

    public void b_(EntityPlayer entityplayer) {
        if (!this.o.E && this.i && this.b <= 0) {
            boolean flag0 = this.a == 1 || this.a == 2 && entityplayer.bE.d;

            if (this.a == 1 && !entityplayer.bm.a(new ItemStack(Items.g, 1))) {
                flag0 = false;
            }

            if (flag0) {
                this.a("random.pop", 0.2F, ((this.Z.nextFloat() - this.Z.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                entityplayer.a((Entity) this, 1);
                this.B();
            }
        }
    }

    protected boolean g_() {
        return false;
    }

    public void b(double d0) {
        this.av = d0;
    }

    public double e() {
        return this.av;
    }

    public void a(int i0) {
        this.aw = i0;
    }

    public boolean av() {
        return false;
    }

    public void a(boolean flag0) {
        byte b0 = this.af.a(16);

        if (flag0) {
            this.af.b(16, Byte.valueOf((byte) (b0 | 1)));
        }
        else {
            this.af.b(16, Byte.valueOf((byte) (b0 & -2)));
        }
    }

    public boolean f() {
        byte b0 = this.af.a(16);

        return (b0 & 1) != 0;
    }

    public int cmeakb() {
        return this.aw;
    }
}
