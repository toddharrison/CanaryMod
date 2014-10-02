package net.minecraft.entity.item;

import net.canarymod.api.entity.living.LivingBase;
import net.canarymod.api.entity.vehicle.CanaryEmptyMinecart;
import net.canarymod.api.entity.vehicle.Vehicle;
import net.canarymod.hook.CancelableHook;
import net.canarymod.hook.entity.VehicleEnterHook;
import net.canarymod.hook.entity.VehicleExitHook;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;


public class EntityMinecartEmpty extends EntityMinecart {

    public EntityMinecartEmpty(World world) {
        super(world);
        this.entity = new CanaryEmptyMinecart(this); // CanaryMod: Wrap Entity
    }

    public EntityMinecartEmpty(World world, double d0, double d1, double d2) {
        super(world, d0, d1, d2);
        this.entity = new CanaryEmptyMinecart(this); // CanaryMod: Wrap Entity
    }

    public boolean e(EntityPlayer entityplayer) {
        if (this.l != null && this.l instanceof EntityPlayer && this.l != entityplayer) {
            return true;
        } else if (this.l != null && this.l != entityplayer) {
            return false;
        } else {
            if (!this.o.D) {
                // CanaryMod: VehicleEnter/VehicleExit
                CancelableHook hook;

                if (this.l == null) {
                    hook = new VehicleEnterHook((Vehicle) this.entity, (LivingBase) entityplayer.getCanaryEntity());
                } else {
                    hook = new VehicleExitHook((Vehicle) this.entity, (LivingBase) entityplayer.getCanaryEntity());
                }
                hook.call();
                if (!hook.isCanceled()) {
                    entityplayer.a((Entity) this);
                }
                //
            }

            return true;
        }
    }

    public void a(int i0, int i1, int i2, boolean flag0) {
        if (flag0) {
            if (this.l != null) {
                this.l.a((Entity) null);
            }

            if (this.q() == 0) {
                this.k(-this.r());
                this.j(10);
                this.a(50.0F);
                this.ac();
            }
        }

    }

    public EntityMinecart.EnumMinecartType s() {
        return EntityMinecart.EnumMinecartType.RIDEABLE;
    }
}
