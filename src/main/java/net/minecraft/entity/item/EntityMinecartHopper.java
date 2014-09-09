package net.minecraft.entity.item;

import net.canarymod.api.entity.vehicle.CanaryHopperMinecart;
import net.minecraft.block.Block;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.IHopper;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import java.util.List;

public class EntityMinecartHopper extends EntityMinecartContainer implements IHopper {

    private boolean a = true;
    public int b = -1; // CanaryMod: private -> public

    public EntityMinecartHopper(World world) {
        super(world);
        this.entity = new CanaryHopperMinecart(this); // Wrap entity
    }

    public EntityMinecartHopper(World world, double d0, double d1, double d2) {
        super(world, d0, d1, d2);
        this.entity = new CanaryHopperMinecart(this); // Wrap entity
    }

    public int m() {
        return 5;
    }

    public Block o() {
        return Blocks.bZ;
    }

    public int s() {
        return 1;
    }

    public int a() {
        return 5;
    }

    public boolean c(EntityPlayer entityplayer) {
        if (!this.o.E) {
            entityplayer.a(this);
        }

        return true;
    }

    public void a(int i0, int i1, int i2, boolean flag0) {
        boolean flag1 = !flag0;

        if (flag1 != this.v()) {
            this.f(flag1);
        }
    }

    public boolean v() {
        return this.a;
    }

    public void f(boolean flag0) {
        this.a = flag0;
    }

    public World w() {
        return this.o;
    }

    public double x() {
        return this.s;
    }

    public double aD() {
        return this.t;
    }

    public double aE() {
        return this.u;
    }

    public void h() {
        super.h();
        if (!this.o.E && this.Z() && this.v()) {
            --this.b;
            if (!this.aG()) {
                this.n(0);
                if (this.aF()) {
                    this.n(4);
                    this.e();
                }
            }
        }
    }

    public boolean aF() {
        if (TileEntityHopper.a((IHopper) this)) {
            return true;
        } else {
            List list = this.o.a(EntityItem.class, this.C.b(0.25D, 0.0D, 0.25D), IEntitySelector.a);

            if (list.size() > 0) {
                TileEntityHopper.a((IInventory) this, (EntityItem) list.get(0));
            }

            return false;
        }
    }

    public void a(DamageSource damagesource) {
        super.a(damagesource);
        this.a(Item.a((Block) Blocks.bZ), 1, 0.0F);
    }

    protected void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("TransferCooldown", this.b);
    }

    protected void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.b = nbttagcompound.f("TransferCooldown");
    }

    public void n(int i0) {
        this.b = i0;
    }

    public boolean aG() {
        return this.b > 0;
    }
}
