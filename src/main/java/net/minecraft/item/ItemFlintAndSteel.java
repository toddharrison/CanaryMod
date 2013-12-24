package net.minecraft.item;

import net.canarymod.Canary;
import net.canarymod.api.world.blocks.BlockFace;
import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.hook.player.ItemUseHook;
import net.canarymod.hook.world.IgnitionHook;
import net.canarymod.hook.world.IgnitionHook.IgnitionCause;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class ItemFlintAndSteel extends Item {

    public ItemFlintAndSteel() {
        this.h = 1;
        this.f(64);
        this.a(CreativeTabs.i);
    }

    public boolean a(ItemStack itemstack, EntityPlayer entityplayer, World world, int i0, int i1, int i2, int i3, float f0, float f1, float f2) {
        // CanaryMod: get clicked
        CanaryBlock clicked = (CanaryBlock) world.getCanaryWorld().getBlockAt(i0, i1, i2);

        clicked.setFaceClicked(BlockFace.fromByte((byte) i3));
        clicked.setStatus((byte) 2); // Flint&Steel Status 2
        //

        if (i3 == 0) {
            --i1;
        }

        if (i3 == 1) {
            ++i1;
        }

        if (i3 == 2) {
            --i2;
        }

        if (i3 == 3) {
            ++i2;
        }

        if (i3 == 4) {
            --i0;
        }

        if (i3 == 5) {
            ++i0;
        }

        if (!entityplayer.a(i0, i1, i2, i3, itemstack)) {
            return false;
        }
        else {

            // CanaryMod: ItemUse/Ignition
            // Create & Call ItemUseHook
            ItemUseHook iuh = (ItemUseHook) new ItemUseHook(((EntityPlayerMP) entityplayer).getPlayer(), itemstack.getCanaryItem(), clicked).call();
            // Create & Call IgnitionHook
            CanaryBlock ignited = (CanaryBlock) world.getCanaryWorld().getBlockAt(i0, i1, i2);
            IgnitionHook ih = new IgnitionHook(ignited, ((EntityPlayerMP) entityplayer).getPlayer(), clicked, IgnitionCause.FLINT_AND_STEEL);
            Canary.hooks().callHook(ih);

            // If either hook is canceled, return
            if (iuh.isCanceled() || ih.isCanceled()) {
                return false;
            }
            //

            if (world.a(i0, i1, i2).o() == Material.a) {
                world.a((double) i0 + 0.5D, (double) i1 + 0.5D, (double) i2 + 0.5D, "fire.ignite", 1.0F, g.nextFloat() * 0.4F + 0.8F);
                world.b(i0, i1, i2, (Block) Blocks.ab);
            }

            itemstack.a(1, (EntityLivingBase) entityplayer);
            return true;
        }
    }
}
