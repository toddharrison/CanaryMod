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

import java.util.ArrayList;
import java.util.List;

public class CanaryEntityTracker implements EntityTracker {
    private net.minecraft.entity.EntityTracker tracker;

    private CanaryWorld dim;

    public CanaryEntityTracker(net.minecraft.entity.EntityTracker tracker, CanaryWorld dim) {
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
        catch(Exception e) {
            Canary.logWarning("Entitytracker error.", e);
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

        for (net.minecraft.entity.EntityTrackerEntry e : tracker.getTrackedEntities()) {
            entities.add(e.getCanaryTrackerEntry().getEntity());
        }
        return entities;
    }

}
