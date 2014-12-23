package net.minecraft.entity.ai;


import net.canarymod.api.ai.CanaryAIRestrictSun;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathNavigateGround;


public class EntityAIRestrictSun extends EntityAIBase {

    private EntityCreature a;
   
    public EntityAIRestrictSun(EntityCreature entitycreature) {
        this.a = entitycreature;
        this.canaryAI = new CanaryAIRestrictSun(this); //CanaryMod: set our variable
    }

    public boolean a() {
        return this.a.o.w();
    }

    public void c() {
        ((PathNavigateGround) this.a.s()).e(true);
    }

    public void d() {
        ((PathNavigateGround) this.a.s()).e(false);
    }
}
