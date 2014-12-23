package net.minecraft.entity.ai.attributes;

import net.canarymod.api.attributes.CanaryAttribute;

public abstract class BaseAttribute implements IAttribute {

    private final IAttribute a;
    private final String b;
    private final double c;
    // CanaryMod: our vars
    protected CanaryAttribute canaryAttribute;
    private boolean d;

    protected BaseAttribute(IAttribute iattribute, String s0, double d0) {
        this.a = iattribute;
        this.b = s0;
        this.c = d0;
        if (s0 == null) {
            throw new IllegalArgumentException("Name cannot be null!");
        }
        this.canaryAttribute = new CanaryAttribute(this);
    }

    public String a() {
        return this.b;
    }

    public double b() {
        return this.c;
    }

    public boolean c() {
        return this.d;
    }

    public BaseAttribute a(boolean flag0) {
        this.d = flag0;
        return this;
    }

    public IAttribute d() {
        return this.a;
    }

    public int hashCode() {
        return this.b.hashCode();
    }

    public boolean equals(Object object) {
        return object instanceof IAttribute && this.b.equals(((IAttribute) object).a());
    }

    public CanaryAttribute getWrapper() {
        return canaryAttribute;
    }
}
