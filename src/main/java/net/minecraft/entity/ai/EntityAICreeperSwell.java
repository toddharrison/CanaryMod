package net.minecraft.entity.ai;


import net.canarymod.api.ai.CanaryAICreeperSwell;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.monster.EntityCreeper;


public class EntityAICreeperSwell extends EntityAIBase {

    EntityCreeper a;
    EntityLivingBase b;
   
    public EntityAICreeperSwell(EntityCreeper entitycreeper) {
        this.a = entitycreeper;
        this.a(1);
        this.canaryAI = new CanaryAICreeperSwell(this); //CanaryMod: set our variable
    }

    public boolean a() {
        EntityLivingBase entitylivingbase = this.a.u();

        return this.a.ck() > 0 || entitylivingbase != null && this.a.h(entitylivingbase) < 9.0D;
    }

    public void c() {
        this.a.s().n();
        this.b = this.a.u();
    }

    public void d() {
        this.b = null;
    }

    public void e() {
        if (this.b == null) {
            this.a.a(-1);
        } else if (this.a.h(this.b) > 49.0D) {
            this.a.a(-1);
        } else if (!this.a.t().a(this.b)) {
            this.a.a(-1);
        } else {
            this.a.a(1);
        }
    }
}
