package net.canarymod.api.world.blocks.properties;

import com.google.common.collect.ImmutableBiMap;
import net.canarymod.Canary;
import net.canarymod.api.DyeColor;
import net.canarymod.api.world.blocks.BlockFace;
import net.canarymod.api.world.blocks.properties.helpers.*;
import net.minecraft.block.*;
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
        fill(builder, BlockPistonExtension.EnumPistonType.values(), PistonHeadProperties.Type.values());
        fill(builder, BlockPlanks.EnumType.values(), WoodProperties.Variant.values());
        fill(builder, BlockPrismarine.EnumType.values(), PrismarineProperties.Variant.values());
        fill(builder, BlockQuartz.EnumType.values(), QuartzProperties.Variant.values());
        fill(builder, BlockRailBase.EnumRailDirection.values(), RailProperties.Direction.values());
        fill(builder, BlockRedstoneComparator.Mode.values(), RedstoneComparatorProperties.Mode.values());
        fill(builder, BlockRedstoneWire.EnumAttachPosition.values(), RedstoneWireProperties.AttachPosition.values());
        fill(builder, BlockRedSandstone.EnumType.values(), RedSandstoneProperties.Type.values());
        fill(builder, BlockSand.EnumType.values(), SandProperties.Variant.values());
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
}
