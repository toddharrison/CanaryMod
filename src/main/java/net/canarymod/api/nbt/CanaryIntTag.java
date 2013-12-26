package net.canarymod.api.nbt;

import net.minecraft.nbt.NBTTagInt;

/**
 * IntTag wrapper implementation
 *
 * @author Greg (gregthegeek)
 * @author Jason (darkdiplomat)
 */
public class CanaryIntTag extends CanaryPrimativeTag implements IntTag {

    /**
     * Constructs a new wrapper for NBTTagInt
     *
     * @param tag
     *         the NBTTagInt to wrap
     */
    public CanaryIntTag(NBTTagInt tag) {
        super(tag);
    }

    /**
     * Constructs a new CanaryIntTag and associated NBTTagInt
     *
     * @param value
     *         the int to supply the tag
     */
    public CanaryIntTag(int value) {
        super(new NBTTagInt(value));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getValue() {
        return getIntValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(int value) {
        getHandle().b = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IntTag copy() {
        return new CanaryIntTag((NBTTagInt) getHandle().b());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NBTTagInt getHandle() {
        return (NBTTagInt) tag;
    }
}
