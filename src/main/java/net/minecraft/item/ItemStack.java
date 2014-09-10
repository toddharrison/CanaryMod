package net.minecraft.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.canarymod.api.inventory.CanaryBaseItem;
import net.canarymod.api.inventory.CanaryItem;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentDurability;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.HoverEvent;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.stats.StatList;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

import java.text.DecimalFormat;
import java.util.Random;

public final class ItemStack {

    public static final DecimalFormat a = new DecimalFormat("#.###");
    public int b;
    public int c;
    private Item e;
    public NBTTagCompound d;
    private int f;
    private EntityItemFrame g;

    public ItemStack(Block block) {
        this(block, 1);
    }

    public ItemStack(Block block, int i0) {
        this(block, i0, 0);
    }

    public ItemStack(Block block, int i0, int i1) {
        this(Item.a(block), i0, i1);
    }

    public ItemStack(Item item) {
        this(item, 1);
    }

    public ItemStack(Item item, int i0) {
        this(item, i0, 0);
    }

    public ItemStack(Item item, int i0, int i1) {
        this.e = item;
        this.b = i0;
        this.f = i1;
        if (this.f < 0) {
            this.f = 0;
        }
    }

    public static ItemStack a(NBTTagCompound nbttagcompound) {
        ItemStack itemstack = new ItemStack();

        itemstack.c(nbttagcompound);
        return itemstack.b() != null ? itemstack : null;
    }

    private ItemStack() {
    }

    public ItemStack a(int i0) {
        ItemStack itemstack = new ItemStack(this.e, i0, this.f);

        if (this.d != null) {
            itemstack.d = (NBTTagCompound) this.d.b();
        }

        this.b -= i0;
        return itemstack;
    }

    public Item b() {
        return this.e;
    }

    public boolean a(EntityPlayer entityplayer, World world, int i0, int i1, int i2, int i3, float f0, float f1, float f2) {
        boolean flag0 = this.b().a(this, entityplayer, world, i0, i1, i2, i3, f0, f1, f2);

        if (flag0) {
            entityplayer.a(StatList.E[Item.b(this.e)], 1);
        }

        return flag0;
    }

    public float a(Block block) {
        return this.b().a(this, block);
    }

    public ItemStack a(World world, EntityPlayer entityplayer) {
        return this.b().a(this, world, entityplayer);
    }

    public ItemStack b(World world, EntityPlayer entityplayer) {
        return this.b().b(this, world, entityplayer);
    }

    public NBTTagCompound b(NBTTagCompound nbttagcompound) {
        nbttagcompound.a("id", (short) Item.b(this.e));
        nbttagcompound.a("Count", (byte) this.b);
        nbttagcompound.a("Damage", (short) this.f);
        if (this.d != null) {
            nbttagcompound.a("tag", (NBTBase) this.d);
        }

        return nbttagcompound;
    }

    public void c(NBTTagCompound nbttagcompound) {
        this.e = Item.d(nbttagcompound.e("id"));
        this.b = nbttagcompound.d("Count");
        this.f = nbttagcompound.e("Damage");
        if (this.f < 0) {
            this.f = 0;
        }

        if (nbttagcompound.b("tag", 10)) {
            this.d = nbttagcompound.m("tag");
        }
    }

    public int e() {
        return this.b().m();
    }

    public boolean f() {
        return this.e() > 1 && (!this.g() || !this.i());
    }

    public boolean g() {
        return this.e.o() <= 0 ? false : !this.p() || !this.q().n("Unbreakable");
    }

    public boolean h() {
        return this.e.n();
    }

    public boolean i() {
        return this.g() && this.f > 0;
    }

    public int j() {
        return this.f;
    }

    public int k() {
        return this.f;
    }

    public void b(int i0) {
        this.f = i0;
        if (this.f < 0) {
            this.f = 0;
        }
    }

    public int l() {
        return this.e.o();
    }

    public boolean a(int i0, Random random) {
        if (!this.g()) {
            return false;
        }
        else {
            if (i0 > 0) {
                int i1 = EnchantmentHelper.a(Enchantment.t.B, this);
                int i2 = 0;

                for (int i3 = 0; i1 > 0 && i3 < i0; ++i3) {
                    if (EnchantmentDurability.a(this, i1, random)) {
                        ++i2;
                    }
                }

                i0 -= i2;
                if (i0 <= 0) {
                    return false;
                }
            }

            this.f += i0;
            return this.f > this.l();
        }
    }

