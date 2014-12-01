package net.canarymod.api;

import net.canarymod.Canary;
import net.canarymod.api.entity.CanaryEntity;
import net.canarymod.api.entity.Entity;
import net.canarymod.api.entity.living.humanoid.CanaryPlayer;
import net.canarymod.api.entity.living.humanoid.Player;
import net.canarymod.api.packet.CanaryPacket;
import net.canarymod.api.packet.Packet;
import net.canarymod.api.world.CanaryWorld;
import net.canarymod.api.world.World;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
import net.minecraft.network.play.server.S13PacketDestroyEntities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static net.canarymod.Canary.log;

public class CanaryEntityTracker implements EntityTracker {
    private final net.minecraft.entity.EntityTracker tracker;
    private final List<Player> hiddenGlobal;
    private final HashMap<Player, List<Player>> hiddenPlayersMap;

    private final CanaryWorld dim;

    public CanaryEntityTracker(net.minecraft.entity.EntityTracker tracker, CanaryWorld dim) {
        this.hiddenGlobal = new ArrayList<Player>();
        this.hiddenPlayersMap = new HashMap<Player, List<Player>>();
        this.tracker = tracker;
        this.dim = dim;
    }

    public net.minecraft.entity.EntityTracker getHandle() {
        return tracker;
    }

    @Override
    public void trackEntity(Entity entity) {
        try {
            tracker.a((net.minecraft.entity.Entity) ((CanaryEntity) entity).getHandle());
        }
        catch (Exception e) {
            log.error("Entitytracker error.", e);
        }

    }

    @Override
    public void untrackEntity(Entity entity) {
        tracker.b((net.minecraft.entity.Entity) ((CanaryEntity) entity).getHandle());

    }

    @Override
    public void untrackPlayerSymmetrics(Player player) {
        tracker.a((((CanaryPlayer) player).getHandle()));
    }

    @Override
    public void updateTrackedEntities() {
        tracker.a();
    }

    @Override
    public World getAttachedDimension() {
        return dim;
    }

    @Override
    public void sendPacketToTrackedPlayer(Player player, Packet packet) {
        tracker.a(((CanaryEntity) player).getHandle(), ((CanaryPacket) packet).getPacket());
    }

    @Override
    public List<Entity> getTrackedEntities() {
        List<Entity> entities = new ArrayList<Entity>();

        Set<net.minecraft.entity.EntityTrackerEntry> trackedEntities = tracker.getTrackedEntities();
        synchronized (trackedEntities) { // Synchronize set access
            for (net.minecraft.entity.EntityTrackerEntry e : trackedEntities) {
                entities.add(e.getCanaryTrackerEntry().getEntity());
            }
        }
        return entities;
    }

    @Override
    public void hidePlayer(Player player, Player toHide) {
        if (player != null && toHide != null && !player.equals(toHide)) {
            if (hiddenPlayersMap.containsKey(player) && !hiddenPlayersMap.get(player).contains(toHide)) {
                hiddenPlayersMap.get(player).add(toHide);
                player.sendPacket(new CanaryPacket(new S13PacketDestroyEntities(toHide.getID())));
                PlayerListData entry = toHide.getPlayerListData(PlayerListAction.REMOVE_PLAYER);
                player.sendPlayerListData(entry);
            }
        }
    }

    @Override
    public void hidePlayerGlobal(Player toHide) {
        if (toHide != null && !hiddenGlobal.contains(toHide)) {
            hiddenGlobal.add(toHide);
            for (Player p : Canary.getServer().getPlayerList()) {
                if (!p.equals(toHide)) {
                    this.hidePlayer(p, toHide);
                }
            }
        }
    }

    @Override
    public void showPlayer(Player player, Player toShow) {
        if (player != null && toShow != null && !player.equals(toShow)) {
            if (hiddenPlayersMap.containsKey(player) && hiddenPlayersMap.get(player).contains(toShow)) {
                hiddenPlayersMap.get(player).remove(toShow);
                net.minecraft.entity.EntityTrackerEntry t = tracker.getTrackerEntry(toShow.getID());
                player.sendPacket(new CanaryPacket(new S0CPacketSpawnPlayer(((CanaryPlayer)toShow).getHandle())));
                t.b(((CanaryPlayer)player).getHandle());
                PlayerListData entry = toShow.getPlayerListData(PlayerListAction.ADD_PLAYER);
                player.sendPlayerListData(entry);
            }
        }
    }

    @Override
    public void showPlayerGlobal(Player toShow) {
        if (toShow != null && hiddenGlobal.contains(toShow)) {
            hiddenGlobal.remove(toShow);
            for (Player p : Canary.getServer().getPlayerList()) {
                if (!toShow.equals(p)) {
                    this.showPlayer(p, toShow);
                }
            }
        }
    }
    
    @Override
    public boolean isPlayerHidden(Player player, Player isHidden) {
        if (player != null && isHidden != null) {
            if (hiddenPlayersMap.containsKey(player)) {
                if (hiddenPlayersMap.get(player).contains(isHidden)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public void forceHiddenPlayerUpdate(Player player) {
        if (!hiddenPlayersMap.containsKey(player)) {
            hiddenPlayersMap.put(player, new ArrayList<Player>());
        }
        for (Player p : hiddenGlobal) {
            this.hidePlayer(player, p);
        }
    }
    
    public void clearHiddenPlayerData(Player player) {
        if (hiddenPlayersMap.containsKey(player)) {
            hiddenPlayersMap.remove(player);
        }
        if (hiddenGlobal.contains(player)) {
            hiddenGlobal.remove(player);
        }
    }
}
