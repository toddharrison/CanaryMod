package net.canarymod.api.world.blocks;

/**
 * MapColor wrapper implementation
 *
 * @author Jason Jones (darkdiplomat)
 */
public class CanaryMapColor implements MapColor {
    private net.minecraft.block.material.MapColor nmsMapColor;

    public CanaryMapColor(net.minecraft.block.material.MapColor nmsMapColor) {
        this.nmsMapColor = nmsMapColor;
    }

    @Override
    public int getIndex() {
        return nmsMapColor.L;
    }

    @Override
    public int getValue() {
        return nmsMapColor.M;
    }
}
