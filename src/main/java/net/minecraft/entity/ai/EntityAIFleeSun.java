package net.minecraft.entity.ai;


import java.util.Random;
import net.canarymod.api.ai.CanaryAIFleeSun;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;


public class EntityAIFleeSun extends EntityAIBase {

    private EntityCreature a;
    private double b;
    private double c;
    private double d;
    private double e;
    private World f;
   
    public EntityAIFleeSun(EntityCreature entitycreature, double d0) {
        this.a = entitycreature;
        this.e = d0;
        this.f = entitycreature.o;
        this.a(1);
        this.canaryAI = new CanaryAIFleeSun(this); //CanaryMod: set our variable
    }

    public boolean a() {
        if (!this.f.w()) {
            return false;
        } else if (!this.a.au()) {
            return false;
        } else if (!this.f.i(new BlockPos(this.a.s, this.a.aQ().b, this.a.u))) {
            return false;
        } else {
            Vec3 vec3 = this.f();

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

    private Vec3 f() {
        Random random = this.a.bb();
        BlockPos blockpos = new BlockPos(this.a.s, this.a.aQ().b, this.a.u);

        for (int i0 = 0; i0 < 10; ++i0) {
            BlockPos blockpos1 = blockpos.a(random.nextInt(20) - 10, random.nextInt(6) - 3, random.nextInt(20) - 10);

            if (!this.f.i(blockpos1) && this.a.a(blockpos1) < 0.0F) {
                return new Vec3((double) blockpos1.n(), (double) blockpos1.o(), (double) blockpos1.p());
            }
        }

        return null;
    }
}
