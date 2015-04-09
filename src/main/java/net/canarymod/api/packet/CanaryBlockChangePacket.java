package net.canarymod.api.packet;

import net.canarymod.Canary;
import net.canarymod.api.world.CanaryWorld;
import net.canarymod.api.world.blocks.Block;
import net.canarymod.api.world.blocks.BlockType;
import net.canarymod.api.world.position.BlockPosition;
import net.canarymod.api.world.position.Position;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.util.BlockPos;

/**
 * BlockChangePacket implementation
 *
 * @author Jason (darkdiplomat)
 */
public class CanaryBlockChangePacket extends CanaryPacket implements BlockChangePacket {
    // TODO: This whole class needs updated properly

    public CanaryBlockChangePacket(S23PacketBlockChange packet) {
        super(packet);
    }

    public CanaryBlockChangePacket(BlockType type, int meta, BlockPosition blockposition) {
        this(new S23PacketBlockChange(((CanaryWorld)Canary.getServer().getDefaultWorld()).getHandle(), blockposition.asNative()));
        this.setType(type);
        this.setData(meta);
    }

    public CanaryBlockChangePacket(Block block) {
        this(block.getType(), block.getData(), (BlockPosition)block.getPosition());
    }

    @Override
    public int getX() {
        return getPacket().a.n();
    }

    @Override
    public void setX(int x) {
        this.setPosition(x, getY(), getZ());
    }

    @Override
    public int getY() {
        return getPacket().a.o();
    }

    @Override
    public void setY(int y) {
        setPosition(getX(), y, getZ());
    }

    @Override
    public int getZ() {
        return getPacket().a.p();
    }

    @Override
    public void setZ(int z) {
        setPosition(getX(), getY(), z);
    }

    @Override
    public Position getPosition() {
        return new BlockPosition(getPacket().a);
    }

    @Override
    public void setPosition(Position position) {
        this.setPosition(position.getBlockX(), position.getBlockY(), position.getBlockZ());
    }

    public void setPosition(int x, int y, int z) {
        getPacket().a = new BlockPos(x, y, z);
    }

    @Override
    public BlockType getType() {
        return BlockType.fromIdAndData(this.getTypeId(), this.getData());
    }

    @Override
    public void setType(BlockType type) {
        this.setTypeId(type.getId());
        this.setData(type.getData());
    }

    @Override
    public int getTypeId() {
        return net.minecraft.block.Block.a(getPacket().b.c());
    }

    @Override
    public void setTypeId(int id) {
        int data = getData();
        getPacket().b = net.minecraft.block.Block.d(id).c().a(data);
    }

    @Override
    public int getData() {
        return getPacket().b.c().c(getPacket().b);
    }

    @Override
    public void setData(int data) {
        getPacket().b.c().a(data);
    }

    @Override
    public Block getBlock() {
        return null; //new CanaryBlock((short)getTypeId(), (short)getData(), getX(), getY(), getZ(), Canary.getServer().getDefaultWorld());
    }

    @Override
    public void setBlock(Block block) {
        this.setPosition(block.getPosition());
        this.setType(block.getType());
        this.setData(block.getData());
    }

    @Override
    public S23PacketBlockChange getPacket() {
        return (S23PacketBlockChange)packet;
    }
}
