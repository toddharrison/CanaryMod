package net.minecraft.entity;

import net.canarymod.ToolBox;
import net.canarymod.api.CanaryDamageSource;
import net.canarymod.api.entity.CanaryEntity;
import net.canarymod.api.entity.EntityType;
import net.canarymod.api.entity.living.LivingBase;
import net.canarymod.api.entity.vehicle.Vehicle;
import net.canarymod.api.nbt.CanaryCompoundTag;
import net.canarymod.api.nbt.CompoundTag;
import net.canarymod.api.world.CanaryWorld;
import net.canarymod.api.world.position.Location;
import net.canarymod.api.world.position.Vector3D;
import net.canarymod.config.Configuration;
import net.canarymod.hook.CancelableHook;
import net.canarymod.hook.entity.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.*;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Callable;

public abstract class Entity {

    private static int b;
    private int c;
    public double j;
    public boolean k;
    public Entity l;
    public Entity m;
    public boolean n;
    public World o;
    public double p; // Previous Position X
    public double q; // Previous Position Y
    public double r; // Previous Position Z
    public double s; // Position X
    public double t; // Position Y
    public double u; // Position Z
    public double v; // Motion X
    public double w; // Motion Y
    public double x; // Motion Z
    public float y; // Rotation
    public float z; // Pitch
    public float A; // previous Yaw
    public float B; // previous Pitch
    public final AxisAlignedBB C;
    public boolean D;
    public boolean E;
    public boolean F;
    public boolean G;
    public boolean H;// Velocity Changed
    protected boolean I; // In Web
    public boolean J;
    public boolean K; //DEAD
    public float L;
    public float M;
    public float N;
    public float O;
    public float P;
    public float Q;
    public float R;
    private int d;
    public double S;
    public double T;
    public double U;
    public float V;
    public float W;
    public boolean X;
    public float Y;
    protected Random Z;
    public int aa;
    public int ab;
    public int e; // CanaryMod: private -> public (FireTicks)
    protected boolean ac;
    public int ad;
    private boolean f;
    protected boolean ae;
    protected DataWatcher af;
    private double g;
    private double h;
    public boolean ag;
    public int ah;
    public int ai;
    public int aj;
    public boolean ak;
    public boolean al;
    public int am;
    protected boolean an;
    protected int ao;
    public int ap;
    protected int aq;
    private boolean i;
    protected UUID ar;
    public EnumEntitySize as;

    // CanaryMod
    protected CanaryEntity entity;
    protected CompoundTag metadata = null;
    //

    public int y() {
        return this.c;
    }

    public void d(int i0) {
        this.c = i0;
    }

