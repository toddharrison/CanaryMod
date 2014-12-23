package net.minecraft.tileentity;

import net.canarymod.api.world.blocks.CanaryDropper;

public class TileEntityDropper extends TileEntityDispenser {

    public TileEntityDropper() {
        this.complexBlock = new CanaryDropper(this); // CanaryMod: wrap tile entity
    }

    public String d_() {
        return this.k_() ? this.a : "container.dropper";
    }

    public String k() {
        return "minecraft:dropper";
    }

    // CanaryMod
    public CanaryDropper getCanaryDropper() {
        return (CanaryDropper)complexBlock;
    }
}
