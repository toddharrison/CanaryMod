package net.canarymod.api.nbt;

import net.minecraft.nbt.NBTTagString;

/**
 * StringTag wrapper implementation
 *
 * @author Greg (gregthegeek)
 * @author Jason (darkdiplomat)
 */
public class CanaryStringTag extends CanaryBaseTag<StringTag> implements StringTag {

    /**
     * Constructs a new wrapper for NBTTagString
     *
     * @param tag
     *         the NBTTagString to wrap
     */
    public CanaryStringTag(NBTTagString tag) {
        super(tag);
    }

    /**
     * Constructs a new CanaryStringTag and associated NBTTagString
     *
     * @param value
     *         the string to supply the tag
     */
    public CanaryStringTag(String value) {
        super(new NBTTagString(value));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getValue() {
        return getHandle().b;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(String value) {
        getHandle().b = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StringTag copy() {
        return new CanaryStringTag((NBTTagString) getHandle().b());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NBTTagString getHandle() {
        return (NBTTagString) tag;
    }
}
