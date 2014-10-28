package net.minecraft.world.storage;


import net.canarymod.Canary;
import net.canarymod.api.world.CanaryWorld;
import net.canarymod.api.world.DimensionType;
import net.canarymod.api.world.position.Location;
import net.canarymod.hook.world.TimeChangeHook;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;

import java.util.concurrent.Callable;


public class WorldInfo {

    public static final EnumDifficulty a = EnumDifficulty.NORMAL;
    private long b;
    private WorldType c;
    private String d;
    private int e;
    private int f;
    private int g;
    private long h;
    private long i;
    private long j;
    private long k;
    private NBTTagCompound l;
    private int m;
    private String n;
    private int o;
    private int p;
    private boolean q;
    private int r;
    private boolean s;
    private int t;
    private WorldSettings.GameType u;
    private boolean v;
    private boolean w;
    private boolean x;
    private boolean y;
    private EnumDifficulty z;
    private boolean A;
    private double B;
    private double C;
    private double D;
    private long E;
    private double F;
    private double G;
    private double H;
    private int I;
    private int J;
    private GameRules K;

    // CanaryMod: Add Rotation/Pitch for Spawn Point
    private float rotX = 0.0F;
    private float rotY = 0.0F;
    // CanaryMod: end

    protected WorldInfo() {
        this.c = WorldType.b;
        this.d = "";
        this.B = 0.0D;
        this.C = 0.0D;
        this.D = 6.0E7D;
        this.E = 0L;
        this.F = 0.0D;
        this.G = 5.0D;
        this.H = 0.2D;
        this.I = 5;
        this.J = 15;
        this.K = new GameRules();
    }

    public WorldInfo(NBTTagCompound nbttagcompound) {
        this.c = WorldType.b;
        this.d = "";
        this.B = 0.0D;
        this.C = 0.0D;
        this.D = 6.0E7D;
        this.E = 0L;
        this.F = 0.0D;
        this.G = 5.0D;
        this.H = 0.2D;
        this.I = 5;
        this.J = 15;
        this.K = new GameRules();
        this.b = nbttagcompound.g("RandomSeed");
        if (nbttagcompound.b("generatorName", 8)) {
            String s0 = nbttagcompound.j("generatorName");

            this.c = WorldType.a(s0);
            if (this.c == null) {
                this.c = WorldType.b;
            }
            else if (this.c.f()) {
                int i0 = 0;

                if (nbttagcompound.b("generatorVersion", 99)) {
                    i0 = nbttagcompound.f("generatorVersion");
                }

                this.c = this.c.a(i0);
            }

            if (nbttagcompound.b("generatorOptions", 8)) {
                this.d = nbttagcompound.j("generatorOptions");
            }
        }

        this.u = WorldSettings.GameType.a(nbttagcompound.f("GameType"));
        if (nbttagcompound.b("MapFeatures", 99)) {
            this.v = nbttagcompound.n("MapFeatures");
        }
        else {
            this.v = true;
        }

        this.e = nbttagcompound.f("SpawnX");
        this.f = nbttagcompound.f("SpawnY");
        this.g = nbttagcompound.f("SpawnZ");

        // CanaryMod: Rotation/Pitch for SpawnPoint
        if(nbttagcompound.c("SpawnRotX")){
            this.rotX = nbttagcompound.h("SpawnRotX");
        }
        if(nbttagcompound.c("SpawnRotY")){
            this.rotY = nbttagcompound.h("SpawnRotY");
        }

        if(nbttagcompound.c("Dimension")){
            this.m = nbttagcompound.f("Dimension");
        }
        // CanaryMod: end

        this.h = nbttagcompound.g("Time");
        if (nbttagcompound.b("DayTime", 99)) {
            this.i = nbttagcompound.g("DayTime");
        }
        else {
            this.i = this.h;
        }

        this.j = nbttagcompound.g("LastPlayed");
        this.k = nbttagcompound.g("SizeOnDisk");
        this.n = nbttagcompound.j("LevelName");
        this.o = nbttagcompound.f("version");
        this.p = nbttagcompound.f("clearWeatherTime");
        this.r = nbttagcompound.f("rainTime");
        this.q = nbttagcompound.n("raining");
        this.t = nbttagcompound.f("thunderTime");
        this.s = nbttagcompound.n("thundering");
        this.w = nbttagcompound.n("hardcore");
        if (nbttagcompound.b("initialized", 99)) {
            this.y = nbttagcompound.n("initialized");
        }
        else {
            this.y = true;
        }

        if (nbttagcompound.b("allowCommands", 99)) {
            this.x = nbttagcompound.n("allowCommands");
        }
        else {
            this.x = this.u == WorldSettings.GameType.CREATIVE;
        }

        if (nbttagcompound.b("Player", 10)) {
            this.l = nbttagcompound.m("Player");
            this.m = this.l.f("Dimension");
        }

        if (nbttagcompound.b("GameRules", 10)) {
            this.K.a(nbttagcompound.m("GameRules"));
        }

        if (nbttagcompound.b("Difficulty", 99)) {
            this.z = EnumDifficulty.a(nbttagcompound.d("Difficulty"));
        }

        if (nbttagcompound.b("DifficultyLocked", 1)) {
            this.A = nbttagcompound.n("DifficultyLocked");
        }

        if (nbttagcompound.b("BorderCenterX", 99)) {
            this.B = nbttagcompound.i("BorderCenterX");
        }

        if (nbttagcompound.b("BorderCenterZ", 99)) {
            this.C = nbttagcompound.i("BorderCenterZ");
        }

        if (nbttagcompound.b("BorderSize", 99)) {
            this.D = nbttagcompound.i("BorderSize");
        }

        if (nbttagcompound.b("BorderSizeLerpTime", 99)) {
            this.E = nbttagcompound.g("BorderSizeLerpTime");
        }

        if (nbttagcompound.b("BorderSizeLerpTarget", 99)) {
            this.F = nbttagcompound.i("BorderSizeLerpTarget");
        }

        if (nbttagcompound.b("BorderSafeZone", 99)) {
            this.G = nbttagcompound.i("BorderSafeZone");
        }

        if (nbttagcompound.b("BorderDamagePerBlock", 99)) {
            this.H = nbttagcompound.i("BorderDamagePerBlock");
        }

        if (nbttagcompound.b("BorderWarningBlocks", 99)) {
            this.I = nbttagcompound.f("BorderWarningBlocks");
        }

        if (nbttagcompound.b("BorderWarningTime", 99)) {
            this.J = nbttagcompound.f("BorderWarningTime");
        }

    }

