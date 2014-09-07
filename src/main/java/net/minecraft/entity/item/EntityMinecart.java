package net.minecraft.entity.item;

import net.canarymod.Canary;
import net.canarymod.api.CanaryDamageSource;
import net.canarymod.api.entity.living.EntityLiving;
import net.canarymod.api.entity.vehicle.Minecart;
import net.canarymod.api.entity.vehicle.Vehicle;
import net.canarymod.api.world.position.Vector3D;
import net.canarymod.config.Configuration;
import net.canarymod.hook.entity.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityMinecartCommandBlock;
import net.minecraft.entity.ai.EntityMinecartMobSpawner;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.List;

//import net.minecraft.server.MinecraftServer;

public abstract class EntityMinecart extends Entity {

    public boolean a; // CanaryMod: private -> public
    private String b;
    private static final int[][][] c = new int[][][]{{{0, 0, -1}, {0, 0, 1}}, {{-1, 0, 0}, {1, 0, 0}}, {{-1, -1, 0}, {1, 0, 0}}, {{-1, 0, 0}, {1, -1, 0}}, {{0, 0, -1}, {0, -1, 1}}, {{0, -1, -1}, {0, 0, 1}}, {{0, 0, 1}, {1, 0, 0}}, {{0, 0, 1}, {-1, 0, 0}}, {{0, 0, -1}, {-1, 0, 0}}, {{0, 0, -1}, {1, 0, 0}}};
    private int d;
    private double e;
    private double f;
    private double g;
    private double h;
    private double i;

    public EntityMinecart(World world) {
        super(world);
        this.k = true;
        this.a(0.98F, 0.7F);
        this.L = this.N / 2.0F;
    }

    public static EntityMinecart a(World world, double d0, double d1, double d2, int i0) {
        switch (i0) {
            case 1:
                return new EntityMinecartChest(world, d0, d1, d2);

            case 2:
                return new EntityMinecartFurnace(world, d0, d1, d2);

            case 3:
                return new EntityMinecartTNT(world, d0, d1, d2);

            case 4:
                return new EntityMinecartMobSpawner(world, d0, d1, d2);

            case 5:
                return new EntityMinecartHopper(world, d0, d1, d2);
            case 6:
                return new EntityMinecartCommandBlock(world, d0, d1, d2);

            default:
                return new EntityMinecartEmpty(world, d0, d1, d2);
        }
    }

    protected boolean g_() {
        return false;
    }

    protected void c() {
        this.af.a(17, new Integer(0));
        this.af.a(18, new Integer(1));
        this.af.a(19, new Float(0.0F));
        this.af.a(20, new Integer(0));
        this.af.a(21, new Integer(6));
        this.af.a(22, Byte.valueOf((byte) 0));
    }

    public AxisAlignedBB h(Entity entity) {
        return entity.S() ? entity.C : null;
    }

    public AxisAlignedBB J() {
        return null;
    }

    public boolean S() {
        return true;
    }

    public EntityMinecart(World world, double d0, double d1, double d2) {
        this(world);
        this.b(d0, d1, d2);
        this.v = 0.0D;
        this.w = 0.0D;
        this.x = 0.0D;
        this.p = d0;
        this.q = d1;
        this.r = d2;
    }

    public double ae() {
        return (double) this.N * 0.0D - 0.30000001192092896D;
    }

    public boolean a(DamageSource damagesource, float f0) {
        if (!this.o.E && !this.K) {
            if (this.aw()) {
                return false;
            }
            else {
                // CanaryMod: VehicleDamage
                net.canarymod.api.entity.Entity attk = null;

                if (damagesource.j() != null) {
                    attk = damagesource.j().getCanaryEntity();
                }
                VehicleDamageHook hook = (VehicleDamageHook) new VehicleDamageHook((Vehicle) this.entity, attk, new CanaryDamageSource(damagesource), (int) f0).call();
                if (hook.isCanceled()) {
                    return false;
                }
                f0 = hook.getDamageDealt();
                //

                this.j(-this.l());
                this.c(10);
                this.Q();
                this.a(this.j() + f0 * 10.0F);
                boolean flag0 = damagesource.j() instanceof EntityPlayer && ((EntityPlayer) damagesource.j()).bE.d;

                if (flag0 || this.j() > 40.0F) {
                    if (this.l != null) {
                        this.l.a((Entity) this);
                    }

                    if (flag0 && !this.k_()) {
                        this.B();
                    }
                    else {
                        this.a(damagesource);
                    }
                }

                return true;
            }
        }
        else {
            return true;
        }
    }

