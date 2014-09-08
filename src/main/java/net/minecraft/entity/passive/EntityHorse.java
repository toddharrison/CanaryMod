package net.minecraft.entity.passive;

import net.canarymod.api.entity.living.animal.CanaryHorse;
import net.canarymod.api.entity.vehicle.Vehicle;
import net.canarymod.hook.CancelableHook;
import net.canarymod.hook.entity.VehicleEnterHook;
import net.canarymod.hook.entity.VehicleExitHook;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.command.IEntitySelector;
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
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

public class EntityHorse extends EntityAnimal implements IInvBasic {

    private static final IEntitySelector bu = new IEntitySelector() {

        public boolean a(Entity entity) {
            return entity instanceof EntityHorse && ((EntityHorse) entity).co();
        }
    };
    private static final IAttribute bv = (new RangedAttribute("horse.jumpStrength", 0.7D, 0.0D, 2.0D)).a("Jump Strength").a(true);
    private static final String[] bw = new String[]{null, "textures/entity/horse/armor/horse_armor_iron.png", "textures/entity/horse/armor/horse_armor_gold.png", "textures/entity/horse/armor/horse_armor_diamond.png"};
    private static final String[] bx = new String[]{"", "meo", "goo", "dio"};
    private static final int[] by = new int[]{0, 5, 7, 11};
    private static final String[] bz = new String[]{"textures/entity/horse/horse_white.png", "textures/entity/horse/horse_creamy.png", "textures/entity/horse/horse_chestnut.png", "textures/entity/horse/horse_brown.png", "textures/entity/horse/horse_black.png", "textures/entity/horse/horse_gray.png", "textures/entity/horse/horse_darkbrown.png"};
    private static final String[] bA = new String[]{"hwh", "hcr", "hch", "hbr", "hbl", "hgr", "hdb"};
    private static final String[] bB = new String[]{null, "textures/entity/horse/horse_markings_white.png", "textures/entity/horse/horse_markings_whitefield.png", "textures/entity/horse/horse_markings_whitedots.png", "textures/entity/horse/horse_markings_blackdots.png"};
    private static final String[] bC = new String[]{"", "wo_", "wmo", "wdo", "bdo"};
    private int bD;
    private int bE;
    private int bF;
    public int bp;
    public int bq;
    protected boolean br;
    public AnimalChest bG; // CanaryMod: private => public
    private boolean bH;
    protected int bs;
    protected float bt;
    private boolean bI;
    private float bJ;
    private float bK;
    private float bL;
    private float bM;
    private float bN;
    private float bO;
    private int bP;
    private String bQ;
    private String[] bR = new String[3];

