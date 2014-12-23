package net.minecraft.entity.monster;

import net.canarymod.api.entity.living.monster.CanaryPigZombie;
import net.canarymod.hook.entity.MobTargetHook;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import java.util.UUID;

public class EntityPigZombie extends EntityZombie {

    private static final UUID c = UUID.fromString("49455A49-7EC5-45BA-B886-3B90B23A1718");
    private static final AttributeModifier bk = (new AttributeModifier(c, "Attacking speed boost", 0.05D, 0)).a(false);
    public int bl; // CanaryMod: private -> public
    private int bm;
    private UUID bn;

    public EntityPigZombie(World world) {
        super(world);
        this.ab = true;
        this.entity = new CanaryPigZombie(this); // CanaryMod: Wrap Entity
    }

    public void b(EntityLivingBase entitylivingbase) {
        super.b(entitylivingbase);
        if (entitylivingbase != null) {
            this.bn = entitylivingbase.aJ();
        }

    }

    protected void n() {
        this.bg.a(1, new EntityPigZombie.AIHurtByAggressor());
        this.bg.a(2, new EntityPigZombie.AITargetAggressor());
    }

    protected void aW() {
        super.aW();
        this.a(b).a(0.0D);
        this.a(SharedMonsterAttributes.d).a(0.23000000417232513D);
        this.a(SharedMonsterAttributes.e).a(5.0D);
    }

    public void s_() {
        super.s_();
    }

    protected void E() {
        IAttributeInstance iattributeinstance = this.a(SharedMonsterAttributes.d);

        if (this.ck()) {
            if (!this.i_() && !iattributeinstance.a(bk)) {
                iattributeinstance.b(bk);
            }

            --this.bl;
        }
        else if (iattributeinstance.a(bk)) {
            iattributeinstance.c(bk);
        }

        if (this.bm > 0 && --this.bm == 0) {
            this.a("mob.zombiepig.zpigangry", this.bA() * 2.0F, ((this.V.nextFloat() - this.V.nextFloat()) * 0.2F + 1.0F) * 1.8F);
        }

        if (this.bl > 0 && this.bn != null && this.bc() == null) {
            EntityPlayer entityplayer = this.o.b(this.bn);

            this.b((EntityLivingBase) entityplayer);
            this.aL = entityplayer;
            this.aM = this.bd();
        }

        super.E();
    }

    public boolean bQ() {
        return this.o.aa() != EnumDifficulty.PEACEFUL;
    }

    public boolean bR() {
        return this.o.a(this.aQ(), (Entity) this) && this.o.a((Entity) this, this.aQ()).isEmpty() && !this.o.d(this.aQ());
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("Anger", (short) this.bl);
        if (this.bn != null) {
            nbttagcompound.a("HurtBy", this.bn.toString());
        }
        else {
            nbttagcompound.a("HurtBy", "");
        }

    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.bl = nbttagcompound.e("Anger");
        String s0 = nbttagcompound.j("HurtBy");

        if (s0.length() > 0) {
            this.bn = UUID.fromString(s0);
            EntityPlayer entityplayer = this.o.b(this.bn);

            this.b((EntityLivingBase) entityplayer);
            if (entityplayer != null) {
                this.aL = entityplayer;
                this.aM = this.bd();
            }
        }

    }

    public boolean a(DamageSource damagesource, float f0) {
        if (this.b(damagesource)) {
            return false;
        }
        else {
            Entity entity = damagesource.j();

            if (entity instanceof EntityPlayer) {
                // CanaryMod: MobTarget
                MobTargetHook hook = (MobTargetHook) new MobTargetHook((net.canarymod.api.entity.living.LivingBase) this.getCanaryEntity(), (net.canarymod.api.entity.living.LivingBase) entity.getCanaryEntity()).call();
                if (!hook.isCanceled()) {
                    this.b(entity);
                }
                //
            }

            return super.a(damagesource, f0);
        }
    }

    private void b(Entity entity) {
        this.bl = 400 + this.V.nextInt(400);
        this.bm = this.V.nextInt(40);
        if (entity instanceof EntityLivingBase) {
            this.b((EntityLivingBase) entity);
        }

    }

    public boolean ck() {
        return this.bl > 0;
    }

    protected String z() {
        return "mob.zombiepig.zpig";
    }

    protected String bn() {
        return "mob.zombiepig.zpighurt";
    }

    protected String bo() {
        return "mob.zombiepig.zpigdeath";
    }

    protected void b(boolean flag0, int i0) {
        int i1 = this.V.nextInt(2 + i0);

        int i2;

        for (i2 = 0; i2 < i1; ++i2) {
            this.a(Items.bt, 1);
        }

        i1 = this.V.nextInt(2 + i0);

        for (i2 = 0; i2 < i1; ++i2) {
            this.a(Items.bx, 1);
        }

    }

    public boolean a(EntityPlayer entityplayer) {
        return false;
    }

    protected void bp() {
        this.a(Items.k, 1);
    }

    protected void a(DifficultyInstance difficultyinstance) {
        this.c(0, new ItemStack(Items.B));
    }

    public IEntityLivingData a(DifficultyInstance difficultyinstance, IEntityLivingData ientitylivingdata) {
        super.a(difficultyinstance, ientitylivingdata);
        this.m(false);
        return ientitylivingdata;
    }

    class AIHurtByAggressor extends EntityAIHurtByTarget {

        public AIHurtByAggressor() {
            super(EntityPigZombie.this, true, new Class[0]);
        }

        protected void a(EntityCreature p_a_1_, EntityLivingBase p_a_2_) {
            super.a(p_a_1_, p_a_2_);
            if (p_a_1_ instanceof EntityPigZombie) {
                ((EntityPigZombie) p_a_1_).b((Entity) p_a_2_);
            }

        }
    }


    class AITargetAggressor extends EntityAINearestAttackableTarget {

        public AITargetAggressor() {
            super(EntityPigZombie.this, EntityPlayer.class, true);
        }

        public boolean a() {
            return ((EntityPigZombie) this.e).ck() && super.a();
        }
    }
}
