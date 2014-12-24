package net.canarymod.api.world.blocks.properties;

import com.google.common.collect.Maps;
import net.canarymod.Canary;
import net.canarymod.api.world.blocks.BlockType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCarpet;
import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockDoubleStoneSlab;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockNewLeaf;
import net.minecraft.block.BlockNewLog;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockPrismarine;
import net.minecraft.block.BlockQuartz;
import net.minecraft.block.BlockRedSandstone;
import net.minecraft.block.BlockSandStone;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.BlockSilverfish;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.BlockStainedGlassPane;
import net.minecraft.block.BlockStone;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.BlockWoodSlab;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;

import java.util.HashMap;

import static net.canarymod.api.world.blocks.BlockType.AcaciaLeaves;
import static net.canarymod.api.world.blocks.BlockType.AcaciaLog;
import static net.canarymod.api.world.blocks.BlockType.AcaciaSapling;
import static net.canarymod.api.world.blocks.BlockType.AcaciaWood;
import static net.canarymod.api.world.blocks.BlockType.AcaciaWoodSlab;
import static net.canarymod.api.world.blocks.BlockType.Allium;
import static net.canarymod.api.world.blocks.BlockType.Andesite;
import static net.canarymod.api.world.blocks.BlockType.AzureBluet;
import static net.canarymod.api.world.blocks.BlockType.BirchLeaves;
import static net.canarymod.api.world.blocks.BlockType.BirchLog;
import static net.canarymod.api.world.blocks.BlockType.BirchSapling;
import static net.canarymod.api.world.blocks.BlockType.BirchWood;
import static net.canarymod.api.world.blocks.BlockType.BirchWoodSlab;
import static net.canarymod.api.world.blocks.BlockType.BlackCarpet;
import static net.canarymod.api.world.blocks.BlockType.BlackGlass;
import static net.canarymod.api.world.blocks.BlockType.BlackGlassPane;
import static net.canarymod.api.world.blocks.BlockType.BlackStainedClay;
import static net.canarymod.api.world.blocks.BlockType.BlueCarpet;
import static net.canarymod.api.world.blocks.BlockType.BlueGlass;
import static net.canarymod.api.world.blocks.BlockType.BlueGlassPane;
import static net.canarymod.api.world.blocks.BlockType.BlueOrchid;
import static net.canarymod.api.world.blocks.BlockType.BlueStainedClay;
import static net.canarymod.api.world.blocks.BlockType.BrickBlockSlab;
import static net.canarymod.api.world.blocks.BlockType.BrownCarpet;
import static net.canarymod.api.world.blocks.BlockType.BrownGlass;
import static net.canarymod.api.world.blocks.BlockType.BrownGlassPane;
import static net.canarymod.api.world.blocks.BlockType.BrownStainedClay;
import static net.canarymod.api.world.blocks.BlockType.CoarseDirt;
import static net.canarymod.api.world.blocks.BlockType.CobbleSilverFishBlock;
import static net.canarymod.api.world.blocks.BlockType.CobbleSlab;
import static net.canarymod.api.world.blocks.BlockType.CrackedSilverFishBlock;
import static net.canarymod.api.world.blocks.BlockType.CrackedStoneBrick;
import static net.canarymod.api.world.blocks.BlockType.CyanCarpet;
import static net.canarymod.api.world.blocks.BlockType.CyanGlass;
import static net.canarymod.api.world.blocks.BlockType.CyanGlassPane;
import static net.canarymod.api.world.blocks.BlockType.CyanStainedClay;
import static net.canarymod.api.world.blocks.BlockType.DarkOakLeaves;
import static net.canarymod.api.world.blocks.BlockType.DarkOakLog;
import static net.canarymod.api.world.blocks.BlockType.DarkOakSapling;
import static net.canarymod.api.world.blocks.BlockType.DarkOakWood;
import static net.canarymod.api.world.blocks.BlockType.DarkOakWoodSlab;
import static net.canarymod.api.world.blocks.BlockType.DarkPrismarine;
import static net.canarymod.api.world.blocks.BlockType.Diorite;
import static net.canarymod.api.world.blocks.BlockType.DoubleAcaciaWoodSlab;
import static net.canarymod.api.world.blocks.BlockType.DoubleBirchWoodSlab;
import static net.canarymod.api.world.blocks.BlockType.DoubleBrickBlockSlab;
import static net.canarymod.api.world.blocks.BlockType.DoubleCobbleSlab;
import static net.canarymod.api.world.blocks.BlockType.DoubleDarkOakWoodSlab;
import static net.canarymod.api.world.blocks.BlockType.DoubleGrass;
import static net.canarymod.api.world.blocks.BlockType.DoubleJungleWoodSlab;
import static net.canarymod.api.world.blocks.BlockType.DoubleNetherBrickSlab;
import static net.canarymod.api.world.blocks.BlockType.DoubleOrnateStoneSlab;
import static net.canarymod.api.world.blocks.BlockType.DoubleQuartzSlab;
import static net.canarymod.api.world.blocks.BlockType.DoubleSandStoneSlab;
import static net.canarymod.api.world.blocks.BlockType.DoubleSandStoneTrimSlab;
import static net.canarymod.api.world.blocks.BlockType.DoubleSpruceWoodSlab;
import static net.canarymod.api.world.blocks.BlockType.DoubleStoneBricksSlab;
import static net.canarymod.api.world.blocks.BlockType.DoubleStoneSlab;
import static net.canarymod.api.world.blocks.BlockType.DoubleWoodSlab;
import static net.canarymod.api.world.blocks.BlockType.Fern;
import static net.canarymod.api.world.blocks.BlockType.Granite;
import static net.canarymod.api.world.blocks.BlockType.GrayCarpet;
import static net.canarymod.api.world.blocks.BlockType.GrayGlass;
import static net.canarymod.api.world.blocks.BlockType.GrayGlassPane;
import static net.canarymod.api.world.blocks.BlockType.GrayStainedClay;
import static net.canarymod.api.world.blocks.BlockType.GreenCarpet;
import static net.canarymod.api.world.blocks.BlockType.GreenGlass;
import static net.canarymod.api.world.blocks.BlockType.GreenGlassPane;
import static net.canarymod.api.world.blocks.BlockType.GreenStainedClay;
import static net.canarymod.api.world.blocks.BlockType.JungleLeaves;
import static net.canarymod.api.world.blocks.BlockType.JungleLog;
import static net.canarymod.api.world.blocks.BlockType.JungleSapling;
import static net.canarymod.api.world.blocks.BlockType.JungleWood;
import static net.canarymod.api.world.blocks.BlockType.JungleWoodSlab;
import static net.canarymod.api.world.blocks.BlockType.LargeFern;
import static net.canarymod.api.world.blocks.BlockType.LightBlueCarpet;
import static net.canarymod.api.world.blocks.BlockType.LightBlueGlass;
import static net.canarymod.api.world.blocks.BlockType.LightBlueGlassPane;
import static net.canarymod.api.world.blocks.BlockType.LightBlueStainedClay;
import static net.canarymod.api.world.blocks.BlockType.LightGrayCarpet;
import static net.canarymod.api.world.blocks.BlockType.LightGrayGlass;
import static net.canarymod.api.world.blocks.BlockType.LightGrayGlassPane;
import static net.canarymod.api.world.blocks.BlockType.LightGrayStainedClay;
import static net.canarymod.api.world.blocks.BlockType.Lilac;
import static net.canarymod.api.world.blocks.BlockType.LimeCarpet;
import static net.canarymod.api.world.blocks.BlockType.LimeGlass;
import static net.canarymod.api.world.blocks.BlockType.LimeGlassPane;
import static net.canarymod.api.world.blocks.BlockType.LimeStainedClay;
import static net.canarymod.api.world.blocks.BlockType.MagentaCarpet;
import static net.canarymod.api.world.blocks.BlockType.MagentaGlass;
import static net.canarymod.api.world.blocks.BlockType.MagentaGlassPane;
import static net.canarymod.api.world.blocks.BlockType.MagentaStainedClay;
import static net.canarymod.api.world.blocks.BlockType.MossyBrickSilverFishBlock;
import static net.canarymod.api.world.blocks.BlockType.MossyStoneBrick;
import static net.canarymod.api.world.blocks.BlockType.NetherBricksSlab;
import static net.canarymod.api.world.blocks.BlockType.OrangeCarpet;
import static net.canarymod.api.world.blocks.BlockType.OrangeGlass;
import static net.canarymod.api.world.blocks.BlockType.OrangeGlassPane;
import static net.canarymod.api.world.blocks.BlockType.OrangeStainedClay;
import static net.canarymod.api.world.blocks.BlockType.OrangeTulip;
import static net.canarymod.api.world.blocks.BlockType.OrnateQuartzBlock;
import static net.canarymod.api.world.blocks.BlockType.OrnateSilverFishBlock;
import static net.canarymod.api.world.blocks.BlockType.OrnateStoneBrick;
import static net.canarymod.api.world.blocks.BlockType.OrnateStoneSlab;
import static net.canarymod.api.world.blocks.BlockType.OxeyeDaisy;
import static net.canarymod.api.world.blocks.BlockType.Peony;
import static net.canarymod.api.world.blocks.BlockType.PineLeaves;
import static net.canarymod.api.world.blocks.BlockType.PineLog;
import static net.canarymod.api.world.blocks.BlockType.PinkCarpet;
import static net.canarymod.api.world.blocks.BlockType.PinkGlass;
import static net.canarymod.api.world.blocks.BlockType.PinkGlassPane;
import static net.canarymod.api.world.blocks.BlockType.PinkStainedClay;
import static net.canarymod.api.world.blocks.BlockType.PinkTulip;
import static net.canarymod.api.world.blocks.BlockType.Podzol;
import static net.canarymod.api.world.blocks.BlockType.PolishedAndesite;
import static net.canarymod.api.world.blocks.BlockType.PolishedDiorite;
import static net.canarymod.api.world.blocks.BlockType.PolishedGranite;
import static net.canarymod.api.world.blocks.BlockType.PrismarineBricks;
import static net.canarymod.api.world.blocks.BlockType.PurpleCarpet;
import static net.canarymod.api.world.blocks.BlockType.PurpleGlass;
import static net.canarymod.api.world.blocks.BlockType.PurpleGlassPane;
import static net.canarymod.api.world.blocks.BlockType.PurpleStainedClay;
import static net.canarymod.api.world.blocks.BlockType.QuartzPillarCap;
import static net.canarymod.api.world.blocks.BlockType.QuartzPillarHorizontal;
import static net.canarymod.api.world.blocks.BlockType.QuartzPillarVertical;
import static net.canarymod.api.world.blocks.BlockType.QuartzSlab;
import static net.canarymod.api.world.blocks.BlockType.RedCarpet;
import static net.canarymod.api.world.blocks.BlockType.RedGlass;
import static net.canarymod.api.world.blocks.BlockType.RedGlassPane;
import static net.canarymod.api.world.blocks.BlockType.RedSandstoneBlank;
import static net.canarymod.api.world.blocks.BlockType.RedSandstoneOrnate;
import static net.canarymod.api.world.blocks.BlockType.RedStainedClay;
import static net.canarymod.api.world.blocks.BlockType.RedTulip;
import static net.canarymod.api.world.blocks.BlockType.RoseBush;
import static net.canarymod.api.world.blocks.BlockType.SandStoneSlab;
import static net.canarymod.api.world.blocks.BlockType.SandStoneTrimSlab;
import static net.canarymod.api.world.blocks.BlockType.SandstoneBlank;
import static net.canarymod.api.world.blocks.BlockType.SandstoneOrnate;
import static net.canarymod.api.world.blocks.BlockType.SpruceSapling;
import static net.canarymod.api.world.blocks.BlockType.SpruceWood;
import static net.canarymod.api.world.blocks.BlockType.SpruceWoodSlab;
import static net.canarymod.api.world.blocks.BlockType.StoneBrickSilverFishBlock;
import static net.canarymod.api.world.blocks.BlockType.StoneBricksSlab;
import static net.canarymod.api.world.blocks.BlockType.StoneSlab;
import static net.canarymod.api.world.blocks.BlockType.TallGrass;
import static net.canarymod.api.world.blocks.BlockType.WhiteCarpet;
import static net.canarymod.api.world.blocks.BlockType.WhiteGlass;
import static net.canarymod.api.world.blocks.BlockType.WhiteGlassPane;
import static net.canarymod.api.world.blocks.BlockType.WhiteStainedClay;
import static net.canarymod.api.world.blocks.BlockType.WhiteTulip;
import static net.canarymod.api.world.blocks.BlockType.WoodSlab;
import static net.canarymod.api.world.blocks.BlockType.WoolBlack;
import static net.canarymod.api.world.blocks.BlockType.WoolBlue;
import static net.canarymod.api.world.blocks.BlockType.WoolBrown;
import static net.canarymod.api.world.blocks.BlockType.WoolCyan;
import static net.canarymod.api.world.blocks.BlockType.WoolDarkGreen;
import static net.canarymod.api.world.blocks.BlockType.WoolGray;
import static net.canarymod.api.world.blocks.BlockType.WoolLightBlue;
import static net.canarymod.api.world.blocks.BlockType.WoolLightGray;
import static net.canarymod.api.world.blocks.BlockType.WoolLightGreen;
import static net.canarymod.api.world.blocks.BlockType.WoolMagenta;
import static net.canarymod.api.world.blocks.BlockType.WoolOrange;
import static net.canarymod.api.world.blocks.BlockType.WoolPink;
import static net.canarymod.api.world.blocks.BlockType.WoolPurple;
import static net.canarymod.api.world.blocks.BlockType.WoolRed;
import static net.canarymod.api.world.blocks.BlockType.WoolWhite;
import static net.canarymod.api.world.blocks.BlockType.WoolYellow;
import static net.canarymod.api.world.blocks.BlockType.YellowCarpet;
import static net.canarymod.api.world.blocks.BlockType.YellowGlass;
import static net.canarymod.api.world.blocks.BlockType.YellowGlassPane;
import static net.canarymod.api.world.blocks.BlockType.YellowStainedClay;

