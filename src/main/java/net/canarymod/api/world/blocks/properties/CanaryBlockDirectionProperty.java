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
}
