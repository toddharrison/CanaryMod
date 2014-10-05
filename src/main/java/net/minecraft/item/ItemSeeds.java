package net.minecraft.item;

import net.canarymod.api.world.blocks.BlockFace;
import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.api.world.position.BlockPosition;
import net.canarymod.hook.player.BlockPlaceHook;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemSeeds extends Item {

    private Block a;
    private Block b;

    public ItemSeeds(Block block, Block block1) {
        this.a = block;
        this.b = block1;
        this.a(CreativeTabs.l);
    }

    public boolean a(ItemStack itemstack, EntityPlayer entityplayer, World world, BlockPos blockpos, EnumFacing enumfacing, float f0, float f1, float f2) {
        if (enumfacing != EnumFacing.UP) {
            return false;
        }
        else if (!entityplayer.a(blockpos.a(enumfacing), enumfacing, itemstack)) {
            return false;
        }
        else if (world.p(blockpos).c() == this.b && world.d(blockpos.a())) {
            // CanaryMod: BlockPlaceHook
            CanaryBlock clicked = (CanaryBlock) world.getCanaryWorld().getBlockAt(new BlockPosition(blockpos));
            clicked.setFaceClicked(BlockFace.fromByte((byte) 1)); // Should be 1
            CanaryBlock placed = new CanaryBlock((short) Block.a(this.a), (short) 0, i0, i1 + 1, i2, world.getCanaryWorld());
            BlockPlaceHook hook = (BlockPlaceHook) new BlockPlaceHook(((EntityPlayerMP) entityplayer).getPlayer(), clicked, placed).call();
            if (hook.isCanceled()) {
                return false;
            }
            //

            world.a(blockpos.a(), this.a.P());
            --itemstack.b;
            return true;
        }
        else {
            return false;
        }
    }
}
