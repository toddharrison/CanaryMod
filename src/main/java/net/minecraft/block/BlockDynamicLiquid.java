package net.minecraft.block;

import net.canarymod.api.world.blocks.BlockType;
import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.hook.world.FlowHook;
import net.canarymod.hook.world.LiquidDestroyHook;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class BlockDynamicLiquid extends BlockLiquid {

    int a;

    protected BlockDynamicLiquid(Material material) {
        super(material);
    }

    private void f(World world, BlockPos blockpos, IBlockState iblockstate) {
        world.a(blockpos, b(this.J).P().a(b, iblockstate.b(b)), 2);
    }

    public void b(World world, BlockPos blockpos, IBlockState iblockstate, Random random) {

        // CanaryMod: Flow from
        CanaryBlock from = new CanaryBlock(iblockstate, blockpos, world);
        //

        int i0 = ((Integer)iblockstate.b(b)).intValue();
        byte b0 = 1;

        if (this.J == Material.i && !world.t.n()) {
            b0 = 2;
        }

        int i1 = this.a(world);
        int i2;

        if (i0 > 0) {
            int i3 = -100;

            this.a = 0;

            EnumFacing enumfacing;

            for (Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator(); iterator.hasNext(); i3 = this.a(world, blockpos.a(enumfacing), i3)) {
                enumfacing = (EnumFacing)iterator.next();
            }

            int i4 = i3 + b0;

            if (i4 >= 8 || i3 < 0) {
                i4 = -1;
            }

            if (this.e((IBlockAccess)world, blockpos.a()) >= 0) {
                i2 = this.e((IBlockAccess)world, blockpos.a());
                if (i2 >= 8) {
                    i4 = i2;
                }
                else {
                    i4 = i2 + 8;
                }
            }

            if (this.a >= 2 && this.J == Material.h) {
                IBlockState iblockstate1 = world.p(blockpos.b());

                if (iblockstate1.c().r().a()) {
                    i4 = 0;
                }
                else if (iblockstate1.c().r() == this.J && ((Integer)iblockstate1.b(b)).intValue() == 0) {
                    i4 = 0;
                }
            }

            if (this.J == Material.i && i0 < 8 && i4 < 8 && i4 > i0 && random.nextInt(4) != 0) {
                i1 *= 4;
            }

            if (i4 == i0) {
                this.f(world, blockpos, iblockstate);
            }
            else {
                i0 = i4;
                if (i4 < 0) {
                    world.g(blockpos);
                }
                else {
                    iblockstate = iblockstate.a(b, Integer.valueOf(i4));
                    world.a(blockpos, iblockstate, 2);
                    world.a(blockpos, (Block)this, i1);
                    world.c(blockpos, (Block)this);
                }
            }
        }
        else {
            this.f(world, blockpos, iblockstate);
        }

        IBlockState iblockstate2 = world.p(blockpos.b());

        if (this.h(world, blockpos.b(), iblockstate2)) {
            if (this.J == Material.i && world.p(blockpos.b()).c().r() == Material.h) {
                world.a(blockpos.b(), Blocks.b.P());
                this.d(world, blockpos.b());
                return;
            }

            // CanaryMod: Flow (down)
            if (!new FlowHook(from, new CanaryBlock(iblockstate, blockpos.b(), world)).call().isCanceled()) {
                if (i0 >= 8) {
                    this.a(world, blockpos.b(), iblockstate2, i0);
                }
                else {
                    this.a(world, blockpos.b(), iblockstate2, i0 + 8);
                }
                //
            }
        }
        else if (i0 >= 0 && (i0 == 0 || this.g(world, blockpos.b(), iblockstate2))) {
            Set set = this.e(world, blockpos);

            i2 = i0 + b0;
            if (i0 >= 8) {
                i2 = 1;
            }

            if (i2 >= 8) {
                return;
            }

            Iterator iterator1 = set.iterator();

            while (iterator1.hasNext()) {
                EnumFacing enumfacing1 = (EnumFacing)iterator1.next();

                // CanaryMod: Flow
                if (!new FlowHook(from, new CanaryBlock(iblockstate, blockpos.a(enumfacing1), world)).call().isCanceled()) {
                    this.a(world, blockpos.a(enumfacing1), world.p(blockpos.a(enumfacing1)), i2);
                }
                //
            }
        }
    }

    private void a(World world, BlockPos blockpos, IBlockState iblockstate, int i0) {
        if (this.h(world, blockpos, iblockstate)) {
            if (iblockstate.c() != Blocks.a) {
                if (this.J == Material.i) {
                    this.d(world, blockpos);
                }
                else {
                    iblockstate.c().b(world, blockpos, iblockstate, 0);
                }
            }

            world.a(blockpos, this.P().a(b, Integer.valueOf(i0)), 3);
        }
    }

    private int a(World world, BlockPos blockpos, int i0, EnumFacing enumfacing) {
        int i1 = 1000;
        Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();

        while (iterator.hasNext()) {
            EnumFacing enumfacing1 = (EnumFacing)iterator.next();

            if (enumfacing1 != enumfacing) {
                BlockPos blockpos1 = blockpos.a(enumfacing1);
                IBlockState iblockstate = world.p(blockpos1);

                if (!this.g(world, blockpos1, iblockstate) && (iblockstate.c().r() != this.J || ((Integer)iblockstate.b(b)).intValue() > 0)) {
                    if (!this.g(world, blockpos1.b(), iblockstate)) {
                        return i0;
                    }

                    if (i0 < 4) {
                        int i2 = this.a(world, blockpos1, i0 + 1, enumfacing1.d());

                        if (i2 < i1) {
                            i1 = i2;
                        }
                    }
                }
            }
        }

        return i1;
    }

    private Set e(World world, BlockPos blockpos) {
        int i0 = 1000;
        EnumSet enumset = EnumSet.noneOf(EnumFacing.class);
        Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();

        while (iterator.hasNext()) {
            EnumFacing enumfacing = (EnumFacing)iterator.next();
            BlockPos blockpos1 = blockpos.a(enumfacing);
            IBlockState iblockstate = world.p(blockpos1);

            if (!this.g(world, blockpos1, iblockstate) && (iblockstate.c().r() != this.J || ((Integer)iblockstate.b(b)).intValue() > 0)) {
                int i1;

                if (this.g(world, blockpos1.b(), world.p(blockpos1.b()))) {
                    i1 = this.a(world, blockpos1, 1, enumfacing.d());
                }
                else {
                    i1 = 0;
                }

                if (i1 < i0) {
                    enumset.clear();
                }

                if (i1 <= i0) {
                    enumset.add(enumfacing);
                    i0 = i1;
                }
            }
        }

        return enumset;
    }

    private boolean g(World world, BlockPos blockpos, IBlockState iblockstate) {
        Block block = world.p(blockpos).c();

        return !(block instanceof BlockDoor) && block != Blocks.an && block != Blocks.au && block != Blocks.aM ? (block.J == Material.E ? true : block.J.c()) : true;
    }

    protected int a(World world, BlockPos blockpos, int i0) {
        int i1 = this.e((IBlockAccess)world, blockpos);

        if (i1 < 0) {
            return i0;
        }
        else {
            if (i1 == 0) {
                ++this.a;
            }

            if (i1 >= 8) {
                i1 = 0;
            }

            return i0 >= 0 && i1 >= i0 ? i0 : i1;
        }
    }

    private boolean h(World world, BlockPos blockpos, IBlockState iblockstate) {
        Material material = iblockstate.c().r();
        boolean ret = material != this.J && material != Material.i && !this.g(world, blockpos, iblockstate);

        // CanaryMod: LiquidDestroy
        CanaryBlock dest = new CanaryBlock(iblockstate, blockpos, world);
        BlockType liquid = this.J == Material.i ? BlockType.LavaFlowing : BlockType.WaterFlowing;
        LiquidDestroyHook hook = (LiquidDestroyHook)new LiquidDestroyHook(liquid, dest).call();

        return hook.isForceDestroy() || (!hook.isCanceled() && ret);
        //
    }

    public void c(World world, BlockPos blockpos, IBlockState iblockstate) {
        if (!this.e(world, blockpos, iblockstate)) {
            world.a(blockpos, (Block)this, this.a(world));
        }
    }
}
