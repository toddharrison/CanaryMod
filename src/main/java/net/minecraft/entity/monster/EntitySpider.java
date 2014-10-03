package net.minecraft.entity.monster;

import net.canarymod.api.entity.living.monster.CanarySpider;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateClimber;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import java.util.Random;

public class EntitySpider extends EntityMob {

    public EntitySpider(World world) {
        super(world);
        this.a(1.4F, 0.9F);
        this.i.a(1, new EntityAISwimming(this));
        this.i.a(2, this.a);
        this.i.a(3, new EntityAILeapAtTarget(this, 0.4F));
        this.i.a(4, new EntitySpider.AISpiderAttack(EntityPlayer.class));
        this.i.a(4, new EntitySpider.AISpiderAttack(EntityIronGolem.class));
        this.i.a(5, new EntityAIWander(this, 0.8D));
        this.i.a(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.i.a(6, new EntityAILookIdle(this));
        this.bg.a(1, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.bg.a(2, new EntitySpider.AISpiderTarget(EntityPlayer.class));
        this.bg.a(3, new EntitySpider.AISpiderTarget(EntityIronGolem.class));
        this.entity = new CanarySpider(this); // CanaryMod: Wrap Entity
    }

    protected PathNavigate b(World world) {
        return new PathNavigateClimber(this, world);
    }

    protected void h() {
        super.h();
        this.ac.a(16, new Byte((byte) 0));
    }

    public void s_() {
        super.s_();
        if (!this.o.D) {
            this.a(this.D);
        }

    }

    protected void aW() {
        super.aW();
        this.a(SharedMonsterAttributes.a).a(16.0D);
        this.a(SharedMonsterAttributes.d).a(0.30000001192092896D);
    }

    protected String z() {
        return "mob.spider.say";
    }

    protected String bn() {
        return "mob.spider.say";
    }

    protected String bo() {
        return "mob.spider.death";
    }

    protected void a(BlockPos blockpos, Block block) {
        this.a("mob.spider.step", 0.15F, 1.0F);
    }

    protected Item A() {
        return Items.F;
    }

    protected void b(boolean flag0, int i0) {
        super.b(flag0, i0);
        if (flag0 && (this.V.nextInt(3) == 0 || this.V.nextInt(1 + i0) > 0)) {
            this.a(Items.bB, 1);
        }

    }

    public boolean j_() {
        return this.n();
    }

    public void aB() {}

    public EnumCreatureAttribute by() {
        return EnumCreatureAttribute.ARTHROPOD;
    }

    public boolean d(PotionEffect potioneffect) {
        return potioneffect.a() == Potion.u.H ? false : super.d(potioneffect);
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

    public IEntityLivingData a(DifficultyInstance difficultyinstance, IEntityLivingData ientitylivingdata) {
        Object ientitylivingdata1 = super.a(difficultyinstance, ientitylivingdata);

        if (this.o.s.nextInt(100) == 0) {
            EntitySkeleton entityskeleton = new EntitySkeleton(this.o);

            entityskeleton.b(this.s, this.t, this.u, this.y, 0.0F);
            entityskeleton.a(difficultyinstance, (IEntityLivingData) null);
            this.o.d((Entity) entityskeleton);
            entityskeleton.a((Entity) this);
        }

        if (ientitylivingdata1 == null) {
            ientitylivingdata1 = new EntitySpider.GroupData();
            if (this.o.aa() == EnumDifficulty.HARD && this.o.s.nextFloat() < 0.1F * difficultyinstance.c()) {
                ((EntitySpider.GroupData) ientitylivingdata1).a(this.o.s);
            }
        }

        if (ientitylivingdata1 instanceof EntitySpider.GroupData) {
            int i0 = ((EntitySpider.GroupData) ientitylivingdata1).a;

            if (i0 > 0 && Potion.a[i0] != null) {
                this.c(new PotionEffect(i0, Integer.MAX_VALUE));
            }
        }

        return (IEntityLivingData) ientitylivingdata1;
    }

    public float aR() {
        return 0.65F;
    }

    class AISpiderAttack extends EntityAIAttackOnCollide {

        public AISpiderAttack(Class p_i45819_2_) {
            super(EntitySpider.this, p_i45819_2_, 1.0D, true);
        }

        public boolean b() {
            float f0 = this.b.c(1.0F);

            if (f0 >= 0.5F && this.b.bb().nextInt(100) == 0) {
                this.b.d((EntityLivingBase) null);
                return false;
            } else {
                return super.b();
            }
        }

        protected double a(EntityLivingBase p_a_1_) {
            return (double) (4.0F + p_a_1_.J);
        }
    }


    class AISpiderTarget extends EntityAINearestAttackableTarget {

        public AISpiderTarget(Class p_i45818_2_) {
            super(EntitySpider.this, p_i45818_2_, true);
        }

        public boolean a() {
            float f0 = this.e.c(1.0F);

            return f0 >= 0.5F ? false : super.a();
        }
    }


    public static class GroupData implements IEntityLivingData {

        public int a;
      
        public void a(Random p_a_1_) {
            int i0 = p_a_1_.nextInt(5);

            if (i0 <= 1) {
                this.a = Potion.c.H;
            } else if (i0 <= 2) {
                this.a = Potion.g.H;
            } else if (i0 <= 3) {
                this.a = Potion.l.H;
            } else if (i0 <= 4) {
                this.a = Potion.p.H;
            }

        }
    }
}
