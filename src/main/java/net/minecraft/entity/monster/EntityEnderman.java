package net.minecraft.entity.monster;

import net.canarymod.api.entity.living.monster.CanaryEnderman;
import net.canarymod.config.Configuration;
import net.canarymod.hook.entity.EndermanDropBlockHook;
import net.canarymod.hook.entity.EndermanPickupBlockHook;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.UUID;

public class EntityEnderman extends EntityMob {

    private static final UUID bp = UUID.fromString("020E0DFB-87AE-4653-9556-831010E291A0");
    private static final AttributeModifier bq = (new AttributeModifier(bp, "Attacking speed boost", 6.199999809265137D, 0)).a(false);
    // private static boolean[] br = new boolean[256]; //CanaryMod: disabled
    private int bs;
    private int bt;
    private Entity bu;
    private boolean bv;

    public EntityEnderman(World world) {
        super(world);
        this.a(0.6F, 2.9F);
        this.W = 1.0F;
        this.entity = new CanaryEnderman(this); // CanaryMod: Wrap Entity
    }

    protected void aD() {
        super.aD();
        this.a(SharedMonsterAttributes.a).a(40.0D);
        this.a(SharedMonsterAttributes.d).a(0.30000001192092896D);
        this.a(SharedMonsterAttributes.e).a(7.0D);
    }

    protected void c() {
        super.c();
        this.af.a(16, new Byte((byte) 0));
        this.af.a(17, new Byte((byte) 0));
        this.af.a(18, new Byte((byte) 0));
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("carried", (short) Block.b(this.cb()));
        nbttagcompound.a("carriedData", (short) this.cc());
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.a(Block.e(nbttagcompound.e("carried")));
        this.a(nbttagcompound.e("carriedData"));
    }

    protected Entity bR() {
        EntityPlayer entityplayer = this.o.b(this, 64.0D);

        if (entityplayer != null) {
            if (this.f(entityplayer)) {
                this.bv = true;
                if (this.bt == 0) {
                    this.o.a(entityplayer.s, entityplayer.t, entityplayer.u, "mob.endermen.stare", 1.0F, 1.0F);
                }

                if (this.bt++ == 5) {
                    this.bt = 0;
                    this.a(true);
                    return entityplayer;
                }
            } else {
                this.bt = 0;
            }
        }

        return null;
    }

    private boolean f(EntityPlayer entityplayer) {
        ItemStack itemstack = entityplayer.bm.b[3];

        if (itemstack != null && itemstack.b() == Item.a(Blocks.aK)) {
            return false;
        } else {
            Vec3 vec3 = entityplayer.j(1.0F).a();
            Vec3 vec31 = Vec3.a(this.s - entityplayer.s, this.C.b + (double) (this.N / 2.0F) - (entityplayer.t + (double) entityplayer.g()), this.u - entityplayer.u);
            double d0 = vec31.b();

            vec31 = vec31.a();
            double d1 = vec3.b(vec31);

            return d1 > 1.0D - 0.025D / d0 && entityplayer.p(this);
        }
    }

