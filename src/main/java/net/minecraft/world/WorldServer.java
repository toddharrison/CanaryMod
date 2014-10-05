package net.minecraft.world;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.ListenableFuture;
import net.canarymod.Canary;
import net.canarymod.api.CanaryEntityTracker;
import net.canarymod.api.CanaryPlayerManager;
import net.canarymod.api.scoreboard.CanaryScoreboard;
import net.canarymod.hook.world.WeatherChangeHook;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEventData;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.*;
import net.minecraft.profiler.Profiler;
import net.minecraft.scoreboard.ScoreboardSaveData;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.village.VillageCollection;
import net.minecraft.village.VillageSiege;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraft.world.chunk.storage.IChunkLoader;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraft.world.gen.feature.WorldGeneratorBonusChest;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;


public class WorldServer extends World implements IThreadListener {

    private static final Logger a = LogManager.getLogger();
    private final MinecraftServer I;
    private final EntityTracker J;
    private final PlayerManager K;
    private final Set L = Sets.newHashSet();
    private final TreeSet M = new TreeSet();
    private final Map N = Maps.newHashMap();
    public ChunkProviderServer b;
    public boolean c;
    private boolean O;
    private int P;
    private final Teleporter Q;
    private final SpawnerAnimals R = new SpawnerAnimals();
    protected final VillageSiege d = new VillageSiege(this);
    private WorldServer.ServerBlockEventList[] S = new WorldServer.ServerBlockEventList[]{new WorldServer.ServerBlockEventList(null), new WorldServer.ServerBlockEventList(null)};
    private int T;
    private static final List U = Lists.newArrayList(new WeightedRandomChestContent[]{new WeightedRandomChestContent(Items.y, 0, 1, 3, 10), new WeightedRandomChestContent(Item.a(Blocks.f), 0, 1, 3, 10), new WeightedRandomChestContent(Item.a(Blocks.r), 0, 1, 3, 10), new WeightedRandomChestContent(Items.t, 0, 1, 1, 3), new WeightedRandomChestContent(Items.p, 0, 1, 1, 5), new WeightedRandomChestContent(Items.s, 0, 1, 1, 3), new WeightedRandomChestContent(Items.o, 0, 1, 1, 5), new WeightedRandomChestContent(Items.e, 0, 2, 3, 5), new WeightedRandomChestContent(Items.P, 0, 2, 3, 3), new WeightedRandomChestContent(Item.a(Blocks.s), 0, 1, 3, 10)});
    private List V = Lists.newArrayList();

    public WorldServer(MinecraftServer minecraftserver, ISaveHandler isavehandler, WorldInfo worldinfo, int i0, Profiler profiler) {
        // TODO: WorldProvider: Needs changing so it would get any WorldProvider. Might need to make a mapping/register
        super(isavehandler, worldinfo, WorldProvider.a(i0), profiler, false);
        this.I = minecraftserver;
        this.J = new EntityTracker(this);
        this.K = new PlayerManager(this);
        this.t.a(this);
        this.v = this.k();
        this.Q = new Teleporter(this);
        this.B();
        this.C();
        this.af().a(minecraftserver.aG());

        // CanaryMod: overide scoreboard data
        this.D = ((CanaryScoreboard) Canary.scoreboards().getScoreboard()).getHandle();
    }

    public World b() {
        this.z = new MapStorage(this.w);
        String s0 = VillageCollection.a(this.t);
        VillageCollection villagecollection = (VillageCollection) this.z.a(VillageCollection.class, s0);

        if (villagecollection == null) {
            this.A = new VillageCollection(this);
            this.z.a(s0, (WorldSavedData) this.A);
        }
        else {
            this.A = villagecollection;
            this.A.a((World) this);
        }

        this.C = new ServerScoreboard(this.I);
        ScoreboardSaveData scoreboardsavedata = (ScoreboardSaveData) this.z.a(ScoreboardSaveData.class, "scoreboard");

        if (scoreboardsavedata == null) {
            scoreboardsavedata = new ScoreboardSaveData();
            this.z.a("scoreboard", (WorldSavedData) scoreboardsavedata);
        }

        scoreboardsavedata.a(this.C);
        ((ServerScoreboard) this.C).a(scoreboardsavedata);
        this.af().c(this.x.C(), this.x.D());
        this.af().c(this.x.I());
        this.af().b(this.x.H());
        this.af().c(this.x.J());
        this.af().b(this.x.K());
        if (this.x.F() > 0L) {
            this.af().a(this.x.E(), this.x.G(), this.x.F());
        }
        else {
            this.af().a(this.x.E());
        }

        return this;
    }

