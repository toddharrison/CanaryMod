package net.canarymod.api.factory;


import net.canarymod.api.potion.CanaryPotionEffect;
import net.canarymod.api.potion.PotionEffect;


public class CanaryPotionFactory implements PotionFactory {

    @Override
    public PotionEffect newPotionEffect(int id, int duration, int amplifier) {
        net.minecraft.server.PotionEffect oEffect = new net.minecraft.server.PotionEffect(id, duration, amplifier);

        return new CanaryPotionEffect(oEffect);
    }

}
