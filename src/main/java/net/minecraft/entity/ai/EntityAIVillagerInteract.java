package net.minecraft.entity.ai;


import net.canarymod.api.ai.CanaryAIVillagerInteract;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;


public class EntityAIVillagerInteract extends EntityAIWatchClosest2 {

    private int e;
    private EntityVillager f;
   
    public EntityAIVillagerInteract(EntityVillager entityvillager) {
        super(entityvillager, EntityVillager.class, 3.0F, 0.02F);
        this.f = entityvillager;
        this.canaryAI = new CanaryAIVillagerInteract(this); //CanaryMod: set our variable
    }

    public void c() {
        super.c();
        if (this.f.cq() && this.b instanceof EntityVillager && ((EntityVillager) this.b).cr()) {
            this.e = 10;
        } else {
            this.e = 0;
        }

    }

    public void e() {
        super.e();
        if (this.e > 0) {
            --this.e;
            if (this.e == 0) {
                InventoryBasic inventorybasic = this.f.co();

                for (int i0 = 0; i0 < inventorybasic.n_(); ++i0) {
                    ItemStack itemstack = inventorybasic.a(i0);
                    ItemStack itemstack1 = null;

                    if (itemstack != null) {
                        Item item = itemstack.b();
                        int i1;

                        if ((item == Items.P || item == Items.bS || item == Items.bR) && itemstack.b > 3) {
                            i1 = itemstack.b / 2;
                            itemstack.b -= i1;
                            itemstack1 = new ItemStack(item, i1, itemstack.i());
                        } else if (item == Items.O && itemstack.b > 5) {
                            i1 = itemstack.b / 2 / 3 * 3;
                            int i2 = i1 / 3;

                            itemstack.b -= i1;
                            itemstack1 = new ItemStack(Items.P, i2, 0);
                        }

                        if (itemstack.b <= 0) {
                            inventorybasic.a(i0, (ItemStack) null);
                        }
                    }

                    if (itemstack1 != null) {
                        double d0 = this.f.t - 0.30000001192092896D + (double) this.f.aR();
                        EntityItem entityitem = new EntityItem(this.f.o, this.f.s, d0, this.f.u, itemstack1);
                        float f0 = 0.3F;
                        float f1 = this.f.aI;
                        float f2 = this.f.z;

                        entityitem.v = (double) (-MathHelper.a(f1 / 180.0F * 3.1415927F) * MathHelper.b(f2 / 180.0F * 3.1415927F) * f0);
                        entityitem.x = (double) (MathHelper.b(f1 / 180.0F * 3.1415927F) * MathHelper.b(f2 / 180.0F * 3.1415927F) * f0);
                        entityitem.w = (double) (-MathHelper.a(f2 / 180.0F * 3.1415927F) * f0 + 0.1F);
                        entityitem.p();
                        this.f.o.d((Entity) entityitem);
                        break;
                    }
                }
            }
        }

    }
}
