package net.minecraft.entity.ai;


import net.canarymod.api.ai.CanaryAISwimming;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathNavigateGround;


public class EntityAISwimming extends EntityAIBase {

    private EntityLiving a;
   
    public EntityAISwimming(EntityLiving entityliving) {
        this.a = entityliving;
        this.a(4);
        ((PathNavigateGround) entityliving.s()).d(true);
        this.canaryAI = new CanaryAISwimming(this); //CanaryMod: set our variable
    }

    public boolean a() {
        return this.a.V() || this.a.ab();
    }

    public void e() {
        if (this.a.bb().nextFloat() < 0.8F) {
            this.a.r().a();
        }

    }
}
