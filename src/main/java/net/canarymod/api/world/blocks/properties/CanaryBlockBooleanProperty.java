package net.canarymod.api.world.blocks.properties;

import net.minecraft.block.properties.PropertyBool;

/**
 * Created by darkdiplomat on 10/7/14.
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