    public void c() {
        super.c();
        if (this.P().t() && this.aa() != EnumDifficulty.HARD) {
            this.P().a(EnumDifficulty.HARD);
        }

        this.t.m().b();
        if (this.f()) {
            if (this.Q().b("doDaylightCycle")) {
                long i0 = this.x.g() + 24000L;

                this.x.c(i0 - i0 % 24000L);
            }

            this.e();
        }

        this.B.a("mobSpawner");
        if (this.Q().b("doMobSpawning") && this.x.u() != WorldType.g) {
            this.R.a(this, this.F, this.G, this.x.f() % 400L == 0L);
        }

        this.B.c("chunkSource");
        this.v.d();
        int i1 = this.a(1.0F);

        if (i1 != this.ab()) {
            this.b(i1);
        }

        this.x.b(this.x.f() + 1L);
        if (this.Q().b("doDaylightCycle")) {
            this.x.c(this.x.g() + 1L);
        }

        this.B.c("tickPending");
        this.a(false);
        this.B.c("tickBlocks");
        this.h();
        this.B.c("chunkMap");
        this.K.b();
        this.B.c("village");
        this.A.a();
        this.d.a();
        this.B.c("portalForcer");
        this.Q.a(this.K());
        this.B.b();
        this.ak();
    }

    public BiomeGenBase.SpawnListEntry a(EnumCreatureType enumcreaturetype, BlockPos blockpos) {
        List list = this.N().a(enumcreaturetype, blockpos);

        return list != null && !list.isEmpty() ? (BiomeGenBase.SpawnListEntry) WeightedRandom.a(this.s, list) : null;
    }

    public boolean a(EnumCreatureType enumcreaturetype, BiomeGenBase.SpawnListEntry biomegenbase_spawnlistentry, BlockPos blockpos) {
        List list = this.N().a(enumcreaturetype, blockpos);

        return list != null && !list.isEmpty() ? list.contains(biomegenbase_spawnlistentry) : false;
    }

    public void d() {
        this.O = false;
        if (!this.j.isEmpty()) {
            int i0 = 0;
            int i1 = 0;
            Iterator iterator = this.j.iterator();

            while (iterator.hasNext()) {
                EntityPlayer entityplayer = (EntityPlayer) iterator.next();

                if (entityplayer.v()) {
                    ++i0;
                }
                else if (entityplayer.bI()) {
                    ++i1;
                }
            }

            this.O = i1 > 0 && i1 >= this.j.size() - i0;
        }

    }

    protected void e() {
        this.O = false;
        Iterator iterator = this.j.iterator();

        while (iterator.hasNext()) {
            EntityPlayer entityplayer = (EntityPlayer) iterator.next();

            if (entityplayer.bI()) {
                entityplayer.a(false, false, true);
            }
        }

        this.ag();
    }

    private void ag() {
        // CanaryMod: WeatherChange
        WeatherChangeHook hook = (WeatherChangeHook) new WeatherChangeHook(getCanaryWorld(), false, false).call();
        if (!hook.isCanceled()) {
            this.x.g(0);
            this.x.b(false);
        }
        hook = (WeatherChangeHook) new WeatherChangeHook(getCanaryWorld(), false, true).call();
        if (!hook.isCanceled()) {
            this.x.f(0);
            this.x.a(false);
        }
        //
    }

    public boolean f() {
        if (this.O && !this.D) {
            Iterator iterator = this.j.iterator();

            EntityPlayer entityplayer;

            do {
                if (!iterator.hasNext()) {
                    return true;
                }

                entityplayer = (EntityPlayer) iterator.next();
            } while (!entityplayer.v() && entityplayer.ce());

            return false;
        }
        else {
            return false;
        }
    }

