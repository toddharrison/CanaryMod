package net.minecraft.inventory;

import net.canarymod.api.entity.living.humanoid.Player;
import net.canarymod.api.world.blocks.CanaryAnvil;
import net.canarymod.hook.player.AnvilUseHook;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;
import java.util.Map;

public class ContainerRepair extends Container {

    private static final Logger f = LogManager.getLogger();
    public IInventory g = new InventoryCraftResult(); // CanaryMod: private -> public
    public IInventory h = new InventoryBasic("Repair", true, 2) {

        public void e() {
            super.e();
            ContainerRepair.this.a((IInventory) this);
        }
    };
    public World i; // Canary: private -> public
    public int j; // Canary: private -> public
    public int k; // Canary: private -> public
    public int l; // Canary: private -> public
    public int a;
    public int m; // Canary: private -> public
    private String n;
    private final EntityPlayer o;

    public ContainerRepair(InventoryPlayer inventoryplayer, final World world, final int i0, final int i1, final int i2, EntityPlayer entityplayer) {
        this.i = world;
        this.j = i0;
        this.k = i1;
        this.l = i2;
        this.o = entityplayer;
        this.a(new Slot(this.h, 0, 27, 47));
        this.a(new Slot(this.h, 1, 76, 47));
        this.a(new Slot(this.g, 2, 134, 47) {

            public boolean a(ItemStack itemstack) {
                return false;
            }

            public boolean a(EntityPlayer entityplayer) {
                return (entityplayer.bE.d || entityplayer.bF >= ContainerRepair.this.a) && ContainerRepair.this.a > 0 && this.e();
            }

            public void a(EntityPlayer entityplayer, ItemStack itemstack) {
                if (!entityplayer.bE.d) {
                    entityplayer.a(-ContainerRepair.this.a);
                }

                ContainerRepair.this.h.a(0, (ItemStack) null);
                if (ContainerRepair.this.m > 0) {
                    ItemStack itemstack1 = ContainerRepair.this.h.a(1);

                    if (itemstack1 != null && itemstack1.b > ContainerRepair.this.m) {
                        itemstack1.b -= ContainerRepair.this.m;
                        ContainerRepair.this.h.a(1, itemstack1);
                    } else {
                        ContainerRepair.this.h.a(1, (ItemStack) null);
                    }
                } else {
                    ContainerRepair.this.h.a(1, (ItemStack) null);
                }

                ContainerRepair.this.a = 0;
                if (!entityplayer.bE.d && !world.E && world.a(i0, i1, i2) == Blocks.bQ && entityplayer.aI().nextFloat() < 0.12F) {
                    int i3 = world.e(i0, i1, i2);
                    int i4 = i3 & 3;
                    int i5 = i3 >> 2;

                    ++i5;
                    if (i5 > 2) {
                        world.f(i0, i1, i2);
                        world.c(1020, i0, i1, i2, 0);
                    } else {
                        world.a(i0, i1, i2, i1 | i2 << 2, 2);
                        world.c(1021, i0, i1, i2, 0);
                    }
                } else if (!world.E) {
                    world.c(1021, i0, i1, i2, 0);
                }

            }
        });

        int i6;

        for (i6 = 0; i6 < 3; ++i6) {
            for (int i7 = 0; i7 < 9; ++i7) {
                this.a(new Slot(inventoryplayer, i7 + i6 * 9 + 9, 8 + i7 * 18, 84 + i6 * 18));
            }
        }

        for (i6 = 0; i6 < 9; ++i6) {
            this.a(new Slot(inventoryplayer, i6, 8 + i6 * 18, 142));
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
        ItemStack itemstack = this.h.a(0);

        this.a = 0;
        int i0 = 0;
        byte b0 = 0;
        int i1 = 0;

        if (itemstack == null) {
            this.g.a(0, (ItemStack) null);
            this.a = 0;
        } else {
            ItemStack itemstack1 = itemstack.m();
            ItemStack itemstack2 = this.h.a(1);
            Map map = EnchantmentHelper.a(itemstack1);
            boolean flag0 = false;
            int i2 = b0 + itemstack.C() + (itemstack2 == null ? 0 : itemstack2.C());

            this.m = 0;
            int i3;
            int i4;
            int i5;
            int i6;
            int i7;
            Iterator iterator;
            Enchantment enchantment;

            if (itemstack2 != null) {
                flag0 = itemstack2.b() == Items.bR && Items.bR.g(itemstack2).c() > 0;
                if (itemstack1.g() && itemstack1.b().a(itemstack, itemstack2)) {
                    i3 = Math.min(itemstack1.j(), itemstack1.l() / 4);
                    if (i3 <= 0) {
                        this.g.a(0, (ItemStack) null);
                        this.a = 0;
                        return;
                    }

                    for (i4 = 0; i3 > 0 && i4 < itemstack2.b; ++i4) {
                        i5 = itemstack1.j() - i3;
                        itemstack1.b(i5);
                        i0 += Math.max(1, i3 / 100) + map.size();
                        i3 = Math.min(itemstack1.j(), itemstack1.l() / 4);
                    }

                    this.m = i4;
                } else {
                    if (!flag0 && (itemstack1.b() != itemstack2.b() || !itemstack1.g())) {
                        this.g.a(0, (ItemStack) null);
                        this.a = 0;
                        return;
                    }

                    if (itemstack1.g() && !flag0) {
                        i3 = itemstack.l() - itemstack.j();
                        i4 = itemstack2.l() - itemstack2.j();
                        i5 = i4 + itemstack1.l() * 12 / 100;
                        int i8 = i3 + i5;

                        i6 = itemstack1.l() - i8;
                        if (i6 < 0) {
                            i6 = 0;
                        }

                        if (i6 < itemstack1.k()) {
                            itemstack1.b(i6);
                            i0 += Math.max(1, i5 / 100);
                        }
                    }

                    Map map1 = EnchantmentHelper.a(itemstack2);

                    iterator = map1.keySet().iterator();

                    while (iterator.hasNext()) {
                        i5 = ((Integer) iterator.next()).intValue();
                        enchantment = Enchantment.b[i5];
                        i6 = map.containsKey(Integer.valueOf(i5)) ? ((Integer) map.get(Integer.valueOf(i5))).intValue() : 0;
                        i7 = ((Integer) map1.get(Integer.valueOf(i5))).intValue();
                        int i9;

                        if (i6 == i7) {
                            ++i7;
                            i9 = i7;
                        } else {
                            i9 = Math.max(i7, i6);
                        }

                        i7 = i9;
                        int i10 = i7 - i6;
                        boolean flag1 = enchantment.a(itemstack);

                        if (this.o.bE.d || itemstack.b() == Items.bR) {
                            flag1 = true;
                        }

                        Iterator iterator1 = map.keySet().iterator();

                        while (iterator1.hasNext()) {
                            int i11 = ((Integer) iterator1.next()).intValue();

                            if (i11 != i5 && !enchantment.a(Enchantment.b[i11])) {
                                flag1 = false;
                                i0 += i10;
                            }
                        }

                        if (flag1) {
                            if (i7 > enchantment.b()) {
                                i7 = enchantment.b();
                            }

                            map.put(Integer.valueOf(i5), Integer.valueOf(i7));
                            int i12 = 0;

                            switch (enchantment.c()) {
                                case 1:
                                    i12 = 8;
                                    break;

                                case 2:
                                    i12 = 4;

                                case 3:
                                case 4:
                                case 6:
                                case 7:
                                case 8:
                                case 9:
                                default:
                                    break;

                                case 5:
                                    i12 = 2;
                                    break;

                                case 10:
                                    i12 = 1;
                            }

                            if (flag0) {
                                i12 = Math.max(1, i12 / 2);
                            }

                            i0 += i12 * i10;
                        }
                    }
                }
            }

            if (StringUtils.isBlank(this.n)) {
                if (itemstack.u()) {
                    i1 = itemstack.g() ? 7 : itemstack.b * 5;
                    i0 += i1;
                    itemstack1.t();
                }
            } else if (!this.n.equals(itemstack.s())) {
                i1 = itemstack.g() ? 7 : itemstack.b * 5;
                i0 += i1;
                if (itemstack.u()) {
                    i2 += i1 / 2;
                }

                itemstack1.c(this.n);
            }

            i3 = 0;

            for (iterator = map.keySet().iterator(); iterator.hasNext(); i2 += i3 + i6 * i7) {
                i5 = ((Integer) iterator.next()).intValue();
                enchantment = Enchantment.b[i5];
                i6 = ((Integer) map.get(Integer.valueOf(i5))).intValue();
                i7 = 0;
                ++i3;
                switch (enchantment.c()) {
                    case 1:
                        i7 = 8;
                        break;

                    case 2:
                        i7 = 4;

                    case 3:
                    case 4:
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                    default:
                        break;

                    case 5:
                        i7 = 2;
                        break;

                    case 10:
                        i7 = 1;
                }

                if (flag0) {
                    i7 = Math.max(1, i7 / 2);
                }
            }

            if (flag0) {
                i2 = Math.max(1, i2 / 2);
            }

            this.a = i2 + i0;
            if (i0 <= 0) {
                itemstack1 = null;
            }

            if (i1 == i0 && i1 > 0 && this.a >= 40) {
                this.a = 39;
            }

            if (this.a >= 40 && !this.o.bE.d) {
                itemstack1 = null;
            }

            if (itemstack1 != null) {
                i4 = itemstack1.C();
                if (itemstack2 != null && i4 < itemstack2.C()) {
                    i4 = itemstack2.C();
                }

                if (itemstack1.u()) {
                    i4 -= 9;
                }

                if (i4 < 0) {
                    i4 = 0;
                }

                i4 += 2;
                itemstack1.c(i4);
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
        if (!this.i.E) {
            for (int i0 = 0; i0 < this.h.a(); ++i0) {
                ItemStack itemstack = this.h.a_(i0);

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

        return this.i.a(this.j, this.k, this.l) != Blocks.bQ ? false : entityplayer.e((double) this.j + 0.5D, (double) this.k + 0.5D, (double) this.l + 0.5D) <= 64.0D;
    }

    public ItemStack b(EntityPlayer entityplayer, int i0) {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.c.get(i0);

        if (slot != null && slot.e()) {
            ItemStack itemstack1 = slot.d();

            itemstack = itemstack1.m();
            if (i0 == 2) {
                if (!this.a(itemstack1, 3, 39, true)) {
                    return null;
                }

                slot.a(itemstack1, itemstack);
            } else if (i0 != 0 && i0 != 1) {
                if (i0 >= 3 && i0 < 39 && !this.a(itemstack1, 0, 2, false)) {
                    return null;
                }
            } else if (!this.a(itemstack1, 3, 39, false)) {
                return null;
            }

            if (itemstack1.b == 0) {
                slot.c((ItemStack) null);
            } else {
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
        this.n = s0;
        if (this.a(2).e()) {
            ItemStack itemstack = this.a(2).d();

            if (StringUtils.isBlank(s0)) {
                itemstack.t();
            } else {
                itemstack.c(this.n);
            }
        }

        this.e();
    }

    // CanaryMod start
    public String getToolName() {
        return this.n;
    }

    public Player getPlayer() {
        return ((EntityPlayerMP) this.o).getPlayer();
    }
    // CanaryMod end
}
