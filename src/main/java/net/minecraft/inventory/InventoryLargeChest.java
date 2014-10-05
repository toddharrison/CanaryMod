package net.minecraft.inventory;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class InventoryLargeChest implements IInventory {

    private String a;
    public IInventory b; // CanaryMod: private -> public
    public IInventory c; // CanaryMod: private -> public
    private String a;

    public InventoryLargeChest(String s0, ILockableContainer ilockablecontainer, ILockableContainer ilockablecontainer1) {
        this.a = s0;
        if (ilockablecontainer == null) {
            ilockablecontainer = ilockablecontainer1;
        }

        if (ilockablecontainer1 == null) {
            ilockablecontainer1 = ilockablecontainer;
        }

        this.b = ilockablecontainer;
        this.c = ilockablecontainer1;
        if (ilockablecontainer.q_()) {
            ilockablecontainer1.a(ilockablecontainer.i());
        }
        else if (ilockablecontainer1.q_()) {
            ilockablecontainer.a(ilockablecontainer1.i());
        }

    }

    public int n_() {
        return this.b.n_() + this.c.n_();
    }

    public boolean a(IInventory iinventory) {
        return this.b == iinventory || this.c == iinventory;
    }

    public String d_() {
        return this.b.k_() ? this.b.d_() : (this.c.k_() ? this.c.d_() : this.a);
    }

    public boolean k_() {
        return this.b.k_() || this.c.k_();
    }

    public IChatComponent e_() {
        return (IChatComponent) (this.k_() ? new ChatComponentText(this.d_()) : new ChatComponentTranslation(this.d_(), new Object[0]));
    }

    public ItemStack a(int i0) {
        return i0 >= this.b.n_() ? this.c.a(i0 - this.b.n_()) : this.b.a(i0);
    }

    public ItemStack a(int i0, int i1) {
        return i0 >= this.b.n_() ? this.c.a(i0 - this.b.n_(), i1) : this.b.a(i0, i1);
    }

    public ItemStack b(int i0) {
        return i0 >= this.b.n_() ? this.c.b(i0 - this.b.n_()) : this.b.b(i0);
    }

    public void a(int i0, ItemStack itemstack) {
        if (i0 >= this.b.n_()) {
            this.c.a(i0 - this.b.n_(), itemstack);
        }
        else {
            this.b.a(i0, itemstack);
        }

    }

    public int p_() {
        return this.b.p_();
    }

    public void o_() {
        this.b.o_();
        this.c.o_();
    }

    public boolean a(EntityPlayer entityplayer) {
        return this.b.a(entityplayer) && this.c.a(entityplayer);
    }

    public void b(EntityPlayer entityplayer) {
        this.b.b(entityplayer);
        this.c.b(entityplayer);
    }

    public void c(EntityPlayer entityplayer) {
        this.b.c(entityplayer);
        this.c.c(entityplayer);
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

    public boolean q_() {
        return this.b.q_() || this.c.q_();
    }

    public void a(LockCode lockcode) {
        this.b.a(lockcode);
        this.c.a(lockcode);
    }

    public LockCode i() {
        return this.b.i();
    }

    public String k() {
        return this.b.k();
    }

    public Container a(InventoryPlayer inventoryplayer, EntityPlayer entityplayer) {
        return new ContainerChest(inventoryplayer, this, entityplayer);
    }

    public void l() {
        this.b.l();
        this.c.l();
    }
}
