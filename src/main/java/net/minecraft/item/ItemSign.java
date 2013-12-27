package net.minecraft.item;

import net.canarymod.api.world.blocks.BlockFace;
import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.hook.player.BlockPlaceHook;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemSign extends Item {

    public ItemSign() {
        this.h = 16;
        this.a(CreativeTabs.c);
    }

    public boolean a(ItemStack itemstack, EntityPlayer entityplayer, World world, int i0, int i1, int i2, int i3, float f0, float f1, float f2) {
        if (i3 == 0) {
            return false;
        }
        else if (!world.a(i0, i1, i2).o().a()) {
            return false;
        }
        else {
            // CanaryMod: BlockPlaceHook
            CanaryBlock clicked = (CanaryBlock) world.getCanaryWorld().getBlockAt(i0, i1, i2);

            clicked.setFaceClicked(BlockFace.fromByte((byte) i3));

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
            else if (!Blocks.an.c(world, i0, i1, i2)) {
                return false;
            }
            else if (world.E) {
                return true;
            }
            else {
                // Create and call
                CanaryBlock placed = new CanaryBlock((short) (i3 == 1 ? 63 : 68), (short) 0, i0, i1, i2, world.getCanaryWorld());
                BlockPlaceHook hook = (BlockPlaceHook) new BlockPlaceHook(((EntityPlayerMP) entityplayer).getPlayer(), clicked, placed).call();
                if (hook.isCanceled()) {
                    return false;
                }
                //

                if (i3 == 1) {
                    int i4 = MathHelper.c((double) ((entityplayer.z + 180.0F) * 16.0F / 360.0F) + 0.5D) & 15;

                    world.d(i0, i1, i2, Blocks.an, i4, 3);
                }
                else {
                    world.d(i0, i1, i2, Blocks.as, i3, 3);
                }

                --itemstack.b;
                TileEntitySign tileentitysign = (TileEntitySign) world.o(i0, i1, i2);

                if (tileentitysign != null) {
                    entityplayer.a((TileEntity) tileentitysign);
                }

                return true;
            }
        }
    }
}
