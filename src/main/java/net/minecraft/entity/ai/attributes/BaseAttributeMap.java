package net.minecraft.entity.ai.attributes;


import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import net.canarymod.api.attributes.CanaryAttributeMap;
import net.minecraft.server.management.LowerStringMap;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.server.management.LowerStringMap;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;


public abstract class BaseAttributeMap {

    protected final Map a = Maps.newHashMap();
    protected final Map b = new LowerStringMap();
    protected final Multimap c = HashMultimap.create();
   
    public IAttributeInstance a(IAttribute iattribute) {
        return (IAttributeInstance) this.a.get(iattribute);
    }

    public IAttributeInstance a(String s0) {
        return (IAttributeInstance) this.b.get(s0);
    }

    public IAttributeInstance b(IAttribute iattribute) {
        if (this.b.containsKey(iattribute.a())) {
            throw new IllegalArgumentException("Attribute is already registered!");
        } else {
            IAttributeInstance iattributeinstance = this.c(iattribute);

            this.b.put(iattribute.a(), iattributeinstance);
            this.a.put(iattribute, iattributeinstance);

            for (IAttribute iattribute1 = iattribute.d(); iattribute1 != null; iattribute1 = iattribute1.d()) {
                this.c.put(iattribute1, iattribute);
            }

            return iattributeinstance;
        }
    }

    protected abstract IAttributeInstance c(IAttribute iattribute);

    public Collection a() {
        return this.b.values();
    }

    public void a(IAttributeInstance iattributeinstance) {}

    public void a(Multimap multimap) {
        Iterator iterator = multimap.entries().iterator();

        while (iterator.hasNext()) {
            Entry entry = (Entry) iterator.next();
            IAttributeInstance iattributeinstance = this.a((String) entry.getKey());

            if (iattributeinstance != null) {
                iattributeinstance.c((AttributeModifier) entry.getValue());
            }
        }

    }

    public void b(Multimap multimap) {
        Iterator iterator = multimap.entries().iterator();

        while (iterator.hasNext()) {
            Entry entry = (Entry) iterator.next();
            IAttributeInstance iattributeinstance = this.a((String) entry.getKey());

            if (iattributeinstance != null) {
                iattributeinstance.c((AttributeModifier) entry.getValue());
                iattributeinstance.b((AttributeModifier) entry.getValue());
            }
        }
    }

    // CanaryMod
    public abstract CanaryAttributeMap getWrapper();
}
