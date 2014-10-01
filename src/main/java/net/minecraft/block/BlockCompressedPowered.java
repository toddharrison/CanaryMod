package net.minecraft.block;

import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.hook.world.RedstoneChangeHook;
import net.minecraft.block.material.MapColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import static net.canarymod.api.world.blocks.BlockType.RedstoneBlock;

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

    // CanaryMod: pull place method in to do RedstoneChange
    @Override
    public void b(World world, int i0, int i1, int i2) {
        new RedstoneChangeHook(new CanaryBlock(RedstoneBlock.getId(), (short) 0, i0, i1, i2, world.getCanaryWorld()), 0, 15).call();
        super.a(world, i0, i1, i2);
    }

    // CanaryMod: pull break method in to do RedstoneChange
    @Override
    public void a(World world, int i0, int i1, int i2, Block block, int i3) {
        new RedstoneChangeHook(new CanaryBlock(RedstoneBlock.getId(), (short) 0, i0, i1, i2, world.getCanaryWorld()), 15, 0).call();
        super.a(world, i0, i1, i2, block);
    }
    //
}
