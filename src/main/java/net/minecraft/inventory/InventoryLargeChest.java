package net.minecraft.inventory;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class InventoryLargeChest implements IInventory {

    private String a;
    public IInventory b; // CanaryMod: private -> public
    public IInventory c; // CanaryMod: private -> public

    public InventoryLargeChest(String s0, IInventory iinventory, IInventory iinventory1) {
        this.a = s0;
        if (iinventory == null) {
            iinventory = iinventory1;
        }

        if (iinventory1 == null) {
            iinventory1 = iinventory;
        }

        this.b = iinventory;
        this.c = iinventory1;
    }

    public int a() {
        return this.b.a() + this.c.a();
    }

    public boolean a(IInventory iinventory) {
        return this.b == iinventory || this.c == iinventory;
    }

    public String b() {
        return this.b.k_() ? this.b.b() : (this.c.k_() ? this.c.b() : this.a);
    }

    public boolean k_() {
        return this.b.k_() || this.c.k_();
    }

    public ItemStack a(int i0) {
        return i0 >= this.b.a() ? this.c.a(i0 - this.b.a()) : this.b.a(i0);
    }

    public ItemStack a(int i0, int i1) {
        return i0 >= this.b.a() ? this.c.a(i0 - this.b.a(), i1) : this.b.a(i0, i1);
    }

    public ItemStack a_(int i0) {
        return i0 >= this.b.a() ? this.c.a_(i0 - this.b.a()) : this.b.a_(i0);
    }

    public void a(int i0, ItemStack itemstack) {
        if (i0 >= this.b.a()) {
            this.c.a(i0 - this.b.a(), itemstack);
        }
        else {
            this.b.a(i0, itemstack);
        }
    }

    public int d() {
        return this.b.d();
    }

    public void e() {
        this.b.e();
        this.c.e();
    }

    public boolean a(EntityPlayer entityplayer) {
        return this.b.a(entityplayer) && this.c.a(entityplayer);
    }

    public void f() {
        this.b.f();
        this.c.f();
    }

    public void l_() {
        this.b.l_();
        this.c.l_();
    }

    public boolean b(int i0, ItemStack itemstack) {
        return true;
    }
}
