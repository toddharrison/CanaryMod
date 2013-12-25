package net.minecraft.command;

import net.canarymod.api.world.CanaryWorld;
import net.canarymod.api.world.DimensionType;
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

    public void b(ICommandSender icommandsender, String[] astring) {
        this.d();
        a(icommandsender, "commands.downfall.success", new Object[0]);
    }

    protected void d() {
        for (net.canarymod.api.world.World w : MinecraftServer.G().worldManager.getAllWorlds()) {
            WorldInfo worldinfo = ((CanaryWorld) w).getHandle().M();

            if (((CanaryWorld) w).getHandle() != null && w.getType() == DimensionType.fromId(0)) {
                worldinfo.b(!worldinfo.p());
            }
        }
    }
}
