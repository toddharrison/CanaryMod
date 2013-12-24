package net.minecraft.item;

import net.canarymod.api.world.blocks.BlockFace;
import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.hook.player.ItemUseHook;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Direction;
import net.minecraft.world.World;

public class ItemHangingEntity extends Item {

    private final Class a;

    public ItemHangingEntity(Class oclass0) {
        this.a = oclass0;
        this.a(CreativeTabs.c);
    }

    public boolean a(ItemStack itemstack, EntityPlayer entityplayer, World world, int i0, int i1, int i2, int i3, float f0, float f1, float f2) {
        if (i3 == 0) {
            return false;
        }
        else if (i3 == 1) {
            return false;
        }
        else {
            // CanaryMod: ItemUse
            CanaryBlock clicked = (CanaryBlock) world.getCanaryWorld().getBlockAt(i0, i1, i2);

            clicked.setFaceClicked(BlockFace.fromByte((byte) i3));
            ItemUseHook hook = (ItemUseHook) new ItemUseHook(((EntityPlayerMP) entityplayer).getPlayer(), itemstack.getCanaryItem(), clicked).call();
            if (hook.isCanceled()) {
                return false;
            }
            //
            int i4 = Direction.e[i3];
            EntityHanging entityhanging = this.a(world, i0, i1, i2, i4);

            if (!entityplayer.a(i0, i1, i2, i3, itemstack)) {
                return false;
            }
            else {
                if (entityhanging != null && entityhanging.e()) {
                    if (!world.E) {
                        world.d((Entity) entityhanging);
                    }

                    --itemstack.b;
                }

                return true;
            }
        }
    }

    private EntityHanging a(World world, int i0, int i1, int i2, int i3) {
        return (EntityHanging) (this.a == EntityPainting.class ? new EntityPainting(world, i0, i1, i2, i3) : (this.a == EntityItemFrame.class ? new EntityItemFrame(world, i0, i1, i2, i3) : null));
    }
}
