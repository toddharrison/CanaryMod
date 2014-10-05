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
    private boolean f;

    public MerchantRecipe(NBTTagCompound nbttagcompound) {
        this.a(nbttagcompound);
    }

    public MerchantRecipe(ItemStack itemstack, ItemStack itemstack1, ItemStack itemstack2) {
        this(itemstack, itemstack1, itemstack2, 0, 7);
    }

    public MerchantRecipe(ItemStack itemstack, ItemStack itemstack1, ItemStack itemstack2, int i0, int i1) {
        this.a = itemstack;
        this.b = itemstack1;
        this.c = itemstack2;
        this.d = i0;
        this.e = i1;
        this.f = true;
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

    public int e() {
        return this.d;
    }

    public int f() {
        return this.e;
    }

    public void g() {
        ++this.d;
    }

    public void a(int i0) {
        this.e += i0;
    }

    public boolean h() {
        return this.d >= this.e;
    }

    public boolean j() {
        return this.f;
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

        if (nbttagcompound.b("rewardExp", 1)) {
            this.f = nbttagcompound.n("rewardExp");
        }
        else {
            this.f = true;
        }

    }

    public NBTTagCompound k() {
        NBTTagCompound nbttagcompound = new NBTTagCompound();

        nbttagcompound.a("buy", (NBTBase) this.a.b(new NBTTagCompound()));
        nbttagcompound.a("sell", (NBTBase) this.c.b(new NBTTagCompound()));
        if (this.b != null) {
            nbttagcompound.a("buyB", (NBTBase) this.b.b(new NBTTagCompound()));
        }

        nbttagcompound.a("uses", this.d);
        nbttagcompound.a("maxUses", this.e);
        nbttagcompound.a("rewardExp", this.f);
        return nbttagcompound;
    }
}
