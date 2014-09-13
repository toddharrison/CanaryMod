package net.minecraft.command.server;

import net.canarymod.api.world.CanaryWorld;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;

public class CommandSaveOff extends CommandBase {

    public String c() {
        return "save-off";
    }

    public String c(ICommandSender icommandsender) {
        return "commands.save-off.usage";
    }

    public void b(ICommandSender icommandsender, String[] astring) {
        MinecraftServer minecraftserver = MinecraftServer.I();
        boolean flag0 = false;

        // CanaryMod: Fix for MultiWorld
        for (net.canarymod.api.world.World w : minecraftserver.worldManager.getAllWorlds()) {
            WorldServer worldserver = (WorldServer) ((CanaryWorld) w).getHandle();

            if (worldserver != null && !worldserver.c) {
                worldserver.c = true;
                flag0 = true;
            }
        }

        if (flag0) {
            a(icommandsender, this, "commands.save.disabled", new Object[0]);
        }
        else {
            throw new CommandException("commands.save-off.alreadyOff", new Object[0]);
        }
    }
}
