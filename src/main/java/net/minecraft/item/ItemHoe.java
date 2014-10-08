package net.minecraft.item;

import net.canarymod.api.world.blocks.BlockFace;
import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.api.world.position.BlockPosition;
import net.canarymod.hook.player.ItemUseHook;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemHoe extends Item {

    protected Item.ToolMaterial a;

    public ItemHoe(Item.ToolMaterial item_toolmaterial) {
        this.a = item_toolmaterial;
        this.h = 1;
        this.d(item_toolmaterial.a());
        this.a(CreativeTabs.i);
    }

    public boolean a(ItemStack itemstack, EntityPlayer entityplayer, World world, BlockPos blockpos, EnumFacing enumfacing, float f0, float f1, float f2) {
        if (!entityplayer.a(blockpos.a(enumfacing), enumfacing, itemstack)) {
            return false;
        }
        else {
            IBlockState iblockstate = world.p(blockpos);
            Block block = iblockstate.c();

            // CanaryMod: ItemUse
            BlockPosition cbp = new BlockPosition(blockpos); // Translate native block pos
            CanaryBlock clicked = (CanaryBlock)world.getCanaryWorld().getBlockAt(cbp); // Store Clicked
            BlockFace cbf = BlockFace.fromByte((byte)enumfacing.a()); // Get the click face
            clicked.setFaceClicked(cbf); // Set face clicked
            if (((ItemUseHook)new ItemUseHook(((EntityPlayerMP)entityplayer).getPlayer(), itemstack.getCanaryItem(), clicked).call()).isCanceled()) {
                return false;
            }

            //

            if (enumfacing != EnumFacing.DOWN && world.p(blockpos.a()).c().r() == Material.a) {
                if (block == Blocks.c) {
                    return this.a(itemstack, entityplayer, world, blockpos, Blocks.ak.P());
                }

                if (block == Blocks.d) {
                    switch (ItemHoe.SwitchDirtType.a[((BlockDirt.DirtType)iblockstate.b(BlockDirt.a)).ordinal()]) {
                        case 1:
                            return this.a(itemstack, entityplayer, world, blockpos, Blocks.ak.P());

                        case 2:
                            return this.a(itemstack, entityplayer, world, blockpos, Blocks.d.P().a(BlockDirt.a, BlockDirt.DirtType.DIRT));
                    }
                }
            }

            return false;
        }
    }

    protected boolean a(ItemStack itemstack, EntityPlayer entityplayer, World world, BlockPos blockpos, IBlockState iblockstate) {
        world.a((double)((float)blockpos.n() + 0.5F), (double)((float)blockpos.o() + 0.5F), (double)((float)blockpos.p() + 0.5F), iblockstate.c().H.c(), (iblockstate.c().H.d() + 1.0F) / 2.0F, iblockstate.c().H.e() * 0.8F);
        if (world.D) {
            return true;
        }
        else {
            world.a(blockpos, iblockstate);
            itemstack.a(1, (EntityLivingBase)entityplayer);
            return true;
        }
    }

    public String g() {
        return this.a.toString();
    }

    static final class SwitchDirtType {

        static final int[] a = new int[BlockDirt.DirtType.values().length];

        static {
            try {
                a[BlockDirt.DirtType.DIRT.ordinal()] = 1;
            }
            catch (NoSuchFieldError nosuchfielderror) {
                ;
            }

            try {
                a[BlockDirt.DirtType.COARSE_DIRT.ordinal()] = 2;
            }
            catch (NoSuchFieldError nosuchfielderror1) {
                ;
            }
        }
    }
}