    public WorldInfo(WorldSettings worldsettings, String s0, DimensionType type) {
        this.c = WorldType.b;
        this.d = "";
        this.B = 0.0D;
        this.C = 0.0D;
        this.D = 6.0E7D;
        this.E = 0L;
        this.F = 0.0D;
        this.G = 5.0D;
        this.H = 0.2D;
        this.I = 5;
        this.J = 15;
        this.K = new GameRules();
        this.a(worldsettings);
        this.n = s0;
        this.z = a;
        this.y = false;
        this.m = type.getId();
    }

    public void a(WorldSettings worldsettings) {
        this.b = worldsettings.d();
        this.u = worldsettings.e();
        this.v = worldsettings.g();
        this.w = worldsettings.f();
        this.c = worldsettings.h();
        this.d = worldsettings.j();
        this.x = worldsettings.i();
    }

    public WorldInfo(WorldInfo worldinfo) {
        this.c = WorldType.b;
        this.d = "";
        this.B = 0.0D;
        this.C = 0.0D;
        this.D = 6.0E7D;
        this.E = 0L;
        this.F = 0.0D;
        this.G = 5.0D;
        this.H = 0.2D;
        this.I = 5;
        this.J = 15;
        this.K = new GameRules();
        this.b = worldinfo.b;
        this.c = worldinfo.c;
        this.d = worldinfo.d;
        this.u = worldinfo.u;
        this.v = worldinfo.v;
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
        this.o = worldinfo.o;
        this.r = worldinfo.r;
        this.q = worldinfo.q;
        this.t = worldinfo.t;
        this.s = worldinfo.s;
        this.w = worldinfo.w;
        this.x = worldinfo.x;
        this.y = worldinfo.y;
        this.K = worldinfo.K;
        this.z = worldinfo.z;
        this.A = worldinfo.A;
        this.B = worldinfo.B;
        this.C = worldinfo.C;
        this.D = worldinfo.D;
        this.E = worldinfo.E;
        this.F = worldinfo.F;
        this.G = worldinfo.G;
        this.H = worldinfo.H;
        this.J = worldinfo.J;
        this.I = worldinfo.I;

        // CanaryMod: Spawn Rotation/Pitch
        this.rotX = worldinfo.rotX;
        this.rotY = worldinfo.rotY;
        // CanaryMod: end
    }

    public NBTTagCompound a() {
        NBTTagCompound nbttagcompound = new NBTTagCompound();

        this.a(nbttagcompound, this.l);
        return nbttagcompound;
    }

    public NBTTagCompound a(NBTTagCompound nbttagcompound) {
        NBTTagCompound nbttagcompound1 = new NBTTagCompound();

        this.a(nbttagcompound1, nbttagcompound);
        return nbttagcompound1;
    }

