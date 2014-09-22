package net.minecraft.block;


import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.hook.world.BlockGrowHook;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Direction;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;


public class BlockVine extends Block {

    public BlockVine() {
        super(Material.l);
        this.a(true);
        this.a(CreativeTabs.c);
    }

    public void g() {
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    public int b() {
        return 20;
    }

    public boolean c() {
        return false;
    }

    public boolean d() {
        return false;
    }

    public void a(IBlockAccess iblockaccess, int i0, int i1, int i2) {
        float f0 = 0.0625F;
        int i3 = iblockaccess.e(i0, i1, i2);
        float f1 = 1.0F;
        float f2 = 1.0F;
        float f3 = 1.0F;
        float f4 = 0.0F;
        float f5 = 0.0F;
        float f6 = 0.0F;
        boolean flag0 = i3 > 0;

        if ((i3 & 2) != 0) {
            f4 = Math.max(f4, 0.0625F);
            f1 = 0.0F;
            f2 = 0.0F;
            f5 = 1.0F;
            f3 = 0.0F;
            f6 = 1.0F;
            flag0 = true;
        }

        if ((i3 & 8) != 0) {
            f1 = Math.min(f1, 0.9375F);
            f4 = 1.0F;
            f2 = 0.0F;
            f5 = 1.0F;
            f3 = 0.0F;
            f6 = 1.0F;
            flag0 = true;
        }

        if ((i3 & 4) != 0) {
            f6 = Math.max(f6, 0.0625F);
            f3 = 0.0F;
            f1 = 0.0F;
            f4 = 1.0F;
            f2 = 0.0F;
            f5 = 1.0F;
            flag0 = true;
        }

        if ((i3 & 1) != 0) {
            f3 = Math.min(f3, 0.9375F);
            f6 = 1.0F;
            f1 = 0.0F;
            f4 = 1.0F;
            f2 = 0.0F;
            f5 = 1.0F;
            flag0 = true;
        }

        if (!flag0 && this.a(iblockaccess.a(i0, i1 + 1, i2))) {
            f2 = Math.min(f2, 0.9375F);
            f5 = 1.0F;
            f1 = 0.0F;
            f4 = 1.0F;
            f3 = 0.0F;
            f6 = 1.0F;
        }

        this.a(f1, f2, f3, f4, f5, f6);
    }

    public AxisAlignedBB a(World world, int i0, int i1, int i2) {
        return null;
    }

    public boolean d(World world, int i0, int i1, int i2, int i3) {
        switch (i3) {
            case 1:
                return this.a(world.a(i0, i1 + 1, i2));

            case 2:
                return this.a(world.a(i0, i1, i2 + 1));

            case 3:
                return this.a(world.a(i0, i1, i2 - 1));

            case 4:
                return this.a(world.a(i0 + 1, i1, i2));

            case 5:
                return this.a(world.a(i0 - 1, i1, i2));

            default:
                return false;
        }
    }

    private boolean a(Block block) {
        return block.d() && block.J.c();
    }

    private boolean e(World world, int i0, int i1, int i2) {
        int i3 = world.e(i0, i1, i2);
        int i4 = i3;

        if (i3 > 0) {
            for (int i5 = 0; i5 <= 3; ++i5) {
                int i6 = 1 << i5;

                if ((i3 & i6) != 0 && !this.a(world.a(i0 + Direction.a[i5], i1, i2 + Direction.b[i5])) && (world.a(i0, i1 + 1, i2) != this || (world.e(i0, i1 + 1, i2) & i6) == 0)) {
                    i4 &= ~i6;
                }
            }
        }

        if (i4 == 0 && !this.a(world.a(i0, i1 + 1, i2))) {
            return false;
        }
        else {
            if (i4 != i3) {
                world.a(i0, i1, i2, i4, 2);
            }

            return true;
        }
    }

    public void a(World world, int i0, int i1, int i2, Block block) {
        if (!world.E && !this.e(world, i0, i1, i2)) {
            this.b(world, i0, i1, i2, world.e(i0, i1, i2), 0);
            world.f(i0, i1, i2);
        }

    }

    public void a(World world, int i0, int i1, int i2, Random random) {
        if (!world.E && world.s.nextInt(4) == 0) {
            byte b0 = 4;
            int i3 = 5;
            boolean flag0 = false;

            int i4;
            int i5;
            int i6;

            label134:
            for (i4 = i0 - b0; i4 <= i0 + b0; ++i4) {
                for (i5 = i2 - b0; i5 <= i2 + b0; ++i5) {
                    for (i6 = i1 - 1; i6 <= i1 + 1; ++i6) {
                        if (world.a(i4, i6, i5) == this) {
                            --i3;
                            if (i3 <= 0) {
                                flag0 = true;
                                break label134;
                            }
                        }
                    }
                }
            }

            i4 = world.e(i0, i1, i2);
            i5 = world.s.nextInt(6);
            i6 = Direction.e[i5];
            int i7;

            if (i5 == 1 && i1 < 255 && world.c(i0, i1 + 1, i2)) {
                if (flag0) {
                    return;
                }

                int i8 = world.s.nextInt(16) & i4;

                if (i8 > 0) {
                    for (i7 = 0; i7 <= 3; ++i7) {
                        if (!this.a(world.a(i0 + Direction.a[i7], i1 + 1, i2 + Direction.b[i7]))) {
                            i8 &= ~(1 << i7);
                        }
                    }

                    if (i8 > 0) {
                        world.d(i0, i1 + 1, i2, this, i8, 2);
                    }
                }
            }
            else {
                Block block;
                int i9;

                if (i5 >= 2 && i5 <= 5 && (i4 & 1 << i6) == 0) {
                    if (flag0) {
                        return;
                    }

                    block = world.a(i0 + Direction.a[i6], i1, i2 + Direction.b[i6]);
                    if (block.J == Material.a) {
                        i7 = i6 + 1 & 3;
                        i9 = i6 + 3 & 3;
                        // CanaryMod: Grab the original stuff
                        CanaryBlock original = (CanaryBlock) world.getCanaryWorld().getBlockAt(i0, i1, i2);
                        CanaryBlock growth = new CanaryBlock(this, (short) 0, i0 + Direction.a[i6], i1, i2 + Direction.b[i6], world.getCanaryWorld());
                        BlockGrowHook blockGrowHook = new BlockGrowHook(original, growth);
                        //
                        if ((i4 & 1 << i7) != 0 && this.a(world.a(i0 + Direction.a[i6] + Direction.a[i7], i1, i2 + Direction.b[i6] + Direction.b[i7]))) {
                            // CanaryMod: set data, call hook
                            growth.setData((short) (1 << i7));
                            blockGrowHook.call();
                            if (!blockGrowHook.isCanceled()) {
                                world.d(i0 + Direction.a[i6], i1, i2 + Direction.b[i6], this, 1 << i7, 2);
                            }
                            //
                        }
                        else if ((i4 & 1 << i9) != 0 && this.a(world.a(i0 + Direction.a[i6] + Direction.a[i9], i1, i2 + Direction.b[i6] + Direction.b[i9]))) {
                            // CanaryMod: set data, call hook
                            growth.setData((short) (1 << i9));
                            blockGrowHook.call();
                            if (!blockGrowHook.isCanceled()) {
                                world.d(i0 + Direction.a[i6], i1, i2 + Direction.b[i6], this, 1 << i9, 2);
                            }
                            //
                        }
                        else if ((i4 & 1 << i7) != 0 && world.c(i0 + Direction.a[i6] + Direction.a[i7], i1, i2 + Direction.b[i6] + Direction.b[i7]) && this.a(world.a(i0 + Direction.a[i7], i1, i2 + Direction.b[i7]))) {
                            // CanaryMod: set data, call hook
                            growth.setData((short) (1 << (i6 + 2 & 3)));
                            blockGrowHook.call();
                            if (!blockGrowHook.isCanceled()) {
                                world.d(i0 + Direction.a[i6] + Direction.a[i7], i1, i2 + Direction.b[i6] + Direction.b[i7], this, 1 << (i6 + 2 & 3), 2);
                            }
                            //
                        }
                        else if ((i4 & 1 << i9) != 0 && world.c(i0 + Direction.a[i6] + Direction.a[i9], i1, i2 + Direction.b[i6] + Direction.b[i9]) && this.a(world.a(i0 + Direction.a[i9], i1, i2 + Direction.b[i9]))) {
                            // CanaryMod: set data, call hook
                            growth.setData((short) (1 << (i6 + 2 & 3)));
                            blockGrowHook.call();
                            if (!blockGrowHook.isCanceled()) {
                                world.d(i0 + Direction.a[i6] + Direction.a[i9], i1, i2 + Direction.b[i6] + Direction.b[i9], this, 1 << (i6 + 2 & 3), 2);
                            }
                            //
                        }
                        else if (this.a(world.a(i0 + Direction.a[i6], i1 + 1, i2 + Direction.b[i6]))) {
                            // CanaryMod: call hook
                            blockGrowHook.call();
                            if (!blockGrowHook.isCanceled()) {
                                world.d(i0 + Direction.a[i6], i1, i2 + Direction.b[i6], this, 0, 2);
                            }
                            //
                        }
                    }
                    else if (block.J.k() && block.d()) {
                        world.a(i0, i1, i2, i4 | 1 << i6, 2);
                    }
                }
                else if (i1 > 1) {
                    block = world.a(i0, i1 - 1, i2);
                    if (block.J == Material.a) {
                        i7 = world.s.nextInt(16) & i4;
                        if (i7 > 0) {
                            world.d(i0, i1 - 1, i2, this, i7, 2);
                        }
                    }
                    else if (block == this) {
                        i7 = world.s.nextInt(16) & i4;
                        i9 = world.e(i0, i1 - 1, i2);
                        if (i9 != (i9 | i7)) {
                            world.a(i0, i1 - 1, i2, i9 | i7, 2);
                        }
                    }
                }
            }
        }

    }

    public int a(World world, int i0, int i1, int i2, int i3, float f0, float f1, float f2, int i4) {
        byte b0 = 0;

        switch (i3) {
            case 2:
                b0 = 1;
                break;

            case 3:
                b0 = 4;
                break;

            case 4:
                b0 = 8;
                break;

            case 5:
                b0 = 2;
        }

        return b0 != 0 ? b0 : i4;
    }

    public Item a(int i0, Random random, int i1) {
        return null;
    }

    public int a(Random random) {
        return 0;
    }

    public void a(World world, EntityPlayer entityplayer, int i0, int i1, int i2, int i3) {
        if (!world.E && entityplayer.bF() != null && entityplayer.bF().b() == Items.aZ) {
            entityplayer.a(StatList.C[Block.b((Block) this)], 1);
            this.a(world, i0, i1, i2, new ItemStack(Blocks.bd, 1, 0));
        }
        else {
            super.a(world, entityplayer, i0, i1, i2, i3);
        }

    }
}
