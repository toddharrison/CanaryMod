package net.minecraft.inventory;

import com.google.common.collect.Lists;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;

import java.util.List;

public class InventoryBasic implements IInventory {

    private String a;
    private int b;
    public ItemStack[] c; // CanaryMod: private -> public
    private List d;
    private boolean e;

    public InventoryBasic(String s0, boolean flag0, int i0) {
        this.a = s0;
        this.e = flag0;
        this.b = i0;
        this.c = new ItemStack[i0];
    }

    public void a(IInvBasic iinvbasic) {
        if (this.d == null) {
            this.d = Lists.newArrayList();
        }

        this.d.add(iinvbasic);
    }

    public void b(IInvBasic iinvbasic) {
        this.d.remove(iinvbasic);
    }

    public ItemStack a(int i0) {
        return i0 >= 0 && i0 < this.c.length ? this.c[i0] : null;
    }

    public ItemStack a(int i0, int i1) {
        if (this.c[i0] != null) {
            ItemStack itemstack;

            if (this.c[i0].b <= i1) {
                itemstack = this.c[i0];
                this.c[i0] = null;
                this.o_();
                return itemstack;
            }
            else {
                itemstack = this.c[i0].a(i1);
                if (this.c[i0].b == 0) {
                    this.c[i0] = null;
                }

                this.o_();
                return itemstack;
            }
        }
        else {
            return null;
        }
    }

    public ItemStack a(ItemStack itemstack) {
        ItemStack itemstack1 = itemstack.k();

        for (int i0 = 0; i0 < this.b; ++i0) {
            ItemStack itemstack2 = this.a(i0);

            if (itemstack2 == null) {
                this.a(i0, itemstack1);
                this.o_();
                return null;
            }

            if (ItemStack.c(itemstack2, itemstack1)) {
                int i1 = Math.min(this.p_(), itemstack2.c());
                int i2 = Math.min(itemstack1.b, i1 - itemstack2.b);

                if (i2 > 0) {
                    itemstack2.b += i2;
                    itemstack1.b -= i2;
                    if (itemstack1.b <= 0) {
                        this.o_();
                        return null;
                    }
                }
            }
        }

        if (itemstack1.b != itemstack.b) {
            this.o_();
        }

        return itemstack1;
    }

    public ItemStack b(int i0) {
        if (this.c[i0] != null) {
            ItemStack itemstack = this.c[i0];

            this.c[i0] = null;
            return itemstack;
        }
        else {
            return null;
        }
    }

    public void a(int i0, ItemStack itemstack) {
        this.c[i0] = itemstack;
        if (itemstack != null && itemstack.b > this.p_()) {
            itemstack.b = this.p_();
        }

        this.o_();
    }

    public int n_() {
        return this.b;
    }

    public String d_() {
        return this.a;
    }

    public boolean k_() {
        return this.e;
    }

    public void a(String s0) {
        this.e = true;
        this.a = s0;
    }

    public IChatComponent e_() {
        return (IChatComponent)(this.k_() ? new ChatComponentText(this.d_()) : new ChatComponentTranslation(this.d_(), new Object[0]));
    }

    public int p_() {
        return 64;
    }

    public void o_() {
        if (this.d != null) {
            for (int i0 = 0; i0 < this.d.size(); ++i0) {
                ((IInvBasic)this.d.get(i0)).a(this);
            }
        }
    }

    public boolean a(EntityPlayer entityplayer) {
        return true;
    }

    public void b(EntityPlayer entityplayer) {
    }

    public void c(EntityPlayer entityplayer) {
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

    public void l() {
        for (int i0 = 0; i0 < this.c.length; ++i0) {
            this.c[i0] = null;
        }
    }

    public void setName(String value) {
        this.a = value;
        this.e = false; // i don't recall what the setting is for localized name...
    }
}
