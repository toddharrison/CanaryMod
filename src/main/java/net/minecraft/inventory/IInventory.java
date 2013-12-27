package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IInventory {

    int a();

    ItemStack a(int i0);

    ItemStack a(int i0, int i1);

    ItemStack a_(int i0);

    void a(int i0, ItemStack itemstack);

    String b();

    boolean k_();

    int d();

    void e();

    boolean a(EntityPlayer entityplayer);

    void f();

    void l_();

    boolean b(int i0, ItemStack itemstack);
}
