package net.minecraft.entity;

import net.canarymod.ToolBox;
import net.canarymod.api.CanaryDamageSource;
import net.canarymod.api.entity.CanaryEntity;
import net.canarymod.api.entity.EntityType;
import net.canarymod.api.entity.living.LivingBase;
import net.canarymod.api.entity.vehicle.Vehicle;
import net.canarymod.api.world.CanaryWorld;
import net.canarymod.api.world.position.Location;
import net.canarymod.api.world.position.Vector3D;
import net.canarymod.config.Configuration;
import net.canarymod.hook.CancelableHook;
import net.canarymod.hook.entity.DamageHook;
import net.canarymod.hook.entity.DimensionSwitchHook;
import net.canarymod.hook.entity.EntityMountHook;
import net.canarymod.hook.entity.EntityMoveHook;
import net.canarymod.hook.entity.VehicleMoveHook;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommandSender;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.event.HoverEvent;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ReportedException;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Callable;

public abstract class Entity implements ICommandSender {

    private static final AxisAlignedBB a = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
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
    private AxisAlignedBB f;
    public boolean C; // On Ground
    public boolean D;
    public boolean E;
    public boolean F;
    public boolean G; // Velocity Changed
    public boolean H; // CanaryMod: protected => public (In Web)
    private boolean g;    
    public boolean I;  //DEAD
    public float J;
    public float K;
    public float L;
    public float M;
    public float N;
    public float O;
    private int h;
    public double P;
    public double Q;
    public double R;
    public float S;
    public boolean T;
    public float U;
    protected Random V;
    public int W;
    public int X;
    public int i; // CanaryMod: private -> public (FireTicks)
    public boolean Y; // CanaryMod: protected => public (In Water [liquid?])
    public int Z;
    protected boolean aa;
    protected boolean ab;
    protected DataWatcher ac;
    private double ap;
    private double aq;
    public boolean ad;
    public int ae;
    public int af;
    public int ag;
    public boolean ah;
    public boolean ai;
    public int aj;
    protected boolean ak;
    protected int al;
    public int am;
    protected int an;
    private boolean ar;
    protected UUID ao;
    private final CommandResultStats as;

    // CanaryMod
    protected CanaryEntity entity;
    //

    public int F() {
        return this.c;
    }

    public void d(int i0) {
        this.c = i0;
    }

    public void G() {
        this.J();
    }

    public Entity(World world) {
        this.c = b++;
        this.j = 1.0D;
        this.f = a;
        this.J = 0.6F;
        this.K = 1.8F;
        this.h = 1;
        this.V = new Random();
        this.X = 1;
        this.aa = true;
        this.ao = MathHelper.a(this.V);
        this.as = new CommandResultStats();
        this.o = world;
        this.b(0.0D, 0.0D, 0.0D);
        if (world != null) {
            this.am = world.t.q();
        }

        this.ac = new DataWatcher(this);
        this.ac.a(0, Byte.valueOf((byte) 0));
        this.ac.a(1, Short.valueOf((short) 300));
        this.ac.a(3, Byte.valueOf((byte) 0));
        this.ac.a(2, "");
        this.ac.a(4, Byte.valueOf((byte) 0));
        this.h();
    }

    protected abstract void h();

    public DataWatcher H() {
        return this.ac;
    }

    public boolean equals(Object object) {
        return object instanceof Entity ? ((Entity) object).c == this.c : false;
    }

    public int hashCode() {
        return this.c;
    }

    public void J() {
        this.I = true;
    }

