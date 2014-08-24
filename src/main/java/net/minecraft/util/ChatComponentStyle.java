package net.minecraft.util;


import com.google.common.base.Function;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;

import java.util.Iterator;
import java.util.List;

import net.canarymod.api.chat.CanaryChatComponent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;


public abstract class ChatComponentStyle implements IChatComponent {

    protected List a = Lists.newArrayList();
    private ChatStyle b;
    private final CanaryChatComponent canaryChatComponent; // CanaryMod

    public ChatComponentStyle()
    {
        this.canaryChatComponent = new CanaryChatComponent(this);
    }

    public IChatComponent a(IChatComponent ichatcomponent) {
        ichatcomponent.b().a(this.b());
        this.a.add(ichatcomponent);
        return this;
    }

    public List a() {
        return this.a;
    }

    public IChatComponent a(String s0) {
        return this.a((IChatComponent) (new ChatComponentText(s0)));
    }

    public IChatComponent a(ChatStyle chatstyle) {
        this.b = chatstyle;
        Iterator iterator = this.a.iterator();

        while (iterator.hasNext()) {
            IChatComponent ichatcomponent = (IChatComponent) iterator.next();

            ichatcomponent.b().a(this.b());
        }

        return this;
    }

    public ChatStyle b() {
        if (this.b == null) {
            this.b = new ChatStyle();
            Iterator iterator = this.a.iterator();

            while (iterator.hasNext()) {
                IChatComponent ichatcomponent = (IChatComponent) iterator.next();

                ichatcomponent.b().a(this.b);
            }
        }

        return this.b;
    }

    public Iterator iterator() {
        return Iterators.concat(Iterators.forArray(new ChatComponentStyle[] { this}), a((Iterable) this.a));
    }

    public final String c() {
        StringBuilder stringbuilder = new StringBuilder();
        Iterator iterator = this.iterator();

        while (iterator.hasNext()) {
            IChatComponent ichatcomponent = (IChatComponent) iterator.next();

            stringbuilder.append(ichatcomponent.e());
        }

        return stringbuilder.toString();
    }

    public static Iterator a(Iterable iterable) {
        Iterator object1 = Iterators.concat(Iterators.transform(iterable.iterator(), new Function() {

            public Iterator apply(IChatComponent object1) {
                return object1.iterator();
            }

            public Object apply(Object object1) {
                return this.apply((IChatComponent) object1);
            }
        }));

        object1 = Iterators.transform(object1, new Function() {

            public IChatComponent apply(IChatComponent object1) {
                IChatComponent ichatcomponent2 = object1.f();

                ichatcomponent2.a(ichatcomponent2.b().m());
                return ichatcomponent2;
            }

            public Object apply(Object object1) {
                return this.apply((IChatComponent) object1);
            }
        });
        return object1;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (!(object instanceof ChatComponentStyle)) {
            return false;
        } else {
            ChatComponentStyle chatcomponentstyle = (ChatComponentStyle) object;

            return this.a.equals(chatcomponentstyle.a) && this.b().equals(chatcomponentstyle.b());
        }
    }

    public int hashCode() {
        return 31 * this.b.hashCode() + this.a.hashCode();
    }

    public String toString() {
        return "BaseComponent{style=" + this.b + ", siblings=" + this.a + '}';
    }

    @Override
    public CanaryChatComponent getWrapper() {
	return this.canaryChatComponent;
    }
}
