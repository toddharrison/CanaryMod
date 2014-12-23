package net.minecraft.entity.ai;


import net.canarymod.api.ai.CanaryAIWander;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.Vec3;


public class EntityAIWander extends EntityAIBase {

    private EntityCreature a;
    private double b;
    private double c;
    private double d;
    private double e;
    private int f;
    private boolean g;
   
    public EntityAIWander(EntityCreature entitycreature, double d0) {
        this(entitycreature, d0, 120);
    }

    public EntityAIWander(EntityCreature entitycreature, double d0, int i0) {
        this.a = entitycreature;
        this.e = d0;
        this.f = i0;
        this.a(1);
        this.canaryAI = new CanaryAIWander(this); //CanaryMod: set our variable
    }

    public boolean a() {
        if (!this.g) {
            if (this.a.bg() >= 100) {
                return false;
            }

            if (this.a.bb().nextInt(this.f) != 0) {
                return false;
            }
        }

        Vec3 vec3 = RandomPositionGenerator.a(this.a, 10, 7);

        if (vec3 == null) {
            return false;
        } else {
            this.b = vec3.a;
            this.c = vec3.b;
            this.d = vec3.c;
            this.g = false;
            return true;
        }
    }

    public boolean b() {
        return !this.a.s().m();
    }

    public void c() {
        this.a.s().a(this.b, this.c, this.d, this.e);
    }

    public void f() {
        this.g = true;
    }

    public void b(int i0) {
        this.f = i0;
    }
}
