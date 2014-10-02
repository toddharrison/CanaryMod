package net.minecraft.entity.item;

import net.canarymod.api.entity.vehicle.CanaryHopperMinecart;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityMinecartContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerHopper;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.IHopper;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import java.util.List;

public class EntityMinecartHopper extends EntityMinecartContainer implements IHopper {

    private boolean a = true;
    public int b = -1; // CanaryMod: private -> public
    private BlockPos c;
   
    public EntityMinecartHopper(World world) {
        super(world);
        this.c = BlockPos.a;
        this.entity = new CanaryHopperMinecart(this); // Wrap entity
    }

    public EntityMinecartHopper(World world, double d0, double d1, double d2) {
        super(world, d0, d1, d2);
        this.c = BlockPos.a;
        this.entity = new CanaryHopperMinecart(this); // Wrap entity
    }

    public EntityMinecart.EnumMinecartType s() {
        return EntityMinecart.EnumMinecartType.HOPPER;
    }

    public IBlockState u() {
        return Blocks.cp.P();
    }

    public int w() {
        return 1;
    }

    public int n_() {
        return 5;
    }

    public boolean e(EntityPlayer entityplayer) {
        if (!this.o.D) {
            entityplayer.a((IInventory) this);
        }

        return true;
    }

    public void a(int i0, int i1, int i2, boolean flag0) {
        boolean flag1 = !flag0;

        if (flag1 != this.y()) {
            this.i(flag1);
        }

    }

    public boolean y() {
        return this.a;
    }

    public void i(boolean flag0) {
        this.a = flag0;
    }

    public World z() {
        return this.o;
    }

    public double A() {
        return this.s;
    }

    public double B() {
        return this.t;
    }

    public double C() {
        return this.u;
    }

    public void s_() {
        super.s_();
        if (!this.o.D && this.ai() && this.y()) {
            BlockPos blockpos = new BlockPos(this);

            if (blockpos.equals(this.c)) {
                --this.b;
            } else {
                this.m(0);
            }

            if (!this.E()) {
                this.m(0);
                if (this.D()) {
                    this.m(4);
                    this.o_();
                }
            }
        }

    }

    public boolean D() {
        if (TileEntityHopper.a((IHopper) this)) {
            return true;
        } else {
            List list = this.o.a(EntityItem.class, this.aQ().b(0.25D, 0.0D, 0.25D), IEntitySelector.a);

            if (list.size() > 0) {
                TileEntityHopper.a((IInventory) this, (EntityItem) list.get(0));
            }

            return false;
        }
    }

    public void a(DamageSource damagesource) {
        super.a(damagesource);
        this.a(Item.a((Block) Blocks.cp), 1, 0.0F);
    }

    protected void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("TransferCooldown", this.b);
    }

    protected void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.b = nbttagcompound.f("TransferCooldown");
    }

    public void m(int i0) {
        this.b = i0;
    }

    public boolean E() {
        return this.b > 0;
    }

    public String k() {
        return "minecraft:hopper";
    }

    public Container a(InventoryPlayer inventoryplayer, EntityPlayer entityplayer) {
        return new ContainerHopper(inventoryplayer, this, entityplayer);
    }
}
