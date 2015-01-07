package net.minecraft.entity.player;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import net.canarymod.Canary;
import net.canarymod.ToolBox;
import net.canarymod.api.entity.EntityType;
import net.canarymod.api.entity.living.humanoid.CanaryHuman;
import net.canarymod.api.entity.living.humanoid.CanaryPlayer;
import net.canarymod.api.entity.living.humanoid.Player;
import net.canarymod.api.inventory.CanaryEnderChestInventory;
import net.canarymod.api.inventory.CanaryItem;
import net.canarymod.api.inventory.CanaryPlayerInventory;
import net.canarymod.api.inventory.EnderChestInventory;
import net.canarymod.api.inventory.PlayerInventory;
import net.canarymod.api.nbt.CanaryCompoundTag;
import net.canarymod.api.packet.CanaryPacket;
import net.canarymod.api.scoreboard.CanaryScoreboard;
import net.canarymod.api.world.CanaryWorld;
import net.canarymod.api.world.position.BlockPosition;
import net.canarymod.api.world.position.Location;
import net.canarymod.hook.player.BedEnterHook;
import net.canarymod.hook.player.BedExitHook;
import net.canarymod.hook.player.EntityRightClickHook;
import net.canarymod.hook.player.ItemDropHook;
import net.canarymod.hook.player.LevelUpHook;
import net.canarymod.hook.player.ToolBrokenHook;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.event.ClickEvent;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S06PacketUpdateHealth;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S1FPacketSetExperience;
import net.minecraft.potion.Potion;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.FoodStats;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.LockCode;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings;
import net.visualillusionsent.utils.DateUtils;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public abstract class EntityPlayer extends EntityLivingBase {

    public InventoryPlayer bg = new InventoryPlayer(this);
    private InventoryEnderChest a = new InventoryEnderChest();
    public Container bh;
    public Container bi;
    protected FoodStats bj = new FoodStats(this); // CanaryMod: Constructor change
    protected int bk;
    public float bl;
    public float bm;
    public int bn;
    public double bo;
    public double bp;
    public double bq;
    public double br;
    public double bs;
    public double bt;
    protected boolean bu; // Sleeping
    public BlockPos bv;
    private int b;
    public float bw;
    public float bx;
    private BlockPos c;
    private boolean d;
    private BlockPos e;
    public PlayerCapabilities by = new PlayerCapabilities();
    public int bz; // level
    public int bA; // total points
    public float bB;
    private int f;
    public ItemStack g; // CanaryMod: private -> public (item in use)
    private int h;
    protected float bC = 0.1F;
    protected float bD = 0.02F;
    private int i;
    protected final GameProfile bF; // CanaryMod: private => protected
    private boolean bG = false;
    public EntityFishHook bE;

    // CanaryMod
    private boolean sleepIgnored; // Fake Sleeping
    protected IChatComponent displayName;
    protected Location canaryRespawn;
    private long currentSessionStart = ToolBox.getUnixTimestamp(); // CanaryMod: current session tracking.
    //Darkdiplomat Note: Fields are non-persistant between respawns and world switching. Use the Meta tag for persistance.

    public EntityPlayer(World world, GameProfile gameprofile) {
        super(world);
        this.ao = a(gameprofile);
        this.bF = gameprofile;
        this.bh = new ContainerPlayer(this.bg, !world.D, this);
        this.bi = this.bh;
        BlockPos blockpos = world.M();

        this.b((double)blockpos.n() + 0.5D, (double)(blockpos.o() + 1), (double)blockpos.p() + 0.5D, 0.0F, 0.0F);
        this.aT = 180.0F;
        this.X = 20;
    }

    protected void aW() {
        super.aW();
        this.bx().b(SharedMonsterAttributes.e).a(1.0D);
        this.a(SharedMonsterAttributes.d).a(0.10000000149011612D);
    }

    protected void h() {
        super.h();
        this.ac.a(16, Byte.valueOf((byte)0));
        this.ac.a(17, Float.valueOf(0.0F));
        this.ac.a(18, Integer.valueOf(0));
        this.ac.a(10, Byte.valueOf((byte)0));
    }

    public boolean bR() {
        return this.g != null;
    }

    public void bT() {
        if (this.g != null) {
            this.g.b(this.o, this, this.h);
        }

        this.bU();
    }

    public void bU() {
        this.g = null;
        this.h = 0;
        if (!this.o.D) {
            this.f(false);
        }
    }

    public boolean bV() {
        return this.bR() && this.g.b().e(this.g) == EnumAction.BLOCK;
    }

    public void s_() {
        this.T = this.v();
        if (this.v()) {
            this.C = false;
        }

        if (this.g != null) {
            ItemStack itemstack = this.bg.h();

            if (itemstack == this.g) {
                if (this.h <= 25 && this.h % 4 == 0) {
                    this.b(itemstack, 5);
                }

                if (--this.h == 0 && !this.o.D) {
                    this.s();
                }
            }
            else {
                this.bU();
            }
        }

        if (this.bn > 0) {
            --this.bn;
        }

        if (this.bI()) {
            ++this.b;
            if (this.b > 100) {
                this.b = 100;
            }

            if (!this.o.D) {
                if (!this.p()) {
                    this.a(true, true, false);
                }
                else if (this.o.w()) {
                    this.a(false, true, true);
                }
            }
        }
        else if (this.b > 0) {
            ++this.b;
            if (this.b >= 110) {
                this.b = 0;
            }
        }

        super.s_();
        if (!this.o.D && this.bi != null && !this.bi.a(this)) {
            this.n();
            this.bi = this.bh;
        }

        if (this.au() && this.by.a) {
            this.N();
        }

        this.bo = this.br;
        this.bp = this.bs;
        this.bq = this.bt;
        double d0 = this.s - this.br;
        double d1 = this.t - this.bs;
        double d2 = this.u - this.bt;
        double d3 = 10.0D;

        if (d0 > d3) {
            this.bo = this.br = this.s;
        }

        if (d2 > d3) {
            this.bq = this.bt = this.u;
        }

        if (d1 > d3) {
            this.bp = this.bs = this.t;
        }

        if (d0 < -d3) {
            this.bo = this.br = this.s;
        }

        if (d2 < -d3) {
            this.bq = this.bt = this.u;
        }

        if (d1 < -d3) {
            this.bp = this.bs = this.t;
        }

        this.br += d0 * 0.25D;
        this.bt += d2 * 0.25D;
        this.bs += d1 * 0.25D;
        if (this.m == null) {
            this.e = null;
        }

        if (!this.o.D && this instanceof EntityPlayerMP) { // CanaryMod: check if actually a Player
            this.bj.a(this);
            this.b(StatList.g);
            if (this.ai()) {
                this.b(StatList.h);
            }
        }

        int i0 = 29999999;
        double d4 = MathHelper.a(this.s, -2.9999999E7D, 2.9999999E7D);
        double d5 = MathHelper.a(this.u, -2.9999999E7D, 2.9999999E7D);

        if (d4 != this.s || d5 != this.u) {
            this.b(d4, this.t, d5);
        }
    }

    public int L() {
        return this.by.a ? 0 : 80;
    }

    protected String P() {
        return "game.player.swim";
    }

    protected String aa() {
        return "game.player.swim.splash";
    }

    public int ar() {
        return 10;
    }

    public void a(String s0, float f0, float f1) {
        this.o.a(this, s0, f0, f1);
    }

    protected void b(ItemStack itemstack, int i0) {
        if (itemstack.m() == EnumAction.DRINK) {
            this.a("random.drink", 0.5F, this.o.s.nextFloat() * 0.1F + 0.9F);
        }

        if (itemstack.m() == EnumAction.EAT) {
            for (int i1 = 0; i1 < i0; ++i1) {
                Vec3 vec3 = new Vec3(((double)this.V.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);

                vec3 = vec3.a(-this.z * 3.1415927F / 180.0F);
                vec3 = vec3.b(-this.y * 3.1415927F / 180.0F);
                double d0 = (double)(-this.V.nextFloat()) * 0.6D - 0.3D;
                Vec3 vec31 = new Vec3(((double)this.V.nextFloat() - 0.5D) * 0.3D, d0, 0.6D);

                vec31 = vec31.a(-this.z * 3.1415927F / 180.0F);
                vec31 = vec31.b(-this.y * 3.1415927F / 180.0F);
                vec31 = vec31.b(this.s, this.t + (double)this.aR(), this.u);
                if (itemstack.f()) {
                    this.o.a(EnumParticleTypes.ITEM_CRACK, vec31.a, vec31.b, vec31.c, vec3.a, vec3.b + 0.05D, vec3.c, new int[]{ Item.b(itemstack.b()), itemstack.i() });
                }
                else {
                    this.o.a(EnumParticleTypes.ITEM_CRACK, vec31.a, vec31.b, vec31.c, vec3.a, vec3.b + 0.05D, vec3.c, new int[]{ Item.b(itemstack.b()) });
                }
            }

            this.a("random.eat", 0.5F + 0.5F * (float)this.V.nextInt(2), (this.V.nextFloat() - this.V.nextFloat()) * 0.2F + 1.0F);
        }
    }

    protected void s() {
        if (this.g != null) {
            this.b(this.g, 16);
            int i0 = this.g.b;
            ItemStack itemstack = this.g.b(this.o, this);

            if (itemstack != this.g || itemstack != null && itemstack.b != i0) {
                this.bg.a[this.bg.c] = itemstack;
                if (itemstack.b == 0) {
                    this.bg.a[this.bg.c] = null;
                }
            }

            this.bU();
        }
    }

    protected boolean bC() {
        return this.bm() <= 0.0F || this.bI();
    }

    protected void n() {
        this.bi = this.bh;
    }

    public void ak() {
        if (!this.o.D && this.aw()) {
            this.a((Entity)null);
            this.c(false);
        }
        else {
            double d0 = this.s;
            double d1 = this.t;
            double d2 = this.u;
            float f0 = this.y;
            float f1 = this.z;

            super.ak();
            this.bl = this.bm;
            this.bm = 0.0F;
            this.l(this.s - d0, this.t - d1, this.u - d2);
            if (this.m instanceof EntityPig) {
                this.z = f1;
                this.y = f0;
                this.aG = ((EntityPig)this.m).aG;
            }
        }
    }

    protected void bJ() {
        super.bJ();
        this.bw();
        this.aI = this.y;
    }

    public void m() {
        if (this.bk > 0) {
            --this.bk;
        }

        if (this.o.aa() == EnumDifficulty.PEACEFUL && this.o.Q().b("naturalRegeneration")) {
            if (this.bm() < this.bt() && this.W % 20 == 0) {
                this.g(1.0F);
            }

            if (this.bj.c() && this.W % 10 == 0) {
                this.bj.a(this.bj.a() + 1);
            }
        }

        this.bg.k();
        this.bl = this.bm;
        super.m();
        IAttributeInstance iattributeinstance = this.a(SharedMonsterAttributes.d);

        if (!this.o.D) {
            iattributeinstance.a((double)this.by.b());
        }

        this.aK = this.bD;
        if (this.ax()) {
            this.aK = (float)((double)this.aK + (double)this.bD * 0.3D);
        }

        this.j((float)iattributeinstance.e());
        float f0 = MathHelper.a(this.v * this.v + this.x * this.x);
        float f1 = (float)(Math.atan(-this.w * 0.20000000298023224D) * 15.0D);

        if (f0 > 0.1F) {
            f0 = 0.1F;
        }

        if (!this.C || this.bm() <= 0.0F) {
            f0 = 0.0F;
        }

        if (this.C || this.bm() <= 0.0F) {
            f1 = 0.0F;
        }

        this.bm += (f0 - this.bm) * 0.4F;
        this.aD += (f1 - this.aD) * 0.8F;
        if (this.bm() > 0.0F && !this.v()) {
            AxisAlignedBB axisalignedbb = null;

            if (this.m != null && !this.m.I) {
                axisalignedbb = this.aQ().a(this.m.aQ()).b(1.0D, 0.0D, 1.0D);
            }
            else {
                axisalignedbb = this.aQ().b(1.0D, 0.5D, 1.0D);
            }

            List list = this.o.b((Entity)this, axisalignedbb);

            for (int i0 = 0; i0 < list.size(); ++i0) {
                Entity entity = (Entity)list.get(i0);

                if (!entity.I) {
                    this.d(entity);
                }
            }
        }
    }

    private void d(Entity entity) {
        entity.d(this);
    }

    public int bW() {
        return this.ac.c(18);
    }

    public void r(int i0) {
        this.ac.b(18, Integer.valueOf(i0));
    }

    public void s(int i0) {
        int i1 = this.bW();

        this.ac.b(18, Integer.valueOf(i1 + i0));
    }

    public void a(DamageSource damagesource) {
        super.a(damagesource);
        this.a(0.2F, 0.2F);
        this.b(this.s, this.t, this.u);
        this.w = 0.10000000149011612D;
        if (this.d_().equals("Notch")) {
            this.a(new ItemStack(Items.e, 1), true, false);
        }

        if (!this.o.Q().b("keepInventory")) {
            this.bg.n();
        }

        if (damagesource != null) {
            this.v = (double)(-MathHelper.b((this.au + this.y) * 3.1415927F / 180.0F) * 0.1F);
            this.x = (double)(-MathHelper.a((this.au + this.y) * 3.1415927F / 180.0F) * 0.1F);
        }
        else {
            this.v = this.x = 0.0D;
        }

        this.b(StatList.y);
        this.a(StatList.h);
    }

    protected String bn() {
        return "game.player.hurt";
    }

    protected String bo() {
        return "game.player.die";
    }

    public void b(Entity entity, int i0) {
        this.s(i0);
        Collection collection = this.co().a(IScoreObjectiveCriteria.f);

        if (entity instanceof EntityPlayer) {
            this.b(StatList.B);
            collection.addAll(this.co().a(IScoreObjectiveCriteria.e));
            collection.addAll(this.e(entity));
        }
        else {
            this.b(StatList.z);
        }

        Iterator iterator = collection.iterator();

        while (iterator.hasNext()) {
            ScoreObjective scoreobjective = (ScoreObjective)iterator.next();
            Score score = this.co().c(this.d_(), scoreobjective);

            score.a();
        }
    }

    private Collection e(Entity entity) {
        ScorePlayerTeam scoreplayerteam = this.co().h(this.d_());

        if (scoreplayerteam != null) {
            int i0 = scoreplayerteam.l().b();

            if (i0 >= 0 && i0 < IScoreObjectiveCriteria.i.length) {
                Iterator iterator = this.co().a(IScoreObjectiveCriteria.i[i0]).iterator();

                while (iterator.hasNext()) {
                    ScoreObjective scoreobjective = (ScoreObjective)iterator.next();
                    Score score = this.co().c(entity.d_(), scoreobjective);

                    score.a();
                }
            }
        }

        ScorePlayerTeam scoreplayerteam1 = this.co().h(entity.d_());

        if (scoreplayerteam1 != null) {
            int i1 = scoreplayerteam1.l().b();

            if (i1 >= 0 && i1 < IScoreObjectiveCriteria.h.length) {
                return this.co().a(IScoreObjectiveCriteria.h[i1]);
            }
        }

        return Lists.newArrayList();
    }

    public EntityItem a(boolean flag0) {
        return this.a(this.bg.a(this.bg.c, flag0 && this.bg.h() != null ? this.bg.h().b : 1), false, true);
    }

    public EntityItem a(ItemStack itemstack, boolean flag0) {
        return this.a(itemstack, false, false);
    }

    public EntityItem a(ItemStack itemstack, boolean flag0, boolean flag1) {
        if (itemstack == null) {
            return null;
        }
        else if (itemstack.b == 0) {
            return null;
        }
        else {
            double d0 = this.t - 0.30000001192092896D + (double)this.aR();
            EntityItem entityitem = new EntityItem(this.o, this.s, d0, this.u, itemstack);

            entityitem.a(40);
            if (flag1) {
                entityitem.c(this.d_());
            }

            float f0;
            float f1;

            if (flag0) {
                f0 = this.V.nextFloat() * 0.5F;
                f1 = this.V.nextFloat() * 3.1415927F * 2.0F;
                entityitem.v = (double)(-MathHelper.a(f1) * f0);
                entityitem.x = (double)(MathHelper.b(f1) * f0);
                entityitem.w = 0.20000000298023224D;
            }
            else {
                f0 = 0.3F;
                entityitem.v = (double)(-MathHelper.a(this.y / 180.0F * 3.1415927F) * MathHelper.b(this.z / 180.0F * 3.1415927F) * f0);
                entityitem.x = (double)(MathHelper.b(this.y / 180.0F * 3.1415927F) * MathHelper.b(this.z / 180.0F * 3.1415927F) * f0);
                entityitem.w = (double)(-MathHelper.a(this.z / 180.0F * 3.1415927F) * f0 + 0.1F);
                f1 = this.V.nextFloat() * 3.1415927F * 2.0F;
                f0 = 0.02F * this.V.nextFloat();
                entityitem.v += Math.cos((double)f1) * (double)f0;
                entityitem.w += (double)((this.V.nextFloat() - this.V.nextFloat()) * 0.1F);
                entityitem.x += Math.sin((double)f1) * (double)f0;
            }

            // CanaryMod: ItemDrop
            if (this instanceof EntityPlayerMP) { // NO NPCs
                ItemDropHook hook = (ItemDropHook)new ItemDropHook(((EntityPlayerMP)this).getPlayer(), (net.canarymod.api.entity.EntityItem)entityitem.getCanaryEntity()).call();
                if (!hook.isCanceled()) {
                    CanaryItem droppedItem = entityitem.l().getCanaryItem();

                    if (droppedItem.getAmount() < 0) {
                        droppedItem.setAmount(1);
                    }
                    this.a(entityitem);
                    if (flag1) {
                        this.b(StatList.v);
                    }
                    return entityitem;
                }
            }
            return null;
            //
        }
    }

    protected void a(EntityItem entityitem) {
        this.o.d((Entity)entityitem);
    }

    public float a(Block block) {
        float f0 = this.bg.a(block);

        if (f0 > 1.0F) {
            int i0 = EnchantmentHelper.c(this);
            ItemStack itemstack = this.bg.h();

            if (i0 > 0 && itemstack != null) {
                f0 += (float)(i0 * i0 + 1);
            }
        }

        if (this.a(Potion.e)) {
            f0 *= 1.0F + (float)(this.b(Potion.e).c() + 1) * 0.2F;
        }

        if (this.a(Potion.f)) {
            float f1 = 1.0F;

            switch (this.b(Potion.f).c()) {
                case 0:
                    f1 = 0.3F;
                    break;

                case 1:
                    f1 = 0.09F;
                    break;

                case 2:
                    f1 = 0.0027F;
                    break;

                case 3:
                default:
                    f1 = 8.1E-4F;
            }

            f0 *= f1;
        }

        if (this.a(Material.h) && !EnchantmentHelper.j(this)) {
            f0 /= 5.0F;
        }

        if (!this.C) {
            f0 /= 5.0F;
        }

        return f0;
    }

    public boolean b(Block block) {
        return this.bg.b(block);
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.ao = a(this.bF);
        NBTTagList nbttaglist = nbttagcompound.c("Inventory", 10);

        this.bg.b(nbttaglist);
        this.bg.c = nbttagcompound.f("SelectedItemSlot");
        this.bu = nbttagcompound.n("Sleeping");
        this.b = nbttagcompound.e("SleepTimer");
        if(nbttagcompound.c("SleepingIgnored")){
            this.sleepIgnored = nbttagcompound.n("SleepingIgnored");
        }
        this.bB = nbttagcompound.h("XpP");
        this.bz = nbttagcompound.f("XpLevel");
        this.bA = nbttagcompound.f("XpTotal");
        this.f = nbttagcompound.f("XpSeed");
        if (this.f == 0) {
            this.f = this.V.nextInt();
        }

        this.r(nbttagcompound.f("Score"));
        if (this.bu) {
            this.bv = new BlockPos(this);
            this.a(true, true, false);
        }

        if (nbttagcompound.b("SpawnX", 99) && nbttagcompound.b("SpawnY", 99) && nbttagcompound.b("SpawnZ", 99)) {
            this.c = new BlockPos(nbttagcompound.f("SpawnX"), nbttagcompound.f("SpawnY"), nbttagcompound.f("SpawnZ"));
            this.d = nbttagcompound.n("SpawnForced");
            String fqWorld = nbttagcompound.j("SpawnWorld");
            net.canarymod.api.world.World world = Canary.getServer().getWorld(fqWorld);
            // CanaryMod added respawn world
            if (world != null) {
                this.canaryRespawn = new Location(world, c.n(), c.o(), c.p(), 0f, 0f);
            }
        }

        this.bj.a(nbttagcompound);
        this.by.b(nbttagcompound);
        if (nbttagcompound.b("EnderItems", 9)) {
            NBTTagList nbttaglist1 = nbttagcompound.c("EnderItems", 10);

            this.a.a(nbttaglist1);
        }
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("Inventory", (NBTBase)this.bg.a(new NBTTagList()));
        nbttagcompound.a("SelectedItemSlot", this.bg.c);
        nbttagcompound.a("Sleeping", this.bu);
        nbttagcompound.a("SleepTimer", (short)this.b);
        nbttagcompound.a("SleepingIgnored", this.sleepIgnored);
        nbttagcompound.a("XpP", this.bB);
        nbttagcompound.a("XpLevel", this.bz);
        nbttagcompound.a("XpTotal", this.bA);
        nbttagcompound.a("XpSeed", this.f);
        nbttagcompound.a("Score", this.bW());
        if (this.canaryRespawn != null) {
            nbttagcompound.a("SpawnX", this.canaryRespawn.getBlockX());
            nbttagcompound.a("SpawnY", this.canaryRespawn.getBlockY());
            nbttagcompound.a("SpawnZ", this.canaryRespawn.getBlockZ());
            nbttagcompound.a("SpawnForced", this.d);
            // CanaryMod add world fq name
            nbttagcompound.a("SpawnWorld", getCanaryWorld().getFqName());
        }

        this.bj.b(nbttagcompound);
        this.by.a(nbttagcompound);
        nbttagcompound.a("EnderItems", (NBTBase)this.a.h());
        //Make sure meta is saved right
        saveMeta();
        ItemStack itemstack = this.bg.h();

        if (itemstack != null && itemstack.b() != null) {
            nbttagcompound.a("SelectedItem", (NBTBase)itemstack.b(new NBTTagCompound()));
        }
    }

    public boolean a(DamageSource damagesource, float f0) {
        if (this.b(damagesource)) {
            return false;
        }
        else if (this.by.a && !damagesource.g()) {
            return false;
        }
        else {
            this.aO = 0;
            if (this.bm() <= 0.0F) {
                return false;
            }
            else {
                if (this.bI() && !this.o.D) {
                    this.a(true, true, false);
                }

                if (damagesource.r()) {
                    if (this.o.aa() == EnumDifficulty.PEACEFUL) {
                        f0 = 0.0F;
                    }

                    if (this.o.aa() == EnumDifficulty.EASY) {
                        f0 = f0 / 2.0F + 1.0F;
                    }

                    if (this.o.aa() == EnumDifficulty.HARD) {
                        f0 = f0 * 3.0F / 2.0F;
                    }
                }

                if (f0 == 0.0F) {
                    return false;
                }
                else {
                    Entity entity = damagesource.j();

                    if (entity instanceof EntityArrow && ((EntityArrow)entity).c != null) {
                        entity = ((EntityArrow)entity).c;
                    }

                    return super.a(damagesource, f0);
                }
            }
        }
    }

    public boolean a(EntityPlayer entityplayer) {
        Team team = this.bN();
        Team team1 = entityplayer.bN();

        return team == null ? true : (!team.a(team1) ? true : team.g());
    }

    protected void i(float f0) {
        this.bg.a(f0);
    }

    public int bq() {
        return this.bg.m();
    }

    public float bX() {
        int i0 = 0;
        ItemStack[] aitemstack = this.bg.b;
        int i1 = aitemstack.length;

        for (int i2 = 0; i2 < i1; ++i2) {
            ItemStack itemstack = aitemstack[i2];

            if (itemstack != null) {
                ++i0;
            }
        }

        return (float)i0 / (float)this.bg.b.length;
    }

    protected void d(DamageSource damagesource, float f0) {
        if (!this.b(damagesource)) {
            if (!damagesource.e() && this.bV() && f0 > 0.0F) {
                f0 = (1.0F + f0) * 0.5F;
            }

            f0 = this.b(damagesource, f0);
            f0 = this.c(damagesource, f0);
            float f1 = f0;

            f0 = Math.max(f0 - this.bM(), 0.0F);
            this.l(this.bM() - (f1 - f0));
            if (f0 != 0.0F) {
                this.a(damagesource.f());
                float f2 = this.bm();

                this.h(this.bm() - f0);
                this.br().a(damagesource, f2, f0);
                if (f0 < 3.4028235E37F) {
                    this.a(StatList.x, Math.round(f0 * 10.0F));
                }
            }
        }
    }

    public void a(TileEntitySign tileentitysign) {
    }

    public void a(CommandBlockLogic commandblocklogic) {
    }

    public void a(IMerchant imerchant) {
    }

    public void a(IInventory iinventory) {
    }

    public void a(EntityHorse entityhorse, IInventory iinventory) {
    }

    public void a(IInteractionObject iinteractionobject) {
    }

    public void a(ItemStack itemstack) {
    }

    public boolean u(Entity entity) {
        // CanaryMod: EntityRightClickHook
        EntityRightClickHook hook = (EntityRightClickHook)new EntityRightClickHook(entity.getCanaryEntity(), ((EntityPlayerMP)this).getPlayer()).call();
        if (hook.isCanceled()) {
            return false;
        }
        //
        if (this.v()) {
            if (entity instanceof IInventory) {
                this.a((IInventory)entity);
            }

            return false;
        }
        else {
            ItemStack itemstack = this.bY();
            ItemStack itemstack1 = itemstack != null ? itemstack.k() : null;

            if (!entity.e(this)) {
                if (itemstack != null && entity instanceof EntityLivingBase) {
                    if (this.by.d) {
                        itemstack = itemstack1;
                    }

                    if (itemstack.a(this, (EntityLivingBase)entity)) {
                        if (itemstack.b <= 0 && !this.by.d) {
                            this.bZ();
                        }

                        return true;
                    }
                }

                return false;
            }
            else {
                if (itemstack != null && itemstack == this.bY()) {
                    if (itemstack.b <= 0 && !this.by.d) {
                        this.bZ();
                    }
                    else if (itemstack.b < itemstack1.b && this.by.d) {
                        itemstack.b = itemstack1.b;
                    }
                }

                return true;
            }
        }
    }

    public ItemStack bY() {
        return this.bg.h();
    }

    public void bZ() {
        // CanaryMod: ToolBrokenHook
        if (this.getCanaryHuman().isPlayer() && this.bY() != null) { // Just in case a NPC breaks a tool; and make sure there is an actual item
            CanaryItem tool = this.bY().getCanaryItem();
            tool.setSlot(this.bi.d);
            ToolBrokenHook hook = (ToolBrokenHook)new ToolBrokenHook((Player)getCanaryHuman(), this.bY().getCanaryItem()).call();
            if (hook.getTool().getAmount() > 0) {
                return; // A Plugin may have adjusted the tool back to normal
            }
        }
        //
        this.bg.a(this.bg.c, (ItemStack)null);
    }

    public double am() {
        return -0.35D;
    }

    public void f(Entity entity) {
        if (entity.aE()) {
            if (!entity.l(this)) {
                float f0 = (float)this.a(SharedMonsterAttributes.e).e();
                byte b0 = 0;
                float f1 = 0.0F;

                if (entity instanceof EntityLivingBase) {
                    f1 = EnchantmentHelper.a(this.bz(), ((EntityLivingBase)entity).by());
                }
                else {
                    f1 = EnchantmentHelper.a(this.bz(), EnumCreatureAttribute.UNDEFINED);
                }

                int i0 = b0 + EnchantmentHelper.a((EntityLivingBase)this);

                if (this.ax()) {
                    ++i0;
                }

                if (f0 > 0.0F || f1 > 0.0F) {
                    boolean flag0 = this.O > 0.0F && !this.C && !this.j_() && !this.V() && !this.a(Potion.q) && this.m == null && entity instanceof EntityLivingBase;

                    if (flag0 && f0 > 0.0F) {
                        f0 *= 1.5F;
                    }

                    f0 += f1;
                    boolean flag1 = false;
                    int i1 = EnchantmentHelper.b((EntityLivingBase)this);

                    if (entity instanceof EntityLivingBase && i1 > 0 && !entity.au()) {
                        flag1 = true;
                        entity.e(1);
                    }

                    double d0 = entity.v;
                    double d1 = entity.w;
                    double d2 = entity.x;
                    boolean flag2 = entity.a(DamageSource.a(this), f0);

                    if (flag2) {
                        if (i0 > 0) {
                            entity.g((double)(-MathHelper.a(this.y * 3.1415927F / 180.0F) * (float)i0 * 0.5F), 0.1D, (double)(MathHelper.b(this.y * 3.1415927F / 180.0F) * (float)i0 * 0.5F));
                            this.v *= 0.6D;
                            this.x *= 0.6D;
                            this.d(false);
                        }

                        if (entity instanceof EntityPlayerMP && entity.G) {
                            ((EntityPlayerMP)entity).a.a((Packet)(new S12PacketEntityVelocity(entity)));
                            entity.G = false;
                            entity.v = d0;
                            entity.w = d1;
                            entity.x = d2;
                        }

                        if (flag0) {
                            this.b(entity);
                        }

                        if (f1 > 0.0F) {
                            this.c(entity);
                        }

                        if (f0 >= 18.0F) {
                            this.b((StatBase)AchievementList.F);
                        }

                        this.p(entity);
                        if (entity instanceof EntityLivingBase) {
                            EnchantmentHelper.a((EntityLivingBase)entity, (Entity)this);
                        }

                        EnchantmentHelper.b(this, entity);
                        ItemStack itemstack = this.bY();
                        Object object = entity;

                        if (entity instanceof EntityDragonPart) {
                            IEntityMultiPart ientitymultipart = ((EntityDragonPart)entity).a;

                            if (ientitymultipart instanceof EntityLivingBase) {
                                object = (EntityLivingBase)ientitymultipart;
                            }
                        }

                        if (itemstack != null && object instanceof EntityLivingBase) {
                            itemstack.a((EntityLivingBase)object, this);
                            if (itemstack.b <= 0) {
                                this.bZ();
                            }
                        }

                        if (entity instanceof EntityLivingBase) {
                            this.a(StatList.w, Math.round(f0 * 10.0F));
                            if (i1 > 0) {
                                entity.e(i1 * 4);
                            }
                        }

                        this.a(0.3F);
                    }
                    else if (flag1) {
                        entity.N();
                    }
                }
            }
        }
    }

    public void b(Entity entity) {
    }

    public void c(Entity entity) {
    }

    public void J() {
        super.J();
        this.bh.b(this);
        if (this.bi != null) {
            this.bi.b(this);
        }
    }

    public boolean aj() {
        return !this.bu && super.aj();
    }

    public GameProfile cc() {
        return this.bF;
    }

    public EntityPlayer.EnumStatus a(BlockPos blockpos) {
        if (!this.o.D) {
            if (this.bI() || !this.ai()) {
                return EntityPlayer.EnumStatus.OTHER_PROBLEM;
            }

            if (!this.o.t.d()) {
                return EntityPlayer.EnumStatus.NOT_POSSIBLE_HERE;
            }

            if (this.o.w()) {
                return EntityPlayer.EnumStatus.NOT_POSSIBLE_NOW;
            }

            if (Math.abs(this.s - (double)blockpos.n()) > 3.0D || Math.abs(this.t - (double)blockpos.o()) > 2.0D || Math.abs(this.u - (double)blockpos.p()) > 3.0D) {
                return EntityPlayer.EnumStatus.TOO_FAR_AWAY;
            }

            double d0 = 8.0D;
            double d1 = 5.0D;
            List list = this.o.a(EntityMob.class, new AxisAlignedBB((double)blockpos.n() - d0, (double)blockpos.o() - d1, (double)blockpos.p() - d0, (double)blockpos.n() + d0, (double)blockpos.o() + d1, (double)blockpos.p() + d0));

            if (!list.isEmpty()) {
                return EntityPlayer.EnumStatus.NOT_SAFE;
            }
        }

        if (this.av()) {
            this.a((Entity)null);
        }

        // CanaryMod: BedEnterHook
        if (this.getCanaryEntity() instanceof CanaryPlayer) {
            BedEnterHook beh = (BedEnterHook)new BedEnterHook(((EntityPlayerMP)this).getPlayer(), this.o.getCanaryWorld().getBlockAt(new BlockPosition(blockpos))).call();
            if (beh.isCanceled()) {
                return EnumStatus.OTHER_PROBLEM;
            }
        }
        //

        this.a(0.2F, 0.2F);
        if (this.o.e(blockpos)) {
            EnumFacing enumfacing = (EnumFacing)this.o.p(blockpos).b(BlockDirectional.N);
            float f0 = 0.5F;
            float f1 = 0.5F;

            switch (EntityPlayer.SwitchEnumFacing.a[enumfacing.ordinal()]) {
                case 1:
                    f1 = 0.9F;
                    break;

                case 2:
                    f1 = 0.1F;
                    break;

                case 3:
                    f0 = 0.1F;
                    break;

                case 4:
                    f0 = 0.9F;
            }

            this.a(enumfacing);
            this.b((double)((float)blockpos.n() + f0), (double)((float)blockpos.o() + 0.6875F), (double)((float)blockpos.p() + f1));
        }
        else {
            this.b((double)((float)blockpos.n() + 0.5F), (double)((float)blockpos.o() + 0.6875F), (double)((float)blockpos.p() + 0.5F));
        }

        this.bu = true;
        this.b = 0;
        this.bv = blockpos;
        this.v = this.x = this.w = 0.0D;
        if (!this.o.D) {
            this.o.d();
        }

        return EntityPlayer.EnumStatus.OK;
    }

    private void a(EnumFacing enumfacing) {
        this.bw = 0.0F;
        this.bx = 0.0F;
        switch (EntityPlayer.SwitchEnumFacing.a[enumfacing.ordinal()]) {
            case 1:
                this.bx = -1.8F;
                break;

            case 2:
                this.bx = 1.8F;
                break;

            case 3:
                this.bw = 1.8F;
                break;

            case 4:
                this.bw = -1.8F;
        }
    }

    public void a(boolean flag0, boolean flag1, boolean flag2) {
        this.a(0.6F, 1.8F);
        IBlockState iblockstate = this.o.p(this.bv);

        // CanaryMod: BedLeaveHook
        if (this.getCanaryEntity() instanceof CanaryPlayer) {
            net.canarymod.api.world.blocks.Block bed = this.o.getCanaryWorld().getBlockAt(new BlockPosition(this.bv));

            new BedExitHook(((EntityPlayerMP)this).getPlayer(), bed).call();
        }
        //

        if (this.bv != null && iblockstate.c() == Blocks.C) {
            this.o.a(this.bv, iblockstate.a(BlockBed.b, Boolean.valueOf(false)), 4);
            BlockPos blockpos = BlockBed.a(this.o, this.bv, 0);

            if (blockpos == null) {
                blockpos = this.bv.a();
            }

            this.b((double)((float)blockpos.n() + 0.5F), (double)((float)blockpos.o() + 0.1F), (double)((float)blockpos.p() + 0.5F));
        }

        this.bu = false;
        if (!this.o.D && flag1) {
            this.o.d();
        }

        this.b = flag0 ? 0 : 100;
        if (flag2) {
            this.a(this.bv, false, true);
        }
    }

    private boolean p() {
        return this.o.p(this.bv).c() == Blocks.C;
    }

    // Get bed location or return given blockpos (if spawn is forced)
    public static BlockPos a(World world, BlockPos blockpos, boolean flag0) {
        if (world.p(blockpos).c() != Blocks.C) {
            if (!flag0) {
                return null;
            }
            else {
                Material material = world.p(blockpos).c().r();
                Material material1 = world.p(blockpos.a()).c().r();
                boolean flag1 = !material.a() && !material.d();
                boolean flag2 = !material1.a() && !material1.d();

                return flag1 && flag2 ? blockpos : null;
            }
        }
        else {
            return BlockBed.a(world, blockpos, 0);
        }
    }

    public boolean bI() {
        return this.bu;
    }

    public boolean ce() {
        return this.bu && this.b >= 100;
    }

    public void b(IChatComponent ichatcomponent) {
    }

    public BlockPos cg() {
        return this.c;
    }

    public boolean ch() {
        return this.d;
    }

    // CanaryMod added boolean to signature to handle bed spawn updates cross-worlds
    public void a(BlockPos blockpos, boolean spawnWasForced, boolean updateCanaryLocation) {
        if (blockpos != null) {
            this.c = blockpos;
            this.d = spawnWasForced;
            if (updateCanaryLocation) {
                this.canaryRespawn = new Location(getCanaryWorld(), c.n(), c.o(), c.p(), 0f, 0f);
            }
        }
        else {
            this.c = null;
            this.d = false;
            if (updateCanaryLocation) {
                this.canaryRespawn = null;
            }
        }
    }

    public void b(StatBase statbase) {
        this.a(statbase, 1);
    }

    public void a(StatBase statbase, int i0) {
    }

    public void a(StatBase statbase) {
    }

    public void bE() {
        super.bE();
        this.b(StatList.u);
        if (this.ax()) {
            this.a(0.8F);
        }
        else {
            this.a(0.2F);
        }
    }

    public void g(float f0, float f1) {
        double d0 = this.s;
        double d1 = this.t;
        double d2 = this.u;

        if (this.by.b && this.m == null) {
            double d3 = this.w;
            float f2 = this.aK;

            this.aK = this.by.a() * (float)(this.ax() ? 2 : 1);
            super.g(f0, f1);
            this.w = d3 * 0.6D;
            this.aK = f2;
        }
        else {
            super.g(f0, f1);
        }

        this.k(this.s - d0, this.t - d1, this.u - d2);
    }

    public float bH() {
        return (float)this.a(SharedMonsterAttributes.d).e();
    }

    public void k(double d0, double d1, double d2) {
        if (this.m == null) {
            int i0;

            if (this.a(Material.h)) {
                i0 = Math.round(MathHelper.a(d0 * d0 + d1 * d1 + d2 * d2) * 100.0F);
                if (i0 > 0) {
                    this.a(StatList.p, i0);
                    this.a(0.015F * (float)i0 * 0.01F);
                }
            }
            else if (this.V()) {
                i0 = Math.round(MathHelper.a(d0 * d0 + d2 * d2) * 100.0F);
                if (i0 > 0) {
                    this.a(StatList.l, i0);
                    this.a(0.015F * (float)i0 * 0.01F);
                }
            }
            else if (this.j_()) {
                if (d1 > 0.0D) {
                    this.a(StatList.n, (int)Math.round(d1 * 100.0D));
                }
            }
            else if (this.C) {
                i0 = Math.round(MathHelper.a(d0 * d0 + d2 * d2) * 100.0F);
                if (i0 > 0) {
                    this.a(StatList.i, i0);
                    if (this.ax()) {
                        this.a(StatList.k, i0);
                        this.a(0.099999994F * (float)i0 * 0.01F);
                    }
                    else {
                        if (this.aw()) {
                            this.a(StatList.j, i0);
                        }

                        this.a(0.01F * (float)i0 * 0.01F);
                    }
                }
            }
            else {
                i0 = Math.round(MathHelper.a(d0 * d0 + d2 * d2) * 100.0F);
                if (i0 > 25) {
                    this.a(StatList.o, i0);
                }
            }
        }
    }

    private void l(double d0, double d1, double d2) {
        if (this.m != null) {
            int i0 = Math.round(MathHelper.a(d0 * d0 + d1 * d1 + d2 * d2) * 100.0F);

            if (i0 > 0) {
                if (this.m instanceof EntityMinecart) {
                    this.a(StatList.q, i0);
                    if (this.e == null) {
                        this.e = new BlockPos(this);
                    }
                    else if (this.e.c((double)MathHelper.c(this.s), (double)MathHelper.c(this.t), (double)MathHelper.c(this.u)) >= 1000000.0D) {
                        this.b((StatBase)AchievementList.q);
                    }
                }
                else if (this.m instanceof EntityBoat) {
                    this.a(StatList.r, i0);
                }
                else if (this.m instanceof EntityPig) {
                    this.a(StatList.s, i0);
                }
                else if (this.m instanceof EntityHorse) {
                    this.a(StatList.t, i0);
                }
            }
        }
    }

    public void e(float f0, float f1) {
        if (!this.by.c) {
            if (f0 >= 2.0F) {
                this.a(StatList.m, (int)Math.round((double)f0 * 100.0D));
            }

            super.e(f0, f1);
        }
    }

    protected void X() {
        if (!this.v()) {
            super.X();
        }
    }

    protected String n(int i0) {
        return i0 > 4 ? "game.player.hurt.fall.big" : "game.player.hurt.fall.small";
    }

    public void a(EntityLivingBase entitylivingbase) {
        if (entitylivingbase instanceof IMob) {
            this.b((StatBase)AchievementList.s);
        }

        EntityList.EntityEggInfo entitylist_entityegginfo = (EntityList.EntityEggInfo)EntityList.a.get(Integer.valueOf(EntityList.a(entitylivingbase)));

        if (entitylist_entityegginfo != null) {
            this.b(entitylist_entityegginfo.d);
        }
    }

    public void aB() {
        if (!this.by.b) {
            super.aB();
        }
    }

    public ItemStack q(int i0) {
        return this.bg.e(i0);
    }

    public void u(int i0) {
        this.s(i0);
        int i1 = Integer.MAX_VALUE - this.bA;

        if (i0 > i1) {
            i0 = i1;
        }

        this.bB += (float)i0 / (float)this.cj();

        for (this.bA += i0; this.bB >= 1.0F; this.bB /= (float)this.cj()) {
            this.bB = (this.bB - 1.0F) * (float)this.cj();
            this.a(1);
        }
    }

    public int ci() {
        return this.f;
    }

    public void b(int i0) {
        this.bz -= i0;
        if (this.bz < 0) {
            this.bz = 0;
            this.bB = 0.0F;
            this.bA = 0;
        }

        this.f = this.V.nextInt();
    }

    public void a(int i0) {
        // CanaryMod: LevelUpHook
        new LevelUpHook(((EntityPlayerMP)this).getPlayer()).call();
        //
        this.bz += i0;
        if (this.bz < 0) {
            this.bz = 0;
            this.bB = 0.0F;
            this.bA = 0;
        }

        if (i0 > 0 && this.bz % 5 == 0 && (float)this.i < (float)this.W - 100.0F) {
            float f0 = this.bz > 30 ? 1.0F : (float)this.bz / 30.0F;

            this.o.a((Entity)this, "random.levelup", f0 * 0.75F, 1.0F);
            this.i = this.W;
        }
    }

    public int cj() {
        return this.bz >= 30 ? 112 + (this.bz - 30) * 9 : (this.bz >= 15 ? 37 + (this.bz - 15) * 5 : 7 + this.bz * 2);
    }

    public void a(float f0) {
        if (!this.by.a) {
            if (!this.o.D) {
                this.bj.a(f0);
            }
        }
    }

    public FoodStats ck() {
        return this.bj;
    }

    public boolean j(boolean flag0) {
        return (flag0 || this.bj.c()) && !this.by.a;
    }

    public boolean cl() {
        return this.bm() > 0.0F && this.bm() < this.bt();
    }

    public void a(ItemStack itemstack, int i0) {
        if (itemstack != this.g) {
            this.g = itemstack;
            this.h = i0;
            if (!this.o.D) {
                this.f(true);
            }
        }
    }

    public boolean cm() {
        return this.by.e;
    }

    public boolean a(BlockPos blockpos, EnumFacing enumfacing, ItemStack itemstack) {
        if (this.by.e) {
            return true;
        }
        else if (itemstack == null) {
            return false;
        }
        else {
            BlockPos blockpos1 = blockpos.a(enumfacing.d());
            Block block = this.o.p(blockpos1).c();

            return itemstack.d(block) || itemstack.x();
        }
    }

    protected int b(EntityPlayer entityplayer) {
        if (this.o.Q().b("keepInventory")) {
            return 0;
        }
        else {
            int i0 = this.bz * 7;

            return i0 > 100 ? 100 : i0;
        }
    }

    protected boolean ba() {
        return true;
    }

    public void a(EntityPlayer entityplayer, boolean flag0) {
        if (flag0) {
            this.bg.b(entityplayer.bg);
            this.h(entityplayer.bm());
            this.bj = entityplayer.bj;
            this.bz = entityplayer.bz;
            this.bA = entityplayer.bA;
            this.bB = entityplayer.bB;
            this.r(entityplayer.bW());
            this.an = entityplayer.an;
        }
        else if (this.o.Q().b("keepInventory")) {
            this.bg.b(entityplayer.bg);
            this.bz = entityplayer.bz;
            this.bA = entityplayer.bA;
            this.bB = entityplayer.bB;
            this.r(entityplayer.bW());
        }

        this.a = entityplayer.a;
        this.H().b(10, Byte.valueOf(entityplayer.H().a(10)));
    }

    protected boolean r_() {
        return !this.by.b;
    }

    public void t() {
    }

    public void a(WorldSettings.GameType worldsettings_gametype) {
    }

    public String d_() {
        return this.bF.getName();
    }

    public InventoryEnderChest cn() {
        return this.a;
    }

    public ItemStack p(int i0) {
        return i0 == 0 ? this.bg.h() : this.bg.b[i0 - 1];
    }

    public ItemStack bz() {
        return this.bg.h();
    }

    public void c(int i0, ItemStack itemstack) {
        this.bg.b[i0] = itemstack;
    }

    public abstract boolean v();

    public ItemStack[] at() {
        return this.bg.b;
    }

    public boolean aK() {
        return !this.by.b;
    }

    public Scoreboard co() {
        //return this.o.Z();
        // return Canary Master Scoreboard
        return ((CanaryScoreboard)Canary.scoreboards().getScoreboard()).getHandle();
    }

    public Team bN() {
        return this.co().h(this.d_());
    }

    public IChatComponent e_() {
        ChatComponentText chatcomponenttext = new ChatComponentText(ScorePlayerTeam.a(this.bN(), this.d_()));

        chatcomponenttext.b().a(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg " + this.d_() + " "));
        chatcomponenttext.b().a(this.aP());
        chatcomponenttext.b().a(this.d_());
        return chatcomponenttext;
    }

    public float aR() {
        float f0 = 1.62F;

        if (this.bI()) {
            f0 = 0.2F;
        }

        if (this.aw()) {
            f0 -= 0.08F;
        }

        return f0;
    }

    public void l(float f0) {
        if (f0 < 0.0F) {
            f0 = 0.0F;
        }

        this.H().b(17, Float.valueOf(f0));
    }

    public float bM() {
        return this.H().d(17);
    }

    public static UUID a(GameProfile gameprofile) {
        UUID uuid = gameprofile.getId();

        if (uuid == null) {
            uuid = b(gameprofile.getName());
        }

        return uuid;
    }

    public static UUID b(String s0) {
        return UUID.nameUUIDFromBytes(("OfflinePlayer:" + s0).getBytes(Charsets.UTF_8));
    }

    public boolean a(LockCode lockcode) {
        if (lockcode.a()) {
            return true;
        }
        else {
            ItemStack itemstack = this.bY();

            return itemstack != null && itemstack.s() ? itemstack.q().equals(lockcode.b()) : false;
        }
    }

    public boolean t_() {
        // canaryMod: reroute for the right world
        //return MinecraftServer.M().c[0].Q().b("sendCommandFeedback");
        return o.Q().b("sendCommandFeedback");
    }

    public boolean d(int i0, ItemStack itemstack) {
        if (i0 >= 0 && i0 < this.bg.a.length) {
            this.bg.a(i0, itemstack);
            return true;
        }
        else {
            int i1 = i0 - 100;
            int i2;

            if (i1 >= 0 && i1 < this.bg.b.length) {
                i2 = i1 + 1;
                if (itemstack != null && itemstack.b() != null) {
                    if (itemstack.b() instanceof ItemArmor) {
                        if (EntityLiving.c(itemstack) != i2) {
                            return false;
                        }
                    }
                    else if (i2 != 4 || itemstack.b() != Items.bX && !(itemstack.b() instanceof ItemBlock)) {
                        return false;
                    }
                }

                this.bg.a(i1 + this.bg.a.length, itemstack);
                return true;
            }
            else {
                i2 = i0 - 200;
                if (i2 >= 0 && i2 < this.a.n_()) {
                    this.a.a(i2, itemstack);
                    return true;
                }
                else {
                    return false;
                }
            }
        }
    }

    public static enum EnumChatVisibility {

        FULL("FULL", 0, 0, "options.chat.visibility.full"),
        SYSTEM("SYSTEM", 1, 1, "options.chat.visibility.system"),
        HIDDEN("HIDDEN", 2, 2, "options.chat.visibility.hidden");
        private static final EntityPlayer.EnumChatVisibility[] d = new EntityPlayer.EnumChatVisibility[values().length];
        private final int e;
        private final String f;

        private static final EntityPlayer.EnumChatVisibility[] $VALUES = new EntityPlayer.EnumChatVisibility[]{ FULL, SYSTEM, HIDDEN };

        private EnumChatVisibility(String p_i45323_1_, int p_i45323_2_, int p_i45323_3_, String p_i45323_4_) {
            this.e = p_i45323_3_;
            this.f = p_i45323_4_;
        }

        public int a() {
            return this.e;
        }

        public static EntityPlayer.EnumChatVisibility a(int p_a_0_) {
            return d[p_a_0_ % d.length];
        }

        static {
            EntityPlayer.EnumChatVisibility[] aentityplayer_enumchatvisibility = values();
            int i0 = aentityplayer_enumchatvisibility.length;

            for (int i1 = 0; i1 < i0; ++i1) {
                EntityPlayer.EnumChatVisibility entityplayer_enumchatvisibility = aentityplayer_enumchatvisibility[i1];

                d[entityplayer_enumchatvisibility.e] = entityplayer_enumchatvisibility;
            }
        }
    }

    public static enum EnumStatus {

        OK("OK", 0),
        NOT_POSSIBLE_HERE("NOT_POSSIBLE_HERE", 1),
        NOT_POSSIBLE_NOW("NOT_POSSIBLE_NOW", 2),
        TOO_FAR_AWAY("TOO_FAR_AWAY", 3),
        OTHER_PROBLEM("OTHER_PROBLEM", 4),
        NOT_SAFE("NOT_SAFE", 5);

        private static final EntityPlayer.EnumStatus[] $VALUES = new EntityPlayer.EnumStatus[]{ OK, NOT_POSSIBLE_HERE, NOT_POSSIBLE_NOW, TOO_FAR_AWAY, OTHER_PROBLEM, NOT_SAFE };

        private EnumStatus(String p_i1751_1_, int p_i1751_2_) {
        }

    }

    static final class SwitchEnumFacing {

        static final int[] a = new int[EnumFacing.values().length];

        static {
            try {
                a[EnumFacing.SOUTH.ordinal()] = 1;
            }
            catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                a[EnumFacing.NORTH.ordinal()] = 2;
            }
            catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                a[EnumFacing.WEST.ordinal()] = 3;
            }
            catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                a[EnumFacing.EAST.ordinal()] = 4;
            }
            catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }
        }
    }

    // CanaryMod
    // Start: Custom XP methods
    public void addXP(int amount) {
        this.u(amount);
        updateXP();
    }

    public void removeXP(int rXp) {
        if (rXp > this.bA) { // Don't go below 0
            rXp = this.bA;
        }

        this.bB -= (float)rXp / (float)this.cj();

        // Inverse of for loop in this.t(int)
        for (this.bA -= rXp; this.bB < 0.0F; this.bB = this.bB / this.cj() + 1.0F) {
            this.bB *= this.cj();
            this.a(-1);
        }
        updateXP();
    }

    public void setXP(int i) {
        if (i < this.bz) {
            this.removeXP(this.bz - i);
        }
        else {
            this.u(i - this.bz);
        }
        updateXP();
    }

    public void recalculateXP() {
        this.bB = this.bA / (float)this.cj();
        this.bz = 0;

        while (this.bB >= 1.0F) {
            this.bB = (this.bB - 1.0F) * this.cj();
            this.bC++;
            this.bB /= this.cj();
        }

        if (this instanceof EntityPlayerMP) {
            updateLevels();
            updateXP();
        }
    }

    private void updateXP() {
        CanaryPlayer player = ((CanaryPlayer)getCanaryEntity());
        S1FPacketSetExperience packet = new S1FPacketSetExperience(this.bB, this.bA, this.bz);

        player.sendPacket(new CanaryPacket(packet));
    }

    private void updateLevels() {
        CanaryPlayer player = ((CanaryPlayer)getCanaryEntity());
        S06PacketUpdateHealth packet = new S06PacketUpdateHealth(((CanaryPlayer)getCanaryEntity()).getHealth(), ((CanaryPlayer)getCanaryEntity()).getHunger(), ((CanaryPlayer)getCanaryEntity()).getExhaustionLevel());

        player.sendPacket(new CanaryPacket(packet));
    }

    // End: Custom XP methods
    // Start: Inventory getters
    public PlayerInventory getPlayerInventory() {
        return new CanaryPlayerInventory(bg);
    }

    public EnderChestInventory getEnderChestInventory() {
        return new CanaryEnderChestInventory(a, getCanaryHuman());
    }

    // End: Inventory getters

    public String getDisplayName() {
        if(displayName == null && metadata != null) {
            if(metadata.containsKey("displayName") && !metadata.getString("displayName").isEmpty()) {
                displayName = IChatComponent.Serializer.a(metadata.getString("displayName"));
                if (metadata.containsKey("CustomName")){
                    metadata.remove("CustomName"); // No sense keeping this attached
                }
            }
            else if (metadata.containsKey("CustomName") && !metadata.getString("CustomName").isEmpty()){
                setDisplayName(metadata.getString("CustomName"));
            }
        }
        return displayName != null ? displayName.e() : null;
    }

    public void setDisplayName(String name) {
       this.setDisplayNameComponent(name != null && !name.isEmpty() ? new ChatComponentText(name) : null);
    }

    public void setDisplayNameComponent(IChatComponent iChatComponent){
        this.displayName = iChatComponent;
        String serial = "";
        if(iChatComponent != null){
            serial = IChatComponent.Serializer.a(iChatComponent);
        }
        metadata.put("displayName", serial);
    }

    /**
     * Returns a respawn location for this player.
     * Null if there is no explicitly set respawn location
     *
     * @return
     */
    public Location getRespawnLocation() {
        if (canaryRespawn != null) {
            return this.canaryRespawn;
        }
        return null;
    }

    public boolean isBedObstructed(boolean withForcedSpawn) {
        if (this.canaryRespawn != null) {
            return EntityPlayer.a(((CanaryWorld)canaryRespawn.getWorld()).getHandle(),
                    new BlockPos(canaryRespawn.getBlockX(), canaryRespawn.getBlockY(), canaryRespawn.getBlockZ()),
                    withForcedSpawn) == null;
        }
        else {
            return false; // not obstructed as it does not exist
        }
    }

    public void setRespawnLocation(Location l) {
        if (l == null) {
            c = null;
            canaryRespawn = null;
            return;
        }
        c = new BlockPos(l.getBlockX(), l.getBlockY(), l.getBlockZ());
        canaryRespawn = l;
    }

    public String getFirstJoined() {
        return metadata.getString("FirstJoin");
    }

    public long getTimePlayed() {
        return metadata.getLong("TimePlayed") + (ToolBox.getUnixTimestamp() - currentSessionStart);
    }

    public String getPreviousIP() {
        return metadata.getString("PreviousIP");
    }

    public void saveMeta() {
        metadata.put("TimePlayed", metadata.getLong("TimePlayed") + (ToolBox.getUnixTimestamp() - currentSessionStart));
        currentSessionStart = ToolBox.getUnixTimestamp(); // When saving, reset the start time so there isnt a duplicate addition of time stored
    }

    public CanaryHuman getCanaryHuman() {
        return (CanaryHuman)entity;
    }

    public CanaryHuman getCanaryEntity() {
        if (this.entity == null || !(this.entity instanceof CanaryHuman)) {
            // Set the proper wrapper as needed
            this.entity = new CanaryHuman(this) {
                @Override
                public String getFqName() {
                    return "GenericHuman";
                }

                @Override
                public EntityType getEntityType() {
                    return EntityType.GENERIC_LIVING;
                }

                @Override
                public EntityPlayer getHandle() {
                    return (EntityPlayer)entity;
                }
            };
        }
        return (CanaryHuman)this.entity;
    }

    public void initializeNewMeta() {
        if (metadata == null) {
            metadata = new CanaryCompoundTag();
            metadata.put("FirstJoin", DateUtils.longToDateTime(System.currentTimeMillis()));
            metadata.put("LastJoin", DateUtils.longToDateTime(System.currentTimeMillis()));
            metadata.put("TimePlayed", 1L); // Initialize to 1
        }
    }

    /**
     * Gets if sleeping is ignored
     */
    public boolean isSleepIgnored(){
        return this.sleepIgnored;
    }

    /**
     * Sets if sleeping is ignored
     */
    public void setSleepIgnored(boolean ignored){
        this.sleepIgnored = ignored;
    }
}