    public Entity(World world) {
        this.c = b++;
        this.j = 1.0D;
        this.C = AxisAlignedBB.a(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
        this.J = true;
        this.M = 0.6F;
        this.N = 1.8F;
        this.d = 1;
        this.Z = new Random();
        this.ab = 1;
        this.f = true;
        this.ar = UUID.randomUUID();
        this.as = EnumEntitySize.SIZE_2;
        this.o = world;
        this.b(0.0D, 0.0D, 0.0D);
        if (world != null) {
            this.ap = world.t.i;
        }

        this.af = new DataWatcher(this);
        this.af.a(0, Byte.valueOf((byte) 0));
        this.af.a(1, Short.valueOf((short) 300));
        this.c();

        entity = new CanaryEntity(this) {

            @Override
            public Entity getHandle() {
                return entity;
            }

            @Override
            public EntityType getEntityType() {
                return null;
            }

            @Override
            public String getFqName() {
                return "Entity";
            }
        };
    }

    protected abstract void c();

    public DataWatcher z() {
        return this.af;
    }

    public boolean equals(Object object) {
        return object instanceof Entity ? ((Entity) object).c == this.c : false;
    }

    public int hashCode() {
        return this.c;
    }

    public void B() {
        this.K = true;
    }

    protected void a(float f0, float f1) {
        float f2;

        if (f0 != this.M || f1 != this.N) {
            f2 = this.M;
            this.M = f0;
            this.N = f1;
            this.C.d = this.C.a + (double) this.M;
            this.C.f = this.C.c + (double) this.M;
            this.C.e = this.C.b + (double) this.N;
            if (this.M > f2 && !this.f && !this.o.E) {
                this.d((double) (f2 - this.M), 0.0D, (double) (f2 - this.M));
            }
        }

        f2 = f0 % 2.0F;
        if ((double) f2 < 0.375D) {
            this.as = Entity.EnumEntitySize.SIZE_1;
        } else if ((double) f2 < 0.75D) {
            this.as = Entity.EnumEntitySize.SIZE_2;
        } else if ((double) f2 < 1.0D) {
            this.as = Entity.EnumEntitySize.SIZE_3;
        } else if ((double) f2 < 1.375D) {
            this.as = Entity.EnumEntitySize.SIZE_4;
        } else if ((double) f2 < 1.75D) {
            this.as = Entity.EnumEntitySize.SIZE_5;
        } else {
            this.as = Entity.EnumEntitySize.SIZE_6;
        }
    }

    protected void b(float f0, float f1) {
        this.y = f0 % 360.0F;
        this.z = f1 % 360.0F;
    }

    public void b(double d0, double d1, double d2) {
        this.s = d0;
        this.t = d1;
        this.u = d2;
        float f0 = this.M / 2.0F;
        float f1 = this.N;

        this.C.b(d0 - (double) f0, d1 - (double) this.L + (double) this.V, d2 - (double) f0, d0 + (double) f0, d1 - (double) this.L + (double) this.V + (double) f1, d2 + (double) f0);
    }

    public void h() {
        this.C();
    }

    public void C() {
        this.o.C.a("entityBaseTick");
        if (this.m != null && this.m.K) {
            this.m = null;
        }

        this.O = this.P;
        this.p = this.s;
        this.q = this.t;
        this.r = this.u;
        this.B = this.z;
        this.A = this.y;
        int i0;

        if (!this.o.E && this.o instanceof WorldServer) {
            this.o.C.a("portal");

            i0 = this.D();
            if (this.an) {
                // CanaryMod moved allow-nether to per-world config
                if (Configuration.getWorldConfig(getCanaryWorld().getFqName()).isNetherAllowed()) {
                    if (this.m == null && this.ao++ >= i0) {
                        this.ao = i0;
                        this.am = this.ai();
                        byte b0;

                        if (this.o.t.i == -1) {
                            b0 = 0;
                        } else {
                            b0 = -1;
                        }

                        this.b(b0);
                    }

                    this.an = false;
                }
            } else {
                if (this.ao > 0) {
                    this.ao -= 4;
                }

                if (this.ao < 0) {
                    this.ao = 0;
                }
            }

            if (this.am > 0) {
                --this.am;
            }

            this.o.C.b();
        }

        if (this.ao() && !this.M()) {
            int i1 = MathHelper.c(this.s);

            i0 = MathHelper.c(this.t - 0.20000000298023224D - (double) this.L);
            int i2 = MathHelper.c(this.u);
            Block block = this.o.a(i1, i0, i2);

            if (block.o() != Material.a) {
                this.o.a("blockcrack_" + Block.b(block) + "_" + this.o.e(i1, i0, i2), this.s + ((double) this.Z.nextFloat() - 0.5D) * (double) this.M, this.C.b + 0.1D, this.u + ((double) this.Z.nextFloat() - 0.5D) * (double) this.M, -this.v * 4.0D, 1.5D, -this.x * 4.0D);
            }
        }

        this.N();
        if (this.o.E) {
            this.e = 0;
        } else if (this.e > 0) {
            if (this.ae) {
                this.e -= 4;
                if (this.e < 0) {
                    this.e = 0;
                }
            } else {
                if (this.e % 20 == 0) {
                    // CanaryMod: call DamageHook (FireTick)
                    DamageHook hook = (DamageHook) new DamageHook(null, entity, new CanaryDamageSource(DamageSource.b), 1.0F).call();
                    if (!hook.isCanceled()) {
                        this.a((((CanaryDamageSource) hook.getDamageSource()).getHandle()), hook.getDamageDealt());
                    }
                    //
                }

                --this.e;
            }
        }

        if (this.P()) {
            this.E();
            this.R *= 0.5F;
        }

        if (this.t < -64.0D) {
            this.G();
        }

        if (!this.o.E) {
            this.a(0, this.e > 0);
        }

        this.f = false;
        this.o.C.b();
    }

    public int D() {
        return 0;
    }

    protected void E() {
        if (!this.ae) {
            // CanaryMod: call DamageHook (Lava)
            DamageHook hook = (DamageHook) new DamageHook(null, entity, new CanaryDamageSource(DamageSource.c), 4.0F).call();
            if (!hook.isCanceled()) {
                this.a((((CanaryDamageSource) hook.getDamageSource()).getHandle()), hook.getDamageDealt());
                this.e(15);
            }
            //
        }
    }

    public void e(int i0) {
        int i1 = i0 * 20;

        i1 = EnchantmentProtection.a(this, i1);
        if (this.e < i1) {
            this.e = i1;
        }
    }

    public void F() {
        this.e = 0;
    }

    protected void G() {
        this.B();
    }

    public boolean c(double d0, double d1, double d2) {
        AxisAlignedBB axisalignedbb = this.C.c(d0, d1, d2);
        List list = this.o.a(this, axisalignedbb);

        return !list.isEmpty() ? false : !this.o.d(axisalignedbb);
    }

    public void d(double d0, double d1, double d2) {
        if (this.X) {
            this.C.d(d0, d1, d2);
            this.s = (this.C.a + this.C.d) / 2.0D;
            this.t = this.C.b + (double) this.L - (double) this.V;
            this.u = (this.C.c + this.C.f) / 2.0D;
        } else {
            // CanaryMod:
            float prevPR = this.B, prevPP = this.A;
            double prevPX = this.p, prevPY = this.q, prevPZ = this.r;
            //
            this.o.C.a("move");
            this.V *= 0.4F;
            double d3 = this.s;
            double d4 = this.t;
            double d5 = this.u;

            if (this.I) {
                this.I = false;
                d0 *= 0.25D;
                d1 *= 0.05000000074505806D;
                d2 *= 0.25D;
                this.v = 0.0D;
                this.w = 0.0D;
                this.x = 0.0D;
            }

            double d6 = d0;
            double d7 = d1;
            double d8 = d2;
            AxisAlignedBB axisalignedbb = this.C.b();
            boolean flag0 = this.D && this.an() && this instanceof EntityPlayer;

            if (flag0) {
                double d9;

                for (d9 = 0.05D; d0 != 0.0D && this.o.a(this, this.C.c(d0, -1.0D, 0.0D)).isEmpty(); d6 = d0) {
                    if (d0 < d9 && d0 >= -d9) {
                        d0 = 0.0D;
                    } else if (d0 > 0.0D) {
                        d0 -= d9;
                    } else {
                        d0 += d9;
                    }
                }

                for (; d2 != 0.0D && this.o.a(this, this.C.c(0.0D, -1.0D, d2)).isEmpty(); d8 = d2) {
                    if (d2 < d9 && d2 >= -d9) {
                        d2 = 0.0D;
                    } else if (d2 > 0.0D) {
                        d2 -= d9;
                    } else {
                        d2 += d9;
                    }
                }

                while (d0 != 0.0D && d2 != 0.0D && this.o.a(this, this.C.c(d0, -1.0D, d2)).isEmpty()) {
                    if (d0 < d9 && d0 >= -d9) {
                        d0 = 0.0D;
                    } else if (d0 > 0.0D) {
                        d0 -= d9;
                    } else {
                        d0 += d9;
                    }

                    if (d2 < d9 && d2 >= -d9) {
                        d2 = 0.0D;
                    } else if (d2 > 0.0D) {
                        d2 -= d9;
                    } else {
                        d2 += d9;
                    }

                    d6 = d0;
                    d8 = d2;
                }
            }

            List list = this.o.a(this, this.C.a(d0, d1, d2));

            for (int i0 = 0; i0 < list.size(); ++i0) {
                d1 = ((AxisAlignedBB) list.get(i0)).b(this.C, d1);
            }

            this.C.d(0.0D, d1, 0.0D);
            if (!this.J && d7 != d1) {
                d2 = 0.0D;
                d1 = 0.0D;
                d0 = 0.0D;
            }

            boolean flag1 = this.D || d7 != d1 && d7 < 0.0D;

            int i1;

            for (i1 = 0; i1 < list.size(); ++i1) {
                d0 = ((AxisAlignedBB) list.get(i1)).a(this.C, d0);
            }

            this.C.d(d0, 0.0D, 0.0D);
            if (!this.J && d6 != d0) {
                d2 = 0.0D;
                d1 = 0.0D;
                d0 = 0.0D;
            }

            for (i1 = 0; i1 < list.size(); ++i1) {
                d2 = ((AxisAlignedBB) list.get(i1)).c(this.C, d2);
            }

            this.C.d(0.0D, 0.0D, d2);
            if (!this.J && d8 != d2) {
                d2 = 0.0D;
                d1 = 0.0D;
                d0 = 0.0D;
            }

            double d10;
            double d11;
            int i2;
            double d12;

            if (this.W > 0.0F && flag1 && (flag0 || this.V < 0.05F) && (d6 != d0 || d8 != d2)) {
                d12 = d0;
                d10 = d1;
                d11 = d2;
                d0 = d6;
                d1 = (double) this.W;
                d2 = d8;
                AxisAlignedBB axisalignedbb1 = this.C.b();

                this.C.d(axisalignedbb);
                list = this.o.a(this, this.C.a(d6, d1, d8));

                for (i2 = 0; i2 < list.size(); ++i2) {
                    d1 = ((AxisAlignedBB) list.get(i2)).b(this.C, d1);
                }

                this.C.d(0.0D, d1, 0.0D);
                if (!this.J && d7 != d1) {
                    d2 = 0.0D;
                    d1 = 0.0D;
                    d0 = 0.0D;
                }

                for (i2 = 0; i2 < list.size(); ++i2) {
                    d0 = ((AxisAlignedBB) list.get(i2)).a(this.C, d0);
                }

                this.C.d(d0, 0.0D, 0.0D);
                if (!this.J && d6 != d0) {
                    d2 = 0.0D;
                    d1 = 0.0D;
                    d0 = 0.0D;
                }

                for (i2 = 0; i2 < list.size(); ++i2) {
                    d2 = ((AxisAlignedBB) list.get(i2)).c(this.C, d2);
                }

                this.C.d(0.0D, 0.0D, d2);
                if (!this.J && d8 != d2) {
                    d2 = 0.0D;
                    d1 = 0.0D;
                    d0 = 0.0D;
                }

                if (!this.J && d7 != d1) {
                    d2 = 0.0D;
                    d1 = 0.0D;
                    d0 = 0.0D;
                } else {
                    d1 = (double) (-this.W);

                    for (i2 = 0; i2 < list.size(); ++i2) {
                        d1 = ((AxisAlignedBB) list.get(i2)).b(this.C, d1);
                    }

                    this.C.d(0.0D, d1, 0.0D);
                }

                if (d12 * d12 + d11 * d11 >= d0 * d0 + d2 * d2) {
                    d0 = d12;
                    d1 = d10;
                    d2 = d11;
                    this.C.d(axisalignedbb1);
                }
            }

            this.o.C.b();
            this.o.C.a("rest");
            this.s = (this.C.a + this.C.d) / 2.0D;
            this.t = this.C.b + (double) this.L - (double) this.V;
            this.u = (this.C.c + this.C.f) / 2.0D;
            this.E = d6 != d0 || d8 != d2;
            this.F = d7 != d1;
            this.D = d7 != d1 && d7 < 0.0D;
            this.G = this.E || this.F;
            this.a(d1, this.D);
            // CanaryMod: EntityMoveHook
            Location vecFrom = new Location(getCanaryWorld(), this.p, this.q, this.r, this.B, this.A);
            Vector3D vecTo = new Vector3D(this.s, this.t, this.u);
            if (!(this instanceof EntityPlayerMP) && hasMovedOneBlockOrMore()) {
                boolean pHflag = this instanceof EntityPig || this instanceof EntityHorse;
                if (pHflag && this.l != null && this.l instanceof EntityPlayerMP) {
                    // Its an Animal Vehicle!
                    // CanaryMod: VehcileMoveHook (Pig/Horse) --
                    VehicleMoveHook vmh = (VehicleMoveHook) new VehicleMoveHook((Vehicle) this.entity, vecFrom, vecTo).call();
                    // Remember rotation and pitch are swapped in Location constructor...
                    EntityMoveHook emh = (EntityMoveHook) new EntityMoveHook(entity, vecFrom).call();
                    if (vmh.isCanceled() || emh.isCanceled()) {
                        this.v = 0.0D;
                        this.w = 0.0D;
                        this.x = 0.0D;
                        this.b(this.p, this.q, this.r, this.A, this.B);
                        this.p = prevPX;
                        this.q = prevPY;
                        this.r = prevPZ;
                        this.A = prevPR;
                        this.B = prevPP;
                        this.ac(); //Update Rider
                    }
                } else {
                    EntityMoveHook hook = (EntityMoveHook) new EntityMoveHook(entity, vecFrom).call();
                    if (hook.isCanceled()) {
                        this.v = 0.0D;
                        this.w = 0.0D;
                        this.x = 0.0D;
                        this.b(this.p, this.q, this.r, this.A, this.B);
                        this.p = prevPX;
                        this.q = prevPY;
                        this.r = prevPZ;
                        this.A = prevPR;
                        this.B = prevPP;
                        this.ac(); //Update Rider
                    }
                }
            }
            //
            if (d6 != d0) {
                this.v = 0.0D;
            }

            if (d7 != d1) {
                this.w = 0.0D;
            }

            if (d8 != d2) {
                this.x = 0.0D;
            }

            d12 = this.s - d3;
            d10 = this.t - d4;
            d11 = this.u - d5;
            if (this.g_() && !flag0 && this.m == null) {
                int i3 = MathHelper.c(this.s);

                i2 = MathHelper.c(this.t - 0.20000000298023224D - (double) this.L);
                int i4 = MathHelper.c(this.u);
                Block block = this.o.a(i3, i2, i4);
                int i5 = this.o.a(i3, i2 - 1, i4).b();

                if (i5 == 11 || i5 == 32 || i5 == 21) {
                    block = this.o.a(i3, i2 - 1, i4);
                }

                if (block != Blocks.ap) {
                    d10 = 0.0D;
                }

                this.P = (float) ((double) this.P + (double) MathHelper.a(d12 * d12 + d11 * d11) * 0.6D);
                this.Q = (float) ((double) this.Q + (double) MathHelper.a(d12 * d12 + d10 * d10 + d11 * d11) * 0.6D);
                if (this.Q > (float) this.d && block.o() != Material.a) {
                    this.d = (int) this.Q + 1;
                    if (this.M()) {
                        float f0 = MathHelper.a(this.v * this.v * 0.20000000298023224D + this.w * this.w + this.x * this.x * 0.20000000298023224D) * 0.35F;

                        if (f0 > 1.0F) {
                            f0 = 1.0F;
                        }

                        this.a(this.H(), f0, 1.0F + (this.Z.nextFloat() - this.Z.nextFloat()) * 0.4F);
                    }

                    this.a(i3, i2, i4, block);
                    block.b(this.o, i3, i2, i4, this);
                }
            }

            try {
                this.I();
            } catch (Throwable throwable) {
                CrashReport crashreport = CrashReport.a(throwable, "Checking entity block collision");
                CrashReportCategory crashreportcategory = crashreport.a("Entity being checked for collision");

                this.a(crashreportcategory);
                throw new ReportedException(crashreport);
            }

            boolean flag2 = this.L();

            if (this.o.e(this.C.e(0.001D, 0.001D, 0.001D))) {
                this.f(1);
                if (!flag2) {
                    ++this.e;
                    if (this.e == 0) {
                        this.e(8);
                    }
                }
            } else if (this.e <= 0) {
                this.e = -this.ab;
            }

            if (flag2 && this.e > 0) {
                this.a("random.fizz", 0.7F, 1.6F + (this.Z.nextFloat() - this.Z.nextFloat()) * 0.4F);
                this.e = -this.ab;
            }

            this.o.C.b();
        }
    }

    protected String H() {
        return "game.neutral.swim";
    }

    protected void I() {
        int i0 = MathHelper.c(this.C.a + 0.001D);
        int i1 = MathHelper.c(this.C.b + 0.001D);
        int i2 = MathHelper.c(this.C.c + 0.001D);
        int i3 = MathHelper.c(this.C.d - 0.001D);
        int i4 = MathHelper.c(this.C.e - 0.001D);
        int i5 = MathHelper.c(this.C.f - 0.001D);

        if (this.o.b(i0, i1, i2, i3, i4, i5)) {
            for (int i6 = i0; i6 <= i3; ++i6) {
                for (int i7 = i1; i7 <= i4; ++i7) {
                    for (int i8 = i2; i8 <= i5; ++i8) {
                        Block block = this.o.a(i6, i7, i8);

                        try {
                            block.a(this.o, i6, i7, i8, this);
                        } catch (Throwable throwable) {
                            CrashReport crashreport = CrashReport.a(throwable, "Colliding entity with block");
                            CrashReportCategory crashreportcategory = crashreport.a("Block being collided with");

                            CrashReportCategory.a(crashreportcategory, i6, i7, i8, block, this.o.e(i6, i7, i8));
                            throw new ReportedException(crashreport);
                        }
                    }
                }
            }
        }
    }

    protected void a(int i0, int i1, int i2, Block block) {
        Block.SoundType block_soundtype = block.H;

        if (this.o.a(i0, i1 + 1, i2) == Blocks.aC) {
            block_soundtype = Blocks.aC.H;
            this.a(block_soundtype.e(), block_soundtype.c() * 0.15F, block_soundtype.d());
        } else if (!block.o().d()) {
            this.a(block_soundtype.e(), block_soundtype.c() * 0.15F, block_soundtype.d());
        }
    }

    public void a(String s0, float f0, float f1) {
        this.o.a(this, s0, f0, f1);
    }

    protected boolean g_() {
        return true;
    }

    protected void a(double d0, boolean flag0) {
        if (flag0) {
            if (this.R > 0.0F) {
                this.b(this.R);
                this.R = 0.0F;
            }
        } else if (d0 < 0.0D) {
            this.R = (float) ((double) this.R - d0);
        }
    }

    public AxisAlignedBB J() {
        return null;
    }

    protected void f(int i0) {
        if (!this.ae) {
            // CanaryMod: call DamageHook (onfire)
            DamageHook hook = (DamageHook) new DamageHook(null, entity, new CanaryDamageSource(DamageSource.a), i0).call();
            if (!hook.isCanceled()) {
                this.a((((CanaryDamageSource) hook.getDamageSource()).getHandle()), hook.getDamageDealt());
            }
            //
        }
    }

    public final boolean K() {
        return this.ae;
    }

    protected void b(float f0) {
        if (this.l != null) {
            this.l.b(f0);
        }
    }

    public boolean L() {
        return this.ac || this.o.y(MathHelper.c(this.s), MathHelper.c(this.t), MathHelper.c(this.u)) || this.o.y(MathHelper.c(this.s), MathHelper.c(this.t + (double) this.N), MathHelper.c(this.u));
    }

    public boolean M() {
        return this.ac;
    }

    public boolean N() {
        if (this.o.a(this.C.b(0.0D, -0.4000000059604645D, 0.0D).e(0.001D, 0.001D, 0.001D), Material.h, this)) {
            if (!this.ac && !this.f) {
                float f0 = MathHelper.a(this.v * this.v * 0.20000000298023224D + this.w * this.w + this.x * this.x * 0.20000000298023224D) * 0.2F;

                if (f0 > 1.0F) {
                    f0 = 1.0F;
                }

                this.a(this.O(), f0, 1.0F + (this.Z.nextFloat() - this.Z.nextFloat()) * 0.4F);
                float f1 = (float) MathHelper.c(this.C.b);

                int i0;
                float f2;
                float f3;

                for (i0 = 0; (float) i0 < 1.0F + this.M * 20.0F; ++i0) {
                    f2 = (this.Z.nextFloat() * 2.0F - 1.0F) * this.M;
                    f3 = (this.Z.nextFloat() * 2.0F - 1.0F) * this.M;
                    this.o.a("bubble", this.s + (double) f2, (double) (f1 + 1.0F), this.u + (double) f3, this.v, this.w - (double) (this.Z.nextFloat() * 0.2F), this.x);
                }

                for (i0 = 0; (float) i0 < 1.0F + this.M * 20.0F; ++i0) {
                    f2 = (this.Z.nextFloat() * 2.0F - 1.0F) * this.M;
                    f3 = (this.Z.nextFloat() * 2.0F - 1.0F) * this.M;
                    this.o.a("splash", this.s + (double) f2, (double) (f1 + 1.0F), this.u + (double) f3, this.v, this.w, this.x);
                }
            }

            this.R = 0.0F;
            this.ac = true;
            this.e = 0;
        } else {
            this.ac = false;
        }

        return this.ac;
    }

    protected String O() {
        return "game.neutral.swim.splash";
    }

    public boolean a(Material material) {
        double d0 = this.t + (double) this.g();
        int i0 = MathHelper.c(this.s);
        int i1 = MathHelper.d((float) MathHelper.c(d0));
        int i2 = MathHelper.c(this.u);
        Block block = this.o.a(i0, i1, i2);

        if (block.o() == material) {
            float f0 = BlockLiquid.b(this.o.e(i0, i1, i2)) - 0.11111111F;
            float f1 = (float) (i1 + 1) - f0;

            return d0 < (double) f1;
        } else {
            return false;
        }
    }

    public float g() {
        return 0.0F;
    }

    public boolean P() {
        return this.o.a(this.C.b(-0.10000000149011612D, -0.4000000059604645D, -0.10000000149011612D), Material.i);
    }

    public void a(float f0, float f1, float f2) {
        float f3 = f0 * f0 + f1 * f1;

        if (f3 >= 1.0E-4F) {
            f3 = MathHelper.c(f3);
            if (f3 < 1.0F) {
                f3 = 1.0F;
            }

            f3 = f2 / f3;
            f0 *= f3;
            f1 *= f3;
            float f4 = MathHelper.a(this.y * 3.1415927F / 180.0F);
            float f5 = MathHelper.b(this.y * 3.1415927F / 180.0F);

            this.v += (double) (f0 * f5 - f1 * f4);
            this.x += (double) (f1 * f5 + f0 * f4);
        }
    }

    public float d(float f0) {
        int i0 = MathHelper.c(this.s);
        int i1 = MathHelper.c(this.u);

        if (this.o.d(i0, 0, i1)) {
            double d0 = (this.C.e - this.C.b) * 0.66D;
            int i2 = MathHelper.c(this.t - (double) this.L + d0);

            return this.o.n(i0, i2, i1);
        } else {
            return 0.0F;
        }
    }

    public void a(World world) {
        this.o = world;
    }

    public void a(double d0, double d1, double d2, float f0, float f1) {
        this.p = this.s = d0;
        this.q = this.t = d1;
        this.r = this.u = d2;
        this.A = this.y = f0;
        this.B = this.z = f1;
        this.V = 0.0F;
        double d3 = (double) (this.A - f0);

        if (d3 < -180.0D) {
            this.A += 360.0F;
        }

        if (d3 >= 180.0D) {
            this.A -= 360.0F;
        }

        this.b(this.s, this.t, this.u);
        this.b(f0, f1);
    }

    public void b(double d0, double d1, double d2, float f0, float f1) {
        this.S = this.p = this.s = d0;
        this.T = this.q = this.t = d1 + (double) this.L;
        this.U = this.r = this.u = d2;
        this.y = f0;
        this.z = f1;
        this.b(this.s, this.t, this.u);
    }

    public float e(Entity entity) {
        float f0 = (float) (this.s - entity.s);
        float f1 = (float) (this.t - entity.t);
        float f2 = (float) (this.u - entity.u);

        return MathHelper.c(f0 * f0 + f1 * f1 + f2 * f2);
    }

    public double e(double d0, double d1, double d2) {
        double d3 = this.s - d0;
        double d4 = this.t - d1;
        double d5 = this.u - d2;

        return d3 * d3 + d4 * d4 + d5 * d5;
    }

    public double f(double d0, double d1, double d2) {
        double d3 = this.s - d0;
        double d4 = this.t - d1;
        double d5 = this.u - d2;

        return (double) MathHelper.a(d3 * d3 + d4 * d4 + d5 * d5);
    }

    public double f(Entity entity) {
        double d0 = this.s - entity.s;
        double d1 = this.t - entity.t;
        double d2 = this.u - entity.u;

        return d0 * d0 + d1 * d1 + d2 * d2;
    }

    public void b_(EntityPlayer entityplayer) {
    }

    public void g(Entity entity) {
        if (entity.l != this && entity.m != this) {
            double d0 = entity.s - this.s;
            double d1 = entity.u - this.u;
            double d2 = MathHelper.a(d0, d1);

            if (d2 >= 0.009999999776482582D) {
                d2 = (double) MathHelper.a(d2);
                d0 /= d2;
                d1 /= d2;
                double d3 = 1.0D / d2;

                if (d3 > 1.0D) {
                    d3 = 1.0D;
                }

                d0 *= d3;
                d1 *= d3;
                d0 *= 0.05000000074505806D;
                d1 *= 0.05000000074505806D;
                d0 *= (double) (1.0F - this.Y);
                d1 *= (double) (1.0F - this.Y);
                this.g(-d0, 0.0D, -d1);
                entity.g(d0, 0.0D, d1);
            }
        }
    }

    public void g(double d0, double d1, double d2) {
        this.v += d0;
        this.w += d1;
        this.x += d2;
        this.al = true;
    }

    protected void Q() {
        this.H = true;
    }

    public boolean a(DamageSource damagesource, float f0) {
        if (this.aw()) {
            return false;
        } else {
            this.Q();
            return false;
        }
    }

    public boolean R() {
        return false;
    }

    public boolean S() {
        return false;
    }

    public void b(Entity entity, int i0) {
    }

    public boolean c(NBTTagCompound nbttagcompound) {
        String s0 = this.W();

        if (!this.K && s0 != null) {
            nbttagcompound.a("id", s0);
            this.e(nbttagcompound);
            return true;
        } else {
            return false;
        }
    }

    public boolean d(NBTTagCompound nbttagcompound) {
        String s0 = this.W();

        if (!this.K && s0 != null && this.l == null) {
            nbttagcompound.a("id", s0);
            this.e(nbttagcompound);
            return true;
        } else {
            return false;
        }
    }

    public void e(NBTTagCompound nbttagcompound) {
        try {
            nbttagcompound.a("Pos", (NBTBase) this.a(new double[]{this.s, this.t + (double) this.V, this.u}));
            nbttagcompound.a("Motion", (NBTBase) this.a(new double[]{this.v, this.w, this.x}));
            nbttagcompound.a("Rotation", (NBTBase) this.a(new float[]{this.y, this.z}));
            nbttagcompound.a("FallDistance", this.R);
            nbttagcompound.a("Fire", (short) this.e);
            nbttagcompound.a("Air", (short) this.ar());
            nbttagcompound.a("OnGround", this.D);
            nbttagcompound.a("Dimension", this.ap);
            nbttagcompound.a("Invulnerable", this.i);
            nbttagcompound.a("PortalCooldown", this.am);
            nbttagcompound.a("UUIDMost", this.aB().getMostSignificantBits());
            nbttagcompound.a("UUIDLeast", this.aB().getLeastSignificantBits());
            // CanaryMod add level name
            nbttagcompound.a("LevelName", getCanaryWorld().getName());
            this.b(nbttagcompound); //  this method should remain before metadata saving. EntityPlayer has an update to add before saving is to be completed
            // CanaryMod: allow the saving of persistent metadata
            if (metadata != null) {
                nbttagcompound.a("Canary", ((CanaryCompoundTag) metadata).getHandle());
            } // CanaryMod end
            if (this.m != null) {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();

                if (this.m.c(nbttagcompound1)) {
                    nbttagcompound.a("Riding", (NBTBase) nbttagcompound1);
                }
            }
        } catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.a(throwable, "Saving entity NBT");
            CrashReportCategory crashreportcategory = crashreport.a("Entity being saved");

            this.a(crashreportcategory);
            throw new ReportedException(crashreport);
        }
    }

