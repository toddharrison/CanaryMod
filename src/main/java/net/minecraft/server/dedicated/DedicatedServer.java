package net.minecraft.server.dedicated;

import net.canarymod.Canary;
import net.canarymod.api.CanaryServer;
import net.canarymod.config.Configuration;
import net.canarymod.config.ServerConfiguration;
import net.canarymod.config.WorldConfiguration;
import net.canarymod.hook.system.ServerGuiStartHook;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.ServerCommand;
import net.minecraft.crash.CrashReport;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.rcon.IServer;
import net.minecraft.network.rcon.RConThreadMain;
import net.minecraft.network.rcon.RConThreadQuery;
import net.minecraft.profiler.PlayerUsageSnooper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.gui.MinecraftServerGui;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.CryptManager;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

public class DedicatedServer extends MinecraftServer implements IServer {

    private static final Logger h = LogManager.getLogger();
    private final List i = Collections.synchronizedList(new ArrayList());
    private RConThreadQuery j;
    private RConThreadMain k;
    // CanaryMod - Removed private PropertyManager l;
    // CanaryMod - Removed private boolean m;
    // CanaryMod - Removed private WorldSettings.GameType n;
    private boolean o;

    public DedicatedServer(File file1) {
        super(file1, Proxy.NO_PROXY);
        Thread thread = new Thread("Server Infinisleeper") {

            {
                this.setDaemon(true);
                this.start();
            }

            public void run() {
                while (true) {
                    try {
                        while (true) {
                            Thread.sleep(2147483647L);
                        }
                    }
                    catch (InterruptedException interruptedexception) {
                        ;
                    }
                }
            }
        };
    }

    protected boolean e() throws IOException {
        Thread bufferedreader = new Thread("Server console handler") {

            public void run() {
                BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(System.in));

                String i0;

                try {
                    while (!DedicatedServer.this.ae() && DedicatedServer.this.p() && (i0 = bufferedreader.readLine()) != null) {
                        DedicatedServer.this.a(i0, (ICommandSender) DedicatedServer.this);
                    }
                }
                catch (IOException i1) {
                    DedicatedServer.h.error("Exception handling console input", i1);
                }

            }
        };

        bufferedreader.setDaemon(true);
        bufferedreader.start();
        h.info("Starting minecraft server version 1.7.2");
        if (Runtime.getRuntime().maxMemory() / 1024L / 1024L < 512L) {
            h.warn("To start the server with more ram, launch it as \"java -Xmx1024M -Xms1024M -jar minecraft_server.jar\"");
        }

        h.info("Loading properties");
        // this.l = new PropertyManager(new File("server.properties")); //CanaryMod - Removed
        // CanaryMod use our config
        ServerConfiguration cfg = Configuration.getServerConfig();
        if (this.L()) {
            this.c("127.0.0.1");
        }
        else {
            this.d(cfg.isOnlineMode());
            this.c(cfg.getBindIp());
        }
        // CanaryMod: Removed world-dependent settings
        this.n(cfg.getMotd());
        this.m(cfg.getTexturePack());
        this.d(cfg.getPlayerIdleTimeout());
        InetAddress inetaddress = null;

        if (this.o().length() > 0) {
            inetaddress = InetAddress.getByName(this.o());
        }

        if (this.J() < 0) {
            this.b(cfg.getPort());
        }

        h.info("Generating keypair");
        this.a(CryptManager.b());
        h.info("Starting Minecraft server on " + (this.o().length() == 0 ? "*" : this.o()) + ":" + this.J());

        try {
            this.ag().a(inetaddress, this.J());
        }
        catch (IOException ioexception1) {
            h.warn("**** FAILED TO BIND TO PORT!");
            h.warn("The exception was: {}", new Object[]{ ioexception1.toString() });
            h.warn("Perhaps a server is already running on that port?");
            return false;
        }

        if (!this.W()) {
            h.warn("**** SERVER IS RUNNING IN OFFLINE/INSECURE MODE!");
            h.warn("The server will make no attempt to authenticate usernames. Beware.");
            h.warn("While this makes the game possible to play without internet access, it also opens up the ability for hackers to connect with any username they choose.");
            h.warn("To change this, set \"online-mode\" to \"true\" in the server.properties file.");
        }

        this.a((ServerConfigurationManager) (new DedicatedPlayerList(this)));
        long i1 = System.nanoTime();

        if (this.M() == null) {
            this.k(Configuration.getServerConfig().getDefaultWorldName()); // CanaryMod use our world config
        }
        // CanaryMod use or configurations instead of native ones
        WorldConfiguration worldcfg = Configuration.getWorldConfig(this.L() + "_NORMAL");

