package net.canarymod.api.inventory;

import net.canarymod.api.OfflinePlayer;
import net.canarymod.api.nbt.CanaryBaseTag;
import net.canarymod.api.nbt.CanaryListTag;
import net.canarymod.api.nbt.ListTag;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagList;

/**
 * Copyright (C) 2015 Visual Illusions Entertainment
 * All Rights Reserved.
 *
 * @author Jason Jones (darkdiplomat)
 */
public class CanaryOfflinePlayerInventory extends CanaryPlayerInventory {

    public CanaryOfflinePlayerInventory(OfflinePlayer offlinePlayer) {
        super(formInventory(offlinePlayer));
    }

    private static InventoryPlayer formInventory(OfflinePlayer offlinePlayer) {
        InventoryPlayer ip = new InventoryPlayer(null);
        ip.b(((CanaryListTag)offlinePlayer.getNBT().getListTag("Inventory")).getHandle());
        return ip;
    }

    public ListTag storeInventory() {
        NBTTagList tag = new NBTTagList();
        getHandle().b(tag);
        return (ListTag)CanaryBaseTag.wrap(tag);
    }
}
