package net.minecraft.server.gui;


import com.mojang.util.QueueLogAppender;
import net.canarymod.Canary;
import net.canarymod.api.gui.GUIControl;
import net.canarymod.config.Configuration;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.*;


public class MinecraftServerGui extends JComponent implements GUIControl {

    private static final Font a = new Font("Monospaced", 0, 12);
    private static final Logger b = LogManager.getLogger();
    private static MinecraftServerGui minecraftservergui; // CanaryMod keeping this in the static so it can work right.
    private static final JTextArea jtextarea = new JTextArea(); // CanaryMod This is done so we can log
    private static JComponent logAndChat = c(); // CanaryMod
    private static JFrame jframe; // CanaryMod
    private DedicatedServer c;

    public static GUIControl a(final DedicatedServer dedicatedserver) { // Signature Changed to return GUIControl
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception interruptedexception) {
            ;
        }

        jframe = new JFrame("CanaryMod: Minecraft server"); // CanaryMod lets brand this Window!!

        jframe.add(minecraftservergui); // CanaryMod Replaced the variable with the one added
        jframe.pack();
        jframe.setLocationRelativeTo((Component) null);
        jframe.setVisible(true);
        jframe.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent windowevent) {
                dedicatedserver.r();

                while (!dedicatedserver.aq()) {
                    try {
                        Thread.sleep(100L);
                    }
                    catch (InterruptedException interruptedexception) {
                        // CanaryMod Debug catcher thingy
                        if (Configuration.getServerConfig().isDebugMode()) {
                            interruptedexception.printStackTrace();
                        }
                    }
                }

                System.exit(0);
            }

            // CanaryMod
            @Override
            public void windowOpened(WindowEvent e) {
                jframe.setIconImage(new ImageIcon(getClass().getResource("/assets/favicon.png")).getImage());
            }
            // End
        });
        return minecraftservergui; // CanaryMod need to return the GUIControl
    }

    public MinecraftServerGui(DedicatedServer dedicatedserver) {
        this.c = dedicatedserver;
        this.setPreferredSize(new Dimension(854, 480));
        this.setLayout(new BorderLayout());

        try {
            this.add(logAndChat, "Center"); // Change use of c() to static logAndChat value
            this.add(this.a(), "West");
        }
        catch (Exception exception) {
            b.error("Couldn\'t build server GUI", exception);
        }
        minecraftservergui = this;

    }

    private JComponent a() {
        JPanel jpanel = new JPanel(new BorderLayout());

        jpanel.add(new StatsComponent(this.c), "North");
        jpanel.add(this.b(), "Center");
        jpanel.setBorder(new TitledBorder(new EtchedBorder(), "Stats"));
        return jpanel;
    }

    private JComponent b() {
        PlayerListComponent playerlistcomponent = new PlayerListComponent(this.c);
        JScrollPane jscrollpane = new JScrollPane(playerlistcomponent, 22, 30);

        jscrollpane.setBorder(new TitledBorder(new EtchedBorder(), "Players"));
        return jscrollpane;
    }

    private static JComponent c() { // Signature Changed to static
        JPanel s1 = new JPanel(new BorderLayout());
        final JScrollPane jscrollpane = new JScrollPane(jtextarea, 22, 30);

        jtextarea.setEditable(false);
        jtextarea.setFont(a);
        final JTextField jtextfield = new JTextField();

        jtextfield.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent s1) {
                String s0 = jtextfield.getText().trim();

                if (s0.length() > 0) {
                    // CanaryMod replaced how commands are sent to the server
                    // Not sure why this line was not ((DedicatedServer) MinecraftServer.G()).a(s0, (ICommandSender) MinecraftServer.G());
                    // That would have removed the need for the storage of the object here and they do it for the the 2nd part why not the first lol
                    // MinecraftServerGui.this.c.a(s0, (ICommandSender) MinecraftServer.G());
                    Canary.getServer().consoleCommand(s0);
                }

                jtextfield.setText("");
            }
        });
        jtextarea.addFocusListener(new FocusAdapter() {

            public void focusGained(FocusEvent s1) {
            }
        });
        s1.add(jscrollpane, "Center");
        s1.add(jtextfield, "South");
        s1.setBorder(new TitledBorder(new EtchedBorder(), "Log and chat"));
        Thread thread = new Thread(new Runnable() {

            public void run() {
                String s1;

                while ((s1 = QueueLogAppender.getNextLogEvent("ServerGuiConsole")) != null) {
                    a(jtextarea, jscrollpane, s1);
                }

            }
        });

        thread.setDaemon(true);
        thread.start();
        return s1;
    }

    public static void a(final JTextArea jtextarea, final JScrollPane jscrollpane, final String s0) {
        if (!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeLater(new Runnable() {

                public void run() {
                    a(jtextarea, jscrollpane, s0);
                }
            });
        }
        else {
            Document document = jtextarea.getDocument();
            JScrollBar jscrollbar = jscrollpane.getVerticalScrollBar();
            boolean flag0 = false;

            if (jscrollpane.getViewport().getView() == jtextarea) {
                flag0 = (double) jscrollbar.getValue() + jscrollbar.getSize().getHeight() + (double) (a.getSize() * 4) > (double) jscrollbar.getMaximum();
            }

            try {
                document.insertString(document.getLength(), s0, (AttributeSet) null);
            }
            catch (BadLocationException badlocationexception) {
                ;
            }

            if (flag0) {
                jscrollbar.setValue(Integer.MAX_VALUE);
            }

        }
    }

    @Override
    public void closeWindow() {
        if (jframe != null) {
            jframe.dispose();
        }
        jframe = null;
    }

    @Override
    public void start() {
        a((DedicatedServer) MinecraftServer.G());
    }

    /**
     * Sets up the GUI with out starting it
     *
     * @param dedicatedserver
     *
     * @return
     */
    public static final GUIControl preInit(DedicatedServer dedicatedserver) {
        return new MinecraftServerGui(dedicatedserver);
    }

    /**
     * Gets the Contents of the log up to this point
     *
     * @return current log
     */
    public static String getLog() {
        return jtextarea.getText();
    }

}
