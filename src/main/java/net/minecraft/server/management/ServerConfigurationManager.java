package net.minecraft.server.management;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import net.canarymod.Canary;
import net.canarymod.ToolBox;
import net.canarymod.Translator;
import net.canarymod.api.CanaryConfigurationManager;
import net.canarymod.api.PlayerListEntry;
import net.canarymod.api.entity.living.humanoid.CanaryPlayer;
import net.canarymod.api.nbt.CanaryCompoundTag;
import net.canarymod.api.packet.CanaryPacket;
import net.canarymod.api.scoreboard.CanaryScoreboardManager;
import net.canarymod.api.world.CanaryWorld;
import net.canarymod.api.world.DimensionType;
import net.canarymod.api.world.position.Location;
import net.canarymod.bansystem.Ban;
import net.canarymod.config.Configuration;
import net.canarymod.config.ServerConfiguration;
import net.canarymod.hook.player.*;
import net.canarymod.hook.system.ServerShutdownHook;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.*;
import net.minecraft.potion.PotionEffect;
import net.minecraft.scoreboard.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.StatList;
import net.minecraft.stats.StatisticsFile;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.demo.DemoWorldManager;
import net.minecraft.world.storage.IPlayerFileData;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.SaveHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

public abstract class ServerConfigurationManager {

    /* CanaryMod: Removed
        public static final File a = new File("banned-players.json");
        public static final File b = new File("banned-ips.json");
        public static final File c = new File("ops.json");
        public static final File d = new File("whitelist.json");
    */
    private static final Logger g = LogManager.getLogger();
    private static final SimpleDateFormat h = new SimpleDateFormat("yyyy-MM-dd \'at\' HH:mm:ss z");
    private final MinecraftServer i;
    public final List e = new ArrayList();
    /* CanaryMod: Removed
        private final UserListBans j;
        private final BanList k;
        private final UserListOps l;
        private final UserListWhitelist m;
    */
    private final Map n;
    private IPlayerFileData o;
    private boolean p;
    protected int f;
    private int q;
    private WorldSettings.GameType r;
    private boolean s;
    private int t;

    // CanaryMod
    protected CanaryConfigurationManager configurationmanager;
    private HashMap<String, IPlayerFileData> playerFileData = new HashMap<String, IPlayerFileData>();

    //
    public ServerConfigurationManager(MinecraftServer minecraftserver) {
/* CanaryMod: Removed      
        this.j = new UserListBans(a);
        this.k = new BanList(b);
        this.l = new UserListOps(c);
        this.m = new UserListWhitelist(d);
*/
        this.n = Maps.newHashMap();
        this.i = minecraftserver;
/* CanaryMod: Removed
        this.j.a(false);
        this.k.a(false);
*/
        this.f = Configuration.getServerConfig().getMaxPlayers();
        configurationmanager = new CanaryConfigurationManager(this);
    }

    // XXX LOGIN
    public void a(NetworkManager networkmanager, EntityPlayerMP entityplayermp) {
        GameProfile gameprofile = entityplayermp.bJ();
        PlayerProfileCache playerprofilecache = this.i.ax();
        GameProfile gameprofile1 = playerprofilecache.a(gameprofile.getId());
        String s0 = gameprofile1 == null ? gameprofile.getName() : gameprofile1.getName();

        playerprofilecache.a(gameprofile);
        NBTTagCompound nbttagcompound = this.a(entityplayermp);
        CanaryWorld w;
        boolean firstTime = true;
        if (nbttagcompound != null) {
            w = (CanaryWorld) Canary.getServer().getWorldManager().getWorld(nbttagcompound.j("LevelName"), net.canarymod.api.world.DimensionType.fromId(nbttagcompound.f("Dimension")), true);
            firstTime = false;
        } else {
            w = (CanaryWorld) Canary.getServer().getDefaultWorld();
        }
        entityplayermp.a(w.getHandle());
        entityplayermp.c.a((WorldServer) entityplayermp.o);
        String s1 = "local";

        if (networkmanager.b() != null) {
            s1 = networkmanager.b().toString();
        }

        g.info(entityplayermp.b_() + "[" + s1 + "] logged in with entity id " + entityplayermp.y() + " at (" + entityplayermp.s + ", " + entityplayermp.t + ", " + entityplayermp.u + ")");
        // CanaryMod: Use world we got from players NBT data
        WorldServer worldserver = (WorldServer) w.getHandle();
        ChunkCoordinates chunkcoordinates = worldserver.K();

        this.a(entityplayermp, (EntityPlayerMP) null, worldserver);
        NetHandlerPlayServer nethandlerplayserver = new NetHandlerPlayServer(this.i, networkmanager, entityplayermp);

        nethandlerplayserver.a((Packet) (new S01PacketJoinGame(entityplayermp.y(), entityplayermp.c.b(), worldserver.N().t(), worldserver.t.i, worldserver.r, this.p(), worldserver.N().u())));
        nethandlerplayserver.a((Packet) (new S3FPacketCustomPayload("MC|Brand", this.c().getServerModName().getBytes(Charsets.UTF_8))));
        nethandlerplayserver.a((Packet) (new S05PacketSpawnPosition(chunkcoordinates.a, chunkcoordinates.b, chunkcoordinates.c)));
        nethandlerplayserver.a((Packet) (new S39PacketPlayerAbilities(entityplayermp.bE)));
        nethandlerplayserver.a((Packet) (new S09PacketHeldItemChange(entityplayermp.bm.c)));
        entityplayermp.w().d();
        entityplayermp.w().b(entityplayermp);
        // CanaryMod: comment this out and use our own Method
        //this.a((ServerScoreboard) worldserver.W(), entityplayermp);
        ((CanaryScoreboardManager)Canary.scoreboards()).updateClientAll(entityplayermp);
        // CanaryMod: End
        this.i.az();
        ChatComponentTranslation chatcomponenttranslation;
        if (!entityplayermp.b_().equalsIgnoreCase(s0)) {
            chatcomponenttranslation = new ChatComponentTranslation("multiplayer.player.joined.renamed", new Object[]{ entityplayermp.c_(), s0 });
        }
        else {
            chatcomponenttranslation = new ChatComponentTranslation("multiplayer.player.joined", new Object[]{ entityplayermp.c_() });
        }
        chatcomponenttranslation.b().a(EnumChatFormatting.YELLOW);
        // CanaryMod: End
        this.c(entityplayermp);
        // CanaryMod Connection hook
        ConnectionHook hook = (ConnectionHook) new ConnectionHook(entityplayermp.getPlayer(), chatcomponenttranslation.e(), firstTime).call();
        if (!hook.isHidden()) {
            this.a((IChatComponent) chatcomponenttranslation);
        }
        // CanaryMod end
        nethandlerplayserver.a(entityplayermp.s, entityplayermp.t, entityplayermp.u, entityplayermp.y, entityplayermp.z, entityplayermp.ap, w.getName(), TeleportHook.TeleportCause.RESPAWN);
        this.b(entityplayermp, worldserver);
        if (this.i.V().length() > 0) {
            entityplayermp.a(this.i.V());
        }


        Iterator iterator = entityplayermp.aQ().iterator();

        while (iterator.hasNext()) {
            PotionEffect potioneffect = (PotionEffect) iterator.next();

            nethandlerplayserver.a((Packet) (new S1DPacketEntityEffect(entityplayermp.y(), potioneffect)));
        }

        entityplayermp.d_();
        if (nbttagcompound != null && nbttagcompound.b("Riding", 10)) {
            Entity entity = EntityList.a(nbttagcompound.m("Riding"), worldserver);

            if (entity != null) {
                entity.n = true;
                worldserver.d(entity);
                entityplayermp.a(entity);
                entity.n = false;
            }
        }


        // CanaryMod: Send Message of the Day
        Canary.motd().sendMOTD(entityplayermp.getPlayer());
        entityplayermp.getPlayer().setDisplayName(entityplayermp.getDisplayName()); // Login DisplayName work around
        //
    }

