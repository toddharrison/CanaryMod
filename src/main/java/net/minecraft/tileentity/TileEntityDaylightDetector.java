package net.minecraft.tileentity;

import net.canarymod.api.world.blocks.CanaryDaylightDetector;
import net.minecraft.block.BlockDaylightDetector;
import net.minecraft.server.gui.IUpdatePlayerListBox;

public class TileEntityDaylightDetector extends TileEntity implements IUpdatePlayerListBox {

    public TileEntityDaylightDetector() {
        this.complexBlock = new CanaryDaylightDetector(this); // CanaryMod: wrap tile entity
    }

    public void c() {
        if (this.b != null && !this.b.D && this.b.K() % 20L == 0L) {
            this.e = this.w();
            if (this.e instanceof BlockDaylightDetector) {
                ((BlockDaylightDetector) this.e).d(this.b, this.c);
            }
        }
    }

    // CanaryMod
    public CanaryDaylightDetector getCanaryDaylightDetector() {
        return (CanaryDaylightDetector) complexBlock;
    }
}