        String s1 = worldcfg.getWorldSeed(); // this.p.a("level-seed", "");
        String s2 = worldcfg.getWorldType().toString(); // this.p.a("level-type", "DEFAULT");
        String s3 = worldcfg.getGeneratorSettings(); // this.p.a("generator-settings", "");
        long i2 = (new Random()).nextLong();

        if (s1.length() > 0) {
            try {
                long i3 = Long.parseLong(s1);

                if (i3 != 0L) {
                    i2 = i3;
                }
            }
            catch (NumberFormatException numberformatexception) {
                i2 = (long) s1.hashCode();
            }
        }

        WorldType worldtype = WorldType.a(s2);

        if (worldtype == null) {
            worldtype = WorldType.b;
        }

        this.ar();
        this.ab();
        this.l();
        this.U();
        this.c(worldcfg.getMaxBuildHeight());
        this.c((this.ad() + 8) / 16 * 16);
        this.c(MathHelper.a(this.ad(), 64, 256));
        worldcfg.getFile().setInt("max-build-height", this.ad());
        // CanaryMod enable plugins here, before the first world is loaded.
        // At this point all bootstrapping should be done and systems should be running
        Canary.enablePlugins();

        if (!MinecraftServer.isHeadless()) {
            // CanaryMod moved GUI start to after plugins enable
            this.ay();
        }

        // CanaryMod changed call to initWorld
        net.canarymod.api.world.DimensionType wt = net.canarymod.api.world.DimensionType.fromName("NORMAL");

        this.initWorld(this.M(), i2, worldtype, wt, s2);
        //
        long i4 = System.nanoTime() - i1;
        String s4 = String.format("%.3fs", new Object[]{ Double.valueOf((double) i4 / 1.0E9D) });

        h.info("Done (" + s4 + ")! For help, type \"help\" or \"?\"");
        if (cfg.isQueryEnabled()) {
            h.info("Starting GS4 status listener");
            this.j = new RConThreadQuery(this);
            this.j.a();
        }
        if (cfg.isRconEnabled()) {
            h.info("Starting remote control listener");
            this.k = new RConThreadMain(this);
            this.k.a();
        }

