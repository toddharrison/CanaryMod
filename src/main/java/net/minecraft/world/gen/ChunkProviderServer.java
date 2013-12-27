package net.minecraft.world.gen;

import net.canarymod.NMSToolBox;
import net.canarymod.api.world.CanaryChunkProviderServer;
import net.canarymod.hook.world.ChunkCreatedHook;
import net.canarymod.hook.world.ChunkCreationHook;
import net.canarymod.hook.world.ChunkLoadedHook;
import net.canarymod.hook.world.ChunkUnloadHook;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.LongHashMap;
import net.minecraft.util.ReportedException;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.EmptyChunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.IChunkLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ChunkProviderServer implements IChunkProvider {

    private static Logger b = LogManager.getLogger();
    public Set c = new HashSet(); // CanaryMod private->public
    private Chunk d;
    public IChunkProvider e; // CanaryMod private->public
    public IChunkLoader f; // //CanaryMod private->public
    public boolean a = true;
    public LongHashMap g = new LongHashMap(); // CanaryMod private->public
    public List h = new ArrayList(); // CanaryMod private->public
    private WorldServer i;

    // CanaryMod start
    private CanaryChunkProviderServer canaryChunkProvider;

    //
    public ChunkProviderServer(WorldServer worldserver, IChunkLoader ichunkloader, IChunkProvider ichunkprovider) {
        this.d = new EmptyChunk(worldserver, 0, 0);
        this.i = worldserver;
        this.f = ichunkloader;
        this.e = ichunkprovider;
    }

    // CanaryMod start
    public CanaryChunkProviderServer getCanaryChunkProvider() {
        return canaryChunkProvider;
    }

    // CanaryMod end

    public boolean a(int i0, int i1) {
        return this.g.b(ChunkCoordIntPair.a(i0, i1));
    }

    public void b(int i0, int i1) {
        if (this.i.t.e()) {
            ChunkCoordinates chunkcoordinates = this.i.J();
            int i2 = i0 * 16 + 8 - chunkcoordinates.a;
            int i3 = i1 * 16 + 8 - chunkcoordinates.c;
            short short1 = 128;

            if (i2 < -short1 || i2 > short1 || i3 < -short1 || i3 > short1) {
                this.c.add(Long.valueOf(ChunkCoordIntPair.a(i0, i1)));
            }
        }
        else {
            this.c.add(Long.valueOf(ChunkCoordIntPair.a(i0, i1)));
        }
    }

    public void a() {
        Iterator iterator = this.h.iterator();

        while (iterator.hasNext()) {
            Chunk chunk = (Chunk) iterator.next();

            this.b(chunk.g, chunk.h);
        }
    }

    public Chunk c(int i0, int i1) {
        long i2 = ChunkCoordIntPair.a(i0, i1);

        this.c.remove(Long.valueOf(i2));
        Chunk chunk = (Chunk) this.g.a(i2);

        if (chunk == null) {
            chunk = this.f(i0, i1);
            if (chunk == null) {
                // CanaryMod: ChunkCreation
                ChunkCreationHook hook = (ChunkCreationHook) new ChunkCreationHook(i0, i1, i.getCanaryWorld()).call();
                byte[] blocks = hook.getBlockData();
                if (blocks != null) {
                    chunk = new Chunk(i, NMSToolBox.blockIdsToBlocks(blocks), i0, i1);
                    chunk.k = true; // is populated
                    chunk.b(); // lighting update
                    if (hook.getBiomeData() != null) {
                        chunk.getCanaryChunk().setBiomeData(hook.getBiomeData());
                    }
                }
                //
                else if (this.e == null) {
                    chunk = this.d;
                }
                else {
                    try {
                        chunk = this.e.d(i0, i1);
                    }
                    catch (Throwable throwable) {
                        CrashReport crashreport = CrashReport.a(throwable, "Exception generating new chunk");
                        CrashReportCategory crashreportcategory = crashreport.a("Chunk to be generated");

                        crashreportcategory.a("Location", (Object) String.format("%d,%d", new Object[]{ Integer.valueOf(i0), Integer.valueOf(i1) }));
                        crashreportcategory.a("Position hash", (Object) Long.valueOf(i2));
                        crashreportcategory.a("Generator", (Object) this.e.e());
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
                new ChunkLoadedHook(chunk.getCanaryChunk(), i.getCanaryWorld()).call();
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

        return chunk == null ? (!this.i.y && !this.a ? this.d : this.c(i0, i1)) : chunk;
    }

    private Chunk f(int i0, int i1) {
        if (this.f == null) {
            return null;
        }
        else {
            try {
                Chunk chunk = this.f.a(this.i, i0, i1);

                if (chunk != null) {
                    chunk.p = this.i.H();
                    if (this.e != null) {
                        this.e.e(i0, i1);
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
                chunk.p = this.i.H();
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

        if (!chunk.k) {
            chunk.p();
            if (this.e != null) {
                this.e.a(ichunkprovider, i0, i1);
                chunk.e();
            }
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
                chunk.n = false;
                ++i0;
                if (i0 == 24 && !flag0) {
                    return false;
                }
            }
        }

        return true;
    }

    public void b() {
        if (this.f != null) {
            this.f.b();
        }
    }

    public boolean c() {
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
                        this.c.remove(olong);
                        this.g.d(olong.longValue());
                        this.h.remove(chunk);
                    }
                }
            }

            if (this.f != null) {
                this.f.a();
            }
        }

        return this.e.c();
    }

    public boolean d() {
        return !this.i.c;
    }

    public String e() {
        return "ServerChunkCache: " + this.g.a() + " Drop: " + this.c.size();
    }

    public List a(EnumCreatureType enumcreaturetype, int i0, int i1, int i2) {
        return this.e.a(enumcreaturetype, i0, i1, i2);
    }

    public ChunkPosition a(World world, String s0, int i0, int i1, int i2) {
        return this.e.a(world, s0, i0, i1, i2);
    }

    public int f() {
        return this.g.a();
    }

    public void e(int i0, int i1) {
    }
}
