package net.minecraft.item;

import net.canarymod.api.world.blocks.BlockFace;
import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.hook.player.BlockPlaceHook;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemReed extends Item {

    private Block a;

    public ItemReed(Block block) {
        this.a = block;
    }

    public boolean a(ItemStack itemstack, EntityPlayer entityplayer, World world, BlockPos blockpos, EnumFacing enumfacing, float f0, float f1, float f2) {
        IBlockState iblockstate = world.p(blockpos);
        Block block = iblockstate.c();

        // CanaryMod: BlockPlaceHook
        CanaryBlock clicked = (CanaryBlock) world.getCanaryWorld().getBlockAt(i0, i1, i2);
        clicked.setFaceClicked(BlockFace.fromByte((byte) i3));
        //

        if (block == Blocks.aH && ((Integer) iblockstate.b(BlockSnow.a)).intValue() < 1) {
            enumfacing = EnumFacing.UP;
        }
        else if (!block.f(world, blockpos)) {
            blockpos = blockpos.a(enumfacing);
        }

        if (!entityplayer.a(blockpos, enumfacing, itemstack)) {
            return false;
        }
        else if (itemstack.b == 0) {
            return false;
        }
        else {
            if (!entityplayer.a(i0, i1, i2, i3, itemstack)) {
                return false;
            }
            else if (itemstack.b == 0) {
                return false;
            }
            else {
                if (world.a(this.a, blockpos, false, enumfacing, (Entity) null, itemstack)) {
                    IBlockState iblockstate1 = this.a.a(world, blockpos, enumfacing, f0, f1, f2, 0, entityplayer);

                    if (world.a(blockpos, iblockstate1, 3)) {
                        iblockstate1 = world.p(blockpos);
                        // set placed
                        CanaryBlock placed = new CanaryBlock((short) Block.b(this.a), (short) 0, i0, i1, i2, world.getCanaryWorld());
                        // Create and Call
                        BlockPlaceHook hook = (BlockPlaceHook) new BlockPlaceHook(((EntityPlayerMP) entityplayer).getPlayer(), clicked, placed).call();
                        if (hook.isCanceled()) {
                            return false;
                        }
                        //
                        if (iblockstate1.c() == this.a) {
                            ItemBlock.a(world, blockpos, itemstack);
                            iblockstate1.c().a(world, blockpos, iblockstate1, (EntityLivingBase) entityplayer, itemstack);
                        }

                        world.a((double) ((float) blockpos.n() + 0.5F), (double) ((float) blockpos.o() + 0.5F), (double) ((float) blockpos.p() + 0.5F), this.a.H.b(), (this.a.H.d() + 1.0F) / 2.0F, this.a.H.e() * 0.8F);
                        --itemstack.b;
                        return true;
                    }
                }

                return false;
            }
        }
    }
