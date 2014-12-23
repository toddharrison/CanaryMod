package net.minecraft.entity.ai;


import net.canarymod.api.ai.CanaryAIDefendVillage;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.village.Village;


public class EntityAIDefendVillage extends EntityAITarget {

    EntityIronGolem a;
    EntityLivingBase b;
   
    public EntityAIDefendVillage(EntityIronGolem entityirongolem) {
        super(entityirongolem, false, true);
        this.a = entityirongolem;
        this.a(1);
        this.canaryAI = new CanaryAIDefendVillage(this); //CanaryMod: set our variable
    }

    public boolean a() {
        Village village = this.a.n();

        if (village == null) {
            return false;
        } else {
            this.b = village.b((EntityLivingBase) this.a);
            if (!this.a(this.b, false)) {
                if (this.e.bb().nextInt(20) == 0) {
                    this.b = village.c((EntityLivingBase) this.a);
                    return this.a(this.b, false);
                } else {
                    return false;
                }
            } else {
                return true;
            }
        }
    }

    public void c() {
        this.a.d(this.b);
        super.c();
    }
}
