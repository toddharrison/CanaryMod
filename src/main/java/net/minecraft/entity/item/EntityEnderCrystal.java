package net.minecraft.entity.item;

import net.canarymod.api.entity.CanaryEnderCrystal;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;


public class EntityEnderCrystal extends Entity {

    public int a;
    public int b;

    public EntityEnderCrystal(World world) {
        super(world);
        this.k = true;
        this.a(2.0F, 2.0F);
        this.b = 5;
        this.a = this.V.nextInt(100000);
        this.entity = new CanaryEnderCrystal(this); // CanaryMod: Wrap Entity

    }

    protected boolean r_() {
        return false;
    }

    protected void h() {
        this.ac.a(8, Integer.valueOf(this.b));
    }

    public void s_() {
        this.p = this.s;
        this.q = this.t;
        this.r = this.u;
        ++this.a;
        this.ac.b(8, Integer.valueOf(this.b));
        int i0 = MathHelper.c(this.s);
        int i1 = MathHelper.c(this.t);
        int i2 = MathHelper.c(this.u);

        if (this.o.t instanceof WorldProviderEnd && this.o.p(new BlockPos(i0, i1, i2)).c() != Blocks.ab) {
            this.o.a(new BlockPos(i0, i1, i2), Blocks.ab.P());
        }

    }

    protected void b(NBTTagCompound nbttagcompound) {
    }

    protected void a(NBTTagCompound nbttagcompound) {
    }

    public boolean ad() {
        return true;
    }

    public boolean a(DamageSource damagesource, float f0) {
        if (this.b(damagesource)) {
            return false;
        }
        else {
            if (!this.I && !this.o.D) {
                // CanaryMod: Check if one hit can kill this entity
                if (((CanaryEnderCrystal) entity).isOneHitDetonate()) {
                    this.b = 0;
                }
                else {
                    this.b -= f0;
                }
                //
                if (this.b <= 0) {
                    this.J();
                    if (!this.o.D) {
                        // CanaryMod: configure Explosion power and set the entity properly
                        this.o.a((Entity) null, this.s, this.t, this.u, 6.0F, true);
                    }
                }
            }

            return true;
        }
    }
}
