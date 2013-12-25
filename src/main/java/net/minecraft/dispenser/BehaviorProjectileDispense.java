package net.minecraft.dispenser;

import net.canarymod.hook.world.DispenseHook;
import net.minecraft.block.BlockDispenser;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public abstract class BehaviorProjectileDispense extends BehaviorDefaultDispenseItem {

    public ItemStack b(IBlockSource iblocksource, ItemStack itemstack) {
        World world = iblocksource.k();
        IPosition iposition = BlockDispenser.a(iblocksource);
        EnumFacing enumfacing = BlockDispenser.b(iblocksource.h());
        IProjectile iprojectile = this.a(world, iposition);

        iprojectile.c((double) enumfacing.c(), (double) ((float) enumfacing.d() + 0.1F), (double) enumfacing.e(), this.b(), this.a());
        // CanaryMod: Dispense
        DispenseHook hook = (DispenseHook) new DispenseHook(((TileEntityDispenser) iblocksource.j()).getCanaryDispenser(), ((Entity) iprojectile).getCanaryEntity()).call();
        if (!hook.isCanceled()) {
            world.d((Entity) iprojectile);
            itemstack.a(1);
        }
        //
        return itemstack;
    }

    protected void a(IBlockSource iblocksource) {
        iblocksource.k().c(1002, iblocksource.d(), iblocksource.e(), iblocksource.f(), 0);
    }

    protected abstract IProjectile a(World world, IPosition iposition);

    protected float a() {
        return 6.0F;
    }

    protected float b() {
        return 1.1F;
    }
}
