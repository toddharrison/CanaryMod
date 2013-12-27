package net.canarymod.api.nbt;

import net.minecraft.nbt.NBTTagLong;

/**
 * LongTag wrapper implementation
 *
 * @author Greg (gregthegeek)
 * @author Jason (darkdiplomat)
 */
public class CanaryLongTag extends CanaryPrimativeTag implements LongTag {

    /**
     * Constructs a new wrapper for NBTTagLong
     *
     * @param tag
     *         the NBTTagLong to wrap
     */
    public CanaryLongTag(NBTTagLong tag) {
        super(tag);
    }

    /**
     * Constructs a new CanaryLongTag and associated NBTTagLong
     *
     * @param value
     *         the long to supply the tag
     */
    public CanaryLongTag(long value) {
        super(new NBTTagLong(value));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getValue() {
        return getLongValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(long value) {
        getHandle().b = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LongTag copy() {
        return new CanaryLongTag((NBTTagLong) getHandle().b());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NBTTagLong getHandle() {
        return (NBTTagLong) tag;
    }
}
