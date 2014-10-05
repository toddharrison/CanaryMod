package net.minecraft.block;

import net.canarymod.api.world.position.BlockPosition;
import net.canarymod.hook.world.BlockDropXpHook;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;
import java.util.Random;


public class Block {

    private static final ResourceLocation a = new ResourceLocation("air");
    public static final RegistryNamespacedDefaultedByKey c = new RegistryNamespacedDefaultedByKey(a);
    public static final ObjectIntIdentityMap d = new ObjectIntIdentityMap();
    private CreativeTabs b;
    public static final Block.SoundType e = new Block.SoundType("stone", 1.0F, 1.0F);
    public static final Block.SoundType f = new Block.SoundType("wood", 1.0F, 1.0F);
    public static final Block.SoundType g = new Block.SoundType("gravel", 1.0F, 1.0F);
    public static final Block.SoundType h = new Block.SoundType("grass", 1.0F, 1.0F);
    public static final Block.SoundType i = new Block.SoundType("stone", 1.0F, 1.0F);
    public static final Block.SoundType j = new Block.SoundType("stone", 1.0F, 1.5F);
    public static final Block.SoundType k = new Block.SoundType("stone", 1.0F, 1.0F) {

        public String a() {
            return "dig.glass";
        }

        public String b() {
            return "step.stone";
        }
    };
    public static final Block.SoundType l = new Block.SoundType("cloth", 1.0F, 1.0F);
    public static final Block.SoundType m = new Block.SoundType("sand", 1.0F, 1.0F);
    public static final Block.SoundType n = new Block.SoundType("snow", 1.0F, 1.0F);
    public static final Block.SoundType o = new Block.SoundType("ladder", 1.0F, 1.0F) {

        public String a() {
            return "dig.wood";
        }
    };
    public static final Block.SoundType p = new Block.SoundType("anvil", 0.3F, 1.0F) {

        public String a() {
            return "dig.stone";
        }

        public String b() {
            return "random.anvil_land";
        }
    };
    public static final Block.SoundType q = new Block.SoundType("slime", 1.0F, 1.0F) {

        public String a() {
            return "mob.slime.big";
        }

        public String b() {
            return "mob.slime.big";
        }

        public String c() {
            return "mob.slime.small";
        }
    };
    protected boolean r;
    protected int s;
    protected boolean t;
    protected int u;
    protected boolean v;
    protected float w;
    protected float x;
    protected boolean y = true;
    protected boolean z;
    protected boolean A;
    protected double B;
    protected double C;
    protected double D;
    protected double E;
    protected double F;
    protected double G;
    public Block.SoundType H;
    public float I;
    protected final Material J;
    public float K;
    protected final BlockState L;
    private IBlockState M;
    private String N;

    public static int a(Block block) {
        return c.b(block);
    }

    public static int f(IBlockState iblockstate) {
        return a(iblockstate.c()) + (iblockstate.c().c(iblockstate) << 12);
    }

    public static Block c(int i0) {
        return (Block) c.a(i0);
    }

    public static IBlockState d(int i0) {
        int i1 = i0 & 4095;
        int i2 = i0 >> 12 & 15;

        return c(i1).a(i2);
    }

    public static Block a(Item item) {
        return item instanceof ItemBlock ? ((ItemBlock) item).d() : null;
    }

    public static Block b(String s0) {
        ResourceLocation resourcelocation = new ResourceLocation(s0);

        if (c.d(resourcelocation)) {
            return (Block) c.a(resourcelocation);
        }
        else {
            try {
                return (Block) c.a(Integer.parseInt(s0));
            }
            catch (NumberFormatException numberformatexception) {
                return null;
            }
        }
    }

    public boolean m() {
        return this.r;
    }

    public int n() {
        return this.s;
    }

    public int p() {
        return this.u;
    }

    public boolean q() {
        return this.v;
    }

    public Material r() {
        return this.J;
    }

    public MapColor g(IBlockState iblockstate) {
        return this.r().r();
    }

    public IBlockState a(int i0) {
        return this.P();
    }

    public int c(IBlockState iblockstate) {
        if (iblockstate != null && !iblockstate.a().isEmpty()) {
            throw new IllegalArgumentException("Don\'t know how to convert " + iblockstate + " back into data...");
        }
        else {
            return 0;
        }
    }

    public IBlockState a(IBlockState iblockstate, IBlockAccess iblockaccess, BlockPos blockpos) {
        return iblockstate;
    }

    protected Block(Material material) {
        this.H = e;
        this.I = 1.0F;
        this.K = 0.6F;
        this.J = material;
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        this.r = this.c();
        this.s = this.c() ? 255 : 0;
        this.t = !material.b();
        this.L = this.e();
        this.j(this.L.b());
    }

    protected Block a(Block.SoundType block_soundtype) {
        this.H = block_soundtype;
        return this;
    }

    protected Block e(int i0) {
        this.s = i0;
        return this;
    }

    protected Block a(float f0) {
        this.u = (int) (15.0F * f0);
        return this;
    }

    protected Block b(float f0) {
        this.x = f0 * 3.0F;
        return this;
    }

    public boolean s() {
        return this.J.c() && this.d();
    }

    public boolean t() {
        return this.J.k() && this.d() && !this.g();
    }

    public boolean u() {
        return this.J.c() && this.d();
    }

    public boolean d() {
        return true;
    }

    public boolean b(IBlockAccess iblockaccess, BlockPos blockpos) {
        return !this.J.c();
    }

    public int b() {
        return 3;
    }

    public boolean f(World world, BlockPos blockpos) {
        return false;
    }

    protected Block c(float f0) {
        this.w = f0;
        if (this.x < f0 * 5.0F) {
            this.x = f0 * 5.0F;
        }

        return this;
    }

