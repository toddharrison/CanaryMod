package net.canarymod.api.entity.living.animal;

import net.canarymod.api.entity.EntityType;
import net.minecraft.entity.passive.EntityRabbit;

/**
 * EntityRabbit wrapper implementation
 *
 * @author Jason Jones (darkdiplomat)
 */
public class CanaryRabbit extends CanaryAnimal implements Rabbit {

    public CanaryRabbit(EntityRabbit rabbit) {
        super(rabbit);
    }

    @Override
    public String getFqName() {
        return "Rabbit";
    }

    @Override
    public EntityType getEntityType() {
        return null;
    }
}
