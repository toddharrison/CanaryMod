package net.minecraft.dispenser;

import net.canarymod.hook.world.DispenseHook;
import net.minecraft.block.BlockDispenser;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;


public class BehaviorDefaultDispenseItem implements IBehaviorDispenseItem {

    public final ItemStack a(IBlockSource iblocksource, ItemStack itemstack) {
        ItemStack itemstack1 = this.b(iblocksource, itemstack);

        this.a(iblocksource);
        this.a(iblocksource, BlockDispenser.b(iblocksource.f()));
        return itemstack1;
    }

    protected ItemStack b(IBlockSource iblocksource, ItemStack itemstack) {
        EnumFacing enumfacing = BlockDispenser.b(iblocksource.f());
        IPosition iposition = BlockDispenser.a(iblocksource);

        // CanaryMod: Dispense
        EntityItem temp = new EntityItem(iblocksource.i(), iposition.a(), iposition.b() - 0.3D, iposition.c(), itemstack);
        DispenseHook hook = (DispenseHook) new DispenseHook(((TileEntityDispenser) iblocksource.h()).getCanaryDispenser(), temp.getCanaryEntity()).call();
        if (!hook.isCanceled()) {
            ItemStack itemstack1 = itemstack.a(1);
            a(iblocksource.i(), itemstack1, 6, enumfacing, iposition);
        }
        temp.J(); // Clear the temp EntityItem
        //
        return itemstack;
    }

    public static void a(World world, ItemStack itemstack, int i0, EnumFacing enumfacing, IPosition iposition) {
        double d0 = iposition.a();
        double d1 = iposition.b();
        double d2 = iposition.c();

        if (enumfacing.k() == EnumFacing.Axis.Y) {
            d1 -= 0.125D;
        }
        else {
            d1 -= 0.15625D;
        }

        EntityItem entityitem = new EntityItem(world, d0, d1, d2, itemstack);
        double d3 = world.s.nextDouble() * 0.1D + 0.2D;

        entityitem.v = (double) enumfacing.g() * d3;
        entityitem.w = 0.20000000298023224D;
        entityitem.x = (double) enumfacing.i() * d3;
        entityitem.v += world.s.nextGaussian() * 0.007499999832361937D * (double) i0;
        entityitem.w += world.s.nextGaussian() * 0.007499999832361937D * (double) i0;
        entityitem.x += world.s.nextGaussian() * 0.007499999832361937D * (double) i0;
        world.d((Entity) entityitem);
    }

    protected void a(IBlockSource iblocksource) {
        iblocksource.i().b(1000, iblocksource.d(), 0);
    }

    protected void a(IBlockSource iblocksource, EnumFacing enumfacing) {
        iblocksource.i().b(2000, iblocksource.d(), this.a(enumfacing));
    }

    private int a(EnumFacing enumfacing) {
        return enumfacing.g() + 1 + (enumfacing.i() + 1) * 3;
    }
}
