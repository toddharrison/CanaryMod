package net.minecraft.entity.ai;


import net.canarymod.api.ai.CanaryAIWatchClosest2;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIWatchClosest;


public class EntityAIWatchClosest2 extends EntityAIWatchClosest {

    public EntityAIWatchClosest2(EntityLiving entityliving, Class oclass0, float f0, float f1) {
        super(entityliving, oclass0, f0, f1);
        this.a(3);
        this.canaryAI = new CanaryAIWatchClosest2(this); //CanaryMod: set our variable
    }
}
