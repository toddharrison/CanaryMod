package net.minecraft.entity.item;

import net.canarymod.Canary;
import net.canarymod.api.entity.vehicle.CanaryTNTMinecart;
import net.canarymod.api.entity.vehicle.Minecart;
import net.canarymod.api.entity.vehicle.TNTMinecart;
import net.canarymod.hook.entity.MinecartActivateHook;
import net.canarymod.hook.world.TNTActivateHook;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;


public class EntityMinecartTNT extends EntityMinecart {

    public int a = -1; // CanaryMod: private -> public

    public EntityMinecartTNT(World world) {
        super(world);
        this.entity = new CanaryTNTMinecart(this); // CanaryMod: Wrap Entity
    }

    public EntityMinecartTNT(World world, double d0, double d1, double d2) {
        super(world, d0, d1, d2);
        this.entity = new CanaryTNTMinecart(this); // CanaryMod: Wrap Entity
    }

    public EntityMinecart.EnumMinecartType s() {
        return EntityMinecart.EnumMinecartType.TNT;
    }

    public IBlockState u() {
        return Blocks.W.P();
    }

    public void s_() {
        super.s_();
        if (this.a > 0) {
            --this.a;
            this.o.a(EnumParticleTypes.SMOKE_NORMAL, this.s, this.t + 0.5D, this.u, 0.0D, 0.0D, 0.0D, new int[0]);
        }
        else if (this.a == 0) {
            this.b(this.v * this.v + this.x * this.x);
        }

        if (this.D) {
            double d0 = this.v * this.v + this.x * this.x;

            if (d0 >= 0.009999999776482582D) {
                this.b(d0);
            }
        }

    }

    public boolean a(DamageSource damagesource, float f0) {
        Entity entity = damagesource.i();

        if (entity instanceof EntityArrow) {
            EntityArrow entityarrow = (EntityArrow) entity;

            if (entityarrow.au()) {
                this.b(entityarrow.v * entityarrow.v + entityarrow.w * entityarrow.w + entityarrow.x * entityarrow.x);
            }
        }

        return super.a(damagesource, f0);
    }

    public void a(DamageSource damagesource) {
        super.a(damagesource);
        double d0 = this.v * this.v + this.x * this.x;

        if (!damagesource.c()) {
            this.a(new ItemStack(Blocks.W, 1), 0.0F);
        }

        if (damagesource.o() || damagesource.c() || d0 >= 0.009999999776482582D) {
            this.b(d0);
        }

    }

    protected void b(double d0) {
        if (!this.o.D) {
            double d1 = Math.sqrt(d0);

            if (d1 > 5.0D) {
                d1 = 5.0D;
            }

            this.o.a(this, this.s, this.t, this.u, (float) (4.0D + this.V.nextDouble() * 1.5D * d1), true);
            this.J();
        }

    }

    public void e(float f0, float f1) {
        if (f0 >= 3.0F) {
            float f2 = f0 / 10.0F;

            this.b((double) (f2 * f2));
        }

        super.e(f0, f1);
    }

    public void a(int i0, int i1, int i2, boolean flag0) {
        if (flag0 && this.a < 0) {
            // CanaryMod: MinecartActivate
            MinecartActivateHook mah = (MinecartActivateHook) new MinecartActivateHook((Minecart) this.getCanaryEntity(), (i1 & 8) != 0).call();
            TNTActivateHook tah = (TNTActivateHook) new TNTActivateHook((TNTMinecart) this.getCanaryEntity()).call();
            Canary.hooks().callHook(mah);
            if (!mah.isCanceled() && !tah.isCanceled()) {
                this.j();
            }
            //
        }
    }

    public void j() {
        this.a = 80;
        if (!this.o.D) {
            this.o.a((Entity) this, (byte) 10);
            if (!this.R()) {
                this.o.a((Entity) this, "game.tnt.primed", 1.0F, 1.0F);
            }
        }

    }

    public boolean y() {
        return this.a > -1;
    }

    public float a(Explosion explosion, World world, BlockPos blockpos, IBlockState iblockstate) {
        return this.y() && (BlockRailBase.d(iblockstate) || BlockRailBase.d(world, blockpos.a())) ? 0.0F : super.a(explosion, world, blockpos, iblockstate);
    }

    public boolean a(Explosion explosion, World world, BlockPos blockpos, IBlockState iblockstate, float f0) {
        return this.y() && (BlockRailBase.d(iblockstate) || BlockRailBase.d(world, blockpos.a())) ? false : super.a(explosion, world, blockpos, iblockstate, f0);
    }

    protected void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        if (nbttagcompound.b("TNTFuse", 99)) {
            this.a = nbttagcompound.f("TNTFuse");
        }

    }

    protected void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("TNTFuse", this.a);
    }
}
