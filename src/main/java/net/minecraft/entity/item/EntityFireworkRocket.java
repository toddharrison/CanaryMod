package net.minecraft.entity.item;

import net.canarymod.api.entity.CanaryFireworkRocket;
import net.canarymod.api.entity.FireworkRocket;
import net.canarymod.hook.world.FireworkExplodeHook;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;


public class EntityFireworkRocket extends Entity {

    public int a; // CanaryMod: private => public; life
    public int b; // CanaryMod: private => public; life max

    public EntityFireworkRocket(World world) {
        super(world);
        this.a(0.25F, 0.25F);
        this.entity = new CanaryFireworkRocket(this); // CanaryMod: Wrap Entity
    }

    protected void h() {
        this.ac.a(8, 5);
    }

    public EntityFireworkRocket(World world, double d0, double d1, double d2, ItemStack itemstack) {
        super(world);
        this.a = 0;
        this.a(0.25F, 0.25F);
        this.b(d0, d1, d2);
        int i0 = 1;

        if (itemstack != null && itemstack.n()) {
            this.ac.b(8, itemstack);
            NBTTagCompound nbttagcompound = itemstack.o();
            NBTTagCompound nbttagcompound1 = nbttagcompound.m("Fireworks");

            if (nbttagcompound1 != null) {
                i0 += nbttagcompound1.d("Flight");
            }
        }

        this.v = this.V.nextGaussian() * 0.001D;
        this.x = this.V.nextGaussian() * 0.001D;
        this.w = 0.05D;
        this.b = 10 * i0 + this.V.nextInt(6) + this.V.nextInt(7);
        this.entity = new CanaryFireworkRocket(this); // CanaryMod: Wrap Entity
    }

    public void s_() {
        this.P = this.s;
        this.Q = this.t;
        this.R = this.u;
        super.s_();
        this.v *= 1.15D;
        this.x *= 1.15D;
        this.w += 0.04D;
        this.d(this.v, this.w, this.x);
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
        if (this.a == 0 && !this.R()) {
            this.o.a((Entity) this, "fireworks.launch", 3.0F, 1.0F);
        }

        ++this.a;
        if (this.o.D && this.a % 2 < 2) {
            this.o.a(EnumParticleTypes.FIREWORKS_SPARK, this.s, this.t - 0.3D, this.u, this.V.nextGaussian() * 0.05D, -this.w * 0.5D, this.V.nextGaussian() * 0.05D, new int[0]);
        }

        if (!this.o.D && this.a > this.b) {
            // CanaryMod: FireworkExplode
            FireworkExplodeHook hook = (FireworkExplodeHook) new FireworkExplodeHook((FireworkRocket) this.getCanaryEntity()).call();
            if (!hook.isCanceled()) {
                this.o.a((Entity) this, (byte) 17);
                this.J();
            }
            //
        }

    }

    public void b(NBTTagCompound nbttagcompound) {
        nbttagcompound.a("Life", this.a);
        nbttagcompound.a("LifeTime", this.b);
        ItemStack itemstack = this.ac.f(8);

        if (itemstack != null) {
            NBTTagCompound nbttagcompound1 = new NBTTagCompound();

            itemstack.b(nbttagcompound1);
            nbttagcompound.a("FireworksItem", (NBTBase) nbttagcompound1);
        }

    }

    public void a(NBTTagCompound nbttagcompound) {
        this.a = nbttagcompound.f("Life");
        this.b = nbttagcompound.f("LifeTime");
        NBTTagCompound nbttagcompound1 = nbttagcompound.m("FireworksItem");

        if (nbttagcompound1 != null) {
            ItemStack itemstack = ItemStack.a(nbttagcompound1);

            if (itemstack != null) {
                this.ac.b(8, itemstack);
            }
        }

    }

    public float c(float f0) {
        return super.c(f0);
    }

    public boolean aE() {
        return false;
    }

    // CanaryMod
    public ItemStack getItemStack() {
        return this.ac.f(8);
    }

    public void setItemStack(ItemStack stack) {
        this.ac.b(8, stack);
        // Update Flight information
        NBTTagCompound nbttagcompound = stack.q();
        NBTTagCompound nbttagcompound1 = nbttagcompound.m("Fireworks");
        if (nbttagcompound1 != null) {
            this.b = 10 * nbttagcompound1.d("Flight") + this.V.nextInt(6) + this.V.nextInt(7);
        }
    }
//
}
