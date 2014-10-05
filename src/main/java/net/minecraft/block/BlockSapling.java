package net.minecraft.block;

import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.hook.world.BlockGrowHook;
import net.canarymod.hook.world.TreeGrowHook;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.*;

import java.util.Random;


public class BlockSapling extends BlockBush implements IGrowable {

    public static final PropertyEnum a = PropertyEnum.a("type", BlockPlanks.EnumType.class);
    public static final PropertyInteger b = PropertyInteger.a("stage", 0, 1);

    protected BlockSapling() {
        this.j(this.L.b().a(a, BlockPlanks.EnumType.OAK).a(b, Integer.valueOf(0)));
        float f0 = 0.4F;

        this.a(0.5F - f0, 0.0F, 0.5F - f0, 0.5F + f0, f0 * 2.0F, 0.5F + f0);
        this.a(CreativeTabs.c);
    }

    public void b(World world, BlockPos blockpos, IBlockState iblockstate, Random random) {
        if (!world.D) {
            super.b(world, blockpos, iblockstate, random);
            if (world.l(blockpos.a()) >= 9 && random.nextInt(7) == 0) {
                this.d(world, blockpos, iblockstate, random);
            }

        }
    }

    public void d(World world, BlockPos blockpos, IBlockState iblockstate, Random random) {
        if (((Integer) iblockstate.b(b)).intValue() == 0) {
            // CanaryMod: BlockGrowHook
            CanaryBlock growth = (CanaryBlock) world.getCanaryWorld().getBlockAt(i0, i1, i2);
            growth.setData((short) (i3 | 8));
            BlockGrowHook blockGrowHook = new BlockGrowHook(world.getCanaryWorld().getBlockAt(i0, i1, i2), growth);
            if (!blockGrowHook.isCanceled()) {
                world.a(blockpos, iblockstate.a(b), 4);
            }
            //
        }
        else {
            // CanaryMod: TreeGrow; If someone figures out how to get more information into this, let me know - darkdiplomat;
            TreeGrowHook hook = (TreeGrowHook) new TreeGrowHook(world.getCanaryWorld().getBlockAt(i0, i1, i2)).call();
            if (!hook.isCanceled()) {
                this.e(world, blockpos, iblockstate, random);
            }
            //
        }
    }

    public void e(World world, BlockPos blockpos, IBlockState iblockstate, Random random) {
        Object object = random.nextInt(10) == 0 ? new WorldGenBigTree(true) : new WorldGenTrees(true);
        int i0 = 0;
        int i1 = 0;
        boolean flag0 = false;

        switch (BlockSapling.SwitchEnumType.a[((BlockPlanks.EnumType) iblockstate.b(a)).ordinal()]) {
            case 1:
                label78:
                for (i0 = 0; i0 >= -1; --i0) {
                    for (i1 = 0; i1 >= -1; --i1) {
                        if (this.a(world, blockpos.a(i0, 0, i1), BlockPlanks.EnumType.SPRUCE) && this.a(world, blockpos.a(i0 + 1, 0, i1), BlockPlanks.EnumType.SPRUCE) && this.a(world, blockpos.a(i0, 0, i1 + 1), BlockPlanks.EnumType.SPRUCE) && this.a(world, blockpos.a(i0 + 1, 0, i1 + 1), BlockPlanks.EnumType.SPRUCE)) {
                            object = new WorldGenMegaPineTree(false, random.nextBoolean());
                            flag0 = true;
                            break label78;
                        }
                    }
                }

                if (!flag0) {
                    i1 = 0;
                    i0 = 0;
                    object = new WorldGenTaiga2(true);
                }
                break;

            case 2:
                object = new WorldGenForest(true, false);
                break;

            case 3:
                label93:
                for (i0 = 0; i0 >= -1; --i0) {
                    for (i1 = 0; i1 >= -1; --i1) {
                        if (this.a(world, blockpos.a(i0, 0, i1), BlockPlanks.EnumType.JUNGLE) && this.a(world, blockpos.a(i0 + 1, 0, i1), BlockPlanks.EnumType.JUNGLE) && this.a(world, blockpos.a(i0, 0, i1 + 1), BlockPlanks.EnumType.JUNGLE) && this.a(world, blockpos.a(i0 + 1, 0, i1 + 1), BlockPlanks.EnumType.JUNGLE)) {
                            object = new WorldGenMegaJungle(true, 10, 20, BlockPlanks.EnumType.JUNGLE.a(), BlockPlanks.EnumType.JUNGLE.a());
                            flag0 = true;
                            break label93;
                        }
                    }
                }

                if (!flag0) {
                    i1 = 0;
                    i0 = 0;
                    object = new WorldGenTrees(true, 4 + random.nextInt(7), BlockPlanks.EnumType.JUNGLE.a(), BlockPlanks.EnumType.JUNGLE.a(), false);
                }
                break;

            case 4:
                object = new WorldGenSavannaTree(true);
                break;

            case 5:
                label108:
                for (i0 = 0; i0 >= -1; --i0) {
                    for (i1 = 0; i1 >= -1; --i1) {
                        if (this.a(world, blockpos.a(i0, 0, i1), BlockPlanks.EnumType.DARK_OAK) && this.a(world, blockpos.a(i0 + 1, 0, i1), BlockPlanks.EnumType.DARK_OAK) && this.a(world, blockpos.a(i0, 0, i1 + 1), BlockPlanks.EnumType.DARK_OAK) && this.a(world, blockpos.a(i0 + 1, 0, i1 + 1), BlockPlanks.EnumType.DARK_OAK)) {
                            object = new WorldGenCanopyTree(true);
                            flag0 = true;
                            break label108;
                        }
                    }
                }

                if (!flag0) {
                    return;
                }

            case 6:
        }

        IBlockState iblockstate1 = Blocks.a.P();

        if (flag0) {
            world.a(blockpos.a(i0, 0, i1), iblockstate1, 4);
            world.a(blockpos.a(i0 + 1, 0, i1), iblockstate1, 4);
            world.a(blockpos.a(i0, 0, i1 + 1), iblockstate1, 4);
            world.a(blockpos.a(i0 + 1, 0, i1 + 1), iblockstate1, 4);
        }
        else {
            world.a(blockpos, iblockstate1, 4);
        }

        if (!((WorldGenerator) object).b(world, random, blockpos.a(i0, 0, i1))) {
            if (flag0) {
                world.a(blockpos.a(i0, 0, i1), iblockstate, 4);
                world.a(blockpos.a(i0 + 1, 0, i1), iblockstate, 4);
                world.a(blockpos.a(i0, 0, i1 + 1), iblockstate, 4);
                world.a(blockpos.a(i0 + 1, 0, i1 + 1), iblockstate, 4);
            }
            else {
                world.a(blockpos, iblockstate, 4);
            }
        }

    }