    public void f(NBTTagCompound nbttagcompound) {
        try {
            NBTTagList nbttaglist = nbttagcompound.c("Pos", 6);
            NBTTagList nbttaglist1 = nbttagcompound.c("Motion", 6);
            NBTTagList nbttaglist2 = nbttagcompound.c("Rotation", 5);

            this.v = nbttaglist1.d(0);
            this.w = nbttaglist1.d(1);
            this.x = nbttaglist1.d(2);
            if (Math.abs(this.v) > 10.0D) {
                this.v = 0.0D;
            }
            if (Math.abs(this.w) > 10.0D) {
                this.w = 0.0D;
            }
            if (Math.abs(this.x) > 10.0D) {
                this.x = 0.0D;
            }

            this.p = this.S = this.s = nbttaglist.d(0);
            this.q = this.T = this.t = nbttaglist.d(1);
            this.r = this.U = this.u = nbttaglist.d(2);
            this.A = this.y = nbttaglist2.e(0);
            this.B = this.z = nbttaglist2.e(1);
            this.R = nbttagcompound.h("FallDistance");
            this.e = nbttagcompound.e("Fire");
            this.h(nbttagcompound.e("Air"));
            this.D = nbttagcompound.n("OnGround");
            this.ap = nbttagcompound.f("Dimension");
            this.i = nbttagcompound.n("Invulnerable");
            this.am = nbttagcompound.f("PortalCooldown");
            if (nbttagcompound.b("UUIDMost", 4) && nbttagcompound.b("UUIDLeast", 4)) {
                this.ar = new UUID(nbttagcompound.g("UUIDMost"), nbttagcompound.g("UUIDLeast"));
            }

            this.b(this.s, this.t, this.u);
            this.b(this.y, this.z);
            // CanaryMod: allow the saving of persistent metadata
            this.metadata = nbttagcompound.c("Canary") ? new CanaryCompoundTag(nbttagcompound.m("Canary")) : new CanaryCompoundTag();
            // CanaryMod: END
            this.a(nbttagcompound);
            if (this.V()) {
                this.b(this.s, this.t, this.u);
            }
        } catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.a(throwable, "Loading entity NBT");
            CrashReportCategory crashreportcategory = crashreport.a("Entity being loaded");

            this.a(crashreportcategory);
            throw new ReportedException(crashreport);
        }
    }

    protected boolean V() {
        return true;
    }

    protected final String W() {
        return EntityList.b(this);
    }

    protected abstract void a(NBTTagCompound nbttagcompound);

    protected abstract void b(NBTTagCompound nbttagcompound);

    public void X() {
    }

    protected NBTTagList a(double... adouble) {
        NBTTagList nbttaglist = new NBTTagList();
        double[] adouble1 = adouble;
        int i0 = adouble.length;

        for (int i1 = 0; i1 < i0; ++i1) {
            double d0 = adouble1[i1];

            nbttaglist.a((NBTBase) (new NBTTagDouble(d0)));
        }

        return nbttaglist;
    }

    protected NBTTagList a(float... afloat) {
        NBTTagList nbttaglist = new NBTTagList();
        float[] afloat1 = afloat;
        int i0 = afloat.length;

        for (int i1 = 0; i1 < i0; ++i1) {
            float f0 = afloat1[i1];

            nbttaglist.a((NBTBase) (new NBTTagFloat(f0)));
        }

        return nbttaglist;
    }

    public EntityItem a(Item item, int i0) {
        return this.a(item, i0, 0.0F);
    }

    public EntityItem a(Item item, int i0, float f0) {
        return this.a(new ItemStack(item, i0, 0), f0);
    }

    public EntityItem a(ItemStack itemstack, float f0) {
        if (itemstack.b != 0 && itemstack.b() != null) {
            EntityItem entityitem = new EntityItem(this.o, this.s, this.t + (double) f0, this.u, itemstack);

            entityitem.b = 10;
            this.o.d((Entity) entityitem);
            return entityitem;
        } else {
            return null;
        }
    }

    public boolean Z() {
        return !this.K;
    }

    public boolean aa() {
        for (int i0 = 0; i0 < 8; ++i0) {
            float f0 = ((float) ((i0 >> 0) % 2) - 0.5F) * this.M * 0.8F;
            float f1 = ((float) ((i0 >> 1) % 2) - 0.5F) * 0.1F;
            float f2 = ((float) ((i0 >> 2) % 2) - 0.5F) * this.M * 0.8F;
            int i1 = MathHelper.c(this.s + (double) f0);
            int i2 = MathHelper.c(this.t + (double) this.g() + (double) f1);
            int i3 = MathHelper.c(this.u + (double) f2);

            if (this.o.a(i1, i2, i3).r()) {
                return true;
            }
        }

        return false;
    }

    public boolean c(EntityPlayer entityplayer) {
        return false;
    }

    public AxisAlignedBB h(Entity entity) {
        return null;
    }

    public void ab() {
        if (this.m.K) {
            this.m = null;
        } else {
            this.v = 0.0D;
            this.w = 0.0D;
            this.x = 0.0D;
            this.h();
            if (this.m != null) {
                this.m.ac();
                this.h += (double) (this.m.y - this.m.A);

                for (this.g += (double) (this.m.z - this.m.B); this.h >= 180.0D; this.h -= 360.0D) {
                    ;
                }

                while (this.h < -180.0D) {
                    this.h += 360.0D;
                }

                while (this.g >= 180.0D) {
                    this.g -= 360.0D;
                }

                while (this.g < -180.0D) {
                    this.g += 360.0D;
                }

                double d0 = this.h * 0.5D;
                double d1 = this.g * 0.5D;
                float f0 = 10.0F;

                if (d0 > (double) f0) {
                    d0 = (double) f0;
                }

                if (d0 < (double) (-f0)) {
                    d0 = (double) (-f0);
                }

                if (d1 > (double) f0) {
                    d1 = (double) f0;
                }

                if (d1 < (double) (-f0)) {
                    d1 = (double) (-f0);
                }

                this.h -= d0;
                this.g -= d1;
            }
        }
    }

    public void ac() {
        if (this.l != null) {
            this.l.b(this.s, this.t + this.ae() + this.l.ad(), this.u);
        }
    }

    public double ad() {
        return (double) this.L;
    }

    public double ae() {
        return (double) this.N * 0.75D;
    }

    public void a(Entity entity) {
        this.g = 0.0D;
        this.h = 0.0D;
        if (entity == null) {
            if (this.m != null) {
                this.b(this.m.s, this.m.C.b + (double) this.m.N, this.m.u, this.y, this.z);
                this.m.l = null;
            }

            this.m = null;
        } else {
            if (this.m != null) {
                this.m.l = null;
            }

            // CanaryMod: EntityMount
            EntityMountHook hook = null;
            if (this instanceof EntityLivingBase && entity instanceof EntityLivingBase) { // If its non-living, its probably a Vehicle
                hook = (EntityMountHook) new EntityMountHook((LivingBase) entity.getCanaryEntity(), (LivingBase) this.getCanaryEntity()).call();
            }
            if (hook == null || !hook.isCanceled()) {
                this.m = entity;
                entity.l = this;
            }
            //
        }
    }

    public float af() {
        return 0.1F;
    }

    public Vec3 ag() {
        return null;
    }

    public void ah() {
        if (this.am > 0) {
            this.am = this.ai();
        } else {
            double d0 = this.p - this.s;
            double d1 = this.r - this.u;

            if (!this.o.E && !this.an) {
                this.aq = Direction.a(d0, d1);
            }

            this.an = true;
        }
    }

    public int ai() {
        return 300;
    }

    public ItemStack[] ak() {
        return null;
    }

    public void c(int i0, ItemStack itemstack) {
    }

    public boolean al() {
        boolean flag0 = this.o != null && this.o.E;

        return !this.ae && (this.e > 0 || flag0 && this.g(0));
    }

    public boolean am() {
        return this.m != null;
    }

    public boolean an() {
        return this.g(1);
    }

    public void b(boolean flag0) {
        this.a(1, flag0);
    }

    public boolean ao() {
        return this.g(3);
    }

    public void c(boolean flag0) {
        this.a(3, flag0);
    }

    public boolean ap() {
        return this.g(5);
    }

    public void d(boolean flag0) {
        this.a(5, flag0);
    }

    public void e(boolean flag0) {
        this.a(4, flag0);
    }

    protected boolean g(int i0) {
        return (this.af.a(0) & 1 << i0) != 0;
    }

    protected void a(int i0, boolean flag0) {
        byte b0 = this.af.a(0);

        if (flag0) {
            this.af.b(0, Byte.valueOf((byte) (b0 | 1 << i0)));
        } else {
            this.af.b(0, Byte.valueOf((byte) (b0 & ~(1 << i0))));
        }
    }

    public int ar() {
        return this.af.b(1);
    }

    public void h(int i0) {
        this.af.b(1, Short.valueOf((short) i0));
    }

    public void a(EntityLightningBolt entitylightningbolt) {
        this.f(5);
        ++this.e;
        if (this.e == 0) {
            this.e(8);
        }
    }

    public void a(EntityLivingBase entitylivingbase) {
    }

    protected boolean j(double d0, double d1, double d2) {
        int i0 = MathHelper.c(d0);
        int i1 = MathHelper.c(d1);
        int i2 = MathHelper.c(d2);
        double d3 = d0 - (double) i0;
        double d4 = d1 - (double) i1;
        double d5 = d2 - (double) i2;
        List list = this.o.a(this.C);

        if (list.isEmpty() && !this.o.q(i0, i1, i2)) {
            return false;
        } else {
            boolean flag0 = !this.o.q(i0 - 1, i1, i2);
            boolean flag1 = !this.o.q(i0 + 1, i1, i2);
            boolean flag2 = !this.o.q(i0, i1 - 1, i2);
            boolean flag3 = !this.o.q(i0, i1 + 1, i2);
            boolean flag4 = !this.o.q(i0, i1, i2 - 1);
            boolean flag5 = !this.o.q(i0, i1, i2 + 1);
            byte b0 = 3;
            double d6 = 9999.0D;

            if (flag0 && d3 < d6) {
                d6 = d3;
                b0 = 0;
            }

            if (flag1 && 1.0D - d3 < d6) {
                d6 = 1.0D - d3;
                b0 = 1;
            }

            if (flag3 && 1.0D - d4 < d6) {
                d6 = 1.0D - d4;
                b0 = 3;
            }

            if (flag4 && d5 < d6) {
                d6 = d5;
                b0 = 4;
            }

            if (flag5 && 1.0D - d5 < d6) {
                d6 = 1.0D - d5;
                b0 = 5;
            }

            float f0 = this.Z.nextFloat() * 0.2F + 0.1F;

            if (b0 == 0) {
                this.v = (double) (-f0);
            }

            if (b0 == 1) {
                this.v = (double) f0;
            }

            if (b0 == 2) {
                this.w = (double) (-f0);
            }

            if (b0 == 3) {
                this.w = (double) f0;
            }

            if (b0 == 4) {
                this.x = (double) (-f0);
            }

            if (b0 == 5) {
                this.x = (double) f0;
            }

            return true;
        }
    }

    public void as() {
        this.I = true;
        this.R = 0.0F;
    }

    public String b_() {
        String s0 = EntityList.b(this);

        if (s0 == null) {
            s0 = "generic";
        }

        return StatCollector.a("entity." + s0 + ".name");
    }

    public Entity[] at() {
        return null;
    }

    public boolean i(Entity entity) {
        return this == entity;
    }

    public float au() {
        return 0.0F;
    }

    public boolean av() {
        return true;
    }

    public boolean j(Entity entity) {
        return false;
    }

    public String toString() {
        return String.format("%s[\'%s\'/%d, l=\'%s\', x=%.2f, y=%.2f, z=%.2f]", new Object[]{this.getClass().getSimpleName(), this.b_(), Integer.valueOf(this.c), this.o == null ? "~NULL~" : this.o.N().k(), Double.valueOf(this.s), Double.valueOf(this.t), Double.valueOf(this.u)});
    }

    public boolean aw() {
        return this.i;
    }

    public void k(Entity entity) {
        this.b(entity.s, entity.t, entity.u, entity.y, entity.z);
    }

    public void a(Entity entity, boolean flag0) {
        NBTTagCompound nbttagcompound = new NBTTagCompound();

        entity.e(nbttagcompound);
        this.f(nbttagcompound);
        this.am = entity.am;
        this.aq = entity.aq;
    }

    public void b(int i0) {
        if (!this.o.E && !this.K) {
            this.o.C.a("changeDimension");
            MinecraftServer minecraftserver = MinecraftServer.I();
            int i1 = this.ap;
            WorldServer worldserver = minecraftserver.getWorld(getCanaryWorld().getName(), i1); //Coming From
            WorldServer worldserver1 = minecraftserver.getWorld(getCanaryWorld().getName(), i0); //Going To

            // CanaryMod: Dimension switch hook.
            Location goingTo = this.simulatePortalUse(i0, worldserver1);
            CancelableHook hook = (CancelableHook) new DimensionSwitchHook(this.getCanaryEntity(), this.getCanaryEntity().getLocation(), goingTo).call();
            if (hook.isCanceled()) {
                this.o.C.b(); //Profiler
                return;
            }//

            this.ap = i0;
            if (i1 == 1 && i0 == 1) {
                //worldserver1 = minecraftserver.a(0);
                worldserver1 = minecraftserver.getWorld(getCanaryWorld().getName(), 0); // CanaryMod: world name required
                this.ap = 0;
            }

            this.o.e(this);
            this.K = false;
            this.o.C.a("reposition");
            minecraftserver.ah().a(this, i1, worldserver, worldserver1);
            this.o.C.c("reloading");
            Entity entity = EntityList.a(EntityList.b(this), worldserver1);

            if (entity != null) {
                entity.a(this, true);
                if (i1 == 1 && i0 == 1) {
                    ChunkCoordinates chunkcoordinates = worldserver1.K();

                    chunkcoordinates.b = this.o.i(chunkcoordinates.a, chunkcoordinates.c);
                    entity.b((double) chunkcoordinates.a, (double) chunkcoordinates.b, (double) chunkcoordinates.c, entity.y, entity.z);
                }

                worldserver1.d(entity);
            }

            this.K = true;
            this.o.C.b();
            worldserver.i();
            worldserver1.i();
            this.o.C.b();
        }
    }

    public float a(Explosion explosion, World world, int i0, int i1, int i2, Block block) {
        return block.a(this);
    }

    public boolean a(Explosion explosion, World world, int i0, int i1, int i2, Block block, float f0) {
        return true;
    }

    public int ax() {
        return 3;
    }

    public int ay() {
        return this.aq;
    }

    public boolean az() {
        return false;
    }

    public void a(CrashReportCategory crashreportcategory) {
        crashreportcategory.a("Entity Type", new Callable() {

            public String call() {
                return EntityList.b(Entity.this) + " (" + Entity.this.getClass().getCanonicalName() + ")";
            }
        });
        crashreportcategory.a("Entity ID", (Object) Integer.valueOf(this.c));
        crashreportcategory.a("Entity Name", new Callable() {

            public String call() {
                return Entity.this.b_();
            }
        });
        crashreportcategory.a("Entity\'s Exact location", (Object) String.format("%.2f, %.2f, %.2f", new Object[]{Double.valueOf(this.s), Double.valueOf(this.t), Double.valueOf(this.u)}));
        crashreportcategory.a("Entity\'s Block location", (Object) CrashReportCategory.a(MathHelper.c(this.s), MathHelper.c(this.t), MathHelper.c(this.u)));
        crashreportcategory.a("Entity\'s Momentum", (Object) String.format("%.2f, %.2f, %.2f", new Object[]{Double.valueOf(this.v), Double.valueOf(this.w), Double.valueOf(this.x)}));
    }

    public UUID aB() {
        return this.ar;
    }

    public boolean aC() {
        return true;
    }

    public IChatComponent c_() {
        return new ChatComponentText(this.b_());
    }

    public void i(int i0) {
    }

    public static enum EnumEntitySize {

        SIZE_1("SIZE_1", 0), SIZE_2("SIZE_2", 1), SIZE_3("SIZE_3", 2), SIZE_4("SIZE_4", 3), SIZE_5("SIZE_5", 4), SIZE_6("SIZE_6", 5);

        private static final EnumEntitySize[] $VALUES = new EnumEntitySize[]{SIZE_1, SIZE_2, SIZE_3, SIZE_4, SIZE_5, SIZE_6};

        private EnumEntitySize(String s0, int i0) {
        }

        public int a(double d0) {
            double d1 = d0 - ((double) MathHelper.c(d0) + 0.5D);

            switch (SwitchEnumEntitySize.a[this.ordinal()]) {
                case 1:
                    if (d1 < 0.0D) {
                        if (d1 < -0.3125D) {
                            return MathHelper.f(d0 * 32.0D);
                        }
                    } else if (d1 < 0.3125D) {
                        return MathHelper.f(d0 * 32.0D);
                    }

                    return MathHelper.c(d0 * 32.0D);

                case 2:
                    if (d1 < 0.0D) {
                        if (d1 < -0.3125D) {
                            return MathHelper.c(d0 * 32.0D);
                        }
                    } else if (d1 < 0.3125D) {
                        return MathHelper.c(d0 * 32.0D);
                    }

                    return MathHelper.f(d0 * 32.0D);

                case 3:
                    if (d1 > 0.0D) {
                        return MathHelper.c(d0 * 32.0D);
                    }

                    return MathHelper.f(d0 * 32.0D);

                case 4:
                    if (d1 < 0.0D) {
                        if (d1 < -0.1875D) {
                            return MathHelper.f(d0 * 32.0D);
                        }
                    } else if (d1 < 0.1875D) {
                        return MathHelper.f(d0 * 32.0D);
                    }

                    return MathHelper.c(d0 * 32.0D);

                case 5:
                    if (d1 < 0.0D) {
                        if (d1 < -0.1875D) {
                            return MathHelper.c(d0 * 32.0D);
                        }
                    } else if (d1 < 0.1875D) {
                        return MathHelper.c(d0 * 32.0D);
                    }

                    return MathHelper.f(d0 * 32.0D);

                case 6:
                default:
                    if (d1 > 0.0D) {
                        return MathHelper.f(d0 * 32.0D);
                    } else {
                        return MathHelper.c(d0 * 32.0D);
                    }
            }
        }
    }

    static final class SwitchEnumEntitySize {

        static final int[] a = new int[EnumEntitySize.values().length];

        static {
            try {
                a[EnumEntitySize.SIZE_1.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                a[EnumEntitySize.SIZE_2.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                a[EnumEntitySize.SIZE_3.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                a[EnumEntitySize.SIZE_4.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

            try {
                a[EnumEntitySize.SIZE_5.ordinal()] = 5;
            } catch (NoSuchFieldError nosuchfielderror4) {
                ;
            }

            try {
                a[EnumEntitySize.SIZE_6.ordinal()] = 6;
            } catch (NoSuchFieldError nosuchfielderror5) {
                ;
            }

        }
    }

    /**
     * Applies this Entities Properties to an NBT Tag. Initially implemented for
     * getting properties for MobSpawnerEntry.
     *
     * @param tag tag to apply this entities properties to.
     */
    public void getNBTProperties(NBTTagCompound tag) {
        this.b(tag);
    }

    public void setNBTProperties(NBTTagCompound tag) {
        this.a(tag);
    }

    /**
     * CanaryMod Get this entities current World (dimension)
     *
     * @return
     */
    public CanaryWorld getCanaryWorld() {
        return o.getCanaryWorld();
    }

    public void setDimension(CanaryWorld dim) {
        this.o = dim.getHandle();
    }

    public CanaryEntity getCanaryEntity() {
        return entity;
    }

    // CanaryMod: Simulates the use of a Portal by the Player to determine the location going to
    protected final Location simulatePortalUse(int dimensionTo, WorldServer oworldserverTo) {
        double y = this.t;
        float rotX = this.y;
        float rotY = this.z;
        double x = this.s;
        double z = this.u;
        double adjust = 8.0D;

        if (dimensionTo == -1) {
            x /= adjust;
            z /= adjust;
        } else if (dimensionTo == 0) {
            x *= adjust;
            z *= adjust;
        } else {
            ChunkCoordinates ochunkcoordinates;

            if (dimensionTo == 1) {
                ochunkcoordinates = oworldserverTo.K();
            } else {
                ochunkcoordinates = oworldserverTo.l();
            }
            x = (double) ochunkcoordinates.a;
            y = (double) ochunkcoordinates.b;
            z = (double) ochunkcoordinates.c;
            rotX = 90.0F;
            rotY = 0.0F;
        }
        if (dimensionTo != 1) {
            x = (double) MathHelper.a((int) x, -29999872, 29999872);
            z = (double) MathHelper.a((int) z, -29999872, 29999872);
        }
        return new Location(oworldserverTo.getCanaryWorld(), x, y, z, rotX, rotY);
    }

    public CompoundTag getMetaData() {
        return this.metadata;
    }

    protected boolean hasMovedOneBlockOrMore() {
        return ToolBox.floorToBlock(this.p) != ToolBox.floorToBlock(this.s)
                || ToolBox.floorToBlock(this.q) != ToolBox.floorToBlock(this.t)
                || ToolBox.floorToBlock(this.r) != ToolBox.floorToBlock(this.u);
    }
    //
}
