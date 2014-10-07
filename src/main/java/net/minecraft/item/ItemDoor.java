package net.minecraft.item;

import net.canarymod.api.world.blocks.BlockFace;
import net.canarymod.api.world.blocks.BlockType;
import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.api.world.position.BlockPosition;
import net.canarymod.hook.player.BlockPlaceHook;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemDoor extends Item {

    private Block a;

    public ItemDoor(Block block) {
        this.a = block;
        this.a(CreativeTabs.d);
    }

    public boolean a(ItemStack itemstack, EntityPlayer entityplayer, World world, BlockPos blockpos, EnumFacing enumfacing, float f0, float f1, float f2) {
        if (enumfacing != EnumFacing.UP) {
            return false;
        }
        else {
            IBlockState iblockstate = world.p(blockpos);
            Block block = iblockstate.c();

            if (!block.f(world, blockpos)) {
                blockpos = blockpos.a(enumfacing);
            }

            if (!entityplayer.a(blockpos, enumfacing, itemstack)) {
                return false;
            }
            else if (!this.a.c(world, blockpos)) {
                return false;
            }
            else {
                // CanaryMod: BlockPlaceHook
                BlockPosition cbp = new BlockPosition(blockpos);
                CanaryBlock clicked = (CanaryBlock)world.getCanaryWorld().getBlockAt(cbp);
                BlockFace cbf = BlockFace.fromByte((byte)enumfacing.a());
                clicked.setFaceClicked(cbf); // Set face clicked
                cbp = cbp.safeClone(); // clone the original BlockPosition
                cbp.transform(cbf); // Adjust BlockPostiion according to clicked face
                CanaryBlock placed = new CanaryBlock(BlockType.fromId((short)Block.a(block)), (short)0, cbp, world.getCanaryWorld());
                BlockPlaceHook hook = (BlockPlaceHook)new BlockPlaceHook(((EntityPlayerMP)entityplayer).getPlayer(), clicked, placed).call();
                if (hook.isCanceled()) {
                    return false;
                }
                //

                a(world, blockpos, EnumFacing.a((double)entityplayer.y), this.a);
                --itemstack.b;
                return true;
            }
        }
    }

    public static void a(World world, BlockPos blockpos, EnumFacing enumfacing, Block block) {
        BlockPos blockpos1 = blockpos.a(enumfacing.e());
        BlockPos blockpos2 = blockpos.a(enumfacing.f());
        int i0 = (world.p(blockpos2).c().t() ? 1 : 0) + (world.p(blockpos2.a()).c().t() ? 1 : 0);
        int i1 = (world.p(blockpos1).c().t() ? 1 : 0) + (world.p(blockpos1.a()).c().t() ? 1 : 0);
        boolean flag0 = world.p(blockpos2).c() == block || world.p(blockpos2.a()).c() == block;
        boolean flag1 = world.p(blockpos1).c() == block || world.p(blockpos1.a()).c() == block;
        boolean flag2 = false;

        if (flag0 && !flag1 || i1 > i0) {
            flag2 = true;
        }

        BlockPos blockpos3 = blockpos.a();
        IBlockState iblockstate = block.P().a(BlockDoor.a, enumfacing).a(BlockDoor.M, flag2 ? BlockDoor.EnumHingePosition.RIGHT : BlockDoor.EnumHingePosition.LEFT);

        world.a(blockpos, iblockstate.a(BlockDoor.O, BlockDoor.EnumDoorHalf.LOWER), 2);
        world.a(blockpos3, iblockstate.a(BlockDoor.O, BlockDoor.EnumDoorHalf.UPPER), 2);
        world.c(blockpos, block);
        world.c(blockpos3, block);
    }
}
