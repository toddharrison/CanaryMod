package net.minecraft.dispenser;

import net.canarymod.hook.world.DispenseHook;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;


public abstract class BehaviorProjectileDispense extends BehaviorDefaultDispenseItem {

    public ItemStack b(IBlockSource iblocksource, ItemStack itemstack) {
        World world = iblocksource.i();
        IPosition iposition = BlockDispenser.a(iblocksource);
        EnumFacing enumfacing = BlockDispenser.b(iblocksource.f());
        IProjectile iprojectile = this.a(world, iposition);

        iprojectile.c((double) enumfacing.g(), (double) ((float) enumfacing.h() + 0.1F), (double) enumfacing.i(), this.b(), this.a());
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
        iblocksource.i().b(1002, iblocksource.d(), 0);
    }

    protected abstract IProjectile a(World world, IPosition iposition);

    protected float a() {
        return 6.0F;
    }

    protected float b() {
        return 1.1F;
    }
}
