package net.minecraft.entity.monster;

import net.canarymod.api.entity.living.monster.CanarySilverfish;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSilverfish;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;


public class EntitySilverfish extends EntityMob {

    private EntitySilverfish.AISummonSilverfish b;
   
    public EntitySilverfish(World world) {
        super(world);
        this.a(0.4F, 0.3F);
        this.i.a(1, new EntityAISwimming(this));
        this.i.a(3, this.b = new EntitySilverfish.AISummonSilverfish());
        this.i.a(4, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
        this.i.a(5, new EntitySilverfish.AIHideInStone());
        this.bg.a(1, new EntityAIHurtByTarget(this, true, new Class[0]));
        this.bg.a(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        this.entity = new CanarySilverfish(this); // CanaryMod: Wrap Entity
    }

    public float aR() {
        return 0.1F;
    }

    protected void aW() {
        super.aW();
        this.a(SharedMonsterAttributes.a).a(8.0D);
        this.a(SharedMonsterAttributes.d).a(0.25D);
        this.a(SharedMonsterAttributes.e).a(1.0D);
    }

    protected boolean r_() {
        return false;
    }

    protected String z() {
        return "mob.silverfish.say";
    }

    protected String bn() {
        return "mob.silverfish.hit";
    }

    protected String bo() {
        return "mob.silverfish.kill";
    }

    public boolean a(DamageSource damagesource, float f0) {
        if (this.b(damagesource)) {
            return false;
        } else {
            if (damagesource instanceof EntityDamageSource || damagesource == DamageSource.l) {
                this.b.f();
            }

            return super.a(damagesource, f0);
        }
    }

    protected void a(BlockPos blockpos, Block block) {
        this.a("mob.silverfish.step", 0.15F, 1.0F);
    }

    protected Item A() {
        return null;
    }

    public void s_() {
        this.aG = this.y;
        super.s_();
    }

    public float a(BlockPos blockpos) {
        return this.o.p(blockpos.b()).c() == Blocks.b ? 10.0F : super.a(blockpos);
    }

    protected boolean m_() {
        return true;
    }

    public boolean bQ() {
        if (super.bQ()) {
            EntityPlayer entityplayer = this.o.a(this, 5.0D);

            return entityplayer == null;
        } else {
            return false;
        }
    }

    public EnumCreatureAttribute by() {
        return EnumCreatureAttribute.ARTHROPOD;
    }

    class AIHideInStone extends EntityAIWander {

        private EnumFacing b;
        private boolean c;
      
        public AIHideInStone() {
            super(EntitySilverfish.this, 1.0D, 10);
            this.a(1);
        }

        public boolean a() {
            if (EntitySilverfish.this.u() != null) {
                return false;
            } else if (!EntitySilverfish.this.s().m()) {
                return false;
            } else {
                Random world = EntitySilverfish.this.bb();

                if (world.nextInt(10) == 0) {
                    this.b = EnumFacing.a(world);
                    BlockPos blockpos1 = (new BlockPos(EntitySilverfish.this.s, EntitySilverfish.this.t + 0.5D, EntitySilverfish.this.u)).a(this.b);
                    IBlockState iblockstate1 = EntitySilverfish.this.o.p(blockpos1);

                    if (BlockSilverfish.d(iblockstate1)) {
                        this.c = true;
                        return true;
                    }
                }

                this.c = false;
                return super.a();
            }
        }

        public boolean b() {
            return this.c ? false : super.b();
        }

        public void c() {
            if (!this.c) {
                super.c();
            } else {
                World world = EntitySilverfish.this.o;
                BlockPos blockpos1 = (new BlockPos(EntitySilverfish.this.s, EntitySilverfish.this.t + 0.5D, EntitySilverfish.this.u)).a(this.b);
                IBlockState iblockstate1 = world.p(blockpos1);

                if (BlockSilverfish.d(iblockstate1)) {
                    world.a(blockpos1, Blocks.be.P().a(BlockSilverfish.a, BlockSilverfish.EnumType.a(iblockstate1)), 3);
                    EntitySilverfish.this.y();
                    EntitySilverfish.this.J();
                }

            }
        }
    }


    class AISummonSilverfish extends EntityAIBase {

        private EntitySilverfish a = EntitySilverfish.this;
        private int b;
      
        public void f() {
            if (this.b == 0) {
                this.b = 20;
            }

        }

        public boolean a() {
            return this.b > 0;
        }

        public void e() {
            --this.b;
            if (this.b <= 0) {
                World world = this.a.o;
                Random random = this.a.bb();
                BlockPos blockpos = new BlockPos(this.a);

                for (int i0 = 0; i0 <= 5 && i0 >= -5; i0 = i0 <= 0 ? 1 - i0 : 0 - i0) {
                    for (int i1 = 0; i1 <= 10 && i1 >= -10; i1 = i1 <= 0 ? 1 - i1 : 0 - i1) {
                        for (int i2 = 0; i2 <= 10 && i2 >= -10; i2 = i2 <= 0 ? 1 - i2 : 0 - i2) {
                            BlockPos blockpos1 = blockpos.a(i1, i0, i2);
                            IBlockState iblockstate = world.p(blockpos1);

                            if (iblockstate.c() == Blocks.be) {
                                if (world.Q().b("mobGriefing")) {
                                    world.b(blockpos1, true);
                                } else {
                                    world.a(blockpos1, ((BlockSilverfish.EnumType) iblockstate.b(BlockSilverfish.a)).d(), 3);
                                }

                                if (random.nextBoolean()) {
                                    return;
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}
