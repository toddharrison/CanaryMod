package net.canarymod.api.entity;

import net.canarymod.api.inventory.CanaryItem;
import net.canarymod.api.inventory.Item;
import net.canarymod.api.nbt.BaseTag;
import net.canarymod.api.nbt.CanaryCompoundTag;
import net.canarymod.api.nbt.CompoundTag;
import net.canarymod.api.world.CanaryWorld;
import net.canarymod.api.world.World;
import net.canarymod.api.world.position.Location;
import net.canarymod.api.world.position.Position;
import net.canarymod.api.world.position.Vector3D;
import net.minecraft.nbt.NBTTagCompound;

import java.util.UUID;

/**
 * Entity Wrapper
 *
 * @author Jason (darkdiplomat)
 */
public abstract class CanaryEntity implements Entity {

    protected net.minecraft.entity.Entity entity;

    public CanaryEntity(net.minecraft.entity.Entity entity) {
        this.entity = entity;
    }

    @Override
    public double getX() {
        return entity.s;
    }

    @Override
    public double getY() {
        return entity.t;
    }

    @Override
    public double getZ() {
        return entity.u;
    }

    @Override
    public double getMotionX() {
        return entity.v;
    }

    @Override
    public double getMotionY() {
        return entity.w;
    }

    @Override
    public double getMotionZ() {
        return entity.x;
    }

    @Override
    public float getPitch() {
        return entity.z;
    }

    @Override
    public float getRotation() {
        return entity.y;
    }

    @Override
    public void setX(double x) {
        this.entity.s = x;
    }

    @Override
    public void setX(int x) {
        this.entity.s = x;
    }

    @Override
    public void setY(double y) {
        this.entity.t = y;
    }

    @Override
    public void setY(int y) {
        this.entity.t = y;
    }

    @Override
    public void setZ(double z) {
        this.entity.u = z;
    }

    @Override
    public void setZ(int z) {
        this.entity.u = z;
    }

    @Override
    public void setMotionX(double motionX) {
        entity.v = motionX;
        entity.G = true;
    }

    @Override
    public void setMotionY(double motionY) {
        entity.w = motionY;
        entity.G = true;
    }

    @Override
    public void setMotionZ(double motionZ) {
        entity.x = motionZ;
        entity.G = true;
    }

    @Override
    public Vector3D getMotion() {
        return new Vector3D(getMotionX(), getMotionY(), getMotionZ());
    }

    @Override
    public void moveEntity(double motionX, double motionY, double motionZ) {
        entity.v = motionX;
        entity.w = motionY;
        entity.x = motionZ;
        entity.G = true;
    }

    @Override
    public void setPitch(float pitch) {
        entity.z = pitch;
    }

    @Override
    public void setRotation(float rotation) {
        entity.y = rotation;
    }

    @Override
    public float getEyeHeight() {
        return entity.aR();
    }

    @Override
    public void teleportTo(double x, double y, double z, float pitch, float rotation) {
        teleportTo(x, y, z, pitch, rotation, getWorld());
    }

    @Override
    public void teleportTo(double x, double y, double z) {
        teleportTo(x, y, z, 0.0F, 0.0F);
    }

    @Override
    public void teleportTo(double x, double y, double z, World world) {
        teleportTo(x, y, z, 0.0F, 0.0F, world);
    }

    @Override
    public void teleportTo(double x, double y, double z, float pitch, float rotation, World dim) {
        if (dim != this.getWorld()) {
            this.entity.a(((CanaryWorld) dim).getHandle());
        }
        this.entity.a(x, y, z, rotation, pitch);
    }

    @Override
    public void teleportTo(Location location) {
        teleportTo(location.getX(), location.getY(), location.getZ(), location.getPitch(), location.getRotation(), location.getWorld());
    }

    @Override
    public void teleportTo(Position pos) {
        teleportTo(pos.getX(), pos.getY(), pos.getZ(), 0.0F, 0.0F);
    }

    @Override
    public World getWorld() {
        return entity.getCanaryWorld();
    }

    @Override
    public void setFireTicks(int ticks) {
        this.entity.i = ticks;
    }

    @Override
    public int getFireTicks() {
        return this.entity.i;
    }

    @Override
    public boolean isItem() {
        return false;
    }

    @Override
    public boolean isLiving() {
        return false;
    }

    @Override
    public boolean isAnimal() {
        return false;
    }

    @Override
    public boolean isMob() {
        return false;
    }

    @Override
    public boolean isPlayer() {
        return false;
    }

    @Override
    public boolean isGolem() {
        return false;
    }

