package net.canarymod.api.nbt;

import net.minecraft.nbt.NBTTagDouble;

/**
 * DoubleTag wrapper implementation
 *
 * @author Greg (gregthegeek)
 * @author Jason (darkdiplomat)
 */
public class CanaryDoubleTag extends CanaryPrimativeTag implements DoubleTag {

    /**
     * Constructs a new wrapper for NBTTagDouble
     *
     * @param tag
     *         the NBTTagDouble to wrap
     */
    public CanaryDoubleTag(NBTTagDouble tag) {
        super(tag);
    }

    /**
     * Constructs a new CanaryDoubleTag and associated NBTTagDouble
     *
     * @param value
     *         the double to supply the tag
     */
    public CanaryDoubleTag(double value) {
        super(new NBTTagDouble(value));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getValue() {
        return getDoubleValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(double value) {
        getHandle().b = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DoubleTag copy() {
        return new CanaryDoubleTag((NBTTagDouble)getHandle().b());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NBTTagDouble getHandle() {
        return (NBTTagDouble)tag;
    }
}
