package net.minecraft.block;

import net.canarymod.hook.world.DispenseHook;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.dispenser.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.RegistryDefaulted;
import net.minecraft.world.World;

import java.util.Random;

public class BlockDispenser extends BlockContainer {

    public static final PropertyDirection a = PropertyDirection.a("facing");
    public static final PropertyBool b = PropertyBool.a("triggered");
    public static final RegistryDefaulted M = new RegistryDefaulted(new BehaviorDefaultDispenseItem());
    protected Random N = new Random();

    protected BlockDispenser() {
        super(Material.e);
        this.j(this.L.b().a(a, EnumFacing.NORTH).a(b, Boolean.valueOf(false)));
        this.a(CreativeTabs.d);
    }

    public int a(World world) {
        return 4;
    }

    public void c(World world, BlockPos blockpos, IBlockState iblockstate) {
        super.c(world, blockpos, iblockstate);
        this.e(world, blockpos, iblockstate);
    }

    private void e(World world, BlockPos blockpos, IBlockState iblockstate) {
        if (!world.D) {
            EnumFacing enumfacing = (EnumFacing) iblockstate.b(a);
            boolean flag0 = world.p(blockpos.c()).c().m();
            boolean flag1 = world.p(blockpos.d()).c().m();

            if (enumfacing == EnumFacing.NORTH && flag0 && !flag1) {
                enumfacing = EnumFacing.SOUTH;
            }
            else if (enumfacing == EnumFacing.SOUTH && flag1 && !flag0) {
                enumfacing = EnumFacing.NORTH;
            }
            else {
                boolean flag2 = world.p(blockpos.e()).c().m();
                boolean flag3 = world.p(blockpos.f()).c().m();

                if (enumfacing == EnumFacing.WEST && flag2 && !flag3) {
                    enumfacing = EnumFacing.EAST;
                }
                else if (enumfacing == EnumFacing.EAST && flag3 && !flag2) {
                    enumfacing = EnumFacing.WEST;
                }
            }

            world.a(blockpos, iblockstate.a(a, enumfacing).a(b, Boolean.valueOf(false)), 2);
        }
    }

    public boolean a(World world, BlockPos blockpos, IBlockState iblockstate, EntityPlayer entityplayer, EnumFacing enumfacing, float f0, float f1, float f2) {
        if (world.D) {
            return true;
        }
        else {
            TileEntity tileentity = world.s(blockpos);

            if (tileentity instanceof TileEntityDispenser) {
                entityplayer.a((IInventory) ((TileEntityDispenser) tileentity));
            }

            return true;
        }
    }

    protected void d(World world, BlockPos blockpos) {
        BlockSourceImpl blocksourceimpl = new BlockSourceImpl(world, blockpos);
        TileEntityDispenser tileentitydispenser = (TileEntityDispenser) blocksourceimpl.h();

        if (tileentitydispenser != null) {
            int i0 = tileentitydispenser.m();

            if (i0 < 0) {
                // CanaryMod: Dispense Smoke
                DispenseHook hook = (DispenseHook) new DispenseHook(tileentitydispenser.getCanaryDispenser(), null).call();
                if (!hook.isCanceled()) {
                    world.b(1001, blockpos, 0);
                }
                //
            }
            else {
                ItemStack itemstack = tileentitydispenser.a(i0);
                IBehaviorDispenseItem ibehaviordispenseitem = this.a(itemstack);

                if (ibehaviordispenseitem != IBehaviorDispenseItem.a) {
                    ItemStack itemstack1 = ibehaviordispenseitem.a(blocksourceimpl, itemstack);

                    tileentitydispenser.a(i0, itemstack1.b == 0 ? null : itemstack1);
                }

            }
        }
    }
    
    // CanaryMod: protected>>public
    public IBehaviorDispenseItem a(ItemStack itemstack) {
        return (IBehaviorDispenseItem) M.a(itemstack == null ? null : itemstack.b());
    }

    public void a(World world, BlockPos blockpos, IBlockState iblockstate, Block block) {
        boolean flag0 = world.z(blockpos) || world.z(blockpos.a());
        boolean flag1 = ((Boolean) iblockstate.b(b)).booleanValue();

        if (flag0 && !flag1) {
            world.a(blockpos, (Block) this, this.a(world));
            world.a(blockpos, iblockstate.a(b, Boolean.valueOf(true)), 4);
        }
        else if (!flag0 && flag1) {
            world.a(blockpos, iblockstate.a(b, Boolean.valueOf(false)), 4);
        }

    }

    public void b(World world, BlockPos blockpos, IBlockState iblockstate, Random random) {
        if (!world.D) {
            this.d(world, blockpos);
        }

    }

    public TileEntity a(World world, int i0) {
        return new TileEntityDispenser();
    }

    public IBlockState a(World world, BlockPos blockpos, EnumFacing enumfacing, float f0, float f1, float f2, int i0, EntityLivingBase entitylivingbase) {
        return this.P().a(a, BlockPistonBase.a(world, blockpos, entitylivingbase)).a(b, Boolean.valueOf(false));
    }

    public void a(World world, BlockPos blockpos, IBlockState iblockstate, EntityLivingBase entitylivingbase, ItemStack itemstack) {
        world.a(blockpos, iblockstate.a(a, BlockPistonBase.a(world, blockpos, entitylivingbase)), 2);
        if (itemstack.s()) {
            TileEntity tileentity = world.s(blockpos);

            if (tileentity instanceof TileEntityDispenser) {
                ((TileEntityDispenser) tileentity).a(itemstack.q());
            }
        }

    }

    public void b(World world, BlockPos blockpos, IBlockState iblockstate) {
        TileEntity tileentity = world.s(blockpos);

        if (tileentity instanceof TileEntityDispenser) {
            InventoryHelper.a(world, blockpos, (TileEntityDispenser) tileentity);
            world.e(blockpos, this);
        }

        super.b(world, blockpos, iblockstate);
    }

    public static IPosition a(IBlockSource iblocksource) {
        EnumFacing enumfacing = b(iblocksource.f());
        double d0 = iblocksource.a() + 0.7D * (double) enumfacing.g();
        double d1 = iblocksource.b() + 0.7D * (double) enumfacing.h();
        double d2 = iblocksource.c() + 0.7D * (double) enumfacing.i();

        return new PositionImpl(d0, d1, d2);
    }

    public static EnumFacing b(int i0) {
        return EnumFacing.a(i0 & 7);
    }

    public boolean N() {
        return true;
    }

    public int l(World world, BlockPos blockpos) {
        return Container.a(world.s(blockpos));
    }

    public int b() {
        return 3;
    }

    public IBlockState a(int i0) {
        return this.P().a(a, b(i0)).a(b, Boolean.valueOf((i0 & 8) > 0));
    }

    public int c(IBlockState iblockstate) {
        byte b0 = 0;
        int i0 = b0 | ((EnumFacing) iblockstate.b(a)).a();

        if (((Boolean) iblockstate.b(b)).booleanValue()) {
            i0 |= 8;
        }

        return i0;
    }

    protected BlockState e() {
        return new BlockState(this, new IProperty[]{a, b});
    }

}
