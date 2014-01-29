package net.minecraft.entity.ai.attributes;


import net.canarymod.api.attributes.CanaryModifiedAttribute;

import java.util.Collection;
import java.util.UUID;


public interface AttributeInstance {

    Attribute a();

    double b();

    void a(double d0);

    Collection c();

    AttributeModifier a(UUID uuid);

    void a(AttributeModifier attributemodifier);

    void b(AttributeModifier attributemodifier);

    double e();

    CanaryModifiedAttribute getWrapper(); // CanaryMod
}
