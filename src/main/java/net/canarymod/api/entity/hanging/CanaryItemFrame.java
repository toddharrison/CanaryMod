package net.canarymod.api.entity.hanging;

import net.canarymod.api.entity.EntityType;
import net.canarymod.api.inventory.CanaryItem;
import net.canarymod.api.inventory.Item;
import net.minecraft.entity.item.EntityItemFrame;

import static net.canarymod.api.entity.EntityType.ITEMFRAME;

/**
 * ItemFrame wrapper implementation
 *
 * @author Jason (darkdiplomat)
 */
public class CanaryItemFrame extends CanaryHangingEntity implements ItemFrame {

    /**
     * Constructs a new wrapper for EntityItemFrame
     *
     * @param entity
     *         the EntityItemFrame to be wrapped
     */
    public CanaryItemFrame(EntityItemFrame entity) {
        super(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityType getEntityType() {
        return ITEMFRAME;
    }

    @Override
    public String getFqName() {
        return "ItemFrame";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item getItemInFrame() {
        return getHandle().o().getCanaryItem();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setItemInFrame(Item item) {
        getHandle().a(((CanaryItem) item).getHandle());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getItemRotation() {
        return getHandle().p();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setItemRotation(int rot) {
        getHandle().c(rot);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float getItemDropChance() {
        return getHandle().c;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setItemDropChance(float chance) {
        getHandle().c = chance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityItemFrame getHandle() {
        return (EntityItemFrame) entity;
    }

}
