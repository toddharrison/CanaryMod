package net.minecraft.item;

import net.canarymod.api.world.blocks.BlockFace;
import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.hook.player.BlockPlaceHook;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;

public class ItemSlab extends ItemBlock {

    private final boolean b;
    private final BlockSlab c;
    private final BlockSlab d;
    private BlockPlaceHook hook; // CanaryMod: helper

    public ItemSlab(Block block, BlockSlab blockslab, BlockSlab blockslab1, boolean flag0) {
        super(block);
        this.c = blockslab;
        this.d = blockslab1;
        this.b = flag0;
        this.f(0);
        this.a(true);
    }

    public int a(int i0) {
        return i0;
    }

    public String a(ItemStack itemstack) {
        return this.c.b(itemstack.k());
    }

    public boolean a(ItemStack itemstack, EntityPlayer entityplayer, World world, int i0, int i1, int i2, int i3, float f0, float f1, float f2) {
        this.hook = null; // CanaryMod: Clean up
        this.handled = false; // CanaryMod: Clean up
        // CanaryMod: BlockPlaceHook
        CanaryBlock clicked = (CanaryBlock) world.getCanaryWorld().getBlockAt(i0, i1, i2);
        if (this.b) {
            return super.a(itemstack, entityplayer, world, i0, i1, i2, i3, f0, f1, f2);
        }
        else if (itemstack.b == 0) {
            return false;
        }
        else if (!entityplayer.a(i0, i1, i2, i3, itemstack)) {
            return false;
        }
        else {
            Block block = world.a(i0, i1, i2);
            int i4 = world.e(i0, i1, i2);
            int i5 = i4 & 7;
            boolean flag0 = (i4 & 8) != 0;

            if ((i3 == 1 && !flag0 || i3 == 0 && flag0) && block == this.c && i5 == itemstack.k()) {
                clicked.setFaceClicked(BlockFace.fromByte((byte) i3));
                CanaryBlock placed = new CanaryBlock((short) i4, (short) i5, i0, i1, i2, world.getCanaryWorld());

                hook = (BlockPlaceHook) new BlockPlaceHook(((EntityPlayerMP) entityplayer).getPlayer(), clicked, placed).call();
                if (hook.isCanceled()) {
                    return true;
                }
                //

                if (world.b(this.d.a(world, i0, i1, i2)) && world.d(i0, i1, i2, this.d, i5, 3)) {
                    world.a((double) ((float) i0 + 0.5F), (double) ((float) i1 + 0.5F), (double) ((float) i2 + 0.5F), this.d.H.b(), (this.d.H.c() + 1.0F) / 2.0F, this.d.H.d() * 0.8F);
                    --itemstack.b;
                }

                return true;
            }
            else {
                boolean ret = this.a(itemstack, entityplayer, world, i0, i1, i2, i3); // Moved up to call hook before the return

                this.handled = hook != null; // Let super know we got this shit
                return (hook != null && hook.isCanceled()) ? false : ret ? true : super.a(itemstack, entityplayer, world, i0, i1, i2, i3, f0, f1, f2);
            }
        }
    }

    private boolean a(ItemStack itemstack, EntityPlayer entityplayer, World world, int i0, int i1, int i2, int i3) {
        // CanaryMod: BlockPlaceHook
        CanaryBlock clicked = (CanaryBlock) world.getCanaryWorld().getBlockAt(i0, i1, i2);

        clicked.setFaceClicked(BlockFace.fromByte((byte) i3));

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

        Block block = world.a(i0, i1, i2);
        int i4 = world.e(i0, i1, i2);
        int i5 = i4 & 7;

        if (block == this.c && i5 == itemstack.k()) {
            // Call hook
            CanaryBlock placed = new CanaryBlock((short) i4, (short) i5, i0, i1, i2, world.getCanaryWorld());

            hook = (BlockPlaceHook) new BlockPlaceHook(((EntityPlayerMP) entityplayer).getPlayer(), clicked, placed).call();
            if (hook.isCanceled()) {
                return false;
            }
            //

            if (world.b(this.d.a(world, i0, i1, i2)) && world.d(i0, i1, i2, this.d, i5, 3)) {
                world.a((double) ((float) i0 + 0.5F), (double) ((float) i1 + 0.5F), (double) ((float) i2 + 0.5F), this.d.H.b(), (this.d.H.c() + 1.0F) / 2.0F, this.d.H.d() * 0.8F);
                --itemstack.b;
            }

            return true;
        }
        else {
            return false;
        }
    }
}
