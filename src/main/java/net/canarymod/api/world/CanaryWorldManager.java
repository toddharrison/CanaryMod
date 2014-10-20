package net.canarymod.api.world;

import net.canarymod.Canary;
import net.canarymod.api.CanaryServer;
import net.canarymod.api.entity.living.humanoid.Player;
import net.canarymod.backbone.PermissionDataAccess;
import net.canarymod.config.Configuration;
import net.canarymod.config.WorldConfiguration;
import net.canarymod.database.Database;
import net.canarymod.database.exceptions.DatabaseWriteException;
import net.canarymod.hook.system.LoadWorldHook;
import net.canarymod.hook.system.UnloadWorldHook;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.*;
import net.minecraft.world.chunk.storage.AnvilSaveHandler;
import net.minecraft.world.storage.WorldInfo;

import java.io.File;
import java.io.FileFilter;
import java.util.*;

import static net.canarymod.Canary.log;

/**
 * This is a container for all of the worlds.
 *
 * @author Jos Kuijpers
 * @author Chris Ksoll (damagefilter)
 * @author Jason Jones (darkdiplomat)
 */
public class CanaryWorldManager implements WorldManager {

    private Map<String, World> loadedWorlds;
    private List<String> existingWorlds;
    private Map<String, Boolean> markedForUnload;
    private static final Object worldLock = new Object();

    public CanaryWorldManager() {
        markedForUnload = new HashMap<String, Boolean>(1);
        File worldsFolders = new File("worlds");

        if (!worldsFolders.exists()) {
            worldsFolders.mkdirs();
            // Resonable defaults
            existingWorlds = new ArrayList<String>(3);
            loadedWorlds = new HashMap<String, World>(3);
            return;
        }
        FileFilter worldFilter = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        };
        File[] worlds = worldsFolders.listFiles(worldFilter);
        if (worlds == null) {
            // Resonable defaults
            existingWorlds = new ArrayList<String>(3);
            loadedWorlds = new HashMap<String, World>(3);
            log.warn("No worlds found to be loaded!");
            return;
        }
        int worldNum = worlds.length;
        if (worldNum == 0) {
            worldNum = 3; // Resonable default
        }

