package net.minecraft.world;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.canarymod.api.entity.living.humanoid.CanaryHuman;
import net.canarymod.api.entity.vehicle.CanaryVehicle;
import net.canarymod.api.world.CanaryWorld;
import net.canarymod.config.Configuration;
import net.canarymod.config.WorldConfiguration;
import net.canarymod.hook.entity.EntitySpawnHook;
import net.canarymod.hook.entity.VehicleCollisionHook;
import net.canarymod.hook.world.WeatherChangeHook;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.IEntitySelector;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.INpc;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.profiler.Profiler;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.village.VillageCollection;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldInfo;

import java.util.*;
import java.util.concurrent.Callable;

public abstract class World implements IBlockAccess {

    protected boolean e;
    public final List f = Lists.newArrayList();
    protected final List g = Lists.newArrayList();
    public final List h = Lists.newArrayList();
    public final List i = Lists.newArrayList();
    private final List a = Lists.newArrayList();
    private final List b = Lists.newArrayList();
    public final List j = Lists.newArrayList();
    public final List k = Lists.newArrayList();
    protected final IntHashMap l = new IntHashMap();
    private long c = 16777215L;
    private int d;
    protected int m = (new Random()).nextInt();
    protected final int n = 1013904223;
    protected float o;
    protected float p;
    protected float q;
    protected float r;
    private int I;
    public final Random s = new Random();
    public final WorldProvider t;
    protected List u = Lists.newArrayList();
    protected IChunkProvider v;
    protected final ISaveHandler w;
    public WorldInfo x; // CanaryMod: protected => public
    protected boolean y;
    protected MapStorage z;
    protected VillageCollection A;
    public final Profiler B;
    private final Calendar J = Calendar.getInstance();
    public Scoreboard C = new Scoreboard(); // Protected => public
    public final boolean D;
    protected Set E = Sets.newHashSet();
    private int K;
    protected boolean F;
    protected boolean G;
    private boolean L;
    private final WorldBorder M;
    int[] H;

    // CanaryMod: multiworld
    public CanaryWorld canaryDimension;

    public World(ISaveHandler isavehandler, WorldInfo worldinfo, WorldProvider worldprovider, Profiler profiler, boolean flag0, net.canarymod.api.world.DimensionType type) {
        this.K = this.s.nextInt(12000);
        this.F = true;
        this.G = true;
        this.H = new int['\u8000'];
        this.w = isavehandler;
        this.B = profiler;
        this.x = worldinfo;
        this.t = worldprovider;
        this.D = flag0;
        this.M = worldprovider.r();

        // CanaryMod set dimension type in world provider
        canaryDimension = new CanaryWorld(worldinfo.k(), (WorldServer) this, type);
        this.t.setCanaryDimensionType(type);
    }

    public World b() {
        return this;
    }

    public BiomeGenBase b(final BlockPos blockpos) {
        if (this.e(blockpos)) {
            Chunk chunk = this.f(blockpos);

            try {
                return chunk.a(blockpos, this.t.m());
            }
            catch (Throwable throwable) {
                CrashReport crashreport = CrashReport.a(throwable, "Getting biome");
                CrashReportCategory crashreportcategory = crashreport.a("Coordinates of biome request");

                crashreportcategory.a("Location", new Callable() {

                    public String call() {
                        return CrashReportCategory.a(blockpos);
                    }
                });
                throw new ReportedException(crashreport);
            }

        }
        else {
            return this.t.m().a(blockpos, BiomeGenBase.q);
        }
    }

    public WorldChunkManager v() {
        return this.t.m();
    }

    protected abstract IChunkProvider k();

    public void a(WorldSettings worldsettings) {
        this.x.d(true);
    }

    public Block c(BlockPos blockpos) {
        BlockPos blockpos1;

        for (blockpos1 = new BlockPos(blockpos.n(), 63, blockpos.p()); !this.d(blockpos1.a()); blockpos1 = blockpos1.a()) {
            ;
        }

        return this.p(blockpos1).c();
    }

    private boolean a(BlockPos blockpos) {
        return blockpos.n() >= -30000000 && blockpos.p() >= -30000000 && blockpos.n() < 30000000 && blockpos.p() < 30000000 && blockpos.o() >= 0 && blockpos.o() < 256;
    }

    public boolean d(BlockPos blockpos) {
        return this.p(blockpos).c().r() == Material.a;
    }

    public boolean e(BlockPos blockpos) {
        return this.a(blockpos, true);
    }

    public boolean a(BlockPos blockpos, boolean flag0) {
        return !this.a(blockpos) ? false : this.a(blockpos.n() >> 4, blockpos.p() >> 4, flag0);
    }

    public boolean a(BlockPos blockpos, int i0) {
        return this.a(blockpos, i0, true);
    }

    public boolean a(BlockPos blockpos, int i0, boolean flag0) {
        return this.a(blockpos.n() - i0, blockpos.o() - i0, blockpos.p() - i0, blockpos.n() + i0, blockpos.o() + i0, blockpos.p() + i0, flag0);
    }

    public boolean a(BlockPos blockpos, BlockPos blockpos1) {
        return this.a(blockpos, blockpos1, true);
    }

    public boolean a(BlockPos blockpos, BlockPos blockpos1, boolean flag0) {
        return this.a(blockpos.n(), blockpos.o(), blockpos.p(), blockpos1.n(), blockpos1.o(), blockpos1.p(), flag0);
    }

    public boolean a(StructureBoundingBox structureboundingbox) {
        return this.b(structureboundingbox, true);
    }

    public boolean b(StructureBoundingBox structureboundingbox, boolean flag0) {
        return this.a(structureboundingbox.a, structureboundingbox.b, structureboundingbox.c, structureboundingbox.d, structureboundingbox.e, structureboundingbox.f, flag0);
    }

    private boolean a(int i0, int i1, int i2, int i3, int i4, int i5, boolean flag0) {
        if (i4 >= 0 && i1 < 256) {
            i0 >>= 4;
            i2 >>= 4;
            i3 >>= 4;
            i5 >>= 4;

            for (int i6 = i0; i6 <= i3; ++i6) {
                for (int i7 = i2; i7 <= i5; ++i7) {
                    if (!this.a(i6, i7, flag0)) {
                        return false;
                    }
                }
            }

            return true;
        }
        else {
            return false;
        }
    }

    protected boolean a(int i0, int i1, boolean flag0) {
        return this.v.a(i0, i1) && (flag0 || !this.v.d(i0, i1).f());
    }

    public Chunk f(BlockPos blockpos) {
        return this.a(blockpos.n() >> 4, blockpos.p() >> 4);
    }

    public Chunk a(int i0, int i1) {
        return this.v.d(i0, i1);
    }

    public boolean a(BlockPos blockpos, IBlockState iblockstate, int i0) {
        if (!this.a(blockpos)) {
            return false;
        }
        else if (!this.D && this.x.u() == WorldType.g) {
            return false;
        }
        else {
            Chunk chunk = this.f(blockpos);
            Block block = iblockstate.c();
            IBlockState iblockstate1 = chunk.a(blockpos, iblockstate);

            /* FIXME
            // CanaryMod: BlockUpdate
                boolean flag0 = chunk.a(i0 & 15, i1, i2 & 15, block, i3);
                CanaryBlock cblock;
                if (canaryDimension != null) {
                    cblock = (CanaryBlock) this.canaryDimension.getBlockAt(i0, i1, i2);
                    BlockUpdateHook hook = (BlockUpdateHook) new BlockUpdateHook(cblock, i3).call();
                    if (hook.isCanceled()) {
                        flag0 = false;
                    }
                    //
                }
            */
            if (iblockstate1 == null) {
                return false;
            }
            else {
                Block block1 = iblockstate1.c();

                if (block.n() != block1.n() || block.p() != block1.p()) {
                    this.B.a("checkLight");
                    this.x(blockpos);
                    this.B.b();
                }

                if ((i0 & 2) != 0 && (!this.D || (i0 & 4) == 0) && chunk.i()) {
                    this.h(blockpos);
                }

                if (!this.D && (i0 & 1) != 0) {
                    this.b(blockpos, iblockstate1.c());
                    if (block.N()) {
                        this.e(blockpos, block);
                    }
                }

                return true;
            }
        }
    }

    public boolean g(BlockPos blockpos) {
        return this.a(blockpos, Blocks.a.P(), 3);
    }

    public boolean b(BlockPos blockpos, boolean flag0) {
        IBlockState iblockstate = this.p(blockpos);
        Block block = iblockstate.c();

        if (block.r() == Material.a) {
            return false;
        }
        else {
            this.b(2001, blockpos, Block.f(iblockstate));
            if (flag0) {
                block.b(this, blockpos, iblockstate, 0);
            }

            return this.a(blockpos, Blocks.a.P(), 3);
        }
    }

    public boolean a(BlockPos blockpos, IBlockState iblockstate) {
        return this.a(blockpos, iblockstate, 3);
    }

    public void h(BlockPos blockpos) {
        for (int i0 = 0; i0 < this.u.size(); ++i0) {
            ((IWorldAccess) this.u.get(i0)).a(blockpos);
        }

    }

    public void b(BlockPos blockpos, Block block) {
        if (this.x.u() != WorldType.g) {
            this.c(blockpos, block);
        }

    }

    public void a(int i0, int i1, int i2, int i3) {
        int i4;

        if (i2 > i3) {
            i4 = i3;
            i3 = i2;
            i2 = i4;
        }

        if (!this.t.o()) {
            for (i4 = i2; i4 <= i3; ++i4) {
                this.c(EnumSkyBlock.SKY, new BlockPos(i0, i4, i1));
            }
        }

        this.b(i0, i2, i1, i0, i3, i1);
    }

    public void b(BlockPos blockpos, BlockPos blockpos1) {
        this.b(blockpos.n(), blockpos.o(), blockpos.p(), blockpos1.n(), blockpos1.o(), blockpos1.p());
    }

    public void b(int i0, int i1, int i2, int i3, int i4, int i5) {
        for (int i6 = 0; i6 < this.u.size(); ++i6) {
            ((IWorldAccess) this.u.get(i6)).a(i0, i1, i2, i3, i4, i5);
        }

    }

    public void c(BlockPos blockpos, Block block) {
        this.d(blockpos.e(), block);
        this.d(blockpos.f(), block);
        this.d(blockpos.b(), block);
        this.d(blockpos.a(), block);
        this.d(blockpos.c(), block);
        this.d(blockpos.d(), block);
    }

