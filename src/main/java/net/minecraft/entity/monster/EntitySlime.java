package net.minecraft.entity.monster;

import net.canarymod.api.entity.living.monster.CanarySlime;
import net.canarymod.api.entity.living.monster.Slime;
import net.canarymod.hook.entity.MobTargetHook;
import net.canarymod.hook.entity.SlimeSplitHook;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

import java.util.ArrayList;
import java.util.List;

public class EntitySlime extends EntityLiving implements IMob {

    public float h;
    public float i;
    public float bm;
    private int bn;

    public EntitySlime(World world) {
        super(world);
        int i0 = 1 << this.Z.nextInt(3);

        this.L = 0.0F;
        this.bn = this.Z.nextInt(20) + 10;
        this.a(i0);
        this.entity = new CanarySlime(this); // CanaryMod: Wrap Entity
    }

    protected void c() {
        super.c();
        this.af.a(16, new Byte((byte) 1));
    }

    public void a(int i0) { // CanaryMod: protected => public
        this.af.b(16, new Byte((byte) i0));
        this.a(0.6F * (float) i0, 0.6F * (float) i0);
        this.b(this.s, this.t, this.u);
        this.a(SharedMonsterAttributes.a).a((double) (i0 * i0));
        this.g(this.aY());
        this.b = i0;
    }

    public int bX() {
        return this.af.a(16);
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("Size", this.bX() - 1);
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        int i0 = nbttagcompound.f("Size");

        if (i0 < 0) {
            i0 = 0;
        }

        this.a(i0 + 1);
    }

    protected String bP() {
        return "slime";
    }

    protected String bV() {
        return "mob.slime." + (this.bX() > 1 ? "big" : "small");
    }

    public void h() {
        if (!this.o.E && this.o.r == EnumDifficulty.PEACEFUL && this.bX() > 0) {
            this.K = true;
        }

        this.i += (this.h - this.i) * 0.5F;
        this.bm = this.i;
        boolean flag0 = this.D;

        super.h();
        int i0;

        if (this.D && !flag0) {
            i0 = this.bX();

            for (int i1 = 0; i1 < i0 * 8; ++i1) {
                float f0 = this.Z.nextFloat() * 3.1415927F * 2.0F;
                float f1 = this.Z.nextFloat() * 0.5F + 0.5F;
                float f2 = MathHelper.a(f0) * (float) i0 * 0.5F * f1;
                float f3 = MathHelper.b(f0) * (float) i0 * 0.5F * f1;

                this.o.a(this.bP(), this.s + (double) f2, this.C.b, this.u + (double) f3, 0.0D, 0.0D, 0.0D);
            }

            if (this.bW()) {
                this.a(this.bV(), this.bf(), ((this.Z.nextFloat() - this.Z.nextFloat()) * 0.2F + 1.0F) / 0.8F);
            }

            this.h = -0.5F;
        }
        else if (!this.D && flag0) {
            this.h = 1.0F;
        }

        this.bS();
        if (this.o.E) {
            i0 = this.bX();
            this.a(0.6F * (float) i0, 0.6F * (float) i0);
        }
    }

    protected void bq() {
        this.w();
        EntityPlayer entityplayer = this.o.b(this, 16.0D);

        if (entityplayer != null) {
            // CanaryMod: MobTarget
            MobTargetHook hook = (MobTargetHook) new MobTargetHook((net.canarymod.api.entity.living.LivingBase) this.getCanaryEntity(), (net.canarymod.api.entity.living.LivingBase) entityplayer.getCanaryEntity()).call();
            if (!hook.isCanceled()) {
                this.a(entityplayer, 10.0F, 20.0F);
            }
            else {
                entityplayer = null;
            }
            //
        }

        if (this.D && this.bn-- <= 0) {
            this.bn = this.bR();
            if (entityplayer != null) {
                this.bn /= 3;
            }

            this.bc = true;
            if (this.bY()) {
                this.a(this.bV(), this.bf(), ((this.Z.nextFloat() - this.Z.nextFloat()) * 0.2F + 1.0F) * 0.8F);
            }

            this.bd = 1.0F - this.Z.nextFloat() * 2.0F;
            this.be = (float) (1 * this.bX());
        }
        else {
            this.bc = false;
            if (this.D) {
                this.bd = this.be = 0.0F;
            }
        }
    }

