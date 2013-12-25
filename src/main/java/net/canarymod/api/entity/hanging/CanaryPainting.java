package net.canarymod.api.entity.hanging;

import net.canarymod.api.entity.EntityType;
import net.minecraft.entity.item.EntityPainting;

import static net.canarymod.api.entity.EntityType.PAINTING;

/**
 * Painting wrapper implementation
 *
 * @author Jason (darkdiplomat)
 */
public class CanaryPainting extends CanaryHangingEntity implements Painting {

    /**
     * Constructs a new wrapper for EntityPaintings
     *
     * @param entity
     *         the EntityItemFrame to be wrapped
     */
    public CanaryPainting(EntityPainting entity) {
        super(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityType getEntityType() {
        return PAINTING;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getFqName() {
        return "Painting";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArtType getArtType() {
        return ArtType.values()[getHandle().e.ordinal()];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setArtType(ArtType type) {
        getHandle().e = EntityPainting.EnumArt.values()[type.ordinal()];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSizeX() {
        return getHandle().f();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSizeY() {
        return getHandle().i();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getOffsetX() {
        return getHandle().e.E;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getOffsetY() {
        return getHandle().e.F;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityPainting getHandle() {
        return (EntityPainting) entity;
    }

}
