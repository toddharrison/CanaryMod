package net.minecraft.server;

import com.google.common.base.Charsets;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.base64.Base64;
import net.canarymod.Canary;
import net.canarymod.api.CanaryConfigurationManager;
import net.canarymod.api.CanaryServer;
import net.canarymod.api.world.CanarySaveConverter;
import net.canarymod.api.world.CanaryWorld;
import net.canarymod.api.world.CanaryWorldManager;
import net.canarymod.api.world.DimensionType;
import net.canarymod.config.Configuration;
import net.canarymod.config.WorldConfiguration;
import net.canarymod.hook.system.LoadWorldHook;
import net.canarymod.hook.system.ServerTickHook;
import net.canarymod.tasks.ServerTaskManager;
import net.canarymod.util.ShutdownLogger;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.crash.CrashReport;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Bootstrap;
import net.minecraft.network.NetworkSystem;
import net.minecraft.network.ServerStatusResponse;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import net.minecraft.network.rcon.RConConsoleSource;
import net.minecraft.profiler.IPlayerUsage;
import net.minecraft.profiler.PlayerUsageSnooper;
import net.minecraft.profiler.Profiler;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.chunk.storage.AnvilSaveConverter;
import net.minecraft.world.chunk.storage.AnvilSaveHandler;
import net.minecraft.world.demo.DemoWorldServer;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;
import net.visualillusionsent.utils.PropertiesFile;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.Proxy;
import java.security.KeyPair;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.Callable;

public abstract class MinecraftServer implements ICommandSender, Runnable, IPlayerUsage {

    private static boolean notHeadless = !GraphicsEnvironment.isHeadless(); // CanaryMod
    private static final Logger i = LogManager.getLogger();
    public static final File a = new File("usercache.json");
    private static MinecraftServer j;
    private final ISaveFormat k;
    private final PlayerUsageSnooper l = new PlayerUsageSnooper("server", this, ar());
    private final File m;
    private final List n = new ArrayList();
    private final ICommandManager o;
    public final Profiler b = new Profiler();
    private final NetworkSystem p;
    private final ServerStatusResponse q = new ServerStatusResponse();
    private final Random r = new Random();
    private String s;
    private int t = -1;
    // public WorldServer[] c; // XXX
    public ServerConfigurationManager u; // CanaryMod private -> public
    private boolean v = true;
    private boolean w;
    private int x;
    protected final Proxy d;
    public String e;
    public int f;
    private boolean y;
    private boolean z;
    private boolean A;
    private boolean B;
    private boolean C;
    private String D;
    private int E;
    private int F = 0;
    public final long[] g = new long[100];
    public long[][] h;
    private KeyPair G;
    private String H;
    private String I;
    private boolean K;
    private boolean L;
    private boolean M;
    private String N = "";
    private boolean O;
    private long P;
    private String Q;
    private boolean R;
    private boolean S;
    private final YggdrasilAuthenticationService T;
    private final MinecraftSessionService U;
    private long V = 0L;
    private final GameProfileRepository W;
    private final PlayerProfileCache X;

    // CanaryMod start: Multiworld \o/
    public CanaryWorldManager worldManager = new CanaryWorldManager();
    private CanaryServer server;

    // CanaryMod start: Stop Message
    private String stopMsg;

    //

    public MinecraftServer(File file1, Proxy proxy) {
        this.X = new PlayerProfileCache(this, a);
        j = this;
        this.d = proxy;
        this.m = file1;
        this.p = new NetworkSystem(this);
        this.o = new ServerCommandManager();
        this.k = new AnvilSaveConverter(file1, DimensionType.NORMAL);
        this.T = new YggdrasilAuthenticationService(proxy, UUID.randomUUID().toString());
        this.U = this.T.createMinecraftSessionService();
        this.W = this.T.createProfileRepository();
        // CanaryMod
        this.server = new CanaryServer(this);
        Canary.setServer(server);
        //
    }

    protected abstract boolean e() throws IOException;

    protected void a(String s0) {
        if (this.S().b(s0)) {
            i.info("Converting map!");
            this.b("menu.convertingLevel");
            this.S().a(s0, new IProgressUpdate() {

                private long b = System.currentTimeMillis();

                public void a(String s0) {
                }

                public void a(int s0) {
                    if (System.currentTimeMillis() - this.b >= 1000L) {
                        this.b = System.currentTimeMillis();
                        MinecraftServer.i.info("Converting... " + s0 + "%");
                    }

                }

                public void c(String s0) {
                }
            });
        }

    }

