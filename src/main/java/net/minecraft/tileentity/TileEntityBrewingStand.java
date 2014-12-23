package net.minecraft.tileentity;

import net.canarymod.api.world.blocks.CanaryBrewingStand;
import net.minecraft.block.BlockBrewingStand;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerBrewingStand;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.PotionHelper;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.util.EnumFacing;

import java.util.Arrays;
import java.util.List;

public class TileEntityBrewingStand extends TileEntityLockable implements IUpdatePlayerListBox, ISidedInventory {

    private static final int[] a = new int[]{ 3 };
    private static final int[] f = new int[]{ 0, 1, 2 };
    public ItemStack[] g = new ItemStack[4]; // CanaryMod: private => public
    private int h;
    private boolean[] i;
    private Item j;
    private String k;

    public TileEntityBrewingStand() {
        this.complexBlock = new CanaryBrewingStand(this); // CanaryMod: create once, use forever
    }

    public String d_() {
        return this.k_() ? this.k : "container.brewing";
    }

    public boolean k_() {
        return this.k != null && this.k.length() > 0;
    }

    public void a(String s0) {
        this.k = s0;
    }

    public int n_() {
        return this.g.length;
    }

    public void c() {
        if (this.h > 0) {
            --this.h;
            if (this.h == 0) {
                this.o();
                this.o_();
            }
            else if (!this.n()) {
                this.h = 0;
                this.o_();
            }
            else if (this.j != this.g[3].b()) {
                this.h = 0;
                this.o_();
            }
        }
        else if (this.n()) {
            this.h = 400;
            this.j = this.g[3].b();
        }

        if (!this.b.D) {
            boolean[] aboolean = this.m();

            if (!Arrays.equals(aboolean, this.i)) {
                this.i = aboolean;
                IBlockState iblockstate = this.b.p(this.v());

                if (!(iblockstate.c() instanceof BlockBrewingStand)) {
                    return;
                }

                for (int i0 = 0; i0 < BlockBrewingStand.a.length; ++i0) {
                    iblockstate = iblockstate.a(BlockBrewingStand.a[i0], Boolean.valueOf(aboolean[i0]));
                }

                this.b.a(this.c, iblockstate, 2);
            }
        }
    }

    private boolean n() {
        if (this.g[3] != null && this.g[3].b > 0) {
            ItemStack itemstack = this.g[3];

            if (!itemstack.b().l(itemstack)) {
                return false;
            }
            else {
                boolean flag0 = false;

                for (int i0 = 0; i0 < 3; ++i0) {
                    if (this.g[i0] != null && this.g[i0].b() == Items.bz) {
                        int i1 = this.g[i0].i();
                        int i2 = this.c(i1, itemstack);

                        if (!ItemPotion.f(i1) && ItemPotion.f(i2)) {
                            flag0 = true;
                            break;
                        }

                        List list = Items.bz.e(i1);
                        List list1 = Items.bz.e(i2);

                        if ((i1 <= 0 || list != list1) && (list == null || !list.equals(list1) && list1 != null) && i1 != i2) {
                            flag0 = true;
                            break;
                        }
                    }
                }

                return flag0;
            }
        }
        else {
            return false;
        }
    }

    private void o() {
        if (this.n()) {
            ItemStack itemstack = this.g[3];

            for (int i0 = 0; i0 < 3; ++i0) {
                if (this.g[i0] != null && this.g[i0].b() == Items.bz) {
                    int i1 = this.g[i0].i();
                    int i2 = this.c(i1, itemstack);
                    List list = Items.bz.e(i1);
                    List list1 = Items.bz.e(i2);

                    if ((i1 <= 0 || list != list1) && (list == null || !list.equals(list1) && list1 != null)) {
                        if (i1 != i2) {
                            this.g[i0].b(i2);
                        }
                    }
                    else if (!ItemPotion.f(i1) && ItemPotion.f(i2)) {
                        this.g[i0].b(i2);
                    }
                }
            }

            if (itemstack.b().r()) {
                this.g[3] = new ItemStack(itemstack.b().q());
            }
            else {
                --this.g[3].b;
                if (this.g[3].b <= 0) {
                    this.g[3] = null;
                }
            }
        }
    }