    // CanaryMod: protected to public
    public void a(ServerScoreboard serverscoreboard, EntityPlayerMP entityplayermp) {
        HashSet hashset = new HashSet();
        Iterator iterator = serverscoreboard.g().iterator();

        while (iterator.hasNext()) {
            ScorePlayerTeam scoreplayerteam = (ScorePlayerTeam) iterator.next();

            entityplayermp.a.a((Packet) (new S3EPacketTeams(scoreplayerteam, 0)));
        }

        for (int i0 = 0; i0 < 3; ++i0) {
            ScoreObjective scoreobjective = serverscoreboard.a(i0);

            if (scoreobjective != null && !hashset.contains(scoreobjective)) {
                List list = serverscoreboard.d(scoreobjective);
                Iterator iterator1 = list.iterator();

                while (iterator1.hasNext()) {
                    Packet packet = (Packet) iterator1.next();

                    entityplayermp.a.a(packet);
                }

                hashset.add(scoreobjective);
            }
        }
    }

    public void a(WorldServer[] server) {
        // CanaryMod Multiworld
        playerFileData.put(server[0].getCanaryWorld().getName(), server[0].M().e()); // XXX May need to review this
        //
    }

    public void a(EntityPlayerMP entityplayermp, WorldServer worldserver) {
        WorldServer worldserver1 = entityplayermp.r();

        if (worldserver != null) {
            worldserver.t().c(entityplayermp);
        }

        worldserver1.t().a(entityplayermp);
        worldserver1.b.c((int) entityplayermp.s >> 4, (int) entityplayermp.u >> 4);
    }

    public int d() {
        return PlayerManager.b(this.s());
    }

    public NBTTagCompound a(EntityPlayerMP entityplayermp) {
        NBTTagCompound nbttagcompound = entityplayermp.getCanaryWorld().getHandle().N().i();
        NBTTagCompound nbttagcompound1;

        if (entityplayermp.b_().equals(this.i.M()) && nbttagcompound != null) {
            entityplayermp.f(nbttagcompound);
            nbttagcompound1 = nbttagcompound;
            g.debug("loading single player");
        } else {
            // CanaryMod Multiworld
            nbttagcompound1 = playerFileData.get(entityplayermp.getCanaryWorld().getName()).b(entityplayermp);
            //
        }

        return nbttagcompound1;
    }

    // CanaryMod: get player data for name
    public static NBTTagCompound getPlayerDat(UUID uuid) {
        ISaveHandler handler = ((CanaryWorld) Canary.getServer().getDefaultWorld()).getHandle().M();

        if (handler instanceof SaveHandler) {
            SaveHandler saves = (SaveHandler) handler;

            return saves.b(uuid);
        } else {
            throw new RuntimeException("ISaveHandler is not of type SaveHandler! Failing to load playerdata");
        }
    }

    protected void b(EntityPlayerMP entityplayermp) {
        // CanaryMod Multiworld
        playerFileData.get(entityplayermp.getCanaryWorld().getName()).a(entityplayermp);
        //
        //CanaryMod: TODO: May need to do StatisticsFile relocation
        StatisticsFile statisticsfile = (StatisticsFile) this.n.get(entityplayermp.aB());

        if (statisticsfile != null) {
            statisticsfile.b();
        }
    }

