package net.minecraft.tileentity;

import net.canarymod.api.world.blocks.CanaryDispenser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerDispenser;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.Random;

public class TileEntityDispenser extends TileEntityLockable implements IInventory {

    private static final Random f = new Random();
    public ItemStack[] g = new ItemStack[9]; // CanaryMod: private => public
    protected String a;

    public TileEntityDispenser() {
        this.complexBlock = new CanaryDispenser(this); // CanaryMod: create once, use forever
    }

    public int n_() {
        return 9;
    }

    public ItemStack a(int i0) {
        return this.g[i0];
    }

    public ItemStack a(int i0, int i1) {
        if (this.g[i0] != null) {
            ItemStack itemstack;

            if (this.g[i0].b <= i1) {
                itemstack = this.g[i0];
                this.g[i0] = null;
                this.o_();
                return itemstack;
            }
            else {
                itemstack = this.g[i0].a(i1);
                if (this.g[i0].b == 0) {
                    this.g[i0] = null;
                }

                this.o_();
                return itemstack;
            }
        }
        else {
            return null;
        }
    }

    public ItemStack b(int i0) {
        if (this.g[i0] != null) {
            ItemStack itemstack = this.g[i0];

            this.g[i0] = null;
            return itemstack;
        }
        else {
            return null;
        }
    }

    public int m() {
        int i0 = -1;
        int i1 = 1;

        for (int i2 = 0; i2 < this.g.length; ++i2) {
            if (this.g[i2] != null && f.nextInt(i1++) == 0) {
                i0 = i2;
            }
        }

        return i0;
    }

    public void a(int i0, ItemStack itemstack) {
        this.g[i0] = itemstack;
        if (itemstack != null && itemstack.b > this.p_()) {
            itemstack.b = this.p_();
        }

        this.o_();
    }

    public int a(ItemStack itemstack) {
        for (int i0 = 0; i0 < this.g.length; ++i0) {
            if (this.g[i0] == null || this.g[i0].b() == null) {
                this.a(i0, itemstack);
                return i0;
            }
        }

        return -1;
    }

    public String d_() {
        return this.k_() ? this.a : "container.dispenser";
    }

    public void a(String s0) {
        this.a = s0;
    }

    public boolean k_() {
        return this.a != null;
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        NBTTagList nbttaglist = nbttagcompound.c("Items", 10);

        this.g = new ItemStack[this.n_()];

        for (int i0 = 0; i0 < nbttaglist.c(); ++i0) {
            NBTTagCompound nbttagcompound1 = nbttaglist.b(i0);
            int i1 = nbttagcompound1.d("Slot") & 255;

            if (i1 >= 0 && i1 < this.g.length) {
                this.g[i1] = ItemStack.a(nbttagcompound1);
            }
        }

        if (nbttagcompound.b("CustomName", 8)) {
            this.a = nbttagcompound.j("CustomName");
        }
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i0 = 0; i0 < this.g.length; ++i0) {
            if (this.g[i0] != null) {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();

                nbttagcompound1.a("Slot", (byte)i0);
                this.g[i0].b(nbttagcompound1);
                nbttaglist.a((NBTBase)nbttagcompound1);
            }
        }

        nbttagcompound.a("Items", (NBTBase)nbttaglist);
        if (this.k_()) {
            nbttagcompound.a("CustomName", this.a);
        }
    }

    public int p_() {
        return 64;
    }

    public boolean a(EntityPlayer entityplayer) {
        // CanaryMod: remote inventories
        if (getCanaryDispenser().canOpenRemote()) {
            return true;
        }
        //

        return this.b.s(this.c) != this ? false : entityplayer.e((double)this.c.n() + 0.5D, (double)this.c.o() + 0.5D, (double)this.c.p() + 0.5D) <= 64.0D;
    }

    public void b(EntityPlayer entityplayer) {
    }

    public void c(EntityPlayer entityplayer) {
    }

    public boolean b(int i0, ItemStack itemstack) {
        return true;
    }

    public String k() {
        return "minecraft:dispenser";
    }

    public Container a(InventoryPlayer inventoryplayer, EntityPlayer entityplayer) {
        return new ContainerDispenser(inventoryplayer, this);
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
        for (int i0 = 0; i0 < this.g.length; ++i0) {
            this.g[i0] = null;
        }
    }

    // CanaryMod
    public CanaryDispenser getCanaryDispenser() {
        return (CanaryDispenser)complexBlock;
    }
    //
}
