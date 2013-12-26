package net.canarymod.api.factory;

import net.canarymod.Canary;
import net.canarymod.api.DataWatcher;
import net.canarymod.api.entity.Entity;
import net.canarymod.api.entity.XPOrb;
import net.canarymod.api.entity.hanging.Painting;
import net.canarymod.api.entity.living.LivingBase;
import net.canarymod.api.entity.living.humanoid.Human;
import net.canarymod.api.entity.living.humanoid.Player;
import net.canarymod.api.inventory.Item;
import net.canarymod.api.nbt.CompoundTag;
import net.canarymod.api.packet.InvalidPacketConstructionException;
import net.canarymod.api.packet.Packet;
import net.canarymod.api.potion.PotionEffect;
import net.canarymod.api.world.Chunk;
import net.canarymod.api.world.World;
import net.canarymod.api.world.position.Position;
import net.canarymod.api.world.position.Vector3D;

import java.util.List;

/**
 * Packet Factory implementation
 *
 * @author Jason (darkdiplomat)
 */
public class CanaryPacketFactory implements PacketFactory {
    private final static String toofewargs = "Not enough arguments (Expected: %d Got: %d)";

    @SuppressWarnings({ "unchecked" })
    public Packet createPacket(int id, Object... args) throws Exception {
        if (args == null || args.length < 1) {
            throw new IllegalArgumentException("Arguments cannot be null or empty");
        }
        switch (id) {

            default:
                throw new InvalidPacketConstructionException(id, Integer.toHexString(id), "ALL", "Packet Factory is currently broken");
        }
    }

    @Override
    public Packet updateTime(long world_age, long time) {
        try {
            return createPacket(3, world_age, time);
        }
        catch (Exception ex) {
            Canary.logDebug("Failed to construct a UpdateTime packet", ex);
            return null;
        }
    }

    @Override
    public Packet playerEquipment(int entityID, int slot, Item item) {
        try {
            return createPacket(5, entityID, slot, item);
        }
        catch (Exception ex) {
            Canary.logDebug("Failed to construct a PlayerEquipment packet", ex);
            return null;
        }
    }

    @Override
    public Packet spawnPosition(int x, int y, int z) {
        try {
            return createPacket(6, x, y, z);
        }
        catch (Exception ex) {
            Canary.logDebug("Failed to construct a SpawnPosition packet", ex);
            return null;
        }
    }

    @Override
    public Packet updateHealth(float health, int foodLevel, float saturation) {
        try {
            return createPacket(8, health, foodLevel, saturation);
        }
        catch (Exception ex) {
            Canary.logDebug("Failed to construct a UpdateHealth packet", ex);
            return null;
        }
    }

    @Override
    public Packet playerPositionLook(double x, double stance, double y, double z, float yaw, float pitch, boolean onGround) {
        try {
            return createPacket(13, x, stance, y, z, yaw, pitch, onGround);
        }
        catch (Exception ex) {
            Canary.logDebug("Failed to construct a PlayerPositionLook packet", ex);
            return null;
        }
    }

    @Override
    public Packet heldItemChange(int slot) {
        try {
            return createPacket(16, slot);
        }
        catch (Exception ex) {
            Canary.logDebug("Failed to construct a HeldItemChange packet", ex);
            return null;
        }
    }

    @Override
    public Packet useBed(Player player, int x, int y, int z) {
        try {
            return createPacket(17, player, x, y, z);
        }
        catch (Exception ex) {
            Canary.logDebug("Failed to construct a UseBed packet", ex);
            return null;
        }
    }

    @Override
    public Packet animation(Player player, int animation) {
        try {
            return createPacket(18, player, animation);
        }
        catch (Exception ex) {
            Canary.logDebug("Failed to construct a Animation packet", ex);
            return null;
        }
    }

    @Override
    public Packet spawnNamedEntity(Human human) {
        try {
            return createPacket(20, human);
        }
        catch (Exception ex) {
            Canary.logDebug("Failed to construct a SpawnNamedEntity packet", ex);
            return null;
        }
    }

