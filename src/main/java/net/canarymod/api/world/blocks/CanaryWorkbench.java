package net.canarymod.api.world.blocks;

import net.canarymod.api.inventory.CanaryBlockInventory;
import net.canarymod.api.inventory.CanaryItem;
import net.canarymod.api.inventory.InventoryType;
import net.canarymod.api.inventory.Item;
import net.canarymod.api.world.World;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;

import java.util.Arrays;

/**
 * Workbench wrapper implementation
 *
 * @author Jason (darkdiplomat)
 */
public class CanaryWorkbench extends CanaryBlockInventory implements Workbench {
    private ContainerWorkbench container;

    /**
     * Constructs a new wrapper for ContainerWorkbench
     *
     * @param container
     *         the ContainerWorkbench to be wrapped
     */
    public CanaryWorkbench(ContainerWorkbench container) {
        super(container.a);
        this.container = container;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InventoryType getInventoryType() {
        return InventoryType.WORKBENCH;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getX() {
        return container.h.n();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getY() {
        return container.h.o();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getZ() {
        return container.h.p();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public World getWorld() {
        return container.g.getCanaryWorld();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearContents() {
        Arrays.fill(getInventory().a, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item[] clearInventory() {
        ItemStack[] items = Arrays.copyOf(getInventory().a, getSize());

        clearContents();
        return CanaryItem.stackArrayToItemArray(items);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item[] getContents() {
        return CanaryItem.stackArrayToItemArray(getInventory().a);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setContents(Item[] items) {
        System.arraycopy(CanaryItem.itemArrayToStackArray(items), 0, getInventory().a, 0, getSize());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setInventoryName(String value) {
        getInventory().setName(value);
    }

    /**
     * @throws UnsupportedOperationException
     *         This Block is not of TileEntity
     */
    @Override
    public net.minecraft.tileentity.TileEntity getTileEntity() {
        throw new UnsupportedOperationException("Not a TileEntity");
    }

    /**
     * {@inheritDoc}
     */
    public ContainerWorkbench getContainer() {
        return container;
    }

    public InventoryCrafting getInventory() {
        return container.a;
    }

}
