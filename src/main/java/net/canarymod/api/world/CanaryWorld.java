package net.canarymod.api.world;

import net.canarymod.WorldCacheTimer;
import net.canarymod.api.CanaryEntityTracker;
import net.canarymod.api.GameMode;
import net.canarymod.api.PlayerManager;
import net.canarymod.api.entity.CanaryEntity;
import net.canarymod.api.entity.Entity;
import net.canarymod.api.entity.EntityItem;
import net.canarymod.api.entity.living.EntityLiving;
import net.canarymod.api.entity.living.animal.EntityAnimal;
import net.canarymod.api.entity.living.humanoid.CanaryPlayer;
import net.canarymod.api.entity.living.humanoid.Player;
import net.canarymod.api.entity.living.monster.EntityMob;
import net.canarymod.api.entity.vehicle.Boat;
import net.canarymod.api.entity.vehicle.Minecart;
import net.canarymod.api.entity.vehicle.Vehicle;
import net.canarymod.api.inventory.CanaryItem;
import net.canarymod.api.inventory.Item;
import net.canarymod.api.world.blocks.*;
import net.canarymod.api.world.blocks.TileEntity;
import net.canarymod.api.world.effects.AuxiliarySoundEffect;
import net.canarymod.api.world.effects.Particle;
import net.canarymod.api.world.effects.SoundEffect;
import net.canarymod.api.world.position.Location;
import net.canarymod.api.world.position.Position;
import net.canarymod.config.Configuration;
import net.canarymod.config.WorldConfiguration;
import net.minecraft.block.BlockJukebox;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.S2APacketParticles;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.*;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.storage.WorldInfo;
import net.visualillusionsent.utils.TaskManager;

import java.util.ArrayList;
import java.util.List;

public class CanaryWorld implements World {
    private WorldServer world;
    private DimensionType type;
    public long[] nanoTicks;
    private WorldConfiguration wrld_cfg;
    /** The world name */
    private String name, fqName;

