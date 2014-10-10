package net.minecraft.entity.ai;


import net.canarymod.api.ai.CanaryAIMoveTowardsRestriction;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;


public class EntityAIMoveTowardsRestriction extends EntityAIBase {

    private EntityCreature a;
    private double b;
    private double c;
    private double d;
    private double e;
   
    public EntityAIMoveTowardsRestriction(EntityCreature entitycreature, double d0) {
        this.a = entitycreature;
        this.e = d0;
        this.a(1);
        this.canaryAI = new CanaryAIMoveTowardsRestriction(this); //CanaryMod: set our variable
    }

    public boolean a() {
        if (this.a.ce()) {
            return false;
        } else {
            BlockPos blockpos = this.a.cf();
            Vec3 vec3 = RandomPositionGenerator.a(this.a, 16, 7, new Vec3((double) blockpos.n(), (double) blockpos.o(), (double) blockpos.p()));

            if (vec3 == null) {
                return false;
            } else {
                this.b = vec3.a;
                this.c = vec3.b;
                this.d = vec3.c;
                return true;
            }
        }
    }

    public boolean b() {
        return !this.a.s().m();
    }

    public void c() {
        this.a.s().a(this.b, this.c, this.d, this.e);
    }
}
