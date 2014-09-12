package net.canarymod.api.world;

import net.canarymod.api.entity.Entity;
import net.canarymod.api.world.blocks.TileEntity;
import net.canarymod.api.world.position.Position;
import net.minecraft.world.ChunkPosition;

import java.util.*;

/**
 * Chunk implementation
 *
 * @author Chris (damagefilter)
 * @author Jason (darkdiplomat)
 * @author Jos Kuijpers
 */
public class CanaryChunk implements Chunk {
    private net.minecraft.world.chunk.Chunk handle;

    public CanaryChunk(net.minecraft.world.chunk.Chunk chunk) {
        this.handle = chunk;
    }

    public net.minecraft.world.chunk.Chunk getHandle() {
        return handle;
    }

    @Override
    public int getX() {
        return handle.g;
    }

    @Override
    public int getZ() {
        return handle.h;
    }

    @Override
    public int getBlockTypeAt(int x, int y, int z) {
        return net.minecraft.block.Block.b(handle.a(x, y, z));
    }

    @Override
    public void setBlockTypeAt(int x, int y, int z, int type) {
        handle.a(x, y, z, net.minecraft.block.Block.e(type), 0);
    }

    @Override
    public int getBlockDataAt(int x, int y, int z) {
        return handle.c(x, y, z);
    }

    @Override
    public void setBlockDataAt(int x, int y, int z, int data) {
        handle.a(x, y, z, data);
    }

    @Override
    public int getMaxHeigth() {
        return 256;
    }

    @Override
    public boolean isLoaded() {
        return handle.d;
    }

    @Override
    public World getDimension() {
        return handle.e.getCanaryWorld();
    }

    @Override
    public byte[] getBiomeByteData() {
        return handle.m();
    }

    @Override
    public BiomeType[] getBiomeData() {
        BiomeType[] data = BiomeType.fromIdArray(handle.m());

        return data;
    }

    @Override
    public void setBiomeData(BiomeType[] type) {
        if (type.length != 256) {
            return;
        }
        handle.a(BiomeType.fromTypeArray(type));
    }

    @Override
    public void setBiomeData(byte[] data) {
        if (data.length != 256) {
            return;
        }
        for (int index = 0; index < data.length; index++) {
            if (data[index] < 0 || data[index] > BiomeType.count()) { //Use BiomeType.count() so we don't forget to adjust this here
                data[index] = 0;
            }
        }
        handle.a(data);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<Position, TileEntity> getTileEntityMap() {
        HashMap<Position, TileEntity> toRet = new HashMap<Position, TileEntity>();
        synchronized (handle.i) {
            for (ChunkPosition pos : (Set<ChunkPosition>) handle.i.keySet()) {
                Position cPos = new Position(pos.a, pos.b, pos.c);
                net.minecraft.tileentity.TileEntity te = (net.minecraft.tileentity.TileEntity) handle.i.get(pos);
                if (te.complexBlock != null) {
                    toRet.put(cPos, te.complexBlock);
                }
            }
        }
        return toRet;
    }

    @Override
    public boolean hasEntities() {
        return handle.m;
    }

    @SuppressWarnings("unchecked")
    public List<Entity>[] getEntityLists() {
        List<Entity>[] toRet = new List[handle.j.length];
        for (int index = 0; index < handle.j.length; index++) {
            for (Object e : handle.j[index]) {
                if (toRet[index] == null) {
                    toRet[index] = new ArrayList<Entity>();
                }
                toRet[index].add(((net.minecraft.entity.Entity) e).getCanaryEntity());
            }
        }
        return toRet;
    }

    @Override
    public int[] getHeightMap() {
        return handle.f;
    }

    @Override
    public int[] getPrecipitationHeightMap() {
        return handle.b;
    }

    @Override
    public long getLastSaveTime() {
        return handle.p;
    }

    @Override
    public boolean isTerrainPopulated() {
        return handle.k;
    }

    @Override
    public boolean isModified() {
        return handle.l;
    }

    @Override
    public void generateSkyLightMap() {
        handle.b();
    }

    @Override
    public void updateSkyLightMap(boolean force) {
        if (force) {
            handle.p();
        } else {
            handle.o();
        }
    }

    @Override
    public void relightBlock(int x, int y, int z) {
        handle.h(x, y, z);

    }

    @Override
    public Biome getBiome(int x, int z) {
        return this.getHandle().a(x, z, this.getHandle().e.v()).getCanaryBiome();
    }
}
