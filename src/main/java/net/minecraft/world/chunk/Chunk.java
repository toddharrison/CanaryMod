package net.minecraft.world.chunk;

import com.google.common.base.Predicate;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import net.canarymod.api.world.CanaryChunk;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraft.world.gen.ChunkProviderDebug;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;


public class Chunk {

    private static final Logger c = LogManager.getLogger();
    private final ExtendedBlockStorage[] d;
    public byte[] e; //CanaryMod private => public
    private final int[] f;
    private final boolean[] g;
    private boolean h;
    private final World i;
    private final int[] j;
    public final int a;
    public final int b;
    private boolean k;
    private final Map l;
    private final ClassInheratanceMultiMap[] m;
    private boolean n;
    private boolean o;
    private boolean p;
    private boolean q;
    private boolean r;
    private long s;
    private int t;
    private long u;
    private int v;
    private ConcurrentLinkedQueue w;
    private CanaryChunk canaryChunk; // CanaryMod: Chunk wrapper

    public Chunk(World world, int i0, int i1) {
        canaryChunk = new CanaryChunk(this); // CanaryMod: wrap chunk
        this.d = new ExtendedBlockStorage[16];
        this.e = new byte[256];
        this.f = new int[256];
        this.g = new boolean[256];
        this.l = Maps.newHashMap();
        this.v = 4096;
        this.w = Queues.newConcurrentLinkedQueue();
        this.m = (ClassInheratanceMultiMap[]) (new ClassInheratanceMultiMap[16]);
        this.i = world;
        this.a = i0;
        this.b = i1;
        this.j = new int[256];

        for (int i2 = 0; i2 < this.m.length; ++i2) {
            this.m[i2] = new ClassInheratanceMultiMap(Entity.class);
        }

        Arrays.fill(this.f, -999);
        Arrays.fill(this.e, (byte) -1);
    }

    public Chunk(World world, ChunkPrimer chunkprimer, int i0, int i1) {
        this(world, i0, i1);
        short short1 = 256;
        boolean flag0 = !world.t.o();

        for (int i2 = 0; i2 < 16; ++i2) {
            for (int i3 = 0; i3 < 16; ++i3) {
                for (int i4 = 0; i4 < short1; ++i4) {
                    int i5 = i2 * short1 * 16 | i3 * short1 | i4;
                    IBlockState iblockstate = chunkprimer.a(i5);

                    if (iblockstate.c().r() != Material.a) {
                        int i6 = i4 >> 4;

                        if (this.d[i6] == null) {
                            this.d[i6] = new ExtendedBlockStorage(i6 << 4, flag0);
                        }

                        this.d[i6].a(i2, i4 & 15, i3, iblockstate);
                    }
                }
            }
        }

    }

    public boolean a(int i0, int i1) {
        return i0 == this.a && i1 == this.b;
    }

    public int f(BlockPos blockpos) {
        return this.b(blockpos.n() & 15, blockpos.p() & 15);
    }

    public int b(int i0, int i1) {
        return this.j[i1 << 4 | i0];
    }

    public int g() {
        for (int i0 = this.d.length - 1; i0 >= 0; --i0) {
            if (this.d[i0] != null) {
                return this.d[i0].d();
            }
        }

        return 0;
    }

    public ExtendedBlockStorage[] h() {
        return this.d;
    }

    public void b() {
        int i0 = this.g();

        this.t = Integer.MAX_VALUE;

        for (int i1 = 0; i1 < 16; ++i1) {
            int i2 = 0;

            while (i2 < 16) {
                this.f[i1 + (i2 << 4)] = -999;
                int i3 = i0 + 16;

                while (true) {
                    if (i3 > 0) {
                        if (this.e(i1, i3 - 1, i2) == 0) {
                            --i3;
                            continue;
                        }

                        this.j[i2 << 4 | i1] = i3;
                        if (i3 < this.t) {
                            this.t = i3;
                        }
                    }

                    if (!this.i.t.o()) {
                        i3 = 15;
                        int i4 = i0 + 16 - 1;

                        do {
                            int i5 = this.e(i1, i4, i2);

                            if (i5 == 0 && i3 != 15) {
                                i5 = 1;
                            }

                            i3 -= i5;
                            if (i3 > 0) {
                                ExtendedBlockStorage extendedblockstorage = this.d[i4 >> 4];

                                if (extendedblockstorage != null) {
                                    extendedblockstorage.a(i1, i4 & 15, i2, i3);
                                    this.i.n(new BlockPos((this.a << 4) + i1, i4, (this.b << 4) + i2));
                                }
                            }

                            --i4;
                        } while (i4 > 0 && i3 > 0);
                    }

                    ++i2;
                    break;
                }
            }
        }

        this.q = true;
    }