    public void e() {
        if (this.L()) {
            this.a(DamageSource.e, 1.0F);
        }

        if (this.bu != this.bm) {
            IAttributeInstance iattributeinstance = this.a(SharedMonsterAttributes.d);

            iattributeinstance.b(bq);
            if (this.bm != null) {
                iattributeinstance.a(bq);
            }
        }

        this.bu = this.bm;
        int i0;

        if (!this.o.E && this.o.O().b("mobGriefing")) {
            int i1;
            int i2;
            Block block;

            if (this.cb().o() == Material.a) {
                if (this.Z.nextInt(20) == 0) {
                    i0 = MathHelper.c(this.s - 2.0D + this.Z.nextDouble() * 4.0D);
                    i1 = MathHelper.c(this.t + this.Z.nextDouble() * 3.0D);
                    i2 = MathHelper.c(this.u - 2.0D + this.Z.nextDouble() * 4.0D);
                    block = this.o.a(i0, i1, i2);
                    // CanaryMod: Replace checking static array with checking the world config list for Ender Blocks
                    if (Arrays.asList(Configuration.getWorldConfig(getCanaryWorld().getFqName()).getEnderBlocks()).contains(Block.b(block))) {
                        // CanaryMod: call EndermanPickupBlockHook
                        EndermanPickupBlockHook hook = (EndermanPickupBlockHook) new EndermanPickupBlockHook((CanaryEnderman) entity, entity.getWorld().getBlockAt(i0, i1, i2)).call();
                        if (!hook.isCanceled()) {
                            this.a(block);
                            this.a(this.o.e(i0, i1, i2));
                            this.o.b(i0, i1, i2, Blocks.a);
                        }
                        //
                    }
                }
            } else if (this.Z.nextInt(2000) == 0) {
                i0 = MathHelper.c(this.s - 1.0D + this.Z.nextDouble() * 2.0D);
                i1 = MathHelper.c(this.t + this.Z.nextDouble() * 2.0D);
                i2 = MathHelper.c(this.u - 1.0D + this.Z.nextDouble() * 2.0D);
                block = this.o.a(i0, i1, i2);
                Block block1 = this.o.a(i0, i1 - 1, i2);

                if (block.o() == Material.a && block1.o() != Material.a && block1.d()) {
                    // CanaryMod: call EndermanDropBlockHook
                    EndermanDropBlockHook hook = (EndermanDropBlockHook) new EndermanDropBlockHook((CanaryEnderman) entity, entity.getWorld().getBlockAt(i0, i1, i2)).call();
                    if (!hook.isCanceled()) {
                        this.o.d(i0, i1, i2, this.cb(), this.cc(), 3);
                        this.a(Blocks.a);
                    }
                    //
                }
            }
        }

        for (i0 = 0; i0 < 2; ++i0) {
            this.o.a("portal", this.s + (this.Z.nextDouble() - 0.5D) * (double) this.M, this.t + this.Z.nextDouble() * (double) this.N - 0.25D, this.u + (this.Z.nextDouble() - 0.5D) * (double) this.M, (this.Z.nextDouble() - 0.5D) * 2.0D, -this.Z.nextDouble(), (this.Z.nextDouble() - 0.5D) * 2.0D);
        }

        if (this.o.w() && !this.o.E) {
            float f0 = this.d(1.0F);

            if (f0 > 0.5F && this.o.i(MathHelper.c(this.s), MathHelper.c(this.t), MathHelper.c(this.u)) && this.Z.nextFloat() * 30.0F < (f0 - 0.4F) * 2.0F) {
                this.bm = null;
                this.a(false);
                this.bv = false;
                this.bZ();
            }
        }

        if (this.L() || this.al()) {
            this.bm = null;
            this.a(false);
            this.bv = false;
            this.bZ();
        }

        if (this.cd() && !this.bv && this.Z.nextInt(100) == 0) {
            this.a(false);
        }

        this.bc = false;
        if (this.bm != null) {
            this.a(this.bm, 100.0F, 100.0F);
        }

        if (!this.o.E && this.Z()) {
            if (this.bm != null) {
                if (this.bm instanceof EntityPlayer && this.f((EntityPlayer) this.bm)) {
                    if (this.bm.f((Entity) this) < 16.0D) {
                        this.bZ();
                    }

                    this.bs = 0;
                } else if (this.bm.f((Entity) this) > 256.0D && this.bs++ >= 30 && this.c(this.bm)) {
                    this.bs = 0;
                }
            } else {
                this.a(false);
                this.bs = 0;
            }
        }

        super.e();
    }

    public boolean bZ() { // CanaryMod: protected -> public
        double d0 = this.s + (this.Z.nextDouble() - 0.5D) * 64.0D;
        double d1 = this.t + (double) (this.Z.nextInt(64) - 32);
        double d2 = this.u + (this.Z.nextDouble() - 0.5D) * 64.0D;

        return this.k(d0, d1, d2);
    }

    protected boolean c(Entity entity) {
        Vec3 vec3 = Vec3.a(this.s - entity.s, this.C.b + (double) (this.N / 2.0F) - entity.t + (double) entity.g(), this.u - entity.u);

        vec3 = vec3.a();
        double d0 = 16.0D;
        double d1 = this.s + (this.Z.nextDouble() - 0.5D) * 8.0D - vec3.a * d0;
        double d2 = this.t + (double) (this.Z.nextInt(16) - 8) - vec3.b * d0;
        double d3 = this.u + (this.Z.nextDouble() - 0.5D) * 8.0D - vec3.c * d0;

        return this.k(d1, d2, d3);
    }

