package net.minecraft.block;

import net.canarymod.api.world.blocks.BlockType;
import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.hook.world.PistonExtendHook;
import net.canarymod.hook.world.PistonRetractHook;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockPistonStructureHelper;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityPiston;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public class BlockPistonBase extends Block {

    public static final PropertyDirection a = PropertyDirection.a("facing");
    public static final PropertyBool b = PropertyBool.a("extended");
    private final boolean M;
    // CanaryMod
    boolean attemptRetract = false;

    public BlockPistonBase(boolean flag0) {
        super(Material.H);
        this.j(this.L.b().a(a, EnumFacing.NORTH).a(b, Boolean.valueOf(false)));
        this.M = flag0;
        this.a(i);
        this.c(0.5F);
        this.a(CreativeTabs.d);
    }

    public boolean c() {
        return false;
    }

    public void a(World world, BlockPos blockpos, IBlockState iblockstate, EntityLivingBase entitylivingbase, ItemStack itemstack) {
        world.a(blockpos, iblockstate.a(a, a(world, blockpos, entitylivingbase)), 2);
        if (!world.D) {
            this.e(world, blockpos, iblockstate);
        }

    }

    public void a(World world, BlockPos blockpos, IBlockState iblockstate, Block block) {
        if (!world.D) {
            this.e(world, blockpos, iblockstate);
        }

    }

    public void c(World world, BlockPos blockpos, IBlockState iblockstate) {
        if (!world.D && world.s(blockpos) == null) {
            this.e(world, blockpos, iblockstate);
        }
    }

    public IBlockState a(World world, BlockPos blockpos, EnumFacing enumfacing, float f0, float f1, float f2, int i0, EntityLivingBase entitylivingbase) {
        return this.P().a(a, a(world, blockpos, entitylivingbase)).a(b, Boolean.valueOf(false));
    }

    private void e(World world, BlockPos blockpos, IBlockState iblockstate) {
        EnumFacing enumfacing = (EnumFacing) iblockstate.b(a);
        boolean flag0 = this.b(world, blockpos, enumfacing);

        // CanaryMod: Get Blocks
        CanaryBlock piston = new CanaryBlock((this.a ? BlockType.StickyPiston.getId() : BlockType.Piston.getId()), (byte) 0, i0, i1, i2, world.getCanaryWorld());
        CanaryBlock moving = new CanaryBlock((short) Block.b(world.a(i0 + Facing.b[i4], i1 + Facing.c[i4], i2 + Facing.d[i4])), (byte) 0, (i0 + Facing.b[i4]), (i1 + Facing.c[i4]), (i2 + Facing.d[i4]), world.getCanaryWorld());
        //

        if (flag0 && !((Boolean) iblockstate.b(b)).booleanValue()) {
            if ((new BlockPistonStructureHelper(world, blockpos, enumfacing, true)).a()) {
                // CanaryMod: PistonExtend
                PistonExtendHook hook = (PistonExtendHook) new PistonExtendHook(piston, moving).call();
                if (!hook.isCanceled()) {
                    world.c(blockpos, this, 0, enumfacing.a());
                }
                //
            }
        }
        else if (!flag0 && ((Boolean) iblockstate.b(b)).booleanValue()) {

            // CanaryMod: PistonRetract
            moving = new CanaryBlock((short) Block.b(world.a(i0 + Facing.b[i4] * 2, i1 + Facing.c[i4] * 2, i2 + Facing.d[i4] * 2)), (byte) 0, (i0 + Facing.b[i4]), (i1 + Facing.c[i4]), (i2 + Facing.d[i4]), world.getCanaryWorld());
            PistonRetractHook hook = (PistonRetractHook) new PistonRetractHook(piston, moving).call();
            attemptRetract = !hook.isCanceled();
            //
            world.a(blockpos, iblockstate.a(b, Boolean.valueOf(false)), 2);
            world.c(blockpos, this, 1, enumfacing.a());
        }
    }

    private boolean b(World world, BlockPos blockpos, EnumFacing enumfacing) {
        EnumFacing[] aenumfacing = EnumFacing.values();
        int i0 = aenumfacing.length;

        int i1;

        for (i1 = 0; i1 < i0; ++i1) {
            EnumFacing enumfacing1 = aenumfacing[i1];

            if (enumfacing1 != enumfacing && world.b(blockpos.a(enumfacing1), enumfacing1)) {
                return true;
            }
        }

        if (world.b(blockpos, EnumFacing.NORTH)) {
            return true;
        }
        else {
            BlockPos blockpos1 = blockpos.a();
            EnumFacing[] aenumfacing1 = EnumFacing.values();

            i1 = aenumfacing1.length;

            for (int i2 = 0; i2 < i1; ++i2) {
                EnumFacing enumfacing2 = aenumfacing1[i2];

                if (enumfacing2 != EnumFacing.DOWN && world.b(blockpos1.a(enumfacing2), enumfacing2)) {
                    return true;
                }
            }

            return false;
        }
    }

    public boolean a(World world, BlockPos blockpos, IBlockState iblockstate, int i0, int i1) {
        EnumFacing enumfacing = (EnumFacing) iblockstate.b(a);

        if (!world.D) {
            boolean flag0 = this.b(world, blockpos, enumfacing);

            if (flag0 && i0 == 1) {
                world.a(blockpos, iblockstate.a(b, Boolean.valueOf(true)), 2);
                return false;
            }

            if (!flag0 && i0 == 0) {
                return false;
            }
        }

        if (i0 == 0) {
            if (!this.a(world, blockpos, enumfacing, true)) {
                return false;
            }

            world.a(blockpos, iblockstate.a(b, Boolean.valueOf(true)), 2);
            world.a((double) blockpos.n() + 0.5D, (double) blockpos.o() + 0.5D, (double) blockpos.p() + 0.5D, "tile.piston.out", 0.5F, world.s.nextFloat() * 0.25F + 0.6F);
        }
        else if (i0 == 1) {
            TileEntity tileentity = world.s(blockpos.a(enumfacing));

            if (tileentity instanceof TileEntityPiston) {
                ((TileEntityPiston) tileentity).h();
            }

            world.a(blockpos, Blocks.M.P().a(BlockPistonMoving.a, enumfacing).a(BlockPistonMoving.b, this.M ? BlockPistonExtension.EnumPistonType.STICKY : BlockPistonExtension.EnumPistonType.DEFAULT), 3);
            world.a(blockpos, BlockPistonMoving.a(this.a(i1), enumfacing, false, true));
            if (this.M) {
                BlockPos blockpos1 = blockpos.a(enumfacing.g() * 2, enumfacing.h() * 2, enumfacing.i() * 2);
                Block block = world.p(blockpos1).c();
                boolean flag1 = false;

                if (block == Blocks.M) {
                    TileEntity tileentity1 = world.s(blockpos1);

                    if (tileentity1 instanceof TileEntityPiston) {
                        TileEntityPiston tileentitypiston = (TileEntityPiston) tileentity1;

                        if (tileentitypiston.e() == enumfacing && tileentitypiston.d()) {
                            tileentitypiston.h();
                            flag1 = true;
                        }
                    }
                }

                // CanaryMod: check attemptRetract
                if (attemptRetract && !flag1 && block.r() != Material.a && a(block, world, blockpos1, enumfacing.d(), false) && (block.i() == 0 || block == Blocks.J || block == Blocks.F)))
                {
                    this.a(world, blockpos, enumfacing, false);
                }
            }
            else {
                world.g(blockpos.a(enumfacing));
            }

            world.a((double) blockpos.n() + 0.5D, (double) blockpos.o() + 0.5D, (double) blockpos.p() + 0.5D, "tile.piston.in", 0.5F, world.s.nextFloat() * 0.15F + 0.6F);
        }

        return true;
    }

    public void a(IBlockAccess iblockaccess, BlockPos blockpos) {
        IBlockState iblockstate = iblockaccess.p(blockpos);

        if (iblockstate.c() == this && ((Boolean) iblockstate.b(b)).booleanValue()) {
            float f0 = 0.25F;
            EnumFacing enumfacing = (EnumFacing) iblockstate.b(a);

            if (enumfacing != null) {
                switch (BlockPistonBase.SwitchEnumFacing.a[enumfacing.ordinal()]) {
                    case 1:
                        this.a(0.0F, 0.25F, 0.0F, 1.0F, 1.0F, 1.0F);
                        break;

                    case 2:
                        this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
                        break;

                    case 3:
                        this.a(0.0F, 0.0F, 0.25F, 1.0F, 1.0F, 1.0F);
                        break;

                    case 4:
                        this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.75F);
                        break;

                    case 5:
                        this.a(0.25F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                        break;

                    case 6:
                        this.a(0.0F, 0.0F, 0.0F, 0.75F, 1.0F, 1.0F);
                }
            }
        }
        else {
            this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        }

    }

    public void h() {
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    public void a(World world, BlockPos blockpos, IBlockState iblockstate, AxisAlignedBB axisalignedbb, List list, Entity entity) {
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        super.a(world, blockpos, iblockstate, axisalignedbb, list, entity);
    }

    public AxisAlignedBB a(World world, BlockPos blockpos, IBlockState iblockstate) {
        this.a(world, blockpos);
        return super.a(world, blockpos, iblockstate);
    }

    public boolean d() {
        return false;
    }

    public static EnumFacing b(int i0) {
        int i1 = i0 & 7;

        return i1 > 5 ? null : EnumFacing.a(i1);
    }

    public static EnumFacing a(World world, BlockPos blockpos, EntityLivingBase entitylivingbase) {
        if (MathHelper.e((float) entitylivingbase.s - (float) blockpos.n()) < 2.0F && MathHelper.e((float) entitylivingbase.u - (float) blockpos.p()) < 2.0F) {
            double d0 = entitylivingbase.t + (double) entitylivingbase.aR();

            if (d0 - (double) blockpos.o() > 2.0D) {
                return EnumFacing.UP;
            }

            if ((double) blockpos.o() - d0 > 0.0D) {
                return EnumFacing.DOWN;
            }
        }

        return entitylivingbase.aO().d();
    }

    public static boolean a(Block block, World world, BlockPos blockpos, EnumFacing enumfacing, boolean flag0) {
        if (block == Blocks.Z) {
            return false;
        }
        else if (!world.af().a(blockpos)) {
            return false;
        }
        else if (blockpos.o() >= 0 && (enumfacing != EnumFacing.DOWN || blockpos.o() != 0)) {
            if (blockpos.o() <= world.U() - 1 && (enumfacing != EnumFacing.UP || blockpos.o() != world.U() - 1)) {
                if (block != Blocks.J && block != Blocks.F) {
                    if (block.g(world, blockpos) == -1.0F) {
                        return false;
                    }

                    if (block.i() == 2) {
                        return false;
                    }

                    if (block.i() == 1) {
                        if (!flag0) {
                            return false;
                        }

                        return true;
                    }
                }
                else if (((Boolean) world.p(blockpos).b(b)).booleanValue()) {
                    return false;
                }

                return !(block instanceof ITileEntityProvider);
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    private boolean a(World world, BlockPos blockpos, EnumFacing enumfacing, boolean flag0) {
        if (!flag0) {
            world.g(blockpos.a(enumfacing));
        }

        BlockPistonStructureHelper blockpistonstructurehelper = new BlockPistonStructureHelper(world, blockpos, enumfacing, flag0);
        List list = blockpistonstructurehelper.c();
        List list1 = blockpistonstructurehelper.d();

        if (!blockpistonstructurehelper.a()) {
            return false;
        }
        else {
            int i0 = list.size() + list1.size();
            Block[] ablock = new Block[i0];
            EnumFacing enumfacing1 = flag0 ? enumfacing : enumfacing.d();

            int i1;
            BlockPos blockpos1;

            for (i1 = list1.size() - 1; i1 >= 0; --i1) {
                blockpos1 = (BlockPos) list1.get(i1);
                Block block = world.p(blockpos1).c();

                block.b(world, blockpos1, world.p(blockpos1), 0);
                world.g(blockpos1);
                --i0;
                ablock[i0] = block;
            }

            IBlockState iblockstate;

            for (i1 = list.size() - 1; i1 >= 0; --i1) {
                blockpos1 = (BlockPos) list.get(i1);
                iblockstate = world.p(blockpos1);
                Block block1 = iblockstate.c();

                block1.c(iblockstate);
                world.g(blockpos1);
                blockpos1 = blockpos1.a(enumfacing1);
                world.a(blockpos1, Blocks.M.P().a(a, enumfacing), 4);
                world.a(blockpos1, BlockPistonMoving.a(iblockstate, enumfacing, flag0, false));
                --i0;
                ablock[i0] = block1;
            }

            BlockPos blockpos2 = blockpos.a(enumfacing);

            if (flag0) {
                BlockPistonExtension.EnumPistonType blockpistonextension_enumpistontype = this.M ? BlockPistonExtension.EnumPistonType.STICKY : BlockPistonExtension.EnumPistonType.DEFAULT;

                iblockstate = Blocks.K.P().a(BlockPistonExtension.a, enumfacing).a(BlockPistonExtension.b, blockpistonextension_enumpistontype);
                IBlockState iblockstate1 = Blocks.M.P().a(BlockPistonMoving.a, enumfacing).a(BlockPistonMoving.b, this.M ? BlockPistonExtension.EnumPistonType.STICKY : BlockPistonExtension.EnumPistonType.DEFAULT);

                world.a(blockpos2, iblockstate1, 4);
                world.a(blockpos2, BlockPistonMoving.a(iblockstate, enumfacing, true, false));
            }

            int i2;

            for (i2 = list1.size() - 1; i2 >= 0; --i2) {
                world.c((BlockPos) list1.get(i2), ablock[i0++]);
            }

            for (i2 = list.size() - 1; i2 >= 0; --i2) {
                world.c((BlockPos) list.get(i2), ablock[i0++]);
            }

            if (flag0) {
                world.c(blockpos2, (Block) Blocks.K);
                world.c(blockpos, (Block) this);
            }

            return true;
        }
    }

    public IBlockState a(int i0) {
        return this.P().a(a, b(i0)).a(b, Boolean.valueOf((i0 & 8) > 0));
    }

    public int c(IBlockState iblockstate) {
        byte b0 = 0;
        int i0 = b0 | ((EnumFacing) iblockstate.b(a)).a();

        if (((Boolean) iblockstate.b(b)).booleanValue()) {
            i0 |= 8;
        }

        return i0;
    }

    protected BlockState e() {
        return new BlockState(this, new IProperty[]{a, b});
    }

    static final class SwitchEnumFacing {

        static final int[] a = new int[EnumFacing.values().length];

        static {
            try {
                a[EnumFacing.DOWN.ordinal()] = 1;
            }
            catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                a[EnumFacing.UP.ordinal()] = 2;
            }
            catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                a[EnumFacing.NORTH.ordinal()] = 3;
            }
            catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                a[EnumFacing.SOUTH.ordinal()] = 4;
            }
            catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

            try {
                a[EnumFacing.WEST.ordinal()] = 5;
            }
            catch (NoSuchFieldError nosuchfielderror4) {
                ;
            }

            try {
                a[EnumFacing.EAST.ordinal()] = 6;
            }
            catch (NoSuchFieldError nosuchfielderror5) {
                ;
            }
        }
    }
}
