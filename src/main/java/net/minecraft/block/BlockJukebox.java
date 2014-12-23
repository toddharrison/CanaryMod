package net.minecraft.block;

import net.canarymod.api.world.blocks.CanaryJukebox;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockJukebox extends BlockContainer {

    public static final PropertyBool a = PropertyBool.a("has_record");

    protected BlockJukebox() {
        super(Material.d);
        this.j(this.L.b().a(a, Boolean.valueOf(false)));
        this.a(CreativeTabs.c);
    }

    public boolean a(World world, BlockPos blockpos, IBlockState iblockstate, EntityPlayer entityplayer, EnumFacing enumfacing, float f0, float f1, float f2) {
        if (((Boolean)iblockstate.b(a)).booleanValue()) {
            this.e(world, blockpos, iblockstate);
            iblockstate = iblockstate.a(a, Boolean.valueOf(false));
            world.a(blockpos, iblockstate, 2);
            return true;
        }
        else {
            return false;
        }
    }

    public void a(World world, BlockPos blockpos, IBlockState iblockstate, ItemStack itemstack) {
        if (!world.D) {
            TileEntity tileentity = world.s(blockpos);

            if (tileentity instanceof BlockJukebox.TileEntityJukebox) {
                ((BlockJukebox.TileEntityJukebox)tileentity).a(new ItemStack(itemstack.b(), 1, itemstack.i()));
                world.a(blockpos, iblockstate.a(a, Boolean.valueOf(true)), 2);
            }
        }
    }

    private void e(World world, BlockPos blockpos, IBlockState iblockstate) {
        if (!world.D) {
            TileEntity tileentity = world.s(blockpos);

            if (tileentity instanceof BlockJukebox.TileEntityJukebox) {
                BlockJukebox.TileEntityJukebox blockjukebox_tileentityjukebox = (BlockJukebox.TileEntityJukebox)tileentity;
                ItemStack itemstack = blockjukebox_tileentityjukebox.a();

                if (itemstack != null) {
                    world.b(1005, blockpos, 0);
                    world.a(blockpos, (String)null);
                    blockjukebox_tileentityjukebox.a((ItemStack)null);
                    float f0 = 0.7F;
                    double d0 = (double)(world.s.nextFloat() * f0) + (double)(1.0F - f0) * 0.5D;
                    double d1 = (double)(world.s.nextFloat() * f0) + (double)(1.0F - f0) * 0.2D + 0.6D;
                    double d2 = (double)(world.s.nextFloat() * f0) + (double)(1.0F - f0) * 0.5D;
                    ItemStack itemstack1 = itemstack.k();
                    EntityItem entityitem = new EntityItem(world, (double)blockpos.n() + d0, (double)blockpos.o() + d1, (double)blockpos.p() + d2, itemstack1);

                    entityitem.p();
                    world.d((Entity)entityitem);
                }
            }
        }
    }

    public void b(World world, BlockPos blockpos, IBlockState iblockstate) {
        this.e(world, blockpos, iblockstate);
        super.b(world, blockpos, iblockstate);
    }

    public void a(World world, BlockPos blockpos, IBlockState iblockstate, float f0, int i0) {
        if (!world.D) {
            super.a(world, blockpos, iblockstate, f0, 0);
        }
    }

    public TileEntity a(World world, int i0) {
        return new BlockJukebox.TileEntityJukebox();
    }

    public boolean N() {
        return true;
    }

    public int l(World world, BlockPos blockpos) {
        TileEntity tileentity = world.s(blockpos);

        if (tileentity instanceof BlockJukebox.TileEntityJukebox) {
            ItemStack itemstack = ((BlockJukebox.TileEntityJukebox)tileentity).a();

            if (itemstack != null) {
                return Item.b(itemstack.b()) + 1 - Item.b(Items.cq);
            }
        }

        return 0;
    }

    public int b() {
        return 3;
    }

    public IBlockState a(int i0) {
        return this.P().a(a, Boolean.valueOf(i0 > 0));
    }

    public int c(IBlockState iblockstate) {
        return ((Boolean)iblockstate.b(a)).booleanValue() ? 1 : 0;
    }

    protected BlockState e() {
        return new BlockState(this, new IProperty[]{ a });
    }

    public static class TileEntityJukebox extends TileEntity {

        private ItemStack a;

        public TileEntityJukebox() {
            this.complexBlock = new CanaryJukebox(this); // CanaryMod: wrap tile entity
        }

        public void a(NBTTagCompound p_a_1_) {
            super.a(p_a_1_);
            if (p_a_1_.b("RecordItem", 10)) {
                this.a(ItemStack.a(p_a_1_.m("RecordItem")));
            }
            else if (p_a_1_.f("Record") > 0) {
                this.a(new ItemStack(Item.b(p_a_1_.f("Record")), 1, 0));
            }
        }

        public void b(NBTTagCompound p_b_1_) {
            super.b(p_b_1_);
            if (this.a() != null) {
                p_b_1_.a("RecordItem", (NBTBase)this.a().b(new NBTTagCompound()));
            }
        }

        public ItemStack a() {
            return this.a;
        }

        public void a(ItemStack p_a_1_) {
            this.a = p_a_1_;
            this.o_();
        }

        // CanaryMod
        public CanaryJukebox getCanaryJukebox() {
            return (CanaryJukebox)complexBlock;
        }
    }
}
