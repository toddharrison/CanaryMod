package net.minecraft.entity.passive;

import net.canarymod.api.entity.living.animal.CanaryCow;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;


public class EntityCow extends EntityAnimal {

    public EntityCow(World world) {
        super(world);
        this.a(0.9F, 1.3F);
        ((PathNavigateGround) this.s()).a(true);
        this.i.a(0, new EntityAISwimming(this));
        this.i.a(1, new EntityAIPanic(this, 2.0D));
        this.i.a(2, new EntityAIMate(this, 1.0D));
        this.i.a(3, new EntityAITempt(this, 1.25D, Items.O, false));
        this.i.a(4, new EntityAIFollowParent(this, 1.25D));
        this.i.a(5, new EntityAIWander(this, 1.0D));
        this.i.a(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.i.a(7, new EntityAILookIdle(this));
        this.entity = new CanaryCow(this); // CanaryMod: Wrap Entity
    }

    protected void aW() {
        super.aW();
        this.a(SharedMonsterAttributes.a).a(10.0D);
        this.a(SharedMonsterAttributes.d).a(0.20000000298023224D);
    }

    protected String z() {
        return "mob.cow.say";
    }

    protected String bn() {
        return "mob.cow.hurt";
    }

    protected String bo() {
        return "mob.cow.hurt";
    }

    protected void a(BlockPos blockpos, Block block) {
        this.a("mob.cow.step", 0.15F, 1.0F);
    }

    protected float bA() {
        return 0.4F;
    }

    protected Item A() {
        return Items.aF;
    }

    protected void b(boolean flag0, int i0) {
        int i1 = this.V.nextInt(3) + this.V.nextInt(1 + i0);

        int i2;

        for (i2 = 0; i2 < i1; ++i2) {
            this.a(Items.aF, 1);
        }

        i1 = this.V.nextInt(3) + 1 + this.V.nextInt(1 + i0);

        for (i2 = 0; i2 < i1; ++i2) {
            if (this.au()) {
                this.a(Items.bj, 1);
            }
            else {
                this.a(Items.bi, 1);
            }
        }

    }

    public boolean a(EntityPlayer entityplayer) {
        ItemStack itemstack = entityplayer.bg.h();

        if (itemstack != null && itemstack.b() == Items.aw && !entityplayer.by.d) {
            if (itemstack.b-- == 1) {
                entityplayer.bg.a(entityplayer.bg.c, new ItemStack(Items.aG));
            }
            else if (!entityplayer.bg.a(new ItemStack(Items.aG))) {
                entityplayer.a(new ItemStack(Items.aG, 1, 0), false);
            }

            return true;
        }
        else {
            return super.a(entityplayer);
        }
    }

    public EntityCow b(EntityAgeable entityageable) {
        return new EntityCow(this.o);
    }

    public float aR() {
        return this.K;
    }

    public EntityAgeable a(EntityAgeable entityageable) {
        return this.b(entityageable);
    }
}