    public void c(EntityPlayerMP entityplayermp) {
        // CanaryMod: PlayerListEntry  (online players receiving connecting player's info for first time)
        if (Configuration.getServerConfig().isPlayerListEnabled()) {
            PlayerListEntry plentry = entityplayermp.getPlayer().getPlayerListEntry(true);
            plentry.setPing(1000); // Set the ping for the initial connection
            // Get PlayerListEntry
            for (int i0 = 0; i0 < this.e.size(); ++i0) {
                EntityPlayerMP entityplayermp1 = (EntityPlayerMP) this.e.get(i0);
                // Clone the entry so that each receiver will start with the given data
                PlayerListEntry clone = plentry.clone();
                // Call PlayerListEntryHook
                new PlayerListEntryHook(clone, entityplayermp1.getPlayer()).call();
                // Send Packet
                entityplayermp1.a.a(new S38PacketPlayerListItem(clone.getName(), clone.isShown(), 1000)); //Ping Ignored
            }
        }
        //
        this.e.add(entityplayermp);

        // CanaryMod: Directly use playerworld instead
        WorldServer worldserver = (WorldServer) entityplayermp.getCanaryWorld().getHandle(); // this.e.a(entityplayermp.ar);
        worldserver.d(entityplayermp);
        this.a(entityplayermp, (WorldServer) null);

        // CanaryMod: PlayerListEntry (Connecting player receiving online players)
        if (Configuration.getServerConfig().isPlayerListEnabled()) {
            // Get PlayerListEntry
            for (int i0 = 0; i0 < this.e.size(); ++i0) {
                EntityPlayerMP entityplayermp1 = (EntityPlayerMP) this.e.get(i0);
                PlayerListEntry plentry = entityplayermp1.getPlayer().getPlayerListEntry(true);
                // Call PlayerListEntryHook
                new PlayerListEntryHook(plentry, entityplayermp.getPlayer()).call();
                // Send Packet
                entityplayermp.a.a(new S38PacketPlayerListItem(plentry.getName(), plentry.isShown(), plentry.getPing()));
            }
        }
        //
    }

    public void d(EntityPlayerMP entityplayermp) {
        entityplayermp.r().t().d(entityplayermp);
    }

    public void e(EntityPlayerMP entityplayermp) {
        entityplayermp.a(StatList.f);
        this.b(entityplayermp);
        WorldServer worldserver = entityplayermp.r();

        if (entityplayermp.m != null) {
            worldserver.f(entityplayermp.m);
            g.debug("removing player mount");
        }

        worldserver.e(entityplayermp);
        worldserver.t().c(entityplayermp);
        this.e.remove(entityplayermp);
        this.n.remove(entityplayermp.aB());

        // CanaryMod: PlayerListEntry
        if (Configuration.getServerConfig().isPlayerListEnabled()) {
            PlayerListEntry plentry = entityplayermp.getPlayer().getPlayerListEntry(false);
            plentry.setPing(9999); // Set the ping for the initial connection
            for (int i0 = 0; i0 < this.e.size(); ++i0) {
                EntityPlayerMP entityplayermp1 = (EntityPlayerMP) this.e.get(i0);
                // Get the PlayerListEntry
                PlayerListEntry clone = plentry.clone();
                // Call PlayerListEntryHook
                new PlayerListEntryHook(clone, entityplayermp1.getPlayer()).call();
                // Send Packet
                entityplayermp1.a.a(new S38PacketPlayerListItem(clone.getName(), clone.isShown(), clone.getPing()));
            }
        }
        //
    }

    public String a(SocketAddress socketaddress, GameProfile gameprofile) {
/* DISABLED
        if (this.g.a(gameprofile.getName())) {
            BanEntry banentry = (BanEntry) this.g.c().get(gameprofile.getName());
            String s0 = "You are banned from this server!\nReason: " + banentry.f();

            if (banentry.d() != null) {
                s0 = s0 + "\nYour ban will be removed on " + e.format(banentry.d());
            }

            return s0;
        } else if (!this.c(gameprofile.getName())) {
            return "You are not white-listed on this server!";
        } else {
            String s1 = socketaddress.toString();

            s1 = s1.substring(s1.indexOf("/") + 1);
            s1 = s1.substring(0, s1.indexOf(":"));
            if (this.h.a(s1)) {
                BanEntry banentry1 = (BanEntry) this.h.c().get(s1);
                String s2 = "Your IP address is banned from this server!\nReason: " + banentry1.f();

                if (banentry1.d() != null) {
                    s2 = s2 + "\nYour ban will be removed on " + e.format(banentry1.d());
                }

                return s2;
            } else {
                return this.a.size() >= this.b ? "The server is full!" : null;
            }
        }
*/
//      CanaryMod, redo the whole thing
        String s0 = gameprofile.getName();
        UUID id0 = gameprofile.getId();
        String s2 = ((InetSocketAddress) socketaddress).getAddress().getHostAddress(); // Proper IPv6 handling

        PreConnectionHook hook = (PreConnectionHook) new PreConnectionHook(s2, s0, id0, net.canarymod.api.world.DimensionType.NORMAL, Canary.getServer().getDefaultWorldName()).call();

        if (hook.getKickReason() != null) {
            return hook.getKickReason();
        }
        ServerConfiguration srv = Configuration.getServerConfig();

        if (Canary.bans().isBanned(id0.toString())) {
            Ban ban = Canary.bans().getBan(id0.toString());

            if (ban.getTimestamp() != -1) {
                return ban.getReason() + ", " +
                        srv.getBanExpireDateMessage() + ToolBox.formatTimestamp(ban.getTimestamp());
            }
            return ban.getReason();
        }

        if (Canary.bans().isIpBanned(s2)) {
            return Translator.translate(srv.getDefaultBannedMessage());
        }
        if (!Canary.whitelist().isWhitelisted(id0.toString()) && Configuration.getServerConfig().isWhitelistEnabled()) {
            return srv.getWhitelistMessage();
        }

        if (this.e.size() >= this.f) {
            if (Canary.reservelist().isSlotReserved(id0.toString()) && Configuration.getServerConfig().isReservelistEnabled()) {
                return null;
            }
            return srv.getServerFullMessage();
        }
        return null;
    }