    protected void h() {
        super.h();
        if (this.x.u() == WorldType.g) {
            Iterator i11 = this.E.iterator();

            while (i11.hasNext()) {
                ChunkCoordIntPair i12 = (ChunkCoordIntPair) i11.next();

                this.a(i12.a, i12.b).b(false);
            }

        }
        else {
            int i0 = 0;
            int i1 = 0;

            for (Iterator iterator1 = this.E.iterator(); iterator1.hasNext(); this.B.b()) {
                ChunkCoordIntPair chunkcoordintpair1 = (ChunkCoordIntPair) iterator1.next();
                int i2 = chunkcoordintpair1.a * 16;
                int i3 = chunkcoordintpair1.b * 16;

                this.B.a("getChunk");
                Chunk chunk = this.a(chunkcoordintpair1.a, chunkcoordintpair1.b);

                this.a(i2, i3, chunk);
                this.B.c("tickChunk");
                chunk.b(false);
                this.B.c("thunder");
                int i4;
                BlockPos blockpos;

                if (this.s.nextInt(100000) == 0 && this.S() && this.R()) {
                    this.m = this.m * 3 + 1013904223;
                    i4 = this.m >> 2;
                    blockpos = this.a(new BlockPos(i2 + (i4 & 15), 0, i3 + (i4 >> 8 & 15)));
                    if (this.C(blockpos)) {
                        this.c(new EntityLightningBolt(this, (double) blockpos.n(), (double) blockpos.o(), (double) blockpos.p()));
                    }
                }

                this.B.c("iceandsnow");
                if (this.s.nextInt(16) == 0) {
                    this.m = this.m * 3 + 1013904223;
                    i4 = this.m >> 2;
                    blockpos = this.q(new BlockPos(i2 + (i4 & 15), 0, i3 + (i4 >> 8 & 15)));
                    BlockPos blockpos1 = blockpos.b();

                    if (this.w(blockpos1)) {
                        this.a(blockpos1, Blocks.aI.P());
                    }

                    if (this.S() && this.f(blockpos, true)) {
                        this.a(blockpos, Blocks.aH.P());
                    }

                    if (this.S() && this.b(blockpos1).e()) {
                        this.p(blockpos1).c().k(this, blockpos1);
                    }
                }

                this.B.c("tickBlocks");
                i4 = this.Q().c("randomTickSpeed");
                if (i4 > 0) {
                    ExtendedBlockStorage[] aextendedblockstorage = chunk.h();
                    int i5 = aextendedblockstorage.length;

                    for (int i6 = 0; i6 < i5; ++i6) {
                        ExtendedBlockStorage extendedblockstorage = aextendedblockstorage[i6];

                        if (extendedblockstorage != null && extendedblockstorage.b()) {
                            for (int i7 = 0; i7 < i4; ++i7) {
                                this.m = this.m * 3 + 1013904223;
                                int i8 = this.m >> 2;
                                int i9 = i8 & 15;
                                int i10 = i8 >> 8 & 15;
                                int i11 = i8 >> 16 & 15;

                                ++i1;
                                BlockPos blockpos2 = new BlockPos(i9 + i2, i11 + extendedblockstorage.d(), i10 + i3);
                                IBlockState iblockstate = extendedblockstorage.a(i9, i11, i10);
                                Block block = iblockstate.c();

                                if (block.w()) {
                                    ++i0;
                                    block.a((World) this, blockpos2, iblockstate, this.s);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    protected BlockPos a(BlockPos blockpos) {
        BlockPos blockpos1 = this.q(blockpos);
        AxisAlignedBB axisalignedbb = (new AxisAlignedBB(blockpos1, new BlockPos(blockpos1.n(), this.U(), blockpos1.p()))).b(3.0D, 3.0D, 3.0D);
        List list = this.a(EntityLivingBase.class, axisalignedbb, new Predicate() {

            public boolean a(EntityLivingBase blockpos) {
                return blockpos != null && blockpos.ai() && WorldServer.this.i(blockpos.c());
            }

            public boolean apply(Object p_apply_1_) {
                return this.a((EntityLivingBase) p_apply_1_);
            }
        });

        return !list.isEmpty() ? ((EntityLivingBase) list.get(this.s.nextInt(list.size()))).c() : blockpos1;
    }

    public boolean a(BlockPos blockpos, Block block) {
        NextTickListEntry nextticklistentry = new NextTickListEntry(blockpos, block);

        return this.V.contains(nextticklistentry);
    }

    public void a(BlockPos blockpos, Block block, int i0) {
        this.a(blockpos, block, i0, 0);
    }

    public void a(BlockPos blockpos, Block block, int i0, int i1) {
        NextTickListEntry nextticklistentry = new NextTickListEntry(blockpos, block);
        byte b0 = 0;

        if (this.e && block.r() != Material.a) {
            if (block.M()) {
                b0 = 8;
                if (this.a(nextticklistentry.a.a(-b0, -b0, -b0), nextticklistentry.a.a(b0, b0, b0))) {
                    IBlockState iblockstate = this.p(nextticklistentry.a);

                    if (iblockstate.c().r() != Material.a && iblockstate.c() == nextticklistentry.a()) {
                        iblockstate.c().b((World) this, nextticklistentry.a, iblockstate, this.s);
                    }
                }

                return;
            }

            i0 = 1;
        }

        if (this.a(blockpos.a(-b0, -b0, -b0), blockpos.a(b0, b0, b0))) {
            if (block.r() != Material.a) {
                nextticklistentry.a((long) i0 + this.x.f());
                nextticklistentry.a(i1);
            }

            if (!this.L.contains(nextticklistentry)) {
                this.L.add(nextticklistentry);
                this.M.add(nextticklistentry);
            }
        }

    }

    public void b(BlockPos blockpos, Block block, int i0, int i1) {
        NextTickListEntry nextticklistentry = new NextTickListEntry(blockpos, block);

        nextticklistentry.a(i1);
        if (block.r() != Material.a) {
            nextticklistentry.a((long) i0 + this.x.f());
        }

        if (!this.L.contains(nextticklistentry)) {
            this.L.add(nextticklistentry);
            this.M.add(nextticklistentry);
        }

    }

    public void i() {
        if (this.j.isEmpty()) {
            if (this.P++ >= 1200) {
                return;
            }
        }
        else {
            this.j();
        }

        super.i();
    }

    public void j() {
        this.P = 0;
    }

    public boolean a(boolean flag0) {
        if (this.x.u() == WorldType.g) {
            return false;
        }
        else {
            int i0 = this.M.size();

            if (i0 != this.L.size()) {
                throw new IllegalStateException("TickNextTick list out of synch");
            }
            else {
                if (i0 > 1000) {
                    i0 = 1000;
                }

                this.B.a("cleaning");

                NextTickListEntry nextticklistentry;

                for (int i1 = 0; i1 < i0; ++i1) {
                    nextticklistentry = (NextTickListEntry) this.M.first();
                    if (!flag0 && nextticklistentry.b > this.x.f()) {
                        break;
                    }

                    this.M.remove(nextticklistentry);
                    this.L.remove(nextticklistentry);
                    this.V.add(nextticklistentry);
                }

                this.B.b();
                this.B.a("ticking");
                Iterator iterator = this.V.iterator();

                while (iterator.hasNext()) {
                    nextticklistentry = (NextTickListEntry) iterator.next();
                    iterator.remove();
                    byte b0 = 0;

                    if (this.a(nextticklistentry.a.a(-b0, -b0, -b0), nextticklistentry.a.a(b0, b0, b0))) {
                        IBlockState iblockstate = this.p(nextticklistentry.a);

                        if (iblockstate.c().r() != Material.a && Block.a(iblockstate.c(), nextticklistentry.a())) {
                            try {
                                iblockstate.c().b((World) this, nextticklistentry.a, iblockstate, this.s);
                            }
                            catch (Throwable throwable) {
                                CrashReport crashreport = CrashReport.a(throwable, "Exception while ticking a block");
                                CrashReportCategory crashreportcategory = crashreport.a("Block being ticked");

                                CrashReportCategory.a(crashreportcategory, nextticklistentry.a, iblockstate);
                                throw new ReportedException(crashreport);
                            }
                        }
                    }
                    else {
                        this.a(nextticklistentry.a, nextticklistentry.a(), 0);
                    }
                }

                this.B.b();
                this.V.clear();
                return !this.M.isEmpty();
            }
        }
    }

    public List a(Chunk chunk, boolean flag0) {
        ChunkCoordIntPair chunkcoordintpair = chunk.j();
        int i0 = (chunkcoordintpair.a << 4) - 2;
        int i1 = i0 + 16 + 2;
        int i2 = (chunkcoordintpair.b << 4) - 2;
        int i3 = i2 + 16 + 2;

        return this.a(new StructureBoundingBox(i0, 0, i2, i1, 256, i3), flag0);
    }

    public List a(StructureBoundingBox structureboundingbox, boolean flag0) {
        ArrayList arraylist = null;

        for (int i0 = 0; i0 < 2; ++i0) {
            Iterator iterator;

            if (i0 == 0) {
                iterator = this.M.iterator();
            }
            else {
                iterator = this.V.iterator();
                if (!this.V.isEmpty()) {
                    a.debug("toBeTicked = " + this.V.size());
                }
            }

            while (iterator.hasNext()) {
                NextTickListEntry nextticklistentry = (NextTickListEntry) iterator.next();
                BlockPos blockpos = nextticklistentry.a;

                if (blockpos.n() >= structureboundingbox.a && blockpos.n() < structureboundingbox.d && blockpos.p() >= structureboundingbox.c && blockpos.p() < structureboundingbox.f) {
                    if (flag0) {
                        this.L.remove(nextticklistentry);
                        iterator.remove();
                    }

                    if (arraylist == null) {
                        arraylist = Lists.newArrayList();
                    }

                    arraylist.add(nextticklistentry);
                }
            }
        }

        return arraylist;
    }

    public void a(Entity entity, boolean flag0) {
        /* CanaryMod: Spawning checks per world, see World#canSpawn */
        if (!canSpawn(entity)) {
            entity.J();
        }

/* REMOVED
        if (!this.ai() && (entity instanceof EntityAnimal || entity instanceof EntityWaterMob)) {
            entity.J();
        }
*/
        super.a(entity, flag0);
    }

    private boolean ah() {
        return this.I.ag();
    }

    private boolean ai() {
        return this.I.af();
    }

    protected IChunkProvider k() {
        IChunkLoader ichunkloader = this.w.a(this.t);

        this.b = new ChunkProviderServer(this, ichunkloader, this.t.c());
        return this.b;
    }

    public List a(int i0, int i1, int i2, int i3, int i4, int i5) {
        ArrayList arraylist = Lists.newArrayList();

        for (int i6 = 0; i6 < this.h.size(); ++i6) {
            TileEntity tileentity = (TileEntity) this.h.get(i6);
            BlockPos blockpos = tileentity.v();

            if (blockpos.n() >= i0 && blockpos.o() >= i1 && blockpos.p() >= i2 && blockpos.n() < i3 && blockpos.o() < i4 && blockpos.p() < i5) {
                arraylist.add(tileentity);
            }
        }

        return arraylist;
    }

    public boolean a(EntityPlayer entityplayer, BlockPos blockpos) {
        return !this.I.a((World) this, blockpos, entityplayer) && this.af().a(blockpos);
    }

    public void a(WorldSettings worldsettings) {
        if (!this.x.w()) {
            try {
                this.b(worldsettings);
                if (this.x.u() == WorldType.g) {
                    this.aj();
                }

                super.a(worldsettings);
            }
            catch (Throwable throwable) {
                CrashReport crashreport = CrashReport.a(throwable, "Exception initializing level");

                try {
                    this.a(crashreport);
                }
                catch (Throwable throwable1) {
                    ;
                }

                throw new ReportedException(crashreport);
            }

            this.x.d(true);
        }
    }

    private void aj() {
        this.x.f(false);
        this.x.c(true);
        this.x.b(false);
        this.x.a(false);
        this.x.i(1000000000);
        this.x.c(6000L);
        this.x.a(WorldSettings.GameType.SPECTATOR);
        this.x.g(false);
        this.x.a(EnumDifficulty.PEACEFUL);
        this.x.e(true);
        this.Q().a("doDaylightCycle", "false");
    }

    private void b(WorldSettings worldsettings) {
        if (!this.t.e()) {
            this.x.a(BlockPos.a.b(this.t.i()));
        }
        else if (this.x.u() == WorldType.g) {
            this.x.a(BlockPos.a.a());
        }
        else {
            this.y = true;
            WorldChunkManager worldchunkmanager = this.t.m();
            List list = worldchunkmanager.a();
            Random random = new Random(this.J());
            BlockPos blockpos = worldchunkmanager.a(0, 0, 256, list, random);
            int i0 = 0;
            int i1 = this.t.i();
            int i2 = 0;

            if (blockpos != null) {
                i0 = blockpos.n();
                i2 = blockpos.p();
            }
            else {
                a.warn("Unable to find spawn biome");
            }

            int i3 = 0;

            while (!this.t.a(i0, i2)) {
                i0 += random.nextInt(64) - random.nextInt(64);
                i2 += random.nextInt(64) - random.nextInt(64);
                ++i3;
                if (i3 == 1000) {
                    break;
                }
            }

            this.x.a(new BlockPos(i0, i1, i2));
            this.y = false;
            if (worldsettings.c()) {
                this.l();
            }

        }
    }

    protected void l() {
        WorldGeneratorBonusChest worldgeneratorbonuschest = new WorldGeneratorBonusChest(U, 10);

        for (int i0 = 0; i0 < 10; ++i0) {
            int i1 = this.x.c() + this.s.nextInt(6) - this.s.nextInt(6);
            int i2 = this.x.e() + this.s.nextInt(6) - this.s.nextInt(6);
            BlockPos blockpos = this.r(new BlockPos(i1, 0, i2)).a();

            if (worldgeneratorbonuschest.b(this, this.s, blockpos)) {
                break;
            }
        }

    }

    public BlockPos m() {
        return this.t.h();
    }

    public void a(boolean flag0, IProgressUpdate iprogressupdate) throws MinecraftException {
        if (this.v.e()) {
            if (iprogressupdate != null) {
                iprogressupdate.a("Saving level");
            }

            this.a();
            if (iprogressupdate != null) {
                iprogressupdate.c("Saving chunks");
            }

            this.v.a(flag0, iprogressupdate);
            List list = this.b.a();
            Iterator iterator = list.iterator();

            while (iterator.hasNext()) {
                Chunk chunk = (Chunk) iterator.next();

                if (!this.K.a(chunk.a, chunk.b)) {
                    this.b.b(chunk.a, chunk.b);
                }
            }

        }
    }

    public void n() {
        if (this.v.e()) {
            this.v.c();
        }
    }

    protected void a() throws MinecraftException {
        this.I();
        this.x.a(this.af().h());
        this.x.d(this.af().f());
        this.x.c(this.af().g());
        this.x.e(this.af().m());
        this.x.f(this.af().n());
        this.x.j(this.af().q());
        this.x.k(this.af().p());
        this.x.b(this.af().j());
        this.x.e(this.af().i());
        this.w.a(this.x, this.I.an().u());
        this.z.a();
        // CanaryMod: save Scoreboard Data
        Canary.scoreboards().saveAllScoreboards();
    }

    protected void a(Entity entity) {
        super.a(entity);
        this.l.a(entity.F(), entity);
        this.N.put(entity.aJ(), entity);
        Entity[] aentity = entity.aC();

        if (aentity != null) {
            for (int i0 = 0; i0 < aentity.length; ++i0) {
                this.l.a(aentity[i0].F(), aentity[i0]);
            }
        }

    }

    protected void b(Entity entity) {
        super.b(entity);
        this.l.d(entity.F());
        this.N.remove(entity.aJ());
        Entity[] aentity = entity.aC();

        if (aentity != null) {
            for (int i0 = 0; i0 < aentity.length; ++i0) {
                this.l.d(aentity[i0].F());
            }
        }

    }

    public boolean c(Entity entity) {
        if (super.c(entity)) {
            this.I.an().a(entity.s, entity.t, entity.u, 512.0D, this.t.q(), new S2CPacketSpawnGlobalEntity(entity));
            return true;
        }
        else {
            return false;
        }
    }

    public void a(Entity entity, byte b0) {
        this.s().b(entity, new S19PacketEntityStatus(entity, b0));
    }

    public Explosion a(Entity entity, double d0, double d1, double d2, float f0, boolean flag0, boolean flag1) {
        Explosion explosion = new Explosion(this, entity, d0, d1, d2, f0, flag0, flag1);

        explosion.a();
        explosion.a(false);
        if (!flag1) {
            explosion.d();
        }

        Iterator iterator = this.j.iterator();

        while (iterator.hasNext()) {
            EntityPlayer entityplayer = (EntityPlayer) iterator.next();

            if (entityplayer.e(d0, d1, d2) < 4096.0D) {
                ((EntityPlayerMP) entityplayer).a.a((Packet) (new S27PacketExplosion(d0, d1, d2, f0, explosion.e(), (Vec3) explosion.b().get(entityplayer))));
            }
        }

        return explosion;
    }

    public void c(BlockPos blockpos, Block block, int i0, int i1) {
        BlockEventData blockeventdata = new BlockEventData(blockpos, block, i0, i1);
        Iterator iterator = this.S[this.T].iterator();

        BlockEventData blockeventdata1;

        do {
            if (!iterator.hasNext()) {
                this.S[this.T].add(blockeventdata);
                return;
            }

            blockeventdata1 = (BlockEventData) iterator.next();
        } while (!blockeventdata1.equals(blockeventdata));

    }

    private void ak() {
        while (!this.S[this.T].isEmpty()) {
            int i0 = this.T;

            this.T ^= 1;
            Iterator iterator = this.S[i0].iterator();

            while (iterator.hasNext()) {
                BlockEventData blockeventdata = (BlockEventData) iterator.next();

                if (this.a(blockeventdata)) {
                    this.I.an().a((double) blockeventdata.a().n(), (double) blockeventdata.a().o(), (double) blockeventdata.a().p(), 64.0D, this.t.q(), new S24PacketBlockAction(blockeventdata.a(), blockeventdata.d(), blockeventdata.b(), blockeventdata.c()));
                }
            }

            this.S[i0].clear();
        }

    }

    private boolean a(BlockEventData blockeventdata) {
        IBlockState iblockstate = this.p(blockeventdata.a());

        return iblockstate.c() == blockeventdata.d() ? iblockstate.c().a(this, blockeventdata.a(), iblockstate, blockeventdata.b(), blockeventdata.c()) : false;
    }

    public void o() {
        this.w.a();
    }

    protected void p() {
        boolean flag0 = this.S();

        super.p();
        if (this.o != this.p) {
            // CanaryMod: method change
            this.I.an().sendPacketToDimension((Packet) (new S2BPacketChangeGameState(7, this.p)), getCanaryWorld().getName(), this.t.q());
        }
        if (this.q != this.r) {
            // CanaryMod: method change
            this.I.an().sendPacketToDimension((Packet) (new S2BPacketChangeGameState(8, this.r)), getCanaryWorld().getName(), this.t.q());
        }
        if (flag0 != this.S()) {
            if (flag0) {
                this.I.an().a((Packet) (new S2BPacketChangeGameState(2, 0.0F)));
            }
            else {
                this.I.an().a((Packet) (new S2BPacketChangeGameState(1, 0.0F)));
            }

            this.I.an().a((Packet) (new S2BPacketChangeGameState(7, this.p)));
            this.I.an().a((Packet) (new S2BPacketChangeGameState(8, this.r)));
        }
    }

    protected int q() {
        return this.I.an().t();
    }

    public MinecraftServer r() {
        return this.I;
    }

    public EntityTracker s() {
        return this.J;
    }

    public PlayerManager t() {
        return this.K;
    }

    public Teleporter u() {
        return this.Q;
    }

    public void a(EnumParticleTypes enumparticletypes, double d0, double d1, double d2, int i0, double d3, double d4, double d5, double d6, int... aint) {
        this.a(enumparticletypes, false, d0, d1, d2, i0, d3, d4, d5, d6, aint);
    }

    public void a(EnumParticleTypes enumparticletypes, boolean flag0, double d0, double d1, double d2, int i0, double d3, double d4, double d5, double d6, int... aint) {
        S2APacketParticles s2apacketparticles = new S2APacketParticles(enumparticletypes, flag0, (float) d0, (float) d1, (float) d2, (float) d3, (float) d4, (float) d5, (float) d6, i0, aint);

        for (int i1 = 0; i1 < this.j.size(); ++i1) {
            EntityPlayerMP entityplayermp = (EntityPlayerMP) this.j.get(i1);
            BlockPos blockpos = entityplayermp.c();
            double d7 = blockpos.c(d0, d1, d2);

            if (d7 <= 256.0D || flag0 && d7 <= 65536.0D) {
                entityplayermp.a.a((Packet) s2apacketparticles);
            }
        }

    }

    public Entity a(UUID uuid) {
        return (Entity) this.N.get(uuid);
    }

    public ListenableFuture a(Runnable runnable) {
        return this.I.a(runnable);
    }

    public boolean aH() {
        return this.I.aH();
    }

    static class ServerBlockEventList extends ArrayList {

        private ServerBlockEventList() {
        }

        ServerBlockEventList(Object object) {
            this();
        }
    }

    public CanaryEntityTracker getEntityTracker() {
        return K.getCanaryEntityTracker();
    }

    /**
     * Get the canary player manager wrapper for this dimension
     *
     * @return
     */
    public CanaryPlayerManager getPlayerManager() {
        return L.getPlayerManager();
    }
}
