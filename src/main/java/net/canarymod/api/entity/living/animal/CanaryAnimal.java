package net.canarymod.api.entity.living.animal;

import net.canarymod.api.entity.EntityType;
import net.canarymod.api.entity.living.CanaryEntityLiving;
import net.canarymod.api.inventory.CanaryItem;
import net.canarymod.api.inventory.Item;

/**
 * Animal wrapper implementation
 *
 * @author Jason (darkdiplomat)
 */
public abstract class CanaryAnimal extends CanaryEntityLiving implements EntityAnimal {

    public CanaryAnimal(net.minecraft.entity.passive.EntityAnimal entity) {
        super(entity);
    }

    // For them squids ...
    public CanaryAnimal(net.minecraft.entity.passive.EntitySquid entity) {
        super(entity);
    }

    // For them Bats...
    public CanaryAnimal(net.minecraft.entity.passive.EntityAmbientCreature entity) {
        super(entity);
    }

    @Override
    public boolean isAnimal() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isBreedingItem(Item item) {
        if (this.getEntityType() == EntityType.BAT || this.getEntityType() == EntityType.SQUID || item == null) {
            return false;
        }
        return getAnimalHandle().c(((CanaryItem) item).getHandle());
    }

    private net.minecraft.entity.passive.EntityAnimal getAnimalHandle() {
        return (net.minecraft.entity.passive.EntityAnimal) entity;
    }
}
