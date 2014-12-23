package net.minecraft.command.server;

import net.canarymod.api.world.CanaryWorld;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.WorldServer;

public class CommandSaveAll extends CommandBase {

    public String c() {
        return "save-all";
    }

    public String c(ICommandSender icommandsender) {
        return "commands.save.usage";
    }

    public void a(ICommandSender icommandsender, String[] astring) throws CommandException {
        MinecraftServer minecraftserver = MinecraftServer.M();

        icommandsender.a(new ChatComponentTranslation("commands.save.start", new Object[0]));
        if (minecraftserver.an() != null) {
            minecraftserver.an().k();
        }

        try {
            int i0;
            WorldServer worldserver;
            boolean flag0;
            // CanaryMod: Fix for MultiWorld
            for (net.canarymod.api.world.World w : minecraftserver.worldManager.getAllWorlds()) {
                worldserver = (WorldServer)((CanaryWorld)w).getHandle();

                if (worldserver != null) {
                    flag0 = worldserver.c;
                    worldserver.c = false;
                    worldserver.a(true, (IProgressUpdate)null);
                    worldserver.c = flag0;
                }
            }

            if (astring.length > 0 && "flush".equals(astring[0])) {
                icommandsender.a(new ChatComponentTranslation("commands.save.flushStart", new Object[0]));

                for (net.canarymod.api.world.World w : minecraftserver.worldManager.getAllWorlds()) {
                    worldserver = (WorldServer)((CanaryWorld)w).getHandle();

                    if (worldserver != null) {
                        flag0 = worldserver.c;
                        worldserver.c = false;
                        worldserver.n();
                        worldserver.c = flag0;
                    }
                }

                icommandsender.a(new ChatComponentTranslation("commands.save.flushEnd", new Object[0]));
            }
        }
        catch (MinecraftException minecraftexception) {
            a(icommandsender, this, "commands.save.failed", new Object[]{ minecraftexception.getMessage() });
            return;
        }

        a(icommandsender, this, "commands.save.success", new Object[0]);
    }
}