    private void d(int i0, int i1) {
        this.g[i0 + i1 * 16] = true;
        this.k = true;
    }

    //CanaryMod private->public
    public void h(boolean flag0) {
        this.i.B.a("recheckGaps");
        if (this.i.a(new BlockPos(this.a * 16 + 8, 0, this.b * 16 + 8), (int) 16)) {
            for (int i0 = 0; i0 < 16; ++i0) {
                for (int i1 = 0; i1 < 16; ++i1) {
                    if (this.g[i0 + i1 * 16]) {
                        this.g[i0 + i1 * 16] = false;
                        int i2 = this.b(i0, i1);
                        int i3 = this.a * 16 + i0;
                        int i4 = this.b * 16 + i1;
                        int i5 = Integer.MAX_VALUE;

                        Iterator iterator;
                        EnumFacing enumfacing;

                        for (iterator = EnumFacing.Plane.HORIZONTAL.iterator(); iterator.hasNext(); i5 = Math.min(i5, this.i.b(i3 + enumfacing.g(), i4 + enumfacing.i()))) {
                            enumfacing = (EnumFacing) iterator.next();
                        }

                        this.c(i3, i4, i5);
                        iterator = EnumFacing.Plane.HORIZONTAL.iterator();

                        while (iterator.hasNext()) {
                            enumfacing = (EnumFacing) iterator.next();
                            this.c(i3 + enumfacing.g(), i4 + enumfacing.i(), i2);
                        }

                        if (flag0) {
                            this.i.B.b();
                            return;
                        }
                    }
                }
            }

            this.k = false;
        }

        this.i.B.b();
    }

    private void c(int i0, int i1, int i2) {
        int i3 = this.i.m(new BlockPos(i0, 0, i1)).o();

        if (i3 > i2) {
            this.a(i0, i1, i2, i3 + 1);
        }
        else if (i3 < i2) {
            this.a(i0, i1, i3, i2 + 1);
        }
    }

    private void a(int i0, int i1, int i2, int i3) {
        if (i3 > i2 && this.i.a(new BlockPos(i0, 0, i1), (int) 16)) {
            for (int i4 = i2; i4 < i3; ++i4) {
                this.i.c(EnumSkyBlock.SKY, new BlockPos(i0, i4, i1));
            }

            this.q = true;
        }
    }

    //CanaryMod private->public
    public void d(int i0, int i1, int i2) {
        int i3 = this.j[i2 << 4 | i0] & 255;
        int i4 = i3;

        if (i1 > i3) {
            i4 = i1;
        }

        while (i4 > 0 && this.e(i0, i4 - 1, i2) == 0) {
            --i4;
        }

        if (i4 != i3) {
            this.i.a(i0 + this.a * 16, i2 + this.b * 16, i4, i3);
            this.j[i2 << 4 | i0] = i4;
            int i5 = this.a * 16 + i0;
            int i6 = this.b * 16 + i2;
            int i7;
            int i8;

            if (!this.i.t.o()) {
                ExtendedBlockStorage extendedblockstorage;

                if (i4 < i3) {
                    for (i7 = i4; i7 < i3; ++i7) {
                        extendedblockstorage = this.d[i7 >> 4];
                        if (extendedblockstorage != null) {
                            extendedblockstorage.a(i0, i7 & 15, i2, 15);
                            this.i.n(new BlockPos((this.a << 4) + i0, i7, (this.b << 4) + i2));
                        }
                    }
                }
                else {
                    for (i7 = i3; i7 < i4; ++i7) {
                        // CanaryMod start: Catch corrupt index info
                        if (i7 >> 4 < 0 || i7 >> 4 >= 16) {
                            t.warn("Invalid chunk info array index: " + (i7 >> 4));
                            t.warn("x: " + i3 + ", z: " + i4);
                            t.warn("Chunk location: " + i5 + ", " + i6);
                            i7 = 0;
                        }
                        // CanaryMod end
                        extendedblockstorage = this.d[i7 >> 4];
                        if (extendedblockstorage != null) {
                            extendedblockstorage.a(i0, i7 & 15, i2, 0);
                            this.i.n(new BlockPos((this.a << 4) + i0, i7, (this.b << 4) + i2));
                        }
                    }
                }

                i7 = 15;

                while (i4 > 0 && i7 > 0) {
                    --i4;
                    i8 = this.e(i0, i4, i2);
                    if (i8 == 0) {
                        i8 = 1;
                    }

                    i7 -= i8;
                    if (i7 < 0) {
                        i7 = 0;
                    }

                    ExtendedBlockStorage extendedblockstorage1 = this.d[i4 >> 4];

                    if (extendedblockstorage1 != null) {
                        extendedblockstorage1.a(i0, i4 & 15, i2, i7);
                    }
                }
            }

            i7 = this.j[i2 << 4 | i0];
            i8 = i3;
            int i9 = i7;

            if (i7 < i3) {
                i8 = i7;
                i9 = i3;
            }

            if (i7 < this.t) {
                this.t = i7;
            }

            if (!this.i.t.o()) {
                Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();

                while (iterator.hasNext()) {
                    EnumFacing enumfacing = (EnumFacing) iterator.next();

                    this.a(i5 + enumfacing.g(), i6 + enumfacing.i(), i8, i9);
                }

                this.a(i5, i6, i8, i9);
            }

            this.q = true;
        }
    }

