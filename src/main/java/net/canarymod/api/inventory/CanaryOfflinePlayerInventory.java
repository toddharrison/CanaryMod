package net.canarymod.api.inventory;

import net.canarymod.api.OfflinePlayer;
import net.canarymod.api.nbt.CanaryListTag;
import net.minecraft.entity.player.InventoryPlayer;

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
}
