package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;


public class InventoryCraftResult implements IInventory {

    public ItemStack[] a = new ItemStack[1]; // CanaryMod: private -> public
    private String name = "Result"; // CanaryMod: changeable name

    public int n_() {
        return 1;
    }

    public ItemStack a(int i0) {
        return this.a[0];
    }

    public String d_() {
        return name; // CanaryMod: return name
    }

    public boolean k_() {
        return false;
    }

    public IChatComponent e_() {
        return (IChatComponent) (this.k_() ? new ChatComponentText(this.d_()) : new ChatComponentTranslation(this.d_(), new Object[0]));
    }

    public ItemStack a(int i0, int i1) {
        if (this.a[0] != null) {
            ItemStack itemstack = this.a[0];

            this.a[0] = null;
            return itemstack;
        }
        else {
            return null;
        }
    }

    public ItemStack b(int i0) {
        if (this.a[0] != null) {
            ItemStack itemstack = this.a[0];

            this.a[0] = null;
            return itemstack;
        }
        else {
            return null;
        }
    }

    public void a(int i0, ItemStack itemstack) {
        this.a[0] = itemstack;
    }

    public int p_() {
        return 64;
    }

    public void o_() {
    }

    public boolean a(EntityPlayer entityplayer) {
        return true;
    }

    public void b(EntityPlayer entityplayer) {
    }

    public void c(EntityPlayer entityplayer) {
    }

    public boolean b(int i0, ItemStack itemstack) {
        return true;
    }

    public int a_(int i0) {
        return 0;
    }

    public void b(int i0, int i1) {
    }

    public int g() {
        return 0;
    }

    public void l() {
        for (int i0 = 0; i0 < this.a.length; ++i0) {
            this.a[i0] = null;
        }

    }
}
