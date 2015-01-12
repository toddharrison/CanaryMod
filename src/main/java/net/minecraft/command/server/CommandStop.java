package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class CommandStop extends CommandBase {

    public String c() {
        return "stop";
    }

    public String c(ICommandSender icommandsender) {
        return "commands.stop.usage";
    }

    public void a(ICommandSender icommandsender, String[] astring) throws CommandException {
        /* CanaryMod: Remove code due to MultiWorld
        if (MinecraftServer.M().c != null) {
            a(icommandsender, this, "commands.stop.start", new Object[0]);
        }
        */

        MinecraftServer.M().u();
    }
}
