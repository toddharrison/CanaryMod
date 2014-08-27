package net.minecraft.command;

import net.canarymod.api.world.CanaryWorld;
import net.canarymod.api.world.DimensionType;
import net.canarymod.commandsys.TabCompleteHelper;
import net.minecraft.server.MinecraftServer;
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

    public void b(ICommandSender icommandsender, String[] astring) {

        if (astring.length > 1) {
            int i0;

            WorldServer worldserver = (WorldServer) icommandsender.d();
            if (astring.length == 3) {
                boolean loaded = MinecraftServer.G().worldManager.worldIsLoaded(astring[2]);
                if (!loaded) {
                    a(icommandsender, "No world loaded of Name: '%s'", new Object[]{astring[2]});
                    return;
                }
                worldserver = (WorldServer) ((CanaryWorld) MinecraftServer.G().worldManager.getWorld(astring[2], false)).getHandle();
            }

            if (astring[0].equals("set")) {
                if (astring[1].equals("day")) {
                    i0 = 1000;
                }
                else if (astring[1].equals("midday")) { // CanaryMod: add midday
                    i0 = 6000;
                }
                else if (astring[1].equals("night")) {
                    i0 = 13000;
                }
                else if (astring[1].equals("midnight")) { // CanaryMod: add midnight
                    i0 = 18000;
                }
                else {
                    i0 = a(icommandsender, astring[1], 0);
                }

                this.a(icommandsender, i0, worldserver);
                a(icommandsender, "commands.time.set", new Object[]{Integer.valueOf(i0)});
                return;
            }

            if (astring[0].equals("add")) {
                i0 = a(icommandsender, astring[1], 0);
                this.b(icommandsender, i0, worldserver);
                a(icommandsender, "commands.time.added", new Object[]{Integer.valueOf(i0)});
                return;
            }

        }

        // CanaryMod: add check argument
        if (astring.length > 0 && astring[0].equals("check")) {
            WorldServer worldserver = (WorldServer) icommandsender.d();
            if (astring.length == 2) {
                boolean loaded = MinecraftServer.G().worldManager.worldIsLoaded(astring[1]);
                if (!loaded) {
                    a(icommandsender, "No world loaded of Name: '%s'", new Object[]{ astring[1] });
                    return;
                }
                worldserver = (WorldServer) ((CanaryWorld) MinecraftServer.G().worldManager.getWorld(astring[1], false)).getHandle();
            }

            a(icommandsender, "The time in '%s' is " + worldserver.I(), new Object[]{ worldserver.getCanaryWorld().getName() });
            return;
        }
        //

        throw new WrongUsageException("commands.time.usage", new Object[0]);
    }

    public List a(ICommandSender icommandsender, String[] astring) {
        return astring.length == 1 ? a(astring, new String[]{ "set", "add", "check" })//
                : (astring.length == 2 && astring[0].equals("set") ? a(astring, new String[]{ "day", "midday", "night", "midnight" })  // CanaryMod: Add midday and midnight
                : astring.length == 3 || (astring.length == 2 && astring[0].equals("check")) ? TabCompleteHelper.matchToLoadedWorldOfDimension(astring, DimensionType.NORMAL)
                : null);
    }

    protected void a(ICommandSender icommandsender, int i0, WorldServer worldserver) { // CanaryMod: signature change
        /* CanaryMod: moved into signature
        for (int i1 = 0; i1 < MinecraftServer.G().b.length; ++i1) {
            MinecraftServer.G().b[i1].b((long) i0);
        }
        */
        worldserver.b((long) i0);
    }

    protected void b(ICommandSender icommandsender, int i0, WorldServer worldserver) {
        /* CanaryMod: moved into signature
        for (int i1 = 0; i1 < MinecraftServer.G().b.length; ++i1) {
            MinecraftServer.G().b[i1].b((long) i0);
        }
        */
        worldserver.b(worldserver.I() + (long) i0);
    }
}
