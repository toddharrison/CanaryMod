package net.minecraft.command;

import net.canarymod.api.world.CanaryWorld;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.storage.WorldInfo;

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

    public void a(ICommandSender icommandsender, String[] astring) throws CommandException {
        //this.d(); // CanaryMod: logic reimplemented below
        WorldInfo worldinfo = icommandsender.e().P();
        if (astring.length == 1) { // CanaryMod: inject world selection
            boolean loaded = MinecraftServer.M().worldManager.worldIsLoaded(astring[0]);
            if (!loaded) {
                a(icommandsender, this, "No world loaded of Name: '%s'", new Object[]{astring[0]});
                return;
            }
            worldinfo = ((CanaryWorld)MinecraftServer.M().worldManager.getWorld(astring[0], false)).getHandle().P();
        }
        worldinfo.b(!worldinfo.p());

        a(icommandsender, this, "commands.downfall.success", new Object[0]);
    }

    /* CanaryMod: Logic disabled and moved above
    protected void d() {
        WorldInfo worldinfo = MinecraftServer.M().c[0].P();

        worldinfo.b(!worldinfo.p());
    }
    */
}
