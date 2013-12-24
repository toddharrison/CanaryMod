package net.minecraft.item;

import net.canarymod.api.world.blocks.BlockFace;
import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.hook.player.ItemUseHook;
import net.minecraft.block.BlockJukebox;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class ItemRecord extends Item {

    private static final Map b = new HashMap();
    public final String a;

    protected ItemRecord(String s0) {
        this.a = s0;
        this.h = 1;
        this.a(CreativeTabs.f);
        b.put(s0, this);
    }

    public boolean a(ItemStack itemstack, EntityPlayer entityplayer, World world, int i0, int i1, int i2, int i3, float f0, float f1, float f2) {
        // CanaryMod: ItemUse
        CanaryBlock clicked = (CanaryBlock) world.getCanaryWorld().getBlockAt(i0, i1, i2);

        clicked.setFaceClicked(BlockFace.fromByte((byte) i3));
        ItemUseHook hook = (ItemUseHook) new ItemUseHook(((EntityPlayerMP) entityplayer).getPlayer(), itemstack.getCanaryItem(), clicked).call();
        if (hook.isCanceled()) {
            return false;
        }
        //
        if (world.a(i0, i1, i2) == Blocks.aI && world.e(i0, i1, i2) == 0) {
            if (world.E) {
                return true;
            }
            else {
                ((BlockJukebox) Blocks.aI).b(world, i0, i1, i2, itemstack);
                world.a((EntityPlayer) null, 1005, i0, i1, i2, Item.b((Item) this));
                --itemstack.b;
                return true;
            }
        }
        else {
            return false;
        }
    }

    public EnumRarity f(ItemStack itemstack) {
        return EnumRarity.rare;
    }
}