/**
 * Native mapper for those special blocks that change entirely based on state properties
 *
 * @author Jason Jones (darkdiplomat)
 */
public enum BlockStateMapper {
    /* Stone Variants */
    GRANITE(Granite, BlockStone.a, BlockStone.EnumType.GRANITE),
    GRANITE_SMOOTH(PolishedGranite, BlockStone.a, BlockStone.EnumType.GRANITE_SMOOTH),
    DIORITE(Diorite, BlockStone.a, BlockStone.EnumType.DIORITE),
    DIORITE_SMOOTH(PolishedDiorite, BlockStone.a, BlockStone.EnumType.DIORITE_SMOOTH),
    ANDESITE(Andesite, BlockStone.a, BlockStone.EnumType.ANDESITE),
    ANDESITE_SMOOTH(PolishedAndesite, BlockStone.a, BlockStone.EnumType.ANDESITE_SMOOTH),

    /* Dirt Variants */
    COARSE_DIRT(CoarseDirt, BlockDirt.a, BlockDirt.DirtType.COARSE_DIRT),
    PODZOL(Podzol, BlockDirt.a, BlockDirt.DirtType.PODZOL),

    /* Wood Planks Variants */
    PLANKS_SPRUCE(SpruceWood, BlockPlanks.a, BlockPlanks.EnumType.SPRUCE),
    PLANKS_BIRCH(BirchWood, BlockPlanks.a, BlockPlanks.EnumType.BIRCH),
    PLANKS_JUNGLE(JungleWood, BlockPlanks.a, BlockPlanks.EnumType.JUNGLE),
    PLANKS_ACACIA(AcaciaWood, BlockPlanks.a, BlockPlanks.EnumType.ACACIA),
    PLANKS_DARKOAK(DarkOakWood, BlockPlanks.a, BlockPlanks.EnumType.OAK),

