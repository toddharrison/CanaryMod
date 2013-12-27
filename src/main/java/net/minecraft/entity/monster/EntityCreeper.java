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
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityCreeper extends EntityMob {

    private int bp;
    private int bq;
    public int br = 30; // CanaryMod: private -> public // Fuse
    public int bs = 3; // CanaryMod: private -> public // Power

    public EntityCreeper(World world) {
        super(world);
        this.c.a(1, new EntityAISwimming(this));
        this.c.a(2, new EntityAICreeperSwell(this));
        this.c.a(3, new EntityAIAvoidEntity(this, EntityOcelot.class, 6.0F, 1.0D, 1.2D));
        this.c.a(4, new EntityAIAttackOnCollide(this, 1.0D, false));
        this.c.a(5, new EntityAIWander(this, 0.8D));
        this.c.a(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.c.a(6, new EntityAILookIdle(this));
        this.d.a(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
        this.d.a(2, new EntityAIHurtByTarget(this, false));
        this.entity = new CanaryCreeper(this); // CanaryMod: Wrap Entity
    }

    protected void aD() {
        super.aD();
        this.a(SharedMonsterAttributes.d).a(0.25D);
    }

    public boolean bk() {
        return true;
    }

    public int ax() {
        return this.o() == null ? 3 : 3 + (int) (this.aS() - 1.0F);
    }

    protected void b(float f0) {
        super.b(f0);
        this.bq = (int) ((float) this.bq + f0 * 1.5F);
        if (this.bq > this.br - 5) {
            this.bq = this.br - 5;
        }
    }

    protected void c() {
        super.c();
        this.ag.a(16, Byte.valueOf((byte) -1));
        this.ag.a(17, Byte.valueOf((byte) 0));
        this.ag.a(18, Byte.valueOf((byte) 0));
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        if (this.ag.a(17) == 1) {
            nbttagcompound.a("powered", true);
        }

        nbttagcompound.a("Fuse", (short) this.br);
        nbttagcompound.a("ExplosionRadius", (byte) this.bs);
        nbttagcompound.a("ignited", this.ca());
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.ag.b(17, Byte.valueOf((byte) (nbttagcompound.n("powered") ? 1 : 0)));
        if (nbttagcompound.b("Fuse", 99)) {
            this.br = nbttagcompound.e("Fuse");
        }

        if (nbttagcompound.b("ExplosionRadius", 99)) {
            this.bs = nbttagcompound.d("ExplosionRadius");
        }
        if (nbttagcompound.n("ignited")) {
            this.cb();
        }

    }

    public void h() {
        if (this.Z()) {
            this.bp = this.bq;
            if (this.ca()) {
                this.a(1);
            }

            int i0 = this.bZ();

            if (i0 > 0 && this.bq == 0) {
                this.a("creeper.primed", 1.0F, 0.5F);
            }

            this.bq += i0;
            if (this.bq < 0) {
                this.bq = 0;
            }

            if (this.bq >= this.br) {
                this.bq = this.br;

                this.cc();
            }
        }

        super.h();
    }

    protected String aT() {
        return "mob.creeper.say";
    }

    protected String aU() {
        return "mob.creeper.death";
    }

    public void a(DamageSource damagesource) {
        super.a(damagesource);
        if (damagesource.j() instanceof EntitySkeleton) {
            int i0 = Item.b(Items.cd);
            int i1 = Item.b(Items.co);
            int i2 = i0 + this.aa.nextInt(i1 - i0 + 1);

            this.a(Item.d(i2), 1);
        }
    }

    public boolean m(Entity entity) {
        return true;
    }

    public boolean bX() {
        return this.ag.a(17) == 1;
    }

    protected Item u() {
        return Items.H;
    }

    public int bZ() {
        return this.ag.a(16);
    }

    public void a(int i0) {
        this.ag.b(16, Byte.valueOf((byte) i0));
    }

    public void a(EntityLightningBolt entitylightningbolt) {
        super.a(entitylightningbolt);
        this.ag.b(17, Byte.valueOf((byte) 1));
    }

    protected boolean a(EntityPlayer entityplayer) {
        ItemStack itemstack = entityplayer.bn.h();

        if (itemstack != null && itemstack.b() == Items.d) {
            this.p.a(this.t + 0.5D, this.u + 0.5D, this.v + 0.5D, "fire.ignite", 1.0F, this.aa.nextFloat() * 0.4F + 0.8F);
            entityplayer.ba();
            if (!this.p.E) {
                this.cb();
                itemstack.a(1, (EntityLivingBase) entityplayer);
                return true;
            }
        }

        return super.a(entityplayer);
    }

    private void cc() {
        if (!this.p.E) {
            boolean flag0 = this.p.N().b("mobGriefing");

            if (this.bX()) {
                this.p.a(this, this.t, this.u, this.v, (float) (this.bs * 2), flag0);
            }
            else {
                this.p.a(this, this.t, this.u, this.v, (float) this.bs, flag0);
            }

            this.B();
        }

    }

    public boolean ca() {
        return this.ag.a(18) != 0;
    }

    public void cb() {
        this.ag.b(18, Byte.valueOf((byte) 1));
    }

    // CanaryMod: Set Charge to Creeper
    public void setCharged(boolean charged) {
        this.ag.b(17, Byte.valueOf(charged ? (byte) 1 : (byte) 0));
    }
}
