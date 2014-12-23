package net.minecraft.inventory;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;


public class InventoryCrafting implements IInventory {

    public ItemStack[] a; // CanaryMod: private -> public
    private int b;
    private int c;
    private Container d;
    private String name = "container.crafting"; // CanaryMod: custom name

    public InventoryCrafting(Container container, int i0, int i1) {
        int i2 = i0 * i1;

        this.a = new ItemStack[i2];
        this.d = container;
        this.b = i0;
        this.c = i1;
    }

    public int n_() {
        return this.a.length;
    }

    public ItemStack a(int i0) {
        return i0 >= this.n_() ? null : this.a[i0];
    }

    public ItemStack c(int i0, int i1) {
        return i0 >= 0 && i0 < this.b && i1 >= 0 && i1 <= this.c ? this.a(i0 + i1 * this.b) : null;
    }

    public String d_() {
        return name; // CanaryMod: Return custom name
    }

    public boolean k_() {
        return false;
    }

    public IChatComponent e_() {
        return (IChatComponent) (this.k_() ? new ChatComponentText(this.d_()) : new ChatComponentTranslation(this.d_(), new Object[0]));
    }

    public ItemStack b(int i0) {
        if (this.a[i0] != null) {
            ItemStack itemstack = this.a[i0];

            this.a[i0] = null;
            return itemstack;
        }
        else {
            return null;
        }
    }

    public ItemStack a(int i0, int i1) {
        if (this.a[i0] != null) {
            ItemStack itemstack;

            if (this.a[i0].b <= i1) {
                itemstack = this.a[i0];
                this.a[i0] = null;
                this.d.a((IInventory) this);
                return itemstack;
            }
            else {
                itemstack = this.a[i0].a(i1);
                if (this.a[i0].b == 0) {
                    this.a[i0] = null;
                }

                this.d.a((IInventory) this);
                return itemstack;
            }
        }
        else {
            return null;
        }
    }

    public void a(int i0, ItemStack itemstack) {
        this.a[i0] = itemstack;
        this.d.a((IInventory) this);
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

    public int h() {
        return this.c;
    }

    public int i() {
        return this.b;
    }

    // CanaryMod
    public void setName(String value) {
        this.name = value;
    }
}
