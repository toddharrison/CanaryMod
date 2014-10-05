package net.minecraft.block;

import net.canarymod.api.world.position.BlockPosition;
import net.canarymod.hook.world.BlockPhysicsHook;
import net.canarymod.hook.world.RedstoneChangeHook;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public abstract class BlockBasePressurePlate extends Block {

    protected BlockBasePressurePlate(Material material) {
        super(material);
        this.a(CreativeTabs.d);
        this.a(true);
    }

    public void a(IBlockAccess iblockaccess, BlockPos blockpos) {
        this.d(iblockaccess.p(blockpos));
    }

    protected void d(IBlockState iblockstate) {
        boolean flag0 = this.e(iblockstate) > 0;
        float f0 = 0.0625F;

        if (flag0) {
            this.a(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.03125F, 0.9375F);
        }
        else {
            this.a(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.0625F, 0.9375F);
        }

    }

    public int a(World world) {
        return 20;
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

    public boolean b(IBlockAccess iblockaccess, BlockPos blockpos) {
        return true;
    }

    public boolean c(World world, BlockPos blockpos) {
        return this.m(world, blockpos.b());
    }

    public void a(World world, BlockPos blockpos, IBlockState iblockstate, Block block) {
        if (!this.m(world, blockpos.b())) {
            this.b(world, blockpos, iblockstate, 0);
            world.g(blockpos);
        }
    }

    private boolean m(World world, BlockPos blockpos) {
        return World.a((IBlockAccess) world, blockpos) || world.p(blockpos).c() instanceof BlockFence;
    }

    public void a(World world, BlockPos blockpos, IBlockState iblockstate, Random random) {
    }

    public void b(World world, BlockPos blockpos, IBlockState iblockstate, Random random) {
        if (!world.D) {
            int i0 = this.e(iblockstate);

            if (i0 > 0) {
                this.a(world, blockpos, iblockstate, i0);
            }

        }
    }

    public void a(World world, BlockPos blockpos, IBlockState iblockstate, Entity entity) {
        if (!world.D) {
            int i0 = this.e(iblockstate);

            if (i0 == 0) {
                this.a(world, blockpos, iblockstate, i0);
            }

        }
    }

    protected void a(World world, BlockPos blockpos, IBlockState iblockstate, int i0) {
        int i1 = this.e(world, blockpos);

        // CanaryMod: RedstoneChange
        if (i0 != i1) {
            // CanaryMod: Block Physics
            BlockPhysicsHook blockPhysics = (BlockPhysicsHook) new BlockPhysicsHook(world.getCanaryWorld().getBlockAt(new BlockPosition(blockpos)), false).call();
            if (blockPhysics.isCanceled()) {
                world.a(blockpos, this, this.a(world));  // Reschedule
                return;
            }
            //

            RedstoneChangeHook hook = (RedstoneChangeHook) new RedstoneChangeHook(world.getCanaryWorld().getBlockAt(new BlockPosition(blockpos)), i0, i1).call();
            if (hook.isCanceled()) {
                i1 = hook.getOldLevel();
            }
        }
        //

        boolean flag0 = i0 > 0;
        boolean flag1 = i1 > 0;

        if (i0 != i1) {
            iblockstate = this.a(iblockstate, i1);
            world.a(blockpos, iblockstate, 2);
            this.d(world, blockpos);
            world.b(blockpos, blockpos);
        }

        if (!flag1 && flag0) {
            world.a((double) blockpos.n() + 0.5D, (double) blockpos.o() + 0.1D, (double) blockpos.p() + 0.5D, "random.click", 0.3F, 0.5F);
        }
        else if (flag1 && !flag0) {
            world.a((double) blockpos.n() + 0.5D, (double) blockpos.o() + 0.1D, (double) blockpos.p() + 0.5D, "random.click", 0.3F, 0.6F);
        }

        if (flag1) {
            world.a(blockpos, (Block) this, this.a(world));
        }

    }

    protected AxisAlignedBB a(BlockPos blockpos) {
        float f0 = 0.125F;

        return new AxisAlignedBB((double) ((float) blockpos.n() + 0.125F), (double) blockpos.o(), (double) ((float) blockpos.p() + 0.125F), (double) ((float) (blockpos.n() + 1) - 0.125F), (double) blockpos.o() + 0.25D, (double) ((float) (blockpos.p() + 1) - 0.125F));
    }

    public void b(World world, BlockPos blockpos, IBlockState iblockstate) {
        // CanaryMod: Block Physics
        BlockPhysicsHook blockPhysics = (BlockPhysicsHook) new BlockPhysicsHook(world.getCanaryWorld().getBlockAt(new BlockPosition(blockpos)), false).call();
        if (blockPhysics.isCanceled()) {
            return;
        }
        //

        // CanaryMod: RedstoneChange
        int oldLvl = this.e(iblockstate);
        if (oldLvl > 0) {
            new RedstoneChangeHook(world.getCanaryWorld().getBlockAt(new BlockPosition(blockpos)), oldLvl, 0).call();
            this.d(world, blockpos);
        }
        //

        super.b(world, blockpos, iblockstate);
    }

    protected void d(World world, BlockPos blockpos) {
        world.c(blockpos, (Block) this);
        world.c(blockpos.b(), (Block) this);
    }

    public int a(IBlockAccess iblockaccess, BlockPos blockpos, IBlockState iblockstate, EnumFacing enumfacing) {
        return this.e(iblockstate);
    }

    public int b(IBlockAccess iblockaccess, BlockPos blockpos, IBlockState iblockstate, EnumFacing enumfacing) {
        return enumfacing == EnumFacing.UP ? this.e(iblockstate) : 0;
    }

    public boolean g() {
        return true;
    }

    public void h() {
        float f0 = 0.5F;
        float f1 = 0.125F;
        float f2 = 0.5F;

        this.a(0.0F, 0.375F, 0.0F, 1.0F, 0.625F, 1.0F);
    }

    public int i() {
        return 1;
    }

    protected abstract int e(World world, BlockPos blockpos);

    protected abstract int e(IBlockState iblockstate);

    protected abstract IBlockState a(IBlockState iblockstate, int i0);
}