    protected synchronized void b(String s0) {
        this.Q = s0;
    }

    // Used to initialize the "master" worlds
    protected void initWorld(String name, long seed, WorldType nmsWt, net.canarymod.api.world.DimensionType dimType, String generatorSettings) {
        this.a(name); // Anvil Converter
        this.b("menu.loadingLevel");
        File worldFolder = new File("worlds/" + name);
        CanarySaveConverter converter = new CanarySaveConverter(worldFolder);

        if (converter.isVanillaFormat()) {
            Canary.log.info("World " + name + " is Vanilla. Will now attempt to convert.");
            converter.convert();
        }
        AnvilSaveHandler isavehandler = new AnvilSaveHandler(new File("worlds/"), name, true, dimType);
        WorldInfo worldinfo = isavehandler.d();
        WorldConfiguration config = Configuration.getWorldConfig(name + "_" + dimType.getName());

        WorldSettings worldsettings;
        WorldServer world;

        if (worldinfo == null) {
            worldsettings = new WorldSettings(seed, WorldSettings.GameType.a(config.getGameMode().getId()), config.generatesStructures(), false, nmsWt);
            PropertiesFile worldRaw = config.getFile();
            worldRaw.setString("world-seed", String.valueOf(seed));
            worldRaw.setInt("gamemode", 0);
            worldRaw.save();
            // CanaryMod those are flatworld settings, and they are likely unset
            if (generatorSettings != null) {
                worldsettings.a(generatorSettings);
            }
            else {
                worldsettings.a("");
            }
            //
        }
        else {
            // CanaryMod: Force game type from config
            worldinfo.a(WorldSettings.GameType.a(config.getGameMode().getId()));
            worldsettings = new WorldSettings(worldinfo);
        }

        if (this.K) {
            worldsettings.a();
        }

        if (dimType.getId() == 0) {
            if (this.P()) {
                world = new DemoWorldServer(this, isavehandler, name, dimType.getId(), this.b);
            }
            else {
                world = new WorldServer(this, isavehandler, name, dimType.getId(), worldsettings, this.b);
            }
        }
        else {
            world = new WorldServerMulti(this, isavehandler, name, dimType.getId(), worldsettings, (WorldServer) ((CanaryWorld) worldManager.getWorld(name, net.canarymod.api.world.DimensionType.NORMAL, true)).getHandle(), this.b);
        }

        world.a((IWorldAccess) (new WorldManager(this, world)));
        if (!this.N()) {
            world.N().a(WorldSettings.GameType.a(config.getGameMode().getId()));
        }

        this.u.a(new WorldServer[]{world}); // Init player data files

        // this.a(this.j()); // If we call this then worlds can't do custom difficulty, plus it doesn't work
        world.r = EnumDifficulty.a(config.getDifficulty().getId()); // Set difficulty directly based on WorldConfiguration setting
        this.g(world); // Generate terrain
        worldManager.addWorld(world.getCanaryWorld());
        new LoadWorldHook(world.getCanaryWorld()).call();
    }

    protected void g(WorldServer worldserver) { // CanaryMod: signature changed
        boolean flag0 = true;
        boolean flag1 = true;
        boolean flag2 = true;
        boolean flag3 = true;
        int i0 = 0;

        this.b("menu.generatingTerrain");
        byte b0 = 0;

        i.info("Preparing start region for level " + worldserver.getCanaryWorld().getName());
        ChunkCoordinates chunkcoordinates = worldserver.K();
        long i1 = ar();

        for (int i2 = -192; i2 <= 192 && this.q(); i2 += 16) {
            for (int i3 = -192; i3 <= 192 && this.q(); i3 += 16) {
                long i4 = ar();

                if (i4 - i1 > 1000L) {
                    this.a_("Preparing spawn area", i0 * 100 / 625);
                    i1 = i4;
                }

                ++i0;
                worldserver.b.c(chunkcoordinates.a + i2 >> 4, chunkcoordinates.c + i3 >> 4);
            }
        }

        this.n();
    }

    public abstract boolean h();

    public abstract WorldSettings.GameType i();

    public abstract EnumDifficulty j();

    public abstract boolean k();

    public abstract int l();

    public abstract boolean m();

    protected void a_(String s0, int i0) {
        this.e = s0;
        this.f = i0;
        i.info(s0 + ": " + i0 + "%");
    }

