package net.minecraft.command;

import net.canarymod.api.world.CanaryWorld;
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

    public void b(ICommandSender icommandsender, String[] astring) {
        MinecraftServer minecraftserver = MinecraftServer.G();

        icommandsender.a(new ChatComponentTranslation("commands.save.start", new Object[0]));
        if (minecraftserver.af() != null) {
            minecraftserver.af().g();
        }

        try {
            int i0;
            boolean flag0;
            // CanaryMod: Fix for MultiWorld
            for (net.canarymod.api.world.World w : minecraftserver.worldManager.getAllWorlds()) {
                WorldServer worldserver = (WorldServer) ((CanaryWorld) w).getHandle();

                if (worldserver != null) {
                    flag0 = worldserver.c;
                    worldserver.c = false;
                    worldserver.a(true, (IProgressUpdate) null);
                    worldserver.c = flag0;
                }
            }

            if (astring.length > 0 && "flush".equals(astring[0])) {
                icommandsender.a(new ChatComponentTranslation("commands.save.flushStart", new Object[0]));

                for (net.canarymod.api.world.World w : minecraftserver.worldManager.getAllWorlds()) {
                    WorldServer worldserver = (WorldServer) ((CanaryWorld) w).getHandle();

                    if (worldserver != null) {
                        flag0 = worldserver.c;
                        worldserver.c = false;
                        worldserver.m();
                        worldserver.c = flag0;
                    }
                }

                icommandsender.a(new ChatComponentTranslation("commands.save.flushEnd", new Object[0]));
            }
        }
        catch (MinecraftException minecraftexception) {
            a(icommandsender, "commands.save.failed", new Object[]{ minecraftexception.getMessage() });
            return;
        }

        a(icommandsender, "commands.save.success", new Object[0]);
    }
}
