package net.minecraft.item;

import net.canarymod.api.world.blocks.BlockFace;
import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.hook.player.BlockPlaceHook;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class ItemBlock extends Item {

    protected final Block a;
    protected boolean handled = false; // CanaryMod: for ItemSlab inconsistency...

    public ItemBlock(Block block) {
        this.a = block;
    }

    public ItemBlock b(String s0) {
        super.c(s0);
        return this;
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

        if (itemstack.b == 0) {
            return false;
        }
        else if (!entityplayer.a(i0, i1, i2, i3, itemstack)) {
            return false;
        }
        else if (i1 == 255 && this.a.o().a()) {
            return false;
        }
        else if (world.a(this.a, i0, i1, i2, false, i3, entityplayer, itemstack)) {
            int i4 = this.a(itemstack.k());
            int i5 = this.a.a(world, i0, i1, i2, i3, f0, f1, f2, i4);
            if (!handled) { // if ItemSlab didn't call BlockPlace
                // set placed
                CanaryBlock placed = new CanaryBlock((short) Block.b(this.a), (short) i5, i0, i1, i2, world.getCanaryWorld());
                // Create and Call
                BlockPlaceHook hook = (BlockPlaceHook) new BlockPlaceHook(((EntityPlayerMP) entityplayer).getPlayer(), clicked, placed).call();
                if (hook.isCanceled()) {
                    return false;
                }
                //
            }

            if (world.d(i0, i1, i2, this.a, i5, 3)) {
                if (world.a(i0, i1, i2) == this.a) {
                    this.a.a(world, i0, i1, i2, (EntityLivingBase) entityplayer, itemstack);
                    this.a.e(world, i0, i1, i2, i5);
                }

                world.a((double) ((float) i0 + 0.5F), (double) ((float) i1 + 0.5F), (double) ((float) i2 + 0.5F), this.a.H.b(), (this.a.H.c() + 1.0F) / 2.0F, this.a.H.d() * 0.8F);
                --itemstack.b;
            }

            return true;
        }
        else {
            return false;
        }
    }

    public String a(ItemStack itemstack) {
        return this.a.a();
    }

    public String a() {
        return this.a.a();
    }

    public Item c(String s0) {
        return this.b(s0);
    }
}
