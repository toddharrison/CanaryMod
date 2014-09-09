package net.minecraft.item;

import net.canarymod.api.world.blocks.BlockFace;
import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.hook.player.BlockPlaceHook;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class ItemReed extends Item {

    private Block a;

    public ItemReed(Block block) {
        this.a = block;
    }

    public boolean a(ItemStack itemstack, EntityPlayer entityplayer, World world, int i0, int i1, int i2, int i3, float f0, float f1, float f2) {
        Block block = world.a(i0, i1, i2);

        // CanaryMod: BlockPlaceHook
        CanaryBlock clicked = (CanaryBlock) world.getCanaryWorld().getBlockAt(i0, i1, i2);
        clicked.setFaceClicked(BlockFace.fromByte((byte) i3));
        //

        if (block == Blocks.aC && (world.e(i0, i1, i2) & 7) < 1) {
            i3 = 1;
        }
        else if (block != Blocks.bd && block != Blocks.H && block != Blocks.I) {
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
        }

        if (!entityplayer.a(i0, i1, i2, i3, itemstack)) {
            return false;
        }
        else if (itemstack.b == 0) {
            return false;
        }
        else {
            if (world.a(this.a, i0, i1, i2, false, i3, (Entity) null, itemstack)) {
                int i4 = this.a.a(world, i0, i1, i2, i3, f0, f1, f2, 0);

                if (world.d(i0, i1, i2, this.a, i4, 3)) {
                    // set placed
                    CanaryBlock placed = new CanaryBlock((short) Block.b(this.a), (short) 0, i0, i1, i2, world.getCanaryWorld());
                    // Create and Call
                    BlockPlaceHook hook = (BlockPlaceHook) new BlockPlaceHook(((EntityPlayerMP) entityplayer).getPlayer(), clicked, placed).call();
                    if (hook.isCanceled()) {
                        return false;
                    }
                    //
                    if (world.a(i0, i1, i2) == this.a) {
                        this.a.a(world, i0, i1, i2, (EntityLivingBase) entityplayer, itemstack);
                        this.a.e(world, i0, i1, i2, i4);
                    }

                    world.a((double) ((float) i0 + 0.5F), (double) ((float) i1 + 0.5F), (double) ((float) i2 + 0.5F), this.a.H.b(), (this.a.H.c() + 1.0F) / 2.0F, this.a.H.d() * 0.8F);
                    --itemstack.b;
                }
            }

            return true;
        }
    }
}
