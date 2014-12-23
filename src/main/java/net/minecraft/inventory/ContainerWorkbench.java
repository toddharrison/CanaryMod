package net.minecraft.inventory;

import net.canarymod.api.inventory.CanaryItem;
import net.canarymod.api.world.blocks.CanaryWorkbench;
import net.canarymod.hook.player.CraftHook;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.network.play.server.S2FPacketSetSlot;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class ContainerWorkbench extends Container {

    public InventoryCrafting a = new InventoryCrafting(this, 3, 3);
    public IInventory f = new InventoryCraftResult();
    public World g; // CanaryMod: private -> public
    public BlockPos h; // CanaryMod: private -> public

    public ContainerWorkbench(InventoryPlayer inventoryplayer, World world, BlockPos blockpos) {
        this.g = world;
        this.h = blockpos;
        this.a((Slot)(new SlotCrafting(inventoryplayer.d, this.a, this.f, 0, 124, 35)));

        int i0;
        int i1;

        for (i0 = 0; i0 < 3; ++i0) {
            for (i1 = 0; i1 < 3; ++i1) {
                this.a(new Slot(this.a, i1 + i0 * 3, 30 + i1 * 18, 17 + i0 * 18));
            }
        }

        for (i0 = 0; i0 < 3; ++i0) {
            for (i1 = 0; i1 < 9; ++i1) {
                this.a(new Slot(inventoryplayer, i1 + i0 * 9 + 9, 8 + i1 * 18, 84 + i0 * 18));
            }
        }

        for (i0 = 0; i0 < 9; ++i0) {
            this.a(new Slot(inventoryplayer, i0, 8 + i0 * 18, 142));
        }

        this.inventory = new CanaryWorkbench(this); // CanaryMod: Set inventory instance
        this.a((IInventory)this.a);
    }

    public void a(IInventory iinventory) {
        ItemStack result = CraftingManager.a().a(this.a, this.g);

        if (this.e.isEmpty()) {
            this.f.a(0, result);
            return;
        }

        // CanaryMod: Send custom recipe results to client
        EntityPlayerMP player = (EntityPlayerMP)this.e.get(0);

        // call CraftHook
        CraftHook hook = (CraftHook)new CraftHook(player.getPlayer(), (CanaryWorkbench)inventory, result == null ? null : result.getCanaryItem()).call();
        if (hook.isCanceled()) {
            result = null;
        }
        else {
            result = hook.getRecipeResult() == null ? null : ((CanaryItem)hook.getRecipeResult()).getHandle();
        }

        // Set custom result
        this.f.a(0, result);
        // And send player custom result
        player.a.a(new S2FPacketSetSlot(this.d, 0, result));
        //
    }

    public void b(EntityPlayer entityplayer) {
        super.b(entityplayer);
        if (!this.g.D) {
            for (int i0 = 0; i0 < 9; ++i0) {
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

        return this.g.p(this.h).c() != Blocks.ai ? false : entityplayer.e((double)this.h.n() + 0.5D, (double)this.h.o() + 0.5D, (double)this.h.p() + 0.5D) <= 64.0D;
    }

    public ItemStack b(EntityPlayer entityplayer, int i0) {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.c.get(i0);

        if (slot != null && slot.e()) {
            ItemStack itemstack1 = slot.d();

            itemstack = itemstack1.k();
            if (i0 == 0) {
                if (!this.a(itemstack1, 10, 46, true)) {
                    return null;
                }

                slot.a(itemstack1, itemstack);
            }
            else if (i0 >= 10 && i0 < 37) {
                if (!this.a(itemstack1, 37, 46, false)) {
                    return null;
                }
            }
            else if (i0 >= 37 && i0 < 46) {
                if (!this.a(itemstack1, 10, 37, false)) {
                    return null;
                }
            }
            else if (!this.a(itemstack1, 10, 46, false)) {
                return null;
            }

            if (itemstack1.b == 0) {
                slot.d((ItemStack)null);
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

    public boolean a(ItemStack itemstack, Slot slot) {
        return slot.d != this.f && super.a(itemstack, slot);
    }
}
