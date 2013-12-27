package net.minecraft.entity.item;

import net.canarymod.api.entity.vehicle.CanaryFurnaceMinecart;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
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

    public int m() {
        return 2;
    }

    protected void c() {
        super.c();
        this.ag.a(16, new Byte((byte) 0));
    }

    public void h() {
        super.h();
        if (this.c > 0) {
            --this.c;
        }

        if (this.c <= 0) {
            this.a = this.b = 0.0D;
        }

        this.f(this.c > 0);
        if (this.e() && this.aa.nextInt(4) == 0) {
            this.p.a("largesmoke", this.t, this.u + 0.8D, this.v, 0.0D, 0.0D, 0.0D);
        }
    }

    public void a(DamageSource damagesource) {
        super.a(damagesource);
        if (!damagesource.c()) {
            this.a(new ItemStack(Blocks.al, 1), 0.0F);
        }
    }

    protected void a(int i0, int i1, int i2, double d0, double d1, Block block, int i3) {
        super.a(i0, i1, i2, d0, d1, block, i3);
        double d2 = this.a * this.a + this.b * this.b;

        if (d2 > 1.0E-4D && this.w * this.w + this.y * this.y > 0.001D) {
            d2 = (double) MathHelper.a(d2);
            this.a /= d2;
            this.b /= d2;
            if (this.a * this.w + this.b * this.y < 0.0D) {
                this.a = 0.0D;
                this.b = 0.0D;
            }
            else {
                this.a = this.w;
                this.b = this.y;
            }
        }
    }

    protected void i() {
        double d0 = this.a * this.a + this.b * this.b;

        if (d0 > 1.0E-4D) {
            d0 = (double) MathHelper.a(d0);
            this.a /= d0;
            this.b /= d0;
            double d1 = 0.05D;

            this.w *= 0.800000011920929D;
            this.x *= 0.0D;
            this.y *= 0.800000011920929D;
            this.w += this.a * d1;
            this.y += this.b * d1;
        }
        else {
            this.w *= 0.9800000190734863D;
            this.x *= 0.0D;
            this.y *= 0.9800000190734863D;
        }

        super.i();
    }

    public boolean c(EntityPlayer entityplayer) {
        ItemStack itemstack = entityplayer.bn.h();

        if (itemstack != null && itemstack.b() == Items.h) {
            if (!entityplayer.bF.d && --itemstack.b == 0) {
                entityplayer.bn.a(entityplayer.bn.c, (ItemStack) null);
            }

            this.c += 3600;
        }

        this.a = this.t - entityplayer.t;
        this.b = this.v - entityplayer.v;
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

    protected boolean e() {
        return (this.ag.a(16) & 1) != 0;
    }

    protected void f(boolean flag0) {
        if (flag0) {
            this.ag.b(16, Byte.valueOf((byte) (this.ag.a(16) | 1)));
        }
        else {
            this.ag.b(16, Byte.valueOf((byte) (this.ag.a(16) & -2)));
        }
    }

    public Block o() {
        return Blocks.am;
    }

    public int q() {
        return 2;
    }
}
