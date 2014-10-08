package net.minecraft.block;

import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.hook.world.RedstoneChangeHook;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

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
    public void c(World world, BlockPos blockpos, IBlockState iblockstate) {
        new RedstoneChangeHook(new CanaryBlock(iblockstate, blockpos, world), 0, 15); //Can't really cancel this here...
        super.c(world, blockpos, iblockstate);
    }

    // CanaryMod: pull break method in to do RedstoneChange
    public void b(World world, BlockPos blockpos, IBlockState iblockstate) {
        new RedstoneChangeHook(new CanaryBlock(iblockstate, blockpos, world), 15, 0); //Can't really cancel this here...
        super.b(world, blockpos, iblockstate);
    }
    //
}