    /* Sapling Variants */
    SAPLING_SPRUCE(SpruceSapling, BlockSapling.a, BlockPlanks.EnumType.SPRUCE),
    SAPLING_BIRCH(BirchSapling, BlockSapling.a, BlockPlanks.EnumType.BIRCH),
    SAPLING_JUNGLE(JungleSapling, BlockSapling.a, BlockPlanks.EnumType.JUNGLE),
    SAPLING_ACACIA(AcaciaSapling, BlockSapling.a, BlockPlanks.EnumType.ACACIA),
    SAPLING_DARKOAK(DarkOakSapling, BlockSapling.a, BlockPlanks.EnumType.OAK),

    /* Log Variants */
    LOG_SPRUCE(PineLog, BlockOldLog.b, BlockPlanks.EnumType.SPRUCE),
    LOG_BIRCH(BirchLog, BlockOldLog.b, BlockPlanks.EnumType.BIRCH),
    LOG_JUNGLE(JungleLog, BlockOldLog.b, BlockPlanks.EnumType.JUNGLE),
    LOG_ACACIA(AcaciaLog, BlockNewLog.b, BlockPlanks.EnumType.ACACIA),
    LOG_DARKOAK(DarkOakLog, BlockNewLog.b, BlockPlanks.EnumType.DARK_OAK),

