package net.minecraft.entity.ai;


import net.canarymod.hook.entity.MobTargetHook;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.MathHelper;
import org.apache.commons.lang3.StringUtils;


public abstract class EntityAITarget extends EntityAIBase {

    protected EntityCreature c;
    protected boolean d;
    private boolean a;
    private int b;
    private int e;
    private int f;

    public EntityAITarget(EntityCreature entitycreature, boolean flag0) {
        this(entitycreature, flag0, false);
    }

    public EntityAITarget(EntityCreature entitycreature, boolean flag0, boolean flag1) {
        this.c = entitycreature;
        this.d = flag0;
        this.a = flag1;
    }

    public boolean b() {
        EntityLivingBase entitylivingbase = this.c.o();

        if (entitylivingbase == null) {
            return false;
        } else if (!entitylivingbase.Z()) {
            return false;
        } else {
            double d0 = this.f();

            if (this.c.e(entitylivingbase) > d0 * d0) {
                return false;
            } else {
                if (this.d) {
                    if (this.c.n().a(entitylivingbase)) {
                        this.f = 0;
                    } else if (++this.f > 60) {
                        return false;
                    }
                }

                return !(entitylivingbase instanceof EntityPlayerMP) || !((EntityPlayerMP) entitylivingbase).c.d();
            }
        }
    }

    protected double f() {
        IAttributeInstance attributeinstance = this.c.a(SharedMonsterAttributes.b);

        return attributeinstance == null ? 16.0D : attributeinstance.e();
    }

    public void c() {
        this.b = 0;
        this.e = 0;
        this.f = 0;
    }

    public void d() {
        this.c.d((EntityLivingBase) null);
    }

    protected boolean a(EntityLivingBase entitylivingbase, boolean flag0) {
        if (entitylivingbase == null) {
            return false;
        } else if (entitylivingbase == this.c) {
            return false;
        } else if (!entitylivingbase.Z()) {
            return false;
        } else if (!this.c.a(entitylivingbase.getClass())) {
            return false;
        } else {
            if (this.c instanceof IEntityOwnable && StringUtils.isNotEmpty(((IEntityOwnable) this.c).b())) {
                if (entitylivingbase instanceof IEntityOwnable && ((IEntityOwnable) this.c).b().equals(((IEntityOwnable) entitylivingbase).b())) {
                    return false;
                }

                if (entitylivingbase == ((IEntityOwnable) this.c).i_()) {
                    return false;
                }
            } else if (entitylivingbase instanceof EntityPlayer && !flag0 && ((EntityPlayer) entitylivingbase).bE.a) {
                return false;
            }

            if (!this.c.b(MathHelper.c(entitylivingbase.t), MathHelper.c(entitylivingbase.u), MathHelper.c(entitylivingbase.v))) {
                return false;
            } else if (this.d && !this.c.n().a(entitylivingbase)) {
                return false;
            } else {
                if (this.a) {
                    if (--this.e <= 0) {
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
                return !((MobTargetHook) new MobTargetHook((net.canarymod.api.entity.living.LivingBase) this.c.getCanaryEntity(), (net.canarymod.api.entity.living.LivingBase) entitylivingbase.getCanaryEntity()).call()).isCanceled();
                // CanaryMod: End: MobTargetHook
                //return true;
            }
        }
    }

    private boolean a(EntityLivingBase entitylivingbase) {
        this.e = 10 + this.c.aI().nextInt(5);
        PathEntity pathentity = this.c.m().a(entitylivingbase);

        if (pathentity == null) {
            return false;
        } else {
            PathPoint pathpoint = pathentity.c();

            if (pathpoint == null) {
                return false;
            } else {
                int i0 = pathpoint.a - MathHelper.c(entitylivingbase.t);
                int i1 = pathpoint.c - MathHelper.c(entitylivingbase.v);

                return (double) (i0 * i0 + i1 * i1) <= 2.25D;
            }
        }
    }
}
