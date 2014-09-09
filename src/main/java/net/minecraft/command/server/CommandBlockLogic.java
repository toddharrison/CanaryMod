package net.minecraft.command.server;


import net.canarymod.Canary;
import net.canarymod.config.Configuration;
import net.canarymod.hook.command.CommandBlockCommandHook;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ICommandSender;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

import java.text.SimpleDateFormat;
import java.util.Date;


public abstract class CommandBlockLogic implements ICommandSender {

    private static final SimpleDateFormat a = new SimpleDateFormat("HH:mm:ss");
    private int b;
    private boolean c = true;
    private IChatComponent d = null;
    private String e = "";
    private String f = "@";

    public int g() {
        return this.b;
    }

    public IChatComponent h() {
        return this.d;
    }

    public void a(NBTTagCompound nbttagcompound) {
        nbttagcompound.a("Command", this.e);
        nbttagcompound.a("SuccessCount", this.b);
        nbttagcompound.a("CustomName", this.f);
        if (this.d != null) {
            nbttagcompound.a("LastOutput", IChatComponent.Serializer.a(this.d));
        }

        nbttagcompound.a("TrackOutput", this.c);
    }

    public void b(NBTTagCompound nbttagcompound) {
        this.e = nbttagcompound.j("Command");
        this.b = nbttagcompound.f("SuccessCount");
        if (nbttagcompound.b("CustomName", 8)) {
            this.f = nbttagcompound.j("CustomName");
        }

        if (nbttagcompound.b("LastOutput", 8)) {
            this.d = IChatComponent.Serializer.a(nbttagcompound.j("LastOutput"));
        }

        if (nbttagcompound.b("TrackOutput", 1)) {
            this.c = nbttagcompound.n("TrackOutput");
        }

    }

    public boolean a(int i0, String s0) {
        return i0 <= 2;
    }

    public void a(String s0) {
        this.e = s0;
    }

    public String i() {
        return this.e;
    }

    public void a(World world) {
        if (world.E) {
            this.b = 0;
        }

        MinecraftServer minecraftserver = MinecraftServer.G();

        if (minecraftserver != null && minecraftserver.ab()) {
            ICommandManager icommandmanager = minecraftserver.H();

            // CanaryMod: CommandBlockCommand
            new CommandBlockCommandHook(getReference(), this.e.split(" ")).call();
            if (Canary.getServer().consoleCommand(this.e, this.getReference())) { // Redirect for Canary Console Commands too
                this.b = 1;
            }
            else if (Configuration.getServerConfig().isCommandBlockOpped()) {
                this.b = icommandmanager.a(this, this.e);
            }
            else {
                this.b = 0;
            }
            //
        }
        else {
            this.b = 0;
        }

    }

    public String b_() {
        return this.f;
    }

    public IChatComponent c_() {
        return new ChatComponentText(this.b_());
    }

    public void b(String s0) {
        this.f = s0;
    }

    public void a(IChatComponent ichatcomponent) {
        if (this.c && this.d() != null && !this.d().E) {
            this.d = (new ChatComponentText("[" + a.format(new Date()) + "] ")).a(ichatcomponent);
            this.e();
        }

    }

    public abstract void e();

    public void b(IChatComponent ichatcomponent) {
        this.d = ichatcomponent;
    }

    // CanaryMod: Add method to get CommandBlock reference (Either CanaryCommandBlock or CanaryCommandBlockMinecart)
    public abstract net.canarymod.api.CommandBlockLogic getReference();
    //

}
