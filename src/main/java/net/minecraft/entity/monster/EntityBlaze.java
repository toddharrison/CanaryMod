package net.minecraft.entity.monster;

import net.canarymod.api.entity.living.monster.CanaryBlaze;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityBlaze extends EntityMob {

    private float bp = 0.5F;
    private int bq;
    private int br;

    public EntityBlaze(World world) {
        super(world);
        this.ae = true;
        this.b = 10;
        this.entity = new CanaryBlaze(this); // CanaryMod: Wrap Entity
    }

    protected void aD() {
        super.aD();
        this.a(SharedMonsterAttributes.e).a(6.0D);
    }

    protected void c() {
        super.c();
        this.af.a(16, new Byte((byte) 0));
    }

    protected String t() {
        return "mob.blaze.breathe";
    }

    protected String aT() {
        return "mob.blaze.hit";
    }

    protected String aU() {
        return "mob.blaze.death";
    }

    public float d(float f0) {
        return 1.0F;
    }

    public void e() {
        if (!this.o.E) {
            if (this.L()) {
                this.a(DamageSource.e, 1.0F);
            }

            --this.bq;
            if (this.bq <= 0) {
                this.bq = 100;
                this.bp = 0.5F + (float) this.Z.nextGaussian() * 3.0F;
            }

            if (this.bT() != null && this.bT().t + (double) this.bT().g() > this.t + (double) this.g() + (double) this.bp) {
                this.w += (0.30000001192092896D - this.w) * 0.30000001192092896D;
            }
        }

        if (this.Z.nextInt(24) == 0) {
            this.o.a(this.s + 0.5D, this.t + 0.5D, this.u + 0.5D, "fire.fire", 1.0F + this.Z.nextFloat(), this.Z.nextFloat() * 0.7F + 0.3F);
        }

        if (!this.D && this.w < 0.0D) {
            this.w *= 0.6D;
        }

        for (int i0 = 0; i0 < 2; ++i0) {
            this.o.a("largesmoke", this.s + (this.Z.nextDouble() - 0.5D) * (double) this.M, this.t + this.Z.nextDouble() * (double) this.N, this.u + (this.Z.nextDouble() - 0.5D) * (double) this.M, 0.0D, 0.0D, 0.0D);
        }

        super.e();
    }

    protected void a(Entity entity, float f0) {
        if (this.aB <= 0 && f0 < 2.0F && entity.C.e > this.C.b && entity.C.b < this.C.e) {
            this.aB = 20;
            this.n(entity);
        }
        else if (f0 < 30.0F) {
            double d0 = entity.s - this.s;
            double d1 = entity.C.b + (double) (entity.N / 2.0F) - (this.t + (double) (this.N / 2.0F));
            double d2 = entity.u - this.u;

            if (this.aB == 0) {
                ++this.br;
                if (this.br == 1) {
                    this.aB = 60;
                    this.a(true);
                }
                else if (this.br <= 4) {
                    this.aB = 6;
                }
                else {
                    this.aB = 100;
                    this.br = 0;
                    this.a(false);
                }

                if (this.br > 1) {
                    float f1 = MathHelper.c(f0) * 0.5F;

                    this.o.a((EntityPlayer) null, 1009, (int) this.s, (int) this.t, (int) this.u, 0);

                    for (int i0 = 0; i0 < 1; ++i0) {
                        EntitySmallFireball entitysmallfireball = new EntitySmallFireball(this.o, this, d0 + this.Z.nextGaussian() * (double) f1, d1, d2 + this.Z.nextGaussian() * (double) f1);

                        entitysmallfireball.t = this.t + (double) (this.N / 2.0F) + 0.5D;
                        this.o.d((Entity) entitysmallfireball);
                    }
                }
            }

            this.y = (float) (Math.atan2(d2, d0) * 180.0D / 3.1415927410125732D) - 90.0F;
            this.bn = true;
        }
    }

    protected void b(float f0) {
    }

    protected Item u() {
        return Items.bj;
    }

    public boolean al() {
        return this.bZ();
    }

    protected void b(boolean flag0, int i0) {
        if (flag0) {
            int i1 = this.Z.nextInt(2 + i0);

            for (int i2 = 0; i2 < i1; ++i2) {
                this.a(Items.bj, 1);
            }
        }
    }

    public boolean bZ() {
        return (this.af.a(16) & 1) != 0;
    }

    public void a(boolean flag0) {
        byte b0 = this.af.a(16);

        if (flag0) {
            b0 = (byte) (b0 | 1);
        }
        else {
            b0 &= -2;
        }

        this.af.b(16, Byte.valueOf(b0));
    }

    protected boolean j_() {
        return true;
    }
}
