package net.canarymod.api.world.blocks;

import net.canarymod.api.entity.living.humanoid.Player;
import net.canarymod.api.inventory.CanaryBlockInventory;
import net.canarymod.api.inventory.CanaryItem;
import net.canarymod.api.inventory.InventoryType;
import net.canarymod.api.inventory.Item;
import net.canarymod.api.world.World;
import net.minecraft.inventory.ContainerRepair;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.item.ItemStack;

import java.util.Arrays;

/**
 * The implementation of Anvil
 *
 * @author Somners
 */
public class CanaryAnvil extends CanaryBlockInventory implements Anvil {

    private ContainerRepair container = null;

    public CanaryAnvil(ContainerRepair container) {
        super(container.g);
        this.container = container;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InventoryType getInventoryType() {
        return InventoryType.ANVIL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getX() {
        return container.j.n();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getY() {
        return container.j.o();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getZ() {
        return container.j.p();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public World getWorld() {
        return container.i.getCanaryWorld();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearContents() {
        Arrays.fill(getInventory().c, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item[] clearInventory() {
        ItemStack[] items = Arrays.copyOf(getInventory().c, getSize());

        clearContents();
        return CanaryItem.stackArrayToItemArray(items);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item[] getContents() {
        return CanaryItem.stackArrayToItemArray(getInventory().c);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setContents(Item[] items) {
        System.arraycopy(CanaryItem.itemArrayToStackArray(items), 0, getInventory().c, 0, getSize());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setInventoryName(String value) {
        getInventory().setName(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getToolName() {
        return container.getToolName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setToolName(String name) {
        container.a(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item getResult() {
        ItemStack stack = getCraftResult().a(0xCAFEBABE);

        return stack == null ? null : stack.getCanaryItem();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setResult(Item item) {
        getCraftResult().a(0xCAFEBABE, item == null ? null : ((CanaryItem) item).getHandle());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getXPCost() {
        return this.container.a;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setXPCost(int level) {
        this.container.a = level;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player getPlayer() {
        return this.container.getPlayer();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public net.minecraft.tileentity.TileEntity getTileEntity() {
        throw new UnsupportedOperationException("Method 'getTileEntity' in class 'CanaryAnvil' is not supported. Not a tile Entity.");
    }

    public ContainerRepair getContainer() {
        return container;
    }

    private InventoryBasic getInventory() {
        return (InventoryBasic) container.h;
    }

    private InventoryCraftResult getCraftResult() {
        return (InventoryCraftResult) container.g;
    }

}
