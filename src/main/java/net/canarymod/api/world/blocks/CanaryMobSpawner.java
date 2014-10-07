package net.canarymod.api.world.blocks;

import net.canarymod.api.MobSpawnerLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;

public class CanaryMobSpawner extends CanaryTileEntity implements MobSpawner {

    private MobSpawnerLogic logic = ((TileEntityMobSpawner) this.tileentity).b().logic;

    public CanaryMobSpawner(TileEntityMobSpawner tileentity) {
        super(tileentity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MobSpawnerLogic getLogic() {
        return logic;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TileEntity getTileEntity() {
        return this.tileentity;
    }
}