    /* Leaves Variants */
    LEAVES_SPRUCE(PineLeaves, BlockOldLeaf.P, BlockPlanks.EnumType.SPRUCE),
    LEAVES_BIRCH(BirchLeaves, BlockOldLeaf.P, BlockPlanks.EnumType.BIRCH),
    LEAVES_JUNGLE(JungleLeaves, BlockOldLeaf.P, BlockPlanks.EnumType.JUNGLE),
    LEAVES_ACACIA(AcaciaLeaves, BlockNewLeaf.P, BlockPlanks.EnumType.ACACIA),
    LEAVES_DARKOAK(DarkOakLeaves, BlockNewLeaf.P, BlockPlanks.EnumType.DARK_OAK),

    /* Sandstone Variants */
    SANDSTONE_CHISLED(SandstoneOrnate, BlockSandStone.a, BlockSandStone.EnumType.CHISELED),
    SANDSTONE_SMOOTH(SandstoneBlank, BlockSandStone.a, BlockSandStone.EnumType.SMOOTH),

    /* Grass Variants */
    TALLGRASS(TallGrass, BlockTallGrass.a, BlockTallGrass.EnumType.GRASS),
    FERN(Fern, BlockTallGrass.a, BlockTallGrass.EnumType.FERN),

    /* Wool Variants */
    WOOL_WHITE(WoolWhite, BlockColored.a, EnumDyeColor.WHITE),
    WOOL_ORANGE(WoolOrange, BlockColored.a, EnumDyeColor.ORANGE),
    WOOL_MAGENTA(WoolMagenta, BlockColored.a, EnumDyeColor.MAGENTA),
    WOOL_LIGHTBLUE(WoolLightBlue, BlockColored.a, EnumDyeColor.LIGHT_BLUE),
    WOOL_YELLOW(WoolYellow, BlockColored.a, EnumDyeColor.YELLOW),
    WOOL_LIME(WoolLightGreen, BlockColored.a, EnumDyeColor.LIME),
    WOOL_PINK(WoolPink, BlockColored.a, EnumDyeColor.PINK),
    WOOL_GRAY(WoolGray, BlockColored.a, EnumDyeColor.GRAY),
    WOOL_SILVER(WoolLightGray, BlockColored.a, EnumDyeColor.SILVER),
    WOOL_CYAN(WoolCyan, BlockColored.a, EnumDyeColor.CYAN),
    WOOL_PURPLE(WoolPurple, BlockColored.a, EnumDyeColor.PURPLE),
    WOOL_BLUE(WoolBlue, BlockColored.a, EnumDyeColor.BLUE),
    WOOL_BROWN(WoolBrown, BlockColored.a, EnumDyeColor.BROWN),
    WOOL_GREEN(WoolDarkGreen, BlockColored.a, EnumDyeColor.GREEN),
    WOOL_RED(WoolRed, BlockColored.a, EnumDyeColor.RED),
    WOOL_BLACK(WoolBlack, BlockColored.a, EnumDyeColor.BLACK),

