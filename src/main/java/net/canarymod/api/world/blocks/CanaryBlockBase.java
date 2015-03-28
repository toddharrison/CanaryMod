package net.canarymod.api.world.blocks;

import com.google.common.annotations.Beta;
import net.canarymod.api.entity.Entity;
import net.canarymod.api.world.World;
import net.canarymod.api.world.position.Position;

import java.util.Random;

import static net.canarymod.util.Unwrapper.unwrap;

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
    public MapColor getMapColor(Block block) {
        return nmsBlock.g(((CanaryBlock)block).getNativeState()).wrapper;
    }

    @Override
    public boolean isSolidFullCube() {
        return nmsBlock.s();
    }

    @Override
    public boolean isNormalCube() {
        return nmsBlock.t();
    }

    @Override
    public boolean isVisuallyOpaque() {
        return nmsBlock.u();
    }

    @Override
    public boolean isFullCube() {
        return nmsBlock.d();
    }

    @Override
    public boolean isPassable(Block block, Position position) {
        return nmsBlock.b(unwrap(block.getWorld()), unwrap(position));
    }

    @Override
    public int getRenderType() {
        return nmsBlock.b();
    }

    @Override
    public boolean isReplaceable(World world, Position position) {
        return nmsBlock.f(unwrap(world), unwrap(position));
    }

    @Override
    public float getBlockHardness(World world, Position position) {
        return nmsBlock.g(unwrap(world), unwrap(position));
    }

    @Override
    public boolean ticksRandomly() {
        return nmsBlock.w();
    }

    @Override
    public boolean hasTileEntity() {
        return nmsBlock.x();
    }

    @Override
    public boolean isOpaqueCube() {
        return nmsBlock.c();
    }

    @Override
    public boolean isCollidable() {
        return nmsBlock.y();
    }

    @Override
    public int tickRate(World world) {
        return nmsBlock.a(unwrap(world));
    }

    @Override
    public int quantityDropped(Random random) {
        return nmsBlock.a(random);
    }

    @Override
    public int damageDropped(Block block) {
        return nmsBlock.a(unwrap(block));
    }

    @Override
    public float getExplosionResistance(Entity entity) {
        return nmsBlock.a(unwrap(entity));
    }

    @Override
    public double getBlockBoundsMinX() {
        return nmsBlock.z();
    }

    @Override
    public double getBlockBoundsMaxX() {
        return nmsBlock.A();
    }

    @Override
    public double getBlockBoundsMinY() {
        return nmsBlock.B();
    }

    @Override
    public double getBlockBoundsMaxY() {
        return nmsBlock.C();
    }

    @Override
    public double getBlockBoundsMinZ() {
        return nmsBlock.D();
    }

    @Override
    public double getBlockBoundsMaxZ() {
        return nmsBlock.E();
    }

    @Override
    public boolean canProvidePower() {
        return nmsBlock.g();
    }

    @Override
    public String getLocalizedName() {
        return nmsBlock.H();
    }

    @Override
    public boolean getEnableStats() {
        return nmsBlock.I();
    }

    @Override
    public int getMobilityFlag() {
        return nmsBlock.i();
    }

    @Override
    public boolean requiresUpdates() {
        return nmsBlock.M();
    }

    @Override
    public boolean hasComparatorInputOverride() {
        return nmsBlock.N();
    }

    @Override
    public int getComparatorInputOverride(World world, Position position) {
        return nmsBlock.l(unwrap(world), unwrap(position));
    }

    public final String toString(){ // TODO: More information
        return "BlockBase["+net.minecraft.block.Block.c.c(nmsBlock).toString()+"]";
    }
}
