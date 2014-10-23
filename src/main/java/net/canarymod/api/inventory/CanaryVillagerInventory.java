package net.canarymod.api.inventory;

import java.util.Arrays;
import net.canarymod.api.CanaryVillagerTrade;
import net.canarymod.api.VillagerTrade;
import net.canarymod.api.entity.living.humanoid.Player;
import net.canarymod.api.entity.living.humanoid.Villager;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.inventory.InventoryMerchant;
import net.minecraft.item.ItemStack;

public class CanaryVillagerInventory extends CanaryEntityInventory implements VillagerInventory {

    VillagerTrade trade;

    public CanaryVillagerInventory(InventoryMerchant inventory) {
        super(inventory);
        this.trade = new CanaryVillagerTrade(inventory.i());
    }

    @Override
    public void clearContents() {
        Arrays.fill(getHandle().b, null);
    }

    @Override
    public Item[] clearInventory() {
        ItemStack[] items = Arrays.copyOf(getHandle().b, getSize());

        clearContents();
        return CanaryItem.stackArrayToItemArray(items);
    }

    @Override
    public Item[] getContents() {
        return CanaryItem.stackArrayToItemArray(getHandle().b);
    }

    @Override
    public String getInventoryName() {
        return inventory.d_();
    }

    @Override
    public void setContents(Item[] items) {
        getHandle().b = Arrays.copyOf(CanaryItem.itemArrayToStackArray(items), getHandle().b.length);

    }

    @Override
    public void setInventoryName(String value) {
        getHandle().setName(value);
    }

    @Override
    public void update() {
        getHandle().o_();
    }

    @Override
    public Villager getOwner() {
        return this.getHandle().getMerchant() instanceof EntityVillager ? (Villager) ((EntityVillager) this.getHandle().getMerchant()).getCanaryEntity() : null;
    }

    @Override
    public Player getPlayer() {
        return this.getHandle().getPlayer().getCanaryHuman() instanceof Player ? (Player) this.getHandle().getPlayer().getCanaryHuman() : null;
    }

    @Override
    public VillagerTrade getTrade() {
        return trade;
    }

    @Override
    public InventoryMerchant getHandle() {
        return (InventoryMerchant) inventory;
    }

}
