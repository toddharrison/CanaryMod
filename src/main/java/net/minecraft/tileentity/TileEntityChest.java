package net.minecraft.tileentity;

import net.canarymod.api.world.blocks.CanaryChest;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

import java.util.Iterator;
import java.util.List;

public class TileEntityChest extends TileEntityLockable implements IUpdatePlayerListBox, IInventory {

    public ItemStack[] m = new ItemStack[27]; // CanaryMod: private => public
    public boolean a;
    public TileEntityChest f;
    public TileEntityChest g;
    public TileEntityChest h;
    public TileEntityChest i;
    public float j;
    public float k;
    public int l;
    private int n;
    private int o = -1;
    private String p;

    public TileEntityChest() {
        this.complexBlock = new CanaryChest(this); // CanaryMod: create once, use forever
    }

    public int n_() {
        return 27;
    }

    public ItemStack a(int i0) {
        return this.m[i0];
    }

    public ItemStack a(int i0, int i1) {
        if (this.m[i0] != null) {
            ItemStack itemstack;

            if (this.m[i0].b <= i1) {
                itemstack = this.m[i0];
                this.m[i0] = null;
                this.o_();
                return itemstack;
            }
            else {
                itemstack = this.m[i0].a(i1);
                if (this.m[i0].b == 0) {
                    this.m[i0] = null;
                }

                this.o_();
                return itemstack;
            }
        }
        else {
            return null;
        }
    }

    public ItemStack b(int i0) {
        if (this.m[i0] != null) {
            ItemStack itemstack = this.m[i0];

            this.m[i0] = null;
            return itemstack;
        }
        else {
            return null;
        }
    }

    public void a(int i0, ItemStack itemstack) {
        this.m[i0] = itemstack;
        if (itemstack != null && itemstack.b > this.p_()) {
            itemstack.b = this.p_();
        }

        this.o_();
    }

    public String d_() {
        return this.k_() ? this.p : "container.chest";
    }

    public boolean k_() {
        return this.p != null && this.p.length() > 0;
    }

    public void a(String s0) {
        this.p = s0;
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        NBTTagList nbttaglist = nbttagcompound.c("Items", 10);

        this.m = new ItemStack[this.n_()];
        if (nbttagcompound.b("CustomName", 8)) {
            this.p = nbttagcompound.j("CustomName");
        }

        for (int i0 = 0; i0 < nbttaglist.c(); ++i0) {
            NBTTagCompound nbttagcompound1 = nbttaglist.b(i0);
            int i1 = nbttagcompound1.d("Slot") & 255;

            if (i1 >= 0 && i1 < this.m.length) {
                this.m[i1] = ItemStack.a(nbttagcompound1);
            }
        }
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i0 = 0; i0 < this.m.length; ++i0) {
            if (this.m[i0] != null) {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();

                nbttagcompound1.a("Slot", (byte)i0);
                this.m[i0].b(nbttagcompound1);
                nbttaglist.a((NBTBase)nbttagcompound1);
            }
        }

