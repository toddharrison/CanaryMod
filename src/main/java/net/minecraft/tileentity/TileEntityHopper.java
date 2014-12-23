package net.minecraft.tileentity;

import net.canarymod.api.world.blocks.CanaryHopperBlock;
import net.canarymod.hook.world.HopperTransferHook;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockHopper;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecartHopper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerHopper;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public class TileEntityHopper extends TileEntityLockable implements IHopper, IUpdatePlayerListBox {

    public ItemStack[] a = new ItemStack[5]; // CanaryMod: private to public
    private String f;
    public int g = -1; // CanaryMod: private to public

    public TileEntityHopper() {
        this.complexBlock = new CanaryHopperBlock(this); // CanaryMod: create once, use forever
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        NBTTagList nbttaglist = nbttagcompound.c("Items", 10);

        this.a = new ItemStack[this.n_()];
        if (nbttagcompound.b("CustomName", 8)) {
            this.f = nbttagcompound.j("CustomName");
        }

        this.g = nbttagcompound.f("TransferCooldown");

        for (int i0 = 0; i0 < nbttaglist.c(); ++i0) {
            NBTTagCompound nbttagcompound1 = nbttaglist.b(i0);
            byte b0 = nbttagcompound1.d("Slot");

            if (b0 >= 0 && b0 < this.a.length) {
                this.a[b0] = ItemStack.a(nbttagcompound1);
            }
        }
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i0 = 0; i0 < this.a.length; ++i0) {
            if (this.a[i0] != null) {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();

                nbttagcompound1.a("Slot", (byte)i0);
                this.a[i0].b(nbttagcompound1);
                nbttaglist.a((NBTBase)nbttagcompound1);
            }
        }

        nbttagcompound.a("Items", (NBTBase)nbttaglist);
        nbttagcompound.a("TransferCooldown", this.g);
        if (this.k_()) {
            nbttagcompound.a("CustomName", this.f);
        }
    }

    public void o_() {
        super.o_();
    }

    public int n_() {
        return this.a.length;
    }

    public ItemStack a(int i0) {
        return this.a[i0];
    }

    public ItemStack a(int i0, int i1) {
        if (this.a[i0] != null) {
            ItemStack itemstack;

            if (this.a[i0].b <= i1) {
                itemstack = this.a[i0];
                this.a[i0] = null;
                return itemstack;
            }
            else {
                itemstack = this.a[i0].a(i1);
                if (this.a[i0].b == 0) {
                    this.a[i0] = null;
                }

                return itemstack;
            }
        }
        else {
            return null;
        }
    }

    public ItemStack b(int i0) {
        if (this.a[i0] != null) {
            ItemStack itemstack = this.a[i0];

            this.a[i0] = null;
            return itemstack;
        }
        else {
            return null;
        }
    }

    public void a(int i0, ItemStack itemstack) {
        this.a[i0] = itemstack;
        if (itemstack != null && itemstack.b > this.p_()) {
            itemstack.b = this.p_();
        }
    }

    public String d_() {
        return this.k_() ? this.f : "container.hopper";
    }

    public boolean k_() {
        return this.f != null && this.f.length() > 0;
    }

    public void a(String s0) {
        this.f = s0;
    }

    public int p_() {
        return 64;
    }

    public boolean a(EntityPlayer entityplayer) {
        // CanaryMod: remote inventories
        if (getCanaryHopper().canOpenRemote()) {
            return true;
        }
        //

        return this.b.s(this.c) != this ? false : entityplayer.e((double)this.c.n() + 0.5D, (double)this.c.o() + 0.5D, (double)this.c.p() + 0.5D) <= 64.0D;
    }

    public void b(EntityPlayer entityplayer) {
    }

    public void c(EntityPlayer entityplayer) {
    }

    public boolean b(int i0, ItemStack itemstack) {
        return true;
    }

    public void c() {
        if (this.b != null && !this.b.D) {
            --this.g;
            if (!this.n()) {
                this.d(0);
                this.m();
            }
        }
    }

    public boolean m() {
        if (this.b != null && !this.b.D) {
            if (!this.n() && BlockHopper.f(this.u())) {
                boolean flag0 = false;

                if (!this.p()) {
                    flag0 = this.r();
                }

                if (!this.q()) {
                    flag0 = a((IHopper)this) || flag0;
                }

                if (flag0) {
                    this.d(8);
                    this.o_();
                    return true;
                }
            }

            return false;
        }
        else {
            return false;
        }
    }

    private boolean p() {
        ItemStack[] aitemstack = this.a;
        int i0 = aitemstack.length;

        for (int i1 = 0; i1 < i0; ++i1) {
            ItemStack itemstack = aitemstack[i1];

            if (itemstack != null) {
                return false;
            }
        }

        return true;
    }

    private boolean q() {
        ItemStack[] aitemstack = this.a;
        int i0 = aitemstack.length;

        for (int i1 = 0; i1 < i0; ++i1) {
            ItemStack itemstack = aitemstack[i1];

            if (itemstack == null || itemstack.b != itemstack.c()) {
                return false;
            }
        }

        return true;
    }

    private boolean r() {
        IInventory iinventory = this.G();

        if (iinventory == null) {
            return false;
        }
        else {
            EnumFacing enumfacing = BlockHopper.b(this.u()).d();

            if (this.a(iinventory, enumfacing)) {
                return false;
            }
            else {
                for (int i0 = 0; i0 < this.n_(); ++i0) {
                    if (this.a(i0) != null) {
                        ItemStack itemstack = this.a(i0).k();
                        // CanaryMod: Hopper Transfer hook
                        HopperTransferHook hook = (HopperTransferHook)new HopperTransferHook(getCanaryHopper(), new net.canarymod.api.inventory.CanaryItem(itemstack), false).call();
                        if (hook.isCanceled()) {
                            return false;
                        }
                        // CanaryMod: End

                        ItemStack itemstack1 = a(iinventory, this.a(i0, 1), enumfacing);

                        if (itemstack1 == null || itemstack1.b == 0) {
                            iinventory.o_();
                            return true;
                        }

                        this.a(i0, itemstack);
                    }
                }

                return false;
            }
        }
    }

    private boolean a(IInventory iinventory, EnumFacing enumfacing) {
        if (iinventory instanceof ISidedInventory) {
            ISidedInventory isidedinventory = (ISidedInventory)iinventory;
            int[] aint = isidedinventory.a(enumfacing);

            for (int i0 = 0; i0 < aint.length; ++i0) {
                ItemStack itemstack = isidedinventory.a(aint[i0]);

                if (itemstack == null || itemstack.b != itemstack.c()) {
                    return false;
                }
            }
        }
        else {
            int i1 = iinventory.n_();

            for (int i2 = 0; i2 < i1; ++i2) {
                ItemStack itemstack1 = iinventory.a(i2);

                if (itemstack1 == null || itemstack1.b != itemstack1.c()) {
                    return false;
                }
            }
        }

        return true;
    }

    private static boolean b(IInventory iinventory, EnumFacing enumfacing) {
        if (iinventory instanceof ISidedInventory) {
            ISidedInventory isidedinventory = (ISidedInventory)iinventory;
            int[] aint = isidedinventory.a(enumfacing);

            for (int i0 = 0; i0 < aint.length; ++i0) {
                if (isidedinventory.a(aint[i0]) != null) {
                    return false;
                }
            }
        }
        else {
            int i1 = iinventory.n_();

            for (int i2 = 0; i2 < i1; ++i2) {
                if (iinventory.a(i2) != null) {
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean a(IHopper ihopper) {
        IInventory iinventory = b(ihopper);

        if (iinventory != null) {
            EnumFacing enumfacing = EnumFacing.DOWN;

            if (b(iinventory, enumfacing)) {
                return false;
            }

            if (iinventory instanceof ISidedInventory) {
                ISidedInventory isidedinventory = (ISidedInventory)iinventory;
                int[] aint = isidedinventory.a(enumfacing);

                for (int i0 = 0; i0 < aint.length; ++i0) {
                    if (a(ihopper, iinventory, aint[i0], enumfacing)) {
                        return true;
                    }
                }
            }
            else {
                int i1 = iinventory.n_();

                for (int i2 = 0; i2 < i1; ++i2) {
                    if (a(ihopper, iinventory, i2, enumfacing)) {
                        return true;
                    }
                }
            }
        }
        else {
            EntityItem entityitem = a(ihopper.z(), ihopper.A(), ihopper.B() + 1.0D, ihopper.C());

            if (entityitem != null) {
                return a((IInventory)ihopper, entityitem);
            }
        }

        return false;
    }

    private static boolean a(IHopper ihopper, IInventory iinventory, int i0, EnumFacing enumfacing) {
        ItemStack itemstack = iinventory.a(i0);

        if (itemstack != null && b(iinventory, itemstack, i0, enumfacing)) {
            ItemStack itemstack1 = itemstack.k();
            // CanaryMod: Hopper Transfer hook.
            net.canarymod.api.inventory.Hopper hookHopper = null;

            if (ihopper instanceof TileEntityHopper) {
                hookHopper = (net.canarymod.api.inventory.Hopper)((TileEntityHopper)ihopper).getCanaryHopper();
            }
            else if (ihopper instanceof EntityMinecartHopper) {
                hookHopper = (net.canarymod.api.inventory.Hopper)((EntityMinecartHopper)ihopper).getCanaryEntity();
            }
            HopperTransferHook hook = (HopperTransferHook)new HopperTransferHook(hookHopper, new net.canarymod.api.inventory.CanaryItem(itemstack), true).call();
            if (hook.isCanceled()) {
                return false;
            }// CanaryMod: End

            ItemStack itemstack2 = a(ihopper, iinventory.a(i0, 1), (EnumFacing)null);

            if (itemstack2 == null || itemstack2.b == 0) {
                iinventory.o_();
                return true;
            }

            iinventory.a(i0, itemstack1);
        }

        return false;
    }

    public static boolean a(IInventory iinventory, EntityItem entityitem) {
        boolean flag0 = false;

        if (entityitem == null) {
            return false;
        }
        else {
            ItemStack itemstack = entityitem.l().k();
            ItemStack itemstack1 = a(iinventory, itemstack, (EnumFacing)null);

            if (itemstack1 != null && itemstack1.b != 0) {
                entityitem.a(itemstack1);
            }
            else {
                flag0 = true;
                entityitem.J();
            }

            return flag0;
        }
    }

    public static ItemStack a(IInventory iinventory, ItemStack itemstack, EnumFacing enumfacing) {
        if (iinventory instanceof ISidedInventory && enumfacing != null) {
            ISidedInventory isidedinventory = (ISidedInventory)iinventory;
            int[] aint = isidedinventory.a(enumfacing);

            for (int i0 = 0; i0 < aint.length && itemstack != null && itemstack.b > 0; ++i0) {
                itemstack = c(iinventory, itemstack, aint[i0], enumfacing);
            }
        }
        else {
            int i1 = iinventory.n_();

            for (int i2 = 0; i2 < i1 && itemstack != null && itemstack.b > 0; ++i2) {
                itemstack = c(iinventory, itemstack, i2, enumfacing);
            }
        }

        if (itemstack != null && itemstack.b == 0) {
            itemstack = null;
        }

        return itemstack;
    }

    private static boolean a(IInventory iinventory, ItemStack itemstack, int i0, EnumFacing enumfacing) {
        return !iinventory.b(i0, itemstack) ? false : !(iinventory instanceof ISidedInventory) || ((ISidedInventory)iinventory).a(i0, itemstack, enumfacing);
    }

    private static boolean b(IInventory iinventory, ItemStack itemstack, int i0, EnumFacing enumfacing) {
        return !(iinventory instanceof ISidedInventory) || ((ISidedInventory)iinventory).b(i0, itemstack, enumfacing);
    }

    private static ItemStack c(IInventory iinventory, ItemStack itemstack, int i0, EnumFacing enumfacing) {
        ItemStack itemstack1 = iinventory.a(i0);

        if (a(iinventory, itemstack, i0, enumfacing)) {
            boolean flag0 = false;

            if (itemstack1 == null) {
                iinventory.a(i0, itemstack);
                itemstack = null;
                flag0 = true;
            }
            else if (a(itemstack1, itemstack)) {
                int i1 = itemstack.c() - itemstack1.b;
                int i2 = Math.min(itemstack.b, i1);

                itemstack.b -= i2;
                itemstack1.b += i2;
                flag0 = i2 > 0;
            }

            if (flag0) {
                if (iinventory instanceof TileEntityHopper) {
                    TileEntityHopper tileentityhopper = (TileEntityHopper)iinventory;

                    if (tileentityhopper.o()) {
                        tileentityhopper.d(8);
                    }

                    iinventory.o_();
                }

                iinventory.o_();
            }
        }

        return itemstack;
    }

    private IInventory G() {
        EnumFacing enumfacing = BlockHopper.b(this.u());

        return b(this.z(), (double)(this.c.n() + enumfacing.g()), (double)(this.c.o() + enumfacing.h()), (double)(this.c.p() + enumfacing.i()));
    }

    public static IInventory b(IHopper ihopper) {
        return b(ihopper.z(), ihopper.A(), ihopper.B() + 1.0D, ihopper.C());
    }

    public static EntityItem a(World world, double d0, double d1, double d2) {
        List list = world.a(EntityItem.class, new AxisAlignedBB(d0, d1, d2, d0 + 1.0D, d1 + 1.0D, d2 + 1.0D), IEntitySelector.a);

        return list.size() > 0 ? (EntityItem)list.get(0) : null;
    }

    public static IInventory b(World world, double d0, double d1, double d2) {
        Object object = null;
        int i0 = MathHelper.c(d0);
        int i1 = MathHelper.c(d1);
        int i2 = MathHelper.c(d2);
        BlockPos blockpos = new BlockPos(i0, i1, i2);
        TileEntity tileentity = world.s(new BlockPos(i0, i1, i2));

        if (tileentity instanceof IInventory) {
            object = (IInventory)tileentity;
            if (object instanceof TileEntityChest) {
                Block block = world.p(new BlockPos(i0, i1, i2)).c();

                if (block instanceof BlockChest) {
                    object = ((BlockChest)block).d(world, blockpos);
                }
            }
        }

        if (object == null) {
            List list = world.a((Entity)null, new AxisAlignedBB(d0, d1, d2, d0 + 1.0D, d1 + 1.0D, d2 + 1.0D), IEntitySelector.c);

            if (list.size() > 0) {
                object = (IInventory)list.get(world.s.nextInt(list.size()));
            }
        }

        return (IInventory)object;
    }

    private static boolean a(ItemStack itemstack, ItemStack itemstack1) {
        return itemstack.b() != itemstack1.b() ? false : (itemstack.i() != itemstack1.i() ? false : (itemstack.b > itemstack.c() ? false : ItemStack.a(itemstack, itemstack1)));
    }

    public double A() {
        return (double)this.c.n();
    }

    public double B() {
        return (double)this.c.o();
    }

    public double C() {
        return (double)this.c.p();
    }

    public void d(int i0) {
        this.g = i0;
    }

    public boolean n() {
        return this.g > 0;
    }

    public boolean o() {
        return this.g <= 1;
    }

    public String k() {
        return "minecraft:hopper";
    }

    public Container a(InventoryPlayer inventoryplayer, EntityPlayer entityplayer) {
        return new ContainerHopper(inventoryplayer, this, entityplayer);
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
        for (int i0 = 0; i0 < this.a.length; ++i0) {
            this.a[i0] = null;
        }
    }

    // CanaryMod
    public CanaryHopperBlock getCanaryHopper() {
        return (CanaryHopperBlock)complexBlock;
    }

    public IInventory getInputInventory() {
        return b(this);
    }

    public IInventory getOutputInventory() {
        // TODO: Find replacement
        return null;
        //return this.l();
    }
}
