package net.minecraft.item.crafting;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.block.*;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.*;

public class CraftingManager {

    private static final CraftingManager a = new CraftingManager();
    private final List b = Lists.newArrayList();

    public static CraftingManager a() {
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
        this.b.add(new RecipeRepairItem());
        (new RecipesBanners()).a(this);
        this.a(new ItemStack(Items.aK, 3), new Object[]{"###", Character.valueOf('#'), Items.aJ});
        this.b(new ItemStack(Items.aL, 1), new Object[]{Items.aK, Items.aK, Items.aK, Items.aF});
        this.b(new ItemStack(Items.bM, 1), new Object[]{Items.aL, new ItemStack(Items.aW, 1, EnumDyeColor.BLACK.b()), Items.G});
        this.a(new ItemStack(Blocks.aO, 3), new Object[]{"W#W", "W#W", Character.valueOf('#'), Items.y, Character.valueOf('W'), new ItemStack(Blocks.f, 1, BlockPlanks.EnumType.OAK.a())});
        this.a(new ItemStack(Blocks.aQ, 3), new Object[]{"W#W", "W#W", Character.valueOf('#'), Items.y, Character.valueOf('W'), new ItemStack(Blocks.f, 1, BlockPlanks.EnumType.BIRCH.a())});
        this.a(new ItemStack(Blocks.aP, 3), new Object[]{"W#W", "W#W", Character.valueOf('#'), Items.y, Character.valueOf('W'), new ItemStack(Blocks.f, 1, BlockPlanks.EnumType.SPRUCE.a())});
        this.a(new ItemStack(Blocks.aR, 3), new Object[]{"W#W", "W#W", Character.valueOf('#'), Items.y, Character.valueOf('W'), new ItemStack(Blocks.f, 1, BlockPlanks.EnumType.JUNGLE.a())});
        this.a(new ItemStack(Blocks.aT, 3), new Object[]{"W#W", "W#W", Character.valueOf('#'), Items.y, Character.valueOf('W'), new ItemStack(Blocks.f, 1, 4 + BlockPlanks.EnumType.ACACIA.a() - 4)});
        this.a(new ItemStack(Blocks.aS, 3), new Object[]{"W#W", "W#W", Character.valueOf('#'), Items.y, Character.valueOf('W'), new ItemStack(Blocks.f, 1, 4 + BlockPlanks.EnumType.DARK_OAK.a() - 4)});
        this.a(new ItemStack(Blocks.bZ, 6, BlockWall.EnumType.NORMAL.a()), new Object[]{"###", "###", Character.valueOf('#'), Blocks.e});
        this.a(new ItemStack(Blocks.bZ, 6, BlockWall.EnumType.MOSSY.a()), new Object[]{"###", "###", Character.valueOf('#'), Blocks.Y});
        this.a(new ItemStack(Blocks.bz, 6), new Object[]{"###", "###", Character.valueOf('#'), Blocks.by});
        this.a(new ItemStack(Blocks.bo, 1), new Object[]{"#W#", "#W#", Character.valueOf('#'), Items.y, Character.valueOf('W'), new ItemStack(Blocks.f, 1, BlockPlanks.EnumType.OAK.a())});
        this.a(new ItemStack(Blocks.bq, 1), new Object[]{"#W#", "#W#", Character.valueOf('#'), Items.y, Character.valueOf('W'), new ItemStack(Blocks.f, 1, BlockPlanks.EnumType.BIRCH.a())});
        this.a(new ItemStack(Blocks.bp, 1), new Object[]{"#W#", "#W#", Character.valueOf('#'), Items.y, Character.valueOf('W'), new ItemStack(Blocks.f, 1, BlockPlanks.EnumType.SPRUCE.a())});
        this.a(new ItemStack(Blocks.br, 1), new Object[]{"#W#", "#W#", Character.valueOf('#'), Items.y, Character.valueOf('W'), new ItemStack(Blocks.f, 1, BlockPlanks.EnumType.JUNGLE.a())});
        this.a(new ItemStack(Blocks.bt, 1), new Object[]{"#W#", "#W#", Character.valueOf('#'), Items.y, Character.valueOf('W'), new ItemStack(Blocks.f, 1, 4 + BlockPlanks.EnumType.ACACIA.a() - 4)});
        this.a(new ItemStack(Blocks.bs, 1), new Object[]{"#W#", "#W#", Character.valueOf('#'), Items.y, Character.valueOf('W'), new ItemStack(Blocks.f, 1, 4 + BlockPlanks.EnumType.DARK_OAK.a() - 4)});
        this.a(new ItemStack(Blocks.aN, 1), new Object[]{"###", "#X#", "###", Character.valueOf('#'), Blocks.f, Character.valueOf('X'), Items.i});
        this.a(new ItemStack(Items.cn, 2), new Object[]{"~~ ", "~O ", "  ~", Character.valueOf('~'), Items.F, Character.valueOf('O'), Items.aM});
        this.a(new ItemStack(Blocks.B, 1), new Object[]{"###", "#X#", "###", Character.valueOf('#'), Blocks.f, Character.valueOf('X'), Items.aC});
        this.a(new ItemStack(Blocks.X, 1), new Object[]{"###", "XXX", "###", Character.valueOf('#'), Blocks.f, Character.valueOf('X'), Items.aL});
        this.a(new ItemStack(Blocks.aJ, 1), new Object[]{"##", "##", Character.valueOf('#'), Items.aD});
        this.a(new ItemStack(Blocks.aH, 6), new Object[]{"###", Character.valueOf('#'), Blocks.aJ});
        this.a(new ItemStack(Blocks.aL, 1), new Object[]{"##", "##", Character.valueOf('#'), Items.aI});
        this.a(new ItemStack(Blocks.V, 1), new Object[]{"##", "##", Character.valueOf('#'), Items.aH});
        this.a(new ItemStack(Blocks.aX, 1), new Object[]{"##", "##", Character.valueOf('#'), Items.aT});
        this.a(new ItemStack(Blocks.cq, 1), new Object[]{"##", "##", Character.valueOf('#'), Items.cg});
        this.a(new ItemStack(Blocks.L, 1), new Object[]{"##", "##", Character.valueOf('#'), Items.F});
        this.a(new ItemStack(Blocks.W, 1), new Object[]{"X#X", "#X#", "X#X", Character.valueOf('X'), Items.H, Character.valueOf('#'), Blocks.m});
        this.a(new ItemStack(Blocks.U, 6, BlockStoneSlab.EnumType.COBBLESTONE.a()), new Object[]{"###", Character.valueOf('#'), Blocks.e});
        this.a(new ItemStack(Blocks.U, 6, BlockStoneSlab.EnumType.STONE.a()), new Object[]{"###", Character.valueOf('#'), new ItemStack(Blocks.b, BlockStone.EnumType.STONE.a())});
        this.a(new ItemStack(Blocks.U, 6, BlockStoneSlab.EnumType.SAND.a()), new Object[]{"###", Character.valueOf('#'), Blocks.A});
        this.a(new ItemStack(Blocks.U, 6, BlockStoneSlab.EnumType.BRICK.a()), new Object[]{"###", Character.valueOf('#'), Blocks.V});
        this.a(new ItemStack(Blocks.U, 6, BlockStoneSlab.EnumType.SMOOTHBRICK.a()), new Object[]{"###", Character.valueOf('#'), Blocks.bf});
        this.a(new ItemStack(Blocks.U, 6, BlockStoneSlab.EnumType.NETHERBRICK.a()), new Object[]{"###", Character.valueOf('#'), Blocks.by});
        this.a(new ItemStack(Blocks.U, 6, BlockStoneSlab.EnumType.QUARTZ.a()), new Object[]{"###", Character.valueOf('#'), Blocks.cq});
        this.a(new ItemStack(Blocks.cP, 6, BlockStoneSlabNew.EnumType.RED_SANDSTONE.a()), new Object[]{"###", Character.valueOf('#'), Blocks.cM});
        this.a(new ItemStack(Blocks.bM, 6, 0), new Object[]{"###", Character.valueOf('#'), new ItemStack(Blocks.f, 1, BlockPlanks.EnumType.OAK.a())});
        this.a(new ItemStack(Blocks.bM, 6, BlockPlanks.EnumType.BIRCH.a()), new Object[]{"###", Character.valueOf('#'), new ItemStack(Blocks.f, 1, BlockPlanks.EnumType.BIRCH.a())});
        this.a(new ItemStack(Blocks.bM, 6, BlockPlanks.EnumType.SPRUCE.a()), new Object[]{"###", Character.valueOf('#'), new ItemStack(Blocks.f, 1, BlockPlanks.EnumType.SPRUCE.a())});
        this.a(new ItemStack(Blocks.bM, 6, BlockPlanks.EnumType.JUNGLE.a()), new Object[]{"###", Character.valueOf('#'), new ItemStack(Blocks.f, 1, BlockPlanks.EnumType.JUNGLE.a())});
        this.a(new ItemStack(Blocks.bM, 6, 4 + BlockPlanks.EnumType.ACACIA.a() - 4), new Object[]{"###", Character.valueOf('#'), new ItemStack(Blocks.f, 1, 4 + BlockPlanks.EnumType.ACACIA.a() - 4)});
        this.a(new ItemStack(Blocks.bM, 6, 4 + BlockPlanks.EnumType.DARK_OAK.a() - 4), new Object[]{"###", Character.valueOf('#'), new ItemStack(Blocks.f, 1, 4 + BlockPlanks.EnumType.DARK_OAK.a() - 4)});
        this.a(new ItemStack(Blocks.au, 3), new Object[]{"# #", "###", "# #", Character.valueOf('#'), Items.y});
        this.a(new ItemStack(Items.aq, 3), new Object[]{"##", "##", "##", Character.valueOf('#'), new ItemStack(Blocks.f, 1, BlockPlanks.EnumType.OAK.a())});
        this.a(new ItemStack(Items.ar, 3), new Object[]{"##", "##", "##", Character.valueOf('#'), new ItemStack(Blocks.f, 1, BlockPlanks.EnumType.SPRUCE.a())});
        this.a(new ItemStack(Items.as, 3), new Object[]{"##", "##", "##", Character.valueOf('#'), new ItemStack(Blocks.f, 1, BlockPlanks.EnumType.BIRCH.a())});
        this.a(new ItemStack(Items.at, 3), new Object[]{"##", "##", "##", Character.valueOf('#'), new ItemStack(Blocks.f, 1, BlockPlanks.EnumType.JUNGLE.a())});
        this.a(new ItemStack(Items.au, 3), new Object[]{"##", "##", "##", Character.valueOf('#'), new ItemStack(Blocks.f, 1, BlockPlanks.EnumType.ACACIA.a())});
        this.a(new ItemStack(Items.av, 3), new Object[]{"##", "##", "##", Character.valueOf('#'), new ItemStack(Blocks.f, 1, BlockPlanks.EnumType.DARK_OAK.a())});
        this.a(new ItemStack(Blocks.bd, 2), new Object[]{"###", "###", Character.valueOf('#'), Blocks.f});
        this.a(new ItemStack(Items.aB, 3), new Object[]{"##", "##", "##", Character.valueOf('#'), Items.j});
        this.a(new ItemStack(Blocks.cw, 1), new Object[]{"##", "##", Character.valueOf('#'), Items.j});
        this.a(new ItemStack(Items.ap, 3), new Object[]{"###", "###", " X ", Character.valueOf('#'), Blocks.f, Character.valueOf('X'), Items.y});
        this.a(new ItemStack(Items.aZ, 1), new Object[]{"AAA", "BEB", "CCC", Character.valueOf('A'), Items.aG, Character.valueOf('B'), Items.aY, Character.valueOf('C'), Items.O, Character.valueOf('E'), Items.aP});
        this.a(new ItemStack(Items.aY, 1), new Object[]{"#", Character.valueOf('#'), Items.aJ});
        this.a(new ItemStack(Blocks.f, 4, BlockPlanks.EnumType.OAK.a()), new Object[]{"#", Character.valueOf('#'), new ItemStack(Blocks.r, 1, BlockPlanks.EnumType.OAK.a())});
        this.a(new ItemStack(Blocks.f, 4, BlockPlanks.EnumType.SPRUCE.a()), new Object[]{"#", Character.valueOf('#'), new ItemStack(Blocks.r, 1, BlockPlanks.EnumType.SPRUCE.a())});
        this.a(new ItemStack(Blocks.f, 4, BlockPlanks.EnumType.BIRCH.a()), new Object[]{"#", Character.valueOf('#'), new ItemStack(Blocks.r, 1, BlockPlanks.EnumType.BIRCH.a())});
        this.a(new ItemStack(Blocks.f, 4, BlockPlanks.EnumType.JUNGLE.a()), new Object[]{"#", Character.valueOf('#'), new ItemStack(Blocks.r, 1, BlockPlanks.EnumType.JUNGLE.a())});
        this.a(new ItemStack(Blocks.f, 4, 4 + BlockPlanks.EnumType.ACACIA.a() - 4), new Object[]{"#", Character.valueOf('#'), new ItemStack(Blocks.s, 1, BlockPlanks.EnumType.ACACIA.a() - 4)});
        this.a(new ItemStack(Blocks.f, 4, 4 + BlockPlanks.EnumType.DARK_OAK.a() - 4), new Object[]{"#", Character.valueOf('#'), new ItemStack(Blocks.s, 1, BlockPlanks.EnumType.DARK_OAK.a() - 4)});
        this.a(new ItemStack(Items.y, 4), new Object[]{"#", "#", Character.valueOf('#'), Blocks.f});
        this.a(new ItemStack(Blocks.aa, 4), new Object[]{"X", "#", Character.valueOf('X'), Items.h, Character.valueOf('#'), Items.y});
        this.a(new ItemStack(Blocks.aa, 4), new Object[]{"X", "#", Character.valueOf('X'), new ItemStack(Items.h, 1, 1), Character.valueOf('#'), Items.y});
        this.a(new ItemStack(Items.z, 4), new Object[]{"# #", " # ", Character.valueOf('#'), Blocks.f});
        this.a(new ItemStack(Items.bA, 3), new Object[]{"# #", " # ", Character.valueOf('#'), Blocks.w});
        this.a(new ItemStack(Blocks.av, 16), new Object[]{"X X", "X#X", "X X", Character.valueOf('X'), Items.j, Character.valueOf('#'), Items.y});
        this.a(new ItemStack(Blocks.D, 6), new Object[]{"X X", "X#X", "XRX", Character.valueOf('X'), Items.k, Character.valueOf('R'), Items.aC, Character.valueOf('#'), Items.y});
        this.a(new ItemStack(Blocks.cs, 6), new Object[]{"XSX", "X#X", "XSX", Character.valueOf('X'), Items.j, Character.valueOf('#'), Blocks.aF, Character.valueOf('S'), Items.y});
        this.a(new ItemStack(Blocks.E, 6), new Object[]{"X X", "X#X", "XRX", Character.valueOf('X'), Items.j, Character.valueOf('R'), Items.aC, Character.valueOf('#'), Blocks.az});
        this.a(new ItemStack(Items.az, 1), new Object[]{"# #", "###", Character.valueOf('#'), Items.j});
        this.a(new ItemStack(Items.bG, 1), new Object[]{"# #", "# #", "###", Character.valueOf('#'), Items.j});
        this.a(new ItemStack(Items.bF, 1), new Object[]{" B ", "###", Character.valueOf('#'), Blocks.e, Character.valueOf('B'), Items.bv});
        this.a(new ItemStack(Blocks.aZ, 1), new Object[]{"A", "B", Character.valueOf('A'), Blocks.aU, Character.valueOf('B'), Blocks.aa});
        this.a(new ItemStack(Items.aN, 1), new Object[]{"A", "B", Character.valueOf('A'), Blocks.ae, Character.valueOf('B'), Items.az});
        this.a(new ItemStack(Items.aO, 1), new Object[]{"A", "B", Character.valueOf('A'), Blocks.al, Character.valueOf('B'), Items.az});
        this.a(new ItemStack(Items.ch, 1), new Object[]{"A", "B", Character.valueOf('A'), Blocks.W, Character.valueOf('B'), Items.az});
        this.a(new ItemStack(Items.ci, 1), new Object[]{"A", "B", Character.valueOf('A'), Blocks.cp, Character.valueOf('B'), Items.az});
        this.a(new ItemStack(Items.aE, 1), new Object[]{"# #", "###", Character.valueOf('#'), Blocks.f});
        this.a(new ItemStack(Items.aw, 1), new Object[]{"# #", " # ", Character.valueOf('#'), Items.j});
        this.a(new ItemStack(Items.bQ, 1), new Object[]{"# #", " # ", Character.valueOf('#'), Items.aH});
        this.b(new ItemStack(Items.d, 1), new Object[]{new ItemStack(Items.j, 1), new ItemStack(Items.ak, 1)});
        this.a(new ItemStack(Items.P, 1), new Object[]{"###", Character.valueOf('#'), Items.O});
        this.a(new ItemStack(Blocks.ad, 4), new Object[]{"#  ", "## ", "###", Character.valueOf('#'), new ItemStack(Blocks.f, 1, BlockPlanks.EnumType.OAK.a())});
        this.a(new ItemStack(Blocks.bV, 4), new Object[]{"#  ", "## ", "###", Character.valueOf('#'), new ItemStack(Blocks.f, 1, BlockPlanks.EnumType.BIRCH.a())});
        this.a(new ItemStack(Blocks.bU, 4), new Object[]{"#  ", "## ", "###", Character.valueOf('#'), new ItemStack(Blocks.f, 1, BlockPlanks.EnumType.SPRUCE.a())});
        this.a(new ItemStack(Blocks.bW, 4), new Object[]{"#  ", "## ", "###", Character.valueOf('#'), new ItemStack(Blocks.f, 1, BlockPlanks.EnumType.JUNGLE.a())});
        this.a(new ItemStack(Blocks.cC, 4), new Object[]{"#  ", "## ", "###", Character.valueOf('#'), new ItemStack(Blocks.f, 1, 4 + BlockPlanks.EnumType.ACACIA.a() - 4)});
        this.a(new ItemStack(Blocks.cD, 4), new Object[]{"#  ", "## ", "###", Character.valueOf('#'), new ItemStack(Blocks.f, 1, 4 + BlockPlanks.EnumType.DARK_OAK.a() - 4)});
        this.a(new ItemStack(Items.aR, 1), new Object[]{"  #", " #X", "# X", Character.valueOf('#'), Items.y, Character.valueOf('X'), Items.F});
        this.a(new ItemStack(Items.bY, 1), new Object[]{"# ", " X", Character.valueOf('#'), Items.aR, Character.valueOf('X'), Items.bR}).c();
        this.a(new ItemStack(Blocks.aw, 4), new Object[]{"#  ", "## ", "###", Character.valueOf('#'), Blocks.e});
        this.a(new ItemStack(Blocks.bu, 4), new Object[]{"#  ", "## ", "###", Character.valueOf('#'), Blocks.V});
        this.a(new ItemStack(Blocks.bv, 4), new Object[]{"#  ", "## ", "###", Character.valueOf('#'), Blocks.bf});
        this.a(new ItemStack(Blocks.bA, 4), new Object[]{"#  ", "## ", "###", Character.valueOf('#'), Blocks.by});
        this.a(new ItemStack(Blocks.bO, 4), new Object[]{"#  ", "## ", "###", Character.valueOf('#'), Blocks.A});
        this.a(new ItemStack(Blocks.cN, 4), new Object[]{"#  ", "## ", "###", Character.valueOf('#'), Blocks.cM});
        this.a(new ItemStack(Blocks.cr, 4), new Object[]{"#  ", "## ", "###", Character.valueOf('#'), Blocks.cq});
        this.a(new ItemStack(Items.an, 1), new Object[]{"###", "#X#", "###", Character.valueOf('#'), Items.y, Character.valueOf('X'), Blocks.L});
        this.a(new ItemStack(Items.bP, 1), new Object[]{"###", "#X#", "###", Character.valueOf('#'), Items.y, Character.valueOf('X'), Items.aF});
        this.a(new ItemStack(Items.ao, 1, 0), new Object[]{"###", "#X#", "###", Character.valueOf('#'), Items.k, Character.valueOf('X'), Items.e});
        this.a(new ItemStack(Items.ao, 1, 1), new Object[]{"###", "#X#", "###", Character.valueOf('#'), Blocks.R, Character.valueOf('X'), Items.e});
        this.a(new ItemStack(Items.bW, 1, 0), new Object[]{"###", "#X#", "###", Character.valueOf('#'), Items.bx, Character.valueOf('X'), Items.bR});
        this.a(new ItemStack(Items.bI, 1), new Object[]{"###", "#X#", "###", Character.valueOf('#'), Items.bx, Character.valueOf('X'), Items.bf});
        this.a(new ItemStack(Blocks.ay, 1), new Object[]{"X", "#", Character.valueOf('#'), Blocks.e, Character.valueOf('X'), Items.y});
        this.a(new ItemStack(Blocks.bR, 2), new Object[]{"I", "S", "#", Character.valueOf('#'), Blocks.f, Character.valueOf('S'), Items.y, Character.valueOf('I'), Items.j});
        this.a(new ItemStack(Blocks.aF, 1), new Object[]{"X", "#", Character.valueOf('#'), Items.y, Character.valueOf('X'), Items.aC});
        this.a(new ItemStack(Items.bb, 1), new Object[]{"#X#", "III", Character.valueOf('#'), Blocks.aF, Character.valueOf('X'), Items.aC, Character.valueOf('I'), new ItemStack(Blocks.b, 1, BlockStone.EnumType.STONE.a())});
        this.a(new ItemStack(Items.ce, 1), new Object[]{" # ", "#X#", "III", Character.valueOf('#'), Blocks.aF, Character.valueOf('X'), Items.cg, Character.valueOf('I'), new ItemStack(Blocks.b, 1, BlockStone.EnumType.STONE.a())});
        this.a(new ItemStack(Items.aS, 1), new Object[]{" # ", "#X#", " # ", Character.valueOf('#'), Items.k, Character.valueOf('X'), Items.aC});
        this.a(new ItemStack(Items.aQ, 1), new Object[]{" # ", "#X#", " # ", Character.valueOf('#'), Items.j, Character.valueOf('X'), Items.aC});
        this.a(new ItemStack(Items.bV, 1), new Object[]{"###", "#X#", "###", Character.valueOf('#'), Items.aK, Character.valueOf('X'), Items.aQ});
        this.a(new ItemStack(Blocks.aG, 1), new Object[]{"#", Character.valueOf('#'), new ItemStack(Blocks.b, 1, BlockStone.EnumType.STONE.a())});
        this.a(new ItemStack(Blocks.cd, 1), new Object[]{"#", Character.valueOf('#'), Blocks.f});
        this.a(new ItemStack(Blocks.az, 1), new Object[]{"##", Character.valueOf('#'), new ItemStack(Blocks.b, 1, BlockStone.EnumType.STONE.a())});
        this.a(new ItemStack(Blocks.aB, 1), new Object[]{"##", Character.valueOf('#'), Blocks.f});
        this.a(new ItemStack(Blocks.ci, 1), new Object[]{"##", Character.valueOf('#'), Items.j});
        this.a(new ItemStack(Blocks.ch, 1), new Object[]{"##", Character.valueOf('#'), Items.k});
        this.a(new ItemStack(Blocks.z, 1), new Object[]{"###", "#X#", "#R#", Character.valueOf('#'), Blocks.e, Character.valueOf('X'), Items.f, Character.valueOf('R'), Items.aC});
        this.a(new ItemStack(Blocks.ct, 1), new Object[]{"###", "# #", "#R#", Character.valueOf('#'), Blocks.e, Character.valueOf('R'), Items.aC});
        this.a(new ItemStack(Blocks.J, 1), new Object[]{"TTT", "#X#", "#R#", Character.valueOf('#'), Blocks.e, Character.valueOf('X'), Items.j, Character.valueOf('R'), Items.aC, Character.valueOf('T'), Blocks.f});
        this.a(new ItemStack(Blocks.F, 1), new Object[]{"S", "P", Character.valueOf('S'), Items.aM, Character.valueOf('P'), Blocks.J});
        this.a(new ItemStack(Items.ba, 1), new Object[]{"###", "XXX", Character.valueOf('#'), Blocks.L, Character.valueOf('X'), Blocks.f});
        this.a(new ItemStack(Blocks.bC, 1), new Object[]{" B ", "D#D", "###", Character.valueOf('#'), Blocks.Z, Character.valueOf('B'), Items.aL, Character.valueOf('D'), Items.i});
        this.a(new ItemStack(Blocks.cf, 1), new Object[]{"III", " i ", "iii", Character.valueOf('I'), Blocks.S, Character.valueOf('i'), Items.j});
        this.a(new ItemStack(Items.aF), new Object[]{"##", "##", Character.valueOf('#'), Items.bs});
        this.b(new ItemStack(Items.bH, 1), new Object[]{Items.bu, Items.bD});
        this.b(new ItemStack(Items.bL, 3), new Object[]{Items.H, Items.bD, Items.h});
        this.b(new ItemStack(Items.bL, 3), new Object[]{Items.H, Items.bD, new ItemStack(Items.h, 1, 1)});
        this.a(new ItemStack(Blocks.cl), new Object[]{"GGG", "QQQ", "WWW", Character.valueOf('G'), Blocks.w, Character.valueOf('Q'), Items.cg, Character.valueOf('W'), Blocks.bM});
        this.a(new ItemStack(Blocks.cp), new Object[]{"I I", "ICI", " I ", Character.valueOf('I'), Items.j, Character.valueOf('C'), Blocks.ae});
        this.a(new ItemStack(Items.cj, 1), new Object[]{"///", " / ", "/_/", Character.valueOf('/'), Items.y, Character.valueOf('_'), new ItemStack(Blocks.U, 1, BlockStoneSlab.EnumType.STONE.a())});
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

        for (hashmap = Maps.newHashMap(); i0 < aobject.length; i0 += 2) {
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
                aitemstack[i4] = ((ItemStack) hashmap.get(Character.valueOf(c0))).k();
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
        ArrayList arraylist = Lists.newArrayList();
        Object[] aobject1 = aobject;
        int i0 = aobject.length;

        for (int i1 = 0; i1 < i0; ++i1) {
            Object object = aobject1[i1];

            if (object instanceof ItemStack) {
                arraylist.add(((ItemStack) object).k());
            }
            else if (object instanceof Item) {
                arraylist.add(new ItemStack((Item) object));
            }
            else {
                if (!(object instanceof Block)) {
                    throw new IllegalArgumentException("Invalid shapeless recipe: unknown type " + object.getClass().getName() + "!");
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

    public void a(IRecipe irecipe) {
        this.b.add(irecipe);
    }

    public ItemStack a(InventoryCrafting inventorycrafting, World world) {
        Iterator iterator = this.b.iterator();

        IRecipe irecipe;

        do {
            if (!iterator.hasNext()) {
                return null;
            }

            irecipe = (IRecipe) iterator.next();
        } while (!irecipe.a(inventorycrafting, world));

        return irecipe.a(inventorycrafting);
    }

    public ItemStack[] b(InventoryCrafting inventorycrafting, World world) {
        Iterator iterator = this.b.iterator();

        while (iterator.hasNext()) {
            IRecipe irecipe = (IRecipe) iterator.next();

            if (irecipe.a(inventorycrafting, world)) {
                return irecipe.b(inventorycrafting);
            }
        }

        ItemStack[] aitemstack = new ItemStack[inventorycrafting.n_()];

        for (int i0 = 0; i0 < aitemstack.length; ++i0) {
            aitemstack[i0] = inventorycrafting.a(i0);
        }

        return aitemstack;
    }

    public List b() {
        return this.b;
    }
}
