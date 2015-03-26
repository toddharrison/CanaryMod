package net.canarymod.api.inventory;

import net.canarymod.api.OfflinePlayer;
import net.canarymod.api.entity.living.humanoid.Human;
import net.canarymod.api.nbt.CanaryListTag;
import net.minecraft.inventory.InventoryEnderChest;

/**
 * Offline EnderChest wrapper implementation
 *
 * @author Jason Jones (darkdiplomat)
 */
public class CanaryOfflineEnderChestInventory extends CanaryEnderChestInventory {

    public CanaryOfflineEnderChestInventory(OfflinePlayer offlinePlayer) {
        super(formInventory(offlinePlayer), null);
    }

    @Override
    public Human getInventoryOwner() {
        throw new UnsupportedOperationException("Offline Ender Chest references does not have a \"HUMAN\" owner. Please correct your plugin.");
    }

    private static InventoryEnderChest formInventory(OfflinePlayer offlinePlayer) {
        InventoryEnderChest iec = new InventoryEnderChest();
        iec.a(((CanaryListTag)offlinePlayer.getNBT().getListTag("EnderItems")).getHandle());
        return iec;
    }
}
