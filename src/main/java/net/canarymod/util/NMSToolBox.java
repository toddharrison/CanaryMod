package net.canarymod.util;

import com.google.common.collect.Iterables;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.canarymod.Canary;
import net.canarymod.ToolBox;
import net.canarymod.api.CanaryServer;
import net.canarymod.api.entity.vehicle.CanaryChestMinecart;
import net.canarymod.api.inventory.CanaryEnderChestInventory;
import net.canarymod.api.inventory.Inventory;
import net.canarymod.api.inventory.NativeCustomStorageInventory;
import net.canarymod.api.world.blocks.CanaryDoubleChest;
import net.canarymod.api.world.blocks.CanaryWorkbench;
import net.canarymod.hook.player.InventoryHook;
import net.minecraft.block.BlockWorkbench;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.IInteractionObject;
import net.visualillusionsent.utils.PropertiesFile;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

/**
 * @author Jason (darkdiplomat)
 */
public class NMSToolBox extends ToolBox {
    private static final PropertiesFile skincache = new PropertiesFile("db/skin.cache");

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
            blocks[index] = net.minecraft.block.Block.c(ids[index]);
        }
        return blocks;
    }

    /**
     * Converts a byte array of Block Ids into an array of native IBlockState's
     *
     * @param ids
     *         the byte array of ids
     *
     * @return native IBlockState array
     */
    public static net.minecraft.block.state.IBlockState[] blockIdsToBlockStates(int[] ids) {
        net.minecraft.block.state.IBlockState[] blocks = new net.minecraft.block.state.IBlockState[ids.length];
        for (int index = 0; index < ids.length; index++) {
            blocks[index] = net.minecraft.block.Block.d(ids[index]);
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
        GameProfile profile = ((CanaryServer)Canary.getServer()).gameprofileFromCache(uuid);
        if (profile != null) {
            return profile.getName();
        }

        if (userLookup.containsKey(uuidStr)) {
            if (userLookup.getComments(uuid.toString()).length > 0) {
                String timeStamp = userLookup.getComments(uuidStr)[0].replaceAll(";Verified", "").trim();
                if (!userLookupExpired(timeStamp)) {
                    return userLookup.getString(uuid.toString());
                }
            }
        }

        try {
            URL url = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuidStr.replaceAll("\\-", ""));
            HttpURLConnection uc = (HttpURLConnection)url.openConnection();

            // Parse it
            String json = new Scanner(uc.getInputStream(), "UTF-8").useDelimiter("\\A").next();
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(json);
            name = (String)((JSONObject)obj).get("name");
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

    public static Property getSkinProperty(String name) {
        if (skincache.containsKey(name)) {
            String timeStamp = skincache.getComments(name)[0].replaceAll(";Verified", "").trim();
            Canary.log.debug(timeStamp);
            if (!userLookupExpired(timeStamp)) {
                String[] storedProperty = restoreEscapedEqual(skincache.getString(name)).split(":");
                Canary.log.debug(storedProperty.length);
                return new Property("textures", storedProperty[0], storedProperty[1]);
            }
        }
        UUID uuidOther = uuidFromUsername(name);
        GameProfile profileOther = MinecraftServer.M().aB().fillProfileProperties(new GameProfile(uuidOther, name), true);
        Property property = Iterables.getFirst(profileOther.getProperties().get("textures"), null);

        if (property != null) {
            skincache.setStringArray(name, ":", new String[]{ escapeEqual(property.getValue()), escapeEqual(property.getSignature()) });
            skincache.setComments(name, ";Verified " + System.currentTimeMillis());
            skincache.save();
        }
        else if (skincache.containsKey(name)) {
            // go ahead and return the old data
            String[] storedProperty = skincache.getStringArray(name, ":");
            return new Property("textures", storedProperty[0], storedProperty[1]);
        }

        return property;
    }

    private static String escapeEqual(String property) {
        return property.replaceAll("=", "[]");
    }

    private static String restoreEscapedEqual(String property) {
        return property.replaceAll("\\[]", "=");
    }

    public static GameProfile spoofNameAndTexture(GameProfile original, String newName){
        GameProfile newProfile = new GameProfile(original.getId(), newName);
        for(Map.Entry<String, Property> entry : original.getProperties().entries()){
            if(entry.getKey() == null){
                Canary.log.debug("GameProfile Property had null key it seems... weird...");
                continue;
            }
            if(entry.getKey().equals("textures")){
                Property property =  getSkinProperty(newName);
                if(property != null) {
                    newProfile.getProperties().put("textures", property);
                }
                else {
                    // No Skin Data found
                    newProfile.getProperties().put(entry.getKey(), entry.getValue());
                }
            }
            else{
                newProfile.getProperties().put(entry.getKey(), entry.getValue());
            }
        }
        return newProfile;
    }

    public static Container doInventoryHook(IInventory iinventory, EntityPlayerMP entityplayermp){
        Container container = null;
        Inventory inventory;
        if(iinventory instanceof IInteractionObject){
            container = ((IInteractionObject)iinventory).a(entityplayermp.bg, entityplayermp);

            if (iinventory instanceof TileEntityChest) {
                inventory = ((TileEntityChest) iinventory).getCanaryChest();
                ((ContainerChest) container).setInventory(inventory);
            }
            else if (iinventory instanceof EntityMinecartChest) {
                inventory = (CanaryChestMinecart) ((EntityMinecartChest) iinventory).getCanaryEntity();
                ((ContainerChest) container).setInventory(inventory);
            }
        }
        else {
            container = new ContainerChest(entityplayermp.bg, iinventory, entityplayermp);
            if (iinventory instanceof InventoryLargeChest) {
                inventory = new CanaryDoubleChest((InventoryLargeChest) iinventory);
                ((ContainerChest) container).setInventory(inventory);
            }else if (iinventory instanceof InventoryEnderChest) {
                inventory = new CanaryEnderChestInventory((InventoryEnderChest) iinventory, entityplayermp.getPlayer());
                ((ContainerChest) container).setInventory(inventory);
            }
            else if (iinventory instanceof NativeCustomStorageInventory) {
                inventory = ((NativeCustomStorageInventory) iinventory).getCanaryCustomInventory();
                ((ContainerChest) container).setInventory(inventory);
            }

        }

        if(container != null) {
            if (!new InventoryHook(entityplayermp.getPlayer(), container.getInventory(), false).call().isCanceled()) {
                return container;
            }
        }
        return null;
    }

    public static Container doInventoryHook(IInteractionObject iinteractionobject, EntityPlayerMP entityplayermp){
        Container container = iinteractionobject.a(entityplayermp.bg, entityplayermp);

        if(container != null) {
            if (!new InventoryHook(entityplayermp.getPlayer(), container.getInventory(), false).call().isCanceled()) {
                return container;
            }
        }
        return null;
    }
    
    public static Container doInventoryHook(IMerchant merchant, EntityPlayerMP entityplayermp){
        ContainerMerchant container = null;
        container = new ContainerMerchant(entityplayermp.bg, merchant, entityplayermp.o);
        if(container != null) {
            if (!new InventoryHook(entityplayermp.getPlayer(), container.getInventory(), false).call().isCanceled()) {
                return container;
            }
        }
        return null;
    }
}
