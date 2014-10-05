package net.minecraft.event;


import com.google.common.collect.Maps;
import net.canarymod.api.chat.CanaryClickEvent;
import net.canarymod.api.chat.CanaryClickEventAction;

import java.util.Map;


public class ClickEvent {

    private final ClickEvent.Action a;
    private final String b;
    private final CanaryClickEvent cce; // CanaryMod

    public ClickEvent(ClickEvent.Action clickevent_action, String s0) {
        this.a = clickevent_action;
        this.b = s0;
        this.cce = new CanaryClickEvent(this); // CanaryMod: install wrapper
    }

    public ClickEvent.Action a() {
        return this.a;
    }

    public String b() {
        return this.b;
    }

    public CanaryClickEvent getWrapper() {
        return cce;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        else if (object != null && this.getClass() == object.getClass()) {
            ClickEvent clickevent = (ClickEvent) object;

            if (this.a != clickevent.a) {
                return false;
            }
            else {
                if (this.b != null) {
                    if (!this.b.equals(clickevent.b)) {
                        return false;
                    }
                }
                else if (clickevent.b != null) {
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
        return "ClickEvent{action=" + this.a + ", value=\'" + this.b + '\'' + '}';
    }

    public int hashCode() {
        int i0 = this.a.hashCode();

        i0 = 31 * i0 + (this.b != null ? this.b.hashCode() : 0);
        return i0;
    }

    public static enum Action {
        //CanaryMod: Reduce, Reuse, Recycle (ie: cleaned up a bit)
        OPEN_URL(true), //
        OPEN_FILE(false), //
        RUN_COMMAND(true), //
        TWITCH_USER_INFO(false), //
        SUGGEST_COMMAND(true), //
        CHANGE_PAGE(true);

        private static final Map<String, Action> g = Maps.newHashMap();
        private final boolean h;
        private final CanaryClickEventAction ccea; // CanaryMod

        private Action(boolean flag0) {
            this.h = flag0;
            ccea = new CanaryClickEventAction(this); // CanaryMod: install wrapper
        }

        public boolean a() {
            return this.h;
        }

        public String b() {
            return this.name().toLowerCase();
        }

        // CanaryMod: retreive wrapper
        public CanaryClickEventAction getWrapper() {
            return ccea;
        }
        //

        public static Action a(String aclickevent_action) {
            return f.get(aclickevent_action.toLowerCase());
        }

        static {
            ClickEvent.Action[] aclickevent_action = values();
            int i0 = aclickevent_action.length;

            for (int i1 = 0; i1 < i0; ++i1) {
                ClickEvent.Action clickevent_action = aclickevent_action[i1];

                g.put(clickevent_action.b(), clickevent_action);
            }

        }
    }
}
