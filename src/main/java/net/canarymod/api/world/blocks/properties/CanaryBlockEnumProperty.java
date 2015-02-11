package net.canarymod.api.world.blocks.properties;

import com.google.common.collect.ImmutableBiMap;
import net.canarymod.api.DyeColor;
import net.canarymod.api.world.blocks.BlockFace;
import net.canarymod.api.world.blocks.properties.helpers.BedProperties;
import net.canarymod.api.world.blocks.properties.helpers.DirtProperties;
import net.canarymod.api.world.blocks.properties.helpers.DoorProperties;
import net.canarymod.api.world.blocks.properties.helpers.DoublePlantProperties;
import net.canarymod.api.world.blocks.properties.helpers.FlowerPotProperties;
import net.canarymod.api.world.blocks.properties.helpers.FlowerProperties;
import net.canarymod.api.world.blocks.properties.helpers.HugeMushroomProperties;
import net.canarymod.api.world.blocks.properties.helpers.LeverProperties;
import net.canarymod.api.world.blocks.properties.helpers.LogProperties;
import net.canarymod.api.world.blocks.properties.helpers.PrismarineProperties;
import net.canarymod.api.world.blocks.properties.helpers.QuartzProperties;
import net.canarymod.api.world.blocks.properties.helpers.RailProperties;
import net.canarymod.api.world.blocks.properties.helpers.RedSandstoneProperties;
import net.canarymod.api.world.blocks.properties.helpers.RedstoneComparatorProperties;
import net.canarymod.api.world.blocks.properties.helpers.RedstoneWireProperties;
import net.canarymod.api.world.blocks.properties.helpers.SandstoneProperties;
import net.canarymod.api.world.blocks.properties.helpers.WoodProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockFlowerPot;
import net.minecraft.block.BlockHugeMushroom;
import net.minecraft.block.BlockLever;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockPistonExtension;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockPrismarine;
import net.minecraft.block.BlockQuartz;
import net.minecraft.block.BlockRail;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.BlockRedSandstone;
import net.minecraft.block.BlockRedstoneComparator;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockSandStone;
import net.minecraft.block.BlockSilverfish;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockStone;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.BlockStoneSlabNew;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.BlockWall;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.EnumFacing;

/**
 * PropertyEnum wrapper implementation
 *
 * @author Jason Jones (darkdiplomat)
 */
public class CanaryBlockEnumProperty extends CanaryBlockProperty implements BlockEnumProperty {
    private static final ImmutableBiMap<Enum, Enum> nmscms;

    static {
        ImmutableBiMap.Builder<Enum, Enum> builder = ImmutableBiMap.builder();

        fillMinus1(builder, EnumFacing.values(), BlockFace.values()); // Remove UNKNOWN
        fill(builder, EnumFacing.Axis.values(), BlockFace.Axis.values());
        fill(builder, EnumFacing.AxisDirection.values(), BlockFace.AxisDirection.values());
        fill(builder, EnumFacing.Plane.values(), BlockFace.Plane.values());
        fillMinus1(builder, EnumDyeColor.values(), DyeColor.values()); // Remove CUSTOM
        fill(builder, BlockBed.EnumPartType.values(), BedProperties.Half.values());
        fill(builder, BlockDirt.DirtType.values(), DirtProperties.Variant.values());
        fill(builder, BlockDoor.EnumDoorHalf.values(), DoorProperties.Half.values());
        fill(builder, BlockDoor.EnumHingePosition.values(), DoorProperties.HingePosition.values());
        fill(builder, BlockDoublePlant.EnumBlockHalf.values(), DoublePlantProperties.Half.values());
        fill(builder, BlockDoublePlant.EnumPlantType.values(), DoublePlantProperties.Variant.values());
        fill(builder, BlockFlower.EnumFlowerType.values(), FlowerProperties.Type.values());
        fill(builder, BlockFlowerPot.EnumFlowerType.values(), FlowerPotProperties.Contents.values());
        fill(builder, BlockHugeMushroom.EnumType.values(), HugeMushroomProperties.Variant.values());
        fill(builder, BlockLever.EnumOrientation.values(), LeverProperties.Orientation.values());
        fill(builder, BlockLog.EnumAxis.values(), LogProperties.Axis.values());
        fill(builder, BlockPlanks.EnumType.values(), WoodProperties.Variant.values());
        fill(builder, BlockPrismarine.EnumType.values(), PrismarineProperties.Variant.values());
        fill(builder, BlockQuartz.EnumType.values(), QuartzProperties.Variant.values());
        fill(builder, BlockRailBase.EnumRailDirection.values(), RailProperties.Direction.values());
        fill(builder, BlockRedstoneComparator.Mode.values(), RedstoneComparatorProperties.Mode.values());
        fill(builder, BlockRedstoneWire.EnumAttachPosition.values(), RedstoneWireProperties.AttachPosition.values());
        fill(builder, BlockRedSandstone.EnumType.values(), RedSandstoneProperties.Type.values());
        fill(builder, BlockSandStone.EnumType.values(), SandstoneProperties.Type.values());

        nmscms = builder.build();
    }

