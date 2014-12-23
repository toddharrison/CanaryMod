package net.minecraft.entity.ai;


import net.canarymod.api.ai.CanaryAIRunAroundLikeCrazy;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;


public class EntityAIRunAroundLikeCrazy extends EntityAIBase {

    private EntityHorse a;
    private double b;
    private double c;
    private double d;
    private double e;
   
    public EntityAIRunAroundLikeCrazy(EntityHorse entityhorse, double d0) {
        this.a = entityhorse;
        this.b = d0;
        this.a(1);
        this.canaryAI = new CanaryAIRunAroundLikeCrazy(this); //CanaryMod: set our variable
    }

    public boolean a() {
        if (!this.a.cm() && this.a.l != null) {
            Vec3 vec3 = RandomPositionGenerator.a(this.a, 5, 4);

            if (vec3 == null) {
                return false;
            } else {
                this.c = vec3.a;
                this.d = vec3.b;
                this.e = vec3.c;
                return true;
            }
        } else {
            return false;
        }
    }

    public void c() {
        this.a.s().a(this.c, this.d, this.e, this.b);
    }

    public boolean b() {
        return !this.a.s().m() && this.a.l != null;
    }

    public void e() {
        if (this.a.bb().nextInt(50) == 0) {
            if (this.a.l instanceof EntityPlayer) {
                int i0 = this.a.cA();
                int i1 = this.a.cG();

                if (i1 > 0 && this.a.bb().nextInt(i1) < i0) {
                    this.a.h((EntityPlayer) this.a.l);
                    this.a.o.a((Entity) this.a, (byte) 7);
                    return;
                }

                this.a.u(5);
            }

            this.a.l.a((Entity) null);
            this.a.l = null;
            this.a.cU();
            this.a.o.a((Entity) this.a, (byte) 6);
        }

    }
}
