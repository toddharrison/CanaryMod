package net.minecraft.world.storage;


import net.canarymod.Canary;
import net.canarymod.api.world.CanaryWorld;
import net.canarymod.hook.world.TimeChangeHook;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.GameRules;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;

import java.util.concurrent.Callable;


public class WorldInfo {

    private long a;
    private WorldType b;
    private String c;
    private int d;
    private int e;
    private int f;
    private long g;
    private long h;
    private long i;
    private long j;
    private NBTTagCompound k;
    private int l;
    private String m;
    private int n;
    private boolean o;
    private int p;
    private boolean q;
    private int r;
    private WorldSettings.GameType s;
    private boolean t;
    private boolean u;
    private boolean v;
    private boolean w;
    private GameRules x;

    protected WorldInfo() {
        this.b = WorldType.b;
        this.c = "";
        this.x = new GameRules();
    }

    public WorldInfo(NBTTagCompound nbttagcompound) {
        this.b = WorldType.b;
        this.c = "";
        this.x = new GameRules();
        this.a = nbttagcompound.g("RandomSeed");
        if (nbttagcompound.b("generatorName", 8)) {
            String s0 = nbttagcompound.j("generatorName");

            this.b = WorldType.a(s0);
            if (this.b == null) {
                this.b = WorldType.b;
            }
            else if (this.b.f()) {
                int i0 = 0;

                if (nbttagcompound.b("generatorVersion", 99)) {
                    i0 = nbttagcompound.f("generatorVersion");
                }

                this.b = this.b.a(i0);
            }

            if (nbttagcompound.b("generatorOptions", 8)) {
                this.c = nbttagcompound.j("generatorOptions");
            }
        }

        this.s = WorldSettings.GameType.a(nbttagcompound.f("GameType"));
        if (nbttagcompound.b("MapFeatures", 99)) {
            this.t = nbttagcompound.n("MapFeatures");
        }
        else {
            this.t = true;
        }

        this.d = nbttagcompound.f("SpawnX");
        this.e = nbttagcompound.f("SpawnY");
        this.f = nbttagcompound.f("SpawnZ");
        this.g = nbttagcompound.g("Time");
        if (nbttagcompound.b("DayTime", 99)) {
            this.h = nbttagcompound.g("DayTime");
        }
        else {
            this.h = this.g;
        }

        this.i = nbttagcompound.g("LastPlayed");
        this.j = nbttagcompound.g("SizeOnDisk");
        this.m = nbttagcompound.j("LevelName");
        this.n = nbttagcompound.f("version");
        this.p = nbttagcompound.f("rainTime");
        this.o = nbttagcompound.n("raining");
        this.r = nbttagcompound.f("thunderTime");
        this.q = nbttagcompound.n("thundering");
        this.u = nbttagcompound.n("hardcore");
        if (nbttagcompound.b("initialized", 99)) {
            this.w = nbttagcompound.n("initialized");
        }
        else {
            this.w = true;
        }

        if (nbttagcompound.b("allowCommands", 99)) {
            this.v = nbttagcompound.n("allowCommands");
        }
        else {
            this.v = this.s == WorldSettings.GameType.CREATIVE;
        }

        if (nbttagcompound.b("Player", 10)) {
            this.k = nbttagcompound.m("Player");
            this.l = this.k.f("Dimension");
        }

        if (nbttagcompound.b("GameRules", 10)) {
            this.x.a(nbttagcompound.m("GameRules"));
        }

    }

    public WorldInfo(WorldSettings worldsettings, String s0) {
        this.b = WorldType.b;
        this.c = "";
        this.x = new GameRules();
        this.a = worldsettings.d();
        this.s = worldsettings.e();
        this.t = worldsettings.g();
        this.m = s0;
        this.u = worldsettings.f();
        this.b = worldsettings.h();
        this.c = worldsettings.j();
        this.v = worldsettings.i();
        this.w = false;
    }

