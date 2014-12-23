package net.minecraft.tileentity;

import net.canarymod.api.world.blocks.CanaryFurnace;
import net.canarymod.hook.world.SmeltBeginHook;
import net.canarymod.hook.world.SmeltHook;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.item.*;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;

public class TileEntityFurnace extends TileEntityLockable implements IUpdatePlayerListBox, ISidedInventory {

    private static final int[] a = new int[]{0};
    private static final int[] f = new int[]{2, 1};
    private static final int[] g = new int[]{1};
    public ItemStack[] h = new ItemStack[3]; // CanaryMod: private => public
    private int i;
    private int j;
    private int k;
    private int l;
    private String m;

    public TileEntityFurnace() {
        this.complexBlock = new CanaryFurnace(this); // CanaryMod: create once, use forever
    }

    public int n_() {
        return this.h.length;
    }

    public ItemStack a(int i0) {
        return this.h[i0];
    }

    public ItemStack a(int i0, int i1) {
        if (this.h[i0] != null) {
            ItemStack itemstack;

            if (this.h[i0].b <= i1) {
                itemstack = this.h[i0];
                this.h[i0] = null;
                return itemstack;
            }
            else {
                itemstack = this.h[i0].a(i1);
                if (this.h[i0].b == 0) {
                    this.h[i0] = null;
                }

                return itemstack;
            }
        }
        else {
            return null;
        }
    }

    public ItemStack b(int i0) {
        if (this.h[i0] != null) {
            ItemStack itemstack = this.h[i0];

            this.h[i0] = null;
            return itemstack;
        }
        else {
            return null;
        }
    }

    public void a(int i0, ItemStack itemstack) {
        boolean flag0 = itemstack != null && itemstack.a(this.h[i0]) && ItemStack.a(itemstack, this.h[i0]);

        this.h[i0] = itemstack;
        if (itemstack != null && itemstack.b > this.p_()) {
            itemstack.b = this.p_();
        }
        if (i0 == 0 && !flag0) {
            this.l = this.a(itemstack);
            this.k = 0;
            this.o_();
        }
    }

    public String d_() {
        return this.k_() ? this.m : "container.furnace";
    }

    public boolean k_() {
        return this.m != null && this.m.length() > 0;
    }

    public void a(String s0) {
        this.m = s0;
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        NBTTagList nbttaglist = nbttagcompound.c("Items", 10);

        this.h = new ItemStack[this.n_()];

        for (int i0 = 0; i0 < nbttaglist.c(); ++i0) {
            NBTTagCompound nbttagcompound1 = nbttaglist.b(i0);
            byte b0 = nbttagcompound1.d("Slot");

            if (b0 >= 0 && b0 < this.h.length) {
                this.h[b0] = ItemStack.a(nbttagcompound1);
            }
        }

        this.i = nbttagcompound.e("BurnTime");
        this.k = nbttagcompound.e("CookTime");
        this.l = nbttagcompound.e("CookTimeTotal");
        this.j = b(this.h[1]);
        if (nbttagcompound.b("CustomName", 8)) {
            this.m = nbttagcompound.j("CustomName");
        }
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("BurnTime", (short) this.i);
        nbttagcompound.a("CookTime", (short) this.k);
        nbttagcompound.a("CookTimeTotal", (short) this.l);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i0 = 0; i0 < this.h.length; ++i0) {
            if (this.h[i0] != null) {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();

                nbttagcompound1.a("Slot", (byte) i0);
                this.h[i0].b(nbttagcompound1);
                nbttaglist.a((NBTBase) nbttagcompound1);
            }
        }

