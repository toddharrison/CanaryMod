package net.minecraft.entity.ai;


import net.canarymod.api.ai.CanaryAIPanic;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.Vec3;


public class EntityAIPanic extends EntityAIBase {

    private EntityCreature b;
    protected double a;
    private double c;
    private double d;
    private double e;
   
    public EntityAIPanic(EntityCreature entitycreature, double d0) {
        this.b = entitycreature;
        this.a = d0;
        this.a(1);
        this.canaryAI = new CanaryAIPanic(this); //CanaryMod: set our variable
    }

    public boolean a() {
        if (this.b.bc() == null && !this.b.au()) {
            return false;
        } else {
            Vec3 vec3 = RandomPositionGenerator.a(this.b, 5, 4);

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

    public void c() {
        this.b.s().a(this.c, this.d, this.e, this.a);
    }

    public boolean b() {
        return !this.b.s().m();
    }
}