    private static void fill(ImmutableBiMap.Builder<Enum, Enum> map, Enum[] a, Enum[] b) {
        if (a.length != b.length) {
            throw new IllegalArgumentException("Length mismatch");
        }
        for (int index = 0; index < a.length; index++) {
            map.put(a[index], b[index]);
        }
    }

    private static void fillMinus1(ImmutableBiMap.Builder<Enum, Enum> map, Enum[] a, Enum[] b) {
        if (a.length != b.length - 1) {
            throw new IllegalArgumentException("Length mismatch");
        }
        for (int index = 0; index < a.length; index++) {
            map.put(a[index], b[index]);
        }
    }

    protected CanaryBlockEnumProperty(PropertyEnum property) {
        super(property);

    }

    public boolean canApply(Comparable comparable){
        if(!(comparable instanceof Enum)){
            return false;
        }
        else if (comparable instanceof BlockPropertyEnums.BlockVerticalHalf || comparable instanceof BlockPropertyEnums.SandstoneType){
            return convertApply((Enum)comparable);
        }
        return super.canApply(convertCanary((Enum)comparable, null)); // Block not needed
    }

    public static Comparable convertNative(Enum oenum){
        // New Properties converter
        if (nmscms.containsKey(oenum)) {
            return nmscms.get(oenum);
        }
        //

        // Old style mess
        if(oenum instanceof EnumFacing){
            return CanaryBlockDirectionProperty.convertNative((EnumFacing)oenum);
        }
        else if (oenum instanceof EnumDyeColor){
            return DyeColor.fromColorCode(oenum.ordinal());
        }
        else if (oenum instanceof BlockBed.EnumPartType) {
            return BlockPropertyEnums.BedHalf.valueOf(oenum.ordinal());
        }
        else if (oenum instanceof BlockDirt.DirtType){
            return BlockPropertyEnums.DirtType.valueOf(oenum.ordinal());
        }
        else if (oenum instanceof BlockDoor.EnumDoorHalf){
            return BlockPropertyEnums.BlockVerticalHalf.valueOf(oenum.ordinal());
        }
        else if (oenum instanceof BlockDoor.EnumHingePosition){
            return BlockPropertyEnums.DoorHingePosition.valueOf(oenum.ordinal());
        }
        else if (oenum instanceof BlockDoublePlant.EnumPlantType){
            return BlockPropertyEnums.DoublePlantType.valueOf(oenum.ordinal());
        }
        else if (oenum instanceof BlockDoublePlant.EnumBlockHalf){
            return BlockPropertyEnums.BlockVerticalHalf.valueOf(oenum.ordinal());
        }
        else if (oenum instanceof BlockFlower.EnumFlowerType){
            return BlockPropertyEnums.FlowerType.valueOf(oenum.ordinal());
        }
        else if (oenum instanceof BlockHugeMushroom.EnumType){
            return BlockPropertyEnums.HugeMushroomSection.valueOf(oenum.ordinal());
        }
        else if (oenum instanceof BlockLever.EnumOrientation){
            return BlockPropertyEnums.LeverOrientation.valueOf(oenum.ordinal());
        }
        else if (oenum instanceof BlockLog.EnumAxis){
            return BlockPropertyEnums.LogAxis.valueOf(oenum.ordinal());
        }
        else if (oenum instanceof BlockPistonExtension.EnumPistonType){
            return BlockPropertyEnums.PistonType.valueOf(oenum.ordinal());
        }
        else if (oenum instanceof BlockPlanks.EnumType){
            return BlockPropertyEnums.TreeType.valueOf(oenum.ordinal());
        }
        else if (oenum instanceof BlockPrismarine.EnumType){
            return BlockPropertyEnums.PrismarineType.valueOf(oenum.ordinal());
        }
        else if (oenum instanceof BlockQuartz.EnumType){
            return BlockPropertyEnums.QuartzType.valueOf(oenum.ordinal());
        }
        else if (oenum instanceof BlockRail.EnumRailDirection){
            return BlockPropertyEnums.RailDirection.valueOf(oenum.ordinal());
        }
        else if (oenum instanceof BlockRedSandstone.EnumType){
            return BlockPropertyEnums.SandstoneType.valueOf(oenum.ordinal());
        }
        else if (oenum instanceof BlockRedstoneComparator.Mode){
            return BlockPropertyEnums.ComparatorMode.valueOf(oenum.ordinal());
        }
        else if (oenum instanceof BlockSand.EnumType){
            return BlockPropertyEnums.SandType.valueOf(oenum.ordinal());
        }
        else if (oenum instanceof BlockSandStone.EnumType){
            return BlockPropertyEnums.SandstoneType.valueOf(oenum.ordinal());
        }
        else if (oenum instanceof BlockSilverfish.EnumType){
            return BlockPropertyEnums.SilverfishBlockType.valueOf(oenum.ordinal());
        }
        else if (oenum instanceof BlockSlab.EnumBlockHalf) {
            return BlockPropertyEnums.BlockVerticalHalf.valueOf(oenum.ordinal());
        }
        else if (oenum instanceof BlockStairs.EnumHalf){
            return BlockPropertyEnums.BlockVerticalHalf.valueOf(oenum.ordinal());
        }
        else if (oenum instanceof BlockStairs.EnumShape){
            return BlockPropertyEnums.StairsShape.valueOf(oenum.ordinal());
        }
        else if (oenum instanceof BlockStone.EnumType){
            return BlockPropertyEnums.StoneType.valueOf(oenum.ordinal());
        }
        else if (oenum instanceof BlockStoneBrick.EnumType){
            return BlockPropertyEnums.StoneBrickType.valueOf(oenum.ordinal());
        }
        else if (oenum instanceof BlockStoneSlab.EnumType){
            return BlockPropertyEnums.StoneSlabType.valueOf(oenum.ordinal());
        }
        else if (oenum instanceof BlockStoneSlabNew.EnumType){
            return BlockPropertyEnums.StoneSlabNewType.valueOf(oenum.ordinal());
        }
        else if (oenum instanceof BlockTallGrass.EnumType){
            return BlockPropertyEnums.TallGrassType.valueOf(oenum.ordinal());
        }
        else if (oenum instanceof BlockTrapDoor.DoorHalf){
            return BlockPropertyEnums.BlockVerticalHalf.valueOf(oenum.ordinal());
        }
        else if (oenum instanceof BlockWall.EnumType){
            return BlockPropertyEnums.WallType.valueOf(oenum.ordinal());
        }

        return oenum; // Unknown Enum
    }