    public boolean a(World world, BlockPos blockpos, BlockPlanks.EnumType blockplanks_enumtype) {
        IBlockState iblockstate = world.p(blockpos);

        return iblockstate.c() == this && iblockstate.b(a) == blockplanks_enumtype;
    }

    public int a(IBlockState iblockstate) {
        return ((BlockPlanks.EnumType) iblockstate.b(a)).a();
    }

    public boolean a(World world, BlockPos blockpos, IBlockState iblockstate, boolean flag0) {
        return true;
    }

    public boolean a(World world, Random random, BlockPos blockpos, IBlockState iblockstate) {
        return (double) world.s.nextFloat() < 0.45D;
    }

    public void b(World world, Random random, BlockPos blockpos, IBlockState iblockstate) {
        this.d(world, blockpos, iblockstate, random);
    }

    public IBlockState a(int i0) {
        return this.P().a(a, BlockPlanks.EnumType.a(i0 & 7)).a(b, Integer.valueOf((i0 & 8) >> 3));
    }

    public int c(IBlockState iblockstate) {
        byte b0 = 0;
        int i0 = b0 | ((BlockPlanks.EnumType) iblockstate.b(a)).a();

        i0 |= ((Integer) iblockstate.b(b)).intValue() << 3;
        return i0;
    }

    protected BlockState e() {
        return new BlockState(this, new IProperty[]{a, b});
    }

    static final class SwitchEnumType {

        static final int[] a = new int[BlockPlanks.EnumType.values().length];

        static {
            try {
                a[BlockPlanks.EnumType.SPRUCE.ordinal()] = 1;
            }
            catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                a[BlockPlanks.EnumType.BIRCH.ordinal()] = 2;
            }
            catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                a[BlockPlanks.EnumType.JUNGLE.ordinal()] = 3;
            }
            catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                a[BlockPlanks.EnumType.ACACIA.ordinal()] = 4;
            }
            catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

            try {
                a[BlockPlanks.EnumType.DARK_OAK.ordinal()] = 5;
            }
            catch (NoSuchFieldError nosuchfielderror4) {
                ;
            }

            try {
                a[BlockPlanks.EnumType.OAK.ordinal()] = 6;
            }
            catch (NoSuchFieldError nosuchfielderror5) {
                ;
            }

        }
    }
}
