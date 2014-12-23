package net.minecraft.entity.ai;


import net.canarymod.api.ai.CanaryAIOwnerHurtByTarget;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.passive.EntityTameable;


public class EntityAIOwnerHurtByTarget extends EntityAITarget {

    EntityTameable a;
    EntityLivingBase b;
    private int c;
   
    public EntityAIOwnerHurtByTarget(EntityTameable entitytameable) {
        super(entitytameable, false);
        this.a = entitytameable;
        this.a(1);
        this.canaryAI = new CanaryAIOwnerHurtByTarget(this); //CanaryMod: set our variable
    }

    public boolean a() {
        if (!this.a.cj()) {
            return false;
        } else {
            EntityLivingBase entitylivingbase = this.a.cm();

            if (entitylivingbase == null) {
                return false;
            } else {
                this.b = entitylivingbase.bc();
                int i0 = entitylivingbase.bd();

                return i0 != this.c && this.a(this.b, false) && this.a.a(this.b, entitylivingbase);
            }
        }
    }

    public void c() {
        this.e.d(this.b);
        EntityLivingBase entitylivingbase = this.a.cm();

        if (entitylivingbase != null) {
            this.c = entitylivingbase.bd();
        }

        super.c();
    }
}
