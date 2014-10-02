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
import net.minecraft.block.BlockRailPowered;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityMinecartCommandBlock;
import net.minecraft.entity.ai.EntityMinecartMobSpawner;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.entity.item.EntityMinecartEmpty;
import net.minecraft.entity.item.EntityMinecartFurnace;
import net.minecraft.entity.item.EntityMinecartHopper;
import net.minecraft.entity.item.EntityMinecartTNT;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.IWorldNameable;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.List;
import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.Map;

public abstract class EntityMinecart extends Entity implements IWorldNameable {

    private boolean a;
    private String b;
    private static final int[][][] c = new int[][][] { { { 0, 0, -1}, { 0, 0, 1}}, { { -1, 0, 0}, { 1, 0, 0}}, { { -1, -1, 0}, { 1, 0, 0}}, { { -1, 0, 0}, { 1, -1, 0}}, { { 0, 0, -1}, { 0, -1, 1}}, { { 0, -1, -1}, { 0, 0, 1}}, { { 0, 0, 1}, { 1, 0, 0}}, { { 0, 0, 1}, { -1, 0, 0}}, { { 0, 0, -1}, { -1, 0, 0}}, { { 0, 0, -1}, { 1, 0, 0}}};
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
    }

    public static EntityMinecart a(World world, double d0, double d1, double d2, EntityMinecart.EnumMinecartType entityminecart_enumminecarttype) {
        switch (EntityMinecart.SwitchEnumMinecartType.a[entityminecart_enumminecarttype.ordinal()]) {
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

    protected boolean r_() {
        return false;
    }

    protected void h() {
        this.ac.a(17, new Integer(0));
        this.ac.a(18, new Integer(1));
        this.ac.a(19, new Float(0.0F));
        this.ac.a(20, new Integer(0));
        this.ac.a(21, new Integer(6));
        this.ac.a(22, Byte.valueOf((byte) 0));
    }

    public AxisAlignedBB j(Entity entity) {
        return entity.ae() ? entity.aQ() : null;
    }

    public AxisAlignedBB S() {
        return null;
    }

    public boolean ae() {
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

    public double an() {
        return (double) this.K * 0.5D - 0.20000000298023224D;
    }

    public boolean a(DamageSource damagesource, float f0) {
        if (!this.o.D && !this.I) {
            if (this.b(damagesource)) {
                return false;
            } else {
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

               this.k(-this.r());
                this.j(10);
                this.ac();
                this.a(this.p() + f0 * 10.0F);
                boolean flag0 = damagesource.j() instanceof EntityPlayer && ((EntityPlayer) damagesource.j()).by.d;

                if (flag0 || this.p() > 40.0F) {
                    if (this.l != null) {
                        this.l.a((Entity) null);
                    }

                    if (flag0 && !this.k_()) {
                        this.J();
                    } else {
                        this.a(damagesource);
                    }
                }

                return true;
            }
        } else {
            return true;
        }
    }

    public void a(DamageSource damagesource) {
        // CanaryMod: VehicleDestroy
        new VehicleDestroyHook((Vehicle) this.entity).call();
        //
        this.J();
        ItemStack itemstack = new ItemStack(Items.az, 1);

        if (this.b != null) {
            itemstack.c(this.b);
        }

        this.a(itemstack, 0.0F);
    }

    public boolean ad() {
        return !this.I;
    }

    public void J() {
        super.J();
    }

    public void s_() {
        if (this.q() > 0) {
            this.j(this.q() - 1);
        }

        if (this.p() > 0.0F) {
            this.a(this.p() - 1.0F);
        }

        if (this.t < -64.0D) {
            this.O();
        }

        int i0;

        if (!this.o.D && this.o instanceof WorldServer) {
            this.o.B.a("portal");
            //MinecraftServer minecraftserver = ((WorldServer) this.o).r();

            i0 = this.L();
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

        if (this.o.D) {
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
            } else {
                this.b(this.s, this.t, this.u);
                this.b(this.y, this.z);
            }

        } else {
            float prevRot = this.y, prevPit = this.z;
            double ppX = this.p, ppY = this.q, ppZ = this.r;
            this.p = this.s;
            this.q = this.t;
            this.r = this.u;
            this.w -= 0.03999999910593033D;
            int i1 = MathHelper.c(this.s);

            i0 = MathHelper.c(this.t);
            int i2 = MathHelper.c(this.u);

            if (BlockRailBase.d(this.o, new BlockPos(i1, i0 - 1, i2))) {
                --i0;
            }

            BlockPos blockpos = new BlockPos(i1, i0, i2);
            IBlockState iblockstate = this.o.p(blockpos);

                this.a(blockpos, iblockstate);
                if (iblockstate.c() == Blocks.cs) {
                    // CanaryMod: MinecartActivate
                    MinecartActivateHook mah = new MinecartActivateHook((Minecart) this.getCanaryEntity(), (i1 & 8) != 0);

                    Canary.hooks().callHook(mah);
                    if (!mah.isCanceled()) {
                        this.a(i1, i0, i2, ((Boolean) iblockstate.b(BlockRailPowered.M)).booleanValue());
                    }
                    //
                }
            } else {
                this.n();
            }

            this.Q();
            this.z = 0.0F;
            double d4 = this.p - this.s;
            double d5 = this.r - this.u;

            if (d4 * d4 + d5 * d5 > 0.001D) {
                this.y = (float) (Math.atan2(d5, d4) * 180.0D / 3.141592653589793D);
                if (this.a) {
                    this.y += 180.0F;
                }
            }

            double d6 = (double) MathHelper.g(this.y - this.A);

            if (d6 < -170.0D || d6 >= 170.0D) {
                this.y += 180.0F;
                this.a = !this.a;
            }

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
                    this.z = prevRot;
                    this.al(); // Update rider
                }
            }
            //
            Iterator iterator = this.o.b((Entity) this, this.aQ().b(0.20000000298023224D, 0.0D, 0.20000000298023224D)).iterator();

            while (iterator.hasNext()) {
                Entity entity = (Entity) iterator.next();

                if (entity != this.l && entity.ae() && entity instanceof EntityMinecart) {
                    entity.i(this);
                }
            }

            if (this.l != null && this.l.I) {
                if (this.l.m == this) {
                    this.l.m = null;
                }

                this.l = null;
            }

            this.W();
        }
    }

    protected double m() {
        return 0.4D;
    }

    public void a(int i0, int i1, int i2, boolean flag0) {}

    protected void n() {
        double d0 = this.m();

        this.v = MathHelper.a(this.v, -d0, d0);
        this.x = MathHelper.a(this.x, -d0, d0);
        if (this.C) {
            this.v *= 0.5D;
            this.w *= 0.5D;
            this.x *= 0.5D;
        }

        this.d(this.v, this.w, this.x);
        if (!this.C) {
            this.v *= 0.949999988079071D;
            this.w *= 0.949999988079071D;
            this.x *= 0.949999988079071D;
        }

    }

    protected void a(BlockPos blockpos, IBlockState iblockstate) {
        this.O = 0.0F;
        Vec3 vec3 = this.k(this.s, this.t, this.u);

        this.t = (double) blockpos.o();
        boolean flag0 = false;
        boolean flag1 = false;
        BlockRailBase blockrailbase = (BlockRailBase) iblockstate.c();

        if (blockrailbase == Blocks.D) {
            flag0 = ((Boolean) iblockstate.b(BlockRailPowered.M)).booleanValue();
            flag1 = !flag0;
        }

        double d0 = 0.0078125D;
        BlockRailBase.EnumRailDirection blockrailbase_enumraildirection = (BlockRailBase.EnumRailDirection) iblockstate.b(blockrailbase.l());

        switch (EntityMinecart.SwitchEnumMinecartType.b[blockrailbase_enumraildirection.ordinal()]) {
            case 1:
                this.v -= 0.0078125D;
                ++this.t;
                break;

            case 2:
                this.v += 0.0078125D;
                ++this.t;
                break;

            case 3:
                this.x += 0.0078125D;
                ++this.t;
                break;

            case 4:
                this.x -= 0.0078125D;
                ++this.t;
        }

        int[][] aint = c[blockrailbase_enumraildirection.a()];
        double d1 = (double) (aint[1][0] - aint[0][0]);
        double d2 = (double) (aint[1][2] - aint[0][2]);
        double d3 = Math.sqrt(d1 * d1 + d2 * d2);
        double d4 = this.v * d1 + this.x * d2;

        if (d4 < 0.0D) {
            d1 = -d1;
            d2 = -d2;
        }

        double d5 = Math.sqrt(this.v * this.v + this.x * this.x);

        if (d5 > 2.0D) {
            d5 = 2.0D;
        }

        this.v = d5 * d1 / d3;
        this.x = d5 * d2 / d3;
        double d6;
        double d7;
        double d8;
        double d9;

        if (this.l instanceof EntityLivingBase) {
            d6 = (double) ((EntityLivingBase) this.l).aY;
            if (d6 > 0.0D) {
                d7 = -Math.sin((double) (this.l.y * 3.1415927F / 180.0F));
                d8 = Math.cos((double) (this.l.y * 3.1415927F / 180.0F));
                d9 = this.v * this.v + this.x * this.x;
                if (d9 < 0.01D) {
                    this.v += d7 * 0.1D;
                    this.x += d8 * 0.1D;
                    flag1 = false;
                }
            }
        }

        if (flag1) {
            d6 = Math.sqrt(this.v * this.v + this.x * this.x);
            if (d6 < 0.03D) {
                this.v *= 0.0D;
                this.w *= 0.0D;
                this.x *= 0.0D;
            } else {
                this.v *= 0.5D;
                this.w *= 0.0D;
                this.x *= 0.5D;
            }
        }

        d6 = 0.0D;
        d7 = (double) blockpos.n() + 0.5D + (double) aint[0][0] * 0.5D;
        d8 = (double) blockpos.p() + 0.5D + (double) aint[0][2] * 0.5D;
        d9 = (double) blockpos.n() + 0.5D + (double) aint[1][0] * 0.5D;
        double d10 = (double) blockpos.p() + 0.5D + (double) aint[1][2] * 0.5D;

        d1 = d9 - d7;
        d2 = d10 - d8;
        double d11;
        double d12;

        if (d1 == 0.0D) {
            this.s = (double) blockpos.n() + 0.5D;
            d6 = this.u - (double) blockpos.p();
        } else if (d2 == 0.0D) {
            this.u = (double) blockpos.p() + 0.5D;
            d6 = this.s - (double) blockpos.n();
        } else {
            d11 = this.s - d7;
            d12 = this.u - d8;
            d6 = (d11 * d1 + d12 * d2) * 2.0D;
        }

        this.s = d7 + d1 * d6;
        this.u = d8 + d2 * d6;
        this.b(this.s, this.t, this.u);
        d11 = this.v;
        d12 = this.x;
        if (this.l != null) {
            d11 *= 0.75D;
            d12 *= 0.75D;
        }

        double d13 = this.m();

        d11 = MathHelper.a(d11, -d13, d13);
        d12 = MathHelper.a(d12, -d13, d13);
        this.d(d11, 0.0D, d12);
        if (aint[0][1] != 0 && MathHelper.c(this.s) - blockpos.n() == aint[0][0] && MathHelper.c(this.u) - blockpos.p() == aint[0][2]) {
            this.b(this.s, this.t + (double) aint[0][1], this.u);
        } else if (aint[1][1] != 0 && MathHelper.c(this.s) - blockpos.n() == aint[1][0] && MathHelper.c(this.u) - blockpos.p() == aint[1][2]) {
            this.b(this.s, this.t + (double) aint[1][1], this.u);
        }

        this.o();
        Vec3 vec31 = this.k(this.s, this.t, this.u);

        if (vec31 != null && vec3 != null) {
            double d14 = (vec3.b - vec31.b) * 0.05D;

            d5 = Math.sqrt(this.v * this.v + this.x * this.x);
            if (d5 > 0.0D) {
                this.v = this.v / d5 * (d5 + d14);
                this.x = this.x / d5 * (d5 + d14);
            }

            this.b(this.s, vec31.b, this.u);
        }

        int i0 = MathHelper.c(this.s);
        int i1 = MathHelper.c(this.u);

        if (i0 != blockpos.n() || i1 != blockpos.p()) {
            d5 = Math.sqrt(this.v * this.v + this.x * this.x);
            this.v = d5 * (double) (i0 - blockpos.n());
            this.x = d5 * (double) (i1 - blockpos.p());
        }

        if (flag0) {
            double d15 = Math.sqrt(this.v * this.v + this.x * this.x);

            if (d15 > 0.01D) {
                double d16 = 0.06D;

                this.v += this.v / d15 * d16;
                this.x += this.x / d15 * d16;
            } else if (blockrailbase_enumraildirection == BlockRailBase.EnumRailDirection.EAST_WEST) {
                if (this.o.p(blockpos.e()).c().t()) {
                    this.v = 0.02D;
                } else if (this.o.p(blockpos.f()).c().t()) {
                    this.v = -0.02D;
                }
            } else if (blockrailbase_enumraildirection == BlockRailBase.EnumRailDirection.NORTH_SOUTH) {
                if (this.o.p(blockpos.c()).c().t()) {
                    this.x = 0.02D;
                } else if (this.o.p(blockpos.d()).c().t()) {
                    this.x = -0.02D;
                }
            }
        }

    }

    protected void o() {
        if (this.l != null) {
            this.v *= 0.996999979019165D;
            this.w *= 0.0D;
            this.x *= 0.996999979019165D;
        } else {
            this.v *= 0.9599999785423279D;
            this.w *= 0.0D;
            this.x *= 0.9599999785423279D;
        }

    }

    public void b(double d0, double d1, double d2) {
        this.s = d0;
        this.t = d1;
        this.u = d2;
        float f0 = this.J / 2.0F;
        float f1 = this.K;

        this.a(new AxisAlignedBB(d0 - (double) f0, d1, d2 - (double) f0, d0 + (double) f0, d1 + (double) f1, d2 + (double) f0));
    }

    public Vec3 k(double d0, double d1, double d2) {
        int i0 = MathHelper.c(d0);
        int i1 = MathHelper.c(d1);
        int i2 = MathHelper.c(d2);

        if (BlockRailBase.d(this.o, new BlockPos(i0, i1 - 1, i2))) {
            --i1;
        }

        IBlockState iblockstate = this.o.p(new BlockPos(i0, i1, i2));

        if (BlockRailBase.d(iblockstate)) {
            BlockRailBase.EnumRailDirection blockrailbase_enumraildirection = (BlockRailBase.EnumRailDirection) iblockstate.b(((BlockRailBase) iblockstate.c()).l());
            int[][] aint = c[blockrailbase_enumraildirection.a()];
            double d3 = 0.0D;
            double d4 = (double) i0 + 0.5D + (double) aint[0][0] * 0.5D;
            double d5 = (double) i1 + 0.0625D + (double) aint[0][1] * 0.5D;
            double d6 = (double) i2 + 0.5D + (double) aint[0][2] * 0.5D;
            double d7 = (double) i0 + 0.5D + (double) aint[1][0] * 0.5D;
            double d8 = (double) i1 + 0.0625D + (double) aint[1][1] * 0.5D;
            double d9 = (double) i2 + 0.5D + (double) aint[1][2] * 0.5D;
            double d10 = d7 - d4;
            double d11 = (d8 - d5) * 2.0D;
            double d12 = d9 - d6;

            if (d10 == 0.0D) {
                d0 = (double) i0 + 0.5D;
                d3 = d2 - (double) i2;
            } else if (d12 == 0.0D) {
                d2 = (double) i2 + 0.5D;
                d3 = d0 - (double) i0;
            } else {
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

            return new Vec3(d0, d1, d2);
        } else {
            return null;
        }
    }

    protected void a(NBTTagCompound nbttagcompound) {
        if (nbttagcompound.n("CustomDisplayTile")) {
            int i0 = nbttagcompound.f("DisplayData");
            Block block;

            if (nbttagcompound.b("DisplayTile", 8)) {
                block = Block.b(nbttagcompound.j("DisplayTile"));
                if (block == null) {
                    this.a(Blocks.a.P());
                } else {
                    this.a(block.a(i0));
                }
            } else {
                block = Block.c(nbttagcompound.f("DisplayTile"));
                if (block == null) {
                    this.a(Blocks.a.P());
                } else {
                    this.a(block.a(i0));
                }
            }

            this.l(nbttagcompound.f("DisplayOffset"));
        }

        if (nbttagcompound.b("CustomName", 8) && nbttagcompound.j("CustomName").length() > 0) {
            this.b = nbttagcompound.j("CustomName");
        }

    }

    protected void b(NBTTagCompound nbttagcompound) {
        if (this.x()) {
            nbttagcompound.a("CustomDisplayTile", true);
            IBlockState iblockstate = this.t();
            ResourceLocation resourcelocation = (ResourceLocation) Block.c.c(iblockstate.c());

            nbttagcompound.a("DisplayTile", resourcelocation == null ? "" : resourcelocation.toString());
            nbttagcompound.a("DisplayData", iblockstate.c().c(iblockstate));
            nbttagcompound.a("DisplayOffset", this.v());
        }

        if (this.b != null && this.b.length() > 0) {
            nbttagcompound.a("CustomName", this.b);
        }
    }

    public void g(Entity entity) {
        if (!this.o.D) {
            if (!entity.T && !this.T) {
                if (entity != this.l) {
                    if (entity instanceof EntityLivingBase && !(entity instanceof EntityPlayer) && !(entity instanceof EntityIronGolem) && this.s() == EntityMinecart.EnumMinecartType.RIDEABLE && this.v * this.v + this.x * this.x > 0.01D && this.l == null && entity.m == null) {

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
                            d0 *= (double) (1.0F - this.U);
                            d1 *= (double) (1.0F - this.U);
                            d0 *= 0.5D;
                            d1 *= 0.5D;
                            if (entity instanceof EntityMinecart) {
                                double d4 = entity.s - this.s;
                                double d5 = entity.u - this.u;
                                Vec3 vec3 = (new Vec3(d4, 0.0D, d5)).a();
                                Vec3 vec31 = (new Vec3((double) MathHelper.b(this.y * 3.1415927F / 180.0F), 0.0D, (double) MathHelper.a(this.y * 3.1415927F / 180.0F))).a();
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
                            } else if (((EntityMinecart) entity).m() != 2 && this.m() == 2) {
                                entity.v *= 0.20000000298023224D;
                                entity.x *= 0.20000000298023224D;
                                entity.g(this.v + d0, 0.0D, this.x + d1);
                                this.v *= 0.949999988079071D;
                                this.x *= 0.949999988079071D;
                            } else {
                                d7 /= 2.0D;
                                d8 /= 2.0D;
                                this.v *= 0.20000000298023224D;
                                this.x *= 0.20000000298023224D;
                                this.g(d7 - d0, 0.0D, d8 - d1);
                                entity.v *= 0.20000000298023224D;
                                entity.x *= 0.20000000298023224D;
                                entity.g(d7 + d0, 0.0D, d8 + d1);
                            }
                        } else {
                            this.g(-d0, 0.0D, -d1);
                            entity.g(d0 / 4.0D, 0.0D, d1 / 4.0D);
                        }
                    }
                }
            }
        }
    }

    public void a(float f0) {
        this.ac.b(19, Float.valueOf(f0));
    }

    public float p() {
        return this.ac.d(19);
    }

    public void j(int i0) {
        this.ac.b(17, Integer.valueOf(i0));
    }

    public int q() {
        return this.ac.c(17);
    }

    public void k(int i0) {
        this.ac.b(18, Integer.valueOf(i0));
    }

    public int r() {
        return this.ac.c(18);
    }

    public abstract EntityMinecart.EnumMinecartType s();

    public IBlockState t() {
        return !this.x() ? this.u() : Block.d(this.H().c(20));
    }

    public IBlockState u() {
        return Blocks.a.P();
    }

    public int v() {
        return !this.x() ? this.w() : this.H().c(21);
    }

    public int w() {
        return 6;
    }

    public void a(IBlockState iblockstate) {
        this.H().b(20, Integer.valueOf(Block.f(iblockstate)));
        this.a(true);
    }

    public void l(int i0) {
        this.H().b(21, Integer.valueOf(i0));
        this.a(true);
    }

    public boolean x() {
        return this.H().a(22) == 1;
    }

    public void a(boolean flag0) {
        this.H().b(22, Byte.valueOf((byte) (flag0 ? 1 : 0)));
    }

    public void a(String s0) {
        this.b = s0;
    }

    public String d_() {
        return this.b != null ? this.b : super.d_();
    }

    public boolean k_() {
        return this.b != null;
    }

    public String aL() {
        return this.b;
    }

    public IChatComponent e_() {
        if (this.k_()) {
            ChatComponentText chatcomponenttext = new ChatComponentText(this.b);

            chatcomponenttext.b().a(this.aP());
            chatcomponenttext.b().a(this.aJ().toString());
            return chatcomponenttext;
        } else {
            ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation(this.d_(), new Object[0]);

            chatcomponenttranslation.b().a(this.aP());
            chatcomponenttranslation.b().a(this.aJ().toString());
            return chatcomponenttranslation;
        }
    }

    public static enum EnumMinecartType {

        RIDEABLE("RIDEABLE", 0, 0, "MinecartRideable"), CHEST("CHEST", 1, 1, "MinecartChest"), FURNACE("FURNACE", 2, 2, "MinecartFurnace"), TNT("TNT", 3, 3, "MinecartTNT"), SPAWNER("SPAWNER", 4, 4, "MinecartSpawner"), HOPPER("HOPPER", 5, 5, "MinecartHopper"), COMMAND_BLOCK("COMMAND_BLOCK", 6, 6, "MinecartCommandBlock");
        private static final Map h = Maps.newHashMap();
        private final int i;
        private final String j;

        private static final EntityMinecart.EnumMinecartType[] $VALUES = new EntityMinecart.EnumMinecartType[] { RIDEABLE, CHEST, FURNACE, TNT, SPAWNER, HOPPER, COMMAND_BLOCK};
      
        private EnumMinecartType(String p_i45847_1_, int p_i45847_2_, int p_i45847_3_, String p_i45847_4_) {
            this.i = p_i45847_3_;
            this.j = p_i45847_4_;
        }

        public int a() {
            return this.i;
        }

        public String b() {
            return this.j;
        }

        public static EntityMinecart.EnumMinecartType a(int p_a_0_) {
            EntityMinecart.EnumMinecartType i0 = (EntityMinecart.EnumMinecartType) h.get(Integer.valueOf(p_a_0_));

            return i0 == null ? RIDEABLE : i0;
        }

        static {
            EntityMinecart.EnumMinecartType[] aentityminecart_enumminecarttype = values();
            int i0 = aentityminecart_enumminecarttype.length;

            for (int i1 = 0; i1 < i0; ++i1) {
                EntityMinecart.EnumMinecartType entityminecart_enumminecarttype1 = aentityminecart_enumminecarttype[i1];

                h.put(Integer.valueOf(entityminecart_enumminecarttype1.a()), entityminecart_enumminecarttype1);
            }

        }
    }


    static final class SwitchEnumMinecartType {

        static final int[] a;

        static final int[] b = new int[BlockRailBase.EnumRailDirection.values().length];
      
        static {
            try {
                b[BlockRailBase.EnumRailDirection.ASCENDING_EAST.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror90) {
                ;
            }

            try {
                b[BlockRailBase.EnumRailDirection.ASCENDING_WEST.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                b[BlockRailBase.EnumRailDirection.ASCENDING_NORTH.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                b[BlockRailBase.EnumRailDirection.ASCENDING_SOUTH.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

            a = new int[EntityMinecart.EnumMinecartType.values().length];

            try {
                a[EntityMinecart.EnumMinecartType.CHEST.ordinal()] = 1;
            } catch (NoSuchFieldError nosuchfielderror4) {
                ;
            }

            try {
                a[EntityMinecart.EnumMinecartType.FURNACE.ordinal()] = 2;
            } catch (NoSuchFieldError nosuchfielderror5) {
                ;
            }

            try {
                a[EntityMinecart.EnumMinecartType.TNT.ordinal()] = 3;
            } catch (NoSuchFieldError nosuchfielderror6) {
                ;
            }

            try {
                a[EntityMinecart.EnumMinecartType.SPAWNER.ordinal()] = 4;
            } catch (NoSuchFieldError nosuchfielderror7) {
                ;
            }

            try {
                a[EntityMinecart.EnumMinecartType.HOPPER.ordinal()] = 5;
            } catch (NoSuchFieldError nosuchfielderror8) {
                ;
            }

            try {
                a[EntityMinecart.EnumMinecartType.COMMAND_BLOCK.ordinal()] = 6;
            } catch (NoSuchFieldError nosuchfielderror9) {
                ;
            }

        }
    }
}