    public EntityPlayerMP f(GameProfile gameprofile) {
        UUID uuid = EntityPlayer.a(gameprofile);
        ArrayList arraylist = Lists.newArrayList();

        EntityPlayerMP entityplayermp;

        for (int i0 = 0; i0 < this.e.size(); ++i0) {
            entityplayermp = (EntityPlayerMP) this.e.get(i0);
            if (entityplayermp.aB().equals(uuid)) {
                arraylist.add(entityplayermp);
            }
        }

        Iterator iterator = arraylist.iterator();

        while (iterator.hasNext()) {
            entityplayermp = (EntityPlayerMP) iterator.next();
            entityplayermp.a.c("You logged in from another location");
        }

        // CanaryMod read the players dat file to find out the world it was last in
        String worldName = Canary.getServer().getDefaultWorldName();
        net.canarymod.api.world.DimensionType worldtype = DimensionType.NORMAL;
        NBTTagCompound playertag = getPlayerDat(uuid);
        if (playertag != null) {
            CanaryCompoundTag canarycompound = new CanaryCompoundTag(playertag);

            worldName = canarycompound.getString("LevelName");
            if (worldName == null || worldName.isEmpty()) {
                worldName = Canary.getServer().getDefaultWorldName();
            }
            worldtype = net.canarymod.api.world.DimensionType.fromId(canarycompound.getInt("Dimension"));
        }

        WorldServer world = (WorldServer) ((CanaryWorld) Canary.getServer().getWorldManager().getWorld(worldName, worldtype, true)).getHandle();
        Object object;

        if (this.i.R()) {
            object = new DemoWorldManager(world);
        } else {
            object = new ItemInWorldManager(world);
        }

        // CanaryMod: Start: Meta Initialize
        EntityPlayerMP toRet = new EntityPlayerMP(this.i, this.i.getWorld(worldName, 0), gameprofile, (ItemInWorldManager) object);
        // Making sure some stuff is there before attempting to read it (if the player has no nbt, then the usual route is skipped)
        if (playertag != null && playertag.c("Canary")) {
            toRet.setMetaData(new CanaryCompoundTag(playertag.m("Canary")));
        } else {
            toRet.initializeNewMeta();
        }
        return toRet;
        //
    }

    public EntityPlayerMP a(EntityPlayerMP entityplayermp, int i0, boolean flag) {
        return this.a(entityplayermp, i0, flag, null);
    }

