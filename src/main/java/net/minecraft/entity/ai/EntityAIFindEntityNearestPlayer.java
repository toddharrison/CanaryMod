package net.minecraft.entity.ai;


import com.google.common.base.Predicate;
import java.util.Collections;
import java.util.List;
import net.canarymod.api.ai.CanaryAIFindEntityNearestPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.scoreboard.Team;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class EntityAIFindEntityNearestPlayer extends EntityAIBase {

    private static final Logger a = LogManager.getLogger();
    private EntityLiving b;
    private final Predicate c;
    private final EntityAINearestAttackableTarget.Sorter d;
    private EntityLivingBase e;
   
    public EntityAIFindEntityNearestPlayer(EntityLiving entityliving) {
        this.b = entityliving;
        if (entityliving instanceof EntityCreature) {
            a.warn("Use NearestAttackableTargetGoal.class for PathfinerMob mobs!");
        }

        this.c = new Predicate() {

            public boolean a(Entity p_a_1_) {
                if (!(p_a_1_ instanceof EntityPlayer)) {
                    return false;
                } else {
                    double d0 = EntityAIFindEntityNearestPlayer.this.f();

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

                    return (double) p_a_1_.g(EntityAIFindEntityNearestPlayer.this.b) > d0 ? false : EntityAITarget.a(EntityAIFindEntityNearestPlayer.this.b, (EntityLivingBase) p_a_1_, false, true);
                }
            }

            public boolean apply(Object p_apply_1_) {
                return this.a((Entity) p_apply_1_);
            }
        };
        this.d = new EntityAINearestAttackableTarget.Sorter(entityliving);
        this.canaryAI = new CanaryAIFindEntityNearestPlayer(this); //CanaryMod: set our variable
    }

    public boolean a() {
        double d0 = this.f();
        List list = this.b.o.a(EntityPlayer.class, this.b.aQ().b(d0, 4.0D, d0), this.c);

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
            Team team = this.b.bN();
            Team team1 = entitylivingbase.bN();

            if (team != null && team1 == team) {
                return false;
            } else {
                double d0 = this.f();

                return this.b.h(entitylivingbase) > d0 * d0 ? false : !(entitylivingbase instanceof EntityPlayerMP) || !((EntityPlayerMP) entitylivingbase).c.d();
            }
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