    public int b(BlockPos blockpos) {
        return this.a(blockpos).n();
    }

    private int e(int i0, int i1, int i2) {
        return this.f(i0, i1, i2).n();
    }

    private Block f(int i0, int i1, int i2) {
        Block block = Blocks.a;

        if (i1 >= 0 && i1 >> 4 < this.d.length) {
            ExtendedBlockStorage extendedblockstorage = this.d[i1 >> 4];

            if (extendedblockstorage != null) {
                try {
                    block = extendedblockstorage.b(i0, i1 & 15, i2);
                }
                catch (Throwable throwable) {
                    CrashReport crashreport = CrashReport.a(throwable, "Getting block");

                    throw new ReportedException(crashreport);
                }
            }
        }
        return block;
    }

    public Block a(final int i0, final int i1, final int i2) {
        try {
            return this.f(i0 & 15, i1, i2 & 15);
        }
        catch (ReportedException reportedexception) {
            CrashReportCategory crashreportcategory = reportedexception.a().a("Block being got");

            crashreportcategory.a("Location", new Callable() {

                public String call() {
                    return CrashReportCategory.a(new BlockPos(Chunk.this.a * 16 + i0, i1, Chunk.this.b * 16 + i2));
                }
            });
            throw reportedexception;
        }
    }

    public Block a(final BlockPos blockpos) {
        try {
            return this.f(blockpos.n() & 15, blockpos.o(), blockpos.p() & 15);
        }
        catch (ReportedException reportedexception) {
            CrashReportCategory crashreportcategory = reportedexception.a().a("Block being got");

            crashreportcategory.a("Location", new Callable() {

                public String a() {
                    return CrashReportCategory.a(blockpos);
                }

                public Object call() {
                    return this.a();
                }
            });
            throw reportedexception;
        }
    }

    public IBlockState g(final BlockPos blockpos) {
        if (this.i.G() == WorldType.g) {
            IBlockState iblockstate = null;

            if (blockpos.o() == 60) {
                iblockstate = Blocks.cv.P();
            }

            if (blockpos.o() == 70) {
                iblockstate = ChunkProviderDebug.b(blockpos.n(), blockpos.p());
            }

            return iblockstate == null ? Blocks.a.P() : iblockstate;
        }
        else {
            try {
                if (blockpos.o() >= 0 && blockpos.o() >> 4 < this.d.length) {
                    ExtendedBlockStorage extendedblockstorage = this.d[blockpos.o() >> 4];

                    if (extendedblockstorage != null) {
                        int i0 = blockpos.n() & 15;
                        int i1 = blockpos.o() & 15;
                        int i2 = blockpos.p() & 15;

                        return extendedblockstorage.a(i0, i1, i2);
                    }
                }

                return Blocks.a.P();
            }
            catch (Throwable throwable) {
                CrashReport crashreport = CrashReport.a(throwable, "Getting block state");
                CrashReportCategory crashreportcategory = crashreport.a("Block being got");

                crashreportcategory.a("Location", new Callable() {

                    public String a() {
                        return CrashReportCategory.a(blockpos);
                    }

                    public Object call() {
                        return this.a();
                    }
                });
                throw new ReportedException(crashreport);
            }
        }
    }

    private int g(int i0, int i1, int i2) {
        if (i1 >> 4 >= this.d.length) {
            return 0;
        }
        else {
            ExtendedBlockStorage extendedblockstorage = this.d[i1 >> 4];

            return extendedblockstorage != null ? extendedblockstorage.c(i0, i1 & 15, i2) : 0;
        }
    }

    public int c(BlockPos blockpos) {
        return this.g(blockpos.n() & 15, blockpos.o(), blockpos.p() & 15);
    }

