package net.minecraft.block;


import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.hook.world.BlockGrowHook;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Direction;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;


public class BlockCocoa extends BlockDirectional implements IGrowable {

    public BlockCocoa() {
        super(Material.k);
        this.a(true);
    }

    public void a(World world, int i0, int i1, int i2, Random random) {
        if (!this.j(world, i0, i1, i2)) {
            this.b(world, i0, i1, i2, world.e(i0, i1, i2), 0);
            world.d(i0, i1, i2, e(0), 0, 2);
        }
        else if (world.s.nextInt(5) == 0) {
            int i3 = world.e(i0, i1, i2);
            int i4 = c(i3);

            if (i4 < 2) {
                ++i4;
                // CanaryMod: BlockGrow
                CanaryBlock original = (CanaryBlock) world.getCanaryWorld().getBlockAt(i0, i1, i2);
                CanaryBlock growth = (CanaryBlock) world.getCanaryWorld().getBlockAt(i0, i1, i2);
                growth.setData((short) (i4 << 2 | l(i3)));
                BlockGrowHook blockGrowHook = (BlockGrowHook) new BlockGrowHook(original, growth).call();
                if (!blockGrowHook.isCanceled()) {
                    world.a(i0, i1, i2, i4 << 2 | l(i3), 2);
                }
                //
            }
        }

    }

    public boolean j(World world, int i0, int i1, int i2) {
        int i3 = l(world.e(i0, i1, i2));

        i0 += Direction.a[i3];
        i2 += Direction.b[i3];
        Block block = world.a(i0, i1, i2);

        return block == Blocks.r && BlockLog.c(world.e(i0, i1, i2)) == 3;
    }

    public int b() {
        return 28;
    }

    public boolean d() {
        return false;
    }

    public boolean c() {
        return false;
    }

    public AxisAlignedBB a(World world, int i0, int i1, int i2) {
        this.a((IBlockAccess) world, i0, i1, i2);
        return super.a(world, i0, i1, i2);
    }

    public void a(IBlockAccess iblockaccess, int i0, int i1, int i2) {
        int i3 = iblockaccess.e(i0, i1, i2);
        int i4 = l(i3);
        int i5 = c(i3);
        int i6 = 4 + i5 * 2;
        int i7 = 5 + i5 * 2;
        float f0 = (float) i6 / 2.0F;

        switch (i4) {
            case 0:
                this.a((8.0F - f0) / 16.0F, (12.0F - (float) i7) / 16.0F, (15.0F - (float) i6) / 16.0F, (8.0F + f0) / 16.0F, 0.75F, 0.9375F);
                break;

            case 1:
                this.a(0.0625F, (12.0F - (float) i7) / 16.0F, (8.0F - f0) / 16.0F, (1.0F + (float) i6) / 16.0F, 0.75F, (8.0F + f0) / 16.0F);
                break;

            case 2:
                this.a((8.0F - f0) / 16.0F, (12.0F - (float) i7) / 16.0F, 0.0625F, (8.0F + f0) / 16.0F, 0.75F, (1.0F + (float) i6) / 16.0F);
                break;

            case 3:
                this.a((15.0F - (float) i6) / 16.0F, (12.0F - (float) i7) / 16.0F, (8.0F - f0) / 16.0F, 0.9375F, 0.75F, (8.0F + f0) / 16.0F);
        }

    }

    public void a(World world, int i0, int i1, int i2, EntityLivingBase entitylivingbase, ItemStack itemstack) {
        int i3 = ((MathHelper.c((double) (entitylivingbase.y * 4.0F / 360.0F) + 0.5D) & 3) + 0) % 4;

        world.a(i0, i1, i2, i3, 2);
    }

    public int a(World world, int i0, int i1, int i2, int i3, float f0, float f1, float f2, int i4) {
        if (i3 == 1 || i3 == 0) {
            i3 = 2;
        }

        return Direction.f[Direction.e[i3]];
    }

    public void a(World world, int i0, int i1, int i2, Block block) {
        if (!this.j(world, i0, i1, i2)) {
            this.b(world, i0, i1, i2, world.e(i0, i1, i2), 0);
            world.d(i0, i1, i2, e(0), 0, 2);
        }

    }

    public static int c(int i0) {
        return (i0 & 12) >> 2;
    }

    public void a(World world, int i0, int i1, int i2, int i3, float f0, int i4) {
        int i5 = c(i3);
        byte b0 = 1;

        if (i5 >= 2) {
            b0 = 3;
        }

        for (int i6 = 0; i6 < b0; ++i6) {
            this.a(world, i0, i1, i2, new ItemStack(Items.aR, 1, 3));
        }

    }

    public int k(World world, int i0, int i1, int i2) {
        return 3;
    }

    public boolean a(World world, int i0, int i1, int i2, boolean flag0) {
        int i3 = world.e(i0, i1, i2);
        int i4 = c(i3);

        return i4 < 2;
    }

    public boolean a(World world, Random random, int i0, int i1, int i2) {
        return true;
    }

    public void b(World world, Random random, int i0, int i1, int i2) {
        int i3 = world.e(i0, i1, i2);
        int i4 = BlockDirectional.l(i3);
        int i5 = c(i3);

        ++i5;
        world.a(i0, i1, i2, i5 << 2 | i4, 2);
    }
}
