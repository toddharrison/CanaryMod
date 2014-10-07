package net.minecraft.command.server;

import net.canarymod.Canary;
import net.canarymod.api.world.CanaryWorld;
import net.canarymod.config.Configuration;
import net.canarymod.hook.command.CommandBlockCommandHook;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ICommandSender;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ReportedException;
import net.minecraft.world.World;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;

public abstract class CommandBlockLogic implements ICommandSender {

    private static final SimpleDateFormat a = new SimpleDateFormat("HH:mm:ss");
    private int b;
    private boolean c = true;
    private IChatComponent d = null;
    private String e = "";
    private String f = "@";
    private final CommandResultStats g = new CommandResultStats();

    public int j() {
        return this.b;
    }

    public IChatComponent k() {
        return this.d;
    }

    public void a(NBTTagCompound nbttagcompound) {
        nbttagcompound.a("Command", this.e);
        nbttagcompound.a("SuccessCount", this.b);
        nbttagcompound.a("CustomName", this.f);
        nbttagcompound.a("TrackOutput", this.c);
        if (this.d != null && this.c) {
            nbttagcompound.a("LastOutput", IChatComponent.Serializer.a(this.d));
        }

        this.g.b(nbttagcompound);
    }

    public void b(NBTTagCompound nbttagcompound) {
        this.e = nbttagcompound.j("Command");
        this.b = nbttagcompound.f("SuccessCount");
        if (nbttagcompound.b("CustomName", 8)) {
            this.f = nbttagcompound.j("CustomName");
        }

        if (nbttagcompound.b("TrackOutput", 1)) {
            this.c = nbttagcompound.n("TrackOutput");
        }

        if (nbttagcompound.b("LastOutput", 8) && this.c) {
            this.d = IChatComponent.Serializer.a(nbttagcompound.j("LastOutput"));
        }

        this.g.a(nbttagcompound);
    }

    public boolean a(int i0, String s0) {
        return i0 <= 2;
    }

    public void a(String s0) {
        this.e = s0;
        this.b = 0;
    }

    public String l() {
        return this.e;
    }

    public void a(World world) {
        if (world.D) {
            this.b = 0;
        }

        MinecraftServer minecraftserver = MinecraftServer.M();

        if (minecraftserver != null && minecraftserver.N() && minecraftserver.aj()) {
            ICommandManager icommandmanager = minecraftserver.O();

            try {
                // CanaryMod: CommandBlockCommand
                new CommandBlockCommandHook(getReference(), this.e.split(" ")).call();
                if (Canary.getServer().consoleCommand(this.e, this.getReference())) { // Redirect for Canary Console Commands too
                    this.b = 1;
                }
                else if (Configuration.getServerConfig().isCommandBlockOpped() || this.getReference().hasPermission("canary.command.vanilla.".concat(this.e.split(" ")[0]))) {
                    this.b = icommandmanager.a(this, this.e);
                }
                else {
                    this.b = 0;
                }
                //
            }
            catch (Throwable throwable) {
                CrashReport crashreport = CrashReport.a(throwable, "Executing command block");
                CrashReportCategory crashreportcategory = crashreport.a("Command to be executed");

                crashreportcategory.a("Command", new Callable() {

                                          public String a() {
                                              return CommandBlockLogic.this.l();
                                          }

                                          public Object call() {
                                              return this.a();
                                          }
                                      }
                                     );
                crashreportcategory.a("Name", new Callable() {

                                          public String a() {
                                              return CommandBlockLogic.this.d_();
                                          }

                                          public Object call() {
                                              return this.a();
                                          }
                                      }
                                     );
                throw new ReportedException(crashreport);
            }
        }
        else {
            this.b = 0;
        }
    }

    public String d_() {
        return this.f;
    }

    public IChatComponent e_() {
        return new ChatComponentText(this.d_());
    }

    public void b(String s0) {
        this.f = s0;
    }

    public void a(IChatComponent ichatcomponent) {
        if (this.c && this.e() != null && !this.e().D) {
            this.d = (new ChatComponentText("[" + a.format(new Date()) + "] ")).a(ichatcomponent);
            this.h();
        }
    }

    public boolean t_() {
        MinecraftServer minecraftserver = MinecraftServer.M();
        // CanaryMod: Multiworld fix ->     ->    ->    ->    ->    here
        return minecraftserver == null || !minecraftserver.N() || ((CanaryWorld)Canary.getServer().getDefaultWorld()).getHandle().Q().b("commandBlockOutput");
    }

    public void a(CommandResultStats.Type commandresultstats_type, int i0) {
        this.g.a(this, commandresultstats_type, i0);
    }

    public abstract void h();

    public void b(IChatComponent ichatcomponent) {
        this.d = ichatcomponent;
    }

    public void a(boolean flag0) {
        this.c = flag0;
    }

    public boolean m() {
        return this.c;
    }

    public boolean a(EntityPlayer entityplayer) {
        if (!entityplayer.by.d) {
            return false;
        }
        else {
            if (entityplayer.e().D) {
                entityplayer.a(this);
            }

            return true;
        }
    }

    public CommandResultStats n() {
        return this.g;
    }

    // CanaryMod: Add method to get CommandBlock reference (Either CanaryCommandBlock or CanaryCommandBlockMinecart)
    public abstract net.canarymod.api.CommandBlockLogic getReference();
    //
}
