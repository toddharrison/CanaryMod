package net.minecraft.inventory;

import net.canarymod.api.inventory.CanaryEnchantment;
import net.canarymod.api.inventory.Enchantment;
import net.canarymod.api.world.blocks.CanaryEnchantmentTable;
import net.canarymod.api.world.blocks.EnchantmentTable;
import net.canarymod.hook.player.EnchantHook;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class ContainerEnchantment extends Container {

    public IInventory a = new InventoryBasic("Enchant", true, 2) {

        public int p_() {
            return 64;
        }

        public void o_() {
            super.o_();
            ContainerEnchantment.this.a((IInventory) this);
        }
    };
    public int f; // CanaryMod: private => public
    public int[] g; // CanaryMod: private => public
    public int[] h; // CanaryMod: private => public
    public World i; // CanaryMod: private => public
    private BlockPos j;
    private Random k = new Random();

    public ContainerEnchantment(InventoryPlayer inventoryplayer, World world, BlockPos blockpos) {
        this.i = world;
        this.j = blockpos;
        this.f = inventoryplayer.d.ci();
        this.a(new Slot(this.a, 0, 15, 47) {

            public boolean a(ItemStack p_a_1_) {
                return true;
            }

            public int a() {
                return 1;
            }
        });
        this.a(new Slot(this.a, 1, 35, 47) {

            public boolean a(ItemStack p_a_1_) {
                return p_a_1_.b() == Items.aW && EnumDyeColor.a(p_a_1_.i()) == EnumDyeColor.BLUE;
            }
        });

        int i0;

        for (i0 = 0; i0 < 3; ++i0) {
            for (int i1 = 0; i1 < 9; ++i1) {
                this.a(new Slot(inventoryplayer, i1 + i0 * 9 + 9, 8 + i1 * 18, 84 + i0 * 18));
            }
        }

        for (i0 = 0; i0 < 9; ++i0) {
            this.a(new Slot(inventoryplayer, i0, 8 + i0 * 18, 142));
        }

        this.inventory = new CanaryEnchantmentTable(this); // CanaryMod: Set inventory instance
    }

    public void a(ICrafting icrafting) {
        super.a(icrafting);
        icrafting.a(this, 0, this.g[0]);
        icrafting.a(this, 1, this.g[1]);
        icrafting.a(this, 2, this.g[2]);
        icrafting.a(this, 3, this.f & -16);
        icrafting.a(this, 4, this.h[0]);
        icrafting.a(this, 5, this.h[1]);
        icrafting.a(this, 6, this.h[2]);
    }

    public void b() {
        super.b();

        for (int i0 = 0; i0 < this.e.size(); ++i0) {
            ICrafting icrafting = (ICrafting) this.e.get(i0);

            icrafting.a(this, 0, this.g[0]);
            icrafting.a(this, 1, this.g[1]);
            icrafting.a(this, 2, this.g[2]);
            icrafting.a(this, 3, this.f & -16);
            icrafting.a(this, 4, this.h[0]);
            icrafting.a(this, 5, this.h[1]);
            icrafting.a(this, 6, this.h[2]);
        }

    }

    public void a(IInventory iinventory) {
        if (iinventory == this.a) {
            ItemStack itemstack = iinventory.a(0);
            int i0;

            if (itemstack != null && itemstack.v()) {
                if (!this.i.D) {
                    i0 = 0;

                    int i1;

                    // CanaryMod: if fake cases are used, skip bookcase checks
                    if (!((CanaryEnchantmentTable) this.inventory).hasFakeCases()) {
                        for (i1 = -1; i1 <= 1; ++i1) {
                            for (int i2 = -1; i2 <= 1; ++i2) {
                                if ((i1 != 0 || i2 != 0) && this.i.d(this.j.a(i2, 0, i1)) && this.i.d(this.j.a(i2, 1, i1))) {
                                    if (this.i.p(this.j.a(i2 * 2, 0, i1 * 2)).c() == Blocks.X) {
                                        ++i0;
                                    }

                                    if (this.i.p(this.j.a(i2 * 2, 1, i1 * 2)).c() == Blocks.X) {
                                        ++i0;
                                    }

                                    if (i2 != 0 && i1 != 0) {
                                        if (this.i.p(this.j.a(i2 * 2, 0, i1)).c() == Blocks.X) {
                                            ++i0;
                                        }

                                        if (this.i.p(this.j.a(i2 * 2, 1, i1)).c() == Blocks.X) {
                                            ++i0;
                                        }

                                        if (this.i.p(this.j.a(i2, 0, i1 * 2)).c() == Blocks.X) {
                                            ++i0;
                                        }

                                        if (this.i.p(this.j.a(i2, 1, i1 * 2)).c() == Blocks.X) {
                                            ++i0;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    else {
                        // CanaryMod: set case count
                        i0 = ((CanaryEnchantmentTable) this.inventory).getFakeCaseCount();
                    }

                    
                    this.k.setSeed((long) this.f);

                    for (i1 = 0; i1 < 3; ++i1) {
                        this.g[i1] = EnchantmentHelper.a(this.k, i1, i0, itemstack);
                        this.h[i1] = -1;
                        if (this.g[i1] < i1 + 1) {
                            this.g[i1] = 0;
                        }
                    }

                    for (i1 = 0; i1 < 3; ++i1) {
                        if (this.g[i1] > 0) {
                            List list = this.a(itemstack, i1, this.g[i1]);

                            if (list != null && !list.isEmpty()) {
                                EnchantmentData enchantmentdata = (EnchantmentData) list.get(this.k.nextInt(list.size()));

                                this.h[i1] = enchantmentdata.b.B | enchantmentdata.c << 8;
                            }
                        }
                    }

                    this.b();
                }
            }
            else {
                for (i0 = 0; i0 < 3; ++i0) {
                    this.g[i0] = 0;
                    this.h[i0] = -1;
                }
            }
        }

    }

    @SuppressWarnings("unchecked")
    public boolean a(EntityPlayer entityplayer, int i0) {
        ItemStack itemstack = this.a.a(0);
        ItemStack itemstack1 = this.a.a(1);
        int i1 = i0 + 1;

        if ((itemstack1 == null || itemstack1.b < i1) && !entityplayer.by.d) {
            return false;
        }
        else if (this.g[i0] > 0 && itemstack != null && (entityplayer.bz >= i1 && entityplayer.bz >= this.g[i0] || entityplayer.by.d)) {
            if (!this.i.D) {
                List<EnchantmentData> list = this.a(itemstack, i0, this.g[i0]);
                boolean flag0 = itemstack.b() == Items.aL;

                if (list != null) {
                    // CanaryMod: Enchant
                    List<net.canarymod.api.inventory.Enchantment> cench = new ArrayList<Enchantment>();

                    for (EnchantmentData endat : list) {
                        cench.add(new CanaryEnchantment(endat));
                    }
                    EnchantHook hook = (EnchantHook) new EnchantHook(((EntityPlayerMP) entityplayer).getPlayer(), itemstack.getCanaryItem(), (EnchantmentTable) this.inventory, cench).call();
                    if (!hook.isCanceled() && hook.isValid(false)) {
                        list.clear();
                        for (net.canarymod.api.inventory.Enchantment ench : hook.getEnchantmentList()) {
                            list.add(((CanaryEnchantment) ench).getEnchantmentData());
                        }

                         entityplayer.b(i1);
                    if (flag0) {
                        itemstack.a((Item) Items.cd);
                    }

                    for (int i2 = 0; i2 < list.size(); ++i2) {
                        EnchantmentData enchantmentdata = (EnchantmentData) list.get(i2);

                        if (flag0) {
                            Items.cd.a(itemstack, enchantmentdata);
                        }
                        else {
                            itemstack.a(enchantmentdata.b, enchantmentdata.c);
                        }
                    }

                    if (!entityplayer.by.d) {
                        itemstack1.b -= i1;
                        if (itemstack1.b <= 0) {
                            this.a.a(1, (ItemStack) null);
                        }
                    }

                    this.a.o_();
                    this.f = entityplayer.ci();
                    this.a(this.a);
                    }
                    //
                }
            }

            return true;
        }
        else {
            return false;
        }
    }

    private List a(ItemStack itemstack, int i0, int i1) {
        this.k.setSeed((long) (this.f + i0));
        List list = EnchantmentHelper.b(this.k, itemstack, i1);

        if (itemstack.b() == Items.aL && list != null && list.size() > 1) {
            list.remove(this.k.nextInt(list.size()));
        }

        return list;
    }

    public void b(EntityPlayer entityplayer) {
        super.b(entityplayer);
        if (!this.i.D) {
            for (int i0 = 0; i0 < this.a.n_(); ++i0) {
                ItemStack itemstack = this.a.b(i0);

                if (itemstack != null) {
                    entityplayer.a(itemstack, false);
                }
            }
        }
    }

    public boolean a(EntityPlayer entityplayer) {
        // CanaryMod: remote inventories
        if (this.inventory.canOpenRemote()) {
            return true;
        }
        //

        return this.i.p(this.j).c() != Blocks.bC ? false : entityplayer.e((double) this.j.n() + 0.5D, (double) this.j.o() + 0.5D, (double) this.j.p() + 0.5D) <= 64.0D;
    }

    public ItemStack b(EntityPlayer entityplayer, int i0) {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.c.get(i0);

        if (slot != null && slot.e()) {
            ItemStack itemstack1 = slot.d();

            itemstack = itemstack1.k();
            if (i0 == 0) {
                if (!this.a(itemstack1, 2, 38, true)) {
                    return null;
                }
            }
            else if (i0 == 1) {
                if (!this.a(itemstack1, 2, 38, true)) {
                    return null;
                }
            }
            else if (itemstack1.b() == Items.aW && EnumDyeColor.a(itemstack1.i()) == EnumDyeColor.BLUE) {
                if (!this.a(itemstack1, 1, 2, true)) {
                    return null;
                }
            }
            else {
                if (((Slot) this.c.get(0)).e() || !((Slot) this.c.get(0)).a(itemstack1)) {
                    return null;
                }

                if (itemstack1.n() && itemstack1.b == 1) {
                    ((Slot) this.c.get(0)).d(itemstack1.k());
                    itemstack1.b = 0;
                }
                else if (itemstack1.b >= 1) {
                    ((Slot) this.c.get(0)).d(new ItemStack(itemstack1.b(), 1, itemstack1.i()));
                    --itemstack1.b;
                }
            }

            if (itemstack1.b == 0) {
                slot.d((ItemStack) null);
            }
            else {
                slot.f();
            }

            if (itemstack1.b == itemstack.b) {
                return null;
            }

            slot.a(entityplayer, itemstack1);
        }

        return itemstack;
    }
}
