package net.canarymod.api.world.blocks;

import net.canarymod.Canary;
import net.canarymod.api.world.CanaryWorld;
import net.canarymod.config.Configuration;
import net.canarymod.hook.system.PermissionCheckHook;
import net.canarymod.user.Group;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.tileentity.TileEntityCommandBlock;

/**
 * CommandBlock wrapper implementation
 *
 * @author Jason (darkdiplomat)
 */
public class CanaryCommandBlock extends CanaryTileEntity implements CommandBlock {

    Group group; // The group for permission checking

    /**
     * Constructs a wrapper for TileEntityCommandBlock
     *
     * @param tileentity
     *         the TileEntityCommandBlock to wrap
     */
    public CanaryCommandBlock(TileEntityCommandBlock tileentity) {
        super(tileentity);
        group = Canary.usersAndGroups().getGroup(Configuration.getServerConfig().getCommandBlockGroupName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return getLogic().b_();
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
        getLogic().a(command);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCommand() {
        return getLogic().i();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void activate() {
        getTileEntity().a(((CanaryWorld) getWorld()).getHandle());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TileEntityCommandBlock getTileEntity() {
        return (TileEntityCommandBlock) tileentity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Group getGroup() {
        return this.group;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setGroup(Group group) {
        this.group = group;
    }

    public CommandBlockLogic getLogic() {
        return getTileEntity().getLogic();
    }

}
