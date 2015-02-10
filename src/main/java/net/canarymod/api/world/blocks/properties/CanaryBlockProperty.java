package net.canarymod.api.world.blocks.properties;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;

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
    public Collection<Comparable> getAllowedValues() {
        return property.c();
    }

    @Override
    public Class getValueClass() {
        return property.b();
    }

    public boolean canApply(Comparable comparable) {
        return getAllowedValues().contains(comparable);
    }

    public IProperty getNative() {
        return property;
    }

    public static CanaryBlockProperty wrapAs(IProperty iproperty) {
        if (iproperty instanceof PropertyBool) {
            return new CanaryBlockBooleanProperty((PropertyBool)iproperty);
        }
        else if (iproperty instanceof PropertyDirection) { // Direction should always be before enum
            return new CanaryBlockDirectionProperty((PropertyDirection)iproperty);
        }
        else if (iproperty instanceof PropertyEnum) {
            return new CanaryBlockEnumProperty((PropertyEnum)iproperty);
        }
        else if (iproperty instanceof PropertyInteger) {
            return new CanaryBlockIntegerProperty((PropertyInteger)iproperty);
        }
        return new CanaryBlockProperty(iproperty); // Unknown instance
    }
}
