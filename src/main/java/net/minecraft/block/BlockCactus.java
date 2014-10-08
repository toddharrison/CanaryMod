package net.minecraft.block;

import net.canarymod.api.CanaryDamageSource;
import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.api.world.position.BlockPosition;
import net.canarymod.hook.entity.DamageHook;
import net.canarymod.hook.world.BlockGrowHook;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.Random;

public class BlockCactus extends Block {

    public static final PropertyInteger a = PropertyInteger.a("age", 0, 15);

    protected BlockCactus() {
        super(Material.A);
        this.j(this.L.b().a(a, Integer.valueOf(0)));
        this.a(true);
        this.a(CreativeTabs.c);
    }

    public void b(World world, BlockPos blockpos, IBlockState iblockstate, Random random) {
        BlockPos blockpos1 = blockpos.a();

        if (world.d(blockpos1)) {
            int i0;

            for (i0 = 1; world.p(blockpos.c(i0)).c() == this; ++i0) {
                ;
            }

            if (i0 < 3) {
                int i1 = ((Integer)iblockstate.b(a)).intValue();

                // CanaryMod: BlockGrow
                BlockPosition cbp = new BlockPosition(blockpos);
                CanaryBlock original = (CanaryBlock)world.getCanaryWorld().getBlockAt(cbp);
                CanaryBlock growth;
                if (i1 == 15) {
                    // New Cactus forming
                    growth = new CanaryBlock(this.P(), new BlockPosition(blockpos1), world.getCanaryWorld());
                    if (!new BlockGrowHook(original, growth).call().isCanceled()) {
                        world.a(blockpos1, this.P());
                        IBlockState iblockstate1 = iblockstate.a(a, 0);

                        world.a(blockpos, iblockstate1, 4);
                        this.a(world, blockpos1, iblockstate1, (Block)this);
                    }
                    //
                }
                else {
                    // Ticking
                    IBlockState grownstate = iblockstate.a(a, i1 + 1);
                    growth = new CanaryBlock(grownstate, new BlockPosition(blockpos), world.getCanaryWorld());
                    if (!new BlockGrowHook(original, growth).call().isCanceled()) {
                        world.a(blockpos, grownstate, 4);
                    }
                    // CanaryMod: end
                }
            }
        }
    }

    public AxisAlignedBB a(World world, BlockPos blockpos, IBlockState iblockstate) {
        float f0 = 0.0625F;

        return new AxisAlignedBB((double)((float)blockpos.n() + f0), (double)blockpos.o(), (double)((float)blockpos.p() + f0), (double)((float)(blockpos.n() + 1) - f0), (double)((float)(blockpos.o() + 1) - f0), (double)((float)(blockpos.p() + 1) - f0));
    }

    public boolean d() {
        return false;
    }

    public boolean c() {
        return false;
    }

    public boolean c(World world, BlockPos blockpos) {
        return super.c(world, blockpos) ? this.d(world, blockpos) : false;
    }

    public void a(World world, BlockPos blockpos, IBlockState iblockstate, Block block) {
        if (!this.d(world, blockpos)) {
            world.b(blockpos, true);
        }
    }

    public boolean d(World world, BlockPos blockpos) {
        Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();

        while (iterator.hasNext()) {
            EnumFacing enumfacing = (EnumFacing)iterator.next();

            if (world.p(blockpos.a(enumfacing)).c().r().a()) {
                return false;
            }
        }

        Block block = world.p(blockpos.b()).c();

        return block == Blocks.aK || block == Blocks.m;
    }

    public void a(World world, BlockPos blockpos, IBlockState iblockstate, Entity entity) {
        // CanaryMod: Damage (Craptus)
        if (!new DamageHook(null, entity.getCanaryEntity(), new CanaryDamageSource(DamageSource.g), 1.0F).call().isCanceled()) {
            entity.a(DamageSource.h, 1.0F);
        }
        //
    }

    public IBlockState a(int i0) {
        return this.P().a(a, Integer.valueOf(i0));
    }

    public int c(IBlockState iblockstate) {
        return ((Integer)iblockstate.b(a)).intValue();
    }

    protected BlockState e() {
        return new BlockState(this, new IProperty[]{ a });
    }
}
