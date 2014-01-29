package net.minecraft.entity.ai.attributes;

import net.canarymod.api.attributes.CanaryAttribute;

public abstract class BaseAttribute implements Attribute {

    private final String a;
    private final double b;
    private boolean c;
    protected CanaryAttribute canaryAttribute;

    protected BaseAttribute(String s0, double d0) {
        this.a = s0;
        this.b = d0;
        if (s0 == null) {
            throw new IllegalArgumentException("Name cannot be null!");
        }
        this.canaryAttribute = new CanaryAttribute(this);
    }

    public String a() {
        return this.a;
    }

    public double b() {
        return this.b;
    }

    public boolean c() {
        return this.c;
    }

    public BaseAttribute a(boolean flag0) {
        this.c = flag0;
        return this;
    }

    public int hashCode() {
        return this.a.hashCode();
    }

    public CanaryAttribute getWrapper() {
        return canaryAttribute;
    }
}