    public CanaryWorld(String name, WorldServer dimension, DimensionType type) {
        this.name = name;
        this.type = type;
        world = dimension;
        fqName = name + "_" + this.type.getName();
        // Init nanotick size
        nanoTicks = new long[100];
        wrld_cfg = Configuration.getWorldConfig(this.fqName);
        if (Configuration.getServerConfig().isWorldCacheTimerEnabled()) {
            TaskManager.scheduleContinuedTaskInMinutes(new WorldCacheTimer(this), Configuration.getServerConfig().getWorldCacheTimeout(), Configuration.getServerConfig().getWorldCacheTimeout());
        }

    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getFqName() {
        return fqName;
    }

    @Override
    public void setNanoTick(int tickIndex, long tick) {
        nanoTicks[tickIndex] = tick;
    }

    @Override
    public long getNanoTick(int tickIndex) {
        return nanoTicks[tickIndex];
    }

    @Override
    public CanaryEntityTracker getEntityTracker() {
        return world.q().getCanaryEntityTracker();
    }

    @Override
    public EntityItem dropItem(Position position, Item item) {
        return dropItem((int) position.getX(), (int) position.getY(), (int) position.getZ(), item);
    }

    @Override
    public EntityItem dropItem(int x, int y, int z, int itemId, int amount, int damage) {
        return dropItem(x, y, z, new CanaryItem(itemId, amount, damage));
    }

    @Override
    public EntityItem dropItem(int x, int y, int z, Item item) {
        double d1 = world.s.nextFloat() * 0.7F + (1.0F - 0.7F) * 0.5D;
        double d2 = world.s.nextFloat() * 0.7F + (1.0F - 0.7F) * 0.5D;
        double d3 = world.s.nextFloat() * 0.7F + (1.0F - 0.7F) * 0.5D;

        net.minecraft.entity.item.EntityItem oei = new net.minecraft.entity.item.EntityItem(world, x + d1, y + d2, z + d3, ((CanaryItem) item).getHandle());

        oei.b = 10;
        world.d(oei);
        return oei.getEntityItem();
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List<EntityAnimal> getAnimalList() {
        List<EntityAnimal> animals = new ArrayList<EntityAnimal>();

        for (Object o : (ArrayList) ((ArrayList) world.e).clone()) {
            if (((net.minecraft.entity.Entity) o).getCanaryEntity() instanceof EntityAnimal) {
                animals.add((EntityAnimal) ((net.minecraft.entity.Entity) o).getCanaryEntity());
            }
        }
        return animals;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List<EntityMob> getMobList() {
        List<EntityMob> mobs = new ArrayList<EntityMob>();

        for (Object o : (ArrayList) ((ArrayList) world.e).clone()) {
            if (((net.minecraft.entity.Entity) o).getCanaryEntity() instanceof EntityMob) {
                mobs.add((EntityMob) ((net.minecraft.entity.Entity) o).getCanaryEntity());
            }
        }
        return mobs;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List<Boat> getBoatList() {
        List<Boat> boats = new ArrayList<Boat>();

        for (Object o : (ArrayList) ((ArrayList) world.e).clone()) {
            if (((net.minecraft.entity.Entity) o).getCanaryEntity() instanceof Boat) {
                boats.add((Boat) ((net.minecraft.entity.Entity) o).getCanaryEntity());
            }
        }
        return boats;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List<Minecart> getMinecartList() {
        List<Minecart> minecarts = new ArrayList<Minecart>();

        for (Object o : (ArrayList) ((ArrayList) world.e).clone()) {
            if (((net.minecraft.entity.Entity) o).getCanaryEntity() instanceof Minecart) {
                minecarts.add((Minecart) ((net.minecraft.entity.Entity) o).getCanaryEntity());
            }
        }
        return minecarts;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List<Vehicle> getVehicleList() {
        List<Vehicle> vehicles = new ArrayList<Vehicle>();

        for (Object o : (ArrayList) ((ArrayList) world.e).clone()) {
            if (((net.minecraft.entity.Entity) o).getCanaryEntity() instanceof Vehicle) {
                vehicles.add((Vehicle) ((net.minecraft.entity.Entity) o).getCanaryEntity());
            }
        }
        return vehicles;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List<EntityItem> getItemList() {
        List<EntityItem> items = new ArrayList<EntityItem>();

        for (Object o : (ArrayList) ((ArrayList) world.e).clone()) {
            if (((net.minecraft.entity.Entity) o).getCanaryEntity() instanceof EntityItem) {
                items.add((EntityItem) ((net.minecraft.entity.Entity) o).getCanaryEntity());
            }
        }
        return items;
    }

    @Override
    public List<Player> getPlayerList() {
        return getPlayerManager().getManagedPlayers();
    }

    @Override
    public List<EntityLiving> getEntityLivingList() {
        List<EntityLiving> list = new ArrayList<EntityLiving>();
        for (Entity e : getEntityTracker().getTrackedEntities()) {
            if (e instanceof EntityLiving && !list.contains(e)) {
                list.add((EntityLiving) e);
            }
        }
        return list;
    }

    @Override
    public List<Entity> getTrackedEntities() {
        return getEntityTracker().getTrackedEntities();
    }

    @Override
    public Block getBlockAt(int x, int y, int z) {
        return new CanaryBlock(world.a(x, y, z), getDataAt(x, y, z), x, y, z, this);
    }

    @Override
    public short getDataAt(Position position) {
        return getDataAt(position.getBlockX(), position.getBlockY(), position.getBlockZ());
    }

    @Override
    public Block getBlockAt(Position position) {
        return getBlockAt(position.getBlockX(), position.getBlockY(), position.getBlockZ());
    }

    @Override
    public short getDataAt(int x, int y, int z) {
        return (short) world.e(x, y, z);
    }

    @Override
    public void setBlock(Block block) {
        setBlockAt(block.getX(), block.getY(), block.getZ(), block.getTypeId(), block.getData());
    }

    @Override
    public void setBlockAt(Position vector, Block block) {
        setBlockAt(vector.getBlockX(), vector.getBlockY(), vector.getBlockZ(), block.getTypeId(), block.getData());
    }

    @Override
    public void setBlockAt(int x, int y, int z, short type) {
        setBlockAt(x, y, z, type, (byte) 0);

    }

    @Override
    public void setBlockAt(Position position, short type) {
        setBlockAt(position.getBlockX(), position.getBlockY(), position.getBlockZ(), type);
    }

    @Override
    public void setBlockAt(Position position, short type, short data) {
        setBlockAt(position.getBlockX(), position.getBlockY(), position.getBlockZ(), type, data);
    }

    @Override
    public void setBlockAt(Position position, BlockType blockType) {
        setBlockAt(position.getBlockX(), position.getBlockY(), position.getBlockZ(), blockType);
    }

    @Override
    public void setBlockAt(int x, int y, int z, short type, short data) {
        world.d(x, y, z, net.minecraft.block.Block.e(type), data, (0x1 | 0x2));
    }

    @Override
    public void setBlockAt(int x, int y, int z, BlockType blockType) {
        world.d(x, y, z, net.minecraft.block.Block.b(blockType.getMachineName()), blockType.getData(), (0x1 | 0x2));
    }

    @Override
    public void setDataAt(int x, int y, int z, short data) {
        world.a(x, y, z, data, (0x1 | 0x2));
    }

    @Override
    public void markBlockNeedsUpdate(int x, int y, int z) {
        world.g(x, y, z);
    }

    @Override
    public Player getClosestPlayer(double x, double y, double z, double distance) {
        EntityPlayer user = world.a(x, y, z, distance);

        if ((user != null) && user.getCanaryEntity() instanceof Player) {
            return (Player) user.getCanaryEntity();
        }
        return null;
    }

    @Override
    public Player getClosestPlayer(Entity entity, int distance) {
        EntityPlayer user = world.a(((CanaryEntity) entity).getHandle(), distance);

        if ((user != null) && user.getCanaryEntity() instanceof Player) {
            return (Player) user.getCanaryEntity();
        }
        return null;
    }

    @Override
    public ChunkProvider getChunkProvider() {
        return world.b.getCanaryChunkProvider();
    }

    @Override
    public boolean isChunkLoaded(Block block) {
        return getChunkProvider().isChunkLoaded(block.getX() >> 4, block.getZ() >> 4);
    }

    @Override
    public boolean isChunkLoaded(int x, int y, int z) {
        return getChunkProvider().isChunkLoaded(x >> 4, z >> 4);
    }

    @Override
    public boolean isChunkLoaded(int x, int z) {
        return getChunkProvider().isChunkLoaded(x, z);
    }

    @Override
    public int getHeight() {
        return wrld_cfg.getMaxBuildHeight();
    }

    @Override
    public int getHighestBlockAt(int x, int z) {
        for (int i = 0; i < this.getHeight(); i++) {
            if (world.i(x, i, z)) {
                return i;
            }
        }
        return getHeight();
    }

    @Override
    public void playNoteAt(int x, int y, int z, int instrument, byte notePitch) {
        world.c(x, y, y, instrument, notePitch);
    }

    @Override
    public void setTime(long time) {
        long margin = (time - getRawTime()) % 24000;

        // Java modulus is stupid.
        if (margin < 0) {
            margin += 24000;
        }
        long newTime = getRawTime() + margin;

        getHandle().b(newTime);
    }

    @Override
    public long getRelativeTime() {
        long margin = getRawTime() % 24000;

        // Java modulus is stupid.
        if (margin < 0) {
            margin += 24000;
        }
        return margin;
    }

    @Override
    public long getRawTime() {
        return world.I();
    }

    public long getTotalTime() {
        return world.H();
    }

    @Override
    public int getLightLevelAt(int x, int y, int z) {
        return world.b(EnumSkyBlock.Sky, x, y, z);
    }

    @Override
    public void setLightLevelOnBlockMap(int x, int y, int z, int newLevel) {
        world.b(EnumSkyBlock.Block, x, y, z, newLevel);
    }

    @Override
    public void setLightLevelOnSkyMap(int x, int y, int z, int newLevel) {
        world.b(EnumSkyBlock.Sky, x, y, z, newLevel);
    }

    @Override
    public Chunk loadChunk(int x, int z) {
        return getChunkProvider().loadChunk(x, z);
    }

    @Override
    public Chunk loadChunk(Location location) {
        return getChunkProvider().loadChunk((int) location.getX() >> 4, (int) location.getZ() >> 4);
    }

    @Override
    public Chunk loadChunk(Position vec3d) {
        return getChunkProvider().loadChunk((int) vec3d.getX() >> 4, (int) vec3d.getZ() >> 4);
    }

    @Override
    public Chunk getChunk(int x, int z) {
        return isChunkLoaded(x, z) ? getChunkProvider().provideChunk(x, z) : null;
    }

    @Override
    public List<Chunk> getLoadedChunks() {
        return getChunkProvider().getLoadedChunks();
    }

    @Override
    public DimensionType getType() {
        return this.type;
    }

    @Override
    public PlayerManager getPlayerManager() {
        return world.s().getPlayerManager();
    }

    @Override
    public void spawnParticle(Particle particle) {
        MinecraftServer.G().t.sendPacketToDimension(new S2APacketParticles(particle), this.name, this.type.getId());
    }

    @Override
    public void playSound(SoundEffect effect) {
        world.a(effect.x, effect.y, effect.z, effect.type.getMcName(), effect.volume, effect.pitch);
    }

    @Override
    public void playAUXEffect(AuxiliarySoundEffect effect) {
        world.c(effect.type.getDigits(), effect.x, effect.y, effect.z, effect.extra);
    }

    @Override
    public void playAUXEffectAt(Player player, AuxiliarySoundEffect effect) {
        world.a(player != null ? ((CanaryPlayer) player).getHandle() : null, effect.type.getDigits(), effect.x, effect.y, effect.z, effect.extra);
    }

    @Override
    public boolean isBlockPowered(Block block) {
        return isBlockPowered(block.getX(), block.getY(), block.getZ());
    }

    @Override
    public boolean isBlockPowered(Position position) {
        return isBlockPowered(position.getBlockX(), position.getBlockY(), position.getBlockZ());
    }

    @Override
    public boolean isBlockPowered(int x, int y, int z) {
        return world.u(x, y, z) > 0; //TODO: This may not function as intended
    }

    @Override
    public boolean isBlockIndirectlyPowered(Block block) {
        return isBlockIndirectlyPowered(block.getX(), block.getY(), block.getZ());
    }

    @Override
    public boolean isBlockIndirectlyPowered(Position position) {
        return isBlockIndirectlyPowered(position.getBlockX(), position.getBlockY(), position.getBlockZ());
    }

    @Override
    public boolean isBlockIndirectlyPowered(int x, int y, int z) {
        return world.v(x, y, z);
    }

    @Override
    public void setThundering(boolean thundering) {
        // TODO: add thunder_change hook here?
        world.x.a(thundering);

        // Thanks to Bukkit for figuring out these numbers
        if (thundering) {
            setThunderTime(world.s.nextInt(12000) + 3600);
        }
        else {
            setThunderTime(world.s.nextInt(168000) + 12000);
        }

    }

    @Override
    public void setThunderTime(int ticks) {
        world.x.f(ticks);
    }

    @Override
    public void setRaining(boolean downfall) {
        // TODO: Add weather change hook
        world.x.b(downfall);

        // Thanks to Bukkit for figuring out these numbers
        if (downfall) {
            setRainTime(world.s.nextInt(12000) + 3600);
        }
        else {
            setRainTime(world.s.nextInt(168000) + 12000);
        }
    }

    @Override
    public void setRainTime(int ticks) {
        world.x.g(ticks);
    }

    @Override
    public boolean isRaining() {
        return world.x.p();
    }

    @Override
    public boolean isThundering() {
        return world.x.n();
    }

    @Override
    public int getRainTicks() {
        return world.x.q();
    }

    @Override
    public int getThunderTicks() {
        return world.x.o();
    }

    @Override
    public void makeLightningBolt(int x, int y, int z) {
        world.c(new EntityLightningBolt(world, x, y, z));
    }

    @Override
    public void makeLightningBolt(Position position) {
        world.c(new EntityLightningBolt(world, position.getX(), position.getY(), position.getZ()));
    }

    @Override
    public void makeExplosion(Entity exploder, double x, double y, double z, float power, boolean smoke) {
        if (exploder == null) {
            world.a((net.minecraft.entity.Entity) null, x, y, z, power, smoke);
        }
        else {
            world.a(((CanaryEntity) exploder).getHandle(), x, y, z, power, smoke);
        }
    }

    @Override
    public void makeExplosion(Entity exploder, Position position, float power, boolean smoke) {
        makeExplosion(exploder, position.getX(), position.getY(), position.getZ(), power, smoke);
    }

    @Override
    public long getWorldSeed() {
        return world.H();
    }

    @Override
    public void removePlayerFromWorld(Player player) {
        world.f(((CanaryPlayer) player).getHandle());

    }

    @Override
    public void addPlayerToWorld(Player player) {
        world.d(((CanaryPlayer) player).getHandle());
    }

    @Override
    public Location getSpawnLocation() {
        // More structure ftw
        WorldInfo info = world.x;
        Location spawn = new Location(0, 0, 0);

        spawn.setX(info.c() + 0.5D);
        spawn.setY(info.d());
        spawn.setZ(info.e() + 0.5D);
        spawn.setRotation(0.0F);
        spawn.setPitch(0.0F);
        spawn.setType(type);
        spawn.setWorldName(world.getCanaryWorld().getName());
        return spawn;
    }

    @Override
    public void setSpawnLocation(Location p) {
        world.x.a((int) p.getX(), (int) p.getY(), (int) p.getZ());
    }

    @Override
    public TileEntity getTileEntity(Block block) {
        return getTileEntityAt(block.getX(), block.getY(), block.getZ());
    }

    @Override
    public TileEntity getTileEntityAt(int x, int y, int z) {
        TileEntity result = getOnlyTileEntityAt(x, y, z);

        if (result != null) {
            if (result instanceof Chest) {
                Chest chest = (Chest) result;

                if (chest.hasAttachedChest()) {
                    return chest.getDoubleChest();
                }
            }
        }

        return result;
    }

    @Override
    public TileEntity getOnlyTileEntity(Block block) {
        return getOnlyTileEntityAt(block.getX(), block.getY(), block.getZ());
    }

    @Override
    public TileEntity getOnlyTileEntityAt(int x, int y, int z) {
        net.minecraft.tileentity.TileEntity tileentity = world.o(x, y, z);

        if (tileentity != null) {
            if (tileentity instanceof TileEntityBrewingStand) {
                return ((TileEntityBrewingStand) tileentity).getCanaryBrewingStand();
            }
            else if (tileentity instanceof TileEntityBeacon) {
                return ((TileEntityBeacon) tileentity).getCanaryBeacon();
            }
            else if (tileentity instanceof TileEntityChest) {
                return ((TileEntityChest) tileentity).getCanaryChest();
            }
            else if (tileentity instanceof TileEntityCommandBlock) {
                return ((TileEntityCommandBlock) tileentity).getCanaryCommandBlock();
            }
            else if (tileentity instanceof TileEntityComparator) {
                return ((TileEntityComparator) tileentity).getCanaryComparator();
            }
            else if (tileentity instanceof TileEntityDaylightDetector) {
                return ((TileEntityDaylightDetector) tileentity).getCanaryDaylightDetector();
            }
            else if (tileentity instanceof TileEntityDropper) { // Should come before Dispenser since its an instance of Dispenser too
                return ((TileEntityDropper) tileentity).getCanaryDropper();
            }
            else if (tileentity instanceof TileEntityDispenser) {
                return ((TileEntityDispenser) tileentity).getCanaryDispenser();
            }
            else if (tileentity instanceof TileEntityFurnace) {
                return ((TileEntityFurnace) tileentity).getCanaryFurnace();
            }
            else if (tileentity instanceof TileEntityHopper) {
                return ((TileEntityHopper) tileentity).getCanaryHopper();
            }
            else if (tileentity instanceof TileEntityMobSpawner) {
                return ((TileEntityMobSpawner) tileentity).getCanaryMobSpawner();
            }
            else if (tileentity instanceof TileEntityNote) {
                return ((TileEntityNote) tileentity).getCanaryNoteBlock();
            }
            else if (tileentity instanceof BlockJukebox.TileEntityJukebox) {
                return ((BlockJukebox.TileEntityJukebox) tileentity).getCanaryJukebox();
            }
            else if (tileentity instanceof TileEntitySign) {
                return ((TileEntitySign) tileentity).getCanarySign();
            }
            else if (tileentity instanceof TileEntitySkull) {
                return ((TileEntitySkull) tileentity).getCanarySkull();
            }
        }
        return null;
    }

    @Override
    public BiomeType getBiomeType(int x, int z) {
        CanaryChunk c = (CanaryChunk) getChunk(x, z);
        if (c == null) {
            return BiomeType.fromId((byte) 0);
        }
        int xx = x - ((x >> 4) * 16);
        int zz = z - ((z >> 4) * 16);
        return BiomeType.fromId((byte) c.getHandle().a(xx, zz, this.getHandle().u()).ay);
    }

    @Override
    public void setBiome(int x, int z, BiomeType biome) {
        CanaryChunk c = (CanaryChunk) getChunk(x, z);
        if (c == null) {
            return;
        }
        byte[] bytes = c.getBiomeByteData();
        bytes[((z & 0xF) << 4) | (x & 0xF)] = biome.getId();
        c.setBiomeData(bytes);
    }

    @Override
    public GameMode getGameMode() {
        return GameMode.fromId(world.x.r().a());
    }

    @Override
    public void setGameMode(GameMode mode) {
        world.x.a(WorldSettings.GameType.a(mode.getId()));
    }

    @Override
    public void save() {
        world.b.a(true, null);
    }

    @Override
    public void broadcastMessage(String msg) {
        for (Player player : getPlayerList()) {
            player.message(msg);
        }
    }

    @Override
    public Biome getBiome(int x, int z) {
        CanaryChunk c = (CanaryChunk) getChunk(x, z);
        if (c == null) {
            return null;
        }
        int xx = x - ((x >> 4) * 16);
        int zz = z - ((z >> 4) * 16);
        return c.getHandle().a(xx, zz, this.getHandle().u()).getCanaryBiome();
    }

    public net.minecraft.world.World getHandle() {
        return world;
    }

    @Override
    public boolean equals(Object ob) {
        if (!(ob instanceof CanaryWorld)) {
            return false;
        }
        CanaryWorld test = (CanaryWorld) ob;
        return test.fqName.equals(this.fqName);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.fqName != null ? this.fqName.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "CanaryWorld{" + "world=" + world + ", type=" + type + ", name=" + name + ", fqName=" + fqName + '}';
    }
}
