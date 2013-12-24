package net.minecraft.command;

import net.canarymod.Canary;
import net.canarymod.api.world.CanaryWorld;
import net.minecraft.command.server.CommandAchievement;
import net.minecraft.command.server.CommandBanIp;
import net.minecraft.command.server.CommandBanPlayer;
import net.minecraft.command.server.CommandBroadcast;
import net.minecraft.command.server.CommandDeOp;
import net.minecraft.command.server.CommandEmote;
import net.minecraft.command.server.CommandListBans;
import net.minecraft.command.server.CommandListPlayers;
import net.minecraft.command.server.CommandMessage;
import net.minecraft.command.server.CommandMessageRaw;
import net.minecraft.command.server.CommandOp;
import net.minecraft.command.server.CommandPardonIp;
import net.minecraft.command.server.CommandPardonPlayer;
import net.minecraft.command.server.CommandPublishLocalServer;
import net.minecraft.command.server.CommandSaveAll;
import net.minecraft.command.server.CommandSaveOff;
import net.minecraft.command.server.CommandSaveOn;
import net.minecraft.command.server.CommandScoreboard;
import net.minecraft.command.server.CommandSetBlock;
import net.minecraft.command.server.CommandSetDefaultSpawnpoint;
import net.minecraft.command.server.CommandStop;
import net.minecraft.command.server.CommandSummon;
import net.minecraft.command.server.CommandTeleport;
import net.minecraft.command.server.CommandTestFor;
import net.minecraft.command.server.CommandTestForBlock;
import net.minecraft.command.server.CommandWhitelist;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

import java.util.Iterator;

public class ServerCommandManager extends CommandHandler implements IAdminCommand {

    public ServerCommandManager() {
        // TODO: Should we disable commands we implemented differently?
        this.a(new CommandTime());
        this.a(new CommandGameMode());
        this.a(new CommandDifficulty());
        this.a(new CommandDefaultGameMode());
        this.a(new CommandKill());
        this.a(new CommandToggleDownfall());
        this.a(new CommandWeather());
        this.a(new CommandXP());
        this.a(new CommandTeleport());
        this.a(new CommandGive());
        this.a(new CommandEffect());
        this.a(new CommandEnchant());
        this.a(new CommandEmote());
        this.a(new CommandShowSeed());
        this.a(new CommandHelp());
        this.a(new CommandDebug());
        this.a(new CommandMessage());
        this.a(new CommandBroadcast());
        this.a(new CommandSetSpawnpoint());
        this.a(new CommandSetDefaultSpawnpoint());
        this.a(new CommandGameRule());
        this.a(new CommandClearInventory());
        this.a(new CommandTestFor());
        this.a(new CommandSpreadPlayers());
        this.a(new CommandPlaySound());
        this.a(new CommandScoreboard());
        this.a(new CommandAchievement());
        this.a(new CommandSummon());
        this.a(new CommandSetBlock());
        this.a(new CommandTestForBlock());
        this.a(new CommandMessageRaw());
        if (MinecraftServer.G().V()) {
            this.a(new CommandOp());
            this.a(new CommandDeOp());
            this.a(new CommandStop());
            this.a(new CommandSaveAll());
            this.a(new CommandSaveOff());
            this.a(new CommandSaveOn());
            this.a(new CommandBanIp());
            this.a(new CommandPardonIp());
            this.a(new CommandBanPlayer());
            this.a(new CommandListBans());
            this.a(new CommandPardonPlayer());
            this.a(new CommandServerKick());
            this.a(new CommandListPlayers());
            this.a(new CommandWhitelist());
            this.a(new CommandSetPlayerTimeout());
        }
        else {
            this.a(new CommandPublishLocalServer());
        }

        CommandBase.a((IAdminCommand) this);
    }

    public void a(ICommandSender icommandsender, int i0, String s0, Object... aobject) {
        boolean flag0 = true;

        // CanaryMod: Fix for MultiWorld
        if (icommandsender instanceof TileEntityCommandBlock && !((CanaryWorld) Canary.getServer().getDefaultWorld()).getHandle().N().b("commandBlockOutput")) {
            flag0 = false;
        }

        ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation("chat.type.admin", new Object[]{ icommandsender.b_(), new ChatComponentTranslation(s0, aobject) });

        chatcomponenttranslation.b().a(EnumChatFormatting.GRAY);
        chatcomponenttranslation.b().b(Boolean.valueOf(true));
        if (flag0) {
            Iterator iterator = MinecraftServer.G().af().a.iterator();

            while (iterator.hasNext()) {
                EntityPlayerMP entityplayermp = (EntityPlayerMP) iterator.next();

                if (entityplayermp != icommandsender && MinecraftServer.G().af().d(entityplayermp.b_())) {
                    entityplayermp.a((IChatComponent) chatcomponenttranslation);
                }
            }
        }

        if (icommandsender != MinecraftServer.G()) {
            MinecraftServer.G().a((IChatComponent) chatcomponenttranslation);
        }

        if ((i0 & 1) != 1) {
            icommandsender.a(new ChatComponentTranslation(s0, aobject));
        }
    }
}
