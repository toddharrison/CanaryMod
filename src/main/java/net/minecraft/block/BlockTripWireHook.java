package net.minecraft.block;


import net.canarymod.hook.world.BlockPhysicsHook;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Direction;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;


public class BlockTripWireHook extends Block {

    public BlockTripWireHook() {
        super(Material.q);
        this.a(CreativeTabs.d);
        this.a(true);
    }

    public AxisAlignedBB a(World world, int i0, int i1, int i2) {
        return null;
    }

    public boolean c() {
        return false;
    }

    public boolean d() {
        return false;
    }

    public int b() {
        return 29;
    }

    public int a(World world) {
        return 10;
    }

    public boolean d(World world, int i0, int i1, int i2, int i3) {
        return i3 == 2 && world.a(i0, i1, i2 + 1).r() ? true : (i3 == 3 && world.a(i0, i1, i2 - 1).r() ? true : (i3 == 4 && world.a(i0 + 1, i1, i2).r() ? true : i3 == 5 && world.a(i0 - 1, i1, i2).r()));
    }

    public boolean c(World world, int i0, int i1, int i2) {
        return world.a(i0 - 1, i1, i2).r() ? true : (world.a(i0 + 1, i1, i2).r() ? true : (world.a(i0, i1, i2 - 1).r() ? true : world.a(i0, i1, i2 + 1).r()));
    }

    public int a(World world, int i0, int i1, int i2, int i3, float f0, float f1, float f2, int i4) {
        byte b0 = 0;

        if (i3 == 2 && world.c(i0, i1, i2 + 1, true)) {
            b0 = 2;
        }

        if (i3 == 3 && world.c(i0, i1, i2 - 1, true)) {
            b0 = 0;
        }

        if (i3 == 4 && world.c(i0 + 1, i1, i2, true)) {
            b0 = 1;
        }

        if (i3 == 5 && world.c(i0 - 1, i1, i2, true)) {
            b0 = 3;
        }

        return b0;
    }

    public void e(World world, int i0, int i1, int i2, int i3) {
        this.a(world, i0, i1, i2, false, i3, false, -1, 0);
    }

    public void a(World world, int i0, int i1, int i2, Block block) {
        if (block != this) {
            if (this.e(world, i0, i1, i2)) {
                int i3 = world.e(i0, i1, i2);
                int i4 = i3 & 3;
                boolean flag0 = false;

                if (!world.a(i0 - 1, i1, i2).r() && i4 == 3) {
                    flag0 = true;
                }

                if (!world.a(i0 + 1, i1, i2).r() && i4 == 1) {
                    flag0 = true;
                }

                if (!world.a(i0, i1, i2 - 1).r() && i4 == 0) {
                    flag0 = true;
                }

                if (!world.a(i0, i1, i2 + 1).r() && i4 == 2) {
                    flag0 = true;
                }

                if (flag0) {
                    this.b(world, i0, i1, i2, i3, 0);
                    world.f(i0, i1, i2);
                }
            }

        }
    }

    public void a(World world, int i0, int i1, int i2, boolean flag0, int i3, boolean flag1, int i4, int i5) {
        // CanaryMod: Block Physics
        BlockPhysicsHook blockPhysics = (BlockPhysicsHook) new BlockPhysicsHook(world.getCanaryWorld().getBlockAt(i0, i1, i2), false).call();
        if (blockPhysics.isCanceled()) {
            return;
        }
        //

        int i6 = i3 & 3;
        boolean flag2 = (i3 & 4) == 4;
        boolean flag3 = (i3 & 8) == 8;
        boolean flag4 = !flag0;
        boolean flag5 = false;
        boolean flag6 = !World.a((IBlockAccess) world, i0, i1 - 1, i2);
        int i7 = Direction.a[i6];
        int i8 = Direction.b[i6];
        int i9 = 0;
        int[] aint = new int[42];

        int i10;
        int i11;
        int i12;
        int i13;

        for (i10 = 1; i10 < 42; ++i10) {
            i11 = i0 + i7 * i10;
            i12 = i2 + i8 * i10;
            Block block = world.a(i11, i1, i12);

            if (block == Blocks.bC) {
                i13 = world.e(i11, i1, i12);
                if ((i13 & 3) == Direction.f[i6]) {
                    i9 = i10;
                }
                break;
            }

            if (block != Blocks.bD && i10 != i4) {
                aint[i10] = -1;
                flag4 = false;
            }
            else {
                i13 = i10 == i4 ? i5 : world.e(i11, i1, i12);
                boolean flag7 = (i13 & 8) != 8;
                boolean flag8 = (i13 & 1) == 1;
                boolean flag9 = (i13 & 2) == 2;

                flag4 &= flag9 == flag6;
                flag5 |= flag7 && flag8;
                aint[i10] = i13;
                if (i10 == i4) {
                    world.a(i0, i1, i2, this, this.a(world));
                    flag4 &= flag7;
                }
            }
        }

        flag4 &= i9 > 1;
        flag5 &= flag4;
        i10 = (flag4 ? 4 : 0) | (flag5 ? 8 : 0);
        i3 = i6 | i10;
        int i14;

        if (i9 > 0) {
            i11 = i0 + i7 * i9;
            i12 = i2 + i8 * i9;
            i14 = Direction.f[i6];
            world.a(i11, i1, i12, i14 | i10, 3);
            this.a(world, i11, i1, i12, i14);
            this.a(world, i11, i1, i12, flag4, flag5, flag2, flag3);
        }

        this.a(world, i0, i1, i2, flag4, flag5, flag2, flag3);
        if (!flag0) {
            world.a(i0, i1, i2, i3, 3);
            if (flag1) {
                this.a(world, i0, i1, i2, i6);
            }
        }

        if (flag2 != flag4) {
            for (i11 = 1; i11 < i9; ++i11) {
                i12 = i0 + i7 * i11;
                i14 = i2 + i8 * i11;
                i13 = aint[i11];
                if (i13 >= 0) {
                    if (flag4) {
                        i13 |= 4;
                    }
                    else {
                        i13 &= -5;
                    }

                    world.a(i12, i1, i14, i13, 3);
                }
            }
        }

    }

