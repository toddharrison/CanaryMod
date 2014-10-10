package net.minecraft.entity.ai;


import com.google.common.base.Predicate;
import java.util.Collections;
import java.util.List;
import net.canarymod.api.ai.CanaryAIFindEntityNearest;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayerMP;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class EntityAIFindEntityNearest extends EntityAIBase {

    private static final Logger a = LogManager.getLogger();
    private EntityLiving b;
    private final Predicate c;
    private final EntityAINearestAttackableTarget.Sorter d;
    private EntityLivingBase e;
    private Class f;
   
    public EntityAIFindEntityNearest(EntityLiving entityliving, Class oclass0) {
        this.b = entityliving;
        this.f = oclass0;
        if (entityliving instanceof EntityCreature) {
            a.warn("Use NearestAttackableTargetGoal.class for PathfinerMob mobs!");
        }

        this.c = new Predicate() {

            public boolean a(EntityLivingBase p_a_1_) {
                double d0 = EntityAIFindEntityNearest.this.f();

                if (p_a_1_.aw()) {
                    d0 *= 0.800000011920929D;
                }

                return p_a_1_.ay() ? false : ((double) p_a_1_.g(EntityAIFindEntityNearest.this.b) > d0 ? false : EntityAITarget.a(EntityAIFindEntityNearest.this.b, p_a_1_, false, true));
            }

            public boolean apply(Object p_apply_1_) {
                return this.a((EntityLivingBase) p_apply_1_);
            }
        };
        this.d = new EntityAINearestAttackableTarget.Sorter(entityliving);
        this.canaryAI = new CanaryAIFindEntityNearest(this); //CanaryMod: set our variable
    }

    public boolean a() {
        double d0 = this.f();
        List list = this.b.o.a(this.f, this.b.aQ().b(d0, 4.0D, d0), this.c);

        Collections.sort(list, this.d);
        if (list.isEmpty()) {
            return false;
        } else {
            this.e = (EntityLivingBase) list.get(0);
            return true;
        }
    }

    public boolean b() {
        EntityLivingBase entitylivingbase = this.b.u();

        if (entitylivingbase == null) {
            return false;
        } else if (!entitylivingbase.ai()) {
            return false;
        } else {
            double d0 = this.f();

            return this.b.h(entitylivingbase) > d0 * d0 ? false : !(entitylivingbase instanceof EntityPlayerMP) || !((EntityPlayerMP) entitylivingbase).c.d();
        }
    }

    public void c() {
        this.b.d(this.e);
        super.c();
    }

    public void d() {
        this.b.d((EntityLivingBase) null);
        super.c();
    }

    protected double f() {
        IAttributeInstance iattributeinstance = this.b.a(SharedMonsterAttributes.b);

        return iattributeinstance == null ? 16.0D : iattributeinstance.e();
    }

}
