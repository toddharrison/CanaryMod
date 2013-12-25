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
        this.a(iblocksource, BlockDispenser.b(iblocksource.h()));
        return itemstack1;
    }

    protected ItemStack b(IBlockSource iblocksource, ItemStack itemstack) {
        EnumFacing enumfacing = BlockDispenser.b(iblocksource.h());
        IPosition iposition = BlockDispenser.a(iblocksource);

        // CanaryMod: Dispense
        EntityItem temp = new EntityItem(iblocksource.k(), iposition.a(), iposition.b() - 0.3D, iposition.c(), itemstack);
        DispenseHook hook = (DispenseHook) new DispenseHook(((TileEntityDispenser) iblocksource.j()).getCanaryDispenser(), temp.getCanaryEntity()).call();
        if (!hook.isCanceled()) {
            ItemStack itemstack1 = itemstack.a(1);
            a(iblocksource.k(), itemstack1, 6, enumfacing, iposition);
        }
        temp.B(); // Clear the temp EntityItem
        //
        return itemstack;
    }

    public static void a(World world, ItemStack itemstack, int i0, EnumFacing enumfacing, IPosition iposition) {
        double d0 = iposition.a();
        double d1 = iposition.b();
        double d2 = iposition.c();
        EntityItem entityitem = new EntityItem(world, d0, d1 - 0.3D, d2, itemstack);
        double d3 = world.s.nextDouble() * 0.1D + 0.2D;

        entityitem.w = (double) enumfacing.c() * d3;
        entityitem.x = 0.20000000298023224D;
        entityitem.y = (double) enumfacing.e() * d3;
        entityitem.w += world.s.nextGaussian() * 0.007499999832361937D * (double) i0;
        entityitem.x += world.s.nextGaussian() * 0.007499999832361937D * (double) i0;
        entityitem.y += world.s.nextGaussian() * 0.007499999832361937D * (double) i0;
        world.d((Entity) entityitem);
    }

    protected void a(IBlockSource iblocksource) {
        iblocksource.k().c(1000, iblocksource.d(), iblocksource.e(), iblocksource.f(), 0);
    }

    protected void a(IBlockSource iblocksource, EnumFacing enumfacing) {
        iblocksource.k().c(2000, iblocksource.d(), iblocksource.e(), iblocksource.f(), this.a(enumfacing));
    }

    private int a(EnumFacing enumfacing) {
        return enumfacing.c() + 1 + (enumfacing.e() + 1) * 3;
    }
}