    @Override
    public Packet collectItem(int entityItemID, int collectorID) {
        try {
            return createPacket(22, entityItemID, collectorID);
        }
        catch (Exception ex) {
            Canary.logDebug("Failed to construct a CollectItem packet", ex);
            return null;
        }
    }

    @Override
    public Packet spawnObjectVehicle(Entity entity, int objectID, int throwerID) {
        try {
            return createPacket(23, entity, objectID, throwerID);
        }
        catch (Exception ex) {
            Canary.logDebug("Failed to construct a SpawnObjectVehicle packet", ex);
            return null;
        }
    }

    @Override
    public Packet spawnMob(LivingBase livingbase) {
        try {
            return createPacket(24, livingbase);
        }
        catch (Exception ex) {
            Canary.logDebug("Failed to construct a SpawnMob packet", ex);
            return null;
        }
    }

    @Override
    public Packet spawnPainting(Painting painting) {
        try {
            return createPacket(25, painting);
        }
        catch (Exception ex) {
            Canary.logDebug("Failed to construct a SpawnPainting packet", ex);
            return null;
        }
    }

    @Override
    public Packet spawnXPOrb(XPOrb xporb) {
        try {
            return createPacket(26, xporb);
        }
        catch (Exception ex) {
            Canary.logDebug("Failed to construct a SpawnXPOrb packet", ex);
            return null;
        }
    }

    @Override
    public Packet entityVelocity(int entityID, double motX, double motY, double motZ) {
        try {
            return createPacket(28, entityID, motX, motY, motZ);
        }
        catch (Exception ex) {
            Canary.logDebug("Failed to construct a EntityVelocity packet", ex);
            return null;
        }
    }

    @Override
    public Packet destroyEntity(int... ids) {
        try {
            return createPacket(29, new Object[]{ ids });
        }
        catch (Exception ex) {
            Canary.logDebug("Failed to construct a DestroyEntity packet", ex);
            return null;
        }
    }

    @Override
    public Packet entityRelativeMove(int entityID, byte x, byte y, byte z) {
        try {
            return createPacket(31, entityID, x, y, z);
        }
        catch (Exception ex) {
            Canary.logDebug("Failed to construct a EntityRelativeMove packet", ex);
            return null;
        }
    }

    @Override
    public Packet entityLook(int entityID, byte yaw, byte pitch) {
        try {
            return createPacket(32, entityID, yaw, pitch);
        }
        catch (Exception ex) {
            Canary.logDebug("Failed to construct a EntityLook packet", ex);
            return null;
        }
    }

    @Override
    public Packet entityLookRelativeMove(int entityID, byte x, byte y, byte z, byte yaw, byte pitch) {
        try {
            return createPacket(33, entityID, x, y, z, yaw, pitch);
        }
        catch (Exception ex) {
            Canary.logDebug("Failed to construct a EntityLookRelativeMove packet", ex);
            return null;
        }
    }

    @Override
    public Packet entityTeleport(int entityID, int x, int y, int z, byte yaw, byte pitch) {
        try {
            return createPacket(34, entityID, x, y, z, yaw, pitch);
        }
        catch (Exception ex) {
            Canary.logDebug("Failed to construct a EntityTeleport packet", ex);
            return null;
        }
    }

    @Override
    public Packet entityHeadLook(int entityID, byte yaw) {
        try {
            return createPacket(35, entityID, yaw);
        }
        catch (Exception ex) {
            Canary.logDebug("Failed to construct a EntityHeadLook packet", ex);
            return null;
        }
    }

    @Override
    public Packet entityStatus(int entityID, byte status) {
        try {
            return createPacket(38, entityID, status);
        }
        catch (Exception ex) {
            Canary.logDebug("Failed to construct a EntityStatus packet", ex);
            return null;
        }
    }

