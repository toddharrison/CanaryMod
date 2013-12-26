package net.canarymod.api;

import net.canarymod.api.entity.Entity;
import net.canarymod.api.entity.living.humanoid.CanaryPlayer;
import net.canarymod.api.entity.living.humanoid.Player;
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
        return handle.o;
    }

    @Override
    public String getDeathMessage(Player player) {
        return handle.b(((CanaryPlayer) player).getHandle()).toString();
    }

    @Override
    public float getHungerDamage() {
        return handle.f();
    }

    @Override
    public boolean isFireDamage() {
        return handle.m();
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

    /**
     * Gets a damage source from a damage type<br>
     * Note: This will not work with Entity Damage Sources due to
     * the extra Entity Arguments.
     *
     * @param type
     */
    public static DamageSource getDamageSourceFromType(DamageType type) {
        switch (type) {
            case ANVIL:
                return new CanaryDamageSource(net.minecraft.util.DamageSource.m);

            case CACTUS:
                return new CanaryDamageSource(net.minecraft.util.DamageSource.g);

            case EXPLOSION:
                return new CanaryDamageSource(net.minecraft.util.DamageSource.a((Explosion) null));

            case FALL:
                return new CanaryDamageSource(net.minecraft.util.DamageSource.h);

            case FALLING_BLOCK:
                return new CanaryDamageSource(net.minecraft.util.DamageSource.n);

            case FIRE:
                return new CanaryDamageSource(net.minecraft.util.DamageSource.a);

            case FIRE_TICK:
                return new CanaryDamageSource(net.minecraft.util.DamageSource.b);

            case GENERIC:
                return new CanaryDamageSource(net.minecraft.util.DamageSource.j);

            case LAVA:
                return new CanaryDamageSource(net.minecraft.util.DamageSource.c);

            case POTION:
                return new CanaryDamageSource(net.minecraft.util.DamageSource.k);

            case STARVATION:
                return new CanaryDamageSource(net.minecraft.util.DamageSource.f);

            case SUFFOCATION:
                return new CanaryDamageSource(net.minecraft.util.DamageSource.d);

            case VOID:
                return new CanaryDamageSource(net.minecraft.util.DamageSource.i);

            case WATER:
                return new CanaryDamageSource(net.minecraft.util.DamageSource.e);

            case WITHER:
                return new CanaryDamageSource(net.minecraft.util.DamageSource.l);

            default:
                return null;
        }
    }

}
