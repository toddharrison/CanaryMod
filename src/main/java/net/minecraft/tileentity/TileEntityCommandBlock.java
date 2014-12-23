package net.minecraft.tileentity;

import net.canarymod.api.world.blocks.CanaryCommandBlock;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class TileEntityCommandBlock extends TileEntity {

    private final CommandBlockLogic a = new CommandBlockLogic() {

        public BlockPos c() {
            return TileEntityCommandBlock.this.c;
        }

        public Vec3 d() {
            return new Vec3((double)TileEntityCommandBlock.this.c.n() + 0.5D, (double)TileEntityCommandBlock.this.c.o() + 0.5D, (double)TileEntityCommandBlock.this.c.p() + 0.5D);
        }

        public World e() {
            return TileEntityCommandBlock.this.z();
        }

        public void a(String p_a_1_) {
            super.a(p_a_1_);
            TileEntityCommandBlock.this.o_();
        }

        public void h() {
            TileEntityCommandBlock.this.z().h(TileEntityCommandBlock.this.c);
        }

        public Entity f() {
            return null;
        }

        // CanaryMod: Add method to get CommandBlock reference (Can be either CanaryCommandBlock or CanaryCommandBlockMinecart)
        public net.canarymod.api.CommandBlockLogic getReference() {
            return TileEntityCommandBlock.this.getCanaryCommandBlock();
        }
        //
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

    public Packet x_() {
        NBTTagCompound nbttagcompound = new NBTTagCompound();

        this.b(nbttagcompound);
        return new S35PacketUpdateTileEntity(this.c, 2, nbttagcompound);
    }

    public CommandBlockLogic b() {
        return this.a;
    }

    public CommandResultStats c() {
        return this.a.n();
    }

    // CanaryMod
    public CanaryCommandBlock getCanaryCommandBlock() {
        return (CanaryCommandBlock)complexBlock;
    }

    public CommandBlockLogic getLogic() {
        return this.a;
    }
    //
}
