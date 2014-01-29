package net.canarymod.api.attributes;

import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;

import java.util.UUID;

/**
 * @author Jason (darkdiplomat)
 */
public class CanaryModifiedAttribute implements ModifiedAttribute {
    private final ModifiableAttributeInstance modAttribute;

    public CanaryModifiedAttribute(ModifiableAttributeInstance modAttribute) {
        this.modAttribute = modAttribute;
    }

    @Override
    public Attribute getAttribute() {
        return getNative().a().getWrapper();
    }

    @Override
    public double getBaseValue() {
        return 0;
    }

    @Override
    public void setBaseValue(double v) {

    }

    @Override
    public AttributeModifier getModifier(UUID uuid) {
        return getNative().a(uuid).getWrapper();
    }

    @Override
    public void apply(AttributeModifier attributeModifier) {
        getNative().a(((CanaryAttributeModifier) attributeModifier).getNative());
    }

    @Override
    public void remove(AttributeModifier attributeModifier) {

    }

    @Override
    public double getValue() {
        return 0;
    }

    public ModifiableAttributeInstance getNative() {
        return modAttribute;
    }
}
