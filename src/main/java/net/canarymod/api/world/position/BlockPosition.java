package net.canarymod.api.world.position;

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

    public BlockPosition clone() {
        return (BlockPosition)super.clone();
    }
}
