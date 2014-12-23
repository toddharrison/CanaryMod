package net.minecraft.entity.monster;

import net.canarymod.api.entity.living.monster.CanarySlime;
import net.canarymod.api.entity.living.monster.Slime;
import net.canarymod.hook.entity.SlimeSplitHook;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFindEntityNearest;
import net.minecraft.entity.ai.EntityAIFindEntityNearestPlayer;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

import java.util.ArrayList;
import java.util.List;

public class EntitySlime extends EntityLiving implements IMob {

    public float a;
    public float b;
    public float c;
    private boolean bi;

    public EntitySlime(World world) {
        super(world);
        this.f = new EntitySlime.SlimeMoveHelper();
        this.i.a(1, new EntitySlime.AISlimeFloat());
        this.i.a(2, new EntitySlime.AISlimeAttack());
        this.i.a(3, new EntitySlime.AISlimeFaceRandom());
        this.i.a(5, new EntitySlime.AISlimeHop());
        this.bg.a(1, new EntityAIFindEntityNearestPlayer(this));
        this.bg.a(3, new EntityAIFindEntityNearest(this, EntityIronGolem.class));
        this.entity = new CanarySlime(this); // CanaryMod: Wrap Entity
    }

    protected void h() {
        super.h();
        this.ac.a(16, Byte.valueOf((byte) 1));
    }

    public void a(int i0) { // CanaryMod: protected >> public
        this.ac.b(16, Byte.valueOf((byte) i0));
        this.a(0.51000005F * (float) i0, 0.51000005F * (float) i0);
        this.b(this.s, this.t, this.u);
        this.a(SharedMonsterAttributes.a).a((double) (i0 * i0));
        this.a(SharedMonsterAttributes.d).a((double) (0.2F + 0.1F * (float) i0));
        this.h(this.bt());
        this.b_ = i0;
    }

    public int ck() {
        return this.ac.a(16);
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("Size", this.ck() - 1);
        nbttagcompound.a("wasOnGround", this.bi);
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        int i0 = nbttagcompound.f("Size");

        if (i0 < 0) {
            i0 = 0;
        }

        this.a(i0 + 1);
        this.bi = nbttagcompound.n("wasOnGround");
    }

    protected EnumParticleTypes n() {
        return EnumParticleTypes.SLIME;
    }

    protected String ci() {
        return "mob.slime." + (this.ck() > 1 ? "big" : "small");
    }

    public void s_() {
        if (!this.o.D && this.o.aa() == EnumDifficulty.PEACEFUL && this.ck() > 0) {
            this.I = true;
        }

        this.b += (this.a - this.b) * 0.5F;
        this.c = this.b;
        super.s_();
        if (this.C && !this.bi) {
            int i0 = this.ck();

            for (int i1 = 0; i1 < i0 * 8; ++i1) {
                float f0 = this.V.nextFloat() * 3.1415927F * 2.0F;
                float f1 = this.V.nextFloat() * 0.5F + 0.5F;
                float f2 = MathHelper.a(f0) * (float) i0 * 0.5F * f1;
                float f3 = MathHelper.b(f0) * (float) i0 * 0.5F * f1;
                World world = this.o;
                EnumParticleTypes enumparticletypes = this.n();
                double d0 = this.s + (double) f2;
                double d1 = this.u + (double) f3;

                world.a(enumparticletypes, d0, this.aQ().b, d1, 0.0D, 0.0D, 0.0D, new int[0]);
            }

            if (this.cj()) {
                this.a(this.ci(), this.bA(), ((this.V.nextFloat() - this.V.nextFloat()) * 0.2F + 1.0F) / 0.8F);
            }

            this.a = -0.5F;
        }
        else if (!this.C && this.bi) {
            this.a = 1.0F;
        }

        this.bi = this.C;
        this.cf();
    }

    protected void cf() {
        this.a *= 0.6F;
    }

    protected int ce() {
        return this.V.nextInt(20) + 10;
    }

    protected EntitySlime cd() {
        return new EntitySlime(this.o);
    }

