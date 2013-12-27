package net.canarymod.api.nbt;

import net.minecraft.nbt.NBTTagIntArray;

/**
 * IntArrayTag wrapper implementation
 *
 * @author Greg (gregthegeek)
 * @author Jason (darkdiplomat)
 */
public class CanaryIntArrayTag extends CanaryBaseTag implements IntArrayTag {

    /**
     * Constructs a new wrapper for NBTTagIntArray
     *
     * @param tag
     *         the NBTTagIntArray to wrap
     */
    public CanaryIntArrayTag(NBTTagIntArray tag) {
        super(tag);
    }

    /**
     * Constructs a new CanaryIntArrayTag and associated NBTTagIntArray
     *
     * @param value
     *         the int array to supply the tag
     */
    public CanaryIntArrayTag(int[] value) {
        super(new NBTTagIntArray(value));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int[] getValue() {
        return getHandle().b;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(int[] value) {
        getHandle().b = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IntArrayTag copy() {
        return new CanaryIntArrayTag((NBTTagIntArray) getHandle().b());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NBTTagIntArray getHandle() {
        return (NBTTagIntArray) tag;
    }

}
