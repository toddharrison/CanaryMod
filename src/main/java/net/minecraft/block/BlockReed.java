package net.minecraft.block;

import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.hook.world.BlockGrowHook;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.Random;

public class BlockReed extends Block {

    public static final PropertyInteger a = PropertyInteger.a("age", 0, 15);

    protected BlockReed() {
        super(Material.k);
        this.j(this.L.b().a(a, Integer.valueOf(0)));
        float f0 = 0.375F;

        this.a(0.5F - f0, 0.0F, 0.5F - f0, 0.5F + f0, 1.0F, 0.5F + f0);
        this.a(true);
    }

    public void b(World world, BlockPos blockpos, IBlockState iblockstate, Random random) {
        if (world.p(blockpos.b()).c() == Blocks.aM || this.e(world, blockpos, iblockstate)) {
            if (world.d(blockpos.a())) {
                int i0;

                for (i0 = 1; world.p(blockpos.c(i0)).c() == this; ++i0) {
                    ;
                }

                if (i0 < 3) {
                    int i1 = ((Integer)iblockstate.b(a)).intValue();
                    // CanaryMod: Grab the original block
                    CanaryBlock original = CanaryBlock.getPooledBlock(iblockstate, blockpos, world);
                    //

                    if (i1 == 15) {
                        // Call hook for spawning new
                        if (!new BlockGrowHook(original, CanaryBlock.getPooledBlock(this.P(), blockpos, world)).isCanceled()) {
                            world.a(blockpos.a(), this.P());
                            world.a(blockpos, iblockstate.a(a, Integer.valueOf(0)), 4);
                        }
                        //
                    }
                    else {
                        // Call hook for just growing in place
                        if (!new BlockGrowHook(original, CanaryBlock.getPooledBlock(iblockstate.a(a, i1 + 1), blockpos, world)).isCanceled()) {
                            world.a(blockpos, iblockstate.a(a, Integer.valueOf(i1 + 1)), 4);
                        }
                        //
                    }
                }
            }
        }
    }

    public boolean c(World world, BlockPos blockpos) {
        Block block = world.p(blockpos.b()).c();

        if (block == this) {
            return true;
        }
        else if (block != Blocks.c && block != Blocks.d && block != Blocks.m) {
            return false;
        }
        else {
            Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();

            EnumFacing enumfacing;

            do {
                if (!iterator.hasNext()) {
                    return false;
                }

                enumfacing = (EnumFacing)iterator.next();
            }
            while (world.p(blockpos.a(enumfacing).b()).c().r() != Material.h);

            return true;
        }
    }

    public void a(World world, BlockPos blockpos, IBlockState iblockstate, Block block) {
        this.e(world, blockpos, iblockstate);
    }

    protected final boolean e(World world, BlockPos blockpos, IBlockState iblockstate) {
        if (this.d(world, blockpos)) {
            return true;
        }
        else {
            this.b(world, blockpos, iblockstate, 0);
            world.g(blockpos);
            return false;
        }
    }

    public boolean d(World world, BlockPos blockpos) {
        return this.c(world, blockpos);
    }

    public AxisAlignedBB a(World world, BlockPos blockpos, IBlockState iblockstate) {
        return null;
    }

    public Item a(IBlockState iblockstate, Random random, int i0) {
        return Items.aJ;
    }

    public boolean c() {
        return false;
    }

    public boolean d() {
        return false;
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
