package net.minecraft.entity.passive;

import com.google.common.base.Predicate;
import net.canarymod.api.CanaryVillagerTrade;
import net.canarymod.api.entity.living.humanoid.CanaryVillager;
import net.canarymod.hook.entity.VillagerTradeUnlockHook;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.*;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.village.Village;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.Random;

public class EntityVillager extends EntityAgeable implements INpc, IMerchant {

    private int bl;
    private boolean bm;
    private boolean bn;
    Village bk;
    private EntityPlayer bo;
    private MerchantRecipeList bp;
    private int bq;
    private boolean br;
    private boolean bs;
    private int bt;
    private String bu;
    private int bv;
    private int bw;
    private boolean bx;
    private boolean by;
    private InventoryBasic bz;
    private static final EntityVillager.ITradeList[][][][] bA = new EntityVillager.ITradeList[][][][]{
            {
                    {
                            {new EntityVillager.EmeraldForItems(Items.O, new EntityVillager.PriceInfo(18, 22)), new EntityVillager.EmeraldForItems(Items.bS, new EntityVillager.PriceInfo(15, 19)), new EntityVillager.EmeraldForItems(Items.bR, new EntityVillager.PriceInfo(15, 19)), new EntityVillager.ListItemForEmeralds(Items.P, new EntityVillager.PriceInfo(-4, -2))}, {new EntityVillager.EmeraldForItems(Item.a(Blocks.aU), new EntityVillager.PriceInfo(8, 13)), new EntityVillager.ListItemForEmeralds(Items.ca, new EntityVillager.PriceInfo(-3, -2))}, {new EntityVillager.EmeraldForItems(Item.a(Blocks.bk), new EntityVillager.PriceInfo(7, 12)), new EntityVillager.ListItemForEmeralds(Items.e, new EntityVillager.PriceInfo(-5, -7))},
                            {new EntityVillager.ListItemForEmeralds(Items.bc, new EntityVillager.PriceInfo(-6, -10)), new EntityVillager.ListItemForEmeralds(Items.aZ, new EntityVillager.PriceInfo(1, 1))}},
                    {{new EntityVillager.EmeraldForItems(Items.F, new EntityVillager.PriceInfo(15, 20)), new EntityVillager.EmeraldForItems(Items.h, new EntityVillager.PriceInfo(16, 24)), new EntityVillager.ItemAndEmeraldToItem(Items.aU, new EntityVillager.PriceInfo(6, 6), Items.aV, new EntityVillager.PriceInfo(6, 6))}, {new EntityVillager.ListEnchantedItemForEmeralds(Items.aR, new EntityVillager.PriceInfo(7, 8))}},
                    {
                            {new EntityVillager.EmeraldForItems(Item.a(Blocks.L), new EntityVillager.PriceInfo(16, 22)), new EntityVillager.ListItemForEmeralds(Items.be, new EntityVillager.PriceInfo(3, 4))},
                            {
                                    new EntityVillager.ListItemForEmeralds(new ItemStack(Item.a(Blocks.L), 1, 0), new EntityVillager.PriceInfo(1, 2)), new EntityVillager.ListItemForEmeralds(new ItemStack(Item.a(Blocks.L), 1, 1), new EntityVillager.PriceInfo(1, 2)), new EntityVillager.ListItemForEmeralds(new ItemStack(Item.a(Blocks.L), 1, 2), new EntityVillager.PriceInfo(1, 2)), new EntityVillager.ListItemForEmeralds(new ItemStack(Item.a(Blocks.L), 1, 3), new EntityVillager.PriceInfo(1, 2)), new EntityVillager.ListItemForEmeralds(new ItemStack(Item.a(Blocks.L), 1, 4), new EntityVillager.PriceInfo(1, 2)), new EntityVillager.ListItemForEmeralds(new ItemStack(Item.a(Blocks.L), 1, 5), new EntityVillager.PriceInfo(1, 2)), new EntityVillager.ListItemForEmeralds(new ItemStack(Item.a(Blocks.L), 1, 6), new EntityVillager.PriceInfo(1, 2)),
                                    new EntityVillager.ListItemForEmeralds(new ItemStack(Item.a(Blocks.L), 1, 7), new EntityVillager.PriceInfo(1, 2)), new EntityVillager.ListItemForEmeralds(new ItemStack(Item.a(Blocks.L), 1, 8), new EntityVillager.PriceInfo(1, 2)), new EntityVillager.ListItemForEmeralds(new ItemStack(Item.a(Blocks.L), 1, 9), new EntityVillager.PriceInfo(1, 2)), new EntityVillager.ListItemForEmeralds(new ItemStack(Item.a(Blocks.L), 1, 10), new EntityVillager.PriceInfo(1, 2)), new EntityVillager.ListItemForEmeralds(new ItemStack(Item.a(Blocks.L), 1, 11), new EntityVillager.PriceInfo(1, 2)), new EntityVillager.ListItemForEmeralds(new ItemStack(Item.a(Blocks.L), 1, 12), new EntityVillager.PriceInfo(1, 2)), new EntityVillager.ListItemForEmeralds(new ItemStack(Item.a(Blocks.L), 1, 13), new EntityVillager.PriceInfo(1, 2)),
                                    new EntityVillager.ListItemForEmeralds(new ItemStack(Item.a(Blocks.L), 1, 14), new EntityVillager.PriceInfo(1, 2)), new EntityVillager.ListItemForEmeralds(new ItemStack(Item.a(Blocks.L), 1, 15), new EntityVillager.PriceInfo(1, 2))}},
                    {{new EntityVillager.EmeraldForItems(Items.F, new EntityVillager.PriceInfo(15, 20)), new EntityVillager.ListItemForEmeralds(Items.g, new EntityVillager.PriceInfo(-12, -8))}, {new EntityVillager.ListItemForEmeralds(Items.f, new EntityVillager.PriceInfo(2, 3)), new EntityVillager.ItemAndEmeraldToItem(Item.a(Blocks.n), new EntityVillager.PriceInfo(10, 10), Items.ak, new EntityVillager.PriceInfo(6, 10))}}},
            {{{new EntityVillager.EmeraldForItems(Items.aK, new EntityVillager.PriceInfo(24, 36)), new EntityVillager.ListEnchantedBookForEmeralds()}, {new EntityVillager.EmeraldForItems(Items.aL, new EntityVillager.PriceInfo(8, 10)), new EntityVillager.ListItemForEmeralds(Items.aQ, new EntityVillager.PriceInfo(10, 12)), new EntityVillager.ListItemForEmeralds(Item.a(Blocks.X), new EntityVillager.PriceInfo(3, 4))}, {new EntityVillager.EmeraldForItems(Items.bN, new EntityVillager.PriceInfo(2, 2)), new EntityVillager.ListItemForEmeralds(Items.aS, new EntityVillager.PriceInfo(10, 12)), new EntityVillager.ListItemForEmeralds(Item.a(Blocks.w), new EntityVillager.PriceInfo(-5, -3))}, {new EntityVillager.ListEnchantedBookForEmeralds()}, {new EntityVillager.ListEnchantedBookForEmeralds()}, {new EntityVillager.ListItemForEmeralds(Items.co, new EntityVillager.PriceInfo(20, 22))}}},
            {{{new EntityVillager.EmeraldForItems(Items.bt, new EntityVillager.PriceInfo(36, 40)), new EntityVillager.EmeraldForItems(Items.k, new EntityVillager.PriceInfo(8, 10))}, {new EntityVillager.ListItemForEmeralds(Items.aC, new EntityVillager.PriceInfo(-4, -1)), new EntityVillager.ListItemForEmeralds(new ItemStack(Items.aW, 1, EnumDyeColor.BLUE.b()), new EntityVillager.PriceInfo(-2, -1))}, {new EntityVillager.ListItemForEmeralds(Items.bH, new EntityVillager.PriceInfo(7, 11)), new EntityVillager.ListItemForEmeralds(Item.a(Blocks.aX), new EntityVillager.PriceInfo(-3, -1))}, {new EntityVillager.ListItemForEmeralds(Items.bK, new EntityVillager.PriceInfo(3, 11))}}},
            {
                    {{new EntityVillager.EmeraldForItems(Items.h, new EntityVillager.PriceInfo(16, 24)), new EntityVillager.ListItemForEmeralds(Items.Y, new EntityVillager.PriceInfo(4, 6))}, {new EntityVillager.EmeraldForItems(Items.j, new EntityVillager.PriceInfo(7, 9)), new EntityVillager.ListItemForEmeralds(Items.Z, new EntityVillager.PriceInfo(10, 14))}, {new EntityVillager.EmeraldForItems(Items.i, new EntityVillager.PriceInfo(3, 4)), new EntityVillager.ListEnchantedItemForEmeralds(Items.ad, new EntityVillager.PriceInfo(16, 19))}, {new EntityVillager.ListItemForEmeralds(Items.X, new EntityVillager.PriceInfo(5, 7)), new EntityVillager.ListItemForEmeralds(Items.W, new EntityVillager.PriceInfo(9, 11)), new EntityVillager.ListItemForEmeralds(Items.U, new EntityVillager.PriceInfo(5, 7)), new EntityVillager.ListItemForEmeralds(Items.V, new EntityVillager.PriceInfo(11, 15))}},
                    {{new EntityVillager.EmeraldForItems(Items.h, new EntityVillager.PriceInfo(16, 24)), new EntityVillager.ListItemForEmeralds(Items.c, new EntityVillager.PriceInfo(6, 8))}, {new EntityVillager.EmeraldForItems(Items.j, new EntityVillager.PriceInfo(7, 9)), new EntityVillager.ListEnchantedItemForEmeralds(Items.l, new EntityVillager.PriceInfo(9, 10))}, {new EntityVillager.EmeraldForItems(Items.i, new EntityVillager.PriceInfo(3, 4)), new EntityVillager.ListEnchantedItemForEmeralds(Items.u, new EntityVillager.PriceInfo(12, 15)), new EntityVillager.ListEnchantedItemForEmeralds(Items.x, new EntityVillager.PriceInfo(9, 12))}},
                    {{new EntityVillager.EmeraldForItems(Items.h, new EntityVillager.PriceInfo(16, 24)), new EntityVillager.ListEnchantedItemForEmeralds(Items.a, new EntityVillager.PriceInfo(5, 7))}, {new EntityVillager.EmeraldForItems(Items.j, new EntityVillager.PriceInfo(7, 9)), new EntityVillager.ListEnchantedItemForEmeralds(Items.b, new EntityVillager.PriceInfo(9, 11))}, {new EntityVillager.EmeraldForItems(Items.i, new EntityVillager.PriceInfo(3, 4)), new EntityVillager.ListEnchantedItemForEmeralds(Items.w, new EntityVillager.PriceInfo(12, 15))}}},
            {{{new EntityVillager.EmeraldForItems(Items.al, new EntityVillager.PriceInfo(14, 18)), new EntityVillager.EmeraldForItems(Items.bk, new EntityVillager.PriceInfo(14, 18))}, {new EntityVillager.EmeraldForItems(Items.h, new EntityVillager.PriceInfo(16, 24)), new EntityVillager.ListItemForEmeralds(Items.am, new EntityVillager.PriceInfo(-7, -5)), new EntityVillager.ListItemForEmeralds(Items.bl, new EntityVillager.PriceInfo(-8, -6))}}, {{new EntityVillager.EmeraldForItems(Items.aF, new EntityVillager.PriceInfo(9, 12)), new EntityVillager.ListItemForEmeralds(Items.S, new EntityVillager.PriceInfo(2, 4))}, {new EntityVillager.ListEnchantedItemForEmeralds(Items.R, new EntityVillager.PriceInfo(7, 12))}, {new EntityVillager.ListItemForEmeralds(Items.aA, new EntityVillager.PriceInfo(8, 10))}}}};

