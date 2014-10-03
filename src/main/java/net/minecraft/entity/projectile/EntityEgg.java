package net.minecraft.entity.projectile;

import net.canarymod.api.entity.throwable.CanaryChickenEgg;
import net.canarymod.hook.entity.ProjectileHitHook;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;


public class EntityEgg extends EntityThrowable {

    public EntityEgg(World world) {
        super(world);
        this.entity = new CanaryChickenEgg(this); // CanaryMod: Wrap Entity
    }

    public EntityEgg(World world, EntityLivingBase entitylivingbase) {
        super(world, entitylivingbase);
        this.entity = new CanaryChickenEgg(this); // CanaryMod: Wrap Entity
    }

    public EntityEgg(World world, double d0, double d1, double d2) {
        super(world, d0, d1, d2);
        this.entity = new CanaryChickenEgg(this); // CanaryMod: Wrap Entity
    }

    protected void a(MovingObjectPosition movingobjectposition) {
        // CanaryMod: ProjectileHit
        ProjectileHitHook hook = (ProjectileHitHook) new ProjectileHitHook(this.getCanaryEntity(), movingobjectposition == null || movingobjectposition.g == null ? null : movingobjectposition.g.getCanaryEntity()).call();
        if (!hook.isCanceled()) { //
            if (movingobjectposition.d != null) {
                movingobjectposition.d.a(DamageSource.a((Entity) this, this.j()), 0.0F);
            }

        if (!this.o.D && this.V.nextInt(8) == 0) {
            byte b0 = 1;

            if (this.V.nextInt(32) == 0) {
                b0 = 4;
            }

            for (int i0 = 0; i0 < b0; ++i0) {
                EntityChicken entitychicken = new EntityChicken(this.o);

                entitychicken.b(-24000);
                entitychicken.b(this.s, this.t, this.u, this.y, 0.0F);
                this.o.d((Entity) entitychicken);
            }
        }

        double d0 = 0.08D;

        for (int i1 = 0; i1 < 8; ++i1) {
            this.o.a(EnumParticleTypes.ITEM_CRACK, this.s, this.t, this.u, ((double) this.V.nextFloat() - 0.5D) * 0.08D, ((double) this.V.nextFloat() - 0.5D) * 0.08D, ((double) this.V.nextFloat() - 0.5D) * 0.08D, new int[] { Item.b(Items.aP)});
        }

        if (!this.o.D) {
            this.J();
        }

    }
}