    public void a(BlockPos blockpos, Block block, EnumFacing enumfacing) {
        if (enumfacing != EnumFacing.WEST) {
            this.d(blockpos.e(), block);
        }

        if (enumfacing != EnumFacing.EAST) {
            this.d(blockpos.f(), block);
        }

        if (enumfacing != EnumFacing.DOWN) {
            this.d(blockpos.b(), block);
        }

        if (enumfacing != EnumFacing.UP) {
            this.d(blockpos.a(), block);
        }

        if (enumfacing != EnumFacing.NORTH) {
            this.d(blockpos.c(), block);
        }

        if (enumfacing != EnumFacing.SOUTH) {
            this.d(blockpos.d(), block);
        }

    }

    public void d(BlockPos blockpos, final Block block) {
        if (!this.D) {
            IBlockState iblockstate = this.p(blockpos);

            try {
                iblockstate.c().a(this, blockpos, iblockstate, block);
            }
            catch (Throwable throwable) {
                CrashReport crashreport = CrashReport.a(throwable, "Exception while updating neighbours");
                CrashReportCategory crashreportcategory = crashreport.a("Block being updated");

                crashreportcategory.a("Source block type", new Callable() {

                    public String call() {
                        try {
                            return String.format("ID #%d (%s // %s)", new Object[]{Integer.valueOf(Block.a(block)), block.a(), block.getClass().getCanonicalName()});
                        }
                        catch (Throwable throwable1) {
                            return "ID #" + Block.a(block);
                        }
                    }
                });
                CrashReportCategory.a(crashreportcategory, blockpos, iblockstate);
                throw new ReportedException(crashreport);
            }
        }
    }

    public boolean a(BlockPos blockpos, Block block) {
        return false;
    }

    public boolean i(BlockPos blockpos) {
        return this.f(blockpos).d(blockpos);
    }

    public boolean j(BlockPos blockpos) {
        if (blockpos.o() >= 63) {
            return this.i(blockpos);
        }
        else {
            BlockPos blockpos1 = new BlockPos(blockpos.n(), 63, blockpos.p());

            if (!this.i(blockpos1)) {
                return false;
            }
            else {
                for (blockpos1 = blockpos1.b(); blockpos1.o() > blockpos.o(); blockpos1 = blockpos1.b()) {
                    Block block = this.p(blockpos1).c();

                    if (block.n() > 0 && !block.r().d()) {
                        return false;
                    }
                }

                return true;
            }
        }
    }

    public int k(BlockPos blockpos) {
        if (blockpos.o() < 0) {
            return 0;
        }
        else {
            if (blockpos.o() >= 256) {
                blockpos = new BlockPos(blockpos.n(), 255, blockpos.p());
            }

            return this.f(blockpos).a(blockpos, 0);
        }
    }

    public int l(BlockPos blockpos) {
        return this.c(blockpos, true);
    }

    public int c(BlockPos blockpos, boolean flag0) {
        if (blockpos.n() >= -30000000 && blockpos.p() >= -30000000 && blockpos.n() < 30000000 && blockpos.p() < 30000000) {
            if (flag0 && this.p(blockpos).c().q()) {
                int i0 = this.c(blockpos.a(), false);
                int i1 = this.c(blockpos.f(), false);
                int i2 = this.c(blockpos.e(), false);
                int i3 = this.c(blockpos.d(), false);
                int i4 = this.c(blockpos.c(), false);

                if (i1 > i0) {
                    i0 = i1;
                }

                if (i2 > i0) {
                    i0 = i2;
                }

                if (i3 > i0) {
                    i0 = i3;
                }

                if (i4 > i0) {
                    i0 = i4;
                }

                return i0;
            }
            else if (blockpos.o() < 0) {
                return 0;
            }
            else {
                if (blockpos.o() >= 256) {
                    blockpos = new BlockPos(blockpos.n(), 255, blockpos.p());
                }

                Chunk chunk = this.f(blockpos);

                return chunk.a(blockpos, this.d);
            }
        }
        else {
            return 15;
        }
    }

    public BlockPos m(BlockPos blockpos) {
        int i0;

        if (blockpos.n() >= -30000000 && blockpos.p() >= -30000000 && blockpos.n() < 30000000 && blockpos.p() < 30000000) {
            if (this.a(blockpos.n() >> 4, blockpos.p() >> 4, true)) {
                i0 = this.a(blockpos.n() >> 4, blockpos.p() >> 4).b(blockpos.n() & 15, blockpos.p() & 15);
            }
            else {
                i0 = 0;
            }
        }
        else {
            i0 = 64;
        }

        return new BlockPos(blockpos.n(), i0, blockpos.p());
    }

    public int b(int i0, int i1) {
        if (i0 >= -30000000 && i1 >= -30000000 && i0 < 30000000 && i1 < 30000000) {
            if (!this.a(i0 >> 4, i1 >> 4, true)) {
                return 0;
            }
            else {
                Chunk chunk = this.a(i0 >> 4, i1 >> 4);

                return chunk.v();
            }
        }
        else {
            return 64;
        }
    }

    public int b(EnumSkyBlock enumskyblock, BlockPos blockpos) {
        if (blockpos.o() < 0) {
            blockpos = new BlockPos(blockpos.n(), 0, blockpos.p());
        }

        if (!this.a(blockpos)) {
            return enumskyblock.c;
        }
        else if (!this.e(blockpos)) {
            return enumskyblock.c;
        }
        else {
            Chunk chunk = this.f(blockpos);

            return chunk.a(enumskyblock, blockpos);
        }
    }

    public void a(EnumSkyBlock enumskyblock, BlockPos blockpos, int i0) {
        if (this.a(blockpos)) {
            if (this.e(blockpos)) {
                Chunk chunk = this.f(blockpos);

                chunk.a(enumskyblock, blockpos, i0);
                this.n(blockpos);
            }
        }
    }

    public void n(BlockPos blockpos) {
        for (int i0 = 0; i0 < this.u.size(); ++i0) {
            ((IWorldAccess) this.u.get(i0)).b(blockpos);
        }

    }

    public float o(BlockPos blockpos) {
        return this.t.p()[this.l(blockpos)];
    }

    public IBlockState p(BlockPos blockpos) {
        if (!this.a(blockpos)) {
            return Blocks.a.P();
        }
        else {
            Chunk chunk = this.f(blockpos);

            return chunk.g(blockpos);
        }
    }

    public boolean w() {
        return this.d < 4;
    }

    public MovingObjectPosition a(Vec3 vec3, Vec3 vec31) {
        return this.a(vec3, vec31, false, false, false);
    }

    public MovingObjectPosition a(Vec3 vec3, Vec3 vec31, boolean flag0) {
        return this.a(vec3, vec31, flag0, false, false);
    }

    public MovingObjectPosition a(Vec3 vec3, Vec3 vec31, boolean flag0, boolean flag1, boolean flag2) {
        if (!Double.isNaN(vec3.a) && !Double.isNaN(vec3.b) && !Double.isNaN(vec3.c)) {
            if (!Double.isNaN(vec31.a) && !Double.isNaN(vec31.b) && !Double.isNaN(vec31.c)) {
                int i0 = MathHelper.c(vec31.a);
                int i1 = MathHelper.c(vec31.b);
                int i2 = MathHelper.c(vec31.c);
                int i3 = MathHelper.c(vec3.a);
                int i4 = MathHelper.c(vec3.b);
                int i5 = MathHelper.c(vec3.c);
                BlockPos blockpos = new BlockPos(i3, i4, i5);

                new BlockPos(i0, i1, i2);
                IBlockState iblockstate = this.p(blockpos);
                Block block = iblockstate.c();

                if ((!flag1 || block.a(this, blockpos, iblockstate) != null) && block.a(iblockstate, flag0)) {
                    MovingObjectPosition movingobjectposition = block.a(this, blockpos, vec3, vec31);

                    if (movingobjectposition != null) {
                        return movingobjectposition;
                    }
                }

                MovingObjectPosition movingobjectposition1 = null;
                int i6 = 200;

                while (i6-- >= 0) {
                    if (Double.isNaN(vec3.a) || Double.isNaN(vec3.b) || Double.isNaN(vec3.c)) {
                        return null;
                    }

                    if (i3 == i0 && i4 == i1 && i5 == i2) {
                        return flag2 ? movingobjectposition1 : null;
                    }

                    boolean flag3 = true;
                    boolean flag4 = true;
                    boolean flag5 = true;
                    double d0 = 999.0D;
                    double d1 = 999.0D;
                    double d2 = 999.0D;

                    if (i0 > i3) {
                        d0 = (double) i3 + 1.0D;
                    }
                    else if (i0 < i3) {
                        d0 = (double) i3 + 0.0D;
                    }
                    else {
                        flag3 = false;
                    }

                    if (i1 > i4) {
                        d1 = (double) i4 + 1.0D;
                    }
                    else if (i1 < i4) {
                        d1 = (double) i4 + 0.0D;
                    }
                    else {
                        flag4 = false;
                    }

                    if (i2 > i5) {
                        d2 = (double) i5 + 1.0D;
                    }
                    else if (i2 < i5) {
                        d2 = (double) i5 + 0.0D;
                    }
                    else {
                        flag5 = false;
                    }

                    double d3 = 999.0D;
                    double d4 = 999.0D;
                    double d5 = 999.0D;
                    double d6 = vec31.a - vec3.a;
                    double d7 = vec31.b - vec3.b;
                    double d8 = vec31.c - vec3.c;

                    if (flag3) {
                        d3 = (d0 - vec3.a) / d6;
                    }

                    if (flag4) {
                        d4 = (d1 - vec3.b) / d7;
                    }

                    if (flag5) {
                        d5 = (d2 - vec3.c) / d8;
                    }

                    if (d3 == -0.0D) {
                        d3 = -1.0E-4D;
                    }

                    if (d4 == -0.0D) {
                        d4 = -1.0E-4D;
                    }

                    if (d5 == -0.0D) {
                        d5 = -1.0E-4D;
                    }

                    EnumFacing enumfacing;

                    if (d3 < d4 && d3 < d5) {
                        enumfacing = i0 > i3 ? EnumFacing.WEST : EnumFacing.EAST;
                        vec3 = new Vec3(d0, vec3.b + d7 * d3, vec3.c + d8 * d3);
                    }
                    else if (d4 < d5) {
                        enumfacing = i1 > i4 ? EnumFacing.DOWN : EnumFacing.UP;
                        vec3 = new Vec3(vec3.a + d6 * d4, d1, vec3.c + d8 * d4);
                    }
                    else {
                        enumfacing = i2 > i5 ? EnumFacing.NORTH : EnumFacing.SOUTH;
                        vec3 = new Vec3(vec3.a + d6 * d5, vec3.b + d7 * d5, d2);
                    }

                    i3 = MathHelper.c(vec3.a) - (enumfacing == EnumFacing.EAST ? 1 : 0);
                    i4 = MathHelper.c(vec3.b) - (enumfacing == EnumFacing.UP ? 1 : 0);
                    i5 = MathHelper.c(vec3.c) - (enumfacing == EnumFacing.SOUTH ? 1 : 0);
                    blockpos = new BlockPos(i3, i4, i5);
                    IBlockState iblockstate1 = this.p(blockpos);
                    Block block1 = iblockstate1.c();

                    if (!flag1 || block1.a(this, blockpos, iblockstate1) != null) {
                        if (block1.a(iblockstate1, flag0)) {
                            MovingObjectPosition movingobjectposition2 = block1.a(this, blockpos, vec3, vec31);

                            if (movingobjectposition2 != null) {
                                return movingobjectposition2;
                            }
                        }
                        else {
                            movingobjectposition1 = new MovingObjectPosition(MovingObjectPosition.MovingObjectType.MISS, vec3, enumfacing, blockpos);
                        }
                    }
                }

                return flag2 ? movingobjectposition1 : null;
            }
            else {
                return null;
            }
        }
        else {
            return null;
        }
    }

