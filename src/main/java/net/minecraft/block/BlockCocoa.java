package net.minecraft.block;

import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.hook.world.BlockGrowHook;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockCocoa extends BlockDirectional implements IGrowable {

    public static final PropertyInteger a = PropertyInteger.a("age", 0, 2);

    public BlockCocoa() {
        super(Material.k);
        this.j(this.L.b().a(N, EnumFacing.NORTH).a(a, Integer.valueOf(0)));
        this.a(true);
    }

    public void b(World world, BlockPos blockpos, IBlockState iblockstate, Random random) {
        if (!this.e(world, blockpos, iblockstate)) {
            this.f(world, blockpos, iblockstate);
        }
        else if (world.s.nextInt(5) == 0) {
            int i0 = ((Integer)iblockstate.b(a)).intValue();

            if (i0 < 2) {
                ++i0;
                // CanaryMod: BlockGrow
                if (!new BlockGrowHook(CanaryBlock.getPooledBlock(iblockstate, blockpos, world), CanaryBlock.getPooledBlock(iblockstate.a(a, i0), blockpos, world)).call().isCanceled()) {
                    world.a(blockpos, iblockstate.a(a, Integer.valueOf(i0)), 2);
                }
                //
            }
        }
    }

    public boolean e(World world, BlockPos blockpos, IBlockState iblockstate) {
        blockpos = blockpos.a((EnumFacing)iblockstate.b(N));
        IBlockState iblockstate1 = world.p(blockpos);

        return iblockstate1.c() == Blocks.r && iblockstate1.b(BlockPlanks.a) == BlockPlanks.EnumType.JUNGLE;
    }

    public boolean d() {
        return false;
    }

    public boolean c() {
        return false;
    }

    public AxisAlignedBB a(World world, BlockPos blockpos, IBlockState iblockstate) {
        this.a(world, blockpos);
        return super.a(world, blockpos, iblockstate);
    }

    public void a(IBlockAccess iblockaccess, BlockPos blockpos) {
        IBlockState iblockstate = iblockaccess.p(blockpos);
        EnumFacing enumfacing = (EnumFacing)iblockstate.b(N);
        int i0 = ((Integer)iblockstate.b(a)).intValue();
        int i1 = 4 + i0 * 2;
        int i2 = 5 + i0 * 2;
        float f0 = (float)i1 / 2.0F;

        switch (BlockCocoa.SwitchEnumFacing.a[enumfacing.ordinal()]) {
            case 1:
                this.a((8.0F - f0) / 16.0F, (12.0F - (float)i2) / 16.0F, (15.0F - (float)i1) / 16.0F, (8.0F + f0) / 16.0F, 0.75F, 0.9375F);
                break;

            case 2:
                this.a((8.0F - f0) / 16.0F, (12.0F - (float)i2) / 16.0F, 0.0625F, (8.0F + f0) / 16.0F, 0.75F, (1.0F + (float)i1) / 16.0F);
                break;

            case 3:
                this.a(0.0625F, (12.0F - (float)i2) / 16.0F, (8.0F - f0) / 16.0F, (1.0F + (float)i1) / 16.0F, 0.75F, (8.0F + f0) / 16.0F);
                break;

            case 4:
                this.a((15.0F - (float)i1) / 16.0F, (12.0F - (float)i2) / 16.0F, (8.0F - f0) / 16.0F, 0.9375F, 0.75F, (8.0F + f0) / 16.0F);
        }
    }

    public void a(World world, BlockPos blockpos, IBlockState iblockstate, EntityLivingBase entitylivingbase, ItemStack itemstack) {
        EnumFacing enumfacing = EnumFacing.a((double)entitylivingbase.y);

        world.a(blockpos, iblockstate.a(N, enumfacing), 2);
    }

    public IBlockState a(World world, BlockPos blockpos, EnumFacing enumfacing, float f0, float f1, float f2, int i0, EntityLivingBase entitylivingbase) {
        if (!enumfacing.k().c()) {
            enumfacing = EnumFacing.NORTH;
        }

        return this.P().a(N, enumfacing.d()).a(a, Integer.valueOf(0));
    }

    public void a(World world, BlockPos blockpos, IBlockState iblockstate, Block block) {
        if (!this.e(world, blockpos, iblockstate)) {
            this.f(world, blockpos, iblockstate);
        }
    }

    private void f(World world, BlockPos blockpos, IBlockState iblockstate) {
        world.a(blockpos, Blocks.a.P(), 3);
        this.b(world, blockpos, iblockstate, 0);
    }

    public void a(World world, BlockPos blockpos, IBlockState iblockstate, float f0, int i0) {
        int i1 = ((Integer)iblockstate.b(a)).intValue();
        byte b0 = 1;

        if (i1 >= 2) {
            b0 = 3;
        }

        for (int i2 = 0; i2 < b0; ++i2) {
            a(world, blockpos, new ItemStack(Items.aW, 1, EnumDyeColor.BROWN.b()));
        }
    }

    public int j(World world, BlockPos blockpos) {
        return EnumDyeColor.BROWN.b();
    }

    public boolean a(World world, BlockPos blockpos, IBlockState iblockstate, boolean flag0) {
        return ((Integer)iblockstate.b(a)).intValue() < 2;
    }

    public boolean a(World world, Random random, BlockPos blockpos, IBlockState iblockstate) {
        return true;
    }

    public void b(World world, Random random, BlockPos blockpos, IBlockState iblockstate) {
        world.a(blockpos, iblockstate.a(a, Integer.valueOf(((Integer)iblockstate.b(a)).intValue() + 1)), 2);
    }

    public IBlockState a(int i0) {
        return this.P().a(N, EnumFacing.b(i0)).a(a, Integer.valueOf((i0 & 15) >> 2));
    }

    public int c(IBlockState iblockstate) {
        byte b0 = 0;
        int i0 = b0 | ((EnumFacing)iblockstate.b(N)).b();

        i0 |= ((Integer)iblockstate.b(a)).intValue() << 2;
        return i0;
    }

    protected BlockState e() {
        return new BlockState(this, new IProperty[]{ N, a });
    }

    static final class SwitchEnumFacing {

        static final int[] a = new int[EnumFacing.values().length];

        static {
            try {
                a[EnumFacing.SOUTH.ordinal()] = 1;
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
                a[EnumFacing.WEST.ordinal()] = 3;
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
        }
    }
}
