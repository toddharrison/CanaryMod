package net.canarymod.api.world.blocks.properties;

import net.minecraft.block.properties.*;

import java.util.Collection;

/**
 * Block IProperty/PropertyHelper wrapper implementation
 *
 * @author Jason Jones (darkdiplomat)
 */
public class CanaryBlockProperty implements BlockProperty {
    private final IProperty property;

    protected CanaryBlockProperty(IProperty property) {
        this.property = property;
    }

    @Override
    public String getName() {
        return property.a();
    }

    @Override
    public Collection<?> getAllowedValues() {
        return property.c();
    }

    @Override
    public Class getValueClass() {
        return property.b();
    }

    @Override
    public String getName(Comparable comparable) {
        return property.a(comparable);
    }

    public IProperty getNative() {
        return property;
    }

    public static CanaryBlockProperty wrapAs(IProperty iproperty) {
        if (iproperty instanceof PropertyBool) {
            return new CanaryBlockBooleanProperty((PropertyBool) iproperty);
        }
        else if (iproperty instanceof PropertyDirection) {
            return new CanaryBlockDirectionProperty((PropertyDirection) iproperty);
        }
        else if (iproperty instanceof PropertyEnum) {
            return new CanaryBlockEnumProperty((PropertyEnum) iproperty);
        }
        else if (iproperty instanceof PropertyInteger) {
            return new CanaryBlockProperty((PropertyInteger) iproperty);
        }
        return new CanaryBlockProperty(iproperty); // Unknown instance
    }
}