    public void i(int i0) {
        if (i0 == 16) {
            int i1 = this.ck();

            this.a(0.51000005F * (float) i1, 0.51000005F * (float) i1);
            this.y = this.aI;
            this.aG = this.aI;
            if (this.V() && this.V.nextInt(20) == 0) {
                this.X();
            }
        }

        super.i(i0);
    }

    public void J() {
        int i0 = this.ck();

        if (!this.o.D && i0 > 1 && this.bm() <= 0.0F) {
            int i1 = 2 + this.V.nextInt(3);

            List<Slime> slimes = new ArrayList<Slime>();
            for (int i2 = 0; i2 < i1; ++i2) {
                float f0 = ((float) (i2 % 2) - 0.5F) * (float) i0 / 4.0F;
                float f1 = ((float) (i2 / 2) - 0.5F) * (float) i0 / 4.0F;
                EntitySlime entityslime = this.cd();

                if (this.k_()) {
                    entityslime.a(this.aL());
                }

                if (this.bY()) {
                    entityslime.bW();
                }

                entityslime.a(i0 / 2);
                entityslime.b(this.s + (double) f0, this.t + 0.5D, this.u + (double) f1, this.V.nextFloat() * 360.0F, 0.0F);
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

        super.J();
    }

    public void i(Entity entity) {
        super.i(entity);
        if (entity instanceof EntityIronGolem && this.cg()) {
            this.e((EntityLivingBase) entity);
        }

    }

    public void d(EntityPlayer entityplayer) {
        if (this.cg()) {
            this.e(entityplayer);
        }

    }

    protected void e(EntityLivingBase entitylivingbase) {
        int i0 = this.ck();

        if (this.t(entitylivingbase) && this.h(entitylivingbase) < 0.6D * (double) i0 * 0.6D * (double) i0 && entitylivingbase.a(DamageSource.a((EntityLivingBase) this), (float) this.ch())) {
            this.a("mob.attack", 1.0F, (this.V.nextFloat() - this.V.nextFloat()) * 0.2F + 1.0F);
            this.a(this, entitylivingbase);
        }

    }

    public float aR() {
        return 0.625F * this.K;
    }

    protected boolean cg() {
        return this.ck() > 1;
    }

    protected int ch() {
        return this.ck();
    }

    protected String bn() {
        return "mob.slime." + (this.ck() > 1 ? "big" : "small");
    }

    protected String bo() {
        return "mob.slime." + (this.ck() > 1 ? "big" : "small");
    }

    protected Item A() {
        return this.ck() == 1 ? Items.aM : null;
    }

    public boolean bQ() {
        Chunk chunk = this.o.f(new BlockPos(MathHelper.c(this.s), 0, MathHelper.c(this.u)));

        if (this.o.P().u() == WorldType.c && this.V.nextInt(4) != 1) {
            return false;
        }
        else {
            if (this.o.aa() != EnumDifficulty.PEACEFUL) {
                BiomeGenBase biomegenbase = this.o.b(new BlockPos(MathHelper.c(this.s), 0, MathHelper.c(this.u)));

                if (biomegenbase == BiomeGenBase.v && this.t > 50.0D && this.t < 70.0D && this.V.nextFloat() < 0.5F && this.V.nextFloat() < this.o.y() && this.o.l(new BlockPos(this)) <= this.V.nextInt(8)) {
                    return super.bQ();
                }

                if (this.V.nextInt(10) == 0 && chunk.a(987234911L).nextInt(10) == 0 && this.t < 40.0D) {
                    return super.bQ();
                }
            }

            return false;
        }
    }

    protected float bA() {
        return 0.4F * (float) this.ck();
    }

    public int bP() {
        return 0;
    }

    protected boolean cl() {
        return this.ck() > 0;
    }

    protected boolean cj() {
        return this.ck() > 2;
    }

    protected void bE() {
        this.w = 0.41999998688697815D;
        this.ai = true;
    }

    public IEntityLivingData a(DifficultyInstance difficultyinstance, IEntityLivingData ientitylivingdata) {
        int i0 = this.V.nextInt(3);

        if (i0 < 2 && this.V.nextFloat() < 0.5F * difficultyinstance.c()) {
            ++i0;
        }

        int i1 = 1 << i0;

        this.a(i1);
        return super.a(difficultyinstance, ientitylivingdata);
    }

    class AISlimeAttack extends EntityAIBase {

        private EntitySlime a = EntitySlime.this;
        private int b;

        public AISlimeAttack() {
            this.a(2);
        }

        public boolean a() {
            EntityLivingBase entitylivingbase1 = this.a.u();

            return entitylivingbase1 == null ? false : entitylivingbase1.ai();
        }

        public void c() {
            this.b = 300;
            super.c();
        }

        public boolean b() {
            EntityLivingBase entitylivingbase1 = this.a.u();

            return entitylivingbase1 == null ? false : (!entitylivingbase1.ai() ? false : --this.b > 0);
        }

        public void e() {
            this.a.a(this.a.u(), 10.0F, 10.0F);
            ((EntitySlime.SlimeMoveHelper) this.a.q()).a(this.a.y, this.a.cg());
        }
    }


    class AISlimeFaceRandom extends EntityAIBase {

        private EntitySlime a = EntitySlime.this;
        private float b;
        private int c;

        public AISlimeFaceRandom() {
            this.a(2);
        }

        public boolean a() {
            return this.a.u() == null && (this.a.C || this.a.V() || this.a.ab());
        }

        public void e() {
            if (--this.c <= 0) {
                this.c = 40 + this.a.bb().nextInt(60);
                this.b = (float) this.a.bb().nextInt(360);
            }

            ((EntitySlime.SlimeMoveHelper) this.a.q()).a(this.b, false);
        }
    }


    class AISlimeFloat extends EntityAIBase {

        private EntitySlime a = EntitySlime.this;

        public AISlimeFloat() {
            this.a(5);
            ((PathNavigateGround) EntitySlime.this.s()).d(true);
        }

        public boolean a() {
            return this.a.V() || this.a.ab();
        }

        public void e() {
            if (this.a.bb().nextFloat() < 0.8F) {
                this.a.r().a();
            }

            ((EntitySlime.SlimeMoveHelper) this.a.q()).a(1.2D);
        }
    }


    class AISlimeHop extends EntityAIBase {

        private EntitySlime a = EntitySlime.this;

        public AISlimeHop() {
            this.a(5);
        }

        public boolean a() {
            return true;
        }

        public void e() {
            ((EntitySlime.SlimeMoveHelper) this.a.q()).a(1.0D);
        }
    }


    class SlimeMoveHelper extends EntityMoveHelper {

        private float g;
        private int h;
        private EntitySlime i = EntitySlime.this;
        private boolean j;

        public SlimeMoveHelper() {
            super(EntitySlime.this);
        }

        public void a(float p_a_1_, boolean p_a_2_) {
            this.g = p_a_1_;
            this.j = p_a_2_;
        }

        public void a(double p_a_1_) {
            this.e = p_a_1_;
            this.f = true;
        }

        public void c() {
            this.a.y = this.a(this.a.y, this.g, 30.0F);
            this.a.aI = this.a.y;
            this.a.aG = this.a.y;
            if (!this.f) {
                this.a.m(0.0F);
            }
            else {
                this.f = false;
                if (this.a.C) {
                    this.a.j((float) (this.e * this.a.a(SharedMonsterAttributes.d).e()));
                    if (this.h-- <= 0) {
                        this.h = this.i.ce();
                        if (this.j) {
                            this.h /= 3;
                        }

                        this.i.r().a();
                        if (this.i.cl()) {
                            this.i.a(this.i.ci(), this.i.bA(), ((this.i.bb().nextFloat() - this.i.bb().nextFloat()) * 0.2F + 1.0F) * 0.8F);
                        }
                    }
                    else {
                        this.i.aX = this.i.aY = 0.0F;
                        this.a.j(0.0F);
                    }
                }
                else {
                    this.a.j((float) (this.e * this.a.a(SharedMonsterAttributes.d).e()));
                }

            }
        }
    }
}
