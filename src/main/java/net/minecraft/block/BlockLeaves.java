package net.minecraft.block;

import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.hook.world.LeafDecayHook;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.Random;

public abstract class BlockLeaves extends BlockLeavesBase {

    public static final PropertyBool a = PropertyBool.a("decayable");
    public static final PropertyBool b = PropertyBool.a("check_decay");
    int[] M;
   
    public BlockLeaves() {
        super(Material.j, false);
        this.a(true);
        this.a(CreativeTabs.c);
        this.c(0.2F);
        this.e(1);
        this.a(h);
    }

    public void b(World world, BlockPos blockpos, IBlockState iblockstate) {
        byte b0 = 1;
        int i0 = b0 + 1;
        int i1 = blockpos.n();
        int i2 = blockpos.o();
        int i3 = blockpos.p();

        if (world.a(new BlockPos(i1 - i0, i2 - i0, i3 - i0), new BlockPos(i1 + i0, i2 + i0, i3 + i0))) {
            for (int i4 = -b0; i4 <= b0; ++i4) {
                for (int i5 = -b0; i5 <= b0; ++i5) {
                    for (int i6 = -b0; i6 <= b0; ++i6) {
                        BlockPos blockpos1 = blockpos.a(i4, i5, i6);
                        IBlockState iblockstate1 = world.p(blockpos1);

                        if (iblockstate1.c().r() == Material.j && !((Boolean) iblockstate1.b(b)).booleanValue()) {
                            world.a(blockpos1, iblockstate1.a(b, Boolean.valueOf(true)), 4);
                        }
                    }
                }
            }
        }

    }

    public void b(World world, BlockPos blockpos, IBlockState iblockstate, Random random) {
        if (!world.D) {
            if (((Boolean) iblockstate.b(b)).booleanValue() && ((Boolean) iblockstate.b(a)).booleanValue()) {
                byte b0 = 4;
                int i0 = b0 + 1;
                int i1 = blockpos.n();
                int i2 = blockpos.o();
                int i3 = blockpos.p();
                byte b1 = 32;
                int i4 = b1 * b1;
                int i5 = b1 / 2;

                if (this.M == null) {
                    this.M = new int[b1 * b1 * b1];
                }

                int i6;

                if (world.a(new BlockPos(i1 - i0, i2 - i0, i3 - i0), new BlockPos(i1 + i0, i2 + i0, i3 + i0))) {
                    int i7;
                    int i8;
                    int i9;

                    for (i6 = -b0; i6 <= b0; ++i6) {
                        for (i7 = -b0; i7 <= b0; ++i7) {
                            for (i8 = -b0; i8 <= b0; ++i8) {
                                Block block = world.p(new BlockPos(i1 + i6, i2 + i7, i3 + i8)).c();

                                if (block != Blocks.r && block != Blocks.s) {
                                    if (block.r() == Material.j) {
                                        this.M[(i6 + i5) * i4 + (i7 + i5) * b1 + i8 + i5] = -2;
                                    } else {
                                        this.M[(i6 + i5) * i4 + (i7 + i5) * b1 + i8 + i5] = -1;
                                    }
                                } else {
                                    this.M[(i6 + i5) * i4 + (i7 + i5) * b1 + i8 + i5] = 0;
                                }
                            }
                        }
                    }

                    for (i6 = 1; i6 <= 4; ++i6) {
                        for (i7 = -b0; i7 <= b0; ++i7) {
                            for (i8 = -b0; i8 <= b0; ++i8) {
                                for (int i9 = -b0; i9 <= b0; ++i9) {
                                    if (this.M[(i7 + i5) * i4 + (i8 + i5) * b1 + i9 + i5] == i6 - 1) {
                                        if (this.M[(i7 + i5 - 1) * i4 + (i8 + i5) * b1 + i9 + i5] == -2) {
                                            this.M[(i7 + i5 - 1) * i4 + (i8 + i5) * b1 + i9 + i5] = i6;
                                        }

                                        if (this.M[(i7 + i5 + 1) * i4 + (i8 + i5) * b1 + i9 + i5] == -2) {
                                            this.M[(i7 + i5 + 1) * i4 + (i8 + i5) * b1 + i9 + i5] = i6;
                                        }

                                        if (this.M[(i7 + i5) * i4 + (i8 + i5 - 1) * b1 + i9 + i5] == -2) {
                                            this.M[(i7 + i5) * i4 + (i8 + i5 - 1) * b1 + i9 + i5] = i6;
                                        }

                                        if (this.M[(i7 + i5) * i4 + (i8 + i5 + 1) * b1 + i9 + i5] == -2) {
                                            this.M[(i7 + i5) * i4 + (i8 + i5 + 1) * b1 + i9 + i5] = i6;
                                        }

                                        if (this.M[(i7 + i5) * i4 + (i8 + i5) * b1 + (i9 + i5 - 1)] == -2) {
                                            this.M[(i7 + i5) * i4 + (i8 + i5) * b1 + (i9 + i5 - 1)] = i6;
                                        }

                                        if (this.M[(i7 + i5) * i4 + (i8 + i5) * b1 + i9 + i5 + 1] == -2) {
                                            this.M[(i7 + i5) * i4 + (i8 + i5) * b1 + i9 + i5 + 1] = i6;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                i6 = this.M[i5 * i4 + i5 * b1 + i5];
                if (i6 >= 0) {
                    world.a(blockpos, iblockstate.a(b, Boolean.valueOf(false)), 4);
                } else {
                    this.d(world, blockpos);
                }
            }

        }
    }

    private void d(World world, BlockPos blockpos) {
        // CanaryMod: LeafDecay
        CanaryBlock leaves = (CanaryBlock) world.getCanaryWorld().getBlockAt(i0, i1, i2);
        LeafDecayHook hook = (LeafDecayHook) new LeafDecayHook(leaves).call();
        if (!hook.isCanceled()) {
            this.b(world, blockpos, world.p(blockpos), 0);
        world.g(blockpos);
        }
        //
    }

    public int a(Random random) {
        return random.nextInt(20) == 0 ? 1 : 0;
    }

    public Item a(IBlockState iblockstate, Random random, int i0) {
        return Item.a(Blocks.g);
    }

    public void a(World world, BlockPos blockpos, IBlockState iblockstate, float f0, int i0) {
        if (!world.D) {
            int i1 = this.d(iblockstate);

            if (i0 > 0) {
                i1 -= 2 << i0;
                if (i1 < 10) {
                    i1 = 10;
                }
            }

            if (world.s.nextInt(i1) == 0) {
                Item item = this.a(iblockstate, world.s, i0);

                a(world, blockpos, new ItemStack(item, 1, this.a(iblockstate)));
            }

            i1 = 200;
            if (i0 > 0) {
                i1 -= 10 << i0;
                if (i1 < 40) {
                    i1 = 40;
                }
            }

            this.a(world, blockpos, iblockstate, i1);
        }

    }

    protected void a(World world, BlockPos blockpos, IBlockState iblockstate, int i0) {}

    protected int d(IBlockState iblockstate) {
        return 20;
    }

    public boolean c() {
        return !this.Q;
    }

    public boolean u() {
        return false;
    }

    public abstract BlockPlanks.EnumType b(int i0);

}
