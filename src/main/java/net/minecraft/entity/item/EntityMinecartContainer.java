package net.minecraft.entity.item;


import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.LockCode;
import net.minecraft.world.World;

public abstract class EntityMinecartContainer extends EntityMinecart implements ILockableContainer {

    public ItemStack[] a = new ItemStack[36]; // CanaryMod: private -> public
    private boolean b = true;

    public EntityMinecartContainer(World world) {
        super(world);
    }

    public EntityMinecartContainer(World world, double d0, double d1, double d2) {
        super(world, d0, d1, d2);
    }

    public void a(DamageSource damagesource) {
        super.a(damagesource);
        InventoryHelper.a(this.o, (Entity) this, this);
    }

    public ItemStack a(int i0) {
        return this.a[i0];
    }

    public ItemStack a(int i0, int i1) {
        if (this.a[i0] != null) {
            ItemStack itemstack;

            if (this.a[i0].b <= i1) {
                itemstack = this.a[i0];
                this.a[i0] = null;
                return itemstack;
            }
            else {
                itemstack = this.a[i0].a(i1);
                if (this.a[i0].b == 0) {
                    this.a[i0] = null;
                }

                return itemstack;
            }
        }
        else {
            return null;
        }
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

    public void a(int i0, ItemStack itemstack) {
        this.a[i0] = itemstack;
        if (itemstack != null && itemstack.b > this.p_()) {
            itemstack.b = this.p_();
        }
    }

    public void o_() {
    }

    public boolean a(EntityPlayer entityplayer) {
        return this.I ? false : entityplayer.h(this) <= 64.0D;
    }

    public void b(EntityPlayer entityplayer) {
    }

    public void c(EntityPlayer entityplayer) {
    }

    public boolean b(int i0, ItemStack itemstack) {
        return true;
    }

    public String d_() {
        return this.k_() ? this.aL() : "container.minecart";
    }

    public int p_() {
        return 64;
    }

    public void c(int i0) {
        this.b = false;
        super.c(i0);
    }

    public void J() {
        if (this.b) {
            InventoryHelper.a(this.o, (Entity) this, this);
        }

        super.J();
    }

    protected void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i0 = 0; i0 < this.a.length; ++i0) {
            if (this.a[i0] != null) {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();

                nbttagcompound1.a("Slot", (byte) i0);
                this.a[i0].b(nbttagcompound1);
                nbttaglist.a((NBTBase) nbttagcompound1);
            }
        }

        nbttagcompound.a("Items", (NBTBase) nbttaglist);
    }

    protected void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        NBTTagList nbttaglist = nbttagcompound.c("Items", 10);

        this.a = new ItemStack[this.n_()];

        for (int i0 = 0; i0 < nbttaglist.c(); ++i0) {
            NBTTagCompound nbttagcompound1 = nbttaglist.b(i0);
            int i1 = nbttagcompound1.d("Slot") & 255;

            if (i1 >= 0 && i1 < this.a.length) {
                this.a[i1] = ItemStack.a(nbttagcompound1);
            }
        }

    }

    public boolean e(EntityPlayer entityplayer) {
        if (!this.o.D) {
            entityplayer.a((IInventory) this);
        }

        return true;
    }

    protected void o() {
        int i0 = 15 - Container.b((IInventory) this);
        float f0 = 0.98F + (float) i0 * 0.001F;

        this.v *= (double) f0;
        this.w *= 0.0D;
        this.x *= (double) f0;
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
        return false;
    }

    public void a(LockCode lockcode) {
    }

    public LockCode i() {
        return LockCode.a;
    }

    public void l() {
        for (int i0 = 0; i0 < this.a.length; ++i0) {
            this.a[i0] = null;
        }

    }
}
