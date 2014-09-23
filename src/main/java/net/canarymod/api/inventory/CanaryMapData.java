package net.canarymod.api.inventory;

import net.canarymod.MathHelp;
import net.canarymod.NotYetImplementedException;
import net.canarymod.api.nbt.CanaryCompoundTag;
import net.canarymod.api.nbt.CompoundTag;

/**
 * MapData wrapper
 *
 * @author Jason Jones (darkdiplomat)
 */
public class CanaryMapData implements MapData {
    private final net.minecraft.world.storage.MapData nmsMapData;

    public CanaryMapData(net.minecraft.world.storage.MapData nmsMapData) {
        this.nmsMapData = nmsMapData;
    }

    @Override
    public String getMapName() {
        return getNative().h;
    }

    @Override
    public void setMapName(String s) {
        throw new NotYetImplementedException("setMapName is not yet implemented");
    }

    @Override
    public int getXCenter() {
        return getNative().a;
    }

    @Override
    public int getZCenter() {
        return getNative().b;
    }

    @Override
    public void setXCenter(int xCenter) {
        getNative().a = xCenter;
    }

    @Override
    public void setZCenter(int zCenter) {
        getNative().b = zCenter;
    }

    @Override
    public byte getScale() {
        return getNative().d;
    }

    @Override
    public void setScale(byte scale) {
        getNative().d = (byte)MathHelp.setInRange(scale, 1, 4);
    }

    @Override
    public byte[] getColors() {
        return getNative().e;
    }

    @Override
    public void setColors(byte[] bytes) {
        if (bytes.length != 16384) {
            return;
        }
        getNative().e = bytes;
    }

    @Override
    public void update() {
        throw new NotYetImplementedException("setMapName is not yet implemented");
    }

    @Override
    public void setColumnDirty(int x, int yLower, int yHigher) {
        getNative().a(x, yLower, yHigher);
    }

    @Override
    public void setNBTData(CompoundTag compoundTag) {
        getNative().a(((CanaryCompoundTag)compoundTag).getHandle());
    }

    @Override
    public void getNBTData(CompoundTag compoundTag) {
        getNative().b(((CanaryCompoundTag)compoundTag).getHandle());
    }

    public net.minecraft.world.storage.MapData getNative() {
        return nmsMapData;
    }
}
