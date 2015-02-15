package net.canarymod;

import java.util.ArrayList;
import net.canarymod.api.entity.living.humanoid.Player;
import net.canarymod.api.packet.CanaryPacket;
import net.canarymod.api.world.CanaryChunk;
import net.canarymod.api.world.CanaryWorld;
import net.canarymod.api.world.World;
import net.canarymod.api.world.blocks.Block;
import net.canarymod.api.world.blocks.BlockFace;
import net.canarymod.api.world.blocks.BlockType;
import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.api.world.blocks.properties.helpers.PortalProperties;
import net.canarymod.api.world.position.BlockPosition;
import net.canarymod.api.world.position.Position;
import net.canarymod.hook.world.PortalDestroyHook;
import net.canarymod.tasks.ServerTask;
import net.canarymod.tasks.ServerTaskManager;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.util.BlockPos;

/**
 * Portal Reconstruct Job
 * <p/>
 * Task for delaying the rebuilding of a Portal so the PortalDestroyHook isn't spammed
 *
 * @author YLivay (Phillip)
 * @author Jason (darkdiplomat)
 */
public final class PortalReconstructJob extends ServerTask {
    private ArrayList<Integer[]> portalBlocks;
    private World world;
    private boolean xAxis;

    public PortalReconstructJob(World world, int portalX, int portalY, int portalZ, boolean portalXAxis) {
        super(Canary.instance(), 1, false);
        portalBlocks = new ArrayList<Integer[]>();
        this.world = world;

        xAxis = portalXAxis;
        int portalXOffset = portalXAxis ? 1 : 0;
        int portalZOffset = 1 - portalXOffset;
        int portalId = BlockType.Portal.getId();
        int obsidianId = BlockType.Obsidian.getId();

        portalBlocks.add(new Integer[]{ portalX, portalY, portalZ, portalId });
        portalBlocks.add(new Integer[]{ portalX + portalXOffset, portalY, portalZ + portalZOffset, portalId });
        portalBlocks.add(new Integer[]{ portalX, portalY - 1, portalZ, portalId });
        portalBlocks.add(new Integer[]{ portalX + portalXOffset, portalY - 1, portalZ + portalZOffset, portalId });
        portalBlocks.add(new Integer[]{ portalX, portalY - 2, portalZ, portalId });
        portalBlocks.add(new Integer[]{ portalX + portalXOffset, portalY - 2, portalZ + portalZOffset, portalId });

        portalBlocks.add(new Integer[]{ portalX, portalY + 1, portalZ, obsidianId, 0 });
        portalBlocks.add(new Integer[]{ portalX + portalXOffset, portalY + 1, portalZ + portalZOffset, obsidianId, 0 });
        portalBlocks.add(new Integer[]{ portalX + portalXOffset * 2, portalY, portalZ + portalZOffset * 2, obsidianId, 0 });
        portalBlocks.add(new Integer[]{ portalX + portalXOffset * 2, portalY - 1, portalZ + portalZOffset * 2, obsidianId, 0 });
        portalBlocks.add(new Integer[]{ portalX + portalXOffset * 2, portalY - 2, portalZ + portalZOffset * 2, obsidianId, 0 });
        portalBlocks.add(new Integer[]{ portalX - portalXOffset, portalY, portalZ - portalZOffset, obsidianId, 0 });
        portalBlocks.add(new Integer[]{ portalX - portalXOffset, portalY - 1, portalZ - portalZOffset, obsidianId, 0 });
        portalBlocks.add(new Integer[]{ portalX - portalXOffset, portalY - 2, portalZ - portalZOffset, obsidianId, 0 });
        portalBlocks.add(new Integer[]{ portalX, portalY - 3, portalZ, obsidianId, 0 });
        portalBlocks.add(new Integer[]{ portalX + portalXOffset, portalY - 3, portalZ + portalZOffset, obsidianId, 0 });
    }

