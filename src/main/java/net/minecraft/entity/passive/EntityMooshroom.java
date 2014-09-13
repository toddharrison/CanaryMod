package net.minecraft.entity.passive;

import net.canarymod.api.entity.living.animal.CanaryMooshroom;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityMooshroom extends EntityCow {

    public EntityMooshroom(World world) {
        super(world);
        this.a(0.9F, 1.3F);
        this.entity = new CanaryMooshroom(this); // CanaryMod: Wrap Entity
    }

    public boolean a(EntityPlayer entityplayer) {
        ItemStack itemstack = entityplayer.bm.h();

        if (itemstack != null && itemstack.b() == Items.z && this.d() >= 0) {
            if (itemstack.b == 1) {
                entityplayer.bm.a(entityplayer.bm.c, new ItemStack(Items.A));
                return true;
            }

            if (entityplayer.bm.a(new ItemStack(Items.A)) && !entityplayer.bE.d) {
                entityplayer.bm.a(entityplayer.bm.c, 1);
                return true;
            }
        }

        if (itemstack != null && itemstack.b() == Items.aZ && this.d() >= 0) {
            this.B();
            this.o.a("largeexplode", this.s, this.t + (double) (this.N / 2.0F), this.u, 0.0D, 0.0D, 0.0D);
            if (!this.o.E) {
                EntityCow entitycow = new EntityCow(this.o);

                entitycow.b(this.s, this.t, this.u, this.y, this.z);
                entitycow.g(this.aS());
                entitycow.aM = this.aM;
                this.o.d((Entity) entitycow);

                for (int i0 = 0; i0 < 5; ++i0) {
                    this.o.d((Entity) (new EntityItem(this.o, this.s, this.t + (double) this.N, this.u, new ItemStack(Blocks.Q))));
                }
                itemstack.a(1, (EntityLivingBase) entityplayer);
                this.a("mob.sheep.shear", 1.0F, 1.0F);
            }

            return true;
        } else {
            return super.a(entityplayer);
        }
    }

    public EntityMooshroom c(EntityAgeable entityageable) {
        return new EntityMooshroom(this.o);
    }

    public EntityCow b(EntityAgeable entityageable) {
        return this.c(entityageable);
    }

    public EntityAgeable a(EntityAgeable entityageable) {
        return this.c(entityageable);
    }
}
