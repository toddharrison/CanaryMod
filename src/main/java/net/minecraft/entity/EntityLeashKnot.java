package net.minecraft.entity;

import net.canarymod.api.CanaryDamageSource;
import net.canarymod.api.DamageType;
import net.canarymod.api.entity.hanging.CanaryLeashKnot;
import net.canarymod.api.entity.hanging.HangingEntity;
import net.canarymod.api.entity.living.humanoid.Player;
import net.canarymod.hook.entity.HangingEntityDestroyHook;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

public class EntityLeashKnot extends EntityHanging {

    public EntityLeashKnot(World world) {
        super(world);
        this.entity = new CanaryLeashKnot(this); // CanaryMod: wrap entity
    }

    public EntityLeashKnot(World world, int i0, int i1, int i2) {
        super(world, i0, i1, i2, 0);
        this.b((double) i0 + 0.5D, (double) i1 + 0.5D, (double) i2 + 0.5D);
        this.entity = new CanaryLeashKnot(this); // CanaryMod: wrap entity
    }

    protected void c() {
        super.c();
    }

    public void a(int i0) {
    }

    public int f() {
        return 9;
    }

    public int i() {
        return 9;
    }

    //CanaryMod added logic to notify plugins of leash knots being destroyed
    public void b(Entity entity) {
        //CanaryMod start
        if (entity instanceof EntityPlayer) {
            new HangingEntityDestroyHook((HangingEntity) this.getCanaryEntity(), (Player) entity.getCanaryEntity(), CanaryDamageSource.getDamageSourceFromType(DamageType.GENERIC)).call();
        }
        else {
            new HangingEntityDestroyHook((HangingEntity) this.getCanaryEntity(), null, CanaryDamageSource.getDamageSourceFromType(DamageType.GENERIC)).call();
        }
        //CanaryMod end
    }

    public boolean d(NBTTagCompound nbttagcompound) {
        return false;
    }

    public void b(NBTTagCompound nbttagcompound) {
    }

    public void a(NBTTagCompound nbttagcompound) {
    }

    public boolean c(EntityPlayer entityplayer) {
        ItemStack itemstack = entityplayer.be();
        boolean flag0 = false;
        double d0;
        List list;
        Iterator iterator;
        EntityLiving entityliving;

        if (itemstack != null && itemstack.b() == Items.ca && !this.o.E) {
            d0 = 7.0D;
            list = this.o.a(EntityLiving.class, AxisAlignedBB.a(this.s - d0, this.t - d0, this.u - d0, this.s + d0, this.t + d0, this.u + d0));
            if (list != null) {
                iterator = list.iterator();

                while (iterator.hasNext()) {
                    entityliving = (EntityLiving) iterator.next();
                    if (entityliving.bN() && entityliving.bO() == entityplayer) {
                        entityliving.b(this, true);
                        flag0 = true;
                    }
                }
            }
        }

        if (!this.o.E && !flag0) {
            this.B();
            if (entityplayer.bE.d) {
                d0 = 7.0D;
                list = this.o.a(EntityLiving.class, AxisAlignedBB.a(this.s - d0, this.t - d0, this.u - d0, this.s + d0, this.t + d0, this.u + d0));
                if (list != null) {
                    iterator = list.iterator();

                    while (iterator.hasNext()) {
                        entityliving = (EntityLiving) iterator.next();
                        if (entityliving.bN() && entityliving.bO() == this) {
                            entityliving.a(true, false);
                        }
                    }
                }
            }
        }

        return true;
    }

    public boolean e() {
        return this.o.a(this.b, this.c, this.d).b() == 11;
    }

    public static EntityLeashKnot a(World world, int i0, int i1, int i2) {
        EntityLeashKnot entityleashknot = new EntityLeashKnot(world, i0, i1, i2);

        entityleashknot.n = true;
        world.d((Entity) entityleashknot);
        return entityleashknot;
    }

    public static EntityLeashKnot b(World world, int i0, int i1, int i2) {
        List list = world.a(EntityLeashKnot.class, AxisAlignedBB.a((double) i0 - 1.0D, (double) i1 - 1.0D, (double) i2 - 1.0D, (double) i0 + 1.0D, (double) i1 + 1.0D, (double) i2 + 1.0D));

        if (list != null) {
            Iterator iterator = list.iterator();

            while (iterator.hasNext()) {
                EntityLeashKnot entityleashknot = (EntityLeashKnot) iterator.next();

                if (entityleashknot.b == i0 && entityleashknot.c == i1 && entityleashknot.d == i2) {
                    return entityleashknot;
                }
            }
        }

        return null;
    }
}
