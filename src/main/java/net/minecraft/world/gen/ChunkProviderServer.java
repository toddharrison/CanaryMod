package net.minecraft.world.gen;

import com.google.common.collect.Lists;
import net.canarymod.api.world.CanaryChunkProviderServer;
import net.canarymod.hook.world.ChunkCreatedHook;
import net.canarymod.hook.world.ChunkCreationHook;
import net.canarymod.hook.world.ChunkLoadedHook;
import net.canarymod.hook.world.ChunkUnloadHook;
import net.canarymod.util.NMSToolBox;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.LongHashMap;
import net.minecraft.util.ReportedException;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.EmptyChunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.IChunkLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


public class ChunkProviderServer implements IChunkProvider {

    private static final Logger b = LogManager.getLogger();
    public Set c = Collections.newSetFromMap(new ConcurrentHashMap()); // CanaryMod private->public
    private Chunk d;
    public IChunkProvider e; // CanaryMod private->public
    public IChunkLoader f; // //CanaryMod private->public
    public boolean a = true;
    public LongHashMap g = new LongHashMap(); // CanaryMod private->public
    public List h = Lists.newArrayList(); // CanaryMod private->public
    private WorldServer i;

    // CanaryMod start
    private CanaryChunkProviderServer canaryChunkProvider;

    //
    public ChunkProviderServer(WorldServer worldserver, IChunkLoader ichunkloader, IChunkProvider ichunkprovider) {
        this.d = new EmptyChunk(worldserver, 0, 0);
        this.i = worldserver;
        this.f = ichunkloader;
        this.e = ichunkprovider;

        this.canaryChunkProvider = new CanaryChunkProviderServer(this);
    }

    // CanaryMod start
    public CanaryChunkProviderServer getCanaryChunkProvider() {
        return canaryChunkProvider;
    }

    // CanaryMod end

    public boolean a(int i0, int i1) {
        return this.g.b(ChunkCoordIntPair.a(i0, i1));
    }

    public List a() {
        return this.h;
    }

    public void b(int i0, int i1) {
        if (this.i.t.e()) {
            if (!this.i.c(i0, i1)) {
                this.c.add(Long.valueOf(ChunkCoordIntPair.a(i0, i1)));
            }
        }
        else {
            this.c.add(Long.valueOf(ChunkCoordIntPair.a(i0, i1)));
        }
    }

    public void b() {
        Iterator iterator = this.h.iterator();

        while (iterator.hasNext()) {
            Chunk chunk = (Chunk) iterator.next();

            this.b(chunk.a, chunk.b);
        }
    }

    public Chunk c(int i0, int i1) {
        long i2 = ChunkCoordIntPair.a(i0, i1);

        this.c.remove(Long.valueOf(i2));
        Chunk chunk = (Chunk) this.g.a(i2);

        if (chunk == null) {
            chunk = this.e(i0, i1);
            boolean newchunk = chunk == null; // CanaryMod: Tracking on new chunks
            if (chunk == null) {

                if (this.e == null) {
                    chunk = this.d;
                }
                else {
                    try {
                        chunk = this.e.d(i0, i1);
                    }
                    catch (Throwable throwable) {
                        CrashReport crashreport = CrashReport.a(throwable, "Exception generating new chunk");
                        CrashReportCategory crashreportcategory = crashreport.a("Chunk to be generated");

                        //TODO: Remove casts?
                        crashreportcategory.a("Location", String.format("%d,%d", new Object[]{Integer.valueOf(i0), Integer.valueOf(i1)}));
                        crashreportcategory.a("Position hash", Long.valueOf(i2));
                        crashreportcategory.a("Generator", this.e.f());
                        throw new ReportedException(crashreport);
                    }
                    // CanaryMod: ChunkCreated
                    new ChunkCreatedHook(chunk.getCanaryChunk(), i.getCanaryWorld()).call();
                    //
                }

            }

            this.g.a(i2, chunk);
            this.h.add(chunk);
            if (chunk != null) {
                chunk.c();
                // CanaryMod: ChunkLoaded
                new ChunkLoadedHook(chunk.getCanaryChunk(), i.getCanaryWorld(), newchunk).call();
                //

                if (chunk.k && this.a(i0 + 1, i1 + 1) && this.a(i0, i1 + 1) && this.a(i0 + 1, i1)) {
                    this.a(this, i0, i1);
                }
            }

            chunk.a(this, this, i0, i1);
        }

        return chunk;
    }