    protected boolean k(double d0, double d1, double d2) {
        double d3 = this.s;
        double d4 = this.t;
        double d5 = this.u;

        this.s = d0;
        this.t = d1;
        this.u = d2;
        boolean flag0 = false;
        int i0 = MathHelper.c(this.s);
        int i1 = MathHelper.c(this.t);
        int i2 = MathHelper.c(this.u);

        if (this.o.d(i0, i1, i2)) {
            boolean flag1 = false;

            while (!flag1 && i1 > 0) {
                Block block = this.o.a(i0, i1 - 1, i2);

                if (block.o().c()) {
                    flag1 = true;
                } else {
                    --this.t;
                    --i1;
                }
            }

            if (flag1) {
                this.b(this.s, this.t, this.u);
                if (this.o.a((Entity) this, this.C).isEmpty() && !this.o.d(this.C)) {
                    flag0 = true;
                }
            }
        }

        if (!flag0) {
            this.b(d3, d4, d5);
            return false;
        } else {
            short short1 = 128;

            for (int i3 = 0; i3 < short1; ++i3) {
                double d6 = (double) i3 / ((double) short1 - 1.0D);
                float f0 = (this.Z.nextFloat() - 0.5F) * 0.2F;
                float f1 = (this.Z.nextFloat() - 0.5F) * 0.2F;
                float f2 = (this.Z.nextFloat() - 0.5F) * 0.2F;
                double d7 = d3 + (this.s - d3) * d6 + (this.Z.nextDouble() - 0.5D) * (double) this.M * 2.0D;
                double d8 = d4 + (this.t - d4) * d6 + this.Z.nextDouble() * (double) this.N;
                double d9 = d5 + (this.u - d5) * d6 + (this.Z.nextDouble() - 0.5D) * (double) this.M * 2.0D;

                this.o.a("portal", d7, d8, d9, (double) f0, (double) f1, (double) f2);
            }

            this.o.a(d3, d4, d5, "mob.endermen.portal", 1.0F, 1.0F);
            this.a("mob.endermen.portal", 1.0F, 1.0F);
            return true;
        }
    }

    protected String t() {
        return this.cd() ? "mob.endermen.scream" : "mob.endermen.idle";
    }

    protected String aT() {
        return "mob.endermen.hit";
    }

    protected String aU() {
        return "mob.endermen.death";
    }

    protected Item u() {
        return Items.bi;
    }

    protected void b(boolean flag0, int i0) {
        Item item = this.u();

        if (item != null) {
            int i1 = this.Z.nextInt(2 + i0);

            for (int i2 = 0; i2 < i1; ++i2) {
                this.a(item, 1);
            }
        }
    }

    public void a(Block block) {
        this.af.b(16, Byte.valueOf((byte) (Block.b(block) & 255)));
    }

    public Block cb() {
        return Block.e(this.af.a(16));
    }

    public void a(int i0) {
        this.af.b(17, Byte.valueOf((byte) (i0 & 255)));
    }

    public int cc() {
        return this.af.a(17);
    }

    public boolean a(DamageSource damagesource, float f0) {
        if (this.aw()) {
            return false;
        } else {
            this.a(true);
            if (damagesource instanceof EntityDamageSource && damagesource.j() instanceof EntityPlayer) {
                this.bv = true;
            }

            if (damagesource instanceof EntityDamageSourceIndirect) {
                this.bv = false;

                for (int i0 = 0; i0 < 64; ++i0) {
                    if (this.bZ()) {
                        return true;
                    }
                }

                return false;
            } else {
                return super.a(damagesource, f0);
            }
        }
    }

    public boolean cd() {
        return this.af.a(18) > 0;
    }

    public void a(boolean flag0) {
        this.af.b(18, Byte.valueOf((byte) (flag0 ? 1 : 0)));
    }

/* CanaryMod: Disable all default allowed pick ups
        static {
            // br[Block.z.cF] = true;
            // br[Block.A.cF] = true;
            // br[Block.J.cF] = true;
            // br[Block.K.cF] = true;
            // br[Block.ai.cF] = true;
            // br[Block.aj.cF] = true;
            // br[Block.ak.cF] = true;
            // br[Block.al.cF] = true;
            // br[Block.ar.cF] = true;
            // br[Block.ba.cF] = true;
            // br[Block.bb.cF] = true;
            // br[Block.bf.cF] = true;
            // br[Block.bw.cF] = true;
            // br[Block.bD.cF] = true;
        } 
*/
}