    public WorldInfo(WorldInfo worldinfo) {
        this.b = WorldType.b;
        this.c = "";
        this.x = new GameRules();
        this.a = worldinfo.a;
        this.b = worldinfo.b;
        this.c = worldinfo.c;
        this.s = worldinfo.s;
        this.t = worldinfo.t;
        this.d = worldinfo.d;
        this.e = worldinfo.e;
        this.f = worldinfo.f;
        this.g = worldinfo.g;
        this.h = worldinfo.h;
        this.i = worldinfo.i;
        this.j = worldinfo.j;
        this.k = worldinfo.k;
        this.l = worldinfo.l;
        this.m = worldinfo.m;
        this.n = worldinfo.n;
        this.p = worldinfo.p;
        this.o = worldinfo.o;
        this.r = worldinfo.r;
        this.q = worldinfo.q;
        this.u = worldinfo.u;
        this.v = worldinfo.v;
        this.w = worldinfo.w;
        this.x = worldinfo.x;
    }

    public NBTTagCompound a() {
        NBTTagCompound nbttagcompound = new NBTTagCompound();

        this.a(nbttagcompound, this.k);
        return nbttagcompound;
    }

    public NBTTagCompound a(NBTTagCompound nbttagcompound) {
        NBTTagCompound nbttagcompound1 = new NBTTagCompound();

        this.a(nbttagcompound1, nbttagcompound);
        return nbttagcompound1;
    }

    private void a(NBTTagCompound nbttagcompound, NBTTagCompound nbttagcompound1) {
        nbttagcompound.a("RandomSeed", this.a);
        nbttagcompound.a("generatorName", this.b.a());
        nbttagcompound.a("generatorVersion", this.b.d());
        nbttagcompound.a("generatorOptions", this.c);
        nbttagcompound.a("GameType", this.s.a());
        nbttagcompound.a("MapFeatures", this.t);
        nbttagcompound.a("SpawnX", this.d);
        nbttagcompound.a("SpawnY", this.e);
        nbttagcompound.a("SpawnZ", this.f);
        nbttagcompound.a("Time", this.g);
        nbttagcompound.a("DayTime", this.h);
        nbttagcompound.a("SizeOnDisk", this.j);
        nbttagcompound.a("LastPlayed", MinecraftServer.ap());
        nbttagcompound.a("LevelName", this.m);
        nbttagcompound.a("version", this.n);
        nbttagcompound.a("rainTime", this.p);
        nbttagcompound.a("raining", this.o);
        nbttagcompound.a("thunderTime", this.r);
        nbttagcompound.a("thundering", this.q);
        nbttagcompound.a("hardcore", this.u);
        nbttagcompound.a("allowCommands", this.v);
        nbttagcompound.a("initialized", this.w);
        nbttagcompound.a("GameRules", (NBTBase) this.x.a());
        if (nbttagcompound1 != null) {
            nbttagcompound.a("Player", (NBTBase) nbttagcompound1);
        }

    }

    public long b() {
        return this.a;
    }

    public int c() {
        return this.d;
    }

    public int d() {
        return this.e;
    }

    public int e() {
        return this.f;
    }

    public long f() {
        return this.g;
    }

    public long g() {
        return this.h;
    }

    public NBTTagCompound i() {
        return this.k;
    }

    public int j() {
        return this.l;
    }

    public void b(long i0) {
        this.g = i0;
    }

    public void c(long i0) {
        // CanaryMod: TimeChangeHook
        CanaryWorld world = (CanaryWorld) Canary.getServer().getWorldManager().getWorld(this.m, net.canarymod.api.world.DimensionType.fromId(this.l), false);
        TimeChangeHook hook = (TimeChangeHook) new TimeChangeHook(world, i0).call();
        if (hook.isCanceled()) {
            return;
        }
        //
        this.h = i0;
    }

    public void a(int i0, int i1, int i2) {
        this.d = i0;
        this.e = i1;
        this.f = i2;
    }