    public void a(DamageSource damagesource) {
        // CanaryMod: VehicleDestroy
        new VehicleDestroyHook((Vehicle) this.entity).call();
        //
        this.B();
        ItemStack itemstack = new ItemStack(Items.au, 1);

        if (this.b != null) {
            itemstack.c(this.b);
        }

        this.a(itemstack, 0.0F);
    }

    public boolean R() {
        return !this.K;
    }

    public void B() {
        super.B();
    }

    public void h() {
        if (this.k() > 0) {
            this.c(this.k() - 1);
        }

        if (this.j() > 0.0F) {
            this.a(this.j() - 1.0F);
        }

        if (this.t < -64.0D) {
            this.G();
        }

        int i0;

        if (!this.o.E && this.o instanceof WorldServer) {
            this.o.C.a("portal");
            //MinecraftServer minecraftserver = ((WorldServer) this.p).p();

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
                        }
                        else {
                            b0 = -1;
                        }

                        this.b(b0);
                    }

                    this.an = false;
                }
            }
            else {
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

        if (this.o.E) {
            if (this.d > 0) {
                double d0 = this.s + (this.e - this.s) / (double) this.d;
                double d1 = this.t + (this.f - this.t) / (double) this.d;
                double d2 = this.u + (this.g - this.u) / (double) this.d;
                double d3 = MathHelper.g(this.h - (double) this.y);

                this.y = (float) ((double) this.y + d3 / (double) this.d);
                this.z = (float) ((double) this.z + (this.i - (double) this.z) / (double) this.d);
                --this.d;
                this.b(d0, d1, d2);
                this.b(this.y, this.z);
            }
            else {
                this.b(this.s, this.t, this.u);
                this.b(this.y, this.z);
            }
        }
        else {
            float prevRot = this.y, prevPit = this.z;
            double ppX = this.p, ppY = this.q, ppZ = this.r;
            this.p = this.s;
            this.q = this.t;
            this.r = this.u;
            this.w -= 0.03999999910593033D;
            int i1 = MathHelper.c(this.s);

            i0 = MathHelper.c(this.t);
            int i2 = MathHelper.c(this.u);

            if (BlockRailBase.b_(this.o, i1, i0 - 1, i2)) {
                --i0;
            }

            double d4 = 0.4D;
            double d5 = 0.0078125D;
            Block block = this.o.a(i1, i0, i2);

            if (BlockRailBase.a(block)) {
                int i3 = this.o.e(i1, i0, i2);

                this.a(i1, i0, i2, d4, d5, block, i3);
                if (block == Blocks.cc) {
                    // CanaryMod: MinecartActivate
                    MinecartActivateHook mah = new MinecartActivateHook((Minecart) this.getCanaryEntity(), (i1 & 8) != 0);

                    Canary.hooks().callHook(mah);
                    if (!mah.isCanceled()) {
                        this.a(i1, i0, i2, (i3 & 8) != 0);
                    }
                    //
                }
            }
            else {
                this.b(d4);
            }

            this.I();
            this.z = 0.0F;
            double d6 = this.p - this.s;
            double d7 = this.r - this.u;

            if (d6 * d6 + d7 * d7 > 0.001D) {
                this.y = (float) (Math.atan2(d7, d6) * 180.0D / 3.141592653589793D);
                if (this.a) {
                    this.y += 180.0F;
                }
            }

            double d8 = (double) MathHelper.g(this.y - this.A);

            if (d8 < -170.0D || d8 >= 170.0D) {
                this.y += 180.0F;
                this.a = !this.a;
            }

            this.b(this.y, this.z);
            // CanaryMod: VehicleMove
            Vector3D from = new Vector3D(this.p, this.q, this.r);
            Vector3D to = new Vector3D(this.s, this.t, this.u);
            if (Vector3D.getDistance(from, to) > 1.0F) {
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
                    this.z = prevRot;
                    this.ac(); // Update rider
                }
            }
            //
            List list = this.o.b((Entity) this, this.C.b(0.20000000298023224D, 0.0D, 0.20000000298023224D));

            if (list != null && !list.isEmpty()) {
                for (int i4 = 0; i4 < list.size(); ++i4) {
                    Entity entity = (Entity) list.get(i4);

                    if (entity != this.l && entity.S() && entity instanceof EntityMinecart) {
                        entity.g(this);
                    }
                }
            }

            if (this.l != null && this.l.K) {
                if (this.l.m == this) {
                    this.l.m = null;
                }

                this.l = null;
            }

        }
    }

    public void a(int i0, int i1, int i2, boolean flag0) {
    }

    protected void b(double d0) {
        if (this.v < -d0) {
            this.v = -d0;
        }

        if (this.v > d0) {
            this.v = d0;
        }

        if (this.x < -d0) {
            this.x = -d0;
        }

        if (this.x > d0) {
            this.x = d0;
        }

        if (this.D) {
            this.v *= 0.5D;
            this.w *= 0.5D;
            this.x *= 0.5D;
        }

        this.d(this.v, this.w, this.x);
        if (!this.D) {
            this.v *= 0.949999988079071D;
            this.w *= 0.949999988079071D;
            this.x *= 0.949999988079071D;
        }
    }

    protected void a(int i0, int i1, int i2, double d0, double d1, Block block, int i3) {
        this.R = 0.0F;
        Vec3 vec3 = this.a(this.s, this.t, this.u);

        this.t = (double) i1;
        boolean flag0 = false;
        boolean flag1 = false;

        if (block == Blocks.D) {
            flag0 = (i3 & 8) != 0;
            flag1 = !flag0;
        }

        if (((BlockRailBase) block).e()) {
            i3 &= 7;
        }

        if (i3 >= 2 && i3 <= 5) {
            this.t = (double) (i1 + 1);
        }

        if (i3 == 2) {
            this.v -= d1;
        }

        if (i3 == 3) {
            this.v += d1;
        }

        if (i3 == 4) {
            this.x += d1;
        }

        if (i3 == 5) {
            this.x -= d1;
        }

        int[][] aint = c[i3];
        double d2 = (double) (aint[1][0] - aint[0][0]);
        double d3 = (double) (aint[1][2] - aint[0][2]);
        double d4 = Math.sqrt(d2 * d2 + d3 * d3);
        double d5 = this.v * d2 + this.x * d3;

        if (d5 < 0.0D) {
            d2 = -d2;
            d3 = -d3;
        }

        double d6 = Math.sqrt(this.v * this.v + this.x * this.x);

        if (d6 > 2.0D) {
            d6 = 2.0D;
        }

        this.v = d6 * d2 / d4;
        this.x = d6 * d3 / d4;
        double d7;
        double d8;
        double d9;
        double d10;

        if (this.l != null && this.l instanceof EntityLivingBase) {
            d7 = (double) ((EntityLivingBase) this.l).be;
            if (d7 > 0.0D) {
                d8 = -Math.sin((double) (this.l.y * 3.1415927F / 180.0F));
                d9 = Math.cos((double) (this.l.y * 3.1415927F / 180.0F));
                d10 = this.v * this.v + this.x * this.x;
                if (d10 < 0.01D) {
                    this.v += d8 * 0.1D;
                    this.x += d9 * 0.1D;
                    flag1 = false;
                }
            }
        }

        if (flag1) {
            d7 = Math.sqrt(this.v * this.v + this.x * this.x);
            if (d7 < 0.03D) {
                this.v *= 0.0D;
                this.w *= 0.0D;
                this.x *= 0.0D;
            }
            else {
                this.v *= 0.5D;
                this.w *= 0.0D;
                this.x *= 0.5D;
            }
        }

        d7 = 0.0D;
        d8 = (double) i0 + 0.5D + (double) aint[0][0] * 0.5D;
        d9 = (double) i2 + 0.5D + (double) aint[0][2] * 0.5D;
        d10 = (double) i0 + 0.5D + (double) aint[1][0] * 0.5D;
        double d11 = (double) i2 + 0.5D + (double) aint[1][2] * 0.5D;

        d2 = d10 - d8;
        d3 = d11 - d9;
        double d12;
        double d13;

        if (d2 == 0.0D) {
            this.s = (double) i0 + 0.5D;
            d7 = this.u - (double) i2;
        }
        else if (d3 == 0.0D) {
            this.u = (double) i2 + 0.5D;
            d7 = this.s - (double) i0;
        }
        else {
            d12 = this.s - d8;
            d13 = this.u - d9;
            d7 = (d12 * d2 + d13 * d3) * 2.0D;
        }

        this.s = d8 + d2 * d7;
        this.u = d9 + d3 * d7;
        this.b(this.s, this.t + (double) this.L, this.u);
        d12 = this.v;
        d13 = this.x;
        if (this.l != null) {
            d12 *= 0.75D;
            d13 *= 0.75D;
        }

        if (d12 < -d0) {
            d12 = -d0;
        }

        if (d12 > d0) {
            d12 = d0;
        }

        if (d13 < -d0) {
            d13 = -d0;
        }

        if (d13 > d0) {
            d13 = d0;
        }

        this.d(d12, 0.0D, d13);
        if (aint[0][1] != 0 && MathHelper.c(this.s) - i0 == aint[0][0] && MathHelper.c(this.u) - i2 == aint[0][2]) {
            this.b(this.s, this.t + (double) aint[0][1], this.u);
        }
        else if (aint[1][1] != 0 && MathHelper.c(this.s) - i0 == aint[1][0] && MathHelper.c(this.u) - i2 == aint[1][2]) {
            this.b(this.s, this.t + (double) aint[1][1], this.u);
        }

        this.i();
        Vec3 vec31 = this.a(this.s, this.t, this.u);

        if (vec31 != null && vec3 != null) {
            double d14 = (vec3.b - vec31.b) * 0.05D;

            d6 = Math.sqrt(this.v * this.v + this.x * this.x);
            if (d6 > 0.0D) {
                this.v = this.v / d6 * (d6 + d14);
                this.x = this.x / d6 * (d6 + d14);
            }

            this.b(this.s, vec31.b, this.u);
        }

        int i4 = MathHelper.c(this.s);
        int i5 = MathHelper.c(this.u);

        if (i4 != i0 || i5 != i2) {
            d6 = Math.sqrt(this.v * this.v + this.x * this.x);
            this.v = d6 * (double) (i4 - i0);
            this.x = d6 * (double) (i5 - i2);
        }

        if (flag0) {
            double d15 = Math.sqrt(this.v * this.v + this.x * this.x);

            if (d15 > 0.01D) {
                double d16 = 0.06D;

                this.v += this.v / d15 * d16;
                this.x += this.x / d15 * d16;
            }
            else if (i3 == 1) {
                if (this.o.a(i0 - 1, i1, i2).r()) {
                    this.v = 0.02D;
                }
                else if (this.o.a(i0 + 1, i1, i2).r()) {
                    this.v = -0.02D;
                }
            }
            else if (i3 == 0) {
                if (this.o.a(i0, i1, i2 - 1).r()) {
                    this.x = 0.02D;
                }
                else if (this.o.a(i0, i1, i2 + 1).r()) {
                    this.x = -0.02D;
                }
            }
        }
    }

    protected void i() {
        if (this.l != null) {
            this.v *= 0.996999979019165D;
            this.w *= 0.0D;
            this.x *= 0.996999979019165D;
        }
        else {
            this.v *= 0.9599999785423279D;
            this.w *= 0.0D;
            this.x *= 0.9599999785423279D;
        }
    }

    public Vec3 a(double d0, double d1, double d2) {
        int i0 = MathHelper.c(d0);
        int i1 = MathHelper.c(d1);
        int i2 = MathHelper.c(d2);

        if (BlockRailBase.b_(this.o, i0, i1 - 1, i2)) {
            --i1;
        }

        Block block = this.o.a(i0, i1, i2);

        if (BlockRailBase.a(block)) {
            int i3 = this.o.e(i0, i1, i2);

            d1 = (double) i1;
            if (((BlockRailBase) block).e()) {
                i3 &= 7;
            }

            if (i3 >= 2 && i3 <= 5) {
                d1 = (double) (i1 + 1);
            }

            int[][] aint = c[i3];
            double d3 = 0.0D;
            double d4 = (double) i0 + 0.5D + (double) aint[0][0] * 0.5D;
            double d5 = (double) i1 + 0.5D + (double) aint[0][1] * 0.5D;
            double d6 = (double) i2 + 0.5D + (double) aint[0][2] * 0.5D;
            double d7 = (double) i0 + 0.5D + (double) aint[1][0] * 0.5D;
            double d8 = (double) i1 + 0.5D + (double) aint[1][1] * 0.5D;
            double d9 = (double) i2 + 0.5D + (double) aint[1][2] * 0.5D;
            double d10 = d7 - d4;
            double d11 = (d8 - d5) * 2.0D;
            double d12 = d9 - d6;

            if (d10 == 0.0D) {
                d0 = (double) i0 + 0.5D;
                d3 = d2 - (double) i2;
            }
            else if (d12 == 0.0D) {
                d2 = (double) i2 + 0.5D;
                d3 = d0 - (double) i0;
            }
            else {
                double d13 = d0 - d4;
                double d14 = d2 - d6;

                d3 = (d13 * d10 + d14 * d12) * 2.0D;
            }

            d0 = d4 + d10 * d3;
            d1 = d5 + d11 * d3;
            d2 = d6 + d12 * d3;
            if (d11 < 0.0D) {
                ++d1;
            }

            if (d11 > 0.0D) {
                d1 += 0.5D;
            }

            return Vec3.a(d0, d1, d2);
        }
        else {
            return null;
        }
    }

    protected void a(NBTTagCompound nbttagcompound) {
        if (nbttagcompound.n("CustomDisplayTile")) {
            this.k(nbttagcompound.f("DisplayTile"));
            this.l(nbttagcompound.f("DisplayData"));
            this.m(nbttagcompound.f("DisplayOffset"));
        }

        if (nbttagcompound.b("CustomName", 8) && nbttagcompound.j("CustomName").length() > 0) {
            this.b = nbttagcompound.j("CustomName");
        }
    }

    protected void b(NBTTagCompound nbttagcompound) {
        if (this.t()) {
            nbttagcompound.a("CustomDisplayTile", true);
            nbttagcompound.a("DisplayTile", this.n().o() == Material.a ? 0 : Block.b(this.n()));
            nbttagcompound.a("DisplayData", this.p());
            nbttagcompound.a("DisplayOffset", this.r());
        }

        if (this.b != null && this.b.length() > 0) {
            nbttagcompound.a("CustomName", this.b);
        }
    }

    public void g(Entity entity) {
        if (!this.o.E) {
            if (entity != this.l) {
                // CanaryMod: VehicleCollision
                VehicleCollisionHook vch = new VehicleCollisionHook((Vehicle) this.entity, entity.getCanaryEntity());

                Canary.hooks().callHook(vch);
                if (vch.isCanceled()) {
                    return;
                }
                //
                if (entity instanceof EntityLivingBase && !(entity instanceof EntityPlayer) && !(entity instanceof EntityIronGolem) && this.m() == 0 && this.v * this.v + this.x * this.x > 0.01D && this.l == null && entity.m == null) {
                    // CanaryMod: VehicleEnter (Animal/Mob)
                    VehicleEnterHook veh = new VehicleEnterHook((Vehicle) this.entity, (EntityLiving) entity.getCanaryEntity());

                    Canary.hooks().callHook(veh);
                    if (!veh.isCanceled()) {
                        entity.a((Entity) this);
                    }
                    //
                }

                double d0 = entity.s - this.s;
                double d1 = entity.u - this.u;
                double d2 = d0 * d0 + d1 * d1;

                if (d2 >= 9.999999747378752E-5D) {
                    d2 = (double) MathHelper.a(d2);
                    d0 /= d2;
                    d1 /= d2;
                    double d3 = 1.0D / d2;

                    if (d3 > 1.0D) {
                        d3 = 1.0D;
                    }

                    d0 *= d3;
                    d1 *= d3;
                    d0 *= 0.10000000149011612D;
                    d1 *= 0.10000000149011612D;
                    d0 *= (double) (1.0F - this.Y);
                    d1 *= (double) (1.0F - this.Y);
                    d0 *= 0.5D;
                    d1 *= 0.5D;
                    if (entity instanceof EntityMinecart) {
                        double d4 = entity.s - this.s;
                        double d5 = entity.u - this.u;
                        Vec3 vec3 = Vec3.a(d4, 0.0D, d5).a();
                        Vec3 vec31 = Vec3.a((double) MathHelper.b(this.y * 3.1415927F / 180.0F), 0.0D, (double) MathHelper.a(this.y * 3.1415927F / 180.0F)).a();
                        double d6 = Math.abs(vec3.b(vec31));

                        if (d6 < 0.800000011920929D) {
                            return;
                        }

                        double d7 = entity.v + this.v;
                        double d8 = entity.x + this.x;

                        if (((EntityMinecart) entity).m() == 2 && this.m() != 2) {
                            this.v *= 0.20000000298023224D;
                            this.x *= 0.20000000298023224D;
                            this.g(entity.v - d0, 0.0D, entity.x - d1);
                            entity.v *= 0.949999988079071D;
                            entity.x *= 0.949999988079071D;
                        }
                        else if (((EntityMinecart) entity).m() != 2 && this.m() == 2) {
                            entity.v *= 0.20000000298023224D;
                            entity.x *= 0.20000000298023224D;
                            entity.g(this.v + d0, 0.0D, this.x + d1);
                            this.v *= 0.949999988079071D;
                            this.x *= 0.949999988079071D;
                        }
                        else {
                            d7 /= 2.0D;
                            d8 /= 2.0D;
                            this.v *= 0.20000000298023224D;
                            this.x *= 0.20000000298023224D;
                            this.g(d7 - d0, 0.0D, d8 - d1);
                            entity.v *= 0.20000000298023224D;
                            entity.x *= 0.20000000298023224D;
                            entity.g(d7 + d0, 0.0D, d8 + d1);
                        }
                    }
                    else {
                        this.g(-d0, 0.0D, -d1);
                        entity.g(d0 / 4.0D, 0.0D, d1 / 4.0D);
                    }
                }
            }
        }
    }

    public void a(float f0) {
        this.af.b(19, Float.valueOf(f0));
    }

    public float j() {
        return this.af.d(19);
    }

    public void c(int i0) {
        this.af.b(17, Integer.valueOf(i0));
    }

    public int k() {
        return this.af.c(17);
    }

    public void j(int i0) {
        this.af.b(18, Integer.valueOf(i0));
    }

    public int l() {
        return this.af.c(18);
    }

    public abstract int m();

    public Block n() {
        if (!this.t()) {
            return this.o();
        }
        else {
            int i0 = this.z().c(20) & '\uffff';

            return Block.e(i0);
        }
    }

    public Block o() {
        return Blocks.a;
    }

    public int p() {
        return !this.t() ? this.q() : this.z().c(20) >> 16;
    }

    public int q() {
        return 0;
    }

    public int r() {
        return !this.t() ? this.s() : this.z().c(21);
    }

    public int s() {
        return 6;
    }

    public void k(int i0) {
        this.z().b(20, Integer.valueOf(i0 & '\uffff' | this.p() << 16));
        this.a(true);
    }

    public void l(int i0) {
        this.z().b(20, Integer.valueOf(Block.b(this.n()) & '\uffff' | i0 << 16));
        this.a(true);
    }

    public void m(int i0) {
        this.z().b(21, Integer.valueOf(i0));
        this.a(true);
    }

    public boolean t() {
        return this.z().a(22) == 1;
    }

    public void a(boolean flag0) {
        this.z().b(22, Byte.valueOf((byte) (flag0 ? 1 : 0)));
    }

    public void a(String s0) {
        this.b = s0;
    }

    public String b_() {
        return this.b != null ? this.b : super.b_();
    }

    public boolean k_() {
        return this.b != null;
    }

    public String u() {
        return this.b;
    }
}
