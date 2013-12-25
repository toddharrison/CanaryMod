package net.minecraft.item;

import net.canarymod.api.entity.living.humanoid.Player;
import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.hook.player.BlockDestroyHook;
import net.canarymod.hook.player.BlockPlaceHook;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemBucket extends Item {

    private Block a;

    public ItemBucket(Block block) {
        this.h = 1;
        this.a = block;
        this.a(CreativeTabs.f);
    }

    public ItemStack a(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        boolean flag0 = this.a == Blocks.a;
        MovingObjectPosition movingobjectposition = this.a(world, entityplayer, flag0);

        if (movingobjectposition == null) {
            return itemstack;
        }
        else {
            if (movingobjectposition.a == MovingObjectPosition.MovingObjectType.BLOCK) {
                int i0 = movingobjectposition.b;
                int i1 = movingobjectposition.c;
                int i2 = movingobjectposition.d;

                if (!world.a(entityplayer, i0, i1, i2)) {
                    return itemstack;
                }

                // CanaryMod: BlockDestoryHook
                CanaryBlock clicked = (CanaryBlock) world.getCanaryWorld().getBlockAt(i0, i1, i2);
                BlockDestroyHook hook = new BlockDestroyHook(((EntityPlayerMP) entityplayer).getPlayer(), clicked);
                //

                if (flag0) {
                    if (!entityplayer.a(i0, i1, i2, movingobjectposition.e, itemstack)) {
                        return itemstack;
                    }

                    Material material = world.a(i0, i1, i2).o();
                    int i3 = world.e(i0, i1, i2);

                    if (material == Material.h && i3 == 0) {
                        // Filling Bucket with Water
                        hook.call();
                        if (hook.isCanceled()) {
                            return itemstack;
                        }
                        //

                        world.f(i0, i1, i2);
                        return this.a(itemstack, entityplayer, Items.as);
                    }

                    if (material == Material.i && i3 == 0) {
                        // Filling Bucket with Lava
                        hook.call();
                        if (hook.isCanceled()) {
                            return itemstack;
                        }

                        world.f(i0, i1, i2);
                        return this.a(itemstack, entityplayer, Items.at);
                    }

                }
                else {
                    if (this.a == Blocks.a) {
                        return new ItemStack(Items.ar);
                    }

                    if (movingobjectposition.e == 0) {
                        --i1;
                    }

                    if (movingobjectposition.e == 1) {
                        ++i1;
                    }

                    if (movingobjectposition.e == 2) {
                        --i2;
                    }

                    if (movingobjectposition.e == 3) {
                        ++i2;
                    }

                    if (movingobjectposition.e == 4) {
                        --i0;
                    }

                    if (movingobjectposition.e == 5) {
                        ++i0;
                    }

                    if (!entityplayer.a(i0, i1, i2, movingobjectposition.e, itemstack)) {
                        return itemstack;
                    }

                    if (this.a(world, i0, i1, i2) && !entityplayer.bF.d) {
                        return new ItemStack(Items.ar);
                    }
                }
            }

            return itemstack;
        }
    }

    private ItemStack a(ItemStack itemstack, EntityPlayer entityplayer, Item item) {
        if (entityplayer.bF.d) {
            return itemstack;
        }
        else if (--itemstack.b <= 0) {
            return new ItemStack(item);
        }
        else {
            if (!entityplayer.bn.a(new ItemStack(item))) {
                entityplayer.a(new ItemStack(item, 1, 0), false);
            }

            return itemstack;
        }
    }

    public boolean a(World world, int i0, int i1, int i2) {
        return a(world, i0, i1, i2, null); // CanaryMod: redirection
    }

    // CanaryMod: We need a Player for hooks
    public boolean a(World world, int i0, int i1, int i2, EntityPlayer entityplayer) {
        if (this.a == Blocks.a) {
            return false;
        }
        else {
            Material material = world.a(i0, i1, i2).o();
            boolean flag0 = !material.a();

            if (!world.c(i0, i1, i2) && !flag0) {
                return false;
            }
            else {
                if (world.t.f && this.a == Blocks.i) {
                    world.a((double) ((float) i0 + 0.5F), (double) ((float) i1 + 0.5F), (double) ((float) i2 + 0.5F), "random.fizz", 0.5F, 2.6F + (world.s.nextFloat() - world.s.nextFloat()) * 0.8F);

                    for (int i3 = 0; i3 < 8; ++i3) {
                        world.a("largesmoke", (double) i0 + Math.random(), (double) i1 + Math.random(), (double) i2 + Math.random(), 0.0D, 0.0D, 0.0D);
                    }
                }
                else {
                    // CanaryMod: BlockPlaceHook water/lava bucket
                    if (entityplayer != null) {
                        CanaryBlock clicked = (CanaryBlock) world.getCanaryWorld().getBlockAt(i0, i1, i2);
                        CanaryBlock placed = new CanaryBlock((short) Block.b(this.a), (short) 0, i0, i1, i2, world.getCanaryWorld());
                        Player player = ((EntityPlayerMP) entityplayer).getPlayer();
                        BlockPlaceHook hook = (BlockPlaceHook) new BlockPlaceHook(player, clicked, placed).call();
                        if (hook.isCanceled()) {
                            return false;
                        }
                    }
                    //
                    if (!world.E && flag0 && !material.d()) {
                        world.a(i0, i1, i2, true);
                    }

                    world.d(i0, i1, i2, this.a, 0, 3);
                }

                return true;
            }
        }
    }
}