    /* Red Flower Variants */
    BLUEORCHID(BlueOrchid, Blocks.O.l(), BlockFlower.EnumFlowerType.BLUE_ORCHID),
    ALLIUM(Allium, Blocks.O.l(), BlockFlower.EnumFlowerType.ALLIUM),
    HOUSTONIA(AzureBluet, Blocks.O.l(), BlockFlower.EnumFlowerType.HOUSTONIA),
    REDTULIP(RedTulip, Blocks.O.l(), BlockFlower.EnumFlowerType.RED_TULIP),
    ORANGETULIP(OrangeTulip, Blocks.O.l(), BlockFlower.EnumFlowerType.ORANGE_TULIP),
    WHITETULIP(WhiteTulip, Blocks.O.l(), BlockFlower.EnumFlowerType.WHITE_TULIP),
    PINKTULIP(PinkTulip, Blocks.O.l(), BlockFlower.EnumFlowerType.PINK_TULIP),
    OXEYEDAISY(OxeyeDaisy, Blocks.O.l(), BlockFlower.EnumFlowerType.OXEYE_DAISY),

    /* Double Slab Variants */
    DOUBLESLAB_STONE_TRIM(DoubleOrnateStoneSlab, new IProperty[]{ BlockStoneSlab.M, BlockDoubleStoneSlab.b }, new Comparable[]{ BlockStoneSlab.EnumType.STONE, true }),
    DOUBLESLAB_SANDSTONE_TRIM(DoubleSandStoneTrimSlab, new IProperty[]{ BlockStoneSlab.M, BlockStoneSlab.b }, new Comparable[]{ BlockStoneSlab.EnumType.SAND, true }),
    DOUBLESLAB_WOOD(DoubleWoodSlab, BlockStoneSlab.M, BlockStoneSlab.EnumType.WOOD),
    DOUBLESLAB_COBBLE(DoubleCobbleSlab, BlockStoneSlab.M, BlockStoneSlab.EnumType.COBBLESTONE),
    DOUBLESLAB_BRICK(DoubleBrickBlockSlab, BlockStoneSlab.M, BlockStoneSlab.EnumType.BRICK),
    DOUBLESLAB_STONEBRICK(DoubleStoneBricksSlab, BlockStoneSlab.M, BlockStoneSlab.EnumType.SMOOTHBRICK),
    DOUBLESLAB_NETHERBRICK(DoubleNetherBrickSlab, BlockStoneSlab.M, BlockStoneSlab.EnumType.NETHERBRICK),
    DOUBLESLAB_QUARTZ(DoubleQuartzSlab, BlockStoneSlab.M, BlockStoneSlab.EnumType.QUARTZ),
    DOUBLESLAB_STONE(DoubleStoneSlab, BlockStoneSlab.M, BlockStoneSlab.EnumType.STONE),
    DOUBLESLAB_SANDSTONE(DoubleSandStoneSlab, BlockStoneSlab.M, BlockStoneSlab.EnumType.SAND),

    /* Slab Variants */
    SLAB_STONE_TRIM(OrnateStoneSlab, new IProperty[]{ BlockStoneSlab.M, BlockDoubleStoneSlab.b }, new Comparable[]{ BlockStoneSlab.EnumType.STONE, true }),
    SLAB_SANDSTONE_TRIM(SandStoneTrimSlab, new IProperty[]{ BlockStoneSlab.M, BlockStoneSlab.b }, new Comparable[]{ BlockStoneSlab.EnumType.SAND, true }),
    SLAB_WOOD(WoodSlab, BlockStoneSlab.M, BlockStoneSlab.EnumType.WOOD),
    SLAB_COBBLE(CobbleSlab, BlockStoneSlab.M, BlockStoneSlab.EnumType.COBBLESTONE),
    SLAB_BRICK(BrickBlockSlab, BlockStoneSlab.M, BlockStoneSlab.EnumType.BRICK),
    SLAB_STONEBRICK(StoneBricksSlab, BlockStoneSlab.M, BlockStoneSlab.EnumType.SMOOTHBRICK),
    SLAB_NETHERBRICK(NetherBricksSlab, BlockStoneSlab.M, BlockStoneSlab.EnumType.NETHERBRICK),
    SLAB_QUARTZ(QuartzSlab, BlockStoneSlab.M, BlockStoneSlab.EnumType.QUARTZ),
    SLAB_STONE(StoneSlab, BlockStoneSlab.M, BlockStoneSlab.EnumType.STONE),
    SLAB_SANDSTONE(SandStoneSlab, BlockStoneSlab.M, BlockStoneSlab.EnumType.SAND),

