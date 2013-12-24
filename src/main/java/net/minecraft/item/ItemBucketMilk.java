package net.minecraft.item;

import net.canarymod.hook.player.EatHook;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.world.World;

public class ItemBucketMilk extends Item {

    public ItemBucketMilk() {
        this.e(1);
        this.a(CreativeTabs.f);
    }

    public ItemStack b(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        // CanaryMod: Eat
        EatHook hook = (EatHook) new EatHook(((EntityPlayerMP) entityplayer).getPlayer(), itemstack.getCanaryItem(), 0, 0, null).call();
        if (hook.isCanceled()) {
            return itemstack;
        }
        // For those Lactose intolerant
        entityplayer.bq.a(hook.getLevelGain(), hook.getSaturationGain());
        //

        if (!entityplayer.bF.d) {
            --itemstack.b;
        }

        if (!world.E) {
            entityplayer.aP();
        }

        return itemstack.b <= 0 ? new ItemStack(Items.ar) : itemstack;
    }

    public int d_(ItemStack itemstack) {
        return 32;
    }

    public EnumAction d(ItemStack itemstack) {
        return EnumAction.drink;
    }

    public ItemStack a(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        entityplayer.a(itemstack, this.d_(itemstack));
        return itemstack;
    }
}
