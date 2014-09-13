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
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;

public class EntityItem extends Entity {

    private static final Logger d = LogManager.getLogger();
    public int a;
    public int b;
    public int e; // CanaryMod: private => public; health
    private String f;
    private String g;
    public float c;

    public EntityItem(World world, double d0, double d1, double d2) {
        super(world);
        this.e = 5;
        this.c = (float) (Math.random() * 3.141592653589793D * 2.0D);
        this.a(0.25F, 0.25F);
        this.L = this.N / 2.0F;
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

    protected boolean g_() {
        return false;
    }

    public EntityItem(World world) {
        super(world);
        this.e = 5;
        this.c = (float) (Math.random() * 3.141592653589793D * 2.0D);
        this.a(0.25F, 0.25F);
        this.L = this.N / 2.0F;
        this.entity = new CanaryEntityItem(this); // CanaryMod: Wrap Entity
    }

    protected void c() {
        this.z().a(10, 5);
    }

    public void h() {
        if (this.f() == null) {
            this.B();
        } else {
            super.h();
            if (this.b > 0) {
                --this.b;
            }

            boolean tmpTouch = this.D; // CanaryMod

            this.p = this.s;
            this.q = this.t;
            this.r = this.u;
            this.w -= 0.03999999910593033D;
            this.X = this.j(this.s, (this.C.b + this.C.e) / 2.0D, this.u);
            this.d(this.v, this.w, this.x);
            boolean flag0 = (int) this.p != (int) this.s || (int) this.q != (int) this.t || (int) this.r != (int) this.u;

            if (flag0 || this.aa % 25 == 0) {
                if (this.o.a(MathHelper.c(this.s), MathHelper.c(this.t), MathHelper.c(this.u)).o() == Material.i) {
                    this.w = 0.20000000298023224D;
                    this.v = (double) ((this.Z.nextFloat() - this.Z.nextFloat()) * 0.2F);
                    this.x = (double) ((this.Z.nextFloat() - this.Z.nextFloat()) * 0.2F);
                    this.a("random.fizz", 0.4F, 2.0F + this.Z.nextFloat() * 0.4F);
                }

                if (!this.o.E) {
                    this.k();
                }
            }

            float f0 = 0.98F;

            if (this.D) {
                f0 = this.o.a(MathHelper.c(this.s), MathHelper.c(this.C.b) - 1, MathHelper.c(this.u)).K * 0.98F;

                // CanaryMod: ItemTouchGround
                // It does touch the ground now, but didn't in last tick
                if (!tmpTouch) {
                    ItemTouchGroundHook hook = (ItemTouchGroundHook) new ItemTouchGroundHook((net.canarymod.api.entity.EntityItem) getCanaryEntity()).call();
                    if (hook.isCanceled()) {
                        this.B(); // kill the item
                    }
                }
                //
            }

            this.v *= (double) f0;
            this.w *= 0.9800000190734863D;
            this.x *= (double) f0;
            if (this.D) {
                this.w *= -0.5D;
            }

            ++this.a;
            if (!this.o.E && this.a >= 6000) {
                // CanaryMod: EntityDespawn
                EntityDespawnHook hook = (EntityDespawnHook) new EntityDespawnHook(getCanaryEntity()).call();
                if (!hook.isCanceled()) {
                    this.B();
                } else {
                    this.a = 0; // Reset Age
                }
                //
            }
        }
    }

    private void k() {
        Iterator iterator = this.o.a(EntityItem.class, this.C.b(0.5D, 0.0D, 0.5D)).iterator();

        while (iterator.hasNext()) {
            EntityItem entityitem = (EntityItem) iterator.next();

            this.a(entityitem);
        }
    }

    public boolean a(EntityItem entityitem) {
        if (entityitem == this) {
            return false;
        } else if (entityitem.Z() && this.Z()) {
            ItemStack itemstack = this.f();
            ItemStack itemstack1 = entityitem.f();

            if (itemstack1.b() != itemstack.b()) {
                return false;
            } else if (itemstack1.p() ^ itemstack.p()) {
                return false;
            } else if (itemstack1.p() && !itemstack1.q().equals(itemstack.q())) {
                return false;
            } else if (itemstack1.b() == null) {
                return false;
            } else if (itemstack1.b().n() && itemstack1.k() != itemstack.k()) {
                return false;
            } else if (itemstack1.b < itemstack.b) {
                return entityitem.a(this);
            } else if (itemstack1.b + itemstack.b > itemstack1.e()) {
                return false;
            } else {
                itemstack1.b += itemstack.b;
                entityitem.b = Math.max(entityitem.b, this.b);
                entityitem.a = Math.min(entityitem.a, this.a);
                entityitem.a(itemstack1);
                this.B();
                return true;
            }
        } else {
            return false;
        }
    }

    public void e() {
        this.a = 4800;
    }

    public boolean N() {
        return this.o.a(this.C, Material.h, (Entity) this);
    }

    protected void f(int i0) {
        this.a(DamageSource.a, (float) i0);
    }

    public boolean a(DamageSource damagesource, float f0) {
        if (this.aw()) {
            return false;
        } else if (this.f() != null && this.f().b() == Items.bN && damagesource.c()) {
            return false;
        } else {
            this.Q();
            this.e = (int) ((float) this.e - f0);
            if (this.e <= 0) {
                this.B();
            }

            return false;
        }
    }

    public void b(NBTTagCompound nbttagcompound) {
        nbttagcompound.a("Health", (short) ((byte) this.e));
        nbttagcompound.a("Age", (short) this.a);
        if (this.j() != null) {
            nbttagcompound.a("Thrower", this.f);
        }
        if (this.i() != null) {
            nbttagcompound.a("Owner", this.g);
        }

        if (this.f() != null) {
            nbttagcompound.a("Item", (NBTBase) this.f().b(new NBTTagCompound()));
        }
    }

    public void a(NBTTagCompound nbttagcompound) {
        this.e = nbttagcompound.e("Health") & 255;
        this.a = nbttagcompound.e("Age");
        if (nbttagcompound.c("Owner")) {
            this.g = nbttagcompound.j("Owner");
        }

        if (nbttagcompound.c("Thrower")) {
            this.f = nbttagcompound.j("Thrower");
        }

        NBTTagCompound nbttagcompound1 = nbttagcompound.m("Item");

        this.a(ItemStack.a(nbttagcompound1));
        if (this.f() == null) {
            this.B();
        }
    }

    public void b_(EntityPlayer entityplayer) {
        if (!this.o.E) {
            ItemStack itemstack = this.f();
            int i0 = itemstack.b;

            if (this.b == 0 &&
                    (this.g == null || 6000 - this.a <= 200 || this.g.equals(entityplayer.b_()))
                    && entityplayer.bm.canPickup(this)) { // CanaryMod: simulate pickup first
                if (entityplayer.bm.a(itemstack)) {
                    if (itemstack.b() == Item.a(Blocks.r)) {
                        entityplayer.a((StatBase) AchievementList.g);
                    }

                    if (itemstack.b() == Item.a(Blocks.s)) {
                        entityplayer.a((StatBase) AchievementList.g);
                    }

                    if (itemstack.b() == Items.aA) {
                        entityplayer.a((StatBase) AchievementList.t);
                    }

                    if (itemstack.b() == Items.i) {
                        entityplayer.a((StatBase) AchievementList.w);
                    }

                    if (itemstack.b() == Items.bj) {
                        entityplayer.a((StatBase) AchievementList.A);
                    }

                    if (itemstack.b() == Items.i && this.j() != null) {
                        EntityPlayer entityplayer1 = this.o.a(this.j());

                        if (entityplayer1 != null && entityplayer1 != entityplayer) {
                            entityplayer1.a((StatBase) AchievementList.x);
                        }
                    }

                    this.o.a((Entity) entityplayer, "random.pop", 0.2F, ((this.Z.nextFloat() - this.Z.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                    entityplayer.a((Entity) this, i0);
                    if (itemstack.b <= 0) {
                        this.B();
                    }
                }
            }
        }
    }

    public String b_() {
        return StatCollector.a("item." + this.f().a());
    }

    public boolean av() {
        return false;
    }

    public void b(int i0) {
        super.b(i0);
        if (!this.o.E) {
            this.k();
        }
    }

    public ItemStack f() {
        ItemStack itemstack = this.z().f(10);

        return itemstack == null ? new ItemStack(Blocks.b) : itemstack;
    }

    public void a(ItemStack itemstack) {
        this.z().b(10, itemstack);
        this.z().h(10);
    }

    public String i() {
        return this.g;
    }

    public void a(String s0) {
        this.g = s0;
    }

    public String j() {
        return this.f;
    }

    public void b(String s0) {
        this.f = s0;
    }

    // CanaryMod
    public CanaryEntityItem getEntityItem() {
        return (CanaryEntityItem) entity;
    }
//
}
