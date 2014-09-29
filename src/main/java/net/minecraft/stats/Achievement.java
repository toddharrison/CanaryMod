package net.minecraft.stats;


import net.canarymod.api.statistics.CanaryAchievement;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;


public class Achievement extends StatBase {

    public final int a;
    public final int b;
    public final Achievement c;
    public final String k; // CanaryMod: private => public
    public final ItemStack d;
    private boolean m;

    public Achievement(String s0, String s1, int i0, int i1, Item item, Achievement achievement) {
        this(s0, s1, i0, i1, new ItemStack(item), achievement);
    }

    public Achievement(String s0, String s1, int i0, int i1, Block block, Achievement achievement) {
        this(s0, s1, i0, i1, new ItemStack(block), achievement);
    }

    public Achievement(String s0, String s1, int i0, int i1, ItemStack itemstack, Achievement achievement) {
        super(s0, new ChatComponentTranslation("achievement." + s1, new Object[0]));
        this.d = itemstack;
        this.k = "achievement." + s1 + ".desc";
        this.a = i0;
        this.b = i1;
        if (i0 < AchievementList.a) {
            AchievementList.a = i0;
        }

        if (i1 < AchievementList.b) {
            AchievementList.b = i1;
        }

        if (i0 > AchievementList.c) {
            AchievementList.c = i0;
        }

        if (i1 > AchievementList.d) {
            AchievementList.d = i1;
        }

        this.c = achievement;
        // CanaryMod: wrap achievement
        this.canaryStat = new CanaryAchievement(this);
    }

    public Achievement a() {
        this.f = true;
        return this;
    }

    public Achievement b() {
        this.m = true;
        return this;
    }

    public Achievement c() {
        super.h();
        AchievementList.e.add(this);
        return this;
    }

    public boolean d() {
        return true;
    }

    public IChatComponent e() {
        IChatComponent ichatcomponent = super.e();

        ichatcomponent.b().a(this.g() ? EnumChatFormatting.DARK_PURPLE : EnumChatFormatting.GREEN);
        return ichatcomponent;
    }

    public Achievement a(Class oclass0) {
        return (Achievement) super.b(oclass0);
    }

    public boolean g() {
        return this.m;
    }

    public StatBase b(Class oclass0) {
        return this.a(oclass0);
    }

    public StatBase h() {
        return this.c();
    }

    public StatBase i() {
        return this.a();
    }

    // CanaryMod
    public CanaryAchievement getCanaryAchievement() {
        return (CanaryAchievement) this.canaryStat;
    }
}
