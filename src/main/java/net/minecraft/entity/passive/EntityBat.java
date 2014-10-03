package net.minecraft.entity.passive;

import net.canarymod.api.entity.living.animal.CanaryBat;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Calendar;

public class EntityBat extends EntityAmbientCreature {

    private BlockPos a;
   
    public EntityBat(World world) {
        super(world);
        this.a(0.5F, 0.9F);
        this.a(true);
        this.entity = new CanaryBat(this); // CanaryMod: Wrap Entity
    }

    protected void h() {
        super.h();
        this.ac.a(16, new Byte((byte) 0));
    }

    protected float bA() {
        return 0.1F;
    }

    protected float bB() {
        return super.bB() * 0.95F;
    }

    protected String z() {
        return this.n() && this.V.nextInt(4) != 0 ? null : "mob.bat.idle";
    }

    protected String bn() {
        return "mob.bat.hurt";
    }

    protected String bo() {
        return "mob.bat.death";
    }

    public boolean ae() {
        return false;
    }

    protected void s(Entity entity) {}

    protected void bK() {}

    protected void aW() {
        super.aW();
        this.a(SharedMonsterAttributes.a).a(6.0D);
    }

    public boolean n() {
        return (this.ac.a(16) & 1) != 0;
    }

    public void a(boolean flag0) {
        byte b0 = this.ac.a(16);

        if (flag0) {
            this.ac.b(16, Byte.valueOf((byte) (b0 | 1)));
        } else {
            this.ac.b(16, Byte.valueOf((byte) (b0 & -2)));
        }

    }

    public void s_() {
        super.s_();
        if (this.n()) {
            this.v = this.w = this.x = 0.0D;
            this.t = (double) MathHelper.c(this.t) + 1.0D - (double) this.K;
        } else {
            this.w *= 0.6000000238418579D;
        }

    }

    protected void E() {
        super.E();
        BlockPos blockpos = new BlockPos(this);
        BlockPos blockpos1 = blockpos.a();

        if (this.n()) {
            if (!this.o.p(blockpos1).c().t()) {
                this.a(false);
                this.o.a((EntityPlayer) null, 1015, blockpos, 0);
            } else {
                if (this.V.nextInt(200) == 0) {
                    this.aI = (float) this.V.nextInt(360);
                }

                if (this.o.a(this, 4.0D) != null) {
                    this.a(false);
                    this.o.a((EntityPlayer) null, 1015, blockpos, 0);
                }
            }
        } else {
            if (this.a != null && (!this.o.d(this.a) || this.a.o() < 1)) {
                this.a = null;
            }

            if (this.a == null || this.V.nextInt(30) == 0 || this.a.c((double) ((int) this.s), (double) ((int) this.t), (double) ((int) this.u)) < 4.0D) {
                this.a = new BlockPos((int) this.s + this.V.nextInt(7) - this.V.nextInt(7), (int) this.t + this.V.nextInt(6) - 2, (int) this.u + this.V.nextInt(7) - this.V.nextInt(7));
            }

            double d0 = (double) this.a.n() + 0.5D - this.s;
            double d1 = (double) this.a.o() + 0.1D - this.t;
            double d2 = (double) this.a.p() + 0.5D - this.u;

            this.v += (Math.signum(d0) * 0.5D - this.v) * 0.10000000149011612D;
            this.w += (Math.signum(d1) * 0.699999988079071D - this.w) * 0.10000000149011612D;
            this.x += (Math.signum(d2) * 0.5D - this.x) * 0.10000000149011612D;
            float f0 = (float) (Math.atan2(this.x, this.v) * 180.0D / 3.1415927410125732D) - 90.0F;
            float f1 = MathHelper.g(f0 - this.y);

            this.aY = 0.5F;
            this.y += f1;
            if (this.V.nextInt(100) == 0 && this.o.p(blockpos1).c().t()) {
                this.a(true);
            }
        }

    }

    protected boolean r_() {
        return false;
    }

    public void e(float f0, float f1) {}

    protected void a(double d0, boolean flag0, Block block, BlockPos blockpos) {}

    public boolean aH() {
        return true;
    }

    public boolean a(DamageSource damagesource, float f0) {
        if (this.b(damagesource)) {
            return false;
        } else {
            if (!this.o.D && this.n()) {
                this.a(false);
            }

            return super.a(damagesource, f0);
        }
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.ac.b(16, Byte.valueOf(nbttagcompound.d("BatFlags")));
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("BatFlags", this.ac.a(16));
    }

    public boolean bQ() {
        BlockPos blockpos = new BlockPos(this.s, this.aQ().b, this.u);

        if (blockpos.o() >= 63) {
            return false;
        } else {
            int i0 = this.o.l(blockpos);
            byte b0 = 4;

            if (this.a(this.o.Y())) {
                b0 = 7;
            } else if (this.V.nextBoolean()) {
                return false;
            }

            return i0 > this.V.nextInt(b0) ? false : super.bQ();
        }
    }

    private boolean a(Calendar calendar) {
        return calendar.get(2) + 1 == 10 && calendar.get(5) >= 20 || calendar.get(2) + 1 == 11 && calendar.get(5) <= 3;
    }

    public float aR() {
        return this.K / 2.0F;
    }
}
