package net.minecraft.item;

import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.hook.player.ItemUseHook;
import net.canarymod.hook.world.IgnitionHook;
import net.canarymod.hook.world.IgnitionHook.IgnitionCause;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemFlintAndSteel extends Item {

    public ItemFlintAndSteel() {
        this.h = 1;
        this.d(64);
        this.a(CreativeTabs.i);
    }

    public boolean a(ItemStack itemstack, EntityPlayer entityplayer, World world, BlockPos blockpos, EnumFacing enumfacing, float f0, float f1, float f2) {
        blockpos = blockpos.a(enumfacing);
        // CanaryMod: get clicked
        CanaryBlock clicked = new CanaryBlock(world.p(blockpos), blockpos, world, (byte)2); // Store Clicked, Status 2
        clicked.setFaceClicked(enumfacing.asBlockFace()); // Set face clicked
        //

        if (!entityplayer.a(blockpos, enumfacing, itemstack)) {
            return false;
        }
        else {

            // CanaryMod: ItemUse/Ignition
            CanaryBlock ignited = new CanaryBlock(world.p(blockpos), blockpos, world);
            // If item use gets canceled then no ignition would really take place
            if (new ItemUseHook(((EntityPlayerMP)entityplayer).getPlayer(), itemstack.getCanaryItem(), clicked).call().isCanceled()
                        || new IgnitionHook(ignited, ((EntityPlayerMP)entityplayer).getPlayer(), clicked, IgnitionCause.FIREBALL_CLICK).call().isCanceled()) {
                return false;
            }
            //

            if (world.p(blockpos).c().r() == Material.a) {
                world.a((double)blockpos.n() + 0.5D, (double)blockpos.o() + 0.5D, (double)blockpos.p() + 0.5D, "fire.ignite", 1.0F, g.nextFloat() * 0.4F + 0.8F);
                world.a(blockpos, Blocks.ab.P());
            }

            itemstack.a(1, (EntityLivingBase)entityplayer);
            return true;
        }
    }
}
