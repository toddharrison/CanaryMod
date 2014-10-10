package net.minecraft.entity.ai;


import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import net.canarymod.api.ai.CanaryAIAvoidEntity;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.Vec3;


public class EntityAIAvoidEntity extends EntityAIBase {

    public final Predicate a = new Predicate() {

        public boolean a(Entity p_a_1_) {
            return p_a_1_.ai() && EntityAIAvoidEntity.this.b.t().a(p_a_1_);
        }

        public boolean apply(Object p_apply_1_) {
            return this.a((Entity) p_apply_1_);
        }
    };
    protected EntityCreature b;
    private double d;
    private double e;
    protected Entity c;
    private float f;
    private PathEntity g;
    private PathNavigate h;
    private Predicate i;
   
    public EntityAIAvoidEntity(EntityCreature entitycreature, Predicate predicate, float f0, double d0, double d1) {
        this.b = entitycreature;
        this.i = predicate;
        this.f = f0;
        this.d = d0;
        this.e = d1;
        this.h = entitycreature.s();
        this.a(1);
        this.canaryAI = new CanaryAIAvoidEntity(this); //CanaryMod: set our variable
    }

    public boolean a() {
        List list = this.b.o.a((Entity) this.b, this.b.aQ().b((double) this.f, 3.0D, (double) this.f), Predicates.and(new Predicate[] { IEntitySelector.d, this.a, this.i}));

        if (list.isEmpty()) {
            return false;
        } else {
            this.c = (Entity) list.get(0);
            Vec3 vec3 = RandomPositionGenerator.b(this.b, 16, 7, new Vec3(this.c.s, this.c.t, this.c.u));

            if (vec3 == null) {
                return false;
            } else if (this.c.e(vec3.a, vec3.b, vec3.c) < this.c.h(this.b)) {
                return false;
            } else {
                this.g = this.h.a(vec3.a, vec3.b, vec3.c);
                return this.g == null ? false : this.g.b(vec3);
            }
        }
    }

    public boolean b() {
        return !this.h.m();
    }

    public void c() {
        this.h.a(this.g, this.d);
    }

    public void d() {
        this.c = null;
    }

    public void e() {
        if (this.b.h(this.c) < 49.0D) {
            this.b.s().a(this.e);
        } else {
            this.b.s().a(this.d);
        }

    }
}
