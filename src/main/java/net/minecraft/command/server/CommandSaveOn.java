package net.minecraft.command.server;

import net.canarymod.api.world.CanaryWorld;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;

public class CommandSaveOn extends CommandBase {

    public String c() {
        return "save-on";
    }

    public String c(ICommandSender icommandsender) {
        return "commands.save-on.usage";
    }

    public void b(ICommandSender icommandsender, String[] astring) {
        MinecraftServer minecraftserver = MinecraftServer.G();
        boolean flag0 = false;

        // CanaryMod: Fix for MultiWorld
        for (net.canarymod.api.world.World w : minecraftserver.worldManager.getAllWorlds()) {
            WorldServer worldserver = (WorldServer) ((CanaryWorld) w).getHandle();

            if (worldserver != null && worldserver.c) {
                worldserver.c = false;
                flag0 = true;
            }
        }

        if (flag0) {
            a(icommandsender, "commands.save.enabled", new Object[0]);
        }
        else {
            throw new CommandException("commands.save-on.alreadyOn", new Object[0]);
        }
    }
}
