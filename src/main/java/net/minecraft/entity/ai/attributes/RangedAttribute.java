package net.minecraft.entity.ai.attributes;

import net.canarymod.api.attributes.CanaryRangedAttribute;

public class RangedAttribute extends BaseAttribute {

    private final double a;
    private final double b;
    private String c;

    public RangedAttribute(String s0, double d0, double d1, double d2) {
        super(s0, d0);
        this.a = d1;
        this.b = d2;
        if (d1 > d2) {
            throw new IllegalArgumentException("Minimum value cannot be bigger than maximum value!");
        }
        else if (d0 < d1) {
            throw new IllegalArgumentException("Default value cannot be lower than minimum value!");
        }
        else if (d0 > d2) {
            throw new IllegalArgumentException("Default value cannot be bigger than maximum value!");
        }
        this.canaryAttribute = new CanaryRangedAttribute(this);
    }

    public RangedAttribute a(String s0) {
        this.c = s0;
        return this;
    }

    public String f() {
        return this.c;
    }

    public double a(double d0) {
        if (d0 < this.a) {
            d0 = this.a;
        }

        if (d0 > this.b) {
            d0 = this.b;
        }

        return d0;
    }

    // CanaryMod
    public double getMinValue() {
        return a;
    }

    public double getMaxValue() {
        return b;
    }

    public CanaryRangedAttribute getWrapper() {
        return (CanaryRangedAttribute) super.getWrapper();
    }
    //
}
