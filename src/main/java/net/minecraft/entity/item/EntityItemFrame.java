package net.minecraft.entity.item;

import net.canarymod.api.CanaryDamageSource;
import net.canarymod.api.DamageType;
import net.canarymod.api.entity.hanging.CanaryItemFrame;
import net.canarymod.api.entity.hanging.HangingEntity;
import net.canarymod.api.entity.living.humanoid.Player;
import net.canarymod.hook.entity.HangingEntityDestroyHook;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapData;


public class EntityItemFrame extends EntityHanging {

    public float c = 1.0F; // CanaryMod: private -> public

    public EntityItemFrame(World world) {
        super(world);
        this.entity = new CanaryItemFrame(this); // CanaryMod: Wrap Entity
    }

    public EntityItemFrame(World world, BlockPos blockpos, EnumFacing enumfacing) {
        super(world, blockpos);
        this.a(enumfacing);
        this.entity = new CanaryItemFrame(this); // CanaryMod: Wrap Entity
    }

    protected void h() {
        this.H().a(8, 5);
        this.H().a(9, Byte.valueOf((byte) 0));
    }

    public float ao() {
        return 0.0F;
    }

    public boolean a(DamageSource damagesource, float f0) {
        if (this.b(damagesource)) {
            return false;
        }
        else if (!damagesource.c() && this.o() != null) {
            if (!this.o.D) {
                this.a(damagesource.j(), false);
                this.a((ItemStack) null);
            }

            return true;
        }
        else {
            return super.a(damagesource, f0);
        }
    }

    public int l() {
        return 12;
    }

    public int m() {
        return 12;
    }

    public void b(Entity entity) {
        this.a(entity, true);
    }

    public void a(Entity entity, boolean flag0) {
        //CanaryMod start
        HangingEntityDestroyHook hook;
        if (entity instanceof EntityPlayer) {
            hook = (HangingEntityDestroyHook) new HangingEntityDestroyHook((HangingEntity) this.getCanaryEntity(), (Player) entity.getCanaryEntity(), CanaryDamageSource.getDamageSourceFromType(DamageType.GENERIC)).call();
        }
        else {
            hook = (HangingEntityDestroyHook) new HangingEntityDestroyHook((HangingEntity) this.getCanaryEntity(), null, CanaryDamageSource.getDamageSourceFromType(DamageType.GENERIC)).call();
        }
        if (hook.isCanceled()) {
            return;
        }
        //CanaryMod end

        if (this.o.Q().b("doTileDrops")) {
            ItemStack itemstack = this.o();

            if (entity instanceof EntityPlayer) {
                EntityPlayer entityplayer = (EntityPlayer) entity;

                if (entityplayer.by.d) {
                    this.b(itemstack);
                    return;
                }
            }

            if (flag0) {
                this.a(new ItemStack(Items.bP), 0.0F);
            }

            if (itemstack != null && this.V.nextFloat() < this.c) {
                itemstack = itemstack.k();
                this.b(itemstack);
                this.a(itemstack, 0.0F);
            }

        }
    }

    private void b(ItemStack itemstack) {
        if (itemstack != null) {
            if (itemstack.b() == Items.bd) {
                MapData mapdata = ((ItemMap) itemstack.b()).a(itemstack, this.o);

                mapdata.h.remove("frame-" + this.F());
            }

            itemstack.a((EntityItemFrame) null);
        }
    }

    public ItemStack o() {
        return this.H().f(8);
    }

    public void a(ItemStack itemstack) {
        this.a(itemstack, true);
    }

    private void a(ItemStack itemstack, boolean flag0) {
        if (itemstack != null) {
            itemstack = itemstack.k();
            itemstack.b = 1;
            itemstack.a(this);
        }

        this.H().b(8, itemstack);
        this.H().i(8);
        if (flag0 && this.a != null) {
            this.o.e(this.a, Blocks.a);
        }

    }

    public int p() {
        return this.H().a(9);
    }

    public void a(int i0) {
        this.a(i0, true);
    }

    private void a(int i0, boolean flag0) {
        this.H().b(9, Byte.valueOf((byte) (i0 % 8)));
        if (flag0 && this.a != null) {
            this.o.e(this.a, Blocks.a);
        }

    }

    public void b(NBTTagCompound nbttagcompound) {
        if (this.o() != null) {
            nbttagcompound.a("Item", (NBTBase) this.o().b(new NBTTagCompound()));
            nbttagcompound.a("ItemRotation", (byte) this.p());
            nbttagcompound.a("ItemDropChance", this.c);
        }

        super.b(nbttagcompound);
    }

    public void a(NBTTagCompound nbttagcompound) {
        NBTTagCompound nbttagcompound1 = nbttagcompound.m("Item");

        if (nbttagcompound1 != null && !nbttagcompound1.c_()) {
            this.a(ItemStack.a(nbttagcompound1), false);
            this.a(nbttagcompound.d("ItemRotation"), false);
            if (nbttagcompound.b("ItemDropChance", 99)) {
                this.c = nbttagcompound.h("ItemDropChance");
            }

            if (nbttagcompound.c("Direction")) {
                this.a(this.p() * 2, false);
            }
        }

        super.a(nbttagcompound);
    }

    public boolean e(EntityPlayer entityplayer) {
        if (this.o() == null) {
            ItemStack itemstack = entityplayer.bz();

            if (itemstack != null && !this.o.D) {
                this.a(itemstack);
                if (!entityplayer.by.d && --itemstack.b <= 0) {
                    entityplayer.bg.a(entityplayer.bg.c, (ItemStack) null);
                }
            }
        }
        else if (!this.o.D) {
            this.a(this.p() + 1);
        }

        return true;
    }

    public int q() {
        return this.o() == null ? 0 : this.p() % 8 + 1;
    }
}
