package net.minecraft.item.crafting;

import com.google.common.collect.Maps;
import net.canarymod.api.inventory.CanaryItem;
import net.canarymod.api.inventory.recipes.SmeltRecipe;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFishFood;
import net.minecraft.item.ItemStack;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class FurnaceRecipes {

    private static final FurnaceRecipes a = new FurnaceRecipes();
    private Map b = Maps.newHashMap();
    private Map c = Maps.newHashMap();

    public static FurnaceRecipes a() {
        return a;
    }

    private FurnaceRecipes() {
        this.a(Blocks.p, new ItemStack(Items.j), 0.7F);
        this.a(Blocks.o, new ItemStack(Items.k), 1.0F);
        this.a(Blocks.ag, new ItemStack(Items.i), 1.0F);
        this.a((Block)Blocks.m, new ItemStack(Blocks.w), 0.1F);
        this.a(Items.al, new ItemStack(Items.am), 0.35F);
        this.a(Items.bi, new ItemStack(Items.bj), 0.35F);
        this.a(Items.bk, new ItemStack(Items.bl), 0.35F);
        this.a(Items.bo, new ItemStack(Items.bp), 0.35F);
        this.a(Items.bm, new ItemStack(Items.bn), 0.35F);
        this.a(Blocks.e, new ItemStack(Blocks.b), 0.1F);
        this.a(new ItemStack(Blocks.bf, 1, BlockStoneBrick.b), new ItemStack(Blocks.bf, 1, BlockStoneBrick.N), 0.1F);
        this.a(Items.aI, new ItemStack(Items.aH), 0.3F);
        this.a(Blocks.aL, new ItemStack(Blocks.cz), 0.35F);
        this.a((Block)Blocks.aK, new ItemStack(Items.aW, 1, EnumDyeColor.GREEN.b()), 0.2F);
        this.a(Blocks.r, new ItemStack(Items.h, 1, 1), 0.15F);
        this.a(Blocks.s, new ItemStack(Items.h, 1, 1), 0.15F);
        this.a(Blocks.bP, new ItemStack(Items.bO), 1.0F);
        this.a(Items.bS, new ItemStack(Items.bT), 0.35F);
        this.a(Blocks.aV, new ItemStack(Items.cf), 0.1F);
        this.a(new ItemStack(Blocks.v, 1, 1), new ItemStack(Blocks.v, 1, 0), 0.15F);
        ItemFishFood.FishType[] aitemfishfood_fishtype = ItemFishFood.FishType.values();
        int i0 = aitemfishfood_fishtype.length;

        for (int i1 = 0; i1 < i0; ++i1) {
            ItemFishFood.FishType itemfishfood_fishtype = aitemfishfood_fishtype[i1];

            if (itemfishfood_fishtype.g()) {
                this.a(new ItemStack(Items.aU, 1, itemfishfood_fishtype.a()), new ItemStack(Items.aV, 1, itemfishfood_fishtype.a()), 0.35F);
            }
        }

        this.a(Blocks.q, new ItemStack(Items.h), 0.1F);
        this.a(Blocks.aC, new ItemStack(Items.aC), 0.7F);
        this.a(Blocks.x, new ItemStack(Items.aW, 1, EnumDyeColor.BLUE.b()), 0.2F);
        this.a(Blocks.co, new ItemStack(Items.cg), 0.2F);
    }

    public void a(Block block, ItemStack itemstack, float f0) {
        this.a(Item.a(block), itemstack, f0);
    }

    public void a(Item item, ItemStack itemstack, float f0) {
        this.a(new ItemStack(item, 1, 32767), itemstack, f0);
    }

    public void a(ItemStack itemstack, ItemStack itemstack1, float f0) {
        this.b.put(itemstack, itemstack1);
        this.c.put(itemstack1, Float.valueOf(f0));
    }

    public ItemStack a(ItemStack itemstack) {
        Iterator iterator = this.b.entrySet().iterator();

        Entry entry;

        do {
            if (!iterator.hasNext()) {
                return null;
            }

            entry = (Entry)iterator.next();
        }
        while (!this.a(itemstack, (ItemStack)entry.getKey()));

        return (ItemStack)entry.getValue();
    }

    private boolean a(ItemStack itemstack, ItemStack itemstack1) {
        return itemstack1.b() == itemstack.b() && (itemstack1.i() == 32767 || itemstack1.i() == itemstack.i());
    }

    public Map b() {
        return this.b;
    }

    public float b(ItemStack itemstack) {
        Iterator iterator = this.c.entrySet().iterator();

        Entry entry;

        do {
            if (!iterator.hasNext()) {
                return 0.0F;
            }

            entry = (Entry)iterator.next();
        }
        while (!this.a(itemstack, (ItemStack)entry.getKey()));

        return ((Float)entry.getValue()).floatValue();
    }

    // CanaryMod
    public List<SmeltRecipe> getSmeltingRecipes() {
        List<SmeltRecipe> smelting_recipes = new ArrayList<SmeltRecipe>();
        for (Object key : this.b.keySet()) {
            int fromId = ((Integer)key).intValue();
            net.canarymod.api.inventory.Item result = ((ItemStack)this.b.get(key)).getCanaryItem();
            float xp = this.b(((CanaryItem)result).getHandle());
            smelting_recipes.add(new SmeltRecipe(fromId, result, xp));
        }
        return smelting_recipes;
    }

    public boolean removeSmeltingRecipe(SmeltRecipe recipe) {
        if (this.b.containsKey(Integer.valueOf(recipe.getItemIDFrom()))) {
            this.b.remove(Integer.valueOf(recipe.getItemIDFrom()));
            this.c.remove(Integer.valueOf(recipe.getResult().getId()));
            return true;
        }
        return false;
    }
    //
}
