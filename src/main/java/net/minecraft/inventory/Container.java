package net.minecraft.inventory;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.canarymod.api.entity.living.humanoid.Player;
import net.canarymod.api.inventory.Inventory;
import net.canarymod.hook.player.InventoryHook;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public abstract class Container {

    private final Set h = Sets.newHashSet();
    public List b = Lists.newArrayList();
    public List c = Lists.newArrayList();
    public int d;
    protected List e = Lists.newArrayList();
    private int f = -1;
    private int g;
    private Set i = Sets.newHashSet();

    protected Inventory inventory; // CanaryMod

    public static int b(int i0) {
        return i0 >> 2 & 3;
    }

    public static int c(int i0) {
        return i0 & 3;
    }

    public static boolean a(int i0, EntityPlayer entityplayer) {
        return i0 == 0 ? true : (i0 == 1 ? true : i0 == 2 && entityplayer.by.d);
    }

    public static boolean a(Slot slot, ItemStack itemstack, boolean flag0) {
        boolean flag1 = slot == null || !slot.e();

        if (slot != null && slot.e() && itemstack != null && itemstack.a(slot.d()) && ItemStack.a(slot.d(), itemstack)) {
            int i0 = flag0 ? 0 : itemstack.b;

            flag1 |= slot.d().b + i0 <= itemstack.c();
        }

        return flag1;
    }

    public static void a(Set set, int i0, ItemStack itemstack, int i1) {
        switch (i0) {
            case 0:
                itemstack.b = MathHelper.d((float)itemstack.b / (float)set.size());
                break;

            case 1:
                itemstack.b = 1;
                break;

            case 2:
                itemstack.b = itemstack.b().j();
        }

        itemstack.b += i1;
    }

    public static int a(TileEntity tileentity) {
        return tileentity instanceof IInventory ? b((IInventory)tileentity) : 0;
    }

    public static int b(IInventory iinventory) {
        if (iinventory == null) {
            return 0;
        }
        else {
            int i0 = 0;
            float f0 = 0.0F;

            for (int i1 = 0; i1 < iinventory.n_(); ++i1) {
                ItemStack itemstack = iinventory.a(i1);

                if (itemstack != null) {
                    f0 += (float)itemstack.b / (float)Math.min(iinventory.p_(), itemstack.c());
                    ++i0;
                }
            }

            f0 /= (float)iinventory.n_();
            return MathHelper.d(f0 * 14.0F) + (i0 > 0 ? 1 : 0);
        }
    }

    protected Slot a(Slot slot) {
        slot.e = this.c.size();
        this.c.add(slot);
        this.b.add((Object)null);
        return slot;
    }

    public void a(ICrafting icrafting) {
        if (this.e.contains(icrafting)) {
            throw new IllegalArgumentException("Listener already listening");
        }
        else {
            this.e.add(icrafting);
            icrafting.a(this, this.a());
            this.b();
        }
    }

    public List a() {
        ArrayList arraylist = Lists.newArrayList();

        for (int i0 = 0; i0 < this.c.size(); ++i0) {
            arraylist.add(((Slot)this.c.get(i0)).d());
        }

        return arraylist;
    }

    public void b() {
        for (int i0 = 0; i0 < this.c.size(); ++i0) {
            ItemStack itemstack = ((Slot)this.c.get(i0)).d();
            ItemStack itemstack1 = (ItemStack)this.b.get(i0);

            if (!ItemStack.b(itemstack1, itemstack)) {
                itemstack1 = itemstack == null ? null : itemstack.k();
                this.b.set(i0, itemstack1);

                for (int i1 = 0; i1 < this.e.size(); ++i1) {
                    ((ICrafting)this.e.get(i1)).a(this, i0, itemstack1);
                }
            }
        }
    }

    public boolean a(EntityPlayer entityplayer, int i0) {
        return false;
    }

    public Slot a(IInventory iinventory, int i0) {
        for (int i1 = 0; i1 < this.c.size(); ++i1) {
            Slot slot = (Slot)this.c.get(i1);

            if (slot.a(iinventory, i0)) {
                return slot;
            }
        }

        return null;
    }

    public Slot a(int i0) {
        return (Slot)this.c.get(i0);
    }

    public ItemStack b(EntityPlayer entityplayer, int i0) {
        Slot slot = (Slot)this.c.get(i0);

        return slot != null ? slot.d() : null;
    }

    public ItemStack a(int i0, int i1, int i2, EntityPlayer entityplayer) {
        ItemStack itemstack = null;
        InventoryPlayer inventoryplayer = entityplayer.bg;
        int i3;
        ItemStack itemstack1;

        if (i2 == 5) {
            int i4 = this.g;

            this.g = c(i1);
            if ((i4 != 1 || this.g != 2) && i4 != this.g) {
                this.d();
            }
            else if (inventoryplayer.p() == null) {
                this.d();
            }
            else if (this.g == 0) {
                this.f = b(i1);
                if (a(this.f, entityplayer)) {
                    this.g = 1;
                    this.h.clear();
                }
                else {
                    this.d();
                }
            }
            else if (this.g == 1) {
                Slot slot = (Slot)this.c.get(i0);

                if (slot != null && a(slot, inventoryplayer.p(), true) && slot.a(inventoryplayer.p()) && inventoryplayer.p().b > this.h.size() && this.b(slot)) {
                    this.h.add(slot);
                }
            }
            else if (this.g == 2) {
                if (!this.h.isEmpty()) {
                    itemstack1 = inventoryplayer.p().k();
                    i3 = inventoryplayer.p().b;
                    Iterator iterator = this.h.iterator();

                    while (iterator.hasNext()) {
                        Slot slot1 = (Slot)iterator.next();

                        if (slot1 != null && a(slot1, inventoryplayer.p(), true) && slot1.a(inventoryplayer.p()) && inventoryplayer.p().b >= this.h.size() && this.b(slot1)) {
                            ItemStack itemstack2 = itemstack1.k();
                            int i5 = slot1.e() ? slot1.d().b : 0;

                            a(this.h, this.f, itemstack2, i5);
                            if (itemstack2.b > itemstack2.c()) {
                                itemstack2.b = itemstack2.c();
                            }

                            if (itemstack2.b > slot1.b(itemstack2)) {
                                itemstack2.b = slot1.b(itemstack2);
                            }

                            i3 -= itemstack2.b - i5;
                            slot1.d(itemstack2);
                        }
                    }

                    itemstack1.b = i3;
                    if (itemstack1.b <= 0) {
                        itemstack1 = null;
                    }

                    inventoryplayer.b(itemstack1);
                }

                this.d();
            }
            else {
                this.d();
            }
        }
        else if (this.g != 0) {
            this.d();
        }
        else {
            Slot slot2;
            int i6;
            ItemStack itemstack3;

            if ((i2 == 0 || i2 == 1) && (i1 == 0 || i1 == 1)) {
                if (i0 == -999) {
                    if (inventoryplayer.p() != null) {
                        if (i1 == 0) {
                            entityplayer.a(inventoryplayer.p(), true);
                            inventoryplayer.b((ItemStack)null);
                        }

                        if (i1 == 1) {
                            entityplayer.a(inventoryplayer.p().a(1), true);
                            if (inventoryplayer.p().b == 0) {
                                inventoryplayer.b((ItemStack)null);
                            }
                        }
                    }
                }
                else if (i2 == 1) {
                    if (i0 < 0) {
                        return null;
                    }

                    slot2 = (Slot)this.c.get(i0);
                    if (slot2 != null && slot2.a(entityplayer)) {
                        itemstack1 = this.b(entityplayer, i0);
                        if (itemstack1 != null) {
                            Item item = itemstack1.b();

                            itemstack = itemstack1.k();
                            if (slot2.d() != null && slot2.d().b() == item) {
                                this.a(i0, i1, true, entityplayer);
                            }
                        }
                    }
                }
                else {
                    if (i0 < 0) {
                        return null;
                    }

                    slot2 = (Slot)this.c.get(i0);
                    if (slot2 != null) {
                        itemstack1 = slot2.d();
                        ItemStack itemstack4 = inventoryplayer.p();

                        if (itemstack1 != null) {
                            itemstack = itemstack1.k();
                        }

                        if (itemstack1 == null) {
                            if (itemstack4 != null && slot2.a(itemstack4)) {
                                i6 = i1 == 0 ? itemstack4.b : 1;
                                if (i6 > slot2.b(itemstack4)) {
                                    i6 = slot2.b(itemstack4);
                                }

                                if (itemstack4.b >= i6) {
                                    slot2.d(itemstack4.a(i6));
                                }

                                if (itemstack4.b == 0) {
                                    inventoryplayer.b((ItemStack)null);
                                }
                            }
                        }
                        else if (slot2.a(entityplayer)) {
                            if (itemstack4 == null) {
                                i6 = i1 == 0 ? itemstack1.b : (itemstack1.b + 1) / 2;
                                itemstack3 = slot2.a(i6);
                                inventoryplayer.b(itemstack3);
                                if (itemstack1.b == 0) {
                                    slot2.d((ItemStack)null);
                                }

                                slot2.a(entityplayer, inventoryplayer.p());
                            }
                            else if (slot2.a(itemstack4)) {
                                if (itemstack1.b() == itemstack4.b() && itemstack1.i() == itemstack4.i() && ItemStack.a(itemstack1, itemstack4)) {
                                    i6 = i1 == 0 ? itemstack4.b : 1;
                                    if (i6 > slot2.b(itemstack4) - itemstack1.b) {
                                        i6 = slot2.b(itemstack4) - itemstack1.b;
                                    }

                                    if (i6 > itemstack4.c() - itemstack1.b) {
                                        i6 = itemstack4.c() - itemstack1.b;
                                    }

                                    itemstack4.a(i6);
                                    if (itemstack4.b == 0) {
                                        inventoryplayer.b((ItemStack)null);
                                    }

                                    itemstack1.b += i6;
                                }
                                else if (itemstack4.b <= slot2.b(itemstack4)) {
                                    slot2.d(itemstack4);
                                    inventoryplayer.b(itemstack1);
                                }
                            }
                            else if (itemstack1.b() == itemstack4.b() && itemstack4.c() > 1 && (!itemstack1.f() || itemstack1.i() == itemstack4.i()) && ItemStack.a(itemstack1, itemstack4)) {
                                i6 = itemstack1.b;
                                if (i6 > 0 && i6 + itemstack4.b <= itemstack4.c()) {
                                    itemstack4.b += i6;
                                    itemstack1 = slot2.a(i6);
                                    if (itemstack1.b == 0) {
                                        slot2.d((ItemStack)null);
                                    }

                                    slot2.a(entityplayer, inventoryplayer.p());
                                }
                            }
                        }

                        slot2.f();
                    }
                }
            }
            else if (i2 == 2 && i1 >= 0 && i1 < 9) {
                slot2 = (Slot)this.c.get(i0);
                if (slot2.a(entityplayer)) {
                    itemstack1 = inventoryplayer.a(i1);
                    boolean flag0 = itemstack1 == null || slot2.d == inventoryplayer && slot2.a(itemstack1);

                    i6 = -1;
                    if (!flag0) {
                        i6 = inventoryplayer.j();
                        flag0 |= i6 > -1;
                    }

                    if (slot2.e() && flag0) {
                        itemstack3 = slot2.d();
                        inventoryplayer.a(i1, itemstack3.k());
                        if ((slot2.d != inventoryplayer || !slot2.a(itemstack1)) && itemstack1 != null) {
                            if (i6 > -1) {
                                inventoryplayer.a(itemstack1);
                                slot2.a(itemstack3.b);
                                slot2.d((ItemStack)null);
                                slot2.a(entityplayer, itemstack3);
                            }
                        }
                        else {
                            slot2.a(itemstack3.b);
                            slot2.d(itemstack1);
                            slot2.a(entityplayer, itemstack3);
                        }
                    }
                    else if (!slot2.e() && itemstack1 != null && slot2.a(itemstack1)) {
                        inventoryplayer.a(i1, (ItemStack)null);
                        slot2.d(itemstack1);
                    }
                }
            }
            else if (i2 == 3 && entityplayer.by.d && inventoryplayer.p() == null && i0 >= 0) {
                slot2 = (Slot)this.c.get(i0);
                if (slot2 != null && slot2.e()) {
                    itemstack1 = slot2.d().k();
                    itemstack1.b = itemstack1.c();
                    inventoryplayer.b(itemstack1);
                }
            }
            else if (i2 == 4 && inventoryplayer.p() == null && i0 >= 0) {
                slot2 = (Slot)this.c.get(i0);
                if (slot2 != null && slot2.e() && slot2.a(entityplayer)) {
                    itemstack1 = slot2.a(i1 == 0 ? 1 : slot2.d().b);
                    slot2.a(entityplayer, itemstack1);
                    entityplayer.a(itemstack1, true);
                }
            }
            else if (i2 == 6 && i0 >= 0) {
                slot2 = (Slot)this.c.get(i0);
                itemstack1 = inventoryplayer.p();
                if (itemstack1 != null && (slot2 == null || !slot2.e() || !slot2.a(entityplayer))) {
                    i3 = i1 == 0 ? 0 : this.c.size() - 1;
                    i6 = i1 == 0 ? 1 : -1;

                    for (int i7 = 0; i7 < 2; ++i7) {
                        for (int i8 = i3; i8 >= 0 && i8 < this.c.size() && itemstack1.b < itemstack1.c(); i8 += i6) {
                            Slot slot3 = (Slot)this.c.get(i8);

                            if (slot3.e() && a(slot3, itemstack1, true) && slot3.a(entityplayer) && this.a(itemstack1, slot3) && (i7 != 0 || slot3.d().b != slot3.d().c())) {
                                int i9 = Math.min(itemstack1.c() - itemstack1.b, slot3.d().b);
                                ItemStack itemstack5 = slot3.a(i9);

                                itemstack1.b += i9;
                                if (itemstack5.b <= 0) {
                                    slot3.d((ItemStack)null);
                                }

                                slot3.a(entityplayer, itemstack5);
                            }
                        }
                    }
                }

                this.b();
            }
        }

        return itemstack;
    }

    public boolean a(ItemStack itemstack, Slot slot) {
        return true;
    }

    protected void a(int i0, int i1, boolean flag0, EntityPlayer entityplayer) {
        this.a(i0, i1, 1, entityplayer);
    }

    public void b(EntityPlayer entityplayer) {
        if (entityplayer instanceof EntityPlayerMP) { // CanaryMod: NPC somehow called this causing a crash
            // CanaryMod: Inventory closing
            if (inventory != null) {
                ((Player)entityplayer.getCanaryHuman()).message("called me?");
                new InventoryHook(((EntityPlayerMP)entityplayer).getPlayer(), inventory, true).call();
            }
            //
        }
        InventoryPlayer inventoryplayer = entityplayer.bg;

        if (inventoryplayer.p() != null) {
            entityplayer.a(inventoryplayer.p(), false);
            inventoryplayer.b((ItemStack)null);
        }
    }

    public void a(IInventory iinventory) {
        this.b();
    }

    public void a(int i0, ItemStack itemstack) {
        this.a(i0).d(itemstack);
    }

    public boolean c(EntityPlayer entityplayer) {
        return !this.i.contains(entityplayer);
    }

    public void a(EntityPlayer entityplayer, boolean flag0) {
        if (flag0) {
            this.i.remove(entityplayer);
        }
        else {
            this.i.add(entityplayer);
        }
    }

    public abstract boolean a(EntityPlayer entityplayer);

    protected boolean a(ItemStack itemstack, int i0, int i1, boolean flag0) {
        boolean flag1 = false;
        int i2 = i0;

        if (flag0) {
            i2 = i1 - 1;
        }

        Slot slot;
        ItemStack itemstack1;

        if (itemstack.d()) {
            while (itemstack.b > 0 && (!flag0 && i2 < i1 || flag0 && i2 >= i0)) {
                slot = (Slot)this.c.get(i2);
                itemstack1 = slot.d();
                if (itemstack1 != null && itemstack1.b() == itemstack.b() && (!itemstack.f() || itemstack.i() == itemstack1.i()) && ItemStack.a(itemstack, itemstack1)) {
                    int i3 = itemstack1.b + itemstack.b;

                    if (i3 <= itemstack.c()) {
                        itemstack.b = 0;
                        itemstack1.b = i3;
                        slot.f();
                        flag1 = true;
                    }
                    else if (itemstack1.b < itemstack.c()) {
                        itemstack.b -= itemstack.c() - itemstack1.b;
                        itemstack1.b = itemstack.c();
                        slot.f();
                        flag1 = true;
                    }
                }

                if (flag0) {
                    --i2;
                }
                else {
                    ++i2;
                }
            }
        }

        if (itemstack.b > 0) {
            if (flag0) {
                i2 = i1 - 1;
            }
            else {
                i2 = i0;
            }

            while (!flag0 && i2 < i1 || flag0 && i2 >= i0) {
                slot = (Slot)this.c.get(i2);
                itemstack1 = slot.d();
                if (itemstack1 == null) {
                    slot.d(itemstack.k());
                    slot.f();
                    itemstack.b = 0;
                    flag1 = true;
                    break;
                }

                if (flag0) {
                    --i2;
                }
                else {
                    ++i2;
                }
            }
        }

        return flag1;
    }

    protected void d() {
        this.g = 0;
        this.h.clear();
    }

    public boolean b(Slot slot) {
        return true;
    }

    // CanaryMod: get and set inventory passed to the GUI.
    public Inventory getInventory() {
        return this.inventory;
    }

    /**
     * Mostly a copy of updateCraftingResults()
     * The only change is to bypass a check that prevents crafting-result slot updates.
     */
    public void updateChangedSlots() {
        for (int i = 0; i < this.c.size(); ++i) {
            ItemStack itemstack = ((Slot)this.c.get(i)).d();
            ItemStack itemstack1 = (ItemStack)this.b.get(i);

            if (!ItemStack.b(itemstack1, itemstack)) {
                itemstack1 = itemstack == null ? null : itemstack.k();
                this.b.set(i, itemstack1);

                /* Change from updateCraftingResults() here.
                 * Originally (or similar format depending on Notchian updates):
                 * for (int j = 0; j < this.e.size(); ++j) {
                 *     ((ICrafting) this.e.get(j)).a(this, i, itemstack1);
                 * }
                 *
                 * Now:
                 */
                sendUpdateToCrafters(i, itemstack);
                // End change.
            }
        }
    }

    private void sendUpdateToCrafters(int slotIndex, ItemStack itemstack) {
        for (int j = 0; j < this.e.size(); ++j) {
            if (this.e.get(j) instanceof EntityPlayerMP) {
                ((EntityPlayerMP)this.e.get(j)).updateSlot(this.d, slotIndex, itemstack);
            }
        }
    }

    public void updateSlot(int index) {
        Slot slot = getSlot(index);
        if (slot == null) {
            return;
        }

        ItemStack oitemstack = slot.d();
        if (oitemstack != null) {
            oitemstack = oitemstack.k();
        }

        sendUpdateToCrafters(index, oitemstack);
    }

    public Slot getSlot(int index) {
        if (index < 0 || index >= this.c.size()) {
            return null;
        }
        return this.a(index);
    }
}
