package net.minecraft.entity.passive;

import net.canarymod.api.entity.living.animal.CanaryChicken;
import net.canarymod.api.entity.living.animal.Chicken;
import net.canarymod.hook.entity.ChickenLayEggHook;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;


public class EntityChicken extends EntityAnimal {

    public float bk;
    public float bm;
    public float bn;
    public float bo;
    public float bp = 1.0F;
    public int bq;
    public boolean br;
   
    public EntityChicken(World world) {
        super(world);
        this.a(0.4F, 0.7F);
        this.bq = this.V.nextInt(6000) + 6000;
        this.i.a(0, new EntityAISwimming(this));
        this.i.a(1, new EntityAIPanic(this, 1.4D));
        this.i.a(2, new EntityAIMate(this, 1.0D));
        this.i.a(3, new EntityAITempt(this, 1.0D, Items.N, false));
        this.i.a(4, new EntityAIFollowParent(this, 1.1D));
        this.i.a(5, new EntityAIWander(this, 1.0D));
        this.i.a(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.i.a(7, new EntityAILookIdle(this));
        this.entity = new CanaryChicken(this); // CanaryMod: Wrap Entity
    }

    public float aR() {
        return this.K;
    }

    protected void aW() {
        super.aW();
        this.a(SharedMonsterAttributes.a).a(4.0D);
        this.a(SharedMonsterAttributes.d).a(0.25D);
    }

    public void m() {
        super.m();
        this.bo = this.bk;
        this.bn = this.bm;
        this.bm = (float) ((double) this.bm + (double) (this.C ? -1 : 4) * 0.3D);
        this.bm = MathHelper.a(this.bm, 0.0F, 1.0F);
        if (!this.C && this.bp < 1.0F) {
            this.bp = 1.0F;
        }

        this.bp = (float) ((double) this.bp * 0.9D);
        if (!this.C && this.w < 0.0D) {
            this.w *= 0.6D;
        }

        this.bk += this.bp * 2.0F;
        if (!this.o.D && !this.i_() && !this.cj() && --this.bq <= 0) {
            this.a("mob.chicken.plop", 1.0F, (this.V.nextFloat() - this.V.nextFloat()) * 0.2F + 1.0F);
            this.a(Items.aP, 1);
            this.bq = this.V.nextInt(6000) + 6000;
        }

    }

    public void e(float f0, float f1) {}

    protected String z() {
        return "mob.chicken.say";
    }

    protected String bn() {
        return "mob.chicken.hurt";
    }

    protected String bo() {
        return "mob.chicken.hurt";
    }

    protected void a(BlockPos blockpos, Block block) {
        this.a("mob.chicken.step", 0.15F, 1.0F);
    }

    protected Item A() {
        return Items.G;
    }

    protected void b(boolean flag0, int i0) {
        int i1 = this.V.nextInt(3) + this.V.nextInt(1 + i0);

        for (int i2 = 0; i2 < i1; ++i2) {
            this.a(Items.G, 1);
        }

        if (this.au()) {
            this.a(Items.bl, 1);
        } else {
            this.a(Items.bk, 1);
        }

    }

    public EntityChicken b(EntityAgeable entityageable) {
        return new EntityChicken(this.o);
    }

    public boolean d(ItemStack itemstack) {
        return itemstack != null && itemstack.b() == Items.N;
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.br = nbttagcompound.n("IsChickenJockey");
        if (nbttagcompound.c("EggLayTime")) {
            this.bq = nbttagcompound.f("EggLayTime");
        }

    }

    protected int b(EntityPlayer entityplayer) {
        return this.cj() ? 10 : super.b(entityplayer);
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("IsChickenJockey", this.br);
        nbttagcompound.a("EggLayTime", this.bq);
    }

    protected boolean C() {
        return this.cj() && this.l == null;
    }

    public void al() {
        super.al();
        float f0 = MathHelper.a(this.aG * 3.1415927F / 180.0F);
        float f1 = MathHelper.b(this.aG * 3.1415927F / 180.0F);
        float f2 = 0.1F;
        float f3 = 0.0F;

        this.l.b(this.s + (double) (f2 * f0), this.t + (double) (this.K * 0.5F) + this.l.am() + (double) f3, this.u - (double) (f2 * f1));
        if (this.l instanceof EntityLivingBase) {
            ((EntityLivingBase) this.l).aG = this.aG;
        }

    }

    public boolean cj() {
        return this.br;
    }

    public void l(boolean flag0) {
        this.br = flag0;
    }

    public EntityAgeable a(EntityAgeable entityageable) {
        return this.b(entityageable);
    }
}
