package net.minecraft.entity.passive;

import net.canarymod.api.entity.living.animal.CanaryPig;
import net.canarymod.api.entity.vehicle.Vehicle;
import net.canarymod.hook.CancelableHook;
import net.canarymod.hook.entity.VehicleEnterHook;
import net.canarymod.hook.entity.VehicleExitHook;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.world.World;

public class EntityPig extends EntityAnimal {

    private final EntityAIControlledByPlayer bp;

    public EntityPig(World world) {
        super(world);
        this.a(0.9F, 0.9F);
        this.m().a(true);
        this.c.a(0, new EntityAISwimming(this));
        this.c.a(1, new EntityAIPanic(this, 1.25D));
        this.c.a(2, this.bp = new EntityAIControlledByPlayer(this, 0.3F));
        this.c.a(3, new EntityAIMate(this, 1.0D));
        this.c.a(4, new EntityAITempt(this, 1.2D, Items.bM, false));
        this.c.a(4, new EntityAITempt(this, 1.2D, Items.bF, false));
        this.c.a(5, new EntityAIFollowParent(this, 1.1D));
        this.c.a(6, new EntityAIWander(this, 1.0D));
        this.c.a(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.c.a(8, new EntityAILookIdle(this));
        this.entity = new CanaryPig(this); // CanaryMod: Wrap Entity
    }

    public boolean bk() {
        return true;
    }

    protected void aD() {
        super.aD();
        this.a(SharedMonsterAttributes.a).a(10.0D);
        this.a(SharedMonsterAttributes.d).a(0.25D);
    }

    protected void bn() {
        super.bn();
    }

    public boolean bE() {
        ItemStack itemstack = ((EntityPlayer) this.l).be();

        return itemstack != null && itemstack.b() == Items.bM;
    }

    protected void c() {
        super.c();
        this.af.a(16, Byte.valueOf((byte) 0));
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("Saddle", this.bZ());
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.i(nbttagcompound.n("Saddle"));
    }

    protected String t() {
        return "mob.pig.say";
    }

    protected String aT() {
        return "mob.pig.say";
    }

    protected String aU() {
        return "mob.pig.death";
    }

    protected void a(int i0, int i1, int i2, Block block) {
        this.a("mob.pig.step", 0.15F, 1.0F);
    }

    public boolean a(EntityPlayer entityplayer) {
        if (super.a(entityplayer)) {
            return true;
        }
        else if (this.bZ() && !this.o.E && (this.l == null || this.l == entityplayer)) {
            // CanaryMod: VehicleEnter/VehicleExit
            CancelableHook hook;
            if (this.l == null) {
                hook = new VehicleEnterHook((Vehicle) this.entity, entityplayer.getCanaryHuman());
            }
            else {
                hook = new VehicleExitHook((Vehicle) this.entity, entityplayer.getCanaryHuman());
            }
            hook.call();
            if (!hook.isCanceled()) {
                entityplayer.a((Entity) this);
            }
            //
            return true;
        }
        else {
            return false;
        }
    }

    protected Item u() {
        return this.al() ? Items.am : Items.al;
    }

    protected void b(boolean flag0, int i0) {
        int i1 = this.Z.nextInt(3) + 1 + this.Z.nextInt(1 + i0);

        for (int i2 = 0; i2 < i1; ++i2) {
            if (this.al()) {
                this.a(Items.am, 1);
            }
            else {
                this.a(Items.al, 1);
            }
        }

        if (this.bZ()) {
            this.a(Items.av, 1);
        }
    }

    public boolean bZ() {
        return (this.af.a(16) & 1) != 0;
    }

    public void i(boolean flag0) {
        if (flag0) {
            this.af.b(16, Byte.valueOf((byte) 1));
        }
        else {
            this.af.b(16, Byte.valueOf((byte) 0));
        }
    }

    public void a(EntityLightningBolt entitylightningbolt) {
        if (!this.o.E) {
            EntityPigZombie entitypigzombie = new EntityPigZombie(this.o);

            entitypigzombie.c(0, new ItemStack(Items.B));
            entitypigzombie.b(this.s, this.t, this.u, this.y, this.z);
            this.o.d((Entity) entitypigzombie);
            this.B();
        }
    }

    protected void b(float f0) {
        super.b(f0);
        if (f0 > 5.0F && this.l instanceof EntityPlayer) {
            ((EntityPlayer) this.l).a((StatBase) AchievementList.u);
        }
    }

    public EntityPig b(EntityAgeable entityageable) {
        return new EntityPig(this.o);
    }

    public boolean c(ItemStack itemstack) {
        return itemstack != null && itemstack.b() == Items.bF;
    }

    public EntityAIControlledByPlayer ca() {
        return this.bp;
    }

    public EntityAgeable a(EntityAgeable entityageable) {
        return this.b(entityageable);
    }
}
