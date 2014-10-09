package net.canarymod.api.world.blocks.properties;

import net.minecraft.block.properties.PropertyInteger;

/**
 * PropertyInteger wrapper
 *
 * @author Jason Jones (darkdiplomat)
 */
public class CanaryBlockIntegerProperty extends CanaryBlockProperty implements BlockIntegerProperty {

    protected CanaryBlockIntegerProperty(PropertyInteger property) {
        super(property);
    }

    @Override
    public boolean canApply(Integer value) {
        return super.canApply(value);
    }
}
