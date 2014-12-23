package net.minecraft.block;

import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.hook.world.RedstoneChangeHook;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public abstract class BlockRedstoneDiode extends BlockDirectional {

    protected final boolean M;

    protected BlockRedstoneDiode(boolean flag0) {
        super(Material.q);
        this.M = flag0;
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
    }

    public static boolean d(Block block) {
        return Blocks.bb.e(block) || Blocks.cj.e(block);
    }

    public boolean d() {
        return false;
    }

    public boolean c(World world, BlockPos blockpos) {
        return World.a((IBlockAccess)world, blockpos.b()) ? super.c(world, blockpos) : false;
    }

    public boolean d(World world, BlockPos blockpos) {
        return World.a((IBlockAccess)world, blockpos.b());
    }

    public void a(World world, BlockPos blockpos, IBlockState iblockstate, Random random) {
    }

    public void b(World world, BlockPos blockpos, IBlockState iblockstate, Random random) {
        if (!this.b((IBlockAccess)world, blockpos, iblockstate)) {
            boolean flag0 = this.e(world, blockpos, iblockstate);

            // CanaryMod
            CanaryBlock changing = CanaryBlock.getPooledBlock(iblockstate, blockpos, world);

            if (this.M && !flag0) {
                // CanaryMod: RedstoneChange; turning off
                if (new RedstoneChangeHook(changing, 15, 0).call().isCanceled()) {
                    return;
                }
                //
                world.a(blockpos, this.k(iblockstate), 2);
            }
            else if (!this.M) {
                // CanaryMod: RedstoneChange; turning on
                if (new RedstoneChangeHook(changing, 0, 15).call().isCanceled()) {
                    return;
                }
                //
                world.a(blockpos, this.e(iblockstate), 2);
                if (!flag0) {
                    world.a(blockpos, this.e(iblockstate).c(), this.m(iblockstate), -1);
                }
            }
        }
    }

    protected boolean l(IBlockState iblockstate) {
        return this.M;
    }

    public int b(IBlockAccess iblockaccess, BlockPos blockpos, IBlockState iblockstate, EnumFacing enumfacing) {
        return this.a(iblockaccess, blockpos, iblockstate, enumfacing);
    }

    public int a(IBlockAccess iblockaccess, BlockPos blockpos, IBlockState iblockstate, EnumFacing enumfacing) {
        return !this.l(iblockstate) ? 0 : (iblockstate.b(N) == enumfacing ? this.a(iblockaccess, blockpos, iblockstate) : 0);
    }

    public void a(World world, BlockPos blockpos, IBlockState iblockstate, Block block) {
        if (this.d(world, blockpos)) {
            this.g(world, blockpos, iblockstate);
        }
        else {
            this.b(world, blockpos, iblockstate, 0);
            world.g(blockpos);
            EnumFacing[] aenumfacing = EnumFacing.values();
            int i0 = aenumfacing.length;

            for (int i1 = 0; i1 < i0; ++i1) {
                EnumFacing enumfacing = aenumfacing[i1];

                world.c(blockpos.a(enumfacing), (Block)this);
            }
        }
    }

    protected void g(World world, BlockPos blockpos, IBlockState iblockstate) {
        if (!this.b((IBlockAccess)world, blockpos, iblockstate)) {
            boolean flag0 = this.e(world, blockpos, iblockstate);

            if ((this.M && !flag0 || !this.M && flag0) && !world.a(blockpos, (Block)this)) {
                byte b0 = -1;

                if (this.i(world, blockpos, iblockstate)) {
                    b0 = -3;
                }
                else if (this.M) {
                    b0 = -2;
                }

                world.a(blockpos, this, this.d(iblockstate), b0);
            }
        }
    }

    public boolean b(IBlockAccess iblockaccess, BlockPos blockpos, IBlockState iblockstate) {
        return false;
    }

    protected boolean e(World world, BlockPos blockpos, IBlockState iblockstate) {
        return this.f(world, blockpos, iblockstate) > 0;
    }

    protected int f(World world, BlockPos blockpos, IBlockState iblockstate) {
        EnumFacing enumfacing = (EnumFacing)iblockstate.b(N);
        BlockPos blockpos1 = blockpos.a(enumfacing);
        int i0 = world.c(blockpos1, enumfacing);

        if (i0 >= 15) {
            return i0;
        }
        else {
            IBlockState iblockstate1 = world.p(blockpos1);

            return Math.max(i0, iblockstate1.c() == Blocks.af ? ((Integer)iblockstate1.b(BlockRedstoneWire.O)).intValue() : 0);
        }
    }

    protected int c(IBlockAccess iblockaccess, BlockPos blockpos, IBlockState iblockstate) {
        EnumFacing enumfacing = (EnumFacing)iblockstate.b(N);
        EnumFacing enumfacing1 = enumfacing.e();
        EnumFacing enumfacing2 = enumfacing.f();

        return Math.max(this.c(iblockaccess, blockpos.a(enumfacing1), enumfacing1), this.c(iblockaccess, blockpos.a(enumfacing2), enumfacing2));
    }

    protected int c(IBlockAccess iblockaccess, BlockPos blockpos, EnumFacing enumfacing) {
        IBlockState iblockstate = iblockaccess.p(blockpos);
        Block block = iblockstate.c();

        return this.c(block) ? (block == Blocks.af ? ((Integer)iblockstate.b(BlockRedstoneWire.O)).intValue() : iblockaccess.a(blockpos, enumfacing)) : 0;
    }

    public boolean g() {
        return true;
    }

    public IBlockState a(World world, BlockPos blockpos, EnumFacing enumfacing, float f0, float f1, float f2, int i0, EntityLivingBase entitylivingbase) {
        return this.P().a(N, entitylivingbase.aO().d());
    }

    public void a(World world, BlockPos blockpos, IBlockState iblockstate, EntityLivingBase entitylivingbase, ItemStack itemstack) {
        if (this.e(world, blockpos, iblockstate)) {
            world.a(blockpos, (Block)this, 1);
        }
    }

    public void c(World world, BlockPos blockpos, IBlockState iblockstate) {
        this.h(world, blockpos, iblockstate);
    }

    protected void h(World world, BlockPos blockpos, IBlockState iblockstate) {
        EnumFacing enumfacing = (EnumFacing)iblockstate.b(N);
        BlockPos blockpos1 = blockpos.a(enumfacing.d());

        world.d(blockpos1, this);
        world.a(blockpos1, (Block)this, enumfacing);
    }

    public void d(World world, BlockPos blockpos, IBlockState iblockstate) {
        if (this.M) {
            EnumFacing[] aenumfacing = EnumFacing.values();
            int i0 = aenumfacing.length;

            for (int i1 = 0; i1 < i0; ++i1) {
                EnumFacing enumfacing = aenumfacing[i1];

                world.c(blockpos.a(enumfacing), (Block)this);
            }
        }

        super.d(world, blockpos, iblockstate);
    }

    public boolean c() {
        return false;
    }

    protected boolean c(Block block) {
        return block.g();
    }

    protected int a(IBlockAccess iblockaccess, BlockPos blockpos, IBlockState iblockstate) {
        return 15;
    }

    public boolean e(Block block) {
        return block == this.e(this.P()).c() || block == this.k(this.P()).c();
    }

    public boolean i(World world, BlockPos blockpos, IBlockState iblockstate) {
        EnumFacing enumfacing = ((EnumFacing)iblockstate.b(N)).d();
        BlockPos blockpos1 = blockpos.a(enumfacing);

        return d(world.p(blockpos1).c()) ? world.p(blockpos1).b(N) != enumfacing : false;
    }

    protected int m(IBlockState iblockstate) {
        return this.d(iblockstate);
    }

    protected abstract int d(IBlockState iblockstate);

    protected abstract IBlockState e(IBlockState iblockstate);

    protected abstract IBlockState k(IBlockState iblockstate);

    public boolean b(Block block) {
        return this.e(block);
    }
}
