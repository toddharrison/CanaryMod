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
        return entity.t;
    }

    @Override
    public double getY() {
        return entity.u;
    }

    @Override
    public double getZ() {
        return entity.v;
    }

    @Override
    public double getMotionX() {
        return entity.w;
    }

    @Override
    public double getMotionY() {
        return entity.x;
    }

    @Override
    public double getMotionZ() {
        return entity.y;
    }

    @Override
    public float getPitch() {
        return entity.A;
    }

    @Override
    public float getRotation() {
        return entity.z;
    }

    @Override
    public void setX(double x) {
        this.entity.t = x;
    }

    @Override
    public void setX(int x) {
        this.entity.t = x;

    }

    @Override
    public void setY(double y) {
        this.entity.u = y;

    }

    @Override
    public void setY(int y) {
        this.entity.u = y;

    }

    @Override
    public void setZ(double z) {
        this.entity.v = z;
    }

    @Override
    public void setZ(int z) {
        this.entity.v = z;
    }

    @Override
    public void setMotionX(double motionX) {
        entity.w = motionX;
        entity.K = true;
    }

    @Override
    public void setMotionY(double motionY) {
        entity.x = motionY;
        entity.K = true;
    }

    @Override
    public void setMotionZ(double motionZ) {
        entity.y = motionZ;
        entity.K = true;
    }

    @Override
    public Vector3D getMotion() {
        return new Vector3D(getMotionX(), getMotionY(), getMotionZ());
    }

    @Override
    public void moveEntity(double motionX, double motionY, double motionZ) {
        entity.w = motionX;
        entity.x = motionY;
        entity.y = motionZ;
        entity.K = true;
    }

    @Override
    public void setPitch(float pitch) {
        entity.A = pitch;

    }

    @Override
    public void setRotation(float rotation) {
        entity.z = rotation;

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
        this.entity.e = ticks;

    }

    @Override
    public int getFireTicks() {
        return this.entity.e;
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
        return (EntityItem) entity.a(net.minecraft.item.Item.d(itemId), amount).getEntityItem();
    }

    public EntityItem dropLoot(Item item) {
        return entity.a(((CanaryItem) item).getHandle(), 0.0F).getEntityItem();
    }

    @Override
    public boolean isSprinting() { // 3
        return entity.ao();
    }

    @Override
    public void setSprinting(boolean sprinting) {
        entity.c(sprinting);
    }

    @Override
    public boolean isSneaking() { // 1
        return entity.an();
    }

    @Override
    public void setSneaking(boolean sneaking) {
        entity.b(sneaking);
    }

    @Override
    public boolean isInvisible() { // 5
        return entity.ap();
    }

    @Override
    public void setInvisible(boolean invisible) {
        entity.d(invisible);
    }

    @Override
    public String getName() {
        return entity.b_();
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
        return entity.y();
    }

    @Override
    public UUID getUUID() {
        return entity.aB();
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
            return ((net.minecraft.entity.EntityLiving) entity).bw();
        }
        return getHandle().p.a(entity, entity.D).isEmpty(); //Is not occupied space
    }

    @Override
    public boolean isRiding() {
        return entity.am();
    }

    @Override
    public boolean isRidden() {
        return entity.m != null;
    }

    @Override
    public Entity getRiding() {
        if (entity.n != null) {
            return entity.n.getCanaryEntity();
        }
        return null;
    }

    @Override
    public Entity getRider() {
        if (entity.n != null) {
            return entity.n.getCanaryEntity();
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
        entity.B();
    }

    @Override
    public boolean isDead() {
        return entity.L;
    }

    @Override
    public boolean spawn() {
        entity.b(getX() + 0.5d, getY(), getZ() + 0.5d, getRotation(), 0f);
        return entity.p.d(entity);
    }

    @Override
    public boolean spawn(Entity rider) {
        boolean ret = spawn();

        if (rider != null) {
            net.minecraft.entity.Entity mob2 = ((CanaryEntity) rider).getHandle();

            mob2.b(getX(), getY(), getZ(), getRotation(), 0f);
            ret &= entity.p.d(mob2);
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
     * Gets the Minecraft entity being wrapped
     *
     * @return entity
     */
    public abstract net.minecraft.entity.Entity getHandle();
}
