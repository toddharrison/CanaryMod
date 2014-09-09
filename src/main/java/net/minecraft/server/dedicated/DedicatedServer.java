package net.minecraft.server.dedicated;

import jline.UnsupportedTerminal;
import jline.console.ConsoleReader;
import jline.console.UserInterruptException;
import jline.console.completer.Completer;
import net.canarymod.Canary;
import net.canarymod.Main;
import net.canarymod.api.CanaryServer;
import net.canarymod.config.Configuration;
import net.canarymod.config.ServerConfiguration;
import net.canarymod.config.WorldConfiguration;
import net.canarymod.hook.system.ServerGuiStartHook;
import net.canarymod.util.ForwardLogHandler;
import net.canarymod.util.SysOutWriterThread;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.ServerCommand;
import net.minecraft.crash.CrashReport;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.rcon.IServer;
import net.minecraft.network.rcon.RConThreadMain;
import net.minecraft.network.rcon.RConThreadQuery;
import net.minecraft.profiler.PlayerUsageSnooper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerEula;
import net.minecraft.server.gui.MinecraftServerGui;
import net.minecraft.server.management.PreYggdrasilConverter;
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

    private static final Logger i = LogManager.getLogger();
    private final List<ServerCommand> j = Collections.synchronizedList(new ArrayList<ServerCommand>());
    private RConThreadQuery k;
    private RConThreadMain l;
    private PropertyManager m; // CanaryMod: TODO: darkdiplomat- need this a minute, even if it is technically unused
    private ServerEula n;
    // CanaryMod - Removed private boolean o;
    // CanaryMod - Removed private WorldSettings.GameType p;
    private boolean q;

    private ConsoleReader reader;

    {
        if (!"jline.UnsupportedTerminal".equals(System.getProperty("jline.terminal"))) {
            try {
                reader = new ConsoleReader("Minecraft", System.in, System.out, null);
            } catch (IOException e) {
                try {
                    reader = new ConsoleReader("Minecraft", System.in, System.out, new UnsupportedTerminal());
                } catch (IOException ex) {
                    i.fatal("Could not initialize ConsoleReader", ex);
                }
            }
        }
    }

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
                    } catch (InterruptedException interruptedexception) {
                        ;
                    }
                }
            }
        };
    }

    protected boolean e() throws IOException {
        Thread bufferedreader = new Thread("Server console handler") {

            public void run() {
                //BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(System.in));

                String i0;

                try {
                    if (DedicatedServer.this.reader != null) {
                        reader.setPrompt("> ");
                        reader.setHandleUserInterrupt(true);
                        reader.addCompleter(new Completer() {
                            @Override
                            public int complete(String buffer, int cursor, List<CharSequence> candidates) {
                                String toComplete = buffer.substring(0, cursor);
                                String[] args = toComplete.split("\\s+");

                                List<String> completions = Canary.commands().tabComplete(Canary.getServer(), args[0], args);
                                if (completions == null) {
                                    return -1;
                                }

                                candidates.addAll(completions);
                                return candidates.size() > 0 ? toComplete.lastIndexOf(' ') + 1 : -1;
                            }
                        });

                        try {
                            while (!DedicatedServer.this.ag() && DedicatedServer.this.q() && (i0 = reader.readLine()) != null) {
                                Canary.getServer().consoleCommand(i0);
                            }
                        } catch (UserInterruptException e) {
                            reader.shutdown();
                            Canary.commands().parseCommand(Canary.getServer(), "stop", new String[]{"stop", "Ctrl-C", "at", "console"});
                        }
                    } else {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

                        while (!DedicatedServer.this.ag() && DedicatedServer.this.q() && (i0 = reader.readLine()) != null) {
                            Canary.getServer().consoleCommand(i0);
                        }
                    }
                } catch (IOException i1) {
                    DedicatedServer.i.error("Exception handling console input", i1);
                }

            }
        };

        bufferedreader.setDaemon(true);
        bufferedreader.start();

        // CanaryMod start: logging stuff. Much useful. Hello CraftBukkit!
        java.util.logging.Logger global = java.util.logging.Logger.getLogger("");
        global.setUseParentHandlers(false);
        for (java.util.logging.Handler handler : global.getHandlers()) {
            global.removeHandler(handler);
        }
        global.addHandler(new ForwardLogHandler());

        final org.apache.logging.log4j.core.Logger logger = (org.apache.logging.log4j.core.Logger) LogManager.getRootLogger();
        for (org.apache.logging.log4j.core.Appender appender : logger.getAppenders().values()) {
            if (appender instanceof org.apache.logging.log4j.core.appender.ConsoleAppender) {
                logger.removeAppender(appender);
            }
        }

        new SysOutWriterThread(System.out, this.reader).start();
        // CanaryMod end

        i.info("Starting minecraft server version 1.7.10");
        if (Runtime.getRuntime().maxMemory() / 1024L / 1024L < 512L) {
            i.warn("To start the server with more ram, launch it as \"java -Xmx1024M -Xms1024M -jar minecraft_server.jar\"");
        }

        i.info("Loading properties");
        // this.l = new PropertyManager(new File("server.properties")); //CanaryMod - Removed
        // CanaryMod use our config
        ServerConfiguration cfg = Configuration.getServerConfig();
        this.n = new ServerEula(new File("eula.txt"));
        if (!this.n.a()) {
            i.info("You need to agree to the EULA in order to run the server. Go to eula.txt for more info.");
            this.n.b();
            return false;
        } else {
            if (this.N()) {
                this.c("127.0.0.1");
            } else {
                this.d(cfg.isOnlineMode());
                this.c(cfg.getBindIp());
            }
            // CanaryMod: Removed world-dependent settings
            this.n(cfg.getMotd());
            this.m(cfg.getTexturePack());
            this.d(cfg.getPlayerIdleTimeout());
            InetAddress inetaddress = null;

            if (this.p().length() > 0) {
                inetaddress = InetAddress.getByName(this.p());
            }

            if (this.L() < 0) {
                this.b(cfg.getPort());
            }

            i.info("Generating keypair");
            this.a(CryptManager.b());
            i.info("Starting Minecraft server on " + (this.p().length() == 0 ? "*" : this.p()) + ":" + this.L());

            try {
                this.ai().a(inetaddress, this.L());
            } catch (IOException ioexception1) {
                i.warn("**** FAILED TO BIND TO PORT!");
                i.warn("The exception was: {}", new Object[]{ioexception1.toString()});
                i.warn("Perhaps a server is already running on that port?");
                return false;
            }

            if (!this.Y()) {
                i.warn("**** SERVER IS RUNNING IN OFFLINE/INSECURE MODE!");
                i.warn("The server will make no attempt to authenticate usernames. Beware.");
                i.warn("While this makes the game possible to play without internet access, it also opens up the ability for hackers to connect with any username they choose.");
                i.warn("To change this, set \"online-mode\" to \"true\" in the server.properties file.");
            }

            if (this.aE()) {
                this.ax().c();
            }

            if (!PreYggdrasilConverter.a(this.m)) {
                return false;
            } else {
                this.a((ServerConfigurationManager) (new DedicatedPlayerList(this)));
                long i1 = System.nanoTime();

                if (this.O() == null) {
                    this.k(Configuration.getServerConfig().getDefaultWorldName()); // CanaryMod use our world config
                }
                // CanaryMod use or configurations instead of native ones
                WorldConfiguration worldcfg = Configuration.getWorldConfig(this.O() + "_NORMAL");

                String s1 = worldcfg.getWorldSeed(); // this.m.a("level-seed", "");
                String s2 = worldcfg.getWorldType().toString(); // this.m.a("level-type", "DEFAULT");
                String s3 = worldcfg.getGeneratorSettings(); // this.m.a("generator-settings", "");
                long i2 = (new Random()).nextLong();

                if (s1.length() > 0) {
                    try {
                        long i3 = Long.parseLong(s1);

                        if (i3 != 0L) {
                            i2 = i3;
                        }
                    } catch (NumberFormatException numberformatexception) {
                        i2 = (long) s1.hashCode();
                    }
                }

                WorldType worldtype = WorldType.a(s2);

                if (worldtype == null) {
                    worldtype = WorldType.b;
                }

                this.at();
                this.ad();
                this.l();
                this.W();
                this.c(worldcfg.getMaxBuildHeight());
                this.c((this.af() + 8) / 16 << 4);
                this.c(MathHelper.a(this.af(), 64, 256));
                worldcfg.getFile().setInt("max-build-height", this.af());
                // CanaryMod enable plugins here, before the first world is loaded.
                // At this point all bootstrapping should be done and systems should be running
                Canary.enablePlugins();

                if (!MinecraftServer.isHeadless()) {
                    // CanaryMod moved GUI start to after plugins enable
                    this.aD();
                }

                // CanaryMod changed call to initWorld
                this.initWorld(this.O(), i2, worldtype, net.canarymod.api.world.DimensionType.NORMAL, s3);
                //Load up start-up auto-load enabled worlds
                for (String name : Canary.getServer().getWorldManager().getExistingWorlds()) {
                    WorldConfiguration wCfg = Configuration.getWorldConfig(name);
                    if (wCfg.startupAutoLoadEnabled()) {
                        this.initWorld(name.replaceAll("_(NORMAL|NETHER|END)", ""), wCfg.getWorldSeed().matches("\\d+") ? Long.valueOf(wCfg.getWorldSeed()) : wCfg.getWorldSeed().hashCode(), WorldType.a(wCfg.getWorldType().toString()), net.canarymod.api.world.DimensionType.fromName(name.replaceAll("^.+_(.+)$", "$1")), wCfg.getGeneratorSettings());
                    }
                }
                //
                long i4 = System.nanoTime() - i1;
                String s4 = String.format("%.3fs", new Object[]{Double.valueOf((double) i4 / 1.0E9D)});

                i.info("Done (" + s4 + ")! For help, type \"help\" or \"?\"");
                if (cfg.isQueryEnabled()) {
                    i.info("Starting GS4 status listener");
                    this.k = new RConThreadQuery(this);
                    this.k.a();
                }

                if (cfg.isRconEnabled()) {
                    i.info("Starting remote control listener");
                    this.l = new RConThreadMain(this);
                    this.l.a();
                }

                return true;
            }
        }
    }

    @Deprecated //CanaryMod: deprecate method
    public boolean h() {
        throw new UnsupportedOperationException("Generate-structures setting has been moved to a per-world configuration!");
    }

    @Deprecated //CanaryMod: deprecate method
    public WorldSettings.GameType i() {
        throw new UnsupportedOperationException("GameType setting has been moved to a per-world configuration!");
    }

    @Deprecated //CanaryMod: deprecate method
    public EnumDifficulty j() {
        throw new UnsupportedOperationException("Difficulty setting has been moved to a per-world configuration!");
    }

    @Deprecated //CanaryMod: deprecate method
    public boolean k() {
        throw new UnsupportedOperationException("Hardcoremode setting has been moved to a per-world configuration!");
    }

    protected void a(CrashReport crashreport) {
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

    protected void t() {
        System.exit(0);
    }

    public void v() { // CanaryMod: protected => public
        super.v();
        this.aB();
    }

    @Deprecated //CanaryMod: deprecate method
    public boolean w() {
        throw new UnsupportedOperationException("allow-nether has been moved to a per-world config");
    }

    @Deprecated //CanaryMod: deprecate method
    public boolean Q() {
        throw new UnsupportedOperationException("spawn-monsters has been moved to a per-world config");
    }

    public void a(PlayerUsageSnooper playerusagesnooper) {
        playerusagesnooper.a("whitelist_enabled", Configuration.getServerConfig().isWhitelistEnabled());
        playerusagesnooper.a("whitelist_count", Canary.whitelist().getSize());
        super.a(playerusagesnooper);
    }

    public boolean W() {
        // CanaryMod moved to config/server.cfg
        return Configuration.getServerConfig().isSnooperEnabled();
    }

    public void a(String s0, ICommandSender icommandsender) {
        this.j.add(new ServerCommand(s0, icommandsender));
    }

    public void aB() {
        while (!this.j.isEmpty()) {
            ServerCommand servercommand = this.j.remove(0);
            // CanaryMod intercept command queue for our own commands
            String[] split = servercommand.a.split(" ");
            if (!Canary.commands().parseCommand(getServer(), split[0], split)) {
                this.J().a(servercommand.b, servercommand.a);
            }
        }
    }

    public boolean X() {
        return true;
    }

    public DedicatedPlayerList aC() {
        return (DedicatedPlayerList) super.ah();
    }

    @Deprecated //CanaryMod: deprecate method
    public int a(String s0, int i0) {
        throw new UnsupportedOperationException("Setting int values to server.properties is disabled!");
    }

    @Deprecated //CanaryMod: deprecate method
    public String a(String s0, String s1) {
        throw new UnsupportedOperationException("Setting String values to server.properties is disabled!");
    }

    @Deprecated //CanaryMod: deprecate method
    public boolean a(String s0, boolean flag0) {
        throw new UnsupportedOperationException("Setting boolean values to server.properties is disabled!");
    }

    @Deprecated //CanaryMod: deprecate method
    public void a(String s0, Object object) {
        throw new UnsupportedOperationException("Setting Object values to server.properties is disabled!");
    }

    @Deprecated //CanaryMod: deprecate method
    public void a() {
        throw new UnsupportedOperationException("Cannot finish this request. DedicatedServer.a() is deprecated");
    }

    @Deprecated //CanaryMod: deprecate method
    public String b() {
        throw new UnsupportedOperationException("Cannot finish this request. DedicatedServer.b_() is deprecated");
    }

    public void aD() {
        try {
            ServerGuiStartHook guiHook = (ServerGuiStartHook) new ServerGuiStartHook(MinecraftServerGui.preInit(this)).call(); // CanaryMod: PreInitialize the GUI without starting it
            if (guiHook.getGui() != null) {
                ((CanaryServer) Canary.getServer()).setCurrentGUI(guiHook.getGui());
            } else {
                ((CanaryServer) Canary.getServer()).setCurrentGUI(MinecraftServerGui.a(this));
            }
            Canary.getServer().getCurrentGUI().start();
            this.q = true;
            MinecraftServer.setHeadless(false);
        } catch (Exception ex) {
            // Gui Failure detected
            if (Main.canRunUncontrolled() || System.console() != null) { //If we can run uncontrolled, then just send a warning
                Canary.log.warn("GUI failed to start.", ex);
            } else { //Can't run uncontrolled, error out and kill ourselves for being failures...
                Canary.log.fatal("GUI failed to start and no console availible to control the server... Exiting...", ex);
                System.exit(42);
            }
        }
    }

    public boolean ak() {
        return this.q;
    }

    public String a(WorldSettings.GameType worldsettings_gametype, boolean flag0) {
        return "";
    }

    public boolean ad() {
        // CanaryMod moved to config/server.cfg
        return Configuration.getServerConfig().isCommandBlockEnabled();
    }

    @Deprecated //CanaryMod: deprecate method
    public int ao() {
        throw new UnsupportedOperationException("spawn-protection has been moved to a per-world config!");
    }

    public boolean a(World world, int i0, int i1, int i2, EntityPlayer entityplayer) {
        WorldConfiguration cfg = Configuration.getWorldConfig(world.getCanaryWorld().getFqName());
        if (world.t.i != 0) {
            return false;
            // } else if (this.ax().i().isEmpty()) { // CanaryMod: Empty Ops list shouldn't break spawn protections...
            // return false;
        } else if (this.aC().g(entityplayer.bJ())) {
            return false;
        } else if (cfg.getSpawnProtectionSize() <= 0) {
            return false;
        } else {
            ChunkCoordinates chunkcoordinates = world.K();
            int i3 = MathHelper.a(i0 - chunkcoordinates.a);
            int i4 = MathHelper.a(i2 - chunkcoordinates.c);
            int i5 = Math.max(i3, i4);

            return i5 <= cfg.getSpawnProtectionSize();
        }
    }

    public int l() {
        // return this.p.a("op-permission-level", 4);
        return 4; //CanaryMod: Always return Op Level 4, per-player permissions handles the rest
    }

    public void d(int i0) {
        super.d(i0);
        // CanaryMod: Override config
        ServerConfiguration cfg = Configuration.getServerConfig();
        cfg.setPlayerIdleTimeout(i0);
        //this.p.a("player-idle-timeout", Integer.valueOf(i0));
        //this.a();
    }

    public boolean m() {
        //return this.m.a("broadcast-rcon-to-ops", true);
        return true; // TODO NEED SETTING
    }


    public boolean at() {
        // CanaryMod: Config override
        //return this.l.a("announce-player-achievements", true);
        return Configuration.getServerConfig().getAnnounceAchievements();
    }

    protected boolean aE() throws IOException {
        boolean flag0 = false;

        int i0;

        for (i0 = 0; !flag0 && i0 <= 2; ++i0) {
            if (i0 > 0) {
                i.warn("Encountered a problem while converting the user banlist, retrying in a few seconds");
                this.aG();
            }

            flag0 = PreYggdrasilConverter.a((MinecraftServer) this);
        }

        boolean flag1 = false;

        for (i0 = 0; !flag1 && i0 <= 2; ++i0) {
            if (i0 > 0) {
                i.warn("Encountered a problem while converting the ip banlist, retrying in a few seconds");
                this.aG();
            }

            flag1 = PreYggdrasilConverter.b((MinecraftServer) this);
        }

        boolean flag2 = false;

        for (i0 = 0; !flag2 && i0 <= 2; ++i0) {
            if (i0 > 0) {
                i.warn("Encountered a problem while converting the op list, retrying in a few seconds");
                this.aG();
            }

            flag2 = PreYggdrasilConverter.c((MinecraftServer) this);
        }

        boolean flag3 = false;

        for (i0 = 0; !flag3 && i0 <= 2; ++i0) {
            if (i0 > 0) {
                i.warn("Encountered a problem while converting the whitelist, retrying in a few seconds");
                this.aG();
            }

            flag3 = PreYggdrasilConverter.d((MinecraftServer) this);
        }

        boolean flag4 = false;

        for (i0 = 0; !flag4 && i0 <= 2; ++i0) {
            if (i0 > 0) {
                i.warn("Encountered a problem while converting the player save files, retrying in a few seconds");
                this.aG();
            }

            flag4 = PreYggdrasilConverter.a(this, this.m);
        }

        return flag0 || flag1 || flag2 || flag3 || flag4;
    }

    private void aG() {
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException interruptedexception) {
            ;
        }
    }

    public ServerConfigurationManager ah() {
        return this.aC();
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
