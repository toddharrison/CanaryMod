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
        ItemStack itemstack = entityplayer.bn.h();

        if (itemstack != null && itemstack.b() == Items.z && this.d() >= 0) {
            if (itemstack.b == 1) {
                entityplayer.bn.a(entityplayer.bn.c, new ItemStack(Items.A));
                return true;
            }

            if (entityplayer.bn.a(new ItemStack(Items.A)) && !entityplayer.bF.d) {
                entityplayer.bn.a(entityplayer.bn.c, 1);
                return true;
            }
        }

        if (itemstack != null && itemstack.b() == Items.aZ && this.d() >= 0) {
            this.B();
            this.p.a("largeexplode", this.t, this.u + (double) (this.O / 2.0F), this.v, 0.0D, 0.0D, 0.0D);
            if (!this.p.E) {
                EntityCow entitycow = new EntityCow(this.p);

                entitycow.b(this.t, this.u, this.v, this.z, this.A);
                entitycow.g(this.aS());
                entitycow.aN = this.aN;
                this.p.d((Entity) entitycow);

                for (int i0 = 0; i0 < 5; ++i0) {
                    this.p.d((Entity) (new EntityItem(this.p, this.t, this.u + (double) this.O, this.v, new ItemStack(Blocks.Q))));
                }
                itemstack.a(1, (EntityLivingBase) entityplayer);
                this.a("mob.sheep.shear", 1.0F, 1.0F);
            }

            return true;
        }
        else {
            return super.a(entityplayer);
        }
    }

    public EntityMooshroom c(EntityAgeable entityageable) {
        return new EntityMooshroom(this.p);
    }

    public EntityCow b(EntityAgeable entityageable) {
        return this.c(entityageable);
    }

    public EntityAgeable a(EntityAgeable entityageable) {
        return this.c(entityageable);
    }
}
