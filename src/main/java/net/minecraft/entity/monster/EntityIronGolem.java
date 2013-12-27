package net.minecraft.entity.monster;

import net.canarymod.api.entity.living.CanaryIronGolem;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIDefendVillage;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookAtVillager;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.village.Village;
import net.minecraft.world.World;

public class EntityIronGolem extends EntityGolem {

    private int bq;
    public Village bp; // CanaryMod package -> public
    private int br;
    private int bs;

    public EntityIronGolem(World world) {
        super(world);
        this.a(1.4F, 2.9F);
        this.m().a(true);
        this.c.a(1, new EntityAIAttackOnCollide(this, 1.0D, true));
        this.c.a(2, new EntityAIMoveTowardsTarget(this, 0.9D, 32.0F));
        this.c.a(3, new EntityAIMoveThroughVillage(this, 0.6D, true));
        this.c.a(4, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.c.a(5, new EntityAILookAtVillager(this));
        this.c.a(6, new EntityAIWander(this, 0.6D));
        this.c.a(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.c.a(8, new EntityAILookIdle(this));
        this.d.a(1, new EntityAIDefendVillage(this));
        this.d.a(2, new EntityAIHurtByTarget(this, false));
        this.d.a(3, new EntityAINearestAttackableTarget(this, EntityLiving.class, 0, false, true, IMob.a));
        this.entity = new CanaryIronGolem(this); // CanaryMod: Warp Entity
    }

    protected void c() {
        super.c();
        this.ag.a(16, Byte.valueOf((byte) 0));
    }

    public boolean bk() {
        return true;
    }

    protected void bp() {
        if (--this.bq <= 0) {
            this.bq = 70 + this.aa.nextInt(50);
            this.bp = this.p.A.a(MathHelper.c(this.t), MathHelper.c(this.u), MathHelper.c(this.v), 32);
            if (this.bp == null) {
                this.bV();
            }
            else {
                ChunkCoordinates chunkcoordinates = this.bp.a();

                this.a(chunkcoordinates.a, chunkcoordinates.b, chunkcoordinates.c, (int) ((float) this.bp.b() * 0.6F));
            }
        }

        super.bp();
    }

    protected void aD() {
        super.aD();
        this.a(SharedMonsterAttributes.a).a(100.0D);
        this.a(SharedMonsterAttributes.d).a(0.25D);
    }

    protected int j(int i0) {
        return i0;
    }

    protected void n(Entity entity) {
        if (entity instanceof IMob && this.aI().nextInt(20) == 0) {
            this.d((EntityLivingBase) entity);
        }

        super.n(entity);
    }

    public void e() {
        super.e();
        if (this.br > 0) {
            --this.br;
        }

        if (this.bs > 0) {
            --this.bs;
        }

        if (this.w * this.w + this.y * this.y > 2.500000277905201E-7D && this.aa.nextInt(5) == 0) {
            int i0 = MathHelper.c(this.t);
            int i1 = MathHelper.c(this.u - 0.20000000298023224D - (double) this.M);
            int i2 = MathHelper.c(this.v);
            Block block = this.p.a(i0, i1, i2);

            if (block.o() != Material.a) {
                this.p.a("blockcrack_" + Block.b(block) + "_" + this.p.e(i0, i1, i2), this.t + ((double) this.aa.nextFloat() - 0.5D) * (double) this.N, this.D.b + 0.1D, this.v + ((double) this.aa.nextFloat() - 0.5D) * (double) this.N, 4.0D * ((double) this.aa.nextFloat() - 0.5D), 0.5D, ((double) this.aa.nextFloat() - 0.5D) * 4.0D);
            }
        }
    }

    public boolean a(Class oclass0) {
        return this.ca() && EntityPlayer.class.isAssignableFrom(oclass0) ? false : super.a(oclass0);
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("PlayerCreated", this.ca());
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.i(nbttagcompound.n("PlayerCreated"));
    }

    public boolean m(Entity entity) {
        this.br = 10;
        this.p.a(this, (byte) 4);
        boolean flag0 = entity.a(DamageSource.a((EntityLivingBase) this), (float) (7 + this.aa.nextInt(15)));

        if (flag0) {
            entity.x += 0.4000000059604645D;
        }

        this.a("mob.irongolem.throw", 1.0F, 1.0F);
        return flag0;
    }

    public Village bX() {
        return this.bp;
    }

    public void a(boolean flag0) {
        this.bs = flag0 ? 400 : 0;
        this.p.a(this, (byte) 11);
    }

    protected String aT() {
        return "mob.irongolem.hit";
    }

    protected String aU() {
        return "mob.irongolem.death";
    }

    protected void a(int i0, int i1, int i2, Block block) {
        this.a("mob.irongolem.walk", 1.0F, 1.0F);
    }

    protected void b(boolean flag0, int i0) {
        int i1 = this.aa.nextInt(3);

        int i2;

        for (i2 = 0; i2 < i1; ++i2) {
            this.a(Item.a((Block) Blocks.O), 1, 0.0F);
        }

        i2 = 3 + this.aa.nextInt(3);

        for (int i3 = 0; i3 < i2; ++i3) {
            this.a(Items.j, 1);
        }
    }

    public int bZ() {
        return this.bs;
    }

    public boolean ca() {
        return (this.ag.a(16) & 1) != 0;
    }

    public void i(boolean flag0) {
        byte b0 = this.ag.a(16);

        if (flag0) {
            this.ag.b(16, Byte.valueOf((byte) (b0 | 1)));
        }
        else {
            this.ag.b(16, Byte.valueOf((byte) (b0 & -2)));
        }
    }

    public void a(DamageSource damagesource) {
        if (!this.ca() && this.aS != null && this.bp != null) {
            this.bp.a(this.aS.b_(), -5);
        }

        super.a(damagesource);
    }

    // CanaryMod
    public void setRoseTicks(int ticks) {
        this.bs = ticks;
    }
    //
}
