package net.minecraft.entity.passive;

import com.google.common.base.Predicate;
import net.canarymod.api.entity.living.animal.CanaryHorse;
import net.canarymod.api.entity.vehicle.Vehicle;
import net.canarymod.hook.CancelableHook;
import net.canarymod.hook.entity.VehicleEnterHook;
import net.canarymod.hook.entity.VehicleExitHook;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.AnimalChest;
import net.minecraft.inventory.IInvBasic;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.Potion;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

public class EntityHorse extends EntityAnimal implements IInvBasic {

    private static final Predicate bq = new Predicate() {

        public boolean a(Entity p_a_1_) {
            return p_a_1_ instanceof EntityHorse && ((EntityHorse) p_a_1_).cy();
        }

        public boolean apply(Object p_apply_1_) {
            return this.a((Entity) p_apply_1_);
        }
    };
    private static final IAttribute br = (new RangedAttribute((IAttribute) null, "horse.jumpStrength", 0.7D, 0.0D, 2.0D)).a("Jump Strength").a(true);
    private static final String[] bs = new String[]{null, "textures/entity/horse/armor/horse_armor_iron.png", "textures/entity/horse/armor/horse_armor_gold.png", "textures/entity/horse/armor/horse_armor_diamond.png"};
    private static final String[] bt = new String[]{"", "meo", "goo", "dio"};
    private static final int[] bu = new int[]{0, 5, 7, 11};
    private static final String[] bv = new String[]{"textures/entity/horse/horse_white.png", "textures/entity/horse/horse_creamy.png", "textures/entity/horse/horse_chestnut.png", "textures/entity/horse/horse_brown.png", "textures/entity/horse/horse_black.png", "textures/entity/horse/horse_gray.png", "textures/entity/horse/horse_darkbrown.png"};
    private static final String[] bw = new String[]{"hwh", "hcr", "hch", "hbr", "hbl", "hgr", "hdb"};
    private static final String[] bx = new String[]{null, "textures/entity/horse/horse_markings_white.png", "textures/entity/horse/horse_markings_whitefield.png", "textures/entity/horse/horse_markings_whitedots.png", "textures/entity/horse/horse_markings_blackdots.png"};
    private static final String[] by = new String[]{"", "wo_", "wmo", "wdo", "bdo"};
    public int bk;
    public int bm;
    protected boolean bn;
    protected int bo;
    protected float bp;
    private int bz;
    private int bA;
    private int bB;
    public AnimalChest bC; // CanaryMod: private => public
    private boolean bD;
    private boolean bE;
    private float bF;
    private float bG;
    private float bH;
    private float bI;
    private float bJ;
    private float bK;
    private int bL;
    private String bM;
    private String[] bN = new String[3];
    private boolean bO = false;

