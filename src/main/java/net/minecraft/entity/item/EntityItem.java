package net.minecraft.entity.item;

import net.canarymod.api.entity.CanaryEntityItem;
import net.canarymod.hook.entity.EntityDespawnHook;
import net.canarymod.hook.entity.ItemTouchGroundHook;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;

public class EntityItem extends Entity {

    private static final Logger b = LogManager.getLogger();
    public int c;  // CanaryMod: private => public; age
    public int d;  // CanaryMod: private => public; pickupdelay
    public int e; // CanaryMod: private => public; health
    private String f;
    private String g;
    public float a;

    public EntityItem(World world, double d0, double d1, double d2) {
        super(world);
        this.e = 5;
        this.a = (float) (Math.random() * 3.141592653589793D * 2.0D);
        this.a(0.25F, 0.25F);
        this.b(d0, d1, d2);
        this.y = (float) (Math.random() * 360.0D);
        this.v = (double) ((float) (Math.random() * 0.20000000298023224D - 0.10000000149011612D));
        this.w = 0.20000000298023224D;
        this.x = (double) ((float) (Math.random() * 0.20000000298023224D - 0.10000000149011612D));
        this.entity = new CanaryEntityItem(this); // CanaryMod: Wrap Entity
    }

    public EntityItem(World world, double d0, double d1, double d2, ItemStack itemstack) {
        this(world, d0, d1, d2);
        this.a(itemstack);
    }

    public EntityItem(World world) {
        super(world);
        this.e = 5;
        this.a = (float) (Math.random() * 3.141592653589793D * 2.0D);
        this.a(0.25F, 0.25F);
        this.a(new ItemStack(Blocks.a, 0));
        this.entity = new CanaryEntityItem(this); // CanaryMod: Wrap Entity
    }

    protected boolean r_() {
        return false;
    }

    protected void h() {
        this.H().a(10, 5);
    }

    public void s_() {
        if (this.l() == null) {
            this.J();
        }
        else {
            super.s_();
            if (this.d > 0 && this.d != 32767) {
                --this.d;
            }

            boolean tmpTouch = this.D; // CanaryMod

            this.p = this.s;
            this.q = this.t;
            this.r = this.u;
            this.w -= 0.03999999910593033D;
            this.T = this.j(this.s, (this.aQ().b + this.aQ().e) / 2.0D, this.u);
            this.d(this.v, this.w, this.x);
            boolean flag0 = (int) this.p != (int) this.s || (int) this.q != (int) this.t || (int) this.r != (int) this.u;

            if (flag0 || this.W % 25 == 0) {
                if (this.o.p(new BlockPos(this)).c().r() == Material.i) {
                    this.w = 0.20000000298023224D;
                    this.v = (double) ((this.V.nextFloat() - this.V.nextFloat()) * 0.2F);
                    this.x = (double) ((this.V.nextFloat() - this.V.nextFloat()) * 0.2F);
                    this.a("random.fizz", 0.4F, 2.0F + this.V.nextFloat() * 0.4F);
                }

                if (!this.o.D) {
                    this.w();
                }
            }

            float f0 = 0.98F;

            if (this.C) {
                f0 = this.o.p(new BlockPos(MathHelper.c(this.s), MathHelper.c(this.aQ().b) - 1, MathHelper.c(this.u))).c().K * 0.98F;

                // CanaryMod: ItemTouchGround
                // It does touch the ground now, but didn't in last tick
                if (!tmpTouch) {
                    ItemTouchGroundHook hook = (ItemTouchGroundHook) new ItemTouchGroundHook((net.canarymod.api.entity.EntityItem) getCanaryEntity()).call();
                    if (hook.isCanceled()) {
                        this.J(); // kill the item
                    }
                }
                //
            }

            this.v *= (double) f0;
            this.w *= 0.9800000190734863D;
            this.x *= (double) f0;
            if (this.C) {
                this.w *= -0.5D;
            }

            if (this.c != -32768) {
                ++this.c;
            }

            if (!this.o.D && this.c >= 6000) {
                // CanaryMod: EntityDespawn
                EntityDespawnHook hook = (EntityDespawnHook) new EntityDespawnHook(getCanaryEntity()).call();
                if (!hook.isCanceled()) {
                    this.J();
                }
                else {
                    this.c = 0; // Reset Age
                }
                //
            }

        }
    }

    private void w() {
        Iterator iterator = this.o.a(EntityItem.class, this.aQ().b(0.5D, 0.0D, 0.5D)).iterator();

        while (iterator.hasNext()) {
            EntityItem entityitem = (EntityItem) iterator.next();

            this.a(entityitem);
        }

    }

