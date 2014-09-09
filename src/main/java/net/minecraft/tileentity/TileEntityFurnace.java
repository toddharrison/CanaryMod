package net.minecraft.tileentity;

import net.canarymod.api.world.blocks.CanaryFurnace;
import net.canarymod.hook.world.SmeltBeginHook;
import net.canarymod.hook.world.SmeltHook;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.*;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class TileEntityFurnace extends TileEntity implements ISidedInventory {

    private static final int[] k = new int[]{0};
    private static final int[] l = new int[]{2, 1};
    private static final int[] m = new int[]{1};
    public ItemStack[] n = new ItemStack[3]; // CanaryMod: private => public
    public int a;
    public int i;
    public int j;
    private String o;

    public TileEntityFurnace() {
        this.complexBlock = new CanaryFurnace(this); // CanaryMod: create once, use forever
    }

    public int a() {
        return this.n.length;
    }

    public ItemStack a(int i0) {
        return this.n[i0];
    }

    public ItemStack a(int i0, int i1) {
        if (this.n[i0] != null) {
            ItemStack itemstack;

            if (this.n[i0].b <= i1) {
                itemstack = this.n[i0];
                this.n[i0] = null;
                return itemstack;
            }
            else {
                itemstack = this.n[i0].a(i1);
                if (this.n[i0].b == 0) {
                    this.n[i0] = null;
                }

                return itemstack;
            }
        }
        else {
            return null;
        }
    }

    public ItemStack a_(int i0) {
        if (this.n[i0] != null) {
            ItemStack itemstack = this.n[i0];

            this.n[i0] = null;
            return itemstack;
        }
        else {
            return null;
        }
    }

    public void a(int i0, ItemStack itemstack) {
        this.n[i0] = itemstack;
        if (itemstack != null && itemstack.b > this.d()) {
            itemstack.b = this.d();
        }
    }

    public String b() {
        return this.k_() ? this.o : "container.furnace";
    }

    public boolean k_() {
        return this.o != null && this.o.length() > 0;
    }

    public void a(String s0) {
        this.o = s0;
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        NBTTagList nbttaglist = nbttagcompound.c("Items", 10);

        this.n = new ItemStack[this.a()];

        for (int i0 = 0; i0 < nbttaglist.c(); ++i0) {
            NBTTagCompound nbttagcompound1 = nbttaglist.b(i0);
            byte b0 = nbttagcompound1.d("Slot");

            if (b0 >= 0 && b0 < this.n.length) {
                this.n[b0] = ItemStack.a(nbttagcompound1);
            }
        }

        this.a = nbttagcompound.e("BurnTime");
        this.j = nbttagcompound.e("CookTime");
        this.i = a(this.n[1]);
        if (nbttagcompound.b("CustomName", 8)) {
            this.o = nbttagcompound.j("CustomName");
        }
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("BurnTime", (short) this.a);
        nbttagcompound.a("CookTime", (short) this.j);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i0 = 0; i0 < this.n.length; ++i0) {
            if (this.n[i0] != null) {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();

                nbttagcompound1.a("Slot", (byte) i0);
                this.n[i0].b(nbttagcompound1);
                nbttaglist.a((NBTBase) nbttagcompound1);
            }
        }

        nbttagcompound.a("Items", (NBTBase) nbttaglist);
        if (this.k_()) {
            nbttagcompound.a("CustomName", this.o);
        }
    }

    public int d() {
        return 64;
    }

    public boolean i() {
        return this.a > 0;
    }

    public void h() {
        boolean flag0 = this.a > 0;
        boolean flag1 = false;

        if (this.a > 0) {
            --this.a;
        }

        if (!this.b.E) {
            if (this.a != 0 || this.n[1] != null && this.n[0] != null) {
                if (this.a == 0 && this.k()) {
                    this.i = this.a = a(this.n[1]);
                    if (this.a > 0) {
                        flag1 = true;
                        if (this.n[1] != null) {
                            --this.n[1].b;
                            if (this.n[1].b == 0) {
                                Item item = this.n[1].b().t();

                                this.n[1] = item != null ? new ItemStack(item) : null;
                            }
                        }
                    }
                }

                // CanaryMod: SmeltBegin
                SmeltBeginHook sbh = null;
                if (this.j == 0 && this.i() && this.k()) { // Check that this is the start of a smelting process and that smelting can begin
                    sbh = (SmeltBeginHook) new SmeltBeginHook(this.getCanaryFurnace(), this.n[0].getCanaryItem()).call();
                }
                //

                if (this.i() && this.k() && (sbh == null || !sbh.isCanceled())) { // CanaryMod: Verify the hook
                    ++this.j;
                    if (this.j == 200) {
                        this.j = 0;
                        this.j();
                        flag1 = true;
                    }
                }
                else {
                    this.j = 0;
                }
            }

            if (flag0 != this.a > 0) {
                flag1 = true;
                BlockFurnace.a(this.a > 0, this.b, this.c, this.d, this.e);
            }
        }

        if (flag1) {
            this.e();
        }
    }

    private boolean k() {
        if (this.n[0] == null) {
            return false;
        }
        else {
            ItemStack itemstack = FurnaceRecipes.a().a(this.n[0]);

            return itemstack == null ? false : (this.n[2] == null ? true : (!this.n[2].a(itemstack) ? false : (this.n[2].b < this.d() && this.n[2].b < this.n[2].e() ? true : this.n[2].b < itemstack.e())));
        }
    }

    public void j() {
        if (this.k()) {
            ItemStack itemstack = FurnaceRecipes.a().a(this.n[0]);
            // CanaryMod: Smelt
            SmeltHook hook = (SmeltHook) new SmeltHook(getCanaryFurnace(), this.n[0].getCanaryItem(), itemstack.getCanaryItem()).call();
            if (hook.isCanceled()) {
                return;
            }
            //

            if (this.n[2] == null) {
                this.n[2] = itemstack.m();
            }
            else if (this.n[2].b() == itemstack.b()) {
                ++this.n[2].b;
            }

            --this.n[0].b;
            if (this.n[0].b <= 0) {
                this.n[0] = null;
            }
        }
    }

    public static int a(ItemStack itemstack) {
        if (itemstack == null) {
            return 0;
        }
        else {
            Item item = itemstack.b();

            if (item instanceof ItemBlock && Block.a(item) != Blocks.a) {
                Block block = Block.a(item);

                if (block == Blocks.bx) {
                    return 150;
                }

                if (block.o() == Material.d) {
                    return 300;
                }

                if (block == Blocks.ci) {
                    return 16000;
                }
            }

            return item instanceof ItemTool && ((ItemTool) item).j().equals("WOOD") ? 200 : (item instanceof ItemSword && ((ItemSword) item).j().equals("WOOD") ? 200 : (item instanceof ItemHoe && ((ItemHoe) item).i().equals("WOOD") ? 200 : (item == Items.y ? 100 : (item == Items.h ? 1600 : (item == Items.at ? 20000 : (item == Item.a(Blocks.g) ? 100 : (item == Items.bj ? 2400 : 0)))))));
        }
    }

    public static boolean b(ItemStack itemstack) {
        return a(itemstack) > 0;
    }

    public boolean a(EntityPlayer entityplayer) {
        // CanaryMod: remote inventories
        if (getCanaryFurnace().canOpenRemote()) {
            return true;
        }
        //

        return this.b.o(this.c, this.d, this.e) != this ? false : entityplayer.e((double) this.c + 0.5D, (double) this.d + 0.5D, (double) this.e + 0.5D) <= 64.0D;
    }

    public void f() {
    }

    public void l_() {
    }

    public boolean b(int i0, ItemStack itemstack) {
        return i0 == 2 ? false : (i0 == 1 ? b(itemstack) : true);
    }

    public int[] c(int i0) {
        return i0 == 0 ? l : (i0 == 1 ? k : m);
    }

    public boolean a(int i0, ItemStack itemstack, int i1) {
        return this.b(i0, itemstack);
    }

    public boolean b(int i0, ItemStack itemstack, int i1) {
        return i1 != 0 || i0 != 1 || itemstack.b() == Items.ar;
    }

    // CanaryMod
    public CanaryFurnace getCanaryFurnace() {
        return (CanaryFurnace) complexBlock;
    }
}