    protected void n() {
        this.e = null;
        this.f = 0;
    }

    protected void a(boolean flag0) {
        a(flag0, i);
    }

    private void a(boolean flag0, Logger i) {
        if (!this.M) {
            // CanaryMod changed to use worldManager
            for (net.canarymod.api.world.World w : worldManager.getAllWorlds()) {
                WorldServer worldserver = (WorldServer) ((CanaryWorld) w).getHandle();

                if (worldserver != null) {
                    if (!flag0) {
                        i.info("Saving chunks for level \'" + worldserver.N().k() + "\'/" + worldserver.t.l());
                    }

                    try {
                        worldserver.a(true, (IProgressUpdate) null);
                    }
                    catch (MinecraftException minecraftexception) {
                        i.warn(minecraftexception.getMessage());
                    }
                }
                else {
                    i.warn("null world");
                }
            }
        }
    }

    public void o() {
        if (!this.M) {
            // CanaryMod start: If we're in the shutdown hook, we can't rely on log4j for logging.
            Logger log;
            if (Thread.currentThread().getName().equals("Server Shutdown Thread")) {
                log = new ShutdownLogger(MinecraftServer.class);
            }
            else {
                log = MinecraftServer.i;
            }

            log.info("Stopping server");
            if (this.ai() != null) {
                this.ai().b();
            }

            if (this.u != null) {
                log.info("Saving players");
                this.u.j();
                this.u.u(this.stopMsg);
            }

            log.info("Saving worlds");
            this.a(false, log);

            // CanaryMod Multiworld
            for (net.canarymod.api.world.World w : worldManager.getAllWorlds()) {
                WorldServer worldserver = (WorldServer) ((CanaryWorld) w).getHandle();

                worldserver.n();
            }

            if (this.l.d()) {
                this.l.e();
            }
            // CanaryMod disable plugins:
            Logger canaryLogger = log == h ? Canary.log : new ShutdownLogger("CanaryMod");
            canaryLogger.info("Disabling Plugins ...");
            Canary.loader().disableAllPlugins(canaryLogger);
        }
    }

    public String p() {
        return this.s;
    }

    public void c(String s0) {
        this.s = s0;
    }

    public boolean q() {
        return this.v;
    }

    public void r() {
        this.v = false;
    }