    private void a(NBTTagCompound nbttagcompound, NBTTagCompound nbttagcompound1) {
        nbttagcompound.a("RandomSeed", this.b);
        nbttagcompound.a("generatorName", this.c.a());
        nbttagcompound.a("generatorVersion", this.c.d());
        nbttagcompound.a("generatorOptions", this.d);
        nbttagcompound.a("GameType", this.u.a());
        nbttagcompound.a("MapFeatures", this.v);
        nbttagcompound.a("SpawnX", this.e);
        nbttagcompound.a("SpawnY", this.f);
        nbttagcompound.a("SpawnZ", this.g);

        // CanaryMod: Store Spawn Rotation/Pitch
        nbttagcompound.a("SpawnRotX", this.rotX);
        nbttagcompound.a("SpawnRotY", this.rotY);
        nbttagcompound.a("Dimension", this.m);
        // CanaryMod: end

        nbttagcompound.a("Time", this.h);
        nbttagcompound.a("DayTime", this.i);
        nbttagcompound.a("SizeOnDisk", this.k);
        nbttagcompound.a("LastPlayed", MinecraftServer.ax());
        nbttagcompound.a("LevelName", this.n);
        nbttagcompound.a("version", this.o);
        nbttagcompound.a("clearWeatherTime", this.p);
        nbttagcompound.a("rainTime", this.r);
        nbttagcompound.a("raining", this.q);
        nbttagcompound.a("thunderTime", this.t);
        nbttagcompound.a("thundering", this.s);
        nbttagcompound.a("hardcore", this.w);
        nbttagcompound.a("allowCommands", this.x);
        nbttagcompound.a("initialized", this.y);
        nbttagcompound.a("BorderCenterX", this.B);
        nbttagcompound.a("BorderCenterZ", this.C);
        nbttagcompound.a("BorderSize", this.D);
        nbttagcompound.a("BorderSizeLerpTime", this.E);
        nbttagcompound.a("BorderSafeZone", this.G);
        nbttagcompound.a("BorderDamagePerBlock", this.H);
        nbttagcompound.a("BorderSizeLerpTarget", this.F);
        nbttagcompound.a("BorderWarningBlocks", (double) this.I);
        nbttagcompound.a("BorderWarningTime", (double) this.J);
        if (this.z != null) {
            nbttagcompound.a("Difficulty", (byte) this.z.a());
        }

        nbttagcompound.a("DifficultyLocked", this.A);
        nbttagcompound.a("GameRules", (NBTBase) this.K.a());
        if (nbttagcompound1 != null) {
            nbttagcompound.a("Player", (NBTBase) nbttagcompound1);
        }

    }

    public long b() {
        return this.b;
    }

    public int c() {
        return this.e;
    }

    public int d() {
        return this.f;
    }

    public int e() {
        return this.g;
    }

    public long f() {
        return this.h;
    }

    public long g() {
        return this.i;
    }

    public NBTTagCompound i() {
        return this.l;
    }

    public void b(long i0) {
        this.h = i0;
    }

    public void c(long i0) {
        // CanaryMod: TimeChangeHook
        CanaryWorld world = (CanaryWorld) Canary.getServer().getWorldManager().getWorld(this.n, net.canarymod.api.world.DimensionType.fromId(this.m), false);
        TimeChangeHook hook = (TimeChangeHook) new TimeChangeHook(world, i0).call();
        if (hook.isCanceled()) {
            return;
        }
        //
        this.i = i0;
    }

    public void a(BlockPos blockpos) {
        this.e = blockpos.n();
        this.f = blockpos.o();
        this.g = blockpos.p();
    }

    public String k() {
        return this.n;
    }

    public void a(String s0) {
        this.n = s0;
    }

    public int l() {
        return this.o;
    }

    public void e(int i0) {
        this.o = i0;
    }

    public int A() {
        return this.p;
    }

    public void i(int i0) {
        this.p = i0;
    }

    public boolean n() {
        return this.s;
    }

    public void a(boolean flag0) {
        this.s = flag0;
    }

    public int o() {
        return this.t;
    }

    public void f(int i0) {
        this.t = i0;
    }

    public boolean p() {
        return this.q;
    }

    public void b(boolean flag0) {
        this.q = flag0;
    }

    public int q() {
        return this.r;
    }

    public void g(int i0) {
        this.r = i0;
    }

    public WorldSettings.GameType r() {
        return this.u;
    }

    public boolean s() {
        return this.v;
    }

    public void f(boolean flag0) {
        this.v = flag0;
    }

    public void a(WorldSettings.GameType worldsettings_gametype) {
        this.u = worldsettings_gametype;
    }

    public boolean t() {
        return this.w;
    }

    public void g(boolean flag0) {
        this.w = flag0;
    }

    public WorldType u() {
        return this.c;
    }

    public void a(WorldType worldtype) {
        this.c = worldtype;
    }

