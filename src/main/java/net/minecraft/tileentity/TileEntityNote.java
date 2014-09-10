package net.minecraft.tileentity;

import net.canarymod.api.world.blocks.CanaryNoteBlock;
import net.canarymod.hook.world.NoteBlockPlayHook;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class TileEntityNote extends TileEntity {

    public byte a;
    public boolean i;

    public TileEntityNote() {
        this.complexBlock = new CanaryNoteBlock(this); // CanaryMod: wrap tile entity
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.a("note", this.a);
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.a = nbttagcompound.d("note");
        if (this.a < 0) {
            this.a = 0;
        }

        if (this.a > 24) {
            this.a = 24;
        }
    }

    public void a() {
        this.a = (byte) ((this.a + 1) % 25);
        this.e();
    }

    public void a(World world, int i0, int i1, int i2) {
        if (world.a(i0, i1 + 1, i2).o() == Material.a) {
            Material material = world.a(i0, i1 - 1, i2).o();
            byte b0 = 0;

            if (material == Material.e) {
                b0 = 1;
            }

            if (material == Material.p) {
                b0 = 2;
            }

            if (material == Material.s) {
                b0 = 3;
            }

            if (material == Material.d) {
                b0 = 4;
            }

            // CanaryMod: NoteBlockPlay
            NoteBlockPlayHook nbph = (NoteBlockPlayHook) new NoteBlockPlayHook(getCanaryNoteBlock()).call();
            if (!nbph.isCanceled()) {
                world.c(i0, i1, i2, Blocks.B, b0, this.a);
            }
            //
        }
    }

    // CanaryMod
    public CanaryNoteBlock getCanaryNoteBlock() {
        return (CanaryNoteBlock) complexBlock;
    }
}