    public String k() {
        return this.m;
    }

    public void a(String s0) {
        this.m = s0;
    }

    public int l() {
        return this.n;
    }

    public void e(int i0) {
        this.n = i0;
    }

    public boolean n() {
        return this.q;
    }

    public void a(boolean flag0) {
        this.q = flag0;
    }

    public int o() {
        return this.r;
    }

    public void f(int i0) {
        this.r = i0;
    }

    public boolean p() {
        return this.o;
    }

    public void b(boolean flag0) {
        this.o = flag0;
    }

    public int q() {
        return this.p;
    }

    public void g(int i0) {
        this.p = i0;
    }

    public WorldSettings.GameType r() {
        return this.s;
    }

    public boolean s() {
        return this.t;
    }

    public void a(WorldSettings.GameType worldsettings_gametype) {
        this.s = worldsettings_gametype;
    }

    public boolean t() {
        return this.u;
    }

    public WorldType u() {
        return this.b;
    }

    public void a(WorldType worldtype) {
        this.b = worldtype;
    }

    public String y() {
        return this.c;
    }

    public boolean v() {
        return this.v;
    }

    public boolean w() {
        return this.w;
    }

    public void d(boolean flag0) {
        this.w = flag0;
    }

    public GameRules x() {
        return this.x;
    }

    public void a(CrashReportCategory crashreportcategory) {
        crashreportcategory.a("Level seed", new Callable() {

            public String call() {
                return String.valueOf(WorldInfo.this.b());
            }
        });
        crashreportcategory.a("Level generator", new Callable() {

            public String call() {
                return String.format("ID %02d - %s, ver %d. Features enabled: %b", new Object[]{Integer.valueOf(WorldInfo.this.b.g()), WorldInfo.this.b.a(), Integer.valueOf(WorldInfo.this.b.d()), Boolean.valueOf(WorldInfo.this.t)});
            }
        });
        crashreportcategory.a("Level generator options", new Callable() {

            public String call() {
                return WorldInfo.this.c;
            }
        });
        crashreportcategory.a("Level spawn location", new Callable() {

            public String call() {
                return CrashReportCategory.a(WorldInfo.this.d, WorldInfo.this.e, WorldInfo.this.f);
            }
        });
        crashreportcategory.a("Level time", new Callable() {

            public String call() {
                return String.format("%d game time, %d day time", new Object[]{Long.valueOf(WorldInfo.this.g), Long.valueOf(WorldInfo.this.h)});
            }
        });
        crashreportcategory.a("Level dimension", new Callable() {

            public String call() {
                return String.valueOf(WorldInfo.this.l);
            }
        });
        crashreportcategory.a("Level storage version", new Callable() {

            public String call() {
                String s0 = "Unknown?";

                try {
                    switch (WorldInfo.this.n) {
                        case 19132:
                            s0 = "McRegion";
                            break;

                        case 19133:
                            s0 = "Anvil";
                    }
                }
                catch (Throwable throwable) {
                    ;
                }

                return String.format("0x%05X - %s", new Object[]{Integer.valueOf(WorldInfo.this.n), s0});
            }
        });
        crashreportcategory.a("Level weather", new Callable() {

            public String call() {
                return String.format("Rain time: %d (now: %b), thunder time: %d (now: %b)", new Object[]{Integer.valueOf(WorldInfo.this.p), Boolean.valueOf(WorldInfo.this.o), Integer.valueOf(WorldInfo.this.r), Boolean.valueOf(WorldInfo.this.q)});
            }
        });
        crashreportcategory.a("Level game mode", new Callable() {

            public String call() {
                return String.format("Game mode: %s (ID %d). Hardcore: %b. Cheats: %b", new Object[]{WorldInfo.this.s.b(), Integer.valueOf(WorldInfo.this.s.a()), Boolean.valueOf(WorldInfo.this.u), Boolean.valueOf(WorldInfo.this.v)});
            }
        });
    }
}
