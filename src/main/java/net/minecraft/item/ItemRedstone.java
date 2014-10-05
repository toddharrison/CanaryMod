package net.minecraft.item;

import net.canarymod.api.world.blocks.BlockFace;
import net.canarymod.api.world.blocks.BlockType;
import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.hook.player.BlockPlaceHook;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class ItemRedstone extends Item {

    public ItemRedstone() {
        this.a(CreativeTabs.d);
    }

    public boolean a(ItemStack itemstack, EntityPlayer entityplayer, World world, BlockPos blockpos, EnumFacing enumfacing, float f0, float f1, float f2) {
        boolean flag0 = world.p(blockpos).c().f(world, blockpos);
        BlockPos blockpos1 = flag0 ? blockpos : blockpos.a(enumfacing);
        // CanaryMod: BlockPlaceHook
        CanaryBlock clicked = (CanaryBlock) world.getCanaryWorld().getBlockAt(i0, i1, i2);
        clicked.setFaceClicked(BlockFace.fromByte((byte) i3));
        //

        if (!entityplayer.a(blockpos1, enumfacing, itemstack)) {
            return false;
        }
        else {
            // set placed
            CanaryBlock placed = new CanaryBlock(BlockType.RedstoneBlock, i0, i1, i2, world.getCanaryWorld());
            // Create and Call
            BlockPlaceHook hook = (BlockPlaceHook) new BlockPlaceHook(((EntityPlayerMP) entityplayer).getPlayer(), clicked, placed).call();
            if (hook.isCanceled()) {
                return false;
            }
            //

            Block block = world.p(blockpos1).c();

            if (!world.a(block, blockpos1, false, enumfacing, (Entity) null, itemstack)) {
                return false;
            }
            else if (Blocks.af.c(world, blockpos1)) {
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