    public void a(Entity entity, String s0, float f0, float f1) {
        for (int i0 = 0; i0 < this.u.size(); ++i0) {
            ((IWorldAccess) this.u.get(i0)).a(s0, entity.s, entity.t, entity.u, f0, f1);
        }

    }

    public void a(EntityPlayer entityplayer, String s0, float f0, float f1) {
        for (int i0 = 0; i0 < this.u.size(); ++i0) {
            ((IWorldAccess) this.u.get(i0)).a(entityplayer, s0, entityplayer.s, entityplayer.t, entityplayer.u, f0, f1);
        }

    }

    public void a(double d0, double d1, double d2, String s0, float f0, float f1) {
        for (int i0 = 0; i0 < this.u.size(); ++i0) {
            ((IWorldAccess) this.u.get(i0)).a(s0, d0, d1, d2, f0, f1);
        }

    }

    public void a(double d0, double d1, double d2, String s0, float f0, float f1, boolean flag0) {
    }

    public void a(BlockPos blockpos, String s0) {
        for (int i0 = 0; i0 < this.u.size(); ++i0) {
            ((IWorldAccess) this.u.get(i0)).a(s0, blockpos);
        }

    }

    public void a(EnumParticleTypes enumparticletypes, double d0, double d1, double d2, double d3, double d4, double d5, int... aint) {
        this.a(enumparticletypes.c(), enumparticletypes.e(), d0, d1, d2, d3, d4, d5, aint);
    }

    private void a(int i0, boolean flag0, double d0, double d1, double d2, double d3, double d4, double d5, int... aint) {
        for (int i1 = 0; i1 < this.u.size(); ++i1) {
            ((IWorldAccess) this.u.get(i1)).a(i0, flag0, d0, d1, d2, d3, d4, d5, aint);
        }

    }

    public boolean c(Entity entity) {
        this.k.add(entity);
        return true;
    }

    public boolean d(Entity entity) {

        // Check Entity for spawning
        if (!canSpawn(entity)) {
            return false;
        }
        // CanaryMod: EntitySpawn
        if (!(entity.getCanaryEntity() instanceof CanaryHuman)) {
            EntitySpawnHook hook = (EntitySpawnHook) new EntitySpawnHook(entity.getCanaryEntity()).call();
            if (hook.isCanceled()) {
                return false;
            }
        }
        //
        int i0 = MathHelper.c(entity.s / 16.0D);
        int i1 = MathHelper.c(entity.u / 16.0D);
        boolean flag0 = entity.n;

        if (entity instanceof EntityPlayer) {
            flag0 = true;
        }

        if (!flag0 && !this.a(i0, i1, true)) {
            return false;
        }
        else {
            if (entity instanceof EntityPlayerMP) { // CanaryMod: dont handle NPC's this way
                EntityPlayer entityplayer = (EntityPlayer) entity;

                this.j.add(entityplayer);
                this.d();
            }

            this.a(i0, i1).a(entity);
            this.f.add(entity);
            this.a(entity);
            return true;
        }
    }

    protected void a(Entity entity) {
        for (int i0 = 0; i0 < this.u.size(); ++i0) {
            ((IWorldAccess) this.u.get(i0)).a(entity);
        }

    }

    protected void b(Entity entity) {
        for (int i0 = 0; i0 < this.u.size(); ++i0) {
            ((IWorldAccess) this.u.get(i0)).b(entity);
        }

    }

    public void e(Entity entity) {
        if (entity.l != null) {
            entity.l.a((Entity) null);
        }

        if (entity.m != null) {
            entity.a((Entity) null);
        }

        entity.J();
        if (entity instanceof EntityPlayer) {
            this.j.remove(entity);
            this.d();
            this.b(entity);
        }

    }

    public void f(Entity entity) {
        entity.J();
        if (entity instanceof EntityPlayer) {
            this.j.remove(entity);
            this.d();
        }

        int i0 = entity.ae;
        int i1 = entity.ag;

        if (entity.ad && this.a(i0, i1, true)) {
            this.a(i0, i1).b(entity);
        }

        this.f.remove(entity);
        this.b(entity);
    }

    public void a(IWorldAccess iworldaccess) {
        this.u.add(iworldaccess);
    }

    public List a(Entity entity, AxisAlignedBB axisalignedbb) {
        ArrayList arraylist = Lists.newArrayList();
        int i0 = MathHelper.c(axisalignedbb.a);
        int i1 = MathHelper.c(axisalignedbb.d + 1.0D);
        int i2 = MathHelper.c(axisalignedbb.b);
        int i3 = MathHelper.c(axisalignedbb.e + 1.0D);
        int i4 = MathHelper.c(axisalignedbb.c);
        int i5 = MathHelper.c(axisalignedbb.f + 1.0D);

        for (int i6 = i0; i6 < i1; ++i6) {
            for (int i7 = i4; i7 < i5; ++i7) {
                if (this.e(new BlockPos(i6, 64, i7))) {
                    for (int i8 = i2 - 1; i8 < i3; ++i8) {
                        BlockPos blockpos = new BlockPos(i6, i8, i7);
                        boolean flag0 = entity.aS();
                        boolean flag1 = this.a(this.af(), entity);

                        if (flag0 && flag1) {
                            entity.h(false);
                        }
                        else if (!flag0 && !flag1) {
                            entity.h(true);
                        }

                        IBlockState iblockstate;

                        if (!this.af().a(blockpos) && flag1) {
                            iblockstate = Blocks.b.P();
                        }
                        else {
                            iblockstate = this.p(blockpos);
                        }

                        iblockstate.c().a(this, blockpos, iblockstate, axisalignedbb, arraylist, entity);
                    }
                }
            }
        }

        // CanaryMod: Implemented fix via M4411K4 VEHICLE_COLLISION hook
        CanaryVehicle vehicle = null;

        if (entity instanceof EntityMinecart || entity instanceof EntityBoat) {
            vehicle = (CanaryVehicle) entity.getCanaryEntity();
        }

        double d0 = 0.25D;
        List list = this.b(entity, axisalignedbb.b(d0, d0, d0));

        for (int i9 = 0; i9 < list.size(); ++i9) {
            if (entity.l != list && entity.m != list) {
                Entity entity1 = (Entity) list.get(i9); // CanaryMod: split these two lines
                AxisAlignedBB axisalignedbb1 = entity1.S();

                if (axisalignedbb1 != null && axisalignedbb1.b(axisalignedbb)) {
                    // CanaryMod: this collided with a boat
                    if (vehicle != null) {
                        VehicleCollisionHook vch = (VehicleCollisionHook) new VehicleCollisionHook(vehicle, entity.getCanaryEntity()).call();
                        if (vch.isCanceled()) {
                            continue;
                        }
                    }
                    //
                    arraylist.add(axisalignedbb1);
                }

                axisalignedbb1 = entity.j((Entity) list.get(i9));
                if (axisalignedbb1 != null && axisalignedbb1.b(axisalignedbb)) {
                    // CanaryMod: this collided with entity
                    if (vehicle != null) {
                        VehicleCollisionHook vch = (VehicleCollisionHook) new VehicleCollisionHook(vehicle, entity.getCanaryEntity()).call();
                        if (vch.isCanceled()) {
                            continue;
                        }
                    }
                    //
                    arraylist.add(axisalignedbb1);
                }
            }
        }

        return arraylist;
    }

    public boolean a(WorldBorder worldborder, Entity entity) {
        double d0 = worldborder.b();
        double d1 = worldborder.c();
        double d2 = worldborder.d();
        double d3 = worldborder.e();

        if (entity.aS()) {
            ++d0;
            ++d1;
            --d2;
            --d3;
        }
        else {
            --d0;
            --d1;
            ++d2;
            ++d3;
        }

        return entity.s > d0 && entity.s < d2 && entity.u > d1 && entity.u < d3;
    }

    public List a(AxisAlignedBB axisalignedbb) {
        ArrayList arraylist = Lists.newArrayList();
        int i0 = MathHelper.c(axisalignedbb.a);
        int i1 = MathHelper.c(axisalignedbb.d + 1.0D);
        int i2 = MathHelper.c(axisalignedbb.b);
        int i3 = MathHelper.c(axisalignedbb.e + 1.0D);
        int i4 = MathHelper.c(axisalignedbb.c);
        int i5 = MathHelper.c(axisalignedbb.f + 1.0D);

        for (int i6 = i0; i6 < i1; ++i6) {
            for (int i7 = i4; i7 < i5; ++i7) {
                if (this.e(new BlockPos(i6, 64, i7))) {
                    for (int i8 = i2 - 1; i8 < i3; ++i8) {
                        BlockPos blockpos = new BlockPos(i6, i8, i7);
                        IBlockState iblockstate;

                        if (i6 >= -30000000 && i6 < 30000000 && i7 >= -30000000 && i7 < 30000000) {
                            iblockstate = this.p(blockpos);
                        }
                        else {
                            iblockstate = Blocks.h.P();
                        }

                        iblockstate.c().a(this, blockpos, iblockstate, axisalignedbb, arraylist, (Entity) null);
                    }
                }
            }
        }

        return arraylist;
    }

    public int a(float f0) {
        float f1 = this.c(f0);
        float f2 = 1.0F - (MathHelper.b(f1 * 3.1415927F * 2.0F) * 2.0F + 0.5F);

        f2 = MathHelper.a(f2, 0.0F, 1.0F);
        f2 = 1.0F - f2;
        f2 = (float) ((double) f2 * (1.0D - (double) (this.j(f0) * 5.0F) / 16.0D));
        f2 = (float) ((double) f2 * (1.0D - (double) (this.h(f0) * 5.0F) / 16.0D));
        f2 = 1.0F - f2;
        return (int) (f2 * 11.0F);
    }

