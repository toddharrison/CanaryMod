package net.minecraft.entity.passive;

import net.canarymod.api.entity.EntityType;
import net.canarymod.api.entity.living.animal.CanaryAnimal;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public abstract class EntityAnimal extends EntityAgeable implements IAnimals {

    protected Block bl;
    private int bk;
    private EntityPlayer bm;
   
    public EntityAnimal(World world) {
        super(world);
        this.bl = Blocks.c;

        //CanaryMod: Genericly wrapped animal
        this.entity = new CanaryAnimal(this) {

            @Override
            public String getFqName() {
                return "Generic Animal";
            }

            @Override
            public EntityType getEntityType() {
                return EntityType.GENERIC_ANIMAL;
            }

            @Override
            public EntityAnimal getHandle() {
                return (EntityAnimal) entity;
            }
        };
    }

    protected void E() {
        if (this.l() != 0) {
            this.bk = 0;
        }

        super.E();
    }

    public void m() {
        super.m();
        if (this.l() != 0) {
            this.bk = 0;
        }

        if (this.bk > 0) {
            --this.bk;
            if (this.bk % 10 == 0) {
                double d0 = this.V.nextGaussian() * 0.02D;
                double d1 = this.V.nextGaussian() * 0.02D;
                double d2 = this.V.nextGaussian() * 0.02D;

                this.o.a(EnumParticleTypes.HEART, this.s + (double) (this.V.nextFloat() * this.J * 2.0F) - (double) this.J, this.t + 0.5D + (double) (this.V.nextFloat() * this.K), this.u + (double) (this.V.nextFloat() * this.J * 2.0F) - (double) this.J, d0, d1, d2, new int[0]);
            }
        }

    }

    public boolean a(DamageSource damagesource, float f0) {
        if (this.b(damagesource)) {
            return false;
        } else {
            this.bk = 0;
            return super.a(damagesource, f0);
        }
    }

    public float a(BlockPos blockpos) {
        return this.o.p(blockpos.b()).c() == Blocks.c ? 10.0F : this.o.o(blockpos) - 0.5F;
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("InLove", this.bk);
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.bk = nbttagcompound.f("InLove");
    }

    public boolean bQ() {
        int i0 = MathHelper.c(this.s);
        int i1 = MathHelper.c(this.aQ().b);
        int i2 = MathHelper.c(this.u);
        BlockPos blockpos = new BlockPos(i0, i1, i2);

        return this.o.p(blockpos.b()).c() == this.bl && this.o.k(blockpos) > 8 && super.bQ();
    }

    public int w() {
        return 120;
    }

    protected boolean C() {
        return false;
    }

    protected int b(EntityPlayer entityplayer) {
        return 1 + this.o.s.nextInt(3);
    }

    public boolean d(ItemStack itemstack) {
        return itemstack == null ? false : itemstack.b() == Items.O;
    }

    public boolean a(EntityPlayer entityplayer) {
        ItemStack itemstack = entityplayer.bg.h();

        if (itemstack != null) {
            if (this.d(itemstack) && this.l() == 0 && this.bk <= 0) {
                this.a(entityplayer, itemstack);
                this.c(entityplayer);
                return true;
            }

            if (this.i_() && this.d(itemstack)) {
                this.a(entityplayer, itemstack);
                this.a((int) ((float) (-this.l() / 20) * 0.1F), true);
                return true;
            }
        }

        return super.a(entityplayer);
    }

    protected void a(EntityPlayer entityplayer, ItemStack itemstack) {
        if (!entityplayer.by.d) {
            --itemstack.b;
            if (itemstack.b <= 0) {
                entityplayer.bg.a(entityplayer.bg.c, (ItemStack) null);
            }
        }

    }

    public void c(EntityPlayer entityplayer) {
        this.bk = 600;
        this.bm = entityplayer;
        this.o.a((Entity) this, (byte) 18);
    }

    public EntityPlayer co() {
        return this.bm;
    }

    public boolean cp() {
        return this.bk > 0;
    }

    public void cq() {
        this.bk = 0;
    }

    public boolean a(EntityAnimal entityanimal) {
        return entityanimal == this ? false : (entityanimal.getClass() != this.getClass() ? false : this.cp() && entityanimal.cp());
    }
}
