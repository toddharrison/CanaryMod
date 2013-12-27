package net.minecraft.entity.passive;

import net.canarymod.api.entity.living.animal.CanaryChicken;
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
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityChicken extends EntityAnimal {

    public float bp;
    public float bq;
    public float br;
    public float bs;
    public float bt = 1.0F;
    public int bu;

    public EntityChicken(World world) {
        super(world);
        this.a(0.3F, 0.7F);
        this.bu = this.aa.nextInt(6000) + 6000;
        this.c.a(0, new EntityAISwimming(this));
        this.c.a(1, new EntityAIPanic(this, 1.4D));
        this.c.a(2, new EntityAIMate(this, 1.0D));
        this.c.a(3, new EntityAITempt(this, 1.0D, Items.N, false));
        this.c.a(4, new EntityAIFollowParent(this, 1.1D));
        this.c.a(5, new EntityAIWander(this, 1.0D));
        this.c.a(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.c.a(7, new EntityAILookIdle(this));
        this.entity = new CanaryChicken(this); // CanaryMod: Wrap Entity
    }

    public boolean bk() {
        return true;
    }

    protected void aD() {
        super.aD();
        this.a(SharedMonsterAttributes.a).a(4.0D);
        this.a(SharedMonsterAttributes.d).a(0.25D);
    }

    public void e() {
        super.e();
        this.bs = this.bp;
        this.br = this.bq;
        this.bq = (float) ((double) this.bq + (double) (this.E ? -1 : 4) * 0.3D);
        if (this.bq < 0.0F) {
            this.bq = 0.0F;
        }

        if (this.bq > 1.0F) {
            this.bq = 1.0F;
        }

        if (!this.E && this.bt < 1.0F) {
            this.bt = 1.0F;
        }

        this.bt = (float) ((double) this.bt * 0.9D);
        if (!this.E && this.x < 0.0D) {
            this.x *= 0.6D;
        }

        this.bp += this.bt * 2.0F;
        if (!this.f() && !this.p.E && --this.bu <= 0) {
            this.a("mob.chicken.plop", 1.0F, (this.aa.nextFloat() - this.aa.nextFloat()) * 0.2F + 1.0F);
            this.a(Items.aK, 1);
            this.bu = this.aa.nextInt(6000) + 6000;
        }
    }

    protected void b(float f0) {
    }

    protected String t() {
        return "mob.chicken.say";
    }

    protected String aT() {
        return "mob.chicken.hurt";
    }

    protected String aU() {
        return "mob.chicken.hurt";
    }

    protected void a(int i0, int i1, int i2, Block block) {
        this.a("mob.chicken.step", 0.15F, 1.0F);
    }

    protected Item u() {
        return Items.G;
    }

    protected void b(boolean flag0, int i0) {
        int i1 = this.aa.nextInt(3) + this.aa.nextInt(1 + i0);

        for (int i2 = 0; i2 < i1; ++i2) {
            this.a(Items.G, 1);
        }

        if (this.al()) {
            this.a(Items.bg, 1);
        }
        else {
            this.a(Items.bf, 1);
        }
    }

    public EntityChicken b(EntityAgeable entityageable) {
        return new EntityChicken(this.p);
    }

    public boolean c(ItemStack itemstack) {
        return itemstack != null && itemstack.b() instanceof ItemSeeds;
    }

    public EntityAgeable a(EntityAgeable entityageable) {
        return this.b(entityageable);
    }
}
