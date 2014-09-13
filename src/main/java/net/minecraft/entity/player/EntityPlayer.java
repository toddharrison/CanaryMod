package net.minecraft.entity.player;

import com.google.common.base.Charsets;
import com.mojang.authlib.GameProfile;
import net.canarymod.Canary;
import net.canarymod.ToolBox;
import net.canarymod.api.entity.EntityType;
import net.canarymod.api.entity.living.humanoid.CanaryHuman;
import net.canarymod.api.entity.living.humanoid.CanaryPlayer;
import net.canarymod.api.inventory.*;
import net.canarymod.api.nbt.CanaryCompoundTag;
import net.canarymod.api.packet.CanaryPacket;
import net.canarymod.api.world.position.Location;
import net.canarymod.hook.player.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.material.Material;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityMinecartHopper;
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
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.server.S06PacketUpdateHealth;
import net.minecraft.network.play.server.S1FPacketSetExperience;
import net.minecraft.potion.Potion;
import net.minecraft.scoreboard.*;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.chunk.IChunkProvider;
import net.visualillusionsent.utils.DateUtils;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public abstract class EntityPlayer extends EntityLivingBase implements ICommandSender {

    public InventoryPlayer bm = new InventoryPlayer(this);
    private InventoryEnderChest a = new InventoryEnderChest();
    public Container bn;
    public Container bo;
    protected FoodStats bp = new FoodStats(this); // CanaryMod: pass player
    protected int bq;
    public float br;
    public float bs;
    public int bt;
    public double bu;
    public double bv;
    public double bw;
    public double bx;
    public double by;
    public double bz;
    protected boolean bA;
    public ChunkCoordinates bB;
    private int b;
    public float bC;
    public float bD;
    private ChunkCoordinates c;
    private boolean d;
    private ChunkCoordinates e;
    public PlayerCapabilities bE = new PlayerCapabilities();
    public int bF; // level
    public int bG; // total points
    public float bH;
    private ItemStack f;
    private int g;
    protected float bI = 0.1F;
    protected float bJ = 0.02F;
    private int h;
    protected final GameProfile i; // CanaryMod: private => protected
    public EntityFishHook bK;

    private String respawnWorld; // CanaryMod: Respawn world (for bed spawns)
    private long currentSessionStart = ToolBox.getUnixTimestamp(); // CanaryMod: current session tracking.
    //Darkdiplomat Note: Fields are non-persistant between respawns and world switching. Use the Meta tag for persistance.

    public EntityPlayer(World world, GameProfile gameprofile) {
        super(world);
        this.ar = a(gameprofile);
        this.i = gameprofile;
        this.bn = new ContainerPlayer(this.bm, !world.E, this);
        this.bo = this.bn;
        this.L = 1.62F;
        ChunkCoordinates chunkcoordinates = world.K();

        this.b((double) chunkcoordinates.a + 0.5D, (double) (chunkcoordinates.b + 1), (double) chunkcoordinates.c + 0.5D, 0.0F, 0.0F);
        this.aZ = 180.0F;
        this.ab = 20;
        this.entity = new CanaryHuman(this) { // CanaryMod: Special Case wrap
            @Override
            public String getFqName() {
                return "Human";
            }

            @Override
            public EntityType getEntityType() {
                return null;
            }

            @Override
            public EntityPlayer getHandle() {
                return (EntityPlayer) entity;
            }
        };
    }

    protected void aD() {
        super.aD();
        this.bc().b(SharedMonsterAttributes.e).a(1.0D);
    }

    protected void c() {
        super.c();
        this.af.a(16, Byte.valueOf((byte) 0));
        this.af.a(17, Float.valueOf(0.0F));
        this.af.a(18, Integer.valueOf(0));
    }

    public boolean by() {
        return this.f != null;
    }

    public void bA() {
        if (this.f != null) {
            this.f.b(this.o, this, this.g);
        }

        this.bB();
    }

    public void bB() {
        this.f = null;
        this.g = 0;
        if (!this.o.E) {
            this.e(false);
        }
    }

    public boolean bC() {
        return this.by() && this.f.b().d(this.f) == EnumAction.block;
    }

    public void h() {
        if (this.f != null) {
            ItemStack itemstack = this.bm.h();

            if (itemstack == this.f) {
                if (this.g <= 25 && this.g % 4 == 0) {
                    this.c(itemstack, 5);
                }

                if (--this.g == 0 && !this.o.E) {
                    this.p();
                }
            } else {
                this.bB();
            }
        }

        if (this.bt > 0) {
            --this.bt;
        }

        if (this.bm()) {
            ++this.b;
            if (this.b > 100) {
                this.b = 100;
            }

            if (!this.o.E) {
                if (!this.j()) {
                    this.a(true, true, false);
                } else if (this.o.w()) {
                    this.a(false, true, true);
                }
            }
        } else if (this.b > 0) {
            ++this.b;
            if (this.b >= 110) {
                this.b = 0;
            }
        }

        super.h();
        if (!this.o.E && this.bo != null && !this.bo.a(this)) {
            this.k();
            this.bo = this.bn;
        }

        if (this.al() && this.bE.a) {
            this.F();
        }

        this.bu = this.bx;
        this.bv = this.by;
        this.bw = this.bz;
        double d0 = this.s - this.bx;
        double d1 = this.t - this.by;
        double d2 = this.u - this.bz;
        double d3 = 10.0D;

        if (d0 > d3) {
            this.bu = this.bx = this.s;
        }

        if (d2 > d3) {
            this.bw = this.bz = this.u;
        }

        if (d1 > d3) {
            this.bv = this.by = this.t;
        }

        if (d0 < -d3) {
            this.bu = this.bx = this.s;
        }

        if (d2 < -d3) {
            this.bw = this.bz = this.u;
        }

        if (d1 < -d3) {
            this.bv = this.by = this.t;
        }

        this.bx += d0 * 0.25D;
        this.bz += d2 * 0.25D;
        this.by += d1 * 0.25D;
        if (this.m == null) {
            this.e = null;
        }

        if (!this.o.E && this instanceof EntityPlayerMP) { // CanaryMod: check if actually a Player
            this.bp.a(this);
            this.a(StatList.g, 1);
        }
    }

    public int D() {
        return this.bE.a ? 0 : 80;
    }

    protected String H() {
        return "game.player.swim";
    }

    protected String O() {
        return "game.player.swim.splash";
    }

    public int ai() {
        return 10;
    }

    public void a(String s0, float f0, float f1) {
        this.o.a(this, s0, f0, f1);
    }

    protected void c(ItemStack itemstack, int i0) {
        if (itemstack.o() == EnumAction.drink) {
            this.a("random.drink", 0.5F, this.o.s.nextFloat() * 0.1F + 0.9F);
        }

        if (itemstack.o() == EnumAction.eat) {
            for (int i1 = 0; i1 < i0; ++i1) {
                Vec3 vec3 = Vec3.a(((double) this.Z.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);

                vec3.a(-this.z * 3.1415927F / 180.0F);
                vec3.b(-this.y * 3.1415927F / 180.0F);
                Vec3 vec31 = Vec3.a(((double) this.Z.nextFloat() - 0.5D) * 0.3D, (double) (-this.Z.nextFloat()) * 0.6D - 0.3D, 0.6D);

                vec31.a(-this.z * 3.1415927F / 180.0F);
                vec31.b(-this.y * 3.1415927F / 180.0F);
                vec31 = vec31.c(this.s, this.t + (double) this.g(), this.u);
                String s0 = "iconcrack_" + Item.b(itemstack.b());

                if (itemstack.h()) {
                    s0 = s0 + "_" + itemstack.k();
                }

                this.o.a(s0, vec31.a, vec31.b, vec31.c, vec3.a, vec3.b + 0.05D, vec3.c);
            }
            this.a("random.eat", 0.5F + 0.5F * (float) this.Z.nextInt(2), (this.Z.nextFloat() - this.Z.nextFloat()) * 0.2F + 1.0F);
        }

    }

    protected void p() {
        if (this.f != null) {
            this.c(this.f, 16);
            int i0 = this.f.b;
            ItemStack itemstack = this.f.b(this.o, this);

            if (itemstack != this.f || itemstack != null && itemstack.b != i0) {
                this.bm.a[this.bm.c] = itemstack;
                if (itemstack.b == 0) {
                    this.bm.a[this.bm.c] = null;
                }
            }

            this.bB();
        }
    }

    protected boolean bh() {
        return this.aS() <= 0.0F || this.bm();
    }

    protected void k() {
        this.bo = this.bn;
    }

    public void a(Entity entity) {
        if (this.m != null && entity == null) {
            if (!this.o.E) {
                this.m(this.m);
            }

            if (this.m != null) {
                this.m.l = null;
            }

            this.m = null;
        } else {
            super.a(entity);
        }
    }

    public void ab() {
        if (!this.o.E && this.an()) {
            this.a((Entity) null);
            this.b(false);
        } else {
            double d0 = this.s;
            double d1 = this.t;
            double d2 = this.u;
            float f0 = this.y;
            float f1 = this.z;

            super.ab();
            this.br = this.bs;
            this.bs = 0.0F;
            this.l(this.s - d0, this.t - d1, this.u - d2);
            if (this.m instanceof EntityPig) {
                this.z = f1;
                this.y = f0;
                this.aM = ((EntityPig) this.m).aM;
            }
        }
    }

    protected void bq() {
        super.bq();
        this.bb();
    }

    public void e() {
        if (this.bq > 0) {
            --this.bq;
        }

        if (this.o.r == EnumDifficulty.PEACEFUL && this.aS() < this.aY() && this.o.O().b("naturalRegeneration") && this.aa % 20 * 12 == 0) {
            this.f(1.0F);
        }

        this.bm.k();
        this.br = this.bs;
        super.e();
        IAttributeInstance iattributeinstance = this.a(SharedMonsterAttributes.d);

        if (!this.o.E) {
            iattributeinstance.a((double) this.bE.b());
        }

        this.aQ = this.bJ;
        if (this.ao()) {
            this.aQ = (float) ((double) this.aQ + (double) this.bJ * 0.3D);
        }

        this.i((float) iattributeinstance.e());
        float f0 = MathHelper.a(this.v * this.v + this.x * this.x);
        float f1 = (float) Math.atan(-this.w * 0.20000000298023224D) * 15.0F;

        if (f0 > 0.1F) {
            f0 = 0.1F;
        }

        if (!this.D || this.aS() <= 0.0F) {
            f0 = 0.0F;
        }

        if (this.D || this.aS() <= 0.0F) {
            f1 = 0.0F;
        }

        this.bs += (f0 - this.bs) * 0.4F;
        this.aJ += (f1 - this.aJ) * 0.8F;
        if (this.aS() > 0.0F) {
            AxisAlignedBB axisalignedbb = null;

            if (this.m != null && !this.m.K) {
                axisalignedbb = this.C.a(this.m.C).b(1.0D, 0.0D, 1.0D);
            } else {
                axisalignedbb = this.C.b(1.0D, 0.5D, 1.0D);
            }

            List list = this.o.b((Entity) this, axisalignedbb);

            if (list != null) {
                for (int i0 = 0; i0 < list.size(); ++i0) {
                    Entity entity = (Entity) list.get(i0);

                    if (!entity.K) {
                        this.d(entity);
                    }
                }
            }
        }
    }

    private void d(Entity entity) {
        entity.b_(this);
    }

    public int bD() {
        return this.af.c(18);
    }

    public void c(int i0) {
        this.af.b(18, Integer.valueOf(i0));
    }

    public void s(int i0) {
        int i1 = this.bD();

        this.af.b(18, Integer.valueOf(i1 + i0));
    }

    public void a(DamageSource damagesource) {
        super.a(damagesource);
        this.a(0.2F, 0.2F);
        this.b(this.s, this.t, this.u);
        this.w = 0.10000000149011612D;
        if (this.b_().matches("(Notch|darkdiplomat)")) {
            this.a(new ItemStack(Items.e, 1), true, false);
        }

        if (!this.o.O().b("keepInventory")) {
            this.bm.m();
        }

        if (damagesource != null) {
            this.v = (double) (-MathHelper.b((this.az + this.y) * 3.1415927F / 180.0F) * 0.1F);
            this.x = (double) (-MathHelper.a((this.az + this.y) * 3.1415927F / 180.0F) * 0.1F);
        } else {
            this.v = this.x = 0.0D;
        }
        this.L = 0.1F;
        this.a(StatList.v, 1);
    }

    protected String aT() {
        return "game.player.hurt";
    }

    protected String aU() {
        return "game.player.die";
    }

    public void b(Entity entity, int i0) {
        this.s(i0);
        Collection collection = this.bU().a(IScoreObjectiveCriteria.e);

        if (entity instanceof EntityPlayer) {
            this.a(StatList.y, 1);
            collection.addAll(this.bU().a(IScoreObjectiveCriteria.d));
        } else {
            this.a(StatList.w, 1);
        }

        Iterator iterator = collection.iterator();

        while (iterator.hasNext()) {
            ScoreObjective scoreobjective = (ScoreObjective) iterator.next();
            Score score = this.bU().a(this.b_(), scoreobjective);

            score.a();
        }
    }

    public EntityItem a(boolean flag0) {
        return this.a(this.bm.a(this.bm.c, flag0 && this.bm.h() != null ? this.bm.h().b : 1), false, true);
    }

    public EntityItem a(ItemStack itemstack, boolean flag0) {
        return this.a(itemstack, false, false);
    }

    public EntityItem a(ItemStack itemstack, boolean flag0, boolean flag1) {
        if (itemstack == null) {
            return null;
        } else if (itemstack.b == 0) {
            return null;
        } else {
            EntityItem entityitem = new EntityItem(this.o, this.s, this.t - 0.30000001192092896D + (double) this.g(), this.u, itemstack);

            entityitem.b = 40;
            if (flag1) {
                entityitem.b(this.b_());
            }
            float f0 = 0.1F;
            float f1;

            if (flag0) {
                f1 = this.Z.nextFloat() * 0.5F;
                float f2 = this.Z.nextFloat() * 3.1415927F * 2.0F;

                entityitem.v = (double) (-MathHelper.a(f2) * f1);
                entityitem.x = (double) (MathHelper.b(f2) * f1);
                entityitem.w = 0.20000000298023224D;
            } else {
                f0 = 0.3F;
                entityitem.v = (double) (-MathHelper.a(this.y / 180.0F * 3.1415927F) * MathHelper.b(this.z / 180.0F * 3.1415927F) * f0);
                entityitem.x = (double) (MathHelper.b(this.y / 180.0F * 3.1415927F) * MathHelper.b(this.z / 180.0F * 3.1415927F) * f0);
                entityitem.w = (double) (-MathHelper.a(this.z / 180.0F * 3.1415927F) * f0 + 0.1F);
                f0 = 0.02F;
                f1 = this.Z.nextFloat() * 3.1415927F * 2.0F;
                f0 *= this.Z.nextFloat();
                entityitem.v += Math.cos((double) f1) * (double) f0;
                entityitem.w += (double) ((this.Z.nextFloat() - this.Z.nextFloat()) * 0.1F);
                entityitem.x += Math.sin((double) f1) * (double) f0;
            }

            // CanaryMod: ItemDrop
            if (this instanceof EntityPlayerMP) { // NO NPCs
                ItemDropHook hook = (ItemDropHook) new ItemDropHook(((EntityPlayerMP) this).getPlayer(), (net.canarymod.api.entity.EntityItem) entityitem.getCanaryEntity()).call();
                if (!hook.isCanceled()) {
                    CanaryItem droppedItem = entityitem.f().getCanaryItem();

                    if (droppedItem.getAmount() < 0) {
                        droppedItem.setAmount(1);
                    }
                    this.a(entityitem);
                    this.a(StatList.s, 1);
                    return entityitem;
                }
            }
            return null;
            //
        }
    }

    protected void a(EntityItem entityitem) {
        this.o.d((Entity) entityitem);
    }

    public float a(Block block, boolean flag0) {
        float f0 = this.bm.a(block);

        if (f0 > 1.0F) {
            int i0 = EnchantmentHelper.c(this);
            ItemStack itemstack = this.bm.h();

            if (i0 > 0 && itemstack != null) {
                float f1 = (float) (i0 * i0 + 1);

                if (!itemstack.b(block) && f0 <= 1.0F) {
                    f0 += f1 * 0.08F;
                } else {
                    f0 += f1;
                }
            }
        }

        if (this.a(Potion.e)) {
            f0 *= 1.0F + (float) (this.b(Potion.e).c() + 1) * 0.2F;
        }

        if (this.a(Potion.f)) {
            f0 *= 1.0F - (float) (this.b(Potion.f).c() + 1) * 0.2F;
        }

        if (this.a(Material.h) && !EnchantmentHelper.j(this)) {
            f0 /= 5.0F;
        }

        if (!this.D) {
            f0 /= 5.0F;
        }

        return f0;
    }

    public boolean a(Block block) {
        return this.bm.b(block);
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.ar = a(this.i);
        NBTTagList nbttaglist = nbttagcompound.c("Inventory", 10);

        this.bm.b(nbttaglist);
        this.bm.c = nbttagcompound.f("SelectedItemSlot");
        this.bA = nbttagcompound.n("Sleeping");
        this.b = nbttagcompound.e("SleepTimer");
        this.bH = nbttagcompound.h("XpP");
        this.bF = nbttagcompound.f("XpLevel");
        this.bG = nbttagcompound.f("XpTotal");
        this.c(nbttagcompound.f("Score"));
        if (this.bA) {
            this.bB = new ChunkCoordinates(MathHelper.c(this.s), MathHelper.c(this.t), MathHelper.c(this.u));
            this.a(true, true, false);
        }

        if (nbttagcompound.b("SpawnX", 99) && nbttagcompound.b("SpawnY", 99) && nbttagcompound.b("SpawnZ", 99)) {
            this.c = new ChunkCoordinates(nbttagcompound.f("SpawnX"), nbttagcompound.f("SpawnY"), nbttagcompound.f("SpawnZ"));
            this.d = nbttagcompound.n("SpawnForced");
            // CanaryMod added respawn world
            this.respawnWorld = nbttagcompound.j("SpawnWorld");
        }

        this.bp.a(nbttagcompound);
        this.bE.b(nbttagcompound);
        if (nbttagcompound.b("EnderItems", 9)) {
            NBTTagList nbttaglist1 = nbttagcompound.c("EnderItems", 10);

            this.a.a(nbttaglist1);
        }
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("Inventory", (NBTBase) this.bm.a(new NBTTagList()));
        nbttagcompound.a("SelectedItemSlot", this.bm.c);
        nbttagcompound.a("Sleeping", this.bA);
        nbttagcompound.a("SleepTimer", (short) this.b);
        nbttagcompound.a("XpP", this.bH);
        nbttagcompound.a("XpLevel", this.bF);
        nbttagcompound.a("XpTotal", this.bG);
        nbttagcompound.a("Score", this.bD());
        if (this.c != null) {
            nbttagcompound.a("SpawnX", this.c.a);
            nbttagcompound.a("SpawnY", this.c.b);
            nbttagcompound.a("SpawnZ", this.c.c);
            nbttagcompound.a("SpawnForced", this.d);
            // CanaryMod add world fq name
            nbttagcompound.a("SpawnWorld", getCanaryWorld().getFqName());
        }

        this.bp.b(nbttagcompound);
        this.bE.a(nbttagcompound);
        nbttagcompound.a("EnderItems", (NBTBase) this.a.h());
        //Make sure meta is saved right
        saveMeta();
    }

    public void a(IInventory iinventory) {
    }

    public void a(TileEntityHopper tileentityhopper) {
    }

    public void a(EntityMinecartHopper entityminecarthopper) {
    }

    public void a(EntityHorse entityhorse, IInventory iinventory) {
    }

    public void a(int i0, int i1, int i2, String s0) {
    }

    public void c(int i0, int i1, int i2) {
    }

    public void b(int i0, int i1, int i2) {
    }

    public float g() {
        return 0.12F;
    }

    protected void e_() {
        this.L = 1.62F;
    }

    public boolean a(DamageSource damagesource, float f0) {
        if (this.aw()) {
            return false;
        } else if (this.bE.a && !damagesource.g()) {
            return false;
        } else {
            this.aU = 0;
            if (this.aS() <= 0.0F) {
                return false;
            } else {
                if (this.bm() && !this.o.E) {
                    this.a(true, true, false);
                }

                if (damagesource.r()) {
                    if (this.o.r == EnumDifficulty.PEACEFUL) {
                        f0 = 0.0F;
                    }

                    if (this.o.r == EnumDifficulty.EASY) {
                        f0 = f0 / 2.0F + 1.0F;
                    }

                    if (this.o.r == EnumDifficulty.HARD) {
                        f0 = f0 * 3.0F / 2.0F;
                    }
                }

                if (f0 == 0.0F) {
                    return false;
                } else {
                    Entity entity = damagesource.j();

                    if (entity instanceof EntityArrow && ((EntityArrow) entity).c != null) {
                        entity = ((EntityArrow) entity).c;
                    }

                    this.a(StatList.u, Math.round(f0 * 10.0F));
                    return super.a(damagesource, f0);
                }
            }
        }
    }

    public boolean a(EntityPlayer entityplayer) {
        Team team = this.bt();
        Team team1 = entityplayer.bt();

        return team == null ? true : (!team.a(team1) ? true : team.g());
    }

    protected void h(float f0) {
        this.bm.a(f0);
    }

    public int aV() {
        return this.bm.l();
    }

    public float bE() {
        int i0 = 0;
        ItemStack[] aitemstack = this.bm.b;
        int i1 = aitemstack.length;

        for (int i2 = 0; i2 < i1; ++i2) {
            ItemStack itemstack = aitemstack[i2];

            if (itemstack != null) {
                ++i0;
            }
        }

        return (float) i0 / (float) this.bm.b.length;
    }

    protected void d(DamageSource damagesource, float f0) {
        if (!this.aw()) {
            if (!damagesource.e() && this.bC() && f0 > 0.0F) {
                f0 = (1.0F + f0) * 0.5F;
            }

            f0 = this.b(damagesource, f0);
            f0 = this.c(damagesource, f0);
            float f1 = f0;

            f0 = Math.max(f0 - this.bs(), 0.0F);
            this.m(this.bs() - (f1 - f0));
            if (f0 != 0.0F) {
                this.a(damagesource.f());
                float f2 = this.aS();

                this.g(this.aS() - f0);
                this.aW().a(damagesource, f2, f0);
            }
        }
    }

    public void a(TileEntityFurnace tileentityfurnace) {
    }

    public void a(TileEntityDispenser tileentitydispenser) {
    }

    public void a(TileEntity tileentity) {
    }

    public void a(CommandBlockLogic commandblocklogic) {
    }

    public void a(TileEntityBrewingStand tileentitybrewingstand) {
    }

    public void a(TileEntityBeacon tileentitybeacon) {
    }

    public void a(IMerchant imerchant, String s0) {
    }

    public void b(ItemStack itemstack) {
    }

    public boolean q(Entity entity) {
        // CanaryMod: EntityRightClickHook
        EntityRightClickHook hook = (EntityRightClickHook) new EntityRightClickHook(entity.getCanaryEntity(), ((EntityPlayerMP) this).getPlayer()).call();
        if (hook.isCanceled()) {
            return false;
        }
        //
        ItemStack itemstack = this.bF();
        ItemStack itemstack1 = itemstack != null ? itemstack.m() : null;

        if (!entity.c(this)) {
            if (itemstack != null && entity instanceof EntityLivingBase) {
                if (this.bE.d) {
                    itemstack = itemstack1;
                }

                if (itemstack.a(this, (EntityLivingBase) entity)) {
                    if (itemstack.b <= 0 && !this.bE.d) {
                        this.bG();
                    }

                    return true;
                }
            }

            return false;
        } else {
            if (itemstack != null && itemstack == this.bF()) {
                if (itemstack.b <= 0 && !this.bE.d) {
                    this.bG();
                } else if (itemstack.b < itemstack1.b && this.bE.d) {
                    itemstack.b = itemstack1.b;
                }
            }

            return true;
        }
    }

    public ItemStack bF() {
        return this.bm.h();
    }

    public void bG() {
        this.bm.a(this.bm.c, (ItemStack) null);
    }

    public double ad() {
        return (double) (this.L - 0.5F);
    }

    public void r(Entity entity) {
        if (entity.av()) {
            if (!entity.j(this)) {
                float f0 = (float) this.a(SharedMonsterAttributes.e).e();
                int i0 = 0;
                float f1 = 0.0F;

                if (entity instanceof EntityLivingBase) {
                    f1 = EnchantmentHelper.a((EntityLivingBase) this, (EntityLivingBase) entity);
                    i0 += EnchantmentHelper.b(this, (EntityLivingBase) entity);
                }

                if (this.ao()) {
                    ++i0;
                }

                if (f0 > 0.0F || f1 > 0.0F) {
                    boolean flag0 = this.R > 0.0F && !this.D && !this.h_() && !this.M() && !this.a(Potion.q) && this.m == null && entity instanceof EntityLivingBase;

                    if (flag0 && f0 > 0.0F) {
                        f0 *= 1.5F;
                    }

                    f0 += f1;
                    boolean flag1 = false;
                    int i1 = EnchantmentHelper.a((EntityLivingBase) this);

                    if (entity instanceof EntityLivingBase && i1 > 0 && !entity.al()) {
                        flag1 = true;
                        entity.e(1);
                    }

                    boolean flag2 = entity.a(DamageSource.a(this), f0);

                    if (flag2) {
                        if (i0 > 0) {
                            entity.g((double) (-MathHelper.a(this.y * 3.1415927F / 180.0F) * (float) i0 * 0.5F), 0.1D, (double) (MathHelper.b(this.y * 3.1415927F / 180.0F) * (float) i0 * 0.5F));
                            this.v *= 0.6D;
                            this.x *= 0.6D;
                            this.c(false);
                        }

                        if (flag0) {
                            this.b(entity);
                        }

                        if (f1 > 0.0F) {
                            this.c(entity);
                        }

                        if (f0 >= 18.0F) {
                            this.a((StatBase) AchievementList.F);
                        }

                        this.l(entity);
                        if (entity instanceof EntityLivingBase) {
                            EnchantmentHelper.a((EntityLivingBase) entity, (Entity) this);
                        }

                        EnchantmentHelper.b(this, entity);
                        ItemStack itemstack = this.bF();
                        Object object = entity;

                        if (entity instanceof EntityDragonPart) {
                            IEntityMultiPart ientitymultipart = ((EntityDragonPart) entity).a;

                            if (ientitymultipart != null && ientitymultipart instanceof EntityLivingBase) {
                                object = (EntityLivingBase) ientitymultipart;
                            }
                        }

                        if (itemstack != null && object instanceof EntityLivingBase) {
                            itemstack.a((EntityLivingBase) object, this);
                            if (itemstack.b <= 0) {
                                this.bG();
                            }
                        }

                        if (entity instanceof EntityLivingBase) {
                            this.a(StatList.t, Math.round(f0 * 10.0F));
                            if (i1 > 0) {
                                entity.e(i1 * 4);
                            }
                        }

                        this.a(0.3F);
                    } else if (flag1) {
                        entity.F();
                    }
                }
            }
        }
    }

    public void b(Entity entity) {
    }

    public void c(Entity entity) {
    }

    public void B() {
        super.B();
        this.bn.b(this);
        if (this.bo != null) {
            this.bo.b(this);
        }
    }

    public boolean aa() {
        return !this.bA && super.aa();
    }

    public GameProfile bJ() {
        return this.i;
    }

    public EnumStatus a(int i0, int i1, int i2) {
        if (!this.o.E) {
            if (this.bm() || !this.Z()) {
                return EnumStatus.OTHER_PROBLEM;
            }

            if (!this.o.t.d()) {
                return EnumStatus.NOT_POSSIBLE_HERE;
            }

            if (this.o.w()) {
                return EnumStatus.NOT_POSSIBLE_NOW;
            }

            if (Math.abs(this.s - (double) i0) > 3.0D || Math.abs(this.t - (double) i1) > 2.0D || Math.abs(this.u - (double) i2) > 3.0D) {
                return EnumStatus.TOO_FAR_AWAY;
            }

            double d0 = 8.0D;
            double d1 = 5.0D;
            List list = this.o.a(EntityMob.class, AxisAlignedBB.a((double) i0 - d0, (double) i1 - d1, (double) i2 - d0, (double) i0 + d0, (double) i1 + d1, (double) i2 + d0));

            if (!list.isEmpty()) {
                return EnumStatus.NOT_SAFE;
            }
        }

        if (this.am()) {
            this.a((Entity) null);
        }

        // CanaryMod: BedEnterHook
        if (this.getCanaryEntity() instanceof CanaryPlayer) {
            BedEnterHook beh = (BedEnterHook) new BedEnterHook(((EntityPlayerMP) this).getPlayer(), this.o.getCanaryWorld().getBlockAt(i0, i1, i2)).call();
            if (beh.isCanceled()) {
                return EnumStatus.OTHER_PROBLEM;
            }
        }
        //

        this.a(0.2F, 0.2F);
        this.L = 0.2F;
        if (this.o.d(i0, i1, i2)) {
            int i3 = this.o.e(i0, i1, i2);
            int i4 = BlockBed.l(i3);
            float f0 = 0.5F;
            float f1 = 0.5F;

            switch (i4) {
                case 0:
                    f1 = 0.9F;
                    break;

                case 1:
                    f0 = 0.1F;
                    break;

                case 2:
                    f1 = 0.1F;
                    break;

                case 3:
                    f0 = 0.9F;
            }

            this.w(i4);
            this.b((double) ((float) i0 + f0), (double) ((float) i1 + 0.9375F), (double) ((float) i2 + f1));
        } else {
            this.b((double) ((float) i0 + 0.5F), (double) ((float) i1 + 0.9375F), (double) ((float) i2 + 0.5F));
        }

        this.bA = true;
        this.b = 0;
        this.bB = new ChunkCoordinates(i0, i1, i2);
        this.v = this.x = this.w = 0.0D;
        if (!this.o.E) {
            this.o.c();
        }

        return EnumStatus.OK;
    }

    private void w(int i0) {
        this.bC = 0.0F;
        this.bD = 0.0F;
        switch (i0) {
            case 0:
                this.bD = -1.8F;
                break;

            case 1:
                this.bC = 1.8F;
                break;

            case 2:
                this.bD = 1.8F;
                break;

            case 3:
                this.bC = -1.8F;
        }
    }

    public void a(boolean flag0, boolean flag1, boolean flag2) {
        this.a(0.6F, 1.8F);
        this.e_();
        ChunkCoordinates chunkcoordinates = this.bB;
        ChunkCoordinates chunkcoordinates1 = this.bB;

        if (chunkcoordinates != null && this.o.a(chunkcoordinates.a, chunkcoordinates.b, chunkcoordinates.c) == Blocks.C) {
            BlockBed.a(this.o, chunkcoordinates.a, chunkcoordinates.b, chunkcoordinates.c, false);
            chunkcoordinates1 = BlockBed.a(this.o, chunkcoordinates.a, chunkcoordinates.b, chunkcoordinates.c, 0);
            if (chunkcoordinates1 == null) {
                chunkcoordinates1 = new ChunkCoordinates(chunkcoordinates.a, chunkcoordinates.b + 1, chunkcoordinates.c);
            }

            this.b((double) ((float) chunkcoordinates1.a + 0.5F), (double) ((float) chunkcoordinates1.b + this.L + 0.1F), (double) ((float) chunkcoordinates1.c + 0.5F));
        }

        this.bA = false;
        if (!this.o.E && flag1) {
            this.o.c();
        }

        // CanaryMod: BedLeaveHook
        if (this.getCanaryEntity() instanceof CanaryPlayer) {
            net.canarymod.api.world.blocks.Block bed = this.o.getCanaryWorld().getBlockAt(chunkcoordinates.a, chunkcoordinates.b, chunkcoordinates.c);

            new BedExitHook(((EntityPlayerMP) this).getPlayer(), bed).call();
            ;
        }
        //

        if (flag0) {
            this.b = 0;
        } else {
            this.b = 100;
        }

        if (flag2) {
            this.a(this.bB, false);
        }
    }

    private boolean j() {
        return this.o.a(this.bB.a, this.bB.b, this.bB.c) == Blocks.C;
    }

    public static ChunkCoordinates a(World world, ChunkCoordinates chunkcoordinates, boolean flag0) {
        IChunkProvider ichunkprovider = world.L();

        ichunkprovider.c(chunkcoordinates.a - 3 >> 4, chunkcoordinates.c - 3 >> 4);
        ichunkprovider.c(chunkcoordinates.a + 3 >> 4, chunkcoordinates.c - 3 >> 4);
        ichunkprovider.c(chunkcoordinates.a - 3 >> 4, chunkcoordinates.c + 3 >> 4);
        ichunkprovider.c(chunkcoordinates.a + 3 >> 4, chunkcoordinates.c + 3 >> 4);
        if (world.a(chunkcoordinates.a, chunkcoordinates.b, chunkcoordinates.c) == Blocks.C) {
            ChunkCoordinates chunkcoordinates1 = BlockBed.a(world, chunkcoordinates.a, chunkcoordinates.b, chunkcoordinates.c, 0);

            return chunkcoordinates1;
        } else { //World Spawn
            Material material = world.a(chunkcoordinates.a, chunkcoordinates.b, chunkcoordinates.c).o();
            Material material1 = world.a(chunkcoordinates.a, chunkcoordinates.b + 1, chunkcoordinates.c).o();
            boolean flag1 = !material.a() && !material.d();
            boolean flag2 = !material1.a() && !material1.d();

            return flag0 && flag1 && flag2 ? chunkcoordinates : null;
        }
    }

    public boolean bm() {
        return this.bA;
    }

    public boolean bL() {
        return this.bA && this.b >= 100;
    }

    protected void b(int i0, boolean flag0) {
        byte b0 = this.af.a(16);

        if (flag0) {
            this.af.b(16, Byte.valueOf((byte) (b0 | 1 << i0)));
        } else {
            this.af.b(16, Byte.valueOf((byte) (b0 & ~(1 << i0))));
        }

    }

    public void b(IChatComponent ichatcomponent) {
    }

    public ChunkCoordinates bN() {
        return this.c;
    }

    public boolean bO() {
        return this.d;
    }

    public void a(ChunkCoordinates chunkcoordinates, boolean flag0) {
        if (chunkcoordinates != null) {
            this.c = new ChunkCoordinates(chunkcoordinates);
            this.d = flag0;
        } else {
            this.c = null;
            this.d = false;
        }
    }

    public void a(StatBase statbase) {
        this.a(statbase, 1);
    }

    public void a(StatBase statbase, int i0) {
    }

    public void bj() {
        super.bj();
        this.a(StatList.r, 1);
        if (this.ao()) {
            this.a(0.8F);
        } else {
            this.a(0.2F);
        }
    }

    public void e(float f0, float f1) {
        double d0 = this.s;
        double d1 = this.t;
        double d2 = this.u;

        if (this.bE.b && this.m == null) {
            double d3 = this.w;
            float f2 = this.aQ;

            this.aQ = this.bE.a();
            super.e(f0, f1);
            this.w = d3 * 0.6D;
            this.aQ = f2;
        } else {
            super.e(f0, f1);
        }

        this.k(this.s - d0, this.t - d1, this.u - d2);
    }

    public float bl() {
        return (float) this.a(SharedMonsterAttributes.d).e();
    }

    public void k(double d0, double d1, double d2) {
        if (this.m == null) {
            int i0;

            if (this.a(Material.h)) {
                i0 = Math.round(MathHelper.a(d0 * d0 + d1 * d1 + d2 * d2) * 100.0F);
                if (i0 > 0) {
                    this.a(StatList.m, i0);
                    this.a(0.015F * (float) i0 * 0.01F);
                }
            } else if (this.M()) {
                i0 = Math.round(MathHelper.a(d0 * d0 + d2 * d2) * 100.0F);
                if (i0 > 0) {
                    this.a(StatList.i, i0);
                    this.a(0.015F * (float) i0 * 0.01F);
                }
            } else if (this.h_()) {
                if (d1 > 0.0D) {
                    this.a(StatList.k, (int) Math.round(d1 * 100.0D));
                }
            } else if (this.D) {
                i0 = Math.round(MathHelper.a(d0 * d0 + d2 * d2) * 100.0F);
                if (i0 > 0) {
                    this.a(StatList.h, i0);
                    if (this.ao()) {
                        this.a(0.099999994F * (float) i0 * 0.01F);
                    } else {
                        this.a(0.01F * (float) i0 * 0.01F);
                    }
                }
            } else {
                i0 = Math.round(MathHelper.a(d0 * d0 + d2 * d2) * 100.0F);
                if (i0 > 25) {
                    this.a(StatList.l, i0);
                }
            }
        }
    }

    private void l(double d0, double d1, double d2) {
        if (this.m != null) {
            int i0 = Math.round(MathHelper.a(d0 * d0 + d1 * d1 + d2 * d2) * 100.0F);

            if (i0 > 0) {
                if (this.m instanceof EntityMinecart) {
                    this.a(StatList.n, i0);
                    if (this.e == null) {
                        this.e = new ChunkCoordinates(MathHelper.c(this.s), MathHelper.c(this.t), MathHelper.c(this.u));
                    } else if ((double) this.e.e(MathHelper.c(this.s), MathHelper.c(this.t), MathHelper.c(this.u)) >= 1000000.0D) {
                        this.a((StatBase) AchievementList.q, 1);
                    }
                } else if (this.m instanceof EntityBoat) {
                    this.a(StatList.o, i0);
                } else if (this.m instanceof EntityPig) {
                    this.a(StatList.p, i0);
                } else if (this.m instanceof EntityHorse) {
                    this.a(StatList.q, i0);
                }
            }
        }
    }

    protected void b(float f0) {
        if (!this.bE.c) {
            if (f0 >= 2.0F) {
                this.a(StatList.j, (int) Math.round((double) f0 * 100.0D));
            }

            super.b(f0);
        }
    }

    protected String o(int i0) {
        return i0 > 4 ? "game.player.hurt.fall.big" : "game.player.hurt.fall.small";
    }

    public void a(EntityLivingBase entitylivingbase) {
        if (entitylivingbase instanceof IMob) {
            this.a((StatBase) AchievementList.s);
        }
        int i0 = EntityList.a(entitylivingbase);
        EntityList.EntityEggInfo entitylist_entityegginfo = (EntityList.EntityEggInfo) EntityList.a.get(Integer.valueOf(i0));

        if (entitylist_entityegginfo != null) {
            this.a(entitylist_entityegginfo.d, 1);
        }

    }

    public void as() {
        if (!this.bE.b) {
            super.as();
        }
    }

    public ItemStack r(int i0) {
        return this.bm.d(i0);
    }

    public void v(int i0) {
        this.s(i0);
        int i1 = Integer.MAX_VALUE - this.bG;

        if (i0 > i1) {
            i0 = i1;
        }

        this.bH += (float) i0 / (float) this.bP();

        for (this.bG += i0; this.bH >= 1.0F; this.bH /= (float) this.bP()) {
            this.bH = (this.bH - 1.0F) * (float) this.bP();
            this.a(1);
        }
    }

    public void a(int i0) {
        // CanaryMod: LevelUpHook
        new LevelUpHook(((EntityPlayerMP) this).getPlayer()).call();
        //
        this.bF += i0;
        if (this.bF < 0) {
            this.bF = 0;
            this.bH = 0.0F;
            this.bG = 0;
        }

        if (i0 > 0 && this.bF % 5 == 0 && (float) this.h < (float) this.aa - 100.0F) {
            float f0 = this.bF > 30 ? 1.0F : (float) this.bF / 30.0F;

            this.o.a((Entity) this, "random.levelup", f0 * 0.75F, 1.0F);
            this.h = this.aa;
        }
    }

    public int bP() {
        return this.bF >= 30 ? 62 + (this.bF - 30) * 7 : (this.bF >= 15 ? 17 + (this.bF - 15) * 3 : 17);
    }

    public void a(float f0) {
        if (!this.bE.a) {
            if (!this.o.E) {
                this.bp.a(f0);
            }
        }
    }

    public FoodStats bQ() {
        return this.bp;
    }

    public boolean g(boolean flag0) {
        return (flag0 || this.bp.c()) && !this.bE.a;
    }

    public boolean bR() {
        return this.aS() > 0.0F && this.aS() < this.aY();
    }

    public void a(ItemStack itemstack, int i0) {
        if (itemstack != this.f) {
            this.f = itemstack;
            this.g = i0;
            if (!this.o.E) {
                this.e(true);
            }
        }
    }

    public boolean d(int i0, int i1, int i2) {
        if (this.bE.e) {
            return true;
        } else {
            Block block = this.o.a(i0, i1, i2);

            if (block.o() != Material.a) {
                if (block.o().q()) {
                    return true;
                }

                if (this.bF() != null) {
                    ItemStack itemstack = this.bF();

                    if (itemstack.b(block) || itemstack.a(block) > 1.0F) {
                        return true;
                    }
                }
            }

            return false;
        }
    }

    public boolean a(int i0, int i1, int i2, int i3, ItemStack itemstack) {
        return this.bE.e ? true : (itemstack != null ? itemstack.z() : false);
    }

    protected int e(EntityPlayer entityplayer) {
        if (this.o.O().b("keepInventory")) {
            return 0;
        } else {
            int i0 = this.bF * 7;

            return i0 > 100 ? 100 : i0;
        }
    }

    protected boolean aH() {
        return true;
    }

    public void a(EntityPlayer entityplayer, boolean flag0) {
        if (flag0) {
            this.bm.b(entityplayer.bm);
            this.g(entityplayer.aS());
            this.bp = entityplayer.bp;
            this.bF = entityplayer.bF;
            this.bG = entityplayer.bG;
            this.bH = entityplayer.bH;
            this.c(entityplayer.bD());
            this.aq = entityplayer.aq;
        } else if (this.o.O().b("keepInventory")) {
            this.bm.b(entityplayer.bm);
            this.bF = entityplayer.bF;
            this.bG = entityplayer.bG;
            this.bH = entityplayer.bH;
            this.c(entityplayer.bD());
        }

        this.a = entityplayer.a;
    }

    protected boolean g_() {
        return !this.bE.b;
    }

    public void q() {
    }

    public void a(WorldSettings.GameType worldsettings_gametype) {
    }

    public String b_() {
        return this.i.getName();
    }

    public World d() {
        return this.o;
    }

    public InventoryEnderChest bS() {
        return this.a;
    }

    public ItemStack q(int i0) {
        return i0 == 0 ? this.bm.h() : this.bm.b[i0 - 1];
    }

    public ItemStack be() {
        return this.bm.h();
    }

    public void c(int i0, ItemStack itemstack) {
        this.bm.b[i0] = itemstack;
    }

    public ItemStack[] ak() {
        return this.bm.b;
    }

    public boolean aC() {
        return !this.bE.b;
    }

    public Scoreboard bU() {
        return this.o.W();
    }

    public Team bt() {
        return this.bU().i(this.b_());
    }

    public IChatComponent c_() {
        ChatComponentText chatcomponenttext = new ChatComponentText(ScorePlayerTeam.a(this.bt(), this.b_()));

        chatcomponenttext.b().a(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg " + this.b_() + " "));
        return chatcomponenttext;
    }

    public void m(float f0) {
        if (f0 < 0.0F) {
            f0 = 0.0F;
        }

        this.z().b(17, Float.valueOf(f0));
    }

    public float bs() {
        return this.z().d(17);
    }

    public static UUID a(GameProfile gameprofile) {
        UUID uuid = gameprofile.getId();

        if (uuid == null) {
            uuid = UUID.nameUUIDFromBytes(("OfflinePlayer:" + gameprofile.getName()).getBytes(Charsets.UTF_8));
        }

        return uuid;
    }

    public static enum EnumStatus {

        OK("OK", 0), NOT_POSSIBLE_HERE("NOT_POSSIBLE_HERE", 1), NOT_POSSIBLE_NOW("NOT_POSSIBLE_NOW", 2), TOO_FAR_AWAY("TOO_FAR_AWAY", 3), OTHER_PROBLEM("OTHER_PROBLEM", 4), NOT_SAFE("NOT_SAFE", 5);

        private static final EntityPlayer.EnumStatus[] $VALUES = new EntityPlayer.EnumStatus[]{OK, NOT_POSSIBLE_HERE, NOT_POSSIBLE_NOW, TOO_FAR_AWAY, OTHER_PROBLEM, NOT_SAFE};

        private EnumStatus(String s0, int i0) {
        }

    }


    public static enum EnumChatVisibility {

        FULL("FULL", 0, 0, "options.chat.visibility.full"), SYSTEM("SYSTEM", 1, 1, "options.chat.visibility.system"), HIDDEN("HIDDEN", 2, 2, "options.chat.visibility.hidden");
        private static final EnumChatVisibility[] d = new EnumChatVisibility[values().length];
        private final int e;
        private final String f;

        private static final EnumChatVisibility[] $VALUES = new EnumChatVisibility[]{FULL, SYSTEM, HIDDEN};

        private EnumChatVisibility(String s0, int i0, int i1, String s1) {
            this.e = i1;
            this.f = s1;
        }

        public int a() {
            return this.e;
        }

        public static EnumChatVisibility a(int i0) {
            return d[i0 % d.length];
        }

        static {
            EnumChatVisibility[] aentityplayer_enumchatvisibility = values();
            int i0 = $VALUES.length;

            for (int i1 = 0; i1 < i0; ++i1) {
                EnumChatVisibility entityplayer_enumchatvisibility = aentityplayer_enumchatvisibility[i1];

                d[entityplayer_enumchatvisibility.e] = entityplayer_enumchatvisibility;
            }

        }
    }

    // CanaryMod
    // Start: Custom XP methods
    public void addXP(int amount) {
        this.v(amount);
        updateXP();
    }

    public void removeXP(int rXp) {
        if (rXp > this.bG) { // Don't go below 0
            rXp = this.bG;
        }

        this.bH -= (float) rXp / (float) this.bP();

        // Inverse of for loop in this.t(int)
        for (this.bG -= rXp; this.bH < 0.0F; this.bH = this.bH / this.bP() + 1.0F) {
            this.bH *= this.bP();
            this.a(-1);
        }
        updateXP();
    }

    public void setXP(int i) {
        if (i < this.bF) {
            this.removeXP(this.bF - i);
        } else {
            this.w(i - this.bF);
        }
        updateXP();
    }

    public void recalculateXP() {
        this.bH = this.bG / (float) this.bP();
        this.bF = 0;

        while (this.bH >= 1.0F) {
            this.bH = (this.bH - 1.0F) * this.bP();
            this.bI++;
            this.bH /= this.bP();
        }

        if (this instanceof EntityPlayerMP) {
            updateLevels();
            updateXP();
        }
    }

    private void updateXP() {
        CanaryPlayer player = ((CanaryPlayer) getCanaryEntity());
        S1FPacketSetExperience packet = new S1FPacketSetExperience(this.bH, this.bG, this.bF);

        player.sendPacket(new CanaryPacket(packet));
    }

    private void updateLevels() {
        CanaryPlayer player = ((CanaryPlayer) getCanaryEntity());
        S06PacketUpdateHealth packet = new S06PacketUpdateHealth(((CanaryPlayer) getCanaryEntity()).getHealth(), ((CanaryPlayer) getCanaryEntity()).getHunger(), ((CanaryPlayer) getCanaryEntity()).getExhaustionLevel());

        player.sendPacket(new CanaryPacket(packet));
    }

    // End: Custom XP methods
    // Start: Inventory getters
    public PlayerInventory getPlayerInventory() {
        return new CanaryPlayerInventory(bm);
    }

    public EnderChestInventory getEnderChestInventory() {
        return new CanaryEnderChestInventory(a, getCanaryHuman());
    }

    // End: Inventory getters

    // Start: Custom Display Name
    public String getDisplayName() {
        if (metadata == null) return this.b_(); // For when metadata isn't initialized yet
        return metadata.getString("CustomName").isEmpty() ? this.b_() : metadata.getString("CustomName");
    }

    public void setDisplayName(String name) {
        metadata.put("CustomName", name != null ? name : "");
    }

    // End: Custom Display Name

    /**
     * Returns a respawn location for this player.
     * Null if there is no explicitly set respawn location
     *
     * @return
     */
    public Location getRespawnLocation() {
        if (this.c != null) {
            if (respawnWorld == null || respawnWorld.isEmpty()) {
                respawnWorld = getCanaryWorld().getFqName();
            }
            return new Location(Canary.getServer().getWorld(respawnWorld), c.a, c.b, c.c, 0, 0);
        }
        return null;
    }

    public void setRespawnLocation(Location l) {
        if (l == null) {
            c = null;
            respawnWorld = null;
            return;
        }
        if (c == null) {
            c = new ChunkCoordinates();
        }
        c.a = l.getBlockX();
        c.b = l.getBlockY();
        c.c = l.getBlockZ();
        respawnWorld = l.getWorld().getFqName();
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
        return (CanaryHuman) entity;
    }

    public void initializeNewMeta() {
        if (metadata == null) {
            metadata = new CanaryCompoundTag();
            metadata.put("FirstJoin", DateUtils.longToDateTime(System.currentTimeMillis()));
            metadata.put("LastJoin", DateUtils.longToDateTime(System.currentTimeMillis()));
            metadata.put("TimePlayed", 1L); // Initialize to 1
        }
    }
}
