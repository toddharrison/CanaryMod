package net.canarymod.api.world;

import net.minecraft.util.BlockPos;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.Random;

/**
 * @author Somners
 */
public class CanaryBiome implements Biome {

    private BiomeGenBase handle;

    public CanaryBiome(BiomeGenBase biome) {
        this.handle = biome;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canSpawnLightning() {
        return handle.e();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isTropic() {
        return handle.f();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float getSpawnChance() {
        throw new UnsupportedOperationException("Method 'getSpawnChance' in class 'CanaryBiome' is not supported yet.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getRainfall() {
        return handle.h();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float getTemperature() {
        return handle.ap;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void decorate(World world, Random rnd, int x, int z) {
        handle.a(((CanaryWorld) world).getHandle(), rnd, new BlockPos(x, 0, z));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BiomeType getBiomeType() {
        return BiomeType.fromId((byte) handle.az);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTemperatureAndPrecipitation(float temp, float precipitation) {
        handle.setTemperatureAndPrecipitation(temp, precipitation);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCanSnow(boolean canSnow) {
        handle.setCanSnow(canSnow);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCanRain(boolean canRain) {
        handle.setCanRain(canRain);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canSnow() {
        return handle.j();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canRain() {
        return handle.canRain();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setColor(String hexColor) {
        handle.a(Integer.parseInt(hexColor.replaceFirst("#", ""), 16));
    }

    /**
     * {@inheritDoc}
     */
    public BiomeGenBase getHandle() {
        return this.handle;
    }
}