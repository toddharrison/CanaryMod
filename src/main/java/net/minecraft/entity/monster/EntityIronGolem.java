package net.minecraft.entity.monster;

import com.google.common.base.Predicate;
import net.canarymod.api.entity.living.CanaryIronGolem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.village.Village;
import net.minecraft.world.World;


public class EntityIronGolem extends EntityGolem {

    public Village a; // CanaryMod package -> public
    private int b;
    private int c;
    private int bk;

    public EntityIronGolem(World world) {
        super(world);
        this.a(1.4F, 2.9F);
        ((PathNavigateGround) this.s()).a(true);
        this.i.a(1, new EntityAIAttackOnCollide(this, 1.0D, true));
        this.i.a(2, new EntityAIMoveTowardsTarget(this, 0.9D, 32.0F));
        this.i.a(3, new EntityAIMoveThroughVillage(this, 0.6D, true));
        this.i.a(4, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.i.a(5, new EntityAILookAtVillager(this));
        this.i.a(6, new EntityAIWander(this, 0.6D));
        this.i.a(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.i.a(8, new EntityAILookIdle(this));
        this.bg.a(1, new EntityAIDefendVillage(this));
        this.bg.a(2, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.bg.a(3, new EntityIronGolem.AINearestAttackableTargetNonCreeper(this, EntityLiving.class, 10, false, true, IMob.e));
        this.entity = new CanaryIronGolem(this); // CanaryMod: Warp Entity
    }

    protected void h() {
        super.h();
        this.ac.a(16, Byte.valueOf((byte) 0));
    }

    protected void E() {
        if (--this.b <= 0) {
            this.b = 70 + this.V.nextInt(50);
            this.a = this.o.ae().a(new BlockPos(this), 32);
            if (this.a == null) {
                this.ch();
            }
            else {
                BlockPos blockpos = this.a.a();

                this.a(blockpos, (int) ((float) this.a.b() * 0.6F));
            }
        }

        super.E();
    }

    protected void aW() {
        super.aW();
        this.a(SharedMonsterAttributes.a).a(100.0D);
        this.a(SharedMonsterAttributes.d).a(0.25D);
    }

    protected int j(int i0) {
        return i0;
    }

    protected void s(Entity entity) {
        if (entity instanceof IMob && this.bb().nextInt(20) == 0) {
            this.d((EntityLivingBase) entity);
        }

        super.s(entity);
    }

    public void m() {
        super.m();
        if (this.c > 0) {
            --this.c;
        }

        if (this.bk > 0) {
            --this.bk;
        }

        if (this.v * this.v + this.x * this.x > 2.500000277905201E-7D && this.V.nextInt(5) == 0) {
            int i0 = MathHelper.c(this.s);
            int i1 = MathHelper.c(this.t - 0.20000000298023224D);
            int i2 = MathHelper.c(this.u);
            IBlockState iblockstate = this.o.p(new BlockPos(i0, i1, i2));
            Block block = iblockstate.c();

            if (block.r() != Material.a) {
                this.o.a(EnumParticleTypes.BLOCK_CRACK, this.s + ((double) this.V.nextFloat() - 0.5D) * (double) this.J, this.aQ().b + 0.1D, this.u + ((double) this.V.nextFloat() - 0.5D) * (double) this.J, 4.0D * ((double) this.V.nextFloat() - 0.5D), 0.5D, ((double) this.V.nextFloat() - 0.5D) * 4.0D, new int[]{Block.f(iblockstate)});
            }
        }

    }

    public boolean a(Class oclass0) {
        return this.cl() && EntityPlayer.class.isAssignableFrom(oclass0) ? false : super.a(oclass0);
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("PlayerCreated", this.cl());
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.l(nbttagcompound.n("PlayerCreated"));
    }

    public boolean r(Entity entity) {
        this.c = 10;
        this.o.a((Entity) this, (byte) 4);
        boolean flag0 = entity.a(DamageSource.a((EntityLivingBase) this), (float) (7 + this.V.nextInt(15)));

        if (flag0) {
            entity.w += 0.4000000059604645D;
            this.a(this, entity);
        }

        this.a("mob.irongolem.throw", 1.0F, 1.0F);
        return flag0;
    }

    public Village n() {
        return this.a;
    }

    public void a(boolean flag0) {
        this.bk = flag0 ? 400 : 0;
        this.o.a((Entity) this, (byte) 11);
    }

    protected String bn() {
        return "mob.irongolem.hit";
    }

    protected String bo() {
        return "mob.irongolem.death";
    }

    protected void a(BlockPos blockpos, Block block) {
        this.a("mob.irongolem.walk", 1.0F, 1.0F);
    }

    protected void b(boolean flag0, int i0) {
        int i1 = this.V.nextInt(3);

        int i2;

        for (i2 = 0; i2 < i1; ++i2) {
            this.a(Item.a((Block) Blocks.O), 1, (float) BlockFlower.EnumFlowerType.POPPY.b());
        }

        i2 = 3 + this.V.nextInt(3);

        for (int i3 = 0; i3 < i2; ++i3) {
            this.a(Items.j, 1);
        }

    }

    public int ck() {
        return this.bk;
    }

    public boolean cl() {
        return (this.ac.a(16) & 1) != 0;
    }

    public void l(boolean flag0) {
        byte b0 = this.ac.a(16);

        if (flag0) {
            this.ac.b(16, Byte.valueOf((byte) (b0 | 1)));
        }
        else {
            this.ac.b(16, Byte.valueOf((byte) (b0 & -2)));
        }

    }

    public void a(DamageSource damagesource) {
        if (!this.cl() && this.aL != null && this.a != null) {
            this.a.a(this.aL.d_(), -5);
        }

        super.a(damagesource);
    }

    static class AINearestAttackableTargetNonCreeper extends EntityAINearestAttackableTarget {

        public AINearestAttackableTargetNonCreeper(final EntityCreature p_i45858_1_, Class p_i45858_2_, int p_i45858_3_, boolean p_i45858_4_, boolean p_i45858_5_, final Predicate p_i45858_6_) {
            super(p_i45858_1_, p_i45858_2_, p_i45858_3_, p_i45858_4_, p_i45858_5_, p_i45858_6_);
            this.c = new Predicate() {

                public boolean a(EntityLivingBase p_a_1_) {
                    if (p_i45858_6_ != null && !p_i45858_6_.apply(p_a_1_)) {
                        return false;
                    }
                    else if (p_a_1_ instanceof EntityCreeper) {
                        return false;
                    }
                    else {
                        if (p_a_1_ instanceof EntityPlayer) {
                            double d0 = AINearestAttackableTargetNonCreeper.this.f();

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

                            if ((double) p_a_1_.g(p_i45858_1_) > d0) {
                                return false;
                            }
                        }

                        return AINearestAttackableTargetNonCreeper.this.a(p_a_1_, false);
                    }
                }

                public boolean apply(Object p_apply_1_) {
                    return this.a((EntityLivingBase) p_apply_1_);
                }
            };
        }
    }

    // CanaryMod
    public void setRoseTicks(int ticks) {
        this.bk = ticks;
    }
    //
}
