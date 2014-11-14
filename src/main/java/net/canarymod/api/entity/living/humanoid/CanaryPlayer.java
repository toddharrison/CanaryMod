package net.canarymod.api.entity.living.humanoid;

import com.mojang.authlib.GameProfile;
import net.canarymod.Canary;
import net.canarymod.MathHelp;
import net.canarymod.ToolBox;
import net.canarymod.api.GameMode;
import net.canarymod.api.NetServerHandler;
import net.canarymod.api.PlayerListAction;
import net.canarymod.api.PlayerListData;
import net.canarymod.api.PlayerListEntry;
import net.canarymod.api.chat.CanaryChatComponent;
import net.canarymod.api.chat.ChatComponent;
import net.canarymod.api.entity.EntityType;
import net.canarymod.api.entity.living.animal.CanaryHorse;
import net.canarymod.api.inventory.CanaryAnimalInventory;
import net.canarymod.api.inventory.CanaryBlockInventory;
import net.canarymod.api.inventory.CanaryEntityInventory;
import net.canarymod.api.inventory.EnderChestInventory;
import net.canarymod.api.inventory.Inventory;
import net.canarymod.api.packet.CanaryPacket;
import net.canarymod.api.packet.Packet;
import net.canarymod.api.statistics.Achievement;
import net.canarymod.api.statistics.Achievements;
import net.canarymod.api.statistics.CanaryAchievement;
import net.canarymod.api.statistics.CanaryStat;
import net.canarymod.api.statistics.Stat;
import net.canarymod.api.statistics.Statistics;
import net.canarymod.api.world.CanaryWorld;
import net.canarymod.api.world.World;
import net.canarymod.api.world.blocks.CanaryAnvil;
import net.canarymod.api.world.blocks.CanaryBeacon;
import net.canarymod.api.world.blocks.CanaryBrewingStand;
import net.canarymod.api.world.blocks.CanaryDispenser;
import net.canarymod.api.world.blocks.CanaryEnchantmentTable;
import net.canarymod.api.world.blocks.CanaryFurnace;
import net.canarymod.api.world.blocks.CanaryHopperBlock;
import net.canarymod.api.world.blocks.CanarySign;
import net.canarymod.api.world.blocks.CanaryWorkbench;
import net.canarymod.api.world.blocks.Sign;
import net.canarymod.api.world.position.BlockPosition;
import net.canarymod.api.world.position.Direction;
import net.canarymod.api.world.position.Location;
import net.canarymod.chat.ChatFormat;
import net.canarymod.chat.Colors;
import net.canarymod.chat.ReceiverType;
import net.canarymod.chat.TextFormat;
import net.canarymod.config.Configuration;
import net.canarymod.hook.command.PlayerCommandHook;
import net.canarymod.hook.player.ChatHook;
import net.canarymod.hook.player.TeleportHook;
import net.canarymod.hook.player.TeleportHook.TeleportCause;
import net.canarymod.hook.system.PermissionCheckHook;
import net.canarymod.permissionsystem.PermissionProvider;
import net.canarymod.user.Group;
import net.canarymod.user.UserAndGroupsProvider;
import net.canarymod.warp.Warp;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.ContainerBeacon;
import net.minecraft.inventory.ContainerBrewingStand;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.ContainerDispenser;
import net.minecraft.inventory.ContainerEnchantment;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.inventory.ContainerHopper;
import net.minecraft.inventory.ContainerHorseInventory;
import net.minecraft.inventory.ContainerRepair;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.network.play.server.S38PacketPlayerListItem;
import net.minecraft.network.play.server.S39PacketPlayerAbilities;
import net.minecraft.util.BlockPos;
import net.minecraft.world.WorldSettings;
import net.visualillusionsent.utils.StringUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static net.canarymod.Canary.log;

/**
 * Canary Player wrapper.
 *
 * @author Chris (damagefilter)
 * @author Jason (darkdiplomat)
 */
