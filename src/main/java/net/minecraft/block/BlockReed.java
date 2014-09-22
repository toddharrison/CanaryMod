package net.minecraft.block;


import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.hook.world.BlockGrowHook;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.Random;


public class BlockReed extends Block {

    protected BlockReed() {
        super(Material.k);
        float f0 = 0.375F;

        this.a(0.5F - f0, 0.0F, 0.5F - f0, 0.5F + f0, 1.0F, 0.5F + f0);
        this.a(true);
    }

    public void a(World world, int i0, int i1, int i2, Random random) {
        if (world.a(i0, i1 - 1, i2) == Blocks.aH || this.e(world, i0, i1, i2)) {
            if (world.c(i0, i1 + 1, i2)) {
                int i3;

                for (i3 = 1; world.a(i0, i1 - i3, i2) == this; ++i3) {
                    ;
                }

                if (i3 < 3) {
                    int i4 = world.e(i0, i1, i2);
                    // CanaryMod: Grab the original stuff
                    CanaryBlock original = (CanaryBlock) world.getCanaryWorld().getBlockAt(i0, i1, i2);
                    CanaryBlock growth = new CanaryBlock(this, (short) 0, i0, i1 + 1, i2, world.getCanaryWorld());
                    BlockGrowHook blockGrowHook = new BlockGrowHook(original, growth);
                    //
                    if (i4 == 15) {
                        // Call hook for spawning new
                        blockGrowHook.call();
                        if (!blockGrowHook.isCanceled()) {
                            world.b(i0, i1 + 1, i2, (Block) this);
                            world.a(i0, i1, i2, 0, 4);
                        }
                        //
                    }
                    else {
                        // Call hook for just growing in place
                        growth = (CanaryBlock) world.getCanaryWorld().getBlockAt(i0, i1, i2);
                        growth.setData((short) (i4 + 1));
                        blockGrowHook = (BlockGrowHook) new BlockGrowHook(original, growth).call();
                        if (!blockGrowHook.isCanceled()) {
                            world.a(i0, i1, i2, i4 + 1, 4);
                        }
                        //
                    }
                }
            }

        }
    }

    public boolean c(World world, int i0, int i1, int i2) {
        Block block = world.a(i0, i1 - 1, i2);

        return block == this ? true : (block != Blocks.c && block != Blocks.d && block != Blocks.m ? false : (world.a(i0 - 1, i1 - 1, i2).o() == Material.h ? true : (world.a(i0 + 1, i1 - 1, i2).o() == Material.h ? true : (world.a(i0, i1 - 1, i2 - 1).o() == Material.h ? true : world.a(i0, i1 - 1, i2 + 1).o() == Material.h))));
    }

    public void a(World world, int i0, int i1, int i2, Block block) {
        this.e(world, i0, i1, i2);
    }

    protected final boolean e(World world, int i0, int i1, int i2) {
        if (!this.j(world, i0, i1, i2)) {
            this.b(world, i0, i1, i2, world.e(i0, i1, i2), 0);
            world.f(i0, i1, i2);
            return false;
        }
        else {
            return true;
        }
    }

    public boolean j(World world, int i0, int i1, int i2) {
        return this.c(world, i0, i1, i2);
    }

    public AxisAlignedBB a(World world, int i0, int i1, int i2) {
        return null;
    }

    public Item a(int i0, Random random, int i1) {
        return Items.aE;
    }

    public boolean c() {
        return false;
    }

    public boolean d() {
        return false;
    }

    public int b() {
        return 1;
    }
}
