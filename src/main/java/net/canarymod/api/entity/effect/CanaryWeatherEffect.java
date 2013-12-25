package net.canarymod.api.entity.effect;

import net.canarymod.api.entity.CanaryEntity;
import net.minecraft.entity.effect.EntityWeatherEffect;

/**
 * @author Jason (darkdiplomat)
 */
public abstract class CanaryWeatherEffect extends CanaryEntity implements WeatherEffect {

    public CanaryWeatherEffect(EntityWeatherEffect entity) {
        super(entity);
    }
}
