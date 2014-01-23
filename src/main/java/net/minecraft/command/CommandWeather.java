package net.minecraft.command;

import net.canarymod.api.world.CanaryWorld;
import net.canarymod.api.world.DimensionType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.WorldInfo;

import java.util.List;
import java.util.Random;

public class CommandWeather extends CommandBase {

    public String c() {
        return "weather";
    }

    public int a() {
        return 2;
    }

    public String c(ICommandSender icommandsender) {
        return "commands.weather.usage";
    }

    public void b(ICommandSender icommandsender, String[] astring) {
        if (astring.length >= 1 && astring.length <= 3) { // CanaryMod: Max arguments is now 3
            int i0 = (300 + (new Random()).nextInt(600)) * 20;

            // CanaryMod: MutliWorld fix
            WorldServer worldserver = (WorldServer) icommandsender.d();

            if (astring.length > 1) {
                // CanaryMod: inject world selection
                if (astring.length > 2) {
                    boolean loaded = MinecraftServer.G().worldManager.worldIsLoaded(astring[1]);
                    if (!loaded) {
                        a(icommandsender, "No world loaded of Name: '%s'", new Object[]{ astring[1] });
                        return;
                    }
                    worldserver = (WorldServer) ((CanaryWorld) MinecraftServer.G().worldManager.getWorld(astring[1], false)).getHandle();
                    i0 = a(icommandsender, astring[2], 1, 1000000) * 20;
                }
                else if (astring[1].matches("\\d+")) {
                    i0 = a(icommandsender, astring[1], 1, 1000000) * 20;
                }
                else {
                    boolean loaded = MinecraftServer.G().worldManager.worldIsLoaded(astring[1]);
                    if (!loaded) {
                        a(icommandsender, "No world loaded of Name: '%s'", new Object[]{ astring[1] });
                        return;
                    }
                    worldserver = (WorldServer) ((CanaryWorld) MinecraftServer.G().worldManager.getWorld(astring[1], false)).getHandle();
                }
            }

            WorldInfo worldinfo = worldserver.M();
            if (worldserver.M().j() == 0) {
                if ("clear".equalsIgnoreCase(astring[0])) {
                    worldinfo.g(0);
                    worldinfo.f(0);
                    worldinfo.b(false);
                    worldinfo.a(false);
                    a(icommandsender, "commands.weather.clear", new Object[0]);
                }
                else if ("rain".equalsIgnoreCase(astring[0])) {
                    worldinfo.g(i0);
                    worldinfo.b(true);
                    worldinfo.a(false);
                    a(icommandsender, "commands.weather.rain", new Object[0]);
                }
                else {
                    if (!"thunder".equalsIgnoreCase(astring[0])) {
                        throw new WrongUsageException("commands.weather.usage", new Object[0]);
                    }

                    worldinfo.g(i0);
                    worldinfo.f(i0);
                    worldinfo.b(true);
                    worldinfo.a(true);
                    a(icommandsender, "commands.weather.thunder", new Object[0]);
                }
            }
            else {
                throw new WrongUsageException("commands.weather.usage", new Object[0]);
            }
        }
    }

    public List a(ICommandSender icommandsender, String[] astring) {
        return astring.length == 1 ? a(astring, new String[]{ "clear", "rain", "thunder" })
                : astring.length == 2 ? a(astring, MinecraftServer.G().worldManager.getLoadedWorldsNamesArrayOfDimension(DimensionType.fromId(0)))
                : null;
    }
}
