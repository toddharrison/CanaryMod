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
            this.p.a("portal", this.t, this.u + this.aa.nextDouble() * 2.0D, this.v, this.aa.nextGaussian(), 0.0D, this.aa.nextGaussian());
        }

        if (!this.p.E) {
            if (this.j() != null && this.j() instanceof EntityPlayerMP) {
                EntityPlayerMP entityplayermp = (EntityPlayerMP) this.j();

                if (entityplayermp.a.b().d() && entityplayermp.p == this.p) {
                    if (this.j().am()) {
                        this.j().a((Entity) null);
                    }

                    this.j().a(this.t, this.u, this.v);
                    this.j().S = 0.0F;
                    this.j().a(DamageSource.h, 5.0F);
                }
            }

            this.B();
        }
    }
}
