package net.canarymod.util;

import net.canarymod.api.entity.CanaryEntity;
import net.canarymod.api.entity.Entity;
import net.canarymod.api.world.CanaryWorld;
import net.canarymod.api.world.World;
import net.canarymod.api.world.blocks.Block;
import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.api.world.position.BlockPosition;
import net.canarymod.api.world.position.Position;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;

/**
 * Tool for typing less casting and unwrapping
 *
 * @author Jason Jones (darkdiplomat)
 */
public class Unwrapper {

    public static net.minecraft.world.World unwrap(World world) {
        return ((CanaryWorld)world).getHandle();
    }

    public static BlockPos unwrap(Position position) {
        if (position instanceof BlockPosition) {
            return ((BlockPosition)position).asNative();
        }
        return new BlockPosition(position).asNative();
    }

    public static IBlockState unwrap(Block block) {
        return ((CanaryBlock)block).getNativeState();
    }

    public static net.minecraft.entity.Entity unwrap(Entity entity) {
        return ((CanaryEntity)entity).getHandle();
    }
}
