package net.minecraft.block;


import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import java.util.Iterator;
import java.util.Random;
import net.canarymod.api.world.position.BlockPosition;
import net.canarymod.hook.world.BlockPhysicsHook;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;


public class BlockTripWireHook extends Block {

    public static final PropertyDirection a = PropertyDirection.a("facing", (Predicate) EnumFacing.Plane.HORIZONTAL);
    public static final PropertyBool b = PropertyBool.a("powered");
    public static final PropertyBool M = PropertyBool.a("attached");
    public static final PropertyBool N = PropertyBool.a("suspended");

    public BlockTripWireHook() {
        super(Material.q);
        this.j(this.L.b().a(a, EnumFacing.NORTH).a(b, Boolean.valueOf(false)).a(M, Boolean.valueOf(false)).a(N, Boolean.valueOf(false)));
        this.a(CreativeTabs.d);
        this.a(true);
    }

    public IBlockState a(IBlockState iblockstate, IBlockAccess iblockaccess, BlockPos blockpos) {
        return iblockstate.a(N, Boolean.valueOf(!World.a(iblockaccess, blockpos.b())));
    }

    public AxisAlignedBB a(World world, BlockPos blockpos, IBlockState iblockstate) {
        return null;
    }

    public boolean c() {
        return false;
    }

    public boolean d() {
        return false;
    }

    public boolean a(World world, BlockPos blockpos, EnumFacing enumfacing) {
        return enumfacing.k().c() && world.p(blockpos.a(enumfacing.d())).c().t();
    }

    public boolean c(World world, BlockPos blockpos) {
        Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();

        EnumFacing enumfacing;

        do {
            if (!iterator.hasNext()) {
                return false;
            }

            enumfacing = (EnumFacing) iterator.next();
        } while (!world.p(blockpos.a(enumfacing)).c().t());

        return true;
    }

    public IBlockState a(World world, BlockPos blockpos, EnumFacing enumfacing, float f0, float f1, float f2, int i0, EntityLivingBase entitylivingbase) {
        IBlockState iblockstate = this.P().a(b, Boolean.valueOf(false)).a(M, Boolean.valueOf(false)).a(N, Boolean.valueOf(false));

        if (enumfacing.k().c()) {
            iblockstate = iblockstate.a(a, enumfacing);
        }

        return iblockstate;
    }

    public void a(World world, BlockPos blockpos, IBlockState iblockstate, EntityLivingBase entitylivingbase, ItemStack itemstack) {
        this.a(world, blockpos, iblockstate, false, false, -1, (IBlockState) null);
    }

    public void a(World world, BlockPos blockpos, IBlockState iblockstate, Block block) {
        if (block != this) {
            if (this.e(world, blockpos, iblockstate)) {
                EnumFacing enumfacing = (EnumFacing) iblockstate.b(a);

                if (!world.p(blockpos.a(enumfacing.d())).c().t()) {
                    this.b(world, blockpos, iblockstate, 0);
                    world.g(blockpos);
                }
            }

        }
    }