        return true;
    }

    public boolean h() {
        throw new UnsupportedOperationException("Generate-structures setting has been moved to a per-world configuration!");
    }

    public WorldSettings.GameType i() {
        throw new UnsupportedOperationException("GameType setting has been moved to a per-world configuration!");
    }

    public EnumDifficulty j() {
        throw new UnsupportedOperationException("Difficulty setting has been moved to a per-world configuration!");
    }

    public boolean k() {
        throw new UnsupportedOperationException("Hardcoremode setting has been moved to a per-world configuration!");
    }

    protected void a(CrashReport crashreport) {
        while (this.p()) {
            this.aw();

            try {
                Thread.sleep(10L);
            }
            catch (InterruptedException interruptedexception) {
                ;
            }
        }
    }

    public CrashReport b(CrashReport crashreport) {
        crashreport = super.b(crashreport);
        crashreport.g().a("Is Modded", new Callable() {

            public String call() {
                String s0 = DedicatedServer.this.getServerModName();

                return !s0.equals("vanilla") ? "Definitely; Server brand changed to \'" + s0 + "\'" : "Unknown (can\'t tell)";
            }
        });
        crashreport.g().a("Type", new Callable() {

            public String call() {
                return "Dedicated Server (map_server.txt)";
            }
        });
        return crashreport;
    }

    protected void s() {
        System.exit(0);
    }

    public void u() { // CanaryMod: protected => public
        super.u();
        this.aw();
    }

    public boolean v() {
        throw new UnsupportedOperationException("allow-nether has been moved to a per-world config");
    }

    public boolean O() {
        throw new UnsupportedOperationException("spawn-monsters has been moved to a per-world config");
    }

    public void a(PlayerUsageSnooper playerusagesnooper) {
        playerusagesnooper.a("whitelist_enabled", Configuration.getServerConfig().isWhitelistEnabled());
        playerusagesnooper.a("whitelist_count", Canary.whitelist().getSize());
        super.a(playerusagesnooper);
    }

    public boolean U() {
        // CanaryMod moved to config/server.cfg
        return Configuration.getServerConfig().isSnooperEnabled();
    }

    public void a(String s0, ICommandSender icommandsender) {
        this.i.add(new ServerCommand(s0, icommandsender));
    }

    public void aw() {
        while (!this.i.isEmpty()) {
            ServerCommand servercommand = (ServerCommand) this.i.remove(0);
            // CanaryMod intercept command queue for our own commands
            String[] split = servercommand.a.split(" ");
            if (!Canary.commands().parseCommand(getServer(), split[0], split)) {
                this.H().a(servercommand.b, servercommand.a);
            }
        }
    }

    public boolean V() {
        return true;
    }

    public DedicatedPlayerList ax() {
        return (DedicatedPlayerList) super.af();
    }

    public int a(String s0, int i0) {
        throw new UnsupportedOperationException("Setting int values to server.properties is disabled!");
    }

    public String a(String s0, String s1) {
        throw new UnsupportedOperationException("Setting String values to server.properties is disabled!");
    }

    public boolean a(String s0, boolean flag0) {
        throw new UnsupportedOperationException("Setting boolean values to server.properties is disabled!");
    }

    public void a(String s0, Object object) {
        throw new UnsupportedOperationException("Setting Object values to server.properties is disabled!");
    }

    public void a() {
        throw new UnsupportedOperationException("Cannot finish this request. DedicatedServer.a() is deprecated");
    }

    public String b() {
        throw new UnsupportedOperationException("Cannot finish this request. DedicatedServer.b_() is deprecated");
    }

    public void ay() {
        try {
            ServerGuiStartHook guiHook = (ServerGuiStartHook) new ServerGuiStartHook(MinecraftServerGui.preInit(this)).call(); // CanaryMod: PreInitialize the GUI without starting it
            if (guiHook.getGui() != null) {
                ((CanaryServer) Canary.getServer()).setCurrentGUI(guiHook.getGui());
            }
            else {
                ((CanaryServer) Canary.getServer()).setCurrentGUI(MinecraftServerGui.a(this));
            }
            Canary.getServer().getCurrentGUI().start();
            this.o = true;
            MinecraftServer.setHeadless(false);
        } catch (Exception e) {
            // The gui is causing hang ups so just ignore the gui entirely
            Canary.logDerp("GUI failed to start.", e);
        }
    }

    public boolean ai() {
        return this.o;
    }

    public String a(WorldSettings.GameType worldsettings_gametype, boolean flag0) {
        return "";
    }

    public boolean ab() {
        // CanaryMod moved to config/server.cfg
        return Configuration.getServerConfig().isCommandBlockEnabled();
    }

    public int am() {
        throw new UnsupportedOperationException("spawn-protection has been moved to a per-world config!");
    }

    public boolean a(World world, int i0, int i1, int i2, EntityPlayer entityplayer) {
        WorldConfiguration cfg = Configuration.getWorldConfig(world.getCanaryWorld().getFqName());
        if (world.t.i != 0) {
            return false;
            // } else if (this.ax().i().isEmpty()) { // CanaryMod: Empty Ops list shouldn't break spawn protections...
            // return false;
        }
        else if (this.ax().d(entityplayer.b_())) {
            return false;
        }
        else if (cfg.getSpawnProtectionSize() <= 0) {
            return false;
        }
        else {
            ChunkCoordinates chunkcoordinates = world.J();
            int i3 = MathHelper.a(i0 - chunkcoordinates.a);
            int i4 = MathHelper.a(i2 - chunkcoordinates.c);
            int i5 = Math.max(i3, i4);

            return i5 <= cfg.getSpawnProtectionSize();
        }
    }

    public int l() {
        // MERGE: This must be in server.cfg instead! XXX
        // return this.p.a("op-permission-level", 4);
        return 4;
    }

    public void d(int i0) {
        super.d(i0);
        ServerConfiguration cfg = Configuration.getServerConfig();
        cfg.setPlayerIdleTimeout(i0);
        //this.p.a("player-idle-timeout", Integer.valueOf(i0));
        //this.a();
    }

    public boolean ar() {
        //return this.l.a("announce-player-achievements", true);
        return Configuration.getServerConfig().getAnnounceAchievements();
    }

    public ServerConfigurationManager af() {
        return this.ax();
    }

    @Override
    public void reload() {/* WorldConfiguration defWorld = Configuration.getWorldConfig(Configuration.getServerConfig().getDefaultWorldName());
                          * // this.d = new OPropertyManager(new File("server.properties"));
                          * this.y = Configuration.getNetConfig().getBindIp();
                          * this.n = Configuration.getNetConfig().isOnlineMode();
                          * this.o = defWorld.canSpawnAnimals();
                          * this.p = defWorld.canSpawnNpcs();
                          * this.q = defWorld.isPvpEnabled();
                          * this.r = defWorld.isFlightAllowed();
                          * this.s = Configuration.getServerConfig().getMotd();
                          * this.z = Configuration.getNetConfig().getPort();
                          * this.t = defWorld.getMaxBuildHeight();
                          * this.t = (this.t + 8) / 16 * 16;
                          * this.t = OMathHelper.a(this.t, 64, 256);
                          * // TODO Update worlds (??) */
    }
}
