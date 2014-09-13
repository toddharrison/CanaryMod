package net.minecraft.entity.monster;

import net.canarymod.hook.entity.MobTargetHook;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;


public abstract class EntityMob extends EntityCreature implements IMob {

    public EntityMob(World world) {
        super(world);
        this.b = 5;
    }

    public void e() {
        this.bb();
        float f0 = this.d(1.0F);

        if (f0 > 0.5F) {
            this.aU += 2;
        }

        super.e();
    }

    public void h() {
        super.h();
        if (!this.o.E && this.o.r == EnumDifficulty.PEACEFUL) {
            this.B();
        }
    }

    protected String H() {
        return "game.hostile.swim";
    }

    protected String O() {
        return "game.hostile.swim.splash";
    }

    protected Entity bR() {
        EntityPlayer entityplayer = this.o.b(this, 16.0D);

        return entityplayer != null && this.p(entityplayer) ? entityplayer : null;
    }

    public boolean a(DamageSource damagesource, float f0) {
        if (this.aw()) {
            return false;
        } else if (super.a(damagesource, f0)) {
            Entity entity = damagesource.j();

            if (this.l != entity && this.m != entity) {
                if (entity != this) {
                    // CanaryMod: MobTarget
                    if (entity instanceof EntityLiving) {
                        MobTargetHook hook = (MobTargetHook) new MobTargetHook((net.canarymod.api.entity.living.EntityLiving) this.getCanaryEntity(), (net.canarymod.api.entity.living.EntityLiving) entity.getCanaryEntity()).call();
                        if (!hook.isCanceled()) {
                            this.bm = entity;
                        }
                    } else {
                        this.bm = entity;
                    }
                    //
                }

                return true;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    protected String aT() {
        return "game.hostile.hurt";
    }

    protected String aU() {
        return "game.hostile.die";
    }

    protected String o(int i0) {
        return i0 > 4 ? "game.hostile.hurt.fall.big" : "game.hostile.hurt.fall.small";
    }

    public boolean n(Entity entity) {
        float f0 = (float) this.a(SharedMonsterAttributes.e).e();
        int i0 = 0;

        if (entity instanceof EntityLivingBase) {
            f0 += EnchantmentHelper.a((EntityLivingBase) this, (EntityLivingBase) entity);
            i0 += EnchantmentHelper.b(this, (EntityLivingBase) entity);
        }

        boolean flag0 = entity.a(DamageSource.a((EntityLivingBase) this), f0);

        if (flag0) {
            if (i0 > 0) {
                entity.g((double) (-MathHelper.a(this.y * 3.1415927F / 180.0F) * (float) i0 * 0.5F), 0.1D, (double) (MathHelper.b(this.y * 3.1415927F / 180.0F) * (float) i0 * 0.5F));
                this.v *= 0.6D;
                this.x *= 0.6D;
            }

            int i1 = EnchantmentHelper.a((EntityLivingBase) this);

            if (i1 > 0) {
                entity.e(i1 * 4);
            }

            if (entity instanceof EntityLivingBase) {
                EnchantmentHelper.a((EntityLivingBase) entity, (Entity) this);
            }
            EnchantmentHelper.b(this, entity);
        }

        return flag0;
    }

    protected void a(Entity entity, float f0) {
        if (this.aB <= 0 && f0 < 2.0F && entity.C.e > this.C.b && entity.C.b < this.C.e) {
            this.aB = 20;
            this.n(entity);
        }
    }

    public float a(int i0, int i1, int i2) {
        return 0.5F - this.o.n(i0, i1, i2);
    }

    protected boolean j_() {
        int i0 = MathHelper.c(this.s);
        int i1 = MathHelper.c(this.C.b);
        int i2 = MathHelper.c(this.u);

        if (this.o.b(EnumSkyBlock.Sky, i0, i1, i2) > this.Z.nextInt(32)) {
            return false;
        } else {
            int i3 = this.o.k(i0, i1, i2);

            if (this.o.P()) {
                int i4 = this.o.j;

                this.o.j = 10;
                i3 = this.o.k(i0, i1, i2);
                this.o.j = i4;
            }

            return i3 <= this.Z.nextInt(8);
        }
    }

    public boolean by() {
        return this.o.r != EnumDifficulty.PEACEFUL && this.j_() && super.by();
    }

    protected void aD() {
        super.aD();
        this.bc().b(SharedMonsterAttributes.e);
    }

    protected boolean aG() {
        return true;
    }
}
