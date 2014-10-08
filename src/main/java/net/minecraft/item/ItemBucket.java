package net.minecraft.item;

import net.canarymod.api.entity.living.humanoid.Player;
import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.api.world.position.BlockPosition;
import net.canarymod.hook.player.BlockDestroyHook;
import net.canarymod.hook.player.BlockPlaceHook;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
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
                BlockPos blockpos = movingobjectposition.a();

                if (!world.a(entityplayer, blockpos)) {
                    return itemstack;
                }

                // CanaryMod: BlockDestroyHook
                CanaryBlock clicked = (CanaryBlock)world.getCanaryWorld().getBlockAt(new BlockPosition(blockpos));
                BlockDestroyHook hook = new BlockDestroyHook(((EntityPlayerMP)entityplayer).getPlayer(), clicked);
                //

                if (flag0) {
                    if (!entityplayer.a(blockpos.a(movingobjectposition.b), movingobjectposition.b, itemstack)) {
                        return itemstack;
                    }

                    IBlockState iblockstate = world.p(blockpos);
                    Material material = iblockstate.c().r();

                    if (material == Material.h && ((Integer)iblockstate.b(BlockLiquid.b)).intValue() == 0) {
                        // Filling Bucket with Water
                        hook.call();
                        if (hook.isCanceled()) {
                            return itemstack;
                        }
                        //

                        world.g(blockpos);
                        entityplayer.b(StatList.J[Item.b((Item)this)]);
                        return this.a(itemstack, entityplayer, Items.ax);
                    }

                    if (material == Material.i && ((Integer)iblockstate.b(BlockLiquid.b)).intValue() == 0) {
                        // Filling Bucket with Lava
                        hook.call();
                        if (hook.isCanceled()) {
                            return itemstack;
                        }
                        //

                        world.g(blockpos);
                        entityplayer.b(StatList.J[Item.b((Item)this)]);
                        return this.a(itemstack, entityplayer, Items.ay);
                    }
                }
                else {
                    if (this.a == Blocks.a) {
                        return new ItemStack(Items.aw);
                    }

                    BlockPos blockpos1 = blockpos.a(movingobjectposition.b);

                    if (!entityplayer.a(blockpos1, movingobjectposition.b, itemstack)) {
                        return itemstack;
                    }

                    // CanaryMod: Pass player to tryPlace
                    if (this.a(world, blockpos1, entityplayer, false) && !entityplayer.by.d) {
                        entityplayer.b(StatList.J[Item.b((Item)this)]);
                        return new ItemStack(Items.aw);
                    }
                }
            }

            return itemstack;
        }
    }

    private ItemStack a(ItemStack itemstack, EntityPlayer entityplayer, Item item) {
        if (entityplayer.by.d) {
            return itemstack;
        }
        else if (--itemstack.b <= 0) {
            return new ItemStack(item);
        }
        else {
            if (!entityplayer.bg.a(new ItemStack(item))) {
                entityplayer.a(new ItemStack(item, 1, 0), false);
            }

            return itemstack;
        }
    }

    public boolean a(World world, BlockPos blockpos) {
        return a(world, blockpos, null, false); // CanaryMod: redirection
    }

    // CanaryMod: We need a Player for hooks, testOnly is to simulate a Dispenser letting stuff happen
    public boolean a(World world, BlockPos blockpos, EntityPlayer entityplayer, boolean testOnly) {
        if (this.a == Blocks.a) {
            return false;
        }
        else {
            Material material = world.p(blockpos).c().r();
            boolean flag0 = !material.a();

            if (!world.d(blockpos) && !flag0) {
                return false;
            }
            else {
                if (world.t.n() && this.a == Blocks.i) {
                    int i0 = blockpos.n();
                    int i1 = blockpos.o();
                    int i2 = blockpos.p();

                    world.a((double)((float)i0 + 0.5F), (double)((float)i1 + 0.5F), (double)((float)i2 + 0.5F), "random.fizz", 0.5F, 2.6F + (world.s.nextFloat() - world.s.nextFloat()) * 0.8F);

                    for (int i3 = 0; i3 < 8; ++i3) {
                        world.a(EnumParticleTypes.SMOKE_LARGE, (double)i0 + Math.random(), (double)i1 + Math.random(), (double)i2 + Math.random(), 0.0D, 0.0D, 0.0D, new int[0]);
                    }
                }
                else {
                    if (!world.D && flag0 && !material.d()) {
                        world.b(blockpos, true);
                    }

                    // CanaryMod: BlockPlaceHook water/lava bucket
                    if (entityplayer != null) {
                        BlockPosition cbp = new BlockPosition(blockpos);
                        CanaryBlock clicked = (CanaryBlock)world.getCanaryWorld().getBlockAt(cbp);
                        CanaryBlock placed = new CanaryBlock(this.a.P(), cbp, world.getCanaryWorld());
                        Player player = ((EntityPlayerMP)entityplayer).getPlayer();
                        BlockPlaceHook hook = (BlockPlaceHook)new BlockPlaceHook(player, clicked, placed).call();
                        if (hook.isCanceled()) {
                            return false;
                        }
                    }
                    //

                    // CanaryMod: Dispense helper
                    if (!testOnly) {
                        world.a(blockpos, this.a.P(), 3);
                    }
                    //
                }

                return true;
            }
        }
    }
}
