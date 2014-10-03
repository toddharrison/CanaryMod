package net.minecraft.entity.monster;

import net.canarymod.api.entity.living.monster.CanaryBlaze;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;


public class EntityBlaze extends EntityMob {

    private float b = 0.5F;
    private int c;
   
    public EntityBlaze(World world) {
        super(world);
        this.ab = true;
        this.b_ = 10;
        this.i.a(4, new EntityBlaze.AIFireballAttack());
        this.i.a(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.i.a(7, new EntityAIWander(this, 1.0D));
        this.i.a(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.i.a(8, new EntityAILookIdle(this));
        this.bg.a(1, new EntityAIHurtByTarget(this, true, new Class[0]));
        this.bg.a(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        this.entity = new CanaryBlaze(this); // CanaryMod: Wrap Entity
    }

    protected void aW() {
        super.aW();
        this.a(SharedMonsterAttributes.e).a(6.0D);
        this.a(SharedMonsterAttributes.d).a(0.23000000417232513D);
        this.a(SharedMonsterAttributes.b).a(48.0D);
    }

    protected void h() {
        super.h();
        this.ac.a(16, new Byte((byte) 0));
    }

    protected String z() {
        return "mob.blaze.breathe";
    }

    protected String bn() {
        return "mob.blaze.hit";
    }

    protected String bo() {
        return "mob.blaze.death";
    }

    public float c(float f0) {
        return 1.0F;
    }

    public void m() {
        if (!this.C && this.w < 0.0D) {
            this.w *= 0.6D;
        }

        if (this.o.D) {
            if (this.V.nextInt(24) == 0 && !this.R()) {
                this.o.a(this.s + 0.5D, this.t + 0.5D, this.u + 0.5D, "fire.fire", 1.0F + this.V.nextFloat(), this.V.nextFloat() * 0.7F + 0.3F, false);
            }

            for (int i0 = 0; i0 < 2; ++i0) {
                this.o.a(EnumParticleTypes.SMOKE_LARGE, this.s + (this.V.nextDouble() - 0.5D) * (double) this.J, this.t + this.V.nextDouble() * (double) this.K, this.u + (this.V.nextDouble() - 0.5D) * (double) this.J, 0.0D, 0.0D, 0.0D, new int[0]);
            }
        }

        super.m();
    }

    protected void E() {
        if (this.U()) {
            this.a(DamageSource.f, 1.0F);
        }

        --this.c;
        if (this.c <= 0) {
            this.c = 100;
            this.b = 0.5F + (float) this.V.nextGaussian() * 3.0F;
        }

        EntityLivingBase entitylivingbase = this.u();

        if (entitylivingbase != null && entitylivingbase.t + (double) entitylivingbase.aR() > this.t + (double) this.aR() + (double) this.b) {
            this.w += (0.30000001192092896D - this.w) * 0.30000001192092896D;
            this.ai = true;
        }

        super.E();
    }

    public void e(float f0, float f1) {}

    protected Item A() {
        return Items.bv;
    }

    public boolean au() {
        return this.n();
    }

    protected void b(boolean flag0, int i0) {
        if (flag0) {
            int i1 = this.V.nextInt(2 + i0);

            for (int i2 = 0; i2 < i1; ++i2) {
                this.a(Items.bv, 1);
            }
        }

    }

    public boolean n() {
        return (this.ac.a(16) & 1) != 0;
    }

    public void a(boolean flag0) {
        byte b0 = this.ac.a(16);

        if (flag0) {
            b0 = (byte) (b0 | 1);
        } else {
            b0 &= -2;
        }

        this.ac.b(16, Byte.valueOf(b0));
    }

    protected boolean m_() {
        return true;
    }

    class AIFireballAttack extends EntityAIBase {

        private EntityBlaze a = EntityBlaze.this;
        private int b;
        private int c;
      
        public AIFireballAttack() {
            this.a(3);
        }

        public boolean a() {
            EntityLivingBase entitylivingbase1 = this.a.u();

            return entitylivingbase1 != null && entitylivingbase1.ai();
        }

        public void c() {
            this.b = 0;
        }

        public void d() {
            this.a.a(false);
        }

        public void e() {
            --this.c;
            EntityLivingBase entitylivingbase1 = this.a.u();
            double d0 = this.a.h(entitylivingbase1);

            if (d0 < 4.0D) {
                if (this.c <= 0) {
                    this.c = 20;
                    this.a.r(entitylivingbase1);
                }

                this.a.q().a(entitylivingbase1.s, entitylivingbase1.t, entitylivingbase1.u, 1.0D);
            } else if (d0 < 256.0D) {
                double d1 = entitylivingbase1.s - this.a.s;
                double d2 = entitylivingbase1.aQ().b + (double) (entitylivingbase1.K / 2.0F) - (this.a.t + (double) (this.a.K / 2.0F));
                double d3 = entitylivingbase1.u - this.a.u;

                if (this.c <= 0) {
                    ++this.b;
                    if (this.b == 1) {
                        this.c = 60;
                        this.a.a(true);
                    } else if (this.b <= 4) {
                        this.c = 6;
                    } else {
                        this.c = 100;
                        this.b = 0;
                        this.a.a(false);
                    }

                    if (this.b > 1) {
                        float f0 = MathHelper.c(MathHelper.a(d0)) * 0.5F;

                        this.a.o.a((EntityPlayer) null, 1009, new BlockPos((int) this.a.s, (int) this.a.t, (int) this.a.u), 0);

                        for (int i0 = 0; i0 < 1; ++i0) {
                            EntitySmallFireball entitysmallfireball = new EntitySmallFireball(this.a.o, this.a, d1 + this.a.bb().nextGaussian() * (double) f0, d2, d3 + this.a.bb().nextGaussian() * (double) f0);

                            entitysmallfireball.t = this.a.t + (double) (this.a.K / 2.0F) + 0.5D;
                            this.a.o.d((Entity) entitysmallfireball);
                        }
                    }
                }

                this.a.p().a(entitylivingbase1, 10.0F, 10.0F);
            } else {
                this.a.s().n();
                this.a.q().a(entitylivingbase1.s, entitylivingbase1.t, entitylivingbase1.u, 1.0D);
            }

            super.e();
        }
    }
}
