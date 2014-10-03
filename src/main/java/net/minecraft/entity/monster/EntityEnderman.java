package net.minecraft.entity.monster;

import net.canarymod.api.entity.living.monster.CanaryEnderman;
import net.canarymod.config.Configuration;
import net.canarymod.hook.entity.EndermanDropBlockHook;
import net.canarymod.hook.entity.EndermanPickupBlockHook;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import com.google.common.base.Predicate;
import com.google.common.collect.Sets;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

public class EntityEnderman extends EntityMob {

    private static final UUID b = UUID.fromString("020E0DFB-87AE-4653-9556-831010E291A0");
    private static final AttributeModifier c = (new AttributeModifier(b, "Attacking speed boost", 0.15000000596046448D, 0)).a(false);
    private static final Set bk = Sets.newIdentityHashSet();
    private boolean bl;
   
    public EntityEnderman(World world) {
        super(world);
        this.a(0.6F, 2.9F);
        this.S = 1.0F;
        this.i.a(0, new EntityAISwimming(this));
        this.i.a(2, new EntityAIAttackOnCollide(this, 1.0D, false));
        this.i.a(7, new EntityAIWander(this, 1.0D));
        this.i.a(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.i.a(8, new EntityAILookIdle(this));
        this.i.a(10, new EntityEnderman.AIPlaceBlock());
        this.i.a(11, new EntityEnderman.AITakeBlock());
        this.bg.a(1, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.bg.a(2, new EntityEnderman.AIFindPlayer());
        this.bg.a(3, new EntityAINearestAttackableTarget(this, EntityEndermite.class, 10, true, false, new Predicate() {

            public boolean a(EntityEndermite p_a_1_) {
                return p_a_1_.n();
            }

            public boolean apply(Object p_apply_1_) {
                return this.a((EntityEndermite) p_apply_1_);
            }
        }));
        this.entity = new CanaryEnderman(this); // CanaryMod: Wrap Entity
    }

    protected void aW() {
        super.aW();
        this.a(SharedMonsterAttributes.a).a(40.0D);
        this.a(SharedMonsterAttributes.d).a(0.30000001192092896D);
        this.a(SharedMonsterAttributes.e).a(7.0D);
        this.a(SharedMonsterAttributes.b).a(64.0D);
    }

    protected void h() {
        super.h();
        this.ac.a(16, new Short((short) 0));
        this.ac.a(17, new Byte((byte) 0));
        this.ac.a(18, new Byte((byte) 0));
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        IBlockState iblockstate = this.ck();

        nbttagcompound.a("carried", (short) Block.a(iblockstate.c()));
        nbttagcompound.a("carriedData", (short) iblockstate.c().c(iblockstate));
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        IBlockState iblockstate;

        if (nbttagcompound.b("carried", 8)) {
            iblockstate = Block.b(nbttagcompound.j("carried")).a(nbttagcompound.e("carriedData") & '\uffff');
        } else {
            iblockstate = Block.c(nbttagcompound.e("carried")).a(nbttagcompound.e("carriedData") & '\uffff');
        }

        this.a(iblockstate);
    }

    private boolean c(EntityPlayer entityplayer) {
        ItemStack itemstack = entityplayer.bg.b[3];

        if (itemstack != null && itemstack.b() == Item.a(Blocks.aU)) {
            return false;
        } else {
            Vec3 vec3 = entityplayer.d(1.0F).a();
            Vec3 vec31 = new Vec3(this.s - entityplayer.s, this.aQ().b + (double) (this.K / 2.0F) - (entityplayer.t + (double) entityplayer.aR()), this.u - entityplayer.u);
            double d0 = vec31.b();

            vec31 = vec31.a();
            double d1 = vec3.b(vec31);

            return d1 > 1.0D - 0.025D / d0 ? entityplayer.t(this) : false;
        }
    }

    public float aR() {
        return 2.55F;
    }

    public void m() {
        if (this.o.D) {
            for (int i0 = 0; i0 < 2; ++i0) {
                this.o.a(EnumParticleTypes.PORTAL, this.s + (this.V.nextDouble() - 0.5D) * (double) this.J, this.t + this.V.nextDouble() * (double) this.K - 0.25D, this.u + (this.V.nextDouble() - 0.5D) * (double) this.J, (this.V.nextDouble() - 0.5D) * 2.0D, -this.V.nextDouble(), (this.V.nextDouble() - 0.5D) * 2.0D, new int[0]);
            }
        }

        this.aW = false;
        super.m();
    }

    protected void E() {
        if (this.U()) {
            this.a(DamageSource.f, 1.0F);
        }

        if (this.cm() && !this.bl && this.V.nextInt(100) == 0) {
            this.a(false);
        }

        if (this.o.w()) {
            float f0 = this.c(1.0F);

            if (f0 > 0.5F && this.o.i(new BlockPos(this)) && this.V.nextFloat() * 30.0F < (f0 - 0.4F) * 2.0F) {
                this.d((EntityLivingBase) null);
                this.a(false);
                this.bl = false;
                this.n();
            }
        }

        super.E();
    }

    public boolean n() { // CanaryMod: protected -> public
        double d0 = this.s + (this.V.nextDouble() - 0.5D) * 64.0D;
        double d1 = this.t + (double) (this.V.nextInt(64) - 32);
        double d2 = this.u + (this.V.nextDouble() - 0.5D) * 64.0D;

        return this.k(d0, d1, d2);
    }

    protected boolean b(Entity entity) {
        Vec3 vec3 = new Vec3(this.s - entity.s, this.aQ().b + (double) (this.K / 2.0F) - entity.t + (double) entity.aR(), this.u - entity.u);

        vec3 = vec3.a();
        double d0 = 16.0D;
        double d1 = this.s + (this.V.nextDouble() - 0.5D) * 8.0D - vec3.a * d0;
        double d2 = this.t + (double) (this.V.nextInt(16) - 8) - vec3.b * d0;
        double d3 = this.u + (this.V.nextDouble() - 0.5D) * 8.0D - vec3.c * d0;

        return this.k(d1, d2, d3);
    }

    protected boolean k(double d0, double d1, double d2) {
        double d3 = this.s;
        double d4 = this.t;
        double d5 = this.u;

        this.s = d0;
        this.t = d1;
        this.u = d2;
        boolean flag0 = false;
        BlockPos blockpos = new BlockPos(this.s, this.t, this.u);

        if (this.o.e(blockpos)) {
            boolean flag1 = false;

            while (!flag1 && blockpos.o() > 0) {
                BlockPos blockpos1 = blockpos.b();
                Block block = this.o.p(blockpos1).c();

                if (block.r().c()) {
                    flag1 = true;
                } else {
                    --this.t;
                    blockpos = blockpos1;
                }
            }

            if (flag1) {
                super.a(this.s, this.t, this.u);
                if (this.o.a((Entity) this, this.aQ()).isEmpty() && !this.o.d(this.aQ())) {
                    flag0 = true;
                }
            }
        }

        if (!flag0) {
            this.b(d3, d4, d5);
            return false;
        } else {
            short short1 = 128;

            for (int i0 = 0; i0 < short1; ++i0) {
                double d6 = (double) i0 / ((double) short1 - 1.0D);
                float f0 = (this.V.nextFloat() - 0.5F) * 0.2F;
                float f1 = (this.V.nextFloat() - 0.5F) * 0.2F;
                float f2 = (this.V.nextFloat() - 0.5F) * 0.2F;
                double d7 = d3 + (this.s - d3) * d6 + (this.V.nextDouble() - 0.5D) * (double) this.J * 2.0D;
                double d8 = d4 + (this.t - d4) * d6 + this.V.nextDouble() * (double) this.K;
                double d9 = d5 + (this.u - d5) * d6 + (this.V.nextDouble() - 0.5D) * (double) this.J * 2.0D;

                this.o.a(EnumParticleTypes.PORTAL, d7, d8, d9, (double) f0, (double) f1, (double) f2, new int[0]);
            }

            this.o.a(d3, d4, d5, "mob.endermen.portal", 1.0F, 1.0F);
            this.a("mob.endermen.portal", 1.0F, 1.0F);
            return true;
        }
    }

    protected String z() {
        return this.cm() ? "mob.endermen.scream" : "mob.endermen.idle";
    }

    protected String bn() {
        return "mob.endermen.hit";
    }

    protected String bo() {
        return "mob.endermen.death";
    }

    protected Item A() {
        return Items.bu;
    }

    protected void b(boolean flag0, int i0) {
        Item item = this.A();

        if (item != null) {
            int i1 = this.V.nextInt(2 + i0);

            for (int i2 = 0; i2 < i1; ++i2) {
                this.a(item, 1);
            }
        }
    }

    public void a(IBlockState iblockstate) {
        this.ac.b(16, Short.valueOf((short) (Block.f(iblockstate) & '\uffff')));
    }

    public IBlockState ck() {
        return Block.d(this.ac.b(16) & '\uffff');
    }

    public boolean a(DamageSource damagesource, float f0) {
        if (this.b(damagesource)) {
            return false;
        } else {
            if (damagesource.j() == null || !(damagesource.j() instanceof EntityEndermite)) {
                if (!this.o.D) {
                    this.a(true);
                }

                if (damagesource instanceof EntityDamageSource && damagesource.j() instanceof EntityPlayer) {
                    if (damagesource.j() instanceof EntityPlayerMP && ((EntityPlayerMP) damagesource.j()).c.d()) {
                        this.a(false);
                    } else {
                        this.bl = true;
                    }
                }

                if (damagesource instanceof EntityDamageSourceIndirect) {
                    this.bl = false;

                    for (int i0 = 0; i0 < 64; ++i0) {
                        if (this.n()) {
                            return true;
                        }
                    }

                    return false;
                }
            }

            boolean flag0 = super.a(damagesource, f0);

            if (damagesource.e() && this.V.nextInt(10) != 0) {
                this.n();
            }

            return flag0;
        }
    }

    public boolean cm() {
        return this.ac.a(18) > 0;
    }

    public void a(boolean flag0) {
        this.ac.b(18, Byte.valueOf((byte) (flag0 ? 1 : 0)));
    }
/* CanaryMod: Disable all default allowed pick ups
        
    static {
        bk.add(Blocks.c);
        bk.add(Blocks.d);
        bk.add(Blocks.m);
        bk.add(Blocks.n);
        bk.add(Blocks.N);
        bk.add(Blocks.O);
        bk.add(Blocks.P);
        bk.add(Blocks.Q);
        bk.add(Blocks.W);
        bk.add(Blocks.aK);
        bk.add(Blocks.aL);
        bk.add(Blocks.aU);
        bk.add(Blocks.bk);
        bk.add(Blocks.bw);
    }
 END */

    class AIFindPlayer extends EntityAINearestAttackableTarget {

        private EntityPlayer g;
        private int h;
        private int i;
        private EntityEnderman j = EntityEnderman.this;
      
        public AIFindPlayer() {
            super(EntityEnderman.this, EntityPlayer.class, true);
        }

        public boolean a() {
            double iattributeinstance1 = this.f();
            List list = this.e.o.a(EntityPlayer.class, this.e.aQ().b(iattributeinstance1, 4.0D, iattributeinstance1), this.c);

            Collections.sort(list, this.b);
            if (list.isEmpty()) {
                return false;
            } else {
                this.g = (EntityPlayer) list.get(0);
                return true;
            }
        }

        public void c() {
            this.h = 5;
            this.i = 0;
        }

        public void d() {
            this.g = null;
            this.j.a(false);
            IAttributeInstance iattributeinstance1 = this.j.a(SharedMonsterAttributes.d);

            iattributeinstance1.c(EntityEnderman.c);
            super.d();
        }

        public boolean b() {
            if (this.g != null) {
                if (!this.j.c(this.g)) {
                    return false;
                } else {
                    this.j.bl = true;
                    this.j.a(this.g, 10.0F, 10.0F);
                    return true;
                }
            } else {
                return super.b();
            }
        }

        public void e() {
            if (this.g != null) {
                if (--this.h <= 0) {
                    this.d = this.g;
                    this.g = null;
                    super.c();
                    this.j.a("mob.endermen.stare", 1.0F, 1.0F);
                    this.j.a(true);
                    IAttributeInstance iattributeinstance1 = this.j.a(SharedMonsterAttributes.d);

                    iattributeinstance1.b(EntityEnderman.c);
                }
            } else {
                if (this.d != null) {
                    if (this.d instanceof EntityPlayer && this.j.c((EntityPlayer) this.d)) {
                        if (this.d.h(this.j) < 16.0D) {
                            this.j.n();
                        }

                        this.i = 0;
                    } else if (this.d.h(this.j) > 256.0D && this.i++ >= 30 && this.j.b((Entity) this.d)) {
                        this.i = 0;
                    }
                }

                super.e();
            }

        }
    }


    class AIPlaceBlock extends EntityAIBase {

        private EntityEnderman a = EntityEnderman.this;
      
        public boolean a() {
            return !this.a.o.Q().b("mobGriefing") ? false : (this.a.ck().c().r() == Material.a ? false : this.a.bb().nextInt(2000) == 0);
        }

        public void e() {
            Random random = this.a.bb();
            World world = this.a.o;
            int i0 = MathHelper.c(this.a.s - 1.0D + random.nextDouble() * 2.0D);
            int i1 = MathHelper.c(this.a.t + random.nextDouble() * 2.0D);
            int i2 = MathHelper.c(this.a.u - 1.0D + random.nextDouble() * 2.0D);
            BlockPos blockpos = new BlockPos(i0, i1, i2);
            Block block = world.p(blockpos).c();
            Block block1 = world.p(blockpos.b()).c();

            if (this.a(world, blockpos, this.a.ck().c(), block, block1)) {
                // CanaryMod: call EndermanDropBlockHook
                EndermanDropBlockHook hook = (EndermanDropBlockHook) new EndermanDropBlockHook((CanaryEnderman) entity, entity.getWorld().getBlockAt(i0, i1, i2)).call();
                if (!hook.isCanceled()) {                
                    world.a(blockpos, this.a.ck(), 3);
                    this.a.a(Blocks.a.P());
                }
                //
            }

        }

        private boolean a(World p_a_1_, BlockPos p_a_2_, Block p_a_3_, Block p_a_4_, Block p_a_5_) {
            return !p_a_3_.c(p_a_1_, p_a_2_) ? false : (p_a_4_.r() != Material.a ? false : (p_a_5_.r() == Material.a ? false : p_a_5_.d()));
        }
    }


    class AITakeBlock extends EntityAIBase {

        private EntityEnderman a = EntityEnderman.this;
      
        public boolean a() {
            return !this.a.o.Q().b("mobGriefing") ? false : (this.a.ck().c().r() != Material.a ? false : this.a.bb().nextInt(20) == 0);
        }

        public void e() {
            Random random = this.a.bb();
            World world = this.a.o;
            int i0 = MathHelper.c(this.a.s - 2.0D + random.nextDouble() * 4.0D);
            int i1 = MathHelper.c(this.a.t + random.nextDouble() * 3.0D);
            int i2 = MathHelper.c(this.a.u - 2.0D + random.nextDouble() * 4.0D);
            BlockPos blockpos = new BlockPos(i0, i1, i2);
            IBlockState iblockstate = world.p(blockpos);
            Block block = iblockstate.c();

             // CanaryMod: Replace checking static array with checking the world config list for Ender Blocks
            if (Arrays.asList(Configuration.getWorldConfig(getCanaryWorld().getFqName()).getEnderBlocks()).contains(Block.b(block))) {
                // CanaryMod: call EndermanPickupBlockHook
                EndermanPickupBlockHook hook = (EndermanPickupBlockHook) new EndermanPickupBlockHook((CanaryEnderman) entity, entity.getWorld().getBlockAt(i0, i1, i2)).call();
                if (!hook.isCanceled()) {
                    this.a.a(iblockstate);
                    world.a(blockpos, Blocks.a.P());
                }
            }
        }
    }
}
