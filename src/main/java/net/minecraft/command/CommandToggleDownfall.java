package net.minecraft.command;

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
        this.d(icommandsender);
        a(icommandsender, "commands.downfall.success", new Object[0]);
    }

    protected void d(ICommandSender iCommandSender) { // CanaryMod: Signature Change
        WorldInfo worldinfo = iCommandSender.d().M(); // CanaryMod: Multiworld fix

        if (worldinfo != null && worldinfo.j() == 0) {
            worldinfo.b(!worldinfo.p());
        }
    }
}
