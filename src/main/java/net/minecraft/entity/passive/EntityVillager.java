package net.minecraft.entity.passive;

import net.canarymod.api.CanaryVillagerTrade;
import net.canarymod.api.entity.living.humanoid.CanaryVillager;
import net.canarymod.api.entity.living.humanoid.Villager;
import net.canarymod.hook.entity.VillagerTradeUnlockHook;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Tuple;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.village.Village;
import net.minecraft.world.World;

import java.util.*;

public class EntityVillager extends EntityAgeable implements IMerchant, INpc {

    private int bq;
    private boolean br;
    private boolean bs;
    public Village bp; // CanaryMod: package => public
    private EntityPlayer bt;
    private MerchantRecipeList bu;
    private int bv;
    private boolean bw;
    private int bx;
    private String by;
    private boolean bz;
    private float bA;
    private static final Map bB = new HashMap();
    private static final Map bC = new HashMap();

    public EntityVillager(World world) {
        this(world, 0);
    }

    public EntityVillager(World world, int i0) {
        super(world);
        this.s(i0);
        this.a(0.6F, 1.8F);
        this.m().b(true);
        this.m().a(true);
        this.c.a(0, new EntityAISwimming(this));
        this.c.a(1, new EntityAIAvoidEntity(this, EntityZombie.class, 8.0F, 0.6D, 0.6D));
        this.c.a(1, new EntityAITradePlayer(this));
        this.c.a(1, new EntityAILookAtTradePlayer(this));
        this.c.a(2, new EntityAIMoveIndoors(this));
        this.c.a(3, new EntityAIRestrictOpenDoor(this));
        this.c.a(4, new EntityAIOpenDoor(this, true));
        this.c.a(5, new EntityAIMoveTowardsRestriction(this, 0.6D));
        this.c.a(6, new EntityAIVillagerMate(this));
        this.c.a(7, new EntityAIFollowGolem(this));
        this.c.a(8, new EntityAIPlay(this, 0.32D));
        this.c.a(9, new EntityAIWatchClosest2(this, EntityPlayer.class, 3.0F, 1.0F));
        this.c.a(9, new EntityAIWatchClosest2(this, EntityVillager.class, 5.0F, 0.02F));
        this.c.a(9, new EntityAIWander(this, 0.6D));
        this.c.a(10, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
        this.entity = new CanaryVillager(this); // CanaryMod: Wrap Entity
    }

    protected void aD() {
        super.aD();
        this.a(SharedMonsterAttributes.d).a(0.5D);
    }

    public boolean bk() {
        return true;
    }

    protected void bp() {
        if (--this.bq <= 0) {
            this.p.A.a(MathHelper.c(this.t), MathHelper.c(this.u), MathHelper.c(this.v));
            this.bq = 70 + this.aa.nextInt(50);
            this.bp = this.p.A.a(MathHelper.c(this.t), MathHelper.c(this.u), MathHelper.c(this.v), 32);
            if (this.bp == null) {
                this.bV();
            }
            else {
                ChunkCoordinates chunkcoordinates = this.bp.a();

                this.a(chunkcoordinates.a, chunkcoordinates.b, chunkcoordinates.c, (int) ((float) this.bp.b() * 0.6F));
                if (this.bz) {
                    this.bz = false;
                    this.bp.b(5);
                }
            }
        }

        if (!this.ca() && this.bv > 0) {
            --this.bv;
            if (this.bv <= 0) {
                if (this.bw) {
                    if (this.bu.size() > 1) {
                        Iterator iterator = this.bu.iterator();

                        while (iterator.hasNext()) {
                            MerchantRecipe merchantrecipe = (MerchantRecipe) iterator.next();

                            if (merchantrecipe.g()) {
                                merchantrecipe.a(this.aa.nextInt(6) + this.aa.nextInt(6) + 2);
                            }
                        }
                    }

                    this.t(1);
                    this.bw = false;
                    if (this.bp != null && this.by != null) {
                        this.p.a(this, (byte) 14);
                        this.bp.a(this.by, 1);
                    }
                }

                this.c(new PotionEffect(Potion.l.H, 200, 0));
            }
        }

        super.bp();
    }

    public boolean a(EntityPlayer entityplayer) {
        ItemStack itemstack = entityplayer.bn.h();
        boolean flag0 = itemstack != null && itemstack.b() == Items.bx;

        if (!flag0 && this.Z() && !this.ca() && !this.f()) {
            if (!this.p.E) {
                this.a_(entityplayer);
                entityplayer.a((IMerchant) this, this.bE());
            }

            return true;
        }
        else {
            return super.a(entityplayer);
        }
    }

    protected void c() {
        super.c();
        this.ag.a(16, Integer.valueOf(0));
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("Profession", this.bX());
        nbttagcompound.a("Riches", this.bx);
        if (this.bu != null) {
            nbttagcompound.a("Offers", (NBTBase) this.bu.a());
        }
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.s(nbttagcompound.f("Profession"));
        this.bx = nbttagcompound.f("Riches");
        if (nbttagcompound.b("Offers", 10)) {
            NBTTagCompound nbttagcompound1 = nbttagcompound.m("Offers");

            this.bu = new MerchantRecipeList(nbttagcompound1);
        }
    }

    protected boolean v() {
        return false;
    }

    protected String t() {
        return this.ca() ? "mob.villager.haggle" : "mob.villager.idle";
    }

    protected String aT() {
        return "mob.villager.hit";
    }

    protected String aU() {
        return "mob.villager.death";
    }

    public void s(int i0) {
        this.ag.b(16, Integer.valueOf(i0));
    }

    public int bX() {
        return this.ag.c(16);
    }

    public boolean bY() {
        return this.br;
    }

    public void i(boolean flag0) {
        this.br = flag0;
    }

    public void j(boolean flag0) {
        this.bs = flag0;
    }

    public boolean bZ() {
        return this.bs;
    }

    public void b(EntityLivingBase entitylivingbase) {
        super.b(entitylivingbase);
        if (this.bp != null && entitylivingbase != null) {
            this.bp.a(entitylivingbase);
            if (entitylivingbase instanceof EntityPlayer) {
                byte b0 = -1;

                if (this.f()) {
                    b0 = -3;
                }

                this.bp.a(entitylivingbase.b_(), b0);
                if (this.Z()) {
                    this.p.a(this, (byte) 13);
                }
            }
        }
    }

    public void a(DamageSource damagesource) {
        if (this.bp != null) {
            Entity entity = damagesource.j();

            if (entity != null) {
                if (entity instanceof EntityPlayer) {
                    this.bp.a(entity.b_(), -2);
                }
                else if (entity instanceof IMob) {
                    this.bp.h();
                }
            }
            else if (entity == null) {
                EntityPlayer entityplayer = this.p.a(this, 16.0D);

                if (entityplayer != null) {
                    this.bp.h();
                }
            }
        }

        super.a(damagesource);
    }

    public void a_(EntityPlayer entityplayer) {
        this.bt = entityplayer;
    }

    public EntityPlayer b() {
        return this.bt;
    }

    public boolean ca() {
        return this.bt != null;
    }

    public void a(MerchantRecipe merchantrecipe) {
        merchantrecipe.f();
        this.a_ = -this.q();
        this.a("mob.villager.yes", this.bf(), this.bg());
        if (merchantrecipe.a((MerchantRecipe) this.bu.get(this.bu.size() - 1))) {
            this.bv = 40;
            this.bw = true;
            if (this.bt != null) {
                this.by = this.bt.b_();
            }
            else {
                this.by = null;
            }
        }

        if (merchantrecipe.a().b() == Items.bC) {
            this.bx += merchantrecipe.a().b;
        }
    }

    public void a_(ItemStack itemstack) {
        if (!this.p.E && this.a_ > -this.q() + 20) {
            this.a_ = -this.q();
            if (itemstack != null) {
                this.a("mob.villager.yes", this.bf(), this.bg());
            }
            else {
                this.a("mob.villager.no", this.bf(), this.bg());
            }
        }
    }

    public MerchantRecipeList b(EntityPlayer entityplayer) {
        if (this.bu == null) {
            this.t(1);
        }

        return this.bu;
    }

    private float p(float f0) {
        float f1 = f0 + this.bA;

        return f1 > 0.9F ? 0.9F - (f1 - 0.9F) : f1;
    }

    private void t(int i0) {
        if (this.bu != null) {
            this.bA = MathHelper.c((float) this.bu.size()) * 0.2F;
        }
        else {
            this.bA = 0.0F;
        }

        MerchantRecipeList merchantrecipelist;

        merchantrecipelist = new MerchantRecipeList();
        int i1;

        label50:
        switch (this.bX()) {
            case 0:
                a(merchantrecipelist, Items.O, this.aa, this.p(0.9F));
                a(merchantrecipelist, Item.a(Blocks.L), this.aa, this.p(0.5F));
                a(merchantrecipelist, Items.bf, this.aa, this.p(0.5F));
                a(merchantrecipelist, Items.aQ, this.aa, this.p(0.4F));
                b(merchantrecipelist, Items.P, this.aa, this.p(0.9F));
                b(merchantrecipelist, Items.ba, this.aa, this.p(0.3F));
                b(merchantrecipelist, Items.e, this.aa, this.p(0.3F));
                b(merchantrecipelist, Items.aX, this.aa, this.p(0.3F));
                b(merchantrecipelist, Items.aZ, this.aa, this.p(0.3F));
                b(merchantrecipelist, Items.d, this.aa, this.p(0.3F));
                b(merchantrecipelist, Items.bg, this.aa, this.p(0.3F));
                b(merchantrecipelist, Items.g, this.aa, this.p(0.5F));
                if (this.aa.nextFloat() < this.p(0.5F)) {
                    merchantrecipelist.add(new MerchantRecipe(new ItemStack(Blocks.n, 10), new ItemStack(Items.bC), new ItemStack(Items.ak, 4 + this.aa.nextInt(2), 0)));
                }
                break;

            case 1:
                a(merchantrecipelist, Items.aF, this.aa, this.p(0.8F));
                a(merchantrecipelist, Items.aG, this.aa, this.p(0.8F));
                a(merchantrecipelist, Items.bB, this.aa, this.p(0.3F));
                b(merchantrecipelist, Item.a(Blocks.X), this.aa, this.p(0.8F));
                b(merchantrecipelist, Item.a(Blocks.w), this.aa, this.p(0.2F));
                b(merchantrecipelist, Items.aL, this.aa, this.p(0.2F));
                b(merchantrecipelist, Items.aN, this.aa, this.p(0.2F));
                if (this.aa.nextFloat() < this.p(0.07F)) {
                    Enchantment enchantment = Enchantment.c[this.aa.nextInt(Enchantment.c.length)];
                    int i2 = MathHelper.a(this.aa, enchantment.d(), enchantment.b());
                    ItemStack itemstack = Items.bR.a(new EnchantmentData(enchantment, i2));

                    i1 = 2 + this.aa.nextInt(5 + i2 * 10) + 3 * i2;
                    merchantrecipelist.add(new MerchantRecipe(new ItemStack(Items.aG), new ItemStack(Items.bC, i1), itemstack));
                }
                break;

            case 2:
                b(merchantrecipelist, Items.bv, this.aa, this.p(0.3F));
                b(merchantrecipelist, Items.by, this.aa, this.p(0.2F));
                b(merchantrecipelist, Items.ax, this.aa, this.p(0.4F));
                b(merchantrecipelist, Item.a(Blocks.aN), this.aa, this.p(0.3F));
                Item[] aitem = new Item[]{Items.l, Items.u, Items.Z, Items.ad, Items.c, Items.x, Items.b, Items.w};
                Item[] aitem1 = aitem;
                int i3 = aitem.length;

                i1 = 0;

                while (true) {
                    if (i1 >= i3) {
                        break label50;
                    }

                    Item item = aitem1[i1];

                    if (this.aa.nextFloat() < this.p(0.05F)) {
                        merchantrecipelist.add(new MerchantRecipe(new ItemStack(item, 1, 0), new ItemStack(Items.bC, 2 + this.aa.nextInt(3), 0), EnchantmentHelper.a(this.aa, new ItemStack(item, 1, 0), 5 + this.aa.nextInt(15))));
                    }

                    ++i1;
                }

            case 3:
                a(merchantrecipelist, Items.h, this.aa, this.p(0.7F));
                a(merchantrecipelist, Items.j, this.aa, this.p(0.5F));
                a(merchantrecipelist, Items.k, this.aa, this.p(0.5F));
                a(merchantrecipelist, Items.i, this.aa, this.p(0.5F));
                b(merchantrecipelist, Items.l, this.aa, this.p(0.5F));
                b(merchantrecipelist, Items.u, this.aa, this.p(0.5F));
                b(merchantrecipelist, Items.c, this.aa, this.p(0.3F));
                b(merchantrecipelist, Items.x, this.aa, this.p(0.3F));
                b(merchantrecipelist, Items.b, this.aa, this.p(0.5F));
                b(merchantrecipelist, Items.w, this.aa, this.p(0.5F));
                b(merchantrecipelist, Items.a, this.aa, this.p(0.2F));
                b(merchantrecipelist, Items.v, this.aa, this.p(0.2F));
                b(merchantrecipelist, Items.K, this.aa, this.p(0.2F));
                b(merchantrecipelist, Items.L, this.aa, this.p(0.2F));
                b(merchantrecipelist, Items.ab, this.aa, this.p(0.2F));
                b(merchantrecipelist, Items.af, this.aa, this.p(0.2F));
                b(merchantrecipelist, Items.Y, this.aa, this.p(0.2F));
                b(merchantrecipelist, Items.ac, this.aa, this.p(0.2F));
                b(merchantrecipelist, Items.Z, this.aa, this.p(0.2F));
                b(merchantrecipelist, Items.ad, this.aa, this.p(0.2F));
                b(merchantrecipelist, Items.aa, this.aa, this.p(0.2F));
                b(merchantrecipelist, Items.ae, this.aa, this.p(0.2F));
                b(merchantrecipelist, Items.X, this.aa, this.p(0.1F));
                b(merchantrecipelist, Items.U, this.aa, this.p(0.1F));
                b(merchantrecipelist, Items.V, this.aa, this.p(0.1F));
                b(merchantrecipelist, Items.W, this.aa, this.p(0.1F));
                break;

            case 4:
                a(merchantrecipelist, Items.h, this.aa, this.p(0.7F));
                a(merchantrecipelist, Items.al, this.aa, this.p(0.5F));
                a(merchantrecipelist, Items.bd, this.aa, this.p(0.5F));
                b(merchantrecipelist, Items.av, this.aa, this.p(0.1F));
                b(merchantrecipelist, Items.R, this.aa, this.p(0.3F));
                b(merchantrecipelist, Items.T, this.aa, this.p(0.3F));
                b(merchantrecipelist, Items.Q, this.aa, this.p(0.3F));
                b(merchantrecipelist, Items.S, this.aa, this.p(0.3F));
                b(merchantrecipelist, Items.am, this.aa, this.p(0.3F));
                b(merchantrecipelist, Items.be, this.aa, this.p(0.3F));
        }

        if (merchantrecipelist.isEmpty()) {
            a(merchantrecipelist, Items.k, this.aa, 1.0F);
        }

        Collections.shuffle(merchantrecipelist);
        if (this.bu == null) {
            this.bu = new MerchantRecipeList();
        }

        for (int i4 = 0; i4 < i0 && i4 < merchantrecipelist.size(); ++i4) {
            MerchantRecipe recipe = (MerchantRecipe) merchantrecipelist.get(i4);
            // CanaryMod: VillagerTradeUnlock
            VillagerTradeUnlockHook hook = (VillagerTradeUnlockHook) new VillagerTradeUnlockHook((Villager) getCanaryEntity(), new CanaryVillagerTrade(recipe)).call();
            if (!hook.isCanceled()) {
                this.bu.a(recipe);
            }
            //
        }
    }

    private static void a(MerchantRecipeList merchantrecipelist, Item item, Random random, float f0) {
        if (random.nextFloat() < f0) {
            merchantrecipelist.add(new MerchantRecipe(a(item, random), Items.bC));
        }
    }

    private static ItemStack a(Item item, Random random) {
        return new ItemStack(item, b(item, random), 0);
    }

    private static int b(Item item, Random random) {
        Tuple tuple = (Tuple) bB.get(item);

        return tuple == null ? 1 : (((Integer) tuple.a()).intValue() >= ((Integer) tuple.b()).intValue() ? ((Integer) tuple.a()).intValue() : ((Integer) tuple.a()).intValue() + random.nextInt(((Integer) tuple.b()).intValue() - ((Integer) tuple.a()).intValue()));
    }

    private static void b(MerchantRecipeList merchantrecipelist, Item item, Random random, float f0) {
        if (random.nextFloat() < f0) {
            int i0 = c(item, random);
            ItemStack itemstack;
            ItemStack itemstack1;

            if (i0 < 0) {
                itemstack = new ItemStack(Items.bC, 1, 0);
                itemstack1 = new ItemStack(item, -i0, 0);
            }
            else {
                itemstack = new ItemStack(Items.bC, i0, 0);
                itemstack1 = new ItemStack(item, 1, 0);
            }

            merchantrecipelist.add(new MerchantRecipe(itemstack, itemstack1));
        }
    }

    private static int c(Item item, Random random) {
        Tuple tuple = (Tuple) bC.get(item);

        return tuple == null ? 1 : (((Integer) tuple.a()).intValue() >= ((Integer) tuple.b()).intValue() ? ((Integer) tuple.a()).intValue() : ((Integer) tuple.a()).intValue() + random.nextInt(((Integer) tuple.b()).intValue() - ((Integer) tuple.a()).intValue()));
    }

    public IEntityLivingData a(IEntityLivingData entitylivingdata) {
        entitylivingdata = super.a(entitylivingdata);
        this.s(this.p.s.nextInt(5));
        return entitylivingdata;
    }

    public void cb() {
        this.bz = true;
    }

    public EntityVillager b(EntityAgeable entityageable) {
        EntityVillager entityvillager = new EntityVillager(this.p);

        entityvillager.a((IEntityLivingData) null);
        return entityvillager;
    }

    public boolean bK() {
        return false;
    }

    public EntityAgeable a(EntityAgeable entityageable) {
        return this.b(entityageable);
    }

    static {
        bB.put(Items.h, new Tuple(Integer.valueOf(16), Integer.valueOf(24)));
        bB.put(Items.j, new Tuple(Integer.valueOf(8), Integer.valueOf(10)));
        bB.put(Items.k, new Tuple(Integer.valueOf(8), Integer.valueOf(10)));
        bB.put(Items.i, new Tuple(Integer.valueOf(4), Integer.valueOf(6)));
        bB.put(Items.aF, new Tuple(Integer.valueOf(24), Integer.valueOf(36)));
        bB.put(Items.aG, new Tuple(Integer.valueOf(11), Integer.valueOf(13)));
        bB.put(Items.bB, new Tuple(Integer.valueOf(1), Integer.valueOf(1)));
        bB.put(Items.bi, new Tuple(Integer.valueOf(3), Integer.valueOf(4)));
        bB.put(Items.bv, new Tuple(Integer.valueOf(2), Integer.valueOf(3)));
        bB.put(Items.al, new Tuple(Integer.valueOf(14), Integer.valueOf(18)));
        bB.put(Items.bd, new Tuple(Integer.valueOf(14), Integer.valueOf(18)));
        bB.put(Items.bf, new Tuple(Integer.valueOf(14), Integer.valueOf(18)));
        bB.put(Items.aQ, new Tuple(Integer.valueOf(9), Integer.valueOf(13)));
        bB.put(Items.N, new Tuple(Integer.valueOf(34), Integer.valueOf(48)));
        bB.put(Items.bc, new Tuple(Integer.valueOf(30), Integer.valueOf(38)));
        bB.put(Items.bb, new Tuple(Integer.valueOf(30), Integer.valueOf(38)));
        bB.put(Items.O, new Tuple(Integer.valueOf(18), Integer.valueOf(22)));
        bB.put(Item.a(Blocks.L), new Tuple(Integer.valueOf(14), Integer.valueOf(22)));
        bB.put(Items.bh, new Tuple(Integer.valueOf(36), Integer.valueOf(64)));
        bC.put(Items.d, new Tuple(Integer.valueOf(3), Integer.valueOf(4)));
        bC.put(Items.aZ, new Tuple(Integer.valueOf(3), Integer.valueOf(4)));
        bC.put(Items.l, new Tuple(Integer.valueOf(7), Integer.valueOf(11)));
        bC.put(Items.u, new Tuple(Integer.valueOf(12), Integer.valueOf(14)));
        bC.put(Items.c, new Tuple(Integer.valueOf(6), Integer.valueOf(8)));
        bC.put(Items.x, new Tuple(Integer.valueOf(9), Integer.valueOf(12)));
        bC.put(Items.b, new Tuple(Integer.valueOf(7), Integer.valueOf(9)));
        bC.put(Items.w, new Tuple(Integer.valueOf(10), Integer.valueOf(12)));
        bC.put(Items.a, new Tuple(Integer.valueOf(4), Integer.valueOf(6)));
        bC.put(Items.v, new Tuple(Integer.valueOf(7), Integer.valueOf(8)));
        bC.put(Items.K, new Tuple(Integer.valueOf(4), Integer.valueOf(6)));
        bC.put(Items.L, new Tuple(Integer.valueOf(7), Integer.valueOf(8)));
        bC.put(Items.ab, new Tuple(Integer.valueOf(4), Integer.valueOf(6)));
        bC.put(Items.af, new Tuple(Integer.valueOf(7), Integer.valueOf(8)));
        bC.put(Items.Y, new Tuple(Integer.valueOf(4), Integer.valueOf(6)));
        bC.put(Items.ac, new Tuple(Integer.valueOf(7), Integer.valueOf(8)));
        bC.put(Items.Z, new Tuple(Integer.valueOf(10), Integer.valueOf(14)));
        bC.put(Items.ad, new Tuple(Integer.valueOf(16), Integer.valueOf(19)));
        bC.put(Items.aa, new Tuple(Integer.valueOf(8), Integer.valueOf(10)));
        bC.put(Items.ae, new Tuple(Integer.valueOf(11), Integer.valueOf(14)));
        bC.put(Items.X, new Tuple(Integer.valueOf(5), Integer.valueOf(7)));
        bC.put(Items.U, new Tuple(Integer.valueOf(5), Integer.valueOf(7)));
        bC.put(Items.V, new Tuple(Integer.valueOf(11), Integer.valueOf(15)));
        bC.put(Items.W, new Tuple(Integer.valueOf(9), Integer.valueOf(11)));
        bC.put(Items.P, new Tuple(Integer.valueOf(-4), Integer.valueOf(-2)));
        bC.put(Items.ba, new Tuple(Integer.valueOf(-8), Integer.valueOf(-4)));
        bC.put(Items.e, new Tuple(Integer.valueOf(-8), Integer.valueOf(-4)));
        bC.put(Items.aX, new Tuple(Integer.valueOf(-10), Integer.valueOf(-7)));
        bC.put(Item.a(Blocks.w), new Tuple(Integer.valueOf(-5), Integer.valueOf(-3)));
        bC.put(Item.a(Blocks.X), new Tuple(Integer.valueOf(3), Integer.valueOf(4)));
        bC.put(Items.R, new Tuple(Integer.valueOf(4), Integer.valueOf(5)));
        bC.put(Items.T, new Tuple(Integer.valueOf(2), Integer.valueOf(4)));
        bC.put(Items.Q, new Tuple(Integer.valueOf(2), Integer.valueOf(4)));
        bC.put(Items.S, new Tuple(Integer.valueOf(2), Integer.valueOf(4)));
        bC.put(Items.av, new Tuple(Integer.valueOf(6), Integer.valueOf(8)));
        bC.put(Items.by, new Tuple(Integer.valueOf(-4), Integer.valueOf(-1)));
        bC.put(Items.ax, new Tuple(Integer.valueOf(-4), Integer.valueOf(-1)));
        bC.put(Items.aL, new Tuple(Integer.valueOf(10), Integer.valueOf(12)));
        bC.put(Items.aN, new Tuple(Integer.valueOf(10), Integer.valueOf(12)));
        bC.put(Item.a(Blocks.aN), new Tuple(Integer.valueOf(-3), Integer.valueOf(-1)));
        bC.put(Items.am, new Tuple(Integer.valueOf(-7), Integer.valueOf(-5)));
        bC.put(Items.be, new Tuple(Integer.valueOf(-7), Integer.valueOf(-5)));
        bC.put(Items.bg, new Tuple(Integer.valueOf(-8), Integer.valueOf(-6)));
        bC.put(Items.bv, new Tuple(Integer.valueOf(7), Integer.valueOf(11)));
        bC.put(Items.g, new Tuple(Integer.valueOf(-12), Integer.valueOf(-8)));
    }
}
