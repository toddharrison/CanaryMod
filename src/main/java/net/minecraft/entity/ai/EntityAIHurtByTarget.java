package net.minecraft.entity.ai;


import java.util.Iterator;
import java.util.List;
import net.canarymod.api.ai.CanaryAIHurtByTarget;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.util.AxisAlignedBB;


public class EntityAIHurtByTarget extends EntityAITarget {

    private boolean a;
    private int b;
    private final Class[] c;
   
    public EntityAIHurtByTarget(EntityCreature entitycreature, boolean flag0, Class... aclass) {
        super(entitycreature, false);
        this.a = flag0;
        this.c = aclass;
        this.a(1);
        this.canaryAI = new CanaryAIHurtByTarget(this); //CanaryMod: set our variable
    }

    public boolean a() {
        int i0 = this.e.bd();

        return i0 != this.b && this.a(this.e.bc(), false);
    }

    public void c() {
        this.e.d(this.e.bc());
        this.b = this.e.bd();
        if (this.a) {
            double d0 = this.f();
            List list = this.e.o.a(this.e.getClass(), (new AxisAlignedBB(this.e.s, this.e.t, this.e.u, this.e.s + 1.0D, this.e.t + 1.0D, this.e.u + 1.0D)).b(d0, 10.0D, d0));
            Iterator iterator = list.iterator();

            while (iterator.hasNext()) {
                EntityCreature entitycreature = (EntityCreature) iterator.next();

                if (this.e != entitycreature && entitycreature.u() == null && !entitycreature.c(this.e.bc())) {
                    boolean flag0 = false;
                    Class[] aclass = this.c;
                    int i0 = aclass.length;

                    for (int i1 = 0; i1 < i0; ++i1) {
                        Class oclass0 = aclass[i1];

                        if (entitycreature.getClass() == oclass0) {
                            flag0 = true;
                            break;
                        }
                    }

                    if (!flag0) {
                        this.a(entitycreature, this.e.bc());
                    }
                }
            }
        }

        super.c();
    }

    protected void a(EntityCreature entitycreature, EntityLivingBase entitylivingbase) {
        entitycreature.d(entitylivingbase);
    }
}
