package net.canarymod.api.world.blocks;

import net.minecraft.tileentity.TileEntityBanner;

/**
 * Banner wrapper implementation
 *
 * @author Jason Jones (darkdiplomat)
 */
public class CanaryBanner extends CanaryTileEntity implements Banner {

    public CanaryBanner(TileEntityBanner banner) {
        super(banner);
    }

    @Override
    public TileEntityBanner getTileEntity() {
        return (TileEntityBanner)tileentity;
    }
}
