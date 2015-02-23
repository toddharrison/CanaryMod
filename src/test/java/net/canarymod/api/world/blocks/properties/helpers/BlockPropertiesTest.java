package net.canarymod.api.world.blocks.properties.helpers;

import net.canarymod.CanaryModTest;
import net.canarymod.api.world.blocks.BlockFace;
import net.canarymod.api.world.blocks.CanaryBlock;
import net.minecraft.block.Block;
import net.minecraft.init.Bootstrap;
import org.junit.Test;

import static net.canarymod.api.world.blocks.BlockType.Anvil;
import static net.canarymod.api.world.blocks.BlockType.Bed;
import static net.canarymod.api.world.blocks.BlockType.StandingBanner;
import static net.canarymod.api.world.blocks.BlockType.Torch;
import static net.canarymod.api.world.blocks.BlockType.WallBanner;

/**
 * Block Properties helpers testing (Since we need to have Minecraft native code to run these)
 *
 * @author Jason Jones (darkdiplomat)
 */
public class BlockPropertiesTest {

    static {
        // Initialize
        Bootstrap.c(); // Need to run bootstrap before attempting to test any blocks
        try {
            CanaryModTest.enableFactories(); // darkdiplomat wizardry
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testAnvilProperties() {
        CanaryBlock anvil = new CanaryBlock(Block.b(Anvil.getMachineName()).P());
        AnvilProperties.applyDamage(anvil, 0);
        AnvilProperties.applyFacing(anvil, BlockFace.NORTH);
    }

    @Test
    public void testBannerProperties() {
        CanaryBlock banner = new CanaryBlock(Block.b(StandingBanner.getMachineName()).P());
        BannerProperties.applyRotation(banner, 0);
        banner = new CanaryBlock(Block.b(WallBanner.getMachineName()).P());
        BannerProperties.applyFacing(banner, BlockFace.NORTH);
    }

    @Test
    public void testBedProperties() {
        CanaryBlock bed = new CanaryBlock(Block.b(Bed.getMachineName()).P());
        BedProperties.applyOccupided(bed, false);
        BedProperties.applyPart(bed, BedProperties.Half.FOOT);
    }

    @Test
    public void testBrewingStandProperties() {

    }

    @Test
    public void testTorchProperties() {
        CanaryBlock torch = new CanaryBlock(Block.b(Torch.getMachineName()).P());
        TorchProperties.applyFacing(torch, BlockFace.NORTH);
    }
}
