package net.minecraft.block;

import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.hook.world.BlockPhysicsHook;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockFalling extends Block {

    public static boolean M;

    public BlockFalling() {
        super(Material.p);
        this.a(CreativeTabs.b);
    }

    public BlockFalling(Material material) {
        super(material);
    }

    public void c(World world, BlockPos blockpos, IBlockState iblockstate) {
        // CanaryMod: BlockPhysics
        if (world.getCanaryWorld() != null) {
            if (new BlockPhysicsHook(CanaryBlock.getPooledBlock(iblockstate, blockpos, world), true).call().isCanceled()) {
                return;
            }
        }
        //
        world.a(blockpos, (Block)this, this.a(world));
    }

    public void a(World world, BlockPos blockpos, IBlockState iblockstate, Block block) {
        // CanaryMod: BlockPhysics
        if (new BlockPhysicsHook(CanaryBlock.getPooledBlock(iblockstate, blockpos, world), true).call().isCanceled()) {
            return;
        }
        //
        world.a(blockpos, (Block)this, this.a(world));
    }

    public void b(World world, BlockPos blockpos, IBlockState iblockstate, Random random) {
        if (!world.D) {
            this.e(world, blockpos);
        }
    }

    private void e(World world, BlockPos blockpos) {
        if (d(world, blockpos.b()) && blockpos.o() >= 0) {
            byte b0 = 32;

            if (!M && world.a(blockpos.a(-b0, -b0, -b0), blockpos.a(b0, b0, b0))) {
                if (!world.D) {
                    EntityFallingBlock entityfallingblock = new EntityFallingBlock(world, (double)blockpos.n() + 0.5D, (double)blockpos.o(), (double)blockpos.p() + 0.5D, world.p(blockpos));

                    this.a(entityfallingblock);
                    world.d((Entity)entityfallingblock);
                }
            }
            else {
                world.g(blockpos);

                BlockPos blockpos1;

                for (blockpos1 = blockpos.b(); d(world, blockpos1) && blockpos1.o() > 0; blockpos1 = blockpos1.b()) {
                    ;
                }

                if (blockpos1.o() > 0) {
                    world.a(blockpos1.a(), this.P());
                }
            }
        }
    }

    protected void a(EntityFallingBlock entityfallingblock) {
    }

    public int a(World world) {
        return 2;
    }

    public static boolean d(World world, BlockPos blockpos) {
        Block block = world.p(blockpos).c();
        Material material = block.J;

        return block == Blocks.ab || material == Material.a || material == Material.h || material == Material.i;
    }

    public void a_(World world, BlockPos blockpos) {
    }
}