    private int c(int i0, ItemStack itemstack) {
        return itemstack == null ? i0 : (itemstack.b().l(itemstack) ? PotionHelper.a(i0, itemstack.b().j(itemstack)) : i0);
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        NBTTagList nbttaglist = nbttagcompound.c("Items", 10);

        this.g = new ItemStack[this.n_()];

        for (int i0 = 0; i0 < nbttaglist.c(); ++i0) {
            NBTTagCompound nbttagcompound1 = nbttaglist.b(i0);
            byte b0 = nbttagcompound1.d("Slot");

            if (b0 >= 0 && b0 < this.g.length) {
                this.g[b0] = ItemStack.a(nbttagcompound1);
            }
        }

        this.h = nbttagcompound.e("BrewTime");
        if (nbttagcompound.b("CustomName", 8)) {
            this.k = nbttagcompound.j("CustomName");
        }
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("BrewTime", (short)this.h);
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
            nbttagcompound.a("CustomName", this.k);
        }
    }

    public ItemStack a(int i0) {
        return i0 >= 0 && i0 < this.g.length ? this.g[i0] : null;
    }

    public ItemStack a(int i0, int i1) {
        if (i0 >= 0 && i0 < this.g.length) {
            ItemStack itemstack = this.g[i0];

            this.g[i0] = null;
            return itemstack;
        }
        else {
            return null;
        }
    }

    public ItemStack b(int i0) {
        if (i0 >= 0 && i0 < this.g.length) {
            ItemStack itemstack = this.g[i0];

            this.g[i0] = null;
            return itemstack;
        }
        else {
            return null;
        }
    }

    public void a(int i0, ItemStack itemstack) {
        if (i0 >= 0 && i0 < this.g.length) {
            this.g[i0] = itemstack;
        }
    }

    public int p_() {
        return 64;
    }

    public boolean a(EntityPlayer entityplayer) {
        return this.b.s(this.c) != this ? false : entityplayer.e((double)this.c.n() + 0.5D, (double)this.c.o() + 0.5D, (double)this.c.p() + 0.5D) <= 64.0D;
    }

    public void b(EntityPlayer entityplayer) {
    }

    public void c(EntityPlayer entityplayer) {
    }

    public boolean b(int i0, ItemStack itemstack) {
        return i0 == 3 ? itemstack.b().l(itemstack) : itemstack.b() == Items.bz || itemstack.b() == Items.bA;
    }

    public boolean[] m() {
        boolean[] aboolean = new boolean[3];

        for (int i0 = 0; i0 < 3; ++i0) {
            if (this.g[i0] != null) {
                aboolean[i0] = true;
            }
        }

        return aboolean;
    }

    public int[] a(EnumFacing enumfacing) {
        return enumfacing == EnumFacing.UP ? a : f;
    }

    public boolean a(int i0, ItemStack itemstack, EnumFacing enumfacing) {
        return this.b(i0, itemstack);
    }

    public boolean b(int i0, ItemStack itemstack, EnumFacing enumfacing) {
        return true;
    }

    public String k() {
        return "minecraft:brewing_stand";
    }

    public Container a(InventoryPlayer inventoryplayer, EntityPlayer entityplayer) {
        return new ContainerBrewingStand(inventoryplayer, this);
    }

    public int a_(int i0) {
        switch (i0) {
            case 0:
                return this.h;

            default:
                return 0;
        }
    }

    public void b(int i0, int i1) {
        switch (i0) {
            case 0:
                this.h = i1;

            default:
        }
    }

    public int g() {
        return 1;
    }

    public void l() {
        for (int i0 = 0; i0 < this.g.length; ++i0) {
            this.g[i0] = null;
        }
    }

    // CanaryMod
    public CanaryBrewingStand getCanaryBrewingStand() {
        return (CanaryBrewingStand)complexBlock;
    }
}
