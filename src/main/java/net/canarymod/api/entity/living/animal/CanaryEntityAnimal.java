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
public abstract class CanaryEntityAnimal extends CanaryEntityLiving implements EntityAnimal {

    public CanaryEntityAnimal(net.minecraft.entity.passive.EntityAnimal entity) {
        super(entity);
    }

    // For them squids ...
    public CanaryEntityAnimal(net.minecraft.entity.passive.EntitySquid entity) {
        super(entity);
    }

    // For them Bats...
    public CanaryEntityAnimal(net.minecraft.entity.passive.EntityAmbientCreature entity) {
        super(entity);
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
