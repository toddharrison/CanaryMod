package net.minecraft.block;

import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.hook.world.IgnitionHook;
import net.canarymod.hook.world.IgnitionHook.IgnitionCause;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import java.util.Random;

public class BlockStaticLiquid extends BlockLiquid {

    protected BlockStaticLiquid(Material material) {
        super(material);
        this.a(false);
        if (material == Material.i) {
            this.a(true);
        }
    }

    public void a(World world, BlockPos blockpos, IBlockState iblockstate, Block block) {
        if (!this.e(world, blockpos, iblockstate)) {
            this.f(world, blockpos, iblockstate);
        }
    }

    private void f(World world, BlockPos blockpos, IBlockState iblockstate) {
        BlockDynamicLiquid blockdynamicliquid = a(this.J);

        world.a(blockpos, blockdynamicliquid.P().a(b, iblockstate.b(b)), 2);
        world.a(blockpos, (Block)blockdynamicliquid, this.a(world));
    }

    public void b(World world, BlockPos blockpos, IBlockState iblockstate, Random random) {
        if (this.J == Material.i) {
            if (world.Q().b("doFireTick")) {
                int i0 = random.nextInt(3);

                // CanaryMod: Ignition
                CanaryBlock b = CanaryBlock.getPooledBlock(iblockstate, blockpos, world);
                b.setStatus((byte)1);
                if (new IgnitionHook(b, null, null, IgnitionCause.LAVA).call().isCanceled()) {
                    return;
                }
                //

                if (i0 > 0) {
                    BlockPos blockpos1 = blockpos;

                    for (int i1 = 0; i1 < i0; ++i1) {
                        blockpos1 = blockpos1.a(random.nextInt(3) - 1, 1, random.nextInt(3) - 1);
                        Block block = world.p(blockpos1).c();

                        if (block.J == Material.a) {
                            if (this.e(world, blockpos1)) {
                                world.a(blockpos1, Blocks.ab.P());
                                return;
                            }
                        }
                        else if (block.J.c()) {
                            return;
                        }
                    }
                }
                else {
                    for (int i2 = 0; i2 < 3; ++i2) {
                        BlockPos blockpos2 = blockpos.a(random.nextInt(3) - 1, 0, random.nextInt(3) - 1);

                        if (world.d(blockpos2.a()) && this.m(world, blockpos2)) {
                            world.a(blockpos2.a(), Blocks.ab.P());
                        }
                    }
                }
            }
        }
    }

    protected boolean e(World world, BlockPos blockpos) {
        EnumFacing[] aenumfacing = EnumFacing.values();
        int i0 = aenumfacing.length;

        for (int i1 = 0; i1 < i0; ++i1) {
            EnumFacing enumfacing = aenumfacing[i1];

            if (this.m(world, blockpos.a(enumfacing))) {
                return true;
            }
        }

        return false;
    }

    private boolean m(World world, BlockPos blockpos) {
        return world.p(blockpos).c().r().h();
    }
}
