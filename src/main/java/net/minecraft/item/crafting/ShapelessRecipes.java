package net.minecraft.item.crafting;

import com.google.common.collect.Lists;
import net.canarymod.api.inventory.recipes.CanaryShapelessRecipe;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ShapelessRecipes implements IRecipe {

    private final ItemStack a;
    private final List b;
    private final CanaryShapelessRecipe canary_recipe;

    public ShapelessRecipes(ItemStack itemstack, List list) {
        this.a = itemstack;
        this.b = list;
        this.canary_recipe = new CanaryShapelessRecipe(this);
    }

    public ItemStack b() {
        return this.a;
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
        ArrayList arraylist = Lists.newArrayList(this.b);

        for (int i0 = 0; i0 < inventorycrafting.h(); ++i0) {
            for (int i1 = 0; i1 < inventorycrafting.i(); ++i1) {
                ItemStack itemstack = inventorycrafting.c(i1, i0);

                if (itemstack != null) {
                    boolean flag0 = false;
                    Iterator iterator = arraylist.iterator();

                    while (iterator.hasNext()) {
                        ItemStack itemstack1 = (ItemStack)iterator.next();

                        if (itemstack.b() == itemstack1.b() && (itemstack1.i() == 32767 || itemstack.i() == itemstack1.i())) {
                            flag0 = true;
                            arraylist.remove(itemstack1);
                            break;
                        }
                    }

                    if (!flag0) {
                        return false;
                    }
                }
            }
        }

        return arraylist.isEmpty();
    }

    public ItemStack a(InventoryCrafting inventorycrafting) {
        return this.a.k();
    }

    public int a() {
        return this.b.size();
    }

    // CanaryMod
    @SuppressWarnings("unchecked")
    public List<ItemStack> getRecipeItems() {
        return this.b;
    }

    public CanaryShapelessRecipe getCanaryRecipe() {
        return canary_recipe;
    }
    //
}
