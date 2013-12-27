package net.canarymod.api.entity.vehicle;

import net.canarymod.api.entity.Entity;
import net.canarymod.api.entity.living.animal.CanaryAnimal;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityPig;

/**
 * Animal Vehicle implementation
 *
 * @author Jason (darkdiplomat)
 */
public abstract class CanaryAnimalVehicle extends CanaryAnimal implements Vehicle {

    public CanaryAnimalVehicle(EntityPig entity) {
        super(entity);
    }

    public CanaryAnimalVehicle(EntityHorse entity) {
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
        return false;
    }

    @Override
    public boolean isMinecart() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return entity.m == null;
    }
}