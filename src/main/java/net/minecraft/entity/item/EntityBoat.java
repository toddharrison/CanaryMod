package net.minecraft.entity.item;

import net.canarymod.api.CanaryDamageSource;
import net.canarymod.api.entity.living.LivingBase;
import net.canarymod.api.entity.vehicle.CanaryBoat;
import net.canarymod.api.entity.vehicle.Vehicle;
import net.canarymod.api.world.position.Vector3D;
import net.canarymod.hook.CancelableHook;
import net.canarymod.hook.entity.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public class EntityBoat extends Entity {

    private boolean a;
    private double b;
    private int c;
    private double d;
    private double e;
    private double f;
    private double g;
    private double h;
   
    public EntityBoat(World world) {
        super(world);
        this.a = true;
        this.b = 0.07D;
        this.k = true;
        this.a(1.5F, 0.6F);
        this.entity = new CanaryBoat(this); // CanaryMod: Wrap Entity
    }

    protected boolean r_() {
        return false;
    }

    protected void h() {
        this.ac.a(17, new Integer(0));
        this.ac.a(18, new Integer(1));
        this.ac.a(19, new Float(0.0F));
    }

    public AxisAlignedBB j(Entity entity) {
        return entity.aQ();
    }

    public AxisAlignedBB S() {
        return this.aQ();
    }

    public boolean ae() {
        return true;
    }

    public EntityBoat(World world, double d0, double d1, double d2) {
        this(world);
        this.b(d0, d1, d2);
        this.v = 0.0D;
        this.w = 0.0D;
        this.x = 0.0D;
        this.p = d0;
        this.q = d1;
        this.r = d2;

        this.entity = new CanaryBoat(this); // CanaryMod: Wrap Entity
    }

    public double an() {
        return (double) this.K * 0.0D - 0.30000001192092896D;
    }

    public boolean a(DamageSource damagesource, float f0) {
        if (this.b(damagesource)) {
            return false;
        } else if (!this.o.D && !this.I) {
            if (this.l != null && this.l == damagesource.j() && damagesource instanceof EntityDamageSourceIndirect) {
                return false;
            } else {
                // CanaryMod: VehicleDamage
                net.canarymod.api.entity.Entity attk = null;

                if (damagesource.i() != null) {
                    attk = damagesource.i().getCanaryEntity();
                }
                VehicleDamageHook hook = (VehicleDamageHook) new VehicleDamageHook((CanaryBoat) this.entity, attk, new CanaryDamageSource(damagesource), (int) f0).call();
                if (hook.isCanceled()) {
                    return false;
                }
                f0 = hook.getDamageDealt();
                //

                this.b(-this.m());
                this.a(10);
                this.a(this.j() + f0 * 10.0F);
                this.ac();
                boolean flag0 = damagesource.j() instanceof EntityPlayer && ((EntityPlayer) damagesource.j()).by.d;

                if (flag0 || this.j() > 40.0F) {
                    if (this.l != null) {
                        this.l.a((Entity) this);
                    }

                    if (!flag0) {
                        this.a(Items.aE, 1, 0.0F);
                    }
                    // CanaryMod: VehicleDestroy
                    new VehicleDestroyHook((Vehicle) this.entity).call();
                    //

                    this.J();
                }

                return true;
            }
        } else {
            return true;
        }
    }

    public boolean ad() {
        return !this.I;
    }

    public void s_() {
        super.s_();
        if (this.l() > 0) {
            this.a(this.l() - 1);
        }

        if (this.j() > 0.0F) {
            this.a(this.j() - 1.0F);
        }

        this.p = this.s;
        this.q = this.t;
        this.r = this.u;
        byte b0 = 5;
        double d0 = 0.0D;

        for (int i0 = 0; i0 < b0; ++i0) {
            double d1 = this.aQ().b + (this.aQ().e - this.aQ().b) * (double) (i0 + 0) / (double) b0 - 0.125D;
            double d2 = this.aQ().b + (this.aQ().e - this.aQ().b) * (double) (i0 + 1) / (double) b0 - 0.125D;
            AxisAlignedBB axisalignedbb = new AxisAlignedBB(this.aQ().a, d1, this.aQ().c, this.aQ().d, d2, this.aQ().f);

            if (this.o.b(axisalignedbb, Material.h)) {
                d0 += 1.0D / (double) b0;
            }
        }

        double d3 = Math.sqrt(this.v * this.v + this.x * this.x);
        double d4;
        double d5;
        int i1;

        if (d3 > 0.2975D) {
            d4 = Math.cos((double) this.y * 3.141592653589793D / 180.0D);
            d5 = Math.sin((double) this.y * 3.141592653589793D / 180.0D);

            for (i1 = 0; (double) i1 < 1.0D + d3 * 60.0D; ++i1) {
                double d6 = (double) (this.V.nextFloat() * 2.0F - 1.0F);
                double d7 = (double) (this.V.nextInt(2) * 2 - 1) * 0.7D;
                double d8;
                double d9;

                if (this.V.nextBoolean()) {
                    d8 = this.s - d4 * d6 * 0.8D + d5 * d7;
                    d9 = this.u - d5 * d6 * 0.8D - d4 * d7;
                    this.o.a(EnumParticleTypes.WATER_SPLASH, d8, this.t - 0.125D, d9, this.v, this.w, this.x, new int[0]);
                } else {
                    d8 = this.s + d4 + d5 * d6 * 0.7D;
                    d9 = this.u + d5 - d4 * d6 * 0.7D;
                    this.o.a(EnumParticleTypes.WATER_SPLASH, d8, this.t - 0.125D, d9, this.v, this.w, this.x, new int[0]);
                }
            }
        }

        double d10;
        double d11;

        if (this.o.D && this.a) {
            if (this.c > 0) {
                d4 = this.s + (this.d - this.s) / (double) this.c;
                d5 = this.t + (this.e - this.t) / (double) this.c;
                d10 = this.u + (this.f - this.u) / (double) this.c;
                d11 = MathHelper.g(this.g - (double) this.y);
                this.y = (float) ((double) this.y + d11 / (double) this.c);
                this.z = (float) ((double) this.z + (this.h - (double) this.z) / (double) this.c);
                --this.c;
                this.b(d4, d5, d10);
                this.b(this.y, this.z);
            } else {
                d4 = this.s + this.v;
                d5 = this.t + this.w;
                d10 = this.u + this.x;
                this.b(d4, d5, d10);
                if (this.C) {
                    this.v *= 0.5D;
                    this.w *= 0.5D;
                    this.x *= 0.5D;
                }

                this.v *= 0.9900000095367432D;
                this.w *= 0.949999988079071D;
                this.x *= 0.9900000095367432D;
            }

        } else {
            if (d0 < 1.0D) {
                d4 = d0 * 2.0D - 1.0D;
                this.w += 0.03999999910593033D * d4;
            } else {
                if (this.w < 0.0D) {
                    this.w /= 2.0D;
                }

                this.w += 0.007000000216066837D;
            }

            if (this.l instanceof EntityLivingBase) {
                EntityLivingBase entitylivingbase = (EntityLivingBase) this.l;
                float f0 = this.l.y + -entitylivingbase.aX * 90.0F;

                this.v += -Math.sin((double) (f0 * 3.1415927F / 180.0F)) * this.b * (double) entitylivingbase.aY * 0.05000000074505806D;
                this.x += Math.cos((double) (f0 * 3.1415927F / 180.0F)) * this.b * (double) entitylivingbase.aY * 0.05000000074505806D;
            }

            d4 = Math.sqrt(this.v * this.v + this.x * this.x);
            if (d4 > 0.35D) {
                d5 = 0.35D / d4;
                this.v *= d5;
                this.x *= d5;
                d4 = 0.35D;
            }

            if (d4 > d3 && this.b < 0.35D) {
                this.b += (0.35D - this.b) / 35.0D;
                if (this.b > 0.35D) {
                    this.b = 0.35D;
                }
            } else {
                this.b -= (this.b - 0.07D) / 35.0D;
                if (this.b < 0.07D) {
                    this.b = 0.07D;
                }
            }

            int i2;

            for (i2 = 0; i2 < 4; ++i2) {
                int i3 = MathHelper.c(this.s + ((double) (i2 % 2) - 0.5D) * 0.8D);

                i1 = MathHelper.c(this.u + ((double) (i2 / 2) - 0.5D) * 0.8D);

                for (int i4 = 0; i4 < 2; ++i4) {
                    int i5 = MathHelper.c(this.t) + i4;
                    BlockPos blockpos = new BlockPos(i3, i5, i1);
                    Block block = this.o.p(blockpos).c();

                    if (block == Blocks.aH) {
                        this.o.g(blockpos);
                        this.D = false;
                    } else if (block == Blocks.bx) {
                        this.o.b(blockpos, true);
                        this.D = false;
                    }
                }
            }

            if (this.C) {
                this.v *= 0.5D;
                this.w *= 0.5D;
                this.x *= 0.5D;
            }

            this.d(this.v, this.w, this.x);
            if (this.D && d3 > 0.2D) {
                if (!this.o.D && !this.I) {
                    this.J();

                    for (i2 = 0; i2 < 3; ++i2) {
                        this.a(Item.a(Blocks.f), 1, 0.0F);
                    }

                    for (i2 = 0; i2 < 2; ++i2) {
                        this.a(Items.y, 1, 0.0F);
                    }
                }
            } else {
                this.v *= 0.9900000095367432D;
                this.w *= 0.949999988079071D;
                this.x *= 0.9900000095367432D;
            }

            this.z = 0.0F;
            d5 = (double) this.y;
            d10 = this.p - this.s;
            d11 = this.r - this.u;
            if (d10 * d10 + d11 * d11 > 0.001D) {
                d5 = (double) ((float) (Math.atan2(d11, d10) * 180.0D / 3.141592653589793D));
            }

            double d12 = MathHelper.g(d5 - (double) this.y);

            if (d12 > 20.0D) {
                d12 = 20.0D;
            }

            if (d12 < -20.0D) {
                d12 = -20.0D;
            }

            this.y = (float) ((double) this.y + d12);
            this.b(this.y, this.z);
            // CanaryMod: VehicleMove
            Vector3D from = new Vector3D(this.p, this.q, this.r);
            Vector3D to = new Vector3D(this.s, this.t, this.u);
            if (hasMovedOneBlockOrMore()) {
                VehicleMoveHook vmh = (VehicleMoveHook) new VehicleMoveHook((Vehicle) this.entity, from, to).call();
                if (vmh.isCanceled()) {
                    this.v = 0.0D;
                    this.w = 0.0D;
                    this.x = 0.0D;
                    this.b(this.p, this.q, this.r, this.y, this.z);
                    this.p = ppX;
                    this.q = ppY;
                    this.r = ppZ;
                    this.y = prevRot;
                    this.z = prevPit;
                    this.al(); // Update rider
                }
            }
            //
            if (!this.o.E) {
                List list = this.o.b((Entity) this, this.C.b(0.20000000298023224D, 0.0D, 0.20000000298023224D));

            if (!this.o.D) {
                List list = this.o.b((Entity) this, this.aQ().b(0.20000000298023224D, 0.0D, 0.20000000298023224D));

                if (list != null && !list.isEmpty()) {
                    for (int i6 = 0; i6 < list.size(); ++i6) {
                        Entity entity = (Entity) list.get(i6);

                        if (entity != this.l && entity.ae() && entity instanceof EntityBoat) {
                            // CanaryMod: VehicleCollision
                            VehicleCollisionHook vch = (VehicleCollisionHook) new VehicleCollisionHook((Vehicle) this.entity, entity.getCanaryEntity()).call();
                            if (!vch.isCanceled()) {
                                entity.i(this);
                            }
                            //
                        }
                    }
                }

                if (this.l != null && this.l.I) {
                    this.l = null;
                }

            }
        }
    }

    public void al() {
        if (this.l != null) {
            double d0 = Math.cos((double) this.y * 3.141592653589793D / 180.0D) * 0.4D;
            double d1 = Math.sin((double) this.y * 3.141592653589793D / 180.0D) * 0.4D;

            this.l.b(this.s + d0, this.t + this.an() + this.l.am(), this.u + d1);
        }
    }

    protected void b(NBTTagCompound nbttagcompound) {}

    protected void a(NBTTagCompound nbttagcompound) {}

    public boolean e(EntityPlayer entityplayer) {
        if (this.l != null && this.l instanceof EntityPlayer && this.l != entityplayer) {
            return true;
        } else {
            if (!this.o.D) {
                // CanaryMod: VehicleEnter/VehicleExit
                CancelableHook hook = null;

                if (this.l == null) {
                    hook = new VehicleEnterHook((Vehicle) this.entity, (LivingBase) entityplayer.getCanaryEntity());
                } else if (this.l == entityplayer) {
                    hook = new VehicleExitHook((Vehicle) this.entity, (LivingBase) entityplayer.getCanaryEntity());
                }
                if (hook != null) {
                    hook.call();
                    if (!hook.isCanceled()) {
                        entityplayer.a((Entity) this);
                    }
                }
                //
            }

            return true;
        }
    }

    protected void a(double d0, boolean flag0, Block block, BlockPos blockpos) {
        if (flag0) {
            if (this.O > 3.0F) {
                this.e(this.O, 1.0F);
                if (!this.o.D && !this.I) {
                    this.J();

                    int i0;

                    for (i0 = 0; i0 < 3; ++i0) {
                        this.a(Item.a(Blocks.f), 1, 0.0F);
                    }

                    for (i0 = 0; i0 < 2; ++i0) {
                        this.a(Items.y, 1, 0.0F);
                    }
                }

                this.O = 0.0F;
            }
        } else if (this.o.p((new BlockPos(this)).b()).c().r() != Material.h && d0 < 0.0D) {
            this.O = (float) ((double) this.O - d0);
        }

    }

    public void a(float f0) {
        this.ac.b(19, Float.valueOf(f0));
    }

    public float j() {
        return this.ac.d(19);
    }

    public void a(int i0) {
        this.ac.b(17, Integer.valueOf(i0));
    }

    public int l() {
        return this.ac.c(17);
    }

    public void b(int i0) {
        this.ac.b(18, Integer.valueOf(i0));
    }

    public int m() {
        return this.ac.c(18);
    }
}
