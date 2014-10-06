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
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;


public class EntityPig extends EntityAnimal {

    private final EntityAIControlledByPlayer bk;

    public EntityPig(World world) {
        super(world);
        this.a(0.9F, 0.9F);
        ((PathNavigateGround) this.s()).a(true);
        this.i.a(0, new EntityAISwimming(this));
        this.i.a(1, new EntityAIPanic(this, 1.25D));
        this.i.a(2, this.bk = new EntityAIControlledByPlayer(this, 0.3F));
        this.i.a(3, new EntityAIMate(this, 1.0D));
        this.i.a(4, new EntityAITempt(this, 1.2D, Items.bY, false));
        this.i.a(4, new EntityAITempt(this, 1.2D, Items.bR, false));
        this.i.a(5, new EntityAIFollowParent(this, 1.1D));
        this.i.a(6, new EntityAIWander(this, 1.0D));
        this.i.a(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.i.a(8, new EntityAILookIdle(this));
        this.entity = new CanaryPig(this); // CanaryMod: Wrap Entity
    }


    protected void aW() {
        super.aW();
        this.a(SharedMonsterAttributes.a).a(10.0D);
        this.a(SharedMonsterAttributes.d).a(0.25D);
    }

    public boolean bV() {
        ItemStack itemstack = ((EntityPlayer) this.l).bz();

        return itemstack != null && itemstack.b() == Items.bY;
    }

    protected void h() {
        super.h();
        this.ac.a(16, Byte.valueOf((byte) 0));
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("Saddle", this.cj());
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.l(nbttagcompound.n("Saddle"));
    }

    protected String z() {
        return "mob.pig.say";
    }

    protected String bn() {
        return "mob.pig.say";
    }

    protected String bo() {
        return "mob.pig.death";
    }

    protected void a(BlockPos blockpos, Block block) {
        this.a("mob.pig.step", 0.15F, 1.0F);
    }

    public boolean a(EntityPlayer entityplayer) {
        if (super.a(entityplayer)) {
            return true;
        }
        else if (this.cj() && !this.o.D && (this.l == null || this.l == entityplayer)) {
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

    protected Item A() {
        return this.au() ? Items.am : Items.al;
    }

    protected void b(boolean flag0, int i0) {
        int i1 = this.V.nextInt(3) + 1 + this.V.nextInt(1 + i0);

        for (int i2 = 0; i2 < i1; ++i2) {
            if (this.au()) {
                this.a(Items.am, 1);
            }
            else {
                this.a(Items.al, 1);
            }
        }

        if (this.cj()) {
            this.a(Items.aA, 1);
        }

    }

    public boolean cj() {
        return (this.ac.a(16) & 1) != 0;
    }

    public void l(boolean flag0) {
        if (flag0) {
            this.ac.b(16, Byte.valueOf((byte) 1));
        }
        else {
            this.ac.b(16, Byte.valueOf((byte) 0));
        }

    }

    public void a(EntityLightningBolt entitylightningbolt) {
        if (!this.o.D) {
            EntityPigZombie entitypigzombie = new EntityPigZombie(this.o);

            entitypigzombie.c(0, new ItemStack(Items.B));
            entitypigzombie.b(this.s, this.t, this.u, this.y, this.z);
            this.o.d((Entity) entitypigzombie);
            this.J();
        }
    }

    public void e(float f0, float f1) {
        super.e(f0, f1);
        if (f0 > 5.0F && this.l instanceof EntityPlayer) {
            ((EntityPlayer) this.l).b((StatBase) AchievementList.u);
        }

    }

    public EntityPig b(EntityAgeable entityageable) {
        return new EntityPig(this.o);
    }

    public boolean d(ItemStack itemstack) {
        return itemstack != null && itemstack.b() == Items.bR;
    }

    public EntityAIControlledByPlayer ck() {
        return this.bk;
    }

    public EntityAgeable a(EntityAgeable entityageable) {
        return this.b(entityageable);
    }
}
