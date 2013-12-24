package net.minecraft.entity.passive;

import net.canarymod.api.entity.living.animal.CanaryCow;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityCow extends EntityAnimal {

    public EntityCow(World world) {
        super(world);
        this.a(0.9F, 1.3F);
        this.m().a(true);
        this.c.a(0, new EntityAISwimming(this));
        this.c.a(1, new EntityAIPanic(this, 2.0D));
        this.c.a(2, new EntityAIMate(this, 1.0D));
        this.c.a(3, new EntityAITempt(this, 1.25D, Items.O, false));
        this.c.a(4, new EntityAIFollowParent(this, 1.25D));
        this.c.a(5, new EntityAIWander(this, 1.0D));
        this.c.a(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.c.a(7, new EntityAILookIdle(this));
        this.entity = new CanaryCow(this); // CanaryMod: Wrap Entity
    }

    public boolean bk() {
        return true;
    }

    protected void aD() {
        super.aD();
        this.a(SharedMonsterAttributes.a).a(10.0D);
        this.a(SharedMonsterAttributes.d).a(0.20000000298023224D);
    }

    protected String t() {
        return "mob.cow.say";
    }

    protected String aT() {
        return "mob.cow.hurt";
    }

    protected String aU() {
        return "mob.cow.hurt";
    }

    protected void a(int i0, int i1, int i2, Block block) {
        this.a("mob.cow.step", 0.15F, 1.0F);
    }

    protected float bf() {
        return 0.4F;
    }

    protected Item u() {
        return Items.aA;
    }

    protected void b(boolean flag0, int i0) {
        int i1 = this.aa.nextInt(3) + this.aa.nextInt(1 + i0);

        int i2;

        for (i2 = 0; i2 < i1; ++i2) {
            this.a(Items.aA, 1);
        }

        i1 = this.aa.nextInt(3) + 1 + this.aa.nextInt(1 + i0);

        for (i2 = 0; i2 < i1; ++i2) {
            if (this.al()) {
                this.a(Items.be, 1);
            }
            else {
                this.a(Items.bd, 1);
            }
        }
    }

    public boolean a(EntityPlayer entityplayer) {
        ItemStack itemstack = entityplayer.bn.h();

        if (itemstack != null && itemstack.b() == Items.ar && !entityplayer.bF.d) {
            if (itemstack.b-- == 1) {
                entityplayer.bn.a(entityplayer.bn.c, new ItemStack(Items.aB));
            }
            else if (!entityplayer.bn.a(new ItemStack(Items.aB))) {
                entityplayer.a(new ItemStack(Items.aB, 1, 0), false);
            }

            return true;
        }
        else {
            return super.a(entityplayer);
        }
    }

    public EntityCow b(EntityAgeable entityageable) {
        return new EntityCow(this.p);
    }

    public EntityAgeable a(EntityAgeable entityageable) {
        return this.b(entityageable);
    }
}
