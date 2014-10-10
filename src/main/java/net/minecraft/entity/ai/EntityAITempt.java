package net.minecraft.entity.ai;


import net.canarymod.api.ai.CanaryAITempt;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigateGround;


public class EntityAITempt extends EntityAIBase {

    private EntityCreature a;
    private double b;
    private double c;
    private double d;
    private double e;
    private double f;
    private double g;
    private EntityPlayer h;
    private int i;
    private boolean j;
    private Item k;
    private boolean l;
    private boolean m;
   
    public EntityAITempt(EntityCreature entitycreature, double d0, Item item, boolean flag0) {
        this.a = entitycreature;
        this.b = d0;
        this.k = item;
        this.l = flag0;
        this.a(3);
        if (!(entitycreature.s() instanceof PathNavigateGround)) {
            throw new IllegalArgumentException("Unsupported mob type for TemptGoal");
        }
        this.canaryAI = new CanaryAITempt(this); //CanaryMod: set our variable
    }

    public boolean a() {
        if (this.i > 0) {
            --this.i;
            return false;
        } else {
            this.h = this.a.o.a(this.a, 10.0D);
            if (this.h == null) {
                return false;
            } else {
                ItemStack itemstack = this.h.bY();

                return itemstack == null ? false : itemstack.b() == this.k;
            }
        }
    }

    public boolean b() {
        if (this.l) {
            if (this.a.h(this.h) < 36.0D) {
                if (this.h.e(this.c, this.d, this.e) > 0.010000000000000002D) {
                    return false;
                }

                if (Math.abs((double) this.h.z - this.f) > 5.0D || Math.abs((double) this.h.y - this.g) > 5.0D) {
                    return false;
                }
            } else {
                this.c = this.h.s;
                this.d = this.h.t;
                this.e = this.h.u;
            }

            this.f = (double) this.h.z;
            this.g = (double) this.h.y;
        }

        return this.a();
    }

    public void c() {
        this.c = this.h.s;
        this.d = this.h.t;
        this.e = this.h.u;
        this.j = true;
        this.m = ((PathNavigateGround) this.a.s()).e();
        ((PathNavigateGround) this.a.s()).a(false);
    }

    public void d() {
        this.h = null;
        this.a.s().n();
        this.i = 100;
        this.j = false;
        ((PathNavigateGround) this.a.s()).a(this.m);
    }

    public void e() {
        this.a.p().a(this.h, 30.0F, (float) this.a.bP());
        if (this.a.h(this.h) < 6.25D) {
            this.a.s().n();
        } else {
            this.a.s().a((Entity) this.h, this.b);
        }

    }

    public boolean f() {
        return this.j;
    }
}
