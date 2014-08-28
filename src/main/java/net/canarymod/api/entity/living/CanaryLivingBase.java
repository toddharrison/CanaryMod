package net.canarymod.api.entity.living;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.canarymod.Canary;
import net.canarymod.api.CanaryDamageSource;
import net.canarymod.api.DamageSource;
import net.canarymod.api.DamageType;
import net.canarymod.api.attributes.AttributeMap;
import net.canarymod.api.entity.CanaryEntity;
import net.canarymod.api.entity.Entity;
import net.canarymod.api.entity.living.humanoid.CanaryPlayer;
import net.canarymod.api.entity.living.humanoid.Player;
import net.canarymod.api.potion.*;
import net.canarymod.api.world.CanaryWorld;
import net.canarymod.api.world.Chunk;
import net.canarymod.api.world.position.Location;
import net.canarymod.api.world.position.Position;
import net.canarymod.api.world.position.Vector3D;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public abstract class CanaryLivingBase extends CanaryEntity implements LivingBase {

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
        return getHandle().aS();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setHealth(float newHealth) {
        getHandle().g(newHealth);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void increaseHealth(float increase) {
        getHandle().f(increase);
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
        return getHandle().o(((CanaryEntity) livingbase).getHandle());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getDeathTicks() {
        return getHandle().aB;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDeathTicks(int ticks) {
        getHandle().aB = ticks;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getInvulnerabilityTicks() {
        return getHandle().aI;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setInvulnerabilityTicks(int ticks) {
        getHandle().aI = ticks;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getAge() {
        return getHandle().aN();
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
        Collection<net.minecraft.potion.PotionEffect> effect_collection = ((net.minecraft.entity.EntityLivingBase) entity).aQ();
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
        net.minecraft.entity.EntityLivingBase target = getHandle().aJ();
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
        net.minecraft.entity.EntityLivingBase target = ((net.minecraft.entity.EntityLivingBase) entity).aL();
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
            getHandle().k((net.minecraft.entity.Entity) null);
        }
        else {
            getHandle().k(((CanaryEntity) livingbase).getHandle());
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
        return getHandle().aZ();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setArrowCountInEntity(int arrows) {
        getHandle().p(arrows);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void swingArm() {
        getHandle().ba();
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
        return getHandle().aP;
    }

    @Override
    public void setHeadRotation(float rot) {
        getHandle().aP = rot;
    }

    @Override
    public AttributeMap getAttributeMap() {
        return getHandle().bc().getWrapper();
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
        Vec3 vec = ((CanaryLivingBase)this).getHandle().ag();
        Position startPos = this.getPosition();
        Position nextPos = new Position((startPos.getX() + vec.c), (startPos.getY() + vec.d) + 1.63,(startPos.getZ() + vec.e));

        while (distanceTo(startPos, nextPos) < searchRadius * searchRadius) {
            // Get the nearest entity and check that its not null
            Entity near = this.getNearestEnttiy(this, nextPos.getBlockX(), nextPos.getBlockY(), nextPos.getBlockZ());
            if (near != null) {
                AxisAlignedBB bb = ((CanaryEntity)near).getHandle().D;
                if ((nextPos.getX() > bb.a && nextPos.getX() < bb.d) && (nextPos.getY() > bb.b && nextPos.getY() < bb.e) && (nextPos.getZ() > bb.c && nextPos.getZ() < bb.f)) {
                    toRet = near;
                    break;
                }
            }
            // Calculate the next position to check
            nextPos = new Position((nextPos.getX() + (vec.c*.01)), (nextPos.getY() + (vec.d*.01)),(nextPos.getZ() + (vec.e*.01)));
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
    private Entity getNearestEnttiy(CanaryLivingBase entity, int x, int y, int z) {
        // Get the entities world
        net.canarymod.api.world.World w = entity.getWorld();
        // create a bounding box around the point with the given coordinates
        AxisAlignedBB aabb = AxisAlignedBB.a(x - 5, y - 5, z - 5, x + 5, y + 5, z + 5);
        // get the entity in the Bounding box closest to the entity searching
        net.minecraft.entity.Entity target = ((CanaryWorld)w).getHandle().a(net.minecraft.entity.EntityLiving.class, aabb, ((CanaryLivingBase)entity).getHandle());

        return (target == null) ? null : target.getCanaryEntity();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityLivingBase getHandle() {
        return (EntityLivingBase) entity;
    }
}
