package net.canarymod.api.entity.living.monster;

import net.canarymod.api.entity.EntityType;
import net.minecraft.entity.monster.EntitySilverfish;

/**
 * Silverfish wrapper implementation
 *
 * @author Jason (darkdiplomat)
 */
public class CanarySilverfish extends CanaryEntityMob implements Silverfish {

    /**
     * Constructs a new wrapper for EntitySilverfish
     *
     * @param entity
     *         the EntitySilverfish to wrap
     */
    public CanarySilverfish(EntitySilverfish entity) {
        super(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityType getEntityType() {
        return EntityType.SILVERFISH;
    }

    @Override
    public String getFqName() {
        return "Silverfish";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getAllySummonCooldown() {
        throw new UnsupportedOperationException("Minecraft update broke this shit");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAllySummonCooldown(int cooldown) {
        throw new UnsupportedOperationException("Minecraft update broke this shit");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntitySilverfish getHandle() {
        return (EntitySilverfish) entity;
    }
}