    public IBlockState a(BlockPos blockpos, IBlockState iblockstate) {
        int i0 = blockpos.n() & 15;
        int i1 = blockpos.o();
        int i2 = blockpos.p() & 15;
        int i3 = i2 << 4 | i0;

        if (i1 >= this.f[i3] - 1) {
            this.f[i3] = -999;
        }

        int i4 = this.j[i3];
        IBlockState iblockstate1 = this.g(blockpos);

        if (iblockstate1 == iblockstate) {
            return null;
        }
        else {
            Block block = iblockstate.c();
            Block block1 = iblockstate1.c();
            ExtendedBlockStorage extendedblockstorage = this.d[i1 >> 4];
            boolean flag0 = false;

            if (extendedblockstorage == null) {
                if (block == Blocks.a) {
                    return null;
                }

                extendedblockstorage = this.d[i1 >> 4] = new ExtendedBlockStorage(i1 >> 4 << 4, !this.i.t.o());
                flag0 = i1 >= i4;
            }

            extendedblockstorage.a(i0, i1 & 15, i2, iblockstate);
            if (block1 != block) {
                if (!this.i.D) {
                    block1.b(this.i, blockpos, iblockstate1);
                }
                else if (block1 instanceof ITileEntityProvider) {
                    this.i.t(blockpos);
                }
            }

            if (extendedblockstorage.b(i0, i1 & 15, i2) != block) {
                return null;
            }
            else {
                if (flag0) {
                    this.b();
                }
                else {
                    int i5 = block.n();
                    int i6 = block1.n();

                    if (i5 > 0) {
                        if (i1 >= i4) {
                            this.d(i0, i1 + 1, i2);
                        }
                    }
                    else if (i1 == i4 - 1) {
                        this.d(i0, i1, i2);
                    }

                    if (i5 != i6 && (i5 < i6 || this.a(EnumSkyBlock.SKY, blockpos) > 0 || this.a(EnumSkyBlock.BLOCK, blockpos) > 0)) {
                        this.d(i0, i2);
                    }
                }

                TileEntity tileentity;

                if (block1 instanceof ITileEntityProvider) {
                    tileentity = this.a(blockpos, EnumCreateEntityType.CHECK);
                    if (tileentity != null) {
                        tileentity.E();
                    }
                }

                if (!this.i.D && block1 != block) {
                    block.c(this.i, blockpos, iblockstate);
                }

                if (block instanceof ITileEntityProvider) {
                    tileentity = this.a(blockpos, EnumCreateEntityType.CHECK);
                    if (tileentity == null) {
                        tileentity = ((ITileEntityProvider) block).a(this.i, block.c(iblockstate));
                        this.i.a(blockpos, tileentity);
                    }

                    if (tileentity != null) {
                        tileentity.E();
                    }
                }

                this.q = true;
                return iblockstate1;
            }
        }
    }

    public int a(EnumSkyBlock enumskyblock, BlockPos blockpos) {
        int i0 = blockpos.n() & 15;
        int i1 = blockpos.o();
        int i2 = blockpos.p() & 15;
        ExtendedBlockStorage extendedblockstorage = this.d[i1 >> 4];

        return extendedblockstorage == null ? (this.d(blockpos) ? enumskyblock.c : 0) : (enumskyblock == EnumSkyBlock.SKY ? (this.i.t.o() ? 0 : extendedblockstorage.d(i0, i1 & 15, i2)) : (enumskyblock == EnumSkyBlock.BLOCK ? extendedblockstorage.e(i0, i1 & 15, i2) : enumskyblock.c));
    }

    public void a(EnumSkyBlock enumskyblock, BlockPos blockpos, int i0) {
        int i1 = blockpos.n() & 15;
        int i2 = blockpos.o();
        int i3 = blockpos.p() & 15;
        ExtendedBlockStorage extendedblockstorage = this.d[i2 >> 4];

        if (extendedblockstorage == null) {
            extendedblockstorage = this.d[i2 >> 4] = new ExtendedBlockStorage(i2 >> 4 << 4, !this.i.t.o());
            this.b();
        }

        this.q = true;
        if (enumskyblock == EnumSkyBlock.SKY) {
            if (!this.i.t.o()) {
                extendedblockstorage.a(i1, i2 & 15, i3, i0);
            }
        }
        else if (enumskyblock == EnumSkyBlock.BLOCK) {
            extendedblockstorage.b(i1, i2 & 15, i3, i0);
        }
    }

