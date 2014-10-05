package net.minecraft.util;


import com.google.common.base.Function;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import net.canarymod.api.chat.CanaryChatComponent;

import java.util.Iterator;
import java.util.List;


public abstract class ChatComponentStyle implements IChatComponent {

    protected List a = Lists.newArrayList();
    private ChatStyle b;
    private final CanaryChatComponent canaryChatComponent; // CanaryMod

    public ChatComponentStyle() {
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
        return Iterators.concat(Iterators.forArray(new ChatComponentStyle[]{this}), a((Iterable) this.a));
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
        Iterator iterator = Iterators.concat(Iterators.transform(iterable.iterator(), new Function() {

            public Iterator apply(IChatComponent ichatcomponent) {
                return ichatcomponent.iterator();
            }

            public Object apply(Object object) {
                return this.apply((IChatComponent) object);
            }
        }));

        iterator = Iterators.transform(iterator, new Function() {

            public IChatComponent apply(IChatComponent ichatcomponent) {
                IChatComponent ichatcomponent2 = ichatcomponent.f();

                ichatcomponent2.a(ichatcomponent2.b().n());
                return ichatcomponent2;
            }

            public Object apply(Object object) {
                return this.apply((IChatComponent) object);
            }
        });
        return iterator;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        else if (!(object instanceof ChatComponentStyle)) {
            return false;
        }
        else {
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