    protected Block v() {
        this.c(-1.0F);
        return this;
    }

    public float g(World world, BlockPos blockpos) {
        return this.w;
    }

    protected Block a(boolean flag0) {
        this.z = flag0;
        return this;
    }

    public boolean w() {
        return this.z;
    }

    public boolean x() {
        return this.A;
    }

    protected final void a(float f0, float f1, float f2, float f3, float f4, float f5) {
        this.B = (double) f0;
        this.C = (double) f1;
        this.D = (double) f2;
        this.E = (double) f3;
        this.F = (double) f4;
        this.G = (double) f5;
    }

    public boolean b(IBlockAccess iblockaccess, BlockPos blockpos, EnumFacing enumfacing) {
        return iblockaccess.p(blockpos).c().r().a();
    }

    public void a(World world, BlockPos blockpos, IBlockState iblockstate, AxisAlignedBB axisalignedbb, List list, Entity entity) {
        AxisAlignedBB axisalignedbb1 = this.a(world, blockpos, iblockstate);

        if (axisalignedbb1 != null && axisalignedbb.b(axisalignedbb1)) {
            list.add(axisalignedbb1);
        }

    }

    public AxisAlignedBB a(World world, BlockPos blockpos, IBlockState iblockstate) {
        return new AxisAlignedBB((double) blockpos.n() + this.B, (double) blockpos.o() + this.C, (double) blockpos.p() + this.D, (double) blockpos.n() + this.E, (double) blockpos.o() + this.F, (double) blockpos.p() + this.G);
    }

    public boolean c() {
        return true;
    }

    public boolean a(IBlockState iblockstate, boolean flag0) {
        return this.y();
    }

    public boolean y() {
        return true;
    }

    public void a(World world, BlockPos blockpos, IBlockState iblockstate, Random random) {
        this.b(world, blockpos, iblockstate, random);
    }

    public void b(World world, BlockPos blockpos, IBlockState iblockstate, Random random) {
    }

    public void d(World world, BlockPos blockpos, IBlockState iblockstate) {
    }

    public void a(World world, BlockPos blockpos, IBlockState iblockstate, Block block) {
    }

    public int a(World world) {
        return 10;
    }

    public void c(World world, BlockPos blockpos, IBlockState iblockstate) {
    }

    public void b(World world, BlockPos blockpos, IBlockState iblockstate) {
    }

    public int a(Random random) {
        return 1;
    }

    public Item a(IBlockState iblockstate, Random random, int i0) {
        return Item.a(this);
    }

    public float a(EntityPlayer entityplayer, World world, BlockPos blockpos) {
        float f0 = this.g(world, blockpos);

        return f0 < 0.0F ? 0.0F : (!entityplayer.b(this) ? entityplayer.a(this) / f0 / 100.0F : entityplayer.a(this) / f0 / 30.0F);
    }

    public final void b(World world, BlockPos blockpos, IBlockState iblockstate, int i0) {
        this.a(world, blockpos, iblockstate, 1.0F, i0);
    }

    public void a(World world, BlockPos blockpos, IBlockState iblockstate, float f0, int i0) {
        if (!world.D) {
            int i1 = this.a(i0, world.s);

            for (int i2 = 0; i2 < i1; ++i2) {
                if (world.s.nextFloat() <= f0) {
                    Item item = this.a(iblockstate, world.s, i0);

                    if (item != null) {
                        a(world, blockpos, new ItemStack(item, 1, this.a(iblockstate)));
                    }
                }
            }

        }
    }

    public static void a(World world, BlockPos blockpos, ItemStack itemstack) {
        if (!world.D && world.Q().b("doTileDrops")) {
            float f0 = 0.5F;
            double d0 = (double) (world.s.nextFloat() * f0) + (double) (1.0F - f0) * 0.5D;
            double d1 = (double) (world.s.nextFloat() * f0) + (double) (1.0F - f0) * 0.5D;
            double d2 = (double) (world.s.nextFloat() * f0) + (double) (1.0F - f0) * 0.5D;
            EntityItem entityitem = new EntityItem(world, (double) blockpos.n() + d0, (double) blockpos.o() + d1, (double) blockpos.p() + d2, itemstack);

            entityitem.p();
            world.d((Entity) entityitem);
        }
    }

    protected void b(World world, BlockPos blockpos, int i0) {
        if (!world.D) {
            // CanaryMod: BlockDropXpHook
            net.canarymod.api.world.blocks.Block block = world.getCanaryWorld().getBlockAt(new BlockPosition(blockpos));
            BlockDropXpHook hook = (BlockDropXpHook) new BlockDropXpHook(block, i0).call();
            if (hook.isCanceled()) {
                return;
            }
            i0 = hook.getXp();
            //

            while (i0 > 0) {
                int i1 = EntityXPOrb.a(i0);

                i0 -= i1;
                world.d((Entity) (new EntityXPOrb(world, (double) blockpos.n() + 0.5D, (double) blockpos.o() + 0.5D, (double) blockpos.p() + 0.5D, i1)));
            }
        }

    }

    public int a(IBlockState iblockstate) {
        return 0;
    }

    public float a(Entity entity) {
        return this.x / 5.0F;
    }

