package net.canarymod.api.nbt;

import net.minecraft.nbt.NBTTagFloat;

/**
 * FloatTag wrapper implementation
 *
 * @author Greg (gregthegeek)
 * @author Jason (darkdiplomat)
 */
public class CanaryFloatTag extends CanaryPrimitiveTag<FloatTag> implements FloatTag {

    /**
     * Constructs a new wrapper for NBTTagFloat
     *
     * @param tag
     *         the NBTTagFloat to wrap
     */
    public CanaryFloatTag(NBTTagFloat tag) {
        super(tag);
    }

    /**
     * Constructs a new CanaryFloatTag and associated NBTTagFloat
     *
     * @param value
     *         the float to supply the tag
     */
    public CanaryFloatTag(float value) {
        super(new NBTTagFloat(value));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float getValue() {
        return getFloatValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(float value) {
        getHandle().b = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FloatTag copy() {
        return new CanaryFloatTag((NBTTagFloat) getHandle().b());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NBTTagFloat getHandle() {
        return (NBTTagFloat) tag;
    }

}
