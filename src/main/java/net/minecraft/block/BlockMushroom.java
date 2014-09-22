package net.minecraft.block;

import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.hook.world.BlockGrowHook;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenBigMushroom;

import java.util.Random;


public class BlockMushroom extends BlockBush implements IGrowable {

    protected BlockMushroom() {
        float f0 = 0.2F;

        this.a(0.5F - f0, 0.0F, 0.5F - f0, 0.5F + f0, f0 * 2.0F, 0.5F + f0);
        this.a(true);
    }

    public void a(World world, int i0, int i1, int i2, Random random) {
        if (random.nextInt(25) == 0) {
            // CanaryMod: Grab the original stuff
            CanaryBlock original = (CanaryBlock) world.getCanaryWorld().getBlockAt(i0, i1, i2);
            //
            byte b0 = 4;
            int i3 = 5;

            int i4;
            int i5;
            int i6;

            for (i4 = i0 - b0; i4 <= i0 + b0; ++i4) {
                for (i5 = i2 - b0; i5 <= i2 + b0; ++i5) {
                    for (i6 = i1 - 1; i6 <= i1 + 1; ++i6) {
                        if (world.a(i4, i6, i5) == this) {
                            --i3;
                            if (i3 <= 0) {
                                return;
                            }
                        }
                    }
                }
            }

            i4 = i0 + random.nextInt(3) - 1;
            i5 = i1 + random.nextInt(2) - random.nextInt(2);
            i6 = i2 + random.nextInt(3) - 1;

            for (int i7 = 0; i7 < 4; ++i7) {
                if (world.c(i4, i5, i6) && this.j(world, i4, i5, i6)) {
                    i0 = i4;
                    i1 = i5;
                    i2 = i6;
                }

                i4 = i0 + random.nextInt(3) - 1;
                i5 = i1 + random.nextInt(2) - random.nextInt(2);
                i6 = i2 + random.nextInt(3) - 1;
            }

            if (world.c(i4, i5, i6) && this.j(world, i4, i5, i6)) {
                // CanaryMod: BlockGrow
                CanaryBlock growth = new CanaryBlock(this, (short) 0, i0, i1 + 1, i2, world.getCanaryWorld());
                BlockGrowHook blockGrowHook = (BlockGrowHook) new BlockGrowHook(original, growth).call();
                if (!blockGrowHook.isCanceled()) {
                    world.d(i4, i5, i6, this, 0, 2);
                }
                //
            }
        }
    }

    public boolean c(World world, int i0, int i1, int i2) {
        return super.c(world, i0, i1, i2) && this.j(world, i0, i1, i2);
    }

    protected boolean a(Block block) {
        return block.j();
    }

    public boolean j(World world, int i0, int i1, int i2) {
        if (i1 >= 0 && i1 < 256) {
            Block block = world.a(i0, i1 - 1, i2);

            return block == Blocks.bh || block == Blocks.d && world.e(i0, i1 - 1, i2) == 2 || world.j(i0, i1, i2) < 13 && this.a(block);
        }
        else {
            return false;
        }
    }

    public boolean c(World world, int i0, int i1, int i2, Random random) {
        int i3 = world.e(i0, i1, i2);

        world.f(i0, i1, i2);
        WorldGenBigMushroom worldgenbigmushroom = null;

        if (this == Blocks.P) {
            worldgenbigmushroom = new WorldGenBigMushroom(0);
        }
        else if (this == Blocks.Q) {
            worldgenbigmushroom = new WorldGenBigMushroom(1);
        }

        if (worldgenbigmushroom != null && worldgenbigmushroom.a(world, random, i0, i1, i2)) {
            return true;
        }
        else {
            world.d(i0, i1, i2, this, i3, 3);
            return false;
        }
    }

    public boolean a(World world, int i0, int i1, int i2, boolean flag0) {
        return true;
    }

    public boolean a(World world, Random random, int i0, int i1, int i2) {
        return (double) random.nextFloat() < 0.4D;
    }

    public void b(World world, Random random, int i0, int i1, int i2) {
        this.c(world, i0, i1, i2, random);
    }
}
