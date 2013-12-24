package net.minecraft.tileentity;

import net.canarymod.api.world.blocks.CanaryComparator;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityComparator extends TileEntity {

    private int a;

    public TileEntityComparator() {
        this.complexBlock = new CanaryComparator(this); // CanaryMod: wrap tile entity
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("OutputSignal", this.a);
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.a = nbttagcompound.f("OutputSignal");
    }

    public int a() {
        return this.a;
    }

    public void a(int i0) {
        this.a = i0;
    }

    // CanaryMod
    public CanaryComparator getCanaryComparator() {
        return (CanaryComparator) complexBlock;
    }
}