    public void a(World world, BlockPos blockpos, IBlockState iblockstate, boolean flag0, boolean flag1, int i0, IBlockState iblockstate1) {
        // CanaryMod: Block Physics
        BlockPhysicsHook blockPhysics = (BlockPhysicsHook) new BlockPhysicsHook(world.getCanaryWorld().getBlockAt(new BlockPosition(blockpos)), false).call();
        if (blockPhysics.isCanceled()) {
            return;
        }
        //

        EnumFacing enumfacing = (EnumFacing) iblockstate.b(a);
        boolean flag2 = ((Boolean) iblockstate.b(M)).booleanValue();
        boolean flag3 = ((Boolean) iblockstate.b(b)).booleanValue();
        boolean flag4 = !World.a((IBlockAccess) world, blockpos.b());
        boolean flag5 = !flag0;
        boolean flag6 = false;
        int i1 = 0;
        IBlockState[] aiblockstate = new IBlockState[42];

        BlockPos blockpos1;

        for (int i2 = 1; i2 < 42; ++i2) {
            blockpos1 = blockpos.a(enumfacing, i2);
            IBlockState iblockstate2 = world.p(blockpos1);

            if (iblockstate2.c() == Blocks.bR) {
                if (iblockstate2.b(a) == enumfacing.d()) {
                    i1 = i2;
                }
                break;
            }

            if (iblockstate2.c() != Blocks.bS && i2 != i0) {
                aiblockstate[i2] = null;
                flag5 = false;
            }
            else {
                if (i2 == i0) {
                    iblockstate2 = (IBlockState) Objects.firstNonNull(iblockstate1, iblockstate2);
                }

                boolean flag7 = !((Boolean) iblockstate2.b(BlockTripWire.N)).booleanValue();
                boolean flag8 = ((Boolean) iblockstate2.b(BlockTripWire.a)).booleanValue();
                boolean flag9 = ((Boolean) iblockstate2.b(BlockTripWire.b)).booleanValue();

                flag5 &= flag9 == flag4;
                flag6 |= flag7 && flag8;
                aiblockstate[i2] = iblockstate2;
                if (i2 == i0) {
                    world.a(blockpos, (Block) this, this.a(world));
                    flag5 &= flag7;
                }
            }
        }

        flag5 &= i1 > 1;
        flag6 &= flag5;
        IBlockState iblockstate3 = this.P().a(M, Boolean.valueOf(flag5)).a(b, Boolean.valueOf(flag6));

        if (i1 > 0) {
            blockpos1 = blockpos.a(enumfacing, i1);
            EnumFacing enumfacing1 = enumfacing.d();

            world.a(blockpos1, iblockstate3.a(a, enumfacing1), 3);
            this.b(world, blockpos1, enumfacing1);
            this.a(world, blockpos1, flag5, flag6, flag2, flag3);
        }

        this.a(world, blockpos, flag5, flag6, flag2, flag3);
        if (!flag0) {
            world.a(blockpos, iblockstate3.a(a, enumfacing), 3);
            if (flag1) {
                this.b(world, blockpos, enumfacing);
            }
        }

        if (flag2 != flag5) {
            for (int i3 = 1; i3 < i1; ++i3) {
                BlockPos blockpos2 = blockpos.a(enumfacing, i3);
                IBlockState iblockstate4 = aiblockstate[i3];

                if (iblockstate4 != null && world.p(blockpos2).c() != Blocks.a) {
                    world.a(blockpos2, iblockstate4.a(M, Boolean.valueOf(flag5)), 3);
                }
            }
        }

    }

    public void a(World world, BlockPos blockpos, IBlockState iblockstate, Random random) {
    }

    public void b(World world, BlockPos blockpos, IBlockState iblockstate, Random random) {
        this.a(world, blockpos, iblockstate, false, true, -1, (IBlockState) null);
    }

    private void a(World world, BlockPos blockpos, boolean flag0, boolean flag1, boolean flag2, boolean flag3) {
        if (flag1 && !flag3) {
            world.a((double) blockpos.n() + 0.5D, (double) blockpos.o() + 0.1D, (double) blockpos.p() + 0.5D, "random.click", 0.4F, 0.6F);
        }
        else if (!flag1 && flag3) {
            world.a((double) blockpos.n() + 0.5D, (double) blockpos.o() + 0.1D, (double) blockpos.p() + 0.5D, "random.click", 0.4F, 0.5F);
        }
        else if (flag0 && !flag2) {
            world.a((double) blockpos.n() + 0.5D, (double) blockpos.o() + 0.1D, (double) blockpos.p() + 0.5D, "random.click", 0.4F, 0.7F);
        }
        else if (!flag0 && flag2) {
            world.a((double) blockpos.n() + 0.5D, (double) blockpos.o() + 0.1D, (double) blockpos.p() + 0.5D, "random.bowhit", 0.4F, 1.2F / (world.s.nextFloat() * 0.2F + 0.9F));
        }

    }