    public int a(BlockPos blockpos, int i0) {
        int i1 = blockpos.n() & 15;
        int i2 = blockpos.o();
        int i3 = blockpos.p() & 15;
        ExtendedBlockStorage extendedblockstorage = this.d[i2 >> 4];

        if (extendedblockstorage == null) {
            return !this.i.t.o() && i0 < EnumSkyBlock.SKY.c ? EnumSkyBlock.SKY.c - i0 : 0;
        }
        else {
            int i4 = this.i.t.o() ? 0 : extendedblockstorage.d(i1, i2 & 15, i3);

            i4 -= i0;
            int i5 = extendedblockstorage.e(i1, i2 & 15, i3);

            if (i5 > i4) {
                i4 = i5;
            }

            return i4;
        }
    }

    public void a(Entity entity) {
        this.r = true;
        int i0 = MathHelper.c(entity.s / 16.0D);
        int i1 = MathHelper.c(entity.u / 16.0D);

        if (i0 != this.a || i1 != this.b) {
            c.warn("Wrong location! (" + i0 + ", " + i1 + ") should be (" + this.a + ", " + this.b + "), " + entity, new Object[]{entity});
            entity.J();
        }

        int i2 = MathHelper.c(entity.t / 16.0D);

        if (i2 < 0) {
            i2 = 0;
        }

        if (i2 >= this.m.length) {
            i2 = this.m.length - 1;
        }

        entity.ad = true;
        entity.ae = this.a;
        entity.af = i2;
        entity.ag = this.b;
        this.m[i2].add(entity);
    }

    public void b(Entity entity) {
        this.a(entity, entity.af);
    }

    public void a(Entity entity, int i0) {
        if (i0 < 0) {
            i0 = 0;
        }

        if (i0 >= this.m.length) {
            i0 = this.m.length - 1;
        }

        this.m[i0].remove(entity);
    }

    public boolean d(BlockPos blockpos) {
        int i0 = blockpos.n() & 15;
        int i1 = blockpos.o();
        int i2 = blockpos.p() & 15;

        return i1 >= this.j[i2 << 4 | i0];
    }

    private TileEntity i(BlockPos blockpos) {
        Block block = this.a(blockpos);

        return !block.x() ? null : ((ITileEntityProvider) block).a(this.i, this.c(blockpos));
    }

    public TileEntity a(BlockPos blockpos, EnumCreateEntityType chunk_enumcreateentitytype) {
        TileEntity tileentity = (TileEntity) this.l.get(blockpos);

        if (tileentity == null) {
            if (chunk_enumcreateentitytype == EnumCreateEntityType.IMMEDIATE) {
                tileentity = this.i(blockpos);
                this.i.a(blockpos, tileentity);
            }
            else if (chunk_enumcreateentitytype == EnumCreateEntityType.QUEUED) {
                this.w.add(blockpos);
            }
        }
        else if (tileentity.x()) {
            this.l.remove(blockpos);
            return null;
        }

        return tileentity;
    }

    public void a(TileEntity tileentity) {
        this.a(tileentity.v(), tileentity);
        if (this.h) {
            this.i.a(tileentity);
        }
    }

    public void a(BlockPos blockpos, TileEntity tileentity) {
        tileentity.a(this.i);
        tileentity.a(blockpos);
        if (this.a(blockpos) instanceof ITileEntityProvider) {
            if (this.l.containsKey(blockpos)) {
                ((TileEntity) this.l.get(blockpos)).y();
            }

            tileentity.D();
            this.l.put(blockpos, tileentity);
        }
    }

    public void e(BlockPos blockpos) {
        if (this.h) {
            TileEntity tileentity = (TileEntity) this.l.remove(blockpos);

            if (tileentity != null) {
                tileentity.y();
            }
        }
    }

    public void c() {
        this.h = true;
        this.i.a(this.l.values());

        for (int i0 = 0; i0 < this.m.length; ++i0) {
            Iterator iterator = this.m[i0].iterator();

            while (iterator.hasNext()) {
                Entity entity = (Entity) iterator.next();

                entity.ah();
            }

            this.i.b((Collection) this.m[i0]);
        }
    }

    public void d() {
        this.h = false;
        Iterator iterator = this.l.values().iterator();

        while (iterator.hasNext()) {
            TileEntity tileentity = (TileEntity) iterator.next();

            this.i.b(tileentity);
        }

        for (int i0 = 0; i0 < this.m.length; ++i0) {
            this.i.c((Collection) this.m[i0]);
        }
    }

    public void e() {
        this.q = true;
    }

