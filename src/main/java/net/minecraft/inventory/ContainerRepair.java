package net.minecraft.inventory;

import java.util.Iterator;
import java.util.Map;
import net.canarymod.api.entity.living.humanoid.Player;
import net.canarymod.api.world.blocks.CanaryAnvil;
import net.canarymod.hook.player.AnvilUseHook;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ContainerRepair extends Container {

    private static final Logger f = LogManager.getLogger();
    private final EntityPlayer m;
    public int a;
    public IInventory g = new InventoryCraftResult(); // CanaryMod: private -> public
    public IInventory h = new InventoryBasic("Repair", true, 2) {

        public void o_() {
            super.o_();
            ContainerRepair.this.a((IInventory) this);
        }
    };
    public World i; // Canary: private -> public
    public BlockPos j; // Canary: private -> public
    public int k; // Canary: private -> public
    private String l;

    public ContainerRepair(InventoryPlayer inventoryplayer, final World world, final BlockPos blockpos, EntityPlayer entityplayer) {
        this.j = blockpos;
        this.i = world;
        this.m = entityplayer;
        this.a(new Slot(this.h, 0, 27, 47));
        this.a(new Slot(this.h, 1, 76, 47));
        this.a(new Slot(this.g, 2, 134, 47) {

            public boolean a(ItemStack p_a_1_) {
                return false;
            }

            public boolean a(EntityPlayer p_a_1_) {
                return (p_a_1_.by.d || p_a_1_.bz >= ContainerRepair.this.a) && ContainerRepair.this.a > 0 && this.e();
            }

            public void a(EntityPlayer p_a_1_, ItemStack p_a_2_) {
                if (!p_a_1_.by.d) {
                    p_a_1_.a(-ContainerRepair.this.a);
                }

                ContainerRepair.this.h.a(0, (ItemStack) null);
                if (ContainerRepair.this.k > 0) {
                    ItemStack itemstack = ContainerRepair.this.h.a(1);

                    if (itemstack != null && itemstack.b > ContainerRepair.this.k) {
                        itemstack.b -= ContainerRepair.this.k;
                        ContainerRepair.this.h.a(1, itemstack);
                    }
                    else {
                        ContainerRepair.this.h.a(1, (ItemStack) null);
                    }
                }
                else {
                    ContainerRepair.this.h.a(1, (ItemStack) null);
                }

                ContainerRepair.this.a = 0;
                IBlockState i1 = world.p(blockpos);

                if (!p_a_1_.by.d && !world.D && i1.c() == Blocks.cf && p_a_1_.bb().nextFloat() < 0.12F) {
                    int i0 = ((Integer) i1.b(BlockAnvil.b)).intValue();

                    ++i0;
                    if (i0 > 2) {
                        world.g(blockpos);
                        world.b(1020, blockpos, 0);
                    }
                    else {
                        world.a(blockpos, i1.a(BlockAnvil.b, Integer.valueOf(i0)), 2);
                        world.b(1021, blockpos, 0);
                    }
                }
                else if (!world.D) {
                    world.b(1021, blockpos, 0);
                }

            }
        });

        int i1;

        for (i1 = 0; i1 < 3; ++i1) {
            for (int i2 = 0; i2 < 9; ++i2) {
                this.a(new Slot(inventoryplayer, i2 + i1 * 9 + 9, 8 + i2 * 18, 84 + i1 * 18));
            }
        }

        for (i1 = 0; i1 < 9; ++i1) {
            this.a(new Slot(inventoryplayer, i1, 8 + i1 * 18, 142));
        }

        this.inventory = new CanaryAnvil(this); // CanaryMod: Set inventory instance
    }

    public void a(IInventory iinventory) {
        super.a(iinventory);
        if (iinventory == this.h) {
            this.e();
        }

    }

    public void e() {
        boolean flag0 = false;
        boolean flag1 = true;
        boolean flag2 = true;
        boolean flag3 = true;
        boolean flag4 = true;
        boolean flag5 = true;
        boolean flag6 = true;
        ItemStack itemstack = this.h.a(0);

        this.a = 1;
        int i0 = 0;
        byte b0 = 0;
        byte b1 = 0;

        if (itemstack == null) {
            this.g.a(0, (ItemStack) null);
            this.a = 0;
        }
        else {
            ItemStack itemstack1 = itemstack.k();
            ItemStack itemstack2 = this.h.a(1);
            Map map = EnchantmentHelper.a(itemstack1);
            boolean flag7 = false;
            int i1 = b0 + itemstack.A() + (itemstack2 == null ? 0 : itemstack2.A());

            this.k = 0;
            int i2;

            if (itemstack2 != null) {
                flag7 = itemstack2.b() == Items.cd && Items.cd.h(itemstack2).c() > 0;
                int i3;
                int i4;

                if (itemstack1.e() && itemstack1.b().a(itemstack, itemstack2)) {
                    i2 = Math.min(itemstack1.h(), itemstack1.j() / 4);
                    if (i2 <= 0) {
                        this.g.a(0, (ItemStack) null);
                        this.a = 0;
                        return;
                    }

                    for (i3 = 0; i2 > 0 && i3 < itemstack2.b; ++i3) {
                        i4 = itemstack1.h() - i2;
                        itemstack1.b(i4);
                        ++i0;
                        i2 = Math.min(itemstack1.h(), itemstack1.j() / 4);
                    }

                    this.k = i3;
                }
                else {
                    if (!flag7 && (itemstack1.b() != itemstack2.b() || !itemstack1.e())) {
                        this.g.a(0, (ItemStack) null);
                        this.a = 0;
                        return;
                    }

                    int i5;

                    if (itemstack1.e() && !flag7) {
                        i2 = itemstack.j() - itemstack.h();
                        i3 = itemstack2.j() - itemstack2.h();
                        i4 = i3 + itemstack1.j() * 12 / 100;
                        int i6 = i2 + i4;

                        i5 = itemstack1.j() - i6;
                        if (i5 < 0) {
                            i5 = 0;
                        }

                        if (i5 < itemstack1.i()) {
                            itemstack1.b(i5);
                            i0 += 2;
                        }
                    }

                    Map map1 = EnchantmentHelper.a(itemstack2);
                    Iterator iterator = map1.keySet().iterator();

                    while (iterator.hasNext()) {
                        i4 = ((Integer) iterator.next()).intValue();
                        Enchantment enchantment = Enchantment.c(i4);

                        if (enchantment != null) {
                            i5 = map.containsKey(Integer.valueOf(i4)) ? ((Integer) map.get(Integer.valueOf(i4))).intValue() : 0;
                            int i7 = ((Integer) map1.get(Integer.valueOf(i4))).intValue();
                            int i8;

                            if (i5 == i7) {
                                ++i7;
                                i8 = i7;
                            }
                            else {
                                i8 = Math.max(i7, i5);
                            }

                            i7 = i8;
                            boolean flag8 = enchantment.a(itemstack);

                            if (this.m.by.d || itemstack.b() == Items.cd) {
                                flag8 = true;
                            }

                            Iterator iterator1 = map.keySet().iterator();

                            while (iterator1.hasNext()) {
                                int i9 = ((Integer) iterator1.next()).intValue();

                                if (i9 != i4 && !enchantment.a(Enchantment.c(i9))) {
                                    flag8 = false;
                                    ++i0;
                                }
                            }

                            if (flag8) {
                                if (i7 > enchantment.b()) {
                                    i7 = enchantment.b();
                                }

                                map.put(Integer.valueOf(i4), Integer.valueOf(i7));
                                int i10 = 0;

                                switch (enchantment.d()) {
                                    case 1:
                                        i10 = 8;
                                        break;

                                    case 2:
                                        i10 = 4;

                                    case 3:
                                    case 4:
                                    case 6:
                                    case 7:
                                    case 8:
                                    case 9:
                                    default:
                                        break;

                                    case 5:
                                        i10 = 2;
                                        break;

                                    case 10:
                                        i10 = 1;
                                }

                                if (flag7) {
                                    i10 = Math.max(1, i10 / 2);
                                }

                                i0 += i10 * i7;
                            }
                        }
                    }
                }
            }

            if (StringUtils.isBlank(this.l)) {
                if (itemstack.s()) {
                    b1 = 1;
                    i0 += b1;
                    itemstack1.r();
                }
            }
            else if (!this.l.equals(itemstack.q())) {
                b1 = 1;
                i0 += b1;
                itemstack1.c(this.l);
            }

            this.a = i1 + i0;
            if (i0 <= 0) {
                itemstack1 = null;
            }

            if (b1 == i0 && b1 > 0 && this.a >= 40) {
                this.a = 39;
            }

            if (this.a >= 40 && !this.m.by.d) {
                itemstack1 = null;
            }

            if (itemstack1 != null) {
                i2 = itemstack1.A();
                if (itemstack2 != null && i2 < itemstack2.A()) {
                    i2 = itemstack2.A();
                }

                i2 = i2 * 2 + 1;
                itemstack1.c(i2);
                EnchantmentHelper.a(map, itemstack1);
            }

            this.g.a(0, itemstack1);
            // CanaryMod: AnvilUse
            new AnvilUseHook(getPlayer(), new CanaryAnvil(this)).call();
            //
            this.b();
        }
    }

    public void a(ICrafting icrafting) {
        super.a(icrafting);
        icrafting.a(this, 0, this.a);
    }

    public void b(EntityPlayer entityplayer) {
        super.b(entityplayer);
        if (!this.i.D) {
            for (int i0 = 0; i0 < this.h.n_(); ++i0) {
                ItemStack itemstack = this.h.b(i0);

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

        return this.i.p(this.j).c() != Blocks.cf ? false : entityplayer.e((double) this.j.n() + 0.5D, (double) this.j.o() + 0.5D, (double) this.j.p() + 0.5D) <= 64.0D;
    }

    public ItemStack b(EntityPlayer entityplayer, int i0) {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.c.get(i0);

        if (slot != null && slot.e()) {
            ItemStack itemstack1 = slot.d();

            itemstack = itemstack1.k();
            if (i0 == 2) {
                if (!this.a(itemstack1, 3, 39, true)) {
                    return null;
                }

                slot.a(itemstack1, itemstack);
            }
            else if (i0 != 0 && i0 != 1) {
                if (i0 >= 3 && i0 < 39 && !this.a(itemstack1, 0, 2, false)) {
                    return null;
                }
            }
            else if (!this.a(itemstack1, 3, 39, false)) {
                return null;
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

    public void a(String s0) {
        this.l = s0;
        if (this.a(2).e()) {
            ItemStack itemstack = this.a(2).d();

            if (StringUtils.isBlank(s0)) {
                itemstack.r();
            }
            else {
                itemstack.c(this.l);
            }
        }

        this.e();
    }

    // CanaryMod start
    public String getToolName() {
        return this.l;
    }

    public Player getPlayer() {
        return ((EntityPlayerMP) this.m).getPlayer();
    }
    // CanaryMod end
}
