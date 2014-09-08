package net.minecraft.entity.monster;

import net.canarymod.api.entity.living.CanaryIronGolem;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
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
        this.af.a(16, Byte.valueOf((byte) 0));
    }

    public boolean bk() {
        return true;
    }

    protected void bp() {
        if (--this.bq <= 0) {
            this.bq = 70 + this.Z.nextInt(50);
            this.bp = this.o.A.a(MathHelper.c(this.s), MathHelper.c(this.t), MathHelper.c(this.u), 32);
            if (this.bp == null) {
                this.bX();
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

    protected void o(Entity entity) {
        if (entity instanceof IMob && this.aI().nextInt(20) == 0) {
            this.d((EntityLivingBase) entity);
        }

        super.o(entity);
    }

    public void e() {
        super.e();
        if (this.br > 0) {
            --this.br;
        }

        if (this.bs > 0) {
            --this.bs;
        }

        if (this.v * this.v + this.x * this.x > 2.500000277905201E-7D && this.Z.nextInt(5) == 0) {
            int i0 = MathHelper.c(this.s);
            int i1 = MathHelper.c(this.t - 0.20000000298023224D - (double) this.L);
            int i2 = MathHelper.c(this.u);
            Block block = this.o.a(i0, i1, i2);

            if (block.o() != Material.a) {
                this.o.a("blockcrack_" + Block.b(block) + "_" + this.o.e(i0, i1, i2), this.s + ((double) this.Z.nextFloat() - 0.5D) * (double) this.M, this.C.b + 0.1D, this.u + ((double) this.Z.nextFloat() - 0.5D) * (double) this.M, 4.0D * ((double) this.Z.nextFloat() - 0.5D), 0.5D, ((double) this.Z.nextFloat() - 0.5D) * 4.0D);
            }
        }
    }

    public boolean a(Class oclass0) {
        return this.cc() && EntityPlayer.class.isAssignableFrom(oclass0) ? false : super.a(oclass0);
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("PlayerCreated", this.cc());
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.i(nbttagcompound.n("PlayerCreated"));
    }

    public boolean n(Entity entity) {
        this.br = 10;
        this.o.a(this, (byte) 4);
        boolean flag0 = entity.a(DamageSource.a((EntityLivingBase) this), (float) (7 + this.Z.nextInt(15)));

        if (flag0) {
            entity.w += 0.4000000059604645D;
        }

        this.a("mob.irongolem.throw", 1.0F, 1.0F);
        return flag0;
    }

    public Village bZ() {
        return this.bp;
    }

    public void a(boolean flag0) {
        this.bs = flag0 ? 400 : 0;
        this.o.a(this, (byte) 11);
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
        int i1 = this.Z.nextInt(3);

        int i2;

        for (i2 = 0; i2 < i1; ++i2) {
            this.a(Item.a((Block) Blocks.O), 1, 0.0F);
        }

        i2 = 3 + this.Z.nextInt(3);

        for (int i3 = 0; i3 < i2; ++i3) {
            this.a(Items.j, 1);
        }
    }

    public int cb() {
        return this.bs;
    }

    public boolean cc() {
        return (this.af.a(16) & 1) != 0;
    }

    public void i(boolean flag0) {
        byte b0 = this.af.a(16);

        if (flag0) {
            this.af.b(16, Byte.valueOf((byte) (b0 | 1)));
        }
        else {
            this.af.b(16, Byte.valueOf((byte) (b0 & -2)));
        }
    }

    public void a(DamageSource damagesource) {
        if (!this.cc() && this.aR != null && this.bp != null) {
            this.bp.a(this.aR.b_(), -5);
        }

        super.a(damagesource);
    }

    // CanaryMod
    public void setRoseTicks(int ticks) {
        this.bs = ticks;
    }
    //
}
