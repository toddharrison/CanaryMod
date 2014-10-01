package net.minecraft.block;


import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.hook.world.BlockGrowHook;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Random;


public class BlockNetherWart extends BlockBush {

    public static final PropertyInteger a = PropertyInteger.a("age", 0, 3);
   
    protected BlockNetherWart() {
        this.j(this.L.b().a(a, Integer.valueOf(0)));
        this.a(true);
        float f0 = 0.5F;

        this.a(0.5F - f0, 0.0F, 0.5F - f0, 0.5F + f0, 0.25F, 0.5F + f0);
        this.a((CreativeTabs) null);
    }

    protected boolean c(Block block) {
        return block == Blocks.aW;
    }

    public boolean f(World world, BlockPos blockpos, IBlockState iblockstate) {
        return this.c(world.p(blockpos.b()).c());
    }

    public void b(World world, BlockPos blockpos, IBlockState iblockstate, Random random) {
        int i0 = ((Integer) iblockstate.b(a)).intValue();

        if (i0 < 3 && random.nextInt(10) == 0) {
            iblockstate = iblockstate.a(a, Integer.valueOf(i0 + 1));
            // CanaryMod: BlockGrow
            CanaryBlock original = (CanaryBlock) world.getCanaryWorld().getBlockAt(i0, i1, i2);
            CanaryBlock growth = (CanaryBlock) world.getCanaryWorld().getBlockAt(i0, i1, i2);
            growth.setData((short) i3);
            BlockGrowHook blockGrowHook = (BlockGrowHook) new BlockGrowHook(original, growth).call();
            if (!blockGrowHook.isCanceled()) {
                world.a(blockpos, iblockstate, 2);
            }
            //
        }

        super.b(world, blockpos, iblockstate, random);
    }

    public void a(World world, BlockPos blockpos, IBlockState iblockstate, float f0, int i0) {
        if (!world.D) {
            int i1 = 1;

            if (((Integer) iblockstate.b(a)).intValue() >= 3) {
                i1 = 2 + world.s.nextInt(3);
                if (i0 > 0) {
                    i1 += world.s.nextInt(i0 + 1);
                }
            }

            for (int i2 = 0; i2 < i1; ++i2) {
                a(world, blockpos, new ItemStack(Items.by));
            }

        }
    }

    public Item a(IBlockState iblockstate, Random random, int i0) {
        return null;
    }

    public int a(Random random) {
        return 0;
    }

    public IBlockState a(int i0) {
        return this.P().a(a, Integer.valueOf(i0));
    }

    public int c(IBlockState iblockstate) {
        return ((Integer) iblockstate.b(a)).intValue();
    }

    protected BlockState e() {
        return new BlockState(this, new IProperty[] { a});
    }

}
