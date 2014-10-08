package net.minecraft.item;

import com.google.common.collect.Maps;
import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.hook.player.ItemUseHook;
import net.minecraft.block.BlockJukebox;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import java.util.Map;

public class ItemRecord extends Item {

    private static final Map b = Maps.newHashMap();
    public final String a;

    protected ItemRecord(String s0) {
        this.a = s0;
        this.h = 1;
        this.a(CreativeTabs.f);
        b.put("records." + s0, this);
    }

    public boolean a(ItemStack itemstack, EntityPlayer entityplayer, World world, BlockPos blockpos, EnumFacing enumfacing, float f0, float f1, float f2) {
        IBlockState iblockstate = world.p(blockpos);
        // CanaryMod: ItemUse
        CanaryBlock clicked = new CanaryBlock(iblockstate, blockpos, world); // Store Clicked
        clicked.setFaceClicked(enumfacing.asBlockFace()); // Set face clicked
        if (new ItemUseHook(((EntityPlayerMP)entityplayer).getPlayer(), itemstack.getCanaryItem(), clicked).call().isCanceled()) {
            return false;
        }
        //
        if (iblockstate.c() == Blocks.aN && !((Boolean)iblockstate.b(BlockJukebox.a)).booleanValue()) {
            if (world.D) {
                return true;
            }
            else {
                ((BlockJukebox)Blocks.aN).a(world, blockpos, iblockstate, itemstack);
                world.a((EntityPlayer)null, 1005, blockpos, Item.b((Item)this));
                --itemstack.b;
                return true;
            }
        }
        else {
            return false;
        }
    }

    public EnumRarity g(ItemStack itemstack) {
        return EnumRarity.RARE;
    }
}
