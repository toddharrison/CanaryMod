package net.minecraft.block;


import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.hook.world.BlockGrowHook;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;


public class BlockCrops extends BlockBush implements IGrowable {

    protected BlockCrops() {
        this.a(true);
        float f0 = 0.5F;

        this.a(0.5F - f0, 0.0F, 0.5F - f0, 0.5F + f0, 0.25F, 0.5F + f0);
        this.a((CreativeTabs) null);
        this.c(0.0F);
        this.a(h);
        this.H();
    }

    protected boolean a(Block block) {
        return block == Blocks.ak;
    }

    public void a(World world, int i0, int i1, int i2, Random random) {
        super.a(world, i0, i1, i2, random);
        if (world.k(i0, i1 + 1, i2) >= 9) {
            int i3 = world.e(i0, i1, i2);

            if (i3 < 7) {
                float f0 = this.n(world, i0, i1, i2);

                if (random.nextInt((int) (25.0F / f0) + 1) == 0) {
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
            }
        }

    }

    public void m(World world, int i0, int i1, int i2) {
        int i3 = world.e(i0, i1, i2) + MathHelper.a(world.s, 2, 5);

        if (i3 > 7) {
            i3 = 7;
        }

        world.a(i0, i1, i2, i3, 2);
    }

    private float n(World world, int i0, int i1, int i2) {
        float f0 = 1.0F;
        Block block = world.a(i0, i1, i2 - 1);
        Block block1 = world.a(i0, i1, i2 + 1);
        Block block2 = world.a(i0 - 1, i1, i2);
        Block block3 = world.a(i0 + 1, i1, i2);
        Block block4 = world.a(i0 - 1, i1, i2 - 1);
        Block block5 = world.a(i0 + 1, i1, i2 - 1);
        Block block6 = world.a(i0 + 1, i1, i2 + 1);
        Block block7 = world.a(i0 - 1, i1, i2 + 1);
        boolean flag0 = block2 == this || block3 == this;
        boolean flag1 = block == this || block1 == this;
        boolean flag2 = block4 == this || block5 == this || block6 == this || block7 == this;

        for (int i3 = i0 - 1; i3 <= i0 + 1; ++i3) {
            for (int i4 = i2 - 1; i4 <= i2 + 1; ++i4) {
                float f1 = 0.0F;

                if (world.a(i3, i1 - 1, i4) == Blocks.ak) {
                    f1 = 1.0F;
                    if (world.e(i3, i1 - 1, i4) > 0) {
                        f1 = 3.0F;
                    }
                }

                if (i3 != i0 || i4 != i2) {
                    f1 /= 4.0F;
                }

                f0 += f1;
            }
        }

        if (flag2 || flag0 && flag1) {
            f0 /= 2.0F;
        }

        return f0;
    }

    public int b() {
        return 6;
    }

    protected Item i() {
        return Items.N;
    }

    protected Item P() {
        return Items.O;
    }

    public void a(World world, int i0, int i1, int i2, int i3, float f0, int i4) {
        super.a(world, i0, i1, i2, i3, f0, 0);
        if (!world.E) {
            if (i3 >= 7) {
                int i5 = 3 + i4;

                for (int i6 = 0; i6 < i5; ++i6) {
                    if (world.s.nextInt(15) <= i3) {
                        this.a(world, i0, i1, i2, new ItemStack(this.i(), 1, 0));
                    }
                }
            }

        }
    }

    public Item a(int i0, Random random, int i1) {
        return i0 == 7 ? this.P() : this.i();
    }

    public int a(Random random) {
        return 1;
    }

    public boolean a(World world, int i0, int i1, int i2, boolean flag0) {
        return world.e(i0, i1, i2) != 7;
    }

    public boolean a(World world, Random random, int i0, int i1, int i2) {
        return true;
    }

    public void b(World world, Random random, int i0, int i1, int i2) {
        this.m(world, i0, i1, i2);
    }
}
