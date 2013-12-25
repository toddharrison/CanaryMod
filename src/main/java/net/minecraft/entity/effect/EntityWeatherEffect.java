package net.minecraft.entity.effect;

import net.canarymod.api.entity.EntityType;
import net.canarymod.api.entity.effect.CanaryWeatherEffect;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public abstract class EntityWeatherEffect extends Entity {

    public EntityWeatherEffect(World world) {
        super(world);

        // CanaryMod: Wrap effect if a sub class isn't a wrapped class
        this.entity = new CanaryWeatherEffect(this) {
            @Override
            public EntityType getEntityType() {
                return EntityType.GENERIC_EFFECT;
            }

            @Override
            public String getFqName() {
                return "Weather Effect";
            }

            public EntityWeatherEffect getHandle() {
                return (EntityWeatherEffect) entity;
            }
        };
    }
}
