package net.minecraft.item;

import net.canarymod.api.world.blocks.BlockFace;
import net.canarymod.api.world.blocks.BlockType;
import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.api.world.position.BlockPosition;
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
        // CanaryMod: BlockPlaceHook
        BlockPosition cbp = new BlockPosition(blockpos); // Translate native block pos
        CanaryBlock clicked = (CanaryBlock)world.getCanaryWorld().getBlockAt(cbp); // Store Clicked
        BlockFace cbf = BlockFace.fromByte((byte)enumfacing.a()); // Get the click face
        clicked.setFaceClicked(cbf); // Set face clicked
        cbp = cbp.safeClone(); // Remake BlockPosition
        cbp.transform(cbf); // Adjust position based on face
        //

        if (!entityplayer.a(blockpos1, enumfacing, itemstack)) {
            return false;
        }
        else {
            // set placed
            CanaryBlock placed = new CanaryBlock(BlockType.RedstoneWire, (short)0, cbp, world.getCanaryWorld());
            // Create and Call
            BlockPlaceHook hook = (BlockPlaceHook)new BlockPlaceHook(((EntityPlayerMP)entityplayer).getPlayer(), clicked, placed).call();
            if (hook.isCanceled()) {
                return false;
            }
            //

            Block block = world.p(blockpos1).c();

            if (!world.a(block, blockpos1, false, enumfacing, (Entity)null, itemstack)) {
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
