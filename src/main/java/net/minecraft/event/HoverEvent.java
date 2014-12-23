package net.minecraft.event;


import com.google.common.collect.Maps;
import net.canarymod.api.chat.CanaryHoverEvent;
import net.canarymod.api.chat.CanaryHoverEventAction;
import net.minecraft.util.IChatComponent;

import java.util.Map;

import net.minecraft.util.IChatComponent;


public class HoverEvent {

    private final HoverEvent.Action a;
    private final IChatComponent b;
    private final CanaryHoverEvent che; // CanaryMod

    public HoverEvent(HoverEvent.Action hoverevent_action, IChatComponent ichatcomponent) {
        this.a = hoverevent_action;
        this.b = ichatcomponent;
        this.che = new CanaryHoverEvent(this); // CanaryMod: install wrapper
    }

    public HoverEvent.Action a() {
        return this.a;
    }

    public IChatComponent b() {
        return this.b;
    }

    //CanaryMod
    public CanaryHoverEvent getWrapper() {
        return che;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        else if (object != null && this.getClass() == object.getClass()) {
            HoverEvent hoverevent = (HoverEvent) object;

            if (this.a != hoverevent.a) {
                return false;
            }
            else {
                if (this.b != null) {
                    if (!this.b.equals(hoverevent.b)) {
                        return false;
                    }
                }
                else if (hoverevent.b != null) {
                    return false;
                }

                return true;
            }
        }
        else {
            return false;
        }
    }

    public String toString() {
        return "HoverEvent{action=" + this.a + ", value=\'" + this.b + '\'' + '}';
    }

    public int hashCode() {
        int i0 = this.a.hashCode();

        i0 = 31 * i0 + (this.b != null ? this.b.hashCode() : 0);
        return i0;
    }

    public static enum Action {
        //CanaryMod: Reduce, Reuse, Recycle (ie: cleaned up a bit)
        SHOW_TEXT(true),//
        SHOW_ACHIEVEMENT(true),//
        SHOW_ITEM(true), //
        SHOW_ENTITY(true);

        private static final Map<String, Action> e = Maps.newHashMap();
        private final boolean f;
        private final CanaryHoverEventAction chea; // CanaryMod

        private Action(boolean flag0) {
            this.f = flag0;
            this.chea = new CanaryHoverEventAction(this); // CanaryMod: install wrapper
        }

        public boolean a() {
            return this.f;
        }

        public String b() {
            return this.name().toLowerCase();
        }

        // CanaryMod
        public CanaryHoverEventAction getWrapper() {
            return chea;
        }

        public static HoverEvent.Action a(String p_a_0_) {
            return (HoverEvent.Action) e.get(p_a_0_);
        }

        static {
            HoverEvent.Action[] ahoverevent_action = values();
            int i0 = ahoverevent_action.length;

            for (int i1 = 0; i1 < i0; ++i1) {
                HoverEvent.Action hoverevent_action = ahoverevent_action[i1];

                e.put(hoverevent_action.b(), hoverevent_action);
            }

        }
    }
}