    public void a(Entity entity, AxisAlignedBB axisalignedbb, List list, Predicate predicate) {
        int i0 = MathHelper.c((axisalignedbb.b - 2.0D) / 16.0D);
        int i1 = MathHelper.c((axisalignedbb.e + 2.0D) / 16.0D);

        i0 = MathHelper.a(i0, 0, this.m.length - 1);
        i1 = MathHelper.a(i1, 0, this.m.length - 1);

        for (int i2 = i0; i2 <= i1; ++i2) {
            Iterator iterator = this.m[i2].iterator();

            while (iterator.hasNext()) {
                Entity entity1 = (Entity) iterator.next();

                if (entity1 != entity && entity1.aQ().b(axisalignedbb) && (predicate == null || predicate.apply(entity1))) {
                    list.add(entity1);
                    Entity[] aentity = entity1.aC();

                    if (aentity != null) {
                        for (int i3 = 0; i3 < aentity.length; ++i3) {
                            entity1 = aentity[i3];
                            if (entity1 != entity && entity1.aQ().b(axisalignedbb) && (predicate == null || predicate.apply(entity1))) {
                                list.add(entity1);
                            }
                        }
                    }
                }
            }
        }
    }

    public void a(Class oclass0, AxisAlignedBB axisalignedbb, List list, Predicate predicate) {
        int i0 = MathHelper.c((axisalignedbb.b - 2.0D) / 16.0D);
        int i1 = MathHelper.c((axisalignedbb.e + 2.0D) / 16.0D);

        i0 = MathHelper.a(i0, 0, this.m.length - 1);
        i1 = MathHelper.a(i1, 0, this.m.length - 1);

        for (int i2 = i0; i2 <= i1; ++i2) {
            Iterator iterator = this.m[i2].b(oclass0).iterator();

            while (iterator.hasNext()) {
                Entity entity = (Entity) iterator.next();

                if (entity.aQ().b(axisalignedbb) && (predicate == null || predicate.apply(entity))) {
                    list.add(entity);
                }
            }
        }
    }

    public boolean a(boolean flag0) {
        if (flag0) {
            if (this.r && this.i.K() != this.s || this.q) {
                return true;
            }
        }
        else if (this.r && this.i.K() >= this.s + 600L) {
            return true;
        }

        return this.q;
    }

    public Random a(long i0) {
        return new Random(this.i.J() + (long) (this.a * this.a * 4987142) + (long) (this.a * 5947611) + (long) (this.b * this.b) * 4392871L + (long) (this.b * 389711) ^ i0);
    }

    public boolean f() {
        return false;
    }

    public void a(IChunkProvider ichunkprovider, IChunkProvider ichunkprovider1, int i0, int i1) {
        boolean flag0 = ichunkprovider.a(i0, i1 - 1);
        boolean flag1 = ichunkprovider.a(i0 + 1, i1);
        boolean flag2 = ichunkprovider.a(i0, i1 + 1);
        boolean flag3 = ichunkprovider.a(i0 - 1, i1);
        boolean flag4 = ichunkprovider.a(i0 - 1, i1 - 1);
        boolean flag5 = ichunkprovider.a(i0 + 1, i1 + 1);
        boolean flag6 = ichunkprovider.a(i0 - 1, i1 + 1);
        boolean flag7 = ichunkprovider.a(i0 + 1, i1 - 1);

        if (flag1 && flag2 && flag5) {
            if (!this.n) {
                ichunkprovider.a(ichunkprovider1, i0, i1);
            }
            else {
                ichunkprovider.a(ichunkprovider1, this, i0, i1);
            }
        }

        Chunk chunk;

        if (flag3 && flag2 && flag6) {
            chunk = ichunkprovider.d(i0 - 1, i1);
            if (!chunk.n) {
                ichunkprovider.a(ichunkprovider1, i0 - 1, i1);
            }
            else {
                ichunkprovider.a(ichunkprovider1, chunk, i0 - 1, i1);
            }
        }

        if (flag0 && flag1 && flag7) {
            chunk = ichunkprovider.d(i0, i1 - 1);
            if (!chunk.n) {
                ichunkprovider.a(ichunkprovider1, i0, i1 - 1);
            }
            else {
                ichunkprovider.a(ichunkprovider1, chunk, i0, i1 - 1);
            }
        }

        if (flag4 && flag0 && flag3) {
            chunk = ichunkprovider.d(i0 - 1, i1 - 1);
            if (!chunk.n) {
                ichunkprovider.a(ichunkprovider1, i0 - 1, i1 - 1);
            }
            else {
                ichunkprovider.a(ichunkprovider1, chunk, i0 - 1, i1 - 1);
            }
        }
    }