    public MovingObjectPosition a(World world, BlockPos blockpos, Vec3 vec3, Vec3 vec31) {
        this.a((IBlockAccess) world, blockpos);
        vec3 = vec3.b((double) (-blockpos.n()), (double) (-blockpos.o()), (double) (-blockpos.p()));
        vec31 = vec31.b((double) (-blockpos.n()), (double) (-blockpos.o()), (double) (-blockpos.p()));
        Vec3 vec32 = vec3.a(vec31, this.B);
        Vec3 vec33 = vec3.a(vec31, this.E);
        Vec3 vec34 = vec3.b(vec31, this.C);
        Vec3 vec35 = vec3.b(vec31, this.F);
        Vec3 vec36 = vec3.c(vec31, this.D);
        Vec3 vec37 = vec3.c(vec31, this.G);

        if (!this.a(vec32)) {
            vec32 = null;
        }

        if (!this.a(vec33)) {
            vec33 = null;
        }

        if (!this.b(vec34)) {
            vec34 = null;
        }

        if (!this.b(vec35)) {
            vec35 = null;
        }

        if (!this.c(vec36)) {
            vec36 = null;
        }

        if (!this.c(vec37)) {
            vec37 = null;
        }

        Vec3 vec38 = null;

        if (vec32 != null && (vec38 == null || vec3.g(vec32) < vec3.g(vec38))) {
            vec38 = vec32;
        }

        if (vec33 != null && (vec38 == null || vec3.g(vec33) < vec3.g(vec38))) {
            vec38 = vec33;
        }

        if (vec34 != null && (vec38 == null || vec3.g(vec34) < vec3.g(vec38))) {
            vec38 = vec34;
        }

        if (vec35 != null && (vec38 == null || vec3.g(vec35) < vec3.g(vec38))) {
            vec38 = vec35;
        }

        if (vec36 != null && (vec38 == null || vec3.g(vec36) < vec3.g(vec38))) {
            vec38 = vec36;
        }

        if (vec37 != null && (vec38 == null || vec3.g(vec37) < vec3.g(vec38))) {
            vec38 = vec37;
        }

        if (vec38 == null) {
            return null;
        }
        else {
            EnumFacing enumfacing = null;

            if (vec38 == vec32) {
                enumfacing = EnumFacing.WEST;
            }

            if (vec38 == vec33) {
                enumfacing = EnumFacing.EAST;
            }

            if (vec38 == vec34) {
                enumfacing = EnumFacing.DOWN;
            }

            if (vec38 == vec35) {
                enumfacing = EnumFacing.UP;
            }

            if (vec38 == vec36) {
                enumfacing = EnumFacing.NORTH;
            }

            if (vec38 == vec37) {
                enumfacing = EnumFacing.SOUTH;
            }

            return new MovingObjectPosition(vec38.b((double) blockpos.n(), (double) blockpos.o(), (double) blockpos.p()), enumfacing, blockpos);
        }
    }

    private boolean a(Vec3 vec3) {
        return vec3 == null ? false : vec3.b >= this.C && vec3.b <= this.F && vec3.c >= this.D && vec3.c <= this.G;
    }

    private boolean b(Vec3 vec3) {
        return vec3 == null ? false : vec3.a >= this.B && vec3.a <= this.E && vec3.c >= this.D && vec3.c <= this.G;
    }

    private boolean c(Vec3 vec3) {
        return vec3 == null ? false : vec3.a >= this.B && vec3.a <= this.E && vec3.b >= this.C && vec3.b <= this.F;
    }

    public void a(World world, BlockPos blockpos, Explosion explosion) {
    }

    public boolean a(World world, BlockPos blockpos, EnumFacing enumfacing, ItemStack itemstack) {
        return this.a(world, blockpos, enumfacing);
    }

    public boolean a(World world, BlockPos blockpos, EnumFacing enumfacing) {
        return this.c(world, blockpos);
    }

    public boolean c(World world, BlockPos blockpos) {
        return world.p(blockpos).c().J.j();
    }

    public boolean a(World world, BlockPos blockpos, IBlockState iblockstate, EntityPlayer entityplayer, EnumFacing enumfacing, float f0, float f1, float f2) {
        return false;
    }

    public void a(World world, BlockPos blockpos, Entity entity) {
    }

    public IBlockState a(World world, BlockPos blockpos, EnumFacing enumfacing, float f0, float f1, float f2, int i0, EntityLivingBase entitylivingbase) {
        return this.a(i0);
    }

    public void a(World world, BlockPos blockpos, EntityPlayer entityplayer) {
    }

    public Vec3 a(World world, BlockPos blockpos, Entity entity, Vec3 vec3) {
        return vec3;
    }

    public void a(IBlockAccess iblockaccess, BlockPos blockpos) {
    }

    public final double z() {
        return this.B;
    }

    public final double A() {
        return this.E;
    }

    public final double B() {
        return this.C;
    }

    public final double C() {
        return this.F;
    }

    public final double D() {
        return this.D;
    }

    public final double E() {
        return this.G;
    }

    public int a(IBlockAccess iblockaccess, BlockPos blockpos, IBlockState iblockstate, EnumFacing enumfacing) {
        return 0;
    }

    public boolean g() {
        return false;
    }

    public void a(World world, BlockPos blockpos, IBlockState iblockstate, Entity entity) {
    }

    public int b(IBlockAccess iblockaccess, BlockPos blockpos, IBlockState iblockstate, EnumFacing enumfacing) {
        return 0;
    }

    public void h() {
    }

    public void a(World world, EntityPlayer entityplayer, BlockPos blockpos, IBlockState iblockstate, TileEntity tileentity) {
        entityplayer.b(StatList.H[a(this)]);
        entityplayer.a(0.025F);
        if (this.G() && EnchantmentHelper.e(entityplayer)) {
            ItemStack itemstack = this.i(iblockstate);

            if (itemstack != null) {
                a(world, blockpos, itemstack);
            }
        }
        else {
            int i0 = EnchantmentHelper.f(entityplayer);

            this.b(world, blockpos, iblockstate, i0);
        }

    }

    protected boolean G() {
        return this.d() && !this.A;
    }

