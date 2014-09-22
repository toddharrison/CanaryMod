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

    protected BlockNetherWart() {
        this.a(true);
        float f0 = 0.5F;

        this.a(0.5F - f0, 0.0F, 0.5F - f0, 0.5F + f0, 0.25F, 0.5F + f0);
        this.a((CreativeTabs) null);
    }

    protected boolean a(Block block) {
        return block == Blocks.aM;
    }

    public boolean j(World world, int i0, int i1, int i2) {
        return this.a(world.a(i0, i1 - 1, i2));
    }

    public void a(World world, int i0, int i1, int i2, Random random) {
        int i3 = world.e(i0, i1, i2);

        if (i3 < 3 && random.nextInt(10) == 0) {
            ++i3;
            // CanaryMod: BlockGrow
            CanaryBlock original = (CanaryBlock) world.getCanaryWorld().getBlockAt(i0, i1, i2);
            CanaryBlock growth = (CanaryBlock) world.getCanaryWorld().getBlockAt(i0, i1, i2);
            growth.setData((short) i3);
            BlockGrowHook blockGrowHook = (BlockGrowHook) new BlockGrowHook(original, growth).call();
            if (!blockGrowHook.isCanceled()) {
                world.a(i0, i1, i2, i3, 2);
            }
            //
        }

        super.a(world, i0, i1, i2, random);
    }

    public int b() {
        return 6;
    }

    public void a(World world, int i0, int i1, int i2, int i3, float f0, int i4) {
        if (!world.E) {
            int i5 = 1;

            if (i3 >= 3) {
                i5 = 2 + world.s.nextInt(3);
                if (i4 > 0) {
                    i5 += world.s.nextInt(i4 + 1);
                }
            }

            for (int i6 = 0; i6 < i5; ++i6) {
                this.a(world, i0, i1, i2, new ItemStack(Items.bm));
            }

        }
    }

    public Item a(int i0, Random random, int i1) {
        return null;
    }

    public int a(Random random) {
        return 0;
    }
}
