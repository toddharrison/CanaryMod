package net.canarymod.api.entity;

import net.canarymod.api.entity.living.LivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.nbt.NBTTagCompound;

/**
 * EntityTNTPrimed wrapper implementation
 *
 * @author Jason (darkdiplomat)
 */
public class CanaryTNTPrimed extends CanaryEntity implements TNTPrimed {
    private boolean damageWorld = true, damageEntity = true;
    private float power = 4.0F;

    /**
     * Constructs a new wrapper for EntityItemFrame
     *
     * @param tntprimed
     *         the EntityItemFrame to be wrapped
     */
    public CanaryTNTPrimed(EntityTNTPrimed tntprimed) {
        super(tntprimed);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityType getEntityType() {
        return EntityType.TNTPRIMED;
    }

    @Override
    public String getFqName() {
        return "TNTPrimed";
    }

    @Override
    public LivingBase getActivatedBy() {
        return (LivingBase) getHandle().j().getCanaryEntity();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getFuse() {
        return getHandle().a;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setFuse(int fuse) {
        getHandle().a = fuse;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void increaseFuse(int increase) {
        getHandle().a += increase;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void decreaseFuse(int decrease) {
        getHandle().a -= decrease;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void detonate() {
        getHandle().a = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canDamageEntities() {
        return damageEntity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canDamageWorld() {
        return damageWorld;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCanDamageEntities(boolean canDamage) {
        this.damageEntity = canDamage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCanDamageWorld(boolean canDamage) {
        this.damageWorld = canDamage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float getPower() {
        return power;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPower(float power) {
        this.power = power;
    }

    @Override
    public NBTTagCompound writeCanaryNBT(NBTTagCompound nbttagcompound) {
        super.writeCanaryNBT(nbttagcompound);
        nbttagcompound.a("DamageEntities", damageEntity);
        nbttagcompound.a("DamageWorld", damageWorld);
        nbttagcompound.a("Power", power);
        return nbttagcompound;
    }

    @Override
    public void readCanaryNBT(NBTTagCompound nbttagcompound) {
        super.readCanaryNBT(nbttagcompound);
        this.damageEntity = !nbttagcompound.c("DamageEntities") || nbttagcompound.n("DamageEntities");
        this.damageWorld = !nbttagcompound.c("DamageWorld") || nbttagcompound.n("DamageWorld");
        this.power = nbttagcompound.c("Power") ? nbttagcompound.h("Power") : 4.0F;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityTNTPrimed getHandle() {
        return (EntityTNTPrimed) entity;
    }
}