    private void b(World world, BlockPos blockpos, EnumFacing enumfacing) {
        world.c(blockpos, (Block) this);
        world.c(blockpos.a(enumfacing.d()), (Block) this);
    }

    private boolean e(World world, BlockPos blockpos, IBlockState iblockstate) {
        if (!this.c(world, blockpos)) {
            this.b(world, blockpos, iblockstate, 0);
            world.g(blockpos);
            return false;
        }
        else {
            return true;
        }
    }

    public void a(IBlockAccess iblockaccess, BlockPos blockpos) {
        float f0 = 0.1875F;

        switch (BlockTripWireHook.SwitchEnumFacing.a[((EnumFacing) iblockaccess.p(blockpos).b(a)).ordinal()]) {
            case 1:
                this.a(0.0F, 0.2F, 0.5F - f0, f0 * 2.0F, 0.8F, 0.5F + f0);
                break;

            case 2:
                this.a(1.0F - f0 * 2.0F, 0.2F, 0.5F - f0, 1.0F, 0.8F, 0.5F + f0);
                break;

            case 3:
                this.a(0.5F - f0, 0.2F, 0.0F, 0.5F + f0, 0.8F, f0 * 2.0F);
                break;

            case 4:
                this.a(0.5F - f0, 0.2F, 1.0F - f0 * 2.0F, 0.5F + f0, 0.8F, 1.0F);
        }

    }

    public void b(World world, BlockPos blockpos, IBlockState iblockstate) {
        boolean flag0 = ((Boolean) iblockstate.b(M)).booleanValue();
        boolean flag1 = ((Boolean) iblockstate.b(b)).booleanValue();

        if (flag0 || flag1) {
            this.a(world, blockpos, iblockstate, true, false, -1, (IBlockState) null);
        }

        if (flag1) {
            world.c(blockpos, (Block) this);
            world.c(blockpos.a(((EnumFacing) iblockstate.b(a)).d()), (Block) this);
        }

        super.b(world, blockpos, iblockstate);
    }

    public int a(IBlockAccess iblockaccess, BlockPos blockpos, IBlockState iblockstate, EnumFacing enumfacing) {
        return ((Boolean) iblockstate.b(b)).booleanValue() ? 15 : 0;
    }

    public int b(IBlockAccess iblockaccess, BlockPos blockpos, IBlockState iblockstate, EnumFacing enumfacing) {
        return !((Boolean) iblockstate.b(b)).booleanValue() ? 0 : (iblockstate.b(a) == enumfacing ? 15 : 0);
    }

    public boolean g() {
        return true;
    }

    public IBlockState a(int i0) {
        return this.P().a(a, EnumFacing.b(i0 & 3)).a(b, Boolean.valueOf((i0 & 8) > 0)).a(M, Boolean.valueOf((i0 & 4) > 0));
    }

    public int c(IBlockState iblockstate) {
        byte b0 = 0;
        int i0 = b0 | ((EnumFacing) iblockstate.b(a)).b();

        if (((Boolean) iblockstate.b(b)).booleanValue()) {
            i0 |= 8;
        }

        if (((Boolean) iblockstate.b(M)).booleanValue()) {
            i0 |= 4;
        }

        return i0;
    }

    protected BlockState e() {
        return new BlockState(this, new IProperty[]{a, b, M, N});
    }

    static final class SwitchEnumFacing {

        static final int[] a = new int[EnumFacing.values().length];

        static {
            try {
                a[EnumFacing.EAST.ordinal()] = 1;
            }
            catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                a[EnumFacing.WEST.ordinal()] = 2;
            }
            catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }

            try {
                a[EnumFacing.SOUTH.ordinal()] = 3;
            }
            catch (NoSuchFieldError nosuchfielderror2) {
                ;
            }

            try {
                a[EnumFacing.NORTH.ordinal()] = 4;
            }
            catch (NoSuchFieldError nosuchfielderror3) {
                ;
            }

        }
    }
}
