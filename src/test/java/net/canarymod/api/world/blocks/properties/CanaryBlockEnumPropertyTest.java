package net.canarymod.api.world.blocks.properties;

import net.canarymod.CanaryModTest;
import net.canarymod.api.DyeColor;
import net.canarymod.api.world.blocks.BlockFace;
import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.api.world.blocks.properties.helpers.StoneProperties;
import net.minecraft.init.Blocks;
import net.minecraft.init.Bootstrap;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.EnumFacing;
import org.junit.Test;

import static net.canarymod.api.world.blocks.properties.CanaryBlockEnumProperty.convertCanary;
import static net.canarymod.api.world.blocks.properties.CanaryBlockEnumProperty.convertNative;
import static org.junit.Assert.assertEquals;

/**
 * PropertyEnum wrapper testing
 *
 * @author Jason Jones (darkdiplomat)
 */
public class CanaryBlockEnumPropertyTest {

    @Test
    public void testBlockFaceMapping() {
        assertEquals("UP TOP MISMATCH", BlockFace.TOP, convertNative(EnumFacing.UP));
        assertEquals("DOWN BOTTOM MISMATCH", BlockFace.BOTTOM, convertNative(EnumFacing.DOWN));
        assertEquals("NORTH MISMATCH", BlockFace.NORTH, convertNative(EnumFacing.NORTH));
        assertEquals("SOUTH MISMATCH", BlockFace.SOUTH, convertNative(EnumFacing.SOUTH));
        assertEquals("EAST MISMATCH", BlockFace.EAST, convertNative(EnumFacing.EAST));
        assertEquals("WEST MISMATCH", BlockFace.WEST, convertNative(EnumFacing.WEST));
    }

    @Test
    public void testBlockFaceMappingInverse() {
        assertEquals("UP TOP MISMATCH", EnumFacing.UP, convertCanary(BlockFace.TOP));
        assertEquals("DOWN BOTTOM MISMATCH", EnumFacing.DOWN, convertCanary(BlockFace.BOTTOM));
        assertEquals("NORTH MISMATCH", EnumFacing.NORTH, convertCanary(BlockFace.NORTH));
        assertEquals("SOUTH MISMATCH", EnumFacing.SOUTH, convertCanary(BlockFace.SOUTH));
        assertEquals("EAST MISMATCH", EnumFacing.EAST, convertCanary(BlockFace.EAST));
        assertEquals("WEST MISMATCH", EnumFacing.WEST, convertCanary(BlockFace.WEST));
    }

    @Test
    public void testDyeColorMapping() {
        assertEquals("WHITE MISMATCH", DyeColor.WHITE, convertNative(EnumDyeColor.WHITE));
        assertEquals("ORANGE MISMATCH", DyeColor.ORANGE, convertNative(EnumDyeColor.ORANGE));
        assertEquals("MAGENTA MISMATCH", DyeColor.MAGENTA, convertNative(EnumDyeColor.MAGENTA));
        assertEquals("LIGHT BLUE MISMATCH", DyeColor.LIGHT_BLUE, convertNative(EnumDyeColor.LIGHT_BLUE));
        assertEquals("YELLOW MISMATCH", DyeColor.YELLOW, convertNative(EnumDyeColor.YELLOW));
        assertEquals("LIME MISMATCH", DyeColor.LIME, convertNative(EnumDyeColor.LIME));
        assertEquals("PINK MISMATCH", DyeColor.PINK, convertNative(EnumDyeColor.PINK));
        assertEquals("GRAY MISMATCH", DyeColor.GRAY, convertNative(EnumDyeColor.GRAY));
        assertEquals("LIGHT GRAY MISMATCH", DyeColor.LIGHT_GRAY, convertNative(EnumDyeColor.SILVER));
        assertEquals("CYAN MISMATCH", DyeColor.CYAN, convertNative(EnumDyeColor.CYAN));
        assertEquals("PURPLE MISMATCH", DyeColor.PURPLE, convertNative(EnumDyeColor.PURPLE));
        assertEquals("BLUE MISMATCH", DyeColor.BLUE, convertNative(EnumDyeColor.BLUE));
        assertEquals("BROWN MISMATCH", DyeColor.BROWN, convertNative(EnumDyeColor.BROWN));
        assertEquals("GREEN MISMATCH", DyeColor.GREEN, convertNative(EnumDyeColor.GREEN));
        assertEquals("RED MISMATCH", DyeColor.RED, convertNative(EnumDyeColor.RED));
        assertEquals("BLACK MISMATCH", DyeColor.BLACK, convertNative(EnumDyeColor.BLACK));
    }

    @Test
    public void testDyeColorMappingInverse() {
        assertEquals("WHITE MISMATCH", EnumDyeColor.WHITE, convertCanary(DyeColor.WHITE));
        assertEquals("ORANGE MISMATCH", EnumDyeColor.ORANGE, convertCanary(DyeColor.ORANGE));
        assertEquals("MAGENTA MISMATCH", EnumDyeColor.MAGENTA, convertCanary(DyeColor.MAGENTA));
        assertEquals("LIGHT BLUE MISMATCH", EnumDyeColor.LIGHT_BLUE, convertCanary(DyeColor.LIGHT_BLUE));
        assertEquals("YELLOW MISMATCH", EnumDyeColor.YELLOW, convertCanary(DyeColor.YELLOW));
        assertEquals("LIME MISMATCH", EnumDyeColor.LIME, convertCanary(DyeColor.LIME));
        assertEquals("PINK MISMATCH", EnumDyeColor.PINK, convertCanary(DyeColor.PINK));
        assertEquals("GRAY MISMATCH", EnumDyeColor.GRAY, convertCanary(DyeColor.GRAY));
        assertEquals("LIGHT GRAY MISMATCH", EnumDyeColor.SILVER, convertCanary(DyeColor.LIGHT_GRAY));
        assertEquals("CYAN MISMATCH", EnumDyeColor.CYAN, convertCanary(DyeColor.CYAN));
        assertEquals("PURPLE MISMATCH", EnumDyeColor.PURPLE, convertCanary(DyeColor.PURPLE));
        assertEquals("BLUE MISMATCH", EnumDyeColor.BLUE, convertCanary(DyeColor.BLUE));
        assertEquals("BROWN MISMATCH", EnumDyeColor.BROWN, convertCanary(DyeColor.BROWN));
        assertEquals("GREEN MISMATCH", EnumDyeColor.GREEN, convertCanary(DyeColor.GREEN));
        assertEquals("RED MISMATCH", EnumDyeColor.RED, convertCanary(DyeColor.RED));
        assertEquals("BLACK MISMATCH", EnumDyeColor.BLACK, convertCanary(DyeColor.BLACK));
    }

    @Test
    public void testStatePropertyConversion() throws Exception {
        Bootstrap.c(); // Need to run bootstrap before attempting to test any blocks
        CanaryModTest.enableFactories(); // darkdiplomat wizardry

        CanaryBlock testBlock = new CanaryBlock(Blocks.b.P());
        StoneProperties.applyVariant(testBlock, StoneProperties.Variant.ANDESITE);
        assertEquals(StoneProperties.Variant.ANDESITE, testBlock.getValue(StoneProperties.variant));
    }
}