    @Override
    public Packet attachEntity(int leashId, Entity attaching, Entity vehicle) {
        try {
            return createPacket(39, leashId, attaching, vehicle);
        }
        catch (Exception ex) {
            Canary.logDebug("Failed to construct a AttachEntity packet", ex);
            return null;
        }
    }

    @Override
    public Packet entityMetaData(int entityID, DataWatcher watcher) {
        try {
            return createPacket(40, entityID, watcher);
        }
        catch (Exception ex) {
            Canary.logDebug("Failed to construct a EntityMetaData packet", ex);
            return null;
        }
    }

    @Override
    public Packet entityEffect(int entityID, PotionEffect effect) {
        try {
            return createPacket(41, entityID, effect);
        }
        catch (Exception ex) {
            Canary.logDebug("Failed to construct a EntityEffect packet", ex);
            return null;
        }
    }

    @Override
    public Packet removeEntityEffect(int entityID, PotionEffect effect) {
        try {
            return createPacket(42, entityID, effect);
        }
        catch (Exception ex) {
            Canary.logDebug("Failed to construct a RemoveEntityEffect packet", ex);
            return null;
        }
    }

    @Override
    public Packet setExperience(float bar, int level, int totalXp) {
        try {
            return createPacket(43, bar, level, totalXp);
        }
        catch (Exception ex) {
            Canary.logDebug("Failed to construct a SetExperience packet", ex);
            return null;
        }
    }

    @Override
    public Packet chunkData(Chunk chunk, boolean initialize, int bitflag) {
        try {
            return createPacket(51, chunk, initialize, bitflag);
        }
        catch (Exception ex) {
            Canary.logDebug("Failed to construct a ChunkData packet", ex);
            return null;
        }
    }

    @Override
    public Packet multiBlockChange(int chunkX, int chunkZ, short[] chunkBlocks, int size, World world) {
        try {
            return createPacket(52, chunkX, chunkZ, chunkBlocks, size, world);
        }
        catch (Exception ex) {
            Canary.logDebug("Failed to construct a MultiBlockChange packet", ex);
            return null;
        }
    }

    @Override
    public Packet blockChange(int x, int y, int z, int typeId, int data) {
        try {
            return createPacket(53, x, y, z, typeId, data);
        }
        catch (Exception ex) {
            Canary.logDebug("Failed to construct a BlockChange packet", ex);
            return null;
        }
    }

    @Override
    public Packet blockAction(int x, int y, int z, int stat1, int stat2, int targetId) {
        try {
            return createPacket(54, x, y, z, stat1, stat2, targetId);
        }
        catch (Exception ex) {
            Canary.logDebug("Failed to construct a BlockAction packet", ex);
            return null;
        }
    }

    @Override
    public Packet blockBreakAnimation(int entityId, int x, int y, int z, int state) {
        try {
            return createPacket(55, entityId, x, y, z, state);
        }
        catch (Exception ex) {
            Canary.logDebug("Failed to construct a BlockBreakAnimation packet", ex);
            return null;
        }
    }

    @Override
    public Packet mapChunkBulk(List<Chunk> chunks) {
        try {
            return createPacket(56, chunks);
        }
        catch (Exception ex) {
            Canary.logDebug("Failed to construct a MapChunkBulk packet", ex);
            return null;
        }
    }

    @Override
    public Packet explosion(double explodeX, double explodeY, double explodeZ, float power, List<Position> affectedPositions, Vector3D playerVelocity) {
        try {
            return createPacket(60, explodeX, explodeY, explodeZ, power, affectedPositions, playerVelocity);
        }
        catch (Exception ex) {
            Canary.logDebug("Failed to construct a Explosion packet", ex);
            return null;
        }
    }

    @Override
    public Packet soundParticleEffect(int sfxID, int x, int y, int z, int aux, boolean disableRelVol) {
        try {
            return createPacket(61, sfxID, x, y, z, aux, disableRelVol);
        }
        catch (Exception ex) {
            Canary.logDebug("Failed to construct a SoundParticleEffect packet", ex);
            return null;
        }
    }

