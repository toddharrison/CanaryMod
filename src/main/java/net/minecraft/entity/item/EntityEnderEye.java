package net.minecraft.entity.item;

import net.canarymod.api.entity.CanaryEnderEye;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityEnderEye extends Entity {

    public double a; // CanaryMod: private => public; targetX
    public double b; // CanaryMod: private => public; targetY
    public double c; // CanaryMod: private => public; targetZ
    public int d; // CanaryMod: private => public; despawnTimer
    public boolean e; // CanaryMod: private => public; dropOrShatter

    public EntityEnderEye(World world) {
        super(world);
        this.a(0.25F, 0.25F);
        this.entity = new CanaryEnderEye(this); // CanaryMod: Wrap Entity
    }

    public EntityEnderEye(World world, double d0, double d1, double d2) {
        super(world);
        this.d = 0;
        this.a(0.25F, 0.25F);
        this.b(d0, d1, d2);
        this.entity = new CanaryEnderEye(this); // CanaryMod: Wrap Entity
    }

    protected void h() {
    }

    public void a(BlockPos blockpos) {
        double d0 = (double) blockpos.n();
        int i0 = blockpos.o();
        double d1 = (double) blockpos.p();
        double d2 = d0 - this.s;
        double d3 = d1 - this.u;
        float f0 = MathHelper.a(d2 * d2 + d3 * d3);

        if (f0 > 12.0F) {
            this.a = this.s + d2 / (double) f0 * 12.0D;
            this.c = this.u + d3 / (double) f0 * 12.0D;
            this.b = this.t + 8.0D;
        }
        else {
            this.a = d0;
            this.b = (double) i0;
            this.c = d1;
        }

        this.d = 0;
        this.e = this.V.nextInt(5) > 0;
    }

    public void s_() {
        this.P = this.s;
        this.Q = this.t;
        this.R = this.u;
        super.s_();
        this.s += this.v;
        this.t += this.w;
        this.u += this.x;
        float f0 = MathHelper.a(this.v * this.v + this.x * this.x);

        this.y = (float) (Math.atan2(this.v, this.x) * 180.0D / 3.1415927410125732D);

        for (this.z = (float) (Math.atan2(this.w, (double) f0) * 180.0D / 3.1415927410125732D); this.z - this.B < -180.0F; this.B -= 360.0F) {
            ;
        }

        while (this.z - this.B >= 180.0F) {
            this.B += 360.0F;
        }

        while (this.y - this.A < -180.0F) {
            this.A -= 360.0F;
        }

        while (this.y - this.A >= 180.0F) {
            this.A += 360.0F;
        }

        this.z = this.B + (this.z - this.B) * 0.2F;
        this.y = this.A + (this.y - this.A) * 0.2F;
        if (!this.o.D) {
            double d0 = this.a - this.s;
            double d1 = this.c - this.u;
            float f1 = (float) Math.sqrt(d0 * d0 + d1 * d1);
            float f2 = (float) Math.atan2(d1, d0);
            double d2 = (double) f0 + (double) (f1 - f0) * 0.0025D;

            if (f1 < 1.0F) {
                d2 *= 0.8D;
                this.w *= 0.8D;
            }

            this.v = Math.cos((double) f2) * d2;
            this.x = Math.sin((double) f2) * d2;
            if (this.t < this.b) {
                this.w += (1.0D - this.w) * 0.014999999664723873D;
            }
            else {
                this.w += (-1.0D - this.w) * 0.014999999664723873D;
            }
        }

        float f3 = 0.25F;

        if (this.V()) {
            for (int i0 = 0; i0 < 4; ++i0) {
                this.o.a(EnumParticleTypes.WATER_BUBBLE, this.s - this.v * (double) f3, this.t - this.w * (double) f3, this.u - this.x * (double) f3, this.v, this.w, this.x, new int[0]);
            }
        }
        else {
            this.o.a(EnumParticleTypes.PORTAL, this.s - this.v * (double) f3 + this.V.nextDouble() * 0.6D - 0.3D, this.t - this.w * (double) f3 - 0.5D, this.u - this.x * (double) f3 + this.V.nextDouble() * 0.6D - 0.3D, this.v, this.w, this.x, new int[0]);
        }

        if (!this.o.D) {
            this.b(this.s, this.t, this.u);
            ++this.d;
            if (this.d > 80 && !this.o.D) {
                this.J();
                if (this.e) {
                    this.o.d((Entity) (new EntityItem(this.o, this.s, this.t, this.u, new ItemStack(Items.bH))));
                }
                else {
                    this.o.b(2003, new BlockPos(this), 0);
                }
            }
        }
    }

    public void b(NBTTagCompound nbttagcompound) {
    }

    public void a(NBTTagCompound nbttagcompound) {
    }

    public float c(float f0) {
        return 1.0F;
    }

    public boolean aE() {
        return false;
    }
}
