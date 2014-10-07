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

    public BlockPosition(int x, int y, int z) {
        super(x, y, z);
    }

    public BlockPosition(BlockPosition templ) {
        super(templ);
    }

    public BlockPosition clone() throws CloneNotSupportedException {
        return (BlockPosition)super.clone();
    }

    public BlockPosition safeClone() {
        try {
            return this.clone();
        }
        catch (CloneNotSupportedException e) {
            //it is supported...
        }
        return new BlockPosition(this);
    }

    public BlockPos asNative() {
        return new BlockPos(getBlockX(), getBlockY(), getBlockZ());
    }
}
