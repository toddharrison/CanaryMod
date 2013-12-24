package net.minecraft.tileentity;

import net.canarymod.api.world.blocks.CanaryCommandBlock;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

public class TileEntityCommandBlock extends TileEntity implements ICommandSender {

    private final CommandBlockLogic a = new CommandBlockLogic() {

        public ChunkCoordinates f_() {
            return new ChunkCoordinates(TileEntityCommandBlock.this.c, TileEntityCommandBlock.this.d, TileEntityCommandBlock.this.e);
        }

        public World d() {
            return TileEntityCommandBlock.this.w();
        }

        public void a(String var1) {
            super.a(var1);
            TileEntityCommandBlock.this.e();
        }

        public void e() {
            TileEntityCommandBlock.this.w().g(TileEntityCommandBlock.this.c, TileEntityCommandBlock.this.d, TileEntityCommandBlock.this.e);
        }
    };

    public TileEntityCommandBlock() {
        this.complexBlock = new CanaryCommandBlock(this); // CanaryMod: wrap tile entity
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        this.a.a(nbttagcompound);
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.a.b(nbttagcompound);
    }

    public Packet m() {
        NBTTagCompound nbttagcompound = new NBTTagCompound();

        this.b(nbttagcompound);
        return new S35PacketUpdateTileEntity(this.c, this.d, this.e, 2, nbttagcompound);
    }

    public CommandBlockLogic a() {
        return this.a;
    }

    // CanaryMod
    public CanaryCommandBlock getCanaryCommandBlock() {
        return (CanaryCommandBlock) complexBlock;
    }
}
