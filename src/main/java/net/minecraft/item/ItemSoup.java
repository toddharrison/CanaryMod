package net.minecraft.item;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.world.World;

public class ItemSoup extends ItemFood {

    public ItemSoup(int i0) {
        super(i0, false);
        this.e(1);
    }

    public ItemStack b(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        int tempAm = itemstack.b;

        super.b(itemstack, world, entityplayer);
        // CanaryMod: check if EatHook got canceled
        if (itemstack.b != tempAm) {
            return new ItemStack(Items.z);
        }
        //
        return itemstack;
    }
}
