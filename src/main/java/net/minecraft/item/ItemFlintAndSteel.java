package net.minecraft.item;

import net.canarymod.Canary;
import net.canarymod.api.world.blocks.BlockFace;
import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.api.world.position.BlockPosition;
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
        BlockPosition cbp = new BlockPosition(blockpos); // Translate native block pos
        CanaryBlock clicked = (CanaryBlock)world.getCanaryWorld().getBlockAt(cbp); // Store Clicked
        BlockFace cbf = BlockFace.fromByte((byte)enumfacing.a()); // Get the click face
        clicked.setFaceClicked(cbf); // Set face clicked
        cbp = cbp.safeClone(); // Remake BlockPosition
        cbp.transform(cbf); // Adjust position based on face
        clicked.setStatus((byte)2); // Flint&Steel Status 2
        //

        if (!entityplayer.a(blockpos, enumfacing, itemstack)) {
            return false;
        }
        else {

            // CanaryMod: ItemUse/Ignition
            // Create & Call ItemUseHook
            ItemUseHook iuh = (ItemUseHook)new ItemUseHook(((EntityPlayerMP)entityplayer).getPlayer(), itemstack.getCanaryItem(), clicked).call();
            // Create & Call IgnitionHook
            CanaryBlock ignited = (CanaryBlock)world.getCanaryWorld().getBlockAt(cbp);
            IgnitionHook ih = new IgnitionHook(ignited, ((EntityPlayerMP)entityplayer).getPlayer(), clicked, IgnitionCause.FLINT_AND_STEEL);
            Canary.hooks().callHook(ih);

            // If either hook is canceled, return
            if (iuh.isCanceled() || ih.isCanceled()) {
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
