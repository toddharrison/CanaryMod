package net.canarymod.api;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import net.canarymod.Canary;
import net.canarymod.api.entity.CanaryEntity;
import net.canarymod.api.entity.Entity;
import net.canarymod.api.entity.living.humanoid.CanaryPlayer;
import net.canarymod.api.entity.living.humanoid.Player;
import net.canarymod.api.packet.CanaryPacket;
import net.canarymod.api.packet.Packet;
import net.canarymod.api.world.CanaryWorld;
import net.canarymod.api.world.World;

import java.util.ArrayList;
import java.util.List;

import static net.canarymod.Canary.log;

public class CanaryEntityTracker implements EntityTracker {
    private final net.minecraft.entity.EntityTracker tracker;
    private final List<Player> hiddenGlobal;
    private final ArrayListMultimap<Player, Player> hiddenPlayersMap;

    private final CanaryWorld dim;

    public CanaryEntityTracker(net.minecraft.entity.EntityTracker tracker, CanaryWorld dim) {
        this.hiddenGlobal = Lists.newArrayList();
        this.hiddenPlayersMap = ArrayListMultimap.create();
        this.tracker = tracker;
        this.dim = dim;
    }

    public net.minecraft.entity.EntityTracker getHandle() {
        return tracker;
    }

    @Override
    public void trackEntity(Entity entity) {
        try {
            tracker.a(((CanaryEntity)entity).getHandle());
        }
        catch (Exception e) {
            log.error("Entitytracker error.", e);
        }

    }

    @Override
    public void untrackEntity(Entity entity) {
        tracker.b(((CanaryEntity)entity).getHandle());

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

        synchronized (tracker.getTrackedEntities()) { // Synchronize set access
            for (net.minecraft.entity.EntityTrackerEntry e : tracker.getTrackedEntities()) {
                entities.add(e.getCanaryTrackerEntry().getEntity());
            }
        }
        return entities;
    }

    @Override
    public void hidePlayer(Player player, Player toHide) {
        if (player != null && toHide != null && !player.equals(toHide)) {
            if (!hiddenPlayersMap.containsKey(player) || !hiddenPlayersMap.get(player).contains(toHide)) {
                hiddenPlayersMap.get(player).add(toHide);

                net.minecraft.entity.EntityTrackerEntry entry = tracker.getTrackerEntry(toHide.getID());
                entry.a(((CanaryPlayer)player).getHandle());

                PlayerListData listEntry = toHide.getPlayerListData(PlayerListAction.REMOVE_PLAYER);
                player.sendPlayerListData(listEntry);
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

                PlayerListData listEntry = toShow.getPlayerListData(PlayerListAction.ADD_PLAYER);
                player.sendPlayerListData(listEntry);

                net.minecraft.entity.EntityTrackerEntry entry = tracker.getTrackerEntry(toShow.getID());
                entry.b(((CanaryPlayer)player).getHandle());
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
                return hiddenPlayersMap.get(player).contains(isHidden);
            }
        }
        return false;
    }

    public boolean isHiddenGlobally(Player player) {
        return this.hiddenGlobal.contains(player);
    }
    
    public void forceHiddenPlayerUpdate(Player player) {
        for (Player p : hiddenGlobal) {
            this.hidePlayer(player, p);
        }
    }
    
    public void clearHiddenPlayerData(Player player) {
        if (hiddenPlayersMap.containsKey(player)) {
            hiddenPlayersMap.removeAll(player);
        }
        if (hiddenGlobal.contains(player)) {
            hiddenGlobal.remove(player);
        }
    }
}
