package net.minecraft.entity.passive;

import com.google.common.collect.Maps;
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
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import java.util.Map;
import java.util.Random;

public class EntitySheep extends EntityAnimal {

    private static final Map bm = Maps.newEnumMap(EnumDyeColor.class);
    private final InventoryCrafting bk = new InventoryCrafting(new Container() {

        public boolean a(EntityPlayer p_a_1_) {
            return false;
        }
    }, 2, 1);
    private int bn;
    private EntityAIEatGrass bo = new EntityAIEatGrass(this);

    public EntitySheep(World world) {
        super(world);
        this.a(0.9F, 1.3F);
        ((PathNavigateGround) this.s()).a(true);
        this.i.a(0, new EntityAISwimming(this));
        this.i.a(1, new EntityAIPanic(this, 1.25D));
        this.i.a(2, new EntityAIMate(this, 1.0D));
        this.i.a(3, new EntityAITempt(this, 1.1D, Items.O, false));
        this.i.a(4, new EntityAIFollowParent(this, 1.1D));
        this.i.a(5, this.bo);
        this.i.a(6, new EntityAIWander(this, 1.0D));
        this.i.a(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.i.a(8, new EntityAILookIdle(this));
        this.bk.a(0, new ItemStack(Items.aW, 1, 0));
        this.bk.a(1, new ItemStack(Items.aW, 1, 0));
        this.entity = new CanarySheep(this); // CanaryMod: Wrap Entity
    }

    public static float[] a(EnumDyeColor enumdyecolor) {
        return (float[]) bm.get(enumdyecolor);
    }

    public static EnumDyeColor a(Random random) {
        int i0 = random.nextInt(100);

        return i0 < 5 ? EnumDyeColor.BLACK : (i0 < 10 ? EnumDyeColor.GRAY : (i0 < 15 ? EnumDyeColor.SILVER : (i0 < 18 ? EnumDyeColor.BROWN : (random.nextInt(500) == 0 ? EnumDyeColor.PINK : EnumDyeColor.WHITE))));
    }

    protected void E() {
        this.bn = this.bo.f();
        super.E();
    }

    public void m() {
        if (this.o.D) {
            this.bn = Math.max(0, this.bn - 1);
        }

        super.m();
    }

    protected void aW() {
        super.aW();
        this.a(SharedMonsterAttributes.a).a(8.0D);
        this.a(SharedMonsterAttributes.d).a(0.23000000417232513D);
    }

    protected void h() {
        super.h();
        this.ac.a(16, new Byte((byte) 0));
    }

    protected void b(boolean flag0, int i0) {
        if (!this.ck()) {
            this.a(new ItemStack(Item.a(Blocks.L), 1, this.cj().a()), 0.0F);
        }

        int i1 = this.V.nextInt(2) + 1 + this.V.nextInt(1 + i0);

        for (int i2 = 0; i2 < i1; ++i2) {
            if (this.au()) {
                this.a(Items.bn, 1);
            }
            else {
                this.a(Items.bm, 1);
            }
        }

    }

    protected Item A() {
        return Item.a(Blocks.L);
    }

    public boolean a(EntityPlayer entityplayer) {
        ItemStack itemstack = entityplayer.bg.h();

        if (itemstack != null && itemstack.b() == Items.be && !this.ck() && !this.i_()) {
            if (!this.o.D) {
                this.l(true);
                int i0 = 1 + this.V.nextInt(3);

                for (int i1 = 0; i1 < i0; ++i1) {
                    EntityItem entityitem = this.a(new ItemStack(Item.a(Blocks.L), 1, this.cj().a()), 1.0F);

                    entityitem.w += (double) (this.V.nextFloat() * 0.05F);
                    entityitem.v += (double) ((this.V.nextFloat() - this.V.nextFloat()) * 0.1F);
                    entityitem.x += (double) ((this.V.nextFloat() - this.V.nextFloat()) * 0.1F);
                }
            }

            itemstack.a(1, (EntityLivingBase) entityplayer);
            this.a("mob.sheep.shear", 1.0F, 1.0F);
        }

        return super.a(entityplayer);
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("Sheared", this.ck());
        nbttagcompound.a("Color", (byte) this.cj().a());
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.l(nbttagcompound.n("Sheared"));
        this.b(EnumDyeColor.b(nbttagcompound.d("Color")));
    }

    protected String z() {
        return "mob.sheep.say";
    }

    protected String bn() {
        return "mob.sheep.say";
    }

    protected String bo() {
        return "mob.sheep.say";
    }

    protected void a(BlockPos blockpos, Block block) {
        this.a("mob.sheep.step", 0.15F, 1.0F);
    }

    public EnumDyeColor cj() {
        return EnumDyeColor.b(this.ac.a(16) & 15);
    }

    public void b(EnumDyeColor enumdyecolor) {
        byte b0 = this.ac.a(16);

        this.ac.b(16, Byte.valueOf((byte) (b0 & 240 | enumdyecolor.a() & 15)));
    }

    public boolean ck() {
        return (this.ac.a(16) & 16) != 0;
    }

    public void l(boolean flag0) {
        byte b0 = this.ac.a(16);

        if (flag0) {
            this.ac.b(16, Byte.valueOf((byte) (b0 | 16)));
        }
        else {
            this.ac.b(16, Byte.valueOf((byte) (b0 & -17)));
        }

    }

    public EntitySheep b(EntityAgeable entityageable) {
        EntitySheep entitysheep = (EntitySheep) entityageable;
        EntitySheep entitysheep1 = new EntitySheep(this.o);

        entitysheep1.b(this.a((EntityAnimal) this, (EntityAnimal) entitysheep));
        return entitysheep1;
    }

    public void v() {
        this.l(false);
        if (this.i_()) {
            this.a(60);
        }

    }

    public IEntityLivingData a(DifficultyInstance difficultyinstance, IEntityLivingData ientitylivingdata) {
        ientitylivingdata = super.a(difficultyinstance, ientitylivingdata);
        this.b(a(this.o.s));
        return ientitylivingdata;
    }

    private EnumDyeColor a(EntityAnimal entityanimal, EntityAnimal entityanimal1) {
        int i0 = ((EntitySheep) entityanimal).cj().b();
        int i1 = ((EntitySheep) entityanimal1).cj().b();

        this.bk.a(0).b(i0);
        this.bk.a(1).b(i1);
        ItemStack itemstack = CraftingManager.a().a(this.bk, ((EntitySheep) entityanimal).o);
        int i2;

        if (itemstack != null && itemstack.b() == Items.aW) {
            i2 = itemstack.i();
        }
        else {
            i2 = this.o.s.nextBoolean() ? i0 : i1;
        }

        return EnumDyeColor.a(i2);
    }

    public float aR() {
        return 0.95F * this.K;
    }

    public EntityAgeable a(EntityAgeable entityageable) {
        return this.b(entityageable);
    }

    static {
        bm.put(EnumDyeColor.WHITE, new float[]{1.0F, 1.0F, 1.0F});
        bm.put(EnumDyeColor.ORANGE, new float[]{0.85F, 0.5F, 0.2F});
        bm.put(EnumDyeColor.MAGENTA, new float[]{0.7F, 0.3F, 0.85F});
        bm.put(EnumDyeColor.LIGHT_BLUE, new float[]{0.4F, 0.6F, 0.85F});
        bm.put(EnumDyeColor.YELLOW, new float[]{0.9F, 0.9F, 0.2F});
        bm.put(EnumDyeColor.LIME, new float[]{0.5F, 0.8F, 0.1F});
        bm.put(EnumDyeColor.PINK, new float[]{0.95F, 0.5F, 0.65F});
        bm.put(EnumDyeColor.GRAY, new float[]{0.3F, 0.3F, 0.3F});
        bm.put(EnumDyeColor.SILVER, new float[]{0.6F, 0.6F, 0.6F});
        bm.put(EnumDyeColor.CYAN, new float[]{0.3F, 0.5F, 0.6F});
        bm.put(EnumDyeColor.PURPLE, new float[]{0.5F, 0.25F, 0.7F});
        bm.put(EnumDyeColor.BLUE, new float[]{0.2F, 0.3F, 0.7F});
        bm.put(EnumDyeColor.BROWN, new float[]{0.4F, 0.3F, 0.2F});
        bm.put(EnumDyeColor.GREEN, new float[]{0.4F, 0.5F, 0.2F});
        bm.put(EnumDyeColor.RED, new float[]{0.6F, 0.2F, 0.2F});
        bm.put(EnumDyeColor.BLACK, new float[]{0.1F, 0.1F, 0.1F});
    }
}
