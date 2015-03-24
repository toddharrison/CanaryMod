package net.canarymod.api.inventory;

import net.canarymod.api.nbt.CanaryCompoundTag;
import net.canarymod.api.nbt.CanaryListTag;
import net.canarymod.api.nbt.CompoundTag;
import net.canarymod.api.nbt.ListTag;
import net.minecraft.item.ItemStack;

import java.util.Arrays;

public class CanaryCustomStorageInventory extends CanaryEntityInventory implements CustomStorageInventory {

    public CanaryCustomStorageInventory(NativeCustomStorageInventory inventory) {
        super(inventory);
    }

    @Override
    public String getInventoryName() {
        return inventory.d_();
    }

    @Override
    public InventoryType getInventoryType() {
        return InventoryType.CUSTOM;
    }

    @Override
    public void clearContents() {
        Arrays.fill(getHandle().c, null);
    }

    @Override
    public Item[] clearInventory() {
        ItemStack[] items = Arrays.copyOf(getHandle().c, getSize());

        clearContents();
        return CanaryItem.stackArrayToItemArray(items);
    }

    @Override
    public Item[] getContents() {
        return CanaryItem.stackArrayToItemArray(getHandle().c);
    }

    @Override
    public void setContents(Item[] items) {
        getHandle().c = Arrays.copyOf(CanaryItem.itemArrayToStackArray(items), getHandle().c.length);
    }

    @Override
    public void setInventoryName(String value) {
        getHandle().setName(value);
    }

    @Override
    public ListTag<CompoundTag> saveInventoryNBT() {
        ListTag<CompoundTag> listTag = new CanaryListTag<CompoundTag>();
        CompoundTag compoundTag;
        Item[] contents = getContents();

        for (int index = 0; index < this.getSize(); ++index) {
            if (this.getSlot(index) != null) {
                compoundTag = contents[index].getDataTag();
                compoundTag.put("Slot", (byte)index);

                listTag.add(compoundTag);
            }
        }

        return listTag;
    }

    @Override
    public void loadInventoryNBT(ListTag<CompoundTag> listTag) {
        Item[] temp = new Item[getSize()];
        for (CompoundTag compoundTag : listTag) {
            int slot = compoundTag.getByte("Slot") & 255;
            ItemStack itemstack = ItemStack.a(((CanaryCompoundTag)compoundTag).getHandle());

            if (itemstack != null) {
                if (slot >= 0 && slot < this.getSize()) {
                    temp[slot] = itemstack.getCanaryItem();
                }
            }
        }
        this.clearContents();
        this.setContents(temp);
    }

    @Override
    public void update() {
        getHandle().o_();
    }

    public NativeCustomStorageInventory getHandle() {
        return (NativeCustomStorageInventory) inventory;
    }

}