public class CanaryPlayer extends CanaryHuman implements Player {
    private Pattern badChatPattern = Pattern.compile("[\u2302\u00D7\u00AA\u00BA\u00AE\u00AC\u00BD\u00BC\u00A1\u00AB\u00BB]");
    private List<Group> groups;
    private PermissionProvider permissions;
    private boolean muted;
    private String[] allowedIPs;
    private HashMap<String, String> defaultChatpattern = new HashMap<String, String>();
    //Global chat format setting
    private static String chatFormat = Configuration.getServerConfig().getChatFormat().replace("&", Colors.MARKER);

    public CanaryPlayer(EntityPlayerMP entity) {
        super(entity);
        initPlayerData();
    }

    @Override
    public boolean isPlayer() {
        return true;
    }

    @Override
    public ReceiverType getReceiverType() {
        return ReceiverType.PLAYER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityType getEntityType() {
        return EntityType.PLAYER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getFqName() {
        return "Player";
    }

    public UUID getUUID() {
        return EntityPlayer.a(getHandle().cc());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUUIDString() {
        return getUUID().toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void chat(final String message) {
        if (message.length() > 100) {
            kick("Message too long!");
        }
        String out = message.trim();
        Matcher m = badChatPattern.matcher(out);

        if (m.find() && !this.canIgnoreRestrictions()) {
            out = out.replaceAll(m.group(), "");
        }

        if (out.startsWith("/")) {
            executeCommand(out.split(" "));
        } else {
            if (isMuted()) {
                notice("You are currently muted!");
            } else {
                // This is a copy of the real player list already, no need to copy again (re: Collections.copy())
                List<Player> receivers = Canary.getServer().getPlayerList();
                defaultChatpattern.put("%name", getDisplayName()); // Safe to get name now
                defaultChatpattern.put("%message", out);
                defaultChatpattern.put("%group",getGroup().getName());
               
                ChatHook hook = (ChatHook) new ChatHook(this, chatFormat, receivers, defaultChatpattern).call();
                if (hook.isCanceled()) {
                    return;
                }
                receivers = hook.getReceiverList();
                String formattedMessage = hook.buildSendMessage();
                for (Player player : receivers) {
                    player.message(formattedMessage);
                }
                log.info(TextFormat.consoleFormat(formattedMessage));
            }
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void message(CharSequence message) {
        getNetServerHandler().sendMessage(message.toString());
        // Should cover all chat logging
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void message(CharSequence... messages) {
        for (CharSequence message : messages) {
            message(message);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void message(Iterable<? extends CharSequence> messages) {
        for (CharSequence message : messages) {
            message(message);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notice(CharSequence message) {
        message(Colors.LIGHT_RED + message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notice(CharSequence... messages) {
        for (CharSequence message : messages) {
            notice(message);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notice(Iterable<? extends CharSequence> messages) {
        for (CharSequence message : messages) {
            notice(message);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Location getSpawnPosition() {
        Location spawn = Canary.getServer().getDefaultWorld().getSpawnLocation();

        if (getHandle().cg() != null) {
            BlockPosition loc = new BlockPosition(getHandle().cg());
            spawn = new Location(Canary.getServer().getDefaultWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), 0.0F, 0.0F);
        }
        return spawn;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Location getHome() {
        Warp home = Canary.warps().getHome(this);

        if (home != null) {
            return home.getLocation();
        }
        return getSpawnPosition();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setHome(Location home) {
        Canary.warps().setHome(this, home);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasHome() {
        return Canary.warps().getHome(this) != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSpawnPosition(Location spawn) {
        BlockPos loc = new BlockPos((int) spawn.getX(), (int) spawn.getY(), (int) spawn.getZ());

        getHandle().a(loc, true, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getIP() {
        String ip = getHandle().a.a.b().toString();

        return ip.substring(1, ip.lastIndexOf(":"));
    }

    public String getPreviousIP() {
        if (getMetaData() != null && getMetaData().containsKey("PreviousIP")) {
            return getMetaData().getString("PreviousIP");
        }
        return "UNKNOWN";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean executeCommand(String[] command) {
        try {
            boolean toRet = false;
            PlayerCommandHook hook = (PlayerCommandHook) new PlayerCommandHook(this, command).call();
            if (hook.isCanceled()) {
                return true;
            } // someone wants us not to execute the command. So lets do them the favor

            String cmdName = command[0];
            if (cmdName.startsWith("/")) {
                cmdName = cmdName.substring(1);
            }
            toRet = Canary.commands().parseCommand(this, cmdName, command);
            if (!toRet) {
                Canary.log.debug("Vanilla Command Execution...");
                toRet = Canary.getServer().consoleCommand(StringUtils.joinString(command, " ", 0), this); // Vanilla Commands passed
            }
            if (toRet) {
                log.info("Command used by " + getName() + ": " + StringUtils.joinString(command, " ", 0));
            }
            return toRet;
        } catch (Throwable ex) {
            log.error("Exception in command handler: ", ex);
            if (isAdmin()) {
                message(Colors.LIGHT_RED + "Exception occured. " + ex.getMessage());
            }
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendPacket(Packet packet) {
        getHandle().a.a(((CanaryPacket) packet).getPacket());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Group getGroup() {
        return groups.get(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Group[] getPlayerGroups() {
        return groups.toArray(new Group[groups.size()]);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setGroup(Group group) {
        groups.set(0, group);
        Canary.usersAndGroups().addOrUpdatePlayerData(this);
        defaultChatpattern.put("%prefix", getPrefix()); // Update Prefix
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addGroup(Group group) {
        if (!groups.contains(group)) {
            groups.add(group);
            Canary.usersAndGroups().addOrUpdatePlayerData(this);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasPermission(String permission) {
        if (isOperator()) {
            return true;
        }
        PermissionCheckHook hook = new PermissionCheckHook(permission, this, false);
        // If player has the permission set, use its personal permissions
        if (permissions.pathExists(permission)) {
            hook.setResult(permissions.queryPermission(permission));
            Canary.hooks().callHook(hook);
            return hook.getResult();
        }
        // Only main group is set
        else if (groups.size() == 1) {
            hook.setResult(groups.get(0).hasPermission(permission));
            Canary.hooks().callHook(hook);
            return hook.getResult();
        }

        // Check sub groups
        for (int i = 1; i < groups.size(); i++) {
            // First group that
            if (groups.get(i).getPermissionProvider().pathExists(permission)) {
                hook.setResult(groups.get(i).hasPermission(permission));
                Canary.hooks().callHook(hook);
                return hook.getResult();
            }
        }

        // No subgroup has permission defined, use what base group has to say
        hook.setResult(groups.get(0).hasPermission(permission));
        Canary.hooks().callHook(hook);
        return hook.getResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean safeHasPermission(String permission) {
        if (isOperator()) {
            return true;
        }
        // If player has the permission set, use its personal permissions
        if (permissions.pathExists(permission)) {
            return permissions.queryPermission(permission);
        }
        // Only main group is set
        else if (groups.size() == 1) {
            return groups.get(0).hasPermission(permission);
        }

        // Check sub groups
        for (int i = 1; i < groups.size(); i++) {
            // First group that
            if (groups.get(i).getPermissionProvider().pathExists(permission)) {
                return groups.get(i).hasPermission(permission);
            }
        }

        // No subgroup has permission defined, use what base group has to say
        return groups.get(0).hasPermission(permission);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isOperator() {
        return Canary.ops().isOpped(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAdmin() {
        return isOperator() || hasPermission("canary.super.administrator");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canBuild() {
        return isAdmin() || hasPermission("canary.world.build");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCanBuild(boolean canModify) {
        permissions.addPermission("canary.world.build", canModify);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canIgnoreRestrictions() {
        return isAdmin() || hasPermission("canary.super.ignoreRestrictions");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCanIgnoreRestrictions(boolean canIgnore) {
        permissions.addPermission("canary.super.ignoreRestrictions", canIgnore, -1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isMuted() {
        return muted;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMuted(boolean muted) {
        this.muted = muted;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PermissionProvider getPermissionProvider() {
        return permissions;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EnderChestInventory getEnderChestInventory() {
        return ((EntityPlayerMP) entity).getEnderChestInventory();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isInGroup(Group group, boolean parents) {
        for (Group g : groups) {
            if (g.getName().equals(group.getName())) {
                return true;
            }
            if (parents) {
                for (Group parent : g.parentsToList()) {
                    if (parent.getName().equals(group.getName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void kick(String reason) {
        getHandle().a.c(reason);
    }

    public void kickNoHook(String reason) {
        getHandle().a.kickNoHook(reason);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NetServerHandler getNetServerHandler() {
        return getHandle().getServerHandler();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isInGroup(String group, boolean parents) {
        for (Group g : groups) {
            if (g.getName().equals(group)) {
                return true;
            }
            if (parents) {
                for (Group parent : g.parentsToList()) {
                    if (parent.getName().equals(group)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String[] getAllowedIPs() {
        return allowedIPs;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Direction getCardinalDirection() {
        double degrees = (getRotation() - 180) % 360;

        if (degrees < 0) {
            degrees += 360.0;
        }

        if (0 <= degrees && degrees < 22.5) {
            return Direction.NORTH;
        } else if (22.5 <= degrees && degrees < 67.5) {
            return Direction.NORTHEAST;
        } else if (67.5 <= degrees && degrees < 112.5) {
            return Direction.EAST;
        } else if (112.5 <= degrees && degrees < 157.5) {
            return Direction.SOUTHEAST;
        } else if (157.5 <= degrees && degrees < 202.5) {
            return Direction.SOUTH;
        } else if (202.5 <= degrees && degrees < 247.5) {
            return Direction.SOUTHWEST;
        } else if (247.5 <= degrees && degrees < 292.5) {
            return Direction.WEST;
        } else if (292.5 <= degrees && degrees < 337.5) {
            return Direction.NORTHWEST;
        } else if (337.5 <= degrees && degrees < 360.0) {
            return Direction.NORTH;
        } else {
            return Direction.ERROR;
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initPlayerData() {
        UserAndGroupsProvider provider = Canary.usersAndGroups();
        String uuid = getUUIDString();

        boolean isNew = !provider.playerExists(uuid);
        String[] data = provider.getPlayerData(uuid);
        Group[] subs = provider.getModuleGroupsForPlayer(uuid);
        groups = new LinkedList<Group>();
        groups.add(Canary.usersAndGroups().getGroup(data[1]));
        for (Group g : subs) {
            if (g != null) {
                groups.add(g);
            }
        }

        permissions = Canary.permissionManager().getPlayerProvider(uuid, getWorld().getFqName());
        if (data[0] != null && (!data[0].isEmpty() && !data[0].equals(" "))) {
            prefix = ToolBox.stringToNull(data[0]);
        }

        if (data[2] != null && !data[2].isEmpty()) {
            muted = Boolean.valueOf(data[2]);
        }
        //defaultChatpattern.put("%name", getDisplayName()); // Display name not initialized at this time
        defaultChatpattern.put("%prefix", getPrefix());
        if (isNew) {
            provider.addOrUpdatePlayerData(this);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeGroup(Group group) {
        boolean success = groups.remove(group);
        if (success) {
            Canary.usersAndGroups().addOrUpdatePlayerData(this);
        }
        return success;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeGroup(String group) {
        Group g = Canary.usersAndGroups().getGroup(group);
        if (g == null) {
            return false;
        }
        return removeGroup(g);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPing() {
        return getHandle().h;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlayerListEntry getPlayerListEntry(boolean shown) {
        return new PlayerListEntry(this, shown);
    }

    @Override
    public PlayerListData getPlayerListData(PlayerListAction playerListAction) {
        return new PlayerListData(playerListAction, getGameProfile(), getPing(), getMode(), getDisplayNameComponent());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendPlayerListEntry(PlayerListEntry plentry) {
        // AND dead...
    }

    @Override
    public void sendPlayerListData(PlayerListData playerListData) {
        getHandle().a.a(new S38PacketPlayerListItem(playerListData.getAction(), playerListData));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addExhaustion(float exhaustion) {
        getHandle().ck().a(exhaustion);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setExhaustion(float exhaustion) {
        getHandle().ck().setExhaustionLevel(exhaustion);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float getExhaustionLevel() {
        return getHandle().ck().getExhaustionLevel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setHunger(int hunger) {
        getHandle().ck().setFoodLevel(hunger);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getHunger() {
        return getHandle().ck().a();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addExperience(int experience) {
        getHandle().addXP(experience);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeExperience(int experience) {
        getHandle().removeXP(experience);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getExperience() {
        return getHandle().bA;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setExperience(int xp) {
        if (xp < 0) {
            return;
        }
        getHandle().setXP(xp);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLevel() {
        return getHandle().bz;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLevel(int level) {
        this.setExperience(ToolBox.levelToExperience(level));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addLevel(int level) {
        this.addExperience(ToolBox.levelToExperience(level));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeLevel(int level) {
        this.removeExperience(ToolBox.levelToExperience(level));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSleeping() {
        return getHandle().bI();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDeeplySleeping() {
        return getHandle().ce();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getModeId() {
        return getHandle().c.b().a();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameMode getMode() {
        return GameMode.fromId(getHandle().c.b().a());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setModeId(int mode) {
        // Adjust mode, make it null if number is invalid
        WorldSettings.GameType gt = WorldSettings.a(mode);
        EntityPlayerMP ent = getHandle();

        if (ent.c.b() != gt) {
            ent.c.a(gt);
            ent.a.a(new S2BPacketChangeGameState(3, mode));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMode(GameMode mode) {
        this.setModeId(mode.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void refreshCreativeMode() {
        if (getModeId() == 1 || Configuration.getWorldConfig(getWorld().getFqName()).getGameMode() == GameMode.CREATIVE) {
            getHandle().c.a(WorldSettings.a(1));
        } else {
            getHandle().c.a(WorldSettings.a(0));
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void teleportTo(double x, double y, double z, float pitch, float rotation) {
        this.teleportTo(x, y, z, pitch, rotation, getWorld());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void teleportTo(double x, double y, double z, float pitch, float rotation, World dim) {
        this.teleportTo(x, y, z, pitch, rotation, dim, TeleportHook.TeleportCause.PLUGIN);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void teleportTo(Location location, TeleportCause cause) {
        this.teleportTo(location.getX(), location.getY(), location.getZ(), location.getPitch(), location.getRotation(), location.getWorld(), cause);
    }

    protected void teleportTo(double x, double y, double z, float pitch, float rotation, World world, TeleportHook.TeleportCause cause) {
        // If in a vehicle - eject before teleporting.
        if (isRiding()) {
            dismount();
        }
        if (!world.equals(this.getWorld())) {
            Canary.getServer().getConfigurationManager().switchDimension(this, world, cause == TeleportCause.WARP && Configuration.getWorldConfig(world.getFqName()).allowWarpAutoLoad());
        }

        getHandle().a.a(x, y, z, rotation, pitch, getWorld().getType().getId(), getWorld().getName(), cause);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPrefix() {
        if (prefix != null) {
            return prefix;
        }
        else if (groups.get(0).getPrefix() != null) {
            return groups.get(0).getPrefix();
        }
        else {
            return ChatFormat.WHITE.toString();
        }
    }

    @Override
    public boolean isOnline() {
        return Canary.getServer().getPlayer(getName()) != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPrefix(String prefix) {
        super.setPrefix(prefix);
        Canary.usersAndGroups().addOrUpdatePlayerData(this);
        this.defaultChatpattern.put("%prefix", getPrefix());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateCapabilities() {
        this.sendPacket(new CanaryPacket(new S39PacketPlayerAbilities(((CanaryHumanCapabilities) getCapabilities()).getHandle())));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void openInventory(Inventory inventory) {
        if (inventory == null) {
            return;
        }
        switch (inventory.getInventoryType()) {
            case CHEST:
                ContainerChest chest = new ContainerChest(getHandle().bg, ((CanaryBlockInventory)inventory).getInventoryHandle(), this.getHandle());
                chest.setInventory(inventory);
                getHandle().openContainer(chest);
                return;
            case CUSTOM: // Same action as MINECART_CHEST
            case MINECART_CHEST:
                ContainerChest eChest = new ContainerChest(getHandle().bg, ((CanaryEntityInventory)inventory).getHandle(), this.getHandle());
                eChest.setInventory(inventory);
                getHandle().openContainer(eChest);
                return;
            case ANVIL:
                getHandle().openContainer(((CanaryAnvil)inventory).getContainer());
                return;
            case ANIMAL:
                getHandle().openContainer(new ContainerHorseInventory(getHandle().bg, ((CanaryAnimalInventory)inventory).getHandle(), ((CanaryHorse)((CanaryAnimalInventory)inventory).getOwner()).getHandle(), this.getHandle()));
                return;
            case BEACON:
                getHandle().openContainer(new ContainerBeacon(getHandle().bg, ((CanaryBeacon)inventory).getTileEntity()));
                return;
            case BREWING:
                getHandle().openContainer(new ContainerBrewingStand(getHandle().bg, ((CanaryBrewingStand)inventory).getTileEntity()));
                return;
            case DISPENSER:
                getHandle().openContainer(new ContainerDispenser(getHandle().bg, ((CanaryDispenser)inventory).getTileEntity()));
                return;
            case ENCHANTMENT:
                getHandle().openContainer(((CanaryEnchantmentTable)inventory).getContainer());
                return;
            case FURNACE:
                getHandle().openContainer(new ContainerFurnace(getHandle().bg, ((CanaryFurnace)inventory).getTileEntity()));
                return;
            case HOPPER:
                getHandle().openContainer(new ContainerHopper(getHandle().bg, ((CanaryHopperBlock)inventory).getTileEntity(), this.getHandle()));
                return;
            case MINECART_HOPPER:
                getHandle().openContainer(new ContainerHopper(getHandle().bg, ((CanaryEntityInventory)inventory).getHandle(), this.getHandle()));
                return;
            case WORKBENCH:
                getHandle().openContainer(((CanaryWorkbench)inventory).getContainer());
                return;
            default: // do nothing
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createAndOpenWorkbench() {
        Inventory bench_inv = new ContainerWorkbench(getHandle().bg, ((CanaryWorld)getWorld()).getHandle(), new BlockPos(-1, -1, -1)).getInventory();
        bench_inv.setCanOpenRemote(true);
        openInventory(bench_inv);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createAndOpenAnvil() {
        Inventory anvil_inv = new ContainerRepair(getHandle().bg, ((CanaryWorld)getWorld()).getHandle(), new BlockPos(-1, -1, -1), getHandle()).getInventory();
        anvil_inv.setCanOpenRemote(true);
        openInventory(anvil_inv);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createAndOpenEnchantmentTable(int bookshelves) {
        CanaryEnchantmentTable ench_inv = (CanaryEnchantmentTable)new ContainerEnchantment(getHandle().bg, ((CanaryWorld)getWorld()).getHandle(), new BlockPos(-1, -1, -1)).getInventory();
        ench_inv.setCanOpenRemote(true);
        ench_inv.fakeCaseCount = MathHelp.setInRange(bookshelves, 0, 15);
        openInventory(ench_inv);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void closeWindow() {
        getHandle().n();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void openSignEditWindow(Sign sign) {
        getHandle().a(((CanarySign) sign).getTileEntity());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getFirstJoined() {
        return getHandle().getFirstJoined();
    }

    @Override
    public String getLastJoined() {
        return getHandle().getLastJoined();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getTimePlayed() {
        return getHandle().getTimePlayed();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getLocale() {
        return getHandle().bG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendChatComponent(ChatComponent chatComponent) {
        this.sendPacket(new CanaryPacket(new S02PacketChat(((CanaryChatComponent) chatComponent).getNative())));
    }

    public void resetNativeEntityReference(EntityPlayerMP entityPlayerMP) {
        this.entity = entityPlayerMP;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hidePlayer(Player player) {
        this.getWorld().getEntityTracker().hidePlayer(player, this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hidePlayerGlobal() {
        this.getWorld().getEntityTracker().hidePlayerGlobal(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showPlayer(Player player) {
        this.getWorld().getEntityTracker().showPlayer(player, this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showPlayerGlobal() {
        this.getWorld().getEntityTracker().showPlayerGlobal(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPlayerHidden(Player player, Player isHidden) {
        return this.getWorld().getEntityTracker().isPlayerHidden(player, this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCompassTarget(int x, int y, int z) {
        this.sendPacket(new CanaryPacket(new net.minecraft.network.play.server.S05PacketSpawnPosition(new BlockPos(x, y, z))));
    }

    @Override
    public GameProfile getGameProfile() {
        return getHandle().cc();
    }

    @Override
    public ChatComponent getDisplayNameComponent() {
        return getHandle().E() == null ? null : getHandle().E().getWrapper();
    }

    @Override
    public void setDisplayNameComponent(ChatComponent chatComponent){
        getHandle().setDisplayNameComponent(chatComponent != null ? ((CanaryChatComponent) chatComponent).getNative() : null);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void setStat(Stat stat, int value) {
        getHandle().A().a(getHandle(), ((CanaryStat) stat).getHandle(), value);
    }

    @Override
    public void setStat(Statistics statistics, int value) {
        setStat(statistics.getInstance(), value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void increaseStat(Stat stat, int value) {
        if (value < 0) return;
        getHandle().a(((CanaryStat) stat).getHandle(), value);
    }

    @Override
    public void increaseStat(Statistics statistics, int value) {
        increaseStat(statistics.getInstance(), value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void decreaseStat(Stat stat, int value) {
        if (value < 0) return;
        setStat(stat, getStat(stat) - value);
    }

    @Override
    public void decreaseStat(Statistics statistics, int value) {
        decreaseStat(statistics.getInstance(), value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getStat(Stat stat) {
        return getHandle().A().a(((CanaryStat) stat).getHandle());
    }

    @Override
    public int getStat(Statistics statistics) {
        return getStat(statistics.getInstance());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void awardAchievement(Achievement achievement) {
        // Need to give all parent achievements
        if (achievement.getParent() != null && !hasAchievement(achievement.getParent())) {
            awardAchievement(achievement.getParent());
        }
        getHandle().A().b(getHandle(), ((CanaryAchievement) achievement).getHandle(), 1);
        getHandle().A().b(getHandle());
    }

    @Override
    public void awardAchievement(Achievements achievements) {
        awardAchievement(achievements.getInstance());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeAchievement(Achievement achievement) {
        // Need to remove any children achievements
        for (Achievements achieve : Achievements.values()) {
            Achievement child = achieve.getInstance();
            if (child.getParent() == achievement && hasAchievement(child)) {
                removeAchievement(child);
            }
        }
        getHandle().A().a(getHandle(), ((CanaryAchievement) achievement).getHandle(), 0);
    }

    @Override
    public void removeAchievement(Achievements achievements) {
        removeAchievement(achievements.getInstance());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasAchievement(Achievement achievement) {
        return getHandle().A().a(((CanaryAchievement) achievement).getHandle());
    }

    @Override
    public boolean hasAchievement(Achievements achievements) {
        return hasAchievement(achievements.getInstance());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Inventory getSecondInventory() {
        if (getHandle().bi == getHandle().bh) {
            return null;
        }
        return getHandle().bi.getInventory();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format("Player[name=%s]", getName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Player)) {
            return false;
        }
        final Player other = (Player) obj;

        return getName().equals(other.getName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = 7;

        hash = 89 * hash + (this.getName() != null ? this.getName().hashCode() : 0);
        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityPlayerMP getHandle() {
        return (EntityPlayerMP) entity;
    }
}
