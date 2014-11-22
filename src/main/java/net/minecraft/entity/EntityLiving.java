package net.minecraft.entity;

import net.canarymod.hook.entity.EntityDespawnHook;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityBodyHelper;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityJumpHelper;
import net.minecraft.entity.ai.EntityLookHelper;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.ai.EntitySenses;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S1BPacketEntityAttach;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public abstract class EntityLiving extends EntityLivingBase {

    public int a_;
    protected int b_;
    private EntityLookHelper a;
    protected EntityMoveHelper f;
    protected EntityJumpHelper g;
    private EntityBodyHelper b;
    protected PathNavigate h;
    protected final EntityAITasks i;
    protected final EntityAITasks bg;
    private EntityLivingBase c;
    private EntitySenses bi;
    private ItemStack[] bj = new ItemStack[5];
    protected float[] bh = new float[5];
    private boolean bk;
    private boolean bl;
    private boolean bm;
    private Entity bn;
    private NBTTagCompound bo;
   
    public EntityLiving(World world) {
        super(world);
        this.i = new EntityAITasks(world != null && world.B != null ? world.B : null);
        this.bg = new EntityAITasks(world != null && world.B != null ? world.B : null);
        this.a = new EntityLookHelper(this);
        this.f = new EntityMoveHelper(this);
        this.g = new EntityJumpHelper(this);
        this.b = new EntityBodyHelper(this);
        this.h = this.b(world);
        this.bi = new EntitySenses(this);

        for (int i0 = 0; i0 < this.bh.length; ++i0) {
            this.bh[i0] = 0.085F;
        }

    }

    protected void aW() {
        super.aW();
        this.bx().b(SharedMonsterAttributes.b).a(16.0D);
    }

    protected PathNavigate b(World world) {
        return new PathNavigateGround(this, world);
    }

    public EntityLookHelper p() {
        return this.a;
    }

    public EntityMoveHelper q() {
        return this.f;
    }

    public EntityJumpHelper r() {
        return this.g;
    }

    public PathNavigate s() {
        return this.h;
    }

    public EntitySenses t() {
        return this.bi;
    }

    public EntityLivingBase u() {
        return this.c;
    }

    public void d(EntityLivingBase entitylivingbase) {
        this.c = entitylivingbase;
    }

    public boolean a(Class oclass0) {
        return oclass0 != EntityGhast.class;
    }

    public void v() {}

    protected void h() {
        super.h();
        this.ac.a(15, Byte.valueOf((byte) 0));
    }

    public int w() {
        return 80;
    }

    public void x() {
        String s0 = this.z();

        if (s0 != null) {
            this.a(s0, this.bA(), this.bB());
        }

    }

    public void K() {
        super.K();
        this.o.B.a("mobBaseTick");
        if (this.ai() && this.V.nextInt(1000) < this.a_++) {
            this.a_ = -this.w();
            this.x();
        }

        this.o.B.b();
    }

    protected int b(EntityPlayer entityplayer) {
        if (this.b_ > 0) {
            int i0 = this.b_;
            ItemStack[] aitemstack = this.at();

            for (int i1 = 0; i1 < aitemstack.length; ++i1) {
                if (aitemstack[i1] != null && this.bh[i1] <= 1.0F) {
                    i0 += 1 + this.V.nextInt(3);
                }
            }

            return i0;
        } else {
            return this.b_;
        }
    }

    public void y() {
        if (this.o.D) {
            for (int i0 = 0; i0 < 20; ++i0) {
                double d0 = this.V.nextGaussian() * 0.02D;
                double d1 = this.V.nextGaussian() * 0.02D;
                double d2 = this.V.nextGaussian() * 0.02D;
                double d3 = 10.0D;

                this.o.a(EnumParticleTypes.EXPLOSION_NORMAL, this.s + (double) (this.V.nextFloat() * this.J * 2.0F) - (double) this.J - d0 * d3, this.t + (double) (this.V.nextFloat() * this.K) - d1 * d3, this.u + (double) (this.V.nextFloat() * this.J * 2.0F) - (double) this.J - d2 * d3, d0, d1, d2, new int[0]);
            }
        } else {
            this.o.a((Entity) this, (byte) 20);
        }

    }

    public void s_() {
        super.s_();
        if (!this.o.D) {
            this.bZ();
        }

    }

    protected float h(float f0, float f1) {
        this.b.a();
        return f1;
    }

    protected String z() {
        return null;
    }

    protected Item A() {
        return null;
    }

    protected void b(boolean flag0, int i0) {
        Item item = this.A();

        if (item != null) {
            int i1 = this.V.nextInt(3);

            if (i0 > 0) {
                i1 += this.V.nextInt(i0 + 1);
            }

            for (int i2 = 0; i2 < i1; ++i2) {
                this.a(item, 1);
            }
        }

    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("CanPickUpLoot", this.bX());
        nbttagcompound.a("PersistenceRequired", this.bl);
        NBTTagList nbttaglist = new NBTTagList();

        NBTTagCompound nbttagcompound1;

        for (int i0 = 0; i0 < this.bj.length; ++i0) {
            nbttagcompound1 = new NBTTagCompound();
            if (this.bj[i0] != null) {
                this.bj[i0].b(nbttagcompound1);
            }

            nbttaglist.a((NBTBase) nbttagcompound1);
        }

        nbttagcompound.a("Equipment", (NBTBase) nbttaglist);
        NBTTagList nbttaglist1 = new NBTTagList();

        for (int i1 = 0; i1 < this.bh.length; ++i1) {
            nbttaglist1.a((NBTBase) (new NBTTagFloat(this.bh[i1])));
        }

        nbttagcompound.a("DropChances", (NBTBase) nbttaglist1);
        nbttagcompound.a("Leashed", this.bm);
        if (this.bn != null) {
            nbttagcompound1 = new NBTTagCompound();
            if (this.bn instanceof EntityLivingBase) {
                nbttagcompound1.a("UUIDMost", this.bn.aJ().getMostSignificantBits());
                nbttagcompound1.a("UUIDLeast", this.bn.aJ().getLeastSignificantBits());
            } else if (this.bn instanceof EntityHanging) {
                BlockPos blockpos = ((EntityHanging) this.bn).n();

                nbttagcompound1.a("X", blockpos.n());
                nbttagcompound1.a("Y", blockpos.o());
                nbttagcompound1.a("Z", blockpos.p());
            }

            nbttagcompound.a("Leash", (NBTBase) nbttagcompound1);
        }

        if (this.cd()) {
            nbttagcompound.a("NoAI", this.cd());
        }

    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        if (nbttagcompound.b("CanPickUpLoot", 1)) {
            this.j(nbttagcompound.n("CanPickUpLoot"));
        }

        this.bl = nbttagcompound.n("PersistenceRequired");
        NBTTagList nbttaglist;
        int i0;

        if (nbttagcompound.b("Equipment", 9)) {
            nbttaglist = nbttagcompound.c("Equipment", 10);

            for (i0 = 0; i0 < this.bj.length; ++i0) {
                this.bj[i0] = ItemStack.a(nbttaglist.b(i0));
            }
        }

        if (nbttagcompound.b("DropChances", 9)) {
            nbttaglist = nbttagcompound.c("DropChances", 5);

            for (i0 = 0; i0 < nbttaglist.c(); ++i0) {
                this.bh[i0] = nbttaglist.e(i0);
            }
        }

        this.bm = nbttagcompound.n("Leashed");
        if (this.bm && nbttagcompound.b("Leash", 10)) {
            this.bo = nbttagcompound.m("Leash");
        }

        this.k(nbttagcompound.n("NoAI"));
    }

    public void m(float f0) {
        this.aY = f0;
    }

    public void j(float f0) {
        super.j(f0);
        this.m(f0);
    }

    public void m() {
        super.m();
        this.o.B.a("looting");
        if (!this.o.D && this.bX() && !this.aN && this.o.Q().b("mobGriefing")) {
            List list = this.o.a(EntityItem.class, this.aQ().b(1.0D, 0.0D, 1.0D));
            Iterator iterator = list.iterator();

            while (iterator.hasNext()) {
                EntityItem entityitem = (EntityItem) iterator.next();

                if (!entityitem.I && entityitem.l() != null && !entityitem.s()) {
                    this.a(entityitem);
                }
            }
        }

        this.o.B.b();
    }

    protected void a(EntityItem entityitem) {
        ItemStack itemstack = entityitem.l();
        int i0 = c(itemstack);

        if (i0 > -1) {
            boolean flag0 = true;
            ItemStack itemstack1 = this.p(i0);

            if (itemstack1 != null) {
                if (i0 == 0) {
                    if (itemstack.b() instanceof ItemSword && !(itemstack1.b() instanceof ItemSword)) {
                        flag0 = true;
                    } else if (itemstack.b() instanceof ItemSword && itemstack1.b() instanceof ItemSword) {
                        ItemSword itemsword = (ItemSword) itemstack.b();
                        ItemSword itemsword1 = (ItemSword) itemstack1.b();

                        if (itemsword.g() == itemsword1.g()) {
                            flag0 = itemstack.i() > itemstack1.i() || itemstack.n() && !itemstack1.n();
                        } else {
                            flag0 = itemsword.g() > itemsword1.g();
                        }
                    } else if (itemstack.b() instanceof ItemBow && itemstack1.b() instanceof ItemBow) {
                        flag0 = itemstack.n() && !itemstack1.n();
                    } else {
                        flag0 = false;
                    }
                } else if (itemstack.b() instanceof ItemArmor && !(itemstack1.b() instanceof ItemArmor)) {
                    flag0 = true;
                } else if (itemstack.b() instanceof ItemArmor && itemstack1.b() instanceof ItemArmor) {
                    ItemArmor itemarmor = (ItemArmor) itemstack.b();
                    ItemArmor itemarmor1 = (ItemArmor) itemstack1.b();

                    if (itemarmor.c == itemarmor1.c) {
                        flag0 = itemstack.i() > itemstack1.i() || itemstack.n() && !itemstack1.n();
                    } else {
                        flag0 = itemarmor.c > itemarmor1.c;
                    }
                } else {
                    flag0 = false;
                }
            }

            if (flag0 && this.a(itemstack)) {
                if (itemstack1 != null && this.V.nextFloat() - 0.1F < this.bh[i0]) {
                    this.a(itemstack1, 0.0F);
                }

                if (itemstack.b() == Items.i && entityitem.n() != null) {
                    EntityPlayer entityplayer = this.o.a(entityitem.n());

                    if (entityplayer != null) {
                        entityplayer.b((StatBase) AchievementList.x);
                    }
                }

                this.c(i0, itemstack);
                this.bh[i0] = 2.0F;
                this.bl = true;
                this.a(entityitem, 1);
                entityitem.J();
            }
        }

    }

    protected boolean a(ItemStack itemstack) {
        return true;
    }

    protected boolean C() {
        // CanaryMod: EntityDespawn
        return !((EntityDespawnHook) new EntityDespawnHook(entity).call()).isCanceled();
        //
    }

    protected void D() {
        if (this.bl) {
            this.aO = 0;
        } else {
            EntityPlayer entityplayer = this.o.a(this, -1.0D);

            if (entityplayer != null) {
                double d0 = entityplayer.s - this.s;
                double d1 = entityplayer.t - this.t;
                double d2 = entityplayer.u - this.u;
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;

                if (this.C() && d3 > 16384.0D) {
                    this.J();
                }

                if (this.aO > 600 && this.V.nextInt(800) == 0 && d3 > 1024.0D && this.C()) {
                    this.J();
                } else if (d3 < 1024.0D) {
                    this.aO = 0;
                }
            }

        }
    }

    protected final void bJ() {
        ++this.aO;
        this.o.B.a("checkDespawn");
        this.D();
        this.o.B.b();
        this.o.B.a("sensing");
        this.bi.a();
        this.o.B.b();
        this.o.B.a("targetSelector");
        this.bg.a();
        this.o.B.b();
        this.o.B.a("goalSelector");
        this.i.a();
        this.o.B.b();
        this.o.B.a("navigation");
        this.h.k();
        this.o.B.b();
        this.o.B.a("mob tick");
        this.E();
        this.o.B.b();
        this.o.B.a("controls");
        this.o.B.a("move");
        this.f.c();
        this.o.B.c("look");
        this.a.a();
        this.o.B.c("jump");
        this.g.b();
        this.o.B.b();
        this.o.B.b();
    }

    protected void E() {}

    public int bP() {
        return 40;
    }

    public void a(Entity entity, float f0, float f1) {
        double d0 = entity.s - this.s;
        double d1 = entity.u - this.u;
        double d2;

        if (entity instanceof EntityLivingBase) {
            EntityLivingBase entitylivingbase = (EntityLivingBase) entity;

            d2 = entitylivingbase.t + (double) entitylivingbase.aR() - (this.t + (double) this.aR());
        } else {
            d2 = (entity.aQ().b + entity.aQ().e) / 2.0D - (this.t + (double) this.aR());
        }

        double d3 = (double) MathHelper.a(d0 * d0 + d1 * d1);
        float f2 = (float) (Math.atan2(d1, d0) * 180.0D / 3.1415927410125732D) - 90.0F;
        float f3 = (float) (-(Math.atan2(d2, d3) * 180.0D / 3.1415927410125732D));

        this.z = this.b(this.z, f3, f1);
        this.y = this.b(this.y, f2, f0);
    }

    private float b(float f0, float f1, float f2) {
        float f3 = MathHelper.g(f1 - f0);

        if (f3 > f2) {
            f3 = f2;
        }

        if (f3 < -f2) {
            f3 = -f2;
        }

        return f0 + f3;
    }

    public boolean bQ() {
        return true;
    }

    public boolean bR() {
        return this.o.a(this.aQ(), (Entity) this) && this.o.a((Entity) this, this.aQ()).isEmpty() && !this.o.d(this.aQ());
    }

    public int bU() {
        return 4;
    }

    public int aF() {
        if (this.u() == null) {
            return 3;
        } else {
            int i0 = (int) (this.bm() - this.bt() * 0.33F);

            i0 -= (3 - this.o.aa().a()) * 4;
            if (i0 < 0) {
                i0 = 0;
            }

            return i0 + 3;
        }
    }

    public ItemStack bz() {
        return this.bj[0];
    }

    public ItemStack p(int i0) {
        return this.bj[i0];
    }

    public ItemStack q(int i0) {
        return this.bj[i0 + 1];
    }

    public void c(int i0, ItemStack itemstack) {
        this.bj[i0] = itemstack;
    }

    public ItemStack[] at() {
        return this.bj;
    }

    protected void a(boolean flag0, int i0) {
        for (int i1 = 0; i1 < this.at().length; ++i1) {
            ItemStack itemstack = this.p(i1);
            boolean flag1 = this.bh[i1] > 1.0F;

            if (itemstack != null && (flag0 || flag1) && this.V.nextFloat() - (float) i0 * 0.01F < this.bh[i1]) {
                if (!flag1 && itemstack.e()) {
                    int i2 = Math.max(itemstack.j() - 25, 1);
                    int i3 = itemstack.j() - this.V.nextInt(this.V.nextInt(i2) + 1);

                    if (i3 > i2) {
                        i3 = i2;
                    }

                    if (i3 < 1) {
                        i3 = 1;
                    }

                    itemstack.b(i3);
                }

                this.a(itemstack, 0.0F);
            }
        }

    }

    protected void a(DifficultyInstance difficultyinstance) {
        if (this.V.nextFloat() < 0.15F * difficultyinstance.c()) {
            int i0 = this.V.nextInt(2);
            float f0 = this.o.aa() == EnumDifficulty.HARD ? 0.1F : 0.25F;

            if (this.V.nextFloat() < 0.095F) {
                ++i0;
            }

            if (this.V.nextFloat() < 0.095F) {
                ++i0;
            }

            if (this.V.nextFloat() < 0.095F) {
                ++i0;
            }

            for (int i1 = 3; i1 >= 0; --i1) {
                ItemStack itemstack = this.q(i1);

                if (i1 < 3 && this.V.nextFloat() < f0) {
                    break;
                }

                if (itemstack == null) {
                    Item item = a(i1 + 1, i0);

                    if (item != null) {
                        this.c(i1 + 1, new ItemStack(item));
                    }
                }
            }
        }

    }

    public static int c(ItemStack itemstack) {
        if (itemstack.b() != Item.a(Blocks.aU) && itemstack.b() != Items.bX) {
            if (itemstack.b() instanceof ItemArmor) {
                switch (((ItemArmor) itemstack.b()).b) {
                    case 0:
                        return 4;

                    case 1:
                        return 3;

                    case 2:
                        return 2;

                    case 3:
                        return 1;
                }
            }

            return 0;
        } else {
            return 4;
        }
    }

    public static Item a(int i0, int i1) {
        switch (i0) {
            case 4:
                if (i1 == 0) {
                    return Items.Q;
                } else if (i1 == 1) {
                    return Items.ag;
                } else if (i1 == 2) {
                    return Items.U;
                } else if (i1 == 3) {
                    return Items.Y;
                } else if (i1 == 4) {
                    return Items.ac;
                }

            case 3:
                if (i1 == 0) {
                    return Items.R;
                } else if (i1 == 1) {
                    return Items.ah;
                } else if (i1 == 2) {
                    return Items.V;
                } else if (i1 == 3) {
                    return Items.Z;
                } else if (i1 == 4) {
                    return Items.ad;
                }

            case 2:
                if (i1 == 0) {
                    return Items.S;
                } else if (i1 == 1) {
                    return Items.ai;
                } else if (i1 == 2) {
                    return Items.W;
                } else if (i1 == 3) {
                    return Items.aa;
                } else if (i1 == 4) {
                    return Items.ae;
                }

            case 1:
                if (i1 == 0) {
                    return Items.T;
                } else if (i1 == 1) {
                    return Items.aj;
                } else if (i1 == 2) {
                    return Items.X;
                } else if (i1 == 3) {
                    return Items.ab;
                } else if (i1 == 4) {
                    return Items.af;
                }

            default:
                return null;
        }
    }

    protected void b(DifficultyInstance difficultyinstance) {
        float f0 = difficultyinstance.c();

        if (this.bz() != null && this.V.nextFloat() < 0.25F * f0) {
            EnchantmentHelper.a(this.V, this.bz(), (int) (5.0F + f0 * (float) this.V.nextInt(18)));
        }

        for (int i0 = 0; i0 < 4; ++i0) {
            ItemStack itemstack = this.q(i0);

            if (itemstack != null && this.V.nextFloat() < 0.5F * f0) {
                EnchantmentHelper.a(this.V, itemstack, (int) (5.0F + f0 * (float) this.V.nextInt(18)));
            }
        }

    }

    public IEntityLivingData a(DifficultyInstance difficultyinstance, IEntityLivingData ientitylivingdata) {
        this.a(SharedMonsterAttributes.b).b(new AttributeModifier("Random spawn bonus", this.V.nextGaussian() * 0.05D, 1));
        return ientitylivingdata;
    }

    public boolean bV() {
        return false;
    }

    public void bW() {
        this.bl = true;
    }

    public void a(int i0, float f0) {
        this.bh[i0] = f0;
    }

    public boolean bX() {
        return this.bk;
    }

    public void j(boolean flag0) {
        this.bk = flag0;
    }

    public boolean bY() {
        return this.bl;
    }

    public final boolean e(EntityPlayer entityplayer) {
        if (this.cb() && this.cc() == entityplayer) {
            this.a(true, !entityplayer.by.d);
            return true;
        } else {
            ItemStack itemstack = entityplayer.bg.h();

            if (itemstack != null && itemstack.b() == Items.cn && this.ca()) {
                if (!(this instanceof EntityTameable) || !((EntityTameable) this).cj()) {
                    this.a(entityplayer, true);
                    --itemstack.b;
                    return true;
                }

                if (((EntityTameable) this).e((EntityLivingBase)entityplayer)) {
                    this.a(entityplayer, true);
                    --itemstack.b;
                    return true;
                }
            }

            return this.a(entityplayer) ? true : super.e(entityplayer);
        }
    }

    protected boolean a(EntityPlayer entityplayer) {
        return false;
    }

    protected void bZ() {
        if (this.bo != null) {
            this.n();
        }

        if (this.bm) {
            if (!this.ai()) {
                this.a(true, true);
            }

            if (this.bn == null || this.bn.I) {
                this.a(true, true);
            }
        }
    }

    public void a(boolean flag0, boolean flag1) {
        if (this.bm) {
            this.bm = false;
            this.bn = null;
            if (!this.o.D && flag1) {
                this.a(Items.cn, 1);
            }

            if (!this.o.D && flag0 && this.o instanceof WorldServer) {
                ((WorldServer) this.o).s().a((Entity) this, (Packet) (new S1BPacketEntityAttach(1, this, (Entity) null)));
            }
        }

    }

    public boolean ca() {
        return !this.cb() && !(this instanceof IMob);
    }

    public boolean cb() {
        return this.bm;
    }

    public Entity cc() {
        return this.bn;
    }

    public void a(Entity entity, boolean flag0) {
        this.bm = true;
        this.bn = entity;
        if (!this.o.D && flag0 && this.o instanceof WorldServer) {
            ((WorldServer) this.o).s().a((Entity) this, (Packet) (new S1BPacketEntityAttach(1, this, this.bn)));
        }

    }

    private void n() {
        if (this.bm && this.bo != null) {
            if (this.bo.b("UUIDMost", 4) && this.bo.b("UUIDLeast", 4)) {
                UUID uuid = new UUID(this.bo.g("UUIDMost"), this.bo.g("UUIDLeast"));
                List list = this.o.a(EntityLivingBase.class, this.aQ().b(10.0D, 10.0D, 10.0D));
                Iterator iterator = list.iterator();

                while (iterator.hasNext()) {
                    EntityLivingBase entitylivingbase = (EntityLivingBase) iterator.next();

                    if (entitylivingbase.aJ().equals(uuid)) {
                        this.bn = entitylivingbase;
                        break;
                    }
                }
            } else if (this.bo.b("X", 99) && this.bo.b("Y", 99) && this.bo.b("Z", 99)) {
                BlockPos blockpos = new BlockPos(this.bo.f("X"), this.bo.f("Y"), this.bo.f("Z"));
                EntityLeashKnot entityleashknot = EntityLeashKnot.b(this.o, blockpos);

                if (entityleashknot == null) {
                    entityleashknot = EntityLeashKnot.a(this.o, blockpos);
                }

                this.bn = entityleashknot;
            } else {
                this.a(false, true);
            }
        }

        this.bo = null;
    }

    public boolean d(int i0, ItemStack itemstack) {
        int i1;

        if (i0 == 99) {
            i1 = 0;
        } else {
            i1 = i0 - 100 + 1;
            if (i1 < 0 || i1 >= this.bj.length) {
                return false;
            }
        }

        if (itemstack != null && c(itemstack) != i1 && (i1 != 4 || !(itemstack.b() instanceof ItemBlock))) {
            return false;
        } else {
            this.c(i1, itemstack);
            return true;
        }
    }

    public boolean bL() {
        return super.bL() && !this.cd();
    }

    protected void k(boolean flag0) {
        this.ac.b(15, Byte.valueOf((byte) (flag0 ? 1 : 0)));
    }

    private boolean cd() {
        return this.ac.a(15) != 0;
    }

    public static enum SpawnPlacementType {

        ON_GROUND("ON_GROUND", 0), IN_AIR("IN_AIR", 1), IN_WATER("IN_WATER", 2);

        private static final EntityLiving.SpawnPlacementType[] $VALUES = new EntityLiving.SpawnPlacementType[]{ON_GROUND, IN_AIR, IN_WATER};

        private SpawnPlacementType(String p_i45893_1_, int p_i45893_2_) {
        }
    }

    // CanaryMod
    public float getDropChanceForSlot(int slot) {
        if (slot < 0 || slot > 5) {
            return -1.0F;
        }
        return this.bh[slot];
    }

    public EntityAITasks getTasks() {
        return this.i;
    }

    public EntityAITasks getTargetTasks() {
        return this.bg;
    }
}
