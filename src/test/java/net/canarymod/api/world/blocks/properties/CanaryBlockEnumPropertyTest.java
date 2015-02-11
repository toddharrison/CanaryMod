package net.canarymod.api.world.blocks.properties;

import net.canarymod.Canary;
import net.canarymod.CanaryMod;
import net.canarymod.api.DyeColor;
import net.canarymod.api.factory.CanaryFactory;
import net.canarymod.api.world.blocks.BlockFace;
import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.api.world.blocks.properties.helpers.StoneProperties;
import net.minecraft.init.Blocks;
import net.minecraft.init.Bootstrap;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.EnumFacing;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

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
        assertEquals("UP TOP MISMATCH", EnumFacing.UP, convertCanary(BlockFace.TOP, null));
        assertEquals("DOWN BOTTOM MISMATCH", EnumFacing.DOWN, convertCanary(BlockFace.BOTTOM, null));
        assertEquals("NORTH MISMATCH", EnumFacing.NORTH, convertCanary(BlockFace.NORTH, null));
        assertEquals("SOUTH MISMATCH", EnumFacing.SOUTH, convertCanary(BlockFace.SOUTH, null));
        assertEquals("EAST MISMATCH", EnumFacing.EAST, convertCanary(BlockFace.EAST, null));
        assertEquals("WEST MISMATCH", EnumFacing.WEST, convertCanary(BlockFace.WEST, null));
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
        assertEquals("WHITE MISMATCH", EnumDyeColor.WHITE, convertCanary(DyeColor.WHITE, null));
        assertEquals("ORANGE MISMATCH", EnumDyeColor.ORANGE, convertCanary(DyeColor.ORANGE, null));
        assertEquals("MAGENTA MISMATCH", EnumDyeColor.MAGENTA, convertCanary(DyeColor.MAGENTA, null));
        assertEquals("LIGHT BLUE MISMATCH", EnumDyeColor.LIGHT_BLUE, convertCanary(DyeColor.LIGHT_BLUE, null));
        assertEquals("YELLOW MISMATCH", EnumDyeColor.YELLOW, convertCanary(DyeColor.YELLOW, null));
        assertEquals("LIME MISMATCH", EnumDyeColor.LIME, convertCanary(DyeColor.LIME, null));
        assertEquals("PINK MISMATCH", EnumDyeColor.PINK, convertCanary(DyeColor.PINK, null));
        assertEquals("GRAY MISMATCH", EnumDyeColor.GRAY, convertCanary(DyeColor.GRAY, null));
        assertEquals("LIGHT GRAY MISMATCH", EnumDyeColor.SILVER, convertCanary(DyeColor.LIGHT_GRAY, null));
        assertEquals("CYAN MISMATCH", EnumDyeColor.CYAN, convertCanary(DyeColor.CYAN, null));
        assertEquals("PURPLE MISMATCH", EnumDyeColor.PURPLE, convertCanary(DyeColor.PURPLE, null));
        assertEquals("BLUE MISMATCH", EnumDyeColor.BLUE, convertCanary(DyeColor.BLUE, null));
        assertEquals("BROWN MISMATCH", EnumDyeColor.BROWN, convertCanary(DyeColor.BROWN, null));
        assertEquals("GREEN MISMATCH", EnumDyeColor.GREEN, convertCanary(DyeColor.GREEN, null));
        assertEquals("RED MISMATCH", EnumDyeColor.RED, convertCanary(DyeColor.RED, null));
        assertEquals("BLACK MISMATCH", EnumDyeColor.BLACK, convertCanary(DyeColor.BLACK, null));
    }

    @Test
    public void testStatePropertyConversion() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        Bootstrap.c(); // Need to run bootstrap before attempting to test any blocks
        enableFactory(); // darkdiplomat wizardry

        CanaryBlock testBlock = new CanaryBlock(Blocks.b.P());
        StoneProperties.applyVariant(testBlock, StoneProperties.Variant.ANDESITE);
        assertEquals(StoneProperties.Variant.ANDESITE, testBlock.getValue(StoneProperties.variant));
    }

    private static void enableFactory() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        Constructor<CanaryMod> cm = CanaryMod.class.getConstructor(Object.class);
        cm.setAccessible(true);
        CanaryMod canaryMod = cm.newInstance(new Object());

        Field factory = Canary.class.getDeclaredField("factory");
        factory.setAccessible(true);
        factory.set(canaryMod, new CanaryFactory());
        Field instance = Canary.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(canaryMod, canaryMod);
    }
}