    public BlockPos h(BlockPos blockpos) {
        int i0 = blockpos.n() & 15;
        int i1 = blockpos.p() & 15;
        int i2 = i0 | i1 << 4;
        BlockPos blockpos1 = new BlockPos(blockpos.n(), this.f[i2], blockpos.p());

        if (blockpos1.o() == -999) {
            int i3 = this.g() + 15;

            blockpos1 = new BlockPos(blockpos.n(), i3, blockpos.p());
            int i4 = -1;

            while (blockpos1.o() > 0 && i4 == -1) {
                Block block = this.a(blockpos1);
                Material material = block.r();

                if (!material.c() && !material.d()) {
                    blockpos1 = blockpos1.b();
                }
                else {
                    i4 = blockpos1.o() + 1;
                }
            }

            this.f[i2] = i4;
        }

        return new BlockPos(blockpos.n(), this.f[i2], blockpos.p());
    }

    public void b(boolean flag0) {
        if (this.k && !this.i.t.o() && !flag0) {
            this.h(this.i.D);
        }

        this.p = true;
        if (!this.o && this.n) {
            this.n();
        }

        while (!this.w.isEmpty()) {
            BlockPos blockpos = (BlockPos) this.w.poll();

            if (this.a(blockpos, EnumCreateEntityType.CHECK) == null && this.a(blockpos).x()) {
                TileEntity tileentity = this.i(blockpos);

                this.i.a(blockpos, tileentity);
                this.i.b(blockpos, blockpos);
            }
        }

    }

    public boolean i() {
        return this.p && this.n && this.o;
    }

    public ChunkCoordIntPair j() {
        return new ChunkCoordIntPair(this.a, this.b);
    }

    public boolean c(int i0, int i1) {
        if (i0 < 0) {
            i0 = 0;
        }

        if (i1 >= 256) {
            i1 = 255;
        }

        for (int i2 = i0; i2 <= i1; i2 += 16) {
            ExtendedBlockStorage extendedblockstorage = this.d[i2 >> 4];

            if (extendedblockstorage != null && !extendedblockstorage.a()) {
                return false;
            }
        }

        return true;
    }

    public void a(ExtendedBlockStorage[] aextendedblockstorage) {
        if (this.d.length != aextendedblockstorage.length) {
            c.warn("Could not set level chunk sections, array length is " + aextendedblockstorage.length + " instead of " + this.d.length);
        }
        else {
            for (int i0 = 0; i0 < this.d.length; ++i0) {
                this.d[i0] = aextendedblockstorage[i0];
            }

        }
    }

    public BiomeGenBase a(BlockPos blockpos, WorldChunkManager worldchunkmanager) {
        int i0 = blockpos.n() & 15;
        int i1 = blockpos.p() & 15;
        int i2 = this.e[i1 << 4 | i0] & 255;
        BiomeGenBase biomegenbase;

        if (i2 == 255) {
            biomegenbase = worldchunkmanager.a(blockpos, BiomeGenBase.q);
            i2 = biomegenbase.az;
            this.e[i1 << 4 | i0] = (byte) (i2 & 255);
        }

        biomegenbase = BiomeGenBase.e(i2);
        return biomegenbase == null ? BiomeGenBase.q : biomegenbase;
    }

    public byte[] k() {
        return this.e;
    }

    public void a(byte[] abyte) {
        if (this.e.length != abyte.length) {
            c.warn("Could not set level chunk biomes, array length is " + abyte.length + " instead of " + this.e.length);
        }
        else {
            for (int i0 = 0; i0 < this.e.length; ++i0) {
                this.e[i0] = abyte[i0];
            }

        }
    }

    public void l() {
        this.v = 0;
    }

    public void m() {
        BlockPos blockpos = new BlockPos(this.a << 4, 0, this.b << 4);

        for (int i0 = 0; i0 < 8; ++i0) {
            if (this.v >= 4096) {
                return;
            }

            int i1 = this.v % 16;
            int i2 = this.v / 16 % 16;
            int i3 = this.v / 256;

            ++this.v;

            for (int i4 = 0; i4 < 16; ++i4) {
                BlockPos blockpos1 = blockpos.a(i2, (i1 << 4) + i4, i3);
                boolean flag0 = i4 == 0 || i4 == 15 || i2 == 0 || i2 == 15 || i3 == 0 || i3 == 15;

                if (this.d[i1] == null && flag0 || this.d[i1] != null && this.d[i1].b(i2, i4, i3).r() == Material.a) {
                    EnumFacing[] aenumfacing = EnumFacing.values();
                    int i5 = aenumfacing.length;

                    for (int i6 = 0; i6 < i5; ++i6) {
                        EnumFacing enumfacing = aenumfacing[i6];
                        BlockPos blockpos2 = blockpos1.a(enumfacing);

                        if (this.i.p(blockpos2).c().p() > 0) {
                            this.i.x(blockpos2);
                        }
                    }

                    this.i.x(blockpos1);
                }
            }
        }
    }