    public float c(float f0) {
        return this.t.a(this.x.g(), f0);
    }

    public float y() {
        return WorldProvider.a[this.t.a(this.x.g())];
    }

    public float d(float f0) {
        float f1 = this.c(f0);

        return f1 * 3.1415927F * 2.0F;
    }

    public BlockPos q(BlockPos blockpos) {
        return this.f(blockpos).h(blockpos);
    }

    public BlockPos r(BlockPos blockpos) {
        Chunk chunk = this.f(blockpos);

        BlockPos blockpos1;
        BlockPos blockpos2;

        for (blockpos1 = new BlockPos(blockpos.n(), chunk.g() + 16, blockpos.p()); blockpos1.o() >= 0; blockpos1 = blockpos2) {
            blockpos2 = blockpos1.b();
            Material material = chunk.a(blockpos2).r();

            if (material.c() && material != Material.j) {
                break;
            }
        }

        return blockpos1;
    }

    public void a(BlockPos blockpos, Block block, int i0) {
    }

    public void a(BlockPos blockpos, Block block, int i0, int i1) {
    }

    public void b(BlockPos blockpos, Block block, int i0, int i1) {
    }

    public void i() {
        this.B.a("entities");
        this.B.a("global");

        int i0;
        Entity entity;
        CrashReport crashreport;
        CrashReportCategory crashreportcategory;

        for (i0 = 0; i0 < this.k.size(); ++i0) {
            entity = (Entity) this.k.get(i0);

            try {
                ++entity.W;
                entity.s_();
            }
            catch (Throwable throwable) {
                crashreport = CrashReport.a(throwable, "Ticking entity");
                crashreportcategory = crashreport.a("Entity being ticked");
                if (entity == null) {
                    crashreportcategory.a("Entity", (Object) "~~NULL~~");
                }
                else {
                    entity.a(crashreportcategory);
                }

                throw new ReportedException(crashreport);
            }

            if (entity.I) {
                this.k.remove(i0--);
            }
        }

        this.B.c("remove");
        this.f.removeAll(this.g);

        int i1;
        int i2;

        for (i0 = 0; i0 < this.g.size(); ++i0) {
            entity = (Entity) this.g.get(i0);
            i1 = entity.ae;
            i2 = entity.ag;
            if (entity.ad && this.a(i1, i2, true)) {
                this.a(i1, i2).b(entity);
            }
        }

        for (i0 = 0; i0 < this.g.size(); ++i0) {
            this.b((Entity) this.g.get(i0));
        }

        this.g.clear();
        this.B.c("regular");

        for (i0 = 0; i0 < this.f.size(); ++i0) {
            entity = (Entity) this.f.get(i0);
            if (entity.m != null) {
                if (!entity.m.I && entity.m.l == entity) {
                    continue;
                }

                entity.m.l = null;
                entity.m = null;
            }

            this.B.a("tick");
            if (!entity.I) {
                try {
                    this.g(entity);
                }
                catch (Throwable throwable1) {
                    crashreport = CrashReport.a(throwable1, "Ticking entity");
                    crashreportcategory = crashreport.a("Entity being ticked");
                    entity.a(crashreportcategory);
                    throw new ReportedException(crashreport);
                }
            }

            this.B.b();
            this.B.a("remove");
            if (entity.I) {
                i1 = entity.ae;
                i2 = entity.ag;
                if (entity.ad && this.a(i1, i2, true)) {
                    this.a(i1, i2).b(entity);
                }

                this.f.remove(i0--);
                this.b(entity);
            }

            this.B.b();
        }

        this.B.c("blockEntities");
        this.L = true;
        Iterator iterator = this.i.iterator();

        while (iterator.hasNext()) {
            TileEntity tileentity = (TileEntity) iterator.next();

            if (!tileentity.x() && tileentity.t()) {
                BlockPos blockpos = tileentity.v();

                if (this.e(blockpos) && this.M.a(blockpos)) {
                    try {
                        ((IUpdatePlayerListBox) tileentity).c();
                    }
                    catch (Throwable throwable2) {
                        CrashReport crashreport1 = CrashReport.a(throwable2, "Ticking block entity");
                        CrashReportCategory crashreportcategory1 = crashreport1.a("Block entity being ticked");

                        tileentity.a(crashreportcategory1);
                        throw new ReportedException(crashreport1);
                    }
                }
            }

            if (tileentity.x()) {
                iterator.remove();
                this.h.remove(tileentity);
                if (this.e(tileentity.v())) {
                    this.f(tileentity.v()).e(tileentity.v());
                }
            }
        }

        this.L = false;
        if (!this.b.isEmpty()) {
            this.i.removeAll(this.b);
            this.h.removeAll(this.b);
            this.b.clear();
        }

        this.B.c("pendingBlockEntities");
        if (!this.a.isEmpty()) {
            for (int i3 = 0; i3 < this.a.size(); ++i3) {
                TileEntity tileentity1 = (TileEntity) this.a.get(i3);

                if (!tileentity1.x()) {
                    if (!this.h.contains(tileentity1)) {
                        this.a(tileentity1);
                    }

                    if (this.e(tileentity1.v())) {
                        this.f(tileentity1.v()).a(tileentity1.v(), tileentity1);
                    }

                    this.h(tileentity1.v());
                }
            }

            this.a.clear();
        }

        this.B.b();
        this.B.b();
    }

    public boolean a(TileEntity tileentity) {
        boolean flag0 = this.h.add(tileentity);

        if (flag0 && tileentity instanceof IUpdatePlayerListBox) {
            this.i.add(tileentity);
        }

        return flag0;
    }

    public void a(Collection collection) {
        if (this.L) {
            this.a.addAll(collection);
        }
        else {
            Iterator iterator = collection.iterator();

            while (iterator.hasNext()) {
                TileEntity tileentity = (TileEntity) iterator.next();

                this.h.add(tileentity);
                if (tileentity instanceof IUpdatePlayerListBox) {
                    this.i.add(tileentity);
                }
            }
        }

    }

    public void g(Entity entity) {
        this.a(entity, true);
    }

    public void a(Entity entity, boolean flag0) {
        int i0 = MathHelper.c(entity.s);
        int i1 = MathHelper.c(entity.u);
        byte b0 = 32;

        if (!flag0 || this.a(i0 - b0, 0, i1 - b0, i0 + b0, 0, i1 + b0, true)) {
            entity.P = entity.s;
            entity.Q = entity.t;
            entity.R = entity.u;
            entity.A = entity.y;
            entity.B = entity.z;
            if (flag0 && entity.ad) {
                ++entity.W;
                if (entity.m != null) {
                    entity.ak();
                }
                else {
                    entity.s_();
                }
            }

            this.B.a("chunkCheck");
            if (Double.isNaN(entity.s) || Double.isInfinite(entity.s)) {
                entity.s = entity.P;
            }

            if (Double.isNaN(entity.t) || Double.isInfinite(entity.t)) {
                entity.t = entity.Q;
            }

            if (Double.isNaN(entity.u) || Double.isInfinite(entity.u)) {
                entity.u = entity.R;
            }

            if (Double.isNaN((double) entity.z) || Double.isInfinite((double) entity.z)) {
                entity.z = entity.B;
            }

            if (Double.isNaN((double) entity.y) || Double.isInfinite((double) entity.y)) {
                entity.y = entity.A;
            }

            int i2 = MathHelper.c(entity.s / 16.0D);
            int i3 = MathHelper.c(entity.t / 16.0D);
            int i4 = MathHelper.c(entity.u / 16.0D);

            if (!entity.ad || entity.ae != i2 || entity.af != i3 || entity.ag != i4) {
                if (entity.ad && this.a(entity.ae, entity.ag, true)) {
                    this.a(entity.ae, entity.ag).a(entity, entity.af);
                }

                if (this.a(i2, i4, true)) {
                    entity.ad = true;
                    this.a(i2, i4).a(entity);
                }
                else {
                    entity.ad = false;
                }
            }

            this.B.b();
            if (flag0 && entity.ad && entity.l != null) {
                if (!entity.l.I && entity.l.m == entity) {
                    this.g(entity.l);
                }
                else {
                    entity.l.m = null;
                    entity.l = null;
                }
            }

        }
    }

    public boolean b(AxisAlignedBB axisalignedbb) {
        return this.a(axisalignedbb, (Entity) null);
    }

    public boolean a(AxisAlignedBB axisalignedbb, Entity entity) {
        List list = this.b((Entity) null, axisalignedbb);

        for (int i0 = 0; i0 < list.size(); ++i0) {
            Entity entity1 = (Entity) list.get(i0);

            if (!entity1.I && entity1.k && entity1 != entity && (entity == null || entity.m != entity1 && entity.l != entity1)) {
                return false;
            }
        }

        return true;
    }

