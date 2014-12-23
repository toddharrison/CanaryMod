package net.minecraft.entity.ai.attributes;


import net.canarymod.api.attributes.CanaryModifiedAttribute;

import java.util.Collection;
import java.util.UUID;


public interface IAttributeInstance {

    IAttribute a();

    double b();

    void a(double d0);

    Collection a(int i0);

    Collection c();

    boolean a(AttributeModifier attributemodifier);

    AttributeModifier a(UUID uuid);

    void b(AttributeModifier attributemodifier);

    void c(AttributeModifier attributemodifier);

    double e();

    CanaryModifiedAttribute getWrapper(); // CanaryMod
}
