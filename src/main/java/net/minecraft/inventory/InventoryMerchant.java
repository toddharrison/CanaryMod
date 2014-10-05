package net.minecraft.inventory;


import net.minecraft.entity.IMerchant;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;


public class InventoryMerchant implements IInventory {

    private final IMerchant a;
    private final EntityPlayer c;
    public ItemStack[] b = new ItemStack[3]; // CanaryMod: private -> public
    private MerchantRecipe d;
    private int e;
    private String name = "mob.villager"; // CanaryMod: custom inventory name

    public InventoryMerchant(EntityPlayer entityplayer, IMerchant imerchant) {
        this.c = entityplayer;
        this.a = imerchant;
    }

    public int n_() {
        return this.b.length;
    }

    public ItemStack a(int i0) {
        return this.b[i0];
    }

    public ItemStack a(int i0, int i1) {
        if (this.b[i0] != null) {
            ItemStack itemstack;

            if (i0 == 2) {
                itemstack = this.b[i0];
                this.b[i0] = null;
                return itemstack;
            }
            else if (this.b[i0].b <= i1) {
                itemstack = this.b[i0];
                this.b[i0] = null;
                if (this.e(i0)) {
                    this.h();
                }

                return itemstack;
            }
            else {
                itemstack = this.b[i0].a(i1);
                if (this.b[i0].b == 0) {
                    this.b[i0] = null;
                }

                if (this.e(i0)) {
                    this.h();
                }

                return itemstack;
            }
        }
        else {
            return null;
        }
    }

    private boolean e(int i0) {
        return i0 == 0 || i0 == 1;
    }

    public ItemStack b(int i0) {
        if (this.b[i0] != null) {
            ItemStack itemstack = this.b[i0];

            this.b[i0] = null;
            return itemstack;
        }
        else {
            return null;
        }
    }

    public void a(int i0, ItemStack itemstack) {
        this.b[i0] = itemstack;
        if (itemstack != null && itemstack.b > this.p_()) {
            itemstack.b = this.p_();
        }

        if (this.e(i0)) {
            this.h();
        }

    }

    public String d_() {
        return name; // CanaryMod: return name
    }

    public boolean k_() {
        return false;
    }

    public IChatComponent e_() {
        return (IChatComponent) (this.k_() ? new ChatComponentText(this.d_()) : new ChatComponentTranslation(this.d_(), new Object[0]));
    }

    public int p_() {
        return 64;
    }

    public boolean a(EntityPlayer entityplayer) {
        return this.a.u_() == entityplayer;
    }

    public void b(EntityPlayer entityplayer) {
    }

    public void c(EntityPlayer entityplayer) {
    }

    public boolean b(int i0, ItemStack itemstack) {
        return true;
    }

    public void o_() {
        this.h();
    }

    public void h() {
        this.d = null;
        ItemStack itemstack = this.b[0];
        ItemStack itemstack1 = this.b[1];

        if (itemstack == null) {
            itemstack = itemstack1;
            itemstack1 = null;
        }

        if (itemstack == null) {
            this.a(2, (ItemStack) null);
        }
        else {
            MerchantRecipeList merchantrecipelist = this.a.b_(this.c);

            if (merchantrecipelist != null) {
                MerchantRecipe merchantrecipe = merchantrecipelist.a(itemstack, itemstack1, this.e);

                if (merchantrecipe != null && !merchantrecipe.h()) {
                    this.d = merchantrecipe;
                    this.a(2, merchantrecipe.d().k());
                }
                else if (itemstack1 != null) {
                    merchantrecipe = merchantrecipelist.a(itemstack1, itemstack, this.e);
                    if (merchantrecipe != null && !merchantrecipe.h()) {
                        this.d = merchantrecipe;
                        this.a(2, merchantrecipe.d().k());
                    }
                    else {
                        this.a(2, (ItemStack) null);
                    }
                }
                else {
                    this.a(2, (ItemStack) null);
                }
            }
        }

        this.a.a_(this.a(2));
    }

    public MerchantRecipe i() {
        return this.d;
    }

    public void d(int i0) {
        this.e = i0;
        this.h();
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
        for (int i0 = 0; i0 < this.b.length; ++i0) {
            this.b[i0] = null;
        }

    }
}
