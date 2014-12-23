package net.minecraft.entity.ai;


import net.canarymod.api.ai.CanaryAIArrowAttack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.MathHelper;


public class EntityAIArrowAttack extends EntityAIBase {

    private final EntityLiving a;
    private final IRangedAttackMob b;
    private EntityLivingBase c;
    private int d;
    private double e;
    private int f;
    private int g;
    private int h;
    private float i;
    private float j;
   
    public EntityAIArrowAttack(IRangedAttackMob irangedattackmob, double d0, int i0, float f0) {
        this(irangedattackmob, d0, i0, i0, f0);
    }

    public EntityAIArrowAttack(IRangedAttackMob irangedattackmob, double d0, int i0, int i1, float f0) {
        this.d = -1;
        if (!(irangedattackmob instanceof EntityLivingBase)) {
            throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob");
        } else {
            this.b = irangedattackmob;
            this.a = (EntityLiving) irangedattackmob;
            this.e = d0;
            this.g = i0;
            this.h = i1;
            this.i = f0;
            this.j = f0 * f0;
            this.a(3);
        }
        this.canaryAI = new CanaryAIArrowAttack(this); //CanaryMod: set our variable
    }

    public boolean a() {
        EntityLivingBase entitylivingbase = this.a.u();

        if (entitylivingbase == null) {
            return false;
        } else {
            this.c = entitylivingbase;
            return true;
        }
    }

    public boolean b() {
        return this.a() || !this.a.s().m();
    }

    public void d() {
        this.c = null;
        this.f = 0;
        this.d = -1;
    }

    public void e() {
        double d0 = this.a.e(this.c.s, this.c.aQ().b, this.c.u);
        boolean flag0 = this.a.t().a(this.c);

        if (flag0) {
            ++this.f;
        } else {
            this.f = 0;
        }

        if (d0 <= (double) this.j && this.f >= 20) {
            this.a.s().n();
        } else {
            this.a.s().a((Entity) this.c, this.e);
        }

        this.a.p().a(this.c, 30.0F, 30.0F);
        float f0;

        if (--this.d == 0) {
            if (d0 > (double) this.j || !flag0) {
                return;
            }

            f0 = MathHelper.a(d0) / this.i;
            float f1 = MathHelper.a(f0, 0.1F, 1.0F);

            this.b.a(this.c, f1);
            this.d = MathHelper.d(f0 * (float) (this.h - this.g) + (float) this.g);
        } else if (this.d < 0) {
            f0 = MathHelper.a(d0) / this.i;
            this.d = MathHelper.d(f0 * (float) (this.h - this.g) + (float) this.g);
        }

    }
}