    public boolean c(AxisAlignedBB axisalignedbb) {
        int i0 = MathHelper.c(axisalignedbb.a);
        int i1 = MathHelper.c(axisalignedbb.d);
        int i2 = MathHelper.c(axisalignedbb.b);
        int i3 = MathHelper.c(axisalignedbb.e);
        int i4 = MathHelper.c(axisalignedbb.c);
        int i5 = MathHelper.c(axisalignedbb.f);

        for (int i6 = i0; i6 <= i1; ++i6) {
            for (int i7 = i2; i7 <= i3; ++i7) {
                for (int i8 = i4; i8 <= i5; ++i8) {
                    Block block = this.p(new BlockPos(i6, i7, i8)).c();

                    if (block.r() != Material.a) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean d(AxisAlignedBB axisalignedbb) {
        int i0 = MathHelper.c(axisalignedbb.a);
        int i1 = MathHelper.c(axisalignedbb.d);
        int i2 = MathHelper.c(axisalignedbb.b);
        int i3 = MathHelper.c(axisalignedbb.e);
        int i4 = MathHelper.c(axisalignedbb.c);
        int i5 = MathHelper.c(axisalignedbb.f);

        for (int i6 = i0; i6 <= i1; ++i6) {
            for (int i7 = i2; i7 <= i3; ++i7) {
                for (int i8 = i4; i8 <= i5; ++i8) {
                    Block block = this.p(new BlockPos(i6, i7, i8)).c();

                    if (block.r().d()) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean e(AxisAlignedBB axisalignedbb) {
        int i0 = MathHelper.c(axisalignedbb.a);
        int i1 = MathHelper.c(axisalignedbb.d + 1.0D);
        int i2 = MathHelper.c(axisalignedbb.b);
        int i3 = MathHelper.c(axisalignedbb.e + 1.0D);
        int i4 = MathHelper.c(axisalignedbb.c);
        int i5 = MathHelper.c(axisalignedbb.f + 1.0D);

        if (this.a(i0, i2, i4, i1, i3, i5, true)) {
            for (int i6 = i0; i6 < i1; ++i6) {
                for (int i7 = i2; i7 < i3; ++i7) {
                    for (int i8 = i4; i8 < i5; ++i8) {
                        Block block = this.p(new BlockPos(i6, i7, i8)).c();

                        if (block == Blocks.ab || block == Blocks.k || block == Blocks.l) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    public boolean a(AxisAlignedBB axisalignedbb, Material material, Entity entity) {
        int i0 = MathHelper.c(axisalignedbb.a);
        int i1 = MathHelper.c(axisalignedbb.d + 1.0D);
        int i2 = MathHelper.c(axisalignedbb.b);
        int i3 = MathHelper.c(axisalignedbb.e + 1.0D);
        int i4 = MathHelper.c(axisalignedbb.c);
        int i5 = MathHelper.c(axisalignedbb.f + 1.0D);

        if (!this.a(i0, i2, i4, i1, i3, i5, true)) {
            return false;
        }
        else {
            boolean flag0 = false;
            Vec3 vec3 = new Vec3(0.0D, 0.0D, 0.0D);

            for (int i6 = i0; i6 < i1; ++i6) {
                for (int i7 = i2; i7 < i3; ++i7) {
                    for (int i8 = i4; i8 < i5; ++i8) {
                        BlockPos blockpos = new BlockPos(i6, i7, i8);
                        IBlockState iblockstate = this.p(blockpos);
                        Block block = iblockstate.c();

                        if (block.r() == material) {
                            double d0 = (double) ((float) (i7 + 1) - BlockLiquid.b(((Integer) iblockstate.b(BlockLiquid.b)).intValue()));

                            if ((double) i3 >= d0) {
                                flag0 = true;
                                vec3 = block.a(this, blockpos, entity, vec3);
                            }
                        }
                    }
                }
            }

            if (vec3.b() > 0.0D && entity.aK()) {
                vec3 = vec3.a();
                double d1 = 0.014D;

                entity.v += vec3.a * d1;
                entity.w += vec3.b * d1;
                entity.x += vec3.c * d1;
            }

            return flag0;
        }
    }

    public boolean a(AxisAlignedBB axisalignedbb, Material material) {
        int i0 = MathHelper.c(axisalignedbb.a);
        int i1 = MathHelper.c(axisalignedbb.d + 1.0D);
        int i2 = MathHelper.c(axisalignedbb.b);
        int i3 = MathHelper.c(axisalignedbb.e + 1.0D);
        int i4 = MathHelper.c(axisalignedbb.c);
        int i5 = MathHelper.c(axisalignedbb.f + 1.0D);

        for (int i6 = i0; i6 < i1; ++i6) {
            for (int i7 = i2; i7 < i3; ++i7) {
                for (int i8 = i4; i8 < i5; ++i8) {
                    if (this.p(new BlockPos(i6, i7, i8)).c().r() == material) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean b(AxisAlignedBB axisalignedbb, Material material) {
        int i0 = MathHelper.c(axisalignedbb.a);
        int i1 = MathHelper.c(axisalignedbb.d + 1.0D);
        int i2 = MathHelper.c(axisalignedbb.b);
        int i3 = MathHelper.c(axisalignedbb.e + 1.0D);
        int i4 = MathHelper.c(axisalignedbb.c);
        int i5 = MathHelper.c(axisalignedbb.f + 1.0D);

        for (int i6 = i0; i6 < i1; ++i6) {
            for (int i7 = i2; i7 < i3; ++i7) {
                for (int i8 = i4; i8 < i5; ++i8) {
                    BlockPos blockpos = new BlockPos(i6, i7, i8);
                    IBlockState iblockstate = this.p(blockpos);
                    Block block = iblockstate.c();

                    if (block.r() == material) {
                        int i9 = ((Integer) iblockstate.b(BlockLiquid.b)).intValue();
                        double d0 = (double) (i7 + 1);

                        if (i9 < 8) {
                            d0 = (double) (i7 + 1) - (double) i9 / 8.0D;
                        }

                        if (d0 >= axisalignedbb.b) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    public Explosion a(Entity entity, double d0, double d1, double d2, float f0, boolean flag0) {
        return this.a(entity, d0, d1, d2, f0, false, flag0);
    }

    public Explosion a(Entity entity, double d0, double d1, double d2, float f0, boolean flag0, boolean flag1) {
        Explosion explosion = new Explosion(this, entity, d0, d1, d2, f0, flag0, flag1);

        explosion.a();
        explosion.a(true);
        return explosion;
    }

    public float a(Vec3 vec3, AxisAlignedBB axisalignedbb) {
        double d0 = 1.0D / ((axisalignedbb.d - axisalignedbb.a) * 2.0D + 1.0D);
        double d1 = 1.0D / ((axisalignedbb.e - axisalignedbb.b) * 2.0D + 1.0D);
        double d2 = 1.0D / ((axisalignedbb.f - axisalignedbb.c) * 2.0D + 1.0D);

        if (d0 >= 0.0D && d1 >= 0.0D && d2 >= 0.0D) {
            int i0 = 0;
            int i1 = 0;

            for (float f0 = 0.0F; f0 <= 1.0F; f0 = (float) ((double) f0 + d0)) {
                for (float f1 = 0.0F; f1 <= 1.0F; f1 = (float) ((double) f1 + d1)) {
                    for (float f2 = 0.0F; f2 <= 1.0F; f2 = (float) ((double) f2 + d2)) {
                        double d3 = axisalignedbb.a + (axisalignedbb.d - axisalignedbb.a) * (double) f0;
                        double d4 = axisalignedbb.b + (axisalignedbb.e - axisalignedbb.b) * (double) f1;
                        double d5 = axisalignedbb.c + (axisalignedbb.f - axisalignedbb.c) * (double) f2;

                        if (this.a(new Vec3(d3, d4, d5), vec3) == null) {
                            ++i0;
                        }

                        ++i1;
                    }
                }
            }

            return (float) i0 / (float) i1;
        }
        else {
            return 0.0F;
        }
    }

    public boolean a(EntityPlayer entityplayer, BlockPos blockpos, EnumFacing enumfacing) {
        blockpos = blockpos.a(enumfacing);
        if (this.p(blockpos).c() == Blocks.ab) {
            this.a(entityplayer, 1004, blockpos, 0);
            this.g(blockpos);
            return true;
        }
        else {
            return false;
        }
    }

    public TileEntity s(BlockPos blockpos) {
        if (!this.a(blockpos)) {
            return null;
        }
        else {
            TileEntity tileentity = null;
            int i0;
            TileEntity tileentity1;

            if (this.L) {
                for (i0 = 0; i0 < this.a.size(); ++i0) {
                    tileentity1 = (TileEntity) this.a.get(i0);
                    if (!tileentity1.x() && tileentity1.v().equals(blockpos)) {
                        tileentity = tileentity1;
                        break;
                    }
                }
            }

            if (tileentity == null) {
                tileentity = this.f(blockpos).a(blockpos, Chunk.EnumCreateEntityType.IMMEDIATE);
            }

            if (tileentity == null) {
                for (i0 = 0; i0 < this.a.size(); ++i0) {
                    tileentity1 = (TileEntity) this.a.get(i0);
                    if (!tileentity1.x() && tileentity1.v().equals(blockpos)) {
                        tileentity = tileentity1;
                        break;
                    }
                }
            }

            return tileentity;
        }
    }

    public void a(BlockPos blockpos, TileEntity tileentity) {
        if (tileentity != null && !tileentity.x()) {
            if (this.L) {
                tileentity.a(blockpos);
                Iterator iterator = this.a.iterator();

                while (iterator.hasNext()) {
                    TileEntity tileentity1 = (TileEntity) iterator.next();

                    if (tileentity1.v().equals(blockpos)) {
                        tileentity1.y();
                        iterator.remove();
                    }
                }

                this.a.add(tileentity);
            }
            else {
                this.a(tileentity);
                this.f(blockpos).a(blockpos, tileentity);
            }
        }

    }

    public void t(BlockPos blockpos) {
        TileEntity tileentity = this.s(blockpos);

        if (tileentity != null && this.L) {
            tileentity.y();
            this.a.remove(tileentity);
        }
        else {
            if (tileentity != null) {
                this.a.remove(tileentity);
                this.h.remove(tileentity);
                this.i.remove(tileentity);
            }

            this.f(blockpos).e(blockpos);
        }

    }

    public void b(TileEntity tileentity) {
        this.b.add(tileentity);
    }

    public boolean u(BlockPos blockpos) {
        IBlockState iblockstate = this.p(blockpos);
        AxisAlignedBB axisalignedbb = iblockstate.c().a(this, blockpos, iblockstate);

        return axisalignedbb != null && axisalignedbb.a() >= 1.0D;
    }

    public static boolean a(IBlockAccess iblockaccess, BlockPos blockpos) {
        IBlockState iblockstate = iblockaccess.p(blockpos);
        Block block = iblockstate.c();

        return block.r().k() && block.d() ? true : (block instanceof BlockStairs ? iblockstate.b(BlockStairs.b) == BlockStairs.EnumHalf.TOP : (block instanceof BlockSlab ? iblockstate.b(BlockSlab.a) == BlockSlab.EnumBlockHalf.TOP : (block instanceof BlockHopper ? true : (block instanceof BlockSnow ? ((Integer) iblockstate.b(BlockSnow.a)).intValue() == 7 : false))));
    }

    public boolean d(BlockPos blockpos, boolean flag0) {
        if (!this.a(blockpos)) {
            return flag0;
        }
        else {
            Chunk chunk = this.v.a(blockpos);

            if (chunk.f()) {
                return flag0;
            }
            else {
                Block block = this.p(blockpos).c();

                return block.r().k() && block.d();
            }
        }
    }

    public void B() {
        int i0 = this.a(1.0F);

        if (i0 != this.d) {
            this.d = i0;
        }

    }

    public void a(boolean flag0, boolean flag1) {
        this.F = flag0;
        this.G = flag1;
    }

    public void c() {
        this.p();
    }

    protected void C() {
        if (this.x.p()) {
            this.p = 1.0F;
            if (this.x.n()) {
                this.r = 1.0F;
            }
        }

    }

    protected void p() {
        if (!this.t.o()) {
            if (!this.D) {
                int i0 = this.x.A();

                if (i0 > 0) {
                    --i0;
                    this.x.i(i0);
                    this.x.f(this.x.n() ? 1 : 2);
                    this.x.g(this.x.p() ? 1 : 2);
                }

                int i1 = this.x.o();

                if (i1 <= 0) {
                    if (this.x.n()) {
                        this.x.f(this.s.nextInt(12000) + 3600);
                    }
                    else {
                        this.x.f(this.s.nextInt(168000) + 12000);
                    }
                }
                else {
                    --i1;
                    this.x.f(i1);
                    if (i1 <= 0) {
                        // CanaryMod: WeatherChange (Thunder)
                        WeatherChangeHook hook = (WeatherChangeHook) new WeatherChangeHook(canaryDimension, !this.x.n(), true).call();
                        if (!hook.isCanceled()) {
                            this.x.a(!this.x.n());
                        }
                        //
                    }
                }

                this.q = this.r;
                if (this.x.n()) {
                    this.r = (float) ((double) this.r + 0.01D);
                }
                else {
                    this.r = (float) ((double) this.r - 0.01D);
                }

                this.r = MathHelper.a(this.r, 0.0F, 1.0F);
                int i2 = this.x.q();

                if (i2 <= 0) {
                    if (this.x.p()) {
                        this.x.g(this.s.nextInt(12000) + 12000);
                    }
                    else {
                        this.x.g(this.s.nextInt(168000) + 12000);
                    }
                }
                else {
                    --i2;
                    this.x.g(i2);
                    if (i2 <= 0) {
                        // CanaryMod: WeatherChange (Rain)
                        WeatherChangeHook hook = (WeatherChangeHook) new WeatherChangeHook(canaryDimension, !this.x.p(), false).call();
                        if (!hook.isCanceled()) {
                            this.x.b(!this.x.p());
                        }
                        //
                    }
                }

                this.o = this.p;
                if (this.x.p()) {
                    this.p = (float) ((double) this.p + 0.01D);
                }
                else {
                    this.p = (float) ((double) this.p - 0.01D);
                }

                this.p = MathHelper.a(this.p, 0.0F, 1.0F);
            }
        }
    }

    protected void D() {
        this.E.clear();
        this.B.a("buildList");

        int i0;
        EntityPlayer entityplayer;
        int i1;
        int i2;
        int i3;

        for (i0 = 0; i0 < this.j.size(); ++i0) {
            entityplayer = (EntityPlayer) this.j.get(i0);
            i1 = MathHelper.c(entityplayer.s / 16.0D);
            i2 = MathHelper.c(entityplayer.u / 16.0D);
            i3 = this.q();

            for (int i4 = -i3; i4 <= i3; ++i4) {
                for (int i5 = -i3; i5 <= i3; ++i5) {
                    this.E.add(new ChunkCoordIntPair(i4 + i1, i5 + i2));
                }
            }
        }

        this.B.b();
        if (this.K > 0) {
            --this.K;
        }

        this.B.a("playerCheckLight");
        if (!this.j.isEmpty()) {
            i0 = this.s.nextInt(this.j.size());
            entityplayer = (EntityPlayer) this.j.get(i0);
            i1 = MathHelper.c(entityplayer.s) + this.s.nextInt(11) - 5;
            i2 = MathHelper.c(entityplayer.t) + this.s.nextInt(11) - 5;
            i3 = MathHelper.c(entityplayer.u) + this.s.nextInt(11) - 5;
            this.x(new BlockPos(i1, i2, i3));
        }

        this.B.b();
    }

    protected abstract int q();

    protected void a(int i0, int i1, Chunk chunk) {
        this.B.c("moodSound");
        if (this.K == 0 && !this.D) {
            this.m = this.m * 3 + 1013904223;
            int i2 = this.m >> 2;
            int i3 = i2 & 15;
            int i4 = i2 >> 8 & 15;
            int i5 = i2 >> 16 & 255;
            BlockPos blockpos = new BlockPos(i3, i5, i4);
            Block block = chunk.a(blockpos);

            i3 += i0;
            i4 += i1;
            if (block.r() == Material.a && this.k(blockpos) <= this.s.nextInt(8) && this.b(EnumSkyBlock.SKY, blockpos) <= 0) {
                EntityPlayer entityplayer = this.a((double) i3 + 0.5D, (double) i5 + 0.5D, (double) i4 + 0.5D, 8.0D);

                if (entityplayer != null && entityplayer.e((double) i3 + 0.5D, (double) i5 + 0.5D, (double) i4 + 0.5D) > 4.0D) {
                    this.a((double) i3 + 0.5D, (double) i5 + 0.5D, (double) i4 + 0.5D, "ambient.cave.cave", 0.7F, 0.8F + this.s.nextFloat() * 0.2F);
                    this.K = this.s.nextInt(12000) + 6000;
                }
            }
        }

        this.B.c("checkLight");
        chunk.m();
    }

    protected void h() {
        this.D();
    }

    public void a(Block block, BlockPos blockpos, Random random) {
        this.e = true;
        block.b(this, blockpos, this.p(blockpos), random);
        this.e = false;
    }

    public boolean v(BlockPos blockpos) {
        return this.e(blockpos, false);
    }

    public boolean w(BlockPos blockpos) {
        return this.e(blockpos, true);
    }

    public boolean e(BlockPos blockpos, boolean flag0) {
        BiomeGenBase biomegenbase = this.b(blockpos);
        float f0 = biomegenbase.a(blockpos);

        if (f0 > 0.15F) {
            return false;
        }
        else {
            if (blockpos.o() >= 0 && blockpos.o() < 256 && this.b(EnumSkyBlock.BLOCK, blockpos) < 10) {
                IBlockState iblockstate = this.p(blockpos);
                Block block = iblockstate.c();

                if ((block == Blocks.j || block == Blocks.i) && ((Integer) iblockstate.b(BlockLiquid.b)).intValue() == 0) {
                    if (!flag0) {
                        return true;
                    }

                    boolean flag1 = this.F(blockpos.e()) && this.F(blockpos.f()) && this.F(blockpos.c()) && this.F(blockpos.d());

                    if (!flag1) {
                        return true;
                    }
                }
            }

            return false;
        }
    }

    private boolean F(BlockPos blockpos) {
        return this.p(blockpos).c().r() == Material.h;
    }

    public boolean f(BlockPos blockpos, boolean flag0) {
        BiomeGenBase biomegenbase = this.b(blockpos);
        float f0 = biomegenbase.a(blockpos);

        if (f0 > 0.15F) {
            return false;
        }
        else if (!flag0) {
            return true;
        }
        else {
            if (blockpos.o() >= 0 && blockpos.o() < 256 && this.b(EnumSkyBlock.BLOCK, blockpos) < 10) {
                Block block = this.p(blockpos).c();

                if (block.r() == Material.a && Blocks.aH.c(this, blockpos)) {
                    return true;
                }
            }

            return false;
        }
    }

    public boolean x(BlockPos blockpos) {
        boolean flag0 = false;

        if (!this.t.o()) {
            flag0 |= this.c(EnumSkyBlock.SKY, blockpos);
        }

        flag0 |= this.c(EnumSkyBlock.BLOCK, blockpos);
        return flag0;
    }

    private int a(BlockPos blockpos, EnumSkyBlock enumskyblock) {
        if (enumskyblock == EnumSkyBlock.SKY && this.i(blockpos)) {
            return 15;
        }
        else {
            Block block = this.p(blockpos).c();
            int i0 = enumskyblock == EnumSkyBlock.SKY ? 0 : block.p();
            int i1 = block.n();

            if (i1 >= 15 && block.p() > 0) {
                i1 = 1;
            }

            if (i1 < 1) {
                i1 = 1;
            }

            if (i1 >= 15) {
                return 0;
            }
            else if (i0 >= 14) {
                return i0;
            }
            else {
                EnumFacing[] aenumfacing = EnumFacing.values();
                int i2 = aenumfacing.length;

                for (int i3 = 0; i3 < i2; ++i3) {
                    EnumFacing enumfacing = aenumfacing[i3];
                    BlockPos blockpos1 = blockpos.a(enumfacing);
                    int i4 = this.b(enumskyblock, blockpos1) - i1;

                    if (i4 > i0) {
                        i0 = i4;
                    }

                    if (i0 >= 14) {
                        return i0;
                    }
                }

                return i0;
            }
        }
    }

    public boolean c(EnumSkyBlock enumskyblock, BlockPos blockpos) {
        if (!this.a(blockpos, 17, false)) {
            return false;
        }
        else {
            int i0 = 0;
            int i1 = 0;

            this.B.a("getBrightness");
            int i2 = this.b(enumskyblock, blockpos);
            int i3 = this.a(blockpos, enumskyblock);
            int i4 = blockpos.n();
            int i5 = blockpos.o();
            int i6 = blockpos.p();
            int i7;
            int i8;
            int i9;
            int i10;
            int i11;
            int i12;
            int i13;
            int i14;

            if (i3 > i2) {
                this.H[i1++] = 133152;
            }
            else if (i3 < i2) {
                this.H[i1++] = 133152 | i2 << 18;

                while (i0 < i1) {
                    i7 = this.H[i0++];
                    i8 = (i7 & 63) - 32 + i4;
                    i9 = (i7 >> 6 & 63) - 32 + i5;
                    i10 = (i7 >> 12 & 63) - 32 + i6;
                    int i15 = i7 >> 18 & 15;
                    BlockPos blockpos1 = new BlockPos(i8, i9, i10);

                    i11 = this.b(enumskyblock, blockpos1);
                    if (i11 == i15) {
                        this.a(enumskyblock, blockpos1, 0);
                        if (i15 > 0) {
                            i12 = MathHelper.a(i8 - i4);
                            i13 = MathHelper.a(i9 - i5);
                            i14 = MathHelper.a(i10 - i6);
                            if (i12 + i13 + i14 < 17) {
                                EnumFacing[] aenumfacing = EnumFacing.values();
                                int i16 = aenumfacing.length;

                                for (int i17 = 0; i17 < i16; ++i17) {
                                    EnumFacing enumfacing = aenumfacing[i17];
                                    int i18 = i8 + enumfacing.g();
                                    int i19 = i9 + enumfacing.h();
                                    int i20 = i10 + enumfacing.i();
                                    BlockPos blockpos2 = new BlockPos(i18, i19, i20);
                                    int i21 = Math.max(1, this.p(blockpos2).c().n());

                                    i11 = this.b(enumskyblock, blockpos2);
                                    if (i11 == i15 - i21 && i1 < this.H.length) {
                                        this.H[i1++] = i18 - i4 + 32 | i19 - i5 + 32 << 6 | i20 - i6 + 32 << 12 | i15 - i21 << 18;
                                    }
                                }
                            }
                        }
                    }
                }

                i0 = 0;
            }

            this.B.b();
            this.B.a("checkedPosition < toCheckCount");

            while (i0 < i1) {
                i7 = this.H[i0++];
                i8 = (i7 & 63) - 32 + i4;
                i9 = (i7 >> 6 & 63) - 32 + i5;
                i10 = (i7 >> 12 & 63) - 32 + i6;
                BlockPos blockpos3 = new BlockPos(i8, i9, i10);
                int i22 = this.b(enumskyblock, blockpos3);

                i11 = this.a(blockpos3, enumskyblock);
                if (i11 != i22) {
                    this.a(enumskyblock, blockpos3, i11);
                    if (i11 > i22) {
                        i12 = Math.abs(i8 - i4);
                        i13 = Math.abs(i9 - i5);
                        i14 = Math.abs(i10 - i6);
                        boolean flag0 = i1 < this.H.length - 6;

                        if (i12 + i13 + i14 < 17 && flag0) {
                            if (this.b(enumskyblock, blockpos3.e()) < i11) {
                                this.H[i1++] = i8 - 1 - i4 + 32 + (i9 - i5 + 32 << 6) + (i10 - i6 + 32 << 12);
                            }

                            if (this.b(enumskyblock, blockpos3.f()) < i11) {
                                this.H[i1++] = i8 + 1 - i4 + 32 + (i9 - i5 + 32 << 6) + (i10 - i6 + 32 << 12);
                            }

                            if (this.b(enumskyblock, blockpos3.b()) < i11) {
                                this.H[i1++] = i8 - i4 + 32 + (i9 - 1 - i5 + 32 << 6) + (i10 - i6 + 32 << 12);
                            }

                            if (this.b(enumskyblock, blockpos3.a()) < i11) {
                                this.H[i1++] = i8 - i4 + 32 + (i9 + 1 - i5 + 32 << 6) + (i10 - i6 + 32 << 12);
                            }

                            if (this.b(enumskyblock, blockpos3.c()) < i11) {
                                this.H[i1++] = i8 - i4 + 32 + (i9 - i5 + 32 << 6) + (i10 - 1 - i6 + 32 << 12);
                            }

                            if (this.b(enumskyblock, blockpos3.d()) < i11) {
                                this.H[i1++] = i8 - i4 + 32 + (i9 - i5 + 32 << 6) + (i10 + 1 - i6 + 32 << 12);
                            }
                        }
                    }
                }
            }

            this.B.b();
            return true;
        }
    }

    public boolean a(boolean flag0) {
        return false;
    }

    public List a(Chunk chunk, boolean flag0) {
        return null;
    }

    public List a(StructureBoundingBox structureboundingbox, boolean flag0) {
        return null;
    }

    public List b(Entity entity, AxisAlignedBB axisalignedbb) {
        return this.a(entity, axisalignedbb, IEntitySelector.d);
    }

    public List a(Entity entity, AxisAlignedBB axisalignedbb, Predicate predicate) {
        ArrayList arraylist = Lists.newArrayList();
        int i0 = MathHelper.c((axisalignedbb.a - 2.0D) / 16.0D);
        int i1 = MathHelper.c((axisalignedbb.d + 2.0D) / 16.0D);
        int i2 = MathHelper.c((axisalignedbb.c - 2.0D) / 16.0D);
        int i3 = MathHelper.c((axisalignedbb.f + 2.0D) / 16.0D);

        for (int i4 = i0; i4 <= i1; ++i4) {
            for (int i5 = i2; i5 <= i3; ++i5) {
                if (this.a(i4, i5, true)) {
                    this.a(i4, i5).a(entity, axisalignedbb, arraylist, predicate);
                }
            }
        }

        return arraylist;
    }

    public List a(Class oclass0, Predicate predicate) {
        ArrayList arraylist = Lists.newArrayList();
        Iterator iterator = this.f.iterator();

        while (iterator.hasNext()) {
            Entity entity = (Entity) iterator.next();

            if (oclass0.isAssignableFrom(entity.getClass()) && predicate.apply(entity)) {
                arraylist.add(entity);
            }
        }

        return arraylist;
    }

    public List b(Class oclass0, Predicate predicate) {
        ArrayList arraylist = Lists.newArrayList();
        Iterator iterator = this.j.iterator();

        while (iterator.hasNext()) {
            Entity entity = (Entity) iterator.next();

            if (oclass0.isAssignableFrom(entity.getClass()) && predicate.apply(entity)) {
                arraylist.add(entity);
            }
        }

        return arraylist;
    }

    public List a(Class oclass0, AxisAlignedBB axisalignedbb) {
        return this.a(oclass0, axisalignedbb, IEntitySelector.d);
    }

    public List a(Class oclass0, AxisAlignedBB axisalignedbb, Predicate predicate) {
        int i0 = MathHelper.c((axisalignedbb.a - 2.0D) / 16.0D);
        int i1 = MathHelper.c((axisalignedbb.d + 2.0D) / 16.0D);
        int i2 = MathHelper.c((axisalignedbb.c - 2.0D) / 16.0D);
        int i3 = MathHelper.c((axisalignedbb.f + 2.0D) / 16.0D);
        ArrayList arraylist = Lists.newArrayList();

        for (int i4 = i0; i4 <= i1; ++i4) {
            for (int i5 = i2; i5 <= i3; ++i5) {
                if (this.a(i4, i5, true)) {
                    this.a(i4, i5).a(oclass0, axisalignedbb, arraylist, predicate);
                }
            }
        }

        return arraylist;
    }

    public Entity a(Class oclass0, AxisAlignedBB axisalignedbb, Entity entity) {
        List list = this.a(oclass0, axisalignedbb);
        Entity entity1 = null;
        double d0 = Double.MAX_VALUE;

        for (int i0 = 0; i0 < list.size(); ++i0) {
            Entity entity2 = (Entity) list.get(i0);

            if (entity2 != entity && IEntitySelector.d.apply(entity2)) {
                double d1 = entity.h(entity2);

                if (d1 <= d0) {
                    entity1 = entity2;
                    d0 = d1;
                }
            }
        }

        return entity1;
    }

    public Entity a(int i0) {
        return (Entity) this.l.a(i0);
    }

    public void b(BlockPos blockpos, TileEntity tileentity) {
        if (this.e(blockpos)) {
            this.f(blockpos).e();
        }

    }

    public int a(Class oclass0) {
        int i0 = 0;
        Iterator iterator = this.f.iterator();

        while (iterator.hasNext()) {
            Entity entity = (Entity) iterator.next();

            if ((!(entity instanceof EntityLiving) || !((EntityLiving) entity).bY()) && oclass0.isAssignableFrom(entity.getClass())) {
                ++i0;
            }
        }

        return i0;
    }

    public void b(Collection collection) {
        this.f.addAll(collection);
        Iterator iterator = collection.iterator();

        while (iterator.hasNext()) {
            Entity entity = (Entity) iterator.next();

            this.a(entity);
        }

    }

    public void c(Collection collection) {
        this.g.addAll(collection);
    }

    public boolean a(Block block, BlockPos blockpos, boolean flag0, EnumFacing enumfacing, Entity entity, ItemStack itemstack) {
        Block block1 = this.p(blockpos).c();
        AxisAlignedBB axisalignedbb = flag0 ? null : block.a(this, blockpos, block.P());

        return axisalignedbb != null && !this.a(axisalignedbb, entity) ? false : (block1.r() == Material.q && block == Blocks.cf ? true : block1.r().j() && block.a(this, blockpos, enumfacing, itemstack));
    }

    public int a(BlockPos blockpos, EnumFacing enumfacing) {
        IBlockState iblockstate = this.p(blockpos);

        return iblockstate.c().b((IBlockAccess) this, blockpos, iblockstate, enumfacing);
    }

    public WorldType G() {
        return this.x.u();
    }

    public int y(BlockPos blockpos) {
        byte b0 = 0;
        int i0 = Math.max(b0, this.a(blockpos.b(), EnumFacing.DOWN));

        if (i0 >= 15) {
            return i0;
        }
        else {
            i0 = Math.max(i0, this.a(blockpos.a(), EnumFacing.UP));
            if (i0 >= 15) {
                return i0;
            }
            else {
                i0 = Math.max(i0, this.a(blockpos.c(), EnumFacing.NORTH));
                if (i0 >= 15) {
                    return i0;
                }
                else {
                    i0 = Math.max(i0, this.a(blockpos.d(), EnumFacing.SOUTH));
                    if (i0 >= 15) {
                        return i0;
                    }
                    else {
                        i0 = Math.max(i0, this.a(blockpos.e(), EnumFacing.WEST));
                        if (i0 >= 15) {
                            return i0;
                        }
                        else {
                            i0 = Math.max(i0, this.a(blockpos.f(), EnumFacing.EAST));
                            return i0 >= 15 ? i0 : i0;
                        }
                    }
                }
            }
        }
    }

    public boolean b(BlockPos blockpos, EnumFacing enumfacing) {
        return this.c(blockpos, enumfacing) > 0;
    }

    public int c(BlockPos blockpos, EnumFacing enumfacing) {
        IBlockState iblockstate = this.p(blockpos);
        Block block = iblockstate.c();

        return block.t() ? this.y(blockpos) : block.a((IBlockAccess) this, blockpos, iblockstate, enumfacing);
    }

    public boolean z(BlockPos blockpos) {
        return this.c(blockpos.b(), EnumFacing.DOWN) > 0 ? true : (this.c(blockpos.a(), EnumFacing.UP) > 0 ? true : (this.c(blockpos.c(), EnumFacing.NORTH) > 0 ? true : (this.c(blockpos.d(), EnumFacing.SOUTH) > 0 ? true : (this.c(blockpos.e(), EnumFacing.WEST) > 0 ? true : this.c(blockpos.f(), EnumFacing.EAST) > 0))));
    }

    public int A(BlockPos blockpos) {
        int i0 = 0;
        EnumFacing[] aenumfacing = EnumFacing.values();
        int i1 = aenumfacing.length;

        for (int i2 = 0; i2 < i1; ++i2) {
            EnumFacing enumfacing = aenumfacing[i2];
            int i3 = this.c(blockpos.a(enumfacing), enumfacing);

            if (i3 >= 15) {
                return 15;
            }

            if (i3 > i0) {
                i0 = i3;
            }
        }

        return i0;
    }

    public EntityPlayer a(Entity entity, double d0) {
        return this.a(entity.s, entity.t, entity.u, d0);
    }

    public EntityPlayer a(double d0, double d1, double d2, double d3) {
        double d4 = -1.0D;
        EntityPlayer entityplayer = null;

        for (int i0 = 0; i0 < this.j.size(); ++i0) {
            EntityPlayer entityplayer1 = (EntityPlayer) this.j.get(i0);

            if (IEntitySelector.d.apply(entityplayer1)) {
                double d5 = entityplayer1.e(d0, d1, d2);

                if ((d3 < 0.0D || d5 < d3 * d3) && (d4 == -1.0D || d5 < d4)) {
                    d4 = d5;
                    entityplayer = entityplayer1;
                }
            }
        }

        return entityplayer;
    }

    public boolean b(double d0, double d1, double d2, double d3) {
        for (int i0 = 0; i0 < this.j.size(); ++i0) {
            EntityPlayer entityplayer = (EntityPlayer) this.j.get(i0);

            if (IEntitySelector.d.apply(entityplayer)) {
                double d4 = entityplayer.e(d0, d1, d2);

                if (d3 < 0.0D || d4 < d3 * d3) {
                    return true;
                }
            }
        }

        return false;
    }

    public EntityPlayer a(String s0) {
        for (int i0 = 0; i0 < this.j.size(); ++i0) {
            EntityPlayer entityplayer = (EntityPlayer) this.j.get(i0);

            if (s0.equals(entityplayer.d_())) {
                return entityplayer;
            }
        }

        return null;
    }

    public EntityPlayer b(UUID uuid) {
        for (int i0 = 0; i0 < this.j.size(); ++i0) {
            EntityPlayer entityplayer = (EntityPlayer) this.j.get(i0);

            if (uuid.equals(entityplayer.aJ())) {
                return entityplayer;
            }
        }

        return null;
    }

    public void I() throws MinecraftException {
        this.w.c();
    }

    public long J() {
        return this.x.b();
    }

    public long K() {
        return this.x.f();
    }

    public long L() {
        return this.x.g();
    }

    public void b(long i0) {
        this.x.c(i0);
    }

    public BlockPos M() {
        BlockPos blockpos = new BlockPos(this.x.c(), this.x.d(), this.x.e());

        if (!this.af().a(blockpos)) {
            blockpos = this.m(new BlockPos(this.af().f(), 0.0D, this.af().g()));
        }

        return blockpos;
    }

    public void B(BlockPos blockpos) {
        this.x.a(blockpos);
    }

    public boolean a(EntityPlayer entityplayer, BlockPos blockpos) {
        return true;
    }

    public void a(Entity entity, byte b0) {
    }

    public IChunkProvider N() {
        return this.v;
    }

    public void c(BlockPos blockpos, Block block, int i0, int i1) {
        block.a(this, blockpos, this.p(blockpos), i0, i1);
    }

    public ISaveHandler O() {
        return this.w;
    }

    public WorldInfo P() {
        return this.x;
    }

    public GameRules Q() {
        return this.x.x();
    }

    public void d() {
    }

    public float h(float f0) {
        return (this.q + (this.r - this.q) * f0) * this.j(f0);
    }

    public float j(float f0) {
        return this.o + (this.p - this.o) * f0;
    }

    public boolean R() {
        return (double) this.h(1.0F) > 0.9D;
    }

    public boolean S() {
        return (double) this.j(1.0F) > 0.2D;
    }

    public boolean C(BlockPos blockpos) {
        if (!this.S()) {
            return false;
        }
        else if (!this.i(blockpos)) {
            return false;
        }
        else if (this.q(blockpos).o() > blockpos.o()) {
            return false;
        }
        else {
            BiomeGenBase biomegenbase = this.b(blockpos);

            return biomegenbase.d() ? false : (this.f(blockpos, false) ? false : biomegenbase.e());
        }
    }

    public boolean D(BlockPos blockpos) {
        BiomeGenBase biomegenbase = this.b(blockpos);

        return biomegenbase.f();
    }

    public MapStorage T() {
        return this.z;
    }

    public void a(String s0, WorldSavedData worldsaveddata) {
        this.z.a(s0, worldsaveddata);
    }

    public WorldSavedData a(Class oclass0, String s0) {
        return this.z.a(oclass0, s0);
    }

    public int b(String s0) {
        return this.z.a(s0);
    }

    public void a(int i0, BlockPos blockpos, int i1) {
        for (int i2 = 0; i2 < this.u.size(); ++i2) {
            ((IWorldAccess) this.u.get(i2)).a(i0, blockpos, i1);
        }

    }

    public void b(int i0, BlockPos blockpos, int i1) {
        this.a((EntityPlayer) null, i0, blockpos, i1);
    }

    public void a(EntityPlayer entityplayer, int i0, BlockPos blockpos, int i1) {
        try {
            for (int i2 = 0; i2 < this.u.size(); ++i2) {
                ((IWorldAccess) this.u.get(i2)).a(entityplayer, i0, blockpos, i1);
            }

        }
        catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.a(throwable, "Playing level event");
            CrashReportCategory crashreportcategory = crashreport.a("Level event being played");

            crashreportcategory.a("Block coordinates", (Object) CrashReportCategory.a(blockpos));
            crashreportcategory.a("Event source", (Object) entityplayer);
            crashreportcategory.a("Event type", (Object) Integer.valueOf(i0));
            crashreportcategory.a("Event data", (Object) Integer.valueOf(i1));
            throw new ReportedException(crashreport);
        }
    }

    public int U() {
        return 256;
    }

    public int V() {
        return this.t.o() ? 128 : 256;
    }

    public Random a(int i0, int i1, int i2) {
        long i3 = (long) i0 * 341873128712L + (long) i1 * 132897987541L + this.P().b() + (long) i2;

        this.s.setSeed(i3);
        return this.s;
    }

    public BlockPos a(String s0, BlockPos blockpos) {
        return this.N().a(this, s0, blockpos);
    }

    public CrashReportCategory a(CrashReport crashreport) {
        CrashReportCategory crashreportcategory = crashreport.a("Affected level", 1);

        crashreportcategory.a("Level name", (Object) (this.x == null ? "????" : this.x.k()));
        crashreportcategory.a("All players", new Callable() {

            public String call() {
                return World.this.j.size() + " total; " + World.this.j.toString();
            }
        });
        crashreportcategory.a("Chunk stats", new Callable() {

            public String call() {
                return World.this.v.f();
            }
        });

        try {
            this.x.a(crashreportcategory);
        }
        catch (Throwable throwable) {
            crashreportcategory.a("Level Data Unobtainable", throwable);
        }

        return crashreportcategory;
    }

    public void c(int i0, BlockPos blockpos, int i1) {
        for (int i2 = 0; i2 < this.u.size(); ++i2) {
            IWorldAccess iworldaccess = (IWorldAccess) this.u.get(i2);

            iworldaccess.b(i0, blockpos, i1);
        }

    }

    public Calendar Y() {
        if (this.K() % 600L == 0L) {
            this.J.setTimeInMillis(MinecraftServer.ax());
        }

        return this.J;
    }

    public Scoreboard Z() {
        return this.C;
    }

    public void e(BlockPos blockpos, Block block) {
        Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();

        while (iterator.hasNext()) {
            EnumFacing enumfacing = (EnumFacing) iterator.next();
            BlockPos blockpos1 = blockpos.a(enumfacing);

            if (this.e(blockpos1)) {
                IBlockState iblockstate = this.p(blockpos1);

                if (Blocks.cj.e(iblockstate.c())) {
                    iblockstate.c().a(this, blockpos1, iblockstate, block);
                }
                else if (iblockstate.c().t()) {
                    blockpos1 = blockpos1.a(enumfacing);
                    iblockstate = this.p(blockpos1);
                    if (Blocks.cj.e(iblockstate.c())) {
                        iblockstate.c().a(this, blockpos1, iblockstate, block);
                    }
                }
            }
        }

    }

    public DifficultyInstance E(BlockPos blockpos) {
        long i0 = 0L;
        float f0 = 0.0F;

        if (this.e(blockpos)) {
            f0 = this.y();
            i0 = this.f(blockpos).w();
        }

        return new DifficultyInstance(this.aa(), this.L(), i0, f0);
    }

    public EnumDifficulty aa() {
        return this.P().y();
    }

    public int ab() {
        return this.d;
    }

    public void b(int i0) {
        this.d = i0;
    }

    public void c(int i0) {
        this.I = i0;
    }

    public boolean ad() {
        return this.y;
    }

    public VillageCollection ae() {
        return this.A;
    }

    public WorldBorder af() {
        return this.M;
    }

    public boolean c(int i0, int i1) {
        BlockPos blockpos = this.M();
        int i2 = i0 * 16 + 8 - blockpos.n();
        int i3 = i1 * 16 + 8 - blockpos.p();
        short short1 = 128;

        return i2 >= -short1 && i2 <= short1 && i3 >= -short1 && i3 <= short1;
    }

    /**
     * Get the canary dimension wrapper
     *
     * @return
     */
    public CanaryWorld getCanaryWorld() {
        return canaryDimension;
    }

    /**
     * Set the canary dimension wrapper
     *
     * @param dim
     */
    public void setCanaryWorld(CanaryWorld dim) {
        this.canaryDimension = dim;
    }

    /**
     * Checks if the Entity is allowed to spawn based on the world configuration
     *
     * @param entity
     *         the entity to check
     *
     * @return true if can spawn; false if not
     */
    protected final boolean canSpawn(Entity entity) {
        WorldConfiguration world_cfg = Configuration.getWorldConfig(this.canaryDimension.getFqName());
        String fqEntityName = entity.getCanaryEntity().getFqName();
        if (entity instanceof EntityAnimal || entity instanceof EntityWaterMob || entity instanceof EntityAmbientCreature) {
            if (!world_cfg.canSpawnAnimals()) {
                return false;
            }
            else if (entity instanceof EntityWaterMob) {
                if (!world_cfg.getSpawnableWaterAnimals().contains(fqEntityName)) {
                    return false;
                }
            }
            else if (!world_cfg.getSpawnableAnimals().contains(fqEntityName)) {
                return false;
            }
        }
        else if (entity instanceof IMob) {
            if (!world_cfg.canSpawnMonsters()) {
                return false;
            }
            else if (!world_cfg.getSpawnableMobs().contains(fqEntityName)) {
                return false;
            }
        }
        else if (entity instanceof INpc && !world_cfg.canSpawnVillagers()) {
            return false;
        }
        else if (entity instanceof EntityGolem) {
            if (!world_cfg.canSpawnGolems()) {
                return false;
            }
            else if (!world_cfg.getSpawnableGolems().contains(fqEntityName)) {
                return false;
            }
        }
        return true;
    }
}
