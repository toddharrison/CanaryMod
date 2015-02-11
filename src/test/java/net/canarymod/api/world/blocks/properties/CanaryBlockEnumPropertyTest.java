package net.canarymod.api.world.blocks.properties;

import net.canarymod.api.DyeColor;
import net.canarymod.api.world.blocks.BlockFace;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.EnumFacing;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * PropertyEnum wrapper testing
 *
 * @author Jason Jones (darkdiplomat)
 */
public class CanaryBlockEnumPropertyTest {

    @Test
    public void testBiMap() {
        assertEquals("Dye color failed", EnumDyeColor.BLACK, CanaryBlockEnumProperty.convertCanary(DyeColor.BLACK, null));
        assertEquals("Block face failed", BlockFace.TOP, CanaryBlockEnumProperty.convertNative(EnumFacing.UP));
    }
}
