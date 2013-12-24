package net.minecraft.server.dedicated;

//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.PrintWriter;
//import java.util.Iterator;

import net.canarymod.Canary;
import net.canarymod.config.Configuration;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

public class DedicatedPlayerList extends ServerConfigurationManager {

    // CanaryMod removed whitelist
    // CanaryMod: removed ops

    public DedicatedPlayerList(DedicatedServer dedicatedserver) {
        super(dedicatedserver);
        // CanaryMod removed whitelist settings
        // CanaryMod: removed ops
        this.c = Configuration.getServerConfig().getViewDistance();
        this.b = Configuration.getServerConfig().getMaxPlayers();
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
        super.a(flag0);
        this.s().a("white-list", (Object) Boolean.valueOf(flag0));
        this.s().a();
    }

    public void a(String s0) {
        super.a(s0);
        //this.u();
    }

    public void b(String s0) {
        super.b(s0);
        //this.u();
    }

    public void g(String s0) {
        // CanaryMod re-route to our whitelist
        Canary.whitelist().removePlayer(s0);
    }

    public void f(String s0) {
        // Canary, re-route to our whitelist
        Canary.whitelist().addPlayer(s0);
    }

    public void j() {
        // Load whitelist
        throw new UnsupportedOperationException("Minecraft whitelist is disabled! Cannot load");
    }

    private void t() {
        // try {
        // this.i().clear();
        // BufferedReader bufferedreader = new BufferedReader(new FileReader(this.d));
        // String s0 = "";
        //
        // while ((s0 = bufferedreader.readLine()) != null) {
        // this.i().add(s0.trim().toLowerCase());
        // }
        //
        // bufferedreader.close();
        // } catch (Exception exception) {
        // this.s().an().b("Failed to load operators list: " + exception);
        // }
    }

    private void u() {
        // try {
        // PrintWriter printwriter = new PrintWriter(new FileWriter(this.d, false));
        // Iterator iterator = this.i().iterator();
        //
        // while (iterator.hasNext()) {
        // String s0 = (String) iterator.next();
        //
        // printwriter.println(s0);
        // }
        //
        // printwriter.close();
        // } catch (Exception exception) {
        // this.s().an().b("Failed to save operators list: " + exception);
        // }
    }

    private void v() {
        // try {
        // this.h().clear();
        // BufferedReader bufferedreader = new BufferedReader(new FileReader(this.e));
        // String s0 = "";
        //
        // while ((s0 = bufferedreader.readLine()) != null) {
        // this.h().add(s0.trim().toLowerCase());
        // }
        //
        // bufferedreader.close();
        // } catch (Exception exception) {
        // this.s().an().b("Failed to load white-list: " + exception);
        // }
    }

    private void w() {
        // try {
        // PrintWriter printwriter = new PrintWriter(new FileWriter(this.e, false));
        // Iterator iterator = this.h().iterator();
        //
        // while (iterator.hasNext()) {
        // String s0 = (String) iterator.next();
        //
        // printwriter.println(s0);
        // }
        //
        // printwriter.close();
        // } catch (Exception exception) {
        // this.s().an().b("Failed to save white-list: " + exception);
        // }
    }

    public boolean c(String s0) {
        return !this.n() || this.d(s0) || Canary.ops().isOpped(s0);
    }

    public DedicatedServer s() {
        return (DedicatedServer) super.p();
    }

    public MinecraftServer p() {
        return this.s();
    }
}
