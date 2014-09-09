package net.minecraft.server.dedicated;

//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.PrintWriter;
//import java.util.Iterator;

import com.mojang.authlib.GameProfile;
import net.canarymod.Canary;
import net.canarymod.config.Configuration;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DedicatedPlayerList extends ServerConfigurationManager {

    // CanaryMod removed whitelist
    // CanaryMod: removed ops
    private static final Logger g = LogManager.getLogger();

    public DedicatedPlayerList(DedicatedServer dedicatedserver) {
        super(dedicatedserver);
        // CanaryMod removed whitelist settings
        // CanaryMod: removed ops
        this.a(Configuration.getServerConfig().getViewDistance());
        this.f = Configuration.getServerConfig().getMaxPlayers();
        // CanaryMod removed references to NMS ban system
        // this.t();
        // this.v();
        // this.u();
        //this.e().e();
        //this.e().f();
        //this.f().e();
        //this.f().f();
        //this.t();
        //this.v();
        //this.u();
        //if (!this.f.exists()) {
        //    this.w();
        //}
    }

    public void a(boolean flag0) {
        //super.a(flag0);
        //this.b().a("white-list", (Object) Boolean.valueOf(flag0));
        //this.b().a();
    }

    public void a(GameProfile gameprofile) {
        super.a(gameprofile);
        //this.A();
    }

    public void b(GameProfile gameprofile) {
        super.b(gameprofile);
        //this.A();
    }

    public void c(GameProfile gameprofile) {
        // Canary, re-route to our whitelist
        Canary.whitelist().removePlayer(gameprofile.getId().toString());
    }

    public void d(GameProfile gameprofile) {
        // Canary, re-route to our whitelist
        Canary.whitelist().addPlayer(gameprofile.getId().toString());
    }

    public void a() {
        // Load whitelist
        throw new UnsupportedOperationException("Minecraft whitelist is disabled! Cannot load");
    }

    @Deprecated
    private void v() {
        throw new UnsupportedOperationException("Vanilla BanList is disabled! Use Canary.bans() instead.");
        /* CanaryMod: disabled
        try {
            this.i().f();
        }
        catch (IOException ioexception) {
            g.warn("Failed to save ip banlist: ", ioexception);
        }
        */
    }

    @Deprecated
    private void w() {
        throw new UnsupportedOperationException("Vanilla BanList is disabled! Use Canary.bans() instead.");
        /* CanaryMod: disabled
        try {
            this.h().f();
        }
        catch (IOException ioexception) {
            g.warn("Failed to save user banlist: ", ioexception);
        }
        */
    }

    @Deprecated
    private void x() {
        throw new UnsupportedOperationException("Vanilla BanList is disabled! Use Canary.bans() instead.");
        /* CanaryMod: disabled
        try {
            this.i().g();
        }
        catch (IOException ioexception) {
            g.warn("Failed to load ip banlist: ", ioexception);
        }
        */

    }

    @Deprecated
    private void y() {
        throw new UnsupportedOperationException("Vanilla BanList is disabled! Use Canary.bans() instead.");
        /* CanaryMod: disabled
        try {
            this.h().g();
        }
        catch (IOException ioexception) {
            g.warn("Failed to load user banlist: ", ioexception);
        }
        */
    }

    @Deprecated
    private void z() {
        throw new UnsupportedOperationException("Vanilla OperatorsList is disabled! Use Canary.ops() instead.");
        /* CanaryMod: disabled
        try {
            this.m().g();
        }
        catch (Exception exception) {
            g.warn("Failed to load operators list: ", exception);
        }
        */
    }

    @Deprecated
    private void A() {
        throw new UnsupportedOperationException("Vanilla OperatorsList is disabled! Use Canary.ops() instead.");
        /* CanaryMod: disabled
        try {
            this.m().f();
        }
        catch (Exception exception) {
            g.warn("Failed to save operators list: ", exception);
        }
        */
    }

    @Deprecated
    private void B() {
        throw new UnsupportedOperationException("Vanilla WhiteList is disabled! Use Canary.whitelist() instead.");
        /* CanaryMod: disabled
        try {
            this.k().g();
        }
        catch (Exception exception) {
            g.warn("Failed to load white-list: ", exception);
        }
        */
    }

    private void C() {
        throw new UnsupportedOperationException("Vanilla WhiteList is disabled! Use Canary.whitelist() instead.");
        /* CanaryMod: disabled
        try {
            this.k().f();
        }
        catch (Exception exception) {
            g.warn("Failed to save white-list: ", exception);
        }
        */
    }

    public boolean e(GameProfile gameprofile) {
        return !this.r() || Canary.ops().isOpped(gameprofile.getId().toString());
    }

    public DedicatedServer b() {
        return (DedicatedServer) super.c();
    }

    public MinecraftServer c() {
        return this.b();
    }
}
