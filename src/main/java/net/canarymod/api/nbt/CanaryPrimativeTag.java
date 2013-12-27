package net.canarymod.api.nbt;

import net.minecraft.nbt.NBTBase;

/**
 * Cause, you know, I am lazy and this makes it so I only have to update these methods 1 time
 *
 * @author Jason (darkdiplomat)
 */
public abstract class CanaryPrimativeTag extends CanaryBaseTag implements PrimativeTag {

    protected CanaryPrimativeTag(NBTBase.NBTPrimitive tag) {
        super(tag);
    }

    @Override
    public long getLongValue() {
        return getHandle().c();
    }

    @Override
    public int getIntValue() {
        return getHandle().d();
    }

    @Override
    public short getShortValue() {
        return getHandle().e();
    }

    @Override
    public byte getByteValue() {
        return getHandle().f();
    }

    @Override
    public double getDoubleValue() {
        return getHandle().g();
    }

    @Override
    public float getFloatValue() {
        return getHandle().h();
    }

    @Override
    protected NBTBase.NBTPrimitive getHandle() {
        return (NBTBase.NBTPrimitive) tag;
    }
}
