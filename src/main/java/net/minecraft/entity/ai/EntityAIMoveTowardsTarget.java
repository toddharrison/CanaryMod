package net.minecraft.entity.ai;


import net.canarymod.api.ai.CanaryAIMoveTowardsTarget;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.Vec3;


public class EntityAIMoveTowardsTarget extends EntityAIBase {

    private EntityCreature a;
    private EntityLivingBase b;
    private double c;
    private double d;
    private double e;
    private double f;
    private float g;
   
    public EntityAIMoveTowardsTarget(EntityCreature entitycreature, double d0, float f0) {
        this.a = entitycreature;
        this.f = d0;
        this.g = f0;
        this.a(1);
        this.canaryAI = new CanaryAIMoveTowardsTarget(this); //CanaryMod: set our variable
    }

    public boolean a() {
        this.b = this.a.u();
        if (this.b == null) {
            return false;
        } else if (this.b.h(this.a) > (double) (this.g * this.g)) {
            return false;
        } else {
            Vec3 vec3 = RandomPositionGenerator.a(this.a, 16, 7, new Vec3(this.b.s, this.b.t, this.b.u));

            if (vec3 == null) {
                return false;
            } else {
                this.c = vec3.a;
                this.d = vec3.b;
                this.e = vec3.c;
                return true;
            }
        }
    }

    public boolean b() {
        return !this.a.s().m() && this.b.ai() && this.b.h(this.a) < (double) (this.g * this.g);
    }

    public void d() {
        this.b = null;
    }

    public void c() {
        this.a.s().a(this.c, this.d, this.e, this.f);
    }
}
