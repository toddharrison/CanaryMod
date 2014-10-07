package net.minecraft.item;

import net.canarymod.api.world.blocks.BlockFace;
import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.api.world.position.BlockPosition;
import net.canarymod.hook.player.ItemUseHook;
import net.canarymod.hook.world.IgnitionHook;
import net.canarymod.hook.world.IgnitionHook.IgnitionCause;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemFireball extends Item {

    public ItemFireball() {
        this.a(CreativeTabs.f);
    }

    public boolean a(ItemStack itemstack, EntityPlayer entityplayer, World world, BlockPos blockpos, EnumFacing enumfacing, float f0, float f1, float f2) {
        // CanaryMod: get clicked
        BlockPosition cbp = new BlockPosition(blockpos); // Translate native block pos
        CanaryBlock clicked = (CanaryBlock)world.getCanaryWorld().getBlockAt(cbp); // Store Clicked
        BlockFace cbf = BlockFace.fromByte((byte)enumfacing.a()); // Get the click face
        clicked.setFaceClicked(cbf); // Set face clicked
        cbp = cbp.safeClone(); // Remake BlockPosition
        cbp.transform(cbf); // Adjust position based on face
        clicked.setStatus((byte)6); // Fireball Status 6
        //
        if (world.D) {
            return true;
        }
        else {
            blockpos = blockpos.a(enumfacing);
            if (!entityplayer.a(blockpos, enumfacing, itemstack)) {
                return false;
            }
            else {
                // CanaryMod: ItemUse/Ignition
                // Create & Call ItemUseHook
                ItemUseHook iuh = (ItemUseHook)new ItemUseHook(((EntityPlayerMP)entityplayer).getPlayer(), itemstack.getCanaryItem(), clicked).call();
                // Create & Call IgnitionHook
                CanaryBlock ignited = (CanaryBlock)world.getCanaryWorld().getBlockAt(cbp);
                IgnitionHook ih = (IgnitionHook)new IgnitionHook(ignited, ((EntityPlayerMP)entityplayer).getPlayer(), clicked, IgnitionCause.FIREBALL_CLICK).call();

                // If either hook is canceled, return
                if (iuh.isCanceled() || ih.isCanceled()) {
                    return false;
                }
                //

                if (world.p(blockpos).c().r() == Material.a) {
                    world.a((double)blockpos.n() + 0.5D, (double)blockpos.o() + 0.5D, (double)blockpos.p() + 0.5D, "item.fireCharge.use", 1.0F, (g.nextFloat() - g.nextFloat()) * 0.2F + 1.0F);
                    world.a(blockpos, Blocks.ab.P());
                }

                if (!entityplayer.by.d) {
                    --itemstack.b;
                }

                return true;
            }
        }
    }
}
