package net.minecraft.entity.ai.attributes;

import net.canarymod.api.attributes.CanaryAttribute;

public interface IAttribute {

    String a();

    double a(double d0);

    double b();

    boolean c();

    IAttribute d();

    CanaryAttribute getWrapper(); // CanaryMod
}
