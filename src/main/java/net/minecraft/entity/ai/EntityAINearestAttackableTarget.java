package net.minecraft.entity.ai;


import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import net.canarymod.api.ai.CanaryAINearestAttackableTarget;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.player.EntityPlayer;


public class EntityAINearestAttackableTarget extends EntityAITarget {

    protected final Class a;
    private final int g;
    protected final EntityAINearestAttackableTarget.Sorter b;
    protected Predicate c;
    protected EntityLivingBase d;
   
    public EntityAINearestAttackableTarget(EntityCreature entitycreature, Class oclass0, boolean flag0) {
        this(entitycreature, oclass0, flag0, false);
    }

    public EntityAINearestAttackableTarget(EntityCreature entitycreature, Class oclass0, boolean flag0, boolean flag1) {
        this(entitycreature, oclass0, 10, flag0, flag1, (Predicate) null);
    }

    public EntityAINearestAttackableTarget(EntityCreature entitycreature, Class oclass0, int i0, boolean flag0, boolean flag1, final Predicate predicate) {
        super(entitycreature, flag0, flag1);
        this.a = oclass0;
        this.g = i0;
        this.b = new EntityAINearestAttackableTarget.Sorter(entitycreature);
        this.a(1);
        this.c = new Predicate() {

            public boolean a(EntityLivingBase p_a_1_) {
                if (predicate != null && !predicate.apply(p_a_1_)) {
                    return false;
                } else {
                    if (p_a_1_ instanceof EntityPlayer) {
                        double d0 = EntityAINearestAttackableTarget.this.f();

                        if (p_a_1_.aw()) {
                            d0 *= 0.800000011920929D;
                        }

                        if (p_a_1_.ay()) {
                            float f0 = ((EntityPlayer) p_a_1_).bX();

                            if (f0 < 0.1F) {
                                f0 = 0.1F;
                            }

                            d0 *= (double) (0.7F * f0);
                        }

                        if ((double) p_a_1_.g(EntityAINearestAttackableTarget.this.e) > d0) {
                            return false;
                        }
                    }

                    return EntityAINearestAttackableTarget.this.a(p_a_1_, false);
                }
            }

            public boolean apply(Object p_apply_1_) {
                return this.a((EntityLivingBase) p_apply_1_);
            }
        };
        this.canaryAI = new CanaryAINearestAttackableTarget(this); //CanaryMod: set our variable
    }

    public boolean a() {
        if (this.g > 0 && this.e.bb().nextInt(this.g) != 0) {
            return false;
        } else {
            double d0 = this.f();
            List list = this.e.o.a(this.a, this.e.aQ().b(d0, 4.0D, d0), Predicates.and(this.c, IEntitySelector.d));

            Collections.sort(list, this.b);
            if (list.isEmpty()) {
                return false;
            } else {
                this.d = (EntityLivingBase) list.get(0);
                return true;
            }
        }
    }

    public void c() {
        this.e.d(this.d);
        super.c();
    }

    public static class Sorter implements Comparator {

        private final Entity a;
      
        public Sorter(Entity p_i1662_1_) {
            this.a = p_i1662_1_;
        }

        public int compare(Entity p_compare_1_, Entity p_compare_2_) {
            double d0 = this.a.h(p_compare_1_);
            double d1 = this.a.h(p_compare_2_);

            return d0 < d1 ? -1 : (d0 > d1 ? 1 : 0);
        }

        public int compare(Object p_compare_1_, Object p_compare_2_) {
            return this.compare((Entity) p_compare_1_, (Entity) p_compare_2_);
        }
    }
}