    protected void bS() {
        this.h *= 0.6F;
    }

    protected int bR() {
        return this.Z.nextInt(20) + 10;
    }

    protected EntitySlime bQ() {
        return new EntitySlime(this.o);
    }

    public void B() {
        int i0 = this.bX();

        if (!this.o.E && i0 > 1 && this.aS() <= 0.0F) {
            int i1 = 2 + this.Z.nextInt(3);

            List<Slime> slimes = new ArrayList<Slime>();
            for (int i2 = 0; i2 < i1; ++i2) {
                float f0 = ((float) (i2 % 2) - 0.5F) * (float) i0 / 4.0F;
                float f1 = ((float) (i2 / 2) - 0.5F) * (float) i0 / 4.0F;
                EntitySlime entityslime = this.bQ();

                entityslime.a(i0 / 2);
                entityslime.b(this.s + (double) f0, this.t + 0.5D, this.u + (double) f1, this.Z.nextFloat() * 360.0F, 0.0F);

                // CanaryMod: get the slimes that should spawn
                slimes.add((Slime) entityslime.getCanaryEntity());
            }

            new SlimeSplitHook((Slime) this.getCanaryEntity(), slimes).call(); // CanaryMod: SlimeSplitHook, call to change the slimes

            // Spawn Slimes after calling the event and allowing to change them
            for (Slime slime : slimes) {
                slime.spawn();
            }
            //
        }

        super.B();
    }

    public void b_(EntityPlayer entityplayer) {
        if (this.bT()) {
            int i0 = this.bX();

            if (this.p(entityplayer) && this.f(entityplayer) < 0.6D * (double) i0 * 0.6D * (double) i0 && entityplayer.a(DamageSource.a((EntityLivingBase) this), (float) this.bU())) {
                this.a("mob.attack", 1.0F, (this.Z.nextFloat() - this.Z.nextFloat()) * 0.2F + 1.0F);
            }
        }
    }

    protected boolean bT() {
        return this.bX() > 1;
    }

    protected int bU() {
        return this.bX();
    }

    protected String aT() {
        return "mob.slime." + (this.bX() > 1 ? "big" : "small");
    }

    protected String aU() {
        return "mob.slime." + (this.bX() > 1 ? "big" : "small");
    }

    protected Item u() {
        return this.bX() == 1 ? Items.aH : Item.d(0);
    }

    public boolean by() {
        Chunk chunk = this.o.d(MathHelper.c(this.s), MathHelper.c(this.u));

        if (this.o.N().u() == WorldType.c && this.Z.nextInt(4) != 1) {
            return false;
        }
        else {
            if (this.bX() == 1 || this.o.r != EnumDifficulty.PEACEFUL) {
                BiomeGenBase biomegenbase = this.o.a(MathHelper.c(this.s), MathHelper.c(this.u));

                if (biomegenbase == BiomeGenBase.u && this.t > 50.0D && this.t < 70.0D && this.Z.nextFloat() < 0.5F && this.Z.nextFloat() < this.o.y() && this.o.k(MathHelper.c(this.s), MathHelper.c(this.t), MathHelper.c(this.u)) <= this.Z.nextInt(8)) {
                    return super.by();
                }

                if (this.Z.nextInt(10) == 0 && chunk.a(987234911L).nextInt(10) == 0 && this.t < 40.0D) {
                    return super.by();
                }
            }

            return false;
        }
    }

    protected float bf() {
        return 0.4F * (float) this.bX();
    }

    public int x() {
        return 0;
    }

    protected boolean bY() {
        return this.bX() > 0;
    }

    protected boolean bW() {
        return this.bX() > 2;
    }
}
