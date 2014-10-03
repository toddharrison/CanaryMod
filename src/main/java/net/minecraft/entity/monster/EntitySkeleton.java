package net.minecraft.entity.monster;

import net.canarymod.api.entity.living.monster.CanarySkeleton;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIRestrictSun;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderHell;

import java.util.Calendar;

public class EntitySkeleton extends EntityMob implements IRangedAttackMob {

    private EntityAIArrowAttack b = new EntityAIArrowAttack(this, 1.0D, 20, 60, 15.0F);
    private EntityAIAttackOnCollide c = new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.2D, false);
   
    public EntitySkeleton(World world) {
        super(world);
        this.i.a(1, new EntityAISwimming(this));
        this.i.a(2, new EntityAIRestrictSun(this));
        this.i.a(2, this.a);
        this.i.a(3, new EntityAIFleeSun(this, 1.0D));
        this.i.a(3, new EntityAIAvoidEntity(this, new Predicate() {

            public boolean a(Entity p_a_1_) {
                return p_a_1_ instanceof EntityWolf;
            }

            public boolean apply(Object p_apply_1_) {
                return this.a((Entity) p_apply_1_);
            }
        }, 6.0F, 1.0D, 1.2D));
        this.i.a(4, new EntityAIWander(this, 1.0D));
        this.i.a(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.i.a(6, new EntityAILookIdle(this));
        this.bg.a(1, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.bg.a(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        this.bg.a(3, new EntityAINearestAttackableTarget(this, EntityIronGolem.class, true));
        if (world != null && !world.D) {
            this.n();
        }
        this.entity = new CanarySkeleton(this); // CanaryMod: Wrap Entity
    }

    protected void aW() {
        super.aW();
        this.a(SharedMonsterAttributes.d).a(0.25D);
    }

    protected void h() {
        super.h();
        this.ac.a(13, new Byte((byte) 0));
    }

    protected String z() {
        return "mob.skeleton.say";
    }

    protected String bn() {
        return "mob.skeleton.hurt";
    }

    protected String bo() {
        return "mob.skeleton.death";
    }

    protected void a(BlockPos blockpos, Block block) {
        this.a("mob.skeleton.step", 0.15F, 1.0F);
    }

    public boolean r(Entity entity) {
        if (super.r(entity)) {
            if (this.ck() == 1 && entity instanceof EntityLivingBase) {
                ((EntityLivingBase) entity).c(new PotionEffect(Potion.v.H, 200));
            }

            return true;
        } else {
            return false;
        }
    }

    public EnumCreatureAttribute by() {
        return EnumCreatureAttribute.UNDEAD;
    }

    public void m() {
        if (this.o.w() && !this.o.D) {
            float f0 = this.c(1.0F);
            BlockPos blockpos = new BlockPos(this.s, (double) Math.round(this.t), this.u);

            if (f0 > 0.5F && this.V.nextFloat() * 30.0F < (f0 - 0.4F) * 2.0F && this.o.i(blockpos)) {
                boolean flag0 = true;
                ItemStack itemstack = this.p(4);

                if (itemstack != null) {
                    if (itemstack.e()) {
                        itemstack.b(itemstack.h() + this.V.nextInt(2));
                        if (itemstack.h() >= itemstack.j()) {
                            this.b(itemstack);
                            this.c(4, (ItemStack) null);
                        }
                    }

                    flag0 = false;
                }

                if (flag0) {
                    this.e(8);
                }
            }
        }

        if (this.o.D && this.ck() == 1) {
            this.a(0.72F, 2.535F);
        }

        super.m();
    }

    public void ak() {
        super.ak();
        if (this.m instanceof EntityCreature) {
            EntityCreature entitycreature = (EntityCreature) this.m;

            this.aG = entitycreature.aG;
        }

    }

    public void a(DamageSource damagesource) {
        super.a(damagesource);
        if (damagesource.i() instanceof EntityArrow && damagesource.j() instanceof EntityPlayer) {
            EntityPlayer entityplayer = (EntityPlayer) damagesource.j();
            double d0 = entityplayer.s - this.s;
            double d1 = entityplayer.u - this.u;

            if (d0 * d0 + d1 * d1 >= 2500.0D) {
                entityplayer.b((StatBase) AchievementList.v);
            }
        } else if (damagesource.j() instanceof EntityCreeper && ((EntityCreeper) damagesource.j()).n() && ((EntityCreeper) damagesource.j()).cn()) {
            ((EntityCreeper) damagesource.j()).co();
            this.a(new ItemStack(Items.bX, 1, this.ck() == 1 ? 1 : 0), 0.0F);
        }

    }

    protected Item A() {
        return Items.g;
    }

    protected void b(boolean flag0, int i0) {
        int i1;
        int i2;

        if (this.ck() == 1) {
            i1 = this.V.nextInt(3 + i0) - 1;

            for (i2 = 0; i2 < i1; ++i2) {
                this.a(Items.h, 1);
            }
        } else {
            i1 = this.V.nextInt(3 + i0);

            for (i2 = 0; i2 < i1; ++i2) {
                this.a(Items.g, 1);
            }
        }

        i1 = this.V.nextInt(3 + i0);

        for (i2 = 0; i2 < i1; ++i2) {
            this.a(Items.aX, 1);
        }

    }

    protected void bp() {
        if (this.ck() == 1) {
            this.a(new ItemStack(Items.bX, 1, 1), 0.0F);
        }

    }

    protected void a(DifficultyInstance difficultyinstance) {
        super.a(difficultyinstance);
        this.c(0, new ItemStack(Items.f));
    }

    public IEntityLivingData a(DifficultyInstance difficultyinstance, IEntityLivingData ientitylivingdata) {
        ientitylivingdata = super.a(difficultyinstance, ientitylivingdata);
        if (this.o.t instanceof WorldProviderHell && this.bb().nextInt(5) > 0) {
            this.i.a(4, this.c);
            this.a(1);
            this.c(0, new ItemStack(Items.q));
            this.a(SharedMonsterAttributes.e).a(4.0D);
        } else {
            this.i.a(4, this.b);
            this.a(difficultyinstance);
            this.b(difficultyinstance);
        }

        this.j(this.V.nextFloat() < 0.55F * difficultyinstance.c());
        if (this.p(4) == null) {
            Calendar calendar = this.o.Y();

            if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && this.V.nextFloat() < 0.25F) {
                this.c(4, new ItemStack(this.V.nextFloat() < 0.1F ? Blocks.aZ : Blocks.aU));
                this.bh[4] = 0.0F;
            }
        }

        return ientitylivingdata;
    }

    public void n() {
        this.i.a((EntityAIBase) this.c);
        this.i.a((EntityAIBase) this.b);
        ItemStack itemstack = this.bz();

        if (itemstack != null && itemstack.b() == Items.f) {
            this.i.a(4, this.b);
        } else {
            this.i.a(4, this.c);
        }

    }

    public void a(EntityLivingBase entitylivingbase, float f0) {
        EntityArrow entityarrow = new EntityArrow(this.o, this, entitylivingbase, 1.6F, (float) (14 - this.o.aa().a() * 4));
        int i0 = EnchantmentHelper.a(Enchantment.v.B, this.bz());
        int i1 = EnchantmentHelper.a(Enchantment.w.B, this.bz());

        entityarrow.b((double) (f0 * 2.0F) + this.V.nextGaussian() * 0.25D + (double) ((float) this.o.aa().a() * 0.11F));
        if (i0 > 0) {
            entityarrow.b(entityarrow.j() + (double) i0 * 0.5D + 0.5D);
        }

        if (i1 > 0) {
            entityarrow.a(i1);
        }

        if (EnchantmentHelper.a(Enchantment.x.B, this.bz()) > 0 || this.ck() == 1) {
            entityarrow.e(100);
        }

        this.a("random.bow", 1.0F, 1.0F / (this.bb().nextFloat() * 0.4F + 0.8F));
        this.o.d((Entity) entityarrow);
    }

    public int ck() {
        return this.ac.a(13);
    }

    public void a(int i0) {
        this.ac.b(13, Byte.valueOf((byte) i0));
        this.ab = i0 == 1;
        if (i0 == 1) {
            this.a(0.72F, 2.535F);
        } else {
            this.a(0.6F, 1.95F);
        }

    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        if (nbttagcompound.b("SkeletonType", 99)) {
            byte b0 = nbttagcompound.d("SkeletonType");

            this.a(b0);
        }

        this.n();
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("SkeletonType", (byte) this.ck());
    }

    public void c(int i0, ItemStack itemstack) {
        super.c(i0, itemstack);
        if (!this.o.D && i0 == 0) {
            this.n();
        }

    }

    public float aR() {
        return this.ck() == 1 ? super.aR() : 1.74F;
    }

    public double am() {
        return super.am() - 0.5D;
    }
}
