package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IWorldNameable;

public interface IInventory extends IWorldNameable {

    int n_();

    ItemStack a(int i0);

    ItemStack a(int i0, int i1);

    ItemStack b(int i0);

    void a(int i0, ItemStack itemstack);

    int p_();

    void o_();

    boolean a(EntityPlayer entityplayer);

    void b(EntityPlayer entityplayer);

    void c(EntityPlayer entityplayer);

    boolean b(int i0, ItemStack itemstack);

    int a_(int i0);

    void b(int i0, int i1);

    int g();

    void l();
}
