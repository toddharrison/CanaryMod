package net.canarymod;

import net.canarymod.api.inventory.CanaryEnchantment;
import net.canarymod.api.inventory.CanaryItem;
import net.canarymod.api.inventory.Enchantment;
import net.canarymod.api.inventory.Item;
import net.canarymod.serialize.EnchantmentSerializer;
import net.canarymod.serialize.ItemSerializer;
import net.canarymod.util.ConsoleHandlerThread;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.gui.MinecraftServerGui;
import net.minecraft.world.storage.ThreadedFileIOBase;
import net.visualillusionsent.utils.TaskManager;

import java.awt.GraphicsEnvironment;
import java.lang.reflect.Field;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import static net.canarymod.Canary.log;

public class Main {
    private static CanaryMod mod;
    private static boolean nocontrol;
    private static boolean restart = false;
    private static boolean reload = true;

    private static void initBird() {
        // Initialize the bird
        mod = new CanaryMod();
        Canary.setCanary(mod);
        // Add system internal serializers
        Canary.addSerializer(new ItemSerializer(), CanaryItem.class);
        Canary.addSerializer(new ItemSerializer(), Item.class);
        Canary.addSerializer(new EnchantmentSerializer(), CanaryEnchantment.class);
        Canary.addSerializer(new EnchantmentSerializer(), Enchantment.class);
    }

    /**
     * The canary Bootstrap process
     *
     * @param args
     */
    public static void main(String[] args) {
        log.info("Starting: " + Canary.getImplementationTitle() + " " + Canary.getImplementationVersion());
        try {
            Class.forName("org.sqlite.JDBC");
        }
        catch (ClassNotFoundException e) {
        } // Need to initialize the SQLite driver for some reason, initialize here for plugin use as well
        try {
            // Sets the default state for the gui, true is off, false is on
            MinecraftServer.setHeadless(true);
            boolean headless = GraphicsEnvironment.isHeadless();
            for (int index = 0; index < args.length; ++index) {
                String key = args[index];
                String value = index == args.length - 1 ? null : args[index + 1];
                // Replace the nogui option with gui option so the gui is off by default
                if (key.equals("gui") || key.equals("--gui") || key.equals("-gui")) {
                    MinecraftServer.setHeadless(false);
                }
                else if (key.equalsIgnoreCase("noControl") || key.equalsIgnoreCase("-noControl") || key.equalsIgnoreCase("--noControl")) {
                    nocontrol = true;
                }
            }

            // Check if there is a Console in use and if we should launch a GUI as replacement for no console
            if (System.console() == null) {
                if (!headless && !nocontrol) { //if not headless, no console, and not unControlled, launch the GUI
                    MinecraftServer.setHeadless(false);
                }
                else if (nocontrol) { // If allowed to be unControlled, just log a warning
                    log.warn("Server is starting with no Console or GUI be warned!");
                }
                else { // No graphics environment and not allowed to be uncontrolled? KILL IT!
                    log.fatal("Server can not start. No Console or GUI is available to control the server.");
                    System.exit(42);
                }
            }

            if (!MinecraftServer.isHeadless()) {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                }
                catch (UnsupportedLookAndFeelException ignored) {}
                MinecraftServerGui.getLog();
            }

            Field threadPool = TaskManager.class.getDeclaredField("threadPool");
            threadPool.setAccessible(true);
            ScheduledThreadPoolExecutor taskExecutor = (ScheduledThreadPoolExecutor) threadPool.get(null);

            do {
                restart = false;
                if (reload) {
                    initBird(); // Initialize the Bird
                }

                MinecraftServer.main(args); // Boot up the native server

                if (reload) {
                    // They need the server to be set
                    mod.initPermissions();
                    // Initialize providers that require Canary to be set already
                    mod.initUserAndGroupsManager();
                    mod.initKits();
                    // Warps need the DimensionType data which is created upon servre start
                    mod.initWarps();
                    // commands require a valid commandOwner which is the server.
                    // That means for commands to work, we gotta load Minecraft first
                    mod.initCommands();
                    // and finally throw in the MOTDListner
                    mod.initMOTDListener();
                }

                MinecraftServer server = MinecraftServer.G();

                synchronized (server) {
                    while (!server.ae()) {
                        server.wait();
                    }
                }

                // Interrupt the infinisleeper
                for (Thread thread : Thread.getAllStackTraces().keySet()) {
                    if (thread.getName().equals("Server Infinisleeper")) {
                        thread.interrupt();
                    } else if (thread instanceof ConsoleHandlerThread) {
                        thread.interrupt();
                        //((ConsoleHandlerThread) thread).shutdown();
                    }
                }

                taskExecutor.getQueue().clear(); // Clear existing tasks.

                if (restart) {
                    // Use some agressive cleanup, we're in the perfect state to do that right now
                    System.gc();
                    System.runFinalization();
                }
            } while (restart);

            ThreadedFileIOBase.a.keepRunning = false;
            taskExecutor.shutdown();
        }
        catch (Throwable t) {
            log.fatal("Error occurred durring start up, unable to continue... ", t);
            System.exit(42); //Just in case something did manage to start going
        }
    }

    /**
     * Restart the server without killing the JVM
     *
     * @param reloadCanary
     */
    public static void restart(boolean reloadCanary) {
        restart = true;
        reload = reloadCanary;
        Canary.getServer().initiateShutdown("Restarting server");
    }

    public static boolean canRunUncontrolled() {
        return nocontrol;
    }
}
