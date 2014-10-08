package net.minecraft.item;

import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.hook.player.BlockPlaceHook;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemRedstone extends Item {

    public ItemRedstone() {
        this.a(CreativeTabs.d);
    }

    public boolean a(ItemStack itemstack, EntityPlayer entityplayer, World world, BlockPos blockpos, EnumFacing enumfacing, float f0, float f1, float f2) {
        boolean flag0 = world.p(blockpos).c().f(world, blockpos);
        BlockPos blockpos1 = flag0 ? blockpos : blockpos.a(enumfacing);

        if (!entityplayer.a(blockpos1, enumfacing, itemstack)) {
            return false;
        }
        else {
            Block block = world.p(blockpos1).c();

            if (!world.a(block, blockpos1, false, enumfacing, (Entity)null, itemstack)) {
                return false;
            }
            else if (Blocks.af.c(world, blockpos1)) {
                // CanaryMod: BlockPlaceHook
                CanaryBlock clicked = new CanaryBlock(world.p(blockpos), blockpos, world); // Store Clicked
                clicked.setFaceClicked(enumfacing.asBlockFace()); // Set face clicked
                if (new BlockPlaceHook(((EntityPlayerMP)entityplayer).getPlayer(), clicked, new CanaryBlock(Blocks.af.P(), blockpos1, world)).call().isCanceled()) {
                    return false;
                }
                //

                --itemstack.b;
                world.a(blockpos1, Blocks.af.P());
                return true;
            }
            else {
                return false;
            }
        }
    }
}
