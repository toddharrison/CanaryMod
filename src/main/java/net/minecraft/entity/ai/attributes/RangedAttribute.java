package net.minecraft.entity.ai.attributes;

import net.canarymod.api.attributes.CanaryRangedAttribute;
import net.minecraft.util.MathHelper;

public class RangedAttribute extends BaseAttribute {

    private final double a;
    private final double b;
    private String c;

    public RangedAttribute(IAttribute iattribute, String s0, double d0, double d1, double d2) {
        super(iattribute, s0, d0);
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

    public String g() {
        return this.c;
    }

    public double a(double d0) {
        d0 = MathHelper.a(d0, this.a, this.b);
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
