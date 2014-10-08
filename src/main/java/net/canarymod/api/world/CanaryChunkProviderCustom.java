package net.canarymod.api.world;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.List;

/**
 * Implements an IChunkProvider and maps to a CanaryMod ChunkProvider implementation.
 *
 * @author Chris (damagefilter)
 */
public class CanaryChunkProviderCustom implements IChunkProvider {
    private ChunkProviderCustom provider;
    private IChunkProvider handle;

    //NOTE: to get the ichunkprovider see where ChunkProviderServre is instantiated!
    //Then pass it a copy of this custom provider, with the handle that would usually go into it instead.
    //the ichunkprovider that would go into the server provider needs to be in here instead!
    public CanaryChunkProviderCustom(ChunkProviderCustom provider, IChunkProvider handle) {
        this.provider = provider;
        this.handle = handle;
    }

    //chunkExists
    @Override
    public boolean a(int i0, int i1) {
        return handle.a(i0, i1);
    }
//
    //provideChunk
    @Override
    public net.minecraft.world.chunk.Chunk d(int i0, int i1) {
        CanaryChunk c = (CanaryChunk) provider.provideChunk(i0, i1);
        return c != null ? c.getHandle() : null;
    }

    //loadChunk
    @Override
    public Chunk a(BlockPos blockPos) {
        return handle.a(blockPos);
    }

    // TODO whatthe fark?
    @Override
    public void a(IChunkProvider iChunkProvider, int i, int i2) {

    }

    //populate (distribute ores, flowers and detail structures etc)
    @Override
    public boolean a(IChunkProvider iChunkProvider, Chunk chunk, int i, int i2) {
        // TODO: Change chunk provider interface in canary to accept the chunk that is populated
        // provider.populate(i0, i1);
        return handle.a(iChunkProvider, chunk, i, i2);
    }

    //saveChunks
    @Override
    public boolean a(boolean flag0, IProgressUpdate iprogressupdate) {
        return handle.a(flag0, iprogressupdate);
    }

    //unloadQueuedChunks
    @Override
    public boolean e() {
        //All ChunkProviders, except ChunkProviderServer return false.
        //This is an indication that unloading chunks failed
        return false;
    }

    //canSave
    @Override
    public boolean d() {
        return true; //ofc we can save!
    }

    //getStatisticsAsString
    @Override
    public String f() {
        return handle.f();
    }

    //getPossibleCreaturs (list of mobs that can spawn here) TODO: Should be useful to have access to!
    @Override
    public List a(EnumCreatureType enumCreatureType, BlockPos blockPos) {
        return handle.a(enumCreatureType, blockPos);
    }

    @Override
    public BlockPos a(World world, String s, BlockPos blockPos) {
        return handle.a(world, s, blockPos);
    }

    //getLoadedChunkCount
    @Override
    public int g() {
        return handle.g();
    }

    //recreateStructures
    @Override
    public void a(Chunk chunk, int i, int i2) {
        // provider.createStructures(i0, i1);
    }

    @Override
    public void c() {
        handle.c();
    }
}
