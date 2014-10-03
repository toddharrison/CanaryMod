package net.minecraft.entity.projectile;

import net.canarymod.api.entity.throwable.CanarySnowball;
import net.canarymod.hook.entity.ProjectileHitHook;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;


public class EntitySnowball extends EntityThrowable {

    public EntitySnowball(World world) {
        super(world);
        this.entity = new CanarySnowball(this); // CanaryMod: wrap entity
    }

    public EntitySnowball(World world, EntityLivingBase entitylivingbase) {
        super(world, entitylivingbase);
        this.entity = new CanarySnowball(this); // CanaryMod: warp entity
    }

    public EntitySnowball(World world, double d0, double d1, double d2) {
        super(world, d0, d1, d2);
        this.entity = new CanarySnowball(this); // CanaryMod: warp entity
    }

    protected void a(MovingObjectPosition movingobjectposition) {
        // CanaryMod: ProjectileHit
        ProjectileHitHook hook = (ProjectileHitHook) new ProjectileHitHook(this.getCanaryEntity(), movingobjectposition == null || movingobjectposition.g == null ? null : movingobjectposition.g.getCanaryEntity()).call();
        if (!hook.isCanceled()) { //
            if (movingobjectposition.d != null) {
                byte b0 = 0;

                if (movingobjectposition.d instanceof EntityBlaze) {
                    b0 = 3;
                }

                movingobjectposition.d.a(DamageSource.a((Entity) this, this.n()), (float) b0);
            }

            for (int i0 = 0; i0 < 8; ++i0) {
                this.o.a(EnumParticleTypes.SNOWBALL, this.s, this.t, this.u, 0.0D, 0.0D, 0.0D, new int[0]);
            }
            if (!this.o.D) {
                this.J();
            }
        }
    }
}
