package net.minecraft.entity.projectile;

import net.canarymod.api.entity.throwable.CanaryEntityPotion;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

public class EntityPotion extends EntityThrowable {

    private ItemStack c;

    public EntityPotion(World world) {
        super(world);
        // CanaryMod
        this.gravity = 0.05F;
        this.entity = new CanaryEntityPotion(this); // Wrap Entity
        //
    }

    public EntityPotion(World world, EntityLivingBase entitylivingbase, int i0) {
        this(world, entitylivingbase, new ItemStack(Items.bn, 1, i0));
    }

    public EntityPotion(World world, EntityLivingBase entitylivingbase, ItemStack itemstack) {
        super(world, entitylivingbase);
        this.c = itemstack;
        // CanaryMod
        this.gravity = 0.05F;
        this.entity = new CanaryEntityPotion(this); // Wrap Entity
        //
    }

    public EntityPotion(World world, double d0, double d1, double d2, ItemStack itemstack) {
        super(world, d0, d1, d2);
        this.c = itemstack;
        // CanaryMod
        this.entity = new CanaryEntityPotion(this); // Wrap Entity
        this.gravity = 0.05F;
        //
    }

    // CanaryMod: remove unneeded method override
    // protected float i() {
    // return 0.05F;
    // }
    //

    protected float e() {
        return 0.5F;
    }

    protected float f() {
        return -20.0F;
    }

    public void a(int i0) {
        if (this.c == null) {
            this.c = new ItemStack(Items.bn, 1, 0);
        }

        this.c.b(i0);
    }

    public int k() {
        if (this.c == null) {
            this.c = new ItemStack(Items.bn, 1, 0);
        }

        return this.c.k();
    }

    protected void a(MovingObjectPosition movingobjectposition) {
        if (!this.p.E) {
            List list = Items.bn.g(this.c);

            if (list != null && !list.isEmpty()) {
                AxisAlignedBB axisalignedbb = this.D.b(4.0D, 2.0D, 4.0D);
                List list1 = this.p.a(EntityLivingBase.class, axisalignedbb);

                if (list1 != null && !list1.isEmpty()) {
                    Iterator iterator = list1.iterator();

                    while (iterator.hasNext()) {
                        EntityLivingBase entitylivingbase = (EntityLivingBase) iterator.next();
                        double d0 = this.e(entitylivingbase);

                        if (d0 < 16.0D) {
                            double d1 = 1.0D - Math.sqrt(d0) / 4.0D;

                            if (entitylivingbase == movingobjectposition.g) {
                                d1 = 1.0D;
                            }

                            Iterator iterator1 = list.iterator();

                            while (iterator1.hasNext()) {
                                PotionEffect potioneffect = (PotionEffect) iterator1.next();
                                int i0 = potioneffect.a();

                                if (Potion.a[i0].b()) {
                                    Potion.a[i0].a(this.j(), entitylivingbase, potioneffect.c(), d1);
                                }
                                else {
                                    int i1 = (int) (d1 * (double) potioneffect.b() + 0.5D);

                                    if (i1 > 20) {
                                        entitylivingbase.c(new PotionEffect(i0, i1, potioneffect.c()));
                                    }
                                }
                            }
                        }
                    }
                }
            }

            this.p.c(2002, (int) Math.round(this.t), (int) Math.round(this.u), (int) Math.round(this.v), this.k());
            this.B();
        }
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        if (nbttagcompound.b("Potion", 10)) {
            this.c = ItemStack.a(nbttagcompound.m("Potion"));
        }
        else {
            this.a(nbttagcompound.f("potionValue"));
        }

        if (this.c == null) {
            this.B();
        }
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        if (this.c != null) {
            nbttagcompound.a("Potion", (NBTBase) this.c.b(new NBTTagCompound()));
        }
    }
}
