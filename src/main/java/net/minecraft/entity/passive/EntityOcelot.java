package net.minecraft.entity.passive;

import net.canarymod.api.entity.living.animal.CanaryOcelot;
import net.canarymod.hook.entity.EntityTameHook;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIOcelotAttack;
import net.minecraft.entity.ai.EntityAIOcelotSit;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class EntityOcelot extends EntityTameable {

    private EntityAITempt bq;

    public EntityOcelot(World world) {
        super(world);
        this.a(0.6F, 0.8F);
        this.m().a(true);
        this.c.a(1, new EntityAISwimming(this));
        this.c.a(2, this.bp);
        this.c.a(3, this.bq = new EntityAITempt(this, 0.6D, Items.aP, true));
        this.c.a(4, new EntityAIAvoidEntity(this, EntityPlayer.class, 16.0F, 0.8D, 1.33D));
        this.c.a(5, new EntityAIFollowOwner(this, 1.0D, 10.0F, 5.0F));
        this.c.a(6, new EntityAIOcelotSit(this, 1.33D));
        this.c.a(7, new EntityAILeapAtTarget(this, 0.3F));
        this.c.a(8, new EntityAIOcelotAttack(this));
        this.c.a(9, new EntityAIMate(this, 0.8D));
        this.c.a(10, new EntityAIWander(this, 0.8D));
        this.c.a(11, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
        this.d.a(1, new EntityAITargetNonTamed(this, EntityChicken.class, 750, false));
        this.entity = new CanaryOcelot(this); // CanaryMod: Wrap Entity
    }

    protected void c() {
        super.c();
        this.ag.a(18, Byte.valueOf((byte) 0));
    }

    public void bp() {
        if (this.k().a()) {
            double d0 = this.k().b();

            if (d0 == 0.6D) {
                this.b(true);
                this.c(false);
            }
            else if (d0 == 1.33D) {
                this.b(false);
                this.c(true);
            }
            else {
                this.b(false);
                this.c(false);
            }
        }
        else {
            this.b(false);
            this.c(false);
        }
    }

    protected boolean v() {
        return !this.bX() && this.ab > 2400;
    }

    public boolean bk() {
        return true;
    }

    protected void aD() {
        super.aD();
        this.a(SharedMonsterAttributes.a).a(10.0D);
        this.a(SharedMonsterAttributes.d).a(0.30000001192092896D);
    }

    protected void b(float f0) {
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("CatType", this.ce());
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.s(nbttagcompound.f("CatType"));
    }

    protected String t() {
        return this.bX() ? (this.cc() ? "mob.cat.purr" : (this.aa.nextInt(4) == 0 ? "mob.cat.purreow" : "mob.cat.meow")) : "";
    }

    protected String aT() {
        return "mob.cat.hitt";
    }

    protected String aU() {
        return "mob.cat.hitt";
    }

    protected float bf() {
        return 0.4F;
    }

    protected Item u() {
        return Items.aA;
    }

    public boolean m(Entity entity) {
        return entity.a(DamageSource.a((EntityLivingBase) this), 3.0F);
    }

    public boolean a(DamageSource damagesource, float f0) {
        if (this.aw()) {
            return false;
        }
        else {
            this.bp.a(false);
            return super.a(damagesource, f0);
        }
    }

    protected void b(boolean flag0, int i0) {
    }

    public boolean a(EntityPlayer entityplayer) {
        ItemStack itemstack = entityplayer.bn.h();

        if (this.bX()) {
            if (entityplayer.b_().equalsIgnoreCase(this.b()) && !this.p.E && !this.c(itemstack)) {
                this.bp.a(!this.bY());
            }
        }
        else if (this.bq.f() && itemstack != null && itemstack.b() == Items.aP && entityplayer.e(this) < 9.0D) {
            if (!entityplayer.bF.d) {
                --itemstack.b;
            }

            if (itemstack.b <= 0) {
                entityplayer.bn.a(entityplayer.bn.c, (ItemStack) null);
            }

            if (!this.p.E) {
                // CanaryMod: EntityTame
                EntityTameHook hook = new EntityTameHook((net.canarymod.api.entity.living.animal.EntityAnimal) this.getCanaryEntity(), ((EntityPlayerMP) entityplayer).getPlayer(), this.aa.nextInt(3) == 0);

                if (hook.isTamed() && !hook.isCanceled()) {
                    //
                    this.j(true);
                    this.s(1 + this.p.s.nextInt(3));
                    this.b(entityplayer.b_());
                    this.i(true);
                    this.bp.a(true);
                    this.p.a(this, (byte) 7);
                }
                else {
                    this.i(false);
                    this.p.a(this, (byte) 6);
                }
            }

            return true;
        }

        return super.a(entityplayer);
    }

    public EntityOcelot b(EntityAgeable entityageable) {
        EntityOcelot entityocelot = new EntityOcelot(this.p);

        if (this.bX()) {
            entityocelot.b(this.b());
            entityocelot.j(true);
            entityocelot.s(this.ce());
        }

        return entityocelot;
    }

    public boolean c(ItemStack itemstack) {
        return itemstack != null && itemstack.b() == Items.aP;
    }

    public boolean a(EntityAnimal entityanimal) {
        if (entityanimal == this) {
            return false;
        }
        else if (!this.bX()) {
            return false;
        }
        else if (!(entityanimal instanceof EntityOcelot)) {
            return false;
        }
        else {
            EntityOcelot entityocelot = (EntityOcelot) entityanimal;

            return !entityocelot.bX() ? false : this.cc() && entityocelot.cc();
        }
    }

    public int ce() {
        return this.ag.a(18);
    }

    public void s(int i0) {
        this.ag.b(18, Byte.valueOf((byte) i0));
    }

    public boolean bw() {
        if (this.p.s.nextInt(3) == 0) {
            return false;
        }
        else {
            if (this.p.b(this.D) && this.p.a((Entity) this, this.D).isEmpty() && !this.p.d(this.D)) {
                int i0 = MathHelper.c(this.t);
                int i1 = MathHelper.c(this.D.b);
                int i2 = MathHelper.c(this.v);

                if (i1 < 63) {
                    return false;
                }

                Block block = this.p.a(i0, i1 - 1, i2);

                if (block == Blocks.c || block.o() == Material.j) {
                    return true;
                }
            }

            return false;
        }
    }

    public String b_() {
        return this.bF() ? this.bE() : (this.bX() ? StatCollector.a("entity.Cat.name") : super.b_());
    }

    public EntityLivingData a(EntityLivingData entitylivingdata) {
        entitylivingdata = super.a(entitylivingdata);
        if (this.p.s.nextInt(7) == 0) {
            for (int i0 = 0; i0 < 2; ++i0) {
                EntityOcelot entityocelot = new EntityOcelot(this.p);

                entityocelot.b(this.t, this.u, this.v, this.z, 0.0F);
                entityocelot.c(-24000);
                this.p.d((Entity) entityocelot);
            }
        }

        return entitylivingdata;
    }

    public EntityAgeable a(EntityAgeable entityageable) {
        return this.b(entityageable);
    }
}
