package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.tileentity.TileEntityBrewingStand;

public class ContainerBrewingStand extends Container {

    private final Slot f;
    private IInventory a;
    private int g;

    public ContainerBrewingStand(InventoryPlayer inventoryplayer, IInventory iinventory) {
        this.a = iinventory;
        this.a((Slot)(new ContainerBrewingStand.Potion(inventoryplayer.d, iinventory, 0, 56, 46)));
        this.a((Slot)(new ContainerBrewingStand.Potion(inventoryplayer.d, iinventory, 1, 79, 53)));
        this.a((Slot)(new ContainerBrewingStand.Potion(inventoryplayer.d, iinventory, 2, 102, 46)));
        this.f = this.a((Slot)(new ContainerBrewingStand.Ingredient(iinventory, 3, 79, 17)));

        int i0;

        for (i0 = 0; i0 < 3; ++i0) {
            for (int i1 = 0; i1 < 9; ++i1) {
                this.a(new Slot(inventoryplayer, i1 + i0 * 9 + 9, 8 + i1 * 18, 84 + i0 * 18));
            }
        }

        for (i0 = 0; i0 < 9; ++i0) {
            this.a(new Slot(inventoryplayer, i0, 8 + i0 * 18, 142));
        }

        this.inventory = ((TileEntityBrewingStand)a).getCanaryBrewingStand(); // CanaryMod: Set inventory instance
    }

    public void a(ICrafting icrafting) {
        super.a(icrafting);
        icrafting.a(this, this.a);
    }

    public void b() {
        super.b();

        for (int i0 = 0; i0 < this.e.size(); ++i0) {
            ICrafting icrafting = (ICrafting)this.e.get(i0);

            if (this.g != this.a.a_(0)) {
                icrafting.a(this, 0, this.a.a_(0));
            }
        }

        this.g = this.a.a_(0);
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
            if ((i0 < 0 || i0 > 2) && i0 != 3) {
                if (!this.f.e() && this.f.a(itemstack1)) {
                    if (!this.a(itemstack1, 3, 4, false)) {
                        return null;
                    }
                }
                else if (ContainerBrewingStand.Potion.b_(itemstack)) {
                    if (!this.a(itemstack1, 0, 3, false)) {
                        return null;
                    }
                }
                else if (i0 >= 4 && i0 < 31) {
                    if (!this.a(itemstack1, 31, 40, false)) {
                        return null;
                    }
                }
                else if (i0 >= 31 && i0 < 40) {
                    if (!this.a(itemstack1, 4, 31, false)) {
                        return null;
                    }
                }
                else if (!this.a(itemstack1, 4, 40, false)) {
                    return null;
                }
            }
            else {
                if (!this.a(itemstack1, 4, 40, true)) {
                    return null;
                }

                slot.a(itemstack1, itemstack);
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

    public static class Potion extends Slot { //CanaryMod: package-private => public

        private EntityPlayer a;

        public Potion(EntityPlayer p_i1804_1_, IInventory p_i1804_2_, int p_i1804_3_, int p_i1804_4_, int p_i1804_5_) {
            super(p_i1804_2_, p_i1804_3_, p_i1804_4_, p_i1804_5_);
            this.a = p_i1804_1_;
        }

        public static boolean b_(ItemStack p_b__0_) {
            return p_b__0_ != null && (p_b__0_.b() == Items.bz || p_b__0_.b() == Items.bA);
        }

        public boolean a(ItemStack p_a_1_) {
            return b_(p_a_1_);
        }

        public int a() {
            return 1;
        }

        public void a(EntityPlayer p_a_1_, ItemStack p_a_2_) {
            if (p_a_2_.b() == Items.bz && p_a_2_.i() > 0) {
                this.a.b((StatBase)AchievementList.B);
            }

            super.a(p_a_1_, p_a_2_);
        }
    }

    public class Ingredient extends Slot { //CanaryMod: package-private => public

        public Ingredient(IInventory p_i1803_2_, int p_i1803_3_, int p_i1803_4_, int p_i1803_5_) {
            super(p_i1803_2_, p_i1803_3_, p_i1803_4_, p_i1803_5_);
        }

        public boolean a(ItemStack p_a_1_) {
            return p_a_1_ != null ? p_a_1_.b().l(p_a_1_) : false;
        }

        public int a() {
            return 64;
        }
    }
}
