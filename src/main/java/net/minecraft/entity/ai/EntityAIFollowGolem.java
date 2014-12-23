package net.minecraft.entity.ai;


import java.util.Iterator;
import java.util.List;
import net.canarymod.api.ai.CanaryAIFollowGolem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.passive.EntityVillager;


public class EntityAIFollowGolem extends EntityAIBase {

    private EntityVillager a;
    private EntityIronGolem b;
    private int c;
    private boolean d;
   
    public EntityAIFollowGolem(EntityVillager entityvillager) {
        this.a = entityvillager;
        this.a(3);
        this.canaryAI = new CanaryAIFollowGolem(this); //CanaryMod: set our variable
    }

    public boolean a() {
        if (this.a.l() >= 0) {
            return false;
        } else if (!this.a.o.w()) {
            return false;
        } else {
            List list = this.a.o.a(EntityIronGolem.class, this.a.aQ().b(6.0D, 2.0D, 6.0D));

            if (list.isEmpty()) {
                return false;
            } else {
                Iterator iterator = list.iterator();

                while (iterator.hasNext()) {
                    EntityIronGolem entityirongolem = (EntityIronGolem) iterator.next();

                    if (entityirongolem.ck() > 0) {
                        this.b = entityirongolem;
                        break;
                    }
                }

                return this.b != null;
            }
        }
    }

    public boolean b() {
        return this.b.ck() > 0;
    }

    public void c() {
        this.c = this.a.bb().nextInt(320);
        this.d = false;
        this.b.s().n();
    }

    public void d() {
        this.b = null;
        this.a.s().n();
    }

    public void e() {
        this.a.p().a(this.b, 30.0F, 30.0F);
        if (this.b.ck() == this.c) {
            this.a.s().a((Entity) this.b, 0.5D);
            this.d = true;
        }

        if (this.d && this.a.h(this.b) < 4.0D) {
            this.b.a(false);
            this.a.s().n();
        }

    }
}
