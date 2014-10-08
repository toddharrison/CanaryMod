package net.canarymod.api.entity.living.humanoid;

import net.canarymod.Canary;
import net.canarymod.api.entity.CanaryEntity;
import net.canarymod.api.entity.Entity;
import net.canarymod.api.entity.EntityType;
import net.canarymod.api.packet.CanaryPacket;
import net.canarymod.api.world.CanaryWorld;
import net.canarymod.chat.Colors;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
import net.minecraft.network.play.server.S13PacketDestroyEntities;

import java.util.List;

import net.canarymod.api.PathFinder;
import net.canarymod.api.entity.living.humanoid.npc.NPCBehaviorListener;
import net.canarymod.api.entity.living.humanoid.npc.NPCBehaviorRegistry;

/**
 * NonPlayableCharacter implementation
 *
 * @author Jason (darkdiplomat)
 */
public class CanaryNonPlayableCharacter extends CanaryHuman implements NonPlayableCharacter {
    private String prefix = "<" + Colors.ORANGE + "NPC " + Colors.WHITE + "%name> ";

    /**
     * Constructs a new wrapper for EntityNonPlayableCharacter
     *
     * @param npc
     *         the EntityNonPlayableCharacter to wrap
     */
    public CanaryNonPlayableCharacter(EntityNonPlayableCharacter npc) {
        super(npc);
    }

    @Override
    public boolean isNPC() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityType getEntityType() {
        return EntityType.NPC;
    }

    @Override
    public String getFqName() {
        return "NonPlayableCharacter";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void ghost(Player player) {
        player.sendPacket(new CanaryPacket(new S13PacketDestroyEntities(this.getID())));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void haunt(Player player) {
        player.sendPacket(new CanaryPacket(new S0CPacketSpawnPlayer(this.getHandle())));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void lookAtNearest() {
        net.minecraft.entity.Entity target = ((CanaryWorld) this.getWorld()).getHandle().a(EntityPlayerMP.class, this.getHandle().aQ().b(15.0D, 15.0D, 15.0D), this.getHandle());
        if (target != null) {
            lookAt(target.getCanaryEntity());
        }
    }

    private double distanceTo(Player player) {
        double xDiff = player.getX() - getX();
        double yDiff = player.getY() - getY();
        double zDiff = player.getZ() - getZ();

        return xDiff * xDiff + yDiff * yDiff + zDiff * zDiff;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NonPlayableCharacter despawn() {
        getWorld().getEntityTracker().untrackEntity(this);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void chat(String msg) {
        Canary.getServer().broadcastMessage(prefix.replace("%name", getName()) + msg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void privateMessage(Player player, String msg) {
        player.message("(MSG) " + prefix.replace("%name", getName()) + msg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void attackEntity(Entity entity) {
        this.lookAt(entity);// looks at the entity
        this.swingArm();
        this.getHandle().q(((CanaryEntity) entity).getHandle());// attacks the target XXX
    }

    /**
     * {@inheritDoc} Needed to make NPC's turn.
     */
    @Override
    public void moveEntity(double x, double y, double z) {
        this.lookAt(x, y, z);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format("%s[ID=%d, Name=%s]", getFqName(), getID(), getName());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.getID();
        hash = 89 * hash + (this.getName() != null ? this.getName().hashCode() : 0);
        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PathFinder getPathFinder() {
        return this.getHandle().getPathNavigate().getCanaryPathFinderNPC();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NPCBehaviorListener getRegisteredListener(Class<? extends NPCBehaviorListener> clazz) {
        return NPCBehaviorRegistry.getRegisteredListener(clazz, this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<NPCBehaviorListener> geRegisteredListeners() {
        return NPCBehaviorRegistry.getRegisteredListeners(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityNonPlayableCharacter getHandle() {
        return (EntityNonPlayableCharacter) entity;
    }
}
