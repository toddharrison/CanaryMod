package net.minecraft.stats;


import net.canarymod.api.statistics.CanaryStat;
import net.minecraft.event.HoverEvent;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;


public class StatBase {

    public final String e;
    private final IChatComponent a;
    public boolean f;
    private final IStatType b;
    private final IScoreObjectiveCriteria c;
    private Class d;
    private static NumberFormat k = NumberFormat.getIntegerInstance(Locale.US);
    public static IStatType g = new IStatType() {
    };
    private static DecimalFormat l = new DecimalFormat("########0.00");
    public static IStatType h = new IStatType() {
    };
    public static IStatType i = new IStatType() {
    };
    public static IStatType j = new IStatType() {
    };
    // CanaryMod
    protected CanaryStat canaryStat;

    public StatBase(String s0, IChatComponent ichatcomponent, IStatType istattype) {
        this.e = s0;
        this.a = ichatcomponent;
        this.b = istattype;
        this.c = new ObjectiveStat(this);
        IScoreObjectiveCriteria.a.put(this.c.a(), this.c);
        // CanaryMod: wrap stat
        this.canaryStat = new CanaryStat(this);
    }

    public StatBase(String s0, IChatComponent ichatcomponent) {
        this(s0, ichatcomponent, g);
    }

    public StatBase i() {
        this.f = true;
        return this;
    }

    public StatBase h() {
        if (StatList.a.containsKey(this.e)) {
            throw new RuntimeException("Duplicate stat id: \"" + ((StatBase) StatList.a.get(this.e)).a + "\" and \"" + this.a + "\" at id " + this.e);
        }
        else {
            StatList.b.add(this);
            StatList.a.put(this.e, this);
            return this;
        }
    }

    public boolean d() {
        return false;
    }

    public IChatComponent e() {
        IChatComponent ichatcomponent = this.a.f();

        ichatcomponent.b().a(EnumChatFormatting.GRAY);
        ichatcomponent.b().a(new HoverEvent(HoverEvent.Action.SHOW_ACHIEVEMENT, new ChatComponentText(this.e)));
        return ichatcomponent;
    }

    public IChatComponent j() {
        IChatComponent ichatcomponent = this.e();
        IChatComponent ichatcomponent1 = (new ChatComponentText("[")).a(ichatcomponent).a("]");

        ichatcomponent1.a(ichatcomponent.b());
        return ichatcomponent1;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        else if (object != null && this.getClass() == object.getClass()) {
            StatBase statbase = (StatBase) object;

            return this.e.equals(statbase.e);
        }
        else {
            return false;
        }
    }

    public int hashCode() {
        return this.e.hashCode();
    }

    public String toString() {
        return "Stat{id=" + this.e + ", nameId=" + this.a + ", awardLocallyOnly=" + this.f + ", formatter=" + this.b + ", objectiveCriteria=" + this.c + '}';
    }

    public IScoreObjectiveCriteria k() {
        return this.c;
    }

    public Class l() {
        return this.d;
    }

    public StatBase b(Class oclass0) {
        this.d = oclass0;
        return this;
    }

    // CanaryMod
    public CanaryStat getCanaryStat() {
        return this.canaryStat;
    }
}