    public void a(int i0, EntityLivingBase entitylivingbase) {
        if (!(entitylivingbase instanceof EntityPlayer) || !((EntityPlayer) entitylivingbase).bE.d) {
            if (this.g()) {
                if (this.a(i0, entitylivingbase.aI())) {
                    entitylivingbase.a(this);
                    --this.b;
                    if (entitylivingbase instanceof EntityPlayer) {
                        EntityPlayer entityplayer = (EntityPlayer) entitylivingbase;

                        entityplayer.a(StatList.F[Item.b(this.e)], 1);
                        if (this.b == 0 && this.b() instanceof ItemBow) {
                            entityplayer.bG();
                        }
                    }

                    if (this.b < 0) {
                        this.b = 0;
                    }

                    this.f = 0;
                }
            }
        }
    }

    public void a(EntityLivingBase entitylivingbase, EntityPlayer entityplayer) {
        boolean flag0 = this.e.a(this, entitylivingbase, (EntityLivingBase) entityplayer);

        if (flag0) {
            entityplayer.a(StatList.E[Item.b(this.e)], 1);
        }
    }

    public void a(World world, Block block, int i0, int i1, int i2, EntityPlayer entityplayer) {
        boolean flag0 = this.e.a(this, world, block, i0, i1, i2, entityplayer);

        if (flag0) {
            entityplayer.a(StatList.E[Item.b(this.e)], 1);
        }
    }

    public boolean b(Block block) {
        return this.e.b(block);
    }

    public boolean a(EntityPlayer entityplayer, EntityLivingBase entitylivingbase) {
        return this.e.a(this, entityplayer, entitylivingbase);
    }

    public ItemStack m() {
        ItemStack itemstack = new ItemStack(this.e, this.b, this.f);

        if (this.d != null) {
            itemstack.d = (NBTTagCompound) this.d.b();
        }

        return itemstack;
    }

    public static boolean a(ItemStack itemstack, ItemStack itemstack1) {
        return itemstack == null && itemstack1 == null ? true : (itemstack != null && itemstack1 != null ? (itemstack.d == null && itemstack1.d != null ? false : itemstack.d == null || itemstack.d.equals(itemstack1.d)) : false);
    }

    public static boolean b(ItemStack itemstack, ItemStack itemstack1) {
        return itemstack == null && itemstack1 == null ? true : (itemstack != null && itemstack1 != null ? itemstack.d(itemstack1) : false);
    }

    private boolean d(ItemStack itemstack) {
        return this.b != itemstack.b ? false : (this.e != itemstack.e ? false : (this.f != itemstack.f ? false : (this.d == null && itemstack.d != null ? false : this.d == null || this.d.equals(itemstack.d))));
    }

    public boolean a(ItemStack itemstack) {
        return this.e == itemstack.e && this.f == itemstack.f;
    }

    public String a() {
        return this.e.a(this);
    }

    public static ItemStack b(ItemStack itemstack) {
        return itemstack == null ? null : itemstack.m();
    }

    public String toString() {
        return this.b + "x" + this.e.a() + "@" + this.f;
    }

    public void a(World world, Entity entity, int i0, boolean flag0) {
        if (this.c > 0) {
            --this.c;
        }

        this.e.a(this, world, entity, i0, flag0);
    }

    public void a(World world, EntityPlayer entityplayer, int i0) {
        entityplayer.a(StatList.D[Item.b(this.e)], i0);
        this.e.d(this, world, entityplayer);
    }

    public int n() {
        return this.b().d_(this);
    }

    public EnumAction o() {
        return this.b().d(this);
    }

    public void b(World world, EntityPlayer entityplayer, int i0) {
        this.b().a(this, world, entityplayer, i0);
    }

    public boolean p() {
        return this.d != null;
    }

    public NBTTagCompound q() {
        return this.d;
    }

    public NBTTagList r() {
        return this.d == null ? null : this.d.c("ench", 10);
    }

    public void d(NBTTagCompound nbttagcompound) {
        this.d = nbttagcompound;
    }

