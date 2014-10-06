package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityBeacon;

public class ContainerBeacon extends Container {

    private final ContainerBeacon.BeaconSlot f;
    private IInventory a;

    public ContainerBeacon(IInventory iinventory, IInventory iinventory1) {
        this.a = iinventory1;
        this.a((Slot)(this.f = new ContainerBeacon.BeaconSlot(iinventory1, 0, 136, 110)));
        byte b0 = 36;
        short short1 = 137;

        int i0;

        for (i0 = 0; i0 < 3; ++i0) {
            for (int i1 = 0; i1 < 9; ++i1) {
                this.a(new Slot(iinventory, i1 + i0 * 9 + 9, b0 + i1 * 18, short1 + i0 * 18));
            }
        }

        for (i0 = 0; i0 < 9; ++i0) {
            this.a(new Slot(iinventory, i0, b0 + i0 * 18, 58 + short1));
        }
        this.inventory = ((TileEntityBeacon)a).getCanaryBeacon(); // CanaryMod: Set inventory instance
    }

    public void a(ICrafting icrafting) {
        super.a(icrafting);
        icrafting.a(this, this.a);
    }

    public IInventory e() {
        return this.a;
    }

    public boolean a(EntityPlayer entityplayer) {
        return this.a.a(entityplayer);
    }

    public ItemStack b(EntityPlayer entityplayer, int i0) {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.c.get(i0);

        if (slot != null && slot.e()) {
            ItemStack itemstack1 = slot.d();

            itemstack = itemstack1.k();
            if (i0 == 0) {
                if (!this.a(itemstack1, 1, 37, true)) {
                    return null;
                }

                slot.a(itemstack1, itemstack);
            }
            else if (!this.f.e() && this.f.a(itemstack1) && itemstack1.b == 1) {
                if (!this.a(itemstack1, 0, 1, false)) {
                    return null;
                }
            }
            else if (i0 >= 1 && i0 < 28) {
                if (!this.a(itemstack1, 28, 37, false)) {
                    return null;
                }
            }
            else if (i0 >= 28 && i0 < 37) {
                if (!this.a(itemstack1, 1, 28, false)) {
                    return null;
                }
            }
            else if (!this.a(itemstack1, 1, 37, false)) {
                return null;
            }

            if (itemstack1.b == 0) {
                slot.d((ItemStack)null);
            }
            else {
                slot.f();
            }

            if (itemstack1.b == itemstack.b) {
                return null;
            }

            slot.a(entityplayer, itemstack1);
        }

        return itemstack;
    }

    public class BeaconSlot extends Slot { // CanaryMod package-private => public

        public BeaconSlot(IInventory iinventory, int i0, int i1, int i2) {
            super(iinventory, i0, i1, i2);
        }

        public boolean a(ItemStack itemstack) {
            return itemstack == null ? false : itemstack.b() == Items.bO || itemstack.b() == Items.i || itemstack.b() == Items.k || itemstack.b() == Items.j;
        }

        public int a() {
            return 1;
        }
    }
}
