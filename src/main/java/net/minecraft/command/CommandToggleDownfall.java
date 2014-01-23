package net.minecraft.command;

import net.canarymod.api.world.CanaryWorld;
import net.canarymod.api.world.DimensionType;
import net.canarymod.commandsys.TabCompleteHelper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.storage.WorldInfo;

import java.util.List;

public class CommandToggleDownfall extends CommandBase {

    public String c() {
        return "toggledownfall";
    }

    public int a() {
        return 2;
    }

    public String c(ICommandSender icommandsender) {
        return "commands.downfall.usage";
    }

    public void b(ICommandSender icommandsender, String[] astring) {
        //this.d(); // CanaryMod: logic reimplemented below
        WorldInfo worldinfo = icommandsender.d().M();
        if (astring.length == 1) { // CanaryMod: inject world selection
            boolean loaded = MinecraftServer.G().worldManager.worldIsLoaded(astring[1]);
            if (!loaded) {
                a(icommandsender, "No world loaded of Name: '%s'", new Object[]{ astring[1] });
                return;
            }
            worldinfo = ((CanaryWorld) MinecraftServer.G().worldManager.getWorld(astring[1], false)).getHandle().M();
        }

        a(icommandsender, "commands.downfall.success", new Object[0]);
    }

    /* CanaryMod: Logic disabled and moved above
    protected void d() {
        WorldInfo worldinfo = MinecraftServer.G().b[0].M();

        worldinfo.b(!worldinfo.p());
    }
    */

    public List a(ICommandSender icommandsender, String[] astring) {
        return astring.length == 1 ? TabCompleteHelper.matchToLoadedWorldOfDimension(astring, DimensionType.fromId(0)) : null;
    }
}
