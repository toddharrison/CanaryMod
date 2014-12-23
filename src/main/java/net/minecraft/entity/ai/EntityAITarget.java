package net.minecraft.entity.ai;


import net.canarymod.hook.entity.MobTargetHook;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import org.apache.commons.lang3.StringUtils;


public abstract class EntityAITarget extends EntityAIBase {

    protected final EntityCreature e;
    protected boolean f;
    private boolean a;
    private int b;
    private int c;
    private int d;

    public EntityAITarget(EntityCreature entitycreature, boolean flag0) {
        this(entitycreature, flag0, false);
    }

    public EntityAITarget(EntityCreature entitycreature, boolean flag0, boolean flag1) {
        this.e = entitycreature;
        this.f = flag0;
        this.a = flag1;
    }

    public static boolean a(EntityLiving entityliving, EntityLivingBase entitylivingbase, boolean flag0, boolean flag1) {
        if (entitylivingbase == null) {
            return false;
        }
        else if (entitylivingbase == entityliving) {
            return false;
        }
        else if (!entitylivingbase.ai()) {
            return false;
        }
        else if (!entityliving.a(entitylivingbase.getClass())) {
            return false;
        }
        else {
            Team team = entityliving.bN();
            Team team1 = entitylivingbase.bN();

            if (team != null && team1 == team) {
                return false;
            }
            else {
                if (entityliving instanceof IEntityOwnable && StringUtils.isNotEmpty(((IEntityOwnable) entityliving).b())) {
                    if (entitylivingbase instanceof IEntityOwnable && ((IEntityOwnable) entityliving).b().equals(((IEntityOwnable) entitylivingbase).b())) {
                        return false;
                    }

                    if (entitylivingbase == ((IEntityOwnable) entityliving).l_()) {
                        return false;
                    }
                }
                else if (entitylivingbase instanceof EntityPlayer && !flag0 && ((EntityPlayer) entitylivingbase).by.a) {
                    return false;
                }

                return !flag1 || entityliving.t().a(entitylivingbase);
            }
        }
    }

    public boolean b() {
        EntityLivingBase entitylivingbase = this.e.u();

        if (entitylivingbase == null) {
            return false;
        }
        else if (!entitylivingbase.ai()) {
            return false;
        }
        else {
            Team team = this.e.bN();
            Team team1 = entitylivingbase.bN();

            if (team != null && team1 == team) {
                return false;
            }
            else {
                double d0 = this.f();

                if (this.e.h(entitylivingbase) > d0 * d0) {
                    return false;
                }
                else {
                    if (this.f) {
                        if (this.e.t().a(entitylivingbase)) {
                            this.d = 0;
                        }
                        else if (++this.d > 60) {
                            return false;
                        }
                    }
                }

                return !(entitylivingbase instanceof EntityPlayer) || !((EntityPlayer) entitylivingbase).by.a;
            }
        }
    }

    protected double f() {
        IAttributeInstance iattributeinstance = this.e.a(SharedMonsterAttributes.b);

        return iattributeinstance == null ? 16.0D : iattributeinstance.e();
    }

    public void c() {
        this.b = 0;
        this.c = 0;
        this.d = 0;
    }

    public void d() {
        this.e.d((EntityLivingBase) null);
    }

    protected boolean a(EntityLivingBase entitylivingbase, boolean flag0) {
        if (!a(this.e, entitylivingbase, flag0, this.f)) {
            return false;
        }
        else if (!this.e.d(new BlockPos(entitylivingbase))) {
            return false;
        }
        else {
            if (this.a) {
                if (--this.c <= 0) {
                    this.b = 0;
                }

                if (this.b == 0) {
                    this.b = this.a(entitylivingbase) ? 1 : 2;
                }

                if (this.b == 2) {
                    return false;
                }
            }

            // CanaryMod: Start: MobTargetHook
            return !((MobTargetHook) new MobTargetHook((net.canarymod.api.entity.living.LivingBase) this.e.getCanaryEntity(), (net.canarymod.api.entity.living.LivingBase) entitylivingbase.getCanaryEntity()).call()).isCanceled();
            // CanaryMod: End: MobTargetHook
            //return true;
        }
    }

    private boolean a(EntityLivingBase entitylivingbase) {
        this.c = 10 + this.e.bb().nextInt(5);
        PathEntity pathentity = this.e.s().a((Entity) entitylivingbase);

        if (pathentity == null) {
            return false;
        }
        else {
            PathPoint pathpoint = pathentity.c();

            if (pathpoint == null) {
                return false;
            }
            else {
                int i0 = pathpoint.a - MathHelper.c(entitylivingbase.s);
                int i1 = pathpoint.c - MathHelper.c(entitylivingbase.u);

                return (double) (i0 * i0 + i1 * i1) <= 2.25D;
            }
        }
    }
}
