package net.minecraft.block;

import com.google.common.base.Predicate;
import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.hook.world.RedstoneChangeHook;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityMinecartCommandBlock;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class BlockRailDetector extends BlockRailBase {

    public static final PropertyEnum b = PropertyEnum.a("shape", BlockRailBase.EnumRailDirection.class, new Predicate() {

                                                            public boolean a(BlockRailBase.EnumRailDirection p_a_1_) {
                                                                return p_a_1_ != BlockRailBase.EnumRailDirection.NORTH_EAST && p_a_1_ != BlockRailBase.EnumRailDirection.NORTH_WEST && p_a_1_ != BlockRailBase.EnumRailDirection.SOUTH_EAST && p_a_1_ != BlockRailBase.EnumRailDirection.SOUTH_WEST;
                                                            }

                                                            public boolean apply(Object p_apply_1_) {
                                                                return this.a((BlockRailBase.EnumRailDirection)p_apply_1_);
                                                            }
                                                        }
                                                       );
    public static final PropertyBool M = PropertyBool.a("powered");

    public BlockRailDetector() {
        super(true);
        this.j(this.L.b().a(M, Boolean.valueOf(false)).a(b, BlockRailBase.EnumRailDirection.NORTH_SOUTH));
        this.a(true);
    }

    public int a(World world) {
        return 20;
    }

    public boolean g() {
        return true;
    }

    public void a(World world, BlockPos blockpos, IBlockState iblockstate, Entity entity) {
        if (!world.D) {
            if (!((Boolean)iblockstate.b(M)).booleanValue()) {
                this.e(world, blockpos, iblockstate);
            }
        }
    }

    public void a(World world, BlockPos blockpos, IBlockState iblockstate, Random random) {
    }

    public void b(World world, BlockPos blockpos, IBlockState iblockstate, Random random) {
        if (!world.D && ((Boolean)iblockstate.b(M)).booleanValue()) {
            this.e(world, blockpos, iblockstate);
        }
    }

    public int a(IBlockAccess iblockaccess, BlockPos blockpos, IBlockState iblockstate, EnumFacing enumfacing) {
        return ((Boolean)iblockstate.b(M)).booleanValue() ? 15 : 0;
    }

    public int b(IBlockAccess iblockaccess, BlockPos blockpos, IBlockState iblockstate, EnumFacing enumfacing) {
        return !((Boolean)iblockstate.b(M)).booleanValue() ? 0 : (enumfacing == EnumFacing.UP ? 15 : 0);
    }

    private void e(World world, BlockPos blockpos, IBlockState iblockstate) {
        boolean flag0 = ((Boolean)iblockstate.b(M)).booleanValue();
        boolean flag1 = false;
        List list = this.a(world, blockpos, EntityMinecart.class, new Predicate[0]);

        // CanaryMod
        CanaryBlock changing = new CanaryBlock(iblockstate, blockpos, world);

        if (!list.isEmpty()) {
            flag1 = true;
        }

        if (flag1 && !flag0) {
            // CanaryMod: RedstoneChange; Rails on
            if (new RedstoneChangeHook(changing, 0, 15).call().isCanceled()) {
                return;
            }
            //
            world.a(blockpos, iblockstate.a(M, Boolean.valueOf(true)), 3);
            world.c(blockpos, (Block)this);
            world.c(blockpos.b(), (Block)this);
            world.b(blockpos, blockpos);
        }

        if (!flag1 && flag0) {
            // CanaryMod: RedstoneChange; Rails off
            if (new RedstoneChangeHook(changing, 15, 0).call().isCanceled()) {
                return;
            }
            //
            world.a(blockpos, iblockstate.a(M, Boolean.valueOf(false)), 3);
            world.c(blockpos, (Block)this);
            world.c(blockpos.b(), (Block)this);
            world.b(blockpos, blockpos);
        }

        if (flag1) {
            world.a(blockpos, (Block)this, this.a(world));
        }

        world.e(blockpos, this);
    }

    public void c(World world, BlockPos blockpos, IBlockState iblockstate) {
        super.c(world, blockpos, iblockstate);
        this.e(world, blockpos, iblockstate);
    }

    public IProperty l() {
        return b;
    }

    public boolean N() {
        return true;
    }

    public int l(World world, BlockPos blockpos) {
        if (((Boolean)world.p(blockpos).b(M)).booleanValue()) {
            List list = this.a(world, blockpos, EntityMinecartCommandBlock.class, new Predicate[0]);

            if (!list.isEmpty()) {
                return ((EntityMinecartCommandBlock)list.get(0)).j().j();
            }

            List list1 = this.a(world, blockpos, EntityMinecart.class, new Predicate[]{ IEntitySelector.c });

            if (!list1.isEmpty()) {
                return Container.b((IInventory)list1.get(0));
            }
        }

        return 0;
    }

    protected List a(World world, BlockPos blockpos, Class oclass0, Predicate... apredicate) {
        AxisAlignedBB axisalignedbb = this.a(blockpos);

        return apredicate.length != 1 ? world.a(oclass0, axisalignedbb) : world.a(oclass0, axisalignedbb, apredicate[0]);
    }

    private AxisAlignedBB a(BlockPos blockpos) {
        float f0 = 0.2F;

        return new AxisAlignedBB((double)((float)blockpos.n() + 0.2F), (double)blockpos.o(), (double)((float)blockpos.p() + 0.2F), (double)((float)(blockpos.n() + 1) - 0.2F), (double)((float)(blockpos.o() + 1) - 0.2F), (double)((float)(blockpos.p() + 1) - 0.2F));
    }

    public IBlockState a(int i0) {
        return this.P().a(b, BlockRailBase.EnumRailDirection.a(i0 & 7)).a(M, Boolean.valueOf((i0 & 8) > 0));
    }

    public int c(IBlockState iblockstate) {
        byte b0 = 0;
        int i0 = b0 | ((BlockRailBase.EnumRailDirection)iblockstate.b(b)).a();

        if (((Boolean)iblockstate.b(M)).booleanValue()) {
            i0 |= 8;
        }

        return i0;
    }

    protected BlockState e() {
        return new BlockState(this, new IProperty[]{ b, M });
    }
}
