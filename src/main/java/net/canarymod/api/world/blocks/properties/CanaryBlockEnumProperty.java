package net.canarymod.api.world.blocks.properties;

import com.google.common.collect.ImmutableBiMap;
import net.canarymod.Canary;
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
import net.canarymod.api.world.blocks.properties.helpers.SilverfishProperties;
import net.canarymod.api.world.blocks.properties.helpers.SlabProperties;
import net.canarymod.api.world.blocks.properties.helpers.StairsProperties;
import net.canarymod.api.world.blocks.properties.helpers.StoneBrickProperties;
import net.canarymod.api.world.blocks.properties.helpers.StoneProperties;
import net.canarymod.api.world.blocks.properties.helpers.StoneSlabNewProperties;
import net.canarymod.api.world.blocks.properties.helpers.StoneSlabProperties;
import net.canarymod.api.world.blocks.properties.helpers.TallGrassProperties;
import net.canarymod.api.world.blocks.properties.helpers.TrapDoorProperties;
import net.canarymod.api.world.blocks.properties.helpers.WallProperties;
import net.canarymod.api.world.blocks.properties.helpers.WoodProperties;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockFlowerPot;
import net.minecraft.block.BlockHugeMushroom;
import net.minecraft.block.BlockLever;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockPrismarine;
import net.minecraft.block.BlockQuartz;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.BlockRedSandstone;
import net.minecraft.block.BlockRedstoneComparator;
import net.minecraft.block.BlockRedstoneWire;
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
        fill(builder, BlockSilverfish.EnumType.values(), SilverfishProperties.Variant.values());
        fill(builder, BlockSlab.EnumBlockHalf.values(), SlabProperties.Half.values());
        fill(builder, BlockStairs.EnumHalf.values(), StairsProperties.Half.values());
        fill(builder, BlockStairs.EnumShape.values(), StairsProperties.Shape.values());
        fill(builder, BlockStoneBrick.EnumType.values(), StoneBrickProperties.Variant.values());
        fill(builder, BlockStone.EnumType.values(), StoneProperties.Variant.values());
        fill(builder, BlockStoneSlab.EnumType.values(), StoneSlabProperties.Variant.values());
        fill(builder, BlockStoneSlabNew.EnumType.values(), StoneSlabNewProperties.Variant.values());
        fill(builder, BlockTallGrass.EnumType.values(), TallGrassProperties.Variant.values());
        fill(builder, BlockTrapDoor.DoorHalf.values(), TrapDoorProperties.Half.values());
        fill(builder, BlockWall.EnumType.values(), WallProperties.Variant.values());

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

    public boolean canApply(Comparable comparable) {
        return comparable instanceof Enum && super.canApply(convertCanary((Enum)comparable));
    }

    public static Comparable convertNative(Enum oenum){
        if (nmscms.containsKey(oenum)) {
            return nmscms.get(oenum);
        }

        Canary.log.debug("A mapping is missing for Native Enum value: " + oenum);
        return oenum; // Unknown Enum
    }

    public static Comparable convertCanary(Enum oenum) {
        if (nmscms.inverse().containsKey(oenum)) {
            return nmscms.inverse().get(oenum);
        }

        Canary.log.debug("A mapping is missing for Canary Enum value: " + oenum);
        return oenum; // Unknown Enum
    }
}
