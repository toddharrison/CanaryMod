package net.minecraft.entity.passive;

import net.canarymod.api.entity.living.animal.CanaryWolf;
import net.canarymod.hook.entity.EntityTameHook;
import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityWolf extends EntityTameable {

    private float bq;
    private float br;
    private boolean bs;
    private boolean bt;
    private float bu;
    private float bv;

    public EntityWolf(World world) {
        super(world);
        this.a(0.6F, 0.8F);
        this.m().a(true);
        this.c.a(1, new EntityAISwimming(this));
        this.c.a(2, this.bp);
        this.c.a(3, new EntityAILeapAtTarget(this, 0.4F));
        this.c.a(4, new EntityAIAttackOnCollide(this, 1.0D, true));
        this.c.a(5, new EntityAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
        this.c.a(6, new EntityAIMate(this, 1.0D));
        this.c.a(7, new EntityAIWander(this, 1.0D));
        this.c.a(8, new EntityAIBeg(this, 8.0F));
        this.c.a(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.c.a(9, new EntityAILookIdle(this));
        this.d.a(1, new EntityAIOwnerHurtByTarget(this));
        this.d.a(2, new EntityAIOwnerHurtTarget(this));
        this.d.a(3, new EntityAIHurtByTarget(this, true));
        this.d.a(4, new EntityAITargetNonTamed(this, EntitySheep.class, 200, false));
        this.j(false);
        this.entity = new CanaryWolf(this); // CanaryMod: Wrap Entity
    }

    protected void aD() {
        super.aD();
        this.a(SharedMonsterAttributes.d).a(0.30000001192092896D);
        if (this.bZ()) {
            this.a(SharedMonsterAttributes.a).a(20.0D);
        }
        else {
            this.a(SharedMonsterAttributes.a).a(8.0D);
        }
    }

    public boolean bk() {
        return true;
    }

    public void d(EntityLivingBase entitylivingbase) {
        super.d(entitylivingbase);
        if (entitylivingbase == null) {
            this.l(false);
        }
        else if (!this.bZ()) {
            this.l(true);
        }
    }

    protected void bp() {
        this.af.b(18, Float.valueOf(this.aS()));
    }

    protected void c() {
        super.c();
        this.af.a(18, new Float(this.aS()));
        this.af.a(19, new Byte((byte) 0));
        this.af.a(20, new Byte((byte) BlockColored.b(1)));
    }

    protected void a(int i0, int i1, int i2, Block block) {
        this.a("mob.wolf.step", 0.15F, 1.0F);
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("Angry", this.ci());
        nbttagcompound.a("CollarColor", (byte) this.cj());
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.l(nbttagcompound.n("Angry"));
        if (nbttagcompound.b("CollarColor", 99)) {
            this.s(nbttagcompound.d("CollarColor"));
        }
    }

    protected String t() {
        return this.ci() ? "mob.wolf.growl" : (this.Z.nextInt(3) == 0 ? (this.bZ() && this.af.d(18) < 10.0F ? "mob.wolf.whine" : "mob.wolf.panting") : "mob.wolf.bark");
    }

    protected String aT() {
        return "mob.wolf.hurt";
    }

    protected String aU() {
        return "mob.wolf.death";
    }

    protected float bf() {
        return 0.4F;
    }

    protected Item u() {
        return Item.d(-1);
    }

    public void e() {
        super.e();
        if (!this.o.E && this.bs && !this.bt && !this.bS() && this.D) {
            this.bt = true;
            this.bu = 0.0F;
            this.bv = 0.0F;
            this.o.a(this, (byte) 8);
        }
    }

    public void h() {
        super.h();
        this.br = this.bq;
        if (this.ck()) {
            this.bq += (1.0F - this.bq) * 0.4F;
        }
        else {
            this.bq += (0.0F - this.bq) * 0.4F;
        }

        if (this.ck()) {
            this.g = 10;
        }

        if (this.L()) {
            this.bs = true;
            this.bt = false;
            this.bu = 0.0F;
            this.bv = 0.0F;
        }
        else if ((this.bs || this.bt) && this.bt) {
            if (this.bu == 0.0F) {
                this.a("mob.wolf.shake", this.bf(), (this.Z.nextFloat() - this.Z.nextFloat()) * 0.2F + 1.0F);
            }

            this.bv = this.bu;
            this.bu += 0.05F;
            if (this.bv >= 2.0F) {
                this.bs = false;
                this.bt = false;
                this.bv = 0.0F;
                this.bu = 0.0F;
            }

            if (this.bu > 0.4F) {
                float f0 = (float) this.C.b;
                int i0 = (int) (MathHelper.a((this.bu - 0.4F) * 3.1415927F) * 7.0F);

                for (int i1 = 0; i1 < i0; ++i1) {
                    float f1 = (this.Z.nextFloat() * 2.0F - 1.0F) * this.M * 0.5F;
                    float f2 = (this.Z.nextFloat() * 2.0F - 1.0F) * this.M * 0.5F;

                    this.o.a("splash", this.s + (double) f1, (double) (f0 + 0.8F), this.u + (double) f2, this.v, this.w, this.x);
                }
            }
        }
    }

    public float g() {
        return this.N * 0.8F;
    }

    public int x() {
        return this.ca() ? 20 : super.x();
    }

    public boolean a(DamageSource damagesource, float f0) {
        if (this.aw()) {
            return false;
        }
        else {
            Entity entity = damagesource.j();

            this.bp.a(false);
            if (entity != null && !(entity instanceof EntityPlayer) && !(entity instanceof EntityArrow)) {
                f0 = (f0 + 1.0F) / 2.0F;
            }

            return super.a(damagesource, f0);
        }
    }

    public boolean n(Entity entity) {
        int i0 = this.bZ() ? 4 : 2;

        return entity.a(DamageSource.a((EntityLivingBase) this), (float) i0);
    }

    public void j(boolean flag0) {
        super.j(flag0);
        if (flag0) {
            this.a(SharedMonsterAttributes.a).a(20.0D);
        }
        else {
            this.a(SharedMonsterAttributes.a).a(8.0D);
        }
    }

    public boolean a(EntityPlayer entityplayer) {
        ItemStack itemstack = entityplayer.bm.h();

        if (this.bZ()) {
            if (itemstack != null) {
                if (itemstack.b() instanceof ItemFood) {
                    ItemFood itemfood = (ItemFood) itemstack.b();

                    if (itemfood.i() && this.af.d(18) < 20.0F) {
                        if (!entityplayer.bE.d) {
                            --itemstack.b;
                        }

                        this.f((float) itemfood.g(itemstack));
                        if (itemstack.b <= 0) {
                            entityplayer.bm.a(entityplayer.bm.c, (ItemStack) null);
                        }

                        return true;
                    }
                }
                else if (itemstack.b() == Items.aR) {
                    int i0 = BlockColored.b(itemstack.k());

                    if (i0 != this.cj()) {
                        this.s(i0);
                        if (!entityplayer.bE.d && --itemstack.b <= 0) {
                            entityplayer.bm.a(entityplayer.bm.c, (ItemStack) null);
                        }

                        return true;
                    }
                }
            }

            if (this.e((EntityLivingBase) entityplayer) && !this.o.E && !this.c(itemstack)) {
                this.bp.a(!this.ca());
                this.bc = false;
                this.a((PathEntity) null);
                this.b((Entity) null);
                this.d((EntityLivingBase) null);
            }
        }
        else if (itemstack != null && itemstack.b() == Items.aS && !this.ci()) {
            if (!entityplayer.bE.d) {
                --itemstack.b;
            }

            if (itemstack.b <= 0) {
                entityplayer.bm.a(entityplayer.bm.c, (ItemStack) null);
            }

            if (!this.o.E) {
                // CanaryMod: EntityTame
                EntityTameHook hook = (EntityTameHook) new EntityTameHook((net.canarymod.api.entity.living.animal.EntityAnimal) this.getCanaryEntity(), ((EntityPlayerMP) entityplayer).getPlayer(), this.Z.nextInt(3) == 0).call();

                if (hook.isTamed() && !hook.isCanceled()) {
                    //
                    this.j(true);
                    this.a((PathEntity) null);
                    this.d((EntityLivingBase) null);
                    this.bp.a(true);
                    this.g(20.0F);
                    this.b(entityplayer.aB().toString());
                    this.i(true);
                    this.o.a(this, (byte) 7);
                }
                else {
                    this.i(false);
                    this.o.a(this, (byte) 6);
                }
            }

            return true;
        }

        return super.a(entityplayer);
    }

    public boolean c(ItemStack itemstack) {
        return itemstack == null ? false : (!(itemstack.b() instanceof ItemFood) ? false : ((ItemFood) itemstack.b()).i());
    }

    public int bB() {
        return 8;
    }

    public boolean ci() {
        return (this.af.a(16) & 2) != 0;
    }

    public void l(boolean flag0) {
        byte b0 = this.af.a(16);

        if (flag0) {
            this.af.b(16, Byte.valueOf((byte) (b0 | 2)));
        }
        else {
            this.af.b(16, Byte.valueOf((byte) (b0 & -3)));
        }
    }

    public int cj() {
        return this.af.a(20) & 15;
    }

    public void s(int i0) {
        this.af.b(20, Byte.valueOf((byte) (i0 & 15)));
    }

    public EntityWolf b(EntityAgeable entityageable) {
        EntityWolf entitywolf = new EntityWolf(this.o);
        String s0 = this.b();

        if (s0 != null && s0.trim().length() > 0) {
            entitywolf.b(s0);
            entitywolf.j(true);
        }

        return entitywolf;
    }

    public void m(boolean flag0) {
        if (flag0) {
            this.af.b(19, Byte.valueOf((byte) 1));
        }
        else {
            this.af.b(19, Byte.valueOf((byte) 0));
        }
    }

    public boolean a(EntityAnimal entityanimal) {
        if (entityanimal == this) {
            return false;
        }
        else if (!this.bZ()) {
            return false;
        }
        else if (!(entityanimal instanceof EntityWolf)) {
            return false;
        }
        else {
            EntityWolf entitywolf = (EntityWolf) entityanimal;

            return !entitywolf.bZ() ? false : (entitywolf.ca() ? false : this.ce() && entitywolf.ce());
        }
    }

    public boolean ck() {
        return this.af.a(19) == 1;
    }

    protected boolean v() {
        return !this.bZ() && this.aa > 2400;
    }

    public boolean a(EntityLivingBase entitylivingbase, EntityLivingBase entitylivingbase1) {
        if (!(entitylivingbase instanceof EntityCreeper) && !(entitylivingbase instanceof EntityGhast)) {
            if (entitylivingbase instanceof EntityWolf) {
                EntityWolf entitywolf = (EntityWolf) entitylivingbase;

                if (entitywolf.bZ() && entitywolf.cb() == entitylivingbase1) {
                    return false;
                }
            }

            return entitylivingbase instanceof EntityPlayer && entitylivingbase1 instanceof EntityPlayer && !((EntityPlayer) entitylivingbase1).a((EntityPlayer) entitylivingbase) ? false : !(entitylivingbase instanceof EntityHorse) || !((EntityHorse) entitylivingbase).cc();
        }
        else {
            return false;
        }
    }

    public EntityAgeable a(EntityAgeable entityageable) {
        return this.b(entityageable);
    }
}