    protected ItemStack i(IBlockState iblockstate) {
        int i0 = 0;
        Item item = Item.a(this);

        if (item != null && item.k()) {
            i0 = this.c(iblockstate);
        }

        return new ItemStack(item, 1, i0);
    }

    public int a(int i0, Random random) {
        return this.a(random);
    }

    public void a(World world, BlockPos blockpos, IBlockState iblockstate, EntityLivingBase entitylivingbase, ItemStack itemstack) {
    }

    public Block c(String s0) {
        this.N = s0;
        return this;
    }

    public String H() {
        return StatCollector.a(this.a() + ".name");
    }

    public String a() {
        return "tile." + this.N;
    }

    public boolean a(World world, BlockPos blockpos, IBlockState iblockstate, int i0, int i1) {
        return false;
    }

    public boolean I() {
        return this.y;
    }

    protected Block J() {
        this.y = false;
        return this;
    }

    public int i() {
        return this.J.m();
    }

    public void a(World world, BlockPos blockpos, Entity entity, float f0) {
        entity.e(f0, 1.0F);
    }

    public void a(World world, Entity entity) {
        entity.w = 0.0D;
    }

    public int j(World world, BlockPos blockpos) {
        return this.a(world.p(blockpos));
    }

    public Block a(CreativeTabs creativetabs) {
        this.b = creativetabs;
        return this;
    }

    public void a(World world, BlockPos blockpos, IBlockState iblockstate, EntityPlayer entityplayer) {
    }

    public void k(World world, BlockPos blockpos) {
    }

    public boolean M() {
        return true;
    }

    public boolean a(Explosion explosion) {
        return true;
    }

    public boolean b(Block block) {
        return this == block;
    }

    public static boolean a(Block block, Block block1) {
        return block != null && block1 != null ? (block == block1 ? true : block.b(block1)) : false;
    }

    public boolean N() {
        return false;
    }

    public int l(World world, BlockPos blockpos) {
        return 0;
    }

    protected BlockState e() {
        return new BlockState(this, new IProperty[0]);
    }

    public BlockState O() {
        return this.L;
    }

    protected final void j(IBlockState iblockstate) {
        this.M = iblockstate;
    }

    public final IBlockState P() {
        return this.M;
    }

