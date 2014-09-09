package net.minecraft.entity.passive;

import net.canarymod.api.entity.living.animal.CanaryOcelot;
import net.canarymod.hook.entity.EntityTameHook;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
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
        this.af.a(18, Byte.valueOf((byte) 0));
    }

    public void bp() {
        if (this.k().a()) {
            double d0 = this.k().b();

            if (d0 == 0.6D) {
                this.b(true);
                this.c(false);
            } else if (d0 == 1.33D) {
                this.b(false);
                this.c(true);
            } else {
                this.b(false);
                this.c(false);
            }
        } else {
            this.b(false);
            this.c(false);
        }
    }

    protected boolean v() {
        return !this.bZ() && this.aa > 2400;
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
        nbttagcompound.a("CatType", this.cg());
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.s(nbttagcompound.f("CatType"));
    }

    protected String t() {
        return this.bZ() ? (this.ce() ? "mob.cat.purr" : (this.Z.nextInt(4) == 0 ? "mob.cat.purreow" : "mob.cat.meow")) : "";
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

    public boolean n(Entity entity) {
        return entity.a(DamageSource.a((EntityLivingBase) this), 3.0F);
    }

    public boolean a(DamageSource damagesource, float f0) {
        if (this.aw()) {
            return false;
        } else {
            this.bp.a(false);
            return super.a(damagesource, f0);
        }
    }

    protected void b(boolean flag0, int i0) {
    }

    public boolean a(EntityPlayer entityplayer) {
        ItemStack itemstack = entityplayer.bm.h();

        if (this.bZ()) {
            if (this.e((EntityLivingBase) entityplayer) && !this.o.E && !this.c(itemstack)) {
                this.bp.a(!this.ca());
            }
        } else if (this.bq.f() && itemstack != null && itemstack.b() == Items.aP && entityplayer.f(this) < 9.0D) {
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
                    this.s(1 + this.o.s.nextInt(3));
                    this.b(entityplayer.aB().toString());
                    this.i(true);
                    this.bp.a(true);
                    this.o.a(this, (byte) 7);
                } else {
                    this.i(false);
                    this.o.a(this, (byte) 6);
                }
            }

            return true;
        }

        return super.a(entityplayer);
    }

    public EntityOcelot b(EntityAgeable entityageable) {
        EntityOcelot entityocelot = new EntityOcelot(this.o);

        if (this.bZ()) {
            entityocelot.b(this.b());
            entityocelot.j(true);
            entityocelot.s(this.cg());
        }

        return entityocelot;
    }

    public boolean c(ItemStack itemstack) {
        return itemstack != null && itemstack.b() == Items.aP;
    }

    public boolean a(EntityAnimal entityanimal) {
        if (entityanimal == this) {
            return false;
        } else if (!this.bZ()) {
            return false;
        } else if (!(entityanimal instanceof EntityOcelot)) {
            return false;
        } else {
            EntityOcelot entityocelot = (EntityOcelot) entityanimal;

            return !entityocelot.bZ() ? false : this.ce() && entityocelot.ce();
        }
    }

    public int cg() {
        return this.af.a(18);
    }

    public void s(int i0) {
        this.af.b(18, Byte.valueOf((byte) i0));
    }

    public boolean by() {
        if (this.o.s.nextInt(3) == 0) {
            return false;
        } else {
            if (this.o.b(this.C) && this.o.a((Entity) this, this.C).isEmpty() && !this.o.d(this.C)) {
                int i0 = MathHelper.c(this.s);
                int i1 = MathHelper.c(this.C.b);
                int i2 = MathHelper.c(this.u);

                if (i1 < 63) {
                    return false;
                }

                Block block = this.o.a(i0, i1 - 1, i2);

                if (block == Blocks.c || block.o() == Material.j) {
                    return true;
                }
            }

            return false;
        }
    }

    public String b_() {
        return this.bH() ? this.bG() : (this.bZ() ? StatCollector.a("entity.Cat.name") : super.b_());
    }

    public IEntityLivingData a(IEntityLivingData ientitylivingdata) {
        ientitylivingdata = super.a(ientitylivingdata);
        if (this.o.s.nextInt(7) == 0) {
            for (int i0 = 0; i0 < 2; ++i0) {
                EntityOcelot entityocelot = new EntityOcelot(this.o);

                entityocelot.b(this.s, this.t, this.u, this.y, 0.0F);
                entityocelot.c(-24000);
                this.o.d((Entity) entityocelot);
            }
        }

        return ientitylivingdata;
    }

    public EntityAgeable a(EntityAgeable entityageable) {
        return this.b(entityageable);
    }
}
