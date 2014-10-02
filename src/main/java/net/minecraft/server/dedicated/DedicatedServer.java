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
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.BlockPos;
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

    private static final Logger j = LogManager.getLogger();
    private final List<ServerCommand> k = Collections.synchronizedList(new ArrayList<ServerCommand>());
    private RConThreadQuery l;
    private RConThreadMain m;
    // CanaryMod - Removed private PropertyManager n;
    private ServerEula o;
    // CanaryMod - Removed private boolean p;
    // CanaryMod - Removed private WorldSettings.GameType q;
    private boolean r;

    private ConsoleReader reader;

    {
        if (!"jline.UnsupportedTerminal".equals(System.getProperty("jline.terminal"))) {
            try {
                reader = new ConsoleReader("Minecraft", System.in, System.out, null);
            }
            catch (IOException e) {
                try {
                    reader = new ConsoleReader("Minecraft", System.in, System.out, new UnsupportedTerminal());
                }
                catch (IOException ex) {
                    i.fatal("Could not initialize ConsoleReader", ex);
                }
            }
        }
    }

    public DedicatedServer(File file1) {
        super(file1, Proxy.NO_PROXY, a);
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

    protected boolean i() throws IOException {
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
                                            }
                                           );

                        try {
                            while (!DedicatedServer.this.am() && DedicatedServer.this.t() && (i0 = reader.readLine()) != null) {
                                Canary.getServer().consoleCommand(i0);
                            }
                        }
                        catch (UserInterruptException e) {
                            reader.shutdown();
                            Canary.commands().parseCommand(Canary.getServer(), "stop", new String[]{ "stop", "Ctrl-C", "at", "console" });
                        }
                    }
                    else {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

                        while (!DedicatedServer.this.am() && DedicatedServer.this.t() && (i0 = reader.readLine()) != null) {
                            Canary.getServer().consoleCommand(i0);
                        }
                    }
                }
                catch (IOException i1) {
                    DedicatedServer.j.error("Exception handling console input", i1);
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

        final org.apache.logging.log4j.core.Logger logger = (org.apache.logging.log4j.core.Logger)LogManager.getRootLogger();
        for (org.apache.logging.log4j.core.Appender appender : logger.getAppenders().values()) {
            if (appender instanceof org.apache.logging.log4j.core.appender.ConsoleAppender) {
                logger.removeAppender(appender);
            }
        }

        new SysOutWriterThread(System.out, this.reader).start();
        // CanaryMod end

        j.info("Starting minecraft server version 1.8");
        if (Runtime.getRuntime().maxMemory() / 1024L / 1024L < 512L) {
            j.warn("To start the server with more ram, launch it as \"java -Xmx1024M -Xms1024M -jar minecraft_server.jar\"");
        }

        j.info("Loading properties");
        // this.l = new PropertyManager(new File("server.properties")); //CanaryMod - Removed
        // CanaryMod use our config
        ServerConfiguration cfg = Configuration.getServerConfig();
        this.o = new ServerEula(new File("eula.txt"));
        if (!this.o.a()) {
            j.info("You need to agree to the EULA in order to run the server. Go to eula.txt for more info.");
            this.o.b();
            return false;
        }
        else {
            if (this.S()) {
                this.c("127.0.0.1");
            }
            else {
                this.d(cfg.isOnlineMode());
                this.c(cfg.getBindIp());
            }
            // CanaryMod: Removed world-dependent settings
            this.m(cfg.getMotd());
            this.a_(cfg.getTexturePack());
            this.d(cfg.getPlayerIdleTimeout());
            InetAddress inetaddress = null;

            if (this.s().length() > 0) {
                inetaddress = InetAddress.getByName(this.s());
            }

            if (this.Q() < 0) {
                this.b(cfg.getPort());
            }

            j.info("Generating keypair");
            this.a(CryptManager.b());
            j.info("Starting Minecraft server on " + (this.s().length() == 0 ? "*" : this.s()) + ":" + this.Q());

            try {
                this.ao().a(inetaddress, this.Q());
            }
            catch (IOException ioexception1) {
                j.warn("**** FAILED TO BIND TO PORT!");
                j.warn("The exception was: {}", new Object[]{ ioexception1.toString() });
                j.warn("Perhaps a server is already running on that port?");
                return false;
            }

            if (!this.ae()) {
                j.warn("**** SERVER IS RUNNING IN OFFLINE/INSECURE MODE!");
                j.warn("The server will make no attempt to authenticate usernames. Beware.");
                j.warn("While this makes the game possible to play without internet access, it also opens up the ability for hackers to connect with any username they choose.");
                j.warn("To change this, set \"online-mode\" to \"true\" in the server.properties file.");
            }

            if (this.aP()) {
                this.aD().c();
            }

            /* CanaryMod: Convert unnessary
            if (!PreYggdrasilConverter.a(this.n)) {
                return false;
            } else {
            */
            this.a((ServerConfigurationManager)(new DedicatedPlayerList(this)));
            long i1 = System.nanoTime();

            if (this.T() == null) {
                this.k(Configuration.getServerConfig().getDefaultWorldName()); // CanaryMod use our world config
            }
            // CanaryMod use or configurations instead of native ones
            WorldConfiguration worldcfg = Configuration.getWorldConfig(this.O() + "_NORMAL");

            String s1 = worldcfg.getWorldSeed(); // this.n.a("level-seed", "");
            String s2 = worldcfg.getWorldType().toString(); // this.n.a("level-type", "DEFAULT");
            String s3 = worldcfg.getGeneratorSettings(); // this.n.a("generator-settings", "");
            long i2 = (new Random()).nextLong();

            if (s1.length() > 0) {
                try {
                    long i3 = Long.parseLong(s1);

                    if (i3 != 0L) {
                        i2 = i3;
                    }
                }
                catch (NumberFormatException numberformatexception) {
                    i2 = (long)s1.hashCode();
                }
            }

            WorldType worldtype = WorldType.a(s2);

            if (worldtype == null) {
                worldtype = WorldType.b;
            }

            this.az();
            this.aj();
            this.p();
            this.ac();
            this.aI();
            this.c(worldcfg.getMaxBuildHeight());
            this.c((this.al() + 8) / 16 << 4);
            this.c(MathHelper.a(this.al(), 64, 256));
            worldcfg.getFile().setInt("max-build-height", this.al());
            // CanaryMod enable plugins here, before the first world is loaded.
            // At this point all bootstrapping should be done and systems should be running
            Canary.enablePlugins();

            if (!MinecraftServer.isHeadless()) {
                // CanaryMod moved GUI start to after plugins enable
                this.aO();
            }

            // CanaryMod changed call to initWorld
            this.initWorld(this.T(), i2, worldtype, net.canarymod.api.world.DimensionType.NORMAL, s3);
            //Load up start-up auto-load enabled worlds
            for (String name : Canary.getServer().getWorldManager().getExistingWorlds()) {
                WorldConfiguration wCfg = Configuration.getWorldConfig(name);
                if (wCfg.startupAutoLoadEnabled()) {
                    this.initWorld(name.replaceAll("_(NORMAL|NETHER|END)", ""), wCfg.getWorldSeed().matches("\\d+") ? Long.valueOf(wCfg.getWorldSeed()) : wCfg.getWorldSeed().hashCode(), WorldType.a(wCfg.getWorldType().toString()), net.canarymod.api.world.DimensionType.fromName(name.replaceAll("^.+_(.+)$", "$1")), wCfg.getGeneratorSettings());
                }
            }
            //
            long i4 = System.nanoTime() - i1;
            String s4 = String.format("%.3fs", new Object[]{ Double.valueOf((double)i4 / 1.0E9D) });

            j.info("Done (" + s4 + ")! For help, type \"help\" or \"?\"");
            if (cfg.isQueryEnabled()) {
                j.info("Starting GS4 status listener");
                this.l = new RConThreadQuery(this);
                this.l.a();
            }

            if (cfg.isRconEnabled()) {
                j.info("Starting remote control listener");
                this.m = new RConThreadMain(this);
                this.m.a();
            }

            if (this.aQ() > 0L) {
                Thread thread1 = new Thread(new ServerHangWatchdog(this));

                thread1.setName("Server Watchdog");
                thread1.setDaemon(true);
                thread1.start();
            }

            return true;
            /*
            }
            */
        }
    }

    public void a(WorldSettings.GameType worldsettings_gametype) {
        //super.a(worldsettings_gametype);
        //this.q = worldsettings_gametype;
        Canary.log.warn("Nope @ DedicatedServer#a(WorldSettings.GameType:330")
    }

    @Deprecated //CanaryMod: deprecate method
    public boolean l() {
        throw new UnsupportedOperationException("Generate-structures setting has been moved to a per-world configuration!");
    }

    @Deprecated //CanaryMod: deprecate method
    public WorldSettings.GameType m() {
        throw new UnsupportedOperationException("GameType setting has been moved to a per-world configuration!");
    }

    @Deprecated //CanaryMod: deprecate method
    public EnumDifficulty n() {
        throw new UnsupportedOperationException("Difficulty setting has been moved to a per-world configuration!");
    }

    @Deprecated //CanaryMod: deprecate method
    public boolean o() {
        throw new UnsupportedOperationException("Hardcoremode setting has been moved to a per-world configuration!");
    }

    protected void a(CrashReport crashreport) {
        while (this.t()) {
            this.aM();

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
                          }
                         );
        crashreport.g().a("Type", new Callable() {

                              public String call() {
                                  return "Dedicated Server (map_server.txt)";
                              }
                          }
                         );
        return crashreport;
    }

    protected void x() {
        System.exit(0);
    }

    public void z() { // CanaryMod: protected => public
        super.z();
        this.aM();
    }

    @Deprecated //CanaryMod: deprecate method
    public boolean A() {
        throw new UnsupportedOperationException("allow-nether has been moved to a per-world config");
    }

    @Deprecated //CanaryMod: deprecate method
    public boolean V() {
        throw new UnsupportedOperationException("spawn-monsters has been moved to a per-world config");
    }

    public void a(PlayerUsageSnooper playerusagesnooper) {
        playerusagesnooper.a("whitelist_enabled", Configuration.getServerConfig().isWhitelistEnabled());
        playerusagesnooper.a("whitelist_count", Canary.whitelist().getSize());
        super.a(playerusagesnooper);
    }

    public boolean ac() {
        // CanaryMod moved to config/server.cfg
        return Configuration.getServerConfig().isSnooperEnabled();
    }

    public void a(String s0, ICommandSender icommandsender) {
        this.k.add(new ServerCommand(s0, icommandsender));
    }

    public void aM() {
        while (!this.k.isEmpty()) {
            ServerCommand servercommand = this.k.remove(0);
            // CanaryMod intercept command queue for our own commands
            String[] split = servercommand.a.split(" ");
            if (!Canary.commands().parseCommand(getServer(), split[0], split)) {
                this.O().a(servercommand.b, servercommand.a);
            }
        }
    }

    public boolean ad() {
        return true;
    }

    public DedicatedPlayerList aN() {
        return (DedicatedPlayerList)super.an();
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

    public void aO() {
        try {
            ServerGuiStartHook guiHook = (ServerGuiStartHook)new ServerGuiStartHook(MinecraftServerGui.preInit(this)).call(); // CanaryMod: PreInitialize the GUI without starting it
            if (guiHook.getGui() != null) {
                ((CanaryServer)Canary.getServer()).setCurrentGUI(guiHook.getGui());
            }
            else {
                ((CanaryServer)Canary.getServer()).setCurrentGUI(MinecraftServerGui.a(this));
            }
            Canary.getServer().getCurrentGUI().start();
            this.q = true;
            MinecraftServer.setHeadless(false);
        }
        catch (Exception ex) {
            // Gui Failure detected
            if (Main.canRunUncontrolled() || System.console() != null) { //If we can run uncontrolled, then just send a warning
                Canary.log.warn("GUI failed to start.", ex);
            }
            else { //Can't run uncontrolled, error out and kill ourselves for being failures...
                Canary.log.fatal("GUI failed to start and no console availible to control the server... Exiting...", ex);
                System.exit(42);
            }
        }
    }

    public boolean aq() {
        return this.r;
    }

    public String a(WorldSettings.GameType worldsettings_gametype, boolean flag0) {
        return "";
    }

    public boolean aj() {
        // CanaryMod moved to config/server.cfg
        return Configuration.getServerConfig().isCommandBlockEnabled();
    }

    @Deprecated //CanaryMod: deprecate method
    public int au() {
        throw new UnsupportedOperationException("spawn-protection has been moved to a per-world config!");
    }

    public boolean a(World world, BlockPos blockpos, EntityPlayer entityplayer) {
        WorldConfiguration cfg = Configuration.getWorldConfig(world.getCanaryWorld().getFqName());
        if (world.t.q() != 0) {
            return false;
            // } else if (this.ax().i().isEmpty()) { // CanaryMod: Empty Ops list shouldn't break spawn protections...
            // return false;
        }
        else if (this.aN().g(entityplayer.cc())) {
            return false;
        }
        else if (cfg.getSpawnProtectionSize() <= 0) {
            return false;
        }
        else {
            BlockPos blockpos1 = world.M();
            int i0 = MathHelper.a(blockpos.n() - blockpos1.n());
            int i1 = MathHelper.a(blockpos.p() - blockpos1.p());
            int i2 = Math.max(i0, i1);

            return i2 <= cfg.getSpawnProtectionSize();
        }
    }

    public int p() {
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

    public int aG() {
        int i0 = Configuration.getServerConfig().getDefaultMaxWorldSize(); //this.n.a("max-world-size", super.aG());

        if (i0 < 1) {
            i0 = 1;
        }
        else if (i0 > super.aG()) {
            i0 = super.aG();
        }

        return i0;
    }

    public boolean az() {
        // CanaryMod: Config override
        //return this.l.a("announce-player-achievements", true);
        return Configuration.getServerConfig().getAnnounceAchievements();
    }

    public int aI() {
        //return this.n.a("network-compression-threshold", super.aI());
        return Configuration.getServerConfig().getNetworkCompressionThreshold();
    }

    protected boolean aP() throws IOException {
        /* CanaryMod: Vanilla Files Update disabled
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
        */
        return true;
    }

    private void aS() {
        try {
            Thread.sleep(5000L);
        }
        catch (InterruptedException interruptedexception) {
            ;
        }
    }

    public long aQ() {
        return Configuration.getServerConfig().getMaxTickTime()//this.n.a("max-tick-time", TimeUnit.MINUTES.toMillis(1L));
    }

    public ServerConfigurationManager an() {
        return this.aN();
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