    @Override
    public boolean isNPC() {
        return false;
    }

    @Override
    public EntityItem dropLoot(int itemId, int amount) {
        return (EntityItem) entity.a(net.minecraft.item.Item.b(itemId), amount).getEntityItem();
    }

    public EntityItem dropLoot(Item item) {
        return entity.a(((CanaryItem) item).getHandle(), 0.0F).getEntityItem();
    }

    @Override
    public boolean isSprinting() { // 3
        return entity.ax();
    }

    @Override
    public void setSprinting(boolean sprinting) {
        entity.d(sprinting);
    }

    @Override
    public boolean isSneaking() { // 1
        return entity.aw();
    }

    @Override
    public void setSneaking(boolean sneaking) {
        entity.c(sneaking);
    }

    @Override
    public boolean isInvisible() { // 5
        return entity.ay();
    }

    @Override
    public void setInvisible(boolean invisible) {
        entity.e(invisible);
    }

    @Override
    public String getName() {
        return entity.d_();
    }

    @Override
    public Position getPosition() {
        return new Position(getX(), getY(), getZ());
    }

    @Override
    public Location getLocation() {
        return new Location(getWorld(), getX(), getY(), getZ(), getPitch(), getRotation());
    }

    @Override
    public int getID() {
        return entity.F();
    }

    @Override
    public UUID getUUID() {
        return entity.aJ();
    }

    @Override
    public Vector3D getForwardVector() {
        return Vector3D.forward;
    }

    @Override
    public void translate(Vector3D factor) {
        setX(getX() + factor.getX());
        setY(getY() + factor.getY());
        setZ(getZ() + factor.getZ());
    }

    @Override
    public boolean canSpawn() {
        if (this.isLiving()) {
            return ((net.minecraft.entity.EntityLiving) entity).bQ();
        }
        return getHandle().o.a(entity, entity.aQ()).isEmpty(); //Is not occupied space
    }

    @Override
    public boolean isRiding() {
        return entity.av();
    }

    @Override
    public boolean isRidden() {
        return entity.l != null;
    }

    @Override
    public Entity getRiding() {
        if (entity.m != null) {
            return entity.m.getCanaryEntity();
        }
        return null;
    }

    @Override
    public Entity getRider() {
        if (entity.l != null) {
            return entity.l.getCanaryEntity();
        }
        return null;
    }

    /**
     * Mounts a specified {@code Entity}
     *
     * @param entity
     *         the {@code Entity} to mount
     */
    public void mount(Entity entity) {
        if (entity != null) {
            this.entity.a(((CanaryEntity) entity).getHandle());
        }
        else {
            this.entity.a((net.minecraft.entity.Entity) null);
        }
    }

    /**
     * Dismount ridden {@code Entity}
     */
    public void dismount() {
        mount(null);
    }

    /** Destroys this entity */
    @Override
    public void destroy() {
        entity.J();
    }

    @Override
    public boolean isDead() {
        return entity.I;
    }

    @Override
    public boolean spawn() {
        entity.b(getX() + 0.5d, getY(), getZ() + 0.5d, getRotation(), 0f);
        return entity.o.d(entity);
    }

    @Override
    public boolean spawn(Entity rider) {
        boolean ret = spawn();

        if (rider != null) {
            net.minecraft.entity.Entity mob2 = ((CanaryEntity) rider).getHandle();

            mob2.b(getX(), getY(), getZ(), getRotation(), 0f);
            ret &= entity.o.d(mob2);
            mob2.a(entity);
        }
        return ret;
    }

    @Override
    public void setRider(Entity rider) {
        ((CanaryEntity) rider).getHandle().a(this.entity);
    }

    @Override
    public CompoundTag getNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        this.getHandle().d(tag);
        return (new CanaryCompoundTag(tag));
    }

    @Override
    public void setNBT(BaseTag tag) {
        this.getHandle().f((NBTTagCompound) ((CanaryCompoundTag) tag).getHandle());
    }

    @Override
    public CompoundTag getMetaData() {
        return entity.getMetaData();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAmbient() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isOnGround() {
        return getHandle().C;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isInWeb() {
        return getHandle().H;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isInWater() {
        return getHandle().Y;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isInLava() {
        return getHandle().ab();
    }

    @Override
    public String toString() {
        return String.format("%s[Native: %s]", this.getClass().getSimpleName(), getHandle());
    }

    /**
     * Gets the Minecraft entity being wrapped
     *
     * @return entity
     */
    public abstract net.minecraft.entity.Entity getHandle();

}
