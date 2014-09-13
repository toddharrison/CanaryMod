package net.minecraft.command;

import net.canarymod.Canary;
import net.canarymod.api.world.CanaryWorld;
import net.minecraft.command.server.*;
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
        if (MinecraftServer.I().X()) {
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
            this.a(new CommandNetstat());
        } else {
            this.a(new CommandPublishLocalServer());
        }

        CommandBase.a((IAdminCommand) this);
    }

    public void a(ICommandSender icommandsender, ICommand icommand, int i0, String s0, Object... aobject) {
        boolean flag0 = true;

        // CanaryMod: Fix for MultiWorld
        if (icommandsender instanceof CommandBlockLogic && !((CanaryWorld) Canary.getServer().getDefaultWorld()).getHandle().O().b("commandBlockOutput")) {
            flag0 = false;
        }

        ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation("chat.type.admin", new Object[]{icommandsender.b_(), new ChatComponentTranslation(s0, aobject)});

        chatcomponenttranslation.b().a(EnumChatFormatting.GRAY);
        chatcomponenttranslation.b().b(Boolean.valueOf(true));
        if (flag0) {
            Iterator iterator = MinecraftServer.I().ah().e.iterator();

            while (iterator.hasNext()) {
                EntityPlayer entityplayer = (EntityPlayer) iterator.next();

                if (entityplayer != icommandsender && MinecraftServer.I().ah().g(entityplayer.bJ()) && icommand.a(entityplayer) && (!(icommandsender instanceof RConConsoleSource) || MinecraftServer.I().m())) {
                    entityplayer.a((IChatComponent) chatcomponenttranslation);
                }
            }
        }

        if (icommandsender != MinecraftServer.I()) {
            MinecraftServer.I().a((IChatComponent) chatcomponenttranslation);
        }

        if ((i0 & 1) != 1) {
            icommandsender.a(new ChatComponentTranslation(s0, aobject));
        }
    }
}
