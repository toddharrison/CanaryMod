package net.minecraft.entity.ai.attributes;


import com.google.common.collect.Sets;
import net.canarymod.api.attributes.CanaryAttributeMap;
import net.minecraft.server.management.LowerStringMap;

import java.util.*;


public class ServersideAttributeMap extends BaseAttributeMap {

    private final Set d = Sets.newHashSet();
    protected final Map c = new LowerStringMap();
    private final CanaryAttributeMap canaryAttributeMap = new CanaryAttributeMap(this);

    public ModifiableAttributeInstance c(IAttribute IAttribute) {
        return (ModifiableAttributeInstance) super.a(IAttribute);
    }

    public ModifiableAttributeInstance b(String s0) {
        IAttributeInstance attributeinstance = super.a(s0);

        if (attributeinstance == null) {
            attributeinstance = (IAttributeInstance) this.c.get(s0);
        }

        return (ModifiableAttributeInstance) attributeinstance;
    }

    public IAttributeInstance b(IAttribute IAttribute) {
        if (this.b.containsKey(IAttribute.a())) {
            throw new IllegalArgumentException("Attribute is already registered!");
        } else {
            ModifiableAttributeInstance modifiableattributeinstance = new ModifiableAttributeInstance(this, IAttribute);

            this.b.put(IAttribute.a(), modifiableattributeinstance);
            if (IAttribute instanceof RangedAttribute && ((RangedAttribute) IAttribute).f() != null) {
                this.c.put(((RangedAttribute) IAttribute).f(), modifiableattributeinstance);
            }

            this.a.put(IAttribute, modifiableattributeinstance);
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
            IAttributeInstance attributeinstance = (IAttributeInstance) iterator.next();

            if (attributeinstance.a().c()) {
                hashset.add(attributeinstance);
            }
        }

        return hashset;
    }

    public IAttributeInstance a(String s0) {
        return this.b(s0);
    }

    public IAttributeInstance a(IAttribute IAttribute) {
        return this.c(IAttribute);
    }
}
