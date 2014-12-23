package net.canarymod.util;

import com.mojang.authlib.GameProfile;
import net.canarymod.api.nbt.BaseTag;
import net.canarymod.api.nbt.CanaryBaseTag;
import net.canarymod.api.nbt.CanaryCompoundTag;
import net.canarymod.api.nbt.CompoundTag;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTUtil;

/**
 * JSON NBT Utility implementation
 *
 * @author Jason Jones (darkdiplomat)
 */
public class CanaryJsonNBTUtility implements JsonNBTUtility {

    @Override
    public BaseTag jsonToNBT(String rawJson) {
        try {
            return CanaryBaseTag.wrap(JsonToNBT.a(rawJson));
        }
        catch (NBTException nbtex) {
            throw new RuntimeException(nbtex);
        }
    }

    @Override
    public String baseTagToJSON(BaseTag baseTag) {
        return baseTag.toString(); // WAT
    }

    public GameProfile gameProfileFromNBT(CompoundTag tag) {
        return NBTUtil.a(((CanaryCompoundTag)tag).getHandle());
    }

    public CompoundTag gameProfileToNBT(GameProfile profile) {
        return (CompoundTag)CanaryBaseTag.wrap(NBTUtil.a(new CanaryCompoundTag().getHandle(), profile));
    }
}