    /* Stained Glass Variants */
    STAINEDGLASS_WHITE(WhiteGlass, BlockStainedGlass.a, EnumDyeColor.WHITE),
    STAINEDGLASS_ORANGE(OrangeGlass, BlockStainedGlass.a, EnumDyeColor.ORANGE),
    STAINEDGLASS_MAGENTA(MagentaGlass, BlockStainedGlass.a, EnumDyeColor.MAGENTA),
    STAINEDGLASS_LIGHTBLUE(LightBlueGlass, BlockStainedGlass.a, EnumDyeColor.LIGHT_BLUE),
    STAINEDGLASS_YELLOW(YellowGlass, BlockStainedGlass.a, EnumDyeColor.YELLOW),
    STAINEDGLASS_LIME(LimeGlass, BlockStainedGlass.a, EnumDyeColor.LIME),
    STAINEDGLASS_PINK(PinkGlass, BlockStainedGlass.a, EnumDyeColor.PINK),
    STAINEDGLASS_GRAY(GrayGlass, BlockStainedGlass.a, EnumDyeColor.GRAY),
    STAINEDGLASS_SILVER(LightGrayGlass, BlockStainedGlass.a, EnumDyeColor.SILVER),
    STAINEDGLASS_CYAN(CyanGlass, BlockStainedGlass.a, EnumDyeColor.CYAN),
    STAINEDGLASS_PURPLE(PurpleGlass, BlockStainedGlass.a, EnumDyeColor.PURPLE),
    STAINEDGLASS_BLUE(BlueGlass, BlockStainedGlass.a, EnumDyeColor.BLUE),
    STAINEDGLASS_BROWN(BrownGlass, BlockStainedGlass.a, EnumDyeColor.BROWN),
    STAINEDGLASS_GREEN(GreenGlass, BlockStainedGlass.a, EnumDyeColor.GREEN),
    STAINEDGLASS_RED(RedGlass, BlockStainedGlass.a, EnumDyeColor.RED),
    STAINEDGLASS_BLACK(BlackGlass, BlockStainedGlass.a, EnumDyeColor.BLACK),

    /* Monster Egg (SliverFish) Variants */
    MONSTEREGG_COBBLE(CobbleSilverFishBlock, BlockSilverfish.a, BlockSilverfish.EnumType.COBBLESTONE),
    MONSTEREGG_STONEBRICK(StoneBrickSilverFishBlock, BlockSilverfish.a, BlockSilverfish.EnumType.STONEBRICK),
    MONSTEREGG_MOSSYBRICK(MossyBrickSilverFishBlock, BlockSilverfish.a, BlockSilverfish.EnumType.MOSSY_STONEBRICK),
    MONSTEREGG_CRACKEDBRICK(CrackedSilverFishBlock, BlockSilverfish.a, BlockSilverfish.EnumType.CRACKED_STONEBRICK),
    MONSTEREGG_ORNATEBRICK(OrnateSilverFishBlock, BlockSilverfish.a, BlockSilverfish.EnumType.CHISELED_STONEBRICK),

    /* Stone Brick Variants */
    STONEBRICK_MOSSY(MossyStoneBrick, BlockStoneBrick.a, BlockStoneBrick.EnumType.MOSSY),
    STONEBRICK_CRACKED(CrackedStoneBrick, BlockStoneBrick.a, BlockStoneBrick.EnumType.CRACKED),
    STONEBRICK_CHISELED(OrnateStoneBrick, BlockStoneBrick.a, BlockStoneBrick.EnumType.CHISELED),

    /* Double Wood Slab Variants */
    DOUBLEWOODSLAB_SPRUCE(DoubleSpruceWoodSlab, BlockWoodSlab.b, BlockPlanks.EnumType.SPRUCE),
    DOUBLEWOODSLAB_BIRCH(DoubleBirchWoodSlab, BlockWoodSlab.b, BlockPlanks.EnumType.BIRCH),
    DOUBLEWOODSLAB_JUNGLE(DoubleJungleWoodSlab, BlockWoodSlab.b, BlockPlanks.EnumType.JUNGLE),
    DOUBLEWOODSLAB_ACACIA(DoubleAcaciaWoodSlab, BlockWoodSlab.b, BlockPlanks.EnumType.ACACIA),
    DOUBLEWOODSLAB_DARKOAK(DoubleDarkOakWoodSlab, BlockWoodSlab.b, BlockPlanks.EnumType.DARK_OAK),

    /* Wood Slab Variants */
    WOODSLAB_SPRUCE(SpruceWoodSlab, BlockWoodSlab.b, BlockPlanks.EnumType.SPRUCE),
    WOODSLAB_BIRCH(BirchWoodSlab, BlockWoodSlab.b, BlockPlanks.EnumType.BIRCH),
    WOODSLAB_JUNGLE(JungleWoodSlab, BlockWoodSlab.b, BlockPlanks.EnumType.JUNGLE),
    WOODSLAB_ACACIA(AcaciaWoodSlab, BlockWoodSlab.b, BlockPlanks.EnumType.ACACIA),
    WOODSLAB_DARKOAK(DarkOakWoodSlab, BlockWoodSlab.b, BlockPlanks.EnumType.DARK_OAK),

