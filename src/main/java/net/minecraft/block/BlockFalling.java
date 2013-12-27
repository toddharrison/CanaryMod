package net.minecraft.block;


import net.canarymod.hook.world.BlockPhysicsHook;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.init.Blocks;
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

    public void b(World world, int i0, int i1, int i2) {
        // CanaryMod: BlockPhysics
        if (world.getCanaryWorld() != null) {
            BlockPhysicsHook hook = (BlockPhysicsHook) new BlockPhysicsHook(world.getCanaryWorld().getBlockAt(i0, i1, i2), true).call();
            if (hook.isCanceled()) {
                return;
            }
        }
        //
        world.a(i0, i1, i2, this, this.a(world));
    }

    public void a(World world, int i0, int i1, int i2, Block block) {
        // CanaryMod: BlockPhysics
        BlockPhysicsHook hook = (BlockPhysicsHook) new BlockPhysicsHook(world.getCanaryWorld().getBlockAt(i0, i1, i2), false).call();
        if (hook.isCanceled()) {
            return;
        }
        //
        world.a(i0, i1, i2, this, this.a(world));
    }

    public void a(World world, int i0, int i1, int i2, Random random) {
        if (!world.E) {
            this.m(world, i0, i1, i2);
        }
    }

    private void m(World world, int i0, int i1, int i2) {
        if (e(world, i0, i1 - 1, i2) && i1 >= 0) {
            byte b0 = 32;

            if (!M && world.b(i0 - b0, i1 - b0, i2 - b0, i0 + b0, i1 + b0, i2 + b0)) {
                if (!world.E) {
                    EntityFallingBlock entityfallingblock = new EntityFallingBlock(world, (double) ((float) i0 + 0.5F), (double) ((float) i1 + 0.5F), (double) ((float) i2 + 0.5F), this, world.e(i0, i1, i2));

                    this.a(entityfallingblock);
                    world.d((Entity) entityfallingblock);
                }
            }
            else {
                world.f(i0, i1, i2);

                while (e(world, i0, i1 - 1, i2) && i1 > 0) {
                    --i1;
                }

                if (i1 > 0) {
                    world.b(i0, i1, i2, (Block) this);
                }
            }
        }
    }

    protected void a(EntityFallingBlock entityfallingblock) {
    }

    public int a(World world) {
        return 2;
    }

    public static boolean e(World world, int i0, int i1, int i2) {
        Block block = world.a(i0, i1, i2);

        if (block.J == Material.a) {
            return true;
        }
        else if (block == Blocks.ab) {
            return true;
        }
        else {
            Material material = block.J;

            return material == Material.h ? true : material == Material.i;
        }
    }

    public void a(World world, int i0, int i1, int i2, int i3) {
    }
}
