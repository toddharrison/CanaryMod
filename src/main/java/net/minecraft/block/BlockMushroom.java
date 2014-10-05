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
            int i0 = 5;
            boolean flag0 = true;
            Iterator iterator = BlockPos.b(blockpos.a(-4, -1, -4), blockpos.a(4, 1, 4)).iterator();

            while (iterator.hasNext()) {
                BlockPos blockpos1 = (BlockPos) iterator.next();

                if (world.p(blockpos1).c() == this) {
                    --i0;
                    if (i0 <= 0) {
                        return;
                    }
                }
            }

            BlockPos blockpos2 = blockpos.a(random.nextInt(3) - 1, random.nextInt(2) - random.nextInt(2), random.nextInt(3) - 1);

            for (int i1 = 0; i1 < 4; ++i1) {
                if (world.d(blockpos2) && this.f(world, blockpos2, this.P())) {
                    blockpos = blockpos2;
                }

                blockpos2 = blockpos.a(random.nextInt(3) - 1, random.nextInt(2) - random.nextInt(2), random.nextInt(3) - 1);
            }

            if (world.d(blockpos2) && this.f(world, blockpos2, this.P())) {
                // CanaryMod: BlockGrow
                CanaryBlock growth = new CanaryBlock(this, (short) 0, i0, i1 + 1, i2, world.getCanaryWorld());
                BlockGrowHook blockGrowHook = (BlockGrowHook) new BlockGrowHook(original, growth).call();
                if (!blockGrowHook.isCanceled()) {
                    world.a(blockpos2, this.P(), 2);
                }
                //
            }
        }
    }

    public boolean c(World world, BlockPos blockpos) {
        return super.c(world, blockpos) && this.f(world, blockpos, this.P());
    }

    protected boolean c(Block block) {
        return block.m();
    }

    public boolean f(World world, BlockPos blockpos, IBlockState iblockstate) {
        if (blockpos.o() >= 0 && blockpos.o() < 256) {
            IBlockState iblockstate1 = world.p(blockpos.b());

            return iblockstate1.c() == Blocks.bw ? true : (iblockstate1.c() == Blocks.d && iblockstate1.b(BlockDirt.a) == BlockDirt.DirtType.PODZOL ? true : world.k(blockpos) < 13 && this.c(iblockstate1.c()));
        }
        else {
            return false;
        }
    }

    public boolean d(World world, BlockPos blockpos, IBlockState iblockstate, Random random) {
        world.g(blockpos);
        WorldGenBigMushroom worldgenbigmushroom = null;

        if (this == Blocks.P) {
            worldgenbigmushroom = new WorldGenBigMushroom(0);
        }
        else if (this == Blocks.Q) {
            worldgenbigmushroom = new WorldGenBigMushroom(1);
        }

        if (worldgenbigmushroom != null && worldgenbigmushroom.b(world, random, blockpos)) {
            return true;
        }
        else {
            world.a(blockpos, iblockstate, 3);
            return false;
        }
    }

    public boolean a(World world, BlockPos blockpos, IBlockState iblockstate, boolean flag0) {
        return true;
    }

    public boolean a(World world, Random random, BlockPos blockpos, IBlockState iblockstate) {
        return (double) random.nextFloat() < 0.4D;
    }

    public void b(World world, Random random, BlockPos blockpos, IBlockState iblockstate) {
        this.d(world, blockpos, iblockstate, random);
    }
}
