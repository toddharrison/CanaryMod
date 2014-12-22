package net.canarymod.api.entity.living.humanoid;

import net.canarymod.api.entity.EntityItem;
import net.canarymod.api.entity.EntityType;
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

    @Override
    public String getName() {
        return getHandle().d_();
    }

    @Override
    public String getDisplayName() {
        String displayName = getHandle().getDisplayName();
        return displayName != null ? displayName : getName();
    }

    @Override
    public void setDisplayName(String name) {
        getHandle().setDisplayName(name);
    }

    @Override
    public void kill() {
        this.getCapabilities().setInvulnerable(false); // FORCE DEATH!
        super.kill();
    }

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

    @Override
    public boolean isBlocking() {
        return getHandle().bV();
    }

    @Override
    public boolean isShooting() {
        /* mimics the isBlocking method above only with BOW Action*/
        return getHandle().bR() && getHandle().g.b().e(getHandle().g) == EnumAction.BOW;
    }

    @Override
    public void destroyItemHeld() {
        getHandle().bZ();
    }

    @Override
    public Item getItemHeld() {
        Item item = getInventory().getItemInHand();

        if (item != null) {
            item.setSlot(getInventory().getSelectedHotbarSlotId());
            return item;
        }
        return null;
    }

    @Override
    public void dropItem(Item item) {
        getWorld().dropItem(getPosition(), item);
    }

    @Override
    public PlayerInventory getInventory() {
        return getHandle().getPlayerInventory();
    }

    @Override
    public void giveItem(Item item) {
        getHandle().getPlayerInventory().addItem(item);
        getHandle().getPlayerInventory().update();
    }

    @Override
    public String getPrefix() {
        return this.prefix;
    }

    @Override
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

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

    @Override
    public boolean isUsingItem() {
        return getHandle().bR();
    }

    @Override
    public Item getItemInUse(){
        return getHandle().g != null ? getHandle().g.getCanaryItem() : null;
    }

    @Override
    public EntityPlayer getHandle() {
        return (EntityPlayer) entity;
    }
}
