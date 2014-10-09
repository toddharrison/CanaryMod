package net.canarymod.api.world.blocks;

import net.canarymod.api.inventory.CanaryItem;
import net.canarymod.api.inventory.InventoryType;
import net.canarymod.api.inventory.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

import java.util.Arrays;

/**
 * Furnace wrapper implementation
 *
 * @author Jason (darkdiplomat)
 */
public class CanaryFurnace extends CanaryLockableTileEntity implements Furnace {

    /**
     * Constructs a new wrapper for TileEntityFurnace
     *
     * @param tileentity
     *         the TileEntityFurnace to be wrapped
     */
    public CanaryFurnace(TileEntityFurnace tileentity) {
        super(tileentity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InventoryType getInventoryType() {
        return InventoryType.FURNACE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public short getBurnTime() {
        return (short)getTileEntity().a_(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBurnTime(short time) {
        getTileEntity().b(0, time);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public short getCookTime() {
        return (short)getTileEntity().a_(1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCookTime(short time) {
        getTileEntity().b(1, time);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearContents() {
        Arrays.fill(getTileEntity().h, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item[] clearInventory() {
        ItemStack[] items = Arrays.copyOf(getTileEntity().h, getSize());

        clearContents();
        return CanaryItem.stackArrayToItemArray(items);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item[] getContents() {
        return CanaryItem.stackArrayToItemArray(getTileEntity().h);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setContents(Item[] items) {
        System.arraycopy(CanaryItem.itemArrayToStackArray(items), 0, getTileEntity().h, 0, getSize());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setInventoryName(String value) {
        getTileEntity().a(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TileEntityFurnace getTileEntity() {
        return (TileEntityFurnace)tileentity;
    }
}