    public String B() {
        return this.d;
    }

    public boolean v() {
        return this.x;
    }

    public void c(boolean flag0) {
        this.x = flag0;
    }

    public boolean w() {
        return this.y;
    }

    public void d(boolean flag0) {
        this.y = flag0;
    }

    public GameRules x() {
        return this.K;
    }

    public double C() {
        return this.B;
    }

    public double D() {
        return this.C;
    }

    public double E() {
        return this.D;
    }

    public void a(double d0) {
        this.D = d0;
    }

    public long F() {
        return this.E;
    }

    public void e(long i0) {
        this.E = i0;
    }

    public double G() {
        return this.F;
    }

    public void b(double d0) {
        this.F = d0;
    }

    public void c(double d0) {
        this.C = d0;
    }

    public void d(double d0) {
        this.B = d0;
    }

    public double H() {
        return this.G;
    }

    public void e(double d0) {
        this.G = d0;
    }

    public double I() {
        return this.H;
    }

    public void f(double d0) {
        this.H = d0;
    }

    public int J() {
        return this.I;
    }

    public int K() {
        return this.J;
    }

    public void j(int i0) {
        this.I = i0;
    }

    public void k(int i0) {
        this.J = i0;
    }

    public EnumDifficulty y() {
        return this.z;
    }

    public void a(EnumDifficulty enumdifficulty) {
        this.z = enumdifficulty;
    }

    public boolean z() {
        return this.A;
    }

    public void e(boolean flag0) {
        this.A = flag0;
    }

    public void a(CrashReportCategory crashreportcategory) {
        crashreportcategory.a("Level seed", new Callable() {

            public String call() {
                return String.valueOf(WorldInfo.this.b());
            }
        });
        crashreportcategory.a("Level generator", new Callable() {

            public String call() {
                return String.format("ID %02d - %s, ver %d. Features enabled: %b", new Object[]{Integer.valueOf(WorldInfo.this.c.g()), WorldInfo.this.c.a(), Integer.valueOf(WorldInfo.this.c.d()), Boolean.valueOf(WorldInfo.this.v)});
            }
        });
        crashreportcategory.a("Level generator options", new Callable() {

            public String call() {
                return WorldInfo.this.d;
            }
        });
        crashreportcategory.a("Level spawn location", new Callable() {

            public String call() {
                return CrashReportCategory.a((double) WorldInfo.this.e, (double) WorldInfo.this.f, (double) WorldInfo.this.g);
            }
        });
        crashreportcategory.a("Level time", new Callable() {

            public String call() {
                return String.format("%d game time, %d day time", new Object[]{Long.valueOf(WorldInfo.this.h), Long.valueOf(WorldInfo.this.i)});
            }
        });
        crashreportcategory.a("Level dimension", new Callable() {

            public String call() {
                return String.valueOf(WorldInfo.this.m);
            }
        });
        crashreportcategory.a("Level storage version", new Callable() {

            public String call() {
                String s0 = "Unknown?";

                try {
                    switch (WorldInfo.this.o) {
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

                return String.format("0x%05X - %s", new Object[]{Integer.valueOf(WorldInfo.this.o), s0});
            }
        });
        crashreportcategory.a("Level weather", new Callable() {

            public String call() {
                return String.format("Rain time: %d (now: %b), thunder time: %d (now: %b)", new Object[]{Integer.valueOf(WorldInfo.this.r), Boolean.valueOf(WorldInfo.this.q), Integer.valueOf(WorldInfo.this.t), Boolean.valueOf(WorldInfo.this.s)});
            }
        });
        crashreportcategory.a("Level game mode", new Callable() {

            public String call() {
                return String.format("Game mode: %s (ID %d). Hardcore: %b. Cheats: %b", new Object[]{WorldInfo.this.u.b(), Integer.valueOf(WorldInfo.this.u.a()), Boolean.valueOf(WorldInfo.this.w), Boolean.valueOf(WorldInfo.this.x)});
            }
        });
    }

    // CanaryMod Start
    public void setSpawn(Location location) {
        this.e = location.getBlockX();
        this.f = location.getBlockY();
        this.g = location.getBlockZ();
        this.rotX = location.getRotation();
        this.rotY = location.getPitch();
    }

    public Location getSpawnLocation(){
        Location spawn = new Location(this.e, this.f, this.g);
        spawn.setPitch(rotX);
        spawn.setRotation(rotY);
        spawn.setType(DimensionType.fromId(this.m)); // Need to set Dimension
        spawn.setWorldName(this.n); // Need to set World
        return spawn;
    }

    /**
     * Return the dimension ID defined via this WorldInfo
     * @return the dim ID
     */
    public int getDimId() {
        return this.m;
    }
    // CanaryMod End
}
