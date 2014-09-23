package net.canarymod.util;

import net.canarymod.api.nbt.BaseTag;
import net.canarymod.api.nbt.CanaryBaseTag;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;

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
}