    @Override
    public Packet namedSoundEffect(String name, double x, double y, double z, float volume, float pitch) {
        try {
            return createPacket(62, name, x, y, z, volume, pitch);
        }
        catch (Exception ex) {
            Canary.logDebug("Failed to construct a NamedSoundEffect packet", ex);
            return null;
        }
    }

    @Override
    public Packet changeGameState(int state, int mode) {
        try {
            return createPacket(70, state, mode);
        }
        catch (Exception ex) {
            Canary.logDebug("Failed to construct a ChangeGameState packet", ex);
            return null;
        }
    }

    @Override
    public Packet spawnGlobalEntity(Entity entity) {
        try {
            return createPacket(71, entity);
        }
        catch (Exception ex) {
            Canary.logDebug("Failed to construct a UpdateTime packet", ex);
            return null;
        }
    }

    @Override
    public Packet openWindow(int windowId, int type, String title, int slots, boolean useTitle) {
        try {
            return createPacket(100, windowId, type, title, slots, useTitle);
        }
        catch (Exception ex) {
            Canary.logDebug("Failed to construct a OpenWindow packet", ex);
            return null;
        }
    }

    @Override
    public Packet closeWindow(int windowId) {
        try {
            return createPacket(101, windowId);
        }
        catch (Exception ex) {
            Canary.logDebug("Failed to construct a CloseWindow packet", ex);
            return null;
        }
    }

    @Override
    public Packet setSlot(int windowId, int slotId, Item item) {
        try {
            return createPacket(103, windowId, slotId, item);
        }
        catch (Exception ex) {
            Canary.logDebug("Failed to construct a SetSlot packet", ex);
            return null;
        }
    }

    @Override
    public Packet setWindowItems(int windowId, List<Item> items) {
        try {
            return createPacket(104, windowId, items);
        }
        catch (Exception ex) {
            Canary.logDebug("Failed to construct a SetWindowItems packet", ex);
            return null;
        }
    }

    @Override
    public Packet updateWindowProperty(int windowId, int bar, int value) {
        try {
            return createPacket(105, windowId, bar, value);
        }
        catch (Exception ex) {
            Canary.logDebug("Failed to construct a UpdateWindowProperty packet", ex);
            return null;
        }
    }

    @Override
    public Packet updateSign(int x, int y, int z, String[] text) {
        try {
            return createPacket(130, x, y, z, text);
        }
        catch (Exception ex) {
            Canary.logDebug("Failed to construct a UpdateSign packet", ex);
            return null;
        }
    }

    @Override
    public Packet itemData(short itemId, short uniqueId, byte[] data) {
        try {
            return createPacket(131, itemId, uniqueId, data);
        }
        catch (Exception ex) {
            Canary.logDebug("Failed to construct a ItemData packet", ex);
            return null;
        }
    }

    @Override
    public Packet updateTileEntity(int x, int y, int z, int action, CompoundTag compoundTag) {
        try {
            return createPacket(132, x, y, z, action, compoundTag);
        }
        catch (Exception ex) {
            Canary.logDebug("Failed to construct a UpdateTileEntity packet", ex);
            return null;
        }
    }

    @Override
    public Packet tileEditorOpen(int id, int x, int y, int z) {
        try {
            return createPacket(133, id, x, y, z);
        }
        catch (Exception ex) {
            Canary.logDebug("Failed to construct a TileEditorOpen packet", ex);
            return null;
        }
    }

    @Override
    public Packet incrementStatistic(int statId, int amount) {
        try {
            return createPacket(200, statId, amount);
        }
        catch (Exception ex) {
            Canary.logDebug("Failed to construct a IncrementStatistic packet", ex);
            return null;
        }
    }

    @Override
    public Packet playerInfo(String name, boolean connected, int ping) {
        try {
            return createPacket(201, name, connected, ping);
        }
        catch (Exception ex) {
            Canary.logDebug("Failed to construct a PlayerInfo packet", ex);
            return null;
        }
    }
}