    @Override
    public final void run() {
        for (Integer[] frameCoord : portalBlocks) {
            world.getChunk(frameCoord[0] >> 4, frameCoord[2] >> 4).setBlockTypeAt(frameCoord[0] & 15, frameCoord[1], frameCoord[2] & 15, frameCoord[3]);
            Block block = ((CanaryChunk) world.getChunk(frameCoord[0] >> 4, frameCoord[2] >> 4)).getBlockAt(frameCoord[0] & 15, frameCoord[1], frameCoord[2] & 15);

            if(!xAxis && BlockType.Portal.getId() == frameCoord[3]){ // Correct Portal Axis
                PortalProperties.applyAxis(block, BlockFace.Axis.Z);
                ((CanaryChunk) world.getChunk(frameCoord[0] >> 4, frameCoord[2] >> 4)).setBlockAt(block, frameCoord[0] & 15, frameCoord[1], frameCoord[2] & 15);
            }

            for (Player player : world.getPlayerList()) {
                player.sendPacket(new CanaryPacket(new S23PacketBlockChange(((CanaryWorld) world).getHandle(), new BlockPos(frameCoord[0], frameCoord[1], frameCoord[2]))));
            }
        }
    }

    public static void doPortalCheck(net.minecraft.world.chunk.Chunk chunk, int x, int y, int z){
        CanaryChunk canaryChunk = chunk.getCanaryChunk();
        int portalPointX = canaryChunk.getX() * 16 + x;
        int portalPointY = y;
        int portalPointZ = canaryChunk.getZ() * 16 + z;

        net.canarymod.api.world.World world = chunk.getCanaryChunk().getDimension();
        if (world != null && world.getBlockAt(portalPointX, portalPointY, portalPointZ).getType().equals(BlockType.Portal)) {
            // These will be equal 1 if the portal is defined on their axis and 0 if not.
            int portalXOffset = (canaryChunk.getDimension().getBlockAt(portalPointX - 1, portalPointY, portalPointZ).getType().equals(BlockType.Portal) || canaryChunk.getDimension().getBlockAt(portalPointX + 1, portalPointY, portalPointZ).getType().equals(BlockType.Portal)) ? 1 : 0;
            int portalZOffset = (canaryChunk.getDimension().getBlockAt(portalPointX, portalPointY, portalPointZ - 1).getType().equals(BlockType.Portal) || canaryChunk.getDimension().getBlockAt(portalPointX, portalPointY, portalPointZ + 1).getType().equals(BlockType.Portal)) ? 1 : 0;

            // If the portal is either x aligned or z aligned but not both (has neighbor portal in x or z plane but not both)
            if (portalXOffset != portalZOffset) {
                // Get the edge of the portal.
                int portalX = portalPointX - ((canaryChunk.getDimension().getBlockAt(portalPointX - 1, portalPointY, portalPointZ).getType().equals(BlockType.Portal)) ? 1 : 0);
                int portalZ = portalPointZ - ((canaryChunk.getDimension().getBlockAt(portalPointX, portalPointY, portalPointZ - 1).getType().equals(BlockType.Portal)) ? 1 : 0);
                int portalY = portalPointY;

                while (canaryChunk.getDimension().getBlockAt(portalX, ++portalY, portalZ).getType().equals(BlockType.Portal)) {
                    ;
                }
                portalY -= 1;
                // Scan the portal and see if its still all there (2x3 formation)
                boolean completePortal = true;
                CanaryBlock[][] portalBlocks = new CanaryBlock[3][2];

                for (int vertical = 0; vertical < 3 && completePortal; vertical += 1) {
                    for (int horizontal = 0; horizontal < 2 && completePortal; horizontal += 1) {
                        portalBlocks[vertical][horizontal] = (CanaryBlock) canaryChunk.getDimension().getBlockAt(portalX + horizontal * portalXOffset, portalY - vertical, portalZ + horizontal * portalZOffset);
                        if (!portalBlocks[vertical][horizontal].getType().equals(BlockType.Portal)) {
                            completePortal = false;
                        }
                    }
                }
                if (completePortal == true) {
                    // CanaryMod: PortalDestroy
                    PortalDestroyHook hook = (PortalDestroyHook) new PortalDestroyHook(portalBlocks, new Position(portalPointX, portalPointY, portalPointZ), world).call();
                    if (hook.isCanceled()) {// Hook canceled = don't destroy the portal.
                        // in that case we need to reconstruct the portal's frame to make the portal valid.
                        // Problem is we don't want to reconstruct it right away because more blocks might be deleted (for example on explosion).
                        // In order to avoid spamming the hook for each destroyed block, I'm queuing the reconstruction of the portal instead.
                        ServerTaskManager.addTask(new PortalReconstructJob(world, portalX, portalY, portalZ, (portalXOffset == 1)));
                    }
                }
            }
        }
    }
}
