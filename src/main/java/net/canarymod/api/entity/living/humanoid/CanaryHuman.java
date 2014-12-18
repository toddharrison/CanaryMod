package net.canarymod.api.entity.living.humanoid;

import net.canarymod.api.entity.EntityItem;
import net.canarymod.api.entity.living.CanaryLivingBase;
import net.canarymod.api.inventory.Item;
import net.canarymod.api.inventory.PlayerInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;

/**
 * Human implementation
 *
 * @author Jason (darkdiplomat)
 */
public abstract class CanaryHuman extends CanaryLivingBase implements Human {
    protected String prefix = null;

    public CanaryHuman(EntityPlayer entity) {
        super(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return getHandle().d_();
    }

    /**
     * {@inheritDoc}
     */
    public String getDisplayName() {
        String displayName = getHandle().getDisplayName();
        return displayName != null ? displayName : getName();
    }

    /**
     * {@inheritDoc}
     */
    public void setDisplayName(String name) {
        getHandle().setDisplayName(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void kill() {
        this.getCapabilities().setInvulnerable(false); // FORCE DEATH!
        super.kill();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityItem[] dropInventory() {
        Item[] items = getInventory().getContents();
        EntityItem[] drops = new EntityItem[items.length];

        for (int i = 0; i < items.length; i++) {
            if (items[i] == null) {
                continue;
            }
            drops[i] = getWorld().dropItem(getPosition(), items[i]);

        }
        getInventory().clearContents();
        return drops;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isBlocking() {
        return getHandle().bV();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isShooting() {
        /* mimics the isBlocking method above only with BOW Action*/
        return getHandle().bR() && getHandle().g.b().e(getHandle().g) == EnumAction.BOW;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void destroyItemHeld() {
        getHandle().bZ();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item getItemHeld() {
        Item item = getInventory().getItemInHand();

        if (item != null) {
            item.setSlot(getInventory().getSelectedHotbarSlotId());
            return item;
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dropItem(Item item) {
        getWorld().dropItem(getPosition(), item);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlayerInventory getInventory() {
        return getHandle().getPlayerInventory();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void giveItem(Item item) {
        getHandle().getPlayerInventory().addItem(item);
        getHandle().getPlayerInventory().update();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPrefix() {
        return this.prefix;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HumanCapabilities getCapabilities() {
        return getHandle().by.getCanaryCapabilities();
    }

    @Override
    public boolean isSleeping() {
        return getHandle().bI();
    }

    @Override
    public boolean isDeeplySleeping() {
        return getHandle().ce();
    }

    @Override
    public boolean isSleepingIgnored() {
        return getHandle().isSleepIgnored();
    }

    @Override
    public void setSleepingIgnored(boolean ignored) {
        getHandle().setSleepIgnored(ignored);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityPlayer getHandle() {
        return (EntityPlayer) entity;
    }
}
