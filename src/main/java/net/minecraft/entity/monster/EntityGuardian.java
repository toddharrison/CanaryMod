package net.minecraft.entity.monster;

import com.google.common.base.Predicate;
import net.canarymod.api.entity.living.monster.CanaryGuardian;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFishFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateSwimmer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

public class EntityGuardian extends EntityMob {

    private float b;
    private float c;
    private float bk;
    private float bl;
    private float bm;
    private EntityLivingBase bn;
    private int bo;
    private boolean bp;
    private EntityAIWander bq;

    public EntityGuardian(World world) {
        super(world);
        this.b_ = 10;
        this.a(0.85F, 0.85F);
        this.i.a(4, new AIGuardianAttack());
        EntityAIMoveTowardsRestriction entityaimovetowardsrestriction;

        this.i.a(5, entityaimovetowardsrestriction = new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.i.a(7, this.bq = new EntityAIWander(this, 1.0D, 80));
        this.i.a(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.i.a(8, new EntityAIWatchClosest(this, EntityGuardian.class, 12.0F, 0.01F));
        this.i.a(9, new EntityAILookIdle(this));
        this.bq.a(3);
        entityaimovetowardsrestriction.a(3);
        this.bg.a(1, new EntityAINearestAttackableTarget(this, EntityLivingBase.class, 10, true, false, new GuardianTargetSelector()));
        this.f = new GuardianMoveHelper();
        this.c = this.b = this.V.nextFloat();

        this.entity = new CanaryGuardian(this); // CanaryMod: wrap entity
    }

    protected void aW() {
        super.aW();
        this.a(SharedMonsterAttributes.e).a(6.0D);
        this.a(SharedMonsterAttributes.d).a(0.5D);
        this.a(SharedMonsterAttributes.b).a(16.0D);
        this.a(SharedMonsterAttributes.a).a(30.0D);
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.a(nbttagcompound.n("Elder"));
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("Elder", this.cl());
    }

    protected PathNavigate b(World world) {
        return new PathNavigateSwimmer(this, world);
    }

    protected void h() {
        super.h();
        this.ac.a(16, Integer.valueOf(0));
        this.ac.a(17, Integer.valueOf(0));
    }

    private boolean a(int i0) {
        return (this.ac.c(16) & i0) != 0;
    }

    private void a(int i0, boolean flag0) {
        int i1 = this.ac.c(16);

        if (flag0) {
            this.ac.b(16, Integer.valueOf(i1 | i0));
        }
        else {
            this.ac.b(16, Integer.valueOf(i1 & ~i0));
        }
    }

    public boolean n() {
        return this.a(2);
    }

    private void l(boolean flag0) {
        this.a(2, flag0);
    }

    public int ck() {
        return this.cl() ? 60 : 80;
    }

    public boolean cl() {
        return this.a(4);
    }

    public void a(boolean flag0) {
        this.a(4, flag0);
        if (flag0) {
            this.a(1.9975F, 1.9975F);
            this.a(SharedMonsterAttributes.d).a(0.30000001192092896D);
            this.a(SharedMonsterAttributes.e).a(8.0D);
            this.a(SharedMonsterAttributes.a).a(80.0D);
            this.bW();
            this.bq.b(400);
        }
    }

    private void b(int i0) {
        this.ac.b(17, Integer.valueOf(i0));
    }

    public boolean cn() {
        return this.ac.c(17) != 0;
    }

    public EntityLivingBase co() {
        if (!this.cn()) {
            return null;
        }
        else if (this.o.D) {
            if (this.bn != null) {
                return this.bn;
            }
            else {
                Entity entity = this.o.a(this.ac.c(17));

                if (entity instanceof EntityLivingBase) {
                    this.bn = (EntityLivingBase)entity;
                    return this.bn;
                }
                else {
                    return null;
                }
            }
        }
        else {
            return this.u();
        }
    }

    public void i(int i0) {
        super.i(i0);
        if (i0 == 16) {
            if (this.cl() && this.J < 1.0F) {
                this.a(1.9975F, 1.9975F);
            }
        }
        else if (i0 == 17) {
            this.bo = 0;
            this.bn = null;
        }
    }

    public int w() {
        return 160;
    }

    protected String z() {
        return !this.V() ? "mob.guardian.land.idle" : (this.cl() ? "mob.guardian.elder.idle" : "mob.guardian.idle");
    }

    protected String bn() {
        return !this.V() ? "mob.guardian.land.hit" : (this.cl() ? "mob.guardian.elder.hit" : "mob.guardian.hit");
    }

    protected String bo() {
        return !this.V() ? "mob.guardian.land.death" : (this.cl() ? "mob.guardian.elder.death" : "mob.guardian.death");
    }

    protected boolean r_() {
        return false;
    }

    public float aR() {
        return this.K * 0.5F;
    }

    public float a(BlockPos blockpos) {
        return this.o.p(blockpos).c().r() == Material.h ? 10.0F + this.o.o(blockpos) - 0.5F : super.a(blockpos);
    }

    public void m() {
        if (this.o.D) {
            this.c = this.b;
            if (!this.V()) {
                this.bk = 2.0F;
                if (this.w > 0.0D && this.bp && !this.R()) {
                    this.o.a(this.s, this.t, this.u, "mob.guardian.flop", 1.0F, 1.0F, false);
                }

                this.bp = this.w < 0.0D && this.o.d((new BlockPos(this)).b(), false);
            }
            else if (this.n()) {
                if (this.bk < 0.5F) {
                    this.bk = 4.0F;
                }
                else {
                    this.bk += (0.5F - this.bk) * 0.1F;
                }
            }
            else {
                this.bk += (0.125F - this.bk) * 0.2F;
            }

            this.b += this.bk;
            this.bm = this.bl;
            if (!this.V()) {
                this.bl = this.V.nextFloat();
            }
            else if (this.n()) {
                this.bl += (0.0F - this.bl) * 0.25F;
            }
            else {
                this.bl += (1.0F - this.bl) * 0.06F;
            }

            if (this.n() && this.V()) {
                Vec3 vec3 = this.d(0.0F);

                for (int i0 = 0; i0 < 2; ++i0) {
                    this.o.a(EnumParticleTypes.WATER_BUBBLE, this.s + (this.V.nextDouble() - 0.5D) * (double)this.J - vec3.a * 1.5D, this.t + this.V.nextDouble() * (double)this.K - vec3.b * 1.5D, this.u + (this.V.nextDouble() - 0.5D) * (double)this.J - vec3.c * 1.5D, 0.0D, 0.0D, 0.0D, new int[0]);
                }
            }

            if (this.cn()) {
                if (this.bo < this.ck()) {
                    ++this.bo;
                }

                EntityLivingBase entitylivingbase = this.co();

                if (entitylivingbase != null) {
                    this.p().a(entitylivingbase, 90.0F, 90.0F);
                    this.p().a();
                    double d0 = (double)this.p(0.0F);
                    double d1 = entitylivingbase.s - this.s;
                    double d2 = entitylivingbase.t + (double)(entitylivingbase.K * 0.5F) - (this.t + (double)this.aR());
                    double d3 = entitylivingbase.u - this.u;
                    double d4 = Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);

                    d1 /= d4;
                    d2 /= d4;
                    d3 /= d4;
                    double d5 = this.V.nextDouble();

                    while (d5 < d4) {
                        d5 += 1.8D - d0 + this.V.nextDouble() * (1.7D - d0);
                        this.o.a(EnumParticleTypes.WATER_BUBBLE, this.s + d1 * d5, this.t + d2 * d5 + (double)this.aR(), this.u + d3 * d5, 0.0D, 0.0D, 0.0D, new int[0]);
                    }
                }
            }
        }

        if (this.Y) {
            this.h(300);
        }
        else if (this.C) {
            this.w += 0.5D;
            this.v += (double)((this.V.nextFloat() * 2.0F - 1.0F) * 0.4F);
            this.x += (double)((this.V.nextFloat() * 2.0F - 1.0F) * 0.4F);
            this.y = this.V.nextFloat() * 360.0F;
            this.C = false;
            this.ai = true;
        }

        if (this.cn()) {
            this.y = this.aI;
        }

        super.m();
    }

    public float p(float f0) {
        return ((float)this.bo + f0) / (float)this.ck();
    }

    protected void E() {
        super.E();
        if (this.cl()) {
            boolean flag0 = true;
            boolean flag1 = true;
            boolean flag2 = true;
            boolean flag3 = true;

            if ((this.W + this.F()) % 1200 == 0) {
                Potion potion = Potion.f;
                List list = this.o.b(EntityPlayerMP.class, new Predicate() {

                                         public boolean a(EntityPlayerMP p_a_1_) {
                                             return EntityGuardian.this.h(p_a_1_) < 2500.0D && p_a_1_.c.c();
                                         }

                                         public boolean apply(Object p_apply_1_) {
                                             return this.a((EntityPlayerMP)p_apply_1_);
                                         }
                                     }
                                    );
                Iterator iterator = list.iterator();

                while (iterator.hasNext()) {
                    EntityPlayerMP entityplayermp = (EntityPlayerMP)iterator.next();

                    if (!entityplayermp.a(potion) || entityplayermp.b(potion).c() < 2 || entityplayermp.b(potion).b() < 1200) {
                        entityplayermp.a.a((Packet)(new S2BPacketChangeGameState(10, 0.0F)));
                        entityplayermp.c(new PotionEffect(potion.H, 6000, 2));
                    }
                }
            }

            if (!this.ci()) {
                this.a(new BlockPos(this), 16);
            }
        }
    }

    protected void b(boolean flag0, int i0) {
        int i1 = this.V.nextInt(3) + this.V.nextInt(i0 + 1);

        if (i1 > 0) {
            this.a(new ItemStack(Items.cC, i1, 0), 1.0F);
        }

        if (this.V.nextInt(3 + i0) > 1) {
            this.a(new ItemStack(Items.aU, 1, ItemFishFood.FishType.COD.a()), 1.0F);
        }
        else if (this.V.nextInt(3 + i0) > 1) {
            this.a(new ItemStack(Items.cD, 1, 0), 1.0F);
        }

        if (flag0 && this.cl()) {
            this.a(new ItemStack(Blocks.v, 1, 1), 1.0F);
        }
    }

    protected void bp() {
        ItemStack itemstack = ((WeightedRandomFishable)WeightedRandom.a(this.V, EntityFishHook.j())).a(this.V);

        this.a(itemstack, 1.0F);
    }

    protected boolean m_() {
        return true;
    }

    public boolean bR() {
        return this.o.a(this.aQ(), (Entity)this) && this.o.a((Entity)this, this.aQ()).isEmpty();
    }

    public boolean bQ() {
        return (this.V.nextInt(20) == 0 || !this.o.j(new BlockPos(this))) && super.bQ();
    }

    public boolean a(DamageSource damagesource, float f0) {
        if (!this.n() && !damagesource.s() && damagesource.i() instanceof EntityLivingBase) {
            EntityLivingBase entitylivingbase = (EntityLivingBase)damagesource.i();

            if (!damagesource.c()) {
                entitylivingbase.a(DamageSource.a((Entity)this), 2.0F);
                entitylivingbase.a("damage.thorns", 0.5F, 1.0F);
            }
        }

        this.bq.f();
        return super.a(damagesource, f0);
    }

    public int bP() {
        return 180;
    }

    public void g(float f0, float f1) {
        if (this.bL()) {
            if (this.V()) {
                this.a(f0, f1, 0.1F);
                this.d(this.v, this.w, this.x);
                this.v *= 0.8999999761581421D;
                this.w *= 0.8999999761581421D;
                this.x *= 0.8999999761581421D;
                if (!this.n() && this.u() == null) {
                    this.w -= 0.005D;
                }
            }
            else {
                super.g(f0, f1);
            }
        }
        else {
            super.g(f0, f1);
        }
    }

    class AIGuardianAttack extends EntityAIBase {

        private EntityGuardian a = EntityGuardian.this;
        private int b;

        public AIGuardianAttack() {
            this.a(3);
        }

        public boolean a() {
            EntityLivingBase entitylivingbase1 = this.a.u();

            return entitylivingbase1 != null && entitylivingbase1.ai();
        }

        public boolean b() {
            return super.b() && (this.a.cl() || this.a.h(this.a.u()) > 9.0D);
        }

        public void c() {
            this.b = -10;
            this.a.s().n();
            this.a.p().a(this.a.u(), 90.0F, 90.0F);
            this.a.ai = true;
        }

        public void d() {
            this.a.b(0);
            this.a.d((EntityLivingBase)null);
            this.a.bq.f();
        }

        public void e() {
            EntityLivingBase entitylivingbase1 = this.a.u();

            this.a.s().n();
            this.a.p().a(entitylivingbase1, 90.0F, 90.0F);
            if (!this.a.t(entitylivingbase1)) {
                this.a.d((EntityLivingBase)null);
            }
            else {
                ++this.b;
                if (this.b == 0) {
                    this.a.b(this.a.u().F());
                    this.a.o.a((Entity)this.a, (byte)21);
                }
                else if (this.b >= this.a.ck()) {
                    float f0 = 1.0F;

                    if (this.a.o.aa() == EnumDifficulty.HARD) {
                        f0 += 2.0F;
                    }

                    if (this.a.cl()) {
                        f0 += 2.0F;
                    }

                    entitylivingbase1.a(DamageSource.b(this.a, this.a), f0);
                    entitylivingbase1.a(DamageSource.a((EntityLivingBase)this.a), (float)this.a.a(SharedMonsterAttributes.e).e());
                    this.a.d((EntityLivingBase)null);
                }
                else if (this.b >= 60 && this.b % 20 == 0) {
                    ;
                }

                super.e();
            }
        }
    }

    class GuardianMoveHelper extends EntityMoveHelper {

        private EntityGuardian g = EntityGuardian.this;

        public GuardianMoveHelper() {
            super(EntityGuardian.this);
        }

        public void c() {
            if (this.f && !this.g.s().m()) {
                double d0 = this.b - this.g.s;
                double d1 = this.c - this.g.t;
                double d2 = this.d - this.g.u;
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;

                d3 = (double)MathHelper.a(d3);
                d1 /= d3;
                float f0 = (float)(Math.atan2(d2, d0) * 180.0D / 3.1415927410125732D) - 90.0F;

                this.g.y = this.a(this.g.y, f0, 30.0F);
                this.g.aG = this.g.y;
                float f1 = (float)(this.e * this.g.a(SharedMonsterAttributes.d).e());

                this.g.j(this.g.bH() + (f1 - this.g.bH()) * 0.125F);
                double d4 = Math.sin((double)(this.g.W + this.g.F()) * 0.5D) * 0.05D;
                double d5 = Math.cos((double)(this.g.y * 3.1415927F / 180.0F));
                double d6 = Math.sin((double)(this.g.y * 3.1415927F / 180.0F));

                this.g.v += d4 * d5;
                this.g.x += d4 * d6;
                d4 = Math.sin((double)(this.g.W + this.g.F()) * 0.75D) * 0.05D;
                this.g.w += d4 * (d6 + d5) * 0.25D;
                this.g.w += (double)this.g.bH() * d1 * 0.1D;
                EntityLookHelper entitylookhelper = this.g.p();
                double d7 = this.g.s + d0 / d3 * 2.0D;
                double d8 = (double)this.g.aR() + this.g.t + d1 / d3 * 1.0D;
                double d9 = this.g.u + d2 / d3 * 2.0D;
                double d10 = entitylookhelper.e();
                double d11 = entitylookhelper.f();
                double d12 = entitylookhelper.g();

                if (!entitylookhelper.b()) {
                    d10 = d7;
                    d11 = d8;
                    d12 = d9;
                }

                this.g.p().a(d10 + (d7 - d10) * 0.125D, d11 + (d8 - d11) * 0.125D, d12 + (d9 - d12) * 0.125D, 10.0F, 40.0F);
                this.g.l(true);
            }
            else {
                this.g.j(0.0F);
                this.g.l(false);
            }
        }
    }

    class GuardianTargetSelector implements Predicate {

        private EntityGuardian a = EntityGuardian.this;

        public boolean a(EntityLivingBase p_a_1_) {
            return (p_a_1_ instanceof EntityPlayer || p_a_1_ instanceof EntitySquid) && p_a_1_.h(this.a) > 9.0D;
        }

        public boolean apply(Object p_apply_1_) {
            return this.a((EntityLivingBase)p_apply_1_);
        }
    }
}
