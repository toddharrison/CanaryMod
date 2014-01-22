package net.minecraft.command;

import net.canarymod.ToolBox;
import net.canarymod.api.world.CanaryWorld;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.GameRules;

import java.util.List;

public class CommandGameRule extends CommandBase {

    public String c() {
        return "gamerule";
    }

    public int a() {
        return 2;
    }

    public String c(ICommandSender icommandsender) {
        return "commands.gamerule.usage";
    }

    public void b(ICommandSender icommandsender, String[] astring) {
        String s0;
        GameRules gamerules = this.d(icommandsender);
        if (astring.length >= 2) { // CanaryMod: Logic redux to support world argument
            s0 = astring[0];
            String s1 = astring[1];
            String s2 = astring.length == 3 ? astring[2] : null;


            if (astring.length == 3 || s1.matches("(true|false)")) {
                String setting = s1;
                if (astring.length == 3) {
                    CanaryWorld cWorld = ((CanaryWorld) MinecraftServer.G().worldManager.getWorld(s1, false));
                    if (cWorld == null) {
                        a(icommandsender, "No world of Name: '%s'", new Object[]{ s1 });
                        return;
                    }
                    gamerules = cWorld.getHandle().N();
                    setting = s2;
                }

                if (gamerules.e(s0)) {
                    gamerules.b(s0, setting);
                    a(icommandsender, "commands.gamerule.success", new Object[0]);
                }
                else {
                    a(icommandsender, "commands.gamerule.norule", new Object[]{ s0 });
                }
            }
            else if (astring.length == 2) {
                gamerules = ((CanaryWorld) MinecraftServer.G().worldManager.getWorld(s1, false)).getHandle().N();
                if (gamerules.e(s0)) {
                    String s3 = gamerules.a(s0);

                    icommandsender.a((new ChatComponentText(s0)).a(" = ").a(s3));
                }
                else {
                    a(icommandsender, "commands.gamerule.norule", new Object[]{ s0 });
                }
            }
        }
        else if (astring.length == 1) {
            s0 = astring[0];

            if (gamerules.e(s0)) {
                String s2 = gamerules.a(s0);

                icommandsender.a((new ChatComponentText(s0)).a(" = ").a(s2));
            }
            else {
                a(icommandsender, "commands.gamerule.norule", new Object[]{ s0 });
            }
        }
        else if (astring.length == 0) {
            icommandsender.a(new ChatComponentText(a(gamerules.b())));
        }
        else {
            throw new WrongUsageException("commands.gamerule.usage", new Object[0]);
        }
    }

    public List a(ICommandSender icommandsender, String[] astring) {
        // CanaryMod: inject loaded world names into Tab Complete
        return astring.length == 1 ? a(astring, this.d(icommandsender).b()) //
                : astring.length == 2 ? a(astring, ToolBox.arrayMerge(new String[]{ "true", "false" }, MinecraftServer.G().worldManager.getLoadedWorldsNamesArray())) //
                : astring.length == 3 && !astring[1].matches("(true|false)") ? a(astring, new String[]{ "true", "false" }) //
                : null;
    }

    private GameRules d(ICommandSender iCommandSender) { // CanaryMod: Signature change to pass the sender reference
        // CanaryMod: Fixes for MultiWorld
        // return MinecraftServer.G().a(0).N();
        return iCommandSender.d().N();
        //
    }
}
