package net.minecraft.block;

import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.hook.world.BlockGrowHook;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.Random;

public class BlockVine extends Block {

    public static final PropertyBool a = PropertyBool.a("up");
    public static final PropertyBool b = PropertyBool.a("north");
    public static final PropertyBool M = PropertyBool.a("east");
    public static final PropertyBool N = PropertyBool.a("south");
    public static final PropertyBool O = PropertyBool.a("west");
    public static final PropertyBool[] P = new PropertyBool[]{ a, b, N, O, M };
    public static final int Q = b(EnumFacing.SOUTH);
    public static final int R = b(EnumFacing.NORTH);
    public static final int S = b(EnumFacing.EAST);
    public static final int T = b(EnumFacing.WEST);

    public BlockVine() {
        super(Material.l);
        this.j(this.L.b().a(a, Boolean.valueOf(false)).a(b, Boolean.valueOf(false)).a(M, Boolean.valueOf(false)).a(N, Boolean.valueOf(false)).a(O, Boolean.valueOf(false)));
        this.a(true);
        this.a(CreativeTabs.c);
    }

    private static int b(EnumFacing enumfacing) {
        return 1 << enumfacing.b();
    }

    public static PropertyBool a(EnumFacing enumfacing) {
        switch (BlockVine.SwitchEnumFacing.a[enumfacing.ordinal()]) {
            case 1:
                return a;

            case 2:
                return b;

            case 3:
                return N;

            case 4:
                return M;

            case 5:
                return O;

            default:
                throw new IllegalArgumentException(enumfacing + " is an invalid choice");
        }
    }

    public static int d(IBlockState iblockstate) {
        int i0 = 0;
        PropertyBool[] apropertybool = P;
        int i1 = apropertybool.length;

        for (int i2 = 0; i2 < i1; ++i2) {
            PropertyBool propertybool = apropertybool[i2];

            if (((Boolean)iblockstate.b(propertybool)).booleanValue()) {
                ++i0;
            }
        }

        return i0;
    }

    public IBlockState a(IBlockState iblockstate, IBlockAccess iblockaccess, BlockPos blockpos) {
        return iblockstate.a(a, Boolean.valueOf(iblockaccess.p(blockpos.a()).c().s()));
    }