    public Chunk d(int i0, int i1) {
        Chunk chunk = (Chunk) this.g.a(ChunkCoordIntPair.a(i0, i1));

        return chunk == null ? (!this.i.ad() && !this.a ? this.d : this.c(i0, i1)) : chunk;
    }

    private Chunk e(int i0, int i1) {
        if (this.f == null) {
            return null;
        }
        else {
            try {
                Chunk chunk = this.f.a(this.i, i0, i1);

                if (chunk != null) {
                    chunk.b(this.i.K());
                    if (this.e != null) {
                        this.e.a(chunk, i0, i1);
                    }
                }

                return chunk;
            }
            catch (Exception exception) {
                b.error("Couldn\'t load chunk", exception);
                return null;
            }
        }
    }

    // CanaryMod private -> public
    public void a(Chunk chunk) {
        if (this.f != null) {
            try {
                this.f.b(this.i, chunk);
            }
            catch (Exception exception) {
                b.error("Couldn\'t save entities", exception);
            }
        }
    }

    // CanaryMod private -> public
    public void b(Chunk chunk) {
        if (this.f != null) {
            try {
                chunk.b(this.i.K());
                this.f.a(this.i, chunk);
            }
            catch (IOException ioexception) {
                b.error("Couldn\'t save chunk", ioexception);
            }
            catch (MinecraftException minecraftexception) {
                b.error("Couldn\'t save chunk; already in use by another instance of Minecraft?", minecraftexception);
            }
        }
    }

    public void a(IChunkProvider ichunkprovider, int i0, int i1) {
        Chunk chunk = this.d(i0, i1);

        if (!chunk.t()) {
            chunk.n();
            if (this.e != null) {
                this.e.a(ichunkprovider, i0, i1);
                chunk.e();
            }
        }

    }

    public boolean a(IChunkProvider ichunkprovider, Chunk chunk, int i0, int i1) {
        if (this.e != null && this.e.a(ichunkprovider, chunk, i0, i1)) {
            Chunk chunk1 = this.d(i0, i1);

            chunk1.e();
            return true;
        }
        else {
            return false;
        }
    }

    public boolean a(boolean flag0, IProgressUpdate iprogressupdate) {
        int i0 = 0;

        for (int i1 = 0; i1 < this.h.size(); ++i1) {
            Chunk chunk = (Chunk) this.h.get(i1);

            if (flag0) {
                this.a(chunk);
            }

            if (chunk.a(flag0)) {
                this.b(chunk);
                chunk.f(false);
                ++i0;
                if (i0 == 24 && !flag0) {
                    return false;
                }
            }
        }

        return true;
    }

    public void c() {
        if (this.f != null) {
            this.f.b();
        }
    }

    public boolean d() {
        if (!this.i.c) {
            for (int i0 = 0; i0 < 100; ++i0) {
                if (!this.c.isEmpty()) {
                    Long olong = (Long) this.c.iterator().next();
                    Chunk chunk = (Chunk) this.g.a(olong.longValue());

                    if (chunk != null) {
                        // CanaryMod: ChunkUnload
                        ChunkUnloadHook hook = (ChunkUnloadHook) new ChunkUnloadHook(chunk.getCanaryChunk(), i.getCanaryWorld()).call();
                        if (hook.isCanceled()) {
                            // TODO: Might need to return false instead ... unsure
                            return true;
                        }
                        //
                        chunk.d();
                        this.b(chunk);
                        this.a(chunk);
                        this.g.d(olong.longValue());
                        this.h.remove(chunk);
                    }

                    this.c.remove(olong);
                }
            }

            if (this.f != null) {
                this.f.a();
            }
        }

        return this.e.d();
    }

    public boolean e() {
        return !this.i.c;
    }

    public String f() {
        return "ServerChunkCache: " + this.g.a() + " Drop: " + this.c.size();
    }

    public List a(EnumCreatureType enumcreaturetype, BlockPos blockpos) {
        return this.e.a(enumcreaturetype, blockpos);
    }

    public BlockPos a(World world, String s0, BlockPos blockpos) {
        return this.e.a(world, s0, blockpos);
    }

    public int g() {
        return this.g.a();
    }

    public void a(Chunk chunk, int i0, int i1) {
    }

    public Chunk a(BlockPos blockpos) {
        return this.d(blockpos.n() >> 4, blockpos.p() >> 4);
    }

}
