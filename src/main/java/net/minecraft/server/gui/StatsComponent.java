package net.minecraft.server.gui;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Collections;
import javax.swing.JComponent;
import javax.swing.Timer;

import net.canarymod.Canary;
import net.canarymod.api.world.CanaryWorld;
import net.canarymod.api.world.World;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;


public class StatsComponent extends JComponent {

    private static final DecimalFormat a = new DecimalFormat("########0.000");
    private int[] b = new int[256];
    private int c;
    private String[] d = new String[11];
    private final MinecraftServer e;

    public StatsComponent(MinecraftServer actionevent) {
        this.e = actionevent;
        this.setPreferredSize(new Dimension(456, 246));
        this.setMinimumSize(new Dimension(456, 246));
        this.setMaximumSize(new Dimension(456, 246));
        (new Timer(500, new ActionListener() {

            public void actionPerformed(ActionEvent actionevent) {
                StatsComponent.this.a();
            }
        })).start();
        this.setBackground(Color.BLACK);
    }

    private void a() {

        // Clear what to show on stats
        for (int i = 0; i < this.d.length; ++i) {
            d[i] = null;
        }

        long i0 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        System.gc();
        // CanaryMod
        // Changed how Strings get added from a literal to a var so we don't have to keep track of what is before the worlds
        int i = 0;
        this.d[i++] = "Memory use: " + i0 / 1024L / 1024L + " mb (" + Runtime.getRuntime().freeMemory() * 100L / Runtime.getRuntime().maxMemory() + "% free)";
        this.d[i++] = "Avg tick: " + a.format(this.a(this.e.f) * 1.0E-6D) + " ms";

        // CanaryMod: Multiworld
        Collection<World> worlds = Canary.getServer().getWorldManager().getAllWorlds();
        if (worlds != null) {
            for (World world : worlds) {
                this.d[i] = "World " + world.getName()+ ": " + world.getType().getName() + ":" + world.getType().getId() + " at " + a.format(world.getNanoTick(Canary.getServer().getCurrentTick() % 100) * 1.0E-6D) + " ms/t";
                if (world != null && world.getChunkProvider() != null) {
                    this.d[i] += ", " + world.getChunkProvider().canSave();
                }
                i++; // New Line
                this.d[i++] = "Entities tracked: " + world.getEntityTracker().getTrackedEntities().size();

            }
        }
        //
        this.repaint();
    }

    private double a(long[] along) {
        long i0 = 0L;

        for (int i1 = 0; i1 < along.length; ++i1) {
            i0 += along[i1];
        }

        return (double) i0 / (double) along.length;
    }

    public void paint(Graphics graphics) {
        graphics.setColor(new Color(16777215));
        graphics.fillRect(0, 0, 456, 246);

        int i0;

        for (i0 = 0; i0 < 256; ++i0) {
            int i1 = this.b[i0 + this.c & 255];

            graphics.setColor(new Color(i1 + 28 << 16));
            graphics.fillRect(i0, 100 - i1, 1, i1);
        }

        graphics.setColor(Color.BLACK);

        for (i0 = 0; i0 < this.d.length; ++i0) {
            String s0 = this.d[i0];

            if (s0 != null) {
                graphics.drawString(s0, 32, 116 + i0 * 16);
            }
        }
    }

}