    public EntityHorse(World world) {
        super(world);
        this.a(1.4F, 1.6F);
        this.ab = false;
        this.o(false);
        ((PathNavigateGround) this.s()).a(true);
        this.i.a(0, new EntityAISwimming(this));
        this.i.a(1, new EntityAIPanic(this, 1.2D));
        this.i.a(1, new EntityAIRunAroundLikeCrazy(this, 1.2D));
        this.i.a(2, new EntityAIMate(this, 1.0D));
        this.i.a(4, new EntityAIFollowParent(this, 1.0D));
        this.i.a(6, new EntityAIWander(this, 0.7D));
        this.i.a(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.i.a(8, new EntityAILookIdle(this));
        this.cY();
        this.entity = new CanaryHorse(this); // CanaryMod: wrap entity
    }

    public static boolean a(Item item) {
        return item == Items.ck || item == Items.cl || item == Items.cm;
    }

    protected void h() {
        super.h();
        this.ac.a(16, Integer.valueOf(0));
        this.ac.a(19, Byte.valueOf((byte) 0));
        this.ac.a(20, Integer.valueOf(0));
        this.ac.a(21, String.valueOf(""));
        this.ac.a(22, Integer.valueOf(0));
    }

    public void r(int i0) {
        this.ac.b(19, Byte.valueOf((byte) i0));
        this.da();
    }

    public int cj() {
        return this.ac.a(19);
    }

    public void s(int i0) {
        this.ac.b(20, Integer.valueOf(i0));
        this.da();
    }

    public int ck() {
        return this.ac.c(20);
    }

    public String d_() {
        if (this.k_()) {
            return this.aL();
        }
        else {
            int i0 = this.cj();

            switch (i0) {
                case 0:
                default:
                    return StatCollector.a("entity.horse.name");

                case 1:
                    return StatCollector.a("entity.donkey.name");

                case 2:
                    return StatCollector.a("entity.mule.name");

                case 3:
                    return StatCollector.a("entity.zombiehorse.name");

                case 4:
                    return StatCollector.a("entity.skeletonhorse.name");
            }
        }
    }

    private boolean w(int i0) {
        return (this.ac.c(16) & i0) != 0;
    }

    private void c(int i0, boolean flag0) {
        int i1 = this.ac.c(16);

        if (flag0) {
            this.ac.b(16, Integer.valueOf(i1 | i0));
        }
        else {
            this.ac.b(16, Integer.valueOf(i1 & ~i0));
        }

    }

    public boolean cl() {
        return !this.i_();
    }

    public boolean cm() {
        return this.w(2);
    }

    public boolean cn() {
        return this.cl();
    }

    public String cr() {
        return this.ac.e(21);
    }

    public void b(String s0) {
        this.ac.b(21, s0);
    }

    public float cs() {
        int i0 = this.l();

        return i0 >= 0 ? 1.0F : 0.5F + (float) (-24000 - i0) / -24000.0F * 0.5F;
    }

    public void a(boolean flag0) {
        if (flag0) {
            this.a(this.cs());
        }
        else {
            this.a(1.0F);
        }

    }

    public boolean ct() {
        return this.bn;
    }

    public void l(boolean flag0) {
        this.c(2, flag0);
    }

    public void m(boolean flag0) {
        this.bn = flag0;
    }

    public boolean ca() {
        return !this.cP() && super.ca();
    }

    protected void n(float f0) {
        if (f0 > 6.0F && this.cw()) {
            this.r(false);
        }

    }

    public boolean cu() {
        return this.w(8);
    }

    public int cv() {
        return this.ac.c(22);
    }

    private int f(ItemStack itemstack) {
        if (itemstack == null) {
            return 0;
        }
        else {
            Item item = itemstack.b();

            return item == Items.ck ? 1 : (item == Items.cl ? 2 : (item == Items.cm ? 3 : 0));
        }
    }

    public boolean cw() {
        return this.w(32);
    }

    public boolean cx() {
        return this.w(64);
    }

    public boolean cy() {
        return this.w(16);
    }

    public boolean cz() {
        return this.bD;
    }

    public void e(ItemStack itemstack) {
        this.ac.b(22, Integer.valueOf(this.f(itemstack)));
        this.da();
    }

    public void n(boolean flag0) {
        this.c(16, flag0);
    }

    public void o(boolean flag0) {
        this.c(8, flag0);
    }

    public void p(boolean flag0) {
        this.bD = flag0;
    }

    public void q(boolean flag0) {
        this.c(4, flag0);
    }

    public int cA() {
        return this.bo;
    }

    public void t(int i0) {
        this.bo = i0;
    }

    public int u(int i0) {
        int i1 = MathHelper.a(this.cA() + i0, 0, this.cG());

        this.t(i1);
        return i1;
    }

    public boolean a(DamageSource damagesource, float f0) {
        Entity entity = damagesource.j();

        return this.l != null && this.l.equals(entity) ? false : super.a(damagesource, f0);
    }

    public int bq() {
        return bu[this.cv()];
    }

    public boolean ae() {
        return this.l == null;
    }

    public boolean cB() {
        int i0 = MathHelper.c(this.s);
        int i1 = MathHelper.c(this.u);

        this.o.b(new BlockPos(i0, 0, i1));
        return true;
    }

    public void cC() {
        if (!this.o.D && this.cu()) {
            this.a(Item.a((Block) Blocks.ae), 1);
            this.o(false);
        }
    }

    private void cW() {
        this.dd();
        if (!this.R()) {
            this.o.a((Entity) this, "eating", 1.0F, 1.0F + (this.V.nextFloat() - this.V.nextFloat()) * 0.2F);
        }

    }

    public void e(float f0, float f1) {
        if (f0 > 1.0F) {
            this.a("mob.horse.land", 0.4F, 1.0F);
        }

        int i0 = MathHelper.f((f0 * 0.5F - 3.0F) * f1);

        if (i0 > 0) {
            this.a(DamageSource.i, (float) i0);
            if (this.l != null) {
                this.l.a(DamageSource.i, (float) i0);
            }

            Block block = this.o.p(new BlockPos(this.s, this.t - 0.2D - (double) this.A, this.u)).c();

            if (block.r() != Material.a && !this.R()) {
                Block.SoundType block_soundtype = block.H;

                this.o.a((Entity) this, block_soundtype.c(), block_soundtype.d() * 0.5F, block_soundtype.e() * 0.75F);
            }

        }
    }

    private int cX() {
        int i0 = this.cj();

        return this.cu() && (i0 == 1 || i0 == 2) ? 17 : 2;
    }

    private void cY() {
        AnimalChest animalchest = this.bC;

        this.bC = new AnimalChest("HorseChest", this.cX());
        this.bC.a(this.d_());
        if (animalchest != null) {
            animalchest.b(this);
            int i0 = Math.min(animalchest.n_(), this.bC.n_());

            for (int i1 = 0; i1 < i0; ++i1) {
                ItemStack itemstack = animalchest.a(i1);

                if (itemstack != null) {
                    this.bC.a(i1, itemstack.k());
                }
            }
        }

        this.bC.a(this);
        this.cZ();
    }

    private void cZ() {
        if (!this.o.D) {
            this.q(this.bC.a(0) != null);
            if (this.cM()) {
                this.e(this.bC.a(1));
            }
        }

    }

    public void a(InventoryBasic inventorybasic) {
        int i0 = this.cv();
        boolean flag0 = this.cE();

        this.cZ();
        if (this.W > 20) {
            if (i0 == 0 && i0 != this.cv()) {
                this.a("mob.horse.armor", 0.5F, 1.0F);
            }
            else if (i0 != this.cv()) {
                this.a("mob.horse.armor", 0.5F, 1.0F);
            }

            if (!flag0 && this.cE()) {
                this.a("mob.horse.leather", 0.5F, 1.0F);
            }
        }

    }

    public boolean bQ() {
        this.cB();
        return super.bQ();
    }

    protected EntityHorse a(Entity entity, double d0) {
        double d1 = Double.MAX_VALUE;
        Entity entity1 = null;
        List list = this.o.a(entity, entity.aQ().a(d0, d0, d0), bq);
        Iterator iterator = list.iterator();

        while (iterator.hasNext()) {
            Entity entity2 = (Entity) iterator.next();
            double d2 = entity2.e(entity.s, entity.t, entity.u);

            if (d2 < d1) {
                entity1 = entity2;
                d1 = d2;
            }
        }

        return (EntityHorse) entity1;
    }

    public double cD() {
        return this.a(br).e();
    }

    protected String bo() {
        this.dd();
        int i0 = this.cj();

        return i0 == 3 ? "mob.horse.zombie.death" : (i0 == 4 ? "mob.horse.skeleton.death" : (i0 != 1 && i0 != 2 ? "mob.horse.death" : "mob.horse.donkey.death"));
    }

    protected Item A() {
        boolean flag0 = this.V.nextInt(4) == 0;
        int i0 = this.cj();

        return i0 == 4 ? Items.aX : (i0 == 3 ? (flag0 ? null : Items.bt) : Items.aF);
    }

    protected String bn() {
        this.dd();
        if (this.V.nextInt(3) == 0) {
            this.df();
        }

        int i0 = this.cj();

        return i0 == 3 ? "mob.horse.zombie.hit" : (i0 == 4 ? "mob.horse.skeleton.hit" : (i0 != 1 && i0 != 2 ? "mob.horse.hit" : "mob.horse.donkey.hit"));
    }

    public boolean cE() {
        return this.w(4);
    }

    protected String z() {
        this.dd();
        if (this.V.nextInt(10) == 0 && !this.bC()) {
            this.df();
        }

        int i0 = this.cj();

        return i0 == 3 ? "mob.horse.zombie.idle" : (i0 == 4 ? "mob.horse.skeleton.idle" : (i0 != 1 && i0 != 2 ? "mob.horse.idle" : "mob.horse.donkey.idle"));
    }

    protected String cF() {
        this.dd();
        this.df();
        int i0 = this.cj();

        return i0 != 3 && i0 != 4 ? (i0 != 1 && i0 != 2 ? "mob.horse.angry" : "mob.horse.donkey.angry") : null;
    }

    protected void a(BlockPos blockpos, Block block) {
        Block.SoundType block_soundtype = block.H;

        if (this.o.p(blockpos.a()).c() == Blocks.aH) {
            block_soundtype = Blocks.aH.H;
        }

        if (!block.r().d()) {
            int i0 = this.cj();

            if (this.l != null && i0 != 1 && i0 != 2) {
                ++this.bL;
                if (this.bL > 5 && this.bL % 3 == 0) {
                    this.a("mob.horse.gallop", block_soundtype.d() * 0.15F, block_soundtype.e());
                    if (i0 == 0 && this.V.nextInt(10) == 0) {
                        this.a("mob.horse.breathe", block_soundtype.d() * 0.6F, block_soundtype.e());
                    }
                }
                else if (this.bL <= 5) {
                    this.a("mob.horse.wood", block_soundtype.d() * 0.15F, block_soundtype.e());
                }
            }
            else if (block_soundtype == Block.f) {
                this.a("mob.horse.wood", block_soundtype.d() * 0.15F, block_soundtype.e());
            }
            else {
                this.a("mob.horse.soft", block_soundtype.d() * 0.15F, block_soundtype.e());
            }
        }

    }

    protected void aW() {
        super.aW();
        this.bx().b(br);
        this.a(SharedMonsterAttributes.a).a(53.0D);
        this.a(SharedMonsterAttributes.d).a(0.22499999403953552D);
    }

    public int bU() {
        return 6;
    }

    public int cG() {
        return 100;
    }

    protected float bA() {
        return 0.8F;
    }

    public int w() {
        return 400;
    }

    private void da() {
        this.bM = null;
    }

    public void g(EntityPlayer entityplayer) {
        if (!this.o.D && (this.l == null || this.l == entityplayer) && this.cm()) {
            this.bC.a(this.d_());
            entityplayer.a(this, this.bC);
        }

    }

    public boolean a(EntityPlayer entityplayer) {
        ItemStack itemstack = entityplayer.bg.h();

        if (itemstack != null && itemstack.b() == Items.bJ) {
            return super.a(entityplayer);
        }
        else if (!this.cm() && this.cP()) {
            return false;
        }
        else if (this.cm() && this.cl() && entityplayer.aw()) {
            this.g(entityplayer);
            return true;
        }
        else if (this.cn() && this.l != null) {
            return super.a(entityplayer);
        }
        else {
            if (itemstack != null) {
                boolean flag0 = false;

                if (this.cM()) {
                    byte b0 = -1;

                    if (itemstack.b() == Items.ck) {
                        b0 = 1;
                    }
                    else if (itemstack.b() == Items.cl) {
                        b0 = 2;
                    }
                    else if (itemstack.b() == Items.cm) {
                        b0 = 3;
                    }

                    if (b0 >= 0) {
                        if (!this.cm()) {
                            this.cU();
                            return true;
                        }

                        this.g(entityplayer);
                        return true;
                    }
                }

                if (!flag0 && !this.cP()) {
                    float f0 = 0.0F;
                    short short1 = 0;
                    byte b1 = 0;

                    if (itemstack.b() == Items.O) {
                        f0 = 2.0F;
                        short1 = 20;
                        b1 = 3;
                    }
                    else if (itemstack.b() == Items.aY) {
                        f0 = 1.0F;
                        short1 = 30;
                        b1 = 3;
                    }
                    else if (Block.a(itemstack.b()) == Blocks.cx) {
                        f0 = 20.0F;
                        short1 = 180;
                    }
                    else if (itemstack.b() == Items.e) {
                        f0 = 3.0F;
                        short1 = 60;
                        b1 = 3;
                    }
                    else if (itemstack.b() == Items.bW) {
                        f0 = 4.0F;
                        short1 = 60;
                        b1 = 5;
                        if (this.cm() && this.l() == 0) {
                            flag0 = true;
                            this.c(entityplayer);
                        }
                    }
                    else if (itemstack.b() == Items.ao) {
                        f0 = 10.0F;
                        short1 = 240;
                        b1 = 10;
                        if (this.cm() && this.l() == 0) {
                            flag0 = true;
                            this.c(entityplayer);
                        }
                    }

                    if (this.bm() < this.bt() && f0 > 0.0F) {
                        this.g(f0);
                        flag0 = true;
                    }

                    if (!this.cl() && short1 > 0) {
                        this.a(short1);
                        flag0 = true;
                    }

                    if (b1 > 0 && (flag0 || !this.cm()) && b1 < this.cG()) {
                        flag0 = true;
                        this.u(b1);
                    }

                    if (flag0) {
                        this.cW();
                    }
                }

                if (!this.cm() && !flag0) {
                    if (itemstack != null && itemstack.a(entityplayer, (EntityLivingBase) this)) {
                        return true;
                    }

                    this.cU();
                    return true;
                }

                if (!flag0 && this.cN() && !this.cu() && itemstack.b() == Item.a((Block) Blocks.ae)) {
                    this.o(true);
                    this.a("mob.chickenplop", 1.0F, (this.V.nextFloat() - this.V.nextFloat()) * 0.2F + 1.0F);
                    flag0 = true;
                    this.cY();
                }

                if (!flag0 && this.cn() && !this.cE() && itemstack.b() == Items.aA) {
                    this.g(entityplayer);
                    return true;
                }

                if (flag0) {
                    if (!entityplayer.by.d && --itemstack.b == 0) {
                        entityplayer.bg.a(entityplayer.bg.c, (ItemStack) null);
                    }

                    return true;
                }
            }

            if (this.cn() && this.l == null) {
                if (itemstack != null && itemstack.a(entityplayer, (EntityLivingBase) this)) {
                    return true;
                }
                else {
                    this.i(entityplayer);
                    return true;
                }
            }
            else {
                return super.a(entityplayer);
            }
        }
    }

    private void i(EntityPlayer entityplayer) {
        entityplayer.y = this.y;
        entityplayer.z = this.z;
        this.r(false);
        this.s(false);
        if (!this.o.D) {
            // CanaryMod: VehicleEnter/VehicleExit
            CancelableHook hook = null;
            if (this.l == null) {
                hook = new VehicleEnterHook((Vehicle) this.entity, entityplayer.getCanaryHuman());
            }
            else if (this.l == entityplayer) {
                hook = new VehicleExitHook((Vehicle) this.entity, entityplayer.getCanaryHuman());
            }
            if (hook != null) {
                hook.call();
                if (!hook.isCanceled()) {
                    entityplayer.a((Entity) this);
                }
            }
            //
        }

    }

    public boolean cM() {
        return this.cj() == 0;
    }

    public boolean cN() {
        int i0 = this.cj();

        return i0 == 2 || i0 == 1;
    }

    protected boolean bC() {
        return this.l != null && this.cE() ? true : this.cw() || this.cx();
    }

    public boolean cP() {
        int i0 = this.cj();

        return i0 == 3 || i0 == 4;
    }

    public boolean cQ() {
        return this.cP() || this.cj() == 2;
    }

    public boolean d(ItemStack itemstack) {
        return false;
    }

    private void dc() {
        this.bk = 1;
    }

    public void a(DamageSource damagesource) {
        super.a(damagesource);
        if (!this.o.D) {
            this.cV();
        }

    }

    public void m() {
        if (this.V.nextInt(200) == 0) {
            this.dc();
        }

        super.m();
        if (!this.o.D) {
            if (this.V.nextInt(900) == 0 && this.av == 0) {
                this.g(1.0F);
            }

            if (!this.cw() && this.l == null && this.V.nextInt(300) == 0 && this.o.p(new BlockPos(MathHelper.c(this.s), MathHelper.c(this.t) - 1, MathHelper.c(this.u))).c() == Blocks.c) {
                this.r(true);
            }

            if (this.cw() && ++this.bz > 50) {
                this.bz = 0;
                this.r(false);
            }

            if (this.cy() && !this.cl() && !this.cw()) {
                EntityHorse entityhorse = this.a(this, 16.0D);

                if (entityhorse != null && this.h(entityhorse) > 4.0D) {
                    this.h.a((Entity) entityhorse);
                }
            }
        }

    }

    public void s_() {
        super.s_();
        if (this.o.D && this.ac.a()) {
            this.ac.e();
            this.da();
        }

        if (this.bA > 0 && ++this.bA > 30) {
            this.bA = 0;
            this.c(128, false);
        }

        if (!this.o.D && this.bB > 0 && ++this.bB > 20) {
            this.bB = 0;
            this.s(false);
        }

        if (this.bk > 0 && ++this.bk > 8) {
            this.bk = 0;
        }

        if (this.bm > 0) {
            ++this.bm;
            if (this.bm > 300) {
                this.bm = 0;
            }
        }

        this.bG = this.bF;
        if (this.cw()) {
            this.bF += (1.0F - this.bF) * 0.4F + 0.05F;
            if (this.bF > 1.0F) {
                this.bF = 1.0F;
            }
        }
        else {
            this.bF += (0.0F - this.bF) * 0.4F - 0.05F;
            if (this.bF < 0.0F) {
                this.bF = 0.0F;
            }
        }

        this.bI = this.bH;
        if (this.cx()) {
            this.bG = this.bF = 0.0F;
            this.bH += (1.0F - this.bH) * 0.4F + 0.05F;
            if (this.bH > 1.0F) {
                this.bH = 1.0F;
            }
        }
        else {
            this.bE = false;
            this.bH += (0.8F * this.bH * this.bH * this.bH - this.bH) * 0.6F - 0.05F;
            if (this.bH < 0.0F) {
                this.bH = 0.0F;
            }
        }

        this.bK = this.bJ;
        if (this.w(128)) {
            this.bJ += (1.0F - this.bJ) * 0.7F + 0.05F;
            if (this.bJ > 1.0F) {
                this.bJ = 1.0F;
            }
        }
        else {
            this.bJ += (0.0F - this.bJ) * 0.7F - 0.05F;
            if (this.bJ < 0.0F) {
                this.bJ = 0.0F;
            }
        }

    }

    private void dd() {
        if (!this.o.D) {
            this.bA = 1;
            this.c(128, true);
        }

    }

    private boolean de() {
        return this.l == null && this.m == null && this.cm() && this.cl() && !this.cQ() && this.bm() >= this.bt() && this.cp();
    }

    public void f(boolean flag0) {
        this.c(32, flag0);
    }

    public void r(boolean flag0) {
        this.f(flag0);
    }

    public void s(boolean flag0) {
        if (flag0) {
            this.r(false);
        }

        this.c(64, flag0);
    }

    private void df() {
        if (!this.o.D) {
            this.bB = 1;
            this.s(true);
        }

    }

    public void cU() {
        this.df();
        String s0 = this.cF();

        if (s0 != null) {
            this.a(s0, this.bA(), this.bB());
        }

    }

    public void cV() {
        this.a((Entity) this, this.bC);
        this.cC();
    }

    private void a(Entity entity, AnimalChest animalchest) {
        if (animalchest != null && !this.o.D) {
            for (int i0 = 0; i0 < animalchest.n_(); ++i0) {
                ItemStack itemstack = animalchest.a(i0);

                if (itemstack != null) {
                    this.a(itemstack, 0.0F);
                }
            }

        }
    }

    public boolean h(EntityPlayer entityplayer) {
        this.b(entityplayer.aJ().toString());
        this.l(true);
        return true;
    }

    public void g(float f0, float f1) {
        if (this.l != null && this.l instanceof EntityLivingBase && this.cE()) {
            this.A = this.y = this.l.y;
            this.z = this.l.z * 0.5F;
            this.b(this.y, this.z);
            this.aI = this.aG = this.y;
            f0 = ((EntityLivingBase) this.l).aX * 0.5F;
            f1 = ((EntityLivingBase) this.l).aY;
            if (f1 <= 0.0F) {
                f1 *= 0.25F;
                this.bL = 0;
            }

            if (this.C && this.bp == 0.0F && this.cx() && !this.bE) {
                f0 = 0.0F;
                f1 = 0.0F;
            }

            if (this.bp > 0.0F && !this.ct() && this.C) {
                this.w = this.cD() * (double) this.bp;
                if (this.a(Potion.j)) {
                    this.w += (double) ((float) (this.b(Potion.j).c() + 1) * 0.1F);
                }

                this.m(true);
                this.ai = true;
                if (f1 > 0.0F) {
                    float f2 = MathHelper.a(this.y * 3.1415927F / 180.0F);
                    float f3 = MathHelper.b(this.y * 3.1415927F / 180.0F);

                    this.v += (double) (-0.4F * f2 * this.bp);
                    this.x += (double) (0.4F * f3 * this.bp);
                    this.a("mob.horse.jump", 0.4F, 1.0F);
                }

                this.bp = 0.0F;
            }

            this.S = 1.0F;
            this.aK = this.bH() * 0.1F;
            if (!this.o.D) {
                this.j((float) this.a(SharedMonsterAttributes.d).e());
                super.g(f0, f1);
            }

            if (this.C) {
                this.bp = 0.0F;
                this.m(false);
            }

            this.ay = this.az;
            double d0 = this.s - this.p;
            double d1 = this.u - this.r;
            float f4 = MathHelper.a(d0 * d0 + d1 * d1) * 4.0F;

            if (f4 > 1.0F) {
                f4 = 1.0F;
            }

            this.az += (f4 - this.az) * 0.4F;
            this.aA += this.az;
        }
        else {
            this.S = 0.5F;
            this.aK = 0.02F;
            super.g(f0, f1);
        }
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("EatingHaystack", this.cw());
        nbttagcompound.a("ChestedHorse", this.cu());
        nbttagcompound.a("HasReproduced", this.cz());
        nbttagcompound.a("Bred", this.cy());
        nbttagcompound.a("Type", this.cj());
        nbttagcompound.a("Variant", this.ck());
        nbttagcompound.a("Temper", this.cA());
        nbttagcompound.a("Tame", this.cm());
        nbttagcompound.a("OwnerUUID", this.cr());
        if (this.cu()) {
            NBTTagList nbttaglist = new NBTTagList();

            for (int i0 = 2; i0 < this.bC.n_(); ++i0) {
                ItemStack itemstack = this.bC.a(i0);

                if (itemstack != null) {
                    NBTTagCompound nbttagcompound1 = new NBTTagCompound();

                    nbttagcompound1.a("Slot", (byte) i0);
                    itemstack.b(nbttagcompound1);
                    nbttaglist.a((NBTBase) nbttagcompound1);
                }
            }

            nbttagcompound.a("Items", (NBTBase) nbttaglist);
        }

        if (this.bC.a(1) != null) {
            nbttagcompound.a("ArmorItem", (NBTBase) this.bC.a(1).b(new NBTTagCompound()));
        }

        if (this.bC.a(0) != null) {
            nbttagcompound.a("SaddleItem", (NBTBase) this.bC.a(0).b(new NBTTagCompound()));
        }

    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.r(nbttagcompound.n("EatingHaystack"));
        this.n(nbttagcompound.n("Bred"));
        this.o(nbttagcompound.n("ChestedHorse"));
        this.p(nbttagcompound.n("HasReproduced"));
        this.r(nbttagcompound.f("Type"));
        this.s(nbttagcompound.f("Variant"));
        this.t(nbttagcompound.f("Temper"));
        this.l(nbttagcompound.n("Tame"));
        String s0 = "";

        if (nbttagcompound.b("OwnerUUID", 8)) {
            s0 = nbttagcompound.j("OwnerUUID");
        }
        else {
            String s1 = nbttagcompound.j("Owner");

            s0 = PreYggdrasilConverter.a(s1);
        }

        if (s0.length() > 0) {
            this.b(s0);
        }

        IAttributeInstance iattributeinstance = this.bx().a("Speed");

        if (iattributeinstance != null) {
            this.a(SharedMonsterAttributes.d).a(iattributeinstance.b() * 0.25D);
        }

        if (this.cu()) {
            NBTTagList nbttaglist = nbttagcompound.c("Items", 10);

            this.cY();

            for (int i0 = 0; i0 < nbttaglist.c(); ++i0) {
                NBTTagCompound nbttagcompound1 = nbttaglist.b(i0);
                int i1 = nbttagcompound1.d("Slot") & 255;

                if (i1 >= 2 && i1 < this.bC.n_()) {
                    this.bC.a(i1, ItemStack.a(nbttagcompound1));
                }
            }
        }

        ItemStack itemstack;

        if (nbttagcompound.b("ArmorItem", 10)) {
            itemstack = ItemStack.a(nbttagcompound.m("ArmorItem"));
            if (itemstack != null && a(itemstack.b())) {
                this.bC.a(1, itemstack);
            }
        }

        if (nbttagcompound.b("SaddleItem", 10)) {
            itemstack = ItemStack.a(nbttagcompound.m("SaddleItem"));
            if (itemstack != null && itemstack.b() == Items.aA) {
                this.bC.a(0, itemstack);
            }
        }
        else if (nbttagcompound.n("Saddle")) {
            this.bC.a(0, new ItemStack(Items.aA));
        }

        this.cZ();
    }

    public boolean a(EntityAnimal entityanimal) {
        if (entityanimal == this) {
            return false;
        }
        else if (entityanimal.getClass() != this.getClass()) {
            return false;
        }
        else {
            EntityHorse entityhorse = (EntityHorse) entityanimal;

            if (this.de() && entityhorse.de()) {
                int i0 = this.cj();
                int i1 = entityhorse.cj();

                return i0 == i1 || i0 == 0 && i1 == 1 || i0 == 1 && i1 == 0;
            }
            else {
                return false;
            }
        }
    }

    public EntityAgeable a(EntityAgeable entityageable) {
        EntityHorse entityhorse = (EntityHorse) entityageable;
        EntityHorse entityhorse1 = new EntityHorse(this.o);
        int i0 = this.cj();
        int i1 = entityhorse.cj();
        int i2 = 0;

        if (i0 == i1) {
            i2 = i0;
        }
        else if (i0 == 0 && i1 == 1 || i0 == 1 && i1 == 0) {
            i2 = 2;
        }

        if (i2 == 0) {
            int i3 = this.V.nextInt(9);
            int i4;

            if (i3 < 4) {
                i4 = this.ck() & 255;
            }
            else if (i3 < 8) {
                i4 = entityhorse.ck() & 255;
            }
            else {
                i4 = this.V.nextInt(7);
            }

            int i5 = this.V.nextInt(5);

            if (i5 < 2) {
                i4 |= this.ck() & '\uff00';
            }
            else if (i5 < 4) {
                i4 |= entityhorse.ck() & '\uff00';
            }
            else {
                i4 |= this.V.nextInt(5) << 8 & '\uff00';
            }

            entityhorse1.s(i4);
        }

        entityhorse1.r(i2);
        double d0 = this.a(SharedMonsterAttributes.a).b() + entityageable.a(SharedMonsterAttributes.a).b() + (double) this.dg();

        entityhorse1.a(SharedMonsterAttributes.a).a(d0 / 3.0D);
        double d1 = this.a(br).b() + entityageable.a(br).b() + this.dh();

        entityhorse1.a(br).a(d1 / 3.0D);
        double d2 = this.a(SharedMonsterAttributes.d).b() + entityageable.a(SharedMonsterAttributes.d).b() + this.di();

        entityhorse1.a(SharedMonsterAttributes.d).a(d2 / 3.0D);
        return entityhorse1;
    }

    public IEntityLivingData a(DifficultyInstance difficultyinstance, IEntityLivingData ientitylivingdata) {
        Object ientitylivingdata1 = super.a(difficultyinstance, ientitylivingdata);
        boolean flag0 = false;
        int i0 = 0;
        int i1;

        if (ientitylivingdata1 instanceof EntityHorse.GroupData) {
            i1 = ((EntityHorse.GroupData) ientitylivingdata1).a;
            i0 = ((EntityHorse.GroupData) ientitylivingdata1).b & 255 | this.V.nextInt(5) << 8;
        }
        else {
            if (this.V.nextInt(10) == 0) {
                i1 = 1;
            }
            else {
                int i2 = this.V.nextInt(7);
                int i3 = this.V.nextInt(5);

                i1 = 0;
                i0 = i2 | i3 << 8;
            }

            ientitylivingdata1 = new EntityHorse.GroupData(i1, i0);
        }

        this.r(i1);
        this.s(i0);
        if (this.V.nextInt(5) == 0) {
            this.b(-24000);
        }

        if (i1 != 4 && i1 != 3) {
            this.a(SharedMonsterAttributes.a).a((double) this.dg());
            if (i1 == 0) {
                this.a(SharedMonsterAttributes.d).a(this.di());
            }
            else {
                this.a(SharedMonsterAttributes.d).a(0.17499999701976776D);
            }
        }
        else {
            this.a(SharedMonsterAttributes.a).a(15.0D);
            this.a(SharedMonsterAttributes.d).a(0.20000000298023224D);
        }

        if (i1 != 2 && i1 != 1) {
            this.a(br).a(this.dh());
        }
        else {
            this.a(br).a(0.5D);
        }

        this.h(this.bt());
        return (IEntityLivingData) ientitylivingdata1;
    }

    public void v(int i0) {
        if (this.cE()) {
            if (i0 < 0) {
                i0 = 0;
            }
            else {
                this.bE = true;
                this.df();
            }

            if (i0 >= 90) {
                this.bp = 1.0F;
            }
            else {
                this.bp = 0.4F + 0.4F * (float) i0 / 90.0F;
            }
        }

    }

    public void al() {
        super.al();
        if (this.bI > 0.0F) {
            float f0 = MathHelper.a(this.aG * 3.1415927F / 180.0F);
            float f1 = MathHelper.b(this.aG * 3.1415927F / 180.0F);
            float f2 = 0.7F * this.bI;
            float f3 = 0.15F * this.bI;

            this.l.b(this.s + (double) (f2 * f0), this.t + this.an() + this.l.am() + (double) f3, this.u - (double) (f2 * f1));
            if (this.l instanceof EntityLivingBase) {
                ((EntityLivingBase) this.l).aG = this.aG;
            }
        }

    }

    private float dg() {
        return 15.0F + (float) this.V.nextInt(8) + (float) this.V.nextInt(9);
    }

    private double dh() {
        return 0.4000000059604645D + this.V.nextDouble() * 0.2D + this.V.nextDouble() * 0.2D + this.V.nextDouble() * 0.2D;
    }

    private double di() {
        return (0.44999998807907104D + this.V.nextDouble() * 0.3D + this.V.nextDouble() * 0.3D + this.V.nextDouble() * 0.3D) * 0.25D;
    }

    public boolean j_() {
        return false;
    }

    public float aR() {
        return this.K;
    }

    public boolean d(int i0, ItemStack itemstack) {
        if (i0 == 499 && this.cN()) {
            if (itemstack == null && this.cu()) {
                this.o(false);
                this.cY();
                return true;
            }

            if (itemstack != null && itemstack.b() == Item.a((Block) Blocks.ae) && !this.cu()) {
                this.o(true);
                this.cY();
                return true;
            }
        }

        int i1 = i0 - 400;

        if (i1 >= 0 && i1 < 2 && i1 < this.bC.n_()) {
            if (i1 == 0 && itemstack != null && itemstack.b() != Items.aA) {
                return false;
            }
            else if (i1 == 1 && (itemstack != null && !a(itemstack.b()) || !this.cM())) {
                return false;
            }
            else {
                this.bC.a(i1, itemstack);
                this.cZ();
                return true;
            }
        }
        else {
            int i2 = i0 - 500 + 2;

            if (i2 >= 2 && i2 < this.bC.n_()) {
                this.bC.a(i2, itemstack);
                return true;
            }
            else {
                return false;
            }
        }
    }

    public static class GroupData implements IEntityLivingData {

        public int a;
        public int b;

        public GroupData(int p_i1684_1_, int p_i1684_2_) {
            this.a = p_i1684_1_;
            this.b = p_i1684_2_;
        }
    }
}
