package net.canarymod.api;

import net.canarymod.api.entity.living.humanoid.CanaryPlayer;
import net.canarymod.api.entity.living.humanoid.Player;
import net.canarymod.api.world.CanaryWorld;
import net.canarymod.api.world.World;
import net.minecraft.util.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class CanaryPlayerManager implements PlayerManager {
    private net.minecraft.server.management.PlayerManager pm;
    private CanaryWorld dim;

    public CanaryPlayerManager(net.minecraft.server.management.PlayerManager pm, CanaryWorld dimension) {
        this.pm = pm;
        this.dim = dimension;
    }

    @Override
    public void updateMountedMovingPlayer(Player player) {
        pm.c(((CanaryPlayer)player).getHandle());

    }

    @Override
    public void addPlayer(Player player) {
        pm.a(((CanaryPlayer)player).getHandle());

    }

    @Override
    public void removePlayer(Player player) {
        pm.b(((CanaryPlayer)player).getHandle());

    }

    @Override
    public void markBlockNeedsUpdate(int x, int y, int z) {
        pm.a(new BlockPos(x, y, z));
    }

    @Override
    public List<Player> getManagedPlayers() {
        List<Player> players = new ArrayList<Player>();

        for (net.minecraft.entity.player.EntityPlayerMP player : pm.getManagedPlayers()) {
            players.add(player.getPlayer());
        }
        return players;
    }

    @Override
    public World getAttachedDimension() {
        return dim;
    }

    @Override
    public int getMaxTrackingDistance() {
        return net.minecraft.server.management.PlayerManager.b(pm.getPlayerViewRadius());
    }

    public net.minecraft.server.management.PlayerManager getHandle() {
        return pm;
    }

}
