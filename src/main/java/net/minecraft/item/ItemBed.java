package net.minecraft.item;

import net.canarymod.api.world.blocks.BlockFace;
import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.api.world.position.BlockPosition;
import net.canarymod.hook.player.BlockPlaceHook;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class ItemBed extends Item {

    public ItemBed() {
        this.a(CreativeTabs.c);
    }

    public boolean a(ItemStack itemstack, EntityPlayer entityplayer, World world, BlockPos blockpos, EnumFacing enumfacing, float f0, float f1, float f2) {
        if (world.D) {
            return true;
        }
        else if (enumfacing != EnumFacing.UP) {
            return false;
        }
        else {
            IBlockState iblockstate = world.p(blockpos);
            Block block = iblockstate.c();
            boolean flag0 = block.f(world, blockpos);
            // CanaryMod: BlockPlaceHook
            BlockPosition cbp = new BlockPosition(blockpos); // Translate native block pos
            CanaryBlock clicked = (CanaryBlock)world.getCanaryWorld().getBlockAt(cbp); // Store Clicked
            BlockFace cbf = BlockFace.fromByte((byte)enumfacing.a()); // Get the click face
            clicked.setFaceClicked(cbf); // Set face clicked
            cbp = cbp.safeClone(); // Remake BlockPosition
            cbp.transform(cbf); // Adjust position based on face
            //

            if (!flag0) {
                blockpos = blockpos.a();
            }

            int i0 = MathHelper.c((double)(entityplayer.y * 4.0F / 360.0F) + 0.5D) & 3;
            EnumFacing enumfacing1 = EnumFacing.b(i0);
            BlockPos blockpos1 = blockpos.a(enumfacing1);
            boolean flag1 = block.f(world, blockpos1);
            boolean flag2 = world.d(blockpos) || flag0;
            boolean flag3 = world.d(blockpos1) || flag1;

            if (entityplayer.a(blockpos, enumfacing, itemstack) && entityplayer.a(blockpos1, enumfacing, itemstack)) {
                if (flag2 && flag3 && World.a((IBlockAccess)world, blockpos.b()) && World.a((IBlockAccess)world, blockpos1.b())) {
                    int i1 = enumfacing1.b();
                    IBlockState iblockstate1 = Blocks.C.P().a(BlockBed.b, false).a(BlockBed.N, enumfacing1).a(BlockBed.a, BlockBed.EnumPartType.FOOT);

                    // CanaryMod: BlockPlaceHook continued
                    CanaryBlock placed = new CanaryBlock(iblockstate1, cbp, world.getCanaryWorld());
                    // CanaryMod: Create Hook and call it
                    BlockPlaceHook hook = (BlockPlaceHook)new BlockPlaceHook(((EntityPlayerMP)entityplayer).getPlayer(), clicked, placed).call();
                    if (hook.isCanceled()) {
                        return false;
                    }
                    //
                    if (world.a(blockpos, iblockstate1, 3)) {
                        IBlockState iblockstate2 = iblockstate1.a(BlockBed.a, BlockBed.EnumPartType.HEAD);

                        world.a(blockpos1, iblockstate2, 3);
                    }

                    --itemstack.b;
                    return true;
                }
                else {
                    return false;
                }
            }
            else {
                return false;
            }
        }
    }
}
