package net.minecraft.entity.ai.attributes;


import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.server.management.LowerStringMap;
import net.canarymod.api.attributes.CanaryAttributeMap;


public class ServersideAttributeMap extends BaseAttributeMap {

    private final Set e = Sets.newHashSet();
    protected final Map d = new LowerStringMap();
    // CanaryMod: our variables    
    private final CanaryAttributeMap canaryAttributeMap = new CanaryAttributeMap(this);

    public ModifiableAttributeInstance e(IAttribute iattribute) {
        return (ModifiableAttributeInstance) super.a(iattribute);
    }

    public ModifiableAttributeInstance b(String s0) {
        IAttributeInstance iattributeinstance = super.a(s0);

        if (iattributeinstance == null) {
            iattributeinstance = (IAttributeInstance) this.d.get(s0);
        }

        return (ModifiableAttributeInstance) iattributeinstance;
    }

    public IAttributeInstance b(IAttribute iattribute) {
        IAttributeInstance iattributeinstance = super.b(iattribute);

        if (iattribute instanceof RangedAttribute && ((RangedAttribute) iattribute).g() != null) {
            this.d.put(((RangedAttribute) iattribute).g(), iattributeinstance);
        }

        return iattributeinstance;
    }

    protected IAttributeInstance c(IAttribute iattribute) {
        return new ModifiableAttributeInstance(this, iattribute);
    }

    public void a(IAttributeInstance iattributeinstance) {
        if (iattributeinstance.a().c()) {
            this.e.add(iattributeinstance);
        }

        Iterator iterator = this.c.get(iattributeinstance.a()).iterator();

        while (iterator.hasNext()) {
            IAttribute iattribute = (IAttribute) iterator.next();
            ModifiableAttributeInstance modifiableattributeinstance = this.e(iattribute);

            if (modifiableattributeinstance != null) {
                modifiableattributeinstance.f();
            }
        }

    }

    @Override
    public CanaryAttributeMap getWrapper() {
        return canaryAttributeMap;
    }

    public Set b() {
        return this.e;
    }

    public Collection c() {
        HashSet hashset = Sets.newHashSet();
        Iterator iterator = this.a().iterator();

        while (iterator.hasNext()) {
            IAttributeInstance iattributeinstance = (IAttributeInstance) iterator.next();

            if (iattributeinstance.a().c()) {
                hashset.add(iattributeinstance);
            }
        }

        return hashset;
    }

    public IAttributeInstance a(String s0) {
        return this.b(s0);
    }

    public IAttributeInstance a(IAttribute iattribute) {
        return this.e(iattribute);
    }
}