    public void run() {
        try {
            if (this.e()) {
                long i0 = ar();
                long i20 = 0L;

                this.q.a((IChatComponent) (new ChatComponentText(this.D)));
                this.q.a(new ServerStatusResponse.MinecraftProtocolVersionIdentifier("1.7.10", 5));
                this.a(this.q);

                while (this.v) {
                    long i2 = ar();
                    long i3 = i2 - i0;

                    if (i3 > 2000L && i0 - this.P >= 15000L) {
                        i.warn("Can\'t keep up! Did the system time change, or is the server overloaded? Running {}ms behind, skipping {} tick(s)", new Object[]{Long.valueOf(i3), Long.valueOf(i3 / 50L)});
                        i3 = 2000L;
                        this.P = i0;
                    }

                    if (i3 < 0L) {
                        i.warn("Time ran backwards! Did the system time change?");
                        i3 = 0L;
                    }

                    i20 += i3;
                    i0 = i2;
                    // CanaryMod start: multiworld sleeping
                    boolean allSleeping = true;

                    for (net.canarymod.api.world.World w : worldManager.getAllWorlds()) {
                        allSleeping &= ((WorldServer) ((CanaryWorld) w).getHandle()).e();
                    }
                    // CanaryMod end
                    if (allSleeping) {
                        this.u(); // Run tick
                        i20 = 0L;
                    }
                    else {
                        while (i20 > 50L) {
                            i20 -= 50L;
                            this.u();
                        }
                    }

                    Thread.sleep(Math.max(1L, 50L - i20));
                    this.O = true;
                }
            }
            else {
                this.a((CrashReport) null);
            }
        }
        catch (Throwable throwable) {
            i.error("Encountered an unexpected exception", throwable);
            CrashReport crashreport = null;

            if (throwable instanceof ReportedException) {
                crashreport = this.b(((ReportedException) throwable).a());
            }
            else {
                crashreport = this.b(new CrashReport("Exception in server tick loop", throwable));
            }

            File file1 = new File(new File(this.s(), "crash-reports"), "crash-" + (new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss")).format(new Date()) + "-server.txt");

            if (crashreport.a(file1)) {
                i.error("This crash report has been saved to: " + file1.getAbsolutePath());
            }
            else {
                i.error("We were unable to save this crash report to disk.");
            }

            this.a(crashreport);
        }
        finally {
            try {
                this.o();
                this.w = true;
            }
            catch (Throwable throwable1) {
                i.error("Exception stopping the server", throwable1);
            }
            finally {
                this.t();
            }
        }

    }

    private void a(ServerStatusResponse serverstatusresponse) {
        File file1 = this.d("server-icon.png");

        if (file1.isFile()) {
            ByteBuf bytebuf = Unpooled.buffer();

            try {
                BufferedImage bufferedimage = ImageIO.read(file1);

                Validate.validState(bufferedimage.getWidth() == 64, "Must be 64 pixels wide", new Object[0]);
                Validate.validState(bufferedimage.getHeight() == 64, "Must be 64 pixels high", new Object[0]);
                ImageIO.write(bufferedimage, "PNG", new ByteBufOutputStream(bytebuf));
                ByteBuf bytebuf1 = Base64.encode(bytebuf);

                serverstatusresponse.a("data:image/png;base64," + bytebuf1.toString(Charsets.UTF_8));
            }
            catch (Exception exception) {
                i.error("Couldn\'t load server icon", exception);
            }
            finally {
                bytebuf.release();
            }
        }
    }

    protected File s() {
        return new File(".");
    }

    protected void a(CrashReport crashreport) {
    }

    protected void t() {
    }

    protected void u() {
        ServerTaskManager.runTasks(); // CanaryMod: Run tasks
        long i0 = System.nanoTime();

        ++this.x;
        if (this.R) {
            this.R = false;
            this.b.a = true;
            this.b.a();
        }

        this.b.a("root");
        this.v();
        if (i0 - this.V >= 5000000000L) {
            this.V = i0;
            this.q.a(new ServerStatusResponse.PlayerCountData(this.D(), this.C()));
            GameProfile[] agameprofile = new GameProfile[Math.min(this.C(), 12)];
            int i1 = MathHelper.a(this.r, 0, this.C() - agameprofile.length);

            for (int i2 = 0; i2 < agameprofile.length; ++i2) {
                agameprofile[i2] = ((EntityPlayerMP) this.u.e.get(i1 + i2)).bJ();
            }

            Collections.shuffle(Arrays.asList(agameprofile));
            this.q.b().a(agameprofile);
        }

        if (this.x % 900 == 0) {
            this.b.a("save");
            this.u.j();
            this.a(true);
            this.b.b();
        }

        this.b.a("tallying");
        this.g[this.x % 100] = System.nanoTime() - i0;
        this.b.b();
        this.b.a("snooper");
        if (!this.l.d() && this.x > 100) {
            this.l.a();
        }

        if (this.x % 6000 == 0) {
            this.l.b();
        }

        this.b.b();
        this.b.b();
    }

    // CanaryMod: ticks world
    private long previousTick = -1L; // Tick Time Tracker

    public void v() {
        new ServerTickHook(previousTick).call(); // CanaryMod: ServerTick
        long curTrack = System.nanoTime(); // CanaryMod: Start tick track

        this.b.a("levels");
        int i0;

        // CanaryMod use worldManager instead, and copy into a new list (underlaying list may get modified)
        for (net.canarymod.api.world.World w : new ArrayList<net.canarymod.api.world.World>(worldManager.getAllWorlds())) {
            WorldServer worldserver = (WorldServer) ((CanaryWorld) w).getHandle();
            //
            long i1 = System.nanoTime();

            this.b.a(worldserver.N().k());
            this.b.a("pools");
            this.b.b();
            if (this.w % 20 == 0) {
                this.b.a("timeSync");
                // this.u.a((Packet) (new S03PacketTimeUpdate(worldserver.H(), worldserver.I(), worldserver.N().b("doDaylightCycle"))), worldserver.t.i);
                this.u.sendPacketToDimension(new S03PacketTimeUpdate(worldserver.I(), worldserver.J(), worldserver.O().b("doDaylightCycle")), worldserver.getCanaryWorld().getName(), worldserver.t.i);
                this.b.b();
            }

            this.b.a("tick");

            CrashReport crashreport;

            try {
                worldserver.b();
            }
            catch (Throwable throwable) {
                crashreport = CrashReport.a(throwable, "Exception ticking world");
                worldserver.a(crashreport);
                throw new ReportedException(crashreport);
            }

            try {
                worldserver.h();
            }
            catch (Throwable throwable1) {
                crashreport = CrashReport.a(throwable1, "Exception ticking world entities");
                worldserver.a(crashreport);
                throw new ReportedException(crashreport);
            }

            this.b.b();
            this.b.a("tracker");
            worldserver.r().a();
            this.b.b();
            this.b.b();
            w.setNanoTick(this.w % 100, System.nanoTime() - i1);
            // this.k[i0][this.w % 100] = System.nanoTime() - i1;
        }

        this.b.c("connection");
        this.ai().c();
        this.b.c("players");
        this.u.e();
        this.b.c("tickables");

        for (i0 = 0; i0 < this.n.size(); ++i0) {
            ((IUpdatePlayerListBox) this.n.get(i0)).a();
        }

        this.b.b();

        // CanaryMod: set this ticks time
        previousTick = System.nanoTime() - curTrack;
        //
    }

    @Deprecated //CanaryMod: deprecate method
    public boolean w() {
        throw new UnsupportedOperationException("allow-nether has been moved to a per-world configuration!");
    }

    public void a(IUpdatePlayerListBox iupdateplayerlistbox) {
        this.n.add(iupdateplayerlistbox);
    }

    public static void main(String[] astring) {
        Bootstrap.b();

        try {
            // CanaryMod - Removed boolean flag0 = !GraphicsEnvironment.isHeadless();
            String s0 = null;
            String s1 = ".";
            String s2 = null;
            boolean flag1 = false;
            boolean flag2 = false;
            int i0 = -1;

            for (int i1 = 0; i1 < astring.length; ++i1) {
                String s3 = astring[i1];
                String s4 = i1 == astring.length - 1 ? null : astring[i1 + 1];
                boolean flag3 = false;

                // CanaryMod removed Nogui check, now done in Main
                if (s3.equals("--port") && s4 != null) {
                    flag3 = true;

                    try {
                        i0 = Integer.parseInt(s4);
                    }
                    catch (NumberFormatException numberformatexception) {
                        ;
                    }
                }
                else if (s3.equals("--singleplayer") && s4 != null) {
                    flag3 = true;
                    s0 = s4;
                }
                else if (s3.equals("--universe") && s4 != null) {
                    flag3 = true;
                    s1 = s4;
                }
                else if (s3.equals("--world") && s4 != null) {
                    flag3 = true;
                    s2 = s4;
                }
                else if (s3.equals("--demo")) {
                    flag1 = true;
                }
                else if (s3.equals("--bonusChest")) {
                    flag2 = true;
                }
                // CanaryMod removed else to Nogui check

                if (flag3) {
                    ++i1;
                }
            }

            final DedicatedServer dedicatedserver = new DedicatedServer(new File(s1));

            if (s0 != null) {
                dedicatedserver.j(s0);
            }

            if (s2 != null) {
                dedicatedserver.k(s2);
            }

            if (i0 >= 0) {
                dedicatedserver.b(i0);
            }

            if (flag1) {
                dedicatedserver.b(true);
            }

            if (flag2) {
                dedicatedserver.c(true);
            }

            // CanaryMod Removed old call to start GUI

            dedicatedserver.x();
            Runtime.getRuntime().addShutdownHook(new Thread("Server Shutdown Thread") {

                public void run() {
                    dedicatedserver.o();
                }
            });
        }
        catch (Exception exception) {
            //h.fatal("Failed to start the minecraft server", exception);
            throw new RuntimeException(exception); //We need to know something happen so we can terminate
        }
    }

    public void x() {
        (new Thread("Server thread") {

            public void run() {
                MinecraftServer.this.run();
            }
        }

        ).start();
    }

    public File d(String s0) {
        return new File(this.s(), s0);
    }

    public void e(String s0) {
        i.info(s0);
    }

    public void f(String s0) {
        i.warn(s0);
    }

    @Deprecated //CanaryMod: deprecate method
    public WorldServer a(int i0) {
        throw new UnsupportedOperationException("MinecraftServer.a(int) has" + " been replaced by MinecraftServer.getWorld(String, int).");
    }

    public WorldServer getWorld(String s, int i) {
        net.canarymod.api.world.World w = worldManager.getWorld(s, net.canarymod.api.world.DimensionType.fromId(i), true);

        if (w != null) {
            return (WorldServer) ((CanaryWorld) w).getHandle();
        }
        return null;
    }

    public String y() {
        return this.s;
    }

    public int z() {
        return this.t;
    }

    public String A() {
        return this.D;
    }

    public String B() {
        return "1.7.10";
    }

    public int C() {
        return this.u.o();
    }

    public int D() {
        return this.u.p();
    }

    public String[] E() {
        return this.u.f();
    }

    public GameProfile[] F() {
        return this.u.g();
    }

    public String G() {
        return "";
    }

    public String g(String s0) {
        RConConsoleSource.a.e();
        this.o.a(RConConsoleSource.a, s0);
        return RConConsoleSource.a.f();
    }

    public boolean H() {
        return false;
    }

    public void h(String s0) {
        i.error(s0);
    }

    public void i(String s0) {
        if (this.H()) {
            i.info(s0);
        }
    }

    public String getServerModName() {
        return "CanaryMod";
    }

    public CrashReport b(CrashReport crashreport) {
        crashreport.g().a("Profiler Position", new Callable() {

            public String call() {
                return MinecraftServer.this.b.a ? MinecraftServer.this.b.c() : "N/A (disabled)";
            }
        });
        //if (this.b != null && this.b.length > 0 && this.b[0] != null) {
        for (net.canarymod.api.world.World cWorld : this.getWorldManager().getAllWorlds()) {
            if (cWorld.getType() != DimensionType.NORMAL) {
                continue; // CanaryMod: Skip non-default dimentional worlds
            }
            final WorldServer worldServer = (WorldServer) ((CanaryWorld) cWorld).getHandle();
            crashreport.g().a("Vec3 Pool Size", new Callable() {

                public String call() {
                    byte b0 = 0;
                    int i0 = 56 * b0;
                    int i1 = i0 / 1024 / 1024;
                    byte b1 = 0;
                    int i2 = 56 * b1;
                    int i3 = i2 / 1024 / 1024;

                    return b0 + " (" + i0 + " bytes; " + i1 + " MB) allocated, " + b1 + " (" + i2 + " bytes; " + i3 + " MB) used";
                }
            });
        }

        if (this.u != null) {
            crashreport.g().a("Player Count", new Callable() {

                public String call() {
                    return MinecraftServer.this.u.o() + " / " + MinecraftServer.this.u.p() + "; " + MinecraftServer.this.u.e;
                }
            });
        }

        return crashreport;
    }

    public List a(ICommandSender icommandsender, String s0) {
        ArrayList arraylist = new ArrayList();

        if (s0.startsWith("/")) {
            s0 = s0.substring(1);
            boolean flag0 = !s0.contains(" ");

            List list = this.o.b(icommandsender, s0);

            if (list != null) {
                Iterator iterator = list.iterator();

                while (iterator.hasNext()) {
                    String s1 = (String) iterator.next();

                    if (flag0) {
                        arraylist.add("/" + s1);
                    }
                    else {
                        arraylist.add(s1);
                    }
                }
            }

            return arraylist;
        }
        else {
            String[] astring = s0.split(" ", -1);
            String s2 = astring[astring.length - 1];
            String[] astring1 = this.u.f();
            int i0 = astring1.length;

            for (int i1 = 0; i1 < i0; ++i1) {
                String s3 = astring1[i1];

                if (CommandBase.a(s2, s3)) {
                    arraylist.add(s3);
                }
            }

            return arraylist;
        }
    }

    public static MinecraftServer I() {
        return j;
    }

    public String b_() {
        return "Server";
    }

    public void a(IChatComponent ichatcomponent) {
        i.info(ichatcomponent.c());
    }

    public boolean a(int i0, String s0) {
        return true;
    }

    public ICommandManager J() {
        return this.o;
    }

    public KeyPair K() {
        return this.G;
    }

    public int L() {
        return this.t;
    }

    public void b(int i0) {
        this.t = i0;
    }

    public String M() {
        return this.H;
    }

    public void j(String s0) {
        this.H = s0;
    }

    public boolean N() {
        return this.H != null;
    }

    public String O() {
        return this.I;
    }

    public void k(String s0) {
        this.I = s0;
    }

    public void a(KeyPair keypair) {
        this.G = keypair;
    }

    public void a(EnumDifficulty enumdifficulty, WorldServer worldserver) { // CanaryMod: Signature change to include world
        // CanaryMod changes for Multiworld, Don't set every world the same.
        //WorldServer worldserver = (WorldServer) ((CanaryWorld) w).getHandle();
        if (worldserver != null) {
            // System.out.println(worldserver.getCanaryWorld().getName() + " Difficulty " + i0);
            boolean monsters = Configuration.getWorldConfig(worldserver.getCanaryWorld().getFqName()).canSpawnMonsters();
            boolean animals = Configuration.getWorldConfig(worldserver.getCanaryWorld().getFqName()).canSpawnAnimals();
            if (worldserver.N().t()) {
                worldserver.r = EnumDifficulty.HARD;
                worldserver.a(monsters, animals);
            }
            //else if (this.L()) { // NOT SINGLE PLAYER ANYWAYS
            //    worldserver.r = enumdifficulty;
            //    worldserver.a(monsters, animals);
            //}
            else {
                worldserver.r = enumdifficulty;
                worldserver.a(Configuration.getWorldConfig(worldserver.getCanaryWorld().getFqName()).canSpawnMonsters(), animals);
            }
        }
    }

    protected boolean Q() {
        return true;
    }

    public boolean R() {
        return this.K;
    }

    public void b(boolean flag0) {
        this.K = flag0;
    }

    public void c(boolean flag0) {
        this.L = flag0;
    }

    public ISaveFormat S() {
        return this.k;
    }

    public void U() {
        this.M = true;
        this.S().d();

        for (net.canarymod.api.world.World w : worldManager.getAllWorlds()) {
            WorldServer worldserver = (WorldServer) ((CanaryWorld) w).getHandle();

            if (worldserver != null) {
                worldserver.n();
            }

            if (w.getType().getId() == 0) {
                this.S().e(worldserver.M().g());
            }
        }

        this.r();
    }

    public String V() {
        return this.N;
    }

    public void m(String s0) {
        this.N = s0;
    }

    public void a(PlayerUsageSnooper playerusagesnooper) {
        playerusagesnooper.a("whitelist_enabled", Boolean.valueOf(false));
        playerusagesnooper.a("whitelist_count", Integer.valueOf(0));
        playerusagesnooper.a("players_current", Integer.valueOf(this.C()));
        playerusagesnooper.a("players_max", Integer.valueOf(this.D()));
        playerusagesnooper.a("players_seen", Integer.valueOf(this.u.q().length));
        playerusagesnooper.a("uses_auth", Boolean.valueOf(this.y));
        playerusagesnooper.a("gui_state", this.ak() ? "enabled" : "disabled");
        playerusagesnooper.a("run_time", Long.valueOf((ar() - playerusagesnooper.g()) / 60L * 1000L));
        playerusagesnooper.a("avg_tick_ms", Integer.valueOf((int) (MathHelper.a(this.g) * 1.0E-6D)));
        int i0 = 0;

        for (net.canarymod.api.world.World cWorld : this.getWorldManager().getAllWorlds()) {
            WorldServer worldserver = (WorldServer) ((CanaryWorld) cWorld).getHandle();
            //WorldServer worldserver = this.c[i1];
            WorldInfo worldinfo = worldserver.N();

            playerusagesnooper.a("world[" + i0 + "][dimension]", Integer.valueOf(worldserver.t.i));
            playerusagesnooper.a("world[" + i0 + "][mode]", worldinfo.r());
            playerusagesnooper.a("world[" + i0 + "][difficulty]", worldserver.r);
            playerusagesnooper.a("world[" + i0 + "][hardcore]", Boolean.valueOf(worldinfo.t()));
            playerusagesnooper.a("world[" + i0 + "][generator_name]", worldinfo.u().a());
            playerusagesnooper.a("world[" + i0 + "][generator_version]", Integer.valueOf(worldinfo.u().d()));
            playerusagesnooper.a("world[" + i0 + "][height]", Integer.valueOf(this.E));
            playerusagesnooper.a("world[" + i0 + "][chunks_loaded]", Integer.valueOf(worldserver.L().g()));
            ++i0;
            //}
        }

        playerusagesnooper.a("worlds", Integer.valueOf(i0));
    }

    public void b(PlayerUsageSnooper playerusagesnooper) {
        playerusagesnooper.b("singleplayer", Boolean.valueOf(this.N()));
        playerusagesnooper.b("server_brand", this.getServerModName());
        playerusagesnooper.b("gui_supported", GraphicsEnvironment.isHeadless() ? "headless" : "supported");
        playerusagesnooper.b("dedicated", Boolean.valueOf(this.X()));
    }

    public boolean W() {
        return true;
    }

    public abstract boolean X();

    public boolean Y() {
        return this.y;
    }

    public void d(boolean flag0) {
        this.y = flag0;
    }

    public boolean Z() {
        return this.z;
    }

    public void e(boolean flag0) {
        this.z = flag0;
    }

    public boolean aa() {
        return this.A;
    }

    public void f(boolean flag0) {
        this.A = flag0;
    }

    public boolean ab() {
        return this.B;
    }

    public void g(boolean flag0) {
        this.B = flag0;
    }

    public boolean ac() {
        return this.C;
    }

    public void h(boolean flag0) {
        this.C = flag0;
    }

    public abstract boolean ad();

    public String ae() {
        return this.D;
    }

    public void n(String s0) {
        this.D = s0;
    }

    public int af() {
        return this.E;
    }

    public void c(int i0) {
        this.E = i0;
    }

    public boolean ag() {
        return this.w;
    }

    public ServerConfigurationManager ah() {
        return this.u;
    }

    public void a(ServerConfigurationManager serverconfigurationmanager) {
        this.u = serverconfigurationmanager;
    }

    public void a(WorldSettings.GameType worldsettings_gametype) {
        for (net.canarymod.api.world.World w : worldManager.getAllWorlds()) {
            WorldServer worldserver = (WorldServer) ((CanaryWorld) w).getHandle();

            worldserver.N().a(worldsettings_gametype);
        }
    }

    public NetworkSystem ai() {
        return this.p;
    }

    public boolean ak() {
        return false;
    }

    public abstract String a(WorldSettings.GameType worldsettings_gametype, boolean flag0);

    public int al() {
        return this.x;
    }

    public void am() {
        this.R = true;
    }

    public ChunkCoordinates f_() {
        return new ChunkCoordinates(0, 0, 0);
    }

    public World d() {
        return ((CanaryWorld) Canary.getServer().getDefaultWorld()).getHandle(); // CanaryMod
    }

    public int ao() {
        return 16;
    }

    public boolean a(World world, int i0, int i1, int i2, EntityPlayer entityplayer) {
        return false;
    }

    public void i(boolean flag0) {
        this.S = flag0;
    }

    public boolean ap() {
        return this.S;
    }

    public Proxy aq() {
        return this.d;
    }

    public static long ar() {
        return System.currentTimeMillis();
    }

    public int as() {
        return this.F;
    }

    public void d(int i0) {
        this.F = i0;
    }

    public IChatComponent c_() {
        return new ChatComponentText(this.b_());
    }

    public boolean at() {
        return true;
    }

    public MinecraftSessionService av() {
        return this.U;
    }

    public GameProfileRepository aw() {
        return this.W;
    }

    public PlayerProfileCache ax() {
        return this.X;
    }

    public ServerStatusResponse ay() {
        return this.q;
    }

    public void az() {
        this.V = 0L;
    }

    /**
     * Returns the canary world manager for this server instance
     *
     * @return
     */
    public CanaryWorldManager getWorldManager() {
        return worldManager;
    }

    /**
     * Reload configurations
     */
    public abstract void reload(); // look in DedicatedServer class

    // CanaryMod

    /**
     * Get the CanaryMod server handler
     *
     * @return
     */
    public CanaryServer getServer() {
        return server;
    }

    public CanaryConfigurationManager getConfigurationManager() {
        return u.getConfigurationManager();
    }

    public void initShutdown(String message) {
        this.stopMsg = message;
        this.q();
    }

    public boolean isRunning() {
        return this.u;
    }

    /**
     * Creates a new world with given name and seed.
     * This will load the default (NORMAL) world
     *
     * @param name
     * @param seed
     */
    public void loadWorld(String name, long seed) {
        loadWorld(name, seed, net.canarymod.api.world.DimensionType.NORMAL);
    }

    public void loadWorld(String name, long seed, net.canarymod.api.world.DimensionType type) {
        this.loadWorld(name, seed, type, net.canarymod.api.world.WorldType.DEFAULT);
    }

    public void loadWorld(String name, long seed, net.canarymod.api.world.DimensionType type, net.canarymod.api.world.WorldType typeGen) {
        this.initWorld(name, seed, WorldType.a(typeGen.toString()), type, null);
    }

    public static boolean isHeadless() {
        return !MinecraftServer.notHeadless;
    }

    public static void setHeadless(boolean state) {
        if (!GraphicsEnvironment.isHeadless()) {
            MinecraftServer.notHeadless = !state;
        }
    }

}