    private boolean a(EntityItem entityitem) {
        if (entityitem == this) {
            return false;
        }
        else if (entityitem.ai() && this.ai()) {
            ItemStack itemstack = this.l();
            ItemStack itemstack1 = entityitem.l();

            if (this.d != 32767 && entityitem.d != 32767) {
                if (this.c != -32768 && entityitem.c != -32768) {
                    if (itemstack1.b() != itemstack.b()) {
                        return false;
                    }
                    else if (itemstack1.n() ^ itemstack.n()) {
                        return false;
                    }
                    else if (itemstack1.n() && !itemstack1.o().equals(itemstack.o())) {
                        return false;
                    }
                    else if (itemstack1.b() == null) {
                        return false;
                    }
                    else if (itemstack1.b().k() && itemstack1.i() != itemstack.i()) {
                        return false;
                    }
                    else if (itemstack1.b < itemstack.b) {
                        return entityitem.a(this);
                    }
                    else if (itemstack1.b + itemstack.b > itemstack1.c()) {
                        return false;
                    }
                    else {
                        itemstack1.b += itemstack.b;
                        entityitem.d = Math.max(entityitem.d, this.d);
                        entityitem.c = Math.min(entityitem.c, this.c);
                        entityitem.a(itemstack1);
                        this.J();
                        return true;
                    }
                }
                else {
                    return false;
                }
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    public void j() {
        this.c = 4800;
    }

    public boolean W() {
        if (this.o.a(this.aQ(), Material.h, (Entity) this)) {
            if (!this.Y && !this.aa) {
                this.X();
            }

            this.Y = true;
        }
        else {
            this.Y = false;
        }

        return this.Y;
    }

    protected void f(int i0) {
        this.a(DamageSource.a, (float) i0);
    }

    public boolean a(DamageSource damagesource, float f0) {
        if (this.b(damagesource)) {
            return false;
        }
        else if (this.l() != null && this.l().b() == Items.bZ && damagesource.c()) {
            return false;
        }
        else {
            this.ac();
            this.e = (int) ((float) this.e - f0);
            if (this.e <= 0) {
                this.J();
            }

            return false;
        }
    }

    public void b(NBTTagCompound nbttagcompound) {
        nbttagcompound.a("Health", (short) ((byte) this.e));
        nbttagcompound.a("Age", (short) this.c);
        nbttagcompound.a("PickupDelay", (short) this.d);
        if (this.n() != null) {
            nbttagcompound.a("Thrower", this.f);
        }

        if (this.m() != null) {
            nbttagcompound.a("Owner", this.g);
        }

        if (this.l() != null) {
            nbttagcompound.a("Item", (NBTBase) this.l().b(new NBTTagCompound()));
        }

    }

    public void a(NBTTagCompound nbttagcompound) {
        this.e = nbttagcompound.e("Health") & 255;
        this.c = nbttagcompound.e("Age");
        if (nbttagcompound.c("PickupDelay")) {
            this.d = nbttagcompound.e("PickupDelay");
        }

        if (nbttagcompound.c("Owner")) {
            this.g = nbttagcompound.j("Owner");
        }

        if (nbttagcompound.c("Thrower")) {
            this.f = nbttagcompound.j("Thrower");
        }

        NBTTagCompound nbttagcompound1 = nbttagcompound.m("Item");

        this.a(ItemStack.a(nbttagcompound1));
        if (this.l() == null) {
            this.J();
        }

    }

    public void d(EntityPlayer entityplayer) {
        if (!this.o.D) {
            ItemStack itemstack = this.l();
            int i0 = itemstack.b;

            if (this.d == 0 && (this.g == null || 6000 - this.c <= 200 || this.g.equals(entityplayer.d_())) && entityplayer.bg.a(itemstack)) {
                if (itemstack.b() == Item.a(Blocks.r)) {
                    entityplayer.b((StatBase) AchievementList.g);
                }

                if (itemstack.b() == Item.a(Blocks.s)) {
                    entityplayer.b((StatBase) AchievementList.g);
                }

                if (itemstack.b() == Items.aF) {
                    entityplayer.b((StatBase) AchievementList.t);
                }

                if (itemstack.b() == Items.i) {
                    entityplayer.b((StatBase) AchievementList.w);
                }

                if (itemstack.b() == Items.bv) {
                    entityplayer.b((StatBase) AchievementList.A);
                }

                if (itemstack.b() == Items.i && this.n() != null) {
                    EntityPlayer entityplayer1 = this.o.a(this.n());

                    if (entityplayer1 != null && entityplayer1 != entityplayer) {
                        entityplayer1.b((StatBase) AchievementList.x);
                    }
                }

                if (!this.R()) {
                    this.o.a((Entity) entityplayer, "random.pop", 0.2F, ((this.V.nextFloat() - this.V.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                }

                entityplayer.a((Entity) this, i0);
                if (itemstack.b <= 0) {
                    this.J();
                }
            }

        }
    }

    public String d_() {
        return this.k_() ? this.aL() : StatCollector.a("item." + this.l().a());
    }

    public boolean aE() {
        return false;
    }

    public void c(int i0) {
        super.c(i0);
        if (!this.o.D) {
            this.w();
        }

    }

    public ItemStack l() {
        ItemStack itemstack = this.H().f(10);

        if (itemstack == null) {
            if (this.o != null) {
                b.error("Item entity " + this.F() + " has no item?!");
            }

            return new ItemStack(Blocks.b);
        }
        else {
            return itemstack;
        }
    }

    public void a(ItemStack itemstack) {
        this.H().b(10, itemstack);
        this.H().i(10);
    }

    public String m() {
        return this.g;
    }

    public void b(String s0) {
        this.g = s0;
    }

    public String n() {
        return this.f;
    }

    public void c(String s0) {
        this.f = s0;
    }

    public void p() {
        this.d = 10;
    }

    public void q() {
        this.d = 0;
    }

    public void r() {
        this.d = 32767;
    }

    public void a(int i0) {
        this.d = i0;
    }

    public boolean s() {
        return this.d > 0;
    }

    public void u() {
        this.c = -6000;
    }

    public void v() {
        this.r();
        this.c = 5999;
    }

    // CanaryMod
    public CanaryEntityItem getEntityItem() {
        return (CanaryEntityItem) entity;
    }
//
}
