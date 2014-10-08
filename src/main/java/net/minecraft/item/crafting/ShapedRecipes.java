package net.minecraft.item.crafting;

import net.canarymod.api.inventory.recipes.CanaryShapedRecipe;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ShapedRecipes implements IRecipe {

    private final int a;
    private final int b;
    private final ItemStack[] c;
    private final ItemStack d;
    private boolean e;
    private final CanaryShapedRecipe canary_recipe; // CanaryMod: recipe instance

    public ShapedRecipes(int i0, int i1, ItemStack[] aitemstack, ItemStack itemstack) {
        this.a = i0;
        this.b = i1;
        this.c = aitemstack;
        this.d = itemstack;
        this.canary_recipe = new CanaryShapedRecipe(this); // CanaryMod: wrap recipe
    }

    public ItemStack b() {
        return this.d;
    }

    public ItemStack[] b(InventoryCrafting inventorycrafting) {
        ItemStack[] aitemstack = new ItemStack[inventorycrafting.n_()];

        for (int i0 = 0; i0 < aitemstack.length; ++i0) {
            ItemStack itemstack = inventorycrafting.a(i0);

            if (itemstack != null && itemstack.b().r()) {
                aitemstack[i0] = new ItemStack(itemstack.b().q());
            }
        }

        return aitemstack;
    }

    public boolean a(InventoryCrafting inventorycrafting, World world) {
        for (int i0 = 0; i0 <= 3 - this.a; ++i0) {
            for (int i1 = 0; i1 <= 3 - this.b; ++i1) {
                if (this.a(inventorycrafting, i0, i1, true)) {
                    return true;
                }

                if (this.a(inventorycrafting, i0, i1, false)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean a(InventoryCrafting inventorycrafting, int i0, int i1, boolean flag0) {
        for (int i2 = 0; i2 < 3; ++i2) {
            for (int i3 = 0; i3 < 3; ++i3) {
                int i4 = i2 - i0;
                int i5 = i3 - i1;
                ItemStack itemstack = null;

                if (i4 >= 0 && i5 >= 0 && i4 < this.a && i5 < this.b) {
                    if (flag0) {
                        itemstack = this.c[this.a - i4 - 1 + i5 * this.a];
                    }
                    else {
                        itemstack = this.c[i4 + i5 * this.a];
                    }
                }

                ItemStack itemstack1 = inventorycrafting.c(i2, i3);

                if (itemstack1 != null || itemstack != null) {
                    if (itemstack1 == null && itemstack != null || itemstack1 != null && itemstack == null) {
                        return false;
                    }

                    if (itemstack.b() != itemstack1.b()) {
                        return false;
                    }

                    if (itemstack.i() != 32767 && itemstack.i() != itemstack1.i()) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public ItemStack a(InventoryCrafting inventorycrafting) {
        ItemStack itemstack = this.b().k();

        if (this.e) {
            for (int i0 = 0; i0 < inventorycrafting.n_(); ++i0) {
                ItemStack itemstack1 = inventorycrafting.a(i0);

                if (itemstack1 != null && itemstack1.n()) {
                    itemstack.d((NBTTagCompound)itemstack1.o().b());
                }
            }
        }

        return itemstack;
    }

    public int a() {
        return this.a * this.b;
    }

    public ShapedRecipes c() {
        this.e = true;
        return this;
    }

    // CanaryMod
    public int getWidth() {
        return this.a;
    }

    public int getHeight() {
        return this.b;
    }

    public ItemStack[] getRecipeItems() {
        return this.c;
    }

    public CanaryShapedRecipe getCanaryRecipe() {
        return canary_recipe;
    }
    //
}
