package net.minecraft.village;


import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

public class MerchantRecipe {

    // CanaryMod: private => public
    public ItemStack a;
    public ItemStack b;
    public ItemStack c;
    //
    private int d;
    private int e;

    public MerchantRecipe(NBTTagCompound nbttagcompound) {
        this.a(nbttagcompound);
    }

    public MerchantRecipe(ItemStack itemstack, ItemStack itemstack1, ItemStack itemstack2) {
        this.a = itemstack;
        this.b = itemstack1;
        this.c = itemstack2;
        this.e = 7;
    }

    public MerchantRecipe(ItemStack itemstack, ItemStack itemstack1) {
        this(itemstack, (ItemStack) null, itemstack1);
    }

    public MerchantRecipe(ItemStack itemstack, Item item) {
        this(itemstack, new ItemStack(item));
    }

    public ItemStack a() {
        return this.a;
    }

    public ItemStack b() {
        return this.b;
    }

    public boolean c() {
        return this.b != null;
    }

    public ItemStack d() {
        return this.c;
    }

    public boolean a(MerchantRecipe merchantrecipe) {
        return this.a.b() == merchantrecipe.a.b() && this.c.b() == merchantrecipe.c.b() ? this.b == null && merchantrecipe.b == null || this.b != null && merchantrecipe.b != null && this.b.b() == merchantrecipe.b.b() : false;
    }

    public boolean b(MerchantRecipe merchantrecipe) {
        return this.a(merchantrecipe) && (this.a.b < merchantrecipe.a.b || this.b != null && this.b.b < merchantrecipe.b.b);
    }

    public void f() {
        ++this.d;
    }

    public void a(int i0) {
        this.e += i0;
    }

    public boolean g() {
        return this.d >= this.e;
    }

    public void a(NBTTagCompound nbttagcompound) {
        NBTTagCompound nbttagcompound1 = nbttagcompound.m("buy");

        this.a = ItemStack.a(nbttagcompound1);
        NBTTagCompound nbttagcompound2 = nbttagcompound.m("sell");

        this.c = ItemStack.a(nbttagcompound2);
        if (nbttagcompound.b("buyB", 10)) {
            this.b = ItemStack.a(nbttagcompound.m("buyB"));
        }

        if (nbttagcompound.b("uses", 99)) {
            this.d = nbttagcompound.f("uses");
        }

        if (nbttagcompound.b("maxUses", 99)) {
            this.e = nbttagcompound.f("maxUses");
        }
        else {
            this.e = 7;
        }
    }

    public NBTTagCompound i() {
        NBTTagCompound nbttagcompound = new NBTTagCompound();

        nbttagcompound.a("buy", (NBTBase) this.a.b(new NBTTagCompound()));
        nbttagcompound.a("sell", (NBTBase) this.c.b(new NBTTagCompound()));
        if (this.b != null) {
            nbttagcompound.a("buyB", (NBTBase) this.b.b(new NBTTagCompound()));
        }

        nbttagcompound.a("uses", this.d);
        nbttagcompound.a("maxUses", this.e);
        return nbttagcompound;
    }
}
