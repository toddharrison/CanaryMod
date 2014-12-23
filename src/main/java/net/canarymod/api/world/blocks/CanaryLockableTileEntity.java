package net.canarymod.api.world.blocks;

import net.canarymod.NotYetImplementedException;
import net.canarymod.api.inventory.CanaryBlockInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.world.LockCode;

/**
 * TileEntityLockable wrapper implementation
 *
 * @author Jason Jones (darkdiplomat)
 */
public abstract class CanaryLockableTileEntity extends CanaryBlockInventory implements LockableTileEntity {

    public CanaryLockableTileEntity(IInventory inventory) {
        super(inventory);
    }

    @Override
    public String getCode() {
        return getLockCode().b();
    }

    @Override
    public void setCode(String s) {
        throw new NotYetImplementedException("setCode is not yet implemented for LockableTileEntity");
    }

    @Override
    public boolean hasCodeSet() {
        return !getLockCode().a();
    }

    LockCode getLockCode() {
        return ((TileEntityLockable)getTileEntity()).i();
    }
}
