package net.canarymod.api.entity;

import net.canarymod.api.entity.living.CanaryLivingBase;
import net.canarymod.api.inventory.CanaryItem;
import net.canarymod.api.inventory.Item;
import net.canarymod.api.world.position.Rotations;
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
        return CanaryItem.stackArrayToItemArray(getHandle().at());
    }

    @Override
    public void setAllEquipment(Item[] items) {
        for (int index = 0; index < 5; index++) {
            getHandle().c(index, items.length >= 5 ? CanaryItem.itemToNative(items[index]) : null);
        }
    }

    @Override
    public Item getEquipment(Slot slot) {
        return getAllEquipment()[slot.ordinal()];
    }

    @Override
    public void setEquipment(Slot slot, Item item) {
        getHandle().c(slot.ordinal(), CanaryItem.itemToNative(item));
    }

    @Override
    public boolean isSmall() {
        return getHandle().n();
    }

    @Override
    public void setSmall(boolean small) {
        getHandle().setSmall(small);
    }

    @Override
    public boolean showArms() {
        return getHandle().q();
    }

    @Override
    public void setShowArms(boolean set) {
        getHandle().setShowArms(set);
    }

    @Override
    public boolean isSlotDiabled(Slot slot, Disability disability) {
        return (getHandle().bg() & 1 << slot.ordinal() + disability.bitOffset) == 1;
    }

    @Override
    public void disableSlot(Slot slot, Disability disability) {
        if (!isSlotDiabled(slot, disability)) { // Don't apply a disable if disabled already, that would mess this all up
            getHandle().setDisabledSlots(getHandle().getDisabledSlots() + disability.slotOffsets[slot.ordinal()]);
        }
    }

    @Override
    public void enableSlot(Slot slot, Disability disability) {
        if (isSlotDiabled(slot, disability)) { // Don't remove a disability if its not disabled, that would mess this all up
            getHandle().setDisabledSlots(getHandle().getDisabledSlots() - disability.slotOffsets[slot.ordinal()]);
        }
    }

    @Override
    public boolean hasBasePlate() {
        return !getHandle().r(); // Invert result
    }

    @Override
    public void setBasePlate(boolean basePlate) {
        getHandle().setNoBaseplate(!basePlate); // Invert argument
    }

    @Override
    public boolean gravity() {
        return !getHandle().p(); // Invert result
    }

    @Override
    public void setGravity(boolean gravity) {
        getHandle().setNoGravity(!gravity); // Invert argument
    }

    @Override
    public Rotations getPartPose(RotatablePart part) {
        switch (part) {
            case HEAD:
                return rotationsFromNative(getHandle().s());
            case BODY:
                return rotationsFromNative(getHandle().t());
            case LEFTARM:
                return rotationsFromNative(getHandle().getLeftArmRotations());
            case RIGHTARM:
                return rotationsFromNative(getHandle().getRightArmRotations());
            case LEFTLEG:
                return rotationsFromNative(getHandle().getLeftLegRotations());
            case RIGHTLEG:
                return rotationsFromNative(getHandle().getRightLegRotations());
        }
        return new Rotations(0, 0, 0);
    }

    @Override
    public void setPartPose(RotatablePart part, Rotations rotations) {
        net.minecraft.util.Rotations nrots = rotationsToNative(rotations);
        switch (part) {
            case HEAD:
                getHandle().a(nrots);
                break;
            case BODY:
                getHandle().b(nrots);
                break;
            case LEFTARM:
                getHandle().c(nrots);
                break;
            case RIGHTARM:
                getHandle().d(nrots);
                break;
            case LEFTLEG:
                getHandle().e(nrots);
                break;
            case RIGHTLEG:
                getHandle().f(nrots);
                break;
        }
    }

    private Rotations rotationsFromNative(net.minecraft.util.Rotations rotations) {
        return new Rotations(rotations.b(), rotations.c(), rotations.d());
    }

    private net.minecraft.util.Rotations rotationsToNative(Rotations rotations) {
        return new net.minecraft.util.Rotations(rotations.getX(), rotations.getY(), rotations.getZ());
    }

    @Override
    public EntityArmorStand getHandle() {
        return (EntityArmorStand) entity;
    }
}
