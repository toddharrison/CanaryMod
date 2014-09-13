package net.canarymod.api.entity.living;

import net.canarymod.api.PathFinder;
import net.canarymod.api.ai.AIManager;
import net.canarymod.api.entity.CanaryEntity;
import net.canarymod.api.entity.EntityType;
import net.canarymod.api.inventory.CanaryItem;
import net.canarymod.api.inventory.Item;
import net.canarymod.api.world.CanaryWorld;
import net.minecraft.entity.EntityList;
import net.minecraft.item.ItemStack;

/**
 * Living Entity wrapper implementation
 *
 * @author Jason (darkdiplomat)
 */
public abstract class CanaryEntityLiving extends CanaryLivingBase implements EntityLiving {

    public CanaryEntityLiving(net.minecraft.entity.EntityLiving entity) {
        super(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void moveEntityToXYZ(double x, double y, double z, float speed) {
        this.lookAt(x, y, z);
        getHandle().k().a(x, y, z, speed);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void playLivingSound() {
        ((net.minecraft.entity.EntityLiving) entity).r();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean spawn(EntityLiving... riders) {
        net.minecraft.world.World world = ((CanaryWorld) getWorld()).getHandle();

        entity.b(getX() + 0.5d, getY(), getZ() + 0.5d, getRotation(), 0f);
        boolean toRet = world.d(entity);

        if (riders != null) {
            CanaryEntityLiving prev = this;

            for (EntityLiving rider : riders) {
                net.minecraft.entity.EntityLiving mob2 = (net.minecraft.entity.EntityLiving) ((CanaryEntityLiving) rider).getHandle();

                mob2.b(getX(), getY(), getZ(), getRotation(), 0f);
                world.d(mob2);
                mob2.a(prev.getHandle());
            }
        }
        return toRet;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LivingBase getAttackTarget() {
        net.minecraft.entity.EntityLivingBase target = getHandle().o();
        return target == null ? null : (CanaryLivingBase) target.getCanaryEntity();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAttackTarget(LivingBase livingbase) {
        if (livingbase == null) {
            getHandle().d((net.minecraft.entity.EntityLivingBase) null);
        }
        else {
            getHandle().d((net.minecraft.entity.EntityLivingBase) ((CanaryEntity) livingbase).getHandle());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item getItemInHand() {
        return getHandle().be().getCanaryItem();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item[] getEquipment() {
        return CanaryItem.stackArrayToItemArray(getHandle().ak());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Item getEquipmentInSlot(int slot) {
        if (slot < 0 || slot > 5) {
            return null;
        }
        ItemStack nms_stack = getHandle().q(slot);
        if (nms_stack != null) {
            return nms_stack.getCanaryItem();
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setEquipment(Item[] items) {
        for (int index = 0; index < 5; index++) {
            if (items[index] == null) {
                continue;
            }
            getHandle().c(index, ((CanaryItem) items[index]).getHandle());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setEquipment(Item item, int slot) {
        if (slot < 0 || slot > 5) {
            return;
        }
        getHandle().c(slot, ((CanaryItem) item).getHandle());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float getDropChance(int slot) {
        return getHandle().getDropChanceForSlot(slot);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDropChance(int slot, float chance) {
        if (slot < 0 || slot > 5) {
            return;
        }
        getHandle().a(slot, chance);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canPickUpLoot() {
        return getHandle().bJ();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCanPickUpLoot(boolean loot) {
        getHandle().h(loot);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPersistenceRequired() {
        return getHandle().bK();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PathFinder getPathFinder() {
        return getHandle().m().getCanaryPathFinder();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AIManager getAITaskManager() {
        return getHandle().getTasks().getAIManager();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasDisplayName() {
        return getHandle().bI();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDisplayName() {
        return getHandle().bG();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDisplayName(String display) {
        if (display == null) {
            display = "";
        }
        getHandle().a(display);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean showingDisplayName() { // TODO: checkthat this is actually right
        return getHandle().bI();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setShowDisplayName(boolean show) {
        getHandle().g(show);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canAttackEntity(EntityType type) {
        if (type == null || type.getEntityID() == 0) {
            return false;
        }
        return getHandle().a(EntityList.a(type.getEntityID()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public net.minecraft.entity.EntityLiving getHandle() {
        return (net.minecraft.entity.EntityLiving) entity;
    }
}
