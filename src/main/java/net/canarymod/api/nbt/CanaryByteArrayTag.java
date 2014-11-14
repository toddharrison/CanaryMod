package net.canarymod.api.nbt;

import net.minecraft.nbt.NBTTagByteArray;

/**
 * ByteArrayTag wrapper implementation
 *
 * @author Greg (gregthegeek)
 * @author Jason (darkdiplomat)
 */
public class CanaryByteArrayTag extends CanaryBaseTag<ByteArrayTag> implements ByteArrayTag {

    /**
     * Constructs a new wrapper for NBTTagByteArray
     *
     * @param tag
     *         the NBTTagByteArray to wrap
     */
    public CanaryByteArrayTag(NBTTagByteArray tag) {
        super(tag);
    }

    /**
     * Constructs a new CanaryByteArrayTag and associated NBTTagByteArray
     *
     * @param value
     *         the byte array to supply the tag
     */
    public CanaryByteArrayTag(byte[] value) {
        super(new NBTTagByteArray(value));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public byte[] getValue() {
        return getHandle().c();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(byte[] value) {
        if (value == null || value.length <= 0) {
            return;
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ByteArrayTag copy() {
        return new CanaryByteArrayTag((NBTTagByteArray) getHandle().b());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NBTTagByteArray getHandle() {
        return (NBTTagByteArray) tag;
    }
}
