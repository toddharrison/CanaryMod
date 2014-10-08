package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public abstract class BlockSlab extends Block {

    public static final PropertyEnum a = PropertyEnum.a("half", BlockSlab.EnumBlockHalf.class);

    public BlockSlab(Material material) {
        super(material);
        if (this.j()) {
            this.r = true;
        }
        else {
            this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        }

        this.e(255);
    }

    protected boolean G() {
        return false;
    }

    public void a(IBlockAccess iblockaccess, BlockPos blockpos) {
        if (this.j()) {
            this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        }
        else {
            IBlockState iblockstate = iblockaccess.p(blockpos);

            if (iblockstate.c() == this) {
                if (iblockstate.b(a) == BlockSlab.EnumBlockHalf.TOP) {
                    this.a(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
                }
                else {
                    this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
                }
            }
        }
    }

    public void h() {
        if (this.j()) {
            this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        }
        else {
            this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        }
    }

    public void a(World world, BlockPos blockpos, IBlockState iblockstate, AxisAlignedBB axisalignedbb, List list, Entity entity) {
        this.a((IBlockAccess)world, blockpos); // CanaryMod: cast fix
        super.a(world, blockpos, iblockstate, axisalignedbb, list, entity);
    }

    public boolean c() {
        return this.j();
    }

    public IBlockState a(World world, BlockPos blockpos, EnumFacing enumfacing, float f0, float f1, float f2, int i0, EntityLivingBase entitylivingbase) {
        IBlockState iblockstate = super.a(world, blockpos, enumfacing, f0, f1, f2, i0, entitylivingbase).a(a, BlockSlab.EnumBlockHalf.BOTTOM);

        return this.j() ? iblockstate : (enumfacing != EnumFacing.DOWN && (enumfacing == EnumFacing.UP || (double)f1 <= 0.5D) ? iblockstate : iblockstate.a(a, BlockSlab.EnumBlockHalf.TOP));
    }

    public int a(Random random) {
        return this.j() ? 2 : 1;
    }

    public boolean d() {
        return this.j();
    }

    public abstract String b(int i0);

    public int j(World world, BlockPos blockpos) {
        return super.j(world, blockpos) & 7;
    }

    public abstract boolean j();

    public abstract IProperty l();

    public abstract Object a(ItemStack itemstack);

    public static enum EnumBlockHalf implements IStringSerializable {

        TOP("TOP", 0, "top"),
        BOTTOM("BOTTOM", 1, "bottom");
        private final String c;

        private static final BlockSlab.EnumBlockHalf[] $VALUES = new BlockSlab.EnumBlockHalf[]{ TOP, BOTTOM };

        private EnumBlockHalf(String p_i45713_1_, int p_i45713_2_, String p_i45713_3_) {
            this.c = p_i45713_3_;
        }

        public String toString() {
            return this.c;
        }

        public String l() {
            return this.c;
        }

    }
}