        nbttagcompound.a("Items", (NBTBase) nbttaglist);
        if (this.k_()) {
            nbttagcompound.a("CustomName", this.m);
        }
    }

    public int p_() {
        return 64;
    }

    public boolean m() {
        return this.i > 0;
    }

    public void c() {
        boolean flag0 = this.m();
        boolean flag1 = false;

        if (this.m()) {
            --this.i;
        }

        if (!this.b.D) {
            if (!this.m() && (this.h[1] == null || this.h[0] == null)) {
                if (!this.m() && this.k > 0) {
                    this.k = MathHelper.a(this.k - 2, 0, this.l);
                }
            }
            else {
                if (!this.m() && this.o()) {
                    this.j = this.i = b(this.h[1]);
                    if (this.m()) {
                        flag1 = true;
                        if (this.h[1] != null) {
                            --this.h[1].b;
                            if (this.h[1].b == 0) {
                                Item item = this.h[1].b().q();

                                this.h[1] = item != null ? new ItemStack(item) : null;
                            }
                        }
                    }
                }

                // CanaryMod: SmeltBegin
                SmeltBeginHook sbh = null;
                if (this.j == 0 && this.m() && this.o()) { // Check that this is the start of a smelting process and that smelting can begin
                    sbh = (SmeltBeginHook) new SmeltBeginHook(this.getCanaryFurnace(), this.h[0].getCanaryItem()).call();
                }
                //

                if (this.m() && this.o()) {
                    ++this.k;
                    if (this.k == this.l) {
                        this.k = 0;
                        this.l = this.a(this.h[0]);
                        this.n();
                        flag1 = true;
                    }
                }
                else {
                    this.k = 0;
                }
            }

            if (flag0 != this.m()) {
                flag1 = true;
                BlockFurnace.a(this.m(), this.b, this.c);
            }
        }

        if (flag1) {
            this.o_();
        }
    }

    public int a(ItemStack itemstack) {
        return 200;
    }

    private boolean o() {
        if (this.h[0] == null) {
            return false;
        }
        else {
            ItemStack itemstack = FurnaceRecipes.a().a(this.h[0]);

            return itemstack == null ? false : (this.h[2] == null ? true : (!this.h[2].a(itemstack) ? false : (this.h[2].b < this.p_() && this.h[2].b < this.h[2].c() ? true : this.h[2].b < itemstack.c())));
        }
    }

    public void n() {
        if (this.o()) {
            ItemStack itemstack = FurnaceRecipes.a().a(this.h[0]);
            // CanaryMod: Smelt
            SmeltHook hook = (SmeltHook) new SmeltHook(getCanaryFurnace(), this.h[0].getCanaryItem(), itemstack.getCanaryItem()).call();
            if (hook.isCanceled()) {
                return;
            }
            //

            if (this.h[2] == null) {
                this.h[2] = itemstack.k();
            }
            else if (this.h[2].b() == itemstack.b()) {
                ++this.h[2].b;
            }

            if (this.h[0].b() == Item.a(Blocks.v) && this.h[0].i() == 1 && this.h[1] != null && this.h[1].b() == Items.aw) {
                this.h[1] = new ItemStack(Items.ax);
            }

            --this.h[0].b;
            if (this.h[0].b <= 0) {
                this.h[0] = null;
            }
        }
    }

    public static int b(ItemStack itemstack) {
        if (itemstack == null) {
            return 0;
        }
        else {
            Item item = itemstack.b();

            if (item instanceof ItemBlock && Block.a(item) != Blocks.a) {
                Block block = Block.a(item);

                if (block == Blocks.bM) {
                    return 150;
                }

                if (block.r() == Material.d) {
                    return 300;
                }

                if (block == Blocks.cA) {
                    return 16000;
                }
            }

            return item instanceof ItemTool && ((ItemTool) item).h().equals("WOOD") ? 200 : (item instanceof ItemSword && ((ItemSword) item).h().equals("WOOD") ? 200 : (item instanceof ItemHoe && ((ItemHoe) item).g().equals("WOOD") ? 200 : (item == Items.y ? 100 : (item == Items.h ? 1600 : (item == Items.ay ? 20000 : (item == Item.a(Blocks.g) ? 100 : (item == Items.bv ? 2400 : 0)))))));
        }
    }

    public static boolean c(ItemStack itemstack) {
        return b(itemstack) > 0;
    }

    public boolean a(EntityPlayer entityplayer) {
        // CanaryMod: remote inventories
        if (getCanaryFurnace().canOpenRemote()) {
            return true;
        }
        //
        return this.b.s(this.c) != this ? false : entityplayer.e((double) this.c.n() + 0.5D, (double) this.c.o() + 0.5D, (double) this.c.p() + 0.5D) <= 64.0D;
    }

    public void b(EntityPlayer entityplayer) {
    }

    public void c(EntityPlayer entityplayer) {
    }

    public boolean b(int i0, ItemStack itemstack) {
        return i0 == 2 ? false : (i0 != 1 ? true : c(itemstack) || SlotFurnaceFuel.c_(itemstack));
    }

    public int[] a(EnumFacing enumfacing) {
        return enumfacing == EnumFacing.DOWN ? f : (enumfacing == EnumFacing.UP ? a : g);
    }

    public boolean a(int i0, ItemStack itemstack, EnumFacing enumfacing) {
        return this.b(i0, itemstack);
    }

    public boolean b(int i0, ItemStack itemstack, EnumFacing enumfacing) {
        if (enumfacing == EnumFacing.DOWN && i0 == 1) {
            Item item = itemstack.b();

            if (item != Items.ax && item != Items.aw) {
                return false;
            }
        }

        return true;
    }

    public String k() {
        return "minecraft:furnace";
    }

    public Container a(InventoryPlayer inventoryplayer, EntityPlayer entityplayer) {
        return new ContainerFurnace(inventoryplayer, this);
    }

    public int a_(int i0) {
        switch (i0) {
            case 0:
                return this.i;

            case 1:
                return this.j;

            case 2:
                return this.k;

            case 3:
                return this.l;

            default:
                return 0;
        }
    }

    public void b(int i0, int i1) {
        switch (i0) {
            case 0:
                this.i = i1;
                break;

            case 1:
                this.j = i1;
                break;

            case 2:
                this.k = i1;
                break;

            case 3:
                this.l = i1;
        }
    }

    public int g() {
        return 4;
    }

    public void l() {
        for (int i0 = 0; i0 < this.h.length; ++i0) {
            this.h[i0] = null;
        }
    }

    // CanaryMod
    public CanaryFurnace getCanaryFurnace() {
        return (CanaryFurnace) complexBlock;
    }
}
