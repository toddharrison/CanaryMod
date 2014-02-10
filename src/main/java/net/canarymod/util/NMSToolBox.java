package net.canarymod.util;

/**
 * @author Jason (darkdiplomat)
 */
public class NMSToolBox {

    /**
     * Converts a byte array of Block Ids into an array of native Blocks
     *
     * @param ids
     *         the byte array of ids
     *
     * @return native Block array
     */
    public static net.minecraft.block.Block[] blockIdsToBlocks(int[] ids) {
        net.minecraft.block.Block[] blocks = new net.minecraft.block.Block[ids.length];
        for (int index = 0; index < ids.length; index++) {
            blocks[index] = net.minecraft.block.Block.e(ids[index]);
        }
        return blocks;
    }
}
