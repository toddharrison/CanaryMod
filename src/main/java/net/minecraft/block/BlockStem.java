package net.minecraft.block;


import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.hook.world.BlockGrowHook;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;


public class BlockStem extends BlockBush implements IGrowable {

    private final Block a;

    protected BlockStem(Block block) {
        this.a = block;
        this.a(true);
        float f0 = 0.125F;

        this.a(0.5F - f0, 0.0F, 0.5F - f0, 0.5F + f0, 0.25F, 0.5F + f0);
        this.a((CreativeTabs) null);
    }

    protected boolean a(Block block) {
        return block == Blocks.ak;
    }

    public void a(World world, int i0, int i1, int i2, Random random) {
        super.a(world, i0, i1, i2, random);
        if (world.k(i0, i1 + 1, i2) >= 9) {
            float f0 = this.n(world, i0, i1, i2);

            if (random.nextInt((int) (25.0F / f0) + 1) == 0) {
                int i3 = world.e(i0, i1, i2);

                // CanaryMod: Grab the original stuff
                CanaryBlock original = (CanaryBlock) world.getCanaryWorld().getBlockAt(i0, i1, i2);
                CanaryBlock growth;
                BlockGrowHook blockGrowHook;
                //
                if (i3 < 7) {
                    ++i3;
                    // Growth is original with new data
                    growth = (CanaryBlock) world.getCanaryWorld().getBlockAt(i0, i1, i2);
                    growth.setData((short) i3);
                    blockGrowHook = (BlockGrowHook) new BlockGrowHook(original, growth).call();
                    if (!blockGrowHook.isCanceled()) {
                        world.a(i0, i1, i2, i3, 2);
                    }
                    //
                }
                else {
                    if (world.a(i0 - 1, i1, i2) == this.a) {
                        return;
                    }

                    if (world.a(i0 + 1, i1, i2) == this.a) {
                        return;
                    }

                    if (world.a(i0, i1, i2 - 1) == this.a) {
                        return;
                    }

                    if (world.a(i0, i1, i2 + 1) == this.a) {
                        return;
                    }

                    int i4 = random.nextInt(4);
                    int i5 = i0;
                    int i6 = i2;

                    if (i4 == 0) {
                        i5 = i0 - 1;
                    }

                    if (i4 == 1) {
                        ++i5;
                    }

                    if (i4 == 2) {
                        i6 = i2 - 1;
                    }

                    if (i4 == 3) {
                        ++i6;
                    }

                    Block block = world.a(i5, i1 - 1, i6);

                    if (world.a(i5, i1, i6).J == Material.a && (block == Blocks.ak || block == Blocks.d || block == Blocks.c)) {
                        // A Melon/Pumpkin has spawned
                        growth = new CanaryBlock(this.a, (short) 0, i5, i1, i6, world.getCanaryWorld());
                        blockGrowHook = (BlockGrowHook) new BlockGrowHook(original, growth);
                        if (!blockGrowHook.isCanceled()) {
                            world.b(i5, i1, i6, this.a);
                        }
                        //
                    }
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
                Block block8 = world.a(i3, i1 - 1, i4);
                float f1 = 0.0F;

                if (block8 == Blocks.ak) {
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

    public void g() {
        float f0 = 0.125F;

        this.a(0.5F - f0, 0.0F, 0.5F - f0, 0.5F + f0, 0.25F, 0.5F + f0);
    }

    public void a(IBlockAccess iblockaccess, int i0, int i1, int i2) {
        this.F = (double) ((float) (iblockaccess.e(i0, i1, i2) * 2 + 2) / 16.0F);
        float f0 = 0.125F;

        this.a(0.5F - f0, 0.0F, 0.5F - f0, 0.5F + f0, (float) this.F, 0.5F + f0);
    }

    public int b() {
        return 19;
    }

    public void a(World world, int i0, int i1, int i2, int i3, float f0, int i4) {
        super.a(world, i0, i1, i2, i3, f0, i4);
        if (!world.E) {
            Item item = null;

            if (this.a == Blocks.aK) {
                item = Items.bb;
            }

            if (this.a == Blocks.ba) {
                item = Items.bc;
            }

            for (int i5 = 0; i5 < 3; ++i5) {
                if (world.s.nextInt(15) <= i3) {
                    this.a(world, i0, i1, i2, new ItemStack(item));
                }
            }

        }
    }

    public Item a(int i0, Random random, int i1) {
        return null;
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
