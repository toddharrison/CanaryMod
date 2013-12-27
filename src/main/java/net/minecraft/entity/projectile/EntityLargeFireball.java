package net.minecraft.entity.projectile;

import net.canarymod.api.entity.CanaryLargeFireball;
import net.canarymod.hook.entity.ProjectileHitHook;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityLargeFireball extends EntityFireball {

    public int e = 1;

    public EntityLargeFireball(World world) {
        super(world);
        this.entity = new CanaryLargeFireball(this); // CanaryMod: Wrap entity
    }

    public EntityLargeFireball(World world, EntityLivingBase entitylivingbase, double d0, double d1, double d2) {
        super(world, entitylivingbase, d0, d1, d2);
        this.entity = new CanaryLargeFireball(this); // CanaryMod: Wrap entity
    }

    protected void a(MovingObjectPosition movingobjectposition) {
        if (!this.p.E) {
            // CanaryMod: ProjectileHitHook
            ProjectileHitHook hook = (ProjectileHitHook) new ProjectileHitHook(this.getCanaryEntity(), movingobjectposition == null || movingobjectposition.g == null ? null : movingobjectposition.g.getCanaryEntity()).call();
            if (!hook.isCanceled()) { //
                if (movingobjectposition.g != null) {
                    movingobjectposition.g.a(DamageSource.a((EntityFireball) this, this.a), 6.0F);
                }

                this.p.a((Entity) null, this.t, this.u, this.v, (float) this.e, true, this.p.N().b("mobGriefing"));
                this.B();
            }
        }
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("ExplosionPower", this.e);
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        if (nbttagcompound.b("ExplosionPower", 99)) {
            this.e = nbttagcompound.f("ExplosionPower");
        }
    }
}