    public EntityHorse(World world) {
        super(world);
        this.a(1.4F, 1.6F);
        this.ae = false;
        this.l(false);
        this.m().a(true);
        this.c.a(0, new EntityAISwimming(this));
        this.c.a(1, new EntityAIPanic(this, 1.2D));
        this.c.a(1, new EntityAIRunAroundLikeCrazy(this, 1.2D));
        this.c.a(2, new EntityAIMate(this, 1.0D));
        this.c.a(4, new EntityAIFollowParent(this, 1.0D));
        this.c.a(6, new EntityAIWander(this, 0.7D));
        this.c.a(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.c.a(8, new EntityAILookIdle(this));
        this.cN();
        this.entity = new CanaryHorse(this); // CanaryMod: wrap entity
    }

    protected void c() {
        super.c();
        this.af.a(16, Integer.valueOf(0));
        this.af.a(19, Byte.valueOf((byte) 0));
        this.af.a(20, Integer.valueOf(0));
        this.af.a(21, String.valueOf(""));
        this.af.a(22, Integer.valueOf(0));
    }

    public void s(int i0) {
        this.af.b(19, Byte.valueOf((byte) i0));
        this.cP();
    }

    public int bZ() {
        return this.af.a(19);
    }

    public void t(int i0) {
        this.af.b(20, Integer.valueOf(i0));
        this.cP();
    }

    public int ca() {
        return this.af.c(20);
    }

    public String b_() {
        if (this.bH()) {
            return this.bG();
        }
        else {
            int i0 = this.bZ();

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

    private boolean x(int i0) {
        return (this.af.c(16) & i0) != 0;
    }

    private void b(int i0, boolean flag0) {
        int i1 = this.af.c(16);

        if (flag0) {
            this.af.b(16, Integer.valueOf(i1 | i0));
        }
        else {
            this.af.b(16, Integer.valueOf(i1 & ~i0));
        }
    }

    public boolean cb() {
        return !this.f();
    }

    public boolean cc() {
        return this.x(2);
    }

    public boolean cg() {
        return this.cb();
    }

    public String ch() {
        return this.af.e(21);
    }

    public void b(String s0) {
        this.af.b(21, s0);
    }

    public float ci() {
        int i0 = this.d();

        return i0 >= 0 ? 1.0F : 0.5F + (float) (-24000 - i0) / -24000.0F * 0.5F;
    }

    public void a(boolean flag0) {
        if (flag0) {
            this.a(this.ci());
        }
        else {
            this.a(1.0F);
        }
    }

    public boolean cj() {
        return this.br;
    }

    public void i(boolean flag0) {
        this.b(2, flag0);
    }

    public void j(boolean flag0) {
        this.br = flag0;
    }

    public boolean bM() {
        return !this.cE() && super.bM();
    }

    protected void o(float f0) {
        if (f0 > 6.0F && this.cm()) {
            this.o(false);
        }
    }

    public boolean ck() {
        return this.x(8);
    }

    public int cl() {
        return this.af.c(22);
    }

    private int e(ItemStack itemstack) {
        if (itemstack == null) {
            return 0;
        }
        else {
            Item item = itemstack.b();

            return item == Items.bX ? 1 : (item == Items.bY ? 2 : (item == Items.bZ ? 3 : 0));
        }
    }

    public boolean cm() {
        return this.x(32);
    }

    public boolean cn() {
        return this.x(64);
    }

    public boolean co() {
        return this.x(16);
    }

    public boolean cp() {
        return this.bH;
    }

    public void d(ItemStack itemstack) {
        this.af.b(22, Integer.valueOf(this.e(itemstack)));
        this.cP();
    }

    public void k(boolean flag0) {
        this.b(16, flag0);
    }

    public void l(boolean flag0) {
        this.b(8, flag0);
    }

    public void m(boolean flag0) {
        this.bH = flag0;
    }

    public void n(boolean flag0) {
        this.b(4, flag0);
    }

    public int cq() {
        return this.bs;
    }

    public void u(int i0) {
        this.bs = i0;
    }

    public int v(int i0) {
        int i1 = MathHelper.a(this.cq() + i0, 0, this.cw());

        this.u(i1);
        return i1;
    }

    public boolean a(DamageSource damagesource, float f0) {
        Entity entity = damagesource.j();

        return this.l != null && this.l.equals(entity) ? false : super.a(damagesource, f0);
    }

    public int aV() {
        return by[this.cl()];
    }

    public boolean S() {
        return this.l == null;
    }

    public boolean cr() {
        int i0 = MathHelper.c(this.s);
        int i1 = MathHelper.c(this.u);

        this.o.a(i0, i1);
        return true;
    }

    public void cs() {
        if (!this.o.E && this.ck()) {
            this.a(Item.a((Block) Blocks.ae), 1);
            this.l(false);
        }
    }

    private void cL() {
        this.cS();
        this.o.a((Entity) this, "eating", 1.0F, 1.0F + (this.Z.nextFloat() - this.Z.nextFloat()) * 0.2F);
    }

    protected void b(float f0) {
        if (f0 > 1.0F) {
            this.a("mob.horse.land", 0.4F, 1.0F);
        }

        int i0 = MathHelper.f(f0 * 0.5F - 3.0F);

        if (i0 > 0) {
            this.a(DamageSource.h, (float) i0);
            if (this.l != null) {
                this.l.a(DamageSource.h, (float) i0);
            }

            Block block = this.o.a(MathHelper.c(this.s), MathHelper.c(this.t - 0.2D - (double) this.A), MathHelper.c(this.u));

            if (block.o() != Material.a) {
                Block.SoundType block_soundtype = block.H;

                this.o.a((Entity) this, block_soundtype.e(), block_soundtype.c() * 0.5F, block_soundtype.d() * 0.75F);
            }
        }
    }

    private int cM() {
        int i0 = this.bZ();

        return this.ck() && (i0 == 1 || i0 == 2) ? 17 : 2;
    }

    private void cN() {
        AnimalChest animalchest = this.bG;

        this.bG = new AnimalChest("HorseChest", this.cM());
        this.bG.a(this.b_());
        if (animalchest != null) {
            animalchest.b(this);
            int i0 = Math.min(animalchest.a(), this.bG.a());

            for (int i1 = 0; i1 < i0; ++i1) {
                ItemStack itemstack = animalchest.a(i1);

                if (itemstack != null) {
                    this.bG.a(i1, itemstack.m());
                }
            }

            animalchest = null;
        }

        this.bG.a(this);
        this.cO();
    }

    private void cO() {
        if (!this.o.E) {
            this.n(this.bG.a(0) != null);
            if (this.cB()) {
                this.d(this.bG.a(1));
            }
        }
    }

    public void a(InventoryBasic inventorybasic) {
        int i0 = this.cl();
        boolean flag0 = this.cu();

        this.cO();
        if (this.aa > 20) {
            if (i0 == 0 && i0 != this.cl()) {
                this.a("mob.horse.armor", 0.5F, 1.0F);
            }
            else if (i0 != this.cl()) {
                this.a("mob.horse.armor", 0.5F, 1.0F);
            }

            if (!flag0 && this.cu()) {
                this.a("mob.horse.leather", 0.5F, 1.0F);
            }
        }
    }

    public boolean by() {
        this.cr();
        return super.by();
    }

    protected EntityHorse a(Entity entity, double d0) {
        double d1 = Double.MAX_VALUE;
        Entity entity1 = null;
        List list = this.o.a(entity, entity.C.a(d0, d0, d0), bu);
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

    public double ct() {
        return this.a(bv).e();
    }

    protected String aU() {
        this.cS();
        int i0 = this.bZ();

        return i0 == 3 ? "mob.horse.zombie.death" : (i0 == 4 ? "mob.horse.skeleton.death" : (i0 != 1 && i0 != 2 ? "mob.horse.death" : "mob.horse.donkey.death"));
    }

    protected Item u() {
        boolean flag0 = this.Z.nextInt(4) == 0;
        int i0 = this.bZ();

        return i0 == 4 ? Items.aS : (i0 == 3 ? (flag0 ? Item.d(0) : Items.bh) : Items.aA);
    }

    protected String aT() {
        this.cS();
        if (this.Z.nextInt(3) == 0) {
            this.cU();
        }

        int i0 = this.bZ();

        return i0 == 3 ? "mob.horse.zombie.hit" : (i0 == 4 ? "mob.horse.skeleton.hit" : (i0 != 1 && i0 != 2 ? "mob.horse.hit" : "mob.horse.donkey.hit"));
    }

    public boolean cu() {
        return this.x(4);
    }

    protected String t() {
        this.cS();
        if (this.Z.nextInt(10) == 0 && !this.bh()) {
            this.cU();
        }

        int i0 = this.bZ();

        return i0 == 3 ? "mob.horse.zombie.idle" : (i0 == 4 ? "mob.horse.skeleton.idle" : (i0 != 1 && i0 != 2 ? "mob.horse.idle" : "mob.horse.donkey.idle"));
    }

    protected String cv() {
        this.cS();
        this.cU();
        int i0 = this.bZ();

        return i0 != 3 && i0 != 4 ? (i0 != 1 && i0 != 2 ? "mob.horse.angry" : "mob.horse.donkey.angry") : null;
    }

    protected void a(int i0, int i1, int i2, Block block) {
        Block.SoundType block_soundtype = block.H;

        if (this.o.a(i0, i1 + 1, i2) == Blocks.aC) {
            block_soundtype = Blocks.aC.H;
        }

        if (!block.o().d()) {
            int i3 = this.bZ();

            if (this.l != null && i3 != 1 && i3 != 2) {
                ++this.bP;
                if (this.bP > 5 && this.bP % 3 == 0) {
                    this.a("mob.horse.gallop", block_soundtype.c() * 0.15F, block_soundtype.d());
                    if (i3 == 0 && this.Z.nextInt(10) == 0) {
                        this.a("mob.horse.breathe", block_soundtype.c() * 0.6F, block_soundtype.d());
                    }
                }
                else if (this.bP <= 5) {
                    this.a("mob.horse.wood", block_soundtype.c() * 0.15F, block_soundtype.d());
                }
            }
            else if (block_soundtype == Block.f) {
                this.a("mob.horse.wood", block_soundtype.c() * 0.15F, block_soundtype.d());
            }
            else {
                this.a("mob.horse.soft", block_soundtype.c() * 0.15F, block_soundtype.d());
            }
        }
    }

    protected void aD() {
        super.aD();
        this.bc().b(bv);
        this.a(SharedMonsterAttributes.a).a(53.0D);
        this.a(SharedMonsterAttributes.d).a(0.22499999403953552D);
    }

    public int bB() {
        return 6;
    }

    public int cw() {
        return 100;
    }

    protected float bf() {
        return 0.8F;
    }

    public int q() {
        return 400;
    }

    private void cP() {
        this.bQ = null;
    }

    public void g(EntityPlayer entityplayer) {
        if (!this.o.E && (this.l == null || this.l == entityplayer) && this.cc()) {
            this.bG.a(this.b_());
            entityplayer.a(this, (IInventory) this.bG);
        }
    }

    public boolean a(EntityPlayer entityplayer) {
        ItemStack itemstack = entityplayer.bm.h();

        if (itemstack != null && itemstack.b() == Items.bx) {
            return super.a(entityplayer);
        }
        else if (!this.cc() && this.cE()) {
            return false;
        }
        else if (this.cc() && this.cb() && entityplayer.an()) {
            this.g(entityplayer);
            return true;
        }
        else if (this.cg() && this.l != null) {
            return super.a(entityplayer);
        }
        else {
            if (itemstack != null) {
                boolean flag0 = false;

                if (this.cB()) {
                    byte b0 = -1;

                    if (itemstack.b() == Items.bX) {
                        b0 = 1;
                    }
                    else if (itemstack.b() == Items.bY) {
                        b0 = 2;
                    }
                    else if (itemstack.b() == Items.bZ) {
                        b0 = 3;
                    }

                    if (b0 >= 0) {
                        if (!this.cc()) {
                            this.cJ();
                            return true;
                        }

                        this.g(entityplayer);
                        return true;
                    }
                }

                if (!flag0 && !this.cE()) {
                    float f0 = 0.0F;
                    short short1 = 0;
                    byte b1 = 0;

                    if (itemstack.b() == Items.O) {
                        f0 = 2.0F;
                        short1 = 60;
                        b1 = 3;
                    }
                    else if (itemstack.b() == Items.aT) {
                        f0 = 1.0F;
                        short1 = 30;
                        b1 = 3;
                    }
                    else if (itemstack.b() == Items.P) {
                        f0 = 7.0F;
                        short1 = 180;
                        b1 = 3;
                    }
                    else if (Block.a(itemstack.b()) == Blocks.cf) {
                        f0 = 20.0F;
                        short1 = 180;
                    }
                    else if (itemstack.b() == Items.e) {
                        f0 = 3.0F;
                        short1 = 60;
                        b1 = 3;
                    }
                    else if (itemstack.b() == Items.bK) {
                        f0 = 4.0F;
                        short1 = 60;
                        b1 = 5;
                        if (this.cc() && this.d() == 0) {
                            flag0 = true;
                            this.f(entityplayer);
                        }
                    }
                    else if (itemstack.b() == Items.ao) {
                        f0 = 10.0F;
                        short1 = 240;
                        b1 = 10;
                        if (this.cc() && this.d() == 0) {
                            flag0 = true;
                            this.f(entityplayer);
                        }
                    }

                    if (this.aS() < this.aY() && f0 > 0.0F) {
                        this.f(f0);
                        flag0 = true;
                    }

                    if (!this.cb() && short1 > 0) {
                        this.a(short1);
                        flag0 = true;
                    }

                    if (b1 > 0 && (flag0 || !this.cc()) && b1 < this.cw()) {
                        flag0 = true;
                        this.v(b1);
                    }

                    if (flag0) {
                        this.cL();
                    }
                }

                if (!this.cc() && !flag0) {
                    if (itemstack != null && itemstack.a(entityplayer, (EntityLivingBase) this)) {
                        return true;
                    }

                    this.cJ();
                    return true;
                }

                if (!flag0 && this.cC() && !this.ck() && itemstack.b() == Item.a((Block) Blocks.ae)) {
                    this.l(true);
                    this.a("mob.chickenplop", 1.0F, (this.Z.nextFloat() - this.Z.nextFloat()) * 0.2F + 1.0F);
                    flag0 = true;
                    this.cN();
                }

                if (!flag0 && this.cg() && !this.cu() && itemstack.b() == Items.av) {
                    this.g(entityplayer);
                    return true;
                }

                if (flag0) {
                    if (!entityplayer.bE.d && --itemstack.b == 0) {
                        entityplayer.bm.a(entityplayer.bm.c, (ItemStack) null);
                    }

                    return true;
                }
            }

            if (this.cg() && this.l == null) {
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
        this.o(false);
        this.p(false);
        if (!this.o.E) {
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

    public boolean cB() {
        return this.bZ() == 0;
    }

    public boolean cC() {
        int i0 = this.bZ();

        return i0 == 2 || i0 == 1;
    }

    protected boolean bh() {
        return this.l != null && this.cu() ? true : this.cm() || this.cn();
    }

    public boolean cE() {
        int i0 = this.bZ();

        return i0 == 3 || i0 == 4;
    }

    public boolean cF() {
        return this.cE() || this.bZ() == 2;
    }

    public boolean c(ItemStack itemstack) {
        return false;
    }

    private void cR() {
        this.bp = 1;
    }

    public void a(DamageSource damagesource) {
        super.a(damagesource);
        if (!this.o.E) {
            this.cK();
        }
    }

    public void e() {
        if (this.Z.nextInt(200) == 0) {
            this.cR();
        }

        super.e();
        if (!this.o.E) {
            if (this.Z.nextInt(900) == 0 && this.aA == 0) {
                this.f(1.0F);
            }

            if (!this.cm() && this.l == null && this.Z.nextInt(300) == 0 && this.o.a(MathHelper.c(this.s), MathHelper.c(this.t) - 1, MathHelper.c(this.u)) == Blocks.c) {
                this.o(true);
            }

            if (this.cm() && ++this.bD > 50) {
                this.bD = 0;
                this.o(false);
            }

            if (this.co() && !this.cb() && !this.cm()) {
                EntityHorse entityhorse = this.a(this, 16.0D);

                if (entityhorse != null && this.f(entityhorse) > 4.0D) {
                    PathEntity pathentity = this.o.a(this, entityhorse, 16.0F, true, false, false, true);

                    this.a(pathentity);
                }
            }
        }
    }

    public void h() {
        super.h();
        if (this.o.E && this.af.a()) {
            this.af.e();
            this.cP();
        }

        if (this.bE > 0 && ++this.bE > 30) {
            this.bE = 0;
            this.b(128, false);
        }

        if (!this.o.E && this.bF > 0 && ++this.bF > 20) {
            this.bF = 0;
            this.p(false);
        }

        if (this.bp > 0 && ++this.bp > 8) {
            this.bp = 0;
        }

        if (this.bq > 0) {
            ++this.bq;
            if (this.bq > 300) {
                this.bq = 0;
            }
        }

        this.bK = this.bJ;
        if (this.cm()) {
            this.bJ += (1.0F - this.bJ) * 0.4F + 0.05F;
            if (this.bJ > 1.0F) {
                this.bJ = 1.0F;
            }
        }
        else {
            this.bJ += (0.0F - this.bJ) * 0.4F - 0.05F;
            if (this.bJ < 0.0F) {
                this.bJ = 0.0F;
            }
        }

        this.bM = this.bL;
        if (this.cn()) {
            this.bK = this.bJ = 0.0F;
            this.bL += (1.0F - this.bL) * 0.4F + 0.05F;
            if (this.bL > 1.0F) {
                this.bL = 1.0F;
            }
        }
        else {
            this.bI = false;
            this.bL += (0.8F * this.bL * this.bL * this.bL - this.bL) * 0.6F - 0.05F;
            if (this.bL < 0.0F) {
                this.bL = 0.0F;
            }
        }

        this.bO = this.bN;
        if (this.x(128)) {
            this.bN += (1.0F - this.bN) * 0.7F + 0.05F;
            if (this.bN > 1.0F) {
                this.bN = 1.0F;
            }
        }
        else {
            this.bN += (0.0F - this.bN) * 0.7F - 0.05F;
            if (this.bN < 0.0F) {
                this.bN = 0.0F;
            }
        }
    }

    private void cS() {
        if (!this.o.E) {
            this.bE = 1;
            this.b(128, true);
        }
    }

    private boolean cT() {
        return this.l == null && this.m == null && this.cc() && this.cb() && !this.cF() && this.aS() >= this.aY();
    }

    public void e(boolean flag0) {
        this.b(32, flag0);
    }

    public void o(boolean flag0) {
        this.e(flag0);
    }

    public void p(boolean flag0) {
        if (flag0) {
            this.o(false);
        }

        this.b(64, flag0);
    }

    private void cU() {
        if (!this.o.E) {
            this.bF = 1;
            this.p(true);
        }
    }

    public void cJ() {
        this.cU();
        String s0 = this.cv();

        if (s0 != null) {
            this.a(s0, this.bf(), this.bg());
        }
    }

    public void cK() {
        this.a(this, this.bG);
        this.cs();
    }

    private void a(Entity entity, AnimalChest animalchest) {
        if (animalchest != null && !this.o.E) {
            for (int i0 = 0; i0 < animalchest.a(); ++i0) {
                ItemStack itemstack = animalchest.a(i0);

                if (itemstack != null) {
                    this.a(itemstack, 0.0F);
                }
            }
        }
    }

    public boolean h(EntityPlayer entityplayer) {
        this.b(entityplayer.aB().toString());
        this.i(true);
        return true;
    }

    public void e(float f0, float f1) {
        if (this.l != null && this.l instanceof EntityLivingBase && this.cu()) {
            this.A = this.y = this.l.y;
            this.z = this.l.z * 0.5F;
            this.b(this.y, this.z);
            this.aO = this.aM = this.y;
            f0 = ((EntityLivingBase) this.l).bd * 0.5F;
            f1 = ((EntityLivingBase) this.l).be;
            if (f1 <= 0.0F) {
                f1 *= 0.25F;
                this.bP = 0;
            }

            if (this.D && this.bt == 0.0F && this.cn() && !this.bI) {
                f0 = 0.0F;
                f1 = 0.0F;
            }

            if (this.bt > 0.0F && !this.cj() && this.D) {
                this.w = this.ct() * (double) this.bt;
                if (this.a(Potion.j)) {
                    this.w += (double) ((float) (this.b(Potion.j).c() + 1) * 0.1F);
                }

                this.j(true);
                this.al = true;
                if (f1 > 0.0F) {
                    float f2 = MathHelper.a(this.y * 3.1415927F / 180.0F);
                    float f3 = MathHelper.b(this.y * 3.1415927F / 180.0F);

                    this.v += (double) (-0.4F * f2 * this.bt);
                    this.x += (double) (0.4F * f3 * this.bt);
                    this.a("mob.horse.jump", 0.4F, 1.0F);
                }

                this.bt = 0.0F;
            }

            this.W = 1.0F;
            this.aQ = this.bl() * 0.1F;
            if (!this.o.E) {
                this.i((float) this.a(SharedMonsterAttributes.d).e());
                super.e(f0, f1);
            }

            if (this.D) {
                this.bt = 0.0F;
                this.j(false);
            }

            this.aE = this.aF;
            double d0 = this.s - this.p;
            double d1 = this.u - this.r;
            float f4 = MathHelper.a(d0 * d0 + d1 * d1) * 4.0F;

            if (f4 > 1.0F) {
                f4 = 1.0F;
            }

            this.aF += (f4 - this.aF) * 0.4F;
            this.aG += this.aF;
        }
        else {
            this.W = 0.5F;
            this.aQ = 0.02F;
            super.e(f0, f1);
        }
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("EatingHaystack", this.cm());
        nbttagcompound.a("ChestedHorse", this.ck());
        nbttagcompound.a("HasReproduced", this.cp());
        nbttagcompound.a("Bred", this.co());
        nbttagcompound.a("Type", this.bZ());
        nbttagcompound.a("Variant", this.ca());
        nbttagcompound.a("Temper", this.cq());
        nbttagcompound.a("Tame", this.cc());
        nbttagcompound.a("OwnerUUID", this.ch());
        if (this.ck()) {
            NBTTagList nbttaglist = new NBTTagList();

            for (int i0 = 2; i0 < this.bG.a(); ++i0) {
                ItemStack itemstack = this.bG.a(i0);

                if (itemstack != null) {
                    NBTTagCompound nbttagcompound1 = new NBTTagCompound();

                    nbttagcompound1.a("Slot", (byte) i0);
                    itemstack.b(nbttagcompound1);
                    nbttaglist.a((NBTBase) nbttagcompound1);
                }
            }

            nbttagcompound.a("Items", (NBTBase) nbttaglist);
        }

        if (this.bG.a(1) != null) {
            nbttagcompound.a("ArmorItem", (NBTBase) this.bG.a(1).b(new NBTTagCompound()));
        }

        if (this.bG.a(0) != null) {
            nbttagcompound.a("SaddleItem", (NBTBase) this.bG.a(0).b(new NBTTagCompound()));
        }
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.o(nbttagcompound.n("EatingHaystack"));
        this.k(nbttagcompound.n("Bred"));
        this.l(nbttagcompound.n("ChestedHorse"));
        this.m(nbttagcompound.n("HasReproduced"));
        this.s(nbttagcompound.f("Type"));
        this.t(nbttagcompound.f("Variant"));
        this.u(nbttagcompound.f("Temper"));
        this.i(nbttagcompound.n("Tame"));
        if (nbttagcompound.b("OwnerUUID", 8)) {
            this.b(nbttagcompound.j("OwnerUUID"));
        }

        IAttributeInstance iattributeinstance = this.bc().a("Speed");

        if (iattributeinstance != null) {
            this.a(SharedMonsterAttributes.d).a(iattributeinstance.b() * 0.25D);
        }

        if (this.ck()) {
            NBTTagList nbttaglist = nbttagcompound.c("Items", 10);

            this.cN();

            for (int i0 = 0; i0 < nbttaglist.c(); ++i0) {
                NBTTagCompound nbttagcompound1 = nbttaglist.b(i0);
                int i1 = nbttagcompound1.d("Slot") & 255;

                if (i1 >= 2 && i1 < this.bG.a()) {
                    this.bG.a(i1, ItemStack.a(nbttagcompound1));
                }
            }
        }

        ItemStack itemstack;

        if (nbttagcompound.b("ArmorItem", 10)) {
            itemstack = ItemStack.a(nbttagcompound.m("ArmorItem"));
            if (itemstack != null && a(itemstack.b())) {
                this.bG.a(1, itemstack);
            }
        }

        if (nbttagcompound.b("SaddleItem", 10)) {
            itemstack = ItemStack.a(nbttagcompound.m("SaddleItem"));
            if (itemstack != null && itemstack.b() == Items.av) {
                this.bG.a(0, itemstack);
            }
        }
        else if (nbttagcompound.n("Saddle")) {
            this.bG.a(0, new ItemStack(Items.av));
        }

        this.cO();
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

            if (this.cT() && entityhorse.cT()) {
                int i0 = this.bZ();
                int i1 = entityhorse.bZ();

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
        int i0 = this.bZ();
        int i1 = entityhorse.bZ();
        int i2 = 0;

        if (i0 == i1) {
            i2 = i0;
        }
        else if (i0 == 0 && i1 == 1 || i0 == 1 && i1 == 0) {
            i2 = 2;
        }

        if (i2 == 0) {
            int i3 = this.Z.nextInt(9);
            int i4;

            if (i3 < 4) {
                i4 = this.ca() & 255;
            }
            else if (i3 < 8) {
                i4 = entityhorse.ca() & 255;
            }
            else {
                i4 = this.Z.nextInt(7);
            }

            int i5 = this.Z.nextInt(5);

            if (i5 < 2) {
                i4 |= this.ca() & '\uff00';
            }
            else if (i5 < 4) {
                i4 |= entityhorse.ca() & '\uff00';
            }
            else {
                i4 |= this.Z.nextInt(5) << 8 & '\uff00';
            }

            entityhorse1.t(i4);
        }

        entityhorse1.s(i2);
        double d0 = this.a(SharedMonsterAttributes.a).b() + entityageable.a(SharedMonsterAttributes.a).b() + (double) this.cV();

        entityhorse1.a(SharedMonsterAttributes.a).a(d0 / 3.0D);
        double d1 = this.a(bv).b() + entityageable.a(bv).b() + this.cW();

        entityhorse1.a(bv).a(d1 / 3.0D);
        double d2 = this.a(SharedMonsterAttributes.d).b() + entityageable.a(SharedMonsterAttributes.d).b() + this.cX();

        entityhorse1.a(SharedMonsterAttributes.d).a(d2 / 3.0D);
        return entityhorse1;
    }

    public IEntityLivingData a(IEntityLivingData ientitylivingdata) {
        Object ientitylivingdata1 = super.a(ientitylivingdata);
        boolean flag0 = false;
        int i0 = 0;
        int i1;

        if (ientitylivingdata1 instanceof EntityHorse.GroupData) {
            i1 = ((EntityHorse.GroupData) ientitylivingdata1).a;
            i0 = ((EntityHorse.GroupData) ientitylivingdata1).b & 255 | this.Z.nextInt(5) << 8;
        }
        else {
            if (this.Z.nextInt(10) == 0) {
                i1 = 1;
            }
            else {
                int i2 = this.Z.nextInt(7);
                int i3 = this.Z.nextInt(5);

                i1 = 0;
                i0 = i2 | i3 << 8;
            }

            ientitylivingdata1 = new EntityHorse.GroupData(i1, i0);
        }

        this.s(i1);
        this.t(i0);
        if (this.Z.nextInt(5) == 0) {
            this.c(-24000);
        }

        if (i1 != 4 && i1 != 3) {
            this.a(SharedMonsterAttributes.a).a((double) this.cV());
            if (i1 == 0) {
                this.a(SharedMonsterAttributes.d).a(this.cX());
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
            this.a(bv).a(this.cW());
        }
        else {
            this.a(bv).a(0.5D);
        }

        this.g(this.aY());
        return (IEntityLivingData) ientitylivingdata1;
    }

    protected boolean bk() {
        return true;
    }

    public void w(int i0) {
        if (this.cu()) {
            if (i0 < 0) {
                i0 = 0;
            }
            else {
                this.bI = true;
                this.cU();
            }

            if (i0 >= 90) {
                this.bt = 1.0F;
            }
            else {
                this.bt = 0.4F + 0.4F * (float) i0 / 90.0F;
            }
        }
    }

    public void ac() {
        super.ac();
        if (this.bM > 0.0F) {
            float f0 = MathHelper.a(this.aM * 3.1415927F / 180.0F);
            float f1 = MathHelper.b(this.aM * 3.1415927F / 180.0F);
            float f2 = 0.7F * this.bM;
            float f3 = 0.15F * this.bM;

            this.l.b(this.s + (double) (f2 * f0), this.t + this.ae() + this.l.ad() + (double) f3, this.u - (double) (f2 * f1));
            if (this.l instanceof EntityLivingBase) {
                ((EntityLivingBase) this.l).aM = this.aM;
            }
        }
    }

    private float cV() {
        return 15.0F + (float) this.Z.nextInt(8) + (float) this.Z.nextInt(9);
    }

    private double cW() {
        return 0.4000000059604645D + this.Z.nextDouble() * 0.2D + this.Z.nextDouble() * 0.2D + this.Z.nextDouble() * 0.2D;
    }

    private double cX() {
        return (0.44999998807907104D + this.Z.nextDouble() * 0.3D + this.Z.nextDouble() * 0.3D + this.Z.nextDouble() * 0.3D) * 0.25D;
    }

    public static boolean a(Item item) {
        return item == Items.bX || item == Items.bY || item == Items.bZ;
    }

    public boolean h_() {
        return false;
    }

    public static class GroupData implements IEntityLivingData {

        public int a;
        public int b;

        public GroupData(int i0, int i1) {
            this.a = i0;
            this.b = i1;
        }
    }
}
