package net.canarymod.api.world.blocks.properties;

import net.minecraft.block.properties.PropertyDirection;

/**
 * PropertyDirection wrapper implementation
 *
 * @author Jason Jones (darkdiplomat)
 */
public class CanaryBlockDirectionProperty extends CanaryBlockEnumProperty implements BlockDirectionProperty {

    protected CanaryBlockDirectionProperty(PropertyDirection property) {
        super(property);
    }
}
