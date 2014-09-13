package net.minecraft.entity;

import net.canarymod.hook.entity.MobTargetHook;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.UUID;

public abstract class EntityCreature extends EntityLiving {

    public static final UUID h = UUID.fromString("E199AD21-BA8A-4C53-8D13-6182D5C69D3A");
    public static final AttributeModifier i = (new AttributeModifier(h, "Fleeing speed bonus", 2.0D, 2)).a(false);
    private PathEntity bp;
    protected Entity bm;
    protected boolean bn;
    protected int bo;
    private ChunkCoordinates bq = new ChunkCoordinates(0, 0, 0);
    private float br = -1.0F;
    private EntityAIBase bs = new EntityAIMoveTowardsRestriction(this, 1.0D);
    private boolean bt;

    public EntityCreature(World world) {
        super(world);
    }

    protected boolean bP() {
        return false;
    }

    protected void bq() {
        this.o.C.a("ai");
        if (this.bo > 0 && --this.bo == 0) {
            IAttributeInstance iattributeinstance = this.a(SharedMonsterAttributes.d);

            iattributeinstance.b(i);
        }

        this.bn = this.bP();
        float f11 = 16.0F;

        if (this.bm == null) {
            // CanaryMod: MobTarget
            Entity entity = this.bR();
            if (entity != null && entity instanceof EntityLivingBase) {
                MobTargetHook hook = (MobTargetHook) new MobTargetHook((net.canarymod.api.entity.living.LivingBase) this.getCanaryEntity(), (net.canarymod.api.entity.living.LivingBase) entity.getCanaryEntity()).call();
                if (!hook.isCanceled()) {
                    this.bm = entity;
                }
            } else {
                this.bm = entity;
            }
            //

            if (this.bm != null) {
                this.bp = this.o.a(this, this.bm, f11, true, false, false, true);
            }
        } else if (this.bm.Z()) {
            float f1 = this.bm.e((Entity) this);

            if (this.p(this.bm)) {
                this.a(this.bm, f1);
            }
        } else {
            this.bm = null;
        }

        if (this.bm instanceof EntityPlayerMP && ((EntityPlayerMP) this.bm).c.d()) {
            this.bm = null;
        }

        this.o.C.b();
        if (!this.bn && this.bm != null && (this.bp == null || this.Z.nextInt(20) == 0)) {
            this.bp = this.o.a(this, this.bm, f11, true, false, false, true);
        } else if (!this.bn && (this.bp == null && this.Z.nextInt(180) == 0 || this.Z.nextInt(120) == 0 || this.bo > 0) && this.aU < 100) {
            this.bQ();
        }

        int i0 = MathHelper.c(this.C.b + 0.5D);
        boolean flag0 = this.M();
        boolean flag1 = this.P();

        this.z = 0.0F;
        if (this.bp != null && this.Z.nextInt(100) != 0) {
            this.o.C.a("followpath");
            Vec3 vec3 = this.bp.a((Entity) this);
            double d0 = (double) (this.M * 2.0F);

            while (vec3 != null && vec3.d(this.s, vec3.b, this.u) < d0 * d0) {
                this.bp.a();
                if (this.bp.b()) {
                    vec3 = null;
                    this.bp = null;
                } else {
                    vec3 = this.bp.a((Entity) this);
                }
            }

            this.bc = false;
            if (vec3 != null) {
                double d1 = vec3.a - this.s;
                double d2 = vec3.c - this.u;
                double d3 = vec3.b - (double) i0;
                float f2 = (float) (Math.atan2(d2, d1) * 180.0D / 3.1415927410125732D) - 90.0F;
                float f3 = MathHelper.g(f2 - this.y);

                this.be = (float) this.a(SharedMonsterAttributes.d).e();
                if (f3 > 30.0F) {
                    f3 = 30.0F;
                }

                if (f3 < -30.0F) {
                    f3 = -30.0F;
                }

                this.y += f3;
                if (this.bn && this.bm != null) {
                    double d4 = this.bm.s - this.s;
                    double d5 = this.bm.u - this.u;
                    float f4 = this.y;

                    this.y = (float) (Math.atan2(d5, d4) * 180.0D / 3.1415927410125732D) - 90.0F;
                    f3 = (f4 - this.y + 90.0F) * 3.1415927F / 180.0F;
                    this.bd = -MathHelper.a(f3) * this.be * 1.0F;
                    this.be = MathHelper.b(f3) * this.be * 1.0F;
                }

                if (d3 > 0.0D) {
                    this.bc = true;
                }
            }

            if (this.bm != null) {
                this.a(this.bm, 30.0F, 30.0F);
            }

            if (this.E && !this.bS()) {
                this.bc = true;
            }

            if (this.Z.nextFloat() < 0.8F && (flag0 || flag1)) {
                this.bc = true;
            }

            this.o.C.b();
        } else {
            super.bq();
            this.bp = null;
        }
    }

