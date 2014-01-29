package net.minecraft.entity.ai.attributes;


import com.google.common.collect.Maps;
import net.canarymod.api.attributes.CanaryModifiedAttribute;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;


public class ModifiableAttributeInstance implements AttributeInstance {

    private final BaseAttributeMap a;
    private final Attribute b;
    private final Map c = Maps.newHashMap();
    private final Map d = Maps.newHashMap();
    private final Map e = Maps.newHashMap();
    private double f;
    private boolean g = true;
    private double h;
    private CanaryModifiedAttribute canaryModifiableAttribute;

    public ModifiableAttributeInstance(BaseAttributeMap baseattributemap, Attribute attribute) {
        this.a = baseattributemap;
        this.b = attribute;
        this.f = attribute.b();

        for (int i0 = 0; i0 < 3; ++i0) {
            this.c.put(Integer.valueOf(i0), new HashSet());
        }
        this.canaryModifiableAttribute = new CanaryModifiedAttribute(this);
    }

    public Attribute a() {
        return this.b;
    }

    public double b() {
        return this.f;
    }

    public void a(double d0) {
        if (d0 != this.b()) {
            this.f = d0;
            this.f();
        }
    }

    public Collection a(int i0) {
        return (Collection) this.c.get(Integer.valueOf(i0));
    }

    public Collection c() {
        HashSet hashset = new HashSet();

        for (int i0 = 0; i0 < 3; ++i0) {
            hashset.addAll(this.a(i0));
        }

        return hashset;
    }

    public AttributeModifier a(UUID uuid) {
        return (AttributeModifier) this.e.get(uuid);
    }

    public void a(AttributeModifier attributemodifier) {
        if (this.a(attributemodifier.a()) != null) {
            throw new IllegalArgumentException("Modifier is already applied on this attribute!");
        }
        else {
            Object object = (Set) this.d.get(attributemodifier.b());

            if (object == null) {
                object = new HashSet();
                this.d.put(attributemodifier.b(), object);
            }

            ((Set) this.c.get(Integer.valueOf(attributemodifier.c()))).add(attributemodifier);
            ((Set) object).add(attributemodifier);
            this.e.put(attributemodifier.a(), attributemodifier);
            this.f();
        }
    }

    private void f() {
        this.g = true;
        this.a.a(this);
    }

    public void b(AttributeModifier attributemodifier) {
        for (int i0 = 0; i0 < 3; ++i0) {
            Set set = (Set) this.c.get(Integer.valueOf(i0));

            set.remove(attributemodifier);
        }

        Set set1 = (Set) this.d.get(attributemodifier.b());

        if (set1 != null) {
            set1.remove(attributemodifier);
            if (set1.isEmpty()) {
                this.d.remove(attributemodifier.b());
            }
        }

        this.e.remove(attributemodifier.a());
        this.f();
    }

    public double e() {
        if (this.g) {
            this.h = this.g();
            this.g = false;
        }

        return this.h;
    }

    private double g() {
        double d0 = this.b();

        AttributeModifier attributemodifier;

        for (Iterator iterator = this.a(0).iterator(); iterator.hasNext(); d0 += attributemodifier.d()) {
            attributemodifier = (AttributeModifier) iterator.next();
        }

        double d1 = d0;

        Iterator iterator1;
        AttributeModifier attributemodifier1;

        for (iterator1 = this.a(1).iterator(); iterator1.hasNext(); d1 += d0 * attributemodifier1.d()) {
            attributemodifier1 = (AttributeModifier) iterator1.next();
        }

        for (iterator1 = this.a(2).iterator(); iterator1.hasNext(); d1 *= 1.0D + attributemodifier1.d()) {
            attributemodifier1 = (AttributeModifier) iterator1.next();
        }

        return this.b.a(d1);
    }

    public CanaryModifiedAttribute getWrapper() {
        return canaryModifiableAttribute;
    }
}
