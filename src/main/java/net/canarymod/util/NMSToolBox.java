package net.canarymod.util;

import com.mojang.authlib.GameProfile;
import net.canarymod.Canary;
import net.canarymod.api.CanaryServer;
import net.visualillusionsent.utils.PropertiesFile;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.UUID;

/**
 * @author Jason (darkdiplomat)
 */
public class NMSToolBox {
    private static final PropertiesFile userLookup = new PropertiesFile("uuidreverselookup.cfg");

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
     * @return
     */
    public static String usernameFromUUID(UUID uuid) {
        String name = null;
        GameProfile profile = ((CanaryServer) Canary.getServer()).gameprofileFromCache(uuid);
        if (profile != null) {
            return profile.getName();
        }
        else if (userLookup.containsKey(uuid.toString())) {
            return userLookup.getString(uuid.toString());
        }

        try {
            URL url = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid.toString().replaceAll("\\-", ""));
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
            userLookup.setString(uuid.toString(), name);
            userLookup.save();
        }

        return name;
    }
}
