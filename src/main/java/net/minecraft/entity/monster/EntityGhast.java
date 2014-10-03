package net.minecraft.entity.monster;

import net.canarymod.api.entity.living.monster.CanaryGhast;
import net.canarymod.hook.entity.MobTargetHook;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFindEntityNearestPlayer;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;


public class EntityGhast extends EntityFlying implements IMob {

    private int a = 1;
   
    public EntityGhast(World world) {
        super(world);
        this.a(4.0F, 4.0F);
        this.ab = true;
        this.b_ = 5;
        this.f = new EntityGhast.GhastMoveHelper();
        this.i.a(5, new EntityGhast.AIRandomFly());
        this.i.a(7, new EntityGhast.AILookAround());
        this.i.a(7, new EntityGhast.AIFireballAttack());
        this.bg.a(1, new EntityAIFindEntityNearestPlayer(this));
        this.entity = new CanaryGhast(this); // CanaryMod: Wrap Entity
    }

    public void a(boolean flag0) {
        this.ac.b(16, Byte.valueOf((byte) (flag0 ? 1 : 0)));
    }

    public int cd() {
        return this.a;
    }

    public void s_() {
        super.s_();
        if (!this.o.D && this.o.aa() == EnumDifficulty.PEACEFUL) {
            this.J();
        }

    }

    public boolean a(DamageSource damagesource, float f0) {
        if (this.b(damagesource)) {
            return false;
        } else if ("fireball".equals(damagesource.p()) && damagesource.j() instanceof EntityPlayer) {
            super.a(damagesource, 1000.0F);
            ((EntityPlayer) damagesource.j()).b((StatBase) AchievementList.z);
            return true;
        } else {
            return super.a(damagesource, f0);
        }
    }

    protected void h() {
        super.h();
        this.ac.a(16, Byte.valueOf((byte) 0));
    }

    protected void aW() {
        super.aW();
        this.a(SharedMonsterAttributes.a).a(10.0D);
        this.a(SharedMonsterAttributes.b).a(100.0D);
    }

    protected String z() {
        return "mob.ghast.moan";
    }

    protected String bn() {
        return "mob.ghast.scream";
    }

    protected String bo() {
        return "mob.ghast.death";
    }

    protected Item A() {
        return Items.H;
    }

    protected void b(boolean flag0, int i0) {
        int i1 = this.V.nextInt(2) + this.V.nextInt(1 + i0);

        int i2;

        for (i2 = 0; i2 < i1; ++i2) {
            this.a(Items.bw, 1);
        }

        i1 = this.V.nextInt(3) + this.V.nextInt(1 + i0);

        for (i2 = 0; i2 < i1; ++i2) {
            this.a(Items.H, 1);
        }

    }

    protected float bA() {
        return 10.0F;
    }

    public boolean bQ() {
        return this.V.nextInt(20) == 0 && super.bQ() && this.o.aa() != EnumDifficulty.PEACEFUL;
    }

    public int bU() {
        return 1;
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("ExplosionPower", this.a);
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        if (nbttagcompound.b("ExplosionPower", 99)) {
            this.a = nbttagcompound.f("ExplosionPower");
        }

    }

    public float aR() {
        return 2.6F;
    }

    class AIFireballAttack extends EntityAIBase {

        private EntityGhast b = EntityGhast.this;
        public int a;
      
        public boolean a() {
            return this.b.u() != null;
        }

        public void c() {
            this.a = 0;
        }

        public void d() {
            this.b.a(false);
        }

