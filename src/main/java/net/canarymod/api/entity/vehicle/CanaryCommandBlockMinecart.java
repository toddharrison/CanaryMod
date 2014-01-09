package net.canarymod.api.entity.vehicle;

import net.canarymod.Canary;
import net.canarymod.api.entity.EntityType;
import net.canarymod.api.world.CanaryWorld;
import net.canarymod.config.Configuration;
import net.canarymod.hook.system.PermissionCheckHook;
import net.canarymod.user.Group;
import net.minecraft.entity.EntityMinecartCommandBlock;

/**
 * Command Block Minecart wrapper implementation
 *
 * @author Jason (darkdiplomat)
 */
public class CanaryCommandBlockMinecart extends CanaryMinecart implements CommandBlockMinecart {
    Group group = Canary.usersAndGroups().getGroup(Configuration.getServerConfig().getCommandBlockGroupName()); // The group for permission checking

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
        return getHandle().getLogic().b_();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setName(String name) {
        getHandle().getLogic().b(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notice(String message) {
        Canary.logDebug("[NOTICE] ".concat(message));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void message(String message) {
        Canary.logDebug(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasPermission(String node) {
        PermissionCheckHook hook = new PermissionCheckHook(node, this, group.hasPermission(node));
        Canary.hooks().callHook(hook);
        return hook.getResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean safeHasPermission(String node) {
        return group.hasPermission(node);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCommand(String command) {
        getHandle().getLogic().a(command);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCommand() {
        return getHandle().getLogic().i();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void activate() {
        getHandle().getLogic().a(((CanaryWorld) getWorld()).getHandle());
    }

    @Override
    public Group getGroup() {
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
}
