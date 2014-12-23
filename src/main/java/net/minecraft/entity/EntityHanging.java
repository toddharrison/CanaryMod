package net.minecraft.entity;

import net.canarymod.api.entity.hanging.HangingEntity;
import net.canarymod.hook.entity.HangingEntityDestroyHook;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneDiode;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import org.apache.commons.lang3.Validate;

import java.util.Iterator;
import java.util.List;

public abstract class EntityHanging extends Entity {

    private int c;
    protected BlockPos a;
    public EnumFacing b;
   
    public EntityHanging(World world) {
        super(world);
        this.a(0.5F, 0.5F);
    }

    public EntityHanging(World world, BlockPos blockpos) {
        this(world);
        this.a = blockpos;
    }

    protected void h() {}

    protected void a(EnumFacing enumfacing) {
        Validate.notNull(enumfacing);
        Validate.isTrue(enumfacing.k().c());
        this.b = enumfacing;
        this.A = this.y = (float) (this.b.b() * 90);
        this.o();
    }

    private void o() {
        if (this.b != null) {
            double d0 = (double) this.a.n() + 0.5D;
            double d1 = (double) this.a.o() + 0.5D;
            double d2 = (double) this.a.p() + 0.5D;
            double d3 = 0.46875D;
            double d4 = this.a(this.l());
            double d5 = this.a(this.m());

            d0 -= (double) this.b.g() * 0.46875D;
            d2 -= (double) this.b.i() * 0.46875D;
            d1 += d5;
            EnumFacing enumfacing = this.b.f();

            d0 += d4 * (double) enumfacing.g();
            d2 += d4 * (double) enumfacing.i();
            this.s = d0;
            this.t = d1;
            this.u = d2;
            double d6 = (double) this.l();
            double d7 = (double) this.m();
            double d8 = (double) this.l();

            if (this.b.k() == EnumFacing.Axis.Z) {
                d8 = 1.0D;
            } else {
                d6 = 1.0D;
            }

            d6 /= 32.0D;
            d7 /= 32.0D;
            d8 /= 32.0D;
            this.a(new AxisAlignedBB(d0 - d6, d1 - d7, d2 - d8, d0 + d6, d1 + d7, d2 + d8));
        }
    }

    private double a(int i0) {
        return i0 % 32 == 0 ? 0.5D : 0.0D;
    }

    public void s_() {
        this.p = this.s;
        this.q = this.t;
        this.r = this.u;
        if (this.c++ == 100 && !this.o.D) {
            this.c = 0;
            if (!this.I && !this.j()) {
                this.J();
                this.b((Entity) null);
            }
        }

    }

    public boolean j() {
        if (!this.o.a((Entity) this, this.aQ()).isEmpty()) {
            return false;
        } else {
            int i0 = Math.max(1, this.l() / 16);
            int i1 = Math.max(1, this.m() / 16);
            BlockPos blockpos = this.a.a(this.b.d());
            EnumFacing enumfacing = this.b.f();

            for (int i2 = 0; i2 < i0; ++i2) {
                for (int i3 = 0; i3 < i1; ++i3) {
                    BlockPos blockpos1 = blockpos.a(enumfacing, i2).b(i3);
                    Block block = this.o.p(blockpos1).c();

                    if (!block.r().a() && !BlockRedstoneDiode.d(block)) {
                        return false;
                    }
                }
            }

            List list = this.o.b((Entity) this, this.aQ());
            Iterator iterator = list.iterator();

            Entity entity;

            do {
                if (!iterator.hasNext()) {
                    return true;
                }

                entity = (Entity) iterator.next();
            } while (!(entity instanceof EntityHanging));

            return false;
        }
    }

    public boolean ad() {
        return true;
    }

    public boolean l(Entity entity) {
        return entity instanceof EntityPlayer ? this.a(DamageSource.a((EntityPlayer) entity), 0.0F) : false;
    }

    public EnumFacing aO() {
        return this.b;
    }

    public boolean a(DamageSource damagesource, float f0) {
        if (this.b(damagesource)) {
            return false;
        } else {
            if (!this.I && !this.o.D) {
                EntityPlayer entityplayer = null;

                if (damagesource.i() instanceof EntityPlayer) {
                    entityplayer = (EntityPlayer) damagesource.i();
                }

                if (entityplayer != null) {
                    // CanaryMod: HangingEntityDestory
                    HangingEntityDestroyHook hook = (HangingEntityDestroyHook) new HangingEntityDestroyHook((HangingEntity) this.entity, ((EntityPlayerMP) entityplayer).getPlayer(), damagesource.getCanaryDamageSource()).call();
                    if (hook.isCanceled()) {
                        return false;
                    }
                    //
                }

                this.J();
                this.ac();
                this.b(damagesource.j());
            }

            return true;
        }
    }

    public void d(double d0, double d1, double d2) {
        if (!this.o.D && !this.I && d0 * d0 + d1 * d1 + d2 * d2 > 0.0D) {
            this.J();
            this.b((Entity) null);
        }

    }

    public void g(double d0, double d1, double d2) {
        if (!this.o.D && !this.I && d0 * d0 + d1 * d1 + d2 * d2 > 0.0D) {
            this.J();
            this.b((Entity) null);
        }

    }

    public void b(NBTTagCompound nbttagcompound) {
        nbttagcompound.a("Facing", (byte) this.b.b());
        nbttagcompound.a("TileX", this.n().n());
        nbttagcompound.a("TileY", this.n().o());
        nbttagcompound.a("TileZ", this.n().p());
    }

    public void a(NBTTagCompound nbttagcompound) {
        this.a = new BlockPos(nbttagcompound.f("TileX"), nbttagcompound.f("TileY"), nbttagcompound.f("TileZ"));
        EnumFacing enumfacing;

        if (nbttagcompound.b("Direction", 99)) {
            enumfacing = EnumFacing.b(nbttagcompound.d("Direction"));
            this.a = this.a.a(enumfacing);
        } else if (nbttagcompound.b("Facing", 99)) {
            enumfacing = EnumFacing.b(nbttagcompound.d("Facing"));
        } else {
            enumfacing = EnumFacing.b(nbttagcompound.d("Dir"));
        }

        this.a(enumfacing);
    }

    public abstract int l();

    public abstract int m();

    public abstract void b(Entity entity);

    protected boolean af() {
        return false;
    }

    public void b(double d0, double d1, double d2) {
        this.s = d0;
        this.t = d1;
        this.u = d2;
        BlockPos blockpos = this.a;

        this.a = new BlockPos(d0, d1, d2);
        if (!this.a.equals(blockpos)) {
            this.o();
            this.ai = true;
        }

    }

    public BlockPos n() {
        return this.a;
    }

    // CanaryMod
    public int getTickCounter() {
        return this.i;
    }

    public void setTicks(int ticks) {
        this.i = ticks;
    }
    //
}
