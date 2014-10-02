package net.minecraft.server.management;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.authlib.GameProfile;
import io.netty.buffer.Unpooled;
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
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.*;
import net.minecraft.potion.PotionEffect;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.StatList;
import net.minecraft.stats.StatisticsFile;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.border.IBorderListener;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.demo.DemoWorldManager;
import net.minecraft.world.storage.IPlayerFileData;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.SaveHandler;
import net.minecraft.world.storage.WorldInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.text.SimpleDateFormat;
import java.util.*;

public abstract class ServerConfigurationManager {

    /* CanaryMod: Removed
        public static final File a = new File("banned-players.json");
        public static final File b = new File("banned-ips.json");
        public static final File c = new File("ops.json");
        public static final File d = new File("whitelist.json");
    */
    private static final Logger h = LogManager.getLogger();
    private static final SimpleDateFormat i = new SimpleDateFormat("yyyy-MM-dd \'at\' HH:mm:ss z");
    private final MinecraftServer j;
    public final List e = Lists.newArrayList();
    public final Map f = Maps.newHashMap();
    /* CanaryMod: Removed
        private final UserListBans k;
        private final BanList l;
        private final UserListOps m;
        private final UserListWhitelist n;
    */
    private final Map o;
    private IPlayerFileData p;
    private boolean q;
    protected int g;
    private int r;
    private WorldSettings.GameType s;
    private boolean t;
    private int u;

    // CanaryMod
    protected CanaryConfigurationManager configurationmanager;
    private HashMap<String, IPlayerFileData> playerFileData = new HashMap<String, IPlayerFileData>();

    //
    public ServerConfigurationManager(MinecraftServer minecraftserver) {
/* CanaryMod: Removed      
        this.k = new UserListBans(a);
        this.l = new BanList(b);
        this.m = new UserListOps(c);
        this.n = new UserListWhitelist(d);
*/
        this.o = Maps.newHashMap();
        this.j = minecraftserver;
/* CanaryMod: Removed
        this.k.a(false);
        this.l.a(false);
*/
        this.g = Configuration.getServerConfig().getMaxPlayers();
        configurationmanager = new CanaryConfigurationManager(this);
    }

