package net.minecraft.item;

import net.canarymod.api.world.blocks.BlockFace;
import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.hook.player.BlockPlaceHook;
import net.minecraft.block.BlockSkull;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemSkull extends Item {

    private static final String[] b = new String[]{"skeleton", "wither", "zombie", "char", "creeper"};
    public static final String[] a = new String[]{"skeleton", "wither", "zombie", "steve", "creeper"};

    public ItemSkull() {
        this.a(CreativeTabs.c);
        this.f(0);
        this.a(true);
    }

    public boolean a(ItemStack itemstack, EntityPlayer entityplayer, World world, int i0, int i1, int i2, int i3, float f0, float f1, float f2) {
        if (i3 == 0) {
            return false;
        } else if (!world.a(i0, i1, i2).o().a()) {
            return false;
        } else {
            // CanaryMod: BlockPlaceHook
            CanaryBlock clicked = (CanaryBlock) world.getCanaryWorld().getBlockAt(i0, i1, i2);
            clicked.setFaceClicked(BlockFace.fromByte((byte) i3));
            //

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
            } else if (!Blocks.bP.c(world, i0, i1, i2)) {
                return false;
            } else {
                // Create and call
                CanaryBlock placed = new CanaryBlock((short) 144, (short) itemstack.getCanaryItem().getDamage(), i0, i1, i2, world.getCanaryWorld());
                BlockPlaceHook hook = (BlockPlaceHook) new BlockPlaceHook(((EntityPlayerMP) entityplayer).getPlayer(), clicked, placed).call();
                if (hook.isCanceled()) {
                    return false;
                }
                //

                world.d(i0, i1, i2, Blocks.bP, i3, 2);
                int i4 = 0;

                if (i3 == 1) {
                    i4 = MathHelper.c((double) (entityplayer.z * 16.0F / 360.0F) + 0.5D) & 15;
                }

                TileEntity tileentity = world.o(i0, i1, i2);

                if (tileentity != null && tileentity instanceof TileEntitySkull) {
                    String s0 = "";

                    if (itemstack.p() && itemstack.q().b("SkullOwner", 8)) {
                        s0 = itemstack.q().j("SkullOwner");
                    }

                    ((TileEntitySkull) tileentity).a(itemstack.k(), s0);
                    ((TileEntitySkull) tileentity).a(i4);
                    ((BlockSkull) Blocks.bP).a(world, i0, i1, i2, (TileEntitySkull) tileentity);
                }

                --itemstack.b;
                return true;
            }
        }
    }

    public int a(int i0) {
        return i0;
    }

    public String a(ItemStack itemstack) {
        int i0 = itemstack.k();

        if (i0 < 0 || i0 >= b.length) {
            i0 = 0;
        }

        return super.a() + "." + b[i0];
    }

    public String n(ItemStack itemstack) {
        return itemstack.k() == 3 && itemstack.p() && itemstack.q().b("SkullOwner", 8) ? StatCollector.a("item.skull.player.name", new Object[]{itemstack.q().j("SkullOwner")}) : super.n(itemstack);
    }
}
