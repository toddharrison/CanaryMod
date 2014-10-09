package net.canarymod.api.entity;

import net.canarymod.api.world.blocks.Block;
import net.canarymod.api.world.blocks.BlockType;
import net.canarymod.api.world.blocks.CanaryBlock;
import net.minecraft.entity.item.EntityFallingBlock;

/**
 * FallingBlock wrapper implementation
 *
 * @author Jason (darkdiplomat)
 */
public class CanaryFallingBlock extends CanaryEntity implements FallingBlock {

    /**
     * Constructs a new wrapper for EntityFallingBlock
     *
     * @param entity
     *         the EntityFallingBlock to be wrapped
     */
    public CanaryFallingBlock(EntityFallingBlock entity) {
        super(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityType getEntityType() {
        return EntityType.FALLINGBLOCK;
    }

    @Override
    public String getFqName() {
        return "FallingBlock";
    }

    @Override
    public Block getBlock() {
        return new CanaryBlock(getHandle().l(), this.getPosition(), this.getWorld());
    }

    @Override
    public void setBlock(Block block) {
        getHandle().setBlockState(((CanaryBlock)block).getNativeState());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public short getBlockID() {
        return (short)net.minecraft.block.Block.a(getHandle().l().c());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBlockID(short id) {
        if (BlockType.fromId(id) != null) { // Safety
            getHandle().setBlockState(net.minecraft.block.Block.c(id).P());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public short getBlockMetaData() {
        return (short) getHandle().a;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBlockMetaData(short data) {
        getHandle().a = data;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getFallTime() {
        return getHandle().a;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setFallTime(int fallTime) {
        getHandle().a = fallTime;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean dropsItem() {
        return getHandle().b;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDropsItem(boolean drops) {
        getHandle().b = drops;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isBreakingAnvil() {
        return getHandle().f;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setIsBreakingAnvil(boolean breaking) {
        if (getBlockID() == BlockType.Anvil.getId()) {
            getHandle().f = breaking;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hurtEntities() {
        return getHandle().f;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setHurtEntities(boolean hurt) {
        getHandle().f = hurt;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMaxDamage() {
        return getHandle().g;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMaxDamage(int max) {
        getHandle().g = max;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float getDamageAmount() {
        return getHandle().h;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDamageAmount(float damage) {
        getHandle().h = damage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityFallingBlock getHandle() {
        return (EntityFallingBlock) entity;
    }
}
