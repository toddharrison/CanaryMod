package net.canarymod.api.world;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.ChunkPosition;
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

    //provideChunk
    @Override
    public net.minecraft.world.chunk.Chunk d(int i0, int i1) {
        CanaryChunk c = (CanaryChunk) provider.provideChunk(i0, i1);
        return c != null ? c.getHandle() : null;
    }

    //loadChunk
    @Override
    public net.minecraft.world.chunk.Chunk c(int i0, int i1) {
        return handle.c(i0, i1);
    }

    //populate (distribute ores, flowers and detail structures etc)
    @Override
    public void a(IChunkProvider ichunkprovider, int i0, int i1) {
        provider.populate(i0, i1);
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
    public List a(EnumCreatureType enumcreaturetype, int i0, int i1, int i2) {
        return handle.a(enumcreaturetype, i0, i1, i2);
    }

    //Find closes structure
    @Override
    public ChunkPosition a(net.minecraft.world.World world, String s0, int i0, int i1, int i2) {
        return handle.a(world, s0, i0, i1, i2);
    }

    //getLoadedChunkCount
    @Override
    public int g() {
        return handle.g();
    }

    //recreateStructures
    @Override
    public void e(int i0, int i1) {
        provider.createStructures(i0, i1);
    }

    @Override
    public void c() {
        handle.c();
    }

}
