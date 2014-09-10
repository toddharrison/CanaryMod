package net.minecraft.item;

import net.canarymod.api.world.blocks.BlockFace;
import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.hook.player.BlockPlaceHook;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;

public class ItemSeeds extends Item {

    private Block a;
    private Block b;

    public ItemSeeds(Block block, Block block1) {
        this.a = block;
        this.b = block1;
        this.a(CreativeTabs.l);
    }

    public boolean a(ItemStack itemstack, EntityPlayer entityplayer, World world, int i0, int i1, int i2, int i3, float f0, float f1, float f2) {
        if (i3 != 1) {
            return false;
        }
        else if (entityplayer.a(i0, i1, i2, i3, itemstack) && entityplayer.a(i0, i1 + 1, i2, i3, itemstack)) {
            if (world.a(i0, i1, i2) == this.b && world.c(i0, i1 + 1, i2)) {

                // CanaryMod: BlockPlaceHook
                CanaryBlock clicked = (CanaryBlock) world.getCanaryWorld().getBlockAt(i0, i1, i2);
                clicked.setFaceClicked(BlockFace.fromByte((byte) 1)); // Should be 1
                CanaryBlock placed = new CanaryBlock((short) Block.b(this.a), (short) 0, i0, i1 + 1, i2, world.getCanaryWorld());
                BlockPlaceHook hook = (BlockPlaceHook) new BlockPlaceHook(((EntityPlayerMP) entityplayer).getPlayer(), clicked, placed).call();
                if (hook.isCanceled()) {
                    return false;
                }
                //

                world.b(i0, i1 + 1, i2, this.a);
                --itemstack.b;
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }
}