    protected void a(float f0, float f1) {
        if (f0 != this.J || f1 != this.K) {
            float f2 = this.J;

            this.J = f0;
            this.K = f1;
            this.a(new AxisAlignedBB(this.aQ().a, this.aQ().b, this.aQ().c, this.aQ().a + (double) this.J, this.aQ().b + (double) this.K, this.aQ().c + (double) this.J));
            if (this.J > f2 && !this.aa && !this.o.D) {
                this.d((double) (f2 - this.J), 0.0D, (double) (f2 - this.J));
            }
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
        float f0 = this.J / 2.0F;
        float f1 = this.K;

        this.a(new AxisAlignedBB(d0 - (double) f0, d1, d2 - (double) f0, d0 + (double) f0, d1 + (double) f1, d2 + (double) f0));
    }

    public void s_() {
        this.K();
    }

    public void K() {
        this.o.B.a("entityBaseTick");
        if (this.m != null && this.m.I) {
            this.m = null;
        }

        this.L = this.M;
        this.p = this.s;
        this.q = this.t;
        this.r = this.u;
        this.B = this.z;
        this.A = this.y;
        if (!this.o.D && this.o instanceof WorldServer) {
            this.o.B.a("portal");
            MinecraftServer minecraftserver = ((WorldServer) this.o).r();
            int i0 = this.L();

            if (this.ak) {
                // CanaryMod moved allow-nether to per-world config
                if (Configuration.getWorldConfig(getCanaryWorld().getFqName()).isNetherAllowed()) {
                    if (this.m == null && this.al++ >= i0) {
                        this.al = i0;
                        this.aj = this.ar();
                        byte b0;

                        if (this.o.t.q() == -1) {
                            b0 = 0;
                        } else {
                            b0 = -1;
                        }

                        this.c(b0);
                    }

                    this.ak = false;
                }
            } else {
                if (this.al > 0) {
                    this.al -= 4;
                }

                if (this.al < 0) {
                    this.al = 0;
                }
            }

            if (this.aj > 0) {
                --this.aj;
            }

            this.o.B.b();
        }

        this.Y();
        this.W();
        if (this.o.D) {
            this.i = 0;
        } else if (this.i > 0) {
            if (this.ab) {
                this.i -= 4;
                if (this.i < 0) {
                    this.i = 0;
                }
            } else {
                if (this.i % 20 == 0) {
                    // CanaryMod: call DamageHook (FireTick)
                    DamageHook hook = (DamageHook) new DamageHook(null, entity, new CanaryDamageSource(DamageSource.c), 1.0F).call();
                    if (!hook.isCanceled()) {
                        this.a((((CanaryDamageSource) hook.getDamageSource()).getHandle()), hook.getDamageDealt());
                    }
                    //
                }

                --this.i;
            }
        }

        if (this.ab()) {
            this.M();
            this.O *= 0.5F;
        }

        if (this.t < -64.0D) {
            this.O();
        }

        if (!this.o.D) {
            this.b(0, this.i > 0);
        }

        this.aa = false;
        this.o.B.b();
    }

    public int L() {
        return 0;
    }

    protected void M() {
        if (!this.ab) {
            // CanaryMod: call DamageHook (Lava)
            DamageHook hook = (DamageHook) new DamageHook(null, entity, new CanaryDamageSource(DamageSource.d), 4.0F).call();
            if (!hook.isCanceled()) {
                this.a(DamageSource.d, 4.0F);
                this.e(15);
            }
            //
        }
    }

    public void e(int i0) {
        int i1 = i0 * 20;

        i1 = EnchantmentProtection.a(this, i1);
        if (this.i < i1) {
            this.i = i1;
        }

    }

    public void N() {
        this.i = 0;
    }

    protected void O() {
        this.J();
    }

    public boolean c(double d0, double d1, double d2) {
        AxisAlignedBB axisalignedbb = this.aQ().c(d0, d1, d2);

        return this.b(axisalignedbb);
    }

    private boolean b(AxisAlignedBB axisalignedbb) {
        return this.o.a(this, axisalignedbb).isEmpty() && !this.o.d(axisalignedbb);
    }

    public void d(double d0, double d1, double d2) {
        if (this.T) {
            this.a(this.aQ().c(d0, d1, d2));
            this.m();
        } else {
            // CanaryMod:
            float prevPR = this.B, prevPP = this.A;
            double prevPX = this.p, prevPY = this.q, prevPZ = this.r;
            //
            this.o.B.a("move");
            double d3 = this.s;
            double d4 = this.t;
            double d5 = this.u;

            if (this.H) {
                this.H = false;
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
            boolean flag0 = this.C && this.aw() && this instanceof EntityPlayer;

            if (flag0) {
                double d9;

                for (d9 = 0.05D; d0 != 0.0D && this.o.a(this, this.aQ().c(d0, -1.0D, 0.0D)).isEmpty(); d6 = d0) {
                    if (d0 < d9 && d0 >= -d9) {
                        d0 = 0.0D;
                    } else if (d0 > 0.0D) {
                        d0 -= d9;
                    } else {
                        d0 += d9;
                    }
                }

                for (; d2 != 0.0D && this.o.a(this, this.aQ().c(0.0D, -1.0D, d2)).isEmpty(); d8 = d2) {
                    if (d2 < d9 && d2 >= -d9) {
                        d2 = 0.0D;
                    } else if (d2 > 0.0D) {
                        d2 -= d9;
                    } else {
                        d2 += d9;
                    }
                }

                for (; d0 != 0.0D && d2 != 0.0D && this.o.a(this, this.aQ().c(d0, -1.0D, d2)).isEmpty(); d8 = d2) {
                    if (d0 < d9 && d0 >= -d9) {
                        d0 = 0.0D;
                    } else if (d0 > 0.0D) {
                        d0 -= d9;
                    } else {
                        d0 += d9;
                    }

                    d6 = d0;
                    if (d2 < d9 && d2 >= -d9) {
                        d2 = 0.0D;
                    } else if (d2 > 0.0D) {
                        d2 -= d9;
                    } else {
                        d2 += d9;
                    }
                }
            }

            List list = this.o.a(this, this.aQ().a(d0, d1, d2));
            AxisAlignedBB axisalignedbb = this.aQ();

            AxisAlignedBB axisalignedbb1;

            for (Iterator iterator = list.iterator(); iterator.hasNext(); d1 = axisalignedbb1.b(this.aQ(), d1)) {
                axisalignedbb1 = (AxisAlignedBB) iterator.next();
            }

            this.a(this.aQ().c(0.0D, d1, 0.0D));
            boolean flag1 = this.C || d7 != d1 && d7 < 0.0D;

            AxisAlignedBB axisalignedbb2;
            Iterator iterator1;

            for (iterator1 = list.iterator(); iterator1.hasNext(); d0 = axisalignedbb2.a(this.aQ(), d0)) {
                axisalignedbb2 = (AxisAlignedBB) iterator1.next();
            }

            this.a(this.aQ().c(d0, 0.0D, 0.0D));

            for (iterator1 = list.iterator(); iterator1.hasNext(); d2 = axisalignedbb2.c(this.aQ(), d2)) {
                axisalignedbb2 = (AxisAlignedBB) iterator1.next();
            }

            this.a(this.aQ().c(0.0D, 0.0D, d2));
            if (this.S > 0.0F && flag1 && (d6 != d0 || d8 != d2)) {
                double d10 = d0;
                double d11 = d1;
                double d12 = d2;
                AxisAlignedBB axisalignedbb3 = this.aQ();

                this.a(axisalignedbb);
                d1 = (double) this.S;
                List list1 = this.o.a(this, this.aQ().a(d6, d1, d8));
                AxisAlignedBB axisalignedbb4 = this.aQ();
                AxisAlignedBB axisalignedbb5 = axisalignedbb4.a(d6, 0.0D, d8);
                double d13 = d1;

                AxisAlignedBB axisalignedbb6;

                for (Iterator iterator2 = list1.iterator(); iterator2.hasNext(); d13 = axisalignedbb6.b(axisalignedbb5, d13)) {
                    axisalignedbb6 = (AxisAlignedBB) iterator2.next();
                }

                axisalignedbb4 = axisalignedbb4.c(0.0D, d13, 0.0D);
                double d14 = d6;

                AxisAlignedBB axisalignedbb7;

                for (Iterator iterator3 = list1.iterator(); iterator3.hasNext(); d14 = axisalignedbb7.a(axisalignedbb4, d14)) {
                    axisalignedbb7 = (AxisAlignedBB) iterator3.next();
                }

                axisalignedbb4 = axisalignedbb4.c(d14, 0.0D, 0.0D);
                double d15 = d8;

                AxisAlignedBB axisalignedbb8;

                for (Iterator iterator4 = list1.iterator(); iterator4.hasNext(); d15 = axisalignedbb8.c(axisalignedbb4, d15)) {
                    axisalignedbb8 = (AxisAlignedBB) iterator4.next();
                }

                axisalignedbb4 = axisalignedbb4.c(0.0D, 0.0D, d15);
                AxisAlignedBB axisalignedbb9 = this.aQ();
                double d16 = d1;

                AxisAlignedBB axisalignedbb10;

                for (Iterator iterator5 = list1.iterator(); iterator5.hasNext(); d16 = axisalignedbb10.b(axisalignedbb9, d16)) {
                    axisalignedbb10 = (AxisAlignedBB) iterator5.next();
                }

                axisalignedbb9 = axisalignedbb9.c(0.0D, d16, 0.0D);
                double d17 = d6;

                AxisAlignedBB axisalignedbb11;

                for (Iterator iterator6 = list1.iterator(); iterator6.hasNext(); d17 = axisalignedbb11.a(axisalignedbb9, d17)) {
                    axisalignedbb11 = (AxisAlignedBB) iterator6.next();
                }

                axisalignedbb9 = axisalignedbb9.c(d17, 0.0D, 0.0D);
                double d18 = d8;

                AxisAlignedBB axisalignedbb12;

                for (Iterator iterator7 = list1.iterator(); iterator7.hasNext(); d18 = axisalignedbb12.c(axisalignedbb9, d18)) {
                    axisalignedbb12 = (AxisAlignedBB) iterator7.next();
                }

                axisalignedbb9 = axisalignedbb9.c(0.0D, 0.0D, d18);
                double d19 = d14 * d14 + d15 * d15;
                double d20 = d17 * d17 + d18 * d18;

                if (d19 > d20) {
                    d0 = d14;
                    d2 = d15;
                    this.a(axisalignedbb4);
                } else {
                    d0 = d17;
                    d2 = d18;
                    this.a(axisalignedbb9);
                }

                d1 = (double) (-this.S);

                AxisAlignedBB axisalignedbb13;

                for (Iterator iterator8 = list1.iterator(); iterator8.hasNext(); d1 = axisalignedbb13.b(this.aQ(), d1)) {
                    axisalignedbb13 = (AxisAlignedBB) iterator8.next();
                }

                this.a(this.aQ().c(0.0D, d1, 0.0D));
                if (d10 * d10 + d12 * d12 >= d0 * d0 + d2 * d2) {
                    d0 = d10;
                    d1 = d11;
                    d2 = d12;
                    this.a(axisalignedbb3);
                }
            }

            this.o.B.b();
            this.o.B.a("rest");
            this.m();
            this.D = d6 != d0 || d8 != d2;
            this.E = d7 != d1;
            this.C = this.E && d7 < 0.0D;
            this.F = this.D || this.E;
            int i0 = MathHelper.c(this.s);
            int i1 = MathHelper.c(this.t - 0.20000000298023224D);
            int i2 = MathHelper.c(this.u);
            BlockPos blockpos = new BlockPos(i0, i1, i2);
            Block block = this.o.p(blockpos).c();

            if (block.r() == Material.a) {
                Block block1 = this.o.p(blockpos.b()).c();

                if (block1 instanceof BlockFence || block1 instanceof BlockWall || block1 instanceof BlockFenceGate) {
                    block = block1;
                    blockpos = blockpos.b();
                }
            }

            this.a(d1, this.C, block, blockpos);
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
                        this.al(); //Update Rider
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
                        this.al(); //Update Rider
                    }
                }
            }
            //
            if (d6 != d0) {
                this.v = 0.0D;
            }

            if (d8 != d2) {
                this.x = 0.0D;
            }

            if (d7 != d1) {
                block.a(this.o, this);
            }

            if (this.r_() && !flag0 && this.m == null) {
                double d21 = this.s - d3;
                double d22 = this.t - d4;
                double d23 = this.u - d5;

                if (block != Blocks.au) {
                    d22 = 0.0D;
                }

                if (block != null && this.C) {
                    block.a(this.o, blockpos, this);
                }

                this.M = (float) ((double) this.M + (double) MathHelper.a(d21 * d21 + d23 * d23) * 0.6D);
                this.N = (float) ((double) this.N + (double) MathHelper.a(d21 * d21 + d22 * d22 + d23 * d23) * 0.6D);
                if (this.N > (float) this.h && block.r() != Material.a) {
                    this.h = (int) this.N + 1;
                    if (this.V()) {
                        float f0 = MathHelper.a(this.v * this.v * 0.20000000298023224D + this.w * this.w + this.x * this.x * 0.20000000298023224D) * 0.35F;

                        if (f0 > 1.0F) {
                            f0 = 1.0F;
                        }

                        this.a(this.P(), f0, 1.0F + (this.V.nextFloat() - this.V.nextFloat()) * 0.4F);
                    }

                    this.a(blockpos, block);
                }
            }

            try {
                this.Q();
            } catch (Throwable throwable) {
                CrashReport crashreport = CrashReport.a(throwable, "Checking entity block collision");
                CrashReportCategory crashreportcategory = crashreport.a("Entity being checked for collision");

                this.a(crashreportcategory);
                throw new ReportedException(crashreport);
            }

            boolean flag2 = this.U();

            if (this.o.e(this.aQ().d(0.001D, 0.001D, 0.001D))) {
                this.f(1);
                if (!flag2) {
                    ++this.i;
                    if (this.i == 0) {
                        this.e(8);
                    }
                }
            } else if (this.i <= 0) {
                this.i = -this.X;
            }

            if (flag2 && this.i > 0) {
                this.a("random.fizz", 0.7F, 1.6F + (this.V.nextFloat() - this.V.nextFloat()) * 0.4F);
                this.i = -this.X;
            }

            this.o.B.b();
        }
    }

    private void m() {
        this.s = (this.aQ().a + this.aQ().d) / 2.0D;
        this.t = this.aQ().b;
        this.u = (this.aQ().c + this.aQ().f) / 2.0D;
    }

    protected String P() {
        return "game.neutral.swim";
    }

    protected void Q() {
        BlockPos blockpos = new BlockPos(this.aQ().a + 0.001D, this.aQ().b + 0.001D, this.aQ().c + 0.001D);
        BlockPos blockpos1 = new BlockPos(this.aQ().d - 0.001D, this.aQ().e - 0.001D, this.aQ().f - 0.001D);

        if (this.o.a(blockpos, blockpos1)) {
            for (int i0 = blockpos.n(); i0 <= blockpos1.n(); ++i0) {
                for (int i1 = blockpos.o(); i1 <= blockpos1.o(); ++i1) {
                    for (int i2 = blockpos.p(); i2 <= blockpos1.p(); ++i2) {
                        BlockPos blockpos2 = new BlockPos(i0, i1, i2);
                        IBlockState iblockstate = this.o.p(blockpos2);

                        try {
                            iblockstate.c().a(this.o, blockpos2, iblockstate, this);
                        } catch (Throwable throwable) {
                            CrashReport crashreport = CrashReport.a(throwable, "Colliding entity with block");
                            CrashReportCategory crashreportcategory = crashreport.a("Block being collided with");

                            CrashReportCategory.a(crashreportcategory, blockpos2, iblockstate);
                            throw new ReportedException(crashreport);
                        }
                    }
                }
            }
        }

    }

    protected void a(BlockPos blockpos, Block block) {
        Block.SoundType block_soundtype = block.H;

        if (this.o.p(blockpos.a()).c() == Blocks.aH) {
            block_soundtype = Blocks.aH.H;
            this.a(block_soundtype.c(), block_soundtype.d() * 0.15F, block_soundtype.e());
        } else if (!block.r().d()) {
            this.a(block_soundtype.c(), block_soundtype.d() * 0.15F, block_soundtype.e());
        }

    }

    public void a(String s0, float f0, float f1) {
        if (!this.R()) {
            this.o.a(this, s0, f0, f1);
        }

    }

    public boolean R() {
        return this.ac.a(4) == 1;
    }

    public void b(boolean flag0) {
        this.ac.b(4, Byte.valueOf((byte) (flag0 ? 1 : 0)));
    }

    protected boolean r_() {
        return true;
    }

    protected void a(double d0, boolean flag0, Block block, BlockPos blockpos) {
        if (flag0) {
            if (this.O > 0.0F) {
                if (block != null) {
                    block.a(this.o, blockpos, this, this.O);
                } else {
                    this.e(this.O, 1.0F);
                }

                this.O = 0.0F;
            }
        } else if (d0 < 0.0D) {
            this.O = (float) ((double) this.O - d0);
        }

    }

    public AxisAlignedBB S() {
        return null;
    }

    protected void f(int i0) {
        if (!this.ab) {
            // CanaryMod: call DamageHook (onfire)
            DamageHook hook = (DamageHook) new DamageHook(null, entity, new CanaryDamageSource(DamageSource.a), i0).call();
            if (!hook.isCanceled()) {
                this.a((((CanaryDamageSource) hook.getDamageSource()).getHandle()), hook.getDamageDealt());
            }
            //
        }

    }

    public final boolean T() {
        return this.ab;
    }

    public void e(float f0, float f1) {
        if (this.l != null) {
            this.l.e(f0, f1);
        }

    }

    public boolean U() {
        return this.Y || this.o.C(new BlockPos(this.s, this.t, this.u)) || this.o.C(new BlockPos(this.s, this.t + (double) this.K, this.u));
    }

    public boolean V() {
        return this.Y;
    }

    public boolean W() {
        if (this.o.a(this.aQ().b(0.0D, -0.4000000059604645D, 0.0D).d(0.001D, 0.001D, 0.001D), Material.h, this)) {
            if (!this.Y && !this.aa) {
                this.X();
            }

            this.O = 0.0F;
            this.Y = true;
            this.i = 0;
        } else {
            this.Y = false;
        }

        return this.Y;
    }

    protected void X() {
        float f0 = MathHelper.a(this.v * this.v * 0.20000000298023224D + this.w * this.w + this.x * this.x * 0.20000000298023224D) * 0.2F;

        if (f0 > 1.0F) {
            f0 = 1.0F;
        }

        this.a(this.aa(), f0, 1.0F + (this.V.nextFloat() - this.V.nextFloat()) * 0.4F);
        float f1 = (float) MathHelper.c(this.aQ().b);

        int i0;
        float f2;
        float f3;

        for (i0 = 0; (float) i0 < 1.0F + this.J * 20.0F; ++i0) {
            f2 = (this.V.nextFloat() * 2.0F - 1.0F) * this.J;
            f3 = (this.V.nextFloat() * 2.0F - 1.0F) * this.J;
            this.o.a(EnumParticleTypes.WATER_BUBBLE, this.s + (double) f2, (double) (f1 + 1.0F), this.u + (double) f3, this.v, this.w - (double) (this.V.nextFloat() * 0.2F), this.x, new int[0]);
        }

        for (i0 = 0; (float) i0 < 1.0F + this.J * 20.0F; ++i0) {
            f2 = (this.V.nextFloat() * 2.0F - 1.0F) * this.J;
            f3 = (this.V.nextFloat() * 2.0F - 1.0F) * this.J;
            this.o.a(EnumParticleTypes.WATER_SPLASH, this.s + (double) f2, (double) (f1 + 1.0F), this.u + (double) f3, this.v, this.w, this.x, new int[0]);
        }

    }

    public void Y() {
        if (this.ax() && !this.V()) {
            this.Z();
        }

    }

    protected void Z() {
        int i0 = MathHelper.c(this.s);
        int i1 = MathHelper.c(this.t - 0.20000000298023224D);
        int i2 = MathHelper.c(this.u);
        BlockPos blockpos = new BlockPos(i0, i1, i2);
        IBlockState iblockstate = this.o.p(blockpos);
        Block block = iblockstate.c();

        if (block.b() != -1) {
            this.o.a(EnumParticleTypes.BLOCK_CRACK, this.s + ((double) this.V.nextFloat() - 0.5D) * (double) this.J, this.aQ().b + 0.1D, this.u + ((double) this.V.nextFloat() - 0.5D) * (double) this.J, -this.v * 4.0D, 1.5D, -this.x * 4.0D, new int[] { Block.f(iblockstate)});
        }

    }

    protected String aa() {
        return "game.neutral.swim.splash";
    }

    public boolean a(Material material) {
        double d0 = this.t + (double) this.aR();
        BlockPos blockpos = new BlockPos(this.s, d0, this.u);
        IBlockState iblockstate = this.o.p(blockpos);
        Block block = iblockstate.c();

        if (block.r() == material) {
            float f0 = BlockLiquid.b(iblockstate.c().c(iblockstate)) - 0.11111111F;
            float f1 = (float) (blockpos.o() + 1) - f0;
            boolean flag0 = d0 < (double) f1;

            return !flag0 && this instanceof EntityPlayer ? false : flag0;
        } else {
            return false;
        }
    }

    public boolean ab() {
        return this.o.a(this.aQ().b(-0.10000000149011612D, -0.4000000059604645D, -0.10000000149011612D), Material.i);
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

    public float c(float f0) {
        BlockPos blockpos = new BlockPos(this.s, 0.0D, this.u);

        if (this.o.e(blockpos)) {
            double d0 = (this.aQ().e - this.aQ().b) * 0.66D;
            int i0 = MathHelper.c(this.t + d0);

            return this.o.o(blockpos.b(i0));
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

    public void a(BlockPos blockpos, float f0, float f1) {
        this.b((double) blockpos.n() + 0.5D, (double) blockpos.o(), (double) blockpos.p() + 0.5D, f0, f1);
    }

    public void b(double d0, double d1, double d2, float f0, float f1) {
        this.P = this.p = this.s = d0;
        this.Q = this.q = this.t = d1;
        this.R = this.r = this.u = d2;
        this.y = f0;
        this.z = f1;
        this.b(this.s, this.t, this.u);
    }

    public float g(Entity entity) {
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

    public double b(BlockPos blockpos) {
        return blockpos.c(this.s, this.t, this.u);
    }

    public double c(BlockPos blockpos) {
        return blockpos.d(this.s, this.t, this.u);
    }

    public double f(double d0, double d1, double d2) {
        double d3 = this.s - d0;
        double d4 = this.t - d1;
        double d5 = this.u - d2;

        return (double) MathHelper.a(d3 * d3 + d4 * d4 + d5 * d5);
    }

    public double h(Entity entity) {
        double d0 = this.s - entity.s;
        double d1 = this.t - entity.t;
        double d2 = this.u - entity.u;

        return d0 * d0 + d1 * d1 + d2 * d2;
    }

    public void d(EntityPlayer entityplayer) {}

    public void i(Entity entity) {
        if (entity.l != this && entity.m != this) {
            if (!entity.T && !this.T) {
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
                    d0 *= (double) (1.0F - this.U);
                    d1 *= (double) (1.0F - this.U);
                    if (this.l == null) {
                        this.g(-d0, 0.0D, -d1);
                    }

                    if (entity.l == null) {
                        entity.g(d0, 0.0D, d1);
                    }
                }

            }
        }
    }

    public void g(double d0, double d1, double d2) {
        this.v += d0;
        this.w += d1;
        this.x += d2;
        this.ai = true;
    }

    protected void ac() {
        this.G = true;
    }

    public boolean a(DamageSource damagesource, float f0) {
        if (this.b(damagesource)) {
            return false;
        } else {
            this.ac();
            return false;
        }
    }

    public Vec3 d(float f0) {
        if (f0 == 1.0F) {
            return this.f(this.z, this.y);
        } else {
            float f1 = this.B + (this.z - this.B) * f0;
            float f2 = this.A + (this.y - this.A) * f0;

            return this.f(f1, f2);
        }
    }

    protected final Vec3 f(float f0, float f1) {
        float f2 = MathHelper.b(-f1 * 0.017453292F - 3.1415927F);
        float f3 = MathHelper.a(-f1 * 0.017453292F - 3.1415927F);
        float f4 = -MathHelper.b(-f0 * 0.017453292F);
        float f5 = MathHelper.a(-f0 * 0.017453292F);

        return new Vec3((double) (f3 * f4), (double) f5, (double) (f2 * f4));
    }

    public boolean ad() {
        return false;
    }

    public boolean ae() {
        return false;
    }

    public void b(Entity entity, int i0) {}

    public boolean c(NBTTagCompound nbttagcompound) {
        String s0 = this.ag();

        if (!this.I && s0 != null) {
            nbttagcompound.a("id", s0);
            this.e(nbttagcompound);
            return true;
        } else {
            return false;
        }
    }

    public boolean d(NBTTagCompound nbttagcompound) {
        String s0 = this.ag();

        if (!this.I && s0 != null && this.l == null) {
            nbttagcompound.a("id", s0);
            this.e(nbttagcompound);
            return true;
        } else {
            return false;
        }
    }

    public void e(NBTTagCompound nbttagcompound) {
        try {
            nbttagcompound.a("Pos", (NBTBase) this.a(new double[] { this.s, this.t, this.u}));
            nbttagcompound.a("Motion", (NBTBase) this.a(new double[] { this.v, this.w, this.x}));
            nbttagcompound.a("Rotation", (NBTBase) this.a(new float[] { this.y, this.z}));
            nbttagcompound.a("FallDistance", this.O);
            nbttagcompound.a("Fire", (short) this.i);
            nbttagcompound.a("Air", (short) this.aA());
            nbttagcompound.a("OnGround", this.C);
            nbttagcompound.a("Dimension", this.am);
            nbttagcompound.a("Invulnerable", this.ar);
            nbttagcompound.a("PortalCooldown", this.aj);
            nbttagcompound.a("UUIDMost", this.aJ().getMostSignificantBits());
            nbttagcompound.a("UUIDLeast", this.aJ().getLeastSignificantBits());

            if (this.aL() != null && this.aL().length() > 0) {
                nbttagcompound.a("CustomName", this.aL());
                nbttagcompound.a("CustomNameVisible", this.aM());
            }

            this.as.b(nbttagcompound);
            if (this.R()) {
                nbttagcompound.a("Silent", this.R());
            }

            this.b(nbttagcompound);
            if (this.m != null) {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();

                if (this.m.c(nbttagcompound1)) {
                    nbttagcompound.a("Riding", (NBTBase) nbttagcompound1);
                }
            }
            // CanaryMod: Write out our added NBT data
            getCanaryEntity().writeCanaryNBT(nbttagcompound);
            //
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

            this.p = this.P = this.s = nbttaglist.d(0);
            this.q = this.Q = this.t = nbttaglist.d(1);
            this.r = this.R = this.u = nbttaglist.d(2);
            this.A = this.y = nbttaglist2.e(0);
            this.B = this.z = nbttaglist2.e(1);
            this.O = nbttagcompound.h("FallDistance");
            this.i = nbttagcompound.e("Fire");
            this.h(nbttagcompound.e("Air"));
            this.C = nbttagcompound.n("OnGround");
            this.am = nbttagcompound.f("Dimension");
            this.ar = nbttagcompound.n("Invulnerable");
            this.aj = nbttagcompound.f("PortalCooldown");
            if (nbttagcompound.b("UUIDMost", 4) && nbttagcompound.b("UUIDLeast", 4)) {
                this.ao = new UUID(nbttagcompound.g("UUIDMost"), nbttagcompound.g("UUIDLeast"));
            } else if (nbttagcompound.b("UUID", 8)) {
                this.ao = UUID.fromString(nbttagcompound.j("UUID"));
            }

            this.b(this.s, this.t, this.u);
            this.b(this.y, this.z);
            if (nbttagcompound.b("CustomName", 8) && nbttagcompound.j("CustomName").length() > 0) {
                this.a(nbttagcompound.j("CustomName"));
            }

            this.g(nbttagcompound.n("CustomNameVisible"));
            this.as.a(nbttagcompound);
            this.b(nbttagcompound.n("Silent"));
            this.a(nbttagcompound);
            if (this.af()) {
                this.b(this.s, this.t, this.u);
            }
            // CanaryMod: Read our added NBT Tags (not part of meta)
            getCanaryEntity().readCanaryNBT(nbttagcompound);
            //
        } catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.a(throwable, "Loading entity NBT");
            CrashReportCategory crashreportcategory = crashreport.a("Entity being loaded");

            this.a(crashreportcategory);
            throw new ReportedException(crashreport);
        }
    }

    protected boolean af() {
        return true;
    }

    protected final String ag() {
        return EntityList.b(this);
    }

    protected abstract void a(NBTTagCompound nbttagcompound);

    protected abstract void b(NBTTagCompound nbttagcompound);

    public void ah() {}

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

            entityitem.p();
            this.o.d((Entity) entityitem);
            return entityitem;
        } else {
            return null;
        }
    }

    public boolean ai() {
        return !this.I;
    }

    public boolean aj() {
        if (this.T) {
            return false;
        } else {
            for (int i0 = 0; i0 < 8; ++i0) {
                double d0 = this.s + (double) (((float) ((i0 >> 0) % 2) - 0.5F) * this.J * 0.8F);
                double d1 = this.t + (double) (((float) ((i0 >> 1) % 2) - 0.5F) * 0.1F);
                double d2 = this.u + (double) (((float) ((i0 >> 2) % 2) - 0.5F) * this.J * 0.8F);

                if (this.o.p(new BlockPos(d0, d1 + (double) this.aR(), d2)).c().u()) {
                    return true;
                }
            }

            return false;
        }
    }

    public boolean e(EntityPlayer entityplayer) {
        return false;
    }

    public AxisAlignedBB j(Entity entity) {
        return null;
    }

    public void ak() {
        if (this.m.I) {
            this.m = null;
        } else {
            this.v = 0.0D;
            this.w = 0.0D;
            this.x = 0.0D;
            this.s_();
            if (this.m != null) {
                this.m.al();
                this.aq += (double) (this.m.y - this.m.A);

                for (this.ap += (double) (this.m.z - this.m.B); this.aq >= 180.0D; this.aq -= 360.0D) {
                    ;
                }

                while (this.aq < -180.0D) {
                    this.aq += 360.0D;
                }

                while (this.ap >= 180.0D) {
                    this.ap -= 360.0D;
                }

                while (this.ap < -180.0D) {
                    this.ap += 360.0D;
                }

                double d0 = this.aq * 0.5D;
                double d1 = this.ap * 0.5D;
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

                this.aq -= d0;
                this.ap -= d1;
            }
        }
    }

    public void al() {
        if (this.l != null) {
            this.l.b(this.s, this.t + this.an() + this.l.am(), this.u);
        }
    }

    public double am() {
        return 0.0D;
    }

    public double an() {
        return (double) this.K * 0.75D;
    }

    public void a(Entity entity) {
        this.ap = 0.0D;
        this.aq = 0.0D;
        if (entity == null) {
            if (this.m != null) {
                this.b(this.m.s, this.m.aQ().b + (double) this.m.K, this.m.u, this.y, this.z);
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

    public float ao() {
        return 0.1F;
    }

    public Vec3 ap() {
        return null;
    }

    public void aq() {
        if (this.aj > 0) {
            this.aj = this.ar();
        } else {
            double d0 = this.p - this.s;
            double d1 = this.r - this.u;

            if (!this.o.D && !this.ak) {
                int i0;

                if (MathHelper.e((float) d0) > MathHelper.e((float) d1)) {
                    i0 = d0 > 0.0D ? EnumFacing.WEST.b() : EnumFacing.EAST.b();
                } else {
                    i0 = d1 > 0.0D ? EnumFacing.NORTH.b() : EnumFacing.SOUTH.b();
                }

                this.an = i0;
            }

            this.ak = true;
        }
    }

    public int ar() {
        return 300;
    }

    public ItemStack[] at() {
        return null;
    }

    public void c(int i0, ItemStack itemstack) {}

    public boolean au() {
        boolean flag0 = this.o != null && this.o.D;

        return !this.ab && (this.i > 0 || flag0 && this.g(0));
    }

    public boolean av() {
        return this.m != null;
    }

    public boolean aw() {
        return this.g(1);
    }

    public void c(boolean flag0) {
        this.b(1, flag0);
    }

    public boolean ax() {
        return this.g(3);
    }

    public void d(boolean flag0) {
        this.b(3, flag0);
    }

    public boolean ay() {
        return this.g(5);
    }

    public void e(boolean flag0) {
        this.b(5, flag0);
    }

    public void f(boolean flag0) {
        this.b(4, flag0);
    }

    protected boolean g(int i0) {
        return (this.ac.a(0) & 1 << i0) != 0;
    }

    protected void b(int i0, boolean flag0) {
        byte b0 = this.ac.a(0);

        if (flag0) {
            this.ac.b(0, Byte.valueOf((byte) (b0 | 1 << i0)));
        } else {
            this.ac.b(0, Byte.valueOf((byte) (b0 & ~(1 << i0))));
        }

    }

    public int aA() {
        return this.ac.b(1);
    }

    public void h(int i0) {
        this.ac.b(1, Short.valueOf((short) i0));
    }

    public void a(EntityLightningBolt entitylightningbolt) {
        this.a(DamageSource.b, 5.0F);
        ++this.i;
        if (this.i == 0) {
            this.e(8);
        }
    }

    public void a(EntityLivingBase entitylivingbase) {}

    protected boolean j(double d0, double d1, double d2) {
        BlockPos blockpos = new BlockPos(d0, d1, d2);
        double d3 = d0 - (double) blockpos.n();
        double d4 = d1 - (double) blockpos.o();
        double d5 = d2 - (double) blockpos.p();
        List list = this.o.a(this.aQ());

        if (list.isEmpty() && !this.o.u(blockpos)) {
            return false;
        } else {
            byte b0 = 3;
            double d6 = 9999.0D;

            if (!this.o.u(blockpos.e()) && d3 < d6) {
                d6 = d3;
                b0 = 0;
            }

            if (!this.o.u(blockpos.f()) && 1.0D - d3 < d6) {
                d6 = 1.0D - d3;
                b0 = 1;
            }

            if (!this.o.u(blockpos.a()) && 1.0D - d4 < d6) {
                d6 = 1.0D - d4;
                b0 = 3;
            }

            if (!this.o.u(blockpos.c()) && d5 < d6) {
                d6 = d5;
                b0 = 4;
            }

            if (!this.o.u(blockpos.d()) && 1.0D - d5 < d6) {
                d6 = 1.0D - d5;
                b0 = 5;
            }

            float f0 = this.V.nextFloat() * 0.2F + 0.1F;

            if (b0 == 0) {
                this.v = (double) (-f0);
            }

            if (b0 == 1) {
                this.v = (double) f0;
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

    public void aB() {
        this.H = true;
        this.O = 0.0F;
    }

    public String d_() {
        if (this.k_()) {
            return this.aL();
        } else {
            String s0 = EntityList.b(this);

            if (s0 == null) {
                s0 = "generic";
            }

            return StatCollector.a("entity." + s0 + ".name");
        }
    }

    public Entity[] aC() {
        return null;
    }

    public boolean k(Entity entity) {
        return this == entity;
    }

    public float aD() {
        return 0.0F;
    }

    public void f(float f0) {}

    public boolean aE() {
        return true;
    }

    public boolean l(Entity entity) {
        return false;
    }

    public String toString() {
        return String.format("%s[\'%s\'/%d, l=\'%s\', x=%.2f, y=%.2f, z=%.2f]", new Object[] { this.getClass().getSimpleName(), this.d_(), Integer.valueOf(this.c), this.o == null ? "~NULL~" : this.o.P().k(), Double.valueOf(this.s), Double.valueOf(this.t), Double.valueOf(this.u)});
    }

    public boolean b(DamageSource damagesource) {
        return this.ar && damagesource != DamageSource.j && !damagesource.u();
    }

    public void m(Entity entity) {
        this.b(entity.s, entity.t, entity.u, entity.y, entity.z);
    }

    public void n(Entity entity) {
        NBTTagCompound nbttagcompound = new NBTTagCompound();

        entity.e(nbttagcompound);
        this.f(nbttagcompound);
        this.aj = entity.aj;
        this.an = entity.an;
    }

    public void c(int i0) {
        if (!this.o.D && !this.I) {
            this.o.B.a("changeDimension");
            MinecraftServer minecraftserver = MinecraftServer.M();
            int i1 = this.am;

            // CanaryMod: Multiworld fixes
            WorldServer worldserver = minecraftserver.getWorld(getCanaryWorld().getName(), i1);
            WorldServer worldserver1 = minecraftserver.getWorld(getCanaryWorld().getName(), i0);
            //

            // CanaryMod: Dimension switch hook.
            Location goingTo = this.simulatePortalUse(i0, worldserver1);
            CancelableHook hook = (CancelableHook) new DimensionSwitchHook(this.getCanaryEntity(), this.getCanaryEntity().getLocation(), goingTo).call();
            if (hook.isCanceled()) {
                this.o.B.b(); //Profiler
                return;
            }//

            this.am = i0;
            if (i1 == 1 && i0 == 1) {
                //worldserver1 = minecraftserver.a(0);
                worldserver1 = minecraftserver.getWorld(getCanaryWorld().getName(), 0); // CanaryMod: world name required
                this.am = 0;
            }

            this.o.e(this);
            this.I = false;
            this.o.B.a("reposition");
            minecraftserver.an().a(this, i1, worldserver, worldserver1);
            this.o.B.c("reloading");
            Entity entity = EntityList.a(EntityList.b(this), (World) worldserver1);

            if (entity != null) {
                entity.n(this);
                if (i1 == 1 && i0 == 1) {
                    BlockPos blockpos = this.o.r(worldserver1.M());

                    entity.a(blockpos, entity.y, entity.z);
                }

                worldserver1.d(entity);
            }

            this.I = true;
            this.o.B.b();
            worldserver.j();
            worldserver1.j();
            this.o.B.b();
        }
    }

    public float a(Explosion explosion, World world, BlockPos blockpos, IBlockState iblockstate) {
        return iblockstate.c().a(this);
    }

    public boolean a(Explosion explosion, World world, BlockPos blockpos, IBlockState iblockstate, float f0) {
        return true;
    }

    public int aF() {
        return 3;
    }

    public int aG() {
        return this.an;
    }

    public boolean aH() {
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
                return Entity.this.d_();
            }
        });
        crashreportcategory.a("Entity\'s Exact location", (Object) String.format("%.2f, %.2f, %.2f", new Object[] { Double.valueOf(this.s), Double.valueOf(this.t), Double.valueOf(this.u)}));
        crashreportcategory.a("Entity\'s Block location", (Object) CrashReportCategory.a((double) MathHelper.c(this.s), (double) MathHelper.c(this.t), (double) MathHelper.c(this.u)));
        crashreportcategory.a("Entity\'s Momentum", (Object) String.format("%.2f, %.2f, %.2f", new Object[] { Double.valueOf(this.v), Double.valueOf(this.w), Double.valueOf(this.x)}));
        crashreportcategory.a("Entity\'s Rider", new Callable() {

            public String a() {
                return Entity.this.l.toString();
            }

            public Object call() {
                return this.a();
            }
        });
        crashreportcategory.a("Entity\'s Vehicle", new Callable() {

            public String a() {
                return Entity.this.m.toString();
            }

            public Object call() {
                return this.a();
            }
        });
    }

    public UUID aJ() {
        return this.ao;
    }

    public boolean aK() {
        return true;
    }

    public IChatComponent e_() {
        ChatComponentText chatcomponenttext = new ChatComponentText(this.d_());

        chatcomponenttext.b().a(this.aP());
        chatcomponenttext.b().a(this.aJ().toString());
        return chatcomponenttext;
    }

    public void a(String s0) {
        this.ac.b(2, s0);
    }

    public String aL() {
        return this.ac.e(2);
    }

    public boolean k_() {
        return this.ac.e(2).length() > 0;
    }

    public void g(boolean flag0) {
        this.ac.b(3, Byte.valueOf((byte) (flag0 ? 1 : 0)));
    }

    public boolean aM() {
        return this.ac.a(3) == 1;
    }

    public void a(double d0, double d1, double d2) {
        this.b(d0, d1, d2, this.y, this.z);
    }

    public void i(int i0) {}

    public EnumFacing aO() {
        return EnumFacing.b(MathHelper.c((double) (this.y * 4.0F / 360.0F) + 0.5D) & 3);
    }

    protected HoverEvent aP() {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        String s0 = EntityList.b(this);

        nbttagcompound.a("id", this.aJ().toString());
        if (s0 != null) {
            nbttagcompound.a("type", s0);
        }

        nbttagcompound.a("name", this.d_());
        return new HoverEvent(HoverEvent.Action.SHOW_ENTITY, new ChatComponentText(nbttagcompound.toString()));
    }

    public boolean a(EntityPlayerMP entityplayermp) {
        return true;
    }

    public AxisAlignedBB aQ() {
        return this.f;
    }

    public void a(AxisAlignedBB axisalignedbb) {
        this.f = axisalignedbb;
    }

    public float aR() {
        return this.K * 0.85F;
    }

    public boolean aS() {
        return this.g;
    }

    public void h(boolean flag0) {
        this.g = flag0;
    }

    public boolean d(int i0, ItemStack itemstack) {
        return false;
    }

    public void a(IChatComponent ichatcomponent) {}

    public boolean a(int i0, String s0) {
        return true;
    }

    public BlockPos c() {
        return new BlockPos(this.s, this.t + 0.5D, this.u);
    }

    public Vec3 d() {
        return new Vec3(this.s, this.t, this.u);
    }

    public World e() {
        return this.o;
    }

    public Entity f() {
        return this;
    }

    public boolean t_() {
        return false;
    }

    public void a(CommandResultStats.Type commandresultstats_type, int i0) {
        this.as.a(this, commandresultstats_type, i0);
    }

    public CommandResultStats aT() {
        return this.as;
    }

    public void o(Entity entity) {
        this.as.a(entity.aT());
    }

    public NBTTagCompound aU() {
        return null;
    }

    public boolean a(EntityPlayer entityplayer, Vec3 vec3) {
        return false;
    }

    public boolean aV() {
        return false;
    }

    protected void a(EntityLivingBase entitylivingbase, Entity entity) {
        if (entity instanceof EntityLivingBase) {
            EnchantmentHelper.a((EntityLivingBase) entity, (Entity) entitylivingbase);
        }

        EnchantmentHelper.b(entitylivingbase, entity);
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
        if (entity == null) { // Set an instance only if not found
            entity = new CanaryEntity(this) {

                @Override
                public EntityType getEntityType() {
                    return EntityType.GENERIC_ENTITY;
                }

                @Override
                public String getFqName() {
                    return "GenericEntity[" + this.getClass().getSimpleName() + "]";
                }

                @Override
                public Entity getHandle() {
                    return entity;
                }
            };
        }
        return entity;
    }

    // CanaryMod: Simulates the use of a Portal by the Player to determine the location going to
    // Its like ServerConfigurationManager public void [obfuscated_name](Entity entity, int i0, WorldServer worldserver, WorldServer worldserver1) {
    protected final Location simulatePortalUse(int dimensionTo, WorldServer worldserverTo) {
        double y = this.t;
        float rotX = this.y;
        float rotY = this.z;
        double x = this.s;
        double z = this.u;
        double adjust = 8.0D;

        if (dimensionTo == -1) {
            x = MathHelper.a(x / adjust, worldserverTo.af().b() + 16.0D, worldserverTo.af().d() - 16.0D);
            z = MathHelper.a(z / adjust, worldserverTo.af().c() + 16.0D, worldserverTo.af().e() - 16.0D);
        } else if (dimensionTo == 0) {
            x = MathHelper.a(x * adjust, worldserverTo.af().b() + 16.0D, worldserverTo.af().d() - 16.0D);
            z = MathHelper.a(z * adjust, worldserverTo.af().c() + 16.0D, worldserverTo.af().e() - 16.0D);
        } else {
            BlockPos blockpos;

            if (dimensionTo == 1) {
                blockpos = worldserverTo.M();
            } else {
                blockpos = worldserverTo.m();
            }
            x = (double) blockpos.n();
            y = (double) blockpos.o();
            z = (double) blockpos.p();
            rotX = 90.0F;
            rotY = 0.0F;
        }
        if (dimensionTo != 1) {
            x = (double) MathHelper.a((int) x, -29999872, 29999872);
            z = (double) MathHelper.a((int) z, -29999872, 29999872);
        }
        return new Location(worldserverTo.getCanaryWorld(), x, y, z, rotY, rotX);
    }

    protected boolean hasMovedOneBlockOrMore() {
        return ToolBox.floorToBlock(this.p) != ToolBox.floorToBlock(this.s)
                || ToolBox.floorToBlock(this.q) != ToolBox.floorToBlock(this.t)
                || ToolBox.floorToBlock(this.r) != ToolBox.floorToBlock(this.u);
    }
    //

    public boolean isEating(){
        return this.ac.a(4) == 1;
    }
}
