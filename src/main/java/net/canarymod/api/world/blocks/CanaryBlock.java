package net.canarymod.api.world.blocks;

import com.google.common.collect.ImmutableMap;
import net.canarymod.NotYetImplementedException;
import net.canarymod.api.entity.living.humanoid.CanaryPlayer;
import net.canarymod.api.entity.living.humanoid.Player;
import net.canarymod.api.packet.BlockChangePacket;
import net.canarymod.api.packet.CanaryBlockChangePacket;
import net.canarymod.api.world.CanaryWorld;
import net.canarymod.api.world.World;
import net.canarymod.api.world.blocks.properties.BlockProperty;
import net.canarymod.api.world.position.BlockPosition;
import net.canarymod.api.world.position.Location;
import net.canarymod.api.world.position.Position;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

import java.util.Collection;
import java.util.Random;

public class CanaryBlock implements Block {
    private final static Random rndm = new Random(); // Passed to the idDropped method
    protected short data;
    protected BlockType type;
    protected World world;
    protected int x, y, z;
    protected BlockFace faceClicked;
    protected byte status = 0;

    private static String machineNameOfBlock(net.minecraft.block.Block nmsBlock) {
        return (String)net.minecraft.block.Block.c.c(nmsBlock);
    }

    public CanaryBlock(short typeId, short data, int x, int y, int z, World world) {
        this(BlockType.fromIdAndData(typeId, data), x, y, z, world);
    }

