package net.minecraft.entity.ai.attributes;

import io.netty.util.internal.ThreadLocalRandom;
import net.canarymod.api.attributes.CanaryAttributeModifier;
import net.minecraft.util.MathHelper;
import org.apache.commons.lang3.Validate;

import java.util.Random;
import java.util.UUID;


public class AttributeModifier {

    private final double a;
    private final int b;
    private final String c;
    private final UUID d;
    private boolean e;
    private CanaryAttributeModifier canaryAttributeModifier;

    public AttributeModifier(String s0, double d0, int i0) {
        this(MathHelper.a((Random) ThreadLocalRandom.current()), s0, d0, i0);
    }

    public AttributeModifier(UUID uuid, String s0, double d0, int i0) {
        this.e = true;
        this.d = uuid;
        this.c = s0;
        this.a = d0;
        this.b = i0;
        Validate.notEmpty(s0, "Modifier name cannot be empty", new Object[0]);
        Validate.inclusiveBetween(0L, 2L, (long) i0, "Invalid operation");
        // CanaryMod: set our variables    
        this.canaryAttributeModifier = new CanaryAttributeModifier(this);
        // End    
    }

    public UUID a() {
        return this.d;
    }

    public String b() {
        return this.c;
    }

    public int c() {
        return this.b;
    }

    public double d() {
        return this.a;
    }

    public boolean e() {
        return this.e;
    }

    public AttributeModifier a(boolean flag0) {
        this.e = flag0;
        return this;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        else if (object != null && this.getClass() == object.getClass()) {
            AttributeModifier attributemodifier = (AttributeModifier) object;

            if (this.d != null) {
                if (!this.d.equals(attributemodifier.d)) {
                    return false;
                }
            }
            else if (attributemodifier.d != null) {
                return false;
            }

            return true;
        }
        else {
            return false;
        }
    }

    public int hashCode() {
        return this.d != null ? this.d.hashCode() : 0;
    }

    public String toString() {
        return "AttributeModifier{amount=" + this.a + ", operation=" + this.b + ", name=\'" + this.c + '\'' + ", id=" + this.d + ", serialize=" + this.e + '}';
    }

    public CanaryAttributeModifier getWrapper() {
        return canaryAttributeModifier;
    }
}
