package net.minecraft.item.crafting;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class CraftingManager {

    private static final CraftingManager a = new CraftingManager();
    private List b = new ArrayList();

    public static final CraftingManager a() {
        return a;
    }

    private CraftingManager() {
        (new RecipesTools()).a(this);
        (new RecipesWeapons()).a(this);
        (new RecipesIngots()).a(this);
        (new RecipesFood()).a(this);
        (new RecipesCrafting()).a(this);
        (new RecipesArmor()).a(this);
        (new RecipesDyes()).a(this);
        this.b.add(new RecipesArmorDyes());
        this.b.add(new RecipeBookCloning());
        this.b.add(new RecipesMapCloning());
        this.b.add(new RecipesMapExtending());
        this.b.add(new RecipeFireworks());
        this.a(new ItemStack(Items.aF, 3), new Object[]{ "###", Character.valueOf('#'), Items.aE });
        this.b(new ItemStack(Items.aG, 1), new Object[]{ Items.aF, Items.aF, Items.aF, Items.aA });
        this.b(new ItemStack(Items.bA, 1), new Object[]{ Items.aG, new ItemStack(Items.aR, 1, 0), Items.G });
        this.a(new ItemStack(Blocks.aJ, 2), new Object[]{ "###", "###", Character.valueOf('#'), Items.y });
        this.a(new ItemStack(Blocks.bK, 6, 0), new Object[]{ "###", "###", Character.valueOf('#'), Blocks.e });
        this.a(new ItemStack(Blocks.bK, 6, 1), new Object[]{ "###", "###", Character.valueOf('#'), Blocks.Y });
        this.a(new ItemStack(Blocks.bk, 6), new Object[]{ "###", "###", Character.valueOf('#'), Blocks.bj });
        this.a(new ItemStack(Blocks.be, 1), new Object[]{ "#W#", "#W#", Character.valueOf('#'), Items.y, Character.valueOf('W'), Blocks.f });
        this.a(new ItemStack(Blocks.aI, 1), new Object[]{ "###", "#X#", "###", Character.valueOf('#'), Blocks.f, Character.valueOf('X'), Items.i });
        this.a(new ItemStack(Items.ca, 2), new Object[]{ "~~ ", "~O ", "  ~", Character.valueOf('~'), Items.F, Character.valueOf('O'), Items.aH });
        this.a(new ItemStack(Blocks.B, 1), new Object[]{ "###", "#X#", "###", Character.valueOf('#'), Blocks.f, Character.valueOf('X'), Items.ax });
        this.a(new ItemStack(Blocks.X, 1), new Object[]{ "###", "XXX", "###", Character.valueOf('#'), Blocks.f, Character.valueOf('X'), Items.aG });
        this.a(new ItemStack(Blocks.aE, 1), new Object[]{ "##", "##", Character.valueOf('#'), Items.ay });
        this.a(new ItemStack(Blocks.aC, 6), new Object[]{ "###", Character.valueOf('#'), Blocks.aE });
        this.a(new ItemStack(Blocks.aG, 1), new Object[]{ "##", "##", Character.valueOf('#'), Items.aD });
        this.a(new ItemStack(Blocks.V, 1), new Object[]{ "##", "##", Character.valueOf('#'), Items.aC });
        this.a(new ItemStack(Blocks.aN, 1), new Object[]{ "##", "##", Character.valueOf('#'), Items.aO });
        this.a(new ItemStack(Blocks.ca, 1), new Object[]{ "##", "##", Character.valueOf('#'), Items.bU });
        this.a(new ItemStack(Blocks.L, 1), new Object[]{ "##", "##", Character.valueOf('#'), Items.F });
        this.a(new ItemStack(Blocks.W, 1), new Object[]{ "X#X", "#X#", "X#X", Character.valueOf('X'), Items.H, Character.valueOf('#'), Blocks.m });
        this.a(new ItemStack(Blocks.U, 6, 3), new Object[]{ "###", Character.valueOf('#'), Blocks.e });
        this.a(new ItemStack(Blocks.U, 6, 0), new Object[]{ "###", Character.valueOf('#'), Blocks.b });
        this.a(new ItemStack(Blocks.U, 6, 1), new Object[]{ "###", Character.valueOf('#'), Blocks.A });
        this.a(new ItemStack(Blocks.U, 6, 4), new Object[]{ "###", Character.valueOf('#'), Blocks.V });
        this.a(new ItemStack(Blocks.U, 6, 5), new Object[]{ "###", Character.valueOf('#'), Blocks.aV });
        this.a(new ItemStack(Blocks.U, 6, 6), new Object[]{ "###", Character.valueOf('#'), Blocks.bj });
        this.a(new ItemStack(Blocks.U, 6, 7), new Object[]{ "###", Character.valueOf('#'), Blocks.ca });
        this.a(new ItemStack(Blocks.bx, 6, 0), new Object[]{ "###", Character.valueOf('#'), new ItemStack(Blocks.f, 1, 0) });
        this.a(new ItemStack(Blocks.bx, 6, 2), new Object[]{ "###", Character.valueOf('#'), new ItemStack(Blocks.f, 1, 2) });
        this.a(new ItemStack(Blocks.bx, 6, 1), new Object[]{ "###", Character.valueOf('#'), new ItemStack(Blocks.f, 1, 1) });
        this.a(new ItemStack(Blocks.bx, 6, 3), new Object[]{ "###", Character.valueOf('#'), new ItemStack(Blocks.f, 1, 3) });
        this.a(new ItemStack(Blocks.bx, 6, 4), new Object[]{ "###", Character.valueOf('#'), new ItemStack(Blocks.f, 1, 4) });
        this.a(new ItemStack(Blocks.bx, 6, 5), new Object[]{ "###", Character.valueOf('#'), new ItemStack(Blocks.f, 1, 5) });
        this.a(new ItemStack(Blocks.ap, 3), new Object[]{ "# #", "###", "# #", Character.valueOf('#'), Items.y });
        this.a(new ItemStack(Items.aq, 1), new Object[]{ "##", "##", "##", Character.valueOf('#'), Blocks.f });
        this.a(new ItemStack(Blocks.aT, 2), new Object[]{ "###", "###", Character.valueOf('#'), Blocks.f });
        this.a(new ItemStack(Items.aw, 1), new Object[]{ "##", "##", "##", Character.valueOf('#'), Items.j });
        this.a(new ItemStack(Items.ap, 3), new Object[]{ "###", "###", " X ", Character.valueOf('#'), Blocks.f, Character.valueOf('X'), Items.y });
        this.a(new ItemStack(Items.aU, 1), new Object[]{ "AAA", "BEB", "CCC", Character.valueOf('A'), Items.aB, Character.valueOf('B'), Items.aT, Character.valueOf('C'), Items.O, Character.valueOf('E'), Items.aK });
        this.a(new ItemStack(Items.aT, 1), new Object[]{ "#", Character.valueOf('#'), Items.aE });
        this.a(new ItemStack(Blocks.f, 4, 0), new Object[]{ "#", Character.valueOf('#'), new ItemStack(Blocks.r, 1, 0) });
        this.a(new ItemStack(Blocks.f, 4, 1), new Object[]{ "#", Character.valueOf('#'), new ItemStack(Blocks.r, 1, 1) });
        this.a(new ItemStack(Blocks.f, 4, 2), new Object[]{ "#", Character.valueOf('#'), new ItemStack(Blocks.r, 1, 2) });
        this.a(new ItemStack(Blocks.f, 4, 3), new Object[]{ "#", Character.valueOf('#'), new ItemStack(Blocks.r, 1, 3) });
        this.a(new ItemStack(Blocks.f, 4, 4), new Object[]{ "#", Character.valueOf('#'), new ItemStack(Blocks.s, 1, 0) });
        this.a(new ItemStack(Blocks.f, 4, 5), new Object[]{ "#", Character.valueOf('#'), new ItemStack(Blocks.s, 1, 1) });
        this.a(new ItemStack(Items.y, 4), new Object[]{ "#", "#", Character.valueOf('#'), Blocks.f });
        this.a(new ItemStack(Blocks.aa, 4), new Object[]{ "X", "#", Character.valueOf('X'), Items.h, Character.valueOf('#'), Items.y });
        this.a(new ItemStack(Blocks.aa, 4), new Object[]{ "X", "#", Character.valueOf('X'), new ItemStack(Items.h, 1, 1), Character.valueOf('#'), Items.y });
        this.a(new ItemStack(Items.z, 4), new Object[]{ "# #", " # ", Character.valueOf('#'), Blocks.f });
        this.a(new ItemStack(Items.bo, 3), new Object[]{ "# #", " # ", Character.valueOf('#'), Blocks.w });
        this.a(new ItemStack(Blocks.aq, 16), new Object[]{ "X X", "X#X", "X X", Character.valueOf('X'), Items.j, Character.valueOf('#'), Items.y });
        this.a(new ItemStack(Blocks.D, 6), new Object[]{ "X X", "X#X", "XRX", Character.valueOf('X'), Items.k, Character.valueOf('R'), Items.ax, Character.valueOf('#'), Items.y });
        this.a(new ItemStack(Blocks.cc, 6), new Object[]{ "XSX", "X#X", "XSX", Character.valueOf('X'), Items.j, Character.valueOf('#'), Blocks.aA, Character.valueOf('S'), Items.y });
        this.a(new ItemStack(Blocks.E, 6), new Object[]{ "X X", "X#X", "XRX", Character.valueOf('X'), Items.j, Character.valueOf('R'), Items.ax, Character.valueOf('#'), Blocks.au });
        this.a(new ItemStack(Items.au, 1), new Object[]{ "# #", "###", Character.valueOf('#'), Items.j });
        this.a(new ItemStack(Items.bu, 1), new Object[]{ "# #", "# #", "###", Character.valueOf('#'), Items.j });
        this.a(new ItemStack(Items.bt, 1), new Object[]{ " B ", "###", Character.valueOf('#'), Blocks.e, Character.valueOf('B'), Items.bj });
        this.a(new ItemStack(Blocks.aP, 1), new Object[]{ "A", "B", Character.valueOf('A'), Blocks.aK, Character.valueOf('B'), Blocks.aa });
        this.a(new ItemStack(Items.aI, 1), new Object[]{ "A", "B", Character.valueOf('A'), Blocks.ae, Character.valueOf('B'), Items.au });
        this.a(new ItemStack(Items.aJ, 1), new Object[]{ "A", "B", Character.valueOf('A'), Blocks.al, Character.valueOf('B'), Items.au });
        this.a(new ItemStack(Items.bV, 1), new Object[]{ "A", "B", Character.valueOf('A'), Blocks.W, Character.valueOf('B'), Items.au });
        this.a(new ItemStack(Items.bW, 1), new Object[]{ "A", "B", Character.valueOf('A'), Blocks.bZ, Character.valueOf('B'), Items.au });
        this.a(new ItemStack(Items.az, 1), new Object[]{ "# #", "###", Character.valueOf('#'), Blocks.f });
        this.a(new ItemStack(Items.ar, 1), new Object[]{ "# #", " # ", Character.valueOf('#'), Items.j });
        this.a(new ItemStack(Items.bE, 1), new Object[]{ "# #", " # ", Character.valueOf('#'), Items.aC });
        this.b(new ItemStack(Items.d, 1), new Object[]{ new ItemStack(Items.j, 1), new ItemStack(Items.ak, 1) });
        this.a(new ItemStack(Items.P, 1), new Object[]{ "###", Character.valueOf('#'), Items.O });
        this.a(new ItemStack(Blocks.ad, 4), new Object[]{ "#  ", "## ", "###", Character.valueOf('#'), new ItemStack(Blocks.f, 1, 0) });
        this.a(new ItemStack(Blocks.bG, 4), new Object[]{ "#  ", "## ", "###", Character.valueOf('#'), new ItemStack(Blocks.f, 1, 2) });
        this.a(new ItemStack(Blocks.bF, 4), new Object[]{ "#  ", "## ", "###", Character.valueOf('#'), new ItemStack(Blocks.f, 1, 1) });
        this.a(new ItemStack(Blocks.bH, 4), new Object[]{ "#  ", "## ", "###", Character.valueOf('#'), new ItemStack(Blocks.f, 1, 3) });
        this.a(new ItemStack(Blocks.ck, 4), new Object[]{ "#  ", "## ", "###", Character.valueOf('#'), new ItemStack(Blocks.f, 1, 4) });
        this.a(new ItemStack(Blocks.cl, 4), new Object[]{ "#  ", "## ", "###", Character.valueOf('#'), new ItemStack(Blocks.f, 1, 5) });
        this.a(new ItemStack(Items.aM, 1), new Object[]{ "  #", " #X", "# X", Character.valueOf('#'), Items.y, Character.valueOf('X'), Items.F });
        this.a(new ItemStack(Items.bM, 1), new Object[]{ "# ", " X", Character.valueOf('#'), Items.aM, Character.valueOf('X'), Items.bF }).c();
        this.a(new ItemStack(Blocks.ar, 4), new Object[]{ "#  ", "## ", "###", Character.valueOf('#'), Blocks.e });
        this.a(new ItemStack(Blocks.bf, 4), new Object[]{ "#  ", "## ", "###", Character.valueOf('#'), Blocks.V });
        this.a(new ItemStack(Blocks.bg, 4), new Object[]{ "#  ", "## ", "###", Character.valueOf('#'), Blocks.aV });
        this.a(new ItemStack(Blocks.bl, 4), new Object[]{ "#  ", "## ", "###", Character.valueOf('#'), Blocks.bj });
        this.a(new ItemStack(Blocks.bz, 4), new Object[]{ "#  ", "## ", "###", Character.valueOf('#'), Blocks.A });
        this.a(new ItemStack(Blocks.cb, 4), new Object[]{ "#  ", "## ", "###", Character.valueOf('#'), Blocks.ca });
        this.a(new ItemStack(Items.an, 1), new Object[]{ "###", "#X#", "###", Character.valueOf('#'), Items.y, Character.valueOf('X'), Blocks.L });
        this.a(new ItemStack(Items.bD, 1), new Object[]{ "###", "#X#", "###", Character.valueOf('#'), Items.y, Character.valueOf('X'), Items.aA });
        this.a(new ItemStack(Items.ao, 1, 0), new Object[]{ "###", "#X#", "###", Character.valueOf('#'), Items.k, Character.valueOf('X'), Items.e });
        this.a(new ItemStack(Items.ao, 1, 1), new Object[]{ "###", "#X#", "###", Character.valueOf('#'), Blocks.R, Character.valueOf('X'), Items.e });
        this.a(new ItemStack(Items.bK, 1, 0), new Object[]{ "###", "#X#", "###", Character.valueOf('#'), Items.bl, Character.valueOf('X'), Items.bF });
        this.a(new ItemStack(Items.bw, 1), new Object[]{ "###", "#X#", "###", Character.valueOf('#'), Items.bl, Character.valueOf('X'), Items.ba });
        this.a(new ItemStack(Blocks.at, 1), new Object[]{ "X", "#", Character.valueOf('#'), Blocks.e, Character.valueOf('X'), Items.y });
        this.a(new ItemStack(Blocks.bC, 2), new Object[]{ "I", "S", "#", Character.valueOf('#'), Blocks.f, Character.valueOf('S'), Items.y, Character.valueOf('I'), Items.j });
        this.a(new ItemStack(Blocks.aA, 1), new Object[]{ "X", "#", Character.valueOf('#'), Items.y, Character.valueOf('X'), Items.ax });
        this.a(new ItemStack(Items.aW, 1), new Object[]{ "#X#", "III", Character.valueOf('#'), Blocks.aA, Character.valueOf('X'), Items.ax, Character.valueOf('I'), Blocks.b });
        this.a(new ItemStack(Items.bS, 1), new Object[]{ " # ", "#X#", "III", Character.valueOf('#'), Blocks.aA, Character.valueOf('X'), Items.bU, Character.valueOf('I'), Blocks.b });
        this.a(new ItemStack(Items.aN, 1), new Object[]{ " # ", "#X#", " # ", Character.valueOf('#'), Items.k, Character.valueOf('X'), Items.ax });
        this.a(new ItemStack(Items.aL, 1), new Object[]{ " # ", "#X#", " # ", Character.valueOf('#'), Items.j, Character.valueOf('X'), Items.ax });
        this.a(new ItemStack(Items.bJ, 1), new Object[]{ "###", "#X#", "###", Character.valueOf('#'), Items.aF, Character.valueOf('X'), Items.aL });
        this.a(new ItemStack(Blocks.aB, 1), new Object[]{ "#", Character.valueOf('#'), Blocks.b });
        this.a(new ItemStack(Blocks.bO, 1), new Object[]{ "#", Character.valueOf('#'), Blocks.f });
        this.a(new ItemStack(Blocks.au, 1), new Object[]{ "##", Character.valueOf('#'), Blocks.b });
        this.a(new ItemStack(Blocks.aw, 1), new Object[]{ "##", Character.valueOf('#'), Blocks.f });
        this.a(new ItemStack(Blocks.bT, 1), new Object[]{ "##", Character.valueOf('#'), Items.j });
        this.a(new ItemStack(Blocks.bS, 1), new Object[]{ "##", Character.valueOf('#'), Items.k });
        this.a(new ItemStack(Blocks.z, 1), new Object[]{ "###", "#X#", "#R#", Character.valueOf('#'), Blocks.e, Character.valueOf('X'), Items.f, Character.valueOf('R'), Items.ax });
        this.a(new ItemStack(Blocks.cd, 1), new Object[]{ "###", "# #", "#R#", Character.valueOf('#'), Blocks.e, Character.valueOf('R'), Items.ax });
        this.a(new ItemStack(Blocks.J, 1), new Object[]{ "TTT", "#X#", "#R#", Character.valueOf('#'), Blocks.e, Character.valueOf('X'), Items.j, Character.valueOf('R'), Items.ax, Character.valueOf('T'), Blocks.f });
        this.a(new ItemStack(Blocks.F, 1), new Object[]{ "S", "P", Character.valueOf('S'), Items.aH, Character.valueOf('P'), Blocks.J });
        this.a(new ItemStack(Items.aV, 1), new Object[]{ "###", "XXX", Character.valueOf('#'), Blocks.L, Character.valueOf('X'), Blocks.f });
        this.a(new ItemStack(Blocks.bn, 1), new Object[]{ " B ", "D#D", "###", Character.valueOf('#'), Blocks.Z, Character.valueOf('B'), Items.aG, Character.valueOf('D'), Items.i });
        this.a(new ItemStack(Blocks.bQ, 1), new Object[]{ "III", " i ", "iii", Character.valueOf('I'), Blocks.S, Character.valueOf('i'), Items.j });
        this.b(new ItemStack(Items.bv, 1), new Object[]{ Items.bi, Items.br });
        this.b(new ItemStack(Items.bz, 3), new Object[]{ Items.H, Items.br, Items.h });
        this.b(new ItemStack(Items.bz, 3), new Object[]{ Items.H, Items.br, new ItemStack(Items.h, 1, 1) });
        this.a(new ItemStack(Blocks.bW), new Object[]{ "GGG", "QQQ", "WWW", Character.valueOf('G'), Blocks.w, Character.valueOf('Q'), Items.bU, Character.valueOf('W'), Blocks.bx });
        this.a(new ItemStack(Blocks.bZ), new Object[]{ "I I", "ICI", " I ", Character.valueOf('I'), Items.j, Character.valueOf('C'), Blocks.ae });
        Collections.sort(this.b, new Comparator() {

            public int compare(IRecipe object, IRecipe object1) {
                return object instanceof ShapelessRecipes && object1 instanceof ShapedRecipes ? 1 : (object1 instanceof ShapelessRecipes && object instanceof ShapedRecipes ? -1 : (object1.a() < object.a() ? -1 : (object1.a() > object.a() ? 1 : 0)));
            }

            public int compare(Object object, Object object1) {
                return this.compare((IRecipe) object, (IRecipe) object1);
            }
        });
    }

    public ShapedRecipes a(ItemStack itemstack, Object... aobject) { // CanaryMod: package -> public
        String s0 = "";
        int i0 = 0;
        int i1 = 0;
        int i2 = 0;

        if (aobject[i0] instanceof String[]) {
            String[] astring = (String[]) ((String[]) aobject[i0++]);

            for (int i3 = 0; i3 < astring.length; ++i3) {
                String s1 = astring[i3];

                ++i2;
                i1 = s1.length();
                s0 = s0 + s1;
            }
        }
        else {
            while (aobject[i0] instanceof String) {
                String s2 = (String) aobject[i0++];

                ++i2;
                i1 = s2.length();
                s0 = s0 + s2;
            }
        }

        HashMap hashmap;

        for (hashmap = new HashMap(); i0 < aobject.length; i0 += 2) {
            Character character = (Character) aobject[i0];
            ItemStack itemstack1 = null;

            if (aobject[i0 + 1] instanceof Item) {
                itemstack1 = new ItemStack((Item) aobject[i0 + 1]);
            }
            else if (aobject[i0 + 1] instanceof Block) {
                itemstack1 = new ItemStack((Block) aobject[i0 + 1], 1, 32767);
            }
            else if (aobject[i0 + 1] instanceof ItemStack) {
                itemstack1 = (ItemStack) aobject[i0 + 1];
            }

            hashmap.put(character, itemstack1);
        }

        ItemStack[] aitemstack = new ItemStack[i1 * i2];

        for (int i4 = 0; i4 < i1 * i2; ++i4) {
            char c0 = s0.charAt(i4);

            if (hashmap.containsKey(Character.valueOf(c0))) {
                aitemstack[i4] = ((ItemStack) hashmap.get(Character.valueOf(c0))).m();
            }
            else {
                aitemstack[i4] = null;
            }
        }

        ShapedRecipes shapedrecipes = new ShapedRecipes(i1, i2, aitemstack, itemstack);

        this.b.add(shapedrecipes);
        return shapedrecipes;
    }

    void b(ItemStack itemstack, Object... aobject) {// CanaryMod: pass down to return ShapelessRecipe (signature change breaks things elsewhere)
        this.addShapeless(itemstack, aobject);
    }

    public ShapelessRecipes addShapeless(ItemStack itemstack, Object... aobject) { // CanaryMod: safe return without breakage
        ArrayList arraylist = new ArrayList();
        Object[] aobject1 = aobject;
        int i0 = aobject.length;

        for (int i1 = 0; i1 < i0; ++i1) {
            Object object = aobject1[i1];

            if (object instanceof ItemStack) {
                arraylist.add(((ItemStack) object).m());
            }
            else if (object instanceof Item) {
                arraylist.add(new ItemStack((Item) object));
            }
            else {
                if (!(object instanceof Block)) {
                    throw new RuntimeException("Invalid shapeless recipy!");
                }

                arraylist.add(new ItemStack((Block) object));
            }
        }

        // CanaryMod: Allow Shapeless to be returned
        ShapelessRecipes shapelessrecipes = new ShapelessRecipes(itemstack, arraylist);
        this.b.add(shapelessrecipes);
        return shapelessrecipes;
        //
    }

    public ItemStack a(InventoryCrafting inventorycrafting, World world) {
        int i0 = 0;
        ItemStack itemstack = null;
        ItemStack itemstack1 = null;

        int i1;

        for (i1 = 0; i1 < inventorycrafting.a(); ++i1) {
            ItemStack itemstack2 = inventorycrafting.a(i1);

            if (itemstack2 != null) {
                if (i0 == 0) {
                    itemstack = itemstack2;
                }

                if (i0 == 1) {
                    itemstack1 = itemstack2;
                }

                ++i0;
            }
        }

        if (i0 == 2 && itemstack.b() == itemstack1.b() && itemstack.b == 1 && itemstack1.b == 1 && itemstack.b().p()) {
            Item item = itemstack.b();
            int i2 = item.o() - itemstack.j();
            int i3 = item.o() - itemstack1.j();
            int i4 = i2 + i3 + item.o() * 5 / 100;
            int i5 = item.o() - i4;

            if (i5 < 0) {
                i5 = 0;
            }

            return new ItemStack(itemstack.b(), 1, i5);
        }
        else {
            for (i1 = 0; i1 < this.b.size(); ++i1) {
                IRecipe irecipe = (IRecipe) this.b.get(i1);

                if (irecipe.a(inventorycrafting, world)) {
                    return irecipe.a(inventorycrafting);
                }
            }

            return null;
        }
    }

    public List b() {
        return this.b;
    }
}
