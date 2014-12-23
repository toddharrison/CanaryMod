package net.minecraft.item;

import net.canarymod.hook.player.EatHook;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;

public class ItemBucketMilk extends Item {

    public ItemBucketMilk() {
        this.c(1);
        this.a(CreativeTabs.f);
    }

    public ItemStack b(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        // CanaryMod: Eat
        EatHook hook = (EatHook)new EatHook(((EntityPlayerMP)entityplayer).getPlayer(), itemstack.getCanaryItem(), 0, 0, null).call();
        if (hook.isCanceled()) {
            return itemstack;
        }
        // For those Lactose intolerant
        entityplayer.ck().a(hook.getLevelGain(), hook.getSaturationGain());
        //

        if (!entityplayer.by.d) {
            --itemstack.b;
        }

        if (!world.D) {
            entityplayer.bj();
        }

        entityplayer.b(StatList.J[Item.b((Item)this)]);
        return itemstack.b <= 0 ? new ItemStack(Items.aw) : itemstack;
    }

    public int d(ItemStack itemstack) {
        return 32;
    }

    public EnumAction e(ItemStack itemstack) {
        return EnumAction.DRINK;
    }

    public ItemStack a(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        entityplayer.a(itemstack, this.d(itemstack));
        return itemstack;
    }
}
