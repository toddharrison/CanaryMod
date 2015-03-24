package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityEnderChest;

public class InventoryEnderChest extends InventoryBasic {

    private TileEntityEnderChest a;

    public InventoryEnderChest() {
        super("container.enderchest", false, 27);
    }

    public void a(TileEntityEnderChest tileentityenderchest) {
        this.a = tileentityenderchest;
    }

    public void a(NBTTagList nbttaglist) {
        int i0;

        for (i0 = 0; i0 < this.n_(); ++i0) {
            this.a(i0, (ItemStack)null);
        }

        for (i0 = 0; i0 < nbttaglist.c(); ++i0) {
            NBTTagCompound nbttagcompound = nbttaglist.b(i0);
            int i1 = nbttagcompound.d("Slot") & 255;

            if (i1 >= 0 && i1 < this.n_()) {
                this.a(i1, ItemStack.a(nbttagcompound));
            }
        }
    }

    public NBTTagList h() {
        NBTTagList nbttaglist = new NBTTagList();

        for (int i0 = 0; i0 < this.n_(); ++i0) {
            ItemStack itemstack = this.a(i0);

            if (itemstack != null) {
                NBTTagCompound nbttagcompound = new NBTTagCompound();

                nbttagcompound.a("Slot", (byte)i0);
                itemstack.b(nbttagcompound);
                nbttaglist.a((NBTBase)nbttagcompound);
            }
        }

        return nbttaglist;
    }

    public boolean a(EntityPlayer entityplayer) {
        return this.a != null && !this.a.a(entityplayer) ? false : super.a(entityplayer);
    }

    public void b(EntityPlayer entityplayer) {
        if (this.a != null) {
            this.a.b();
        }

        super.b(entityplayer);
    }

    public void c(EntityPlayer entityplayer) {
        if (this.a != null) {
            this.a.d();
        }

        super.c(entityplayer);
        this.a = null;
    }

    // CanaryMod
    public TileEntityEnderChest getEnderChest() {
        return this.a;
    }
}
