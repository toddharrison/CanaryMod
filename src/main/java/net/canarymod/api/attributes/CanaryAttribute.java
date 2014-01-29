package net.canarymod.api.attributes;

import net.minecraft.entity.ai.attributes.BaseAttribute;

/**
 * @author Jason (darkdiplomat)
 */
public class CanaryAttribute implements Attribute {
    private final BaseAttribute attribute;

    public CanaryAttribute(BaseAttribute attribute) {
        this.attribute = attribute;
    }

    @Override
    public String getInternalName() {
        return getNative().a();
    }

    @Override
    public double getDefaultValue() {
        return getNative().b();
    }

    @Override
    public boolean shouldWatch() {
        return getNative().c();
    }

    @Override
    public Attribute setShouldWatch(boolean watch) {
        getNative().a(watch);
        return this;
    }

    public BaseAttribute getNative() {
        return attribute;
    }
}