    /*
    /* Skull Variants * / // Which I have no good way of switching state of...
    SKULL_SKELETON(SkeletonHead, BlockSkull.?, ?),
    SKULL_WITHER(WitherSkeletonHead, BlockSkull.?, ?),
    SKULL_ZOMBIE(ZombieHead, BlockSkull.?, ?),
    SKULL_HUMAN(HumanHead, BlockSkull.?, ?),
    SKULL_CREEPER(CreeperHead, BlockSkull.?, ?),
    */

    /* Quartz Variants */
    QUARTZ_CHISELED(OrnateQuartzBlock, BlockQuartz.a, BlockQuartz.EnumType.CHISELED),
    QUARTZ_PILLAR_VERTICAL(QuartzPillarVertical, BlockQuartz.a, BlockQuartz.EnumType.LINES_Y),
    QUARTZ_PILLAR_HORIZONTAL(QuartzPillarHorizontal, BlockQuartz.a, BlockQuartz.EnumType.LINES_X),
    QUARTZ_PILLAR_CAP(QuartzPillarCap, BlockQuartz.a, BlockQuartz.EnumType.LINES_Z),

    /* Stained Clay Variants */
    STAINEDCLAY_WHITE(WhiteStainedClay, BlockColored.a, EnumDyeColor.WHITE),
    STAINEDCLAY_ORANGE(OrangeStainedClay, BlockColored.a, EnumDyeColor.ORANGE),
    STAINEDCLAY_MAGENTA(MagentaStainedClay, BlockColored.a, EnumDyeColor.MAGENTA),
    STAINEDCLAY_LIGHTBLUE(LightBlueStainedClay, BlockColored.a, EnumDyeColor.LIGHT_BLUE),
    STAINEDCLAY_YELLOW(YellowStainedClay, BlockColored.a, EnumDyeColor.YELLOW),
    STAINEDCLAY_LIME(LimeStainedClay, BlockColored.a, EnumDyeColor.LIME),
    STAINEDCLAY_PINK(PinkStainedClay, BlockColored.a, EnumDyeColor.PINK),
    STAINEDCLAY_GRAY(GrayStainedClay, BlockColored.a, EnumDyeColor.GRAY),
    STAINEDCLAY_SILVER(LightGrayStainedClay, BlockColored.a, EnumDyeColor.SILVER),
    STAINEDCLAY_CYAN(CyanStainedClay, BlockColored.a, EnumDyeColor.CYAN),
    STAINEDCLAY_PURPLE(PurpleStainedClay, BlockColored.a, EnumDyeColor.PURPLE),
    STAINEDCLAY_BLUE(BlueStainedClay, BlockColored.a, EnumDyeColor.BLUE),
    STAINEDCLAY_BROWN(BrownStainedClay, BlockColored.a, EnumDyeColor.BROWN),
    STAINEDCLAY_GREEN(GreenStainedClay, BlockColored.a, EnumDyeColor.GREEN),
    STAINEDCLAY_RED(RedStainedClay, BlockColored.a, EnumDyeColor.RED),
    STAINEDCLAY_BLACK(BlackStainedClay, BlockColored.a, EnumDyeColor.BLACK),

    /* Stained GlassPane Variants */
    STAINEDGLASSPANE_WHITE(WhiteGlassPane, BlockStainedGlassPane.a, EnumDyeColor.WHITE),
    STAINEDGLASSPANE_ORANGE(OrangeGlassPane, BlockStainedGlassPane.a, EnumDyeColor.ORANGE),
    STAINEDGLASSPANE_MAGENTA(MagentaGlassPane, BlockStainedGlassPane.a, EnumDyeColor.MAGENTA),
    STAINEDGLASSPANE_LIGHTBLUE(LightBlueGlassPane, BlockStainedGlassPane.a, EnumDyeColor.LIGHT_BLUE),
    STAINEDGLASSPANE_YELLOW(YellowGlassPane, BlockStainedGlassPane.a, EnumDyeColor.YELLOW),
    STAINEDGLASSPANE_LIME(LimeGlassPane, BlockStainedGlassPane.a, EnumDyeColor.LIME),
    STAINEDGLASSPANE_PINK(PinkGlassPane, BlockStainedGlassPane.a, EnumDyeColor.PINK),
    STAINEDGLASSPANE_GRAY(GrayGlassPane, BlockStainedGlassPane.a, EnumDyeColor.GRAY),
    STAINEDGLASSPANE_SILVER(LightGrayGlassPane, BlockStainedGlassPane.a, EnumDyeColor.SILVER),
    STAINEDGLASSPANE_CYAN(CyanGlassPane, BlockStainedGlassPane.a, EnumDyeColor.CYAN),
    STAINEDGLASSPANE_PURPLE(PurpleGlassPane, BlockStainedGlassPane.a, EnumDyeColor.PURPLE),
    STAINEDGLASSPANE_BLUE(BlueGlassPane, BlockStainedGlassPane.a, EnumDyeColor.BLUE),
    STAINEDGLASSPANE_BROWN(BrownGlassPane, BlockStainedGlassPane.a, EnumDyeColor.BROWN),
    STAINEDGLASSPANE_GREEN(GreenGlassPane, BlockStainedGlassPane.a, EnumDyeColor.GREEN),
    STAINEDGLASSPANE_RED(RedGlassPane, BlockStainedGlassPane.a, EnumDyeColor.RED),
    STAINEDGLASSPANE_BLACK(BlackGlassPane, BlockStainedGlassPane.a, EnumDyeColor.BLACK),

