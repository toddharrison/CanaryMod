package net.canarymod.api.world.blocks.properties;

import net.canarymod.api.world.blocks.BlockFace;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.util.EnumFacing;

/**
 * PropertyDirection wrapper implementation
 *
 * @author Jason Jones (darkdiplomat)
 */
public class CanaryBlockDirectionProperty extends CanaryBlockEnumProperty implements BlockDirectionProperty {

    protected CanaryBlockDirectionProperty(PropertyDirection property) {
        super(property);
    }

    public boolean canApply(BlockFace blockFace) {
        EnumFacing facing;
        switch(blockFace){
            case NORTH: facing = EnumFacing.NORTH; break;
            case SOUTH: facing = EnumFacing.SOUTH; break;
            case EAST: facing = EnumFacing.EAST; break;
            case WEST: facing = EnumFacing.WEST; break;
            case BOTTOM: facing = EnumFacing.DOWN; break;
            case TOP: facing = EnumFacing.UP; break;
            default: return false;
        }
        return getAllowedValues().contains(facing);
    }

    public static EnumFacing convertCanary(BlockFace blockFace) {
        switch (blockFace) {
            case NORTH:
                return EnumFacing.NORTH;
            case SOUTH:
                return EnumFacing.SOUTH;
            case EAST:
                return EnumFacing.EAST;
            case WEST:
                return EnumFacing.WEST;
            case BOTTOM:
                return EnumFacing.DOWN;
            default:
                return EnumFacing.UP;
        }
    }

    public static BlockFace convertNative(EnumFacing enumfacing) {
        switch (enumfacing) {
            case NORTH:
                return BlockFace.NORTH;
            case SOUTH:
                return BlockFace.SOUTH;
            case EAST:
                return BlockFace.EAST;
            case WEST:
                return BlockFace.WEST;
            case DOWN:
                return BlockFace.BOTTOM;
            default:
                return BlockFace.TOP;
        }
    }
}
