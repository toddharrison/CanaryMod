package net.minecraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;

public class BlockCompressedPowered extends BlockCompressed {

    public BlockCompressedPowered(MapColor mapcolor) {
        super(mapcolor);
        this.a(CreativeTabs.d);
    }

    public boolean g() {
        return true;
    }

    public int a(IBlockAccess iblockaccess, BlockPos blockpos, IBlockState iblockstate, EnumFacing enumfacing) {
        return 15;
    }

    /* FIXME
    // CanaryMod: pull place method in to do RedstoneChange
    @Override
    public void b(World world, int i0, int i1, int i2) {
        new RedstoneChangeHook(new CanaryBlock(RedstoneBlock.getId(), (short) 0, i0, i1, i2, world.getCanaryWorld()), 0, 15).call();
        super.b(world, i0, i1, i2);
    }

    // CanaryMod: pull break method in to do RedstoneChange
    @Override
    public void a(World world, int i0, int i1, int i2, Block block, int i3) {
        new RedstoneChangeHook(new CanaryBlock(RedstoneBlock.getId(), (short) 0, i0, i1, i2, world.getCanaryWorld()), 15, 0).call();
        super.a(world, i0, i1, i2, block);
    }
    //
    */
}
