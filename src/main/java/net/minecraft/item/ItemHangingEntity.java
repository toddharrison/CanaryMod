package net.minecraft.item;

import net.canarymod.api.world.blocks.BlockFace;
import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.api.world.position.BlockPosition;
import net.canarymod.hook.player.ItemUseHook;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemHangingEntity extends Item {

    private final Class a;

    public ItemHangingEntity(Class oclass0) {
        this.a = oclass0;
        this.a(CreativeTabs.c);
    }

    public boolean a(ItemStack itemstack, EntityPlayer entityplayer, World world, BlockPos blockpos, EnumFacing enumfacing, float f0, float f1, float f2) {
        if (enumfacing == EnumFacing.DOWN) {
            return false;
        }
        else if (enumfacing == EnumFacing.UP) {
            return false;
        }
        else {
            // CanaryMod: ItemUse
            BlockPosition cbp = new BlockPosition(blockpos); // Translate native block pos
            CanaryBlock clicked = (CanaryBlock)world.getCanaryWorld().getBlockAt(cbp); // Store Clicked
            BlockFace cbf = BlockFace.fromByte((byte)enumfacing.a()); // Get the click face
            clicked.setFaceClicked(cbf); // Set face clicked
            ItemUseHook hook = (ItemUseHook)new ItemUseHook(((EntityPlayerMP)entityplayer).getPlayer(), itemstack.getCanaryItem(), clicked).call();
            if (hook.isCanceled()) {
                return false;
            }
            //
            BlockPos blockpos1 = blockpos.a(enumfacing);

            if (!entityplayer.a(blockpos1, enumfacing, itemstack)) {
                return false;
            }
            else {
                EntityHanging entityhanging = this.a(world, blockpos1, enumfacing);

                if (entityhanging != null && entityhanging.j()) {
                    if (!world.D) {
                        world.d((Entity)entityhanging);
                    }

                    --itemstack.b;
                }

                return true;
            }
        }
    }

    private EntityHanging a(World world, BlockPos blockpos, EnumFacing enumfacing) {
        return (EntityHanging)(this.a == EntityPainting.class ? new EntityPainting(world, blockpos, enumfacing) : (this.a == EntityItemFrame.class ? new EntityItemFrame(world, blockpos, enumfacing) : null));
    }
}
