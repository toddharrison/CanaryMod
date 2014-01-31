package net.canarymod.api.factory;

import net.canarymod.api.inventory.CanaryEnchantment;
import net.canarymod.api.inventory.CanaryItem;
import net.canarymod.api.inventory.Enchantment;
import net.canarymod.api.inventory.Enchantment.Type;
import net.canarymod.api.inventory.Item;
import net.canarymod.api.inventory.ItemType;
import net.visualillusionsent.utils.StringUtils;

/**
 * Item Factory
 * 
 * @author Brian (WWOL)
 * @author Jason (darkdiplomat)
 */
public class CanaryItemFactory implements ItemFactory {

    /**
     * {@inheritDoc}
     */
    @Override
    public Item newItem(int id) {
        return this.newItem(id, 0, 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item newItem(int id, int damage) {
        return this.newItem(id, damage, 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item newItem(int id, int damage, int stackSize) {
        CanaryItem item = new CanaryItem(id, stackSize, damage);
        return item;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item newItem(ItemType type) {
        return type == null ? null : new CanaryItem(type.getId(), 0, type.getData());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item newItem(ItemType type, int damage) {
        return type == null ? null : new CanaryItem(type.getId(), 0, damage);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item newItem(ItemType type, int damage, int stackSize) {
        return type == null ? null : new CanaryItem(type.getId(), stackSize, damage);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item newItem(Item item) {
        return item == null ? null : new CanaryItem(((CanaryItem) item).getHandle().m());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item newItem(String commandInput) {
        String[] data = commandInput.split(":"); // ["minecraft", "wool", "3"]
        CanaryItem item;

        String type;
        int meta = 0;
        if (data[data.length - 1].matches("\\d+")) {
            meta = Integer.parseInt(data[data.length - 1]);
            type = StringUtils.joinString(data, ":", 0, data.length - 2);
        } else {
            type = StringUtils.joinString(data, ":", 0);
        }

        if (type.matches("\\d+")) {
            item = (CanaryItem) (newItem(ItemType.fromIdAndData(Integer.parseInt(type), meta)));
        } else {
            item = (CanaryItem) (newItem(ItemType.fromStringAndData(type, meta)));
        }

        return item;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item newItem(int id, int damage, Enchantment[] enchantments) {
        CanaryItem item = new CanaryItem(id, 0, damage);
        item.setEnchantments(enchantments);
        return item;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Enchantment newEnchantment(short id, short level) {
        return newEnchantment(Enchantment.Type.fromId(id), level);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Enchantment newEnchantment(Type type, short level) {
        if (type != null) {
            return new CanaryEnchantment(type, level);
        }
        return null;
    }
}
