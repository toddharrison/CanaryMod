package net.canarymod.api.entity.vehicle;

import net.canarymod.Canary;
import net.canarymod.api.entity.EntityType;
import net.canarymod.api.world.CanaryWorld;
import net.canarymod.chat.ReceiverType;
import net.canarymod.config.Configuration;
import net.canarymod.hook.system.PermissionCheckHook;
import net.canarymod.logger.Logman;
import net.canarymod.user.Group;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.entity.EntityMinecartCommandBlock;

import static net.canarymod.Canary.log;

/**
 * Command Block Minecart wrapper implementation
 *
 * @author Jason (darkdiplomat)
 */
public class CanaryCommandBlockMinecart extends CanaryMinecart implements CommandBlockMinecart {
    private static final String cmdPrefix = "[CommandBlockMinecart:%s] %s";
    Group group; // The group for permission checking

    /**
     * Constructs a new wrapper for EntityMinecartCommandBlock
     *
     * @param entity
     *         the EntityMinecartCommandBlock to be wrapped
     */
    public CanaryCommandBlockMinecart(EntityMinecartCommandBlock entity) {
        super(entity);
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.COMMANDBLOCKMINECART;
    }

    @Override
    public String getFqName() {
        return "CommandBlock Minecart";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return getLogic().d_();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setName(String name) {
        getLogic().b(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notice(CharSequence message) {
        log.info(Logman.NOTICE, String.format(cmdPrefix, getName(), message));
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
    public void message(CharSequence message) {
        log.info(Logman.MESSAGE, String.format(cmdPrefix, getName(), message));
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
    public boolean hasPermission(String node) {
        return Configuration.getServerConfig().isCommandBlockOpped() || (getGroup() != null && ((PermissionCheckHook) new PermissionCheckHook(node, this, group.hasPermission(node)).call()).getResult());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean safeHasPermission(String node) {
        return Configuration.getServerConfig().isCommandBlockOpped() || (getGroup() != null && group.hasPermission(node));
    }

    @Override
    public ReceiverType getReceiverType() {
        return ReceiverType.COMMANDBLOCKENTITY;
    }

    @Override
    public String getLocale() {
        return Configuration.getServerConfig().getServerLocale();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCommand(String command) {
        getLogic().a(command);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCommand() {
        return getLogic().l();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void activate() {
        getLogic().a(((CanaryWorld) getWorld()).getHandle());
    }

    @Override
    public Group getGroup() {
        if (group == null) {
            String gName = Configuration.getServerConfig().getCommandBlockGroupName();
            if (gName != null && Canary.usersAndGroups().groupExists(gName)) {
                group = Canary.usersAndGroups().getGroup(gName);
            } else {
                Canary.log.warn("CommandBlock has a bad group configuration... Please check your config and fix this.");
                group = Canary.usersAndGroups().getDefaultGroup();
            }
        }
        return group;
    }

    @Override
    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public EntityMinecartCommandBlock getHandle() {
        return (EntityMinecartCommandBlock) entity;
    }

    public CommandBlockLogic getLogic() {
        return getHandle().getLogic();
    }
}
