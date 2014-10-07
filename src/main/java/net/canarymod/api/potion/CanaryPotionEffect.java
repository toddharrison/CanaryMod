package net.canarymod.api.potion;

import net.canarymod.api.entity.living.CanaryLivingBase;
import net.canarymod.api.entity.living.LivingBase;

/**
 * PotionEffect wrapper implementation
 *
 * @author Jason (darkdiplomat)
 */
public class CanaryPotionEffect implements PotionEffect {

    net.minecraft.potion.PotionEffect effect;

    /**
     * Constructs a new wrapper for PotionEffect
     *
     * @param effect
     *         the PotionEffect to wrap
     */
    public CanaryPotionEffect(net.minecraft.potion.PotionEffect effect) {
        this.effect = effect;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPotionID() {
        return effect.a();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getDuration() {
        return effect.b();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getAmplifier() {
        return effect.c();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAmbient() {
        return effect.e();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return effect.g();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void performEffect(LivingBase entity) {
        net.minecraft.entity.EntityLivingBase oLiving = (net.minecraft.entity.EntityLivingBase)((CanaryLivingBase)entity).getHandle();

        effect.b(oLiving);
    }

    /**
     * Gets the PotionEffect being wrapped
     *
     * @return the PotionEffect
     */
    public net.minecraft.potion.PotionEffect getHandle() {
        return effect;
    }
}