    public static void R() {
        a(0, a, (new BlockAir()).c("air"));
        a(1, "stone", (new BlockStone()).c(1.5F).b(10.0F).a(i).c("stone"));
        a(2, "grass", (new BlockGrass()).c(0.6F).a(h).c("grass"));
        a(3, "dirt", (new BlockDirt()).c(0.5F).a(g).c("dirt"));
        Block block = (new Block(Material.e)).c(2.0F).b(10.0F).a(i).c("stonebrick").a(CreativeTabs.b);

        a(4, "cobblestone", block);
        Block block1 = (new BlockPlanks()).c(2.0F).b(5.0F).a(f).c("wood");

        a(5, "planks", block1);
        a(6, "sapling", (new BlockSapling()).c(0.0F).a(h).c("sapling"));
        a(7, "bedrock", (new Block(Material.e)).v().b(6000000.0F).a(i).c("bedrock").J().a(CreativeTabs.b));
        a(8, "flowing_water", (new BlockDynamicLiquid(Material.h)).c(100.0F).e(3).c("water").J());
        a(9, "water", (new BlockStaticLiquid(Material.h)).c(100.0F).e(3).c("water").J());
        a(10, "flowing_lava", (new BlockDynamicLiquid(Material.i)).c(100.0F).a(1.0F).c("lava").J());
        a(11, "lava", (new BlockStaticLiquid(Material.i)).c(100.0F).a(1.0F).c("lava").J());
        a(12, "sand", (new BlockSand()).c(0.5F).a(m).c("sand"));
        a(13, "gravel", (new BlockGravel()).c(0.6F).a(g).c("gravel"));
        a(14, "gold_ore", (new BlockOre()).c(3.0F).b(5.0F).a(i).c("oreGold"));
        a(15, "iron_ore", (new BlockOre()).c(3.0F).b(5.0F).a(i).c("oreIron"));
        a(16, "coal_ore", (new BlockOre()).c(3.0F).b(5.0F).a(i).c("oreCoal"));
        a(17, "log", (new BlockOldLog()).c("log"));
        a(18, "leaves", (new BlockOldLeaf()).c("leaves"));
        a(19, "sponge", (new BlockSponge()).c(0.6F).a(h).c("sponge"));
        a(20, "glass", (new BlockGlass(Material.s, false)).c(0.3F).a(k).c("glass"));
        a(21, "lapis_ore", (new BlockOre()).c(3.0F).b(5.0F).a(i).c("oreLapis"));
        a(22, "lapis_block", (new BlockCompressed(MapColor.H)).c(3.0F).b(5.0F).a(i).c("blockLapis").a(CreativeTabs.b));
        a(23, "dispenser", (new BlockDispenser()).c(3.5F).a(i).c("dispenser"));
        Block block2 = (new BlockSandStone()).a(i).c(0.8F).c("sandStone");

        a(24, "sandstone", block2);
        a(25, "noteblock", (new BlockNote()).c(0.8F).c("musicBlock"));
        a(26, "bed", (new BlockBed()).a(f).c(0.2F).c("bed").J());
        a(27, "golden_rail", (new BlockRailPowered()).c(0.7F).a(j).c("goldenRail"));
        a(28, "detector_rail", (new BlockRailDetector()).c(0.7F).a(j).c("detectorRail"));
        a(29, "sticky_piston", (new BlockPistonBase(true)).c("pistonStickyBase"));
        a(30, "web", (new BlockWeb()).e(1).c(4.0F).c("web"));
        a(31, "tallgrass", (new BlockTallGrass()).c(0.0F).a(h).c("tallgrass"));
        a(32, "deadbush", (new BlockDeadBush()).c(0.0F).a(h).c("deadbush"));
        a(33, "piston", (new BlockPistonBase(false)).c("pistonBase"));
        a(34, "piston_head", new BlockPistonExtension());
        a(35, "wool", (new BlockColored(Material.n)).c(0.8F).a(l).c("cloth"));
        a(36, "piston_extension", new BlockPistonMoving());
        a(37, "yellow_flower", (new BlockYellowFlower()).c(0.0F).a(h).c("flower1"));
        a(38, "red_flower", (new BlockRedFlower()).c(0.0F).a(h).c("flower2"));
        Block block3 = (new BlockMushroom()).c(0.0F).a(h).a(0.125F).c("mushroom");

        a(39, "brown_mushroom", block3);
        Block block4 = (new BlockMushroom()).c(0.0F).a(h).c("mushroom");

        a(40, "red_mushroom", block4);
        a(41, "gold_block", (new BlockCompressed(MapColor.F)).c(3.0F).b(10.0F).a(j).c("blockGold"));
        a(42, "iron_block", (new BlockCompressed(MapColor.h)).c(5.0F).b(10.0F).a(j).c("blockIron"));
        a(43, "double_stone_slab", (new BlockDoubleStoneSlab()).c(2.0F).b(10.0F).a(i).c("stoneSlab"));
        a(44, "stone_slab", (new BlockHalfStoneSlab()).c(2.0F).b(10.0F).a(i).c("stoneSlab"));
        Block block5 = (new Block(Material.e)).c(2.0F).b(10.0F).a(i).c("brick").a(CreativeTabs.b);

        a(45, "brick_block", block5);
        a(46, "tnt", (new BlockTNT()).c(0.0F).a(h).c("tnt"));
        a(47, "bookshelf", (new BlockBookshelf()).c(1.5F).a(f).c("bookshelf"));
        a(48, "mossy_cobblestone", (new Block(Material.e)).c(2.0F).b(10.0F).a(i).c("stoneMoss").a(CreativeTabs.b));
        a(49, "obsidian", (new BlockObsidian()).c(50.0F).b(2000.0F).a(i).c("obsidian"));
        a(50, "torch", (new BlockTorch()).c(0.0F).a(0.9375F).a(f).c("torch"));
        a(51, "fire", (new BlockFire()).c(0.0F).a(1.0F).a(l).c("fire").J());
        a(52, "mob_spawner", (new BlockMobSpawner()).c(5.0F).a(j).c("mobSpawner").J());
        a(53, "oak_stairs", (new BlockStairs(block1.P().a(BlockPlanks.a, BlockPlanks.EnumType.OAK))).c("stairsWood"));
        a(54, "chest", (new BlockChest(0)).c(2.5F).a(f).c("chest"));
        a(55, "redstone_wire", (new BlockRedstoneWire()).c(0.0F).a(e).c("redstoneDust").J());
        a(56, "diamond_ore", (new BlockOre()).c(3.0F).b(5.0F).a(i).c("oreDiamond"));
        a(57, "diamond_block", (new BlockCompressed(MapColor.G)).c(5.0F).b(10.0F).a(j).c("blockDiamond"));
        a(58, "crafting_table", (new BlockWorkbench()).c(2.5F).a(f).c("workbench"));
        a(59, "wheat", (new BlockCrops()).c("crops"));
        Block block6 = (new BlockFarmland()).c(0.6F).a(g).c("farmland");

        a(60, "farmland", block6);
        a(61, "furnace", (new BlockFurnace(false)).c(3.5F).a(i).c("furnace").a(CreativeTabs.c));
        a(62, "lit_furnace", (new BlockFurnace(true)).c(3.5F).a(i).a(0.875F).c("furnace"));
        a(63, "standing_sign", (new BlockStandingSign()).c(1.0F).a(f).c("sign").J());
        a(64, "wooden_door", (new BlockDoor(Material.d)).c(3.0F).a(f).c("doorOak").J());
        a(65, "ladder", (new BlockLadder()).c(0.4F).a(o).c("ladder"));
        a(66, "rail", (new BlockRail()).c(0.7F).a(j).c("rail"));
        a(67, "stone_stairs", (new BlockStairs(block.P())).c("stairsStone"));
        a(68, "wall_sign", (new BlockWallSign()).c(1.0F).a(f).c("sign").J());
        a(69, "lever", (new BlockLever()).c(0.5F).a(f).c("lever"));
        a(70, "stone_pressure_plate", (new BlockPressurePlate(Material.e, BlockPressurePlate.Sensitivity.MOBS)).c(0.5F).a(i).c("pressurePlateStone"));
        a(71, "iron_door", (new BlockDoor(Material.f)).c(5.0F).a(j).c("doorIron").J());
        a(72, "wooden_pressure_plate", (new BlockPressurePlate(Material.d, BlockPressurePlate.Sensitivity.EVERYTHING)).c(0.5F).a(f).c("pressurePlateWood"));
        a(73, "redstone_ore", (new BlockRedstoneOre(false)).c(3.0F).b(5.0F).a(i).c("oreRedstone").a(CreativeTabs.b));
        a(74, "lit_redstone_ore", (new BlockRedstoneOre(true)).a(0.625F).c(3.0F).b(5.0F).a(i).c("oreRedstone"));
        a(75, "unlit_redstone_torch", (new BlockRedstoneTorch(false)).c(0.0F).a(f).c("notGate"));
        a(76, "redstone_torch", (new BlockRedstoneTorch(true)).c(0.0F).a(0.5F).a(f).c("notGate").a(CreativeTabs.d));
        a(77, "stone_button", (new BlockButtonStone()).c(0.5F).a(i).c("button"));
        a(78, "snow_layer", (new BlockSnow()).c(0.1F).a(n).c("snow").e(0));
        a(79, "ice", (new BlockIce()).c(0.5F).e(3).a(k).c("ice"));
        a(80, "snow", (new BlockSnowBlock()).c(0.2F).a(n).c("snow"));
        a(81, "cactus", (new BlockCactus()).c(0.4F).a(l).c("cactus"));
        a(82, "clay", (new BlockClay()).c(0.6F).a(g).c("clay"));
        a(83, "reeds", (new BlockReed()).c(0.0F).a(h).c("reeds").J());
        a(84, "jukebox", (new BlockJukebox()).c(2.0F).b(10.0F).a(i).c("jukebox"));
        a(85, "fence", (new BlockFence(Material.d)).c(2.0F).b(5.0F).a(f).c("fence"));
        Block block7 = (new BlockPumpkin()).c(1.0F).a(f).c("pumpkin");

        a(86, "pumpkin", block7);
        a(87, "netherrack", (new BlockNetherrack()).c(0.4F).a(i).c("hellrock"));
        a(88, "soul_sand", (new BlockSoulSand()).c(0.5F).a(m).c("hellsand"));
        a(89, "glowstone", (new BlockGlowstone(Material.s)).c(0.3F).a(k).a(1.0F).c("lightgem"));
        a(90, "portal", (new BlockPortal()).c(-1.0F).a(k).a(0.75F).c("portal"));
        a(91, "lit_pumpkin", (new BlockPumpkin()).c(1.0F).a(f).a(1.0F).c("litpumpkin"));
        a(92, "cake", (new BlockCake()).c(0.5F).a(l).c("cake").J());
        a(93, "unpowered_repeater", (new BlockRedstoneRepeater(false)).c(0.0F).a(f).c("diode").J());
        a(94, "powered_repeater", (new BlockRedstoneRepeater(true)).c(0.0F).a(f).c("diode").J());
        a(95, "stained_glass", (new BlockStainedGlass(Material.s)).c(0.3F).a(k).c("stainedGlass"));
        a(96, "trapdoor", (new BlockTrapDoor(Material.d)).c(3.0F).a(f).c("trapdoor").J());
        a(97, "monster_egg", (new BlockSilverfish()).c(0.75F).c("monsterStoneEgg"));
        Block block8 = (new BlockStoneBrick()).c(1.5F).b(10.0F).a(i).c("stonebricksmooth");

        a(98, "stonebrick", block8);
        a(99, "brown_mushroom_block", (new BlockHugeMushroom(Material.d, block3)).c(0.2F).a(f).c("mushroom"));
        a(100, "red_mushroom_block", (new BlockHugeMushroom(Material.d, block4)).c(0.2F).a(f).c("mushroom"));
        a(101, "iron_bars", (new BlockPane(Material.f, true)).c(5.0F).b(10.0F).a(j).c("fenceIron"));
        a(102, "glass_pane", (new BlockPane(Material.s, false)).c(0.3F).a(k).c("thinGlass"));
        Block block9 = (new BlockMelon()).c(1.0F).a(f).c("melon");

        a(103, "melon_block", block9);
        a(104, "pumpkin_stem", (new BlockStem(block7)).c(0.0F).a(f).c("pumpkinStem"));
        a(105, "melon_stem", (new BlockStem(block9)).c(0.0F).a(f).c("pumpkinStem"));
        a(106, "vine", (new BlockVine()).c(0.2F).a(h).c("vine"));
        a(107, "fence_gate", (new BlockFenceGate()).c(2.0F).b(5.0F).a(f).c("fenceGate"));
        a(108, "brick_stairs", (new BlockStairs(block5.P())).c("stairsBrick"));
        a(109, "stone_brick_stairs", (new BlockStairs(block8.P().a(BlockStoneBrick.a, BlockStoneBrick.EnumType.DEFAULT))).c("stairsStoneBrickSmooth"));
        a(110, "mycelium", (new BlockMycelium()).c(0.6F).a(h).c("mycel"));
        a(111, "waterlily", (new BlockLilyPad()).c(0.0F).a(h).c("waterlily"));
        Block block10 = (new BlockNetherBrick()).c(2.0F).b(10.0F).a(i).c("netherBrick").a(CreativeTabs.b);

        a(112, "nether_brick", block10);
        a(113, "nether_brick_fence", (new BlockFence(Material.e)).c(2.0F).b(10.0F).a(i).c("netherFence"));
        a(114, "nether_brick_stairs", (new BlockStairs(block10.P())).c("stairsNetherBrick"));
        a(115, "nether_wart", (new BlockNetherWart()).c("netherStalk"));
        a(116, "enchanting_table", (new BlockEnchantmentTable()).c(5.0F).b(2000.0F).c("enchantmentTable"));
        a(117, "brewing_stand", (new BlockBrewingStand()).c(0.5F).a(0.125F).c("brewingStand"));
        a(118, "cauldron", (new BlockCauldron()).c(2.0F).c("cauldron"));
        a(119, "end_portal", (new BlockEndPortal(Material.E)).c(-1.0F).b(6000000.0F));
        a(120, "end_portal_frame", (new BlockEndPortalFrame()).a(k).a(0.125F).c(-1.0F).c("endPortalFrame").b(6000000.0F).a(CreativeTabs.c));
        a(121, "end_stone", (new Block(Material.e)).c(3.0F).b(15.0F).a(i).c("whiteStone").a(CreativeTabs.b));
        a(122, "dragon_egg", (new BlockDragonEgg()).c(3.0F).b(15.0F).a(i).a(0.125F).c("dragonEgg"));
        a(123, "redstone_lamp", (new BlockRedstoneLight(false)).c(0.3F).a(k).c("redstoneLight").a(CreativeTabs.d));
        a(124, "lit_redstone_lamp", (new BlockRedstoneLight(true)).c(0.3F).a(k).c("redstoneLight"));
        a(125, "double_wooden_slab", (new BlockDoubleWoodSlab()).c(2.0F).b(5.0F).a(f).c("woodSlab"));
        a(126, "wooden_slab", (new BlockHalfWoodSlab()).c(2.0F).b(5.0F).a(f).c("woodSlab"));
        a(127, "cocoa", (new BlockCocoa()).c(0.2F).b(5.0F).a(f).c("cocoa"));
        a(128, "sandstone_stairs", (new BlockStairs(block2.P().a(BlockSandStone.a, BlockSandStone.EnumType.SMOOTH))).c("stairsSandStone"));
        a(129, "emerald_ore", (new BlockOre()).c(3.0F).b(5.0F).a(i).c("oreEmerald"));
        a(130, "ender_chest", (new BlockEnderChest()).c(22.5F).b(1000.0F).a(i).c("enderChest").a(0.5F));
        a(131, "tripwire_hook", (new BlockTripWireHook()).c("tripWireSource"));
        a(132, "tripwire", (new BlockTripWire()).c("tripWire"));
        a(133, "emerald_block", (new BlockCompressed(MapColor.I)).c(5.0F).b(10.0F).a(j).c("blockEmerald"));
        a(134, "spruce_stairs", (new BlockStairs(block1.P().a(BlockPlanks.a, BlockPlanks.EnumType.SPRUCE))).c("stairsWoodSpruce"));
        a(135, "birch_stairs", (new BlockStairs(block1.P().a(BlockPlanks.a, BlockPlanks.EnumType.BIRCH))).c("stairsWoodBirch"));
        a(136, "jungle_stairs", (new BlockStairs(block1.P().a(BlockPlanks.a, BlockPlanks.EnumType.JUNGLE))).c("stairsWoodJungle"));
        a(137, "command_block", (new BlockCommandBlock()).v().b(6000000.0F).c("commandBlock"));
        a(138, "beacon", (new BlockBeacon()).c("beacon").a(1.0F));
        a(139, "cobblestone_wall", (new BlockWall(block)).c("cobbleWall"));
        a(140, "flower_pot", (new BlockFlowerPot()).c(0.0F).a(e).c("flowerPot"));
        a(141, "carrots", (new BlockCarrot()).c("carrots"));
        a(142, "potatoes", (new BlockPotato()).c("potatoes"));
        a(143, "wooden_button", (new BlockButtonWood()).c(0.5F).a(f).c("button"));
        a(144, "skull", (new BlockSkull()).c(1.0F).a(i).c("skull"));
        a(145, "anvil", (new BlockAnvil()).c(5.0F).a(p).b(2000.0F).c("anvil"));
        a(146, "trapped_chest", (new BlockChest(1)).c(2.5F).a(f).c("chestTrap"));
        a(147, "light_weighted_pressure_plate", (new BlockPressurePlateWeighted("gold_block", Material.f, 15)).c(0.5F).a(f).c("weightedPlate_light"));
        a(148, "heavy_weighted_pressure_plate", (new BlockPressurePlateWeighted("iron_block", Material.f, 150)).c(0.5F).a(f).c("weightedPlate_heavy"));
        a(149, "unpowered_comparator", (new BlockRedstoneComparator(false)).c(0.0F).a(f).c("comparator").J());
        a(150, "powered_comparator", (new BlockRedstoneComparator(true)).c(0.0F).a(0.625F).a(f).c("comparator").J());
        a(151, "daylight_detector", new BlockDaylightDetector(false));
        a(152, "redstone_block", (new BlockCompressedPowered(MapColor.f)).c(5.0F).b(10.0F).a(j).c("blockRedstone"));
        a(153, "quartz_ore", (new BlockOre()).c(3.0F).b(5.0F).a(i).c("netherquartz"));
        a(154, "hopper", (new BlockHopper()).c(3.0F).b(8.0F).a(j).c("hopper"));
        Block block11 = (new BlockQuartz()).a(i).c(0.8F).c("quartzBlock");

        a(155, "quartz_block", block11);
        a(156, "quartz_stairs", (new BlockStairs(block11.P().a(BlockQuartz.a, BlockQuartz.EnumType.DEFAULT))).c("stairsQuartz"));
        a(157, "activator_rail", (new BlockRailPowered()).c(0.7F).a(j).c("activatorRail"));
        a(158, "dropper", (new BlockDropper()).c(3.5F).a(i).c("dropper"));
        a(159, "stained_hardened_clay", (new BlockColored(Material.e)).c(1.25F).b(7.0F).a(i).c("clayHardenedStained"));
        a(160, "stained_glass_pane", (new BlockStainedGlassPane()).c(0.3F).a(k).c("thinStainedGlass"));
        a(161, "leaves2", (new BlockNewLeaf()).c("leaves"));
        a(162, "log2", (new BlockNewLog()).c("log"));
        a(163, "acacia_stairs", (new BlockStairs(block1.P().a(BlockPlanks.a, BlockPlanks.EnumType.ACACIA))).c("stairsWoodAcacia"));
        a(164, "dark_oak_stairs", (new BlockStairs(block1.P().a(BlockPlanks.a, BlockPlanks.EnumType.DARK_OAK))).c("stairsWoodDarkOak"));
        a(165, "slime", (new BlockSlime()).c("slime").a(q));
        a(166, "barrier", (new BlockBarrier()).c("barrier"));
        a(167, "iron_trapdoor", (new BlockTrapDoor(Material.f)).c(5.0F).a(j).c("ironTrapdoor").J());
        a(168, "prismarine", (new BlockPrismarine()).c(1.5F).b(10.0F).a(i).c("prismarine"));
        a(169, "sea_lantern", (new BlockSeaLantern(Material.s)).c(0.3F).a(k).a(1.0F).c("seaLantern"));
        a(170, "hay_block", (new BlockHay()).c(0.5F).a(h).c("hayBlock").a(CreativeTabs.b));
        a(171, "carpet", (new BlockCarpet()).c(0.1F).a(l).c("woolCarpet").e(0));
        a(172, "hardened_clay", (new BlockHardenedClay()).c(1.25F).b(7.0F).a(i).c("clayHardened"));
        a(173, "coal_block", (new Block(Material.e)).c(5.0F).b(10.0F).a(i).c("blockCoal").a(CreativeTabs.b));
        a(174, "packed_ice", (new BlockPackedIce()).c(0.5F).a(k).c("icePacked"));
        a(175, "double_plant", new BlockDoublePlant());
        a(176, "standing_banner", (new BlockBanner.BlockBannerStanding()).c(1.0F).a(f).c("banner").J());
        a(177, "wall_banner", (new BlockBanner.BlockBannerHanging()).c(1.0F).a(f).c("banner").J());
        a(178, "daylight_detector_inverted", new BlockDaylightDetector(true));
        Block block12 = (new BlockRedSandstone()).a(i).c(0.8F).c("redSandStone");

        a(179, "red_sandstone", block12);
        a(180, "red_sandstone_stairs", (new BlockStairs(block12.P().a(BlockRedSandstone.a, BlockRedSandstone.EnumType.SMOOTH))).c("stairsRedSandStone"));
        a(181, "double_stone_slab2", (new BlockDoubleStoneSlabNew()).c(2.0F).b(10.0F).a(i).c("stoneSlab2"));
        a(182, "stone_slab2", (new BlockHalfStoneSlabNew()).c(2.0F).b(10.0F).a(i).c("stoneSlab2"));
        a(183, "spruce_fence_gate", (new BlockFenceGate()).c(2.0F).b(5.0F).a(f).c("spruceFenceGate"));
        a(184, "birch_fence_gate", (new BlockFenceGate()).c(2.0F).b(5.0F).a(f).c("birchFenceGate"));
        a(185, "jungle_fence_gate", (new BlockFenceGate()).c(2.0F).b(5.0F).a(f).c("jungleFenceGate"));
        a(186, "dark_oak_fence_gate", (new BlockFenceGate()).c(2.0F).b(5.0F).a(f).c("darkOakFenceGate"));
        a(187, "acacia_fence_gate", (new BlockFenceGate()).c(2.0F).b(5.0F).a(f).c("acaciaFenceGate"));
        a(188, "spruce_fence", (new BlockFence(Material.d)).c(2.0F).b(5.0F).a(f).c("spruceFence"));
        a(189, "birch_fence", (new BlockFence(Material.d)).c(2.0F).b(5.0F).a(f).c("birchFence"));
        a(190, "jungle_fence", (new BlockFence(Material.d)).c(2.0F).b(5.0F).a(f).c("jungleFence"));
        a(191, "dark_oak_fence", (new BlockFence(Material.d)).c(2.0F).b(5.0F).a(f).c("darkOakFence"));
        a(192, "acacia_fence", (new BlockFence(Material.d)).c(2.0F).b(5.0F).a(f).c("acaciaFence"));
        a(193, "spruce_door", (new BlockDoor(Material.d)).c(3.0F).a(f).c("doorSpruce").J());
        a(194, "birch_door", (new BlockDoor(Material.d)).c(3.0F).a(f).c("doorBirch").J());
        a(195, "jungle_door", (new BlockDoor(Material.d)).c(3.0F).a(f).c("doorJungle").J());
        a(196, "acacia_door", (new BlockDoor(Material.d)).c(3.0F).a(f).c("doorAcacia").J());
        a(197, "dark_oak_door", (new BlockDoor(Material.d)).c(3.0F).a(f).c("doorDarkOak").J());
        c.a();
        Iterator iterator = c.iterator();

        Block block13;

        while (iterator.hasNext()) {
            block13 = (Block) iterator.next();
            if (block13.J == Material.a) {
                block13.v = false;
            }
            else {
                boolean flag0 = false;
                boolean flag1 = block13 instanceof BlockStairs;
                boolean flag2 = block13 instanceof BlockSlab;
                boolean flag3 = block13 == block6;
                boolean flag4 = block13.t;
                boolean flag5 = block13.s == 0;

                if (flag1 || flag2 || flag3 || flag4 || flag5) {
                    flag0 = true;
                }

                block13.v = flag0;
            }
        }

        iterator = c.iterator();

        while (iterator.hasNext()) {
            block13 = (Block) iterator.next();
            Iterator iterator1 = block13.O().a().iterator();

            while (iterator1.hasNext()) {
                IBlockState iblockstate = (IBlockState) iterator1.next();
                int i0 = c.b(block13) << 4 | block13.c(iblockstate);

                d.a(iblockstate, i0);
            }
        }

    }

    private static void a(int i0, ResourceLocation resourcelocation, Block block) {
        c.a(i0, resourcelocation, block);
    }

    private static void a(int i0, String s0, Block block) {
        a(i0, new ResourceLocation(s0), block);
    }

    public static class SoundType {

        public final String a;
        public final float b;
        public final float c;

        public SoundType(String s0, float f0, float f1) {
            this.a = s0;
            this.b = f0;
            this.c = f1;
        }

        public float d() {
            return this.b;
        }

        public float e() {
            return this.c;
        }

        public String a() {
            return "dig." + this.a;
        }

        public String c() {
            return "step." + this.a;
        }

        public String b() {
            return this.a();
        }
    }
}
