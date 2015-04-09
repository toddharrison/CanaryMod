package net.canarymod.api.entity.living.humanoid;

import net.canarymod.Canary;
import net.canarymod.api.chat.CanaryChatComponent;
import net.canarymod.api.chat.ChatComponent;
import net.canarymod.api.entity.EntityItem;
import net.canarymod.api.entity.living.CanaryLivingBase;
import net.canarymod.api.inventory.Item;
import net.canarymod.api.inventory.PlayerInventory;
import net.canarymod.api.world.position.Location;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IChatComponent;

/**
 * Human implementation
 *
 * @author Jason (darkdiplomat)
 */
public abstract class CanaryHuman extends CanaryLivingBase implements Human {
    protected String prefix = null;
    private boolean sleepIgnored; // Fake Sleeping
    protected ChatComponent displayName;

    public Location canaryRespawn; // Temporary until respawning is reworked properly

    public CanaryHuman(EntityPlayer entity) {
        super(entity);
    }

    @Override
    public String getName() {
        return getHandle().d_();
    }

    @Override
    public String getDisplayName() {
        return displayName != null ? displayName.getFullText() : null;
    }

    @Override
    public void setDisplayName(String name) {
        this.setDisplayNameComponent(name != null && !name.isEmpty() ? Canary.factory().getChatComponentFactory().newChatComponent(name) : null);
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
        return sleepIgnored;
    }

    @Override
    public void setSleepingIgnored(boolean ignored) {
        this.sleepIgnored = ignored;
    }

    @Override
    public boolean isUsingItem() {
        return getHandle().bR();
    }

    @Override
    public Item getItemInUse(){
        return getHandle().g != null ? getHandle().g.getCanaryItem() : null;
    }

    public ChatComponent getDisplayNameComponent() {
        return this.displayName;
    }

    public void setDisplayNameComponent(ChatComponent displayName) {
        this.displayName = displayName;
        String serial = "";
        if (displayName != null) {
            serial = IChatComponent.Serializer.a(((CanaryChatComponent)displayName).getNative());
        }
        metadata.put("displayName", serial);
    }

    /**
     * Writes Canary added NBT tags (Not inside the Canary meta tag)
     */
    public NBTTagCompound writeCanaryNBT(NBTTagCompound nbttagcompound) {
        nbttagcompound.a("SleepingIgnored", this.sleepIgnored);
        return super.writeCanaryNBT(nbttagcompound);
    }

    /**
     * Reads Canary added NBT tags (Not inside the Canary meta tag)
     */
    public void readCanaryNBT(NBTTagCompound nbttagcompound) {
        if (nbttagcompound.c("SleepingIgnored")) {
            this.sleepIgnored = nbttagcompound.n("SleepingIgnored");
        }
        super.readCanaryNBT(nbttagcompound);
    }

    @Override
    public EntityPlayer getHandle() {
        return (EntityPlayer) entity;
    }
}
