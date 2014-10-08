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
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.text.DecimalFormat;
import java.util.Random;

public final class ItemStack {

    public static final DecimalFormat a = new DecimalFormat("#.###");
    public int b;
    public int c;
    private Item d;
    private NBTTagCompound e;
    private int f;
    private EntityItemFrame g;
    private Block h;
    private boolean i;
    private Block j;
    private boolean k;

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
        this.h = null;
        this.i = false;
        this.j = null;
        this.k = false;
        this.d = item;
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
        this.h = null;
        this.i = false;
        this.j = null;
        this.k = false;
    }

    public ItemStack a(int i0) {
        ItemStack itemstack = new ItemStack(this.d, i0, this.f);

        if (this.e != null) {
            itemstack.e = (NBTTagCompound)this.e.b();
        }

        this.b -= i0;
        return itemstack;
    }

    public Item b() {
        return this.d;
    }

    public boolean a(EntityPlayer entityplayer, World world, BlockPos blockpos, EnumFacing enumfacing, float f0, float f1, float f2) {
        boolean flag0 = this.b().a(this, entityplayer, world, blockpos, enumfacing, f0, f1, f2);

        if (flag0) {
            entityplayer.b(StatList.J[Item.b(this.d)]);
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
        ResourceLocation resourcelocation = (ResourceLocation)Item.e.c(this.d);

        nbttagcompound.a("id", resourcelocation == null ? "minecraft:air" : resourcelocation.toString());
        nbttagcompound.a("Count", (byte)this.b);
        nbttagcompound.a("Damage", (short)this.f);
        if (this.e != null) {
            nbttagcompound.a("tag", (NBTBase)this.e);
        }

        return nbttagcompound;
    }

    public void c(NBTTagCompound nbttagcompound) {
        if (nbttagcompound.b("id", 8)) {
            this.d = Item.d(nbttagcompound.j("id"));
        }
        else {
            this.d = Item.b(nbttagcompound.e("id"));
        }

        this.b = nbttagcompound.d("Count");
        this.f = nbttagcompound.e("Damage");
        if (this.f < 0) {
            this.f = 0;
        }

        if (nbttagcompound.b("tag", 10)) {
            this.e = nbttagcompound.m("tag");
            if (this.d != null) {
                this.d.a(this.e);
            }
        }
    }

    public int c() {
        return this.b().j();
    }

    public boolean d() {
        return this.c() > 1 && (!this.e() || !this.g());
    }

    public boolean e() {
        return this.d == null ? false : (this.d.l() <= 0 ? false : !this.n() || !this.o().n("Unbreakable"));
    }

    public boolean f() {
        return this.d.k();
    }

    public boolean g() {
        return this.e() && this.f > 0;
    }

    public int h() {
        return this.f;
    }

    public int i() {
        return this.f;
    }

    public void b(int i0) {
        this.f = i0;
        if (this.f < 0) {
            this.f = 0;
        }
    }

    public int j() {
        return this.d.l();
    }

    public boolean a(int i0, Random random) {
        if (!this.e()) {
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
            return this.f > this.j();
        }
    }

    public void a(int i0, EntityLivingBase entitylivingbase) {
        if (!(entitylivingbase instanceof EntityPlayer) || !((EntityPlayer)entitylivingbase).by.d) {
            if (this.e()) {
                if (this.a(i0, entitylivingbase.bb())) {
                    entitylivingbase.b(this);
                    --this.b;
                    if (entitylivingbase instanceof EntityPlayer) {
                        EntityPlayer entityplayer = (EntityPlayer)entitylivingbase;

                        entityplayer.b(StatList.K[Item.b(this.d)]);
                        if (this.b == 0 && this.b() instanceof ItemBow) {
                            entityplayer.bZ();
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
        boolean flag0 = this.d.a(this, entitylivingbase, (EntityLivingBase)entityplayer);

        if (flag0) {
            entityplayer.b(StatList.J[Item.b(this.d)]);
        }
    }

    public void a(World world, Block block, BlockPos blockpos, EntityPlayer entityplayer) {
        boolean flag0 = this.d.a(this, world, block, blockpos, entityplayer);

        if (flag0) {
            entityplayer.b(StatList.J[Item.b(this.d)]);
        }
    }

    public boolean b(Block block) {
        return this.d.b(block);
    }

    public boolean a(EntityPlayer entityplayer, EntityLivingBase entitylivingbase) {
        return this.d.a(this, entityplayer, entitylivingbase);
    }

    public ItemStack k() {
        ItemStack itemstack = new ItemStack(this.d, this.b, this.f);

        if (this.e != null) {
            itemstack.e = (NBTTagCompound)this.e.b();
        }

        return itemstack;
    }

    public static boolean a(ItemStack itemstack, ItemStack itemstack1) {
        return itemstack == null && itemstack1 == null ? true : (itemstack != null && itemstack1 != null ? (itemstack.e == null && itemstack1.e != null ? false : itemstack.e == null || itemstack.e.equals(itemstack1.e)) : false);
    }

    public static boolean b(ItemStack itemstack, ItemStack itemstack1) {
        return itemstack == null && itemstack1 == null ? true : (itemstack != null && itemstack1 != null ? itemstack.d(itemstack1) : false);
    }

    private boolean d(ItemStack itemstack) {
        return this.b != itemstack.b ? false : (this.d != itemstack.d ? false : (this.f != itemstack.f ? false : (this.e == null && itemstack.e != null ? false : this.e == null || this.e.equals(itemstack.e))));
    }

    public static boolean c(ItemStack itemstack, ItemStack itemstack1) {
        return itemstack == null && itemstack1 == null ? true : (itemstack != null && itemstack1 != null ? itemstack.a(itemstack1) : false);
    }

    public boolean a(ItemStack itemstack) {
        return itemstack != null && this.d == itemstack.d && this.f == itemstack.f;
    }

    public String a() {
        return this.d.e_(this);
    }

    public static ItemStack b(ItemStack itemstack) {
        return itemstack == null ? null : itemstack.k();
    }

    public String toString() {
        return this.b + "x" + this.d.a() + "@" + this.f;
    }

    public void a(World world, Entity entity, int i0, boolean flag0) {
        if (this.c > 0) {
            --this.c;
        }

        this.d.a(this, world, entity, i0, flag0);
    }

    public void a(World world, EntityPlayer entityplayer, int i0) {
        entityplayer.a(StatList.I[Item.b(this.d)], i0);
        this.d.d(this, world, entityplayer);
    }

    public int l() {
        return this.b().d(this);
    }

    public EnumAction m() {
        return this.b().e(this);
    }

    public void b(World world, EntityPlayer entityplayer, int i0) {
        this.b().a(this, world, entityplayer, i0);
    }

    public boolean n() {
        return this.e != null;
    }

    public NBTTagCompound o() {
        return this.e;
    }

    public NBTTagCompound a(String s0, boolean flag0) {
        if (this.e != null && this.e.b(s0, 10)) {
            return this.e.m(s0);
        }
        else if (flag0) {
            NBTTagCompound nbttagcompound = new NBTTagCompound();

            this.a(s0, (NBTBase)nbttagcompound);
            return nbttagcompound;
        }
        else {
            return null;
        }
    }

    public NBTTagList p() {
        return this.e == null ? null : this.e.c("ench", 10);
    }

    public void d(NBTTagCompound nbttagcompound) {
        this.e = nbttagcompound;
    }

    public String q() {
        String s0 = this.b().a(this);

        if (this.e != null && this.e.b("display", 10)) {
            NBTTagCompound nbttagcompound = this.e.m("display");

            if (nbttagcompound.b("Name", 8)) {
                s0 = nbttagcompound.j("Name");
            }
        }

        return s0;
    }

    public ItemStack c(String s0) {
        if (this.e == null) {
            this.e = new NBTTagCompound();
        }

        if (!this.e.b("display", 10)) {
            this.e.a("display", (NBTBase)(new NBTTagCompound()));
        }

        this.e.m("display").a("Name", s0);
        return this;
    }

    public void r() {
        if (this.e != null) {
            if (this.e.b("display", 10)) {
                NBTTagCompound nbttagcompound = this.e.m("display");

                nbttagcompound.o("Name");
                if (nbttagcompound.c_()) {
                    this.e.o("display");
                    if (this.e.c_()) {
                        this.d((NBTTagCompound)null);
                    }
                }
            }
        }
    }

    public boolean s() {
        return this.e == null ? false : (!this.e.b("display", 10) ? false : this.e.m("display").b("Name", 8));
    }

    public EnumRarity u() {
        return this.b().g(this);
    }

    public boolean v() {
        return !this.b().f_(this) ? false : !this.w();
    }

    public void a(Enchantment enchantment, int i0) {
        if (this.e == null) {
            this.d(new NBTTagCompound());
        }

        if (!this.e.b("ench", 9)) {
            this.e.a("ench", (NBTBase)(new NBTTagList()));
        }

        NBTTagList nbttaglist = this.e.c("ench", 10);
        NBTTagCompound nbttagcompound = new NBTTagCompound();

        nbttagcompound.a("id", (short)enchantment.B);
        nbttagcompound.a("lvl", (short)((byte)i0));
        nbttaglist.a((NBTBase)nbttagcompound);
    }

    public boolean w() {
        return this.e != null && this.e.b("ench", 9);
    }

    public void a(String s0, NBTBase nbtbase) {
        if (this.e == null) {
            this.d(new NBTTagCompound());
        }

        this.e.a(s0, nbtbase);
    }

    public boolean x() {
        return this.b().s();
    }

    public boolean y() {
        return this.g != null;
    }

    public void a(EntityItemFrame entityitemframe) {
        this.g = entityitemframe;
    }

    public EntityItemFrame z() {
        return this.g;
    }

    public int A() {
        return this.n() && this.e.b("RepairCost", 3) ? this.e.f("RepairCost") : 0;
    }

    public void c(int i0) {
        if (!this.n()) {
            this.e = new NBTTagCompound();
        }

        this.e.a("RepairCost", i0);
    }

    public Multimap B() {
        Object object;

        if (this.n() && this.e.b("AttributeModifiers", 9)) {
            object = HashMultimap.create();
            NBTTagList nbttaglist = this.e.c("AttributeModifiers", 10);

            for (int i0 = 0; i0 < nbttaglist.c(); ++i0) {
                NBTTagCompound nbttagcompound = nbttaglist.b(i0);
                AttributeModifier attributemodifier = SharedMonsterAttributes.a(nbttagcompound);

                if (attributemodifier != null && attributemodifier.a().getLeastSignificantBits() != 0L && attributemodifier.a().getMostSignificantBits() != 0L) {
                    ((Multimap)object).put(nbttagcompound.j("AttributeName"), attributemodifier);
                }
            }
        }
        else {
            object = this.b().i();
        }

        return (Multimap)object;
    }

    public void a(Item item) {
        this.d = item;
    }

    public IChatComponent C() {
        ChatComponentText chatcomponenttext = new ChatComponentText(this.q());

        if (this.s()) {
            chatcomponenttext.b().b(Boolean.valueOf(true));
        }

        IChatComponent ichatcomponent = (new ChatComponentText("[")).a(chatcomponenttext).a("]");

        if (this.d != null) {
            NBTTagCompound nbttagcompound = new NBTTagCompound();

            this.b(nbttagcompound);
            ichatcomponent.b().a(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new ChatComponentText(nbttagcompound.toString())));
            ichatcomponent.b().a(this.u().e);
        }

        return ichatcomponent;
    }

    public boolean c(Block block) {
        if (block == this.h) {
            return this.i;
        }
        else {
            this.h = block;
            if (this.n() && this.e.b("CanDestroy", 9)) {
                NBTTagList nbttaglist = this.e.c("CanDestroy", 8);

                for (int i0 = 0; i0 < nbttaglist.c(); ++i0) {
                    Block block1 = Block.b(nbttaglist.f(i0));

                    if (block1 == block) {
                        this.i = true;
                        return true;
                    }
                }
            }

            this.i = false;
            return false;
        }
    }

    public boolean d(Block block) {
        if (block == this.j) {
            return this.k;
        }
        else {
            this.j = block;
            if (this.n() && this.e.b("CanPlaceOn", 9)) {
                NBTTagList nbttaglist = this.e.c("CanPlaceOn", 8);

                for (int i0 = 0; i0 < nbttaglist.c(); ++i0) {
                    Block block1 = Block.b(nbttaglist.f(i0));

                    if (block1 == block) {
                        this.k = true;
                        return true;
                    }
                }
            }

            this.k = false;
            return false;
        }
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