    public String s() {
        String s0 = this.b().n(this);

        if (this.d != null && this.d.b("display", 10)) {
            NBTTagCompound nbttagcompound = this.d.m("display");

            if (nbttagcompound.b("Name", 8)) {
                s0 = nbttagcompound.j("Name");
            }
        }

        return s0;
    }

    public ItemStack c(String s0) {
        if (this.d == null) {
            this.d = new NBTTagCompound();
        }

        if (!this.d.b("display", 10)) {
            this.d.a("display", (NBTBase) (new NBTTagCompound()));
        }

        this.d.m("display").a("Name", s0);
        return this;
    }

    public void t() {
        if (this.d != null) {
            if (this.d.b("display", 10)) {
                NBTTagCompound nbttagcompound = this.d.m("display");

                nbttagcompound.o("Name");
                if (nbttagcompound.d()) {
                    this.d.o("display");
                    if (this.d.d()) {
                        this.d((NBTTagCompound) null);
                    }
                }
            }
        }
    }

    public boolean u() {
        return this.d == null ? false : (!this.d.b("display", 10) ? false : this.d.m("display").b("Name", 8));
    }

    public EnumRarity w() {
        return this.b().f(this);
    }

    public boolean x() {
        return !this.b().e_(this) ? false : !this.y();
    }

    public void a(Enchantment enchantment, int i0) {
        if (this.d == null) {
            this.d(new NBTTagCompound());
        }

        if (!this.d.b("ench", 9)) {
            this.d.a("ench", (NBTBase) (new NBTTagList()));
        }

        NBTTagList nbttaglist = this.d.c("ench", 10);
        NBTTagCompound nbttagcompound = new NBTTagCompound();

        nbttagcompound.a("id", (short) enchantment.B);
        nbttagcompound.a("lvl", (short) ((byte) i0));
        nbttaglist.a((NBTBase) nbttagcompound);
    }

    public boolean y() {
        return this.d != null && this.d.b("ench", 9);
    }

    public void a(String s0, NBTBase nbtbase) {
        if (this.d == null) {
            this.d(new NBTTagCompound());
        }

        this.d.a(s0, nbtbase);
    }

    public boolean z() {
        return this.b().v();
    }

    public boolean A() {
        return this.g != null;
    }

    public void a(EntityItemFrame entityitemframe) {
        this.g = entityitemframe;
    }

    public EntityItemFrame B() {
        return this.g;
    }

    public int C() {
        return this.p() && this.d.b("RepairCost", 3) ? this.d.f("RepairCost") : 0;
    }

    public void c(int i0) {
        if (!this.p()) {
            this.d = new NBTTagCompound();
        }

        this.d.a("RepairCost", i0);
    }

    public Multimap D() {
        Object object;

        if (this.p() && this.d.b("AttributeModifiers", 9)) {
            object = HashMultimap.create();
            NBTTagList nbttaglist = this.d.c("AttributeModifiers", 10);

            for (int i0 = 0; i0 < nbttaglist.c(); ++i0) {
                NBTTagCompound nbttagcompound = nbttaglist.b(i0);
                AttributeModifier attributemodifier = SharedMonsterAttributes.a(nbttagcompound);

                if (attributemodifier.a().getLeastSignificantBits() != 0L && attributemodifier.a().getMostSignificantBits() != 0L) {
                    ((Multimap) object).put(nbttagcompound.j("AttributeName"), attributemodifier);
                }
            }
        }
        else {
            object = this.b().k();
        }

        return (Multimap) object;
    }

    public void a(Item item) {
        this.e = item;
    }

    public IChatComponent E() {
        IChatComponent ichatcomponent = (new ChatComponentText("[")).a(this.s()).a("]");

        if (this.e != null) {
            NBTTagCompound nbttagcompound = new NBTTagCompound();

            this.b(nbttagcompound);
            ichatcomponent.b().a(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new ChatComponentText(nbttagcompound.toString())));
            ichatcomponent.b().a(this.w().e);
        }

        return ichatcomponent;
    }

    // CanaryMod
    public CanaryItem getCanaryItem() {
        return new CanaryItem(this);
    }

    public CanaryBaseItem getBaseItem() {
        return this.b().getBaseItem();
    }
    //
}
