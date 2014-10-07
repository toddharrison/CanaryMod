package net.minecraft.block;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.canarymod.api.world.blocks.BlockType;
import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.api.world.position.BlockPosition;
import net.canarymod.hook.world.RedstoneChangeHook;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class BlockRedstoneTorch extends BlockTorch {

    private static Map b = Maps.newHashMap();
    private boolean M; // CanaryMod: remove final

    private boolean a(World world, BlockPos blockpos, boolean flag0) {
        if (!b.containsKey(world)) {
            b.put(world, Lists.newArrayList());
        }

        List list = (List) b.get(world);

        if (flag0) {
            list.add(new BlockRedstoneTorch.Toggle(blockpos, world.K()));
        }

        int i0 = 0;

        for (int i1 = 0; i1 < list.size(); ++i1) {
            BlockRedstoneTorch.Toggle blockredstonetorch_toggle = (BlockRedstoneTorch.Toggle) list.get(i1);

            if (blockredstonetorch_toggle.a.equals(blockpos)) {
                ++i0;
                if (i0 >= 8) {
                    return true;
                }
            }
        }

        return false;
    }

    protected BlockRedstoneTorch(boolean flag0) {
        this.M = flag0;
        this.a(true);
        this.a((CreativeTabs) null);
    }

    public int a(World world) {
        return 2;
    }

    public void c(World world, BlockPos blockpos, IBlockState iblockstate) {
        if (this.M) {
            // CanaryMod: RedstoneChange; Torch put in
            RedstoneChangeHook hook = (RedstoneChangeHook) new RedstoneChangeHook(world.getCanaryWorld().getBlockAt(new BlockPosition(blockpos)), 0, 15).call();
            if (hook.isCanceled()) {
                this.M = false;
                return;
            }
            //
            EnumFacing[] aenumfacing = EnumFacing.values();
            int i0 = aenumfacing.length;

            for (int i1 = 0; i1 < i0; ++i1) {
                EnumFacing enumfacing = aenumfacing[i1];

                world.c(blockpos.a(enumfacing), (Block) this);
            }
        }

    }

    public void b(World world, BlockPos blockpos, IBlockState iblockstate) {
        if (this.M) {
            // CanaryMod: RedstoneChange; Torch broke
            new RedstoneChangeHook(new CanaryBlock(BlockType.RedstoneLampOn, (short) 2, new BlockPosition(blockpos), world.getCanaryWorld()), 15, 0).call();
            //
            EnumFacing[] aenumfacing = EnumFacing.values();
            int i0 = aenumfacing.length;

            for (int i1 = 0; i1 < i0; ++i1) {
                EnumFacing enumfacing = aenumfacing[i1];

                world.c(blockpos.a(enumfacing), (Block) this);
            }
        }
    }

    public int a(IBlockAccess iblockaccess, BlockPos blockpos, IBlockState iblockstate, EnumFacing enumfacing) {
        return this.M && iblockstate.b(a) != enumfacing ? 15 : 0;
    }

    private boolean g(World world, BlockPos blockpos, IBlockState iblockstate) {
        EnumFacing enumfacing = ((EnumFacing) iblockstate.b(a)).d();

        return world.b(blockpos.a(enumfacing), enumfacing);
    }

    public void a(World world, BlockPos blockpos, IBlockState iblockstate, Random random) {
    }

    public void b(World world, BlockPos blockpos, IBlockState iblockstate, Random random) {
        boolean flag0 = this.g(world, blockpos, iblockstate);
        List list = (List) b.get(world);

        while (list != null && !list.isEmpty() && world.K() - ((BlockRedstoneTorch.Toggle) list.get(0)).b > 60L) {
            list.remove(0);
        }

        if (this.M) {
            if (flag0) {
                // CanaryMod: RedstoneChange; Torch off
                RedstoneChangeHook hook = (RedstoneChangeHook) new RedstoneChangeHook(world.getCanaryWorld().getBlockAt(new BlockPosition(blockpos)), 15, 0).call();
                if (hook.isCanceled()) {
                    return;
                }
                //
                world.a(blockpos, Blocks.aE.P().a(a, iblockstate.b(a)), 3);
                if (this.a(world, blockpos, true)) {
                    world.a((double) ((float) blockpos.n() + 0.5F), (double) ((float) blockpos.o() + 0.5F), (double) ((float) blockpos.p() + 0.5F), "random.fizz", 0.5F, 2.6F + (world.s.nextFloat() - world.s.nextFloat()) * 0.8F);

                    for (int i0 = 0; i0 < 5; ++i0) {
                        double d0 = (double) blockpos.n() + random.nextDouble() * 0.6D + 0.2D;
                        double d1 = (double) blockpos.o() + random.nextDouble() * 0.6D + 0.2D;
                        double d2 = (double) blockpos.p() + random.nextDouble() * 0.6D + 0.2D;

                        world.a(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
                    }

                    world.a(blockpos, world.p(blockpos).c(), 160);
                }
            }
        }
        else if (!flag0 && !this.a(world, blockpos, false)) {
            // CanaryMod: RedstoneChange; Torch on
            RedstoneChangeHook hook = (RedstoneChangeHook) new RedstoneChangeHook(world.getCanaryWorld().getBlockAt(new BlockPosition(blockpos)), 15, 0).call();
            if (hook.isCanceled()) {
                return;
            }
            //
            world.a(blockpos, Blocks.aF.P().a(a, iblockstate.b(a)), 3);
        }

    }

    public void a(World world, BlockPos blockpos, IBlockState iblockstate, Block block) {
        if (!this.e(world, blockpos, iblockstate)) {
            if (this.M == this.g(world, blockpos, iblockstate)) {
                world.a(blockpos, (Block) this, this.a(world));
            }

        }
    }

    public int b(IBlockAccess iblockaccess, BlockPos blockpos, IBlockState iblockstate, EnumFacing enumfacing) {
        return enumfacing == EnumFacing.DOWN ? this.a(iblockaccess, blockpos, iblockstate, enumfacing) : 0;
    }

    public Item a(IBlockState iblockstate, Random random, int i0) {
        return Item.a(Blocks.aF);
    }

    public boolean g() {
        return true;
    }

    public boolean b(Block block) {
        return block == Blocks.aE || block == Blocks.aF;
    }

    static class Toggle {

        BlockPos a;
        long b;

        public Toggle(BlockPos p_i45688_1_, long p_i45688_2_) {
            this.a = p_i45688_1_;
            this.b = p_i45688_2_;
        }
    }
}