    public static Comparable convertCanary(Enum oenum, Block block){
        // New Properties converter
        if (nmscms.inverse().containsKey(oenum)) {
            return nmscms.inverse().get(oenum);
        }
        //

        // Old style mess

        if(oenum instanceof BlockFace){
            return CanaryBlockDirectionProperty.convertCanary((BlockFace)oenum);
        }
        else if (oenum instanceof DyeColor){
            return EnumDyeColor.valueOf(oenum.name());
        }
        else if (oenum instanceof BlockPropertyEnums.BedHalf) {
            return BlockBed.EnumPartType.valueOf(oenum.name());
        }
        else if (oenum instanceof BlockPropertyEnums.DirtType){
            return BlockDirt.DirtType.valueOf(oenum.name());
        }
        else if (oenum instanceof BlockPropertyEnums.BlockVerticalHalf){
            if(block instanceof BlockDoor){
                return BlockDoor.EnumDoorHalf.valueOf(oenum.name());
            }
            else if (block instanceof BlockDoublePlant){
                return BlockDoublePlant.EnumBlockHalf.valueOf(oenum.name());
            }
            else if (block instanceof BlockSlab){
                switch ((BlockPropertyEnums.BlockVerticalHalf)oenum){
                    case UPPER: return BlockSlab.EnumBlockHalf.TOP;
                    case LOWER: return BlockSlab.EnumBlockHalf.BOTTOM;
                }
            }
            else if (block instanceof BlockStairs){
                switch ((BlockPropertyEnums.BlockVerticalHalf)oenum){
                    case UPPER: return BlockStairs.EnumHalf.TOP;
                    case LOWER: return BlockStairs.EnumHalf.BOTTOM;
                }
            }
            else if (block instanceof BlockTrapDoor){
                switch ((BlockPropertyEnums.BlockVerticalHalf)oenum){
                    case UPPER: return BlockTrapDoor.DoorHalf.TOP;
                    case LOWER: return BlockTrapDoor.DoorHalf.BOTTOM;
                }
            }
        }
        else if (oenum instanceof BlockPropertyEnums.DoorHingePosition){
            return BlockDoor.EnumHingePosition.valueOf(oenum.name());
        }
        else if (oenum instanceof BlockPropertyEnums.DoublePlantType){
            return BlockDoublePlant.EnumPlantType.valueOf(oenum.name());
        }
        else if (oenum instanceof BlockPropertyEnums.FlowerType){
            return BlockFlower.EnumFlowerType.valueOf(oenum.name());
        }
        else if (oenum instanceof BlockPropertyEnums.HugeMushroomSection){
            return BlockHugeMushroom.EnumType.valueOf(oenum.name());
        }
        else if (oenum instanceof BlockPropertyEnums.LeverOrientation){
            return BlockLever.EnumOrientation.valueOf(oenum.name());
        }
        else if (oenum instanceof BlockPropertyEnums.LogAxis){
            return BlockLog.EnumAxis.valueOf(oenum.name());
        }
        else if (oenum instanceof BlockPropertyEnums.PistonType){
            return BlockPistonExtension.EnumPistonType.valueOf(oenum.name());
        }
        else if (oenum instanceof BlockPropertyEnums.TreeType){
            return BlockPlanks.EnumType.valueOf(oenum.name());
        }
        else if (oenum instanceof BlockPrismarine.EnumType){
            return BlockPropertyEnums.PrismarineType.valueOf(oenum.ordinal());
        }
        else if (oenum instanceof BlockPropertyEnums.QuartzType){
            return BlockQuartz.EnumType.valueOf(oenum.name());
        }
        else if (oenum instanceof BlockPropertyEnums.RailDirection){
            return BlockRail.EnumRailDirection.valueOf(oenum.name());
        }
        else if (oenum instanceof BlockPropertyEnums.SandstoneType){
            if(block instanceof BlockRedSandstone) {
                return BlockRedSandstone.EnumType.valueOf(oenum.name());
            }
            return BlockSandStone.EnumType.valueOf(oenum.name());
        }
        else if (oenum instanceof BlockPropertyEnums.ComparatorMode){
            return BlockRedstoneComparator.Mode.valueOf(oenum.name());
        }
        else if (oenum instanceof BlockPropertyEnums.SandType){
            return BlockSand.EnumType.valueOf(oenum.name());
        }
        else if (oenum instanceof BlockPropertyEnums.SilverfishBlockType){
            return BlockSilverfish.EnumType.valueOf(oenum.name());
        }
        else if (oenum instanceof BlockPropertyEnums.StairsShape){
            return BlockStairs.EnumShape.valueOf(oenum.name());
        }
        else if (oenum instanceof BlockPropertyEnums.StoneType){
            return BlockStone.EnumType.valueOf(oenum.name());
        }
        else if (oenum instanceof BlockPropertyEnums.StoneBrickType){
            return BlockStoneBrick.EnumType.valueOf(oenum.name());
        }
        else if (oenum instanceof BlockPropertyEnums.StoneSlabType){
            return BlockStoneSlab.EnumType.valueOf(oenum.name());
        }
        else if (oenum instanceof BlockPropertyEnums.StoneSlabNewType){
            return BlockStoneSlabNew.EnumType.valueOf(oenum.name());
        }
        else if (oenum instanceof BlockPropertyEnums.TallGrassType){
            return BlockTallGrass.EnumType.valueOf(oenum.name());
        }
        else if (oenum instanceof BlockPropertyEnums.WallType){
            return BlockWall.EnumType.valueOf(oenum.name());
        }

        return oenum; // Unknown Enum
    }

