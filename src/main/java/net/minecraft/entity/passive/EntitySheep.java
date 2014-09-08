package net.minecraft.entity.passive;

import net.canarymod.api.entity.living.animal.CanarySheep;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.Random;

public class EntitySheep extends EntityAnimal {

    private final InventoryCrafting bq = new InventoryCrafting(new Container() {

        public boolean a(EntityPlayer entityplayer) {
            return false;
        }
    }, 2, 1);
    public static final float[][] bp = new float[][]{{1.0F, 1.0F, 1.0F}, {0.85F, 0.5F, 0.2F}, {0.7F, 0.3F, 0.85F}, {0.4F, 0.6F, 0.85F}, {0.9F, 0.9F, 0.2F}, {0.5F, 0.8F, 0.1F}, {0.95F, 0.5F, 0.65F}, {0.3F, 0.3F, 0.3F}, {0.6F, 0.6F, 0.6F}, {0.3F, 0.5F, 0.6F}, {0.5F, 0.25F, 0.7F}, {0.2F, 0.3F, 0.7F}, {0.4F, 0.3F, 0.2F}, {0.4F, 0.5F, 0.2F}, {0.6F, 0.2F, 0.2F}, {0.1F, 0.1F, 0.1F}};
    private int br;
    private EntityAIEatGrass bs = new EntityAIEatGrass(this);

    public EntitySheep(World world) {
        super(world);
        this.a(0.9F, 1.3F);
        this.m().a(true);
        this.c.a(0, new EntityAISwimming(this));
        this.c.a(1, new EntityAIPanic(this, 1.25D));
        this.c.a(2, new EntityAIMate(this, 1.0D));
        this.c.a(3, new EntityAITempt(this, 1.1D, Items.O, false));
        this.c.a(4, new EntityAIFollowParent(this, 1.1D));
        this.c.a(5, this.bs);
        this.c.a(6, new EntityAIWander(this, 1.0D));
        this.c.a(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.c.a(8, new EntityAILookIdle(this));
        this.bq.a(0, new ItemStack(Items.aR, 1, 0));
        this.bq.a(1, new ItemStack(Items.aR, 1, 0));
        this.entity = new CanarySheep(this); // CanaryMod: Wrap Entity
    }

    protected boolean bk() {
        return true;
    }

    protected void bn() {
        this.br = this.bs.f();
        super.bn();
    }

    public void e() {
        if (this.o.E) {
            this.br = Math.max(0, this.br - 1);
        }

        super.e();
    }

    protected void aD() {
        super.aD();
        this.a(SharedMonsterAttributes.a).a(8.0D);
        this.a(SharedMonsterAttributes.d).a(0.23000000417232513D);
    }

    protected void c() {
        super.c();
        this.af.a(16, new Byte((byte) 0));
    }

    protected void b(boolean flag0, int i0) {
        if (!this.ca()) {
            this.a(new ItemStack(Item.a(Blocks.L), 1, this.bZ()), 0.0F);
        }
    }

    protected Item u() {
        return Item.a(Blocks.L);
    }

    public boolean a(EntityPlayer entityplayer) {
        ItemStack itemstack = entityplayer.bm.h();

        if (itemstack != null && itemstack.b() == Items.aZ && !this.ca() && !this.f()) {
            if (!this.o.E) {
                this.i(true);
                int i0 = 1 + this.Z.nextInt(3);

                for (int i1 = 0; i1 < i0; ++i1) {
                    EntityItem entityitem = this.a(new ItemStack(Item.a(Blocks.L), 1, this.bZ()), 1.0F);

                    entityitem.w += (double) (this.Z.nextFloat() * 0.05F);
                    entityitem.v += (double) ((this.Z.nextFloat() - this.Z.nextFloat()) * 0.1F);
                    entityitem.x += (double) ((this.Z.nextFloat() - this.Z.nextFloat()) * 0.1F);
                }
            }

            itemstack.a(1, (EntityLivingBase) entityplayer);
            this.a("mob.sheep.shear", 1.0F, 1.0F);
        }

        return super.a(entityplayer);
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("Sheared", this.ca());
        nbttagcompound.a("Color", (byte) this.bZ());
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.i(nbttagcompound.n("Sheared"));
        this.s(nbttagcompound.d("Color"));
    }

    protected String t() {
        return "mob.sheep.say";
    }

    protected String aT() {
        return "mob.sheep.say";
    }

    protected String aU() {
        return "mob.sheep.say";
    }

    protected void a(int i0, int i1, int i2, Block block) {
        this.a("mob.sheep.step", 0.15F, 1.0F);
    }

    public int bZ() {
        return this.af.a(16) & 15;
    }

    public void s(int i0) {
        byte b0 = this.af.a(16);

        this.af.b(16, Byte.valueOf((byte) (b0 & 240 | i0 & 15)));
    }

    public boolean ca() {
        return (this.af.a(16) & 16) != 0;
    }

    public void i(boolean flag0) {
        byte b0 = this.af.a(16);

        if (flag0) {
            this.af.b(16, Byte.valueOf((byte) (b0 | 16)));
        }
        else {
            this.af.b(16, Byte.valueOf((byte) (b0 & -17)));
        }
    }

    public static int a(Random random) {
        int i0 = random.nextInt(100);

        return i0 < 5 ? 15 : (i0 < 10 ? 7 : (i0 < 15 ? 8 : (i0 < 18 ? 12 : (random.nextInt(500) == 0 ? 6 : 0))));
    }

    public EntitySheep b(EntityAgeable entityageable) {
        EntitySheep entitysheep = (EntitySheep) entityageable;
        EntitySheep entitysheep1 = new EntitySheep(this.o);
        int i0 = this.a(this, entitysheep);

        entitysheep1.s(15 - i0);
        return entitysheep1;
    }

    public void p() {
        this.i(false);
        if (this.f()) {
            this.a(60);
        }
    }

    public IEntityLivingData a(IEntityLivingData ientitylivingdata) {
        ientitylivingdata = super.a(ientitylivingdata);
        this.s(a(this.o.s));
        return ientitylivingdata;
    }

    private int a(EntityAnimal entityanimal, EntityAnimal entityanimal1) {
        int i0 = this.b(entityanimal);
        int i1 = this.b(entityanimal1);

        this.bq.a(0).b(i0);
        this.bq.a(1).b(i1);
        ItemStack itemstack = CraftingManager.a().a(this.bq, ((EntitySheep) entityanimal).o);
        int i2;

        if (itemstack != null && itemstack.b() == Items.aR) {
            i2 = itemstack.k();
        }
        else {
            i2 = this.o.s.nextBoolean() ? i0 : i1;
        }

        return i2;
    }

    private int b(EntityAnimal entityanimal) {
        return 15 - ((EntitySheep) entityanimal).bZ();
    }

    public EntityAgeable a(EntityAgeable entityageable) {
        return this.b(entityageable);
    }
}