        public void e() {
            EntityLivingBase entitylivingbase = this.b.u();
            double d0 = 64.0D;

            if (entitylivingbase.h(this.b) < d0 * d0 && this.b.t(entitylivingbase)) {
                World world = this.b.o;

                ++this.a;
                if (this.a == 10) {
                    world.a((EntityPlayer) null, 1007, new BlockPos(this.b), 0);
                }

                if (this.a == 20) {
                    double d1 = 4.0D;
                    Vec3 vec3 = this.b.d(1.0F);
                    double d2 = entitylivingbase.s - (this.b.s + vec3.a * d1);
                    double d3 = entitylivingbase.aQ().b + (double) (entitylivingbase.K / 2.0F) - (0.5D + this.b.t + (double) (this.b.K / 2.0F));
                    double d4 = entitylivingbase.u - (this.b.u + vec3.c * d1);

                    world.a((EntityPlayer) null, 1008, new BlockPos(this.b), 0);
                    EntityLargeFireball entitylargefireball = new EntityLargeFireball(world, this.b, d2, d3, d4);

                    entitylargefireball.e = this.b.cd();
                    entitylargefireball.s = this.b.s + vec3.a * d1;
                    entitylargefireball.t = this.b.t + (double) (this.b.K / 2.0F) + 0.5D;
                    entitylargefireball.u = this.b.u + vec3.c * d1;
                    world.d((Entity) entitylargefireball);
                    this.a = -40;
                }
            } else if (this.a > 0) {
                --this.a;
            }

            this.b.a(this.a > 10);
        }
    }


    class AILookAround extends EntityAIBase {

        private EntityGhast a = EntityGhast.this;
      
        public AILookAround() {
            this.a(2);
        }

        public boolean a() {
            return true;
        }

        public void e() {
            if (this.a.u() == null) {
                this.a.aG = this.a.y = -((float) Math.atan2(this.a.v, this.a.x)) * 180.0F / 3.1415927F;
            } else {
                EntityLivingBase entitylivingbase = this.a.u();
                double d0 = 64.0D;

                if (entitylivingbase.h(this.a) < d0 * d0) {
                    double d1 = entitylivingbase.s - this.a.s;
                    double d2 = entitylivingbase.u - this.a.u;

                    this.a.aG = this.a.y = -((float) Math.atan2(d1, d2)) * 180.0F / 3.1415927F;
                }
            }
        }
    }


    class AIRandomFly extends EntityAIBase {

        private EntityGhast a = EntityGhast.this;
      
        public AIRandomFly() {
            this.a(1);
        }

        public boolean a() {
            EntityMoveHelper random = this.a.q();

            if (!random.a()) {
                return true;
            } else {
                double d4 = random.d() - this.a.s;
                double d5 = random.e() - this.a.t;
                double d6 = random.f() - this.a.u;
                double d3 = d4 * d4 + d5 * d5 + d6 * d6;

                return d3 < 1.0D || d3 > 3600.0D;
            }
        }

        public boolean b() {
            return false;
        }

        public void c() {
            Random random = this.a.bb();
            double d4 = this.a.s + (double) ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            double d5 = this.a.t + (double) ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            double d6 = this.a.u + (double) ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);

            this.a.q().a(d4, d5, d6, 1.0D);
        }
    }

    class GhastMoveHelper extends EntityMoveHelper {

        private EntityGhast g = EntityGhast.this;
        private int h;
      
        public GhastMoveHelper() {
            super(EntityGhast.this);
        }

        public void c() {
            if (this.f) {
                double d0 = this.b - this.g.s;
                double d1 = this.c - this.g.t;
                double d2 = this.d - this.g.u;
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;

                if (this.h-- <= 0) {
                    this.h += this.g.bb().nextInt(5) + 2;
                    d3 = (double) MathHelper.a(d3);
                    if (this.b(this.b, this.c, this.d, d3)) {
                        this.g.v += d0 / d3 * 0.1D;
                        this.g.w += d1 / d3 * 0.1D;
                        this.g.x += d2 / d3 * 0.1D;
                    } else {
                        this.f = false;
                    }
                }

            }
        }

        private boolean b(double p_b_1_, double p_b_3_, double p_b_5_, double p_b_7_) {
            double d4 = (p_b_1_ - this.g.s) / p_b_7_;
            double d5 = (p_b_3_ - this.g.t) / p_b_7_;
            double d6 = (p_b_5_ - this.g.u) / p_b_7_;
            AxisAlignedBB axisalignedbb = this.g.aQ();

            for (int i0 = 1; (double) i0 < p_b_7_; ++i0) {
                axisalignedbb = axisalignedbb.c(d4, d5, d6);
                if (!this.g.o.a((Entity) this.g, axisalignedbb).isEmpty()) {
                    return false;
                }
            }

            return true;
        }
    }
}