    public EntityVillager(World world) {
        this(world, 0);
    }

    public EntityVillager(World world, int i0) {
        super(world);
        this.bz = new InventoryBasic("Items", false, 8);
        this.r(i0);
        this.a(0.6F, 1.8F);
        ((PathNavigateGround) this.s()).b(true);
        ((PathNavigateGround) this.s()).a(true);
        this.i.a(0, new EntityAISwimming(this));
        this.i.a(1, new EntityAIAvoidEntity(this, new Predicate() {

            public boolean a(Entity p_a_1_) {
                return p_a_1_ instanceof EntityZombie;
            }

            public boolean apply(Object p_apply_1_) {
                return this.a((Entity) p_apply_1_);
            }
        }, 8.0F, 0.6D, 0.6D));
        this.i.a(1, new EntityAITradePlayer(this));
        this.i.a(1, new EntityAILookAtTradePlayer(this));
        this.i.a(2, new EntityAIMoveIndoors(this));
        this.i.a(3, new EntityAIRestrictOpenDoor(this));
        this.i.a(4, new EntityAIOpenDoor(this, true));
        this.i.a(5, new EntityAIMoveTowardsRestriction(this, 0.6D));
        this.i.a(6, new EntityAIVillagerMate(this));
        this.i.a(7, new EntityAIFollowGolem(this));
        this.i.a(9, new EntityAIWatchClosest2(this, EntityPlayer.class, 3.0F, 1.0F));
        this.i.a(9, new EntityAIVillagerInteract(this));
        this.i.a(9, new EntityAIWander(this, 0.6D));
        this.i.a(10, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
        this.j(true);
        this.entity = new CanaryVillager(this); // CanaryMod: Wrap Entity
    }

    private void ct() {
        if (!this.by) {
            this.by = true;
            if (this.i_()) {
                this.i.a(8, new EntityAIPlay(this, 0.32D));
            }
            else if (this.cj() == 0) {
                this.i.a(6, new EntityAIHarvestFarmland(this, 0.6D));
            }

        }
    }

    protected void n() {
        if (this.cj() == 0) {
            this.i.a(8, new EntityAIHarvestFarmland(this, 0.6D));
        }

        super.n();
    }

    protected void aW() {
        super.aW();
        this.a(SharedMonsterAttributes.d).a(0.5D);
    }

    protected void E() {
        if (--this.bl <= 0) {
            BlockPos blockpos = new BlockPos(this);

            this.o.ae().a(blockpos);
            this.bl = 70 + this.V.nextInt(50);
            this.bk = this.o.ae().a(blockpos, 32);
            if (this.bk == null) {
                this.ch();
            }
            else {
                BlockPos blockpos1 = this.bk.a();

                this.a(blockpos1, (int) ((float) this.bk.b() * 1.0F));
                if (this.bx) {
                    this.bx = false;
                    this.bk.b(5);
                }
            }
        }

        if (!this.cm() && this.bq > 0) {
            --this.bq;
            if (this.bq <= 0) {
                if (this.br) {
                    Iterator iterator = this.bp.iterator();

                    while (iterator.hasNext()) {
                        MerchantRecipe merchantrecipe = (MerchantRecipe) iterator.next();

                        if (merchantrecipe.h()) {
                            merchantrecipe.a(this.V.nextInt(6) + this.V.nextInt(6) + 2);
                        }
                    }

                    this.cu();
                    this.br = false;
                    if (this.bk != null && this.bu != null) {
                        this.o.a((Entity) this, (byte) 14);
                        this.bk.a(this.bu, 1);
                    }
                }

                this.c(new PotionEffect(Potion.l.H, 200, 0));
            }
        }

        super.E();
    }

    public boolean a(EntityPlayer entityplayer) {
        ItemStack itemstack = entityplayer.bg.h();
        boolean flag0 = itemstack != null && itemstack.b() == Items.bJ;

        if (!flag0 && this.ai() && !this.cm() && !this.i_()) {
            if (!this.o.D && (this.bp == null || this.bp.size() > 0)) {
                this.a_(entityplayer);
                entityplayer.a((IMerchant) this);
            }

            entityplayer.b(StatList.F);
            return true;
        }
        else {
            return super.a(entityplayer);
        }
    }

    protected void h() {
        super.h();
        this.ac.a(16, Integer.valueOf(0));
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("Profession", this.cj());
        nbttagcompound.a("Riches", this.bt);
        nbttagcompound.a("Career", this.bv);
        nbttagcompound.a("CareerLevel", this.bw);
        nbttagcompound.a("Willing", this.bs);
        if (this.bp != null) {
            nbttagcompound.a("Offers", (NBTBase) this.bp.a());
        }

        NBTTagList nbttaglist = new NBTTagList();

        for (int i0 = 0; i0 < this.bz.n_(); ++i0) {
            ItemStack itemstack = this.bz.a(i0);

            if (itemstack != null) {
                nbttaglist.a((NBTBase) itemstack.b(new NBTTagCompound()));
            }
        }

        nbttagcompound.a("Inventory", (NBTBase) nbttaglist);
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.r(nbttagcompound.f("Profession"));
        this.bt = nbttagcompound.f("Riches");
        this.bv = nbttagcompound.f("Career");
        this.bw = nbttagcompound.f("CareerLevel");
        this.bs = nbttagcompound.n("Willing");
        if (nbttagcompound.b("Offers", 10)) {
            NBTTagCompound nbttagcompound1 = nbttagcompound.m("Offers");

            this.bp = new MerchantRecipeList(nbttagcompound1);
        }

        NBTTagList nbttaglist = nbttagcompound.c("Inventory", 10);

        for (int i0 = 0; i0 < nbttaglist.c(); ++i0) {
            ItemStack itemstack = ItemStack.a(nbttaglist.b(i0));

            if (itemstack != null) {
                this.bz.a(itemstack);
            }
        }

        this.j(true);
        this.ct();
    }

    protected boolean C() {
        return false;
    }

    protected String z() {
        return this.cm() ? "mob.villager.haggle" : "mob.villager.idle";
    }

    protected String bn() {
        return "mob.villager.hit";
    }

    protected String bo() {
        return "mob.villager.death";
    }

    public void r(int i0) {
        this.ac.b(16, Integer.valueOf(i0));
    }

    public int cj() {
        return Math.max(this.ac.c(16) % 5, 0);
    }

    public boolean ck() {
        return this.bm;
    }

    public void l(boolean flag0) {
        this.bm = flag0;
    }

    public void m(boolean flag0) {
        this.bn = flag0;
    }

    public boolean cl() {
        return this.bn;
    }

    public void b(EntityLivingBase entitylivingbase) {
        super.b(entitylivingbase);
        if (this.bk != null && entitylivingbase != null) {
            this.bk.a(entitylivingbase);
            if (entitylivingbase instanceof EntityPlayer) {
                byte b0 = -1;

                if (this.i_()) {
                    b0 = -3;
                }

                this.bk.a(entitylivingbase.d_(), b0);
                if (this.ai()) {
                    this.o.a((Entity) this, (byte) 13);
                }
            }
        }

    }

    public void a(DamageSource damagesource) {
        if (this.bk != null) {
            Entity entity = damagesource.j();

            if (entity != null) {
                if (entity instanceof EntityPlayer) {
                    this.bk.a(entity.d_(), -2);
                }
                else if (entity instanceof IMob) {
                    this.bk.h();
                }
            }
            else {
                EntityPlayer entityplayer = this.o.a(this, 16.0D);

                if (entityplayer != null) {
                    this.bk.h();
                }
            }
        }

        super.a(damagesource);
    }

    public void a_(EntityPlayer entityplayer) {
        this.bo = entityplayer;
    }

    public EntityPlayer u_() {
        return this.bo;
    }

    public boolean cm() {
        return this.bo != null;
    }

    public boolean n(boolean flag0) {
        if (!this.bs && flag0 && this.cp()) {
            boolean flag1 = false;

            for (int i0 = 0; i0 < this.bz.n_(); ++i0) {
                ItemStack itemstack = this.bz.a(i0);

                if (itemstack != null) {
                    if (itemstack.b() == Items.P && itemstack.b >= 3) {
                        flag1 = true;
                        this.bz.a(i0, 3);
                    }
                    else if ((itemstack.b() == Items.bS || itemstack.b() == Items.bR) && itemstack.b >= 12) {
                        flag1 = true;
                        this.bz.a(i0, 12);
                    }
                }

                if (flag1) {
                    this.o.a((Entity) this, (byte) 18);
                    this.bs = true;
                    break;
                }
            }
        }

        return this.bs;
    }

    public void o(boolean flag0) {
        this.bs = flag0;
    }

    public void a(MerchantRecipe merchantrecipe) {
        merchantrecipe.g();
        this.a_ = -this.w();
        this.a("mob.villager.yes", this.bA(), this.bB());
        int i0 = 3 + this.V.nextInt(4);

        if (merchantrecipe.e() == 1 || this.V.nextInt(5) == 0) {
            this.bq = 40;
            this.br = true;
            this.bs = true;
            if (this.bo != null) {
                this.bu = this.bo.d_();
            }
            else {
                this.bu = null;
            }

            i0 += 5;
        }

        if (merchantrecipe.a().b() == Items.bO) {
            this.bt += merchantrecipe.a().b;
        }

        if (merchantrecipe.j()) {
            this.o.d((Entity) (new EntityXPOrb(this.o, this.s, this.t + 0.5D, this.u, i0)));
        }

    }

    public void a_(ItemStack itemstack) {
        if (!this.o.D && this.a_ > -this.w() + 20) {
            this.a_ = -this.w();
            if (itemstack != null) {
                this.a("mob.villager.yes", this.bA(), this.bB());
            }
            else {
                this.a("mob.villager.no", this.bA(), this.bB());
            }
        }

    }

    public MerchantRecipeList b_(EntityPlayer entityplayer) {
        if (this.bp == null) {
            this.cu();
        }

        return this.bp;
    }

    private void cu() {
        EntityVillager.ITradeList[][][] aentityvillager_itradelist = bA[this.cj()];

        if (this.bv != 0 && this.bw != 0) {
            ++this.bw;
        }
        else {
            this.bv = this.V.nextInt(aentityvillager_itradelist.length) + 1;
            this.bw = 1;
        }

        if (this.bp == null) {
            this.bp = new MerchantRecipeList();
        }

        int i0 = this.bv - 1;
        int i1 = this.bw - 1;
        EntityVillager.ITradeList[][] aentityvillager_itradelist1 = aentityvillager_itradelist[i0];

        if (i1 < aentityvillager_itradelist1.length) {
            EntityVillager.ITradeList[] aentityvillager_itradelist2 = aentityvillager_itradelist1[i1];
            EntityVillager.ITradeList[] aentityvillager_itradelist3 = aentityvillager_itradelist2;
            int i2 = aentityvillager_itradelist2.length;

            for (int i3 = 0; i3 < i2; ++i3) {
                EntityVillager.ITradeList entityvillager_itradelist = aentityvillager_itradelist3[i3];
                entityvillager_itradelist.a(this.bp, this.V, this); // CanaryMod: pass EntityVillager
            }
        }

    }

    public IChatComponent e_() {
        String s0 = this.aL();

        if (s0 != null && s0.length() > 0) {
            return new ChatComponentText(s0);
        }
        else {
            if (this.bp == null) {
                this.cu();
            }

            String s1 = null;

            switch (this.cj()) {
                case 0:
                    if (this.bv == 1) {
                        s1 = "farmer";
                    }
                    else if (this.bv == 2) {
                        s1 = "fisherman";
                    }
                    else if (this.bv == 3) {
                        s1 = "shepherd";
                    }
                    else if (this.bv == 4) {
                        s1 = "fletcher";
                    }
                    break;

                case 1:
                    s1 = "librarian";
                    break;

                case 2:
                    s1 = "cleric";
                    break;

                case 3:
                    if (this.bv == 1) {
                        s1 = "armor";
                    }
                    else if (this.bv == 2) {
                        s1 = "weapon";
                    }
                    else if (this.bv == 3) {
                        s1 = "tool";
                    }
                    break;

                case 4:
                    if (this.bv == 1) {
                        s1 = "butcher";
                    }
                    else if (this.bv == 2) {
                        s1 = "leather";
                    }
            }

            if (s1 != null) {
                ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation("entity.Villager." + s1, new Object[0]);

                chatcomponenttranslation.b().a(this.aP());
                chatcomponenttranslation.b().a(this.aJ().toString());
                return chatcomponenttranslation;
            }
            else {
                return super.e_();
            }
        }
    }

    public float aR() {
        float f0 = 1.62F;

        if (this.i_()) {
            f0 = (float) ((double) f0 - 0.81D);
        }

        return f0;
    }

    public IEntityLivingData a(DifficultyInstance difficultyinstance, IEntityLivingData ientitylivingdata) {
        ientitylivingdata = super.a(difficultyinstance, ientitylivingdata);
        this.r(this.o.s.nextInt(5));
        this.ct();
        return ientitylivingdata;
    }

    public void cn() {
        this.bx = true;
    }

    public EntityVillager b(EntityAgeable entityageable) {
        EntityVillager entityvillager = new EntityVillager(this.o);

        entityvillager.a(this.o.E(new BlockPos(entityvillager)), (IEntityLivingData) null);
        return entityvillager;
    }

    public boolean ca() {
        return false;
    }

    public void a(EntityLightningBolt entitylightningbolt) {
        if (!this.o.D) {
            EntityWitch entitywitch = new EntityWitch(this.o);

            entitywitch.b(this.s, this.t, this.u, this.y, this.z);
            entitywitch.a(this.o.E(new BlockPos(entitywitch)), (IEntityLivingData) null);
            this.o.d((Entity) entitywitch);
            this.J();
        }
    }

    public InventoryBasic co() {
        return this.bz;
    }

    protected void a(EntityItem entityitem) {
        ItemStack itemstack = entityitem.l();
        Item item = itemstack.b();

        if (this.a(item)) {
            ItemStack itemstack1 = this.bz.a(itemstack);

            if (itemstack1 == null) {
                entityitem.J();
            }
            else {
                itemstack.b = itemstack1.b;
            }
        }

    }

    private boolean a(Item item) {
        return item == Items.P || item == Items.bS || item == Items.bR || item == Items.O || item == Items.N;
    }

    public boolean cp() {
        return this.s(1);
    }

    public boolean cq() {
        return this.s(2);
    }

    public boolean cr() {
        boolean flag0 = this.cj() == 0;

        return flag0 ? !this.s(5) : !this.s(1);
    }

    private boolean s(int i0) {
        boolean flag0 = this.cj() == 0;

        for (int i1 = 0; i1 < this.bz.n_(); ++i1) {
            ItemStack itemstack = this.bz.a(i1);

            if (itemstack != null) {
                if (itemstack.b() == Items.P && itemstack.b >= 3 * i0 || itemstack.b() == Items.bS && itemstack.b >= 12 * i0 || itemstack.b() == Items.bR && itemstack.b >= 12 * i0) {
                    return true;
                }

                if (flag0 && itemstack.b() == Items.O && itemstack.b >= 9 * i0) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean cs() {
        for (int i0 = 0; i0 < this.bz.n_(); ++i0) {
            ItemStack itemstack = this.bz.a(i0);

            if (itemstack != null && (itemstack.b() == Items.N || itemstack.b() == Items.bS || itemstack.b() == Items.bR)) {
                return true;
            }
        }

        return false;
    }

    public boolean d(int i0, ItemStack itemstack) {
        if (super.d(i0, itemstack)) {
            return true;
        }
        else {
            int i1 = i0 - 300;

            if (i1 >= 0 && i1 < this.bz.n_()) {
                this.bz.a(i1, itemstack);
                return true;
            }
            else {
                return false;
            }
        }
    }

    public EntityAgeable a(EntityAgeable entityageable) {
        return this.b(entityageable);
    }

    static class EmeraldForItems implements EntityVillager.ITradeList {

        public Item a;
        public EntityVillager.PriceInfo b;

        public EmeraldForItems(Item item, EntityVillager.PriceInfo entityvillager_priceinfo) {
            this.a = item;
            this.b = entityvillager_priceinfo;
        }

        public void a(MerchantRecipeList merchantrecipelist, Random random, EntityVillager entityvillager) { // CanaryMod: Signature change, pass EntityVillager
            int i0 = 1;

            if (this.b != null) {
                i0 = this.b.a(random);
            }

            // CanaryMod: VillagerTradeUnlock
            MerchantRecipe newRecipe = new MerchantRecipe(new ItemStack(this.a, i0, 0), Items.bO);
            VillagerTradeUnlockHook hook = (VillagerTradeUnlockHook) new VillagerTradeUnlockHook(entityvillager.getCanaryEntity(), new CanaryVillagerTrade(newRecipe)).call();
            if (!hook.isCanceled()) {
                merchantrecipelist.add(newRecipe);
            }
            //

        }
    }

    interface ITradeList {

        void a(MerchantRecipeList merchantrecipelist, Random random, EntityVillager entityvillager); // CanaryMod: Signature change, pass EntityVillager
    }

    static class ItemAndEmeraldToItem implements EntityVillager.ITradeList {

        public ItemStack a;
        public EntityVillager.PriceInfo b;
        public ItemStack c;
        public EntityVillager.PriceInfo d;

        public ItemAndEmeraldToItem(Item item, EntityVillager.PriceInfo entityvillager_priceinfo, Item item1, EntityVillager.PriceInfo entityvillager_priceinfo1) {
            this.a = new ItemStack(item);
            this.b = entityvillager_priceinfo;
            this.c = new ItemStack(item1);
            this.d = entityvillager_priceinfo1;
        }

        public void a(MerchantRecipeList merchantrecipelist, Random random, EntityVillager entityvillager) { // CanaryMod: Signature change, pass EntityVillager
            int i0 = 1;

            if (this.b != null) {
                i0 = this.b.a(random);
            }

            int i1 = 1;

            if (this.d != null) {
                i1 = this.d.a(random);
            }

            // CanaryMod: VillagerTradeUnlock
            MerchantRecipe newRecipe = new MerchantRecipe(new ItemStack(this.a.b(), i0, this.a.i()), new ItemStack(Items.bO), new ItemStack(this.c.b(), i1, this.c.i()));
            VillagerTradeUnlockHook hook = (VillagerTradeUnlockHook) new VillagerTradeUnlockHook(entityvillager.getCanaryEntity(), new CanaryVillagerTrade(newRecipe)).call();
            if (!hook.isCanceled()) {
                merchantrecipelist.add(newRecipe);
            }
            //
        }
    }


    static class ListEnchantedBookForEmeralds implements EntityVillager.ITradeList {

        public void a(MerchantRecipeList merchantrecipelist, Random random, EntityVillager entityvillager) { // CanaryMod: Signature change, pass EntityVillager
            Enchantment enchantment = Enchantment.b[random.nextInt(Enchantment.b.length)];
            int i0 = MathHelper.a(random, enchantment.e(), enchantment.b());
            ItemStack itemstack = Items.cd.a(new EnchantmentData(enchantment, i0));
            int i1 = 2 + random.nextInt(5 + i0 * 10) + 3 * i0;

            if (i1 > 64) {
                i1 = 64;
            }

            // CanaryMod: VillagerTradeUnlock
            MerchantRecipe newRecipe = new MerchantRecipe(new ItemStack(Items.aL), new ItemStack(Items.bO, i1), itemstack);
            VillagerTradeUnlockHook hook = (VillagerTradeUnlockHook) new VillagerTradeUnlockHook(entityvillager.getCanaryEntity(), new CanaryVillagerTrade(newRecipe)).call();
            if (!hook.isCanceled()) {
                merchantrecipelist.add(newRecipe);
            }
            //
        }
    }


    static class ListEnchantedItemForEmeralds implements EntityVillager.ITradeList {

        public ItemStack a;
        public EntityVillager.PriceInfo b;

        public ListEnchantedItemForEmeralds(Item item, EntityVillager.PriceInfo entityvillager_priceinfo) {
            this.a = new ItemStack(item);
            this.b = entityvillager_priceinfo;
        }

        public void a(MerchantRecipeList merchantrecipelist, Random random, EntityVillager entityvillager) { // CanaryMod: Signature change, pass EntityVillager
            int i0 = 1;

            if (this.b != null) {
                i0 = this.b.a(random);
            }

            ItemStack itemstack = new ItemStack(Items.bO, i0, 0);
            ItemStack itemstack1 = new ItemStack(this.a.b(), 1, this.a.i());

            itemstack1 = EnchantmentHelper.a(random, itemstack1, 5 + random.nextInt(15));

            // CanaryMod: VillagerTradeUnlock
            MerchantRecipe newRecipe = new MerchantRecipe(itemstack, itemstack1);
            VillagerTradeUnlockHook hook = (VillagerTradeUnlockHook) new VillagerTradeUnlockHook(entityvillager.getCanaryEntity(), new CanaryVillagerTrade(newRecipe)).call();
            if (!hook.isCanceled()) {
                merchantrecipelist.add(newRecipe);
            }
            //
        }
    }


    static class ListItemForEmeralds implements EntityVillager.ITradeList {

        public ItemStack a;
        public EntityVillager.PriceInfo b;

        public ListItemForEmeralds(Item item, EntityVillager.PriceInfo entityvillager_priceinfo) {
            this.a = new ItemStack(item);
            this.b = entityvillager_priceinfo;
        }

        public ListItemForEmeralds(ItemStack itemstack, EntityVillager.PriceInfo entityvillager_priceinfo) {
            this.a = itemstack;
            this.b = entityvillager_priceinfo;
        }

        public void a(MerchantRecipeList merchantrecipelist, Random random, EntityVillager entityvillager) { // CanaryMod: Signature change, pass EntityVillager
            int i0 = 1;

            if (this.b != null) {
                i0 = this.b.a(random);
            }

            ItemStack itemstack;
            ItemStack itemstack1;

            if (i0 < 0) {
                itemstack = new ItemStack(Items.bO, 1, 0);
                itemstack1 = new ItemStack(this.a.b(), -i0, this.a.i());
            }
            else {
                itemstack = new ItemStack(Items.bO, i0, 0);
                itemstack1 = new ItemStack(this.a.b(), 1, this.a.i());
            }

            // CanaryMod: VillagerTradeUnlock
            MerchantRecipe newRecipe = new MerchantRecipe(itemstack, itemstack1);
            VillagerTradeUnlockHook hook = (VillagerTradeUnlockHook) new VillagerTradeUnlockHook(entityvillager.getCanaryEntity(), new CanaryVillagerTrade(newRecipe)).call();
            if (!hook.isCanceled()) {
                merchantrecipelist.add(newRecipe);
            }
            //
        }
    }


    static class PriceInfo extends Tuple {

        public PriceInfo(int p_i45810_1_, int p_i45810_2_) {
            super(Integer.valueOf(p_i45810_1_), Integer.valueOf(p_i45810_2_));
        }

        public int a(Random p_a_1_) {
            return ((Integer) this.a()).intValue() >= ((Integer) this.b()).intValue() ? ((Integer) this.a()).intValue() : ((Integer) this.a()).intValue() + p_a_1_.nextInt(((Integer) this.b()).intValue() - ((Integer) this.a()).intValue() + 1);
        }
    }

    // CanaryMod
    public Village getVillage() {
        return this.bk;
    }

    public void setVillage(Village village) {
        this.bk = village;
    }

    public CanaryVillager getCanaryEntity() {
        return (CanaryVillager) super.getCanaryEntity();
    }
    //
}
