package net.minecraft.entity.item;

import net.canarymod.api.entity.throwable.CanaryEnderPearl;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
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
        EntityLivingBase entitylivingbase = this.n();

        if (movingobjectposition.d != null) {
            movingobjectposition.d.a(DamageSource.a((Entity) this, entitylivingbase), 0.0F);
        }

        for (int i0 = 0; i0 < 32; ++i0) {
            this.o.a(EnumParticleTypes.PORTAL, this.s, this.t + this.V.nextDouble() * 2.0D, this.u, this.V.nextGaussian(), 0.0D, this.V.nextGaussian(), new int[0]);
        }

        if (!this.o.D) {
            if (entitylivingbase instanceof EntityPlayerMP) {
                EntityPlayerMP entityplayermp = (EntityPlayerMP) entitylivingbase;

                if (entityplayermp.a.a().g() && entityplayermp.o == this.o && !entityplayermp.bI()) {
                    if (this.V.nextFloat() < 0.05F && this.o.Q().b("doMobSpawning")) {
                        EntityEndermite entityendermite = new EntityEndermite(this.o);

                        entityendermite.a(true);
                        entityendermite.b(entitylivingbase.s, entitylivingbase.t, entitylivingbase.u, entitylivingbase.y, entitylivingbase.z);
                        this.o.d((Entity) entityendermite);
                    }

                    if (entitylivingbase.av()) {
                        entitylivingbase.a((Entity) null);
                    }

                    entitylivingbase.a(this.s, this.t, this.u);
                    entitylivingbase.O = 0.0F;
                    entitylivingbase.a(DamageSource.i, 5.0F);
                }
            }

            this.J();
        }

    }

    public void s_() {
        EntityLivingBase entitylivingbase = this.n();

        if (entitylivingbase != null && entitylivingbase instanceof EntityPlayer && !entitylivingbase.ai()) {
            this.J();
        } else {
            super.s_();
        }

    }
}