    // XXX IMPORTANT, HERE IS WORLD SWITCHING GOING ON!
    public EntityPlayerMP a(EntityPlayerMP entityplayermp, int i0, boolean flag0, Location loc) {
        entityplayermp.r().r().a(entityplayermp);
        entityplayermp.r().r().b(entityplayermp);
        entityplayermp.r().t().c(entityplayermp);
        this.e.remove(entityplayermp);
//        this.f.getWorld(entityplayermp.getCanaryWorld().getName(), entityplayermp.aq).f(entityplayermp); // CanaryMod: added multiworld support
        entityplayermp.getCanaryWorld().getHandle().f(entityplayermp);
        // ChunkCoordinates chunkcoordinates = entityplayermp.bL(); //CanaryMod removed in favor of a real location
        Location respawnLocation = entityplayermp.getRespawnLocation();
        boolean flag1 = entityplayermp.bO();
        boolean isBedSpawn = respawnLocation != null;
        entityplayermp.ap = i0;
        String name = entityplayermp.getCanaryWorld().getName();
        net.canarymod.api.world.DimensionType type = net.canarymod.api.world.DimensionType.fromId(i0);
        // CanaryMod: PlayerRespawn
        PlayerRespawningHook hook = (PlayerRespawningHook) new PlayerRespawningHook(entityplayermp.getPlayer(), loc, isBedSpawn).call();
        loc = hook.getRespawnLocation();
        WorldServer worldserver = (WorldServer) (loc == null ? (WorldServer) ((CanaryWorld) Canary.getServer().getWorldManager().getWorld(name, type, true)).getHandle() : ((CanaryWorld) loc.getWorld()).getHandle());

        // CanaryMod changes to accommodate multiworld bed spawns
        ChunkCoordinates chunkcoordinates = null;
        if (respawnLocation != null) {
            chunkcoordinates = new ChunkCoordinates(respawnLocation.getBlockX(), respawnLocation.getBlockY(), respawnLocation.getBlockZ());
            // Check if the spawn world differs from the expected one and adjust
            if (!worldserver.equals(((CanaryWorld) respawnLocation.getWorld()).getHandle())) {
                worldserver = (WorldServer) ((CanaryWorld) respawnLocation.getWorld()).getHandle();
            }
        }
        if (loc != null) {
            chunkcoordinates = new ChunkCoordinates(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
            // Check if the spawn world differs from the expected one and adjust
            if (!worldserver.equals(((CanaryWorld) loc.getWorld()).getHandle())) {
                worldserver = (WorldServer) ((CanaryWorld) loc.getWorld()).getHandle();
            }
        }
        //
        ItemInWorldManager itemInWorldManager;
        if (this.i.R()) {
            itemInWorldManager = new DemoWorldManager(worldserver);
        } else {
            itemInWorldManager = new ItemInWorldManager(worldserver);
        }
        // Use the wrapper keeper constructor
        EntityPlayerMP entityplayermp1 = new EntityPlayerMP(this.i, worldserver, entityplayermp.bJ(), itemInWorldManager, entityplayermp.getPlayer());
        // Copy nethandlerplayserver as the connection is still the same
        entityplayermp1.a = entityplayermp.a;
        entityplayermp1.a.b = entityplayermp1;
        entityplayermp1.a((EntityPlayer) entityplayermp, flag0);
        entityplayermp1.d(entityplayermp.y());

        // CanaryMod: metadata persistance
        entityplayermp.saveMeta();
        entityplayermp1.setMetaData(entityplayermp.getMetaData());
        //

        this.a(entityplayermp1, entityplayermp, worldserver); // GameMode changing
        ChunkCoordinates chunkcoordinates1;
        if (chunkcoordinates != null) {
            chunkcoordinates1 = loc != null ? chunkcoordinates : EntityPlayer.a(worldserver, chunkcoordinates, flag1); // Is it overridden?
            if (chunkcoordinates1 != null) {
                float rot = 0.0F, yaw = 0.0F;
                if (loc != null) {
                    rot = loc.getRotation();
                    yaw = loc.getPitch();
                }
                entityplayermp1.b(chunkcoordinates1.a + 0.5D, chunkcoordinates1.b + 0.1D, chunkcoordinates1.c + 0.5D, rot, yaw);
                entityplayermp1.a(chunkcoordinates, flag1);
            } else if (isBedSpawn) {
                entityplayermp1.a.a(new S2BPacketChangeGameState(0, 0.0F));
            }
        }
        while (worldserver.getCanaryWorld().getBlockAt(ToolBox.floorToBlock(entityplayermp1.t), ToolBox.floorToBlock(entityplayermp1.u + 1), ToolBox.floorToBlock(entityplayermp1.v)).getTypeId() != 0) {
            entityplayermp1.u += 1D;
        }

        //sending 2 respawn packets seems to force the client to clear its chunk cache. Removes ghosting blocks
        entityplayermp1.a.a((new S07PacketRespawn(entityplayermp1.ap, entityplayermp1.o.r, entityplayermp1.o.N().u(), entityplayermp1.c.b())));
        entityplayermp1.a.a((new S07PacketRespawn(entityplayermp1.ap, entityplayermp1.o.r, entityplayermp1.o.N().u(), entityplayermp1.c.b())));

        entityplayermp1.a.a(entityplayermp1.s, entityplayermp1.t, entityplayermp1.u, entityplayermp1.y, entityplayermp1.z, entityplayermp1.ap, worldserver.getCanaryWorld().getName(), TeleportHook.TeleportCause.RESPAWN);
        // ChanaryMod: Changed to use Player Coordinates instead of the give chunk coords \/
        entityplayermp1.a.a((new S05PacketSpawnPosition((int) entityplayermp1.s, (int) entityplayermp1.t, (int) entityplayermp1.u)));
        entityplayermp1.a.a((new S1FPacketSetExperience(entityplayermp1.bH, entityplayermp1.bG, entityplayermp1.bF)));
        this.b(entityplayermp1, worldserver);
        worldserver.t().a(entityplayermp1);
        worldserver.d(entityplayermp1); //Tracks new entity
        this.e.add(entityplayermp1);
        entityplayermp1.d_();
        entityplayermp1.g(entityplayermp1.aS());
        new PlayerRespawnedHook(entityplayermp1.getPlayer(), new Location(
                entityplayermp1.getPlayer().getWorld(),
                entityplayermp1.s,
                entityplayermp1.t,
                entityplayermp1.u,
                entityplayermp1.z,
                entityplayermp1.y)).call();
        //
        return entityplayermp1;
    }

    @Deprecated
    public void a(EntityPlayerMP entityplayermp, int i0) {
        throw new UnsupportedOperationException("Dimension switching without world name is deprecated. please use a(EntityPlayerMP, String, int))");
    }

    // XXX IMPORTANT, HERE IS DIMENSION SWITCHING GOING ON!
    public void a(EntityPlayerMP entityplayermp, String worldName, int i0) {
        int i1 = entityplayermp.ap;
        WorldServer worldserver = (WorldServer) entityplayermp.getCanaryWorld().getHandle();

        entityplayermp.ap = i0;
        net.canarymod.api.world.DimensionType type = net.canarymod.api.world.DimensionType.fromId(i0);
        WorldServer worldserver1 = (WorldServer) ((CanaryWorld) Canary.getServer().getWorldManager().getWorld(worldName, type, true)).getHandle();

        // Pre-load a chunk in the new world, makes spawning there a little faster
        worldserver1.b.c((int) entityplayermp.s >> 4, (int) entityplayermp.u >> 4);

        /* Force a Dimension's Default GameMode */
        if (Configuration.getWorldConfig(worldserver1.getCanaryWorld().getFqName()).forceDefaultGamemodeDimensional()) {
            entityplayermp.c.a(worldserver1.N().r());
        }
        entityplayermp.a.a((Packet) (new S07PacketRespawn(entityplayermp.ap, entityplayermp.o.r, entityplayermp.o.N().u(), entityplayermp.c.b())));
        worldserver.f(entityplayermp);
        entityplayermp.K = false;
        this.a(entityplayermp, i1, worldserver, worldserver1);
        this.a(entityplayermp, worldserver);
        // TP player to position
        entityplayermp.a.a(entityplayermp.s, entityplayermp.t, entityplayermp.u, entityplayermp.y, entityplayermp.z, entityplayermp.getCanaryWorld().getType().getId(), entityplayermp.getCanaryWorld().getName(), TeleportHook.TeleportCause.PORTAL);
        entityplayermp.c.a(worldserver1);
        this.b(entityplayermp, worldserver1);
        this.f(entityplayermp);
        Iterator iterator = entityplayermp.aQ().iterator();

        while (iterator.hasNext()) {
            PotionEffect potioneffect = (PotionEffect) iterator.next();

            entityplayermp.a.a((Packet) (new S1DPacketEntityEffect(entityplayermp.y(), potioneffect)));
        }
    }

    public void a(Entity entity, int i0, WorldServer worldserver, WorldServer worldserver1) {
        double d0 = entity.s;
        double d1 = entity.u;
        double d2 = 8.0D;
        double d3 = entity.s;
        double d4 = entity.t;
        double d5 = entity.u;
        float f0 = entity.y;

        worldserver.C.a("moving");
        if (entity.ap == -1) {
            d0 /= d2;
            d1 /= d2;
            entity.b(d0, entity.t, d1, entity.y, entity.z);
            if (entity.Z()) {
                worldserver.a(entity, false);
            }
        } else if (entity.ap == 0) {
            d0 *= d2;
            d1 *= d2;
            entity.b(d0, entity.t, d1, entity.y, entity.z);
            if (entity.Z()) {
                worldserver.a(entity, false);
            }
        } else {
            ChunkCoordinates chunkcoordinates;

            if (i0 == 1) {
                chunkcoordinates = worldserver1.K();
            } else {
                chunkcoordinates = worldserver1.l();
            }

            // CanaryMod: fix for an Entity trying to go to an unloaded world via a portal
            if (chunkcoordinates != null) {
                d0 = (double) chunkcoordinates.a;
                entity.t = (double) chunkcoordinates.b;
                d1 = (double) chunkcoordinates.c;
                entity.b(d0, entity.t, d1, 90.0F, 0.0F);
                if (entity.Z()) {
                    worldserver.a(entity, false);
                }
            }
        }

        worldserver.C.b();
        if (i0 != 1) {
            worldserver.C.a("placing");
            d0 = (double) MathHelper.a((int) d0, -29999872, 29999872);
            d1 = (double) MathHelper.a((int) d1, -29999872, 29999872);
            if (entity.Z()) {
                entity.b(d0, entity.t, d1, entity.y, entity.z);
                worldserver1.u().a(entity, d3, d4, d5, f0);
                worldserver1.d(entity);
                worldserver1.a(entity, false);
            }

            worldserver.C.b();
        }

        entity.a((World) worldserver1);
    }

    public void e() {
        if (Configuration.getServerConfig().getPlayerlistAutoUpdate()) {
            if (++this.t > Configuration.getServerConfig().getPlayerlistTicks()) {
                this.t = 0;
            }

            // CanaryMod: PlayerListEntry
            if (Configuration.getServerConfig().isPlayerListEnabled() && this.t < this.e.size()) {
                EntityPlayerMP entityplayermp = (EntityPlayerMP) this.e.get(this.t);

                // Get PlayerListEntry
                PlayerListEntry plentry = entityplayermp.getPlayer().getPlayerListEntry(true);
                for (int i0 = 0; i0 < this.e.size(); ++i0) {
                    EntityPlayerMP entityplayermp1 = (EntityPlayerMP) this.e.get(i0);
                    // Clone the entry so that each receiver will start with the given data
                    PlayerListEntry clone = plentry.clone();
                    // Call PlayerListEntryHook
                    new PlayerListEntryHook(clone, entityplayermp1.getPlayer()).call();
                    // Send Packet
                    entityplayermp1.a.a(new S38PacketPlayerListItem(clone.getName(), clone.isShown(), clone.getPing()));
                }
            }
            //
        }
    }

    public void a(Packet packet) {
        for (int i0 = 0; i0 < this.e.size(); ++i0) {
            ((EntityPlayerMP) this.e.get(i0)).a.a(packet);
        }
    }

    // CanaryMod re-route packets properly
    public void sendPacketToDimension(Packet packet, String world, int i) {
        for (int j = 0; j < this.e.size(); ++j) {
            EntityPlayerMP entityplayermp = (EntityPlayerMP) this.e.get(j);

            if (world.equals(entityplayermp.getCanaryWorld().getName()) && entityplayermp.ap == i) {
                // TODO check: CanaryMod re-route time updates to world-specific entity trackers
                entityplayermp.a.a(packet);
            }
        }
    }

    // CanaryMod end

    @Deprecated
    public void a(Packet packet, int i0) {
        throw new UnsupportedOperationException("a(packet, int) has been deprecated. use sendPacketToDimension instead!");
    }

    public String b(boolean flag0) {
        String s0 = "";
        ArrayList arraylist = Lists.newArrayList(this.e);

        for (int i0 = 0; i0 < arraylist.size(); ++i0) {
            if (i0 > 0) {
                s0 = s0 + ", ";
            }

            s0 = s0 + ((EntityPlayerMP) arraylist.get(i0)).b_();
            if (flag0) {
                s0 = s0 + " (" + ((EntityPlayerMP) arraylist.get(i0)).aB().toString() + ")";
            }
        }

        return s0;
    }

    public String[] f() {
        String[] astring = new String[this.e.size()];

        for (int i0 = 0; i0 < this.e.size(); ++i0) {
            astring[i0] = ((EntityPlayerMP) this.e.get(i0)).b_();
        }

        return astring;
    }

    public GameProfile[] g() {
        GameProfile[] agameprofile = new GameProfile[this.e.size()];

        for (int i0 = 0; i0 < this.e.size(); ++i0) {
            agameprofile[i0] = ((EntityPlayerMP) this.e.get(i0)).bJ();
        }

        return agameprofile;
    }

    public UserListBans h() {
        // CanaryMod this is unused
        throw new UnsupportedOperationException("UserListBans is unsupported");
    }

    public BanList i() {
        // CanaryMod this is unused
        throw new UnsupportedOperationException("BanList is unsupported");
    }

    public void a(GameProfile gameprofile) {
        Canary.ops().addPlayer(gameprofile.getId().toString()); // CanaryMod: Re-route to our Ops listing
    }

    public void b(GameProfile gameprofile) {
        Canary.ops().removePlayer(gameprofile.getId().toString()); // CanaryMod: Re-route to our Ops listing
    }

    public boolean e(GameProfile gameprofile) {
        return !this.p || Canary.ops().isOpped(gameprofile.getId().toString()); // CanaryMod: Re-route to our Ops listing
    }

    public boolean g(GameProfile gameprofile) {
        WorldServer srv = (WorldServer) ((CanaryWorld) Canary.getServer().getDefaultWorld()).getHandle();

        // CanaryMod: Added Re-route to our Ops listing
        return Canary.ops().isOpped(gameprofile.getId().toString()) || this.i.N() && srv.N().v() && this.i.M().equalsIgnoreCase(gameprofile.getName()) || this.s;
    }

    public EntityPlayerMP a(String s0) {
        Iterator iterator = this.e.iterator();

        EntityPlayerMP entityplayermp;

        do {
            if (!iterator.hasNext()) {
                return null;
            }

            entityplayermp = (EntityPlayerMP) iterator.next();
        }
        while (!entityplayermp.b_().equalsIgnoreCase(s0));

        return entityplayermp;
    }

    public List a(ChunkCoordinates chunkcoordinates, int i0, int i1, int i2, int i3, int i4, int i5, Map map, String s0, String s1, World world) {
        if (this.e.isEmpty()) {
            return Collections.emptyList();
        } else {
            Object object = new ArrayList();
            boolean flag0 = i2 < 0;
            boolean flag1 = s0 != null && s0.startsWith("!");
            boolean flag2 = s1 != null && s1.startsWith("!");
            int i6 = i0 * i0;
            int i7 = i1 * i1;

            i2 = MathHelper.a(i2);
            if (flag1) {
                s0 = s0.substring(1);
            }

            if (flag2) {
                s1 = s1.substring(1);
            }

            for (int i8 = 0; i8 < this.e.size(); ++i8) {
                EntityPlayerMP entityplayermp = (EntityPlayerMP) this.e.get(i8);

                if ((world == null || entityplayermp.o == world) && (s0 == null || flag1 != s0.equalsIgnoreCase(entityplayermp.b_()))) {
                    if (s1 != null) {
                        Team team = entityplayermp.bt();
                        String s2 = team == null ? "" : team.b();

                        if (flag2 == s1.equalsIgnoreCase(s2)) {
                            continue;
                        }
                    }

                    if (chunkcoordinates != null && (i0 > 0 || i1 > 0)) {
                        float f0 = chunkcoordinates.e(entityplayermp.f_());

                        if (i0 > 0 && f0 < (float) i6 || i1 > 0 && f0 > (float) i7) {
                            continue;
                        }
                    }

                    if (this.a((EntityPlayer) entityplayermp, map) && (i3 == WorldSettings.GameType.NOT_SET.a() || i3 == entityplayermp.c.b().a()) && (i4 <= 0 || entityplayermp.bF >= i4) && entityplayermp.bF <= i5) {
                        ((List) object).add(entityplayermp);
                    }
                }
            }

            if (chunkcoordinates != null) {
                Collections.sort((List) object, new PlayerPositionComparator(chunkcoordinates));
            }

            if (flag0) {
                Collections.reverse((List) object);
            }

            if (i2 > 0) {
                object = ((List) object).subList(0, Math.min(i2, ((List) object).size()));
            }

            return (List) object;
        }
    }

    private boolean a(EntityPlayer entityplayer, Map map) {
        if (map != null && map.size() != 0) {
            Iterator iterator = map.entrySet().iterator();

            Entry entry;
            boolean flag0;
            int i0;

            do {
                if (!iterator.hasNext()) {
                    return true;
                }

                entry = (Entry) iterator.next();
                String s0 = (String) entry.getKey();

                flag0 = false;
                if (s0.endsWith("_min") && s0.length() > 4) {
                    flag0 = true;
                    s0 = s0.substring(0, s0.length() - 4);
                }

                Scoreboard scoreboard = entityplayer.bU();
                ScoreObjective scoreobjective = scoreboard.b(s0);

                if (scoreobjective == null) {
                    return false;
                }

                Score score = entityplayer.bU().a(entityplayer.b_(), scoreobjective);

                i0 = score.c();
                if (i0 < ((Integer) entry.getValue()).intValue() && flag0) {
                    return false;
                }
            }
            while (i0 <= ((Integer) entry.getValue()).intValue() || flag0);

            return false;
        } else {
            return true;
        }
    }

    public void a(double d0, double d1, double d2, double d3, int i0, Packet packet) {
        this.a((EntityPlayer) null, d0, d1, d2, d3, i0, packet);
    }

    public void a(EntityPlayer entityplayer, double d0, double d1, double d2, double d3, int i0, Packet packet) {
        for (int i1 = 0; i1 < this.e.size(); ++i1) {
            EntityPlayerMP entityplayermp = (EntityPlayerMP) this.e.get(i1);

            if (entityplayermp != entityplayer && entityplayermp.ap == i0) {
                double d4 = d0 - entityplayermp.s;
                double d5 = d1 - entityplayermp.t;
                double d6 = d2 - entityplayermp.u;

                if (d4 * d4 + d5 * d5 + d6 * d6 < d3 * d3) {
                    entityplayermp.a.a(packet);
                }
            }
        }
    }

    public void j() {
        for (int i0 = 0; i0 < this.e.size(); ++i0) {
            this.b((EntityPlayerMP) this.e.get(i0));
        }
    }

    public void d(GameProfile gameprofile) {
        // TODO
        //this.m.a((UserListEntry) (new UserListWhitelistEntry(gameprofile)));
    }

    public void c(GameProfile gameprofile) {
        // TODO
        //this.m.c(gameprofile);
    }

    public UserListWhitelist k() {
        // TODO
        //return this.m;
        return null;
    }

    public String[] l() {
        // TODO
        //return this.m.a();
        return null;
    }

    public UserListOps m() {
        // TODO
        //return this.l;
        return null;
    }

    public String[] n() {
        // TODO
        //return this.l.a();
        return null;
    }

    public void a() {
    }

    public void b(EntityPlayerMP entityplayermp, WorldServer worldserver) {
        entityplayermp.a.a((Packet) (new S03PacketTimeUpdate(worldserver.I(), worldserver.J(), worldserver.O().b("doDaylightCycle"))));
        if (worldserver.Q()) {
            entityplayermp.a.a((Packet) (new S2BPacketChangeGameState(1, 0.0F)));
            entityplayermp.a.a((Packet) (new S2BPacketChangeGameState(7, worldserver.j(1.0F))));
            entityplayermp.a.a((Packet) (new S2BPacketChangeGameState(8, worldserver.h(1.0F))));
        }
    }

    public void f(EntityPlayerMP entityplayermp) {
        entityplayermp.a(entityplayermp.bn);
        entityplayermp.o();
        entityplayermp.a.a((Packet) (new S09PacketHeldItemChange(entityplayermp.bm.c)));
    }

    public int o() {
        return this.e.size();
    }

    public int p() {
        return this.f;
    }

    public String[] q() {
        WorldServer srv = (WorldServer) ((CanaryWorld) Canary.getServer().getDefaultWorld()).getHandle();

        return srv.M().e().f();
    }

    public boolean r() {
        return this.p;
    }

    public void a(boolean flag0) {
        this.p = flag0;
    }

    public List b(String s0) {
        ArrayList arraylist = new ArrayList();
        Iterator iterator = this.e.iterator();

        while (iterator.hasNext()) {
            EntityPlayerMP entityplayermp = (EntityPlayerMP) iterator.next();

            if (entityplayermp.s().equals(s0)) {
                arraylist.add(entityplayermp);
            }
        }

        return arraylist;
    }

    public int s() {
        return this.q;
    }

    public MinecraftServer c() {
        return this.i;
    }

    public NBTTagCompound t() {
        return null;
    }

    private void a(EntityPlayerMP entityplayermp, EntityPlayerMP entityplayermp1, World world) {
        // CanaryMod always use world!

        // if (entityplayermp1 != null) {
        // entityplayermp.c.a(entityplayermp1.c.b());
        // } else if (this.l != null) {
        // entityplayermp.c.a(this.l);
        // }

        //Check GameMode persistance
        if (entityplayermp1 != null) {
            boolean dimensional = entityplayermp.getCanaryWorld().getName().equals(entityplayermp1.getCanaryWorld().getName());
            if (!dimensional && Configuration.getWorldConfig(world.getCanaryWorld().getFqName()).forceDefaultGamemode()) {
                entityplayermp.c.a(world.N().r()); // Force Default GameMode
            } else {
                entityplayermp.c.a(entityplayermp1.c.b()); // Persist
            }
        } else {
            entityplayermp.c.b(world.N().r()); // Set if not set
        }
    }

    public void u(String msg) {
        // CanaryMod shutdown hook
        ServerShutdownHook hook = (ServerShutdownHook) new ServerShutdownHook(msg != null ? msg : "Server closed").call();
        //
        for (int i0 = 0; i0 < this.e.size(); ++i0) {
            ((EntityPlayerMP) this.e.get(i0)).a.c(hook.getReason());
        }
    }

    public void a(IChatComponent ichatcomponent, boolean flag0) {
        this.i.a(ichatcomponent);
        this.a((Packet) (new S02PacketChat(ichatcomponent, flag0)));
    }

    public void a(IChatComponent ichatcomponent) {
        this.a(ichatcomponent, true);
    }

    public StatisticsFile a(EntityPlayer entityplayer) {
        UUID uuid = entityplayer.aB();
        StatisticsFile statisticsfile = uuid == null ? null : (StatisticsFile) this.n.get(uuid);

        if (statisticsfile == null) {
            File file1 = new File(new File("worlds"), "stats");
            File file2 = new File(file1, uuid.toString() + ".json");

            if (!file2.exists()) {
                File file3 = new File(file1, entityplayer.b_() + ".json");

                if (file3.exists() && file3.isFile()) {
                    file3.renameTo(file2);
                }
            }

            statisticsfile = new StatisticsFile(this.i, file2);
            statisticsfile.a();
            this.n.put(uuid, statisticsfile);
        }

        return statisticsfile;
    }

    public void a(int i0) {
        this.q = i0;
        for (net.canarymod.api.world.World world : this.i.getWorldManager().getAllWorlds()) {
            WorldServer worldserver = (WorldServer) ((CanaryWorld) world).getHandle();
            worldserver.t().a(i0);
        }
        /*

        if (this.i.c != null) {
            WorldServer[] aworldserver = this.i.c;
            int i1 = aworldserver.length;
            for (int i2 = 0; i2 < i1; ++i2) {
                WorldServer worldserver = aworldserver[i2];

                if (worldserver != null) {
                    worldserver.t().a(i0);
                }
            }

        }
        */
    }

    /**
     * Get the configuration management wrapper
     *
     * @return the canary configuration manager
     */
    public CanaryConfigurationManager getConfigurationManager() {
        return configurationmanager;
    }

    // This is a CanaryMod method

    /**
     * Send a packet to a specified player.
     *
     * @param player
     * @param packet
     */
    public void sendPacketToPlayer(CanaryPlayer player, CanaryPacket packet) {
        if (e.contains(player.getHandle())) {
            player.getHandle().a.a(packet.getPacket());
        }
    }
}
