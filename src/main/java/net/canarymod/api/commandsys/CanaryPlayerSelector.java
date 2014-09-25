package net.canarymod.api.commandsys;

import net.canarymod.api.CanaryServer;
import net.canarymod.api.entity.living.humanoid.CanaryPlayer;
import net.canarymod.api.entity.living.humanoid.Player;
import net.canarymod.api.entity.vehicle.CanaryCommandBlockMinecart;
import net.canarymod.api.world.blocks.CanaryCommandBlock;
import net.canarymod.chat.MessageReceiver;
import net.canarymod.commandsys.PlayerSelector;
import net.minecraft.entity.player.EntityPlayerMP;

/**
 * A class that handels the "@" attribute
 * 
 * @author Ehud (EhudB)
 * @author Almog (Swift)
 */
public class CanaryPlayerSelector implements PlayerSelector {

    /**
     * {@inheritDoc}
     */
    @Override
    public Player matchOnePlayer(MessageReceiver caller, String pattern) {
        EntityPlayerMP player = null;
        if (caller instanceof CanaryServer) {
            player = net.minecraft.command.PlayerSelector.a(((CanaryServer) caller).getHandle(), pattern);
        }
        else if (caller instanceof CanaryPlayer) {
            player = net.minecraft.command.PlayerSelector.a(((CanaryPlayer) caller).getHandle(), pattern);
        }
        else if (caller instanceof CanaryCommandBlock) {
            player = net.minecraft.command.PlayerSelector.a(((CanaryCommandBlock) caller).getLogic(), pattern);
        }
        else if (caller instanceof CanaryCommandBlockMinecart) {
            player = net.minecraft.command.PlayerSelector.a(((CanaryCommandBlockMinecart) caller).getLogic(), pattern);
        }
        if (player != null)
            return player.getPlayer();
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player[] matchPlayers(MessageReceiver caller, String pattern) {
        if (caller instanceof CanaryServer) {
            return this.toCanaryPlayers(net.minecraft.command.PlayerSelector.c(((CanaryServer) caller).getHandle(), pattern));
        }
        else if (caller instanceof CanaryPlayer) {
            return this.toCanaryPlayers(net.minecraft.command.PlayerSelector.c(((CanaryPlayer) caller).getHandle(), pattern));
        }
        else if (caller instanceof CanaryCommandBlock) {
            return this.toCanaryPlayers(net.minecraft.command.PlayerSelector.c(((CanaryCommandBlock) caller).getLogic(), pattern));
        }
        else if (caller instanceof CanaryCommandBlockMinecart) {
            return this.toCanaryPlayers(net.minecraft.command.PlayerSelector.c(((CanaryCommandBlockMinecart) caller).getLogic(), pattern));
        }
        else
            return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean matchesMultiplePlayers(String pattern) {
        return net.minecraft.command.PlayerSelector.a(pattern);
    }

    private Player[] toCanaryPlayers(EntityPlayerMP[] players) {
        if (players != null) {
            Player[] canaryPlayers = new Player[players.length];
            for (int i = 0; i < players.length; i++) {
                canaryPlayers[i] = players[i].getPlayer();
            }
            return canaryPlayers;
        }
        return null;
    }

}
