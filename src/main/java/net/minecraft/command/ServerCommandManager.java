package net.minecraft.command;

import net.canarymod.Canary;
import net.canarymod.api.world.CanaryWorld;
import net.minecraft.command.common.CommandReplaceItem;
import net.minecraft.command.server.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.WorldServer;

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
        }
        else {
            this.a(new CommandPublishLocalServer());
        }

        CommandBase.a((IAdminCommand)this);
    }

    public void a(ICommandSender icommandsender, ICommand icommand, int i0, String s0, Object... aobject) {
        boolean flag0 = true;
        MinecraftServer minecraftserver = MinecraftServer.M();
        WorldServer default0 = (WorldServer)((CanaryWorld)Canary.getServer().getDefaultWorld()).getHandle();

        if (!icommandsender.t_()) {
            flag0 = false;
        }

        ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation("chat.type.admin", new Object[]{ icommandsender.d_(), new ChatComponentTranslation(s0, aobject) });

        chatcomponenttranslation.b().a(EnumChatFormatting.GRAY);
        chatcomponenttranslation.b().b(Boolean.valueOf(true));
        if (flag0) {
            Iterator iterator = minecraftserver.an().e.iterator();

            while (iterator.hasNext()) {
                EntityPlayer entityplayer = (EntityPlayer)iterator.next();

                if (entityplayer != icommandsender && minecraftserver.an().g(entityplayer.cc()) && icommand.a(icommandsender)) {
                    entityplayer.a((IChatComponent)chatcomponenttranslation);
                }
            }
        }

        // CanaryMod: Always log the command feedback
        //if (icommandsender != minecraftserver && default0.Q().b("logAdminCommands")) {
        minecraftserver.a((IChatComponent)chatcomponenttranslation);
        //}

        // CanaryMod: Fix for MultiWorld
        boolean flag1 = default0.Q().b("sendCommandFeedback");

        if (icommandsender instanceof CommandBlockLogic) {
            flag1 = ((CommandBlockLogic)icommandsender).m();
        }

        if ((i0 & 1) != 1 && flag1) {
            icommandsender.a(new ChatComponentTranslation(s0, aobject));
        }
    }
}
