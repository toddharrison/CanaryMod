package net.minecraft.command;

import net.canarymod.Canary;
import net.canarymod.api.world.CanaryWorld;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings;

import java.util.Iterator;

public class CommandDefaultGameMode extends CommandGameMode {

    public String c() {
        return "defaultgamemode";
    }

    public String c(ICommandSender icommandsender) {
        return "commands.defaultgamemode.usage";
    }

    public void a(ICommandSender icommandsender, String[] astring) throws CommandException {
        if (astring.length <= 0) {
            throw new WrongUsageException("commands.defaultgamemode.usage", new Object[0]);
        }
        else {
            WorldSettings.GameType worldsettings_gametype = this.h(icommandsender, astring[0]);

            // CanaryMod: Multiworld correction, parameter world added
            CanaryWorld world = astring.length > 1 ? (CanaryWorld)Canary.getServer().getWorld(astring[1]) : null;
            this.a(worldsettings_gametype, world == null ? icommandsender.e() : world.getHandle());
            //
            a(icommandsender, this, "commands.defaultgamemode.success", new Object[]{ new ChatComponentTranslation("gameMode." + worldsettings_gametype.b(), new Object[0]) });
        }
    }

    // CanaryMod: Signature change, pass World parameter
    protected void a(WorldSettings.GameType worldsettings_gametype, World world) {
        MinecraftServer minecraftserver = MinecraftServer.M();

        // CanaryMod: Signature change, pass World parameter
        minecraftserver.setDefaultGameMode(worldsettings_gametype, world);
        EntityPlayerMP entityplayermp;

        if (minecraftserver.av()) {
            // CanaryMod: Multiworld correction
            //for (Iterator iterator = MinecraftServer.M().an().e.iterator(); iterator.hasNext(); entityplayermp.O = 0.0F) {
            for (Iterator iterator = world.getCanaryWorld().getPlayerList().iterator(); iterator.hasNext(); entityplayermp.O = 0.0F) {
                entityplayermp = ((net.canarymod.api.entity.living.humanoid.CanaryPlayer)iterator.next()).getHandle();
                entityplayermp.a(worldsettings_gametype);
            }
            //
        }
    }
}