    protected void bQ() {
        this.o.C.a("stroll");
        boolean flag0 = false;
        int i0 = -1;
        int i1 = -1;
        int i2 = -1;
        float f0 = -99999.0F;

        for (int i3 = 0; i3 < 10; ++i3) {
            int i4 = MathHelper.c(this.s + (double) this.Z.nextInt(13) - 6.0D);
            int i5 = MathHelper.c(this.t + (double) this.Z.nextInt(7) - 3.0D);
            int i6 = MathHelper.c(this.u + (double) this.Z.nextInt(13) - 6.0D);
            float f1 = this.a(i4, i5, i6);

            if (f1 > f0) {
                f0 = f1;
                i0 = i4;
                i1 = i5;
                i2 = i6;
                flag0 = true;
            }
        }

        if (flag0) {
            this.bp = this.o.a(this, i0, i1, i2, 10.0F, true, false, false, true);
        }

        this.o.C.b();
    }

    protected void a(Entity entity, float f0) {
    }

    public float a(int i0, int i1, int i2) {
        return 0.0F;
    }

    protected Entity bR() {
        return null;
    }

    public boolean by() {
        int i0 = MathHelper.c(this.s);
        int i1 = MathHelper.c(this.C.b);
        int i2 = MathHelper.c(this.u);

        return super.by() && this.a(i0, i1, i2) >= 0.0F;
    }

    public boolean bS() {
        return this.bp != null;
    }

    public void a(PathEntity pathentity) {
        this.bp = pathentity;
    }

    public Entity bT() {
        return this.bm;
    }

    public void b(Entity entity) {
        this.bm = entity;
    }

    public boolean bU() {
        return this.b(MathHelper.c(this.s), MathHelper.c(this.t), MathHelper.c(this.u));
    }

    public boolean b(int i0, int i1, int i2) {
        return this.br == -1.0F ? true : this.bq.e(i0, i1, i2) < this.br * this.br;
    }

    public void a(int i0, int i1, int i2, int i3) {
        this.bq.b(i0, i1, i2);
        this.br = (float) i3;
    }

    public ChunkCoordinates bV() {
        return this.bq;
    }

    public float bW() {
        return this.br;
    }

    public void bX() {
        this.br = -1.0F;
    }

    public boolean bY() {
        return this.br != -1.0F;
    }

    protected void bL() {
        super.bL();
        if (this.bN() && this.bO() != null && this.bO().o == this.o) {
            Entity entity = this.bO();

            this.a((int) entity.s, (int) entity.t, (int) entity.u, 5);
            float f0 = this.e(entity);

            if (this instanceof EntityTameable && ((EntityTameable) this).ca()) {
                if (f0 > 10.0F) {
                    this.a(true, true);
                }

                return;
            }

            if (!this.bt) {
                this.c.a(2, this.bs);
                this.m().a(false);
                this.bt = true;
            }

            this.o(f0);
            if (f0 > 4.0F) {
                this.m().a(entity, 1.0D);
            }

            if (f0 > 6.0F) {
                double d0 = (entity.s - this.s) / (double) f0;
                double d1 = (entity.t - this.t) / (double) f0;
                double d2 = (entity.u - this.u) / (double) f0;

                this.v += d0 * Math.abs(d0) * 0.4D;
                this.w += d1 * Math.abs(d1) * 0.4D;
                this.x += d2 * Math.abs(d2) * 0.4D;
            }

            if (f0 > 10.0F) {
                this.a(true, true);
            }
        } else if (!this.bN() && this.bt) {
            this.bt = false;
            this.c.a(this.bs);
            this.m().a(true);
            this.bX();
        }
    }

    protected void o(float f0) {
    }
}
