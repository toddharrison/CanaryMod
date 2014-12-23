package net.minecraft.entity.ai;


import net.canarymod.api.ai.CanaryAISit;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.EntityTameable;


public class EntityAISit extends EntityAIBase {

    private EntityTameable a;
    private boolean b;
   
    public EntityAISit(EntityTameable entitytameable) {
        this.a = entitytameable;
        this.a(5);
        this.canaryAI = new CanaryAISit(this); //CanaryMod: set our variable
    }

    public boolean a() {
        if (!this.a.cj()) {
            return false;
        } else if (this.a.V()) {
            return false;
        } else if (!this.a.C) {
            return false;
        } else {
            EntityLivingBase entitylivingbase = this.a.cm();

            return entitylivingbase == null ? true : (this.a.h(entitylivingbase) < 144.0D && entitylivingbase.bc() != null ? false : this.b);
        }
    }

    public void c() {
        this.a.s().n();
        this.a.n(true);
    }

    public void d() {
        this.a.n(false);
    }

    public void a(boolean flag0) {
        this.b = flag0;
    }
}
