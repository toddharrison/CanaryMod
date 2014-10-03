package net.minecraft.entity.projectile;

import net.canarymod.api.entity.CanaryWitherSkull;
import net.canarymod.hook.entity.ProjectileHitHook;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;


public class EntityWitherSkull extends EntityFireball {

    public EntityWitherSkull(World world) {
        super(world);
        this.a(0.3125F, 0.3125F);
        this.entity = new CanaryWitherSkull(this); // CanaryMod: wrap entity
    }

    public EntityWitherSkull(World world, EntityLivingBase entitylivingbase, double d0, double d1, double d2) {
        super(world, entitylivingbase, d0, d1, d2);
        this.a(0.3125F, 0.3125F);
        this.entity = new CanaryWitherSkull(this); // CanaryMod: wrap entity
    }

    public float j() {
        return /* this.l() ? 0.73F  : */super.j(); // CanaryMod: Motion Factor was made configurable
    }

    public boolean au() {
        return false;
    }

    public float a(Explosion explosion, World world, BlockPos blockpos, IBlockState iblockstate) {
        float f0 = super.a(explosion, world, blockpos, iblockstate);

        if (this.l() && iblockstate.c() != Blocks.h && iblockstate.c() != Blocks.bF && iblockstate.c() != Blocks.bG && iblockstate.c() != Blocks.bX) {
            f0 = Math.min(0.8F, f0);
        }

        return f0;
    }

    protected void a(MovingObjectPosition movingobjectposition) {
        if (!this.o.E) {
            // CanaryMod: ProjectileHit
            ProjectileHitHook hook = (ProjectileHitHook) new ProjectileHitHook(this.getCanaryEntity(), movingobjectposition == null || movingobjectposition.g == null ? null : movingobjectposition.g.getCanaryEntity()).call();
            if (!hook.isCanceled()) { //
                if (movingobjectposition.d != null) {
                    if (this.a != null) {
                        if (movingobjectposition.d.ai(DamageSource.a(this.a), 8.0F) && !movingobjectposition.g.Z()) {
                            this.a.g(5.0F);
                        }
                    } else {
                        movingobjectposition.d.a(DamageSource.k, 5.0F);
                    }

                    if (movingobjectposition.d instanceof EntityLivingBase) {
                        byte b0 = 0;

                        if (this.o.aa() == EnumDifficulty.NORMAL) {
                            b0 = 10;
                        } else if (this.o.aa() == EnumDifficulty.HARD) {
                            b0 = 40;
                        }

                        if (b0 > 0) {
                            ((EntityLivingBase) movingobjectposition.d).c(new PotionEffect(Potion.v.H, 20 * b0, 1));
                        }
                    }
                }

                this.o.a(this, this.s, this.t, this.u, 1.0F, false, this.o.Q().b("mobGriefing"));
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

    protected void h() {
        this.ac.a(10, Byte.valueOf((byte) 0));
    }

    public boolean l() {
        return this.ac.a(10) == 1;
    }

    public void a(boolean flag0) {
        this.ac.b(10, Byte.valueOf((byte) (flag0 ? 1 : 0)));
    }
}
