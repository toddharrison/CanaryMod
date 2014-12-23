package net.minecraft.entity.item;

import net.canarymod.api.entity.vehicle.CanaryFurnaceMinecart;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;


public class EntityMinecartFurnace extends EntityMinecart {

    public int c; // CanaryMod: private -> public
    public double a;
    public double b;

    public EntityMinecartFurnace(World world) {
        super(world);
        this.entity = new CanaryFurnaceMinecart(this); // CanaryMod: Wrap Entity
    }

    public EntityMinecartFurnace(World world, double d0, double d1, double d2) {
        super(world, d0, d1, d2);
        this.entity = new CanaryFurnaceMinecart(this); // CanaryMod: Wrap Entity
    }

    public EntityMinecart.EnumMinecartType s() {
        return EntityMinecart.EnumMinecartType.FURNACE;
    }

    protected void h() {
        super.h();
        this.ac.a(16, new Byte((byte) 0));
    }

    public void s_() {
        super.s_();
        if (this.c > 0) {
            --this.c;
        }

        if (this.c <= 0) {
            this.a = this.b = 0.0D;
        }

        this.i(this.c > 0);
        if (this.j() && this.V.nextInt(4) == 0) {
            this.o.a(EnumParticleTypes.SMOKE_LARGE, this.s, this.t + 0.8D, this.u, 0.0D, 0.0D, 0.0D, new int[0]);
        }

    }

    protected double m() {
        return 0.2D;
    }

    public void a(DamageSource damagesource) {
        super.a(damagesource);
        if (!damagesource.c()) {
            this.a(new ItemStack(Blocks.al, 1), 0.0F);
        }

    }

    protected void a(BlockPos blockpos, IBlockState iblockstate) {
        super.a(blockpos, iblockstate);
        double d0 = this.a * this.a + this.b * this.b;

        if (d0 > 1.0E-4D && this.v * this.v + this.x * this.x > 0.001D) {
            d0 = (double) MathHelper.a(d0);
            this.a /= d0;
            this.b /= d0;
            if (this.a * this.v + this.b * this.x < 0.0D) {
                this.a = 0.0D;
                this.b = 0.0D;
            }
            else {
                double d1 = d0 / this.m();

                this.a *= d1;
                this.b *= d1;
            }
        }

    }

    protected void o() {
        double d0 = this.a * this.a + this.b * this.b;

        if (d0 > 1.0E-4D) {
            d0 = (double) MathHelper.a(d0);
            this.a /= d0;
            this.b /= d0;
            double d1 = 1.0D;

            this.v *= 0.800000011920929D;
            this.w *= 0.0D;
            this.x *= 0.800000011920929D;
            this.v += this.a * d1;
            this.x += this.b * d1;
        }
        else {
            this.v *= 0.9800000190734863D;
            this.w *= 0.0D;
            this.x *= 0.9800000190734863D;
        }

        super.o();
    }

    public boolean e(EntityPlayer entityplayer) {
        ItemStack itemstack = entityplayer.bg.h();

        if (itemstack != null && itemstack.b() == Items.h) {
            if (!entityplayer.by.d && --itemstack.b == 0) {
                entityplayer.bg.a(entityplayer.bg.c, (ItemStack) null);
            }

            this.c += 3600;
        }

        this.a = this.s - entityplayer.s;
        this.b = this.u - entityplayer.u;
        return true;
    }

    protected void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("PushX", this.a);
        nbttagcompound.a("PushZ", this.b);
        nbttagcompound.a("Fuel", (short) this.c);
    }

    protected void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.a = nbttagcompound.i("PushX");
        this.b = nbttagcompound.i("PushZ");
        this.c = nbttagcompound.e("Fuel");
    }

    protected boolean j() {
        return (this.ac.a(16) & 1) != 0;
    }

    protected void i(boolean flag0) {
        if (flag0) {
            this.ac.b(16, Byte.valueOf((byte) (this.ac.a(16) | 1)));
        }
        else {
            this.ac.b(16, Byte.valueOf((byte) (this.ac.a(16) & -2)));
        }

    }

    public IBlockState u() {
        return (this.j() ? Blocks.am : Blocks.al).P().a(BlockFurnace.a, EnumFacing.NORTH);
    }
}
