package net.canarymod.api;

import net.canarymod.api.entity.Entity;
import net.canarymod.api.entity.living.humanoid.CanaryPlayer;
import net.canarymod.api.entity.living.humanoid.Player;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.world.Explosion;

public class CanaryDamageSource implements DamageSource {
    net.minecraft.util.DamageSource handle;

    public CanaryDamageSource(net.minecraft.util.DamageSource handle) {
        this.handle = handle;
    }

    public net.minecraft.util.DamageSource getHandle() {
        return handle;
    }

    @Override
    public Entity getDamageDealer() {
        net.minecraft.entity.Entity entity = handle.i();
        return (entity == null) ? null : entity.getCanaryEntity();
    }

    @Override
    public String getNativeName() {
        return handle.p;
    }

    @Override
    public String getDeathMessage(Player player) {
        return handle.b(((CanaryPlayer)player).getHandle()).toString();
    }

    @Override
    public float getHungerDamage() {
        return handle.f();
    }

    @Override
    public boolean isFireDamage() {
        return handle.o();
    }

    @Override
    public boolean isProjectile() {
        return handle.a();
    }

    @Override
    public boolean isUnblockable() {
        return handle.e();
    }

    @Override
    public void setCustomDeathMessage(String message) {// Not real sure how this should be implemented at this time
        // StringTranslate override
    }

    @Override
    public void setHungerDamage(float value) {
        handle.setHungerDamage(value);
    }

    @Override
    public void setUnblockable(boolean set) {
        handle.setUnblockable(set);
    }

    @Override
    public boolean validInCreativeMode() {
        return handle.g();
    }

    @Override
    public DamageType getDamagetype() {
        return DamageType.fromDamageSource(this);
    }

    @Override
    public boolean isCritical() {
        return handle instanceof EntityDamageSource && ((EntityDamageSource)handle).isCritical();
    }

    public final String toString() {
        StringBuilder builder = new StringBuilder("DamageSource[");
        builder.append("Name: ").append(getNativeName()).append(", ");
        builder.append("Dealer: ").append(getDamageDealer()).append(", ");
        builder.append("Hunger: ").append(getHungerDamage()).append(", ");
        builder.append("Fire: ").append(isFireDamage()).append(", ");
        builder.append("Projectile: ").append(isProjectile()).append(", ");
        builder.append("Unblockable: ").append(isUnblockable()).append(", ");
        builder.append("CreativeValid: ").append(validInCreativeMode()).append(", ");
        builder.append("Critical: ").append(isCritical()).append("]");

        return builder.toString();
    }

    /**
     * Gets a damage source from a damage type<br>
     * Note: This will not work with Entity Damage Sources due to
     * the extra Entity Arguments.
     *
     * @param type
     */
    public static DamageSource getDamageSourceFromType(DamageType type) {
        switch (type) {
            case FIRE:
                return new CanaryDamageSource(net.minecraft.util.DamageSource.a);
            case LIGHTNINGBOLT:
                return new CanaryDamageSource(net.minecraft.util.DamageSource.b);
            case FIRE_TICK:
                return new CanaryDamageSource(net.minecraft.util.DamageSource.c);
            case LAVA:
                return new CanaryDamageSource(net.minecraft.util.DamageSource.d);
            case SUFFOCATION:
                return new CanaryDamageSource(net.minecraft.util.DamageSource.e);
            case WATER:
                return new CanaryDamageSource(net.minecraft.util.DamageSource.f);
            case STARVATION:
                return new CanaryDamageSource(net.minecraft.util.DamageSource.g);
            case CACTUS:
                return new CanaryDamageSource(net.minecraft.util.DamageSource.h);
            case FALL:
                return new CanaryDamageSource(net.minecraft.util.DamageSource.i);
            case VOID:
                return new CanaryDamageSource(net.minecraft.util.DamageSource.j);
            case GENERIC:
                return new CanaryDamageSource(net.minecraft.util.DamageSource.k);
            case POTION:
                return new CanaryDamageSource(net.minecraft.util.DamageSource.l);
            case WITHER:
                return new CanaryDamageSource(net.minecraft.util.DamageSource.m);
            case ANVIL:
                return new CanaryDamageSource(net.minecraft.util.DamageSource.n);
            case FALLING_BLOCK:
                return new CanaryDamageSource(net.minecraft.util.DamageSource.o);
            case EXPLOSION:
                return new CanaryDamageSource(net.minecraft.util.DamageSource.a((Explosion)null));
            default:
                return null;
        }
    }
}
