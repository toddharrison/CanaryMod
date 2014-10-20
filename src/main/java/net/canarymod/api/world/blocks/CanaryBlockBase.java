package net.canarymod.api.world.blocks;

import com.google.common.annotations.Beta;
import net.canarymod.api.entity.Entity;
import net.canarymod.api.world.World;
import net.canarymod.api.world.position.Position;

import java.util.Random;

/**
 * Block wrapper implementation
 *
 * @author Jason (darkdiplomat)
 */
@Beta
public class CanaryBlockBase implements BlockBase {

    private final net.minecraft.block.Block nmsBlock;

    public CanaryBlockBase(net.minecraft.block.Block nmsBlock){
        this.nmsBlock = nmsBlock;
    }

    @Override
    public boolean isFullBlock() {
        return nmsBlock.m();
    }

    @Override
    public int getLightOpacity() {
        return nmsBlock.n();
    }

    @Override
    public int getLightValue() {
        return nmsBlock.p();
    }

    @Override
    public boolean getUseNeighborBrightness() {
        return nmsBlock.q();
    }

    @Override
    public BlockMaterial getMaterial() {
        return nmsBlock.r().getCanaryBlockMaterial();
    }

    @Override
    public MapColor getMapColor() {
        return null;
    }

    @Override
    public boolean isSolidFullCube() {
        return false;
    }

    @Override
    public boolean isNormalCube() {
        return false;
    }

    @Override
    public boolean isVisuallyOpaque() {
        return false;
    }

    @Override
    public boolean isFullCube() {
        return false;
    }

    @Override
    public boolean isPassable(Block block, Position position) {
        return false;
    }

    @Override
    public int getRenderType() {
        return 0;
    }

    @Override
    public boolean isReplaceable(World world, Position position) {
        return false;
    }

    @Override
    public float getBlockHardness(World world, Position position) {
        return 0;
    }

    @Override
    public boolean ticksRandomly() {
        return false;
    }

    @Override
    public boolean hasTileEntity() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean isCollidable() {
        return false;
    }

    @Override
    public int tickRate(World world) {
        return 0;
    }

    @Override
    public int quantityDropped(Random random) {
        return 0;
    }

    @Override
    public int damageDropped(Block block) {
        return 0;
    }

    @Override
    public float getExplosionResistance(Entity entity) {
        return 0;
    }

    @Override
    public double getBlockBoundsMinX() {
        return 0;
    }

    @Override
    public double getBlockBoundsMaxX() {
        return 0;
    }

    @Override
    public double getBlockBoundsMinY() {
        return 0;
    }

    @Override
    public double getBlockBoundsMaxY() {
        return 0;
    }

    @Override
    public double getBlockBoundsMinZ() {
        return 0;
    }

    @Override
    public double getBlockBoundsMaxZ() {
        return 0;
    }

    @Override
    public boolean canProvidePower() {
        return false;
    }

    @Override
    public String getLocalizedName() {
        return null;
    }

    @Override
    public boolean getEnableStats() {
        return false;
    }

    @Override
    public int getMobilityFlag() {
        return 0;
    }

    @Override
    public boolean requiresUpdates() {
        return false;
    }

    @Override
    public boolean hasComparatorInputOverride() {
        return false;
    }

    @Override
    public int getComparatorInputOverride(World world, Position position) {
        return 0;
    }
}
