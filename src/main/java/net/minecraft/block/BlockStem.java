package net.minecraft.block;

import com.google.common.base.Predicate;
import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.hook.world.BlockGrowHook;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.Random;

public class BlockStem extends BlockBush implements IGrowable {

    public static final PropertyInteger a = PropertyInteger.a("age", 0, 7);
    public static final PropertyDirection b = PropertyDirection.a("facing", new Predicate() {

                                                                      public boolean a(EnumFacing p_a_1_) {
                                                                          return p_a_1_ != EnumFacing.DOWN;
                                                                      }

                                                                      public boolean apply(Object p_apply_1_) {
                                                                          return this.a((EnumFacing)p_apply_1_);
                                                                      }
                                                                  }
                                                                 );
    private final Block M;

    protected BlockStem(Block block) {
        this.j(this.L.b().a(a, Integer.valueOf(0)).a(b, EnumFacing.UP));
        this.M = block;
        this.a(true);
        float f0 = 0.125F;

        this.a(0.5F - f0, 0.0F, 0.5F - f0, 0.5F + f0, 0.25F, 0.5F + f0);
        this.a((CreativeTabs)null);
    }

    public IBlockState a(IBlockState iblockstate, IBlockAccess iblockaccess, BlockPos blockpos) {
        iblockstate = iblockstate.a(b, EnumFacing.UP);
        Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();

        while (iterator.hasNext()) {
            EnumFacing enumfacing = (EnumFacing)iterator.next();

            if (iblockaccess.p(blockpos.a(enumfacing)).c() == this.M) {
                iblockstate = iblockstate.a(b, enumfacing);
                break;
            }
        }

        return iblockstate;
    }

    protected boolean c(Block block) {
        return block == Blocks.ak;
    }

    public void b(World world, BlockPos blockpos, IBlockState iblockstate, Random random) {
        super.b(world, blockpos, iblockstate, random);
        if (world.l(blockpos.a()) >= 9) {
            float f0 = BlockCrops.a(this, world, blockpos);

            if (random.nextInt((int)(25.0F / f0) + 1) == 0) {
                int i0 = ((Integer)iblockstate.b(a)).intValue();

                // CanaryMod: Grab the original stuff
                CanaryBlock original = new CanaryBlock(iblockstate, blockpos, world);
                CanaryBlock growth;
                //
                if (i0 < 7) {
                    iblockstate = iblockstate.a(a, Integer.valueOf(i0 + 1));
                    // Growth is original with new data
                    if (!new BlockGrowHook(original, new CanaryBlock(iblockstate, blockpos, world)).call().isCanceled()) {
                        world.a(blockpos, iblockstate, 2);
                    }
                    //
                }
                else {
                    Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();

                    while (iterator.hasNext()) {
                        EnumFacing enumfacing = (EnumFacing)iterator.next();

                        if (world.p(blockpos.a(enumfacing)).c() == this.M) {
                            return;
                        }
                    }

                    blockpos = blockpos.a(EnumFacing.Plane.HORIZONTAL.a(random));
                    Block block = world.p(blockpos.b()).c();

                    if (world.p(blockpos).c().J == Material.a && (block == Blocks.ak || block == Blocks.d || block == Blocks.c)) {
                        // A Melon/Pumpkin has spawned
                        if (!new BlockGrowHook(original, new CanaryBlock(this.M.P(), blockpos, world)).call().isCanceled()) {
                            world.a(blockpos, this.M.P());
                        }
                        //
                    }
                }
            }
        }
    }

    public void g(World world, BlockPos blockpos, IBlockState iblockstate) {
        int i0 = ((Integer)iblockstate.b(a)).intValue() + MathHelper.a(world.s, 2, 5);

        world.a(blockpos, iblockstate.a(a, Integer.valueOf(Math.min(7, i0))), 2);
    }

    public void h() {
        float f0 = 0.125F;

        this.a(0.5F - f0, 0.0F, 0.5F - f0, 0.5F + f0, 0.25F, 0.5F + f0);
    }

    public void a(IBlockAccess iblockaccess, BlockPos blockpos) {
        this.F = (double)((float)(((Integer)iblockaccess.p(blockpos).b(a)).intValue() * 2 + 2) / 16.0F);
        float f0 = 0.125F;

        this.a(0.5F - f0, 0.0F, 0.5F - f0, 0.5F + f0, (float)this.F, 0.5F + f0);
    }

    public void a(World world, BlockPos blockpos, IBlockState iblockstate, float f0, int i0) {
        super.a(world, blockpos, iblockstate, f0, i0);
        if (!world.D) {
            Item item = this.j();

            if (item != null) {
                int i1 = ((Integer)iblockstate.b(a)).intValue();

                for (int i2 = 0; i2 < 3; ++i2) {
                    if (world.s.nextInt(15) <= i1) {
                        a(world, blockpos, new ItemStack(item));
                    }
                }
            }
        }
    }

    protected Item j() {
        return this.M == Blocks.aU ? Items.bg : (this.M == Blocks.bk ? Items.bh : null);
    }

    public Item a(IBlockState iblockstate, Random random, int i0) {
        return null;
    }

    public boolean a(World world, BlockPos blockpos, IBlockState iblockstate, boolean flag0) {
        return ((Integer)iblockstate.b(a)).intValue() != 7;
    }

    public boolean a(World world, Random random, BlockPos blockpos, IBlockState iblockstate) {
        return true;
    }

    public void b(World world, Random random, BlockPos blockpos, IBlockState iblockstate) {
        this.g(world, blockpos, iblockstate);
    }

    public IBlockState a(int i0) {
        return this.P().a(a, Integer.valueOf(i0));
    }

    public int c(IBlockState iblockstate) {
        return ((Integer)iblockstate.b(a)).intValue();
    }

    protected BlockState e() {
        return new BlockState(this, new IProperty[]{ a, b });
    }
}
