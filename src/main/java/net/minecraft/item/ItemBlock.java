package net.minecraft.item;

import net.canarymod.api.world.blocks.BlockFace;
import net.canarymod.api.world.blocks.BlockType;
import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.api.world.position.BlockPosition;
import net.canarymod.hook.player.BlockPlaceHook;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemBlock extends Item {

    protected final Block a;
    protected boolean handled = false; // CanaryMod: for ItemSlab inconsistency...

    public ItemBlock(Block block) {
        this.a = block;
    }

    public ItemBlock b(String s0) {
        super.c(s0);
        return this;
    }

    public boolean a(ItemStack itemstack, EntityPlayer entityplayer, World world, BlockPos blockpos, EnumFacing enumfacing, float f0, float f1, float f2) {
        IBlockState iblockstate = world.p(blockpos);
        Block block = iblockstate.c();

        // CanaryMod: BlockPlaceHook
        BlockPosition cbp = new BlockPosition(blockpos); // Translate native block pos
        CanaryBlock clicked = (CanaryBlock)world.getCanaryWorld().getBlockAt(cbp); // Store Clicked
        BlockFace cbf = BlockFace.fromByte((byte)enumfacing.a()); // Get the click face
        clicked.setFaceClicked(cbf); // Set face clicked
        cbp = cbp.clone(); // Remake BlockPosition
        cbp.transform(cbf); // Adjust position based on face
        //

        if (block == Blocks.aH && ((Integer)iblockstate.b(BlockSnow.a)).intValue() < 1) {
            enumfacing = EnumFacing.UP;
        }
        else if (!block.f(world, blockpos)) {
            blockpos = blockpos.a(enumfacing);
        }

        if (itemstack.b == 0) {
            return false;
        }
        else if (!entityplayer.a(blockpos, enumfacing, itemstack)) {
            return false;
        }
        else if (blockpos.o() == 255 && this.a.r().a()) {
            return false;
        }
        else if (world.a(this.a, blockpos, false, enumfacing, (Entity)null, itemstack)) {
            int i0 = this.a(itemstack.i());
            IBlockState iblockstate1 = this.a.a(world, blockpos, enumfacing, f0, f1, f2, i0, entityplayer);

            if (!handled) { // if ItemSlab didn't call BlockPlace
                // set placed
                CanaryBlock placed = new CanaryBlock(BlockType.fromId(Block.a(this.a)), (short)itemstack.h(), cbp, world.getCanaryWorld());
                // Create and Call
                BlockPlaceHook hook = (BlockPlaceHook)new BlockPlaceHook(((EntityPlayerMP)entityplayer).getPlayer(), clicked, placed).call();
                if (hook.isCanceled()) {
                    return false;
                }
                //
            }

            if (world.a(blockpos, iblockstate1, 3)) {
                iblockstate1 = world.p(blockpos);
                if (iblockstate1.c() == this.a) {
                    a(world, blockpos, itemstack);
                    this.a.a(world, blockpos, iblockstate1, (EntityLivingBase)entityplayer, itemstack);
                }

                world.a((double)((float)blockpos.n() + 0.5F), (double)((float)blockpos.o() + 0.5F), (double)((float)blockpos.p() + 0.5F), this.a.H.b(), (this.a.H.d() + 1.0F) / 2.0F, this.a.H.e() * 0.8F);
                --itemstack.b;
            }

            return true;
        }
        else {
            return false;
        }
    }

    public static boolean a(World world, BlockPos blockpos, ItemStack itemstack) {
        if (itemstack.n() && itemstack.o().b("BlockEntityTag", 10)) {
            TileEntity tileentity = world.s(blockpos);

            if (tileentity != null) {
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttagcompound.b();

                tileentity.b(nbttagcompound);
                NBTTagCompound nbttagcompound2 = (NBTTagCompound)itemstack.o().a("BlockEntityTag");

                nbttagcompound.a(nbttagcompound2);
                nbttagcompound.a("x", blockpos.n());
                nbttagcompound.a("y", blockpos.o());
                nbttagcompound.a("z", blockpos.p());
                if (!nbttagcompound.equals(nbttagcompound1)) {
                    tileentity.a(nbttagcompound);
                    tileentity.o_();
                    return true;
                }
            }
        }

        return false;
    }

    public String e_(ItemStack itemstack) {
        return this.a.a();
    }

    public String a() {
        return this.a.a();
    }

    public Block d() {
        return this.a;
    }

    public Item c(String s0) {
        return this.b(s0);
    }
}
