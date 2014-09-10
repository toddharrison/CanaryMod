package net.minecraft.block;

import net.canarymod.api.world.blocks.BlockType;
import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.hook.world.BlockPhysicsHook;
import net.canarymod.hook.world.RedstoneChangeHook;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockRedstoneRepeater extends BlockRedstoneDiode {

    public static final double[] b = new double[]{-0.0625D, 0.0625D, 0.1875D, 0.3125D};
    private static final int[] M = new int[]{1, 2, 3, 4};

    protected BlockRedstoneRepeater(boolean flag0) {
        super(flag0);
    }

    public boolean a(World world, int i0, int i1, int i2, EntityPlayer entityplayer, int i3, float f0, float f1, float f2) {
        // CanaryMod: Block Physics
        BlockPhysicsHook blockPhysics = (BlockPhysicsHook) new BlockPhysicsHook(world.getCanaryWorld().getBlockAt(i0, i1, i2), false).call();
        if (blockPhysics.isCanceled()) {
            return false;
        }
        //

        int i4 = world.e(i0, i1, i2);
        int i5 = (i4 & 12) >> 2;

        i5 = i5 + 1 << 2 & 12;
        world.a(i0, i1, i2, i5 | i4 & 3, 3);
        return true;
    }

    protected int b(int i0) {
        return M[(i0 & 12) >> 2] * 2;
    }

    protected BlockRedstoneDiode e() {
        return Blocks.aS;
    }

    protected BlockRedstoneDiode i() {
        return Blocks.aR;
    }

    public Item a(int i0, Random random, int i1) {
        return Items.aW;
    }

    public int b() {
        return 15;
    }

    public boolean g(IBlockAccess iblockaccess, int i0, int i1, int i2, int i3) {
        return this.h(iblockaccess, i0, i1, i2, i3) > 0;
    }

    protected boolean a(Block block) {
        return d(block);
    }

    public void a(World world, int i0, int i1, int i2, Block block, int i3) {
        // CanaryMod: RedstoneChange
        if (this.a) {
            new RedstoneChangeHook(new CanaryBlock(BlockType.RedstoneRepeaterOn.getId(), (short) i3, i0, i1, i2, world.getCanaryWorld()), 15, 0).call();
        }
        // CanaryMod: end
        super.a(world, i0, i1, i2, block, i3);
        this.e(world, i0, i1, i2);
    }
}
