package net.minecraft.entity.monster;

import net.canarymod.api.entity.living.monster.CanaryCreeper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAICreeperSwell;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;


public class EntityCreeper extends EntityMob {

    private int b;
    private int c;
    public int bk = 30; // CanaryMod: private -> public // Fuse
    public int bl = 3; // CanaryMod: private -> public // Power
    private int bm = 0;

    public EntityCreeper(World world) {
        super(world);
        this.i.a(1, new EntityAISwimming(this));
        this.i.a(2, new EntityAICreeperSwell(this));
        this.i.a(2, this.a);
        this.i.a(3, new EntityAIAvoidEntity(this, new Predicate() {

            public boolean a(Entity p_a_1_) {
                return p_a_1_ instanceof EntityOcelot;
            }

            public boolean apply(Object p_apply_1_) {
                return this.a((Entity) p_apply_1_);
            }
        }, 6.0F, 1.0D, 1.2D));
        this.i.a(4, new EntityAIAttackOnCollide(this, 1.0D, false));
        this.i.a(5, new EntityAIWander(this, 0.8D));
        this.i.a(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.i.a(6, new EntityAILookIdle(this));
        this.bg.a(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        this.bg.a(2, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.entity = new CanaryCreeper(this); // CanaryMod: Wrap Entity
    }

    protected void aW() {
        super.aW();
        this.a(SharedMonsterAttributes.d).a(0.25D);
    }

    public int aF() {
        return this.u() == null ? 3 : 3 + (int) (this.bm() - 1.0F);
    }

    public void e(float f0, float f1) {
        super.e(f0, f1);
        this.c = (int) ((float) this.c + f0 * 1.5F);
        if (this.c > this.bk - 5) {
            this.c = this.bk - 5;
        }

    }

    protected void h() {
        super.h();
        this.ac.a(16, Byte.valueOf((byte) -1));
        this.ac.a(17, Byte.valueOf((byte) 0));
        this.ac.a(18, Byte.valueOf((byte) 0));
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        if (this.ac.a(17) == 1) {
            nbttagcompound.a("powered", true);
        }

        nbttagcompound.a("Fuse", (short) this.bk);
        nbttagcompound.a("ExplosionRadius", (byte) this.bl);
        nbttagcompound.a("ignited", this.cl());
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.ac.b(17, Byte.valueOf((byte) (nbttagcompound.n("powered") ? 1 : 0)));
        if (nbttagcompound.b("Fuse", 99)) {
            this.bk = nbttagcompound.e("Fuse");
        }

        if (nbttagcompound.b("ExplosionRadius", 99)) {
            this.bl = nbttagcompound.d("ExplosionRadius");
        }

        if (nbttagcompound.n("ignited")) {
            this.cm();
        }

    }

    public void s_() {
        if (this.ai()) {
            this.b = this.c;
            if (this.cl()) {
                this.a(1);
            }

            int i0 = this.ck();

            if (i0 > 0 && this.c == 0) {
                this.a("creeper.primed", 1.0F, 0.5F);
            }

            this.c += i0;
            if (this.c < 0) {
                this.c = 0;
            }

            if (this.c >= this.bk) {
                this.c = this.bk;
                this.cp();
            }
        }

        super.s_();
    }

    protected String bn() {
        return "mob.creeper.say";
    }

    protected String bo() {
        return "mob.creeper.death";
    }

    public void a(DamageSource damagesource) {
        super.a(damagesource);
        if (damagesource.j() instanceof EntitySkeleton) {
            int i0 = Item.b(Items.cq);
            int i1 = Item.b(Items.cB);
            int i2 = i0 + this.V.nextInt(i1 - i0 + 1);

            this.a(Item.b(i2), 1);
        } else if (damagesource.j() instanceof EntityCreeper && damagesource.j() != this && ((EntityCreeper) damagesource.j()).n() && ((EntityCreeper) damagesource.j()).cn()) {
            ((EntityCreeper) damagesource.j()).co();
            this.a(new ItemStack(Items.bX, 1, 4), 0.0F);
        }

    }

    public boolean r(Entity entity) {
        return true;
    }

    public boolean n() {
        return this.ac.a(17) == 1;
    }

    protected Item A() {
        return Items.H;
    }

    public int ck() {
        return this.ac.a(16);
    }

    public void a(int i0) {
        this.ac.b(16, Byte.valueOf((byte) i0));
    }

    public void a(EntityLightningBolt entitylightningbolt) {
        super.a(entitylightningbolt);
        this.ac.b(17, Byte.valueOf((byte) 1));
    }

    protected boolean a(EntityPlayer entityplayer) {
        ItemStack itemstack = entityplayer.bg.h();

        if (itemstack != null && itemstack.b() == Items.d) {
            this.o.a(this.s + 0.5D, this.t + 0.5D, this.u + 0.5D, "fire.ignite", 1.0F, this.V.nextFloat() * 0.4F + 0.8F);
            entityplayer.bv();
            if (!this.o.D) {
                this.cm();
                itemstack.a(1, (EntityLivingBase) entityplayer);
                return true;
            }
        }

        return super.a(entityplayer);
    }

    private void cp() {
        if (!this.o.D) {
            boolean flag0 = this.o.Q().b("mobGriefing");
            float f0 = this.n() ? 2.0F : 1.0F;

            this.o.a(this, this.s, this.t, this.u, (float) this.bl * f0, flag0);
            this.J();
        }

    }

    public boolean cl() {
        return this.ac.a(18) != 0;
    }

    public void cm() {
        this.ac.b(18, Byte.valueOf((byte) 1));
    }

    public boolean cn() {
        return this.bm < 1 && this.o.Q().b("doMobLoot");
    }

    public void co() {
        ++this.bm;
    }

    // CanaryMod: Set Charge to Creeper
    public void setCharged(boolean charged) {
        this.ac.b(17, Byte.valueOf(charged ? (byte) 1 : (byte) 0));
    }
}
