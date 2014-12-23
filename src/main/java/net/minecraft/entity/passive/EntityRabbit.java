package net.minecraft.entity.passive;

import com.google.common.base.Predicate;
import net.canarymod.api.entity.living.animal.CanaryRabbit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCarrot;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.*;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityRabbit extends EntityAnimal {

    private AIAvoidEntity bk;
    private int bm = 0;
    private int bn = 0;
    private boolean bo = false;
    private boolean bp = false;
    private int bq = 0;
    private EnumMoveType br;
    private int bs;
    private EntityPlayer bt;

    public EntityRabbit(World world) {
        super(world);
        this.br = EnumMoveType.HOP;
        this.bs = 0;
        this.bt = null;
        this.a(0.6F, 0.7F);
        this.g = new RabbitJumpHelper(this);
        this.f = new RabbitMoveHelper();
        ((PathNavigateGround)this.s()).a(true);
        this.h.a(2.5F);
        this.i.a(1, new EntityAISwimming(this));
        this.i.a(1, new AIPanic(1.33D));
        this.i.a(2, new EntityAITempt(this, 1.0D, Items.bR, false));
        this.i.a(3, new EntityAIMate(this, 0.8D));
        this.i.a(5, new AIRaidFarm());
        this.i.a(5, new EntityAIWander(this, 0.6D));
        this.i.a(11, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
        this.bk = new AIAvoidEntity(new Predicate() {

            public boolean a(Entity p_a_1_) {
                return p_a_1_ instanceof EntityWolf;
            }

            public boolean apply(Object p_apply_1_) {
                return this.a((Entity)p_apply_1_);
            }
        }, 16.0F, 1.33D, 1.33D
        );
        this.i.a(4, this.bk);
        this.b(0.0D);
        this.entity = new CanaryRabbit(this); // CanaryMod: Wrap Entity
    }

    protected float bD() {
        return this.f.a() && this.f.e() > this.t + 0.5D ? 0.5F : this.br.b();
    }

    public void a(EnumMoveType entityrabbit_enummovetype) {
        this.br = entityrabbit_enummovetype;
    }

    public void b(double d0) {
        this.s().a(d0);
        this.f.a(this.f.d(), this.f.e(), this.f.f(), d0);
    }

    public void a(boolean flag0, EnumMoveType entityrabbit_enummovetype) {
        super.i(flag0);
        if (!flag0) {
            if (this.br == EnumMoveType.ATTACK) {
                this.br = EnumMoveType.HOP;
            }
        }
        else {
            this.b(1.5D * (double)entityrabbit_enummovetype.a());
            this.a(this.ck(), this.bA(), ((this.V.nextFloat() - this.V.nextFloat()) * 0.2F + 1.0F) * 0.8F);
        }

        this.bo = flag0;
    }

    public void b(EnumMoveType entityrabbit_enummovetype) {
        this.a(true, entityrabbit_enummovetype);
        this.bn = entityrabbit_enummovetype.d();
        this.bm = 0;
    }

    public boolean cj() {
        return this.bo;
    }

    protected void h() {
        super.h();
        this.ac.a(18, Byte.valueOf((byte)0));
    }

    public void E() {
        if (this.f.b() > 0.8D) {
            this.a(EnumMoveType.SPRINT);
        }
        else if (this.br != EnumMoveType.ATTACK) {
            this.a(EnumMoveType.HOP);
        }

        if (this.bq > 0) {
            --this.bq;
        }

        if (this.bs > 0) {
            this.bs -= this.V.nextInt(3);
            if (this.bs < 0) {
                this.bs = 0;
            }
        }

        if (this.C) {
            if (!this.bp) {
                this.a(false, EnumMoveType.NONE);
                this.cu();
            }

            if (this.cl() == 99 && this.bq == 0) {
                EntityLivingBase entitylivingbase = this.u();

                if (entitylivingbase != null && this.h(entitylivingbase) < 16.0D) {
                    this.a(entitylivingbase.s, entitylivingbase.u);
                    this.f.a(entitylivingbase.s, entitylivingbase.t, entitylivingbase.u, this.f.b());
                    this.b(EnumMoveType.ATTACK);
                    this.bp = true;
                }
            }

            RabbitJumpHelper entityrabbit_rabbitjumphelper = (RabbitJumpHelper)this.g;

            if (!entityrabbit_rabbitjumphelper.c()) {
                if (this.f.a() && this.bq == 0) {
                    PathEntity pathentity = this.h.j();
                    Vec3 vec3 = new Vec3(this.f.d(), this.f.e(), this.f.f());

                    if (pathentity != null && pathentity.e() < pathentity.d()) {
                        vec3 = pathentity.a((Entity)this);
                    }

                    this.a(vec3.a, vec3.c);
                    this.b(this.br);
                }
            }
            else if (!entityrabbit_rabbitjumphelper.d()) {
                this.cr();
            }
        }

        this.bp = this.C;
    }

    public void Y() {
    }

    private void a(double d0, double d1) {
        this.y = (float)(Math.atan2(d1 - this.u, d0 - this.s) * 180.0D / 3.1415927410125732D) - 90.0F;
    }

    private void cr() {
        ((RabbitJumpHelper)this.g).a(true);
    }

    private void cs() {
        ((RabbitJumpHelper)this.g).a(false);
    }

    private void ct() {
        this.bq = this.cm();
    }

    private void cu() {
        this.ct();
        this.cs();
    }

    public void m() {
        super.m();
        if (this.bm != this.bn) {
            if (this.bm == 0 && !this.o.D) {
                this.o.a((Entity)this, (byte)1);
            }

            ++this.bm;
        }
        else if (this.bn != 0) {
            this.bm = 0;
            this.bn = 0;
        }
    }

    protected void aW() {
        super.aW();
        this.a(SharedMonsterAttributes.a).a(10.0D);
        this.a(SharedMonsterAttributes.d).a(0.30000001192092896D);
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("RabbitType", this.cl());
        nbttagcompound.a("MoreCarrotTicks", this.bs);
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.r(nbttagcompound.f("RabbitType"));
        this.bs = nbttagcompound.f("MoreCarrotTicks");
    }

    protected String ck() {
        return "mob.rabbit.hop";
    }

    protected String z() {
        return "mob.rabbit.idle";
    }

    protected String bn() {
        return "mob.rabbit.hurt";
    }

    protected String bo() {
        return "mob.rabbit.death";
    }

    public boolean r(Entity entity) {
        if (this.cl() == 99) {
            this.a("mob.attack", 1.0F, (this.V.nextFloat() - this.V.nextFloat()) * 0.2F + 1.0F);
            return entity.a(DamageSource.a((EntityLivingBase)this), 8.0F);
        }
        else {
            return entity.a(DamageSource.a((EntityLivingBase)this), 3.0F);
        }
    }

    public int bq() {
        return this.cl() == 99 ? 8 : super.bq();
    }

    public boolean a(DamageSource damagesource, float f0) {
        return this.b(damagesource) ? false : super.a(damagesource, f0);
    }

    protected void bp() {
        this.a(new ItemStack(Items.br, 1), 0.0F);
    }

    protected void b(boolean flag0, int i0) {
        int i1 = this.V.nextInt(2) + this.V.nextInt(1 + i0);

        int i2;

        for (i2 = 0; i2 < i1; ++i2) {
            this.a(Items.bs, 1);
        }

        i1 = this.V.nextInt(2);

        for (i2 = 0; i2 < i1; ++i2) {
            if (this.au()) {
                this.a(Items.bp, 1);
            }
            else {
                this.a(Items.bo, 1);
            }
        }
    }

    private boolean a(Item item) {
        return item == Items.bR || item == Items.bW || item == Item.a((Block)Blocks.N);
    }

    public EntityRabbit b(EntityAgeable entityageable) {
        EntityRabbit entityrabbit = new EntityRabbit(this.o);

        if (entityageable instanceof EntityRabbit) {
            entityrabbit.r(this.V.nextBoolean() ? this.cl() : ((EntityRabbit)entityageable).cl());
        }

        return entityrabbit;
    }

    public boolean d(ItemStack itemstack) {
        return itemstack != null && this.a(itemstack.b());
    }

    public int cl() {
        return this.ac.a(18);
    }

    public void r(int i0) {
        if (i0 == 99) {
            this.i.a((EntityAIBase)this.bk);
            this.i.a(4, new AIEvilAttack());
            this.bg.a(1, new EntityAIHurtByTarget(this, false, new Class[0]));
            this.bg.a(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
            this.bg.a(2, new EntityAINearestAttackableTarget(this, EntityWolf.class, true));
            if (!this.k_()) {
                this.a(StatCollector.a("entity.KillerBunny.name"));
            }
        }

        this.ac.b(18, Byte.valueOf((byte)i0));
    }

    public IEntityLivingData a(DifficultyInstance difficultyinstance, IEntityLivingData ientitylivingdata) {
        Object ientitylivingdata1 = super.a(difficultyinstance, ientitylivingdata);
        int i0 = this.V.nextInt(6);
        boolean flag0 = false;

        if (ientitylivingdata1 instanceof RabbitTypeData) {
            i0 = ((RabbitTypeData)ientitylivingdata1).a;
            flag0 = true;
        }
        else {
            ientitylivingdata1 = new RabbitTypeData(i0);
        }

        this.r(i0);
        if (flag0) {
            this.b(-24000);
        }

        return (IEntityLivingData)ientitylivingdata1;
    }

    private boolean cv() {
        return this.bs == 0;
    }

    protected int cm() {
        return this.br.c();
    }

    protected void cn() {
        this.o.a(EnumParticleTypes.BLOCK_DUST, this.s + (double)(this.V.nextFloat() * this.J * 2.0F) - (double)this.J, this.t + 0.5D + (double)(this.V.nextFloat() * this.K), this.u + (double)(this.V.nextFloat() * this.J * 2.0F) - (double)this.J, 0.0D, 0.0D, 0.0D, new int[]{ Block.f(Blocks.cb.a(7)) });
        this.bs = 100;
    }

    public EntityAgeable a(EntityAgeable entityageable) {
        return this.b(entityageable);
    }

    class AIAvoidEntity extends EntityAIAvoidEntity {

        private EntityRabbit d = EntityRabbit.this;

        public AIAvoidEntity(Predicate p_i45865_2_, float p_i45865_3_, double p_i45865_4_, double p_i45865_6_) {
            super(EntityRabbit.this, p_i45865_2_, p_i45865_3_, p_i45865_4_, p_i45865_6_);
        }

        public void e() {
            super.e();
        }
    }

    class AIEvilAttack extends EntityAIAttackOnCollide {

        public AIEvilAttack() {
            super(EntityRabbit.this, EntityLivingBase.class, 1.4D, true);
        }

        protected double a(EntityLivingBase p_a_1_) {
            return (double)(4.0F + p_a_1_.J);
        }
    }

    class AIPanic extends EntityAIPanic {

        private EntityRabbit b = EntityRabbit.this;

        public AIPanic(double p_i45861_2_) {
            super(EntityRabbit.this, p_i45861_2_);
        }

        public void e() {
            super.e();
            this.b.b(this.a);
        }
    }

    class AIRaidFarm extends EntityAIMoveToBlock {

        private boolean d;
        private boolean e = false;

        public AIRaidFarm() {
            super(EntityRabbit.this, 0.699999988079071D, 16);
        }

        public boolean a() {
            if (this.a <= 0) {
                if (!EntityRabbit.this.o.Q().b("mobGriefing")) {
                    return false;
                }

                this.e = false;
                this.d = EntityRabbit.this.cv();
            }

            return super.a();
        }

        public boolean b() {
            return this.e && super.b();
        }

        public void c() {
            super.c();
        }

        public void d() {
            super.d();
        }

        public void e() {
            super.e();
            EntityRabbit.this.p().a((double)this.b.n() + 0.5D, (double)(this.b.o() + 1), (double)this.b.p() + 0.5D, 10.0F, (float)EntityRabbit.this.bP());
            if (this.f()) {
                World world = EntityRabbit.this.o;
                BlockPos blockpos = this.b.a();
                IBlockState block1 = world.p(blockpos);
                Block iblockstate1 = block1.c();

                if (this.e && iblockstate1 instanceof BlockCarrot && ((Integer)block1.b(BlockCarrot.a)).intValue() == 7) {
                    world.a(blockpos, Blocks.a.P(), 2);
                    world.b(blockpos, true);
                    EntityRabbit.this.cn();
                }

                this.e = false;
                this.a = 10;
            }
        }

        protected boolean a(World p_a_1_, BlockPos p_a_2_) {
            Block block1 = p_a_1_.p(p_a_2_).c();

            if (block1 == Blocks.ak) {
                p_a_2_ = p_a_2_.a();
                IBlockState iblockstate1 = p_a_1_.p(p_a_2_);

                block1 = iblockstate1.c();
                if (block1 instanceof BlockCarrot && ((Integer)iblockstate1.b(BlockCarrot.a)).intValue() == 7 && this.d && !this.e) {
                    this.e = true;
                    return true;
                }
            }

            return false;
        }
    }

    static enum EnumMoveType {

        NONE("NONE", 0, 0.0F, 0.0F, 30, 1),
        HOP("HOP", 1, 0.8F, 0.2F, 20, 10),
        STEP("STEP", 2, 1.0F, 0.45F, 14, 14),
        SPRINT("SPRINT", 3, 1.75F, 0.4F, 1, 8),
        ATTACK("ATTACK", 4, 2.0F, 0.7F, 7, 8);
        private final float f;
        private final float g;
        private final int h;
        private final int i;

        private static final EnumMoveType[] $VALUES = new EnumMoveType[]{ NONE, HOP, STEP, SPRINT, ATTACK };

        private EnumMoveType(String p_i45866_1_, int p_i45866_2_, float p_i45866_3_, float p_i45866_4_, int p_i45866_5_, int p_i45866_6_) {
            this.f = p_i45866_3_;
            this.g = p_i45866_4_;
            this.h = p_i45866_5_;
            this.i = p_i45866_6_;
        }

        public float a() {
            return this.f;
        }

        public float b() {
            return this.g;
        }

        public int c() {
            return this.h;
        }

        public int d() {
            return this.i;
        }

    }

    public class RabbitJumpHelper extends EntityJumpHelper {

        private EntityRabbit c;
        private boolean d = false;

        public RabbitJumpHelper(EntityRabbit p_i45863_2_) {
            super(p_i45863_2_);
            this.c = p_i45863_2_;
        }

        public boolean c() {
            return this.a;
        }

        public boolean d() {
            return this.d;
        }

        public void a(boolean p_a_1_) {
            this.d = p_a_1_;
        }

        public void b() {
            if (this.a) {
                this.c.b(EnumMoveType.STEP);
                this.a = false;
            }
        }
    }

    class RabbitMoveHelper extends EntityMoveHelper {

        private EntityRabbit g = EntityRabbit.this;

        public RabbitMoveHelper() {
            super(EntityRabbit.this);
        }

        public void c() {
            if (this.g.C && !this.g.cj()) {
                this.g.b(0.0D);
            }

            super.c();
        }
    }

    public static class RabbitTypeData implements IEntityLivingData {

        public int a;

        public RabbitTypeData(int p_i45864_1_) {
            this.a = p_i45864_1_;
        }
    }
}
