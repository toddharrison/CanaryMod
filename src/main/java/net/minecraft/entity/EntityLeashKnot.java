package net.minecraft.entity;

import net.canarymod.api.CanaryDamageSource;
import net.canarymod.api.DamageType;
import net.canarymod.api.entity.hanging.CanaryLeashKnot;
import net.canarymod.api.entity.hanging.HangingEntity;
import net.canarymod.api.entity.living.humanoid.Player;
import net.canarymod.hook.entity.HangingEntityDestroyHook;
import net.minecraft.block.BlockFence;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

public class EntityLeashKnot extends EntityHanging {

    public EntityLeashKnot(World world) {
        super(world);
        this.entity = new CanaryLeashKnot(this); // CanaryMod: wrap entity
    }

    public EntityLeashKnot(World world, BlockPos blockpos) {
        super(world, blockpos);
        this.b((double) blockpos.n() + 0.5D, (double) blockpos.o() + 0.5D, (double) blockpos.p() + 0.5D);
        float f0 = 0.125F;
        float f1 = 0.1875F;
        float f2 = 0.25F;

        this.a(new AxisAlignedBB(this.s - 0.1875D, this.t - 0.25D + 0.125D, this.u - 0.1875D, this.s + 0.1875D, this.t + 0.25D + 0.125D, this.u + 0.1875D));

        this.entity = new CanaryLeashKnot(this); // CanaryMod: wrap entity
    }

    protected void h() {
        super.h();
    }

    public void a(EnumFacing enumfacing) {}

    public int l() {
        return 9;
    }

    public int m() {
        return 9;
    }

    public float aR() {
        return -0.0625F;
    }

    //CanaryMod added logic to notify plugins of leash knots being destroyed
    public void b(Entity entity) {
        //CanaryMod start
        if (entity instanceof EntityPlayer) {
            new HangingEntityDestroyHook((HangingEntity) this.getCanaryEntity(), (Player) entity.getCanaryEntity(), CanaryDamageSource.getDamageSourceFromType(DamageType.GENERIC)).call();
        } else {
            new HangingEntityDestroyHook((HangingEntity) this.getCanaryEntity(), null, CanaryDamageSource.getDamageSourceFromType(DamageType.GENERIC)).call();
        }
        //CanaryMod end
    }

    public boolean d(NBTTagCompound nbttagcompound) {
        return false;
    }

    public void b(NBTTagCompound nbttagcompound) {}

    public void a(NBTTagCompound nbttagcompound) {}

    public boolean e(EntityPlayer entityplayer) {
        ItemStack itemstack = entityplayer.bz();
        boolean flag0 = false;
        double d0;
        List list;
        Iterator iterator;
        EntityLiving entityliving;

        if (itemstack != null && itemstack.b() == Items.cn && !this.o.D) {
            d0 = 7.0D;
            list = this.o.a(EntityLiving.class, new AxisAlignedBB(this.s - d0, this.t - d0, this.u - d0, this.s + d0, this.t + d0, this.u + d0));
            iterator = list.iterator();

            while (iterator.hasNext()) {
                entityliving = (EntityLiving) iterator.next();
                if (entityliving.cb() && entityliving.cc() == entityplayer) {
                    entityliving.a(this, true);
                    flag0 = true;
                }
            }
        }

        if (!this.o.D && !flag0) {
            this.J();
            if (entityplayer.by.d) {
                d0 = 7.0D;
                list = this.o.a(EntityLiving.class, new AxisAlignedBB(this.s - d0, this.t - d0, this.u - d0, this.s + d0, this.t + d0, this.u + d0));
                iterator = list.iterator();

                while (iterator.hasNext()) {
                    entityliving = (EntityLiving) iterator.next();
                    if (entityliving.cb() && entityliving.cc() == this) {
                        entityliving.a(true, false);
                    }
                }
            }
        }

        return true;
    }

    public boolean j() {
        return this.o.p(this.a).c() instanceof BlockFence;
    }

    public static EntityLeashKnot a(World world, BlockPos blockpos) {
        EntityLeashKnot entityleashknot = new EntityLeashKnot(world, blockpos);

        entityleashknot.n = true;
        world.d((Entity) entityleashknot);
        return entityleashknot;
    }

    public static EntityLeashKnot b(World world, BlockPos blockpos) {
        int i0 = blockpos.n();
        int i1 = blockpos.o();
        int i2 = blockpos.p();
        List list = world.a(EntityLeashKnot.class, new AxisAlignedBB((double) i0 - 1.0D, (double) i1 - 1.0D, (double) i2 - 1.0D, (double) i0 + 1.0D, (double) i1 + 1.0D, (double) i2 + 1.0D));
        Iterator iterator = list.iterator();

        EntityLeashKnot entityleashknot;

        do {
            if (!iterator.hasNext()) {
                return null;
            }
            entityleashknot = (EntityLeashKnot) iterator.next();
        } while (!entityleashknot.n().equals(blockpos));

        return entityleashknot;
    }
}