    public void n() {
        this.n = true;
        this.o = true;
        BlockPos blockpos = new BlockPos(this.a << 4, 0, this.b << 4);

        if (!this.i.t.o()) {
            if (this.i.a(blockpos.a(-1, 0, -1), blockpos.a(16, 63, 16))) {
                label42:
                for (int i0 = 0; i0 < 16; ++i0) {
                    for (int i1 = 0; i1 < 16; ++i1) {
                        if (!this.e(i0, i1)) {
                            this.o = false;
                            break label42;
                        }
                    }
                }

                if (this.o) {
                    Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();

                    while (iterator.hasNext()) {
                        EnumFacing enumfacing = (EnumFacing) iterator.next();
                        int i2 = enumfacing.c() == EnumFacing.AxisDirection.POSITIVE ? 16 : 1;

                        this.i.f(blockpos.a(enumfacing, i2)).a(enumfacing.d());
                    }

                    this.y();
                }
            }
            else {
                this.o = false;
            }
        }

    }

    private void y() {
        for (int i0 = 0; i0 < this.g.length; ++i0) {
            this.g[i0] = true;
        }

        this.h(false);
    }

    private void a(EnumFacing enumfacing) {
        if (this.n) {
            int i0;

            if (enumfacing == EnumFacing.EAST) {
                for (i0 = 0; i0 < 16; ++i0) {
                    this.e(15, i0);
                }
            }
            else if (enumfacing == EnumFacing.WEST) {
                for (i0 = 0; i0 < 16; ++i0) {
                    this.e(0, i0);
                }
            }
            else if (enumfacing == EnumFacing.SOUTH) {
                for (i0 = 0; i0 < 16; ++i0) {
                    this.e(i0, 15);
                }
            }
            else if (enumfacing == EnumFacing.NORTH) {
                for (i0 = 0; i0 < 16; ++i0) {
                    this.e(i0, 0);
                }
            }

        }
    }

    private boolean e(int i0, int i1) {
        BlockPos blockpos = new BlockPos(this.a << 4, 0, this.b << 4);
        int i2 = this.g();
        boolean flag0 = false;
        boolean flag1 = false;

        int i3;
        BlockPos blockpos1;

        for (i3 = i2 + 16 - 1; i3 > 63 || i3 > 0 && !flag1; --i3) {
            blockpos1 = blockpos.a(i0, i3, i1);
            int i4 = this.b(blockpos1);

            if (i4 == 255 && i3 < 63) {
                flag1 = true;
            }

            if (!flag0 && i4 > 0) {
                flag0 = true;
            }
            else if (flag0 && i4 == 0 && !this.i.x(blockpos1)) {
                return false;
            }
        }

        for (; i3 > 0; --i3) {
            blockpos1 = blockpos.a(i0, i3, i1);
            if (this.a(blockpos1).p() > 0) {
                this.i.x(blockpos1);
            }
        }

        return true;
    }

    public boolean o() {
        return this.h;
    }

    public World p() {
        return this.i;
    }

    public int[] q() {
        return this.j;
    }

    public void a(int[] aint) {
        if (this.j.length != aint.length) {
            c.warn("Could not set level chunk heightmap, array length is " + aint.length + " instead of " + this.j.length);
        }
        else {
            for (int i0 = 0; i0 < this.j.length; ++i0) {
                this.j[i0] = aint[i0];
            }

        }
    }

    public Map r() {
        return this.l;
    }

    public ClassInheratanceMultiMap[] s() {
        return this.m;
    }

    public boolean t() {
        return this.n;
    }

    public void d(boolean flag0) {
        this.n = flag0;
    }

    public boolean u() {
        return this.o;
    }

    public void e(boolean flag0) {
        this.o = flag0;
    }

    public void f(boolean flag0) {
        this.q = flag0;
    }

    public void g(boolean flag0) {
        this.r = flag0;
    }

    public void b(long i0) {
        this.s = i0;
    }

    public int v() {
        return this.t;
    }

    public long w() {
        return this.u;
    }

    public void c(long i0) {
        this.u = i0;
    }

    public static enum EnumCreateEntityType {

        IMMEDIATE("IMMEDIATE", 0), QUEUED("QUEUED", 1), CHECK("CHECK", 2);

        private static final EnumCreateEntityType[] $VALUES = new EnumCreateEntityType[]{IMMEDIATE, QUEUED, CHECK};

        private EnumCreateEntityType(String p_i45642_1_, int p_i45642_2_) {
        }

    }

    // CanaryMod start
    public CanaryChunk getCanaryChunk() {
        return canaryChunk;
    }
    // CanaryMod end
}
