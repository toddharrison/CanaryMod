package net.canarymod.api.world;

import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.ArrayList;
import java.util.List;

public class CanaryChunkProviderServer implements ChunkProvider {

    private net.minecraft.world.gen.ChunkProviderServer handle;

    public CanaryChunkProviderServer(net.minecraft.world.gen.ChunkProviderServer handle) {
        this.handle = handle;
    }

    @Override
    public boolean canSave() {
        return this.handle.d();
    }

    @Override
    public boolean chunkExists(int x, int z) {
        return this.handle.a(x, z);
    }

    @Override
    public Chunk loadChunk(int x, int z) {
        return this.handle.c(x, z).getCanaryChunk();
    }

    @Override
    public Chunk provideChunk(int x, int z) {
        return this.handle.d(x, z).getCanaryChunk();
    }

    @Override
    public boolean saveChunk(boolean saveAll) {
        // The chunk saver doesn't touch the progress object thingy
        return this.handle.a(saveAll, null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Chunk regenerateChunk(int x, int z) {
        Long chunkCoordIntPair = ChunkCoordIntPair.a(x, z);
        // Unloading the chunk
        net.minecraft.world.chunk.Chunk unloadedChunk = (net.minecraft.world.chunk.Chunk) handle.g.a(chunkCoordIntPair.longValue());

        if (unloadedChunk != null) {
            unloadedChunk.e();
            handle.b(unloadedChunk); // save chunk data
            handle.a(unloadedChunk); // save extra chunk data

            handle.c.remove(chunkCoordIntPair);
            handle.g.d(chunkCoordIntPair.longValue());
            handle.h.remove(unloadedChunk);
        }

        // Generating the new chunk
        net.minecraft.world.chunk.Chunk newChunk = handle.e.d(x, z);

        handle.g.a(chunkCoordIntPair, newChunk);
        handle.h.add(newChunk);
        if (newChunk != null) {
            newChunk.c();
            newChunk.d();
        }
        newChunk.a(handle, handle, x, z);

        // Save the new chunk, overriding the old one
        handle.a(newChunk);
        handle.b(newChunk);
        newChunk.k = false;
        if (handle.e != null) {
            handle.e.c();
        }

        return newChunk.getCanaryChunk();
    }

    @Override
    public void reloadChunk(int x, int z) {
        dropChunk(x, z);
        loadChunk(x, z);
    }

    @Override
    public void dropChunk(int x, int z) {
        handle.b(x, z);
    }

    @Override
    public boolean isChunkLoaded(int x, int z) {
        return handle.a(x, z);
    }

    @Override
    public void populate(ChunkProvider provider, int x, int z) {
        handle.a((IChunkProvider)null, x, z);
    }

    @Override
    public String getStatistics() {
        return handle.f();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Chunk> getLoadedChunks() {
        List<Chunk> loadedChunks = new ArrayList<Chunk>(this.handle.h.size());
        for (net.minecraft.world.chunk.Chunk nmschunk : (List<net.minecraft.world.chunk.Chunk>) this.handle.h) {
            loadedChunks.add(nmschunk.getCanaryChunk());
        }
        return loadedChunks;
    }

    /**
     * Get the handle for this CanaryChunkProviderServer.
     * That is the NMS object.
     *
     * @return net.minecraft.server.ChunkProviderServer
     */
    public net.minecraft.world.gen.ChunkProviderServer getHandle() {
        return this.handle;
    }

}
