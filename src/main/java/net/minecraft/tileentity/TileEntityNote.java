package net.minecraft.tileentity;

import net.canarymod.api.world.blocks.CanaryNoteBlock;
import net.canarymod.hook.world.NoteBlockPlayHook;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class TileEntityNote extends TileEntity {

    public byte a;
    public boolean f;

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
        this.a = (byte)MathHelper.a(this.a, 0, 24);
    }

    public void b() {
        this.a = (byte)((this.a + 1) % 25);
        this.o_();
    }

    public void a(World world, BlockPos blockpos) {
        if (world.p(blockpos.a()).c().r() == Material.a) {
            Material material = world.p(blockpos.b()).c().r();
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
            NoteBlockPlayHook nbph = (NoteBlockPlayHook)new NoteBlockPlayHook(getCanaryNoteBlock()).call();
            if (!nbph.isCanceled()) {
                world.c(blockpos, Blocks.B, b0, this.a);
            }
            //
        }
    }

    // CanaryMod
    public CanaryNoteBlock getCanaryNoteBlock() {
        return (CanaryNoteBlock)complexBlock;
    }
}
