package net.minecraft.tileentity;

import net.canarymod.api.world.blocks.CanaryDaylightDetector;
import net.minecraft.block.BlockDaylightDetector;

public class TileEntityDaylightDetector extends TileEntity {

    public TileEntityDaylightDetector() {
        this.complexBlock = new CanaryDaylightDetector(this); // CanaryMod: wrap tile entity
    }

    public void h() {
        if (this.b != null && !this.b.E && this.b.H() % 20L == 0L) {
            this.h = this.q();
            if (this.h instanceof BlockDaylightDetector) {
                ((BlockDaylightDetector) this.h).e(this.b, this.c, this.d, this.e);
            }
        }

        // CanaryMod

    public CanaryDaylightDetector getCanaryDaylightDetector() {
        return (CanaryDaylightDetector) complexBlock;
    }
}
