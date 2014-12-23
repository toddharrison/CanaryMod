package net.minecraft.entity.ai;


import net.canarymod.api.ai.CanaryAIAttackOnCollide;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;


public class EntityAIAttackOnCollide extends EntityAIBase {

    World a;
    protected EntityCreature b;
    int c;
    double d;
    boolean e;
    PathEntity f;
    Class g;
    private int h;
    private double i;
    private double j;
    private double k;
   
    public EntityAIAttackOnCollide(EntityCreature entitycreature, Class oclass0, double d0, boolean flag0) {
        this(entitycreature, d0, flag0);
        this.g = oclass0;
    }

    public EntityAIAttackOnCollide(EntityCreature entitycreature, double d0, boolean flag0) {
        this.b = entitycreature;
        this.a = entitycreature.o;
        this.d = d0;
        this.e = flag0;
        this.a(3);
        this.canaryAI = new CanaryAIAttackOnCollide(this); //CanaryMod: set our variable
    }

    public boolean a() {
        EntityLivingBase entitylivingbase = this.b.u();

        if (entitylivingbase == null) {
            return false;
        } else if (!entitylivingbase.ai()) {
            return false;
        } else if (this.g != null && !this.g.isAssignableFrom(entitylivingbase.getClass())) {
            return false;
        } else {
            this.f = this.b.s().a((Entity) entitylivingbase);
            return this.f != null;
        }
    }

    public boolean b() {
        EntityLivingBase entitylivingbase = this.b.u();

        return entitylivingbase == null ? false : (!entitylivingbase.ai() ? false : (!this.e ? !this.b.s().m() : this.b.d(new BlockPos(entitylivingbase))));
    }

    public void c() {
        this.b.s().a(this.f, this.d);
        this.h = 0;
    }

    public void d() {
        this.b.s().n();
    }

    public void e() {
        EntityLivingBase entitylivingbase = this.b.u();

        this.b.p().a(entitylivingbase, 30.0F, 30.0F);
        double d0 = this.b.e(entitylivingbase.s, entitylivingbase.aQ().b, entitylivingbase.u);
        double d1 = this.a(entitylivingbase);

        --this.h;
        if ((this.e || this.b.t().a(entitylivingbase)) && this.h <= 0 && (this.i == 0.0D && this.j == 0.0D && this.k == 0.0D || entitylivingbase.e(this.i, this.j, this.k) >= 1.0D || this.b.bb().nextFloat() < 0.05F)) {
            this.i = entitylivingbase.s;
            this.j = entitylivingbase.aQ().b;
            this.k = entitylivingbase.u;
            this.h = 4 + this.b.bb().nextInt(7);
            if (d0 > 1024.0D) {
                this.h += 10;
            } else if (d0 > 256.0D) {
                this.h += 5;
            }

            if (!this.b.s().a((Entity) entitylivingbase, this.d)) {
                this.h += 15;
            }
        }

        this.c = Math.max(this.c - 1, 0);
        if (d0 <= d1 && this.c <= 0) {
            this.c = 20;
            if (this.b.bz() != null) {
                this.b.bv();
            }

            this.b.r(entitylivingbase);
        }

    }

    protected double a(EntityLivingBase entitylivingbase) {
        return (double) (this.b.J * 2.0F * this.b.J * 2.0F + entitylivingbase.J);
    }
}
