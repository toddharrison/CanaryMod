package net.minecraft.entity.projectile;

import net.canarymod.api.entity.CanarySmallFireball;
import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.api.world.position.BlockPosition;
import net.canarymod.hook.entity.ProjectileHitHook;
import net.canarymod.hook.world.IgnitionHook;
import net.canarymod.hook.world.IgnitionHook.IgnitionCause;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;


public class EntitySmallFireball extends EntityFireball {

    public EntitySmallFireball(World world) {
        super(world);
        this.a(0.3125F, 0.3125F);
        this.entity = new CanarySmallFireball(this); // CanaryMod: wrap entity
    }

    public EntitySmallFireball(World world, EntityLivingBase entitylivingbase, double d0, double d1, double d2) {
        super(world, entitylivingbase, d0, d1, d2);
        this.a(0.3125F, 0.3125F);
        this.entity = new CanarySmallFireball(this); // CanaryMod: wrap entity
    }

    public EntitySmallFireball(World world, double d0, double d1, double d2, double d3, double d4, double d5) {
        super(world, d0, d1, d2, d3, d4, d5);
        this.a(0.3125F, 0.3125F);
        this.entity = new CanarySmallFireball(this); // CanaryMod: wrap entity
    }

    protected void a(MovingObjectPosition movingobjectposition) {
        if (!this.o.D) {
            // CanaryMod: ProjectileHit
            ProjectileHitHook hook = (ProjectileHitHook) new ProjectileHitHook(this.getCanaryEntity(), movingobjectposition == null || movingobjectposition.d == null ? null : movingobjectposition.d.getCanaryEntity()).call();
            if (!hook.isCanceled()) { //
                boolean flag0;

                if (movingobjectposition.d != null) {
                    flag0 = movingobjectposition.d.a(DamageSource.a((EntityFireball) this, this.a), 5.0F);
                    if (flag0) {
                        this.a(this.a, movingobjectposition.d);
                        if (!movingobjectposition.d.T()) {
                            movingobjectposition.d.e(5);
                        }
                    }
                } else {
                    flag0 = true;
                    if (this.a != null && this.a instanceof EntityLiving) {
                        flag0 = this.o.Q().b("mobGriefing");
                    }

                    if (flag0) {
                        BlockPos blockpos = movingobjectposition.a().a(movingobjectposition.b);

                        if (this.o.d(blockpos)) {
                            // CanaryMod: IgnitionHook
                            CanaryBlock block = (CanaryBlock) this.o.getCanaryWorld().getBlockAt(new BlockPosition(blockpos));
                            block.setStatus((byte) 7); // 7 fireball hit
                            IgnitionHook ignitionHook = (IgnitionHook) new IgnitionHook(block, null, null, IgnitionCause.FIREBALL_HIT).call();
                            if (!ignitionHook.isCanceled()) {
                                this.o.a(blockpos, Blocks.ab.P());
                            }
                            //                        
                        }
                    }
                }
                this.J();
            }
        }
    }

    public boolean ad() {
        return false;
    }

    public boolean a(DamageSource damagesource, float f0) {
        return false;
    }
}
