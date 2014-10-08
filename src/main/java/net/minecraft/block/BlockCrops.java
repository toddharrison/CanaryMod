package net.minecraft.block;

import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.hook.world.BlockGrowHook;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class BlockCrops extends BlockBush implements IGrowable {

    public static final PropertyInteger a = PropertyInteger.a("age", 0, 7);

    protected BlockCrops() {
        this.j(this.L.b().a(a, Integer.valueOf(0)));
        this.a(true);
        float f0 = 0.5F;

        this.a(0.5F - f0, 0.0F, 0.5F - f0, 0.5F + f0, 0.25F, 0.5F + f0);
        this.a((CreativeTabs)null);
        this.c(0.0F);
        this.a(h);
        this.J();
    }

    protected boolean c(Block block) {
        return block == Blocks.ak;
    }

    public void b(World world, BlockPos blockpos, IBlockState iblockstate, Random random) {
        super.b(world, blockpos, iblockstate, random);
        if (world.l(blockpos.a()) >= 9) {
            int i0 = ((Integer)iblockstate.b(a)).intValue();

            if (i0 < 7) {
                float f0 = a(this, world, blockpos);

                if (random.nextInt((int)(25.0F / f0) + 1) == 0) {
                    // CanaryMod: BlockGrow
                    if (!new BlockGrowHook(new CanaryBlock(iblockstate, blockpos, world), new CanaryBlock(iblockstate.a(a, i0 + 1), blockpos, world)).call().isCanceled()) {
                        world.a(blockpos, iblockstate.a(a, Integer.valueOf(i0 + 1)), 2);
                    }
                    //
                }
            }
        }
    }

    public void g(World world, BlockPos blockpos, IBlockState iblockstate) {
        int i0 = ((Integer)iblockstate.b(a)).intValue() + MathHelper.a(world.s, 2, 5);

        if (i0 > 7) {
            i0 = 7;
        }

        world.a(blockpos, iblockstate.a(a, Integer.valueOf(i0)), 2);
    }

    protected static float a(Block block, World world, BlockPos blockpos) {
        float f0 = 1.0F;
        BlockPos blockpos1 = blockpos.b();

        for (int i0 = -1; i0 <= 1; ++i0) {
            for (int i1 = -1; i1 <= 1; ++i1) {
                float f1 = 0.0F;
                IBlockState iblockstate = world.p(blockpos1.a(i0, 0, i1));

                if (iblockstate.c() == Blocks.ak) {
                    f1 = 1.0F;
                    if (((Integer)iblockstate.b(BlockFarmland.a)).intValue() > 0) {
                        f1 = 3.0F;
                    }
                }

                if (i0 != 0 || i1 != 0) {
                    f1 /= 4.0F;
                }

                f0 += f1;
            }
        }

        BlockPos blockpos2 = blockpos.c();
        BlockPos blockpos3 = blockpos.d();
        BlockPos blockpos4 = blockpos.e();
        BlockPos blockpos5 = blockpos.f();
        boolean flag0 = block == world.p(blockpos4).c() || block == world.p(blockpos5).c();
        boolean flag1 = block == world.p(blockpos2).c() || block == world.p(blockpos3).c();

        if (flag0 && flag1) {
            f0 /= 2.0F;
        }
        else {
            boolean flag2 = block == world.p(blockpos4.c()).c() || block == world.p(blockpos5.c()).c() || block == world.p(blockpos5.d()).c() || block == world.p(blockpos4.d()).c();

            if (flag2) {
                f0 /= 2.0F;
            }
        }

        return f0;
    }

    public boolean f(World world, BlockPos blockpos, IBlockState iblockstate) {
        return (world.k(blockpos) >= 8 || world.i(blockpos)) && this.c(world.p(blockpos.b()).c());
    }

    protected Item j() {
        return Items.N;
    }

    protected Item l() {
        return Items.O;
    }

    public void a(World world, BlockPos blockpos, IBlockState iblockstate, float f0, int i0) {
        super.a(world, blockpos, iblockstate, f0, 0);
        if (!world.D) {
            int i1 = ((Integer)iblockstate.b(a)).intValue();

            if (i1 >= 7) {
                int i2 = 3 + i0;

                for (int i3 = 0; i3 < i2; ++i3) {
                    if (world.s.nextInt(15) <= i1) {
                        a(world, blockpos, new ItemStack(this.j(), 1, 0));
                    }
                }
            }
        }
    }

    public Item a(IBlockState iblockstate, Random random, int i0) {
        return ((Integer)iblockstate.b(a)).intValue() == 7 ? this.l() : this.j();
    }

    public boolean a(World world, BlockPos blockpos, IBlockState iblockstate, boolean flag0) {
        return ((Integer)iblockstate.b(a)).intValue() < 7;
    }

    public boolean a(World world, Random random, BlockPos blockpos, IBlockState iblockstate) {
        return true;
    }

    public void b(World world, Random random, BlockPos blockpos, IBlockState iblockstate) {
        this.g(world, blockpos, iblockstate);
    }

    public IBlockState a(int i0) {
        return this.P().a(a, Integer.valueOf(i0));
    }

    public int c(IBlockState iblockstate) {
        return ((Integer)iblockstate.b(a)).intValue();
    }

    protected BlockState e() {
        return new BlockState(this, new IProperty[]{ a });
    }
}
