package net.minecraft.entity.ai.attributes;


import com.google.common.collect.Sets;
import net.canarymod.api.attributes.CanaryAttributeMap;
import net.minecraft.server.management.LowerStringMap;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class ServersideAttributeMap extends BaseAttributeMap {

    private final Set d = Sets.newHashSet();
    protected final Map c = new LowerStringMap();
    private final CanaryAttributeMap canaryAttributeMap = new CanaryAttributeMap(this);

    public ModifiableAttributeInstance c(Attribute attribute) {
        return (ModifiableAttributeInstance) super.a(attribute);
    }

    public ModifiableAttributeInstance b(String s0) {
        AttributeInstance attributeinstance = super.a(s0);

        if (attributeinstance == null) {
            attributeinstance = (AttributeInstance) this.c.get(s0);
        }

        return (ModifiableAttributeInstance) attributeinstance;
    }

    public AttributeInstance b(Attribute attribute) {
        if (this.b.containsKey(attribute.a())) {
            throw new IllegalArgumentException("Attribute is already registered!");
        }
        else {
            ModifiableAttributeInstance modifiableattributeinstance = new ModifiableAttributeInstance(this, attribute);

            this.b.put(attribute.a(), modifiableattributeinstance);
            if (attribute instanceof RangedAttribute && ((RangedAttribute) attribute).f() != null) {
                this.c.put(((RangedAttribute) attribute).f(), modifiableattributeinstance);
            }

            this.a.put(attribute, modifiableattributeinstance);
            return modifiableattributeinstance;
        }
    }

    public void a(ModifiableAttributeInstance modifiableattributeinstance) {
        if (modifiableattributeinstance.a().c()) {
            this.d.add(modifiableattributeinstance);
        }

    }

    @Override
    public CanaryAttributeMap getWrapper() {
        return canaryAttributeMap;
    }

    public Set b() {
        return this.d;
    }

    public Collection c() {
        HashSet hashset = Sets.newHashSet();
        Iterator iterator = this.a().iterator();

        while (iterator.hasNext()) {
            AttributeInstance attributeinstance = (AttributeInstance) iterator.next();

            if (attributeinstance.a().c()) {
                hashset.add(attributeinstance);
            }
        }

        return hashset;
    }

    public AttributeInstance a(String s0) {
        return this.b(s0);
    }

    public AttributeInstance a(Attribute attribute) {
        return this.c(attribute);
    }
}
