package net.canarymod.api.factory;

import net.canarymod.api.nbt.*;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;

public final class CanaryNBTFactory implements NBTFactory {

    /**
     * {@inheritDoc}
     */
    @Override
    public CompoundTag newCompoundTag(String name) {
        return new CanaryCompoundTag();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ByteTag newByteTag(byte value) {
        return new CanaryByteTag(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ByteArrayTag newByteArrayTag(byte[] value) {
        return new CanaryByteArrayTag(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DoubleTag newDoubleTag(double value) {
        return new CanaryDoubleTag(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FloatTag newFloatTag(float value) {
        return new CanaryFloatTag(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IntTag newIntTag(int value) {
        return new CanaryIntTag(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IntArrayTag newIntArrayTag(int[] value) {
        return new CanaryIntArrayTag(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E extends BaseTag> ListTag<E> newListTag() {
        return new CanaryListTag<E>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LongTag newLongTag(long value) {
        return new CanaryLongTag(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShortTag newShortTag(short value) {
        return new CanaryShortTag(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StringTag newStringTag(String value) {
        return new CanaryStringTag(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseTag newTagFromType(NBTTagType type, Object value) {
        try {
            switch (type) {
                case BYTE:
                    return newByteTag((Byte) value);
                case BYTE_ARRAY:
                    return newByteArrayTag((byte[]) value);
                case COMPOUND:
                    return newCompoundTag((String) value);
                case DOUBLE:
                    return newDoubleTag((Double) value);
                case FLOAT:
                    return newFloatTag((Float) value);
                case INT:
                    return newIntTag((Integer) value);
                case INT_ARRAY:
                    return newIntArrayTag((int[]) value);
                case LIST:
                    return newListTag();
                case LONG:
                    return newLongTag((Long) value);
                case SHORT:
                    return newShortTag((Short) value);
                case STRING:
                    return newStringTag(value == null ? "null" : (String) value);
                default:
                    return null;
            }
        }
        catch (Exception ex) {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BaseTag newTagFromJSON(String json) {
        try {
            return CanaryBaseTag.wrap(JsonToNBT.a(json));
        }
        catch (NBTException nbtex) {
        }
        return null;
    }
}
