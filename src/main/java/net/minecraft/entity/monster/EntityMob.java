package net.minecraft.entity.monster;

import com.google.common.base.Predicate;
import net.canarymod.api.entity.EntityType;
import net.canarymod.api.entity.living.monster.CanaryEntityMob;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public abstract class EntityMob extends EntityCreature implements IMob {

    protected final EntityAIBase a = new EntityAIAvoidEntity(this, new Predicate() {

        public boolean a(Entity p_a_1_) {
            return p_a_1_ instanceof EntityCreeper && ((EntityCreeper) p_a_1_).ck() > 0;
        }

        public boolean apply(Object p_apply_1_) {
            return this.a((Entity) p_apply_1_);
        }
    }, 4.0F, 1.0D, 2.0D);

    public EntityMob(World world) {
        super(world);
        this.b_ = 5;
    }

    public void m() {
        this.bw();
        float f0 = this.c(1.0F);

        if (f0 > 0.5F) {
            this.aO += 2;
        }

        super.m();
    }

    public void s_() {
        super.s_();
        if (!this.o.D && this.o.aa() == EnumDifficulty.PEACEFUL) {
            this.J();
        }

    }

    protected String P() {
        return "game.hostile.swim";
    }

    protected String aa() {
        return "game.hostile.swim.splash";
    }

    public boolean a(DamageSource damagesource, float f0) {
        if (this.b(damagesource)) {
            return false;
        } else if (super.a(damagesource, f0)) {
            Entity entity = damagesource.j();

            return this.l != entity && this.m != entity ? true : true;
        } else {
            return false;
        }
    }

    protected String bn() {
        return "game.hostile.hurt";
    }

    protected String bo() {
        return "game.hostile.die";
    }

    protected String n(int i0) {
        return i0 > 4 ? "game.hostile.hurt.fall.big" : "game.hostile.hurt.fall.small";
    }

    public boolean r(Entity entity) {
        float f0 = (float) this.a(SharedMonsterAttributes.e).e();
        int i0 = 0;

        if (entity instanceof EntityLivingBase) {
            f0 += EnchantmentHelper.a(this.bz(), ((EntityLivingBase) entity).by());
            i0 += EnchantmentHelper.a((EntityLivingBase) this);
        }

        boolean flag0 = entity.a(DamageSource.a((EntityLivingBase) this), f0);

        if (flag0) {
            if (i0 > 0) {
                entity.g((double) (-MathHelper.a(this.y * 3.1415927F / 180.0F) * (float) i0 * 0.5F), 0.1D, (double) (MathHelper.b(this.y * 3.1415927F / 180.0F) * (float) i0 * 0.5F));
                this.v *= 0.6D;
                this.x *= 0.6D;
            }

            int i1 = EnchantmentHelper.b((EntityLivingBase) this);

            if (i1 > 0) {
                entity.e(i1 * 4);
            }

            this.a(this, entity);
        }

        return flag0;
    }

    public float a(BlockPos blockpos) {
        return 0.5F - this.o.o(blockpos);
    }

    protected boolean m_() {
        BlockPos blockpos = new BlockPos(this.s, this.aQ().b, this.u);

        if (this.o.b(EnumSkyBlock.SKY, blockpos) > this.V.nextInt(32)) {
            return false;
        }
        else {
            int i0 = this.o.l(blockpos);

            if (this.o.R()) {
                int i1 = this.o.ab();

                this.o.b(10);
                i0 = this.o.l(blockpos);
                this.o.b(i1);
            }

            return i0 <= this.V.nextInt(8);
        }
    }

    public boolean bQ() {
        return this.o.aa() != EnumDifficulty.PEACEFUL && this.m_() && super.bQ();
    }

    protected void aW() {
        super.aW();
        this.bx().b(SharedMonsterAttributes.e);
    }

    protected boolean aZ() {
        return true;
    }

    // CanaryMod
    @Override
    public CanaryEntityMob getCanaryEntity() {
        if (this.entity == null || !(this.entity instanceof CanaryEntityMob)) {
            // Set the proper wrapper as needed
            this.entity =
                    new CanaryEntityMob(this) {
                        @Override
                        public String getFqName() {
                            return "GenericMob[" + getClass().getSimpleName() + "]";
                        }

                        @Override
                        public EntityType getEntityType() {
                            return EntityType.GENERIC_MOB;
                        }

                        @Override
                        public EntityMob getHandle() {
                            return (EntityMob)entity;
                        }
                    };
        }
        return (CanaryEntityMob)entity;
    }
}