    public void a(World world, int i0, int i1, int i2, Random random) {
        this.a(world, i0, i1, i2, false, world.e(i0, i1, i2), true, -1, 0);
    }

    private void a(World world, int i0, int i1, int i2, boolean flag0, boolean flag1, boolean flag2, boolean flag3) {
        if (flag1 && !flag3) {
            world.a((double) i0 + 0.5D, (double) i1 + 0.1D, (double) i2 + 0.5D, "random.click", 0.4F, 0.6F);
        }
        else if (!flag1 && flag3) {
            world.a((double) i0 + 0.5D, (double) i1 + 0.1D, (double) i2 + 0.5D, "random.click", 0.4F, 0.5F);
        }
        else if (flag0 && !flag2) {
            world.a((double) i0 + 0.5D, (double) i1 + 0.1D, (double) i2 + 0.5D, "random.click", 0.4F, 0.7F);
        }
        else if (!flag0 && flag2) {
            world.a((double) i0 + 0.5D, (double) i1 + 0.1D, (double) i2 + 0.5D, "random.bowhit", 0.4F, 1.2F / (world.s.nextFloat() * 0.2F + 0.9F));
        }

    }

    private void a(World world, int i0, int i1, int i2, int i3) {
        world.d(i0, i1, i2, this);
        if (i3 == 3) {
            world.d(i0 - 1, i1, i2, this);
        }
        else if (i3 == 1) {
            world.d(i0 + 1, i1, i2, this);
        }
        else if (i3 == 0) {
            world.d(i0, i1, i2 - 1, this);
        }
        else if (i3 == 2) {
            world.d(i0, i1, i2 + 1, this);
        }

    }

    private boolean e(World world, int i0, int i1, int i2) {
        if (!this.c(world, i0, i1, i2)) {
            this.b(world, i0, i1, i2, world.e(i0, i1, i2), 0);
            world.f(i0, i1, i2);
            return false;
        }
        else {
            return true;
        }
    }

    public void a(IBlockAccess iblockaccess, int i0, int i1, int i2) {
        int i3 = iblockaccess.e(i0, i1, i2) & 3;
        float f0 = 0.1875F;

        if (i3 == 3) {
            this.a(0.0F, 0.2F, 0.5F - f0, f0 * 2.0F, 0.8F, 0.5F + f0);
        }
        else if (i3 == 1) {
            this.a(1.0F - f0 * 2.0F, 0.2F, 0.5F - f0, 1.0F, 0.8F, 0.5F + f0);
        }
        else if (i3 == 0) {
            this.a(0.5F - f0, 0.2F, 0.0F, 0.5F + f0, 0.8F, f0 * 2.0F);
        }
        else if (i3 == 2) {
            this.a(0.5F - f0, 0.2F, 1.0F - f0 * 2.0F, 0.5F + f0, 0.8F, 1.0F);
        }

    }

    public void a(World world, int i0, int i1, int i2, Block block, int i3) {
        boolean flag0 = (i3 & 4) == 4;
        boolean flag1 = (i3 & 8) == 8;

        if (flag0 || flag1) {
            this.a(world, i0, i1, i2, true, i3, false, -1, 0);
        }

        if (flag1) {
            world.d(i0, i1, i2, this);
            int i4 = i3 & 3;

            if (i4 == 3) {
                world.d(i0 - 1, i1, i2, this);
            }
            else if (i4 == 1) {
                world.d(i0 + 1, i1, i2, this);
            }
            else if (i4 == 0) {
                world.d(i0, i1, i2 - 1, this);
            }
            else if (i4 == 2) {
                world.d(i0, i1, i2 + 1, this);
            }
        }

        super.a(world, i0, i1, i2, block, i3);
    }

    public int b(IBlockAccess iblockaccess, int i0, int i1, int i2, int i3) {
        return (iblockaccess.e(i0, i1, i2) & 8) == 8 ? 15 : 0;
    }

    public int c(IBlockAccess iblockaccess, int i0, int i1, int i2, int i3) {
        int i4 = iblockaccess.e(i0, i1, i2);

        if ((i4 & 8) != 8) {
            return 0;
        }
        else {
            int i5 = i4 & 3;

            return i5 == 2 && i3 == 2 ? 15 : (i5 == 0 && i3 == 3 ? 15 : (i5 == 1 && i3 == 4 ? 15 : (i5 == 3 && i3 == 5 ? 15 : 0)));
        }
    }

    public boolean f() {
        return true;
    }
}
