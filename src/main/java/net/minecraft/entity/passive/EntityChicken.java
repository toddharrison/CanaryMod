package net.minecraft.entity.passive;

import net.canarymod.api.entity.living.animal.CanaryChicken;
import net.canarymod.api.entity.living.animal.Chicken;
import net.canarymod.hook.entity.ChickenLayEggHook;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityChicken extends EntityAnimal {

    public float bp;
    public float bq;
    public float br;
    public float bs;
    public float bt = 1.0F;
    public int bu;
    public boolean bv;

    public EntityChicken(World world) {
        super(world);
        this.a(0.3F, 0.7F);
        this.bu = this.Z.nextInt(6000) + 6000;
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
        this.bq = (float) ((double) this.bq + (double) (this.D ? -1 : 4) * 0.3D);
        if (this.bq < 0.0F) {
            this.bq = 0.0F;
        }

        if (this.bq > 1.0F) {
            this.bq = 1.0F;
        }

        if (!this.D && this.bt < 1.0F) {
            this.bt = 1.0F;
        }

        this.bt = (float) ((double) this.bt * 0.9D);
        if (!this.D && this.w < 0.0D) {
            this.w *= 0.6D;
        }

        this.bp += this.bt * 2.0F;
        if (!this.o.E && !this.f() && !this.bZ() && --this.bu <= 0) {
            this.a("mob.chicken.plop", 1.0F, (this.Z.nextFloat() - this.Z.nextFloat()) * 0.2F + 1.0F);
            //TODO REVIEW NEEDED            
            EntityItem item = this.a(Items.aK, 1);
            // CanaryMod: ChickenLayEggHook
            ChickenLayEggHook cleh = (ChickenLayEggHook) new ChickenLayEggHook((Chicken) this.getCanaryEntity(), item.getEntityItem().getItem(), this.Z.nextInt(6000) + 6000).call();
            item.getCanaryEntity().destroy();
            if (!cleh.isCanceled()) {
                this.bu = cleh.getNextEggIn();
                this.o.getCanaryWorld().dropItem(cleh.getChicken().getPosition(), cleh.getEgg());
            }
            //
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
        int i1 = this.Z.nextInt(3) + this.Z.nextInt(1 + i0);

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
        return new EntityChicken(this.o);
    }

    public boolean c(ItemStack itemstack) {
        return itemstack != null && itemstack.b() instanceof ItemSeeds;
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.bv = nbttagcompound.n("IsChickenJockey");
    }

    protected int e(EntityPlayer entityplayer) {
        return this.bZ() ? 10 : super.e(entityplayer);
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("IsChickenJockey", this.bv);
    }

    protected boolean v() {
        return this.bZ() && this.l == null;
    }

    public void ac() {
        super.ac();
        float f0 = MathHelper.a(this.aM * 3.1415927F / 180.0F);
        float f1 = MathHelper.b(this.aM * 3.1415927F / 180.0F);
        float f2 = 0.1F;
        float f3 = 0.0F;

        this.l.b(this.s + (double) (f2 * f0), this.t + (double) (this.N * 0.5F) + this.l.ad() + (double) f3, this.u - (double) (f2 * f1));
        if (this.l instanceof EntityLivingBase) {
            ((EntityLivingBase) this.l).aM = this.aM;
        }

    }

    public boolean bZ() {
        return this.bv;
    }

    public void i(boolean flag0) {
        this.bv = flag0;
    }

    public EntityAgeable a(EntityAgeable entityageable) {
        return this.b(entityageable);
    }
}
