package net.canarymod.api.inventory;

import net.canarymod.Canary;
import net.canarymod.api.nbt.CanaryCompoundTag;
import net.canarymod.api.world.blocks.CanaryTileEntity;
import net.canarymod.config.Configuration;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

/**
 * ContainerBlock buffer between TileEntity and those with Inventories
 *
 * @author Jason (darkdiplomat)
 */
public abstract class CanaryBlockInventory extends CanaryTileEntity implements Inventory {
    private boolean openRemote;

    public CanaryBlockInventory(IInventory inventory) {
        super(inventory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getInventoryName() {
        return inventory.d_();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addItem(ItemType type) {
        this.addItem(new CanaryItem(type, 1, -1));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addItem(int itemId) {
        this.addItem(new CanaryItem(itemId, 1));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addItem(int itemId, short damage) {
        this.addItem(new CanaryItem(itemId, 1));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addItem(int itemId, int amount) {
        this.addItem(new CanaryItem(itemId, amount));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addItem(ItemType type, int amount) {
        this.addItem(new CanaryItem(type, amount, -1));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addItem(int itemId, int amount, short damage) {
        this.addItem(new CanaryItem(itemId, amount, damage));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addItem(String machineName) {
        this.addItem(new CanaryItem(ItemType.fromString(machineName), 1, -1));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addItem(String machineName, int amount) {
        this.addItem(new CanaryItem(ItemType.fromString(machineName), amount, -1));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addItem(Item item) {
        this.insertItem(item);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void decreaseItemStackSize(int itemId, int amount) {
        Item[] items = getContents();
        int remaining = amount;

        for (Item item : items) {
            if (item.getId() == itemId) {
                if (item.getAmount() == remaining) {
                    setSlot(item.getSlot(), null);
                    return;
                }
                else if (item.getAmount() > remaining) {
                    item.setAmount(item.getAmount() - remaining);
                    setSlot(item.getSlot(), item);
                    return;
                }
                else {
                    setSlot(item.getSlot(), null);
                    remaining -= item.getAmount();
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void decreaseItemStackSize(int itemId, int amount, short damage) {
        this.decreaseItemStackSize(new CanaryItem(itemId, amount, damage));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void decreaseItemStackSize(Item item) {
        Item[] items = getContents();
        int remaining = item.getAmount();

        for (Item it : items) {
            if (item.equalsIgnoreSize(it)) {
                if (it.getAmount() == remaining) {
                    setSlot(it.getSlot(), null);
                    return;
                }
                else if (it.getAmount() > remaining) {
                    it.setAmount(it.getAmount() - remaining);
                    setSlot(it.getSlot(), it);
                    return;
                }
                else {
                    setSlot(it.getSlot(), null);
                    remaining -= it.getAmount();
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getEmptySlot() {
        for (int index = 0; index < getSize(); index++) {
            if (getSlot(index) == null) {
                return index;
            }
        }
        return -1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getInventoryStackLimit() {
        return inventory.p_();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item getItem(ItemType type) {
        return getItem(type, -1, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item getItem(int id) {
        return getItem(ItemType.fromId(id), -1, false);
    }

    /**
     * {@inheritDoc}
     */
    public Item getItem(String machineName) {
        return getItem(ItemType.fromString(machineName), -1, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item getItem(ItemType type, int amount) {
        return getItem(type, amount, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item getItem(int id, int amount) {
        return this.getItem(ItemType.fromId(id), amount, false);
    }

    @Override
    public Item getItem(String machineName, int amount) {
        return getItem(ItemType.fromString(machineName), amount, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item getItem(int id, int amount, short damage) {
        return getItem(ItemType.fromIdAndData(id, damage), amount, true);
    }

    /**
     * Internal method to handle getItem doing damage checks and amount checks
     */
    private Item getItem(ItemType type, int amount, boolean doDamage) {
        for (int index = 0; index < getSize(); index++) {
            Item toCheck = getSlot(index);

            if (toCheck != null &&
                    (doDamage ? toCheck.getType().equals(type) : toCheck.getType().getId() == type.getId()) &&
                    (amount == -1 ? true : toCheck.getAmount() == amount)) {
                toCheck.setSlot(index);
                return toCheck;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSize() {
        return inventory.n_();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item getSlot(int index) {
        ItemStack stack = inventory.a(index);
        if (stack != null) {
            Item slot_item = stack.getCanaryItem();
            slot_item.setSlot(index);
            return slot_item;
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasItem(int itemId) {
        return hasItem(ItemType.fromId(itemId), false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasItem(ItemType type) {
        return this.hasItem(type, true);
    }

    @Override
    public boolean hasItem(String machineName) {
        return this.hasItem(ItemType.fromString(machineName), true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasItem(int itemId, short damage) {
        return hasItem(ItemType.fromIdAndData(itemId, damage), true);
    }

    private boolean hasItem(ItemType type, boolean doDamage) {
        for (int index = 0; index < getSize(); index++) {
            Item test = getSlot(index);
            if (test != null && doDamage ? test.getType().equals(type) : test.getType().getId() == type.getId()) {
                return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasItemStack(ItemType type, int amount) {
        return this.hasItemStack(type, amount, Integer.MAX_VALUE, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasItemStack(int itemId, int amount) {
        return this.hasItemStack(ItemType.fromId(itemId), amount, Integer.MAX_VALUE, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasItemStack(String machineName, int amount) {
        return this.hasItemStack(ItemType.fromString(machineName), amount, Integer.MAX_VALUE, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasItemStack(int itemId, int amount, int damage) {
        return hasItemStack(ItemType.fromIdAndData(itemId, damage), amount, Integer.MAX_VALUE, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasItemStack(int itemId, int minAmount, int maxAmount, int damage) {
        return this.hasItemStack(ItemType.fromIdAndData(itemId, damage), minAmount, maxAmount, true);
    }

    @Override
    public boolean hasItemStack(String machineName, int minAmount, int maxAmount) {
        return this.hasItemStack(ItemType.fromString(machineName), minAmount, maxAmount, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasItemStack(ItemType type, int minAmount, int maxAmount) {
        return this.hasItemStack(type, minAmount, maxAmount, true);
    }

    private boolean hasItemStack(ItemType type, int minAmount, int maxAmount, boolean doDamage) {
        for (int index = 0; index < getSize(); index++) {
            Item toCheck = getSlot(index);

            if (toCheck != null && doDamage ? toCheck.getType().equals(type) : toCheck.getType().getId() == type.getId()) {
                int am = toCheck.getAmount();

                if (am > minAmount && am < maxAmount) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean insertItem(Item item) {
        int amount = item.getAmount();
        Item itemExisting = null;
        int maxAmount = item.getMaxAmount();

        while (amount > 0) {

            // Get an existing item with at least 1 spot free
            for (Item i : getContents()) {
                if (i != null && i.getType().equals(item.getType()) && i.getAmount() < i.getMaxAmount()) {
                    if((i.hasDataTag() && !item.hasDataTag()) || (!i.hasDataTag() && item.hasDataTag())){
                        continue;
                    }
                    else if (!i.getDataTag().equals(item.getDataTag())){
                        continue;
                    }
                    itemExisting = i;
                }
            }

            // Add the items to the existing stack of items
            if (itemExisting != null && (!item.isEnchanted() || Configuration.getServerConfig().allowEnchantmentStacking())) {
                // Add as much items as possible to the stack
                int k = Math.min(itemExisting.getMaxAmount() - itemExisting.getAmount(), item.getAmount());
                itemExisting.setAmount(itemExisting.getAmount() + k);
                amount -= k;
                // make sure to set itemExisting to null, else we will loop infinitely
                itemExisting = null;
                continue;
            }
            // We still have slots, but no stack, create a new stack.
            int eslot = getEmptySlot();

            if (eslot != -1) {
                // Set the amount on item so the NBT copies the correct amounts.
                item.setAmount(amount);
                // Create new Item
                CanaryItem tempItem = new CanaryItem(item.getId(), amount, item.getDamage(), -1);
                // Create NBT Tags and new Item, then copy NBT from existing item to new item
                CanaryCompoundTag nbt = item.hasDataTag() ? (CanaryCompoundTag) ((CanaryItem) item).getDataTag().copy() : null;
                // Set NBT on new Item
                tempItem.setDataTag(nbt);
                this.setSlot(eslot, tempItem);
                amount = 0;
                continue;
            }

            // No free slots, no incomplete stacks: full
            // make sure the stored items are removed
            item.setAmount(amount);
            return false;
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSlot(int itemId, int amount, int slot) {
        this.setSlot(new CanaryItem(itemId, 0, 0, slot));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSlot(int itemId, int amount, int damage, int slot) {
        this.setSlot(new CanaryItem(itemId, amount, damage, slot));
    }

    @Override
    public void setSlot(String machineName, int amount, int slot) {
        this.setSlot(new CanaryItem(ItemType.fromString(machineName), amount, slot));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSlot(ItemType type, int amount, int slot) {
        this.setSlot(new CanaryItem(type, amount, slot));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSlot(Item item) {
        this.setSlot(item.getSlot(), item);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSlot(int index, Item value) {
        this.getInventoryHandle().a(index, value == null ? null : ((CanaryItem) value).getHandle());
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public Item removeItem(Item item) {
        for (int index = 0; index < getSize(); index++) {
            Item toCheck = getSlot(index);

            if (toCheck != null && item.equalsIgnoreSize(toCheck)) {
                setSlot(index, null);
                return toCheck;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item removeItem(int id) {
        return removeItem(ItemType.fromId(id), false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item removeItem(String machineName) {
        return removeItem(ItemType.fromString(machineName), true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item removeItem(int id, int damage) {
        return removeItem(ItemType.fromIdAndData(id, damage), true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item removeItem(ItemType type) {
        return this.removeItem(type, true);
    }

    private Item removeItem(ItemType type, boolean doDamage) {
        for (int index = 0; index < getSize(); index++) {
            Item toCheck = getSlot(index);

            if (toCheck != null && doDamage ? toCheck.getType().equals(type) : toCheck.getId() == type.getId()) {
                setSlot(index, null);
                return toCheck;
            }
        }
        return null;
    }

    public boolean canOpenRemote() {
        return openRemote;
    }

    public void setCanOpenRemote(boolean remote) {
        openRemote = remote;
    }

    /**
     * Gets the inventory handle of this ContainerBlock
     *
     * @return native Inventory interface
     */
    public IInventory getInventoryHandle() {
        return inventory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canInsertItems(Item item) {
        int totalSpace = 0;
        for (Item inv : getContents()) {
            if (inv == null) {
                totalSpace += item.getMaxAmount();
            }
            else {
                if (inv.getType().equals(item.getType())) {
                    if (item.hasDataTag() && item.getDataTag().equals(inv.getDataTag())) {
                        totalSpace += inv.getMaxAmount() - inv.getAmount();
                    }
                }
            }
        }


        if (totalSpace > 0) {
            return true;
        }

        return false;
    }
}
