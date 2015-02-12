package net.canarymod.api.world.blocks.properties;

import net.minecraft.block.properties.PropertyBool;

/**
 * PropertyBool wrapper implementation
 *
 * @author Jason Jones (darkdiplomat)
 */
public class CanaryBlockBooleanProperty extends CanaryBlockProperty implements BlockBooleanProperty {

    protected CanaryBlockBooleanProperty(PropertyBool property) {
        super(property);
    }

    @Override
    public boolean canApply(Boolean value) {
        return super.canApply(value);
    }
}
