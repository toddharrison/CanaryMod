package net.canarymod.api.entity;

import net.canarymod.api.entity.living.CanaryLivingBase;
import net.canarymod.api.inventory.Item;
import net.minecraft.entity.item.EntityArmorStand;

/**
 * ArmorStand wrapper implementation
 *
 * @author Jason Jones (darkdiplomat)
 */
public class CanaryArmorStand extends CanaryLivingBase implements ArmorStand {

    public CanaryArmorStand(EntityArmorStand entity) {
        super(entity);
    }

    @Override
    public String getFqName() {
        return "ArmorStand";
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.GENERIC_LIVING;
    }

    @Override
    public Item[] getAllEquipment() {
        // TODO
        return new Item[0];
    }

    @Override
    public void setAllEquipment(Item[] item) {
        // TODO
    }

    @Override
    public Item getEquipment(Slot slot) {
        // TODO
        return null;
    }

    @Override
    public void setEquipment(Slot slot, Item item) {
        // TODO

    }

    @Override
    public boolean isSmall() {
        return getHandle().n();
    }

    @Override
    public void setSmall(boolean small) {

    }

    @Override
    public boolean showArms() {
        return getHandle().q();
    }

    @Override
    public void setShowArms(boolean set) {

    }

    @Override
    public boolean isSlotDiabled(Slot slot) {
        //TODO
        return false;
    }

    @Override
    public void disableSlot(Slot slot) {
        //TODO
    }

    @Override
    public void enableSlot(Slot slot) {
        //TODO
    }

    @Override
    public boolean hasBasePlate() {
        return !getHandle().r(); // Invert result
    }

    @Override
    public void setBasePlate(boolean basePlate) {
        // TODO
    }

    @Override
    public boolean gravity() {
        return !getHandle().p(); // Invert result
    }

    @Override
    public void setGravity(boolean gravity) {
        //TODO
    }

    @Override
    public EntityArmorStand getHandle() {
        return (EntityArmorStand)entity;
    }

    //TODO
    //nbttagcompound.a("Pose", (NBTBase) this.y());
}
