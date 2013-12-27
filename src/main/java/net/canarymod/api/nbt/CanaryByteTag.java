package net.canarymod.api.nbt;

import net.minecraft.nbt.NBTTagByte;

/**
 * ByteTag wrapper implementation
 *
 * @author Greg (gregthegeek)
 * @author Jason (darkdiplomat)
 */
public class CanaryByteTag extends CanaryPrimativeTag implements ByteTag {

    /**
     * Constructs a new wrapper for NBTTagByte
     *
     * @param tag
     *         the NBTTagByte to wrap
     */
    public CanaryByteTag(NBTTagByte tag) {
        super(tag);
    }

    /**
     * Constructs a new CanaryByteTag and associated NBTTagByte
     *
     * @param value
     *         the byte to supply to the tag
     */
    public CanaryByteTag(byte value) {
        super(new NBTTagByte(value));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public byte getValue() {
        return getByteValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(byte value) {
        getHandle().b = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ByteTag copy() {
        return new CanaryByteTag((NBTTagByte) getHandle().b());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NBTTagByte getHandle() {
        return (NBTTagByte) tag;
    }
}