    /* Prismarine Variants */
    PRISMARINE_BRICKS(PrismarineBricks, BlockPrismarine.a, BlockPrismarine.EnumType.BRICKS),
    PRISMARING_DARK(DarkPrismarine, BlockPrismarine.a, BlockPrismarine.EnumType.DARK),

    /* Carpet Variants */
    CARPET_WHITE(WhiteCarpet, BlockCarpet.a, EnumDyeColor.WHITE),
    CARPET_ORANGE(OrangeCarpet, BlockCarpet.a, EnumDyeColor.ORANGE),
    CARPET_MAGENTA(MagentaCarpet, BlockCarpet.a, EnumDyeColor.MAGENTA),
    CARPET_LIGHTBLUE(LightBlueCarpet, BlockCarpet.a, EnumDyeColor.LIGHT_BLUE),
    CARPET_YELLOW(YellowCarpet, BlockCarpet.a, EnumDyeColor.YELLOW),
    CARPET_LIME(LimeCarpet, BlockCarpet.a, EnumDyeColor.LIME),
    CARPET_PINK(PinkCarpet, BlockCarpet.a, EnumDyeColor.PINK),
    CARPET_GRAY(GrayCarpet, BlockCarpet.a, EnumDyeColor.GRAY),
    CARPET_SILVER(LightGrayCarpet, BlockCarpet.a, EnumDyeColor.SILVER),
    CARPET_CYAN(CyanCarpet, BlockCarpet.a, EnumDyeColor.CYAN),
    CARPET_PURPLE(PurpleCarpet, BlockCarpet.a, EnumDyeColor.PURPLE),
    CARPET_BLUE(BlueCarpet, BlockCarpet.a, EnumDyeColor.BLUE),
    CARPET_BROWN(BrownCarpet, BlockCarpet.a, EnumDyeColor.BROWN),
    CARPET_GREEN(GreenCarpet, BlockCarpet.a, EnumDyeColor.GREEN),
    CARPET_RED(RedCarpet, BlockCarpet.a, EnumDyeColor.RED),
    CARPET_BLACK(BlackCarpet, BlockCarpet.a, EnumDyeColor.BLACK),

    /* Double Plant Variants */
    LILAC(Lilac, BlockDoublePlant.a, BlockDoublePlant.EnumPlantType.SYRINGA),
    DOUBLEGRASS(DoubleGrass, BlockDoublePlant.a, BlockDoublePlant.EnumPlantType.GRASS),
    LARGEFERN(LargeFern, BlockDoublePlant.a, BlockDoublePlant.EnumPlantType.FERN),
    ROSEBUSH(RoseBush, BlockDoublePlant.a, BlockDoublePlant.EnumPlantType.ROSE),
    PEONY(Peony, BlockDoublePlant.a, BlockDoublePlant.EnumPlantType.PAEONIA),

    /* Red Sandstone Variants */
    REDSANDSTONE_CHISELED(RedSandstoneOrnate, BlockRedSandstone.a, BlockRedSandstone.EnumType.CHISELED),
    REDSANDSTONE_SMOOTH(RedSandstoneBlank, BlockRedSandstone.a, BlockRedSandstone.EnumType.SMOOTH),;

    private final BlockType type;
    private final IProperty[] properties;
    private final Comparable[] values;

    private BlockStateMapper(BlockType type, IProperty property, Comparable value) {
        this(type, new IProperty[]{ property }, new Comparable[]{ value });
    }

    private BlockStateMapper(BlockType type, IProperty[] properties, Comparable[] values) {
        this.type = type;
        this.properties = properties;
        this.values = values;
    }

    public static IBlockState getStateForType(BlockType type) {
        IBlockState state = Block.b(type.getMachineName()).P();
        if (map.containsKey(type)) {
            BlockStateMapper tts = map.get(type);
            for (int index = 0; index < tts.properties.length; index++) {
                state = state.a(tts.properties[index], tts.values[index]);
            }
        }

        return state;
    }

    private static final HashMap<BlockType, BlockStateMapper> map = Maps.newHashMap();

    static {
        for (BlockStateMapper tts : values()) {
            if (map.containsKey(tts.type)) {
                Canary.log.debug("A BlockType was attempted to be mapped more than once in BlockStateMapper for {}... (Name:{} [Id:{} Data:{}])", tts.name(), tts.type.getMachineName(), tts.type.getId(), tts.type.getData());
                continue;
            }
            map.put(tts.type, tts);
        }
    }
}