    public CanaryBlock(net.minecraft.block.Block nmsBlock, short data, int x, int y, int z, World world) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world;
        this.type = BlockType.fromStringAndData(machineNameOfBlock(nmsBlock), data);
        this.data = data;
    }

    public CanaryBlock(BlockType type, int x, int y, int z, World world) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world;
        this.type = type;
        this.data = type.getData();
    }

    public CanaryBlock(BlockType type, int data, int x, int y, int z, World world) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world;
        this.type = type;
        this.data = (short)data;
    }

    public CanaryBlock(BlockType type, int data, BlockPosition blockPosition, World world) {
        this(type, data, blockPosition.getBlockX(), blockPosition.getBlockY(), blockPosition.getBlockZ(), world);
    }

    @Override
    public short getTypeId() {
        if (type != null) {
            return type.getId();
        }
        return 0;
    }

    @Override
    public void setTypeId(short typeId) {
        this.type = BlockType.fromIdAndData(typeId, data);
    }

    @Override
    public BlockType getType() {
        return type;
    }

    @Override
    public void setType(BlockType type) {
        this.type = type;
        this.data = type.getData();
    }

    @Override
    public short getData() {
        return data;
    }

    @Override
    public void setData(short data) {
        this.data = data;
        this.type = BlockType.fromStringAndData(type.getMachineName(), data);
    }

    @Override
    public World getWorld() {
        return this.world;
    }

    @Override
    public void setWorld(World world) {
        this.world = world;
    }

    @Override
    public BlockFace getFaceClicked() {
        return faceClicked;
    }

    @Override
    public void setFaceClicked(BlockFace face) {
        faceClicked = face;
    }

    @Override
    public void update() {
        world.setBlock(this);
        world.markBlockNeedsUpdate(x, y, z);
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getZ() {
        return z;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public void setZ(int z) {
        this.z = z;
    }

    @Override
    public void setStatus(byte status) {
        this.status = status;
    }

    @Override
    public byte getStatus() {
        return status;
    }

    @Override
    public boolean isAir() {
        return BlockType.Air.equals(type);
    }

    @Override
    public BlockMaterial getBlockMaterial() {
        if (net.minecraft.block.Block.b(getType().getMachineName()) != null) {
            return net.minecraft.block.Block.b(getType().getMachineName()).r().getCanaryBlockMaterial();
        }
        return null;
    }

    @Override
    public Location getLocation() {
        return new Location(world, x, y, z, 0.0F, 0.0F);
    }

    @Override
    public Position getPosition() {
        return new BlockPosition(x, y, z);
    }

    @Override
    public Block getFacingBlock(BlockFace face) {
        switch (face) {
            case BOTTOM:
                return getRelative(0, -1, 0);

            case EAST:
                return getRelative(0, 0, -1);

            case NORTH:
                return getRelative(1, 0, 0);

            case SOUTH:
                return getRelative(-1, 0, 0);

            case TOP:
                return getRelative(0, 1, 0);

            case UNKNOWN:
                break;

            case WEST:
                return getRelative(0, 0, 1);

            default:
                break;
        }
        return null;
    }

    @Override
    public Block getRelative(int x, int y, int z) {
        return this.world.getBlockAt(getX() + x, getY() + y, getZ() + z);
    }

    @Override
    public int getIdDropped() {
        return net.minecraft.item.Item.b(getBlock().a(getBlock().P(), rndm, 0));
    }

    @Override
    public int getDamageDropped() {
        return net.minecraft.block.Block.b(getType().getMachineName()).a(getBlock().P());
    }

    @Override
    public int getQuantityDropped() {
        return net.minecraft.block.Block.b(getType().getMachineName()).a(rndm);
    }

    @Override
    public void dropBlockAsItem(boolean remove) {
        net.minecraft.block.Block.b(getType().getMachineName()).a(((CanaryWorld)getWorld()).getHandle(), new BlockPos(getX(), getY(), getZ()), getBlock().P(), 1.0F, 0);
        if (remove) {
            this.setTypeId((short)0);
            this.update();
        }
    }

    @Override
    public TileEntity getTileEntity() {
        return getWorld().getTileEntity(this);
    }

    @Override
    public boolean rightClick(Player player) {
        EnumFacing enumfacing = EnumFacing.UP;
        if (player != null) {
            double degrees = (player.getRotation() - 180) % 360;

            if (degrees < 0) {
                degrees += 360.0;
            }

            if (0 <= degrees && degrees < 33.75) {
                enumfacing = EnumFacing.NORTH;
            }
            else if (33.75 <= degrees && degrees < 123.75) {
                enumfacing = EnumFacing.EAST;
            }
            else if (123.75 <= degrees && degrees < 213.75) {
                enumfacing = EnumFacing.SOUTH;
            }
            else if (213.75 <= degrees && degrees < 303.75) {
                enumfacing = EnumFacing.WEST;
            }
            else if (303.75 <= degrees && degrees < 360.0) {
                enumfacing = EnumFacing.NORTH;
            }
        }

        return net.minecraft.block.Block.b(getType().getMachineName()).a(((CanaryWorld) getWorld()).getHandle(), new BlockPos(getX(), getY(), getZ()), getBlock().P(), player != null ? ((CanaryPlayer) player).getHandle() : null, enumfacing, 0, 0, 0); // last four parameters aren't even used by lever or button
    }

    public void sendUpdateToPlayers(Player... players) {
        for (Player player : players) {
            player.sendPacket(getBlockPacket());
        }
    }

    @Override
    public BlockChangePacket getBlockPacket() {
        return new CanaryBlockChangePacket(this);
    }

    @Override
    public Collection<BlockProperty> getPropertyKeys() {
        throw new NotYetImplementedException("BlockProperties are not yet implemented");
    }

    @Override
    public ImmutableMap<BlockProperty, Comparable> getProperties() {
        throw new NotYetImplementedException("BlockProperties are not yet implemented");
    }

    @Override
    public Comparable getValue(BlockProperty property) {
        throw new NotYetImplementedException("BlockProperties are not yet implemented");
    }

    @Override
    public void setPropertyValue(BlockProperty property, Comparable comparable) {
        throw new NotYetImplementedException("BlockProperties are not yet implemented");
    }

    @Override
    public String toString() {
        return String.format("Block[Type=%s, data=%d, x=%d, y=%d, z=%d, world=%s, dim=%s]", getType().getMachineName(), this.data, this.x, this.y, this.z, this.world.getName(), this.world.getType().getName());
    }

    /**
     * Tests the given object to see if it equals this object
     *
     * @param obj
     *         the object to test
     *
     * @return true if the two objects match
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CanaryBlock other = (CanaryBlock)obj;

        if (!this.getWorld().equals(other.getWorld())) {
            return false;
        }
        return this.x == other.getX() && this.y == other.getY() && this.z == other.getZ();
    }

    /**
     * Returns a semi-unique hashcode for this block
     *
     * @return hashcode
     */
    @Override
    public int hashCode() {
        int hash = 7;

        hash = 97 * hash + getType().getMachineName().hashCode();
        hash = 97 * hash + this.data;
        hash = 97 * hash + this.x;
        hash = 97 * hash + this.y;
        hash = 97 * hash + this.z;
        return hash;
    }
    
    /**
     * Convenience Method
     */
    public net.minecraft.block.Block getBlock() {
        return net.minecraft.block.Block.b(getType().getMachineName());
    }
    
}
