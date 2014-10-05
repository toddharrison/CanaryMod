package net.minecraft.block;

import net.canarymod.api.world.position.BlockPosition;
import net.canarymod.hook.world.BlockPhysicsHook;
import net.canarymod.hook.world.RedstoneChangeHook;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public abstract class BlockButton extends Block {

    public static final PropertyDirection a = PropertyDirection.a("facing");
    public static final PropertyBool b = PropertyBool.a("powered");
    private final boolean M;

    protected BlockButton(boolean flag0) {
        super(Material.q);
        this.j(this.L.b().a(a, EnumFacing.NORTH).a(b, Boolean.valueOf(false)));
        this.a(true);
        this.a(CreativeTabs.d);
        this.M = flag0;
    }

    public AxisAlignedBB a(World world, BlockPos blockpos, IBlockState iblockstate) {
        return null;
    }

    public int a(World world) {
        return this.M ? 30 : 20;
    }

    public boolean c() {
        return false;
    }

    public boolean d() {
        return false;
    }

    public boolean a(World world, BlockPos blockpos, EnumFacing enumfacing) {
        return world.p(blockpos.a(enumfacing.d())).c().t();
    }

    public boolean c(World world, BlockPos blockpos) {
        EnumFacing[] aenumfacing = EnumFacing.values();
        int i0 = aenumfacing.length;

        for (int i1 = 0; i1 < i0; ++i1) {
            EnumFacing enumfacing = aenumfacing[i1];

            if (world.p(blockpos.a(enumfacing)).c().t()) {
                return true;
            }
        }

        return false;
    }

    public IBlockState a(World world, BlockPos blockpos, EnumFacing enumfacing, float f0, float f1, float f2, int i0, EntityLivingBase entitylivingbase) {
        return world.p(blockpos.a(enumfacing.d())).c().t() ? this.P().a(a, enumfacing).a(b, Boolean.valueOf(false)) : this.P().a(a, EnumFacing.DOWN).a(b, Boolean.valueOf(false));
    }

    public void a(World world, BlockPos blockpos, IBlockState iblockstate, Block block) {
        if (this.e(world, blockpos, iblockstate)) {
            EnumFacing enumfacing = (EnumFacing) iblockstate.b(a);

            if (!world.p(blockpos.a(enumfacing.d())).c().t()) {
                this.b(world, blockpos, iblockstate, 0);
                world.g(blockpos);
            }
        }

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
        this.d(iblockaccess.p(blockpos));
    }

    private void d(IBlockState iblockstate) {
        EnumFacing enumfacing = (EnumFacing) iblockstate.b(a);
        boolean flag0 = ((Boolean) iblockstate.b(b)).booleanValue();
        float f0 = 0.25F;
        float f1 = 0.375F;
        float f2 = (float) (flag0 ? 1 : 2) / 16.0F;
        float f3 = 0.125F;
        float f4 = 0.1875F;

        switch (BlockButton.SwitchEnumFacing.a[enumfacing.ordinal()]) {
            case 1:
                this.a(0.0F, 0.375F, 0.3125F, f2, 0.625F, 0.6875F);
                break;

            case 2:
                this.a(1.0F - f2, 0.375F, 0.3125F, 1.0F, 0.625F, 0.6875F);
                break;

            case 3:
                this.a(0.3125F, 0.375F, 0.0F, 0.6875F, 0.625F, f2);
                break;

            case 4:
                this.a(0.3125F, 0.375F, 1.0F - f2, 0.6875F, 0.625F, 1.0F);
                break;

            case 5:
                this.a(0.3125F, 0.0F, 0.375F, 0.6875F, 0.0F + f2, 0.625F);
                break;

            case 6:
                this.a(0.3125F, 1.0F - f2, 0.375F, 0.6875F, 1.0F, 0.625F);
        }

    }

    public boolean a(World world, BlockPos blockpos, IBlockState iblockstate, EntityPlayer entityplayer, EnumFacing enumfacing, float f0, float f1, float f2) {
        // CanaryMod: Block Physics
        BlockPhysicsHook blockPhysics = (BlockPhysicsHook) new BlockPhysicsHook(world.getCanaryWorld().getBlockAt(new BlockPosition(blockpos)), false).call();
        if (blockPhysics.isCanceled()) {
            return false;
        }
        //

        if (((Boolean) iblockstate.b(b)).booleanValue()) {
            return true;
        }

        // CanaryMod: RedstoneChange; Button On via Click
        RedstoneChangeHook hook = (RedstoneChangeHook) new RedstoneChangeHook(world.getCanaryWorld().getBlockAt(new BlockPosition(blockpos)), 0, 15).call();
        if (hook.isCanceled()) {
            return false;
            //
        }
        else {
            world.a(blockpos, iblockstate.a(b, Boolean.valueOf(true)), 3);
            world.b(blockpos, blockpos);
            world.a((double) blockpos.n() + 0.5D, (double) blockpos.o() + 0.5D, (double) blockpos.p() + 0.5D, "random.click", 0.3F, 0.6F);
            this.b(world, blockpos, (EnumFacing) iblockstate.b(a));
            world.a(blockpos, (Block) this, this.a(world));
            return true;
        }
    }

    public void b(World world, BlockPos blockpos, IBlockState iblockstate) {
        if (((Boolean) iblockstate.b(b)).booleanValue()) {
            // CanaryMod: RedstoneChangeHook (Button broke)
            new RedstoneChangeHook(world.getCanaryWorld().getBlockAt(new BlockPosition(blockpos)), 15, 0).call();
            //

            this.b(world, blockpos, (EnumFacing) iblockstate.b(a));
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

    public void a(World world, BlockPos blockpos, IBlockState iblockstate, Random random) {
    }

    public void b(World world, BlockPos blockpos, IBlockState iblockstate, Random random) {
        if (!world.D) {
            if (((Boolean) iblockstate.b(b)).booleanValue()) {
                if (this.M) {
                    this.f(world, blockpos, iblockstate);
                }
                else {
                    // CanaryMod: Block Physics
                    BlockPhysicsHook blockPhysics = (BlockPhysicsHook) new BlockPhysicsHook(world.getCanaryWorld().getBlockAt(new BlockPosition(blockpos)), false).call();
                    if (blockPhysics.isCanceled()) {
                        return;
                    }
                    //
                    // CanaryMod: RedstoneChange; Stone Button off
                    RedstoneChangeHook hook = (RedstoneChangeHook) new RedstoneChangeHook(world.getCanaryWorld().getBlockAt(new BlockPosition(blockpos)), 15, 0).call();
                    if (hook.isCanceled()) {
                        return;
                    }
                    //

                    world.a(blockpos, iblockstate.a(b, Boolean.valueOf(false)));
                    this.b(world, blockpos, (EnumFacing) iblockstate.b(a));
                    world.a((double) blockpos.n() + 0.5D, (double) blockpos.o() + 0.5D, (double) blockpos.p() + 0.5D, "random.click", 0.3F, 0.5F);
                    world.b(blockpos, blockpos);
                }
            }
        }
    }

    public void h() {
        float f0 = 0.1875F;
        float f1 = 0.125F;
        float f2 = 0.125F;

        this.a(0.5F - f0, 0.5F - f1, 0.5F - f2, 0.5F + f0, 0.5F + f1, 0.5F + f2);
    }

    public void a(World world, BlockPos blockpos, IBlockState iblockstate, Entity entity) {
        if (!world.D) {
            if (this.M) {
                if (!((Boolean) iblockstate.b(b)).booleanValue()) {
                    this.f(world, blockpos, iblockstate);
                }
            }
        }
    }

    private void f(World world, BlockPos blockpos, IBlockState iblockstate) {
        this.d(iblockstate);
        List list = world.a(EntityArrow.class, new AxisAlignedBB((double) blockpos.n() + this.B, (double) blockpos.o() + this.C, (double) blockpos.p() + this.D, (double) blockpos.n() + this.E, (double) blockpos.o() + this.F, (double) blockpos.p() + this.G));
        boolean flag0 = !list.isEmpty();
        boolean flag1 = ((Boolean) iblockstate.b(b)).booleanValue();

        if (flag0 && !flag1) {
            // CanaryMod: Block Physics
            BlockPhysicsHook blockPhysics = (BlockPhysicsHook) new BlockPhysicsHook(world.getCanaryWorld().getBlockAt(new BlockPosition(blockpos)), false).call();
            if (blockPhysics.isCanceled()) {
                world.a(blockpos, this, this.a(world));  // Reschedule
                return;
            }
            //

            // CanaryMod: RedstoneChange; Wood Button off
            RedstoneChangeHook hook = (RedstoneChangeHook) new RedstoneChangeHook(world.getCanaryWorld().getBlockAt(new BlockPosition(blockpos)), 15, 0).call();
            if (hook.isCanceled()) {
                world.a(blockpos, this, this.a(world));  // Reschedule
                return;
            }
            //
            world.a(blockpos, iblockstate.a(b, Boolean.valueOf(true)));
            this.b(world, blockpos, (EnumFacing) iblockstate.b(a));
            world.b(blockpos, blockpos);
            world.a((double) blockpos.n() + 0.5D, (double) blockpos.o() + 0.5D, (double) blockpos.p() + 0.5D, "random.click", 0.3F, 0.6F);
        }

        if (flag1 && !flag0) {
            // CanaryMod: RedstoneChange; Wood Button on
            RedstoneChangeHook hook = (RedstoneChangeHook) new RedstoneChangeHook(world.getCanaryWorld().getBlockAt(new BlockPosition(blockpos)), 0, 15).call();
            if (hook.isCanceled()) {
                world.a(blockpos, this, this.a(world)); // Reschedule
                return;
            }
            //
            world.a(blockpos, iblockstate.a(b, Boolean.valueOf(false)));
            this.b(world, blockpos, (EnumFacing) iblockstate.b(a));
            world.b(blockpos, blockpos);
            world.a((double) blockpos.n() + 0.5D, (double) blockpos.o() + 0.5D, (double) blockpos.p() + 0.5D, "random.click", 0.3F, 0.5F);
        }

        if (flag0) {
            world.a(blockpos, (Block) this, this.a(world));
        }

    }

    private void b(World world, BlockPos blockpos, EnumFacing enumfacing) {
        world.c(blockpos, (Block) this);
        world.c(blockpos.a(enumfacing.d()), (Block) this);
    }

    public IBlockState a(int i0) {
        EnumFacing enumfacing;

        switch (i0 & 7) {
            case 0:
                enumfacing = EnumFacing.DOWN;
                break;

            case 1:
                enumfacing = EnumFacing.EAST;
                break;

            case 2:
                enumfacing = EnumFacing.WEST;
                break;

            case 3:
                enumfacing = EnumFacing.SOUTH;
                break;

            case 4:
                enumfacing = EnumFacing.NORTH;
                break;

            case 5:
            default:
                enumfacing = EnumFacing.UP;
        }

        return this.P().a(a, enumfacing).a(b, Boolean.valueOf((i0 & 8) > 0));
    }

    public int c(IBlockState iblockstate) {
        int i0;

        switch (BlockButton.SwitchEnumFacing.a[((EnumFacing) iblockstate.b(a)).ordinal()]) {
            case 1:
                i0 = 1;
                break;

            case 2:
                i0 = 2;
                break;

            case 3:
                i0 = 3;
                break;

            case 4:
                i0 = 4;
                break;

            case 5:
            default:
                i0 = 5;
                break;

            case 6:
                i0 = 0;
        }

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

            try {
                a[EnumFacing.UP.ordinal()] = 5;
            }
            catch (NoSuchFieldError nosuchfielderror4) {
                ;
            }

            try {
                a[EnumFacing.DOWN.ordinal()] = 6;
            }
            catch (NoSuchFieldError nosuchfielderror5) {
                ;
            }

        }
    }
}
