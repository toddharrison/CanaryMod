package net.canarymod.api.nbt;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagByteArray;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.nbt.NBTTagString;

public abstract class CanaryBaseTag implements BaseTag {
    protected final NBTBase tag;

    protected CanaryBaseTag(NBTBase tag) {
        this.tag = tag;
    }

    @Override
    public String getName() {
        //return tag.e();
        return ""; // The new NBTBase doesnt have this anymore
    }

    @Override
    public void setName(String name) {
        //tag.p(name);
        //The new NBTBase doesnt have this anymore
    }

    @Override
    public byte getTypeId() {
        return tag.a();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CanaryBaseTag) {
            return ((CanaryBaseTag) obj).tag.equals(tag);
        }
        else if (obj instanceof NBTBase) {
            return obj.equals(tag);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return tag.hashCode();
    }

    protected abstract NBTBase getHandle();

    /**
     * Wraps the given NBTBase in its corresponding Canary wrapper.
     *
     * @param tag
     *         The NBTBase tag to wrap.
     *
     * @return The wrapped tag. Null if the tag is of an unsupported type.
     */
    protected static CanaryBaseTag wrap(NBTBase tag) {
        switch (tag.a()) { // switch the id of the tag
            default:
                return null;

            case 1:
                return new CanaryByteTag((NBTTagByte) tag);

            case 2:
                return new CanaryShortTag((NBTTagShort) tag);

            case 3:
                return new CanaryIntTag((NBTTagInt) tag);

            case 4:
                return new CanaryLongTag((NBTTagLong) tag);

            case 5:
                return new CanaryFloatTag((NBTTagFloat) tag);

            case 6:
                return new CanaryDoubleTag((NBTTagDouble) tag);

            case 7:
                return new CanaryByteArrayTag((NBTTagByteArray) tag);

            case 8:
                return new CanaryStringTag((NBTTagString) tag);

            case 9:
                return new CanaryListTag<CanaryBaseTag>((NBTTagList) tag);

            case 10:
                return new CanaryCompoundTag((NBTTagCompound) tag);

            case 11:
                return new CanaryIntArrayTag((NBTTagIntArray) tag);
        }
    }
}
