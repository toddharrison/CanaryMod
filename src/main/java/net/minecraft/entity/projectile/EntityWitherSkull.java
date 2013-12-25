package net.minecraft.entity.projectile;

import net.canarymod.api.entity.CanaryWitherSkull;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
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

    public float e() {
        return /* this.d() ? 0.73F  : */super.e(); // CanaryMod: Motion Factor was made configurable
    }

    public boolean al() {
        return false;
    }

    public float a(Explosion explosion, World world, int i0, int i1, int i2, Block block) {
        float f0 = super.a(explosion, world, i0, i1, i2, block);

        if (this.f() && block != Blocks.h && block != Blocks.bq && block != Blocks.br && block != Blocks.bI) {
            f0 = Math.min(0.8F, f0);
        }

        return f0;
    }

    protected void a(MovingObjectPosition movingobjectposition) {
        if (!this.p.E) {
            if (movingobjectposition.g != null) {
                if (this.a != null) {
                    if (movingobjectposition.g.a(DamageSource.a(this.a), 8.0F) && !movingobjectposition.g.Z()) {
                        this.a.f(5.0F);
                    }
                }
                else {
                    movingobjectposition.g.a(DamageSource.k, 5.0F);
                }

                if (movingobjectposition.g instanceof EntityLivingBase) {
                    byte b0 = 0;

                    if (this.p.r == EnumDifficulty.NORMAL) {
                        b0 = 10;
                    }
                    else if (this.p.r == EnumDifficulty.HARD) {
                        b0 = 40;
                    }

                    if (b0 > 0) {
                        ((EntityLivingBase) movingobjectposition.g).c(new PotionEffect(Potion.v.H, 20 * b0, 1));
                    }
                }
            }

            this.p.a(this, this.t, this.u, this.v, 1.0F, false, this.p.N().b("mobGriefing"));
            this.B();
        }
    }

    public boolean R() {
        return false;
    }

    public boolean a(DamageSource damagesource, float f0) {
        return false;
    }

    protected void c() {
        this.ag.a(10, Byte.valueOf((byte) 0));
    }

    public boolean f() {
        return this.ag.a(10) == 1;
    }

    public void a(boolean flag0) {
        this.ag.b(10, Byte.valueOf((byte) (flag0 ? 1 : 0)));
    }
}
