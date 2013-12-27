package net.canarymod.api.entity.vehicle;

import net.canarymod.api.entity.CanaryEntity;
import net.canarymod.api.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityMinecart;

/**
 * Vehicle implementation
 *
 * @author Jason (darkdiplomat)
 */
public abstract class CanaryVehicle extends CanaryEntity implements Vehicle {

    public CanaryVehicle(EntityBoat entity) {
        super(entity);
    }

    public CanaryVehicle(EntityMinecart entity) {
        super(entity);
    }

    @Override
    public Entity getPassenger() {
        if (!isEmpty()) {
            return entity.m.getCanaryEntity();
        }
        return null;
    }

    @Override
    public boolean isBoat() {
        return (this instanceof CanaryBoat);
    }

    @Override
    public boolean isMinecart() {
        return (this instanceof CanaryMinecart);
    }

    @Override
    public boolean isEmpty() {
        return entity.m == null;
    }
}
