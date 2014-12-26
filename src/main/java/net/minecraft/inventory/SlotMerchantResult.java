package net.minecraft.inventory;


import net.canarymod.api.CanaryVillagerTrade;
import net.canarymod.api.entity.living.humanoid.Villager;
import net.canarymod.hook.player.VillagerTradeHook;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.village.MerchantRecipe;


public class SlotMerchantResult extends Slot {

    private final InventoryMerchant a;
    private final IMerchant h;
    private EntityPlayer b;
    private int c;

    public SlotMerchantResult(EntityPlayer entityplayer, IMerchant imerchant, InventoryMerchant inventorymerchant, int i0, int i1, int i2) {
        super(inventorymerchant, i0, i1, i2);
        this.b = entityplayer;
        this.h = imerchant;
        this.a = inventorymerchant;
    }

    public boolean a(ItemStack itemstack) {
        return false;
    }

    public ItemStack a(int i0) {
        if (this.e()) {
            this.c += Math.min(i0, this.d().b);
        }

        return super.a(i0);
    }

    protected void a(ItemStack itemstack, int i0) {
        this.c += i0;
        this.c(itemstack);
    }

    protected void c(ItemStack itemstack) {
        itemstack.a(this.b.o, this.b, this.c);
        this.c = 0;
    }

    public void a(EntityPlayer entityplayer, ItemStack itemstack) {
        this.c(itemstack);
        MerchantRecipe merchantrecipe = this.a.i();

        if (merchantrecipe != null) {
            // CanaryMod: VillagerTradeHook
            VillagerTradeHook hook = (VillagerTradeHook) new VillagerTradeHook(((EntityPlayerMP) entityplayer).getPlayer(), (Villager) ((EntityVillager) this.h).getCanaryEntity(), new CanaryVillagerTrade(merchantrecipe)).call();
            if (hook.isCanceled()) {
                return;
            }
            //

            ItemStack itemstack1 = this.a.a(0);
            ItemStack itemstack2 = this.a.a(1);

            if (this.a(merchantrecipe, itemstack1, itemstack2) || this.a(merchantrecipe, itemstack2, itemstack1)) {
                this.h.a(merchantrecipe);
                entityplayer.b(StatList.G);
                if (itemstack1 != null && itemstack1.b <= 0) {
                    itemstack1 = null;
                }

                if (itemstack2 != null && itemstack2.b <= 0) {
                    itemstack2 = null;
                }

                this.a.a(0, itemstack1);
                this.a.a(1, itemstack2);
            }
        }

    }

    private boolean a(MerchantRecipe merchantrecipe, ItemStack itemstack, ItemStack itemstack1) {
        ItemStack itemstack2 = merchantrecipe.a();
        ItemStack itemstack3 = merchantrecipe.b();

        if (itemstack != null && itemstack.b() == itemstack2.b()) {
            if (itemstack3 != null && itemstack1 != null && itemstack3.b() == itemstack1.b()) {
                itemstack.b -= itemstack2.b;
                itemstack1.b -= itemstack3.b;
                return true;
            }

            if (itemstack3 == null && itemstack1 == null) {
                itemstack.b -= itemstack2.b;
                return true;
            }
        }

        return false;
    }
}
