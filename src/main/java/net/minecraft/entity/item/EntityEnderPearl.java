package net.minecraft.entity.item;

import net.canarymod.api.entity.throwable.CanaryEnderPearl;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityEnderPearl extends EntityThrowable {

    public EntityEnderPearl(World world) {
        super(world);
        this.entity = new CanaryEnderPearl(this); // CanaryMod: Wrap Entity
    }

    public EntityEnderPearl(World world, EntityLivingBase entitylivingbase) {
        super(world, entitylivingbase);
        this.entity = new CanaryEnderPearl(this); // CanaryMod: Wrap Entity
    }

    protected void a(MovingObjectPosition movingobjectposition) {
        if (movingobjectposition.g != null) {
            movingobjectposition.g.a(DamageSource.a((Entity) this, this.j()), 0.0F);
        }

        for (int i0 = 0; i0 < 32; ++i0) {
            this.o.a("portal", this.s, this.t + this.Z.nextDouble() * 2.0D, this.u, this.Z.nextGaussian(), 0.0D, this.Z.nextGaussian());
        }

        if (!this.o.E) {
            if (this.j() != null && this.j() instanceof EntityPlayerMP) {
                EntityPlayerMP entityplayermp = (EntityPlayerMP) this.j();

                if (entityplayermp.a.b().d() && entityplayermp.o == this.o) {
                    if (this.j().am()) {
                        this.j().a((Entity) null);
                    }

                    this.j().a(this.s, this.t, this.u);
                    this.j().R = 0.0F;
                    this.j().a(DamageSource.h, 5.0F);
                }
            }

            this.B();
        }
    }
}
