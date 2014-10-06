package net.minecraft.entity.passive;

import com.google.common.base.Predicate;
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
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityOcelot extends EntityTameable {

    private EntityAIAvoidEntity bm;
    private EntityAITempt bn;

    public EntityOcelot(World world) {
        super(world);
        this.a(0.6F, 0.7F);
        ((PathNavigateGround) this.s()).a(true);
        this.i.a(1, new EntityAISwimming(this));
        this.i.a(2, this.bk);
        this.i.a(3, this.bn = new EntityAITempt(this, 0.6D, Items.aU, true));
        this.i.a(5, new EntityAIFollowOwner(this, 1.0D, 10.0F, 5.0F));
        this.i.a(6, new EntityAIOcelotSit(this, 0.8D));
        this.i.a(7, new EntityAILeapAtTarget(this, 0.3F));
        this.i.a(8, new EntityAIOcelotAttack(this));
        this.i.a(9, new EntityAIMate(this, 0.8D));
        this.i.a(10, new EntityAIWander(this, 0.8D));
        this.i.a(11, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
        this.bg.a(1, new EntityAITargetNonTamed(this, EntityChicken.class, false, (Predicate) null));
        this.entity = new CanaryOcelot(this); // CanaryMod: Wrap Entity
    }

    protected void h() {
        super.h();
        this.ac.a(18, Byte.valueOf((byte) 0));
    }

    public void E() {
        if (this.q().a()) {
            double d0 = this.q().b();

            if (d0 == 0.6D) {
                this.c(true);
                this.d(false);
            }
            else if (d0 == 1.33D) {
                this.c(false);
                this.d(true);
            }
            else {
                this.c(false);
                this.d(false);
            }
        }
        else {
            this.c(false);
            this.d(false);
        }

    }

    protected boolean C() {
        return !this.cj() && this.W > 2400;
    }

    protected void aW() {
        super.aW();
        this.a(SharedMonsterAttributes.a).a(10.0D);
        this.a(SharedMonsterAttributes.d).a(0.30000001192092896D);
    }

    public void e(float f0, float f1) {
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("CatType", this.cr());
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.r(nbttagcompound.f("CatType"));
    }

    protected String z() {
        return this.cj() ? (this.cp() ? "mob.cat.purr" : (this.V.nextInt(4) == 0 ? "mob.cat.purreow" : "mob.cat.meow")) : "";
    }

    protected String bn() {
        return "mob.cat.hitt";
    }

    protected String bo() {
        return "mob.cat.hitt";
    }

    protected float bA() {
        return 0.4F;
    }

    protected Item A() {
        return Items.aF;
    }

    public boolean r(Entity entity) {
        return entity.a(DamageSource.a((EntityLivingBase) this), 3.0F);
    }

    public boolean a(DamageSource damagesource, float f0) {
        if (this.b(damagesource)) {
            return false;
        }
        else {
            this.bk.a(false);
            return super.a(damagesource, f0);
        }
    }

    protected void b(boolean flag0, int i0) {
    }

    public boolean a(EntityPlayer entityplayer) {
        ItemStack itemstack = entityplayer.bg.h();

        if (this.cj()) {
            if (this.e(entityplayer) && !this.o.D && !this.d(itemstack)) {
                this.bk.a(!this.cl());
            }
        }
        else if (this.bn.f() && itemstack != null && itemstack.b() == Items.aU && entityplayer.h(this) < 9.0D) {
            if (!entityplayer.by.d) {
                --itemstack.b;
            }

            if (itemstack.b <= 0) {
                entityplayer.bg.a(entityplayer.bg.c, (ItemStack) null);
            }

            if (!this.o.D) {
                // CanaryMod: EntityTame
                EntityTameHook hook = (EntityTameHook) new EntityTameHook((net.canarymod.api.entity.living.animal.EntityAnimal) this.getCanaryEntity(), ((EntityPlayerMP) entityplayer).getPlayer(), this.V.nextInt(3) == 0).call();

                if (hook.isTamed() && !hook.isCanceled()) {
                    //
                    this.m(true);
                    this.r(1 + this.o.s.nextInt(3));
                    this.b(entityplayer.aJ().toString());
                    this.l(true);
                    this.bk.a(true);
                    this.o.a((Entity) this, (byte) 7);
                }
                else {
                    this.l(false);
                    this.o.a((Entity) this, (byte) 6);
                }
            }

            return true;
        }

        return super.a(entityplayer);
    }

    public EntityOcelot b(EntityAgeable entityageable) {
        EntityOcelot entityocelot = new EntityOcelot(this.o);

        if (this.cj()) {
            entityocelot.b(this.b());
            entityocelot.m(true);
            entityocelot.r(this.cr());
        }

        return entityocelot;
    }

    public boolean d(ItemStack itemstack) {
        return itemstack != null && itemstack.b() == Items.aU;
    }

    public boolean a(EntityAnimal entityanimal) {
        if (entityanimal == this) {
            return false;
        }
        else if (!this.cj()) {
            return false;
        }
        else if (!(entityanimal instanceof EntityOcelot)) {
            return false;
        }
        else {
            EntityOcelot entityocelot = (EntityOcelot) entityanimal;

            return !entityocelot.cj() ? false : this.cp() && entityocelot.cp();
        }
    }

    public int cr() {
        return this.ac.a(18);
    }

    public void r(int i0) {
        this.ac.b(18, Byte.valueOf((byte) i0));
    }

    public boolean bQ() {
        return this.o.s.nextInt(3) != 0;
    }

    public boolean bR() {
        if (this.o.a(this.aQ(), (Entity) this) && this.o.a((Entity) this, this.aQ()).isEmpty() && !this.o.d(this.aQ())) {
            BlockPos blockpos = new BlockPos(this.s, this.aQ().b, this.u);

            if (blockpos.o() < 63) {
                return false;
            }

            Block block = this.o.p(blockpos.b()).c();

            if (block == Blocks.c || block.r() == Material.j) {
                return true;
            }
        }

        return false;
    }

    public String d_() {
        return this.k_() ? this.aL() : (this.cj() ? StatCollector.a("entity.Cat.name") : super.d_());
    }

    public void m(boolean flag0) {
        super.m(flag0);
    }

    protected void ck() {
        if (this.bm == null) {
            this.bm = new EntityAIAvoidEntity(this, new Predicate() {

                public boolean a(Entity p_a_1_) {
                    return p_a_1_ instanceof EntityPlayer;
                }

                public boolean apply(Object p_apply_1_) {
                    return this.a((Entity) p_apply_1_);
                }
            }, 16.0F, 0.8D, 1.33D);
        }

        this.i.a((EntityAIBase) this.bm);
        if (!this.cj()) {
            this.i.a(4, this.bm);
        }

    }

    public IEntityLivingData a(DifficultyInstance difficultyinstance, IEntityLivingData ientitylivingdata) {
        ientitylivingdata = super.a(difficultyinstance, ientitylivingdata);
        if (this.o.s.nextInt(7) == 0) {
            for (int i0 = 0; i0 < 2; ++i0) {
                EntityOcelot entityocelot = new EntityOcelot(this.o);

                entityocelot.b(this.s, this.t, this.u, this.y, 0.0F);
                entityocelot.b(-24000);
                this.o.d((Entity) entityocelot);
            }
        }

        return ientitylivingdata;
    }

    public EntityAgeable a(EntityAgeable entityageable) {
        return this.b(entityageable);
    }
}