        existingWorlds = new ArrayList<String>(worldNum);
        loadedWorlds = new HashMap<String, World>(worldNum);
        for (File world : worlds) {
            FileFilter dimensionFilter = new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    return pathname.isDirectory() && pathname.getName().contains("_");
                }
            };
            File[] dimensions = world.listFiles(dimensionFilter);
            if (dimensions == null) {
                log.warn("No dimensions found for World - ".concat(world.getName()));
                continue;
            }
            for (File fqname : dimensions) {
                existingWorlds.add(fqname.getName());
            }
        }
    }

    /**
     * Implementation specific, do not call outside of NMS!
     * Adds an already prepared world to the world manager
     *
     * @param world
     */
    public void addWorld(CanaryWorld world) {
        log.debug(String.format("Adding new world to world manager, filed as %s_%s", world.getName(), world.getType()
                .getName()));
        loadedWorlds.put(world.getName() + "_" + world.getType().getName(), world);
    }

    @Override
    public World getWorld(String name, boolean autoload) {
        if (name == null || name.isEmpty()) {
            name = Configuration.getServerConfig().getDefaultWorldName() + "_" + DimensionType.fromId(0).getName();
        }
        DimensionType t = DimensionType.fromName(name.substring(Math.max(0, name.lastIndexOf("_") + 1)));
        String nameOnly = name.substring(0, Math.max(0, name.lastIndexOf("_")));

        if (t != null) {
            return getWorld(nameOnly, t, autoload);
        }

        if (loadedWorlds.containsKey(name)) {
            return loadedWorlds.get(name);
        }
        else if (loadedWorlds.containsKey(name + "_NORMAL")) {
            return loadedWorlds.get(name + "_NORMAL");
        }
        else {
            if (autoload) {
                if (existingWorlds.contains(name)) {
                    return loadWorld(name, DimensionType.NORMAL);
                }
                else if (existingWorlds.contains(name + "_NORMAL")) {
                    return loadWorld(name, DimensionType.NORMAL);
                }
                else {
                    throw new UnknownWorldException("World " + name + " is unknown. Autoload was enabled for this call.");
                }
            }
            throw new UnknownWorldException("World " + name + " is not loaded. Autoload was disabled for this call.");
        }
    }

    @Override
    public World getWorld(String world, DimensionType type, boolean autoload) {
        if (world == null || world.isEmpty()) {
            world = Configuration.getServerConfig().getDefaultWorldName();
        }
        if (worldIsLoaded(world + "_" + type.getName())) {
            return loadedWorlds.get(world + "_" + type.getName());
        }
        else {
            if (worldExists(world + "_" + type.getName()) && autoload) {
                log.debug("World exists but is not loaded. Loading ...");
                return loadWorld(world, type);
            }
            else {
                if (autoload) {
                    log.debug("World does not exist, we can autoload, will load!");
                    createWorld(world, type);
                    return loadedWorlds.get(world + "_" + type.getName());
                }
                else {
                    throw new UnknownWorldException("Tried to get a non-existing world: " + world + "_" + type.getName() + " - you must create it before you can load it or pass autoload = true");
                }

            }
        }

    }

    private void updateExistingWorldsList(String name, DimensionType type) {
        existingWorlds.add(name + "_" + type.getName());
    }

    @Override
    public boolean createWorld(String name, long seed, DimensionType type) {
        return createWorld(name, seed, type, WorldType.DEFAULT);
    }

    @Override
    public boolean createWorld(String name, DimensionType type) {
        return createWorld(name, System.currentTimeMillis(), type, WorldType.DEFAULT);
    }

    @Override
    public boolean createWorld(String name, long seed, DimensionType dimType, WorldType genType) {
        WorldConfiguration cfg = WorldConfiguration.create(name, dimType);
        if (cfg == null) {
            Canary.log.debug("Config already exists for " + name + "_" + dimType.getName());
            cfg = Configuration.getWorldConfig(name.concat("_").concat(dimType.getName()));
        }
        else {
            Canary.log.debug("Updating new config for " + name + "_" + dimType.getName());
            cfg.getFile().setLong("world-seed", seed);
            cfg.getFile().setString("world-type", genType.toString());
        }
        return createWorld(cfg);
    }

    @Override
    public boolean createWorld(WorldConfiguration configuration) {
        if(configuration == null){
            return false;
        }
        MinecraftServer mcserver = ((CanaryServer) Canary.getServer()).getHandle();
        String worldFqName = configuration.getFile().getFileName().replace(".cfg", "");
        String name = worldFqName.replaceAll("_.+", "");
        DimensionType dimType = DimensionType.fromName(worldFqName.replaceAll(".+_", ""));
        AnvilSaveHandler isavehandler = new AnvilSaveHandler(new File("worlds/"), name, true, dimType);
        WorldSettings worldsettings;
        WorldServer world;

        // initialize new perm file
        try {
            Database.get().updateSchema(new PermissionDataAccess(worldFqName));
        }
        catch (DatabaseWriteException e) {
            Canary.log.error("Failed to update database schema", e);
        }

        long seed = configuration.getWorldSeed().matches("\\d+") ? Long.valueOf(configuration.getWorldSeed()) : configuration.getWorldSeed().hashCode();
        worldsettings = new WorldSettings(seed, WorldSettings.GameType.a(configuration.getGameMode().getId()), configuration.generatesStructures(), false, net.minecraft.world.WorldType.a(configuration.getWorldType().toString()));
        worldsettings.a(configuration.getGeneratorSettings());
        WorldInfo worldinfo = new WorldInfo(worldsettings, name);

        if (dimType == DimensionType.NORMAL) {
            world = (WorldServer) new WorldServer(mcserver, isavehandler, worldinfo, dimType.getId(), mcserver.b).b();
            world.a(worldsettings);
        }
        else {
            world = (WorldServer) new WorldServerMulti(mcserver, isavehandler, dimType.getId(), (WorldServer) ((CanaryWorld) getWorld(name, net.canarymod.api.world.DimensionType.NORMAL, true)).getHandle(), mcserver.b).b();
        }

        world.a((new net.minecraft.world.WorldManager(mcserver, world)));
        world.P().a(WorldSettings.GameType.a(configuration.getGameMode().getId()));
        mcserver.an().a(new WorldServer[]{world}); // Init player data files
        world.x.a(EnumDifficulty.a(configuration.getDifficulty().getId())); // Set difficulty directly based on WorldConfiguration setting

        mcserver.loadStartArea(world);

        updateExistingWorldsList(name, dimType);
        addWorld(world.getCanaryWorld());
        new LoadWorldHook(world.getCanaryWorld()).call();
        return true;
    }

    @Override
    public World loadWorld(String name, DimensionType type) {
        //TODO: Instead should we throw an exception to avoid loading trash worlds?
        if (!worldIsLoaded(name + "_" + type.getName())) {
            ((CanaryServer) Canary.getServer()).getHandle().loadWorld(name, new Random().nextLong(), type);
            return loadedWorlds.get(name + "_" + type.getName());
        }
        else {
            return loadedWorlds.get(name + "_" + type.getName());
        }
    }

    @Override
    public boolean destroyWorld(String name) {
        String mainName = name.replaceAll("_.+", "");
        File file = new File("worlds/" + name.replaceAll("_.+", "") + "/" + name);
        File dir = new File("worldsbackup/" + name + "(" + System.currentTimeMillis() + ")"); // Timestamp the backup
        boolean success = dir.mkdirs() && file.renameTo(new File(dir, file.getName()));
        if (success) {
            existingWorlds.remove(name);
        }
        return success;
    }

    @Override
    public Collection<World> getAllWorlds() {
        // before we return all the worlds, first check if there are any worlds marked for unload!
        if (markedForUnload.size() > 0) {
            log.debug("Processing worlds for unload");
            removeWorlds();
            // markedForUnload.clear();
        }
        // Canary.println("getAllWorlds");
        return Collections.unmodifiableCollection(this.loadedWorlds.values());
    }

    @Override
    public void unloadWorld(String name, DimensionType type, boolean force) {
        // This actually just schedules a world for unloading,
        // to prevent ConcurrentModificationExceptions as the values for world are iterated over constantly.
        // See getAllWorld for details
        markedForUnload.put(name + "_" + type.getName(), force);
    }

    /**
     * This'll actually remove all marked worlds from the system so that they may get GC'd soon after
     */
    private void removeWorlds() {
        Iterator<String> iter = markedForUnload.keySet().iterator();
        synchronized (worldLock) {
            while (iter.hasNext()) {
                String fqName = iter.next();
                CanaryWorld world = (CanaryWorld) loadedWorlds.get(fqName);
                boolean force = markedForUnload.get(fqName);
                if (world.getPlayerList().size() > 0) {
                    if (force) {
                        for (Player p : world.getPlayerList()) {
                            p.kick("Server scheduled world shutdown");
                        }
                    }
                    else {
                        log.warn(world.getFqName() + " was scheduled for unload but there were still players in it. Not unloading world!");
                        continue;
                    }
                }
                world.save();
                ((WorldServer) world.getHandle()).n(); // close out the SaveHandler Session Lock
                new UnloadWorldHook(world).call();
                loadedWorlds.remove(world.getFqName());
                world.dereference(); // prevent a leak
                iter.remove();
            }
        }
    }

    @Override
    public boolean worldIsLoaded(String name) {
        return loadedWorlds.containsKey(name);
    }

    @Override
    public boolean worldIsLoaded(String name, DimensionType type) {
        return loadedWorlds.containsKey(name.concat("_").concat(type.getName()));
    }

    @Override
    public boolean worldExists(String name) {
        return existingWorlds.contains(name);
    }

    @Override
    public List<String> getExistingWorlds() {
        return Collections.unmodifiableList(existingWorlds); // TODO: This only reads base folders not the real dimension folders!
    }

    @Override
    public String[] getExistingWorldsArray() {
        return existingWorlds.toArray(new String[existingWorlds.size()]);
    }

    @Override
    public String[] getLoadedWorldsNames() {
        if (markedForUnload.size() > 0) {
            log.debug("Processing worlds for unload");
            removeWorlds();
        }
        Set<String> names = new HashSet<String>();
        for (String name : loadedWorlds.keySet()) {
            names.add(name);
        }
        return names.toArray(new String[names.size()]);
    }

    @Override
    public String[] getLoadedWorldsNamesOfDimension(DimensionType dimensionType) {
        if (markedForUnload.size() > 0) {
            log.debug("Processing worlds for unload");
            removeWorlds();
        }
        Set<String> worlds = new HashSet<String>();
        for (World world : loadedWorlds.values()) {
            if (world.getType() == dimensionType) {
                worlds.add(world.getFqName());
            }
        }
        return worlds.toArray(new String[worlds.size()]);
    }


}