    // XXX LOGIN
    public void a(NetworkManager networkmanager, EntityPlayerMP entityplayermp) {
        GameProfile gameprofile = entityplayermp.cc();
        PlayerProfileCache playerprofilecache = this.j.aD();
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

        h.info(entityplayermp.d_() + "[" + s1 + "] logged in with entity id " + entityplayermp.F() + " at (" + entityplayermp.s + ", " + entityplayermp.t + ", " + entityplayermp.u + ")");
        // CanaryMod: Use world we got from players NBT data
        WorldServer worldserver = (WorldServer) w.getHandle();
        WorldInfo worldinfo = worldserver.P();
        BlockPos blockpos = worldserver.M();

        this.a(entityplayermp, (EntityPlayerMP) null, worldserver);
        NetHandlerPlayServer nethandlerplayserver = new NetHandlerPlayServer(this.j, networkmanager, entityplayermp);

        nethandlerplayserver.a((Packet)(new S01PacketJoinGame(entityplayermp.F(), entityplayermp.c.b(), worldinfo.t(), worldserver.t.q(), worldserver.aa(), this.q(), worldinfo.u(), worldserver.Q().b("reducedDebugInfo"))));
        nethandlerplayserver.a((Packet)(new S3FPacketCustomPayload("MC|Brand", (new PacketBuffer(Unpooled.buffer())).a(this.c().getServerModName()))));
        nethandlerplayserver.a((Packet)(new S41PacketServerDifficulty(worldinfo.y(), worldinfo.z())));
        nethandlerplayserver.a((Packet)(new S05PacketSpawnPosition(blockpos)));
        nethandlerplayserver.a((Packet)(new S39PacketPlayerAbilities(entityplayermp.by)));
        nethandlerplayserver.a((Packet)(new S09PacketHeldItemChange(entityplayermp.bg.c)));
        entityplayermp.A().d();
        entityplayermp.A().b(entityplayermp);
        // CanaryMod: comment this out and use our own Method
        //this.a((ServerScoreboard) worldserver.W(), entityplayermp);
        ((CanaryScoreboardManager)Canary.scoreboards()).updateClientAll(entityplayermp);
        // CanaryMod: End
        this.j.aF();
        ChatComponentTranslation chatcomponenttranslation;
        if (!entityplayermp.b_().equalsIgnoreCase(s0)) {
            chatcomponenttranslation = new ChatComponentTranslation("multiplayer.player.joined.renamed", new Object[]{ entityplayermp.e_(), s0 });
        }
        else {
            chatcomponenttranslation = new ChatComponentTranslation("multiplayer.player.joined", new Object[]{ entityplayermp.e_() });
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
        if (this.j.aa().length() > 0) {
            entityplayermp.a(this.j.aa(), this.j.ab());
        }

        Iterator iterator = entityplayermp.bk().iterator();

        while (iterator.hasNext()) {
            PotionEffect potioneffect = (PotionEffect) iterator.next();

            nethandlerplayserver.a((Packet)(new S1DPacketEntityEffect(entityplayermp.F(), potioneffect)));
        }

        entityplayermp.f_();
        if (nbttagcompound != null && nbttagcompound.b("Riding", 10)) {
            Entity entity = EntityList.a(nbttagcompound.m("Riding"), (World)worldserver);

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
        HashSet hashset = Sets.newHashSet();
        Iterator iterator = serverscoreboard.g().iterator();

        while (iterator.hasNext()) {
            ScorePlayerTeam scoreplayerteam = (ScorePlayerTeam) iterator.next();

            entityplayermp.a.a((Packet) (new S3EPacketTeams(scoreplayerteam, 0)));
        }

        for (int i0 = 0; i0 < 19; ++i0) {
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

    public void a(WorldServer[] aworldserver) {
        // CanaryMod Multiworld
        playerFileData.put(aworldserver[0].getCanaryWorld().getName(), aworldserver[0].M().e()); // XXX May need to review this
        //
        aworldserver[0].af().a(new IBorderListener() {

                                   public void a(WorldBorder aworldserver, double p_a_2_) {
                                       ServerConfigurationManager.this.a((Packet)(new S44PacketWorldBorder(aworldserver, S44PacketWorldBorder.Action.SET_SIZE)));
                                   }

                                   public void a(WorldBorder aworldserver, double p_a_2_, double p_a_4_, long p_a_6_) {
                                       ServerConfigurationManager.this.a((Packet)(new S44PacketWorldBorder(aworldserver, S44PacketWorldBorder.Action.LERP_SIZE)));
                                   }

                                   public void a(WorldBorder aworldserver, double p_a_2_, double p_a_4_) {
                                       ServerConfigurationManager.this.a((Packet)(new S44PacketWorldBorder(aworldserver, S44PacketWorldBorder.Action.SET_CENTER)));
                                   }

                                   public void a(WorldBorder aworldserver, int p_a_2_) {
                                       ServerConfigurationManager.this.a((Packet)(new S44PacketWorldBorder(aworldserver, S44PacketWorldBorder.Action.SET_WARNING_TIME)));
                                   }

                                   public void b(WorldBorder p_b_1_, int p_b_2_) {
                                       ServerConfigurationManager.this.a((Packet)(new S44PacketWorldBorder(p_b_1_, S44PacketWorldBorder.Action.SET_WARNING_BLOCKS)));
                                   }

                                   public void b(WorldBorder p_b_1_, double p_b_2_) {
                                   }

                                   public void c(WorldBorder p_c_1_, double p_c_2_) {
                                   }
                               }
                              );
    }

    public void a(EntityPlayerMP entityplayermp, WorldServer worldserver) {
        WorldServer worldserver1 = entityplayermp.u();

        if (worldserver != null) {
            worldserver.t().c(entityplayermp);
        }

        worldserver1.t().a(entityplayermp);
        worldserver1.b.c((int) entityplayermp.s >> 4, (int) entityplayermp.u >> 4);
    }

    public int d() {
        return PlayerManager.b(this.t());
    }

    public NBTTagCompound a(EntityPlayerMP entityplayermp) {
        NBTTagCompound nbttagcompound = entityplayermp.getCanaryWorld().getHandle().N().i();
        NBTTagCompound nbttagcompound1;

        if (entityplayermp.d_().equals(this.j.R()) && nbttagcompound != null) {
            entityplayermp.f(nbttagcompound);
            nbttagcompound1 = nbttagcompound;
            h.debug("loading single player");
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
        entityplayermp.u().t().d(entityplayermp);
    }

    public void e(EntityPlayerMP entityplayermp) {
        entityplayermp.b(StatList.f);
        this.b(entityplayermp);
        WorldServer worldserver = entityplayermp.u();

        if (entityplayermp.m != null) {
            worldserver.f(entityplayermp.m);
            h.debug("removing player mount");
        }

        worldserver.e(entityplayermp);
        worldserver.t().c(entityplayermp);
        this.e.remove(entityplayermp);
        this.f.remove(entityplayermp.aJ());
        this.o.remove(entityplayermp.aJ());

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
                return this.e.size() >= this.g ? "The server is full!" : null;
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

        if (this.e.size() >= this.g) {
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
            if (entityplayermp.aJ().equals(uuid)) {
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

        if (this.j.W()) {
            object = new DemoWorldManager(world);
        } else {
            object = new ItemInWorldManager(world);
        }

        // CanaryMod: Start: Meta Initialize
        EntityPlayerMP toRet = new EntityPlayerMP(this.j, this.j.getWorld(worldName, 0), gameprofile, (ItemInWorldManager)object);
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
        entityplayermp.u().s().b(entityplayermp);
        entityplayermp.u().s().b((Entity)entityplayermp);
        entityplayermp.u().t().c(entityplayermp);
        this.e.remove(entityplayermp);
//        this.f.getWorld(entityplayermp.getCanaryWorld().getName(), entityplayermp.aq).f(entityplayermp); // CanaryMod: added multiworld support
        entityplayermp.getCanaryWorld().getHandle().f(entityplayermp);
        // ChunkCoordinates chunkcoordinates = entityplayermp.bL(); //CanaryMod removed in favor of a real location
        Location respawnLocation = entityplayermp.getRespawnLocation();
        boolean flag1 = entityplayermp.ch();
        boolean isBedSpawn = respawnLocation != null;
        entityplayermp.am = i0;
        String name = entityplayermp.getCanaryWorld().getName();
        net.canarymod.api.world.DimensionType type = net.canarymod.api.world.DimensionType.fromId(i0);
        // CanaryMod: PlayerRespawn
        PlayerRespawningHook hook = (PlayerRespawningHook) new PlayerRespawningHook(entityplayermp.getPlayer(), loc, isBedSpawn).call();
        loc = hook.getRespawnLocation();
        WorldServer worldserver = (WorldServer) (loc == null ? (WorldServer) ((CanaryWorld) Canary.getServer().getWorldManager().getWorld(name, type, true)).getHandle() : ((CanaryWorld) loc.getWorld()).getHandle());

        // CanaryMod changes to accommodate multiworld bed spawns
        BlockPos blockpos = null;
        if (respawnLocation != null) {
            blockpos = new BlockPos(respawnLocation.getBlockX(), respawnLocation.getBlockY(), respawnLocation.getBlockZ());
            // Check if the spawn world differs from the expected one and adjust
            if (!worldserver.equals(((CanaryWorld) respawnLocation.getWorld()).getHandle())) {
                worldserver = (WorldServer) ((CanaryWorld) respawnLocation.getWorld()).getHandle();
            }
        }
        if (loc != null) {
            blockpos = new BlockPos(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
            // Check if the spawn world differs from the expected one and adjust
            if (!worldserver.equals(((CanaryWorld) loc.getWorld()).getHandle())) {
                worldserver = (WorldServer) ((CanaryWorld) loc.getWorld()).getHandle();
            }
        }
        //
        ItemInWorldManager itemInWorldManager;
        if (this.j.W()) {
            itemInWorldManager = new DemoWorldManager(worldserver);
        } else {
            itemInWorldManager = new ItemInWorldManager(worldserver);
        }
        // Use the wrapper keeper constructor
        EntityPlayerMP entityplayermp1 = new EntityPlayerMP(this.j, worldserver, entityplayermp.cc(), itemInWorldManager, entityplayermp.getPlayer());
        // Copy nethandlerplayserver as the connection is still the same
        entityplayermp1.a = entityplayermp.a;
        entityplayermp1.a((EntityPlayer) entityplayermp, flag0);
        entityplayermp1.d(entityplayermp.F());
        entityplayermp1.o(entityplayermp);

        // CanaryMod: metadata persistance
        entityplayermp.saveMeta();
        entityplayermp1.setMetaData(entityplayermp.getMetaData());
        //

        this.a(entityplayermp1, entityplayermp, worldserver); // GameMode changing
        BlockPos blockpos1;
        if (blockpos != null) {
            blockpos1 = loc != null ? blockpos : EntityPlayer.a(worldserver, blockpos, flag1); // Is it overridden?
            if (blockpos1 != null) {
                float rot = 0.0F, yaw = 0.0F;
                if (loc != null) {
                    rot = loc.getRotation();
                    yaw = loc.getPitch();
                }
                entityplayermp1.b((double)((float)blockpos1.n() + 0.5F), (double)((float)blockpos1.o() + 0.1F), (double)((float)blockpos1.p() + 0.5F), rot, yaw);
                entityplayermp1.a(blockpos, flag1);
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
        entityplayermp.a.a((Packet)(new S07PacketRespawn(entityplayermp.am, entityplayermp.o.aa(), entityplayermp.o.P().u(), entityplayermp.c.b())));
        worldserver.f(entityplayermp);
        entityplayermp.I = false;
        this.a(entityplayermp, i1, worldserver, worldserver1);
        this.a(entityplayermp, worldserver);
        // TP player to position
        entityplayermp.a.a(entityplayermp.s, entityplayermp.t, entityplayermp.u, entityplayermp.y, entityplayermp.z);
        entityplayermp.c.a(worldserver1);
        this.b(entityplayermp, worldserver1);
        this.f(entityplayermp);
        Iterator iterator = entityplayermp.bk().iterator();

        while (iterator.hasNext()) {
            PotionEffect potioneffect = (PotionEffect) iterator.next();

            entityplayermp.a.a((Packet)(new S1DPacketEntityEffect(entityplayermp.F(), potioneffect)));
        }
    }

    public void a(Entity entity, int i0, WorldServer worldserver, WorldServer worldserver1) {
        double d0 = entity.s;
        double d1 = entity.u;
        double d2 = 8.0D;
        float f0 = entity.y;

        worldserver.B.a("moving");
        if (entity.am == -1) {
            d0 = MathHelper.a(d0 / d2, worldserver1.af().b() + 16.0D, worldserver1.af().d() - 16.0D);
            d1 = MathHelper.a(d1 / d2, worldserver1.af().c() + 16.0D, worldserver1.af().e() - 16.0D);
            entity.b(d0, entity.t, d1, entity.y, entity.z);
            if (entity.ai()) {
                worldserver.a(entity, false);
            }
        }
        else if (entity.am == 0) {
            d0 = MathHelper.a(d0 * d2, worldserver1.af().b() + 16.0D, worldserver1.af().d() - 16.0D);
            d1 = MathHelper.a(d1 * d2, worldserver1.af().c() + 16.0D, worldserver1.af().e() - 16.0D);
            entity.b(d0, entity.t, d1, entity.y, entity.z);
            if (entity.ai()) {
                worldserver.a(entity, false);
            }
        } else {
            BlockPos blockpos;

            if (i0 == 1) {
                blockpos = worldserver1.M();
            } else {
                blockpos = worldserver1.m();
            }

            // CanaryMod: fix for an Entity trying to go to an unloaded world via a portal
            if (blockpos != null) {
                d0 = (double)blockpos.n();
                entity.t = (double)blockpos.o();
                d1 = (double)blockpos.p();
                entity.b(d0, entity.t, d1, 90.0F, 0.0F);
                if (entity.ai()) {
                    worldserver.a(entity, false);
                }
            }
        }

        worldserver.B.b();
        if (i0 != 1) {
            worldserver.B.a("placing");
            d0 = (double) MathHelper.a((int) d0, -29999872, 29999872);
            d1 = (double) MathHelper.a((int) d1, -29999872, 29999872);
            if (entity.ai()) {
                entity.b(d0, entity.t, d1, entity.y, entity.z);
                worldserver1.u().a(entity, f0);
                worldserver1.d(entity);
                worldserver1.a(entity, false);
            }

            worldserver.B.b();
        }

        entity.a((World) worldserver1);
    }

    public void e() {
        if (Configuration.getServerConfig().getPlayerlistAutoUpdate()) {
            if (++this.u > Configuration.getServerConfig().getPlayerlistTicks()) {
                this.a((Packet)(new S38PacketPlayerListItem(S38PacketPlayerListItem.Action.UPDATE_LATENCY, this.e)));
                this.u = 0;
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

    public void a(EntityPlayer entityplayer, IChatComponent ichatcomponent) {
        Team team = entityplayer.bN();

        if (team != null) {
            Collection collection = team.d();
            Iterator iterator = collection.iterator();

            while (iterator.hasNext()) {
                String s0 = (String)iterator.next();
                EntityPlayerMP entityplayermp = this.a(s0);

                if (entityplayermp != null && entityplayermp != entityplayer) {
                    entityplayermp.a(ichatcomponent);
                }
            }
        }
    }

    public void b(EntityPlayer entityplayer, IChatComponent ichatcomponent) {
        Team team = entityplayer.bN();

        if (team == null) {
            this.a(ichatcomponent);
        }
        else {
            for (int i0 = 0; i0 < this.e.size(); ++i0) {
                EntityPlayerMP entityplayermp = (EntityPlayerMP)this.e.get(i0);

                if (entityplayermp.bN() != team) {
                    entityplayermp.a(ichatcomponent);
                }
            }
        }
    }

    public String f() {
        String s0 = "";

        for (int i0 = 0; i0 < this.e.size(); ++i0) {
            if (i0 > 0) {
                s0 = s0 + ", ";
            }

            s0 = s0 + ((EntityPlayerMP)this.e.get(i0)).d_();
        }

        return s0;
    }

    public String[] g() {
        String[] astring = new String[this.e.size()];

        for (int i0 = 0; i0 < this.e.size(); ++i0) {
            astring[i0] = ((EntityPlayerMP)this.e.get(i0)).d_();
        }

        return astring;
    }

    public GameProfile[] h() {
        GameProfile[] agameprofile = new GameProfile[this.e.size()];

        for (int i0 = 0; i0 < this.e.size(); ++i0) {
            agameprofile[i0] = ((EntityPlayerMP)this.e.get(i0)).cc();
        }

        return agameprofile;
    }

    public UserListBans i() {
        // CanaryMod this is unused
        throw new UnsupportedOperationException("UserListBans is unsupported");
    }

    public BanList j() {
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
        return !this.q || Canary.ops().isOpped(gameprofile.getId().toString()); // CanaryMod: Re-route to our Ops listing
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
        while (!entityplayermp.d_().equalsIgnoreCase(s0));

        return entityplayermp;
    }

    public void a(double d0, double d1, double d2, double d3, int i0, Packet packet) {
        this.a((EntityPlayer) null, d0, d1, d2, d3, i0, packet);
    }

    public void a(EntityPlayer entityplayer, double d0, double d1, double d2, double d3, int i0, Packet packet) {
        for (int i1 = 0; i1 < this.e.size(); ++i1) {
            EntityPlayerMP entityplayermp = (EntityPlayerMP) this.e.get(i1);

            if (entityplayermp != entityplayer && entityplayermp.am == i0) {
                double d4 = d0 - entityplayermp.s;
                double d5 = d1 - entityplayermp.t;
                double d6 = d2 - entityplayermp.u;

                if (d4 * d4 + d5 * d5 + d6 * d6 < d3 * d3) {
                    entityplayermp.a.a(packet);
                }
            }
        }
    }

    public void k() {
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

    public UserListWhitelist l() {
        // TODO
        //return this.m;
        return null;
    }

    public String[] m() {
        // TODO
        //return this.m.a();
        return null;
    }

    public UserListOps n() {
        // TODO
        //return this.l;
        return null;
    }

    public String[] o() {
        // TODO
        //return this.l.a();
        return null;
    }

    public void a() {
    }

    public void b(EntityPlayerMP entityplayermp, WorldServer worldserver) {
        WorldBorder worldborder = worldserver.af(); //this.j.c[0].af(); // CanaryMod: ERROR Will Robinson, ERROR
        entityplayermp.a.a((Packet)(new S44PacketWorldBorder(worldborder, S44PacketWorldBorder.Action.INITIALIZE)));
        entityplayermp.a.a((Packet)(new S03PacketTimeUpdate(worldserver.I(), worldserver.J(), worldserver.O().b("doDaylightCycle"))));
        if (worldserver.S()) {
            entityplayermp.a.a((Packet) (new S2BPacketChangeGameState(1, 0.0F)));
            entityplayermp.a.a((Packet) (new S2BPacketChangeGameState(7, worldserver.j(1.0F))));
            entityplayermp.a.a((Packet) (new S2BPacketChangeGameState(8, worldserver.h(1.0F))));
        }
    }

    public void f(EntityPlayerMP entityplayermp) {
        entityplayermp.a(entityplayermp.bh);
        entityplayermp.r();
        entityplayermp.a.a((Packet)(new S09PacketHeldItemChange(entityplayermp.bg.c)));
    }

    public int p() {
        return this.e.size();
    }

    public int q() {
        return this.g;
    }

    public String[] r() {
        WorldServer srv = (WorldServer) ((CanaryWorld) Canary.getServer().getDefaultWorld()).getHandle();

        return srv.O().e().f();
    }

    public boolean s() {
        return this.q;
    }

    public void a(boolean flag0) {
        this.q = flag0;
    }

    public List b(String s0) {
        ArrayList arraylist = Lists.newArrayList();
        Iterator iterator = this.e.iterator();

        while (iterator.hasNext()) {
            EntityPlayerMP entityplayermp = (EntityPlayerMP) iterator.next();

            if (entityplayermp.w().equals(s0)) {
                arraylist.add(entityplayermp);
            }
        }

        return arraylist;
    }

    public int t() {
        return this.r;
    }

    public MinecraftServer c() {
        return this.j;
    }

    public NBTTagCompound u() {
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
                entityplayermp.c.a(world.P().r()); // Force Default GameMode
            } else {
                entityplayermp.c.a(entityplayermp1.c.b()); // Persist
            }
        } else {
            entityplayermp.c.b(world.P().r()); // Set if not set
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
        this.j.a(ichatcomponent);
        int i0 = flag0 ? 1 : 0;

        this.a((Packet)(new S02PacketChat(ichatcomponent, (byte)i0)));
    }

    public void a(IChatComponent ichatcomponent) {
        this.a(ichatcomponent, true);
    }

    public StatisticsFile a(EntityPlayer entityplayer) {
        UUID uuid = entityplayer.aJ();
        StatisticsFile statisticsfile = uuid == null ? null : (StatisticsFile)this.o.get(uuid);

        if (statisticsfile == null) {
            File file1 = new File(new File("worlds"), "stats");
            File file2 = new File(file1, uuid.toString() + ".json");

            if (!file2.exists()) {
                File file3 = new File(file1, entityplayer.d_() + ".json");

                if (file3.exists() && file3.isFile()) {
                    file3.renameTo(file2);
                }
            }

            statisticsfile = new StatisticsFile(this.j, file2);
            statisticsfile.a();
            this.o.put(uuid, statisticsfile);
        }

        return statisticsfile;
    }

    public void a(int i0) {
        this.r = i0;
        for (net.canarymod.api.world.World world : this.j.getWorldManager().getAllWorlds()) {
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

    public EntityPlayerMP a(UUID uuid) {
        return (EntityPlayerMP)this.f.get(uuid);
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
