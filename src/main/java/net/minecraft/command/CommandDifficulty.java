package net.minecraft.command;

import net.canarymod.api.world.CanaryWorld;
import net.canarymod.api.world.DimensionType;
import net.canarymod.commandsys.TabCompleteHelper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.WorldServer;

import java.util.List;

public class CommandDifficulty extends CommandBase {

    public String c() {
        return "difficulty";
    }

    public int a() {
        return 2;
    }

    public String c(ICommandSender icommandsender) {
        return "commands.difficulty.usage";
    }

    public void b(ICommandSender icommandsender, String[] astring) {
        if (astring.length > 0) {
            EnumDifficulty enumdifficulty = this.h(icommandsender, astring[0]);

            WorldServer worldserver = (WorldServer) icommandsender.d();
            if (astring.length > 1) { // Add an argument to allow picking a world
                boolean loaded = MinecraftServer.G().worldManager.worldIsLoaded(astring[1]);
                if (!loaded) {
                    a(icommandsender, "No world loaded of Name: '%s'", new Object[]{astring[1]});
                    return;
                }
                worldserver = (WorldServer) ((CanaryWorld) MinecraftServer.G().worldManager.getWorld(astring[1], false)).getHandle();
            }
            MinecraftServer.G().a(enumdifficulty, worldserver);
            a(icommandsender, "commands.difficulty.success", new Object[]{new ChatComponentTranslation(enumdifficulty.b(), new Object[0])});
        }
        else {
            throw new WrongUsageException("commands.difficulty.usage", new Object[0]);
        }
    }

    protected EnumDifficulty h(ICommandSender icommandsender, String s0) {
        return !s0.equalsIgnoreCase("peaceful") && !s0.equalsIgnoreCase("p") ? (!s0.equalsIgnoreCase("easy") && !s0.equalsIgnoreCase("e") ? (!s0.equalsIgnoreCase("normal") && !s0.equalsIgnoreCase("n") ? (!s0.equalsIgnoreCase("hard") && !s0.equalsIgnoreCase("h") ? EnumDifficulty.a(a(icommandsender, s0, 0, 3)) : EnumDifficulty.HARD) : EnumDifficulty.NORMAL) : EnumDifficulty.EASY) : EnumDifficulty.PEACEFUL;
    }

    public List a(ICommandSender icommandsender, String[] astring) {
        return astring.length == 1 ? a(astring, new String[]{"peaceful", "easy", "normal", "hard"})
                : astring.length == 2 ? TabCompleteHelper.matchToLoadedWorldOfDimension(astring, DimensionType.NORMAL) // Pass existing worlds to tab complete
                : null;
    }
}
