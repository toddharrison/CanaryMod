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
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;


public class EntityMooshroom extends EntityCow {

    public EntityMooshroom(World world) {
        super(world);
        this.a(0.9F, 1.3F);
        this.bl = Blocks.bw;
        this.entity = new CanaryMooshroom(this); // CanaryMod: Wrap Entity
    }

    public boolean a(EntityPlayer entityplayer) {
        ItemStack itemstack = entityplayer.bg.h();

        if (itemstack != null && itemstack.b() == Items.z && this.l() >= 0) {
            if (itemstack.b == 1) {
                entityplayer.bg.a(entityplayer.bg.c, new ItemStack(Items.A));
                return true;
            }

            if (entityplayer.bg.a(new ItemStack(Items.A)) && !entityplayer.by.d) {
                entityplayer.bg.a(entityplayer.bg.c, 1);
                return true;
            }
        }

        if (itemstack != null && itemstack.b() == Items.be && this.l() >= 0) {
            this.J();
            this.o.a(EnumParticleTypes.EXPLOSION_LARGE, this.s, this.t + (double) (this.K / 2.0F), this.u, 0.0D, 0.0D, 0.0D, new int[0]);
            if (!this.o.D) {
                EntityCow entitycow = new EntityCow(this.o);

                entitycow.b(this.s, this.t, this.u, this.y, this.z);
                entitycow.h(this.bm());
                entitycow.aG = this.aG;
                if (this.k_()) {
                    entitycow.a(this.aL());
                }

                this.o.d((Entity) entitycow);

                for (int i0 = 0; i0 < 5; ++i0) {
                    this.o.d((Entity) (new EntityItem(this.o, this.s, this.t + (double) this.K, this.u, new ItemStack(Blocks.Q))));
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
        return new EntityMooshroom(this.o);
    }

    public EntityCow b(EntityAgeable entityageable) {
        return this.c(entityageable);
    }

    public EntityAgeable a(EntityAgeable entityageable) {
        return this.c(entityageable);
    }
}
