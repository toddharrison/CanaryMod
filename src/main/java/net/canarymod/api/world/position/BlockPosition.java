package net.canarymod.api.world.position;

import net.canarymod.api.world.blocks.BlockFace;
import net.minecraft.util.BlockPos;

/**
 * A cross over for BlockPos
 *
 * @author Jason Jones (darkdiplomat)
 */
public class BlockPosition extends Position {

    public BlockPosition(BlockPos blockPos) {
        super(blockPos.n(), blockPos.o(), blockPos.p());
    }

    public BlockPosition(Position templ) {
        super(templ);
    }

    public void transform(BlockFace face) {
        switch (face) {
            case TOP:
                ++this.y;
                break;
            case BOTTOM:
                --this.y;
                break;
            case NORTH: // -Z
                --this.z;
                break;
            case SOUTH: // +Z
                ++this.z;
                break;
            case WEST: // -X
                --this.x;
                break;
            case EAST: // +X
                ++this.x;
                break;
            default:
                break;
        }
    }

    public BlockPosition clone() {
        return new BlockPosition(this);
    }
}
