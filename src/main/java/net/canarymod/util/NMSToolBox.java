package net.canarymod.util;

import com.mojang.authlib.GameProfile;
import net.canarymod.Canary;
import net.canarymod.ToolBox;
import net.canarymod.api.CanaryServer;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.UUID;

/**
 * @author Jason (darkdiplomat)
 */
public class NMSToolBox extends ToolBox {

    /**
     * Converts a byte array of Block Ids into an array of native Blocks
     *
     * @param ids
     *         the byte array of ids
     *
     * @return native Block array
     */
    public static net.minecraft.block.Block[] blockIdsToBlocks(int[] ids) {
        net.minecraft.block.Block[] blocks = new net.minecraft.block.Block[ids.length];
        for (int index = 0; index < ids.length; index++) {
            blocks[index] = net.minecraft.block.Block.e(ids[index]);
        }
        return blocks;
    }

    /**
     * Due to the nature of this, it is stored outside of Lib
     * Calling this should be extremely limited as Mojang sets a API call limit
     *
     * @param uuid
     *
     * @return user name associated with the UUID
     */
    public static String usernameFromUUID(UUID uuid) {
        String uuidStr = uuid.toString();
        String name = null;
        GameProfile profile = ((CanaryServer) Canary.getServer()).gameprofileFromCache(uuid);
        if (profile != null) {
            return profile.getName();
        }

        if (userLookup.containsKey(uuidStr)) {
            if (userLookup.getComments(uuid.toString()).length > 0) {
                if (!userLookupExpired(userLookup.getComments(uuidStr)[0].replace(";Verified: ", "").trim())) {
                    return userLookup.getString(uuid.toString());
                }
            }
        }

        try {
            URL url = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuidStr.replaceAll("\\-", ""));
            HttpURLConnection uc = (HttpURLConnection) url.openConnection();

            // Parse it
            String json = new Scanner(uc.getInputStream(), "UTF-8").useDelimiter("\\A").next();
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(json);
            name = (String) ((JSONObject) obj).get("name");
        }
        catch (Exception ex) {
            Canary.log.warn("Failed to translate UUID into a Username. Reason: " + ex.getMessage());
        }

        if (name != null) {
            userLookup.setString(uuidStr, name);
            userLookup.setComments(uuidStr, ";Verified: " + System.currentTimeMillis());
            userLookup.save();
        }
        else if (userLookup.containsKey(uuidStr)) {
            return userLookup.getString(uuidStr); // Return last known even if expired
        }

        return name;
    }
}
