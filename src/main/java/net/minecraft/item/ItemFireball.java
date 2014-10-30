package net.minecraft.item;

import net.canarymod.api.world.blocks.CanaryBlock;
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
        CanaryBlock clicked = CanaryBlock.getPooledBlock(world.p(blockpos), blockpos, world); // Store clicked
        clicked.setStatus((byte)6);
        clicked.setFaceClicked(enumfacing.asBlockFace()); // Set face clicked
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
                CanaryBlock ignited = CanaryBlock.getPooledBlock(world.p(blockpos), blockpos, world);
                // If item use gets canceled then no ignition would really take place
                if (new ItemUseHook(((EntityPlayerMP)entityplayer).getPlayer(), itemstack.getCanaryItem(), clicked).call().isCanceled()
                            || new IgnitionHook(ignited, ((EntityPlayerMP)entityplayer).getPlayer(), clicked, IgnitionCause.FIREBALL_CLICK).call().isCanceled()) {
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
