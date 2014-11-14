package net.canarymod.api.nbt;

import net.minecraft.nbt.NBTTagShort;

/**
 * ShortTag wrapper implementation
 *
 * @author Greg (gregthegeek)
 * @author Jason (darkdiplomat)
 */
public class CanaryShortTag extends CanaryPrimitiveTag<ShortTag> implements ShortTag {

    /**
     * Constructs a new wrapper for NBTTagShort
     *
     * @param tag
     *         the NBTTagShort to wrap
     */
    public CanaryShortTag(NBTTagShort tag) {
        super(tag);
    }

    /**
     * Constructs a new CanaryShortTag and associated NBTTagShort
     *
     * @param value
     *         the short to supply the tag
     */
    public CanaryShortTag(short value) {
        super(new NBTTagShort(value));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public short getValue() {
        return getShortValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(short value) {
        getHandle().b = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShortTag copy() {
        return new CanaryShortTag((NBTTagShort) getHandle().b());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NBTTagShort getHandle() {
        return (NBTTagShort) tag;
    }
}
