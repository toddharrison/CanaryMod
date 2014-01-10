package net.canarymod.api.inventory.slot;

import net.canarymod.api.inventory.Inventory;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerBeacon;
import net.minecraft.inventory.ContainerBrewingStand;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.ContainerDispenser;
import net.minecraft.inventory.ContainerEnchantment;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.inventory.ContainerHorseInventory;
import net.minecraft.inventory.ContainerMerchant;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.ContainerRepair;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.inventory.SlotMerchantResult;

/**
 * SlotHelper for SlotClickHook
 *
 * @author Jason (darkdiplomat)
 */
public final class SlotHelper {

    /**
     * Matches a Container and slot index to a {@link SlotType}
     *
     * @param container
     *         the Container being used
     * @param slotIndex
     *         the slot id
     *
     * @return the {@link SlotType}
     */
    public static SlotType getSlotType(Container container, int slotIndex) {
        if (container == null) {
            return SlotType.NULL;
        }

        // Outside window
        if (slotIndex == -999) {
            return SlotType.OUTSIDE;
        }

        // In Window/non-slot
        if (slotIndex == -1) {
            return SlotType.NULL;
        }

        Slot slot = container.a(slotIndex);
        if (container instanceof ContainerPlayer) {
            if (slotIndex >= 5 && slotIndex <= 8) {
                return SlotType.ARMOR;
            }
            else if (slotIndex <= 4) {
                return SlotType.CRAFTING;
            }
        }
        else if (container instanceof ContainerBeacon) {
            return SlotType.BEACON;
        }
        else if (container instanceof ContainerBrewingStand) {
            return SlotType.BREWING;
        }
        else if (slot instanceof SlotCrafting || (container instanceof ContainerWorkbench && slotIndex < 9)) {
            return SlotType.CRAFTING;
        }
        else if (container instanceof ContainerEnchantment) {
            return SlotType.ENCHANTMENT;
        }
        else if (slot instanceof SlotFurnace) {
            return SlotType.FURNACE;
        }
        else if (slot instanceof SlotMerchantResult) {
            return SlotType.MERCHANT;
        }
        else if (container instanceof ContainerRepair) {
            return SlotType.REPAIR;
        }
        else if (container instanceof ContainerHorseInventory) {
            if (slotIndex == 0) {
                return SlotType.SADDLE;
            }
            else if (slotIndex == 1) {
                return SlotType.ARMOR;
            }
        }
        else if (slot.getClass() != Slot.class) {
            return SlotType.UNKNOWN;
        }

        return SlotType.DEFAULT;
    }

    /**
     * Attempts to define slots. Minecraft updates can break the definitions if slots are added/removed/modified.
     *
     * @return {@link SecondarySlotType}
     */
    public static SecondarySlotType getSpecificSlotType(Container container, int slotIndex) {
        if (container == null) {
            return SecondarySlotType.NULL;
        }

        // In Window, non-slot
        if (slotIndex == -1) {
            return SecondarySlotType.NULL;
        }

        // Outside window
        if (slotIndex == -999) {
            return SecondarySlotType.OUTSIDE;
        }

        Inventory inv = container.getInventory();
        if (inv == null) {
            return SecondarySlotType.NULL;
        }

        if (slotIndex < inv.getContents().length) {
            if (container instanceof ContainerBeacon) {
                return SecondarySlotType.PAYMENT;
            }
            else if (container instanceof ContainerBrewingStand) {
                if (slotIndex == 3) {
                    return SecondarySlotType.INGREDIENT;
                }
                else {
                    return SecondarySlotType.POTION;
                }
            }
            else if (container instanceof ContainerChest) {
                return SecondarySlotType.CONTAINER;
            }
            else if (container instanceof ContainerDispenser) {
                return SecondarySlotType.CONTAINER;
            }
            else if (container instanceof ContainerEnchantment) {
                return SecondarySlotType.ENCHANT;
            }
            else if (container instanceof ContainerFurnace) {
                switch (slotIndex) {
                    case 0:
                        return SecondarySlotType.CRAFT;
                    case 1:
                        return SecondarySlotType.FUEL;
                    default:
                        return SecondarySlotType.RESULT;
                }
            }
            else if (container instanceof ContainerMerchant) {
                switch (slotIndex) {
                    case 0:
                    case 1:
                        return SecondarySlotType.TRADE;
                    default:
                        return SecondarySlotType.RESULT;
                }
            }
            else if (container instanceof ContainerPlayer) {
                if (slotIndex == 0) {
                    return SecondarySlotType.RESULT;
                }
                if (slotIndex <= 4) {
                    return SecondarySlotType.CRAFT;
                }
            }
            else if (container instanceof ContainerRepair) {
                if (slotIndex < 2) {
                    return SecondarySlotType.CRAFT;
                }
                else {
                    return SecondarySlotType.RESULT;
                }
            }
            else if (container instanceof ContainerWorkbench) {
                if (slotIndex == 0) {
                    return SecondarySlotType.RESULT;
                }
                if (slotIndex <= 9) {
                    return SecondarySlotType.CRAFT;
                }
            }
            else if (container instanceof ContainerHorseInventory) {
                if (slotIndex == 0) {
                    return SecondarySlotType.SADDLE;
                }
                if (slotIndex == 1) {
                    return SecondarySlotType.HORSE_ARMOR;
                }
            }
        }

        int localSlot = slotIndex - inv.getSize();

        if (container instanceof ContainerPlayer) {
            if (localSlot < 4) {
                return SecondarySlotType.ARMOR;
            }

            localSlot -= 4; // remove armor index
        }

        if (localSlot >= 27 && localSlot < 36) {
            return SecondarySlotType.QUICKBAR;
        }

        return SecondarySlotType.INVENTORY;
    }
}
