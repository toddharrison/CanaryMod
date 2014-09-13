package net.minecraft.entity.passive;

import net.canarymod.api.entity.living.animal.CanaryBat;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Calendar;

public class EntityBat extends EntityAmbientCreature {

    private ChunkCoordinates h;

    public EntityBat(World world) {
        super(world);
        this.a(0.5F, 0.9F);
        this.a(true);
        this.entity = new CanaryBat(this); // CanaryMod: Wrap Entity
    }

    protected void c() {
        super.c();
        this.af.a(16, new Byte((byte) 0));
    }

    protected float bf() {
        return 0.1F;
    }

    protected float bg() {
        return super.bg() * 0.95F;
    }

    protected String t() {
        return this.bP() && this.Z.nextInt(4) != 0 ? null : "mob.bat.idle";
    }

    protected String aT() {
        return "mob.bat.hurt";
    }

    protected String aU() {
        return "mob.bat.death";
    }

    public boolean S() {
        return false;
    }

    protected void o(Entity entity) {
    }

    protected void bo() {
    }

    protected void aD() {
        super.aD();
        this.a(SharedMonsterAttributes.a).a(6.0D);
    }

    public boolean bP() {
        return (this.af.a(16) & 1) != 0;
    }

    public void a(boolean flag0) {
        byte b0 = this.af.a(16);

        if (flag0) {
            this.af.b(16, Byte.valueOf((byte) (b0 | 1)));
        } else {
            this.af.b(16, Byte.valueOf((byte) (b0 & -2)));
        }
    }

    protected boolean bk() {
        return true;
    }

    public void h() {
        super.h();
        if (this.bP()) {
            this.v = this.w = this.x = 0.0D;
            this.t = (double) MathHelper.c(this.t) + 1.0D - (double) this.N;
        } else {
            this.w *= 0.6000000238418579D;
        }
    }

    protected void bn() {
        super.bn();
        if (this.bP()) {
            if (!this.o.a(MathHelper.c(this.s), (int) this.t + 1, MathHelper.c(this.u)).r()) {
                this.a(false);
                this.o.a((EntityPlayer) null, 1015, (int) this.s, (int) this.t, (int) this.u, 0);
            } else {
                if (this.Z.nextInt(200) == 0) {
                    this.aO = (float) this.Z.nextInt(360);
                }

                if (this.o.a(this, 4.0D) != null) {
                    this.a(false);
                    this.o.a((EntityPlayer) null, 1015, (int) this.s, (int) this.t, (int) this.u, 0);
                }
            }
        } else {
            if (this.h != null && (!this.o.c(this.h.a, this.h.b, this.h.c) || this.h.b < 1)) {
                this.h = null;
            }

            if (this.h == null || this.Z.nextInt(30) == 0 || this.h.e((int) this.s, (int) this.t, (int) this.u) < 4.0F) {
                this.h = new ChunkCoordinates((int) this.s + this.Z.nextInt(7) - this.Z.nextInt(7), (int) this.t + this.Z.nextInt(6) - 2, (int) this.u + this.Z.nextInt(7) - this.Z.nextInt(7));
            }

            double d0 = (double) this.h.a + 0.5D - this.s;
            double d1 = (double) this.h.b + 0.1D - this.t;
            double d2 = (double) this.h.c + 0.5D - this.u;

            this.v += (Math.signum(d0) * 0.5D - this.v) * 0.10000000149011612D;
            this.w += (Math.signum(d1) * 0.699999988079071D - this.w) * 0.10000000149011612D;
            this.x += (Math.signum(d2) * 0.5D - this.x) * 0.10000000149011612D;
            float f0 = (float) (Math.atan2(this.x, this.v) * 180.0D / 3.1415927410125732D) - 90.0F;
            float f1 = MathHelper.g(f0 - this.y);

            this.be = 0.5F;
            this.y += f1;
            if (this.Z.nextInt(100) == 0 && this.o.a(MathHelper.c(this.s), (int) this.t + 1, MathHelper.c(this.u)).r()) {
                this.a(true);
            }
        }
    }

    protected boolean g_() {
        return false;
    }

    protected void b(float f0) {
    }

    protected void a(double d0, boolean flag0) {
    }

    public boolean az() {
        return true;
    }

    public boolean a(DamageSource damagesource, float f0) {
        if (this.aw()) {
            return false;
        } else {
            if (!this.o.E && this.bP()) {
                this.a(false);
            }

            return super.a(damagesource, f0);
        }
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.af.b(16, Byte.valueOf(nbttagcompound.d("BatFlags")));
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("BatFlags", this.af.a(16));
    }

    public boolean by() {
        int i0 = MathHelper.c(this.C.b);

        if (i0 >= 63) {
            return false;
        } else {
            int i1 = MathHelper.c(this.s);
            int i2 = MathHelper.c(this.u);
            int i3 = this.o.k(i1, i0, i2);
            byte b0 = 4;
            Calendar calendar = this.o.V();

            if ((calendar.get(2) + 1 != 10 || calendar.get(5) < 20) && (calendar.get(2) + 1 != 11 || calendar.get(5) > 3)) {
                if (this.Z.nextBoolean()) {
                    return false;
                }
            } else {
                b0 = 7;
            }

            return i3 > this.Z.nextInt(b0) ? false : super.by();
        }
    }
}