    public void h() {
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    public boolean c() {
        return false;
    }

    public boolean d() {
        return false;
    }

    public boolean f(World world, BlockPos blockpos) {
        return true;
    }

    public void a(IBlockAccess iblockaccess, BlockPos blockpos) {
        float f0 = 0.0625F;
        float f1 = 1.0F;
        float f2 = 1.0F;
        float f3 = 1.0F;
        float f4 = 0.0F;
        float f5 = 0.0F;
        float f6 = 0.0F;
        boolean flag0 = false;

        if (((Boolean)iblockaccess.p(blockpos).b(O)).booleanValue()) {
            f4 = Math.max(f4, 0.0625F);
            f1 = 0.0F;
            f2 = 0.0F;
            f5 = 1.0F;
            f3 = 0.0F;
            f6 = 1.0F;
            flag0 = true;
        }

        if (((Boolean)iblockaccess.p(blockpos).b(M)).booleanValue()) {
            f1 = Math.min(f1, 0.9375F);
            f4 = 1.0F;
            f2 = 0.0F;
            f5 = 1.0F;
            f3 = 0.0F;
            f6 = 1.0F;
            flag0 = true;
        }

        if (((Boolean)iblockaccess.p(blockpos).b(b)).booleanValue()) {
            f6 = Math.max(f6, 0.0625F);
            f3 = 0.0F;
            f1 = 0.0F;
            f4 = 1.0F;
            f2 = 0.0F;
            f5 = 1.0F;
            flag0 = true;
        }

        if (((Boolean)iblockaccess.p(blockpos).b(N)).booleanValue()) {
            f3 = Math.min(f3, 0.9375F);
            f6 = 1.0F;
            f1 = 0.0F;
            f4 = 1.0F;
            f2 = 0.0F;
            f5 = 1.0F;
            flag0 = true;
        }

        if (!flag0 && this.c(iblockaccess.p(blockpos.a()).c())) {
            f2 = Math.min(f2, 0.9375F);
            f5 = 1.0F;
            f1 = 0.0F;
            f4 = 1.0F;
            f3 = 0.0F;
            f6 = 1.0F;
        }

        this.a(f1, f2, f3, f4, f5, f6);
    }

    public AxisAlignedBB a(World world, BlockPos blockpos, IBlockState iblockstate) {
        return null;
    }

    public boolean a(World world, BlockPos blockpos, EnumFacing enumfacing) {
        switch (BlockVine.SwitchEnumFacing.a[enumfacing.ordinal()]) {
            case 1:
                return this.c(world.p(blockpos.a()).c());

            case 2:
            case 3:
            case 4:
            case 5:
                return this.c(world.p(blockpos.a(enumfacing.d())).c());

            default:
                return false;
        }
    }

    private boolean c(Block block) {
        return block.d() && block.J.c();
    }

    private boolean e(World world, BlockPos blockpos, IBlockState iblockstate) {
        IBlockState iblockstate1 = iblockstate;
        Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();

        while (iterator.hasNext()) {
            EnumFacing enumfacing = (EnumFacing)iterator.next();
            PropertyBool propertybool = a(enumfacing);

            if (((Boolean)iblockstate.b(propertybool)).booleanValue() && !this.c(world.p(blockpos.a(enumfacing)).c())) {
                IBlockState iblockstate2 = world.p(blockpos.a());

                if (iblockstate2.c() != this || !((Boolean)iblockstate2.b(propertybool)).booleanValue()) {
                    iblockstate = iblockstate.a(propertybool, Boolean.valueOf(false));
                }
            }
        }

        if (d(iblockstate) == 0) {
            return false;
        }
        else {
            if (iblockstate1 != iblockstate) {
                world.a(blockpos, iblockstate, 2);
            }

            return true;
        }
    }

    public void a(World world, BlockPos blockpos, IBlockState iblockstate, Block block) {
        if (!world.D && !this.e(world, blockpos, iblockstate)) {
            this.b(world, blockpos, iblockstate, 0);
            world.g(blockpos);
        }
    }

    public void b(World world, BlockPos blockpos, IBlockState iblockstate, Random random) {
        if (!world.D) {
            if (world.s.nextInt(4) == 0) {
                byte b0 = 4;
                int i0 = 5;
                boolean flag0 = false;

                label189:
                for (int i1 = -b0; i1 <= b0; ++i1) {
                    for (int i2 = -b0; i2 <= b0; ++i2) {
                        for (int i3 = -1; i3 <= 1; ++i3) {
                            if (world.p(blockpos.a(i1, i3, i2)).c() == this) {
                                --i0;
                                if (i0 <= 0) {
                                    flag0 = true;
                                    break label189;
                                }
                            }
                        }
                    }
                }

                EnumFacing enumfacing = EnumFacing.a(random);
                EnumFacing enumfacing1;

                if (enumfacing == EnumFacing.UP && blockpos.o() < 255 && world.d(blockpos.a())) {
                    if (!flag0) {
                        IBlockState iblockstate1 = iblockstate;
                        Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();

                        while (iterator.hasNext()) {
                            enumfacing1 = (EnumFacing)iterator.next();
                            if (random.nextBoolean() || !this.c(world.p(blockpos.a(enumfacing1).a()).c())) {
                                iblockstate1 = iblockstate1.a(a(enumfacing1), Boolean.valueOf(false));
                            }
                        }

                        if (((Boolean)iblockstate1.b(b)).booleanValue() || ((Boolean)iblockstate1.b(M)).booleanValue() || ((Boolean)iblockstate1.b(N)).booleanValue() || ((Boolean)iblockstate1.b(O)).booleanValue()) {
                            world.a(blockpos.a(), iblockstate1, 2);
                        }
                    }
                }
                else {
                    BlockPos blockpos1;

                    if (enumfacing.k().c() && !((Boolean)iblockstate.b(a(enumfacing))).booleanValue()) {
                        if (!flag0) {
                            blockpos1 = blockpos.a(enumfacing);
                            Block block = world.p(blockpos1).c();

                            if (block.J == Material.a) {
                                enumfacing1 = enumfacing.e();
                                EnumFacing enumfacing2 = enumfacing.f();
                                boolean flag1 = ((Boolean)iblockstate.b(a(enumfacing1))).booleanValue();
                                boolean flag2 = ((Boolean)iblockstate.b(a(enumfacing2))).booleanValue();
                                BlockPos blockpos2 = blockpos1.a(enumfacing1);
                                BlockPos blockpos3 = blockpos1.a(enumfacing2);

                                // CanaryMod: Grab the original stuff
                                CanaryBlock original = CanaryBlock.getPooledBlock(iblockstate, blockpos, world);
                                //
                                if (flag1 && this.c(world.p(blockpos2).c())) {
                                    // CanaryMod: set data, call hook
                                    if (!new BlockGrowHook(original, CanaryBlock.getPooledBlock(this.P().a(a(enumfacing1)), blockpos1, world)).call().isCanceled()) {
                                        world.a(blockpos1, this.P().a(a(enumfacing1), Boolean.valueOf(true)), 2);
                                    }
                                    //
                                }
                                else if (flag2 && this.c(world.p(blockpos3).c())) {
                                    // CanaryMod: set data, call hook
                                    if (!new BlockGrowHook(original, CanaryBlock.getPooledBlock(this.P().a(a(enumfacing2)), blockpos1, world)).call().isCanceled()) {
                                        world.a(blockpos1, this.P().a(a(enumfacing2), Boolean.valueOf(true)), 2);
                                    }
                                    //
                                }
                                else if (flag1 && world.d(blockpos2) && this.c(world.p(blockpos.a(enumfacing1)).c())) {
                                    // CanaryMod: set data, call hook
                                    if (!new BlockGrowHook(original, CanaryBlock.getPooledBlock(this.P().a(a(enumfacing.d())), blockpos2, world)).call().isCanceled()) {
                                        world.a(blockpos2, this.P().a(a(enumfacing.d()), Boolean.valueOf(true)), 2);
                                    }
                                    //
                                }
                                else if (flag2 && world.d(blockpos3) && this.c(world.p(blockpos.a(enumfacing2)).c())) {
                                    // CanaryMod: set data, call hook
                                    if (!new BlockGrowHook(original, CanaryBlock.getPooledBlock(this.P().a(a(enumfacing.d())), blockpos3, world)).call().isCanceled()) {
                                        world.a(blockpos3, this.P().a(a(enumfacing.d()), Boolean.valueOf(true)), 2);
                                    }
                                    //
                                }
                                else if (this.c(world.p(blockpos1.a()).c())) {
                                    // CanaryMod: call hook
                                    if (!new BlockGrowHook(original, CanaryBlock.getPooledBlock(this.P(), blockpos1, world)).call().isCanceled()) {
                                        world.a(blockpos1, this.P(), 2);
                                    }
                                    //
                                }
                            }
                            else if (block.J.k() && block.d()) {
                                world.a(blockpos, iblockstate.a(a(enumfacing), Boolean.valueOf(true)), 2);
                            }
                        }
                        else {
                            if (blockpos.o() > 1) {
                                blockpos1 = blockpos.b();
                                IBlockState iblockstate2 = world.p(blockpos1);
                                Block block1 = iblockstate2.c();
                                IBlockState iblockstate3;
                                Iterator iterator1;
                                EnumFacing enumfacing3;

                                if (block1.J == Material.a) {
                                    iblockstate3 = iblockstate;
                                    iterator1 = EnumFacing.Plane.HORIZONTAL.iterator();

                                    while (iterator1.hasNext()) {
                                        enumfacing3 = (EnumFacing)iterator1.next();
                                        if (random.nextBoolean()) {
                                            iblockstate3 = iblockstate3.a(a(enumfacing3), Boolean.valueOf(false));
                                        }
                                    }

                                    if (((Boolean)iblockstate3.b(b)).booleanValue() || ((Boolean)iblockstate3.b(M)).booleanValue() || ((Boolean)iblockstate3.b(N)).booleanValue() || ((Boolean)iblockstate3.b(O)).booleanValue()) {
                                        world.a(blockpos1, iblockstate3, 2);
                                    }
                                }
                                else if (block1 == this) {
                                    iblockstate3 = iblockstate2;
                                    iterator1 = EnumFacing.Plane.HORIZONTAL.iterator();

                                    while (iterator1.hasNext()) {
                                        enumfacing3 = (EnumFacing)iterator1.next();
                                        PropertyBool propertybool = a(enumfacing3);

                                        if (random.nextBoolean() || !((Boolean)iblockstate.b(propertybool)).booleanValue()) {
                                            iblockstate3 = iblockstate3.a(propertybool, Boolean.valueOf(false));
                                        }
                                    }

                                    if (((Boolean)iblockstate3.b(b)).booleanValue() || ((Boolean)iblockstate3.b(M)).booleanValue() || ((Boolean)iblockstate3.b(N)).booleanValue() || ((Boolean)iblockstate3.b(O)).booleanValue()) {
                                        world.a(blockpos1, iblockstate3, 2);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public IBlockState a(World world, BlockPos blockpos, EnumFacing enumfacing, float f0, float f1, float f2, int i0, EntityLivingBase entitylivingbase) {
        IBlockState iblockstate = this.P().a(a, Boolean.valueOf(false)).a(b, Boolean.valueOf(false)).a(M, Boolean.valueOf(false)).a(N, Boolean.valueOf(false)).a(O, Boolean.valueOf(false));

        return enumfacing.k().c() ? iblockstate.a(a(enumfacing.d()), Boolean.valueOf(true)) : iblockstate;
    }

    public Item a(IBlockState iblockstate, Random random, int i0) {
        return null;
    }

    public int a(Random random) {
        return 0;
    }

    public void a(World world, EntityPlayer entityplayer, BlockPos blockpos, IBlockState iblockstate, TileEntity tileentity) {
        if (!world.D && entityplayer.bY() != null && entityplayer.bY().b() == Items.be) {
            entityplayer.b(StatList.H[Block.a((Block)this)]);
            a(world, blockpos, new ItemStack(Blocks.bn, 1, 0));
        }
        else {
            super.a(world, entityplayer, blockpos, iblockstate, tileentity);
        }
    }

    public IBlockState a(int i0) {
        return this.P().a(b, Boolean.valueOf((i0 & R) > 0)).a(M, Boolean.valueOf((i0 & S) > 0)).a(N, Boolean.valueOf((i0 & Q) > 0)).a(O, Boolean.valueOf((i0 & T) > 0));
    }

    public int c(IBlockState iblockstate) {
        int i0 = 0;

        if (((Boolean)iblockstate.b(b)).booleanValue()) {
            i0 |= R;
        }

        if (((Boolean)iblockstate.b(M)).booleanValue()) {
            i0 |= S;
        }

        if (((Boolean)iblockstate.b(N)).booleanValue()) {
            i0 |= Q;
        }

        if (((Boolean)iblockstate.b(O)).booleanValue()) {
            i0 |= T;
        }

        return i0;
    }

    protected BlockState e() {
        return new BlockState(this, new IProperty[]{ a, b, M, N, O });
    }

    static final class SwitchEnumFacing {

        static final int[] a = new int[EnumFacing.values().length];

        static {
            try {
                a[EnumFacing.UP.ordinal()] = 1;
            }
            catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                a[EnumFacing.NORTH.ordinal()] = 2;
            }
            catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                a[EnumFacing.SOUTH.ordinal()] = 3;
            }
            catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                a[EnumFacing.EAST.ordinal()] = 4;
            }
            catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

            try {
                a[EnumFacing.WEST.ordinal()] = 5;
            }
            catch (NoSuchFieldError nosuchfielderror4) {
                ;
            }
        }
    }
}
