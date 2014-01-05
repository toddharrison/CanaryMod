package net.canarymod.api.entity;

import net.canarymod.api.inventory.CanaryItem;
import net.canarymod.api.inventory.Item;

/**
 * EntityItem wrapper implementation
 *
 * @author Jason (darkdiplomat)
 */
public class CanaryEntityItem extends CanaryEntity implements EntityItem {

    /**
     * Constructs a new wrapper for EntityItem
     *
     * @param entity
     *         the EntityItem to be wrapped
     */
    public CanaryEntityItem(net.minecraft.entity.item.EntityItem entity) {
        super(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityType getEntityType() {
        return EntityType.ENTITYITEM;
    }

    @Override
    public String getFqName() {
        return "Item";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAge(short age) {
        getHandle().a = age;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public short getAge() {
        return (short) getHandle().a;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPickUpDelay() {
        return getHandle().b;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPickUpDelay(int delay) {
        getHandle().b = delay;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public short getHealth() {
        return (short) getHandle().e;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setHealth(short health) {
        getHandle().e = Math.min(health, 255);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CanaryItem getItem() {
        return getHandle().f().getCanaryItem();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setItem(Item item) {
        if (item != null) {
            getHandle().a(((CanaryItem) item).getHandle());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getOwner() {
        return getHandle().i();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOwner(String owner) {
        getHandle().a(owner);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getThrower() {
        return getHandle().j();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setThrower(String thrower) {
        getHandle().b(thrower);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public net.minecraft.entity.item.EntityItem getHandle() {
        return (net.minecraft.entity.item.EntityItem) entity;
    }
}