        nbttagcompound.a("Items", (NBTBase)nbttaglist);
        if (this.k_()) {
            nbttagcompound.a("CustomName", this.p);
        }
    }

    public int p_() {
        return 64;
    }

    public boolean a(EntityPlayer entityplayer) {
        return this.b.s(this.c) != this ? false : entityplayer.e((double)this.c.n() + 0.5D, (double)this.c.o() + 0.5D, (double)this.c.p() + 0.5D) <= 64.0D;
    }

    public void E() {
        super.E();
        this.a = false;
    }

    private void a(TileEntityChest tileentitychest, EnumFacing enumfacing) {
        if (tileentitychest.x()) {
            this.a = false;
        }
        else if (this.a) {
            switch (TileEntityChest.SwitchEnumFacing.a[enumfacing.ordinal()]) {

                case 1:
                    if (this.f != tileentitychest) {
                        this.a = false;
                    }
                    break;

                case 2:
                    if (this.i != tileentitychest) {
                        this.a = false;
                    }
                    break;

                case 3:
                    if (this.g != tileentitychest) {
                        this.a = false;
                    }
                    break;

                case 4:
                    if (this.h != tileentitychest) {
                        this.a = false;
                    }
            }
        }
    }

    public void m() {
        if (!this.a) {
            this.a = true;
            this.h = this.a(EnumFacing.WEST);
            this.g = this.a(EnumFacing.EAST);
            this.f = this.a(EnumFacing.NORTH);
            this.i = this.a(EnumFacing.SOUTH);
        }
    }

    protected TileEntityChest a(EnumFacing enumfacing) {
        BlockPos blockpos = this.c.a(enumfacing);

        if (this.b(blockpos)) {
            TileEntity tileentity = this.b.s(blockpos);

            if (tileentity instanceof TileEntityChest) {
                TileEntityChest tileentitychest = (TileEntityChest)tileentity;

                tileentitychest.a(this, enumfacing.d());
                return tileentitychest;
            }
        }

        return null;
    }

    private boolean b(BlockPos blockpos) {
        if (this.b == null) {
            return false;
        }
        else {
            Block block = this.b.p(blockpos).c();

            return block instanceof BlockChest && ((BlockChest)block).b == this.n();
        }
    }

    public void c() {
        this.m();
        int i0 = this.c.n();
        int i1 = this.c.o();
        int i2 = this.c.p();

        ++this.n;
        float f0;

        if (!this.b.D && this.l != 0 && (this.n + i0 + i1 + i2) % 200 == 0) {
            this.l = 0;
            f0 = 5.0F;
            List list = this.b.a(EntityPlayer.class, new AxisAlignedBB((double)((float)i0 - f0), (double)((float)i1 - f0), (double)((float)i2 - f0), (double)((float)(i0 + 1) + f0), (double)((float)(i1 + 1) + f0), (double)((float)(i2 + 1) + f0)));
            Iterator iterator = list.iterator();

            while (iterator.hasNext()) {
                EntityPlayer entityplayer = (EntityPlayer)iterator.next();

                if (entityplayer.bi instanceof ContainerChest) {
                    IInventory iinventory = ((ContainerChest)entityplayer.bi).e();

                    if (iinventory == this || iinventory instanceof InventoryLargeChest && ((InventoryLargeChest)iinventory).a((IInventory)this)) {
                        ++this.l;
                    }
                }
            }
        }

        this.k = this.j;
        f0 = 0.1F;
        double d0;

        if (this.l > 0 && this.j == 0.0F && this.f == null && this.h == null) {
            double d1 = (double)i0 + 0.5D;

            d0 = (double)i2 + 0.5D;
            if (this.i != null) {
                d0 += 0.5D;
            }

            if (this.g != null) {
                d1 += 0.5D;
            }

            this.b.a(d1, (double)i1 + 0.5D, d0, "random.chestopen", 0.5F, this.b.s.nextFloat() * 0.1F + 0.9F);
        }

        if (this.l == 0 && this.j > 0.0F || this.l > 0 && this.j < 1.0F) {
            float f1 = this.j;

            if (this.l > 0) {
                this.j += f0;
            }
            else {
                this.j -= f0;
            }

            if (this.j > 1.0F) {
                this.j = 1.0F;
            }

            float f2 = 0.5F;

            if (this.j < f2 && f1 >= f2 && this.f == null && this.h == null) {
                d0 = (double)i0 + 0.5D;
                double d2 = (double)i2 + 0.5D;

                if (this.i != null) {
                    d2 += 0.5D;
                }

                if (this.g != null) {
                    d0 += 0.5D;
                }

                this.b.a(d0, (double)i1 + 0.5D, d2, "random.chestclosed", 0.5F, this.b.s.nextFloat() * 0.1F + 0.9F);
            }

            if (this.j < 0.0F) {
                this.j = 0.0F;
            }
        }
    }

    public boolean c(int i0, int i1) {
        if (i0 == 1) {
            this.l = i1;
            return true;
        }
        else {
            return super.c(i0, i1);
        }
    }

    public void b(EntityPlayer entityplayer) {
        if (!entityplayer.v()) {
            if (this.l < 0) {
                this.l = 0;
            }

            ++this.l;
            this.b.c(this.c, this.w(), 1, this.l);
            this.b.c(this.c, this.w());
            this.b.c(this.c.b(), this.w());
        }
    }

    public void c(EntityPlayer entityplayer) {
        if (!entityplayer.v() && this.w() instanceof BlockChest) {
            --this.l;
            this.b.c(this.c, this.w(), 1, this.l);
            this.b.c(this.c, this.w());
            this.b.c(this.c.b(), this.w());
        }
    }

    public boolean b(int i0, ItemStack itemstack) {
        return true;
    }

    public void y() {
        super.y();
        this.E();
        this.m();
    }

    public int n() {
        if (this.o == -1) {
            if (this.b == null || !(this.w() instanceof BlockChest)) {
                return 0;
            }

            this.o = ((BlockChest)this.w()).b;
        }

        return this.o;
    }

    public String k() {
        return "minecraft:chest";
    }

    public Container a(InventoryPlayer inventoryplayer, EntityPlayer entityplayer) {
        return new ContainerChest(inventoryplayer, this, entityplayer);
    }

    public int a_(int i0) {
        return 0;
    }

    public void b(int i0, int i1) {
    }

    public int g() {
        return 0;
    }

    public void l() {
        for (int i0 = 0; i0 < this.m.length; ++i0) {
            this.m[i0] = null;
        }
    }

    static final class SwitchEnumFacing {

        static final int[] a = new int[EnumFacing.values().length];

        static {
            try {
                a[EnumFacing.NORTH.ordinal()] = 1;
            }
            catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                a[EnumFacing.SOUTH.ordinal()] = 2;
            }
            catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                a[EnumFacing.EAST.ordinal()] = 3;
            }
            catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                a[EnumFacing.WEST.ordinal()] = 4;
            }
            catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }
        }
    }

    // CanaryMod
    public CanaryChest getCanaryChest() {
        return (CanaryChest)complexBlock;
    }
}
