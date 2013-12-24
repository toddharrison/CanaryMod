package net.minecraft.tileentity;

import net.canarymod.api.world.blocks.CanaryDispenser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.Random;

public class TileEntityDispenser extends TileEntity implements IInventory {

    public ItemStack[] i = new ItemStack[9]; // CanaryMod: private => public
    private Random j = new Random();
    protected String a;

    public TileEntityDispenser() {
        this.complexBlock = new CanaryDispenser(this); // CanaryMod: create once, use forever
    }

    public int a() {
        return 9;
    }

    public ItemStack a(int i0) {
        return this.i[i0];
    }

    public ItemStack a(int i0, int i1) {
        if (this.i[i0] != null) {
            ItemStack itemstack;

            if (this.i[i0].b <= i1) {
                itemstack = this.i[i0];
                this.i[i0] = null;
                this.e();
                return itemstack;
            }
            else {
                itemstack = this.i[i0].a(i1);
                if (this.i[i0].b == 0) {
                    this.i[i0] = null;
                }

                this.e();
                return itemstack;
            }
        }
        else {
            return null;
        }
    }

    public ItemStack a_(int i0) {
        if (this.i[i0] != null) {
            ItemStack itemstack = this.i[i0];

            this.i[i0] = null;
            return itemstack;
        }
        else {
            return null;
        }
    }

    public int i() {
        int i0 = -1;
        int i1 = 1;

        for (int i2 = 0; i2 < this.i.length; ++i2) {
            if (this.i[i2] != null && this.j.nextInt(i1++) == 0) {
                i0 = i2;
            }
        }

        return i0;
    }

    public void a(int i0, ItemStack itemstack) {
        this.i[i0] = itemstack;
        if (itemstack != null && itemstack.b > this.d()) {
            itemstack.b = this.d();
        }

        this.e();
    }

    public int a(ItemStack itemstack) {
        for (int i0 = 0; i0 < this.i.length; ++i0) {
            if (this.i[i0] == null || this.i[i0].b() == null) {
                this.a(i0, itemstack);
                return i0;
            }
        }

        return -1;
    }

    public String b() {
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

        this.i = new ItemStack[this.a()];

        for (int i0 = 0; i0 < nbttaglist.c(); ++i0) {
            NBTTagCompound nbttagcompound1 = nbttaglist.b(i0);
            int i1 = nbttagcompound1.d("Slot") & 255;

            if (i1 >= 0 && i1 < this.i.length) {
                this.i[i1] = ItemStack.a(nbttagcompound1);
            }
        }

        if (nbttagcompound.b("CustomName", 8)) {
            this.a = nbttagcompound.j("CustomName");
        }
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i0 = 0; i0 < this.i.length; ++i0) {
            if (this.i[i0] != null) {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();

                nbttagcompound1.a("Slot", (byte) i0);
                this.i[i0].b(nbttagcompound1);
                nbttaglist.a((NBTBase) nbttagcompound1);
            }
        }

        nbttagcompound.a("Items", (NBTBase) nbttaglist);
        if (this.k_()) {
            nbttagcompound.a("CustomName", this.a);
        }
    }

    public int d() {
        return 64;
    }

    public boolean a(EntityPlayer entityplayer) {
        // CanaryMod: remote inventories
        if (getCanaryDispenser().canOpenRemote()) {
            return true;
        }
        //

        return this.b.o(this.c, this.d, this.e) != this ? false : entityplayer.e((double) this.c + 0.5D, (double) this.d + 0.5D, (double) this.e + 0.5D) <= 64.0D;
    }

    public void f() {
    }

    public void l_() {
    }

    public boolean b(int i0, ItemStack itemstack) {
        return true;
    }

    // CanaryMod
    public CanaryDispenser getCanaryDispenser() {
        return (CanaryDispenser) complexBlock;
    }
    //
}
