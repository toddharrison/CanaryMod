package net.minecraft.command;


import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.WorldServer;

import java.util.List;

public class CommandTime extends CommandBase {

    public String c() {
        return "time";
    }

    public int a() {
        return 2;
    }

    public String c(ICommandSender icommandsender) {
        return "commands.time.usage";
    }

    public void a(ICommandSender icommandsender, String[] astring) throws CommandException {
        if (astring.length > 1) {
            int i0;

            if (astring[0].equals("set")) {
                if (astring[1].equals("day")) {
                    i0 = 1000;
                }
                // CanaryMod: add midday
                else if (astring[1].equals("midday")) {
                    i0 = 6000;
                }
                else if (astring[1].equals("night")) {
                    i0 = 13000;
                }
                // CanaryMod: add midnight
                else if (astring[1].equals("midnight")) {
                    i0 = 18000;
                } else {
                    i0 = a(astring[1], 0);
                }

                this.a(icommandsender, i0);
                a(icommandsender, this, "commands.time.set", new Object[] { Integer.valueOf(i0)});
                return;
            }

            if (astring[0].equals("add")) {
                i0 = a(astring[1], 0);
                this.b(icommandsender, i0);
                a(icommandsender, this, "commands.time.added", new Object[] { Integer.valueOf(i0)});
                return;
            }

            if (astring[0].equals("query")) {
                if (astring[1].equals("daytime")) {
                    i0 = (int) (icommandsender.e().L() % 2147483647L);
                    icommandsender.a(CommandResultStats.Type.QUERY_RESULT, i0);
                    a(icommandsender, this, "commands.time.query", new Object[] { Integer.valueOf(i0)});
                    return;
                }

                if (astring[1].equals("gametime")) {
                    i0 = (int) (icommandsender.e().K() % 2147483647L);
                    icommandsender.a(CommandResultStats.Type.QUERY_RESULT, i0);
                    a(icommandsender, this, "commands.time.query", new Object[] { Integer.valueOf(i0)});
                    return;
                }
            }
        }

        throw new WrongUsageException("commands.time.usage", new Object[0]);
    }

    public List a(ICommandSender icommandsender, String[] astring) {
        // CanaryMod: Add midday and midnight to the tab complete
        return astring.length == 1 ? a(astring, new String[] { "set", "add", "query"}) : (astring.length == 2 && astring[0].equals("set") ? a(astring, new String[] { "day", "night"}) : (astring.length == 2 && astring[0].equals("query") ? a(astring, new String[] { "day", "midday", "night", "midnight" }) : null));
    }

    protected void a(ICommandSender icommandsender, int i0) {
        // CanaryMod: MultiWorld fix
        for (net.canarymod.api.world.World w : MinecraftServer.I().worldManager.getAllWorlds()) {
            WorldServer worldserver = (WorldServer) ((CanaryWorld) w).getHandle();

            if (worldserver != null) {
                worldserver.b((long) i0);
            }
        }

    }

    protected void b(ICommandSender icommandsender, int i0) {
        // CanaryMod: MultiWorld fix
        for (net.canarymod.api.world.World w : MinecraftServer.I().worldManager.getAllWorlds()) {
            WorldServer worldserver = (WorldServer) ((CanaryWorld) w).getHandle();

            if (worldserver != null) {
                worldserver.b(worldserver.L() + (long) i0);
            }
        }

    }
}
