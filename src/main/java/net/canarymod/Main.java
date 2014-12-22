package net.canarymod;

import net.canarymod.api.inventory.CanaryEnchantment;
import net.canarymod.api.inventory.CanaryItem;
import net.canarymod.api.inventory.Enchantment;
import net.canarymod.api.inventory.Item;
import net.canarymod.serialize.EnchantmentSerializer;
import net.canarymod.serialize.ItemSerializer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.gui.MinecraftServerGui;
import sun.management.ManagementFactory;
import sun.management.VMManagement;

import javax.swing.*;
import java.awt.*;
import java.lang.management.RuntimeMXBean;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static net.canarymod.Canary.log;

public class Main {
    static JDialog dialog;

    static {
        // For our jar double-clickers add a message about the libraries loading when the user has a graphics environment and the console isn't present
        if (!GraphicsEnvironment.isHeadless() && System.console() == null) {
            java.net.URL imgURL = Main.class.getResource("/assets/favicon.png");
            Icon icon = null;
            if (imgURL != null) {
                icon = new ImageIcon(imgURL, "DA BIRD");
            }

            final JDialog temp = new JDialog();
            final Icon iconF = icon;
            SwingUtilities.invokeLater(new Runnable() {
                                           public void run() {
                                               try {
                                                   final JOptionPane optionPane = new JOptionPane("Please wait while the libraries initialize....", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, iconF, new Object[]{ }, null);
                                                   temp.setTitle("CanaryMod");
                                                   temp.setModal(true);
                                                   temp.setContentPane(optionPane);
                                                   temp.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
                                                   temp.pack();
                                                   temp.setVisible(true);
                                               }
                                               catch (Exception ignored) {
                                               }
                                           }
                                       }
                                      );
            dialog = temp;
        }
    }

    private static boolean nocontrol;

    private static void initBird() {
        // Initialize the bird
        new CanaryMod();
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
        log.info("Canary Path: " + Canary.getCanaryJarPath() + " & Working From: " + Canary.getWorkingPath());

        // Need to initialize the SQLite driver for some reason, initialize here for plugin use as well
        try {
            Class.forName("org.sqlite.JDBC");
        }
        catch (ClassNotFoundException e) {
        }

        try {
            // Sets the default state for the gui, true is off, false is on
            MinecraftServer.setHeadless(true);
            boolean headless = GraphicsEnvironment.isHeadless();
            for (int index = 0; index < args.length; ++index) {
                String key = args[index].toLowerCase().replaceAll("\\-", "");
                String value = index == args.length - 1 ? null : args[index + 1];
                // Replace the nogui option with gui option so the gui is off by default
                if (key.equals("gui") && !headless) { // if environment is headless, this should have no effect
                    MinecraftServer.setHeadless(false);
                }
                else if (key.equals("nocontrol")) {
                    nocontrol = true;
                }
            }

            // Warn the user if there is no known way to control the server
            if (System.console() == null && headless && !nocontrol) {
                log.warn("Server is starting without a known Console or GUI.");
                log.warn("If this is intentional, use the nocontrol argument to supress this warning.");
                log.warn("The process Id of the server might be "+ processId());
            }

            if (!MinecraftServer.isHeadless()) {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                }
                catch (UnsupportedLookAndFeelException ignored) {}
                MinecraftServerGui.getLog();
            }

            initBird(); // Initialize the Bird
            if (dialog != null) {
                // kill dialog
                dialog.setVisible(false);
                dialog.dispose();
                dialog = null;
            }
            MinecraftServer.main(args); // Boot up the native server
        }
        catch (Throwable t) {
            log.fatal("Error occurred during start up, unable to continue... ", t);
            System.exit(42); //Just in case something did manage to start going
        }
    }

    /**
     * Restart the server without killing the JVM
     *
     * @param reloadCanary
     */
    public static void restart(boolean reloadCanary) {
        throw new UnsupportedOperationException("Restart is not implemented yet!");
    }

    public static boolean canRunUncontrolled() {
        return nocontrol;
    }

    private static int processId(){
        // Make an attempt at retrieving the process ID
        try {
            RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
            Field jvmField = runtimeMXBean.getClass().getDeclaredField("jvm");
            jvmField.setAccessible(true);
            VMManagement vmManagement = (VMManagement) jvmField.get(runtimeMXBean);
            Method getProcessIdMethod = vmManagement.getClass().getDeclaredMethod("getProcessId");
            getProcessIdMethod.setAccessible(true);
            return (Integer) getProcessIdMethod.invoke(vmManagement);
        } catch (Throwable thrown) {
            // Probably not a Sun-compatible JVM
        }

        return -1;
    }
}
