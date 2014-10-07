package net.canarymod.api.attributes;

/**
 * @author Jason (darkdiplomat)
 */
public class CanaryRangedAttribute extends CanaryAttribute implements RangedAttribute {

    public CanaryRangedAttribute(net.minecraft.entity.ai.attributes.RangedAttribute rangedAttribute) {
        super(rangedAttribute);
    }

    @Override
    public RangedAttribute setDescription(String description) {
        return (RangedAttribute)getNative().a(description).getWrapper();
    }

    @Override
    public String getDescription() {
        return getNative().g();
    }

    @Override
    public double setValue(double value) {
        return getNative().a(value);
    }

    @Override
    public double getMaxValue() {
        return getNative().getMaxValue();
    }

    @Override
    public double getMinValue() {
        return getNative().getMinValue();
    }

    public net.minecraft.entity.ai.attributes.RangedAttribute getNative() {
        return (net.minecraft.entity.ai.attributes.RangedAttribute)super.getNative();
    }
}
