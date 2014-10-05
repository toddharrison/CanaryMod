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

    public static final PropertyBool a = PropertyBool.a("locked");
    public static final PropertyInteger b = PropertyInteger.a("delay", 1, 4);

    protected BlockRedstoneRepeater(boolean flag0) {
        super(flag0);
        this.j(this.L.b().a(N, EnumFacing.NORTH).a(b, Integer.valueOf(1)).a(a, Boolean.valueOf(false)));
    }

    public IBlockState a(IBlockState iblockstate, IBlockAccess iblockaccess, BlockPos blockpos) {
        return iblockstate.a(a, Boolean.valueOf(this.b(iblockaccess, blockpos, iblockstate)));
    }


    public boolean a(World world, int i0, int i1, int i2, EntityPlayer entityplayer, int i3, float f0, float f1, float f2) {
        // CanaryMod: Block Physics
        BlockPhysicsHook blockPhysics = (BlockPhysicsHook) new BlockPhysicsHook(world.getCanaryWorld().getBlockAt(i0, i1, i2), false).call();
        if (blockPhysics.isCanceled()) {
            return false;
        }
        //

        if (!entityplayer.by.e) {
            return false;
        }
        else {
            world.a(blockpos, iblockstate.a(b), 3);
            return true;
        }
    }

    protected int d(IBlockState iblockstate) {
        return ((Integer) iblockstate.b(b)).intValue() * 2;
    }

    protected IBlockState e(IBlockState iblockstate) {
        Integer integer = (Integer) iblockstate.b(b);
        Boolean obool = (Boolean) iblockstate.b(a);
        EnumFacing enumfacing = (EnumFacing) iblockstate.b(N);

        return Blocks.bc.P().a(N, enumfacing).a(b, integer).a(a, obool);
    }

    protected IBlockState k(IBlockState iblockstate) {
        Integer integer = (Integer) iblockstate.b(b);
        Boolean obool = (Boolean) iblockstate.b(a);
        EnumFacing enumfacing = (EnumFacing) iblockstate.b(N);

        return Blocks.bb.P().a(N, enumfacing).a(b, integer).a(a, obool);
    }

    public Item a(IBlockState iblockstate, Random random, int i0) {
        return Items.bb;
    }

    public boolean b(IBlockAccess iblockaccess, BlockPos blockpos, IBlockState iblockstate) {
        return this.c(iblockaccess, blockpos, iblockstate) > 0;
    }

    protected boolean c(Block block) {
        return d(block);
    }

    public void b(World world, BlockPos blockpos, IBlockState iblockstate) {
        // CanaryMod: RedstoneChange
        if (this.M) {
            new RedstoneChangeHook(new CanaryBlock(BlockType.RedstoneRepeaterOn.getId(), (short) i3, i0, i1, i2, world.getCanaryWorld()), 15, 0).call();
        }
        // CanaryMod: end
        super.b(world, blockpos, iblockstate);
        this.h(world, blockpos, iblockstate);
    }

    public IBlockState a(int i0) {
        return this.P().a(N, EnumFacing.b(i0)).a(a, Boolean.valueOf(false)).a(b, Integer.valueOf(1 + (i0 >> 2)));
    }

    public int c(IBlockState iblockstate) {
        byte b0 = 0;
        int i0 = b0 | ((EnumFacing) iblockstate.b(N)).b();

        i0 |= ((Integer) iblockstate.b(b)).intValue() - 1 << 2;
        return i0;
    }

    protected BlockState e() {
        return new BlockState(this, new IProperty[]{N, b, a});
    }
}
