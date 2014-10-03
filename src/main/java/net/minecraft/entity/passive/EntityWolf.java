package net.minecraft.entity.passive;

import net.canarymod.api.entity.living.animal.CanaryWolf;
import net.canarymod.hook.entity.EntityTameHook;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBeg;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.google.common.base.Predicate;

public class EntityWolf extends EntityTameable {

    private float bm;
    private float bn;
    private boolean bo;
    private boolean bp;
    private float bq;
    private float br;
   
    public EntityWolf(World world) {
        super(world);
        this.a(0.6F, 0.8F);
        ((PathNavigateGround) this.s()).a(true);
        this.i.a(1, new EntityAISwimming(this));
        this.i.a(2, this.bk);
        this.i.a(3, new EntityAILeapAtTarget(this, 0.4F));
        this.i.a(4, new EntityAIAttackOnCollide(this, 1.0D, true));
        this.i.a(5, new EntityAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
        this.i.a(6, new EntityAIMate(this, 1.0D));
        this.i.a(7, new EntityAIWander(this, 1.0D));
        this.i.a(8, new EntityAIBeg(this, 8.0F));
        this.i.a(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.i.a(9, new EntityAILookIdle(this));
        this.bg.a(1, new EntityAIOwnerHurtByTarget(this));
        this.bg.a(2, new EntityAIOwnerHurtTarget(this));
        this.bg.a(3, new EntityAIHurtByTarget(this, true, new Class[0]));
        this.bg.a(4, new EntityAITargetNonTamed(this, EntityAnimal.class, false, new Predicate() {

            public boolean a(Entity p_a_1_) {
                return p_a_1_ instanceof EntitySheep || p_a_1_ instanceof EntityRabbit;
            }

            public boolean apply(Object p_apply_1_) {
                return this.a((Entity) p_apply_1_);
            }
        }));
        this.bg.a(5, new EntityAINearestAttackableTarget(this, EntitySkeleton.class, false));
        this.m(false);
        this.entity = new CanaryWolf(this); // CanaryMod: Wrap Entity
    }

    protected void aW() {
        super.aW();
        this.a(SharedMonsterAttributes.d).a(0.30000001192092896D);
        if (this.cj()) {
            this.a(SharedMonsterAttributes.a).a(20.0D);
        } else {
            this.a(SharedMonsterAttributes.a).a(8.0D);
        }

        this.bx().b(SharedMonsterAttributes.e);
        this.a(SharedMonsterAttributes.e).a(2.0D);
    }

    public void d(EntityLivingBase entitylivingbase) {
        super.d(entitylivingbase);
        if (entitylivingbase == null) {
            this.o(false);
        } else if (!this.cj()) {
            this.o(true);
        }

    }

    protected void E() {
        this.ac.b(18, Float.valueOf(this.bm()));
    }

    protected void h() {
        super.h();
        this.ac.a(18, new Float(this.bm()));
        this.ac.a(19, new Byte((byte) 0));
        this.ac.a(20, new Byte((byte) EnumDyeColor.RED.a()));
    }

    protected void a(BlockPos blockpos, Block block) {
        this.a("mob.wolf.step", 0.15F, 1.0F);
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("Angry", this.ct());
        nbttagcompound.a("CollarColor", (byte) this.cu().b());
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.o(nbttagcompound.n("Angry"));
        if (nbttagcompound.b("CollarColor", 99)) {
            this.a(EnumDyeColor.a(nbttagcompound.d("CollarColor")));
        }

    }

    protected String z() {
        return this.ct() ? "mob.wolf.growl" : (this.V.nextInt(3) == 0 ? (this.cj() && this.ac.d(18) < 10.0F ? "mob.wolf.whine" : "mob.wolf.panting") : "mob.wolf.bark");
    }

    protected String bn() {
        return "mob.wolf.hurt";
    }

    protected String bo() {
        return "mob.wolf.death";
    }

    protected float bA() {
        return 0.4F;
    }

    protected Item A() {
        return Item.b(-1);
    }

    public void m() {
        super.m();
        if (!this.o.D && this.bo && !this.bp && !this.cd() && this.C) {
            this.bp = true;
            this.bq = 0.0F;
            this.br = 0.0F;
            this.o.a((Entity) this, (byte) 8);
        }

        if (!this.o.D && this.u() == null && this.ct()) {
            this.o(false);
        }

    }

    public void s_() {
        super.s_();
        this.bn = this.bm;
        if (this.cv()) {
            this.bm += (1.0F - this.bm) * 0.4F;
        } else {
            this.bm += (0.0F - this.bm) * 0.4F;
        }

        if (this.U()) {
            this.bo = true;
            this.bp = false;
            this.bq = 0.0F;
            this.br = 0.0F;
        } else if ((this.bo || this.bp) && this.bp) {
            if (this.bq == 0.0F) {
                this.a("mob.wolf.shake", this.bA(), (this.V.nextFloat() - this.V.nextFloat()) * 0.2F + 1.0F);
            }

            this.br = this.bq;
            this.bq += 0.05F;
            if (this.br >= 2.0F) {
                this.bo = false;
                this.bp = false;
                this.br = 0.0F;
                this.bq = 0.0F;
            }

            if (this.bq > 0.4F) {
                float f0 = (float) this.aQ().b;
                int i0 = (int) (MathHelper.a((this.bq - 0.4F) * 3.1415927F) * 7.0F);

                for (int i1 = 0; i1 < i0; ++i1) {
                    float f1 = (this.V.nextFloat() * 2.0F - 1.0F) * this.J * 0.5F;
                    float f2 = (this.V.nextFloat() * 2.0F - 1.0F) * this.J * 0.5F;

                    this.o.a(EnumParticleTypes.WATER_SPLASH, this.s + (double) f1, (double) (f0 + 0.8F), this.u + (double) f2, this.v, this.w, this.x, new int[0]);
                }
            }
        }

    }

    public float aR() {
        return this.K * 0.8F;
    }

    public int bP() {
        return this.cl() ? 20 : super.bP();
    }

    public boolean a(DamageSource damagesource, float f0) {
        if (this.b(damagesource)) {
            return false;
        } else {
            Entity entity = damagesource.j();

            this.bk.a(false);
            if (entity != null && !(entity instanceof EntityPlayer) && !(entity instanceof EntityArrow)) {
                f0 = (f0 + 1.0F) / 2.0F;
            }

            return super.a(damagesource, f0);
        }
    }

    public boolean r(Entity entity) {
        boolean flag0 = entity.a(DamageSource.a((EntityLivingBase) this), (float) ((int) this.a(SharedMonsterAttributes.e).e()));

        if (flag0) {
            this.a((EntityLivingBase) this, entity);
        }

        return flag0;
    }

    public void m(boolean flag0) {
        super.m(flag0);
        if (flag0) {
            this.a(SharedMonsterAttributes.a).a(20.0D);
        } else {
            this.a(SharedMonsterAttributes.a).a(8.0D);
        }

        this.a(SharedMonsterAttributes.e).a(4.0D);
    }

    public boolean a(EntityPlayer entityplayer) {
        ItemStack itemstack = entityplayer.bg.h();

        if (this.cj()) {
            if (itemstack != null) {
                if (itemstack.b() instanceof ItemFood) {
                    ItemFood itemfood = (ItemFood) itemstack.b();

                    if (itemfood.g() && this.ac.d(18) < 20.0F) {
                        if (!entityplayer.by.d) {
                            --itemstack.b;
                        }

                        this.g((float) itemfood.h(itemstack));
                        if (itemstack.b <= 0) {
                            entityplayer.bg.a(entityplayer.bg.c, (ItemStack) null);
                        }

                        return true;
                    }
                } else if (itemstack.b() == Items.aW) {
                    EnumDyeColor enumdyecolor = EnumDyeColor.a(itemstack.i());

                    if (enumdyecolor != this.cu()) {
                        this.a(enumdyecolor);
                        if (!entityplayer.by.d && --itemstack.b <= 0) {
                            entityplayer.bg.a(entityplayer.bg.c, (ItemStack) null);
                        }

                        return true;
                    }
                }
            }

            if (this.e(entityplayer) && !this.o.D && !this.d(itemstack)) {
                this.bk.a(!this.cl());
                this.aW = false;
                this.h.n();
                this.d((EntityLivingBase) null);
            }
        } else if (itemstack != null && itemstack.b() == Items.aX && !this.ct()) {
            if (!entityplayer.by.d) {
                --itemstack.b;
            }

            if (itemstack.b <= 0) {
                entityplayer.bg.a(entityplayer.bg.c, (ItemStack) null);
            }

            if (!this.o.D) {
                // CanaryMod: EntityTame
                EntityTameHook hook = (EntityTameHook) new EntityTameHook((net.canarymod.api.entity.living.animal.EntityAnimal) this.getCanaryEntity(), ((EntityPlayerMP) entityplayer).getPlayer(), this.Z.nextInt(3) == 0).call();

                if (hook.isTamed() && !hook.isCanceled()) {
                    //
                    this.m(true);
                    this.h.n();
                    this.d((EntityLivingBase) null);
                    this.bk.a(true);
                    this.h(20.0F);
                    this.b(entityplayer.aJ().toString());
                    this.l(true);
                    this.o.a((Entity) this, (byte) 7);
                } else {
                    this.l(false);
                    this.o.a((Entity) this, (byte) 6);
                }
            }

            return true;
        }

        return super.a(entityplayer);
    }

    public boolean d(ItemStack itemstack) {
        return itemstack == null ? false : (!(itemstack.b() instanceof ItemFood) ? false : ((ItemFood) itemstack.b()).g());
    }

    public int bU() {
        return 8;
    }

    public boolean ct() {
        return (this.ac.a(16) & 2) != 0;
    }

    public void o(boolean flag0) {
        byte b0 = this.ac.a(16);

        if (flag0) {
            this.ac.b(16, Byte.valueOf((byte) (b0 | 2)));
        } else {
            this.ac.b(16, Byte.valueOf((byte) (b0 & -3)));
        }

    }

    public EnumDyeColor cu() {
        return EnumDyeColor.a(this.ac.a(20) & 15);
    }

    public void a(EnumDyeColor enumdyecolor) {
        this.ac.b(20, Byte.valueOf((byte) (enumdyecolor.b() & 15)));
    }

    public EntityWolf b(EntityAgeable entityageable) {
        EntityWolf entitywolf = new EntityWolf(this.o);
        String s0 = this.b();

        if (s0 != null && s0.trim().length() > 0) {
            entitywolf.b(s0);
            entitywolf.m(true);
        }

        return entitywolf;
    }

    public void p(boolean flag0) {
        if (flag0) {
            this.ac.b(19, Byte.valueOf((byte) 1));
        } else {
            this.ac.b(19, Byte.valueOf((byte) 0));
        }

    }

    public boolean a(EntityAnimal entityanimal) {
        if (entityanimal == this) {
            return false;
        } else if (!this.cj()) {
            return false;
        } else if (!(entityanimal instanceof EntityWolf)) {
            return false;
        } else {
            EntityWolf entitywolf = (EntityWolf) entityanimal;

            return !entitywolf.cj() ? false : (entitywolf.cl() ? false : this.cp() && entitywolf.cp());
        }
    }

    public boolean cv() {
        return this.ac.a(19) == 1;
    }

    protected boolean C() {
        return !this.cj() && this.W > 2400;
    }

    public boolean a(EntityLivingBase entitylivingbase, EntityLivingBase entitylivingbase1) {
        if (!(entitylivingbase instanceof EntityCreeper) && !(entitylivingbase instanceof EntityGhast)) {
            if (entitylivingbase instanceof EntityWolf) {
                EntityWolf entitywolf = (EntityWolf) entitylivingbase;

                if (entitywolf.cj() && entitywolf.cm() == entitylivingbase1) {
                    return false;
                }
            }

            return entitylivingbase instanceof EntityPlayer && entitylivingbase1 instanceof EntityPlayer && !((EntityPlayer) entitylivingbase1).a((EntityPlayer) entitylivingbase) ? false : !(entitylivingbase instanceof EntityHorse) || !((EntityHorse) entitylivingbase).cm();
        } else {
            return false;
        }
    }

    public boolean ca() {
        return !this.ct() && super.ca();
    }

    public EntityAgeable a(EntityAgeable entityageable) {
        return this.b(entityageable);
    }
}