    private boolean convertApply(Enum oenum){
        if(oenum instanceof BlockPropertyEnums.BlockVerticalHalf){
            if(getAllowedValues().contains(BlockDoor.EnumDoorHalf.LOWER)){
                return super.canApply(convertCanary(oenum, Block.b("minecraft:wooden_door")));
            }
            else if (getAllowedValues().contains(BlockDoublePlant.EnumBlockHalf.LOWER)){
                return super.canApply(convertCanary(oenum, Block.b("minecraft:double_plant")));
            }
            else if (getAllowedValues().contains(BlockSlab.EnumBlockHalf.BOTTOM)){
                return super.canApply(convertCanary(oenum, Block.b("minecraft:stone_slab")));
            }
            else if (getAllowedValues().contains(BlockStairs.EnumHalf.BOTTOM)){
                return super.canApply(convertCanary(oenum, Block.b("minecraft:stone_stairs")));
            }
            else if (getAllowedValues().contains(BlockTrapDoor.DoorHalf.BOTTOM)){
                return super.canApply(convertCanary(oenum, Block.b("minecraft:trapdoor")));
            }
        }
        else if (oenum instanceof BlockPropertyEnums.SandstoneType){
            if(getAllowedValues().contains(BlockRedSandstone.EnumType.DEFAULT)) {
                return super.canApply(convertCanary(oenum, Block.b("minecraft:red_sandstone")));
            }
            return super.canApply(convertCanary(oenum, Block.b("minecraft:sandstone")));
        }
        return false;
    }
}
