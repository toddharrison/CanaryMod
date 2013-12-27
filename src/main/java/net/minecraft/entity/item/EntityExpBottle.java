package net.minecraft.entity.item;

import net.canarymod.api.entity.throwable.CanaryXPBottle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityExpBottle extends EntityThrowable {

    public EntityExpBottle(World world) {
        super(world);
        // CanaryMod
        this.gravity = 0.07F;
        this.entity = new CanaryXPBottle(this); // Wrap Entity
        //
    }

    public EntityExpBottle(World world, EntityLivingBase entitylivingbase) {
        super(world, entitylivingbase);
        this.gravity = 0.07F;
        this.entity = new CanaryXPBottle(this); // Wrap Entity
    }

    public EntityExpBottle(World world, double d0, double d1, double d2) {
        super(world, d0, d1, d2);
        // CanaryMod
        this.gravity = 0.07F;
        this.entity = new CanaryXPBottle(this); // Wrap Entity
        //
    }

    // CanaryMod: remove unneeded method override
    // protected float i() {
    // return 0.07F;
    // }
    //

    protected float e() {
        return 0.7F;
    }

    protected float f() {
        return -20.0F;
    }

    protected void a(MovingObjectPosition movingobjectposition) {
        if (!this.p.E) {
            this.p.c(2002, (int) Math.round(this.t), (int) Math.round(this.u), (int) Math.round(this.v), 0);
            int i0 = 3 + this.p.s.nextInt(5) + this.p.s.nextInt(5);

            while (i0 > 0) {
                int i1 = EntityXPOrb.a(i0);

                i0 -= i1;
                this.p.d((Entity) (new EntityXPOrb(this.p, this.t, this.u, this.v, i1)));
            }

            this.B();
        }
    }
}
