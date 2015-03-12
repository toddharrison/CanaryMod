package net.canarymod.api.entity.living;

import net.canarymod.api.CanaryDamageSource;
import net.canarymod.api.DamageSource;
import net.canarymod.api.DamageType;
import net.canarymod.api.attributes.AttributeMap;
import net.canarymod.api.entity.CanaryEntity;
import net.canarymod.api.entity.Entity;
import net.canarymod.api.potion.CanaryPotion;
import net.canarymod.api.potion.CanaryPotionEffect;
import net.canarymod.api.potion.Potion;
import net.canarymod.api.potion.PotionEffect;
import net.canarymod.api.potion.PotionEffectType;
import net.canarymod.api.world.CanaryWorld;
import net.canarymod.api.world.position.Location;
import net.canarymod.api.world.position.Position;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class CanaryLivingBase extends CanaryEntity implements LivingBase {
    // CanaryMod: per entity loot setting, default to true
    private boolean lootDrop = true;
    private boolean xpDrop = true;

    public CanaryLivingBase(EntityLivingBase entity) {
        super(entity);
    }

    @Override
    public boolean isLiving() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float getHealth() {
        return getHandle().bm();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setHealth(float newHealth) {
        getHandle().h(newHealth);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void increaseHealth(float increase) {
        getHandle().g(increase);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getMaxHealth() {
        return getHandle().a(SharedMonsterAttributes.a).e();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMaxHealth(double maxHealth) {
        getHandle().a(SharedMonsterAttributes.a).a(maxHealth);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canSee(LivingBase livingbase) {
        return getHandle().r(((CanaryEntity) livingbase).getHandle());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getDeathTicks() {
        return getHandle().av;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDeathTicks(int ticks) {
        getHandle().av = ticks;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getInvulnerabilityTicks() {
        return getHandle().aB;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setInvulnerabilityTicks(int ticks) {
        getHandle().aB = ticks;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getAge() {
        return getHandle().bg();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAge(int age) {
        getHandle().setAge(age);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void kill() {
        this.dealDamage(DamageType.GENERIC, Float.MAX_VALUE); //overkill?
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dealDamage(DamageType damagetype, float amount) {
        DamageSource theSource = CanaryDamageSource.getDamageSourceFromType(damagetype);
        if (theSource != null) {
            getHandle().a(((CanaryDamageSource) theSource).getHandle(), amount);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void knockBack(double x, double z) {
        getHandle().a(entity, 0, x, z);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addPotionEffect(PotionEffect effect) {
        if (effect == null) {
            return;
        }
        getHandle().c(((CanaryPotionEffect) effect).getHandle());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addPotionEffect(PotionEffectType type, int duration, int amplifier) {
        getHandle().c(new net.minecraft.potion.PotionEffect(type.getID(), duration, amplifier));
    }

    /**
     * {@inheritDoc}
     */
    public void removePotionEffect(PotionEffectType type) {
        getHandle().m(type.getID());
    }

    /**
     * {@inheritDoc}
     */
    public void removeAllPotionEffects() {
        getHandle().removeAllPotionEffects();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPotionActive(Potion potion) {
        if (potion == null) {
            return false;
        }
        return getHandle().a(((CanaryPotion) potion).getHandle());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PotionEffect getActivePotionEffect(Potion potion) {
        if (potion == null) {
            return null;
        }
        net.minecraft.potion.PotionEffect nms_potioneffect = ((net.minecraft.entity.EntityLivingBase) entity).b(((CanaryPotion) potion).getHandle());
        return nms_potioneffect != null ? new CanaryPotionEffect(nms_potioneffect) : null;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<PotionEffect> getAllActivePotionEffects() {
        Collection<net.minecraft.potion.PotionEffect> effect_collection = ((net.minecraft.entity.EntityLivingBase) entity).bk();
        List<PotionEffect> list = new ArrayList<PotionEffect>();

        for (net.minecraft.potion.PotionEffect nms_effect : effect_collection) {
            list.add(new CanaryPotionEffect(nms_effect));
        }
        return list;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LivingBase getRevengeTarget() {
        net.minecraft.entity.EntityLivingBase target = getHandle().bc();
        if (target != null) {
            return (LivingBase) target.getCanaryEntity();
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setRevengeTarget(LivingBase livingbase) {
        if (livingbase == null) {
            getHandle().b((net.minecraft.entity.EntityLivingBase) null);
        }
        else {
            getHandle().b(((CanaryLivingBase) livingbase).getHandle());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LivingBase getLastAssailant() {
        net.minecraft.entity.EntityLivingBase target = ((net.minecraft.entity.EntityLivingBase) entity).be();
        if (target != null) {
            return (EntityLiving) target.getCanaryEntity();
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLastAssailant(LivingBase livingbase) {
        if (livingbase == null) {
            getHandle().m((net.minecraft.entity.Entity) null);
        }
        else {
            getHandle().m(((CanaryEntity) livingbase).getHandle());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void lookAt(double x, double y, double z) {
        double xDiff = x - getX();
        double yDiff = y - getY();
        double zDiff = z - getZ();

        double distanceXZ = Math.sqrt(xDiff * xDiff + zDiff * zDiff);
        double distanceY = Math.sqrt(distanceXZ * distanceXZ + yDiff * yDiff);

        float rot = (float) Math.toDegrees(Math.acos(xDiff / distanceXZ));
        float pitch = (float) (Math.toDegrees(Math.acos(yDiff / distanceY)) - 90.0D);
        if (zDiff < 0.0D) {
            rot += Math.abs(180.0D - rot) * 2.0D;
        }
        while (rot < -180.0F) {
            rot += 360.0F;
        }

        while (rot >= 180.0F) {
            rot -= 360.0F;
        }
        rot -= 90.0F;

        setRotation(rot);
        setPitch(pitch);
        setHeadRotation(rot);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void lookAt(Location location) {
        lookAt(location.getX(), location.getY(), location.getZ());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void lookAt(Entity entity) {
        lookAt(entity.getX(), entity.getY(), entity.getZ());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getArrowCountInEntity() {
        return getHandle().bu();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setArrowCountInEntity(int arrows) {
        getHandle().o(arrows);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void swingArm() {
        getHandle().bv();
    }

    @Override
    public void attackEntity(LivingBase entity, float damage) {
        if (entity == null) {
            return;
        }
        swingArm();
        ((net.canarymod.api.entity.living.CanaryLivingBase) entity).getHandle().a(new net.minecraft.util.EntityDamageSource(getName(), getHandle()), damage);
    }

    @Override
    public float getHeadRotation() {
        return getHandle().aJ;
    }

    @Override
    public void setHeadRotation(float rot) {
        getHandle().aJ = rot;
    }

    @Override
    public AttributeMap getAttributeMap() {
        return getHandle().bx().getWrapper();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Entity getTargetLookingAt() {
        return this.getTargetLookingAt(64);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Entity getTargetLookingAt(int searchRadius) {
        Entity toRet = null;
        
        // Get the vector that this entity is looking; Get our start position
        Vec3 vec = ((CanaryLivingBase)this).getHandle().ap();
        Position startPos = this.getPosition();
        Position nextPos = new Position((startPos.getX() + vec.a), (startPos.getY() + vec.b) + this.getEyeHeight(),(startPos.getZ() + vec.c));

        while (distanceTo(startPos, nextPos) < searchRadius * searchRadius) {
            // Get the nearest entity and check that its not null (i.e. isn't an entity in the BB)
            Entity near = this.getNearestEntity(this, nextPos.getX(), nextPos.getY(), nextPos.getZ());
            if (near != null) {
                AxisAlignedBB bb = ((CanaryEntity)near).getHandle().aQ();
                if ((nextPos.getX() > bb.a && nextPos.getX() < bb.d) && (nextPos.getY() > bb.b && nextPos.getY() < bb.e) && (nextPos.getZ() > bb.c && nextPos.getZ() < bb.f)) {
                    toRet = near;
                    break;
                }
                // Calculate the next position to check, small step because we have entities here
                nextPos = new Position((nextPos.getX() + (vec.a*.01)), (nextPos.getY() + (vec.b*.01)),(nextPos.getZ() + (vec.c*.01)));
            } else {
                // Calculate the next position to check, large step because the immediate area is empty
                nextPos = new Position((nextPos.getX() + (vec.a)), (nextPos.getY() + (vec.b)),(nextPos.getZ() + (vec.c)));
            }
        }
        return toRet;
    }
        
    /**
     * Returns the distance between the two positions
     * @param pos1
     * @param pos2
     * @return 
     */
    private double distanceTo(Position pos1, Position pos2) {
        double xDiff = pos1.getX() - pos2.getX();
        double yDiff = pos1.getY() - pos2.getY();
        double zDiff = pos1.getZ() - pos2.getZ();

        return xDiff * xDiff + yDiff * yDiff + zDiff * zDiff;
    }

    /**
    * Gets the entity nearest to the searching entity within a 5 meter radius of the given position
    */
    private Entity getNearestEntity(CanaryLivingBase entity, double x, double y, double z) {
        // Get the entities world
        net.canarymod.api.world.World w = entity.getWorld();
        // create a bounding box around the point with the given coordinates
        AxisAlignedBB aabb = AxisAlignedBB.a(x - .5, y - .5, z - .5, x + .5, y + .5, z + .5);
        // get the entity in the Bounding box closest to the entity searching
        net.minecraft.entity.Entity target = ((CanaryWorld)w).getHandle().a(net.minecraft.entity.EntityLiving.class, aabb, ((CanaryLivingBase)entity).getHandle());

        return (target == null) ? null : target.getCanaryEntity();
    }

    @Override
    public NBTTagCompound writeCanaryNBT(NBTTagCompound nbttagcompound) {
        super.writeCanaryNBT(nbttagcompound); // always, ALWAYS call super
        nbttagcompound.a("LootDrop", lootDrop);
        nbttagcompound.a("XPDrop", xpDrop);
        return nbttagcompound;
    }

    @Override
    public void readCanaryNBT(NBTTagCompound nbttagcompound) {
        super.readCanaryNBT(nbttagcompound);
        this.lootDrop = !nbttagcompound.c("LootDrop") || nbttagcompound.n("LootDrop");
        this.xpDrop = !nbttagcompound.c("XPDrop") || nbttagcompound.n("XPDrop");
    }

    public boolean doesDropLoot() {
        return lootDrop;
    }

    public boolean doesDropXP() {
        return xpDrop;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityLivingBase getHandle() {
        return (EntityLivingBase) entity;
    }
}
