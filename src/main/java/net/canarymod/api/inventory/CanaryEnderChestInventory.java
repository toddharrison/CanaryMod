package net.canarymod.api.inventory;

import net.canarymod.api.entity.living.humanoid.Human;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import java.util.Arrays;

/**
 * EnderChest Inventory implementation
 *
 * @author Jason (darkdiplomat)
 */
public class CanaryEnderChestInventory extends CanaryBlockInventory implements EnderChestInventory {
    private Human human;

    public CanaryEnderChestInventory(InventoryEnderChest echest, Human human) {
        super(echest);
        this.human = human;
    }

    @Override
    public String getInventoryName() {
        return inventory.d_();
    }

    @Override
    public Human getInventoryOwner() {
        return human;
    }

    @Override
    public void clearContents() {
        Arrays.fill(getHandle().c, null);
    }

    @Override
    public Item[] clearInventory() {
        ItemStack[] items = Arrays.copyOf(getHandle().c, getSize());

        clearContents();
        return CanaryItem.stackArrayToItemArray(items);
    }

    @Override
    public Item[] getContents() {
        return CanaryItem.stackArrayToItemArray(getHandle().c);
    }

    @Override
    public void setContents(Item[] items) {
        System.arraycopy(CanaryItem.itemArrayToStackArray(items), 0, getHandle().c, 0, getSize());
    }

    @Override
    public void setInventoryName(String value) {
        getHandle().setName(value);
    }

    @Override
    public TileEntity getTileEntity() {
        return getHandle().getEnderChest();
    }

    @Override
    public void update() {
        getHandle().o_();
    }

    @Override
    public InventoryType getInventoryType() {
        return InventoryType.CHEST;
    }

    public InventoryEnderChest getHandle() {
        return (InventoryEnderChest) inventory;
    }
}
