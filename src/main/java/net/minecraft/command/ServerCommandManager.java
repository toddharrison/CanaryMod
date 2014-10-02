package net.minecraft.command;

import net.canarymod.Canary;

import java.util.Iterator;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandBlockData;
import net.minecraft.command.CommandClearInventory;
import net.minecraft.command.CommandClone;
import net.minecraft.command.CommandCompare;
import net.minecraft.command.CommandDebug;
import net.minecraft.command.CommandDefaultGameMode;
import net.minecraft.command.CommandDifficulty;
import net.minecraft.command.CommandEffect;
import net.minecraft.command.CommandEnchant;
import net.minecraft.command.CommandEntityData;
import net.minecraft.command.CommandExecuteAt;
import net.minecraft.command.CommandFill;
import net.minecraft.command.CommandGameMode;
import net.minecraft.command.CommandGameRule;
import net.minecraft.command.CommandGive;
import net.minecraft.command.CommandHandler;
import net.minecraft.command.CommandHelp;
import net.minecraft.command.CommandKill;
import net.minecraft.command.CommandParticle;
import net.minecraft.command.CommandPlaySound;
import net.minecraft.command.CommandServerKick;
import net.minecraft.command.CommandSetPlayerTimeout;
import net.minecraft.command.CommandSetSpawnpoint;
import net.minecraft.command.CommandShowSeed;
import net.minecraft.command.CommandSpreadPlayers;
import net.minecraft.command.CommandStats;
import net.minecraft.command.CommandTime;
import net.minecraft.command.CommandTitle;
import net.minecraft.command.CommandToggleDownfall;
import net.minecraft.command.CommandTrigger;
import net.minecraft.command.CommandWeather;
import net.minecraft.command.CommandWorldBorder;
import net.minecraft.command.CommandXP;
import net.minecraft.command.IAdminCommand;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.common.CommandReplaceItem;
import net.minecraft.command.server.CommandAchievement;
import net.minecraft.command.server.CommandBanIp;
import net.minecraft.command.server.CommandBanPlayer;
import net.minecraft.command.server.CommandBlockLogic;
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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.rcon.RConConsoleSource;
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
        this.a(new CommandReplaceItem());
        this.a(new CommandStats());
        this.a(new CommandEffect());
        this.a(new CommandEnchant());
        this.a(new CommandParticle());
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
        this.a(new CommandExecuteAt());
        this.a(new CommandTrigger());
        this.a(new CommandAchievement());
        this.a(new CommandSummon());
        this.a(new CommandSetBlock());
        this.a(new CommandFill());
        this.a(new CommandClone());
        this.a(new CommandCompare());
        this.a(new CommandBlockData());
        this.a(new CommandTestForBlock());
        this.a(new CommandMessageRaw());
        this.a(new CommandWorldBorder());
        this.a(new CommandTitle());
        this.a(new CommandEntityData());
        if (MinecraftServer.M().ad()) {
            /* CanaryMod: Disable commands that are overridden
            this.a(new CommandOp());
            this.a(new CommandDeOp());
            */
            this.a(new CommandStop());
            this.a(new CommandSaveAll());
            this.a(new CommandSaveOff());
            this.a(new CommandSaveOn());
            /*
            this.a(new CommandBanIp());
            this.a(new CommandPardonIp());
            this.a(new CommandBanPlayer());
            this.a(new CommandListBans());
            this.a(new CommandPardonPlayer());
            this.a(new CommandServerKick());
            */
            this.a(new CommandListPlayers());
            /*
            this.a(new CommandWhitelist());
            */
            this.a(new CommandSetPlayerTimeout());
            this.a(new CommandNetstat());
        } else {
            this.a(new CommandPublishLocalServer());
        }

        CommandBase.a((IAdminCommand) this);
    }

    public void a(ICommandSender icommandsender, ICommand icommand, int i0, String s0, Object... aobject) {
        boolean flag0 = true;
        MinecraftServer minecraftserver = MinecraftServer.M();

        // CanaryMod: Fix for MultiWorld
        if (icommandsender instanceof CommandBlockLogic && !((CanaryWorld) Canary.getServer().getDefaultWorld()).getHandle().O().b("commandBlockOutput")) {
            flag0 = false;
        }

        ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation("chat.type.admin", new Object[] { icommandsender.d_(), new ChatComponentTranslation(s0, aobject)});

        chatcomponenttranslation.b().a(EnumChatFormatting.GRAY);
        chatcomponenttranslation.b().b(Boolean.valueOf(true));
        if (flag0) {
            Iterator iterator = minecraftserver.an().e.iterator();

            while (iterator.hasNext()) {
                EntityPlayer entityplayer = (EntityPlayer) iterator.next();

                if (entityplayer != icommandsender && minecraftserver.an().g(entityplayer.cc()) && icommand.a(icommandsender)) {
                    entityplayer.a((IChatComponent) chatcomponenttranslation);
                }
            }
        }

        if (icommandsender != minecraftserver && minecraftserver.c[0].Q().b("logAdminCommands")) {
            minecraftserver.a((IChatComponent) chatcomponenttranslation);
        }

        boolean flag1 = minecraftserver.c[0].Q().b("sendCommandFeedback");

        if (icommandsender instanceof CommandBlockLogic) {
            flag1 = ((CommandBlockLogic) icommandsender).m();
        }

        if ((i0 & 1) != 1 && flag1) {
            icommandsender.a(new ChatComponentTranslation(s0, aobject));
        }
    }
}
